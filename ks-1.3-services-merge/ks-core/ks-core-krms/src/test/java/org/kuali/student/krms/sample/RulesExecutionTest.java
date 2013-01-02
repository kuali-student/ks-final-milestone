package org.kuali.student.krms.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.ExecutionFlag;
import org.kuali.rice.krms.api.engine.ExecutionOptions;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.SelectionCriteria;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.AgendaTree;
import org.kuali.rice.krms.framework.engine.AgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicAgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicContext;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.engine.Context;
import org.kuali.rice.krms.framework.engine.ContextProvider;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.ProviderBasedEngine;
import org.kuali.rice.krms.framework.engine.ResultLogger;
import org.kuali.rice.krms.framework.engine.Rule;
import org.kuali.rice.krms.impl.provider.repository.LazyAgendaTree;
import org.kuali.rice.krms.impl.provider.repository.RepositoryToEngineTranslatorImpl;
import org.kuali.rice.krms.impl.repository.AgendaBoService;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.rice.krms.impl.repository.FunctionBoServiceImpl;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.RuleBoService;
import org.kuali.rice.krms.impl.repository.TermBoService;
import org.kuali.rice.krms.impl.util.KRMSServiceLocatorInternal;
import org.kuali.rice.krms.test.KRMSTestCase;
import org.kuali.rice.test.BaselineTestCase.BaselineMode;
import org.kuali.rice.test.BaselineTestCase.Mode;
import org.kuali.student.krms.KSKRMSConstants;
import org.kuali.student.krms.termresolver.ApprovedCourseTermResolver;

@BaselineMode(Mode.NONE)
public class RulesExecutionTest extends KRMSTestCase {
    
    private static final ResultLogger LOG = ResultLogger.getInstance();

    private static List<TermResolver<?>> termResolvers;
   
    private static SelectionCriteria selectionCriteria;
    private static Agenda agenda1;
    private static Agenda agenda2;
    private static Agenda agenda3;

    private static ExecutionOptions xOptions;
    
    protected ContextBoService contextRepository;
	protected KrmsTypeRepositoryService krmsTypeRepository;
	private AgendaBoService agendaBoService;
	private RuleBoService ruleBoService;
	private FunctionBoServiceImpl functionBoService;

	// Services needed for creation:
	private TermBoService termBoService;
	private SpringResourceLoader krmsTestResourceLoader;
    
	public RulesExecutionTest() {
		super();
		this.setClearTables(false);
	}
	
	@Override
	protected void loadSuiteTestData() throws Exception {
		// Do nothing
	}

	@Override
	protected List<String> getPerTestTablesNotToClear() {
		List<String> tablesNotToClear = super.getPerTestTablesNotToClear();
		// tablesNotToClear.add("KRMS_.*");
		return tablesNotToClear;
	}

	@Override
	protected Lifecycle getLoadApplicationLifecycle() {
		if (krmsTestResourceLoader == null) {
			krmsTestResourceLoader = new SpringResourceLoader(new QName(
					"KRMSTestHarnessApplicationResourceLoader"),
					"classpath:KRMSTestHarnessSpringBeans.xml", null);
			krmsTestResourceLoader
					.setParentSpringResourceLoader(getTestHarnessSpringResourceLoader());
			getTestHarnessSpringResourceLoader().addResourceLoader(
					krmsTestResourceLoader);
		}
		return krmsTestResourceLoader;
	}
	
	@Before
	public void setup() {
    	//
    	getLoadApplicationLifecycle();
    	termBoService = KrmsRepositoryServiceLocator.getTermBoService();
		agendaBoService = KrmsRepositoryServiceLocator.getAgendaBoService();
		contextRepository = KrmsRepositoryServiceLocator.getContextBoService();
		ruleBoService = KrmsRepositoryServiceLocator.getRuleBoService();
		krmsTypeRepository = KrmsRepositoryServiceLocator
				.getKrmsTypeRepositoryService();
		functionBoService = KrmsRepositoryServiceLocator
				.getBean("functionRepositoryService");
    	//
        termResolvers = new ArrayList<TermResolver<?>>();
        
        termResolvers.add(new ApprovedCourseTermResolver());
      
        Map<String, String> contextQualifiers = new HashMap<String, String>();
//        contextQualifiers.put("docTypeName", "Course.PreRequisities");
        
        Map<String, String> empty = Collections.emptyMap();
        selectionCriteria = SelectionCriteria.createCriteria(new DateTime(), contextQualifiers, empty);
        
        xOptions = new ExecutionOptions();
        xOptions.setFlag(ExecutionFlag.LOG_EXECUTION, true);
        
    }
    
    private ProviderBasedEngine buildEngine(Agenda agenda) {
        Context context = new BasicContext(Arrays.asList(agenda), termResolvers);
        ContextProvider contextProvider = new ManualContextProvider(context);
        
        ProviderBasedEngine engine = new ProviderBasedEngine();
        engine.setContextProvider(contextProvider);
        
        return engine;
    }

    private HashMap<String, Object> buildExecFacts(String studentId) {
        HashMap<String, Object> execFacts = new HashMap<String, Object>();
//        execFacts.put(new Term(Constants.studentIdTermSpec), new String(studentId));
        
        return execFacts;
    }
    
//    @Test
//    public void executeStatementsForStudent1() {
//        HashMap<Term, Object> execFacts = buildExecFacts("1");
//        
//        ProviderBasedEngine engine1 = buildEngine(agenda1);
//        EngineResults results1 = engine1.execute(selectionCriteria, execFacts, xOptions);
//        System.out.println("Results for Student 1, agenda 1");
//        printEngineResults(results1);
//        
//        ProviderBasedEngine engine2 = buildEngine(agenda2);
//        EngineResults results2 = engine2.execute(selectionCriteria, execFacts, xOptions);
//        System.out.println("Results for Student 1, agenda 2");
//        printEngineResults(results2);
//        
//        ProviderBasedEngine engine3 = buildEngine(agenda3);
//        EngineResults results3 = engine3.execute(selectionCriteria, execFacts, xOptions);
//        System.out.println("Results for Student 1, agenda 3");
//        printEngineResults(results3);
//        
//    }
//
//    @Test
//    public void executeStatementsForStudent2() {
//        HashMap<Term, Object> execFacts = buildExecFacts("2");
//        
//        ProviderBasedEngine engine1 = buildEngine(agenda1);
//        EngineResults results1 = engine1.execute(selectionCriteria, execFacts, xOptions);
//        System.out.println("Results for Student 2, agenda 1");
//        printEngineResults(results1);
//        
//        ProviderBasedEngine engine2 = buildEngine(agenda2);
//        EngineResults results2 = engine2.execute(selectionCriteria, execFacts, xOptions);
//        System.out.println("Results for Student 2, agenda 2");
//        printEngineResults(results2);
//        
//        ProviderBasedEngine engine3 = buildEngine(agenda3);
//        EngineResults results3 = engine3.execute(selectionCriteria, execFacts, xOptions);
//        System.out.println("Results for Student 2, agenda 3");
//        printEngineResults(results3);
//        
//    }
//    
//    @Test
//    public void executeStatementsForStudent3() {
//        
//        // change permission propositions to return false
//        OrgPermissionProposition.setHasPermission(false);
//        InstructorPermissionProposition.setHasPermission(false);
//        
//        HashMap<Term, Object> execFacts = buildExecFacts("3");
//        
//        ProviderBasedEngine engine1 = buildEngine(agenda1);
//        EngineResults results1 = engine1.execute(selectionCriteria, execFacts, xOptions);
//        System.out.println("Results for Student 3, agenda 1");
//        printEngineResults(results1);
//        
//        ProviderBasedEngine engine2 = buildEngine(agenda2);
//        EngineResults results2 = engine2.execute(selectionCriteria, execFacts, xOptions);
//        System.out.println("Results for Student 3, agenda 2");
//        printEngineResults(results2);
//        
//        ProviderBasedEngine engine3 = buildEngine(agenda3);
//        EngineResults results3 = engine3.execute(selectionCriteria, execFacts, xOptions);
//        System.out.println("Results for Student 3, agenda 3");
//        printEngineResults(results3);
//        
//        // revert permission propositions
//        OrgPermissionProposition.setHasPermission(true);
//        InstructorPermissionProposition.setHasPermission(true);
//    }
    
    @Test
    public void testSimpleAgenda() {
        
        ContextDefinition contextDef = getKRMSContext(KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY);
        Agenda result = loadAgenda(KSKRMSConstants.AGENDA1, contextDef);
        
        ProviderBasedEngine engine = buildEngine(result);
        
        HashMap<String, Object> execFacts = buildExecFacts("1");
        
        EngineResults results = engine.execute(selectionCriteria, execFacts, xOptions);
        
        printEngineResults(results); 
    }

    public Agenda loadAgenda(String agendaName, ContextDefinition contextDef) {
    	AgendaDefinition agendaDef = getKRMSAgenda(agendaName, contextDef);
        if (agendaDef == null) { throw new RiceIllegalArgumentException("agendaDefinition must not be null"); }
        RepositoryToEngineTranslatorImpl repositoryToEngineTranslator = KRMSServiceLocatorInternal.getService(
                "repositoryToEngineTranslator");
        if (repositoryToEngineTranslator == null) {
            return null;
        }
        
        return new BasicAgenda(agendaDef.getAttributes(), new LazyAgendaTree(agendaDef, repositoryToEngineTranslator));
    }
    
	private AgendaDefinition getKRMSAgenda(String agendaName, ContextDefinition contextDef) {
		AgendaDefinition agendaDef = agendaBoService
				.getAgendaByNameAndContextId(agendaName,
						contextDef.getId());
		return agendaDef;
	}

	private ContextDefinition getKRMSContext(String context) {
		return contextRepository.getContextByNameAndNamespace(
				context, KSKRMSConstants.KSNAMESPACE);
	}

    private void printEngineResults(EngineResults results) {
        for(ResultEvent result : results.getAllResults()) {
            System.out.println(result.getType());
            System.out.println("Source object is of type: " + result.getSource().getClass().toString());
            System.out.println(result.getTimestamp());
            System.out.println(result.getResult());
            System.out.println();
        }
        System.out.println("---------------------------");
        System.out.println();
    }
    
}
