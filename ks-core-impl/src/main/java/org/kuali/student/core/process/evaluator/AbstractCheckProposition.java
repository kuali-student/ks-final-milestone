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

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.rice.krms.impl.repository.language.VelocityTemplateEngine;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.constants.ExemptionServiceConstants;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base class for any check proposition
 *
 * @author nwright
 */
public abstract class AbstractCheckProposition extends AbstractLeafProposition {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCheckProposition.class);

    protected InstructionInfo instruction;
    protected CheckInfo check;
    private VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();

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
            Map<String, Object> executionDetails = new LinkedHashMap<>();
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
        PropositionResult result;
        BasicResult br;
        Boolean recordSuccesses = environment.resolveTerm(RulesExecutionConstants.RECORD_INSTRUCTION_SUCCESSES_TERM, this);
        if (recordSuccesses) {
            Map<String, Object> executionDetails = new LinkedHashMap<>();
            ValidationResultInfo vr = new ValidationResultInfo();
            vr.setElement(instruction.getId());
            vr.setLevel(ValidationResult.ErrorLevel.OK);
            vr.setMessage("Failed but was exempted from check " + check.getName());
            executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
//            executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
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
        Map<String, Object> executionContext = new HashMap<>();
        for (Map.Entry<Term, Object> factEntry:environment.getFacts().entrySet()) {
            executionContext.put(factEntry.getKey().getName(), factEntry.getValue());
        }

        String messageData = formatMessageData(environment, executionContext, instruction.getMessage());

        ValidationResultInfo vr = new ValidationResultInfo();
        vr.setElement("registrationRequestItems['" + executionContext.get("registrationRequestItemId") + "']");
        if (instruction.getIsWarning()) {
            vr.setLevel(ValidationResult.ErrorLevel.WARN);
        } else {
            vr.setLevel(ValidationResult.ErrorLevel.ERROR);
        }
        vr.setMessage("{\"instructionId\":\"" + instruction.getId() + "\",\"checkId\":\"" + instruction.getCheckId() + "\"," + messageData + "}");
        Map<String, Object> executionDetails = new LinkedHashMap<>();
        executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
        PropositionResult result;
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
        return !exemptions.isEmpty();
    }

    private String formatMessageData(ExecutionEnvironment environment, Map<String, Object> executionContext, RichTextInfo message) {
        String messageData;
        try {
            List<String> messageVariables = new ArrayList<>();
            Matcher m = Pattern.compile("\\$([a-zA-Z]+)").matcher(message.getFormatted());
            while (m.find()) {
                messageVariables.add(m.group(1));
            }

            for (String messageVariable:messageVariables) {
                //check to see if the variable is already in the execution context
                if (executionContext.get(messageVariable) == null) {
                    //not found, resolve the term and add it
                    Object resolved = environment.resolveTerm(new Term(messageVariable), this);
                    if (resolved != null) {
                        executionContext.put(messageVariable, resolved);
                    } else {
                        LOGGER.warn("Unable to resolve message variable: ${}");
                    }
                }
            }
            messageData = templateEngine.evaluate(executionContext, message.getFormatted());
        } catch (Exception e) {
            messageData = "\"message\":\"" + message.getPlain() + "\"";
        }
        return messageData;
    }
}
