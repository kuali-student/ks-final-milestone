package org.kuali.student.krms;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.kew.util.PerformanceLogger;
import org.kuali.rice.krms.api.KrmsApiServiceLocator;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.ExecutionFlag;
import org.kuali.rice.krms.api.engine.ExecutionOptions;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.SelectionCriteria;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.impl.repository.AgendaBoService;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.rice.krms.impl.repository.FunctionBoServiceImpl;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.RuleBoService;
import org.kuali.rice.krms.impl.repository.TermBoService;
import org.kuali.rice.krms.test.KRMSTestCase;
import org.kuali.rice.krms.test.TestActionTypeService;
import org.kuali.rice.test.BaselineTestCase.BaselineMode;
import org.kuali.rice.test.BaselineTestCase.Mode;
import org.kuali.student.r2.core.statement.util.RulesEvaluationUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@BaselineMode(Mode.NONE)
public class ExecuteAgendaTest extends KRMSTestCase {
//\
	   static final String NAMESPACE1 = "KRMS_TEST_1";
	    static final String NAMESPACE2 = "KRMS_TEST_2";
	    static final String TSUNAMI_EVENT = "Tsunami";
	    static final String EARTHQUAKE_EVENT = "Earthquake";
	    static final String CONTEXT1 = "Context1";
	    static final String CONTEXT2 = "Context2";
	    static final String CONTEXT3 = "Context3";
	    static final String NAME = "name";
	    static final String CONTEXT1_QUALIFIER = "Context1Qualifier";
	    static final String CONTEXT1_QUALIFIER_VALUE = "BLAH1";
	    static final String CONTEXT2_QUALIFIER = "Context2Qualifier";
	    static final String CONTEXT2_QUALIFIER_VALUE = "BLAH2";
	    static final String AGENDA1 = "TestAgenda1";
	    static final String AGENDA2 = "Agenda2";
	    static final String AGENDA3 = "Agenda3";
	    static final String AGENDA4 = "Agenda4";
	    static final String AGENDA5 = "Agenda5";
	    static final String PREREQ_TERM_NAME = "prereqTermSpec";
	    static final String PREREQ_TERM_VALUE = "prereqValue";
	    static final String NAMESPACE_CODE = "namespaceCode";
	    static final String BOOL1 = "bool1";
	    static final String BOOL2 = "bool2";
	    static final String NULL_FACT = "nullFact";
	//
	//private RulesEvaluationUtil rulesEvaluationUtil;
	protected ContextBoService contextRepository;
	protected KrmsTypeRepositoryService krmsTypeRepository;
	private AgendaBoService agendaBoService;
	private RuleBoService ruleBoService;
	private FunctionBoServiceImpl functionBoService;
	private TermBoService termBoService;
	
	public ExecuteAgendaTest() {
		super();
		this.setClearTables(false);
	}
	
	
	@Override
	protected void setUpInternal() throws Exception {
        assertNotNull(getModuleName());
        setModuleName(getModuleName());
        setBaseDirSystemProperty(getModuleName());

        this.perTestLifeCycles = getPerTestLifecycles();
        this.suiteLifeCycles = getSuiteLifecycles();

        if (SUITE_LIFE_CYCLES_FAILED) {
//        	fail("Suite Lifecycles startup failed on test " + failedSuiteTestName + "!!!  Please see logs for details.");
        }
        if (!SUITE_LIFE_CYCLES_RAN) {
	        try {
    	        startLifecycles(this.suiteLifeCycles);
        	    SUITE_LIFE_CYCLES_RAN = true;
        	} catch (Throwable e) {
        		e.printStackTrace();
                SUITE_LIFE_CYCLES_RAN = false;
                SUITE_LIFE_CYCLES_FAILED = true;
                failedSuiteTestName = getFullTestName();
                tearDown();
                stopLifecycles(this.suiteLifeCycles);
                throw new RuntimeException(e);
            }
        }

//        startSuiteDataLoaderLifecycles();

//        startLifecycles(this.perTestLifeCycles);

    }


	@Before
	public void setup() {
		// getLoadApplicationLifecycle();
		termBoService = KrmsRepositoryServiceLocator.getTermBoService();
		agendaBoService = KrmsRepositoryServiceLocator.getAgendaBoService();
		contextRepository = KrmsRepositoryServiceLocator.getContextBoService();
		ruleBoService = KrmsRepositoryServiceLocator.getRuleBoService();
		krmsTypeRepository = KrmsRepositoryServiceLocator
				.getKrmsTypeRepositoryService();
		functionBoService = KrmsRepositoryServiceLocator
				.getBean("functionRepositoryService");

	}
	private EngineResults engineExecute(String contextName, String termName) {
		Map<String, String> contextQualifiers = new HashMap<String, String>();
		contextQualifiers.put("name", contextName);
		contextQualifiers.put("namespaceCode", KSKRMSConstants.KSNAMESPACE);

		SelectionCriteria sc1 = SelectionCriteria.createCriteria(
				new DateTime(), contextQualifiers,
				Collections.<String, String> emptyMap());

		Facts.Builder factsBuilder1 = Facts.Builder.create();
		// factsBuilder1.addFact(TERM_NAME, 49999);
		factsBuilder1.addFact(termName, "BL");

		ExecutionOptions xOptions1 = new ExecutionOptions();
		xOptions1.setFlag(ExecutionFlag.LOG_EXECUTION, true);

		EngineResults engineResults = KrmsApiServiceLocator.getEngine()
				.execute(sc1, factsBuilder1.build(), xOptions1);
		assertNotNull(engineResults);
		assertTrue(engineResults.getAllResults().size() > 0);
		print(engineResults);
		return engineResults;
	}

	private void print(EngineResults engineResults) {
		System.out.println(ToStringBuilder.reflectionToString(engineResults,
				ToStringStyle.MULTI_LINE_STYLE));
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
	
    @Test
    public void testNullFact() {

        Map<String,String> contextQualifiers = new HashMap<String,String>();
        contextQualifiers.put(NAMESPACE_CODE, KSKRMSConstants.KSNAMESPACE);
        contextQualifiers.put(NAME, KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY);

        Map<String,String> agendaQualifiers = new HashMap<String,String>();
        agendaQualifiers.put(NAME, KSKRMSConstants.AGENDA1);

        DateTime now = new DateTime();

        SelectionCriteria sc1 = SelectionCriteria.createCriteria(now, contextQualifiers, agendaQualifiers);

        Facts.Builder factsBuilder1 = Facts.Builder.create();
        factsBuilder1.addFact(NULL_FACT, null);

        ExecutionOptions xOptions1 = new ExecutionOptions();
        xOptions1.setFlag(ExecutionFlag.LOG_EXECUTION, true);

        PerformanceLogger perfLog = new PerformanceLogger();
        perfLog.log("starting rule execution");
        EngineResults eResults1 = KrmsApiServiceLocator.getEngine().execute(sc1, factsBuilder1.build(), xOptions1);
        perfLog.log("finished rule execution", true);
        List<ResultEvent> rEvents1 = eResults1.getAllResults();

        List<ResultEvent> ruleEvaluationResults1 = eResults1.getResultsOfType(ResultEvent.RULE_EVALUATED.toString());

        assertEquals("1 rules should have been evaluated", 1, ruleEvaluationResults1.size());

        assertTrue("rule 0 should have evaluated to true", ruleEvaluationResults1.get(0).getResult());

        // ONLY agenda 1 should have been selected
        assertTrue(TestActionTypeService.actionFired("Agenda5::Rule5::TestAction"));

        assertAgendaDidNotExecute(AGENDA1);
        assertAgendaDidNotExecute(AGENDA2);
        assertAgendaDidNotExecute(AGENDA3);
        assertAgendaDidNotExecute(AGENDA4);
    }    
	
	public void testKrms(
			String studentId, String courseOfferingId) {

		// TODO 1.  Get the agenda for the course...
		AgendaDefinition agendaDef = getKRMSAgenda(KSKRMSConstants.AGENDA1, getKRMSContext(KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY));
		Map<String, String> qualifierMap = null;
		if (qualifierMap == null) {
            qualifierMap = Collections.emptyMap();
        }
        Agenda agenda = new BasicAgenda(qualifierMap, null);

    	

		// TODO 2.  Setup the executionFacts...
        Map<String, Object> executionFacts = new HashMap<String, Object>();
//        executionFacts.put(KSKRMSConstants.TERM_APPROVED_COURSE, studentId);

        // TODO 3.  Execute the agenda...
/*		EngineResults engineResults = rulesEvaluationUtil
				.executeAgenda(agenda,
						executionFacts);*/

//		List<ValidationResultInfo> resultInfos = new ArrayList<ValidationResultInfo>();
//
//		// find and process statements that the PropositionBuilder can handle
//		for (StatementTreeViewInfo statementTree : statements) {
//			if (PropositionBuilder.TRANSLATABLE_STATEMENT_TYPES
//					.contains(statementTree.getType())) {
//
//				Map<String, Object> executionFacts = new HashMap<String, Object>();
//				executionFacts
//						.put(RulesExecutionConstants.STUDENT_ID_TERM_NAME,
//								studentId);
//				executionFacts.put(
//						RulesExecutionConstants.COURSE_ID_TO_ENROLL_TERM_NAME,
//						courseOffering.getCourseId());
//				executionFacts
//						.put(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME,
//								context);
//
//				EngineResults engineResults = rulesEvaluationUtil
//						.executeAgenda(translationResults.agenda,
//								executionFacts);
//
//				List<ReqComponentInfo> failedRequirements = rulesEvaluationUtil
//						.getFailedRequirementsFromEngineResults(engineResults,
//								translationResults.reqComponentPropositionMap);
//
//				if (!failedRequirements.isEmpty()) {
//					for (ReqComponentInfo failedRequirement : failedRequirements) {
//						ValidationResultInfo resultInfo = new ValidationResultInfo();
//						resultInfo.setLevel(ValidationResult.ErrorLevel.ERROR
//								.getLevel());
//						resultInfo.setElement(failedRequirement.getId());
//						try {
//							resultInfo.setMessage(statementService
//									.getNaturalLanguageForReqComponent(
//											failedRequirement.getId(),
//											"KUALI.RULE", "en"));
//						} catch (Exception e) {
//							throw new OperationFailedException(e.getMessage(),
//									e);
//						}
//
//						resultInfos.add(resultInfo);
//					}
//				}
//			}
//		}
//
//		if (resultInfos.isEmpty()) {
//			ValidationResultInfo resultInfo = new ValidationResultInfo();
//			resultInfo.setLevel(ValidationResult.ErrorLevel.OK.getLevel());
//
//			resultInfos.add(resultInfo);
//		}
//
//		return resultInfos;
	}

    private void assertAgendaDidNotExecute(String agendaName) {
        assertFalse(TestActionTypeService.actionFired(agendaName+"::Rule1::TestAction"));
        assertFalse(TestActionTypeService.actionFired(agendaName+"::Rule2::TestAction"));
        assertFalse(TestActionTypeService.actionFired(agendaName+"::Rule3::TestAction"));
    }
}