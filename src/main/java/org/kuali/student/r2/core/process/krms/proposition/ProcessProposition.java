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
package org.kuali.student.r2.core.process.krms.proposition;

import java.util.Date;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.r2.core.process.service.ProcessService;

/**
 * This proposition evaluates all the instructions associated with a process
 *
 * @author nwright
 */
public class ProcessProposition extends AbstractLeafProposition {

    private String processKey;

    public ProcessProposition() {
    }

    public ProcessProposition(String processKey) {
        this.processKey = processKey;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        String personId = environment.resolveTerm(RulesExecutionConstants.PERSON_ID_TERM, this);
        String atpId = null;
        try {
            atpId = environment.resolveTerm(RulesExecutionConstants.ATP_ID_TERM, this);
        } catch (TermResolutionException ex) {
            // do nothing atpId is optional
        }
        Date asOfDate = environment.resolveTerm(RulesExecutionConstants.AS_OF_DATE_TERM, this);
        Boolean recordSuccesses = environment.resolveTerm(RulesExecutionConstants.RECORD_INSTRUCTION_SUCCESSES_TERM, this);

        // get all the needed services from the execution context
        // we do this so we can locally cache some services and the cache lives just for the length of krms execution
        ProcessService processService = environment.resolveTerm(RulesExecutionConstants.PROCESS_SERVICE_TERM, this);
        PopulationService populationService = environment.resolveTerm(RulesExecutionConstants.POPULATION_SERVICE_TERM, this);
        AtpService atpService = environment.resolveTerm(RulesExecutionConstants.ATP_SERVICE_TERM, this);

        List<InstructionInfo> instructions;
        try {
            instructions = processService.getInstructionsForEvaluation(processKey, contextInfo);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }

        AtpInfo atp;
        try {
            atp = this.getAtpIfAtpIdIsNotNull(atpService, atpId, contextInfo);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }
        // filter out Instructions that do apply to the context
        int failureCount = 0;
        Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
        for (InstructionInfo instruction : instructions) {
            // filter out by term
            if (!this.matchesAtpType(atp, instruction, contextInfo)) {
                if (recordSuccesses) {
                    ValidationResultInfo vr = new ValidationResultInfo();
                    vr.setElement(instruction.getId());
                    vr.setLevel(ValidationResult.ErrorLevel.OK);
                    vr.setMessage("Skipped because did not match term type");
                    executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
                }
                continue;
            }
            try {
                // filter out by applicable Population
                if (!this.matchesPopulation(populationService, instruction, personId, asOfDate, contextInfo)) {
                    if (recordSuccesses) {
                        ValidationResultInfo vr = new ValidationResultInfo();
                        vr.setElement(instruction.getId());
                        vr.setLevel(ValidationResult.ErrorLevel.OK);
                        vr.setMessage("Skipped because did not match population");
                        executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
                    }
                    continue;
                }
            } catch (Exception ex) {
                return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
            }
            // create a proposition out of the instruction and execute it
            InstructionProposition proposition = new InstructionProposition(instruction);
            PropositionResult result = proposition.evaluate(environment);
            // if passed then get the next one
            if (result.getResult()) {
                continue;
            }
            // if failed because of an exception then that is a hard error and need to stop processing
            Exception exception = (Exception) result.getExecutionDetails().get(
                    RulesExecutionConstants.PROCESS_EVALUATION_EXCEPTION);
            if (exception != null) {
                return result;
            }
            failureCount++;
            // don't continue since this instruction failed
            if (!instruction.getContinueOnFail()) {
                break;
            }
        }
        PropositionResult result = null;
        if (failureCount == 0) {
            result = new PropositionResult(true, executionDetails);
        } else {
            result = new PropositionResult(false, executionDetails);
        }
        BasicResult br = new BasicResult(executionDetails, ResultEvent.PROPOSITION_EVALUATED,
                this,
                environment,
                result.getResult());
        environment.getEngineResults().addResult(br);
        return result;
    }

    private AtpInfo getAtpIfAtpIdIsNotNull(AtpService atpService, String atpId, ContextInfo contextInfo)
            throws Exception {
        if (atpId == null) {
            return null;
        }
        return atpService.getAtp(atpId, contextInfo);
    }

    private boolean matchesAtpType(AtpInfo atp, InstructionInfo instruction, ContextInfo contextInfo) {
        if (atp == null) {
            return true;
        }
        if (instruction.getAppliedAtpTypeKeys().isEmpty()) {
            return true;
        }
        if (instruction.getAppliedAtpTypeKeys().contains(atp.getTypeKey())) {
            return true;
        }
        return false;
    }

    private boolean matchesPopulation(PopulationService populationService,
            InstructionInfo instruction,
            String personId,
            Date asOfDate,
            ContextInfo contextInfo) throws Exception {
        if (instruction.getAppliedPopulationId() == null) {
            return true;
        }
        if (populationService.isMemberAsOfDate(personId,
                instruction.getAppliedPopulationId(),
                asOfDate,
                contextInfo)) {
            return true;
        }
        return false;
    }
}
