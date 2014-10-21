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
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.enrollment.rules.credit.limit.ActionEnum;
import org.kuali.student.enrollment.rules.credit.limit.ActivityRegistrationTransaction;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a base "Best Effort" proposition.
 *
 * @author Kuali Student Team
 */
public abstract class AbstractBestEffortProposition extends AbstractLeafProposition {

    public static ValidationResultInfo createValidationResultFailureForRegRequestItem(RegistrationRequestItemInfo item, String msg) {
        ValidationResultInfo result = new ValidationResultInfo();
        result.setLevel(ValidationResult.ErrorLevel.ERROR);
        String elt = "registrationRequestItems['" + item.getId() + "']";
        result.setElement(elt);
        result.setMessage(msg);
        return result;
    }

    protected PropositionResult recordAllRegRequestItems(ExecutionEnvironment environment,
                                                       List<ValidationResultInfo> validationResults) {
        Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
        executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, validationResults);
        PropositionResult result = new PropositionResult(true, executionDetails);
        BasicResult br = new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, result.getResult());
        environment.getEngineResults().addResult(br);
        return result;
    }

    @SuppressWarnings("unused")
    protected PropositionResult recordRegRequestItemResult(ExecutionEnvironment environment,
                                                         String regRequestItemId,
                                                         boolean isSuccess) {
        Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
        executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, regRequestItemId);
        PropositionResult result = new PropositionResult(isSuccess, executionDetails);
        BasicResult br = new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, isSuccess);
        environment.getEngineResults().addResult(br);
        return result;
    }

    protected List<CourseRegistrationInfo> getCourseAndWaitlistRegistrations(RegistrationRequestInfo request,
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

//    @SuppressWarnings("unused")
//    protected CourseRegistrationTransaction createNewCourseRegistrationTransaction(CourseOfferingService coService,
//                                                                                   RegistrationRequestItemInfo item,
//                                                                                   boolean skipActivities,
//                                                                                   ContextInfo contextInfo)
//            throws OperationFailedException {
//        CourseRegistrationInfo reg = this.createNewCourseRegistration(item, contextInfo);
//        List<ActivityRegistrationTransaction> activityTrans;
//        if (skipActivities) {
//            activityTrans = Collections.emptyList();
//        } else {
//            activityTrans = this.createNewActivityTransactions(item, contextInfo);
//        }
//        return new CourseRegistrationTransaction(ActionEnum.CREATE, reg, activityTrans);
//    }

    protected CourseRegistrationInfo createNewCourseRegistration(CourseOfferingService coService,
            RegistrationRequestItemInfo item, ContextInfo contextInfo)
            throws OperationFailedException {
        CourseRegistrationInfo reg = new CourseRegistrationInfo();
        RegistrationGroupInfo regGroup = this.getRegGroup(coService, item.getRegistrationGroupId(), contextInfo);
        reg.setPersonId(item.getPersonId());
        reg.setTypeKey(LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY);
        reg.setStateKey(LprServiceConstants.ACTIVE_STATE_KEY);
        reg.setCourseOfferingId(regGroup.getCourseOfferingId());
        reg.setCredits(item.getCredits());
        reg.setGradingOptionId(item.getGradingOptionId());
        reg.setEffectiveDate(contextInfo.getCurrentDate());
        reg.setExpirationDate(null);
        reg.setRegistrationGroupId(regGroup.getId());
        // Adding all but we might want to split and store some attributes on the Activity and others on Course registration
        reg.getAttributes().addAll(item.getAttributes());
        return reg;
    }

    private RegistrationGroupInfo getRegGroup(CourseOfferingService coService,
                                                String regGroupId, ContextInfo contextInfo)
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

    protected List<ActivityRegistrationTransaction> createNewActivityTransactions(CourseOfferingService coService,
                                                                                  RegistrationRequestItemInfo item,
                                                                                  ContextInfo contextInfo)
            throws OperationFailedException {
        List<ActivityRegistrationTransaction> list = new ArrayList<ActivityRegistrationTransaction>();
        RegistrationGroupInfo regGroup = this.getRegGroup(coService, item.getRegistrationGroupId(), contextInfo);
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
        return new ActivityRegistrationTransaction(ActionEnum.CREATE, reg);
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
