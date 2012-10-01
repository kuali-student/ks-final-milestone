package org.kuali.student.r2.core.statement.util;

import org.joda.time.DateTime;
import org.kuali.rice.krms.api.engine.Engine;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.ExecutionFlag;
import org.kuali.rice.krms.api.engine.ExecutionOptions;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.SelectionCriteria;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.BasicContext;
import org.kuali.rice.krms.framework.engine.Context;
import org.kuali.rice.krms.framework.engine.ContextProvider;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.ProviderBasedEngine;
import org.kuali.student.common.util.krms.ManualContextProvider;
import org.kuali.student.r2.core.statement.dto.ReqComponentInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alubbers
 *
 * This class contains utility methods to evaluate agendas using the KRMS engine
 *
 */
public class RulesEvaluationUtil {

    private List<TermResolver<?>> termResolvers;
    private ExecutionOptions executionOptions;
    private Map<String, String> contextQualifiers;
    private SelectionCriteria selectionCriteria;

    public List<TermResolver<?>> getTermResolvers() {
        return termResolvers;
    }

    public void setTermResolvers(List<TermResolver<?>> termResolvers) {
        this.termResolvers = termResolvers;
    }

    public ExecutionOptions getExecutionOptions() {
        return executionOptions;
    }

    public void setExecutionOptions(ExecutionOptions executionOptions) {
        this.executionOptions = executionOptions;
    }

    public Map<String, String> getContextQualifiers() {
        return contextQualifiers;
    }

    public void setContextQualifiers(Map<String, String> contextQualifiers) {
        this.contextQualifiers = contextQualifiers;
    }

    public SelectionCriteria getSelectionCriteria() {
        return selectionCriteria;
    }

    public void setSelectionCriteria(SelectionCriteria selectionCriteria) {
        this.selectionCriteria = selectionCriteria;
    }

    public RulesEvaluationUtil() {
        executionOptions = new ExecutionOptions();
        executionOptions.setFlag(ExecutionFlag.LOG_EXECUTION, true);
        executionOptions.setFlag(ExecutionFlag.EVALUATE_ALL_PROPOSITIONS, true);

        contextQualifiers = new HashMap<String, String>();

        contextQualifiers.put("docTypeName", "Course.PreRequisities");

        Map<String, String> empty = Collections.emptyMap();
        selectionCriteria = SelectionCriteria.createCriteria(new DateTime(), contextQualifiers, empty);
    }

    private ProviderBasedEngine buildEngine(Agenda agenda) {
        Context context = new BasicContext(Arrays.asList(agenda), termResolvers);
        ContextProvider contextProvider = new ManualContextProvider(context);

        ProviderBasedEngine engine = new ProviderBasedEngine();
        engine.setContextProvider(contextProvider);

        return engine;
    }

    public EngineResults executeAgenda(Agenda agenda, Map<String, Object> executionFacts) {

        Engine engine = buildEngine(agenda);
        EngineResults results = engine.execute(selectionCriteria, executionFacts, executionOptions);

        return results;
    }

    public List<ReqComponentInfo> getFailedRequirementsFromEngineResults(EngineResults results, Map<Proposition, ReqComponentInfo> reqComponentPropositionMap) {
        List<ReqComponentInfo> failedRequirements = new ArrayList<ReqComponentInfo>();

        List<ResultEvent> events = results.getResultsOfType(ResultEvent.PROPOSITION_EVALUATED);
        for (ResultEvent e : events) {
            if (!e.getResult()) {
                Proposition prop = (Proposition) e.getSource();
                failedRequirements.add(reqComponentPropositionMap.get(prop));
            }
        }

        return failedRequirements;
    }
}
