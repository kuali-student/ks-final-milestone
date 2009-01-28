package org.kuali.student.rules.internal.common.statement.yvf;

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
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class YVFSumPropositionTest {

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
		
		yvf.setFactStructureList(Arrays.asList(fs));

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		YVFSumProposition<BigDecimal> proposition = new YVFSumProposition<BigDecimal>(
				"1", "YVFSumProposition", 
				ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new BigDecimal(255),
				yvf, factMap);

		PropositionReport report = proposition.getReport();

		Assert.assertTrue(proposition.apply());
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNull(report.getFailureMessage());
		Assert.assertNotNull(report.getSuccessMessage());
		Assert.assertNotNull(report.getFactResult());

		FactResultDTO fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), YVFAverageProposition.STATIC_FACT_COLUMN, "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), YVFAverageProposition.STATIC_FACT_COLUMN, "95"));
	}
    
	@Test
	public void testSumProposition_True() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(YVFSumProposition.SUM_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		yvf.setFactStructureList(Arrays.asList(fs));

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		YVFSumProposition<BigDecimal> proposition = new YVFSumProposition<BigDecimal>(
				"1", "YVFSumProposition", 
				ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new BigDecimal(255),
				yvf, factMap);

		PropositionReport report = proposition.getReport();

		Assert.assertTrue(proposition.apply());
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNull(report.getFailureMessage());
		Assert.assertNotNull(report.getSuccessMessage());
		Assert.assertNotNull(report.getFactResult());

		FactResultDTO fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "95"));
	}

	@Test
	public void testSumProposition_False() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");
		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(YVFSumProposition.SUM_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);
		yvf.setFactStructureList(Arrays.asList(fs));

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		YVFSumProposition<BigDecimal> proposition = new YVFSumProposition<BigDecimal>(
				"1", "YVFSumProposition", 
				ComparisonOperator.EQUAL_TO, new BigDecimal(111),
				yvf, factMap);

		PropositionReport report = proposition.getReport();

		Assert.assertFalse(proposition.apply());
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getFailureMessage());
		Assert.assertNull(report.getSuccessMessage());
		Assert.assertNotNull(report.getFactResult());

		FactResultDTO fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "95"));
	}

	@Test
	public void testSumProposition_InvalidColumn() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");
		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(YVFSumProposition.SUM_COLUMN_KEY, "resultColumn.xxx");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);
		yvf.setFactStructureList(Arrays.asList(fs));

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		try {
			YVFSumProposition<BigDecimal> proposition = new YVFSumProposition<BigDecimal>(
					"1", "YVFSumProposition", 
					ComparisonOperator.EQUAL_TO, new BigDecimal(111),
					yvf, factMap);
			Assert.fail("YVFSumProposition should have thrown a PropositionException for resultColumn.xxx");
		} catch(PropositionException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testSumProposition_NullColumn() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.sum.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(YVFSumProposition.SUM_COLUMN_KEY, null);
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		try {
			YVFSumProposition<BigDecimal> proposition = new YVFSumProposition<BigDecimal>(
					"1", "YVFSumProposition", 
					ComparisonOperator.EQUAL_TO, new BigDecimal(111),
					yvf, factMap);
			Assert.fail("YVFSumProposition should have thrown a PropositionException for a null column");
		} catch(PropositionException e) {
			Assert.assertTrue(true);
		}
	}
}
