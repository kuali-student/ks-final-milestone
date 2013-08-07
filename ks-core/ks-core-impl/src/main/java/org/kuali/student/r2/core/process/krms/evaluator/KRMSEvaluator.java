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
package org.kuali.student.r2.core.process.krms.evaluator;

import org.joda.time.DateTime;
import org.kuali.rice.krms.api.engine.*;
import org.kuali.rice.krms.framework.engine.*;
import org.kuali.student.common.util.krms.ManualContextProvider;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.krms.proposition.ExemptionAwareProposition;
import org.kuali.student.r2.core.process.krms.proposition.SubProcessProposition;
import org.kuali.student.r2.core.process.service.ProcessService;

import java.util.*;

/**
 * Utility class to build up a Proposition tree for to evaluate checks from a Process
 *
 * @author alubbers
 */
public abstract class KRMSEvaluator {

    public static final String EXEMPTION_WAS_USED_MESSAGE_SUFFIX = " (exemption applied)";

    private HoldService holdService;
    private List<TermResolver<?>> termResolvers;
    private ExecutionOptions executionOptions;

    private Map<String, String> contextQualifiers = Collections.singletonMap(RulesExecutionConstants.DOCTYPE_CONTEXT_QUALIFIER, RulesExecutionConstants.STUDENT_ELIGIBILITY_DOCTYPE);

    public void setHoldService(HoldService holdService) {
        this.holdService = holdService;
    }

    /**
     * execute the engine against a trivial context containing the given proposition.
     *
     * @param proposition
     * @param executionFacts
     */
    public EngineResults evaluateProposition(Proposition proposition, Map<String, Object> executionFacts) {
        Map<String, String> empty = Collections.emptyMap();
        return evaluateProposition(proposition, executionFacts, empty);
    }

    /**
     * execute the engine against a trivial context containing the given proposition.
     *
     * @param proposition
     * @param executionFacts
     */
    public EngineResults evaluateProposition(Proposition proposition, Map<String, Object> executionFacts, Map<String, String> agendaQualifiers) {

        // Build the KRMS agenda and other startup objects to execute
        List<AgendaTreeEntry> treeEntries = new ArrayList<AgendaTreeEntry>(1);
        treeEntries.add(new BasicAgendaTreeEntry(new BasicRule(proposition, null)));

        Map<String, String> qualifiers = Collections.emptyMap();
        Agenda agenda = new BasicAgenda(qualifiers, new BasicAgendaTree(treeEntries));

        return evaluateAgenda(agenda, executionFacts, agendaQualifiers);
    }

    /**
     * execute the engine against a trivial context containing the given proposition.
     *
     * @param agenda
     * @param executionFacts
     * @return
     */
    public EngineResults evaluateAgenda(Agenda agenda, Map<String, Object> executionFacts, Map<String, String> agendaQualifiers) {

        Context context = new BasicContext(Arrays.asList(agenda), termResolvers);
        ContextProvider contextProvider = new ManualContextProvider(context);

        ProviderBasedEngine engine = new ProviderBasedEngine();
        engine.setContextProvider(contextProvider);

        if (executionOptions == null) {
            executionOptions = new ExecutionOptions();
            executionOptions.setFlag(ExecutionFlag.LOG_EXECUTION, true);
            executionOptions.setFlag(ExecutionFlag.EVALUATE_ALL_PROPOSITIONS, true);
        }

        SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(new DateTime(), contextQualifiers, agendaQualifiers);

        return engine.execute(selectionCriteria, executionFacts, executionOptions);
    }

    public List<ValidationResultInfo> buildValidationResultsFromEngineResults(EngineResults engineResults, Map<Proposition, InstructionInfo> propositionInstructionMap, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();

        // go through all the results from the Propositions, and build validation results based on any propositions that failed
        List<ResultEvent> events = engineResults.getResultsOfType(ResultEvent.PROPOSITION_EVALUATED);
        for (ResultEvent e : events) {
            Proposition prop = (Proposition) e.getSource();
            InstructionInfo instruction = propositionInstructionMap.get(prop);
            ValidationResultInfo result = new ValidationResultInfo();
            String message = instruction.getMessage().getPlain();
            ExemptionAwareProposition exemptionProp = null;

            if (prop instanceof SubProcessProposition) {
                List<ValidationResultInfo> subResults = (List<ValidationResultInfo>) e.getResultDetails().get(RulesExecutionConstants.SUBPROCESS_EVALUATION_RESULTS);
                results.addAll(subResults);
            } else {
                // if the proposition is could have an exemption, check for the exemption and add a suffix to the message
                if (prop instanceof ExemptionAwareProposition) {
                    exemptionProp = (ExemptionAwareProposition) prop;
                    if (exemptionProp.isExemptionUsed()) {
                        message += EXEMPTION_WAS_USED_MESSAGE_SUFFIX;
                    }
                }
                if (e.getResult()) {
                    result.setLevel(ValidationResult.ErrorLevel.OK);
                    // add a message to an OK result only if an exemption was used
                    if (exemptionProp != null && exemptionProp.isExemptionUsed()) {
                        result.setMessage(message);
                    }
                } else {

                    if (instruction.getIsWarning()) {
                        result.setWarn(message);
                    } else {
                        result.setError(message);
                    }
                }

                results.add(result);
            }

            if (!e.getResult() && !instruction.getContinueOnFail()) {
                break;
            }
        }

        // Now check if there are any warnings from Holds that are marked as warning only
        List<String> warningHoldIds = (List<String>) engineResults.getAttribute(RulesExecutionConstants.REGISTRATION_HOLD_WARNINGS_ATTRIBUTE);

        if (warningHoldIds != null && !warningHoldIds.isEmpty()) {
            for (String holdId : warningHoldIds) {
                AppliedHoldInfo hold = holdService.getAppliedHold(holdId, context);
                ValidationResultInfo result = new ValidationResultInfo();
                result.setWarn("The following hold was found on the student's account, but set as a warning: " + hold.getDescr().getPlain());
                results.add(result);
            }
        }

        return results;
    }

    public void setTermResolvers(List<TermResolver<?>> termResolvers) {
        this.termResolvers = termResolvers;
    }

    public void setExecutionOptions(ExecutionOptions executionOptions) {
        this.executionOptions = executionOptions;
    }
}
