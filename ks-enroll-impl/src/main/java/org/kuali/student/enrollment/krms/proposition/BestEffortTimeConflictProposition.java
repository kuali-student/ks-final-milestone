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
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.enrollment.registration.client.service.dto.ConflictCourseResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.RegistrationValidationResultsUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.TimeConflictCalculator;
import org.kuali.student.enrollment.rules.credit.limit.ActionEnum;
import org.kuali.student.enrollment.rules.credit.limit.ActivityRegistrationTransaction;
import org.kuali.student.enrollment.rules.credit.limit.CourseRegistrationTransaction;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult;
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
 * This class is a proposition that does a time conflict check for registration requests
 *
 * @author Kuali Student Team
 */
public class BestEffortTimeConflictProposition extends AbstractLeafProposition {

    private CourseOfferingService coService;
    private SchedulingService schedulingService;

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
            if(true){//Allow all non adds right now
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
        PropositionResult propositionResult = null;

        Map<String, List<TimeSlotInfo>> rgIdsToTimeSlots;
        try{
        rgIdsToTimeSlots = getRgToTimeSlotMap(existingCrs, contextInfo);
        } catch(Exception ex) {
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
            Map<String, List<TimeSlotInfo>> regItemTimeSlots;
            try{
                regItemTimeSlots = getRgToTimeSlotMap(newRegItem, contextInfo);
            } catch(Exception ex) {
                return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
            }
            for(String rgId: regItemTimeSlots.keySet()){
                rgIdsToTimeSlots.put(rgId, regItemTimeSlots.get(rgId));
            }
            // Verify the credit load
            Map<String, List<String>> conflicts = checkForTimeConflicts(rgIdsToTimeSlots);
            if (conflicts.isEmpty()) {
                // Add the successful cart "registrations" to the list for use in next iteration
                successFromCart.add(regItem);
            } else {
                List<ConflictCourseResult> conflictCourseResults = new ArrayList<ConflictCourseResult>();
                List<CourseRegistrationInfo> conflictingCourseRegistrations = new ArrayList<CourseRegistrationInfo>();
                for(CourseRegistrationInfo courseRegistrationInfo: copyExistingCrs){
                    if(conflicts.keySet().contains(courseRegistrationInfo.getRegistrationGroupId()) || conflicts.containsValue(courseRegistrationInfo.getRegistrationGroupId())){
                        conflictingCourseRegistrations.add(courseRegistrationInfo);
                        ConflictCourseResult conflictCourseResult = new ConflictCourseResult();
                        conflictCourseResult.setMasterLprId(item.getId());
                        CourseOfferingInfo courseOfferingInfo;
                        try{
                            courseOfferingInfo = coService.getCourseOffering(courseRegistrationInfo.getCourseOfferingId(), contextInfo);
                        } catch(Exception e){
                            return KRMSEvaluator.constructExceptionPropositionResult(environment, e, this);
                        }
                        conflictCourseResult.setCourseCode(courseOfferingInfo.getCourseOfferingCode());
                        conflictCourseResult.setLongName(courseOfferingInfo.getCourseOfferingTitle());
                        RegistrationGroupInfo registrationGroupInfo;
                        try{
                            registrationGroupInfo = coService.getRegistrationGroup(courseRegistrationInfo.getRegistrationGroupId(), contextInfo);
                        } catch(Exception e){
                            return KRMSEvaluator.constructExceptionPropositionResult(environment, e, this);
                        }
                        conflictCourseResult.setRegGroupCode(registrationGroupInfo.getName());
                        conflictCourseResults.add(conflictCourseResult);
                    }
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

        if(propositionResult!=null){
            return propositionResult;
        }

        // This result contains all the other results
        return recordAllRegRequestItems(environment,  new ArrayList<ValidationResultInfo>());
    }

    private Map<String, List<TimeSlotInfo>> getRgToTimeSlotMap(List<CourseRegistrationInfo> courseRegistrationInfos, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<String> regGroupIds = new ArrayList<String>();
        for(CourseRegistrationInfo courseRegistrationInfo: courseRegistrationInfos){
            regGroupIds.add(courseRegistrationInfo.getRegistrationGroupId());
        }

        List<RegistrationGroupInfo> registrationGroupInfos = new ArrayList<RegistrationGroupInfo>();

        registrationGroupInfos = coService.getRegistrationGroupsByIds(regGroupIds, contextInfo);



        Map<String, String> aoIdsToRgIds = new LinkedHashMap<String, String>();
        List<String> aoIds = new ArrayList<String>();
        for(RegistrationGroupInfo registrationGroupInfo: registrationGroupInfos){
            for(String activityOfferingId: registrationGroupInfo.getActivityOfferingIds()){
                aoIdsToRgIds.put(activityOfferingId, registrationGroupInfo.getId());
            }
            aoIds.addAll(registrationGroupInfo.getActivityOfferingIds());

        }

        List<ActivityOfferingInfo> activityOfferingInfos = new ArrayList<ActivityOfferingInfo>();

        activityOfferingInfos  = coService.getActivityOfferingsByIds(aoIds, contextInfo);


        Map<String, String> scheduleIdsToAoIds = new LinkedHashMap<String, String>();
        List<String> scheduleIds = new ArrayList<String>();
        for(ActivityOfferingInfo activityOfferingInfo: activityOfferingInfos){
            for(String scheduleId: activityOfferingInfo.getScheduleIds()){
                scheduleIdsToAoIds.put(scheduleId, activityOfferingInfo.getId());
            }
            scheduleIds.addAll(activityOfferingInfo.getScheduleIds());
        }

        List<ScheduleInfo> scheduleInfos = new ArrayList<ScheduleInfo>();

        scheduleInfos  = schedulingService.getSchedulesByIds(scheduleIds, contextInfo);

        Map<String, String> timeSlotIdsToScheduleIds = new LinkedHashMap<String, String>();
        List<String> timeslotIds = new ArrayList<String>();
        for(ScheduleInfo scheduleInfo: scheduleInfos){
            for(ScheduleComponentInfo scheduleComponent: scheduleInfo.getScheduleComponents()){
                timeslotIds.addAll(scheduleComponent.getTimeSlotIds());
                for(String timeSlotId: scheduleComponent.getTimeSlotIds()){
                    timeSlotIdsToScheduleIds.put(timeSlotId, scheduleInfo.getId());
                }
            }
        }


        List<TimeSlotInfo> timeSlots = new ArrayList<TimeSlotInfo>();

        timeSlots  = schedulingService.getTimeSlotsByIds(timeslotIds, contextInfo);


        Map<String, List<TimeSlotInfo>> rgIdsToTimeSlots = new LinkedHashMap<String, List<TimeSlotInfo>>();

        for(TimeSlotInfo timeSlotInfo: timeSlots){
            String rgId = aoIdsToRgIds.get(scheduleIdsToAoIds.get(timeSlotIdsToScheduleIds.get(timeSlotInfo.getId())));
            if(rgIdsToTimeSlots.containsKey(rgId)){
                rgIdsToTimeSlots.get(rgId).add(timeSlotInfo);
            } else {
                List<TimeSlotInfo> newTimeSlotList = new ArrayList<TimeSlotInfo>();
                newTimeSlotList.add(timeSlotInfo);
                rgIdsToTimeSlots.put(rgId, newTimeSlotList);
            }

        }


        return rgIdsToTimeSlots;
    }

    private Map<String, List<String>> checkForTimeConflicts(Map<String, List<TimeSlotInfo>> rgsToTimeSlots){
        TimeConflictCalculator timeConflictCalculator = new TimeConflictCalculator();
        Map<String, List<String>> conflicts;
        conflicts=timeConflictCalculator.calculateConflicts(rgsToTimeSlots, 0);

        return conflicts;
    }

    private ValidationResultInfo createValidationResultFailureForRegRequestItem(RegistrationRequestItemInfo item, List<ConflictCourseResult> conflictCourseResults) {
        ValidationResultInfo result = new ValidationResultInfo();
        result.setLevel(ValidationResult.ErrorLevel.ERROR);
        String elt = "registrationRequestItems['" + item.getId() + "']";
        result.setElement(elt);
        String msg = RegistrationValidationResultsUtil.marshallConflictCourseMessage(LprServiceConstants.LPRTRANS_ITEM_TIME_CONFLICT_MESSAGE_KEY, conflictCourseResults);
        result.setMessage(msg);
        return result;
    }

    private PropositionResult recordAllRegRequestItems(ExecutionEnvironment environment,
                                                       List<ValidationResultInfo> validationResults) {
        Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
        executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, validationResults);
        PropositionResult result = new PropositionResult(true, executionDetails);
        BasicResult br = new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, result.getResult());
        environment.getEngineResults().addResult(br);
        return result;
    }

    private PropositionResult recordRegRequestItemResult(ExecutionEnvironment environment,
                                                         String regRequestItemId,
                                                         boolean isSuccess) {
        Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
        executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, regRequestItemId);
        PropositionResult result = new PropositionResult(isSuccess, executionDetails);
        BasicResult br = new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, isSuccess);
        environment.getEngineResults().addResult(br);
        return result;
    }


    private List<CourseRegistrationInfo> getCourseAndWaitlistRegistrations(RegistrationRequestInfo request,
                                                                           String personId,
                                                                           CourseRegistrationService crService,
                                                                           CourseWaitListService wlService,
                                                                           ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CourseRegistrationInfo> existingCrs;
        existingCrs = crService.getCourseRegistrationsByStudentAndTerm(personId,
                request.getTermId(),
                contextInfo);
        List<CourseRegistrationInfo> waitListedCourses =
                wlService.getCourseWaitListRegistrationsByStudentAndTerm(personId, request.getTermId(),
                        contextInfo);
        // Credit load is computed from actual plus waitlisted courses
        existingCrs.addAll(waitListedCourses);
        return existingCrs;
    }


    private CourseRegistrationTransaction createNewCourseRegistrationTransaction(RegistrationRequestItemInfo item,
                                                                                 boolean skipActivities,
                                                                                 ContextInfo contextInfo)
            throws OperationFailedException {
        CourseRegistrationInfo reg = this.createNewCourseRegistration(item, contextInfo);
        List<ActivityRegistrationTransaction> activityTrans;
        if (skipActivities) {
            activityTrans = Collections.EMPTY_LIST;
        } else {
            activityTrans = this.createNewActivityTransactions(item, contextInfo);
        }
        CourseRegistrationTransaction rt = new CourseRegistrationTransaction(ActionEnum.CREATE, reg, activityTrans);
        return rt;
    }

    private CourseRegistrationInfo createNewCourseRegistration(RegistrationRequestItemInfo item, ContextInfo contextInfo)
            throws OperationFailedException {
        CourseRegistrationInfo reg = new CourseRegistrationInfo();
        RegistrationGroupInfo regGroup = this.getRegGroup(item.getRegistrationGroupId(), contextInfo);
        reg.setPersonId(item.getPersonId());
        reg.setTypeKey(LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY);
        reg.setStateKey(LprServiceConstants.ACTIVE_STATE_KEY);
        reg.setCourseOfferingId(regGroup.getCourseOfferingId());
        reg.setCredits(item.getCredits());
        reg.setGradingOptionId(item.getGradingOptionId());
        reg.setEffectiveDate(contextInfo.getCurrentDate());
        reg.setExpirationDate(null);
        reg.setRegistrationGroupId(item.getRegistrationGroupId());
        // Adding all but we might want to split and store some attributes on the Activity and others on Course registration
        reg.getAttributes().addAll(item.getAttributes());
        return reg;
    }

    private RegistrationGroupInfo getRegGroup(String regGroupId, ContextInfo contextInfo)
            throws OperationFailedException {
        RegistrationGroupInfo regGroup;
        try {
            regGroup = coService.getRegistrationGroup(regGroupId, contextInfo);
            return regGroup;
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("new reg group should exist", ex);
        } catch (InvalidParameterException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (MissingParameterException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    private List<ActivityRegistrationTransaction> createNewActivityTransactions(RegistrationRequestItemInfo item,
                                                                                ContextInfo contextInfo)
            throws OperationFailedException {
        List<ActivityRegistrationTransaction> list = new ArrayList<ActivityRegistrationTransaction>();
        RegistrationGroupInfo regGroup = this.getRegGroup(item.getRegistrationGroupId(), contextInfo);
        for (String activityOfferingId : regGroup.getActivityOfferingIds()) {
            ActivityRegistrationTransaction trans = this.createNewActivityTransaction(item, contextInfo, activityOfferingId);
            list.add(trans);
        }
        return list;
    }

    private ActivityRegistrationTransaction createNewActivityTransaction(RegistrationRequestItemInfo item,
                                                                         ContextInfo contextInfo,
                                                                         String activityOfferingId)
            throws OperationFailedException {
        ActivityRegistrationInfo reg = this.createNewActivityRegistration(item, contextInfo, activityOfferingId);
        ActivityRegistrationTransaction trans = new ActivityRegistrationTransaction(ActionEnum.CREATE, reg);
        return trans;
    }

    private ActivityRegistrationInfo createNewActivityRegistration(RegistrationRequestItemInfo item,
                                                                   ContextInfo contextInfo,
                                                                   String activityOfferingId)
            throws OperationFailedException {
        ActivityRegistrationInfo reg = new ActivityRegistrationInfo();
        reg.setPersonId(item.getPersonId());
        reg.setTypeKey(LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY);
        reg.setStateKey(LprServiceConstants.ACTIVE_STATE_KEY);
        reg.setActivityOfferingId(activityOfferingId);
        reg.setEffectiveDate(contextInfo.getCurrentDate());
        reg.setExpirationDate(null);
        // Adding all but we might want to split and store some attributes on the Activity and others on Course registration
        reg.getAttributes().addAll(item.getAttributes());
        return reg;
    }
}
