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
package org.kuali.student.r2.core.process.evaluator;

import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.framework.engine.CompoundProposition;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.constants.ExemptionServiceConstants;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.infc.DateOverride;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.process.context.ProcessContextInfo;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.krms.proposition.MilestoneDateComparisonProposition;
import org.kuali.student.r2.core.process.krms.proposition.MilestoneDateComparisonProposition.DateComparisonType;
import org.kuali.student.r2.core.process.krms.proposition.RegistrationHoldProposition;
import org.kuali.student.r2.core.process.krms.proposition.SubProcessProposition;
import org.kuali.student.r2.core.process.service.ProcessService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.process.krms.evaluator.KRMSEvaluator;
import org.kuali.student.r2.core.process.krms.evaluator.PropositionFactory;
import org.kuali.student.r2.core.process.krms.proposition.AlwaysFalseProposition;

/**
 * Utility class to build up a Proposition tree for to evaluate checks from a Process
 *
 * @author alubbers
 */
public class KRMSProcessEvaluator extends KRMSEvaluator implements ProcessEvaluator {

    public static final String EXEMPTION_WAS_USED_MESSAGE_SUFFIX = " (exemption applied)";
    private AtpService atpService;
    private ProcessService processService;
    private PopulationService populationService;
    private ExemptionService exemptionService;
    private PropositionFactory propositionFactory;

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public void setProcessService(ProcessService processService) {
        this.processService = processService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

    public void setExemptionService(ExemptionService exemptionService) {
        this.exemptionService = exemptionService;
    }

    public PropositionFactory getPropositionFactory() {
        return propositionFactory;
    }

    public void setPropositionFactory(PropositionFactory propositionFactory) {
        this.propositionFactory = propositionFactory;
    }

    @Override
    public List<ValidationResultInfo> evaluate(ProcessContextInfo processContextInfo, ContextInfo contextInfo)
            throws OperationFailedException {
        if (contextInfo.getCurrentDate() == null) {
            contextInfo.setCurrentDate(new Date());
        }
        List<InstructionInfo> instructions;
        try {
            instructions = processService.getInstructionsForEvaluation(processContextInfo.getProcessKey(), contextInfo);
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }

        AtpInfo atp = this.getAtp(processContextInfo, contextInfo);
        List<InstructionInfo> selectedInstructions = new ArrayList<InstructionInfo>();
        // filter out Instructions that do apply to the context
        for (InstructionInfo instruction : instructions) {
            // filter out by term
            if (!this.matchesAtpType(atp, instruction, contextInfo)) {
                continue;
            }
            // filter out by applicable Population
            if (!this.matchesPopulation(instruction, processContextInfo, contextInfo)) {
                continue;
            }
            selectedInstructions.add(instruction);
        }


        // build a list of propositions based on the sorted instructions, using a LinkedHashMap to maintain the same sorting order
        Map<Proposition, InstructionInfo> propositions = new LinkedHashMap<Proposition, InstructionInfo>(selectedInstructions.
                size());
        for (InstructionInfo instruction : selectedInstructions) {
            // check for any direct exemptions the student may have for this check
            List<ExemptionInfo> exemptions;
            try {
                exemptions = exemptionService.getActiveExemptionsByTypeProcessAndCheckForPerson(
                        ExemptionServiceConstants.CHECK_EXEMPTION_TYPE_KEY,
                        processContextInfo.getProcessKey(),
                        instruction.getCheckId(),
                        processContextInfo.getPersonId(),
                        contextInfo.getCurrentDate(),
                        contextInfo);
            } catch (OperationFailedException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new OperationFailedException("unexpected", ex);
            }
            if (!exemptions.isEmpty()) {
                continue;
            }

            CheckInfo check;
            try {
                check = processService.getCheck(instruction.getCheckId(), contextInfo);
            } catch (OperationFailedException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new OperationFailedException("unexpected", ex);
            }

            // TODO: when we can use java7 change to a switch  statement based on the type
            // if a sub-process is found,
            if (check.getTypeKey().equals(ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY)) {
                ProcessContextInfo subProcessContext = new ProcessContextInfo(processContextInfo);
                subProcessContext.setProcessKey(check.getChildProcessKey());
                propositions.put(new SubProcessProposition(subProcessContext, this), instruction);
                continue;
            }
            // always false
            if (check.getTypeKey().equals(ProcessServiceConstants.ALWAYS_FALSE_CHECK_TYPE_KEY)) {
                propositions.put(new AlwaysFalseProposition(), instruction);
                continue;
            }
            // hold check
            if (check.getTypeKey().equals(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY)) {
                propositions.put(new RegistrationHoldProposition(check.getHoldIssueId()), instruction);
                continue;
            }
            // direct rule get the proposition for that rule
            if (check.getTypeKey().equals(ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY)) {
                try {
                    propositions.put(this.getPropositionFactory().getProposition(check.getRuleId(), contextInfo), instruction);
                    continue;
                } catch (DoesNotExistException ex) {
                    throw new OperationFailedException("unexpected", ex);
                }
            }
            // start date check use the milestone proposition configured with after
            if (check.getTypeKey().equals(ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY)) {
                propositions.put(buildMilestoneCheckProposition(check, DateComparisonType.AFTER, processContextInfo, contextInfo),
                        instruction);
                continue;
            }
            // deadline check use the milestone proposition with the before
            if (check.getTypeKey().equals(ProcessServiceConstants.DEADLINE_CHECK_TYPE_KEY)) {
                propositions.
                        put(buildMilestoneCheckProposition(check, DateComparisonType.BEFORE, processContextInfo, contextInfo),
                        instruction);
                continue;
            }
            // range check use milestone with between 
            if (check.getTypeKey().equals(ProcessServiceConstants.TIME_PERIOD_CHECK_TYPE_KEY)) {
                propositions.put(
                        buildMilestoneCheckProposition(check, DateComparisonType.BETWEEN, processContextInfo, contextInfo),
                        instruction);
                continue;
            }
            throw new OperationFailedException("unknown/unsupported check type" + check.getTypeKey() + " check=" + check);
        }
        
        // if all instructions are skipped, and no propositions were generated, return one "success" message
        if (propositions.isEmpty()) {
            ValidationResultInfo success = new ValidationResultInfo();
            success.setLevel(ValidationResult.ErrorLevel.OK);
            return Collections.singletonList(success);
        }

        // Build the list of known facts prior to execution
        Map<String, Object> executionFacts = buildExecutionFacts(processContextInfo, contextInfo);

        // Combine all propositions into a CompoundProposition and evaluate the results
        EngineResults engineResults = evaluateProposition(new CompoundProposition(LogicalOperator.AND, new ArrayList<Proposition>(
                propositions.keySet())), executionFacts);


        List<ValidationResultInfo> results;
        try {
            results = buildValidationResultsFromEngineResults(engineResults, propositions, contextInfo);
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }

        return results;
    }

    private AtpInfo getAtp(ProcessContextInfo processContextInfo, ContextInfo contextInfo)
            throws OperationFailedException {
        if (processContextInfo.getAtpId() == null) {
            return null;
        }
        try {
            return atpService.getAtp(processContextInfo.getAtpId(), contextInfo);
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }
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

    private boolean matchesPopulation(InstructionInfo instruction, ProcessContextInfo processContextInfo,
            ContextInfo contextInfo) throws OperationFailedException {
        if (instruction.getAppliedPopulationId() == null) {
            return true;
        }
        try {
            if (populationService.isMemberAsOfDate(processContextInfo.getPersonId(), instruction.getAppliedPopulationId(),
                    contextInfo.getCurrentDate(), contextInfo)) {
                return true;
            }
            return false;
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    private MilestoneDateComparisonProposition buildMilestoneCheckProposition(CheckInfo check, DateComparisonType comparisonType,
            ProcessContextInfo processContext, ContextInfo context)
            throws OperationFailedException {
        List<ExemptionInfo> exemptions;
        Date asOfDate = context.getCurrentDate();
        if (asOfDate == null) {
            asOfDate = new Date();
        }
        try {
            exemptions = exemptionService.getActiveExemptionsByTypeProcessAndCheckForPerson(
                    ExemptionServiceConstants.MILESTONE_DATE_EXEMPTION_TYPE_KEY,
                    processContext.getProcessKey(),
                    check.getId(),
                    processContext.getPersonId(),
                    asOfDate,
                    context);
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }

        if (exemptions.isEmpty()) {
            return new MilestoneDateComparisonProposition(RulesExecutionConstants.CURRENT_DATE_TERM_NAME, comparisonType, check.
                    getMilestoneTypeKey(), processContext.getAtpId(), true, null);
        }

        // For now, assume there is only one active Exemption
        //Code Changed for JIRA-9075 - SONAR Critical issues - Use get(0) with caution - 5
        int firstExemptionInfo = 0;
        DateOverride dateOverrideInfo = exemptions.get(firstExemptionInfo).getDateOverride();

        return new MilestoneDateComparisonProposition(RulesExecutionConstants.CURRENT_DATE_TERM_NAME, comparisonType, check.
                getMilestoneTypeKey(), processContext.getAtpId(), true, dateOverrideInfo);
    }

    private Map<String, Object> buildExecutionFacts(ProcessContextInfo processContext, ContextInfo context) {
        Map<String, Object> executionFacts = new HashMap<String, Object>();
        executionFacts.put(RulesExecutionConstants.PERSON_ID_TERM_NAME, processContext.getPersonId());
        executionFacts.put(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME, context);
        if (processContext.getAtpId() != null) {
            executionFacts.put(RulesExecutionConstants.REGISTRATION_TERM_TERM_NAME, processContext.getAtpId());
        }
        return executionFacts;
    }
}
