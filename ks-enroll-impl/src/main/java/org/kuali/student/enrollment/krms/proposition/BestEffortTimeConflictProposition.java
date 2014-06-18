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

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.enrollment.registration.client.service.dto.ConflictCourseResult;
import org.kuali.student.enrollment.registration.client.service.dto.TimeConflictDataContainer;
import org.kuali.student.enrollment.registration.client.service.dto.TimeConflictResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.RegistrationValidationResultsUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.TimeConflictCalculator;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a proposition that does a time conflict check for registration requests.
 *
 * @author Kuali Student Team
 */
public class BestEffortTimeConflictProposition extends BestEffortProposition {

    private SchedulingService schedulingService;

    private static final boolean ALLOW_ALL_NON_ADDS = true; // allow all non adds right now

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        RegistrationRequestInfo request = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_TERM, this);
        String personId = environment.resolveTerm(RulesExecutionConstants.PERSON_ID_TERM, this);
        CourseRegistrationService crService = environment.resolveTerm(RulesExecutionConstants.COURSE_REGISTRATION_SERVICE_TERM,
                this);
        CourseWaitListService wlService = environment.resolveTerm(RulesExecutionConstants.COURSE_WAIT_LIST_SERVICE_TERM,
                this);
        coService = environment.resolveTerm(RulesExecutionConstants.COURSE_OFFERING_SERVICE_TERM, this);
        schedulingService = environment.resolveTerm(RulesExecutionConstants.SCHEDULING_SERVICE_TERM, this);

        // Verify that all operations are add
        boolean allAddOps = true;
        for (RegistrationRequestItemInfo item: request.getRegistrationRequestItems()) {
            if (!LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY.equals(item.getTypeKey())) {
                allAddOps = false;
                break;
            }
        }
        if (!allAddOps) {
            if (ALLOW_ALL_NON_ADDS) {
                return new PropositionResult(true, Collections.<String, Object>emptyMap());
            }

            // All of the operations were not "add", thus this is not a reg cart.
            InvalidParameterException ex = new InvalidParameterException("Should be add ops only");
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }

        // Fetch registrations
        List<CourseRegistrationInfo> existingCrs;
        try {
            existingCrs = getCourseAndWaitlistRegistrations(request, personId, crService, wlService, contextInfo);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }



        // Iterate over each item and check
        List<CourseRegistrationInfo> successFromCart = new ArrayList<CourseRegistrationInfo>();
        for (RegistrationRequestItemInfo item: request.getRegistrationRequestItems()) {

            List<CourseRegistrationInfo> copyExistingCrs = new ArrayList<CourseRegistrationInfo>();
            // Copy the existing registrations
            copyExistingCrs.addAll(existingCrs);
            // Add the successful RGs from the cart (added from previous iterations)
            copyExistingCrs.addAll(successFromCart);

            TimeConflictDataContainer rgIdsToTimeSlots;
            try {
                rgIdsToTimeSlots = getTimeConflictDataContainer(copyExistingCrs, contextInfo);
            } catch (Exception ex) {
                return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
            }

            // Add the item being iterated over
            CourseRegistrationInfo regItem;
            try {
                regItem = createNewCourseRegistration(item, contextInfo);
            } catch (OperationFailedException ex) {
                return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
            }
            copyExistingCrs.add(regItem);
            List<CourseRegistrationInfo> newRegItem = new ArrayList<CourseRegistrationInfo>();
            newRegItem.add(regItem);
            TimeConflictDataContainer regItemTimeSlots;
            try {
                regItemTimeSlots = getTimeConflictDataContainer(newRegItem, contextInfo);
            } catch(Exception ex) {
                return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
            }
            for (String rgId: regItemTimeSlots.getIds()) {
                rgIdsToTimeSlots.getIds().add(rgId);
            }
            for (List<TimeSlotInfo> timeSlotInfos: regItemTimeSlots.getTimeSlotInfos()) {
                rgIdsToTimeSlots.getTimeSlotInfos().add(timeSlotInfos);
            }

            // Check for Time Conflicts
            TimeConflictResult conflicts = checkForTimeConflicts(rgIdsToTimeSlots);
            if (conflicts.getIds().isEmpty()) {
                // Add the successful cart "registrations" to the list for use in next iteration
                successFromCart.add(regItem);
            } else {
                List<ConflictCourseResult> conflictCourseResults = new ArrayList<ConflictCourseResult>();
                for (CourseRegistrationInfo courseRegistrationInfo: copyExistingCrs) {
                    if (conflicts.getIds().contains(courseRegistrationInfo.getRegistrationGroupId())
                            || conflictValuesContainsString(conflicts, courseRegistrationInfo.getRegistrationGroupId())
                            && courseRegistrationInfo.getId() != null) {
                        ConflictCourseResult conflictCourseResult = new ConflictCourseResult();
                        conflictCourseResult.setMasterLprId(courseRegistrationInfo.getId());
                        CourseOfferingInfo courseOfferingInfo;
                        try {
                            courseOfferingInfo = coService.getCourseOffering(courseRegistrationInfo.getCourseOfferingId(), contextInfo);
                        } catch (Exception e) {
                            return KRMSEvaluator.constructExceptionPropositionResult(environment, e, this);
                        }
                        conflictCourseResult.setCourseCode(courseOfferingInfo.getCourseOfferingCode());
                        conflictCourseResult.setLongName(courseOfferingInfo.getCourseOfferingTitle());
                        RegistrationGroupInfo registrationGroupInfo;
                        try {
                            registrationGroupInfo = coService.getRegistrationGroup(courseRegistrationInfo.getRegistrationGroupId(), contextInfo);
                        } catch (Exception e) {
                            return KRMSEvaluator.constructExceptionPropositionResult(environment, e, this);
                        }
                        conflictCourseResult.setRegGroupCode(registrationGroupInfo.getName());
                        conflictCourseResults.add(conflictCourseResult);



                    }/* else {
                        successFromCart.add(regItem);
                    }    */
                }

                ValidationResultInfo vr = createValidationResultFailureForRegRequestItem(item, conflictCourseResults);
                Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
                executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
                PropositionResult result = new PropositionResult(false, executionDetails);
                BasicResult br = new BasicResult(executionDetails, ResultEvent.PROPOSITION_EVALUATED, this, environment, result.
                        getResult());
                environment.getEngineResults().addResult(br);
            }
        }

        // This result contains all the other results
        return recordAllRegRequestItems(environment, new ArrayList<ValidationResultInfo>());
    }

    @Override
    protected CourseRegistrationInfo createNewCourseRegistration(RegistrationRequestItemInfo item, ContextInfo contextInfo)
            throws OperationFailedException {
        CourseRegistrationInfo reg = super.createNewCourseRegistration(item, contextInfo);
        reg.setRegistrationGroupId(item.getRegistrationGroupId());
        return reg;
    }

    private boolean conflictValuesContainsString(TimeConflictResult conflicts, String string) {
        boolean stringFound = false;

        for (List<String> stringList:conflicts.getConflicts()) {
            if (stringList.contains(string)) {
                stringFound = true;
                break;
            }
        }

        return stringFound;
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    private TimeConflictDataContainer getTimeConflictDataContainer(List<CourseRegistrationInfo> courseRegistrationInfos, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<String> regGroupIds = new ArrayList<String>();
        for (CourseRegistrationInfo courseRegistrationInfo: courseRegistrationInfos) {
            regGroupIds.add(courseRegistrationInfo.getRegistrationGroupId());
        }

        List<RegistrationGroupInfo> registrationGroupInfos = coService.getRegistrationGroupsByIds(regGroupIds, contextInfo);


        ArrayList<String> rgIds = new ArrayList<String>();
        Map<String, String> aoIdsToRgIds = new LinkedHashMap<String, String>();
        List<String> aoIds = new ArrayList<String>();
        for (RegistrationGroupInfo registrationGroupInfo: registrationGroupInfos) {
            rgIds.add(registrationGroupInfo.getId());
            for (String activityOfferingId: registrationGroupInfo.getActivityOfferingIds()) {
                aoIdsToRgIds.put(activityOfferingId, registrationGroupInfo.getId());
            }
            aoIds.addAll(registrationGroupInfo.getActivityOfferingIds());

        }

        List<ActivityOfferingInfo> activityOfferingInfos = coService.getActivityOfferingsByIds(aoIds, contextInfo);

        Map<String, String> scheduleIdsToAoIds = new LinkedHashMap<String, String>();
        List<String> scheduleIds = new ArrayList<String>();
        for (ActivityOfferingInfo activityOfferingInfo: activityOfferingInfos) {
            for (String scheduleId: activityOfferingInfo.getScheduleIds()) {
                scheduleIdsToAoIds.put(scheduleId, activityOfferingInfo.getId());
            }
            scheduleIds.addAll(activityOfferingInfo.getScheduleIds());
        }

        List<ScheduleInfo> scheduleInfos = schedulingService.getSchedulesByIds(scheduleIds, contextInfo);

        Map<String, String> timeSlotIdsToScheduleIds = new LinkedHashMap<String, String>();
        List<String> timeslotIds = new ArrayList<String>();
        for (ScheduleInfo scheduleInfo: scheduleInfos) {
            for (ScheduleComponentInfo scheduleComponent: scheduleInfo.getScheduleComponents()) {
                timeslotIds.addAll(scheduleComponent.getTimeSlotIds());
                for (String timeSlotId: scheduleComponent.getTimeSlotIds()) {
                    timeSlotIdsToScheduleIds.put(timeSlotId, scheduleInfo.getId());
                }
            }
        }

        List<TimeSlotInfo> timeSlots = new ArrayList<TimeSlotInfo>();
        timeSlots= schedulingService.getTimeSlotsByIds(timeslotIds, contextInfo);

        TimeConflictDataContainer rgIdsToTimeSlots = new TimeConflictDataContainer();

        int rgIdCount = rgIds.size();

        ArrayList<List<TimeSlotInfo>> timeSlotInfos = new ArrayList<List<TimeSlotInfo>>(rgIdCount);
        for (int i = 0; i < rgIdCount; i++) {
            timeSlotInfos.add(new ArrayList<TimeSlotInfo>());
        }

        for (TimeSlotInfo timeSlotInfo: timeSlots) {
            String rgId = aoIdsToRgIds.get(scheduleIdsToAoIds.get(timeSlotIdsToScheduleIds.get(timeSlotInfo.getId())));
            if (timeSlotInfo.getStartTime() != null && timeSlotInfo.getEndTime() != null) {
              timeSlotInfos.get(rgIds.indexOf(rgId)).add(timeSlotInfo);
            } else {
                timeSlotInfos.remove(rgIds.indexOf(rgId));
                rgIds.remove(rgId);

            }
        }

        rgIdsToTimeSlots.setIds(rgIds);
        rgIdsToTimeSlots.setTimeSlotInfos(timeSlotInfos);


        return rgIdsToTimeSlots;
    }

    private TimeConflictResult checkForTimeConflicts(TimeConflictDataContainer rgsToTimeSlots) {
        TimeConflictCalculator timeConflictCalculator = new TimeConflictCalculator();
        TimeConflictResult conflicts;
        conflicts = timeConflictCalculator.calculateConflicts(rgsToTimeSlots, 0);

        return conflicts;
    }

    private ValidationResultInfo createValidationResultFailureForRegRequestItem(RegistrationRequestItemInfo item, List<ConflictCourseResult> conflictCourseResults) {
        String msg = RegistrationValidationResultsUtil.marshallConflictCourseMessage(LprServiceConstants.LPRTRANS_ITEM_TIME_CONFLICT_MESSAGE_KEY, conflictCourseResults);
        return createValidationResultFailureForRegRequestItem(item, msg);
    }

}
