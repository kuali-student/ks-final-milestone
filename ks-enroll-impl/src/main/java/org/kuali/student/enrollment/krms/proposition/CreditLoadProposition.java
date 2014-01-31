/**
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.krms.proposition;

import java.util.Date;
import java.util.List;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.rules.credit.limit.LoadCalculator;
import org.kuali.student.enrollment.rules.credit.limit.LoadCalculatorRuleFactory;
import org.kuali.student.enrollment.rules.credit.limit.RegistrationRequestMerger;
import org.kuali.student.r2.common.dto.ContextInfo;

/**
 * This proposition is used to evaluate whether or not a person is alive
 *
 * @author alubbers
 */
public class CreditLoadProposition extends AbstractLeafProposition {

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
        RegistrationRequestMerger merger = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_MERGER_TERM, this);
        LoadCalculatorRuleFactory loadCalculatorRuleFactory =
                environment.resolveTerm(RulesExecutionConstants.LOAD_CALCULATOR_RULE_FACTORY_TERM, this);
        GesService gesService = environment.resolveTerm(RulesExecutionConstants.GES_SERVICE_TERM, this);

        // get the name of rule to use to calculation load
        GesCriteriaInfo criteria = new GesCriteriaInfo();
        criteria.setPersonId(personId);
        criteria.setAtpId(atpId);
        ValueInfo value;
        try {
            value = gesService.evaluateValueOnDate(GesServiceConstants.PARAMETER_KEY_LOAD_CALCULATION_FOR_CREDIT_CHECKS,
                    criteria, asOfDate, contextInfo);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }

        String loadCalcRuleId = value.getStringValue();
        // get the actual executable rule we are using to do the calcualtion
        LoadCalculator calculator;
        try {
            calculator = loadCalculatorRuleFactory.getLoadCalculatorForRuleType(loadCalcRuleId, contextInfo);
         } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }
        
        List<CourseRegistrationInfo> existingCrs;
        try {
            existingCrs = crService.getCourseRegistrationsByStudentAndTerm(personId,
                    request.getTermId(),
                    contextInfo);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }

        // calculate the load assuming the request went through
        List<CourseRegistrationInfo> regs;
        try {
            regs = merger.simulate(request, existingCrs, true, contextInfo);
        }  catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }
        LoadInfo load;
        try {
             load = calculator.calculateLoad(regs, personId, contextInfo);
        }  catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }

        return this.recordSuccessWithDecimalValueDetail(environment,  load.getTotalCredits());
    }
}
