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
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.rules.SumRuleProposition;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class SumRulePropositionTest {

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
	public void testSumProposition_StaticFact() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");
		fs.setStaticFact(true);
		fs.setStaticValueDataType(BigDecimal.class.getName());
		fs.setStaticValue("80,95,80");
		
		BigDecimal expectedValue = new BigDecimal(255);
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, expectedValue.toString(), ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		SumRuleProposition<BigDecimal> proposition = new SumRuleProposition<BigDecimal>(
				"1", "SumRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNull(report.getFailureMessage());
		Assert.assertNotNull(report.getSuccessMessage());
		Assert.assertNotNull(report.getFactResult());

		FactResultDTO fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), SumRuleProposition.STATIC_FACT_COLUMN, "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), SumRuleProposition.STATIC_FACT_COLUMN, "95"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), SumRuleProposition.STATIC_FACT_COLUMN, "255.0"));
	}
    
	@Test
	public void testSumProposition_True() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(SumRuleProposition.SUM_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		BigDecimal expectedValue = new BigDecimal(255);
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, expectedValue.toString(), ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		SumRuleProposition<BigDecimal> proposition = new SumRuleProposition<BigDecimal>(
				"1", "SumRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNull(report.getFailureMessage());
		Assert.assertNotNull(report.getSuccessMessage());
		Assert.assertNotNull(report.getFactResult());

		FactResultDTO fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "95"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.credit", "255.0"));
	}

	@Test
	public void testSumProposition_False() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");
		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(SumRuleProposition.SUM_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(111).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		SumRuleProposition<BigDecimal> proposition = new SumRuleProposition<BigDecimal>(
				"1", "SumRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getFailureMessage());
		Assert.assertNull(report.getSuccessMessage());
		Assert.assertNotNull(report.getFactResult());

		FactResultDTO fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "95"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.credit", "255.0"));
	}

	@Test
	public void testSumProposition_InvalidColumn() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");
		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(SumRuleProposition.SUM_COLUMN_KEY, "resultColumn.xxx");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(111).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		try {
			SumRuleProposition<BigDecimal> proposition = new SumRuleProposition<BigDecimal>(
					"1", "SumRuleProposition",  ruleProposition, factMap);
			Assert.fail("SumRuleProposition should have thrown a PropositionException for resultColumn.xxx");
		} catch(PropositionException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testSumProposition_NullColumn() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(SumRuleProposition.SUM_COLUMN_KEY, null);
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(111).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		try {
			SumRuleProposition<BigDecimal> proposition = new SumRuleProposition<BigDecimal>(
					"1", "SumRuleProposition", ruleProposition, factMap);
			Assert.fail("SumRuleProposition should have thrown a PropositionException for a null column");
		} catch(PropositionException e) {
			Assert.assertTrue(true);
		}
	}
}
