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
package org.kuali.student.core.process.evaluator;

import org.joda.time.DateTime;
import org.kuali.rice.krms.api.engine.Engine;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ExecutionFlag;
import org.kuali.rice.krms.api.engine.ExecutionOptions;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.SelectionCriteria;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.framework.engine.Action;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.AgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicAgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicContext;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.engine.Context;
import org.kuali.rice.krms.framework.engine.ContextProvider;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.ProviderBasedEngine;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.ManualContextProvider;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to build up a Proposition tree for to evaluate checks from a Process
 *
 * @author alubbers
 */
public class KRMSEvaluator {

    private Map<String, Object> defaultFacts;
    private List<TermResolver<?>> termResolvers;
    private ExecutionOptions executionOptions;
    private Map<String, String> contextQualifiers;
    private static final Logger LOGGER = LoggerFactory.getLogger(KRMSEvaluator.class);

    public Map<String, Object> getDefaultFacts() {
        return defaultFacts;
    }

    public void setDefaultFacts(
            Map<String, Object> defaultFacts) {
        this.defaultFacts = defaultFacts;
    }

    public List<TermResolver<?>> getTermResolvers() {
        return termResolvers;
    }

    public void setTermResolvers(List<TermResolver<?>> termResolvers) {
        this.termResolvers = termResolvers;
    }

    public ExecutionOptions getExecutionOptions() {

        if (executionOptions == null) {
            executionOptions = new ExecutionOptions();
            executionOptions.setFlag(ExecutionFlag.LOG_EXECUTION, true);
            executionOptions.setFlag(ExecutionFlag.EVALUATE_ALL_PROPOSITIONS, true);
        }

        return executionOptions;
    }

    public Map<String, String> getContextQualifiers() {
        if(contextQualifiers==null){
            contextQualifiers = Collections.singletonMap(RulesExecutionConstants.DOCTYPE_CONTEXT_QUALIFIER,
                    RulesExecutionConstants.STUDENT_ELIGIBILITY_DOCTYPE);
        }
        return contextQualifiers;
    }

    public void setContextQualifiers(Map<String, String> contextQualifiers) {
        this.contextQualifiers = contextQualifiers;
    }

    public void setExecutionOptions(ExecutionOptions executionOptions) {
        this.executionOptions = executionOptions;
    }

    /**
     * execute the engine against a trivial context containing the given proposition.
     *
     * @param proposition
     * @param additionalFacts
     */
    public EngineResults evaluateProposition(Proposition proposition, Map<String, Object> additionalFacts) {
        Map<String, String> agendaQualifiers = Collections.emptyMap();
        return evaluateProposition(proposition, additionalFacts, agendaQualifiers);
    }

    /**
     * execute the engine against a trivial context containing the given proposition.
     *
     * @param proposition
     * @param additionalFacts
     * @param agendaQualifiers
     */
    public EngineResults evaluateProposition(Proposition proposition, Map<String, Object> additionalFacts,
            Map<String, String> agendaQualifiers) {

        Agenda agenda = constructAgenda(proposition);
        return evaluateAgenda(agenda, additionalFacts, agendaQualifiers);
    }

    public Agenda constructAgenda(Proposition proposition) {
        // Build the KRMS agenda and other startup objects to execute
        List<AgendaTreeEntry> treeEntries = new ArrayList<AgendaTreeEntry>(1);
        List<Action> actions = null;
        treeEntries.add(new BasicAgendaTreeEntry(new BasicRule(proposition, actions)));
        Map<String, String> qualifiers = Collections.emptyMap();
        Agenda agenda = new BasicAgenda(qualifiers, new BasicAgendaTree(treeEntries));
        return agenda;
    }

    public Engine constructEngine(Agenda agenda, List<TermResolver<?>> termResolvers) {
        Context context = new BasicContext(Arrays.asList(agenda), termResolvers);
        ContextProvider contextProvider = new ManualContextProvider(context);
        ProviderBasedEngine engine = new ProviderBasedEngine();
        engine.setContextProvider(contextProvider);
        return engine;
    }

    public Engine constructEngine(Proposition proposition, List<TermResolver<?>> termResolvers) {
        Agenda agenda = constructAgenda(proposition);
        Context context = new BasicContext(Arrays.asList(agenda), termResolvers);
        ContextProvider contextProvider = new ManualContextProvider(context);
        ProviderBasedEngine engine = new ProviderBasedEngine();
        engine.setContextProvider(contextProvider);
        return engine;
    }

    /**
     * execute the engine against a trivial context containing the given proposition.
     *
     * @param agenda
     * @param additionalFacts
     * @return
     */
    public EngineResults evaluateAgenda(Agenda agenda, Map<String, Object> additionalFacts, Map<String, String> agendaQualifiers) {

        Engine engine = constructEngine(agenda, this.getTermResolvers());

        SelectionCriteria selectionCriteria = SelectionCriteria.
                createCriteria(new DateTime(), this.getContextQualifiers(), agendaQualifiers);

        Map<String, Object> executionFacts = new HashMap<String, Object>();
        executionFacts.putAll(this.getDefaultFacts());
        executionFacts.putAll(additionalFacts);
        return engine.execute(selectionCriteria, executionFacts, this.getExecutionOptions());
    }

    public static Exception checkForExceptionDuringExecution(EngineResults engineResults) {
        List<ResultEvent> events = engineResults.getResultsOfType(ResultEvent.PROPOSITION_EVALUATED);
        for (ResultEvent e : events) {
            if (!e.getResult()) {
                Map<String, Object> details = (Map<String, Object>) e.getResultDetails();
                Exception ex = (Exception) details.get(RulesExecutionConstants.PROCESS_EVALUATION_EXCEPTION);
                if (ex != null) {
                    return ex;
                }
            }
        }
        return null;
    }

    public static List<ValidationResultInfo> extractValidationResults(EngineResults engineResults) {
        List<ValidationResultInfo> list = new ArrayList<ValidationResultInfo>();
        List<ResultEvent> events = engineResults.getResultsOfType(ResultEvent.PROPOSITION_EVALUATED);
        for (ResultEvent e : events) {
            Map<String, Object> details = (Map<String, Object>) e.getResultDetails();
            ValidationResultInfo vr = (ValidationResultInfo) details.get(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS);
            if (vr != null) {
                list.add(vr);
            }
        }
        return list;
    }
    
    
    /**
     * Utility to construct a proposition result from an exception
     * @param environment
     * @param ex
     * @return 
     */
    public static PropositionResult constructExceptionPropositionResult(ExecutionEnvironment environment, Exception ex, Object source) {
        LOGGER.warn("Exception while executing the Process Rules.", ex);
        Map<String, Object> executionDetails = new HashMap<String, Object>();
        // on an evaluation exception, report the details of the exception to the KRMS environment
        executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_EXCEPTION, ex);
        PropositionResult propositionResult = new PropositionResult(false, executionDetails);
        BasicResult br = new BasicResult(executionDetails, ResultEvent.PROPOSITION_EVALUATED, source, environment, false);
        environment.getEngineResults().addResult(br);
        return propositionResult;
    }

    public static PropositionResult buildPropositionResult(ExecutionEnvironment environment, boolean result, Object source) {
        PropositionResult propositionResult = new PropositionResult(result);
        BasicResult basicResult = new BasicResult(ResultEvent.PROPOSITION_EVALUATED, source, environment, propositionResult.getResult());
        environment.getEngineResults().addResult(basicResult);
        return propositionResult;
    }

    public static PropositionResult buildPropositionResult(ExecutionEnvironment environment, ValidationResult validationResult, boolean result, Object source) {
        Map<String, Object> executionDetails = new LinkedHashMap<>();
        executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, validationResult);
        PropositionResult propositionResult = new PropositionResult(result, executionDetails);

        BasicResult basicResult = new BasicResult(executionDetails, ResultEvent.PROPOSITION_EVALUATED, source, environment, propositionResult.getResult());
        environment.getEngineResults().addResult(basicResult);
        return propositionResult;
    }
}
