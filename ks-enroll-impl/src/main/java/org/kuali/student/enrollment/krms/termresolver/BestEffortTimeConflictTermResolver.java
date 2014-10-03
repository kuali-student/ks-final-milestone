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
 * Ported to a term resolver by Paul Richardson on 9/17/2014
 */
package org.kuali.student.enrollment.krms.termresolver;


import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.dto.ConflictCourseResult;
import org.kuali.student.enrollment.registration.client.service.dto.TimeConflictResult;
import org.kuali.student.enrollment.registration.client.service.dto.TimeSlotCalculationContainer;
import org.kuali.student.enrollment.registration.client.service.impl.util.TimeConflictCalculator;
import org.kuali.student.enrollment.registration.engine.util.RegEnginePerformanceUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is a term resolver that does a time conflict check for registration requests.
 *
 * @author Kuali Student Team
 */
public class BestEffortTimeConflictTermResolver implements TermResolver<String> {

    public static final Logger LOGGER = LoggerFactory.getLogger(BestEffortTimeConflictTermResolver.class);

    private CourseOfferingService courseOfferingService;
    private ScheduleOfClassesService scheduleOfClassesService;

    private boolean countWaitlistedCoursesTowardsTimeConflict = true;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>();

        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        prereqs.add(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());
        prereqs.add(RulesExecutionConstants.REGISTRATION_REQUEST_TERM.getName());
        prereqs.add(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_TERM.getName());
        prereqs.add(RulesExecutionConstants.EXISTING_REGISTRATIONS_TERM.getName());
        prereqs.add(RulesExecutionConstants.EXISTING_WAITLISTED_REGISTRATIONS_TERM.getName());
        prereqs.add(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM.getName());

        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_BEST_EFFORT_TIME_CONFLICT;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        return 3;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters)
            throws TermResolutionException {

        DateTime startTime = new DateTime();

        // Resolve pre-requisite terms
        ContextInfo contextInfo = (ContextInfo) resolvedPrereqs.
                get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        RegistrationGroupInfo regGroup = (RegistrationGroupInfo) resolvedPrereqs.
                get(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());
        RegistrationRequestInfo request = (RegistrationRequestInfo) resolvedPrereqs.
                get(RulesExecutionConstants.REGISTRATION_REQUEST_TERM.getName());
        RegistrationRequestItemInfo regRequestItem = (RegistrationRequestItemInfo) resolvedPrereqs.
                get(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_TERM.getName());
        List<CourseRegistrationInfo> existingCourses = (List<CourseRegistrationInfo>) resolvedPrereqs.
                get(RulesExecutionConstants.EXISTING_REGISTRATIONS_TERM.getName());
        List<CourseRegistrationInfo> existingWaitlist = (List<CourseRegistrationInfo>) resolvedPrereqs.
                get(RulesExecutionConstants.EXISTING_WAITLISTED_REGISTRATIONS_TERM.getName());
        List<CourseRegistrationInfo> simulatedRegistrations = (List<CourseRegistrationInfo>) resolvedPrereqs.
                get(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM.getName());

        // Fetch registrations
        List<CourseRegistrationInfo> existingCrs = new ArrayList<>();
        if (existingCourses != null && !existingCourses.isEmpty()) {
            existingCrs.addAll(existingCourses);
        }
        if (countWaitlistedCoursesTowardsTimeConflict) {
            existingCrs.addAll(existingWaitlist);
        }
        existingCrs.addAll(simulatedRegistrations);

        // Copy the existing registrations. The existing crs might change
        List<CourseRegistrationInfo> copyExistingCrs = new ArrayList<>();
        copyExistingCrs.addAll(existingCrs);

        // Get time conflicts. we pass in reg req items and a list of existing courses
        List<TimeConflictResult> timeConflicts;
        List<ConflictCourseResult> itemConflicts = null;
        try {
            timeConflicts = getTimeConflictResults(regRequestItem, existingCrs, regGroup, contextInfo);
            itemConflicts = getConflicts(regRequestItem, timeConflicts, copyExistingCrs, request, regGroup,
                    contextInfo);
        } catch (Exception ex) {
            LOGGER.error("Exception trying to determine time conflicts", ex);
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, ex, this);
        }

        String timeConflictsJson = convertConflictsToJson(itemConflicts);

        if (itemConflicts != null && !itemConflicts.isEmpty()) {
            LOGGER.warn("Time conflicts found for {}. Reg request id {} has conflicts. Error JSON: {}",
                    contextInfo.getPrincipalId(), regRequestItem.getRegistrationRequestId(), timeConflictsJson);
        }

        DateTime endTime = new DateTime();
        RegEnginePerformanceUtil.putStatistics(RegEnginePerformanceUtil.TERMS, getOutput(), startTime, endTime);

        return timeConflictsJson;
    }

    private List<ConflictCourseResult> getConflicts(RegistrationRequestItemInfo regRequestItem,
                                                    List<TimeConflictResult> timeConflicts,
                                                    List<CourseRegistrationInfo> copyExistingCrs,
                                                    RegistrationRequestInfo request,
                                                    RegistrationGroupInfo regGroup,
                                                    ContextInfo contextInfo)
            throws MissingParameterException, PermissionDeniedException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        // see if there are any conflicts for the reg request item
        List<ConflictCourseResult> itemConflicts = new ArrayList<>();   // will store conflicts for this item
        TimeConflictResult tcr = findTimeConflictInList(regRequestItem.getId(), timeConflicts);  // has conflicts?
        if (tcr != null) {   // is there a conflict for this reg req item
            for (String conflictingId : tcr.getConflictingItemMap().keySet()) {
                // we now know that there is a conflict, but we need to build the object to send back to the user
                // The conflict object themselves only contain an id, and a list of ao conflicts. so it doesn't
                // know the rg code, or course offering code.

                // we are dealing with two types of id's. lprIds and reg request ids.
                // first check if the id is in the existing course list, if it is, it's an lpr else it's a reg req
                ConflictCourseResult conflictCourseResult;
                CourseRegistrationInfo courseRegistrationInfo = findCoRegInfoInList(conflictingId, copyExistingCrs);
                if (courseRegistrationInfo != null) {   // it's an existing course
                    conflictCourseResult = buildConflictCourseResultForCourseRegInfo(courseRegistrationInfo,
                            contextInfo);
                } else {
                    // In this case the conflicting ID == the Reg Req Id. So we need to get the reg group id
                    // for that reg req id.
                    RegistrationRequestItemInfo conflictingRegRequest = findRegReqItemById(conflictingId,
                            request.getRegistrationRequestItems());
                    conflictCourseResult = buildConflictCourseResultForRegGroup(conflictingRegRequest, regGroup,
                            contextInfo);
                    conflictCourseResult.setMasterLprId(conflictingId); // incorrect? this is a reg req id, not master.
                }

                // add conflicts to the reg req item list
                if (conflictCourseResult != null) {
                    itemConflicts.add(conflictCourseResult);
                }
            }
        }

        return itemConflicts;
    }

    private String convertConflictsToJson(List<ConflictCourseResult> timeConflicts) {
        // Marshall the object as JSON
        ObjectMapper mapper = new ObjectMapper();
        String timeConflictsJson = "{}"; // empty object means no conflicts found
        if (timeConflicts != null && !timeConflicts.isEmpty()) {
            try {
                timeConflictsJson = mapper.writeValueAsString(timeConflicts);
            } catch (IOException ex) {
                LOGGER.error("Unable to marshall time conflicts", ex);
            }
        }
        return timeConflictsJson;
    }

    /**
     * The validation results require a ConflictCourseResult. This method takes a reg group id and creates a
     * ConflictCourseResult object.
     * @param item    registration request item we need to get reg group id and crossList code
     * @param contextInfo context info
     * @return conflict course result
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     */
    private ConflictCourseResult buildConflictCourseResultForRegGroup(RegistrationRequestItemInfo item,
                                                                      RegistrationGroupInfo registrationGroupInfo,
                                                                      ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        CourseOfferingInfo courseofferingInfo = courseOfferingService.
                getCourseOffering(registrationGroupInfo.getCourseOfferingId(), contextInfo);

        ConflictCourseResult conflictCourseResult = new ConflictCourseResult();

        // checking if we are dealing with real course vs alias (cross-listed)
        if (item.getCrossListedCode() != null && !StringUtils.equals(item.getCrossListedCode(), courseofferingInfo.
                getCourseOfferingCode())) {
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
     * @param contextInfo context info
     * @return conflict course result
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     */
    private ConflictCourseResult buildConflictCourseResultForCourseRegInfo(
            CourseRegistrationInfo courseRegistrationInfo, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        RegistrationGroupInfo registrationGroupInfo = courseOfferingService.getRegistrationGroup(courseRegistrationInfo.
                getRegistrationGroupId(), contextInfo);
        CourseOfferingInfo courseofferingInfo = courseOfferingService.getCourseOffering(courseRegistrationInfo.
                getCourseOfferingId(), contextInfo);

        ConflictCourseResult conflictCourseResult = new ConflictCourseResult();
        conflictCourseResult.setMasterLprId(courseRegistrationInfo.getId());
        // checking if we are dealing with real course vs alias (cross-listed)
        if (courseRegistrationInfo.getCrossListedCode() != null && !StringUtils.equals(courseRegistrationInfo.
                getCrossListedCode(), courseofferingInfo.getCourseOfferingCode())) {
            conflictCourseResult.setCourseCode(courseRegistrationInfo.getCrossListedCode());
        } else {
            conflictCourseResult.setCourseCode(courseofferingInfo.getCourseOfferingCode());
        }
        conflictCourseResult.setLongName(courseofferingInfo.getCourseOfferingTitle());
        conflictCourseResult.setRegGroupCode(registrationGroupInfo.getName());
        return conflictCourseResult;

    }

    /*
    Simple helper method to find a timeConflict by id.
     */
    private TimeConflictResult findTimeConflictInList(String id, List<TimeConflictResult> timeConflicts) {
        for (TimeConflictResult timeConflictResult : timeConflicts) {
            if (timeConflictResult.getId().equals(id)) {
                return timeConflictResult;
            }
        }
        return null;
    }

    /*
    helper method to get a courseRegistrationInfo by id
     */
    private CourseRegistrationInfo findCoRegInfoInList(String id, List<CourseRegistrationInfo> coRegInfoList) {
        for (CourseRegistrationInfo coRegInfo : coRegInfoList) {
            if (coRegInfo.getId().equals(id)) {
                return coRegInfo;
            }
        }
        return null;
    }

    /*
    returns the corresponding regReqItem from list for the given id.
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
     * @param regRequestItem  registration request item
     * @param existingCourses  list of existing courses
     * @param contextInfo context info
     * @return list of time conflict results
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     */
    private List<TimeConflictResult> getTimeConflictResults(RegistrationRequestItemInfo regRequestItem,
                                                            List<CourseRegistrationInfo> existingCourses,
                                                            RegistrationGroupInfo registrationGroupInfo,
                                                            ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        Map<String, List<TimeSlotInfo>> aoToTimeSlotMap = new HashMap<>();

        // get all info needed to detect time conflicts
        List<TimeSlotCalculationContainer> existingCoursesTimeConflictContainers =
                getTimeSlotCalculationContainerForExistingCourses(existingCourses, aoToTimeSlotMap, contextInfo);
        List<TimeSlotCalculationContainer> newCoursesTimeConflictContainers = new ArrayList<>();
        newCoursesTimeConflictContainers.add(getTimeSlotCalculationContainerForNewCourse(regRequestItem,
                aoToTimeSlotMap, registrationGroupInfo, contextInfo));

        return TimeConflictCalculator.getTimeConflictInOrderResults(newCoursesTimeConflictContainers,
                existingCoursesTimeConflictContainers);
    }

    /**
     * This builds time conflict containers for the registration request item passed in. Each container has the
     * unique ID (regReqId in this case) and a map of aoId->TimeSlots.
     *
     * @param regRequestItem the reg request item
     * @param aoToTimeSlotMap   in order to increase performance we are passing in a map(ao->timeslot) by reference.
     * @param contextInfo the context info
     * @return time slot calculation container
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     */
    private TimeSlotCalculationContainer getTimeSlotCalculationContainerForNewCourse(
            RegistrationRequestItemInfo regRequestItem, Map<String, List<TimeSlotInfo>> aoToTimeSlotMap,
            RegistrationGroupInfo registrationGroupInfo,
            ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            PermissionDeniedException, OperationFailedException {

        Map<String, List<String>> rgToAoIds = new LinkedHashMap<>();
        List<String> aoIds = new ArrayList<>();  // This will help with a bulk timeslot method later
        rgToAoIds.put(registrationGroupInfo.getId(), registrationGroupInfo.getActivityOfferingIds());
        aoIds.addAll(registrationGroupInfo.getActivityOfferingIds());

        // get ALL needed ao->timeSlots
        populateTimeSlotMapForAoIds(aoIds, aoToTimeSlotMap, contextInfo);

        List<String> courseAoIds = rgToAoIds.get(regRequestItem.getRegistrationGroupId());
        TimeSlotCalculationContainer tcContainer = new TimeSlotCalculationContainer();
        tcContainer.setId(regRequestItem.getId()); // unique lprId
        tcContainer.setAoToTimeSlotMap(getFilteredTimeSlotMapForAoIds(courseAoIds, aoToTimeSlotMap));

        return tcContainer;
    }

    /**
     *  Get the time conflict data container for a passed in list of CourseRegistrationInfo objects.
     *
     * @param courseRegistrationInfos list of course registration info objects
     * @param aoToTimeSlotMap   pass by ref map of ao->TimeSlotInfo. passed in for performance reasons
     * @param contextInfo the context info
     * @return a list of time slot calculation containers
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     */
    private List<TimeSlotCalculationContainer> getTimeSlotCalculationContainerForExistingCourses(
            List<CourseRegistrationInfo> courseRegistrationInfos, Map<String, List<TimeSlotInfo>> aoToTimeSlotMap,
            ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            PermissionDeniedException, OperationFailedException {
        List<TimeSlotCalculationContainer> timeSlotCalculationContainers = new ArrayList<>();
        List<String> regGroupIds = new ArrayList<>();
        for (CourseRegistrationInfo courseRegistrationInfo: courseRegistrationInfos) {
            regGroupIds.add(courseRegistrationInfo.getRegistrationGroupId());
        }

        List<RegistrationGroupInfo> registrationGroupInfos = courseOfferingService.
                getRegistrationGroupsByIds(regGroupIds, contextInfo);

        Map<String, List<String>> rgToAoIds = new LinkedHashMap<>();
        List<String> aoIds = new ArrayList<>();  // This will help with a bulk timeslot method later
        for (RegistrationGroupInfo registrationGroupInfo: registrationGroupInfos) {
            rgToAoIds.put(registrationGroupInfo.getId(), registrationGroupInfo.getActivityOfferingIds());
            aoIds.addAll(registrationGroupInfo.getActivityOfferingIds());
        }

        // get ALL needed ao->timeSlots
        populateTimeSlotMapForAoIds(aoIds, aoToTimeSlotMap, contextInfo);


        for (CourseRegistrationInfo courseRegistrationInfo: courseRegistrationInfos) {
            List<String> courseAoIds = rgToAoIds.get(courseRegistrationInfo.getRegistrationGroupId());
            TimeSlotCalculationContainer tcContainer = new TimeSlotCalculationContainer();
            tcContainer.setId(courseRegistrationInfo.getId()); // unique lprId
            tcContainer.setAoToTimeSlotMap(getFilteredTimeSlotMapForAoIds(courseAoIds, aoToTimeSlotMap));
            timeSlotCalculationContainers.add(tcContainer);
        }


        return timeSlotCalculationContainers;
    }

    /**
     * You pass in a list of aoIds, and it returns an aoMap that ONLY contains timeslots for the passed in aoIds.
     * @param aoIds a list of aoids
     * @param aoToTimeSlotMap  a complete map of all ao->Timeslots that are used for this validation
     * @return a map of filtered time slots
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     */
    private Map<String, List<TimeSlotInfo>> getFilteredTimeSlotMapForAoIds(List<String> aoIds, Map<String,
            List<TimeSlotInfo>> aoToTimeSlotMap)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        Map<String, List<TimeSlotInfo>> filteredAoToTimeSlotMap = new HashMap<>();

        if (aoIds != null && !aoIds.isEmpty()) {
            for (String aoId : aoIds) {
                filteredAoToTimeSlotMap.put(aoId, aoToTimeSlotMap.get(aoId));
            }
        }

        return filteredAoToTimeSlotMap;
    }

    /**
     * This is a PassByRef method used to populate List<TimeSlotInfo>> aoToTimeSlotMap
     * We are doing this because timeSlots are very expensive to query in the database
     *
     * @param aoIds a list of ao ids
     * @param aoToTimeSlotMap a map of time slots
     * @param contextInfo the context info
     */
    private void populateTimeSlotMapForAoIds(List<String> aoIds, Map<String, List<TimeSlotInfo>> aoToTimeSlotMap,
                                             ContextInfo contextInfo) {
        // I don't want to search the DB for timeslots we already have
        List<String> aoIdsToProcess = new ArrayList<>();
        for (String aoId : aoIds) {
            if (!aoToTimeSlotMap.containsKey(aoId) && !aoIdsToProcess.contains(aoId)) {
                aoIdsToProcess.add(aoId);
            }
        }

        if (!aoIds.isEmpty()) {
            if (!aoIdsToProcess.isEmpty()) {
                Map<String, List<TimeSlotInfo>> newAoToTimeSlotMap = scheduleOfClassesService.
                        getAoTimeSlotMap(aoIdsToProcess, contextInfo);
                if (newAoToTimeSlotMap != null && !newAoToTimeSlotMap.isEmpty()) {
                    for (Map.Entry<String, List<TimeSlotInfo>> entry: newAoToTimeSlotMap.entrySet()) {
                        String aoid = entry.getKey();
                        aoToTimeSlotMap.put(aoid, entry.getValue());
                    }
                }
            }
        }
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public void setScheduleOfClassesService(ScheduleOfClassesService scheduleOfClassesService) {
        this.scheduleOfClassesService = scheduleOfClassesService;
    }

    @SuppressWarnings("unused")
    public void setCountWaitlistedCoursesTowardsTimeConflict(boolean countWaitlistedCoursesTowardsTimeConflict) {
        this.countWaitlistedCoursesTowardsTimeConflict = countWaitlistedCoursesTowardsTimeConflict;
    }
}
