/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Charles on 11/12/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.extender;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.class2.courseoffering.service.helper.CourseOfferingServiceScheduleHelper;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.ActivityOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.RegistrationGroupTransformer;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.kuali.student.r2.core.scheduling.util.container.TimeSlotContainer;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides a layer underneath the CourseOfferingService that is accessible to the rest of the app.
 * The difference between this and the CourseOfferingServiceFacade is that the facade uses the
 * CourseOfferingServiceImpl to implement, while the CourseOfferingService can use the
 * CourseOfferingServiceExtender to implement and others can access the CourseOfferingServiceExtender
 * This allows additional functionality to the app without altering the service contract.
 *
 * @author Kuali Student Team
 */
public class CourseOfferingServiceExtenderImpl implements CourseOfferingServiceExtender {
    private static Logger LOGGER = Logger.getLogger(CourseOfferingServiceExtenderImpl.class);
    private static final String OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE = "unexpected";

    private RegistrationGroupTransformer registrationGroupTransformer;

    private ActivityOfferingTransformer activityOfferingTransformer;

    private SchedulingService schedulingService;

    private SearchService searchService;

    private RoomService roomService;

    @Override
    public List<String> getActivityTypesForFormatId(String id, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<String> activityTypes = new ArrayList<String>();
        //Create the search request
        SearchRequestInfo request = new SearchRequestInfo("lu.search.relatedTypes");
        request.addParam("lu.queryParam.cluId", id);
        request.addParam("lu.queryParam.luOptionalRelationType", CourseAssemblerConstants.COURSE_ACTIVITY_RELATION_TYPE);
        //Execute the search and parse params
        SearchResultInfo result = searchService.search(request, context);
        for(SearchResultRowInfo row : result.getRows()){
            for(SearchResultCellInfo cell: row.getCells()){
                if("lu.resultColumn.cluType".equals(cell.getKey())){
                    activityTypes.add(cell.getValue());
                    break;
                }
            }
        }

        return activityTypes;
    }

    @Override
    public List<ValidationResultInfo> verifyRegistrationGroup(String registrationGroupId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
            List<String> aoIds = vRG_getAoIdsFromRegGroupEfficiently(registrationGroupId, contextInfo);
            return verifyRegistrationGroup(aoIds, contextInfo);
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException(OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE, e);
        }
    }

    /**
     * A more efficient way to verify registration groups
     * @param aoIds Pass in the AO ids belonging to the registration group
     * @param contextInfo
     * @return List of validation results
     */

    @Override
    public List<ValidationResultInfo> verifyRegistrationGroup(List<String> aoIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        List<ValidationResultInfo> validationResultInfos = new ArrayList<ValidationResultInfo>() ;
        try {
            vRG_verifyAoIds(aoIds);
            if (aoIds.size() > 1) { // Only have to do conflict detection between AOs, not within one
                // Maps ao IDs to the time slots of the container
                Map<String, TimeSlotContainer> aoIdToTimeSlotContainer = new HashMap<String, TimeSlotContainer>();
                List<String> previouslySeenAoIds = new ArrayList<String>();
                // Nested loop to pairwise compare every AO's timeslots to every other AO's timeslots looking for a conflict
                for (String thisAoId: aoIds) {
                    TimeSlotContainer thisContainer =
                            vRG_fetchTimeSlotContainerByAoId(thisAoId, aoIdToTimeSlotContainer, contextInfo);
                    // Inner loop
                    // Only iterate through previously seen AOs to avoid double-comparison
                    for (String thatAoId: previouslySeenAoIds) {
                        TimeSlotContainer thatContainer =
                                vRG_fetchTimeSlotContainerByAoId(thatAoId, aoIdToTimeSlotContainer, contextInfo);
                        if (thisContainer.conflictsWith(thatContainer)) {
                            ValidationResultInfo errorInfo =
                                    vRG_createTimeConflictValidationError(thisContainer, thatContainer);
                            validationResultInfos.add(errorInfo);
                            // Exit with error validation
                            return validationResultInfos;
                        }
                    }
                    previouslySeenAoIds.add(thisAoId); // Previously seen
                }
            }
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException(OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE, e);
        }
        ValidationResultInfo success = vRG_createSuccessValidation();
        validationResultInfos.add(success);
        return validationResultInfos;
    }

    // Do quick verification that ao IDs are unique, otherwise algorithm fails
    private void vRG_verifyAoIds(List<String> aoIds) throws OperationFailedException {
        Set aoIdSet = new HashSet(aoIds);
        if (aoIdSet.size() != aoIds.size()) {
            throw new OperationFailedException("Error: Repeated AO IDs in reg groups");
        }
    }

    private List<String> vRG_getAoIdsFromRegGroupEfficiently(String registrationGroupId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        RegistrationGroupInfo registrationGroupInfo = new RegistrationGroupInfo();
        // Partially reconstruct the RG, just enough to get the AO ids (more efficient than fetching RG)
        registrationGroupTransformer.assembleLuiLuiRelations(registrationGroupInfo, registrationGroupId, contextInfo);

        List<String> aoIds = registrationGroupInfo.getActivityOfferingIds();
        return aoIds;
    }

    private ValidationResultInfo vRG_createSuccessValidation() {
        ValidationResultInfo validationResultInfo = new ValidationResultInfo();
        validationResultInfo.setLevel(ValidationResult.ErrorLevel.OK);
        validationResultInfo.setMessage("No time conflict in the Registration Group");
        return validationResultInfo;
    }

    private ValidationResultInfo vRG_createTimeConflictValidationError(TimeSlotContainer first,
                                                                       TimeSlotContainer second) {
        ValidationResultInfo validationResultInfo = new ValidationResultInfo();
        validationResultInfo.setLevel(ValidationResult.ErrorLevel.WARN);
        validationResultInfo.setMessage("time conflict between AO: " + first.getRefObjectId() +
                " and AO: " + second.getRefObjectId());
        return validationResultInfo;
    }

    /**
     * Extracts timeslots (with a preference of ADLs over RDLs) and caches it in the map so it doesn't
     * have to be refetched needlessly
     * @param aoId An activity offering ID
     * @param aoIdToTimeSlotContainer maps the AO Id to the extracted time slot container
     * @param contextInfo
     * @return the time slot container
     */
    private TimeSlotContainer vRG_fetchTimeSlotContainerByAoId(String aoId,
                                                               Map<String, TimeSlotContainer> aoIdToTimeSlotContainer,
                                                               ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        if (!aoIdToTimeSlotContainer.containsKey(aoId)) {
            // Haven't added this ao ID to the map, so add it
            vRG_addTimeSlotContainerToMap(aoId, aoIdToTimeSlotContainer, contextInfo);
        }
        return aoIdToTimeSlotContainer.get(aoId);
    }

    private void vRG_addTimeSlotContainerToMap(String aoId, Map<String, TimeSlotContainer> aoIdToTimeSlotContainer,
                                               ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        List<String> scheduleIds = retrieveScheduleIds(aoId, contextInfo);
        TimeSlotContainer container =
                CourseOfferingServiceScheduleHelper.computeTimeSlotContainer(aoId, scheduleIds, contextInfo);
        aoIdToTimeSlotContainer.put(aoId, container);
        // Fetch time slot infos (do it once, so there's no need to refetch)
        container.fetchTimeSlots(schedulingService, contextInfo);
    }
    /**
     * For this method we need to find the scheduleId for a particular AO ID. This method was created because the
     * code was originally calling the service to get a full AO object from the db. Getting a full AO is a dozen or so
     * calls to the DB and a couple thousand lines of code.
     *
     * This method is a single call to the db. Much faster.
     *
     * This is a first iteration. It is my hope that this method is no longer needed.
     *
     * @param activityOfferingId
     * @param contextInfo
     * @return returns the scheduleId for a particular Activity Offering
     */
    protected List<String> retrieveScheduleIds(String activityOfferingId, ContextInfo contextInfo){

        List<String> sRet = new ArrayList<String>();

        SearchRequestInfo searchRequest = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.SCH_IDS_BY_AO_SEARCH_TYPE.getKey());
        searchRequest.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.AO_ID, activityOfferingId);

        SearchResultInfo searchResult = null;
        try {
            searchResult = getSearchService().search(searchRequest, contextInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (SearchResultRowInfo row : searchResult.getRows()) {
            for(SearchResultCellInfo cellInfo : row.getCells()){
                if(ActivityOfferingSearchServiceImpl.SearchResultColumns.SCHEDULE_ID.equals(cellInfo.getKey())){
                    sRet.add(cellInfo.getValue());
                }
            }
        }

        return sRet;
    }

    /**
     *
     * This method calls the search service to pull a list of AO Codes for a given CO. This is MUCH faster than
     * our old way of pulling the FULL ao objects, when we just need the code.
     *
     * @param courseOfferingId  The CourseOffering ID of the Course Offering that you want to return all AO codes for.
     * @param context  application contextInfo object
     * @return returns a Map<AO_ID, AO_CODE>
     * @throws OperationFailedException
     */
    @Override
    public Map<String, String> computeAoIdToAoCodeMapByCourseOffering(String courseOfferingId, ContextInfo context)
            throws OperationFailedException {

        Map<String, String> activityCodes = new HashMap<String, String>();

        // Query for AO id and codes, and build a Map.
        SearchRequestInfo request = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.AO_CODES_TYPES_BY_CO_ID_SEARCH_KEY);
        request.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, courseOfferingId);

        SearchResultInfo result = null;
        try {
            result = getSearchService().search(request, context);
        } catch (Exception ex){
            throw new OperationFailedException("Unable to search for AO Codes by CO ID", ex);
        }

        List<SearchResultRowInfo> rows = result.getRows();
        //  If there are no rows assume the operation is an add and skip the check.
        if ( ! rows.isEmpty()) {
            for (SearchResultRowInfo row: rows) {
                List<SearchResultCellInfo> cells = row.getCells();
                String aoId = null;
                String aoCode = null;
                for (SearchResultCellInfo cell: cells) {
                    if (cell.getKey().equals(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID)) {
                        aoId = cell.getValue();
                    } else if (cell.getKey().equals(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_CODE)) {
                        aoCode = cell.getValue();
                    }
                }
                activityCodes.put(aoId, aoCode);
            }
        }
        return  activityCodes;
    }

    @Override
    public ActivityOfferingInfo copyActivityOffering(ActivityOfferingInfo sourceAo,
                                              CourseOfferingService coService,
                                              ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        return copyActivityOffering(sourceAo, coService, null, null, context, Collections.EMPTY_LIST);
    }

    @Override
    public ActivityOfferingInfo copyActivityOffering(ActivityOfferingInfo sourceAo,
                                                     CourseOfferingService coService,
                                                     FormatOfferingInfo targetFo,
                                                     String targetTermId,
                                                     ContextInfo context,
                                                     List<String> optionKeys)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        ActivityOfferingInfo targetAO = new ActivityOfferingInfo(sourceAo);
        if (targetFo != null) {
            // Override FO if copying CO
            targetAO.setFormatOfferingId(targetFo.getId());
        }
        if (targetTermId != null) {
            targetAO.setTermId(targetTermId);
        }
        targetAO.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY); // Need to set to draft
        targetAO.setId(null);
        targetAO.getScheduleIds().clear();
        if (optionKeys.contains(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY)) {
            targetAO.getInstructors().clear();
        }
        if (targetAO.getInstructors() != null && !targetAO.getInstructors().isEmpty()) {
            for (OfferingInstructorInfo inst : targetAO.getInstructors()) {
                inst.setId(null);
            }
        }
        targetAO.setActivityCode(null);
        targetAO = coService.createActivityOffering(targetAO.getFormatOfferingId(), targetAO.getActivityId(), targetAO.getTypeKey(), targetAO, context);

        // have to copy rules AFTER AO is created because the link is by the AO id
        activityOfferingTransformer.copyRulesFromExistingActivityOffering(sourceAo, targetAO, new ArrayList<String>());

        /**
         * Create ScheduleRequests on the target AO. Use ScheduleComponents (ADL) to create the RDLs if any exist.
         * Otherwise, copy the requests from the source AO.
         */
        List<ScheduleRequestInfo> scheduleRequestInfos = new ArrayList<ScheduleRequestInfo>();
        String sourceAoId = sourceAo.getId();
        // Get all SRSes associated with the AO (typically, just one, but with partial colo, could be many --cclin)
        List<ScheduleRequestSetInfo> srsList =
                schedulingService.getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,
                        sourceAoId, context);
        if (srsList.size() > 1) {
            throw new OperationFailedException("Copy AO: Only one SRS is currently supported");
        }

        if (!optionKeys.contains(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY)) {
            // rolloverCourseOffering has option keys
            if (copyAO_hasADLs(sourceAo)) {
                copyAO_copyWithADLs(srsList, targetAO, sourceAo.getStateKey(), coService, context);
            } else {
                copyAO_copyWithoutADLs(srsList, targetAO, context);
            }
        }

        try {
            List<SeatPoolDefinitionInfo> sourceSPList = coService.getSeatPoolDefinitionsForActivityOffering(sourceAoId, context);
            if (sourceSPList != null && !sourceSPList.isEmpty()) {
                for (SeatPoolDefinitionInfo sourceSP : sourceSPList) {
                    SeatPoolDefinitionInfo targetSP = new SeatPoolDefinitionInfo(sourceSP);
                    targetSP.setId(null);
                    targetSP.setTypeKey(LuiServiceConstants.SEATPOOL_LUI_CAPACITY_TYPE_KEY);
                    targetSP.setStateKey(LuiServiceConstants.LUI_CAPACITY_ACTIVE_STATE_KEY);
                    SeatPoolDefinitionInfo seatPoolCreated = coService.createSeatPoolDefinition(targetSP, context);
                    coService.addSeatPoolDefinitionToActivityOffering(seatPoolCreated.getId(), targetAO.getId(), context);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Generate Registration Groups based on the copied AO
        //generateRegistrationGroupsForFormatOffering(targetAO.getFormatOfferingId(),context);

        return targetAO;
    }

    private boolean copyAO_hasADLs(ActivityOfferingInfo sourceAO) {
        return sourceAO.getScheduleIds() != null && ! sourceAO.getScheduleIds().isEmpty();
    }

    private ScheduleRequestSetInfo copyAo_buildDefaultSRSInfo(ScheduleRequestSetInfo set, ActivityOfferingInfo targetAO) {
        ScheduleRequestSetInfo newSrs = new ScheduleRequestSetInfo(set);
        newSrs.getRefObjectIds().clear();
        // Add the newly created AO
        newSrs.getRefObjectIds().add(targetAO.getId());
        newSrs.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET);
        newSrs.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_STATE_CREATED);
        newSrs.setMaxEnrollmentShared(false); // kind of arbitrary, but feel booleans should be set --cclin
        newSrs.setName("Schedule request set for " + targetAO.getCourseOfferingCode() + " - " + targetAO.getActivityCode());
        // Must clear out old ID
        newSrs.setId(null);
        return newSrs;
    }

    private void copyAO_copyWithADLs(List<ScheduleRequestSetInfo> srsList,
                                             ActivityOfferingInfo targetAO,
                                             String sourceAOStateKey,
                                             CourseOfferingService coService,
                                             ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException {

        for (ScheduleRequestSetInfo originalSRSet: srsList) {
            // Should only loop once since exception thrown if multiple SRSes --cclin
            List<String> coloAoIds = originalSRSet.getRefObjectIds(); // Set of AOs associated with the SRS
            boolean isColocated = coloAoIds.size() > 1;

            if (isColocated) {
                // Change state to match target AO, otherwise stays in draft
                // A little hack--the validation forces creation of AOs to be in draft
                coService.changeActivityOfferingState(targetAO.getId(), sourceAOStateKey, context);
            }
            // Convert ADLs to RDLS
            // Step 1: Create the SRS
            ScheduleRequestSetInfo newSrs = copyAo_buildDefaultSRSInfo(originalSRSet, targetAO);
            if (isColocated) {
                // Add rest of AO ids if colocated
                newSrs.getRefObjectIds().addAll(originalSRSet.getRefObjectIds());
            }
            String newSrsTypeKey = newSrs.getTypeKey();
            String newRefObjectTypeKey = newSrs.getRefObjectTypeKey();
            ScheduleRequestSetInfo createdSRSet =
                    schedulingService.createScheduleRequestSet(newSrsTypeKey, newRefObjectTypeKey, newSrs, context);

            // Step 2: get the scheduleIds from the schedule requests (instead of using AO schedule IDs)
            List<ScheduleRequestInfo> sourceRequests =
                    schedulingService.getScheduleRequestsByScheduleRequestSet(originalSRSet.getId(), context);
            for (ScheduleRequestInfo request: sourceRequests) {
                // Step 3: for each schedule ID, create a schedule request and attach it to newly created SRS
                String schedId = request.getScheduleId(); // Get the ID of the ADL

                if (schedId == null) {
                    // If the schedule Id is null it means that the RDL has been saved, but hasn't been submitted to the
                    // "scheduler" for processing. So, just move on the the next item.
                    continue;
                }
                ScheduleInfo schedule = schedulingService.getSchedule(schedId, context);
                ScheduleRequestInfo newRequest = SchedulingServiceUtil.scheduleToRequest(schedule, roomService, context);
                newRequest.setScheduleRequestSetId(createdSRSet.getId()); // Attach to SRS
                // Persist to DB
                ScheduleRequestInfo savedRequest =
                        schedulingService.createScheduleRequest(newRequest.getTypeKey(), newRequest, context);
                if (isColocated) {
                    // Delete the original schedule requests since they are being replaced by new schedule requests
                    schedulingService.deleteScheduleRequest(request.getId(), context);
                }
            }

            if (isColocated) {
                // Delete original SRS if colocated
                schedulingService.deleteScheduleRequestSet(originalSRSet.getId(), context);
                // Schedule the AO.
                // (No need to delete schedules since scheduleActivityOffering deletes schedule IDs
                // before rescheduling)
                coService.scheduleActivityOffering(targetAO.getId(), context);
            }
        }
    }

    private ScheduleRequestInfo _buildDefaultScheduleRequest(ScheduleRequestInfo request) {
        ScheduleRequestInfo copySchedReq = new ScheduleRequestInfo(request);
        copySchedReq.setId(null); // Clear out ID
        for (ScheduleRequestComponentInfo comp: copySchedReq.getScheduleRequestComponents()) {
            // Null out the IDs
            comp.setId(null);
        }
        return copySchedReq;
    }

    private void copyAO_copyWithoutADLs(List<ScheduleRequestSetInfo> srsList,
                                                ActivityOfferingInfo targetAO,
                                                ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {

        for (ScheduleRequestSetInfo set: srsList) {
            // Should only loop once since exception thrown if multiple SRSes --cclin
            List<String> coloAoIds = set.getRefObjectIds(); // Set of AOs associated with the SRS
            boolean isColocated = coloAoIds.size() > 1;
            if (isColocated) {
                // Just add the AO to the colo and update
                set.getRefObjectIds().add(targetAO.getId());
                try {
                    schedulingService.updateScheduleRequestSet(set.getId(), set, context);
                } catch (VersionMismatchException e) {
                    // Re-wrap exception
                    throw new OperationFailedException("Version mismatch: " + e.getMessage());
                }
            } else {
                // Not co-located so need to create a new SRS
                ScheduleRequestSetInfo newSrs = copyAo_buildDefaultSRSInfo(set, targetAO);
                String newSrsTypeKey = newSrs.getTypeKey();
                String newRefObjectTypeKey = newSrs.getRefObjectTypeKey();
                ScheduleRequestSetInfo createdSRSet =
                        schedulingService.createScheduleRequestSet(newSrsTypeKey, newRefObjectTypeKey, newSrs, context);

                // Fetch all SRs attached to original SRS. Make copy of each request and attach to new SRS
                List<ScheduleRequestInfo> requests =
                        schedulingService.getScheduleRequestsByScheduleRequestSet(set.getId(), context);
                for (ScheduleRequestInfo request: requests) {
                    // For each request, create a copy, and attach it to new SRS
                    ScheduleRequestInfo copySchedReq = SchedulingServiceUtil.copyScheduleRequest(request, createdSRSet.getId());
                    // Persist to DB
                    schedulingService.createScheduleRequest(copySchedReq.getTypeKey(), copySchedReq, context);
                }
            }
        }
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public void setRegistrationGroupTransformer(RegistrationGroupTransformer registrationGroupTransformer) {
        this.registrationGroupTransformer = registrationGroupTransformer;
    }

    public void setActivityOfferingTransformer(ActivityOfferingTransformer activityOfferingTransformer) {
        this.activityOfferingTransformer = activityOfferingTransformer;
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public RoomService getRoomService() {
        return roomService;
    }
}
