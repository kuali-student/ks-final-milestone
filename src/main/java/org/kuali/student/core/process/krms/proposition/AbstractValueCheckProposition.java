/**
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.core.process.krms.proposition;

import java.util.Date;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.dto.ValueOverrideInfo;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.core.process.evaluator.PropositionFactory;

/**
 * This class represents a proposition to evaluate if a student has a Hold
 *
 * @author nwright
 */
public abstract class AbstractValueCheckProposition extends AbstractCheckProposition {

    public AbstractValueCheckProposition(InstructionInfo instruction, CheckInfo check) {
        super(instruction, check);
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {

        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        String atpId = environment.resolveTerm(RulesExecutionConstants.ATP_ID_TERM, this);
        boolean recordSuccesses = environment.resolveTerm(RulesExecutionConstants.RECORD_INSTRUCTION_SUCCESSES_TERM, this);
        Date asOfDate = environment.resolveTerm(RulesExecutionConstants.AS_OF_DATE_TERM, this);

        KualiDecimal leftHandValue;
        try {
            leftHandValue = calculateLeftHandValue(environment);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }
        KualiDecimal rightHandValue;
        try {
            rightHandValue = calculateRightHandValue(environment);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }


        if (matches(leftHandValue, rightHandValue)) {
            return this.recordSuccessResult(environment);
        }
        // check if student has an override supplying a new start date 
        // Note: I wonder if the business wants to be able to override a start date setting it later instead of earlier?
        // in other words the exemption actually makes it harder/more restrictive for the student
        // if so we need to do this before we do the ABOVE check
        ValueOverrideInfo valueOverride;
        try {
            valueOverride = this.checkIfHasExeptionOverridingValue(environment, asOfDate, leftHandValue, rightHandValue,
                    contextInfo);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }
        if (valueOverride != null) {
            if (matches(valueOverride.getLeftComparisonValue(), valueOverride.getRightComparisonValue())) {
                if (recordSuccesses) {
                    ValidationResultInfo vr = new ValidationResultInfo();
                    vr.setElement(instruction.getId());
                    vr.setLevel(ValidationResult.ErrorLevel.OK);
                    vr.setMessage(
                            "Iniitally failed left/right comparison check but passed because an exemption overrode the values");
                    Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
                    executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
                    PropositionResult result = new PropositionResult(true, executionDetails);
                    BasicResult br = new BasicResult(executionDetails, ResultEvent.PROPOSITION_EVALUATED, this, environment,
                            result.getResult());
                    environment.getEngineResults().addResult(br);
                    return result;
                }
                PropositionResult result = new PropositionResult(true);
                BasicResult br = new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, result.getResult());
                environment.getEngineResults().addResult(br);
                return result;
            }
        }
        return this.recordFailureResultOrExemption(environment);
    }

    protected abstract boolean matches(KualiDecimal leftValue, KualiDecimal rightValue);

    private KualiDecimal calculateLeftHandValue(ExecutionEnvironment environment) throws Exception {
        KualiDecimal value = this.calculateValueFromRule(environment, check.getLeftComparisonRuleId());
        return value;
    }

    private KualiDecimal calculateRightHandValue(ExecutionEnvironment environment) throws Exception {
        if (check.getRightComparisonValue() != null) {
            try {
                KualiDecimal value = new KualiDecimal(check.getRightComparisonValue());
                return value;
            } catch (IllegalArgumentException ex) {
                throw new OperationFailedException("right comparison value cannot be converted to a decimal ", ex);
            }
        }
        KualiDecimal value = this.calculateValueFromRule(environment, check.getRightComparisonRuleId());
        return value;
    }

    private KualiDecimal calculateValueFromRule(ExecutionEnvironment environment, String ruleId) throws Exception {
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        PropositionFactory propositionFactory =
                environment.resolveTerm(RulesExecutionConstants.PROPOSITION_FACTORY_TERM, this);
        Proposition prop = propositionFactory.getProposition(ruleId, contextInfo);
        PropositionResult propResult = prop.evaluate(environment);
        if (!propResult.getResult()) {
            Exception ex = (Exception) propResult.getExecutionDetails().get(RulesExecutionConstants.PROCESS_EVALUATION_EXCEPTION);
            if (ex == null) {
                throw new OperationFailedException("value proposition for " + ruleId + " failed but did not include the exception");
            }
            throw new OperationFailedException("value proposition for " + ruleId + " failed", ex);
        }
        KualiDecimal value = (KualiDecimal) propResult.getExecutionDetails().get(
                RulesExecutionConstants.PROCESS_EVALUATION_RESULTS);
        if (value == null) {
            throw new OperationFailedException(
                    "value proposition for " + ruleId + " succeeded but did not include the result value");
        }
        return value;
    }

    /**
     * Override to return the type of exemption to look for if this is overridden for the student should be one of the following..
     * check value min, or max or equals
     */
    protected abstract String getExemptionTypeToLookFor();

    private ValueOverrideInfo checkIfHasExeptionOverridingValue(ExecutionEnvironment environment,
            Date asOfDate,
            KualiDecimal leftComparisonValue,
            KualiDecimal rightComparisonValue,
            ContextInfo contextInfo)
            throws Exception {

        ExemptionService exemptionService = environment.resolveTerm(RulesExecutionConstants.EXEMPTION_SERVICE_TERM, this);
        String personId = environment.resolveTerm(RulesExecutionConstants.PERSON_ID_TERM, this);
        List<ExemptionInfo> exemptions;
        exemptions = exemptionService.getActiveExemptionsByTypeProcessAndCheckForPerson(
                getExemptionTypeToLookFor(),
                instruction.getProcessKey(),
                check.getId(),
                personId,
                asOfDate,
                contextInfo);
        if (exemptions.isEmpty()) {
            return null;
        }
        ValueOverrideInfo minMax = new ValueOverrideInfo();
        boolean overrode = false;
        for (ExemptionInfo exemption : exemptions) {
            ValueOverrideInfo override = exemption.getValueOverride();
            if (override == null) {
                continue;
            }
            if (override.getLeftComparisonValue() != null) {
                if (minMax.getLeftComparisonValue() == null) {
                    minMax.setLeftComparisonValue(override.getLeftComparisonValue());
                    overrode = true;
                    continue;
                }
                if (minMax.getLeftComparisonValue().isGreaterThan(override.getLeftComparisonValue())) {
                    minMax.setLeftComparisonValue(override.getLeftComparisonValue());
                    overrode = true;
                    continue;
                }
            }
            if (override.getRightComparisonValue() != null) {
                if (minMax.getRightComparisonValue() == null) {
                    minMax.setRightComparisonValue(override.getRightComparisonValue());
                    overrode = true;
                    continue;
                }
                if (minMax.getRightComparisonValue().isLessThan(override.getRightComparisonValue())) {
                    minMax.setRightComparisonValue(override.getRightComparisonValue());
                    overrode = true;
                    continue;
                }
            }
        }
        if (!overrode) {
            return null;
        }
        if (minMax.getLeftComparisonValue() == null) {
            minMax.setLeftComparisonValue(leftComparisonValue);
        }
        if (minMax.getRightComparisonValue() == null) {
            minMax.setRightComparisonValue(rightComparisonValue);
        }
        return minMax;
    }
}
