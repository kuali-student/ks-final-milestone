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
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class YVFAveragePropositionTest {

    public Map<String, Object> getFactMap(FactStructureDTO fs1, String column) {
    	String factKey = FactUtil.createFactKey(fs1);

    	FactResultTypeInfoDTO columnMetaData1 = CommonTestUtil.createColumnMetaData(BigDecimal.class.getName(), column);
        FactResultDTO factResult = CommonTestUtil.createFactResult(new String[] {"80","85","90"}, column);
        factResult.setFactResultTypeInfo(columnMetaData1);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);
        
        return factMap;
    }

	@Test
	public void testAverageProposition_True() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(YVFAverageProposition.AVERAGE_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		YVFAverageProposition<BigDecimal> proposition = new YVFAverageProposition<BigDecimal>(
				"1", "YVFAverageProposition", 
				ComparisonOperator.EQUAL_TO, new BigDecimal(85),
				yvf, factMap);

		Assert.assertTrue(proposition.apply());
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(proposition.getReport());
		Assert.assertNull(proposition.getReport().getFailureMessage());
		Assert.assertNotNull(proposition.getReport().getSuccessMessage());
	}

	@Test
	public void testAverageProposition_False() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(YVFAverageProposition.AVERAGE_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		YVFAverageProposition<BigDecimal> proposition = new YVFAverageProposition<BigDecimal>(
				"1", "YVFAverageProposition", 
				ComparisonOperator.EQUAL_TO, new BigDecimal(888),
				yvf, factMap);

		Assert.assertFalse(proposition.apply());
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(proposition.getReport());
		Assert.assertNotNull(proposition.getReport().getFailureMessage());
		Assert.assertNull(proposition.getReport().getSuccessMessage());
	}

	@Test
	public void testAverageProposition_InvalidColumn() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(YVFAverageProposition.AVERAGE_COLUMN_KEY, "resultColumn.xxx");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		try {
		YVFAverageProposition<BigDecimal> proposition = new YVFAverageProposition<BigDecimal>(
				"1", "YVFAverageProposition", 
				ComparisonOperator.EQUAL_TO, new BigDecimal(888),
				yvf, factMap);
		Assert.fail("YVFAverageProposition should have thrown a PropositionException for resultColumn.xxx");
		} catch(PropositionException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testAverageProposition_NullColumn() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(YVFAverageProposition.AVERAGE_COLUMN_KEY, null);
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		try {
		YVFAverageProposition<BigDecimal> proposition = new YVFAverageProposition<BigDecimal>(
				"1", "YVFAverageProposition", 
				ComparisonOperator.EQUAL_TO, new BigDecimal(888),
				yvf, factMap);
		Assert.fail("YVFAverageProposition should have thrown a PropositionException for a null column");
		} catch(PropositionException e) {
			Assert.assertTrue(true);
		}
	}
}
