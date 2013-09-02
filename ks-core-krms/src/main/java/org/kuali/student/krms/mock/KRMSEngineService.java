package org.kuali.student.krms.mock;

import org.joda.time.DateTime;
import org.kuali.rice.krms.api.engine.*;
import org.kuali.rice.krms.framework.engine.*;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.krms.ManualContextProvider;

import java.util.*;

/**
 * @author Kuali Student Team
 *
 * This class contains utility methods to evaluate agendas using the KRMS engine
 *
 */
public class KRMSEngineService implements MockService {

    private List<TermResolver<?>> termResolvers;
    private ExecutionOptions executionOptions;
    private Map<String, String> contextQualifiers;
    private SelectionCriteria selectionCriteria;

    public KRMSEngineService() {
       
    	super();
    	this.initialize();
    }
    
    public void initialize() {
    	 executionOptions = new ExecutionOptions();
         executionOptions.setFlag(ExecutionFlag.LOG_EXECUTION, true);
         executionOptions.setFlag(ExecutionFlag.EVALUATE_ALL_PROPOSITIONS, true);

         contextQualifiers = new HashMap<String, String>();

         contextQualifiers.put("docTypeName", "Course.PreRequisities");

         Map<String, String> empty = Collections.emptyMap();
         selectionCriteria = SelectionCriteria.createCriteria(new DateTime(), contextQualifiers, empty);
    }
    
    @Override
	public void clear() {
    	
    	this.contextQualifiers.clear();
    	this.executionOptions = null;
    	this.selectionCriteria  = null;
    	this.termResolvers = null;
    	
		
	}



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

   

    private ProviderBasedEngine buildEngine(Agenda agenda) {
        Context context = new BasicContext(Arrays.asList(agenda), termResolvers);
        ContextProvider contextProvider = new ManualContextProvider(context);

        ProviderBasedEngine engine = new ProviderBasedEngine();
        engine.setContextProvider(contextProvider);

        return engine;
    }

    public EngineResults executeAgenda(BasicAgenda basicAgenda, Map<String, Object> executionFacts) {

        Engine engine = buildEngine(basicAgenda);
        EngineResults results = engine.execute(selectionCriteria, executionFacts, executionOptions);

        return results;
    }

}
