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

import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.class2.courseoffering.service.helper.CourseOfferingServiceScheduleHelper;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.ActivityOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.RegistrationGroupTransformer;
import org.kuali.student.enrollment.class2.courseofferingset.service.facade.RolloverAssist;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.AttributeInfo;
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
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
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
    public static final String COPY_OPERATION_COPY_AO = "copyAO";
    public static final String COPY_OPERATION_COPY_CO = "copyCO";
    public static final String COPY_OPERATION_ROLLOVER = "rollover";

    private static final String OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE = "unexpected";

    private RegistrationGroupTransformer registrationGroupTransformer;

    private ActivityOfferingTransformer activityOfferingTransformer;

    private SchedulingService schedulingService;

    private SearchService searchService;

    private RoomService roomService;

    private AcademicCalendarService academicCalendarService;

    private CourseOfferingSetService socService;

    @Override
    public List<String> getActivityTypesForFormatId(String formatId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<String> activityTypes = new ArrayList<String>();
        // Create the search request
        SearchRequestInfo request = new SearchRequestInfo("lu.search.relatedTypes");
        request.addParam("lu.queryParam.cluId", formatId);
        request.addParam("lu.queryParam.luOptionalRelationType", CourseAssemblerConstants.COURSE_ACTIVITY_RELATION_TYPE);
        // Execute the search and parse params
        SearchResultInfo result = searchService.search(request, context);
        for (SearchResultRowInfo row : result.getRows()) {
            for (SearchResultCellInfo cell: row.getCells()) {
                if ("lu.resultColumn.cluType".equals(cell.getKey())) {
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
     * @param contextInfo The context
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
        Set<String> aoIdSet = new HashSet<String>(aoIds);
        if (aoIdSet.size() != aoIds.size()) {
            throw new OperationFailedException("Error: Repeated AO IDs in reg groups");
        }
    }

    /**
     * Given a reg group ID, finds the AO ids within the RG without fetching the RG
     * @param registrationGroupId A reg group ID
     * @param contextInfo The context info
     * @return List of AO ids
     */
    private List<String> vRG_getAoIdsFromRegGroupEfficiently(String registrationGroupId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        RegistrationGroupInfo registrationGroupInfo = new RegistrationGroupInfo();
        // Partially reconstruct the RG, just enough to get the AO ids (more efficient than fetching RG)
        registrationGroupTransformer.assembleLuiLuiRelations(registrationGroupInfo, registrationGroupId, contextInfo);

        List<String> aoIds = registrationGroupInfo.getActivityOfferingIds();
        return aoIds;
    }

    /**
     * Create a success validationResultInfo object
     * @return A success validationResultInfo object
     */
    private ValidationResultInfo vRG_createSuccessValidation() {
        ValidationResultInfo validationResultInfo = new ValidationResultInfo();
        validationResultInfo.setLevel(ValidationResult.ErrorLevel.OK);
        validationResultInfo.setMessage("No time conflict in the Registration Group");
        return validationResultInfo;
    }

    /**
     * Create a time conflict validationResultInfo object
     * @return A time conflict validationResultInfo object
     */
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
     * @param contextInfo The context
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
     * @param activityOfferingId The ao ID for fetching the schedules
     * @param contextInfo The context
     * @return returns the scheduleId for a particular Activity Offering
     */
    protected List<String> retrieveScheduleIds(String activityOfferingId, ContextInfo contextInfo){

        List<String> sRet = new ArrayList<String>();

        SearchRequestInfo searchRequest
                = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.SCH_IDS_BY_AO_SEARCH_TYPE.getKey());
        searchRequest.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.AO_ID, activityOfferingId);

        SearchResultInfo searchResult;
        try {
            searchResult = getSearchService().search(searchRequest, contextInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (SearchResultRowInfo row : searchResult.getRows()) {
            for (SearchResultCellInfo cellInfo : row.getCells()) {
                if (ActivityOfferingSearchServiceImpl.SearchResultColumns.SCHEDULE_ID.equals(cellInfo.getKey())) {
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

        SearchResultInfo result;
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

        return copyActivityOffering(COPY_OPERATION_COPY_AO, sourceAo, coService,
                null, null, context, Collections.<String>emptyList());
    }

    @Override
    public ActivityOfferingInfo copyActivityOffering(String operation,
                                                     ActivityOfferingInfo sourceAo,
                                                     CourseOfferingService coService,
                                                     FormatOfferingInfo targetFo,
                                                     String targetTermIdCustom,
                                                     ContextInfo context,
                                                     List<String> optionKeys)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        ActivityOfferingInfo ao =
            createTargetActivityOfferingCommon(operation, null, null, sourceAo, targetFo,
                    targetTermIdCustom, optionKeys, coService, context);
        return ao;
    }

    private void common_copySeatpools(ActivityOfferingInfo sourceAo, ActivityOfferingInfo targetAO, CourseOfferingService coService, ContextInfo context) {
        try {
            List<SeatPoolDefinitionInfo> sourceSPList = coService.getSeatPoolDefinitionsForActivityOffering(sourceAo.getId(), context);
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
    }

    private boolean common_hasADLs(ActivityOfferingInfo sourceAO) {
        return sourceAO.getScheduleIds() != null && ! sourceAO.getScheduleIds().isEmpty();
    }

    private ScheduleRequestSetInfo common_buildDefaultSRSInfo(ScheduleRequestSetInfo set, ActivityOfferingInfo targetAO) {
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

    /**
     * @param operation The operation being performed (not used, but may be useful in debugging)
     * @param sourceSRS Source SRS
     * @param targetSRS The target SRS, if it's available.  Can be null, indicating it doesn't yet exist.  If
     *                  it exists, it should have schedule requests attached to it
     * @param targetAo The "copied" AO (from source)
     * @param sourceAndTargetHaveSameTerm true, if source AO and target AO are in the same term
     * @param context The context
     * @return A targetSRS if it is null and a targetSRS is created

     */
    private ScheduleRequestSetInfo common_copySourceRDLsToTargetRDLs(String operation,
                                                                     ScheduleRequestSetInfo sourceSRS,
                                                                     ScheduleRequestSetInfo targetSRS,
                                                                     ActivityOfferingInfo targetAo,
                                                                     boolean sourceAndTargetHaveSameTerm,
                                                                     ContextInfo context)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException,
            VersionMismatchException, InvalidParameterException, ReadOnlyException,
            MissingParameterException, DataValidationErrorException {

        boolean isColocated = sourceSRS.getRefObjectIds().size() > 1;
        ScheduleRequestSetInfo resultTargetSRS;
        if (sourceAndTargetHaveSameTerm) {
            if (isColocated) {
                // This is copying an AO within the same term.  If the source AO was already co-located, and
                // the source AO had no ADLs (only RDLs), then add the target AO to the source SRS (and thus
                // continues to be co-located) --cclin
                sourceSRS.getRefObjectIds().add(targetAo.getId());
                // Note: the sourceSRS and the targetSRS should be identical in this case (or the targetSRS could
                //       be null.
                resultTargetSRS = schedulingService.updateScheduleRequestSet(sourceSRS.getId(), sourceSRS, context);
            } else {
                // The source AO was not co-located, so the copied AO has its own SRS, and we copy
                // the RDLs from the source AO to the target SRS
                resultTargetSRS = common_createOrUpdateTargetScheduleRequestSet(sourceSRS, targetSRS, targetAo, context);
            }
        } else {
            resultTargetSRS = common_createOrUpdateTargetScheduleRequestSet(sourceSRS, targetSRS, targetAo, context);
        }
        return resultTargetSRS;
    }

    private ScheduleRequestSetInfo common_createOrUpdateTargetScheduleRequestSet(ScheduleRequestSetInfo sourceSRS,
                                                                                 ScheduleRequestSetInfo targetSRS,
                                                                                 ActivityOfferingInfo targetAo,
                                                                                 ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        ScheduleRequestSetInfo resultTargetSRS;
        if (targetSRS == null) {
            resultTargetSRS = common_createTargetSRSFromRDLs(sourceSRS, targetAo, context);
        } else {
            // If an AO is copied across terms, then it is part of a rollover or Copy CO
            // Colocation means something else, that is, if rhe source AO was colocated in the source
            // term the target AO will be colocated in the target term, but the target AO can't
            // be (of course) colocated with the source AO
            // And, it turns out being co-located doesn't matter with RDLs.  You just add the target AO to
            // the target SRS (which is constructed prior to calling this method).
            targetSRS.getRefObjectIds().add(targetAo.getId());
            resultTargetSRS = schedulingService.updateScheduleRequestSet(targetSRS.getId(), targetSRS, context);
        }
        return resultTargetSRS;
    }

    private ScheduleRequestSetInfo common_createTargetSRSFromRDLs(ScheduleRequestSetInfo sourceSRS,
                                                                  ActivityOfferingInfo targetAo,
                                                                  ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ScheduleRequestSetInfo targetSRS
                = common_buildDefaultSRSInfo(sourceSRS, targetAo);
        targetSRS = schedulingService.createScheduleRequestSet(targetSRS.getTypeKey(),
                targetSRS.getRefObjectTypeKey(), targetSRS, context);
        List<ScheduleRequestInfo> sourceRequests =
                schedulingService.getScheduleRequestsByScheduleRequestSet(sourceSRS.getId(), context);
        for (ScheduleRequestInfo sourceRequest: sourceRequests) {
            // For each request, create a copy, and attach it to new SRS
            ScheduleRequestInfo targetRequest = SchedulingServiceUtil.copyScheduleRequest(sourceRequest, targetSRS.getId());
            // Persist to DB..we refer to targetRequest even though it isn't used in case someone is debugging and
            // wants to see the variable.
            targetRequest =
                    schedulingService.createScheduleRequest(targetRequest.getTypeKey(), targetRequest, context);
        }
        return targetSRS;
    }

    private SocInfo getMainSoc(String termId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {

        return CourseOfferingSetUtil.getMainSocForTermId(termId, context);
    }

    /**
     * Adds a list of AO ids to the SRS's refObjectIds, but without duplication
     * @param srs A schedule request set
     * @param aoIds The ao IDs to add
     */
    private void common_safelyAddAoIdsToSRS(ScheduleRequestSetInfo srs, List<String> aoIds) {
        Set<String> aoIdSet = new HashSet<String>();
        aoIdSet.addAll(srs.getRefObjectIds()); // Add original IDs from srs
        aoIdSet.addAll(aoIds); // Then, add additional aoIds, but don't duplicate ids
        srs.getRefObjectIds().clear();
        srs.getRefObjectIds().addAll(aoIdSet);
    }

    /**
     * Adds a single AO id to the SRS's refObjectIds, but without duplication
     * @param srs A schedule request set
     * @param aoId The ao ID to add
     */
    private void common_safelyAddOneAoIdToSRS(ScheduleRequestSetInfo srs, String aoId) {
        List<String> aoIds = new ArrayList<String>();
        aoIds.add(aoId);
        common_safelyAddAoIdsToSRS(srs, aoIds);
    }

    /**
     * The logic for this is somewhat complex.  In rollover, it's possible for a targetSRS to exist (e.g., AO1
     * and AO2 are co-located in source term, and AO1 has been rolled over, but AO2 has not) and the ADLs merely
     * convert to RDLs.  For copy CO with different source/target terms, targetSRS should be null since colocation
     * is broken when copying a CO.  For copy CO within the same source/target term or copy AO (which is always
     * in the same source/target term), targetSRS should be null (it will be ignored, regardless) and a new targetSRS
     * @param sourceSRS The source SRS
     * @param targetSRS Could be null, if so, this method creates one
     * @param targetAo The AO just created
     * @param sourceAndTargetHaveSameTerm true, if the AO is being copied within the same parent term
     * @param context The context
     * @return The target SRS
     */
    private ScheduleRequestSetInfo common_copySourceADLsToTargetRDLs(String operation,
                                                                     ScheduleRequestSetInfo sourceSRS,
                                                                     ScheduleRequestSetInfo targetSRS,
                                                                     ActivityOfferingInfo targetAo,
                                                                     boolean sourceAndTargetHaveSameTerm,
                                                                     CourseOfferingService coService,
                                                                     ContextInfo context)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException,
            VersionMismatchException, InvalidParameterException, ReadOnlyException,
            MissingParameterException, DataValidationErrorException {

        boolean isColocated = sourceSRS.getRefObjectIds().size() > 1;
        ScheduleRequestSetInfo resultTargetSRS;
        // Schedules are converted to requests
        if (operation.equals(COPY_OPERATION_ROLLOVER) && targetSRS != null) {
            // If it exists, then add the target AO to the list (thus, must be co-located)
            common_safelyAddOneAoIdToSRS(targetSRS, targetAo.getId());
            resultTargetSRS = schedulingService.updateScheduleRequestSet(targetSRS.getId(), targetSRS, context);
            return resultTargetSRS; // Exit if it's rollover so we don't bother with rest of code
        } else if (targetSRS == null || sourceAndTargetHaveSameTerm || operation.equals(COPY_OPERATION_COPY_AO)) {
            // Create a new target SRS
            resultTargetSRS = common_createTargetSRSFromADLs(sourceSRS, targetAo, context);
        } else {
            resultTargetSRS = targetSRS;
        }
        // WARN! -----------------------------------------------------------
        // WARN! .....At this point, use resultTargetSRS, and not targetSRS (which is out-of-date)
        if (isColocated && sourceAndTargetHaveSameTerm) {
            // This is the co-located, copy CO or copy AO with ADL case
            // Add the AOs from the sourceSRS to target SRS
            common_safelyAddAoIdsToSRS(resultTargetSRS, sourceSRS.getRefObjectIds());
            resultTargetSRS = schedulingService.updateScheduleRequestSet(resultTargetSRS.getId(), resultTargetSRS, context);
            // Delete the source SRS (this cascades the deletes)
            schedulingService.deleteScheduleRequestSet(sourceSRS.getId(), context);
            // Re-schedule for every AO in the colo (this might happen repeatedly, so it could be inefficient)
            // --cclin
            coService.scheduleActivityOffering(targetAo.getId(), context);
            // TODO: KSENROLL-11107 May want to move this to CourseOfferingServiceImpl::scheduleActivityOffering()
            SocInfo socInfo = getMainSoc(targetAo.getTermId(), context);
            if (socInfo == null) {
                throw new OperationFailedException("Unable to find soc for AO: " + targetAo.getId());
            } else {
                if (CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY.equals(socInfo.getStateKey())) {
                    // Published SOC leads to offered
                    coService.changeActivityOfferingState(targetAo.getId(),
                            LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY, context);
                } else {
                    // All other SOC states lead to approved
                    coService.changeActivityOfferingState(targetAo.getId(),
                            LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, context);
                }
            }
            // TODO: end of the code to remove for KSNEROLL-11107 (see above TODO).
        }
        return resultTargetSRS;
    }

    private ScheduleRequestSetInfo common_createTargetSRSFromADLs(ScheduleRequestSetInfo sourceSRS,
                                                                  ActivityOfferingInfo targetAo,
                                                                  ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ScheduleRequestSetInfo targetSRS;
        targetSRS = common_buildDefaultSRSInfo(sourceSRS, targetAo);
        targetSRS = schedulingService.createScheduleRequestSet(targetSRS.getTypeKey(),
                targetSRS.getRefObjectTypeKey(), targetSRS, context);

        // Use SRS to get Schedule Ids (note: getScheduleIds() from AO may produce a superset of IDs
        // compared to fetching it this way) --cclin
        List<String> scheduleIds = new ArrayList<String>();
        List<ScheduleRequestInfo> sourceRequests =
                schedulingService.getScheduleRequestsByScheduleRequestSet(sourceSRS.getId(), context);
        // Note: that although we access source requests, we extract out the schedule ID.  When a request
        //       has been scheduled, both the request, and the AO get the schedule IDs (not ideal, since this
        //       duplicates schedule IDs --cclin
        for (ScheduleRequestInfo request: sourceRequests) {
            if (request.getScheduleId() == null) {
                throw new OperationFailedException("common_createTargetSRSFromADLs: should have non null schedules");
            }
            scheduleIds.add(request.getScheduleId());
        }
        List<ScheduleRequestInfo> targetRequests =
                common_createTargetScheduleRequestsFromScheduleIds(scheduleIds, targetAo,
                        targetSRS.getId(), context);
        return targetSRS;
    }

    private  List<ScheduleRequestInfo> common_createTargetScheduleRequestsFromScheduleIds(List<String> sourceScheduleIds,
                                                                                          ActivityOfferingInfo targetAo,
                                                                                          String targetScheduleRequestSetId,
                                                                                          ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException,
            DataValidationErrorException {
        List<ScheduleRequestInfo> requests = new ArrayList<ScheduleRequestInfo>();
        for (String sourceSchedId : sourceScheduleIds) {
            // copy source SRCs to target
            ScheduleInfo sourceSchedule = schedulingService.getSchedule(sourceSchedId, context);
            ScheduleRequestInfo targetSchedRequest = SchedulingServiceUtil.scheduleToRequest( sourceSchedule, roomService, context );

            // set name & descr on target
            StringBuilder nameBuilder = new StringBuilder("Schedule request for ");
            nameBuilder.append(targetAo.getCourseOfferingCode()).append(" - ").append(targetAo.getActivityCode());
            targetSchedRequest.setName(nameBuilder.toString());
            targetSchedRequest.setDescr(sourceSchedule.getDescr());

            // create the target SR
            targetSchedRequest.setScheduleRequestSetId(targetScheduleRequestSetId);
            ScheduleRequestInfo created =
                    schedulingService.createScheduleRequest(targetSchedRequest.getTypeKey(), targetSchedRequest, context);
            requests.add(created);
        }
        return requests;
    }
    // ----------------------------
    @Override
    public ActivityOfferingInfo createTargetActivityOfferingForRollover(ActivityOfferingInfo sourceAo,
                                                                        FormatOfferingInfo targetFo,
                                                                        String targetTermIdCustom,
                                                                        RolloverAssist rolloverAssist,
                                                                        String rolloverId,
                                                                        List<String> optionKeys,
                                                                        CourseOfferingService coService,
                                                                        ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DataValidationErrorException, ReadOnlyException {
        ActivityOfferingInfo targetAo =
                createTargetActivityOfferingCommon(COPY_OPERATION_ROLLOVER, rolloverAssist, rolloverId, sourceAo, targetFo,
                        targetTermIdCustom, optionKeys, coService, context);
        return targetAo;
    }

    @Override
    public ActivityOfferingInfo copyActivityOfferingInMemory(ActivityOfferingInfo sourceAo) {
        ActivityOfferingInfo targetAo = new ActivityOfferingInfo(sourceAo);
        targetAo.setId(null);
        // clear out the ids on the internal sub-objects
        for (AttributeInfo attr : targetAo.getAttributes()) {
            attr.setId(null);
        }
        for (OfferingInstructorInfo instr : targetAo.getInstructors()) {
            instr.setId(null);
        }
        targetAo.setMeta(null); // reset to null--create will add a new one
        return targetAo;
    }

    /**
     * This is the "consolidated version of copy AO that is used by multiple areas (in copyActivityOffering,
     * rolloverCourseOffering, etc)
     * @param operation A string that indicates what operation is being performed (copyAO, copyCO, rollover)
     * @param rolloverAssist Used in true rollover to handle colocating target term AOs (from colocated source
     *                       term AOs)
     * @param rolloverId The id used for rolloverAssist (rolloverAssist can handle multiple rollovers, so the
     *                   ID is used to specify a single rollover)
     * @param sourceAo The source AO (from which the copy occurred
     * @param targetFo The targetFo which the target AO is associated with
     * @param targetTermId The term ID for the target AO (useful for subterms).  If null, it uses the term ID
     *                     from the targetFo
     * @param optionKeys Rollover option keys
     * @param coService Pass in the course offering service (a bit of a hack) to avoid potential circular
     *                  dependencies
     * @param context The context info
     * @return The target AO created
     */
    private ActivityOfferingInfo createTargetActivityOfferingCommon(String operation,
                                                                    RolloverAssist rolloverAssist,
                                                                    String rolloverId,
                                                                    ActivityOfferingInfo sourceAo,
                                                                    FormatOfferingInfo targetFo,
                                                                    String targetTermId,
                                                                    List<String> optionKeys,
                                                                    CourseOfferingService coService,
                                                                    ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DataValidationErrorException, ReadOnlyException {

        ActivityOfferingInfo targetAo = copyActivityOfferingInMemory(sourceAo);
        boolean isForRollover = operation.equals(COPY_OPERATION_ROLLOVER);

        // Rolled over AO should be in draft state
        targetAo.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
        if (!isForRollover) {
            // In rollover, we copy the activity code, but in non-rollover, it's left blank so the service can
            // decide on the AO code
            targetAo.setActivityCode(null);
        }

        // Customize other aspects such overriding FO id and terms
        if (targetFo != null) {
            targetAo.setFormatOfferingId(targetFo.getId());
        }
        // Replace term information with target term
        if (targetTermId != null) {
            targetAo.setTermId(targetTermId);
        }
        // No need to set term code as createActivityOffering already does that
        // target should have no ADLs
        targetAo.setScheduleIds(Collections.<String>emptyList());
        if (optionKeys.contains(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY)) {
            targetAo.getInstructors().clear();
        }

        // The temp context will signal the services to skip over validation of activity offering code
        ContextInfo tempContext = context; // Default to current context
        List<AttributeInfo> attrs;
        if (isForRollover) {
            // Add an attribute during rollover to
            tempContext = new ContextInfo(context);
            attrs = new ArrayList<AttributeInfo>();
            // If an "unique code service" is created, then skipping the validation may not be needed
            AttributeInfo info = new AttributeInfo("skip.aocode.validation", "true");
            attrs.add(info);
            tempContext.setAttributes(attrs);
        }
        targetAo = coService.createActivityOffering(targetAo.getFormatOfferingId(), targetAo.getActivityId(),
                targetAo.getTypeKey(), targetAo, tempContext);

        // have to copy rules AFTER AO is created because the link is by the AO id
        activityOfferingTransformer.copyRulesFromExistingActivityOffering(sourceAo, targetAo, optionKeys);

        boolean sourceAndTargetHaveSameTerm = sourceAo.getTermId().equals(targetAo.getTermId());
        // Copy schedules from source to target
        if (!optionKeys.contains(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY)) {
            // This option key should only appear in Copy CO (rollover currently does not have options
            // in the UI.
            common_copySchedules(operation, rolloverAssist, rolloverId, sourceAo, targetAo,
                    sourceAndTargetHaveSameTerm, coService, context);
        }
        // Copy seatpools from source to target
        common_copySeatpools(sourceAo, targetAo, coService, context);

        return targetAo;
    }

    /**
     * A helper method used to copy RDLs/ADLs used by method, createTargetActivityOfferingCommon
     * @param operation A string indicating what
     * @param rolloverAssist Used in true rollover to handle colocating target term AOs (from colocated source
     *                       term AOs)
     * @param rolloverId The id used for rolloverAssist (rolloverAssist can handle multiple rollovers, so the
     *                   ID is used to specify a single rollover)
     * @param sourceAo The source AO (from which the copy occurred
     * @param targetAo The target AO that is created from the source AO
     * @param sourceAndTargetHaveSameTerm true if copyCO is happening in same term or copyAO is happening in
     *                                    same term.  false if copyCO is done across terms or for rollove
     * @param coService Pass in the course offering service (a bit of a hack) to avoid potential circular
     *                  dependencies
     * @param context The context info
     */
    private void common_copySchedules(String operation,
                                      RolloverAssist rolloverAssist,
                                      String rolloverId,
                                      ActivityOfferingInfo sourceAo,
                                      ActivityOfferingInfo targetAo,
                                      boolean sourceAndTargetHaveSameTerm,
                                      CourseOfferingService coService,
                                      ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        // Initial part locates sourceSRS, whether sourceAo and targetAO are colocated, and does mapping if it's rollover
        List<ScheduleRequestSetInfo> sourceSRSes =
                schedulingService.getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,
                        sourceAo.getId(), context);
        ScheduleRequestSetInfo sourceSRS =
                KSCollectionUtils.getRequiredZeroElement(sourceSRSes, true, false);
        if (sourceSRS != null) {
            boolean isColocated = sourceSRS.getRefObjectIds().size() > 1;
            // Find out if there is a targetSRS that can be used (either due to rollover that occurred with an AO
            // that was part of a colo-set, or copy CO within term where source and target SRS are the same).
            ScheduleRequestSetInfo coloTargetSRS = null;
            boolean targetSRSMappingExists = false;
            if (operation.equals(COPY_OPERATION_ROLLOVER) && isColocated) {
                // Fetch target SRS if it is available
                String targetSRSId = rolloverAssist.getTargetSRSId(rolloverId, sourceSRS.getId());
                if (targetSRSId != null) {
                    // Found it, so fetch
                    coloTargetSRS = schedulingService.getScheduleRequestSet(targetSRSId, context);
                    targetSRSMappingExists = true;
                }
            } else if (operation.equals(COPY_OPERATION_COPY_CO) && sourceAndTargetHaveSameTerm && isColocated) {
                // Copy CO within same term where AO is co-located
                coloTargetSRS = sourceSRS;
            } else if (operation.equals(COPY_OPERATION_COPY_AO) && isColocated) {
                // If copy AO and co-located, then target SRS is same as source SRS.
                // Copy AO always has same source/target term, so no need to test that in the "if" condition
                coloTargetSRS = sourceSRS;
            }
            // Main call to copy schedules
            ScheduleRequestSetInfo resultTargetSRS =
                    common_copySchedulesHelper(operation, sourceSRS, coloTargetSRS, sourceAndTargetHaveSameTerm,
                            sourceAo, targetAo, coService, context);
            // Save the mapping to the target SRS
            if (operation.equals(COPY_OPERATION_ROLLOVER) && isColocated && !targetSRSMappingExists) {
                rolloverAssist.mapSourceSRSIdToTargetSRSId(rolloverId, sourceSRS.getId(), resultTargetSRS.getId());
            }
        }
    }

    /**
     * Checks if valid operation has been performed.  Throws exception if operation is not valid.  Useful
     * to make sure that null isn't passed
     * @param operation The name of the operations permitted (constants appear at the start of the file)
     * @throws OperationFailedException Exception thrown if operation is null or invalid
     */
    private void invalidOperationCheck(String operation) throws OperationFailedException {
        if (operation == null){
            throw new OperationFailedException("Operation name is null");
        }
        if (!(operation.equals(COPY_OPERATION_COPY_CO) || operation.equals(COPY_OPERATION_COPY_AO)
                || operation.equals(COPY_OPERATION_ROLLOVER))) {
            throw new OperationFailedException("Operation name is missing");
        }
    }

    /**
     *
     * @param operation See static constants at start of file starting with COPY_OPERATION (basically,
     *                  one of copy AO, copy CO, and rollover)
     * @param sourceSRS The SRS associated with the source AO
     * @param coloTargetSRS If not null, then this is co-located.  If null, create a target SRS
     * @param sourceTargetHasSameTerm true, if source and target have same term
     * @param sourceAo The original AO
     * @param targetAo The copied AO
     * @param coService Course offering service (so we don't have to inject this in)
     * @param context The context
     */
    private ScheduleRequestSetInfo common_copySchedulesHelper(String operation,
                                                              ScheduleRequestSetInfo sourceSRS,
                                                              ScheduleRequestSetInfo coloTargetSRS,
                                                              boolean sourceTargetHasSameTerm,
                                                              ActivityOfferingInfo sourceAo,
                                                              ActivityOfferingInfo targetAo,
                                                              CourseOfferingService coService,
                                                              ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        invalidOperationCheck(operation); // Throws exception if invalid
        // Copy RDLs/ADLs
        ScheduleRequestSetInfo resultTargetSRS;
        try {
            if (common_hasADLs(sourceAo)) {
                resultTargetSRS =
                    common_copySourceADLsToTargetRDLs(operation, sourceSRS, coloTargetSRS, targetAo,
                            sourceTargetHasSameTerm, coService, context);
            } else { // Source AO has RDLs, but not ADLs
                resultTargetSRS =
                    common_copySourceRDLsToTargetRDLs(operation, sourceSRS, coloTargetSRS, targetAo,
                            sourceTargetHasSameTerm, context);
            }
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("createTargetActivityOfferingCommon", e);
        }
        return resultTargetSRS;
    }
    // ------------------------------------------------------------------------------
    public void setAcademicCalendarService(AcademicCalendarService academicCalendarService) {
        this.academicCalendarService = academicCalendarService;
    }

    public AcademicCalendarService getAcademicCalendarService() {
        return academicCalendarService;
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

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }

    public CourseOfferingSetService getSocService() {
        return socService;
    }
}
