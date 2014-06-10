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
 * Created by Charles on 5/29/14
 */
package org.kuali.student.enrollment.krms.proposition;

import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.core.process.evaluator.AbstractCheckProposition;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
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
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class BestEffortCreditLoadProposition extends AbstractLeafProposition {
    private CourseOfferingService coService;

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        RegistrationRequestInfo request = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_TERM, this);
//        String registrationRequestId = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_ID_TERM, this);
        String personId = environment.resolveTerm(RulesExecutionConstants.PERSON_ID_TERM, this);
        String atpId = environment.resolveTerm(RulesExecutionConstants.ATP_ID_TERM, this);
        Date asOfDate = environment.resolveTerm(RulesExecutionConstants.AS_OF_DATE_TERM, this);
        CourseRegistrationService crService = environment.resolveTerm(RulesExecutionConstants.COURSE_REGISTRATION_SERVICE_TERM,
                this);
        CourseWaitListService wlService = environment.resolveTerm(RulesExecutionConstants.COURSE_WAIT_LIST_SERVICE_TERM,
                this);
        coService = environment.resolveTerm(RulesExecutionConstants.COURSE_OFFERING_SERVICE_TERM, this);

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
        // Compute the credit limit (assume a fixed value, for now)
        ValueInfo creditLimit;
        try {
            creditLimit = computeCreditLimit(environment);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }
        KualiDecimal creditLimitValue = creditLimit.getDecimalValue();

        // Fetch registrations
        List<CourseRegistrationInfo> existingCrs;
        try {
            existingCrs = getCourseAndWaitlistRegistrations(request, personId, crService, wlService, contextInfo);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }
        // Iterate over each item and check load
        List<CourseRegistrationInfo> successFromCart = new ArrayList<CourseRegistrationInfo>();
        for (RegistrationRequestItemInfo item: request.getRegistrationRequestItems()) {
            List<CourseRegistrationInfo> copyExistingCrs = new ArrayList<CourseRegistrationInfo>();
            // Copy the existing registrations
            copyExistingCrs.addAll(existingCrs);
            // Add the successful RGs from the cart (added from previous iterations)
            copyExistingCrs.addAll(successFromCart);
            // Add the item being iterated over
            CourseRegistrationInfo regItem = null;
            try {
                regItem = createNewCourseRegistration(item, contextInfo);
            } catch (OperationFailedException ex) {
                return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
            }
            copyExistingCrs.add(regItem);
            // Verify the credit load
            boolean loadVerified = verifyLoadIsOK(copyExistingCrs, creditLimitValue);
            if (loadVerified) {
                // Add the successful cart "registrations" to the list for use in next iteration
                successFromCart.add(regItem);
            } else {
                ValidationResultInfo vr = createValidationResultFailureForRegRequestItem(item);
                Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
                executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
                PropositionResult result = new PropositionResult(false, executionDetails);
                BasicResult br = new BasicResult(executionDetails, ResultEvent.PROPOSITION_EVALUATED, this, environment, result.
                        getResult());
                environment.getEngineResults().addResult(br);
            }
        }
        // This result contains all the other results
        return recordAllRegRequestItems(environment,  new ArrayList<ValidationResultInfo>());
    }

    private ValidationResultInfo createValidationResultFailureForRegRequestItem(RegistrationRequestItemInfo item) {
        ValidationResultInfo result = new ValidationResultInfo();
        result.setLevel(ValidationResult.ErrorLevel.ERROR);
        String elt = "registrationRequestItems['" + item.getId() + "']";
        result.setElement(elt);
        String msg = LprServiceConstants.LPRTRANS_ITEM_CREDIT_LOAD_EXCEEDED_MESSAGE_KEY;
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

    /**
     * Verify the sum of the course load (including waitlisted courses) is less than the limit
     * @param existingCrs Course registrations taken by students including waitlisted courses
     * @param creditLimitValue The credit limit
     * @return true, if the courses do not exceed the credit limit
     */
    private boolean verifyLoadIsOK(List<CourseRegistrationInfo> existingCrs,
                                   KualiDecimal creditLimitValue) {
        KualiDecimal totalLoad = new KualiDecimal(0);
        for (CourseRegistrationInfo info: existingCrs) {
            totalLoad = totalLoad.add(info.getCredits());
        }
        boolean result = totalLoad.isLessEqual(creditLimitValue);
        return result;
    }

    private ValueInfo computeCreditLimit(ExecutionEnvironment environment)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        //        RegistrationRequestInfo request = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_TERM, this);
//        String registrationRequestId = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_ID_TERM, this);
        String personId = environment.resolveTerm(RulesExecutionConstants.PERSON_ID_TERM, this);
        String atpId = environment.resolveTerm(RulesExecutionConstants.ATP_ID_TERM, this);
        //        AtpInfo atp = environment.resolveTerm(RulesExecutionConstants.ATP_TERM, this);
        Date asOfDate = environment.resolveTerm(RulesExecutionConstants.AS_OF_DATE_TERM, this);
        GesService gesService = environment.resolveTerm(RulesExecutionConstants.GES_SERVICE_TERM, this);
        GesCriteriaInfo criteria = new GesCriteriaInfo();
        criteria.setPersonId(personId);
        criteria.setAtpId(atpId);
        ValueInfo value;
        value = gesService.evaluateValueOnDate(GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT,
                criteria,
                asOfDate,
                contextInfo);
        return value;
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
