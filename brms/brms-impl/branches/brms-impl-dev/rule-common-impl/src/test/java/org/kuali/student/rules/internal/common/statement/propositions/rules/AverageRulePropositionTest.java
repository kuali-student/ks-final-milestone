package org.kuali.student.rules.internal.common.statement.propositions.rules;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.exceptions.IllegalFunctionStateException;
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.rules.AverageRuleProposition;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class AverageRulePropositionTest {

    public Map<String, Object> getFactMap(FactStructureDTO fs1, String column) {
    	String factKey = FactUtil.createFactKey(fs1);

    	FactResultTypeInfoDTO columnMetaData1 = CommonTestUtil.createColumnMetaData(BigDecimal.class.getName(), column);
        FactResultDTO factResult = CommonTestUtil.createFactResult(new String[] {"80","95","80"}, column);
        factResult.setFactResultTypeInfo(columnMetaData1);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);
        
        return factMap;
    }

	@Test
	public void testAverageProposition_StaticFact() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");
		fs.setStaticFact(true);
		fs.setStaticValueDataType(BigDecimal.class.getName());
		fs.setStaticValue("80,95,80");
		
		BigDecimal expectedValue = new BigDecimal(85);
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, expectedValue.toString(), ComparisonOperator.EQUAL_TO.toString());

		AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
				"1", "AverageRuleProposition", ruleProposition, null);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertNotNull(report.getFactResult());

		FactResultDTO fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "95"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "85.0"));
	}

	@Test
	public void testAverageProposition_True() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(85).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
				"1", "AverageRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertNotNull(report.getFactResult());

		FactResultDTO fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "95"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.credit", "85.0"));
	}

	@Test
	public void testAverageProposition_False() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(90).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");

		AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
				"1", "AverageRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertEquals("Average of 85.0 is short by 5.0. Expected an average of 90.", report.getMessage());

		FactResultDTO fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "95"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.credit", "85.0"));
	}

	@Test
	public void testAverageProposition_InvalidColumn() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "resultColumn.xxx");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(888).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		try {
			AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
					"1", "AverageRuleProposition", ruleProposition, factMap);
			proposition.apply();
			Assert.fail("AverageRuleProposition should have thrown a PropositionException for resultColumn.xxx");
		} catch(IllegalFunctionStateException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testAverageProposition_NullColumn() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, null);
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(888).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		try {
			AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
					"1", "AverageRuleProposition", ruleProposition, factMap);
			Assert.fail("AverageRuleProposition should have thrown a PropositionException for a null column");
		} catch(PropositionException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testAverageProposition_DefaultSuccessMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(85).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
				"1", "AverageRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertEquals(MessageContextConstants.PROPOSITION_AVERAGE_SUCCESS_MESSAGE, report.getMessage());
	}

	@Test
	public void testAverageProposition_DefaultFailureMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal("90.0").toString(), ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
				"1", "AverageRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertEquals("Average of 85.0 is short by 5.0. Expected an average of 90.0.", report.getMessage());
	}
}
