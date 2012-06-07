package org.kuali.student.krms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.kuali.rice.krms.api.KrmsApiServiceLocator;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.ExecutionFlag;
import org.kuali.rice.krms.api.engine.ExecutionOptions;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.engine.SelectionCriteria;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.AgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicAgendaTree;
import org.kuali.rice.krms.impl.repository.AgendaBoService;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.rice.krms.impl.repository.FunctionBoServiceImpl;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.RuleBoService;
import org.kuali.rice.krms.impl.repository.TermBoService;
import org.kuali.rice.krms.test.KRMSTestCase;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ExecuteAgendaTest extends KRMSTestCase {

	private RulesEvaluationUtil rulesEvaluationUtil;
	protected ContextBoService contextRepository;
	protected KrmsTypeRepositoryService krmsTypeRepository;
	private AgendaBoService agendaBoService;
	private RuleBoService ruleBoService;
	private FunctionBoServiceImpl functionBoService;
	private TermBoService termBoService;
	
	
	@Before
	public void setup() {
		getLoadApplicationLifecycle();
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
		EngineResults engineResults = rulesEvaluationUtil
				.executeAgenda(agenda,
						executionFacts);

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

}