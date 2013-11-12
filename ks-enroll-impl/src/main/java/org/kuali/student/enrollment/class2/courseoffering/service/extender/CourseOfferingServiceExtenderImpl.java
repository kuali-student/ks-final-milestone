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
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.RegistrationGroupTransformer;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.container.TimeSlotContainer;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;

import java.util.ArrayList;
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

    private SchedulingService schedulingService;

    private SearchService searchService;

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

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public void setRegistrationGroupTransformer(RegistrationGroupTransformer registrationGroupTransformer) {
        this.registrationGroupTransformer = registrationGroupTransformer;
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }
}
