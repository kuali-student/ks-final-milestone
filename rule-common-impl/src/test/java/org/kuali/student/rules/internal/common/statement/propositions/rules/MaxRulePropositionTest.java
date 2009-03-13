package org.kuali.student.rules.internal.common.statement.propositions.rules;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.propositions.rules.MaxRuleProposition;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class MaxRulePropositionTest {

    public Map<String, Object> getFactMap(FactStructureDTO fs1, String column) {
    	String factKey = FactUtil.createFactKey(fs1);

    	FactResultTypeInfoDTO columnMetaData1 = CommonTestUtil.createColumnMetaData(BigDecimal.class.getName(), column);
        FactResultDTO factResult = CommonTestUtil.createFactResult(new String[] {"80","85","90"}, column);
        factResult.setFactResultTypeInfo(columnMetaData1);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);
        
        return factMap;
    }

    public Map<String, Object> getFactMapForDates(FactStructureDTO fs1, String column, Class<?> dataType) {
    	Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
    	Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);

		String[] date = new String[2];
		date[0] = BusinessRuleUtil.formatIsoDate(cal1.getTime());
		date[1] = BusinessRuleUtil.formatIsoDate(cal2.getTime());

		String factKey = FactUtil.createFactKey(fs1);

    	FactResultTypeInfoDTO columnMetaData1 = CommonTestUtil.createColumnMetaData(dataType.getName(), column);
        FactResultDTO factResult = CommonTestUtil.createFactResult(date, column);
        factResult.setFactResultTypeInfo(columnMetaData1);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);
        
        return factMap;
    }

	@Test
	public void testMaxProposition_StaticFact() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.max.fact");
		fs.setStaticFact(true);
		fs.setStaticValueDataType(BigDecimal.class.getName());
		fs.setStaticValue("80,85,90");

		BigDecimal expectedValue = new BigDecimal(90);
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, expectedValue.toString(), ComparisonOperator.EQUAL_TO.toString(), BigDecimal.class.getName());

		MaxRuleProposition<BigDecimal> proposition = new MaxRuleProposition<BigDecimal>(
				"1", "MaxRuleProposition", ruleProposition, null);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());

		FactResultDTO factResult = report.getFactResult();
		Assert.assertEquals(3, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "85"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "90"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "90"));
	}

	@Test
	public void testMaxProposition_True() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.max.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_MAX_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		BigDecimal expectedValue = new BigDecimal(90);
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, expectedValue.toString(), ComparisonOperator.EQUAL_TO.toString(), BigDecimal.class.getName());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		MaxRuleProposition<BigDecimal> proposition = new MaxRuleProposition<BigDecimal>(
				"1", "MaxRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());

		FactResultDTO factResult = report.getFactResult();
		Assert.assertEquals(3, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.credit", "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.credit", "85"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.credit", "90"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.credit", "90"));
	}

    @Test
    public void testMaxProposition_BigDecimal_SuccessMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.max.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_MAX_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		BigDecimal expectedValue = new BigDecimal(90);
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, expectedValue.toString(), ComparisonOperator.EQUAL_TO.toString(), BigDecimal.class.getName());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		MaxRuleProposition<BigDecimal> proposition = new MaxRuleProposition<BigDecimal>(
				"1", "MaxRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
        Assert.assertEquals(MessageContextConstants.PROPOSITION_MAX_SUCCESS_MESSAGE, report.getMessage());
    }

    @Test
    public void testMaxProposition_BigDecimal_FailureMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.max.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_MAX_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		BigDecimal expectedValue = new BigDecimal(100);
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, expectedValue.toString(), ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(), BigDecimal.class.getName());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		MaxRuleProposition<BigDecimal> proposition = new MaxRuleProposition<BigDecimal>(
				"1", "MaxRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
        Assert.assertEquals("Maximum of 100 not met. Maximum found: 90.", report.getMessage());
    }

    @Test
    public void testMaxProposition_Calendar_GreaterThan_SuccessMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.max.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_MAX_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

    	Calendar cal = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
    	String expectedValue = BusinessRuleUtil.formatIsoDate(cal.getTime());
    			
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, expectedValue, ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(), Calendar.class.getName());

		Map<String, Object> factMap = getFactMapForDates(fs, "resultColumn.credit", Calendar.class);
		
		MaxRuleProposition<Calendar> proposition = new MaxRuleProposition<Calendar>(
				"1", "MaxRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
        Assert.assertEquals(MessageContextConstants.PROPOSITION_MAX_SUCCESS_MESSAGE, report.getMessage());
    }

    @Test
    public void testMaxProposition_Calendar_GreaterThan_FailureMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.max.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_MAX_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

    	Calendar cal = CommonTestUtil.createDate(2020, 1, 1, 1, 0);
    	String expectedValue = BusinessRuleUtil.formatIsoDate(cal.getTime());
    			
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, expectedValue, ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(), Calendar.class.getName());

		Map<String, Object> factMap = getFactMapForDates(fs, "resultColumn.credit", Calendar.class);
		
		MaxRuleProposition<Calendar> proposition = new MaxRuleProposition<Calendar>(
				"1", "MaxRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
        Assert.assertEquals("Maximum of 2020-01-01T01:00:00.000" +
        		BusinessRuleUtil.getDefaultIsoTimeZone(cal.getTime()) +
        		" not met. Maximum found: 2010-01-01T01:00:00.000" +
        		BusinessRuleUtil.getDefaultIsoTimeZone(cal.getTime())+ 
        		".", report.getMessage());
    }

    @Test
    public void testMaxProposition_Date_GreaterThan_SuccessMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.max.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_MAX_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

    	Calendar cal = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
    	String expectedValue = BusinessRuleUtil.formatIsoDate(cal.getTime());
    			
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, expectedValue, ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(), Date.class.getName());

		Map<String, Object> factMap = getFactMapForDates(fs, "resultColumn.credit", Date.class);
		
		MaxRuleProposition<Date> proposition = new MaxRuleProposition<Date>(
				"1", "MaxRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
        Assert.assertEquals(MessageContextConstants.PROPOSITION_MAX_SUCCESS_MESSAGE, report.getMessage());
    }

    @Test
    public void testMaxProposition_Date_GreaterThan_FailureMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.max.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_MAX_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

    	Calendar cal = CommonTestUtil.createDate(2020, 1, 1, 1, 0);
    	String expectedValue = BusinessRuleUtil.formatIsoDate(cal.getTime());
    			
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, expectedValue, ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(), Date.class.getName());

		Map<String, Object> factMap = getFactMapForDates(fs, "resultColumn.credit", Date.class);
		
		MaxRuleProposition<Date> proposition = new MaxRuleProposition<Date>(
				"1", "MaxRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
        Assert.assertEquals("Maximum of 2020-01-01T01:00:00.000" +
        		BusinessRuleUtil.getDefaultIsoTimeZone(cal.getTime()) +
        		" not met. Maximum found: 2010-01-01T01:00:00.000" +
        		BusinessRuleUtil.getDefaultIsoTimeZone(cal.getTime()) +
        		".", report.getMessage());
    }

}
