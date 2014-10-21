/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.r2.core.process.krms;

import org.joda.time.DateTime;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.ExecutionFlag;
import org.kuali.rice.krms.api.engine.ExecutionOptions;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.SelectionCriteria;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.AgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicAgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicContext;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.engine.CompoundProposition;
import org.kuali.rice.krms.framework.engine.Context;
import org.kuali.rice.krms.framework.engine.ContextProvider;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.ProviderBasedEngine;
import org.kuali.student.common.util.krms.ManualContextProvider;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.constants.ExemptionServiceConstants;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.infc.DateOverride;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.process.context.CourseRegistrationProcessContextInfo;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.evaluator.ProcessEvaluator;
import org.kuali.student.r2.core.process.krms.evaluator.KRMSEvaluator;
import org.kuali.student.r2.core.process.krms.proposition.ExemptionAwareProposition;
import org.kuali.student.r2.core.process.krms.proposition.MilestoneDateComparisonProposition;
import org.kuali.student.r2.core.process.krms.proposition.MilestoneDateComparisonProposition.DateComparisonType;
import org.kuali.student.r2.core.process.krms.proposition.PersonLivingProposition;
import org.kuali.student.r2.core.process.krms.proposition.RegistrationHoldProposition;
import org.kuali.student.r2.core.process.krms.proposition.SubProcessProposition;
import org.kuali.student.r2.core.process.krms.proposition.SummerTermProposition;
import org.kuali.student.r2.core.process.service.ProcessService;
import org.kuali.student.r2.core.process.util.InstructionComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to build up a Proposition tree for to evaluate checks from a Process
 *
 * @author alubbers
 */
public class KRMSProcessEvaluator extends KRMSEvaluator implements ProcessEvaluator<CourseRegistrationProcessContextInfo> {

    public static final String EXEMPTION_WAS_USED_MESSAGE_SUFFIX = " (exemption applied)";

    private AcademicCalendarService acalService;
    private ProcessService processService;
    private PopulationService populationService;
    private ExemptionService exemptionService;

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
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

    @Override
    public List<ValidationResultInfo> evaluate(CourseRegistrationProcessContextInfo processContext, ContextInfo context)
            throws OperationFailedException {

        List<InstructionInfo> instructions;
        try {
            instructions = processService.getInstructionsByProcess(processContext.getProcessKey(), context);
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }

        TermInfo term = null;

        if (processContext.getTermKey() != null) {
            try {
                term = acalService.getTerm(processContext.getTermKey(), context);
            } catch (OperationFailedException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new OperationFailedException("unexpected", ex);
            }
        }

        List<InstructionInfo> sortedInstructions = new ArrayList<InstructionInfo>();

        // filter out Instructions that do apply to the context
        for (InstructionInfo instruction : instructions) {

            // filter out by term
            if (term != null && !instruction.getAppliedAtpTypeKeys().isEmpty() && !instruction.getAppliedAtpTypeKeys().contains(term.getTypeKey())) {
                continue;
            }

            // filter out by applicable Population
            boolean skipInstruction = true;
            try {
                if (populationService.isMemberAsOfDate(processContext.getStudentId(), instruction.getAppliedPopulationId(), context.getCurrentDate(), context)) {
                    skipInstruction = false;
                    break;
                }
            } catch (OperationFailedException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new OperationFailedException("unexpected", ex);
            }
            /*
            for (String popKey : instruction.getAppliedPopulationKeys()) {
                try {
                    if (populationService.isMember(processContext.getStudentId(), popKey, context)) {
                        skipInstruction = false;
                        break;
                    }
                } catch (OperationFailedException ex) {
                    throw ex;
                } catch (Exception ex) {
                    throw new OperationFailedException("unexpected", ex);
                }
            }
            */

            Date asOfDate = context.getCurrentDate();
            if (asOfDate == null) {
                asOfDate = new Date ();
            }
            // check for any direct exemptions the student may have for this check
            List<ExemptionInfo> exemptions;
            try {
                exemptions = exemptionService.getActiveExemptionsByTypeProcessAndCheckForPerson(ExemptionServiceConstants.CHECK_EXEMPTION_TYPE_KEY, 
                        processContext.getProcessKey(),
                        instruction.getCheckId(),
                        processContext.getStudentId(), 
                        asOfDate, 
                        context);
            } catch (OperationFailedException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new OperationFailedException("unexpected", ex);
            }
            if (!exemptions.isEmpty()) {
                skipInstruction = true;
            }

            if (skipInstruction) {
                continue;
            }

            sortedInstructions.add(instruction);
        }

        Collections.sort(sortedInstructions, new InstructionComparator());

        // build a list of propositions based on the sorted instructions, using a LinkedHashMap to maintain the same sorting order
        Map<Proposition, InstructionInfo> propositions = new LinkedHashMap<Proposition, InstructionInfo>(sortedInstructions.size());
        for (InstructionInfo instruction : sortedInstructions) {

            CheckInfo check;
            try {
                check = processService.getCheck(instruction.getCheckId(), context);
            } catch (OperationFailedException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new OperationFailedException("unexpected", ex);
            }

            // if a sub-process is found,
            if (check.getTypeKey().equals(ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY)) {

                CourseRegistrationProcessContextInfo checkContext = CourseRegistrationProcessContextInfo.createForRegistrationEligibility(processContext.getStudentId(), processContext.getTermKey());
                checkContext.setProcessKey(check.getChildProcessKey());

                propositions.put(new SubProcessProposition(checkContext, this), instruction);
            }

            if (check.getTypeKey().equals(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY)) {
                propositions.put(new RegistrationHoldProposition(check.getHoldIssueId()), instruction);

                
                /*
                  need to handle these differently
                  
                  } else if (check.getKey().equals(ProcessServiceConstants.CHECK_ID_IS_ALIVE)) {
                  propositions.put(new PersonLivingProposition(), instruction);
                  } else if (check.getKey().equals(ProcessServiceConstants.CHECK_ID_IS_NOT_SUMMER_TERM)) {
                  propositions.put(new SummerTermProposition(term), instruction); 

                */

            } else if (check.getTypeKey().equals(ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY)) {
                propositions.put(buildMilestoneCheckProposition(check, DateComparisonType.AFTER, processContext, context), instruction);
            } else if (check.getTypeKey().equals(ProcessServiceConstants.DEADLINE_CHECK_TYPE_KEY)) {
                propositions.put(buildMilestoneCheckProposition(check, DateComparisonType.BEFORE, processContext, context), instruction);
            } else if (check.getTypeKey().equals(ProcessServiceConstants.TIME_PERIOD_CHECK_TYPE_KEY)) {
                propositions.put(buildMilestoneCheckProposition(check, DateComparisonType.BETWEEN, processContext, context), instruction);
            }
        }

        // if all instructions are skipped, and no propositions were generated, return one "success" message
        if(propositions.isEmpty()) {
            ValidationResultInfo success = new ValidationResultInfo();
            success.setLevel(ValidationResult.ErrorLevel.OK);
            return Collections.singletonList(success);
        }

        // Build the list of known facts prior to execution
        Map<String, Object> executionFacts = buildExecutionFacts(processContext, context);

        // Combine all propositions into a CompoundProposition and evaluate the results
        EngineResults krmsResults = evaluateProposition(new CompoundProposition(LogicalOperator.AND, new ArrayList<Proposition>(propositions.keySet())), executionFacts);


        List<ValidationResultInfo> results;
        try {
            results = buildValidationResultsFromEngineResults(krmsResults, propositions, context);   
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }

        return results;
    }

    private MilestoneDateComparisonProposition buildMilestoneCheckProposition(CheckInfo check, DateComparisonType comparisonType, CourseRegistrationProcessContextInfo processContext, ContextInfo context)
            throws OperationFailedException {
        List<ExemptionInfo> exemptions;
        Date asOfDate = context.getCurrentDate();
            if (asOfDate == null) {
                asOfDate = new Date ();
            }
        try {
            exemptions = exemptionService.getActiveExemptionsByTypeProcessAndCheckForPerson(ExemptionServiceConstants.MILESTONE_DATE_EXEMPTION_TYPE_KEY, 
                    processContext.getProcessKey(), 
                    check.getId(), 
                    processContext.getStudentId(),
                    asOfDate,
                    context);
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }

        if (exemptions.isEmpty()) {
            return new MilestoneDateComparisonProposition(RulesExecutionConstants.AS_OF_DATE_TERM.getName(), comparisonType, check.getMilestoneTypeKey(), processContext.getTermKey(), true, null);
        }

        // For now, assume there is only one active Exemption
        //Code Changed for JIRA-9075 - SONAR Critical issues - Use get(0) with caution - 5
        int firstExemptionInfo = 0;
        DateOverride dateOverrideInfo = exemptions.get(firstExemptionInfo).getDateOverride();

        return new MilestoneDateComparisonProposition(RulesExecutionConstants.AS_OF_DATE_TERM.getName(), comparisonType, check.getMilestoneTypeKey(), processContext.getTermKey(), true, dateOverrideInfo);
    }

    public List<ValidationResultInfo> evaluateStudentAliveRule(InstructionInfo instruction, CourseRegistrationProcessContextInfo processContext, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();

        // Build the list of known facts prior to execution
        Map<String, Object> executionFacts = buildExecutionFacts(processContext, context);

        // evaluate the results for just the one proposition
        Proposition prop = new PersonLivingProposition();
        EngineResults engineResults = evaluateProposition(prop, executionFacts);

        Map<Proposition, InstructionInfo> propInstructionMap = Collections.singletonMap(prop, instruction);

        return buildValidationResultsFromEngineResults(engineResults, propInstructionMap, context);
    }

    public List<ValidationResultInfo> evaluateSummerTermRule(InstructionInfo instruction, CourseRegistrationProcessContextInfo processContext, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();

        TermInfo term = acalService.getTerm(processContext.getTermKey(), context);

        // Build the list of known facts prior to execution
        Map<String, Object> executionFacts = buildExecutionFacts(processContext, context);

        // evaluate the results for just the one proposition
        Proposition prop = new SummerTermProposition(term);
        EngineResults engineResults = evaluateProposition(prop, executionFacts);

        Map<Proposition, InstructionInfo> propInstructionMap = Collections.singletonMap(prop, instruction);

        return buildValidationResultsFromEngineResults(engineResults, propInstructionMap, context);
    }

    private Map<String, Object> buildExecutionFacts(CourseRegistrationProcessContextInfo processContext, ContextInfo context) {
        Map<String, Object> executionFacts = new HashMap<String, Object>();
        executionFacts.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), processContext.getStudentId());
        executionFacts.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), context);
        if (processContext.getTermKey() != null) {
            executionFacts.put(RulesExecutionConstants.REGISTRATION_TERM_TERM.getName(), processContext.getTermKey());
        }
        return executionFacts;
    }

}
