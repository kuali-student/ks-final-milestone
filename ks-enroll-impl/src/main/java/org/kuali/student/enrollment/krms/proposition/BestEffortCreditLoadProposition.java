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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.enrollment.registration.client.service.impl.util.RegistrationValidationResultsUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a proposition that does a credit load check for registration requests.
 *
 * @author Kuali Student Team
 */
public class BestEffortCreditLoadProposition extends AbstractBestEffortProposition {
    public static final KualiDecimal NO_CREDIT_LIMIT = new KualiDecimal(-1);
    private static final boolean ALLOW_ALL_NON_ADDS = true; // allow all non adds right now
    private boolean countWaitlistedCoursesTowardsCreditLimit = true;

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        RegistrationRequestInfo request = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_TERM, this);
        String personId = environment.resolveTerm(RulesExecutionConstants.PERSON_ID_TERM, this);
        //List<RegistrationRequestItemInfo> regRequestItems = request.getRegistrationRequestItems();
        //Changed this code to use a list of one from the environment
        RegistrationRequestItemInfo requestItemInfo = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_TERM, this);

        if (!StringUtils.equals(requestItemInfo.getStateKey(), LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY)) {
            List<RegistrationRequestItemInfo> regRequestItems = new ArrayList<RegistrationRequestItemInfo>();
            regRequestItems.add(requestItemInfo);

            CourseRegistrationService crService = environment.resolveTerm(RulesExecutionConstants.COURSE_REGISTRATION_SERVICE_TERM,
                    this);
            CourseWaitListService wlService = environment.resolveTerm(RulesExecutionConstants.COURSE_WAIT_LIST_SERVICE_TERM,
                    this);
            CourseOfferingService coService = environment.resolveTerm(RulesExecutionConstants.COURSE_OFFERING_SERVICE_TERM, this);

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

            // Compute the credit limit (assume a fixed value, for now)
            ValueInfo creditLimit;
            try {
                creditLimit = computeCreditLimit(environment);
            } catch (Exception ex) {
                return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
            }
            KualiDecimal creditLimitValue = creditLimit.getDecimalValue();

            // Fetch registrations
            List<CourseRegistrationInfo> existingCrs = new ArrayList<CourseRegistrationInfo>();
            existingCrs.addAll((List<CourseRegistrationInfo>) environment.resolveTerm(RulesExecutionConstants.EXISTING_REGISTRATIONS_TERM, this));
            if(countWaitlistedCoursesTowardsCreditLimit){
                existingCrs.addAll((List<CourseRegistrationInfo>) environment.resolveTerm(RulesExecutionConstants.EXISTING_WAITLISTED_REGISTRATIONS_TERM, this));
            }
            existingCrs.addAll((List<CourseRegistrationInfo>) environment.resolveTerm(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM, this));

//        try {
//            existingCrs = getCourseAndWaitlistRegistrations(request, personId, getCourseRegistrationService(), getCourseWaitListService(), contextInfo);
//        } catch (Exception ex) {
//            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
//        }


            // Iterate over each item and check load
            List<CourseRegistrationInfo> successFromCart = new ArrayList<CourseRegistrationInfo>();
//        for (RegistrationRequestItemInfo item: request.getRegistrationRequestItems()) {
            for (RegistrationRequestItemInfo item: regRequestItems) {
                List<CourseRegistrationInfo> copyExistingCrs = new ArrayList<CourseRegistrationInfo>();
                // Copy the existing registrations
                copyExistingCrs.addAll(existingCrs);
                // Add the successful RGs from the cart (added from previous iterations)
                copyExistingCrs.addAll(successFromCart);
                // Add the item being iterated over
                CourseRegistrationInfo regItem;
                try {
                    regItem = createNewCourseRegistration(coService, item, contextInfo);
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
                    ValidationResultInfo vr = createValidationResultFailureForRegRequestItem(item, creditLimitValue);
                    Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
                    executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
                    PropositionResult result = new PropositionResult(false, executionDetails);
                    BasicResult br = new BasicResult(executionDetails, ResultEvent.PROPOSITION_EVALUATED, this, environment, result.
                            getResult());
                    environment.getEngineResults().addResult(br);
                }
            }
        }

        // This result contains all the other results
        return recordAllRegRequestItems(environment,  new ArrayList<ValidationResultInfo>());
    }

    private ValidationResultInfo createValidationResultFailureForRegRequestItem(RegistrationRequestItemInfo item, KualiDecimal creditLimitValue) {
        String msg = RegistrationValidationResultsUtil.marshallMaxCreditMessage(
                LprServiceConstants.LPRTRANS_ITEM_CREDIT_LOAD_EXCEEDED_MESSAGE_KEY, creditLimitValue.toString());
        return createValidationResultFailureForRegRequestItem(item, msg);
    }

    /**
     * Verify that the sum of the course load (including waitlisted courses) is less than the limit.
     * @param existingCrs Course registrations taken by students including waitlisted courses
     * @param creditLimitValue The credit limit
     * @return true, if the courses do not exceed the credit limit
     */
    private boolean verifyLoadIsOK(List<CourseRegistrationInfo> existingCrs,
                                   KualiDecimal creditLimitValue) {
        if (creditLimitValue.equals(NO_CREDIT_LIMIT)) {
            // If no limit is found, then NO_CREDIT_LIMIT is the value of the credit limit, which
            // indicates there's no credit limit.
            return true;
        }
        KualiDecimal totalLoad = new KualiDecimal(0);
        for (CourseRegistrationInfo info: existingCrs) {
            totalLoad = totalLoad.add(info.getCredits());
        }
        return totalLoad.isLessEqual(creditLimitValue);
    }

    /**
     * Compute the credit limit.
     * @param environment the execution environment
     * @return the credit limit
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
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
        try {
            value = gesService.evaluateValueOnDate(GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT,
                    criteria,
                    asOfDate,
                    contextInfo);
        } catch (DoesNotExistException e) {
            // Set a default value of -1 if the credit limit can't be found in GES, e.g., for Winter terms
            value = getDefaultCreditLimit();
        }
        return value;
    }

    /**
     * Treat -1 as no credit limit (since infinity causes KualiDecimal to throw an exception)
     * @return A value info with value of -1
     */
    private ValueInfo getDefaultCreditLimit() {
        ValueInfo value;
        value = new ValueInfo();
        value.setParameterKey(GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT);
        value.setAtpTypeKeys(new ArrayList<String>());
        value.setPopulationId(PopulationServiceConstants.POPULATION_RULE_TYPE_EVERYONE_KEY);
        value.setDecimalValue(NO_CREDIT_LIMIT);
        value.setTypeKey(GesServiceConstants.GES_VALUE_TYPE_KEY);
        value.setStateKey(GesServiceConstants.GES_VALUE_ACTIVE_STATE_KEY);
        value.setId("NONE");
        return value;
    }

    public void setCountWaitlistedCoursesTowardsCreditLimit(boolean countWaitlistedCoursesTowardsCreditLimit) {
        this.countWaitlistedCoursesTowardsCreditLimit = countWaitlistedCoursesTowardsCreditLimit;
    }
}
