package org.kuali.student.rules.internal.common.statement.propositions.rules;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultColumnInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.exceptions.IllegalFunctionStateException;
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.rules.SumRuleProposition;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class SumRulePropositionTest {

	private Map<String, Object> getFactMap(FactStructureDTO fs1, String column) {
    	String factKey = FactUtil.createFactKey(fs1);

    	FactResultTypeInfoDTO columnMetaData1 = CommonTestUtil.createColumnMetaData(BigDecimal.class.getName(), column);
        FactResultDTO factResult = CommonTestUtil.createFactResult(new String[] {"80","95","80"}, column);

        factResult.setFactResultTypeInfo(columnMetaData1);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);
        
        return factMap;
    }

	private Map<String, Object> getFactMap2(FactStructureDTO fs1) {
    	String factKey = FactUtil.createFactKey(fs1);

    	Map<String, FactResultColumnInfoDTO> columnsInfoMap = new HashMap<String, FactResultColumnInfoDTO>();
    	FactResultColumnInfoDTO columnInfo1 = new FactResultColumnInfoDTO();
    	columnInfo1.setKey("resultColumn.cluId");
    	columnInfo1.setDataType(String.class.getName());
    	columnsInfoMap.put(columnInfo1.getKey(), columnInfo1);
    	FactResultColumnInfoDTO columnInfo2 = new FactResultColumnInfoDTO();
    	columnInfo2.setKey("resultColumn.credit");
    	columnInfo2.setDataType(BigDecimal.class.getName());
    	columnsInfoMap.put(columnInfo2.getKey(), columnInfo2);
    	FactResultColumnInfoDTO columnInfo3 = new FactResultColumnInfoDTO();
    	columnInfo3.setKey("resultColumn.grade");
    	columnInfo3.setDataType(BigDecimal.class.getName());
    	columnsInfoMap.put(columnInfo3.getKey(), columnInfo3);
    	FactResultColumnInfoDTO columnInfo4 = new FactResultColumnInfoDTO();
    	columnInfo4.setKey("resultColumn.description");
    	columnInfo4.setDataType(String.class.getName());
    	columnsInfoMap.put(columnInfo4.getKey(), columnInfo4);

    	FactResultTypeInfoDTO typeInfo = new FactResultTypeInfoDTO();
    	typeInfo.setResultColumnsMap(columnsInfoMap);
    	
		FactResultDTO factResult = new FactResultDTO();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		Map<String, String> row1 = new HashMap<String, String>();
		row1.put("resultColumn.cluId", "CHEM111");
		row1.put("resultColumn.credit", "4.0");
		row1.put("resultColumn.grade", "80.0");
		row1.put("resultColumn.description", "CHEM 111 (4) Principles of Chemistry I");
		resultList.add(row1);
		Map<String, String> row2 = new HashMap<String, String>();
		row2.put("resultColumn.cluId", "MATH100");
		row2.put("resultColumn.credit", "3.0");
		row2.put("resultColumn.grade", "95.0");
		row2.put("resultColumn.description", "MATH 100 (3) Differential Calculus");
		resultList.add(row2);
		Map<String, String> row3 = new HashMap<String, String>();
		row3.put("resultColumn.cluId", "PHYS100");
		row3.put("resultColumn.credit", "3.0");
		row3.put("resultColumn.grade", "85.0");
		row2.put("resultColumn.description", "PHYS 100 (3) Introductory Physics");
		resultList.add(row3);

		factResult.setResultList(resultList);
        
        factResult.setFactResultTypeInfo(typeInfo);

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
		Assert.assertNotNull(report.getMessage());
		Assert.assertNotNull(report.getFactResult());

		FactResultDTO fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "95"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "255.0"));
	}
    
	@Test
	public void testSumProposition_True() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SUM_COLUMN_KEY, "resultColumn.credit");
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
		Assert.assertNotNull(report.getMessage());
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
	public void testSumProposition_SuccessMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SUM_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		BigDecimal expectedValue = new BigDecimal(10);
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, expectedValue.toString(), ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());
		ruleProposition.setSuccessMessage(
				"#set( $sum=$prop_result_fr.getResultList().get(0).get(\"resultColumn.credit\") )"
				+ "\nSum successful\n"
				+ "#set( $totalGrade = 0 )"
				+ "#set( $rowCount = $prop_fact_fr.getResultList().size() )"
				+ "#foreach( $columnMap in $prop_fact_fr.getResultList() )"
				+ "    #set( $grd = $mathTool.toNumber($columnMap.get(\"resultColumn.grade\")) )"
				+ "    #set( $totalGrade = $totalGrade + $grd )"
				+ "    CLU ID:    $columnMap.get(\"resultColumn.cluId\")\n"
				+ "    Course:    $columnMap.get(\"resultColumn.description\")\n"
				+ "    Credits:   $columnMap.get(\"resultColumn.credit\")\n"
				+ "    Grade:     $columnMap.get(\"resultColumn.grade\")\n"
				+ "    ------------------------------------------------------------\n"
				+ "#end\n"
				+ "Total credits: $prop_sum\n"
				+ "#set( $avg = $totalGrade / $rowCount )\n"
				+ "Grade Average: $mathTool.roundTo(1,$avg)%\n");

		Map<String, Object> factMap = getFactMap2(fs);
		
		SumRuleProposition<BigDecimal> proposition = new SumRuleProposition<BigDecimal>(
				"1", "SumRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertNotNull(report.getFactResult());

		String expected = "Sum successful\n"
					    + "    CLU ID:    CHEM111\n"
					    + "    Course:    CHEM 111 (4) Principles of Chemistry I\n"
					    + "    Credits:   4.0\n"
					    + "    Grade:     80.0\n"
					    + "    ------------------------------------------------------------\n"
					    + "    CLU ID:    MATH100\n"
					    + "    Course:    PHYS 100 (3) Introductory Physics\n"
					    + "    Credits:   3.0\n"
					    + "    Grade:     95.0\n"
					    + "    ------------------------------------------------------------\n"
					    + "    CLU ID:    PHYS100\n"
					    + "    Course:    $columnMap.get(\"resultColumn.description\")\n"
					    + "    Credits:   3.0\n"
					    + "    Grade:     85.0\n"
					    + "    ------------------------------------------------------------\n"
						+ "Total credits: 10.0\n"
						+ "Grade Average: 86.7%\n";

		Assert.assertEquals(expected, report.getMessage());

		FactResultDTO fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.cluId", "CHEM111"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.cluId", "MATH100"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.cluId", "PHYS100"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
        Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.credit", "10.0"));
	}

	@Test
	public void testSumProposition_False() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");
		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SUM_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(111).toString(), ComparisonOperator.EQUAL_TO.toString());
		ruleProposition.setFailureMessage(
				"#set( $result=$prop_result_fr.getResultList().get(0).get(\"resultColumn.credit\") )"
				+ "\n#set( $resultNumber = $mathTool.toNumber($result) )"
				+ "\n#set( $expectedNumber = $mathTool.toNumber($prop_expected_value_str) )"
				+ "\n#set( $needed = ($expectedNumber - $resultNumber) )"
				+ "\nSum failure. Sum is short by $needed. "
				+ "Sum must be equal to $prop_expected_value_str.0.");

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		SumRuleProposition<BigDecimal> proposition = new SumRuleProposition<BigDecimal>(
				"1", "SumRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertNotNull(report.getFactResult());
        Assert.assertEquals("Sum failure. Sum is short by -144.0. Sum must be equal to 111.0.", report.getMessage());

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
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SUM_COLUMN_KEY, "resultColumn.xxx");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(111).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		try {
			SumRuleProposition<BigDecimal> proposition = new SumRuleProposition<BigDecimal>(
					"1", "SumRuleProposition",  ruleProposition, factMap);
			proposition.apply();
			Assert.fail("SumRuleProposition should have thrown a PropositionException for resultColumn.xxx");
		} catch(IllegalFunctionStateException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testSumProposition_NullColumn() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SUM_COLUMN_KEY, null);
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

	@Test
	public void testSumProposition_True_DefaultSuccessMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SUM_COLUMN_KEY, "resultColumn.credit");
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
		Assert.assertNotNull(report.getMessage());
        Assert.assertEquals(MessageContextConstants.PROPOSITION_SUM_SUCCESS_MESSAGE, report.getMessage());
	}	

	@Test
	public void testSumProposition_True_DefaultFailureMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SUM_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		BigDecimal expectedValue = new BigDecimal("300.0");
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(yvf, expectedValue.toString(), ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		SumRuleProposition<BigDecimal> proposition = new SumRuleProposition<BigDecimal>(
				"1", "SumRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertEquals("Sum constraint failed. Sum is short by 45.0. Expected=300.0, Sum=255.0, Difference=45.0", report.getMessage());
	}	
}
