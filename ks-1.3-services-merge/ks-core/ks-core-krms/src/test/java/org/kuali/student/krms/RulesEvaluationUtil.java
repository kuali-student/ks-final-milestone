package org.kuali.student.krms;

import org.joda.time.DateTime;
import org.kuali.rice.krms.api.engine.*;
import org.kuali.rice.krms.framework.engine.*;
import org.kuali.student.krms.util.ManualContextProvider;

import java.util.*;

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

//    public List<ReqComponentInfo> getFailedRequirementsFromEngineResults(EngineResults results, Map<Proposition, ReqComponentInfo> reqComponentPropositionMap) {
//        List<ReqComponentInfo> failedRequirements = new ArrayList<ReqComponentInfo>();
//
//        List<ResultEvent> events = results.getResultsOfType(ResultEvent.PropositionEvaluated);
//        for (ResultEvent e : events) {
//            if (!e.getResult()) {
//                Proposition prop = (Proposition) e.getSource();
//                failedRequirements.add(reqComponentPropositionMap.get(prop));
//            }
//        }
//
//        return failedRequirements;
//    }
}
