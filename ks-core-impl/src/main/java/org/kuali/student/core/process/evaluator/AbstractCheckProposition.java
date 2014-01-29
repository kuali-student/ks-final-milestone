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
package org.kuali.student.core.process.evaluator;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.constants.ExemptionServiceConstants;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;

/**
 * Base class for any check proposition
 *
 * @author nwright
 */
public abstract class AbstractCheckProposition extends AbstractLeafProposition {

    protected InstructionInfo instruction;
    protected CheckInfo check;

    public AbstractCheckProposition(InstructionInfo instruction, CheckInfo check) {
        this.instruction = instruction;
        this.check = check;
    }

    public PropositionResult recordSuccessResult(ExecutionEnvironment environment) {
        boolean recordSuccesses = environment.resolveTerm(RulesExecutionConstants.RECORD_INSTRUCTION_SUCCESSES_TERM, this);
        if (recordSuccesses) {
            ValidationResultInfo vr = new ValidationResultInfo();
            vr.setElement(instruction.getId());
            vr.setLevel(ValidationResult.ErrorLevel.OK);
            vr.setMessage("Successfully passed this check: " + check.getName());
            Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
            executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
            PropositionResult result = new PropositionResult(true, executionDetails);
            BasicResult br = new BasicResult(executionDetails, ResultEvent.PROPOSITION_EVALUATED, this,
                    environment, result.getResult());
            environment.getEngineResults().addResult(br);
            return result;
        }
        PropositionResult result = new PropositionResult(true);
        BasicResult br = new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, result.getResult());
        environment.getEngineResults().addResult(br);
        return result;
    }

    public PropositionResult recordFailureResultOrExemption(ExecutionEnvironment environment) {
        boolean exempted;
        try {
            exempted = this.checkIfExempted(environment);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }
        if (!exempted) {
            return this.recordFailureResult(environment);
        }
        // exempted so record that exemption instead of the failure
        PropositionResult result = null;
        BasicResult br = null;
        boolean recordSuccesses = environment.resolveTerm(RulesExecutionConstants.RECORD_INSTRUCTION_SUCCESSES_TERM, this);
        if (recordSuccesses) {
            Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
            ValidationResultInfo vr = new ValidationResultInfo();
            vr.setElement(instruction.getId());
            vr.setLevel(ValidationResult.ErrorLevel.OK);
            vr.setMessage("Failed but was exempted from check " + check.getName());
            executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
            executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
            result = new PropositionResult(true, executionDetails);
            br = new BasicResult(executionDetails, ResultEvent.PROPOSITION_EVALUATED, this,
                    environment, result.getResult());
        } else {
            result = new PropositionResult(true);
            br = new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, result.
                    getResult());
        }
        environment.getEngineResults().addResult(br);
        return result;
    }

    private PropositionResult recordFailureResult(ExecutionEnvironment environment) {
        // report error (or warning)
        ValidationResultInfo vr = new ValidationResultInfo();
        vr.setElement(instruction.getId());
        if (instruction.getIsWarning()) {
            vr.setLevel(ValidationResult.ErrorLevel.WARN);
        } else {
            vr.setLevel(ValidationResult.ErrorLevel.ERROR);
        }
        vr.setMessage(instruction.getMessage().getPlain());
        Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
        executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
        PropositionResult result = null;
        if (instruction.getIsWarning()) {
            result = new PropositionResult(true, executionDetails);
        } else {
            result = new PropositionResult(false, executionDetails);
        }
        BasicResult br = new BasicResult(executionDetails, ResultEvent.PROPOSITION_EVALUATED, this, environment, result.
                getResult());
        environment.getEngineResults().addResult(br);
        return result;
    }

    private boolean checkIfExempted(ExecutionEnvironment environment) throws Exception {
        ExemptionService exemptionService = environment.resolveTerm(RulesExecutionConstants.EXEMPTION_SERVICE_TERM, this);
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        String personId = environment.resolveTerm(RulesExecutionConstants.PERSON_ID_TERM, this);
        Date asOfDate = environment.resolveTerm(RulesExecutionConstants.AS_OF_DATE_TERM, this);
        List<ExemptionInfo> exemptions;
        exemptions = exemptionService.getActiveExemptionsByTypeProcessAndCheckForPerson(
                ExemptionServiceConstants.CHECK_EXEMPTION_TYPE_KEY,
                instruction.getProcessKey(),
                instruction.getCheckId(),
                personId,
                asOfDate,
                contextInfo);
        if (!exemptions.isEmpty()) {
            return true;
        }
        return false;
    }
}
