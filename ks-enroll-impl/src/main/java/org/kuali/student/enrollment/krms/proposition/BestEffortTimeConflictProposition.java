/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by cmuller on 6/6/14
 */
package org.kuali.student.enrollment.krms.proposition;


import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesServiceConstants;
import org.kuali.student.enrollment.registration.client.service.dto.ConflictCourseResult;
import org.kuali.student.enrollment.registration.client.service.dto.TimeConflictResult;
import org.kuali.student.enrollment.registration.client.service.dto.TimeSlotCalculationContainer;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.RegistrationValidationResultsUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.TimeConflictCalculator;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.search.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a proposition that does a time conflict check for registration requests.
 *
 * @author Kuali Student Team
 */
public class BestEffortTimeConflictProposition extends AbstractBestEffortProposition {

    public static final Logger LOGGER = LoggerFactory.getLogger(BestEffortTimeConflictProposition.class);

    private SchedulingService schedulingService;
    private CourseOfferingService courseOfferingService;
    private CourseWaitListService courseWaitListService;
    private CourseRegistrationService courseRegistrationService;
    private SearchService searchService;
    private ScheduleOfClassesService scheduleOfClassesService;

    private boolean countWaitlistedCoursesTowardsTimeConflict = true;

    // this comparator is used to sort the reg req items in the order they are displayed on the screen.
    // allows us to validate in order.
    protected static final Comparator<RegistrationRequestItem> REG_REQ_ITEM_CREATE_DATE = new Comparator<RegistrationRequestItem>() {

        @Override
        public int compare(RegistrationRequestItem o1, RegistrationRequestItem o2) {
            int ret = 0;
            try {
                ret = o1.getMeta().getCreateTime().compareTo(o2.getMeta().getCreateTime());
            } catch (NullPointerException ex) {
                LOGGER.error("Error comparing reg request meta data", ex);
            }
            return ret;
        }
    };


    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        RegistrationRequestInfo request = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_TERM, this);
        RegistrationRequestItemInfo requestItemInfo = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_TERM, this);

        List<RegistrationRequestItemInfo> regRequestItems = new ArrayList<>();
        regRequestItems.add(requestItemInfo);

        this.setCourseRegistrationService((CourseRegistrationService) environment.resolveTerm(RulesExecutionConstants.COURSE_REGISTRATION_SERVICE_TERM,
                this));
        this.setCourseWaitListService((CourseWaitListService) environment.resolveTerm(RulesExecutionConstants.COURSE_WAIT_LIST_SERVICE_TERM,
                this));
        this.setCourseOfferingService((CourseOfferingService) environment.resolveTerm(RulesExecutionConstants.COURSE_OFFERING_SERVICE_TERM, this));
        this.setSchedulingService((SchedulingService) environment.resolveTerm(RulesExecutionConstants.SCHEDULING_SERVICE_TERM, this));

        // Fetch registrations
        List<CourseRegistrationInfo> existingCrs = new ArrayList<>();
        List<CourseRegistrationInfo> existingCourses;
        if (requestItemInfo.getTypeKey().equals(LprServiceConstants.REQ_ITEM_ADD_FROM_WAITLIST_TYPE_KEY)) {
            // adding from waitlist, cannot resolve existing registrations from the environment context
            try {
                String personId;
                Entity entity = CourseRegistrationAndScheduleOfClassesUtil.getIdentityService().getEntityByPrincipalId(contextInfo.getPrincipalId());
                if (entity != null) {
                    personId = entity.getId();
                } else {
                    personId = contextInfo.getPrincipalId();
                }
                existingCourses = courseRegistrationService.getCourseRegistrationsByStudentAndTerm(personId, request.getTermId(), contextInfo);
            } catch (Exception ex) {
                return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
            }
        } else {
            existingCourses = environment.resolveTerm(RulesExecutionConstants.EXISTING_REGISTRATIONS_TERM, this);
        }
        if (existingCourses != null && !existingCourses.isEmpty()) {
            existingCrs.addAll(existingCourses);
        }
        if (countWaitlistedCoursesTowardsTimeConflict) {
            existingCrs.addAll((List<CourseRegistrationInfo>) environment.resolveTerm(RulesExecutionConstants.EXISTING_WAITLISTED_REGISTRATIONS_TERM, this));
        }
        existingCrs.addAll((List<CourseRegistrationInfo>) environment.resolveTerm(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM, this));

        // Copy the existing registrations. The existing crs might change
        List<CourseRegistrationInfo> copyExistingCrs = new ArrayList<>();
        copyExistingCrs.addAll(existingCrs);

        // sort the items for processing
        sortRegRequestItemsForProcessing(regRequestItems);

        // Get time conflicts. we pass in reg req items and a list of existing courses
        List<TimeConflictResult> timeConflicts;
        try {
            timeConflicts =  getTimeConflictInOrderResults(regRequestItems, existingCrs, getCourseOfferingService(), contextInfo);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }

        // loop through the reg requests and see if there are any conflicts
        for (RegistrationRequestItemInfo item: regRequestItems) {

            TimeConflictResult tcr = findTimeConflictInList(item.getId(), timeConflicts);  // does reg req item have conflicts?
            if(tcr != null){   // is there a conflict for this reg req item
                List<ConflictCourseResult> itemConflicts = new ArrayList<>();   // will store conflicts for this item
                for(String conflictingId : tcr.getConflictingItemMap().keySet()) {
                    // we now know that there is a conflict, but we need to build the object to send back to the user
                    // The conflict object themselves only contain an id, and a list of ao conflicts. so it doesn't
                    // know the rg code, or course offering code.

                    // we are dealing with two types of id's. lprIds and reg request ids.
                    // first check if the id is in the existing course list, if it is, it's an lpr else it's a reg req
                    ConflictCourseResult conflictCourseResult;
                    CourseRegistrationInfo courseRegistrationInfo = findCoRegInfoInList(conflictingId, copyExistingCrs);
                    if (courseRegistrationInfo != null) {   // it's an existing course
                        try {
                            conflictCourseResult = buildConflictCourseResultForCourseRegInfo(courseRegistrationInfo, getCourseOfferingService(), contextInfo);
                        } catch (Exception ex) {
                            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
                        }
                    } else {
                        // In this case the conflicting ID == the Reg Req Id. So we need to get the reg group id
                        // for that reg req id.
                        try {
                            RegistrationRequestItemInfo regRequestItem = findRegReqItemById(conflictingId, request.getRegistrationRequestItems());
                            conflictCourseResult = buildConflictCourseResultForRegGroup(regRequestItem, getCourseOfferingService(), contextInfo);
                            conflictCourseResult.setMasterLprId(conflictingId);   // this is incorrect. this is a reg req id, not master.
                        } catch (Exception ex) {
                            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
                        }
                    }

                    // add conflicts to the reg req item list
                    if (conflictCourseResult != null) {
                        itemConflicts.add(conflictCourseResult);
                    }
                }
                // if the item has conflicts, build the validation results and let the rules engine do it's thing
                if (!itemConflicts.isEmpty()) {
                    ValidationResultInfo vr = createValidationResultFailureForRegRequestItem(item, itemConflicts);
                    Map<String, Object> executionDetails = new LinkedHashMap<>();
                    executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
                    PropositionResult result = new PropositionResult(false, executionDetails);
                    BasicResult br = new BasicResult(executionDetails, ResultEvent.PROPOSITION_EVALUATED, this, environment, result.
                            getResult());
                    environment.getEngineResults().addResult(br);
                }
            }

        }

        // This result contains all the other results
        return recordAllRegRequestItems(environment, new ArrayList<ValidationResultInfo>());
    }

    /**
     * Pass by ref that will sort the regRequestItems. This method is meant to be overridden by implementing institutions.
     * @param regRequestItems
     */
    protected void sortRegRequestItemsForProcessing(List<RegistrationRequestItemInfo> regRequestItems) {
        Collections.sort(regRequestItems, Collections.reverseOrder(REG_REQ_ITEM_CREATE_DATE)); // make sure we're sorting by date
    }

    private ValidationResultInfo createValidationResultFailureForRegRequestItem(RegistrationRequestItemInfo item, List<ConflictCourseResult> conflictCourseResults) {
        String msg = RegistrationValidationResultsUtil.marshallConflictCourseMessage(LprServiceConstants.LPRTRANS_ITEM_TIME_CONFLICT_MESSAGE_KEY, conflictCourseResults);
        return createValidationResultFailureForRegRequestItem(item, msg);
    }

    /**
     * The validation results require a ConflictCourseResult. This method takes a reg group id and creates a
     * ConflictCourseResult object.
     * @param item    registration request item we need to get reg group id and crossList code
     * @param coService  course offering service
     * @param contextInfo
     * @return
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    private ConflictCourseResult buildConflictCourseResultForRegGroup(RegistrationRequestItemInfo item, CourseOfferingService coService, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        RegistrationGroupInfo registrationGroupInfo = coService.getRegistrationGroup(item.getRegistrationGroupId(), contextInfo);
        CourseOfferingInfo courseofferingInfo = coService.getCourseOffering(registrationGroupInfo.getCourseOfferingId(), contextInfo);

        ConflictCourseResult conflictCourseResult = new ConflictCourseResult();

        // checking if we are dealing with real course vs alias (cross-listed)
        if (item.getCrossListedCode() != null && !StringUtils.equals(item.getCrossListedCode(), courseofferingInfo.getCourseOfferingCode())) {
            conflictCourseResult.setCourseCode(item.getCrossListedCode());
        } else {
            conflictCourseResult.setCourseCode(courseofferingInfo.getCourseOfferingCode());
        }
        conflictCourseResult.setLongName(courseofferingInfo.getCourseOfferingTitle());
        conflictCourseResult.setRegGroupCode(registrationGroupInfo.getName());
        return conflictCourseResult;
    }

    /**
     * The validation results require a ConflictCourseResult. This method takes a CourseRegistrationInfo and creates a
     * ConflictCourseResult object.
     * @param courseRegistrationInfo    this is an object that represents an LPR.
     * @param coService  course offering service
     * @param contextInfo
     * @return
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    private ConflictCourseResult buildConflictCourseResultForCourseRegInfo(CourseRegistrationInfo courseRegistrationInfo, CourseOfferingService coService, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        RegistrationGroupInfo registrationGroupInfo = coService.getRegistrationGroup(courseRegistrationInfo.getRegistrationGroupId(), contextInfo);
        CourseOfferingInfo courseofferingInfo = coService.getCourseOffering(courseRegistrationInfo.getCourseOfferingId(), contextInfo);

        ConflictCourseResult conflictCourseResult = new ConflictCourseResult();
        conflictCourseResult.setMasterLprId(courseRegistrationInfo.getId());
        // checking if we are dealing with real course vs alias (cross-listed)
        if (courseRegistrationInfo.getCrossListedCode() != null && !StringUtils.equals(courseRegistrationInfo.getCrossListedCode(), courseofferingInfo.getCourseOfferingCode())) {
            conflictCourseResult.setCourseCode(courseRegistrationInfo.getCrossListedCode());
        } else {
            conflictCourseResult.setCourseCode(courseofferingInfo.getCourseOfferingCode());
        }
        conflictCourseResult.setLongName(courseofferingInfo.getCourseOfferingTitle());
        conflictCourseResult.setRegGroupCode(registrationGroupInfo.getName());
        return conflictCourseResult;

    }

    /**
     * Simple helper method to find a timeConflict by id.
     * @param id
     * @param timeConflicts
     * @return
     */
    private TimeConflictResult findTimeConflictInList(String id, List<TimeConflictResult> timeConflicts) {
        for (TimeConflictResult timeConflictResult : timeConflicts) {
            if (timeConflictResult.getId().equals(id)) {
                return timeConflictResult;
            }
        }
        return null;
    }

    /**
     * helper method to get a courseRegistrationInfo by id
     * @param id
     * @param coRegInfoList
     * @return
     */
    private CourseRegistrationInfo findCoRegInfoInList(String id, List<CourseRegistrationInfo> coRegInfoList) {
        for (CourseRegistrationInfo coRegInfo : coRegInfoList) {
            if (coRegInfo.getId().equals(id)) {
                return coRegInfo;
            }
        }
        return null;
    }

    /**
     * returnts the corresponding regReqItem from list for the given id.
     * @param id
     * @param regReqItems
     * @return
     */
    private RegistrationRequestItemInfo findRegReqItemById(String id, List<RegistrationRequestItemInfo> regReqItems) {
        for (RegistrationRequestItemInfo regReqItem : regReqItems) {
            if (regReqItem.getId().equals(id)) {
                return regReqItem;
            }
        }
        return null;
    }

    /**
     * This method will return a list of time conflicts between the reg request items and the existing courses. This
     * handles the inorder "best effort"
     *
     * In order to perform time conflict checks we need to build up a "standard" time conflict container object. that
     * object contains all info needed to perform a check.
     *
     * This method builds the objects for the reg request items then builds for the existing courses. Once we have
     * both lists, pass those lists to the conflict calculator to evaluate.
     *
     * @param regRequestItems  registration request items
     * @param existingCourses  list of existing courses
     * @param coService        course offering service
     * @param contextInfo
     * @return
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    protected List<TimeConflictResult> getTimeConflictInOrderResults(List<RegistrationRequestItemInfo> regRequestItems, List<CourseRegistrationInfo> existingCourses, CourseOfferingService coService, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        Map<String, List<TimeSlotInfo>> aoToTimeSlotMap = new HashMap<>();

        // get all info needed to detect time conflicts
        List<TimeSlotCalculationContainer> existingCoursesTimeConflictContainers = getTimeSlotCalculationContainerForExistingCourses(existingCourses, aoToTimeSlotMap, coService, contextInfo);
        List<TimeSlotCalculationContainer> newCoursesTimeConflictContainers = getTimeSlotCalculationContainersForNewCourses(regRequestItems, aoToTimeSlotMap, coService, contextInfo);

        return TimeConflictCalculator.getTimeConflictInOrderResults(newCoursesTimeConflictContainers, existingCoursesTimeConflictContainers);
    }

    /**
     * This builds time conflict containers for the registration request items passed in. Each container has the
     * unique ID (regReqId in this case) and a map of aoId->TimeSlots.
     *
     * @param regRequestItems
     * @param aoToTimeSlotMap   in order to increase performance we are passing in a map(ao->timeslot) by reference.
     * @param coService     course offering service
     * @param contextInfo
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    private List<TimeSlotCalculationContainer> getTimeSlotCalculationContainersForNewCourses(List<RegistrationRequestItemInfo> regRequestItems, Map<String, List<TimeSlotInfo>> aoToTimeSlotMap, CourseOfferingService coService, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        List<TimeSlotCalculationContainer> timeSlotCalculationContainers = new ArrayList<>();
        List<String> regGroupIds = new ArrayList<>();
        for (RegistrationRequestItem requestItem: regRequestItems) {
            regGroupIds.add(requestItem.getRegistrationGroupId());
        }

        List<RegistrationGroupInfo> registrationGroupInfos = coService.getRegistrationGroupsByIds(regGroupIds, contextInfo);

        Map<String, List<String>> rgToAoIds = new LinkedHashMap<>();
        List<String> aoIds = new ArrayList<>();  // This will help with a bulk timeslot method later
        for (RegistrationGroupInfo registrationGroupInfo: registrationGroupInfos) {
            rgToAoIds.put(registrationGroupInfo.getId(), registrationGroupInfo.getActivityOfferingIds());
            aoIds.addAll(registrationGroupInfo.getActivityOfferingIds());

        }

        // get ALL needed ao->timeSlots
        populateTimeSlotMapForAoIds(aoIds, aoToTimeSlotMap, coService, contextInfo);


        for (RegistrationRequestItem regReqItem: regRequestItems) {
            List<String> courseAoIds = rgToAoIds.get(regReqItem.getRegistrationGroupId());
            TimeSlotCalculationContainer tcContainer = new TimeSlotCalculationContainer();
            tcContainer.setId(regReqItem.getId()); // unique lprId
            tcContainer.setAoToTimeSlotMap(getFilteredTimeSlotMapForAoIds(courseAoIds, aoToTimeSlotMap, coService, contextInfo));
            timeSlotCalculationContainers.add(tcContainer);
        }


        return timeSlotCalculationContainers;
    }

    /**
     *  Get the time conflict data container for a passed in list of CourseRegistrationInfo objects.
     *
     * @param courseRegistrationInfos
     * @param aoToTimeSlotMap   pass by ref map of ao->TimeSlotInfo. passed in for performance reasons
     * @param coService
     * @param contextInfo
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    private List<TimeSlotCalculationContainer> getTimeSlotCalculationContainerForExistingCourses(List<CourseRegistrationInfo> courseRegistrationInfos, Map<String, List<TimeSlotInfo>> aoToTimeSlotMap, CourseOfferingService coService, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<TimeSlotCalculationContainer> timeSlotCalculationContainers = new ArrayList<>();
        List<String> regGroupIds = new ArrayList<>();
        for (CourseRegistrationInfo courseRegistrationInfo: courseRegistrationInfos) {
            regGroupIds.add(courseRegistrationInfo.getRegistrationGroupId());
        }

        List<RegistrationGroupInfo> registrationGroupInfos = coService.getRegistrationGroupsByIds(regGroupIds, contextInfo);

        Map<String, List<String>> rgToAoIds = new LinkedHashMap<>();
        List<String> aoIds = new ArrayList<>();  // This will help with a bulk timeslot method later
        for (RegistrationGroupInfo registrationGroupInfo: registrationGroupInfos) {
            rgToAoIds.put(registrationGroupInfo.getId(), registrationGroupInfo.getActivityOfferingIds());
            aoIds.addAll(registrationGroupInfo.getActivityOfferingIds());

        }

        // get ALL needed ao->timeSlots
        populateTimeSlotMapForAoIds(aoIds, aoToTimeSlotMap, coService, contextInfo);


        for (CourseRegistrationInfo courseRegistrationInfo: courseRegistrationInfos) {
            List<String> courseAoIds = rgToAoIds.get(courseRegistrationInfo.getRegistrationGroupId());
            TimeSlotCalculationContainer tcContainer = new TimeSlotCalculationContainer();
            tcContainer.setId(courseRegistrationInfo.getId()); // unique lprId
            tcContainer.setAoToTimeSlotMap(getFilteredTimeSlotMapForAoIds(courseAoIds, aoToTimeSlotMap, coService, contextInfo));
            timeSlotCalculationContainers.add(tcContainer);
        }


        return timeSlotCalculationContainers;
    }

    /**
     * You pass in a list of aoIds, and it returns an aoMap that ONLY contains timeslots for the passed in aoIds.
     * @param aoIds
     * @param aoToTimeSlotMap  a complete map of all ao->Timeslots that are used for this validation
     * @param coService
     * @param contextInfo
     * @return
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    protected Map<String, List<TimeSlotInfo>> getFilteredTimeSlotMapForAoIds(List<String> aoIds, Map<String, List<TimeSlotInfo>> aoToTimeSlotMap,  CourseOfferingService coService, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        Map<String, List<TimeSlotInfo>> filteredAoToTimeSlotMap = new HashMap<>();

        for (String aoId : aoIds) {
            filteredAoToTimeSlotMap.put(aoId, aoToTimeSlotMap.get(aoId));
        }
        return filteredAoToTimeSlotMap;
    }

    /**
     * This is a PassByRef method used to populate List<TimeSlotInfo>> aoToTimeSlotMap
     * We are doing this because timeSlots are very expensive to query in the database
     *
     * @param aoIds
     * @param aoToTimeSlotMap
     * @param contextInfo
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    protected void populateTimeSlotMapForAoIds(List<String> aoIds, Map<String, List<TimeSlotInfo>> aoToTimeSlotMap, CourseOfferingService coService, ContextInfo contextInfo) {
        // I don't want to search the DB for timeslots we already have
        List<String> aoIdsToProcess = new ArrayList<>();
        for (String aoId : aoIds) {
            if (!aoToTimeSlotMap.containsKey(aoId) && !aoIdsToProcess.contains(aoId)) {
                aoIdsToProcess.add(aoId);
            }
        }

        if (!aoIds.isEmpty()) {
            if (!aoIdsToProcess.isEmpty()) {
                Map<String, List<TimeSlotInfo>> newAoToTimeSlotMap = getScheduleOfClassesService().getAoTimeSlotMap(aoIdsToProcess, contextInfo);
                if (newAoToTimeSlotMap != null && !newAoToTimeSlotMap.isEmpty()) {
                    for (Map.Entry<String, List<TimeSlotInfo>> entry: newAoToTimeSlotMap.entrySet()) {
                        String aoid = entry.getKey();
                        aoToTimeSlotMap.put(aoid, newAoToTimeSlotMap.get(aoid));
                    }
                }
            }
        }


    }

    /**
     * get TimeSlotInfo's for the passed in ActivityOfferingInfo.
     *
     * @param activityOfferingInfo
     * @param contextInfo
     * @return
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    protected List<TimeSlotInfo> getTimeSlotsByAoInfo(ActivityOfferingInfo activityOfferingInfo, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        List<TimeSlotInfo> timeSlots = new ArrayList<>();

        if (activityOfferingInfo.getScheduleIds() == null || activityOfferingInfo.getScheduleIds().isEmpty()) {
            return timeSlots; // return empty list if there are no schedule ids
        }

        List<ScheduleInfo> scheduleInfos = getSchedulingService().getSchedulesByIds(activityOfferingInfo.getScheduleIds(), contextInfo);

        List<String> timeslotIds = new ArrayList<>();
        for (ScheduleInfo scheduleInfo: scheduleInfos) {
            for (ScheduleComponentInfo scheduleComponent: scheduleInfo.getScheduleComponents()) {
                timeslotIds.addAll(scheduleComponent.getTimeSlotIds());
            }
        }

        timeSlots = getSchedulingService().getTimeSlotsByIds(timeslotIds, contextInfo);
        return timeSlots;

    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public CourseWaitListService getCourseWaitListService() {
        return courseWaitListService;
    }

    public void setCourseWaitListService(CourseWaitListService courseWaitListService) {
        this.courseWaitListService = courseWaitListService;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    public SearchService getSearchService() {
        if (searchService == null) {
            searchService = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    public ScheduleOfClassesService getScheduleOfClassesService() {
        if(scheduleOfClassesService == null){
            scheduleOfClassesService = GlobalResourceLoader.getService(ScheduleOfClassesServiceConstants.QNAME);
        }

        return scheduleOfClassesService;
    }

    public void setScheduleOfClassesService(ScheduleOfClassesService scheduleOfClassesService) {
        this.scheduleOfClassesService = scheduleOfClassesService;
    }

    public void setCountWaitlistedCoursesTowardsTimeConflict(boolean countWaitlistedCoursesTowardsTimeConflict) {
        this.countWaitlistedCoursesTowardsTimeConflict = countWaitlistedCoursesTowardsTimeConflict;
    }

}
