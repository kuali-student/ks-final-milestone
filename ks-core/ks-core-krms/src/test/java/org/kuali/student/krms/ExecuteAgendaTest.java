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
import org.kuali.rice.krms.test.KRMSTestCase;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ExecuteAgendaTest extends KRMSTestCase {

	private RulesEvaluationUtil rulesEvaluationUtil;

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

//	public void testKrms(
//			String studentId, String courseOfferingId, ContextInfo context)
//			throws InvalidParameterException, MissingParameterException,
//			OperationFailedException, PermissionDeniedException {
//
//		CourseOfferingInfo courseOffering = null;
//		try {
//			courseOffering = courseOfferingService.getCourseOffering(
//					courseOfferingId, context);
//		} catch (DoesNotExistException e) {
//			throw new InvalidParameterException(
//					"Invalid courseOfferingId, no course offering found with id of: "
//							+ courseOfferingId);
//		}
//
//		List<StatementTreeViewInfo> statements;
//
//		try {
//			// TODO fill in nlUsageType and language parameters once the
//			// implementation actually uses them
//			statements = courseService.getCourseStatements(
//					courseOffering.getCourseId(), null, null);
//		} catch (Exception e) {
//			throw new OperationFailedException(e.getMessage(), e);
//		}
//
//		List<ValidationResultInfo> resultInfos = new ArrayList<ValidationResultInfo>();
//
//		// find and process statements that the PropositionBuilder can handle
//		for (StatementTreeViewInfo statementTree : statements) {
//			if (PropositionBuilder.TRANSLATABLE_STATEMENT_TYPES
//					.contains(statementTree.getType())) {
//				PropositionBuilder.TranslationResults translationResults = null;
//				try {
//					translationResults = propositionBuilder.translateStatement(
//							statementTree, null);
//				} catch (org.kuali.student.common.exceptions.InvalidParameterException e) {
//					throw new OperationFailedException(
//							"Exception thrown attempting statement translation for statement: "
//									+ statementTree.getId(), e);
//				}
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
//	}

}