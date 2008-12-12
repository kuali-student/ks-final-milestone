package org.kuali.student.rules.internal.common.statement.yvf;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class YVFSimpleComparablePropositionTest {

    public Map<String, Object> getFactMap(FactStructureDTO fs1, String dataType, String value) {
    	String factKey = FactUtil.createFactKey(fs1);

    	FactResultTypeInfoDTO columnMetaData1 = CommonTestUtil.createColumnMetaData(dataType);
        FactResultDTO factResult = CommonTestUtil.createFactResult(new String[] {value});
        factResult.setFactResultTypeInfo(columnMetaData1);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);
        
        return factMap;
    }

	@Test
	public void testSimpleComparableProposition_BigDecimal() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");
		yvf.setFactStructureList(Arrays.asList(fs1));

		Map<String, Object> factMap = getFactMap(fs1, BigDecimal.class.getName(), "80");
		
		YVFSimpleComparableProposition<BigDecimal> poposition = new YVFSimpleComparableProposition<BigDecimal>(
				"1", "YVFSimpleComparableProposition", 
				ComparisonOperator.EQUAL_TO, new BigDecimal(80),
				yvf, factMap);

		Assert.assertTrue(poposition.apply());
		Assert.assertTrue(poposition.getResult());
		Assert.assertNotNull(poposition.getReport());
		Assert.assertNull(poposition.getReport().getFailureMessage());
		Assert.assertNotNull(poposition.getReport().getSuccessMessage());
	}

	@Test
	public void testSimpleComparableProposition_String() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");
		yvf.setFactStructureList(Arrays.asList(fs1));

		Map<String, Object> factMap = getFactMap(fs1, String.class.getName(), "80");
		
		YVFSimpleComparableProposition<String> poposition = new YVFSimpleComparableProposition<String>(
				"1", "YVFSimpleComparableProposition", 
				ComparisonOperator.EQUAL_TO, "80",
				yvf, factMap);

		Assert.assertTrue(poposition.apply());
		Assert.assertTrue(poposition.getResult());
		Assert.assertNotNull(poposition.getReport());
		Assert.assertNull(poposition.getReport().getFailureMessage());
		Assert.assertNotNull(poposition.getReport().getSuccessMessage());
	}

	@Test
	public void testSimpleComparableProposition_Calendar() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");
		yvf.setFactStructureList(Arrays.asList(fs1));

		SimpleDateFormat dateFormat = new SimpleDateFormat(BusinessRuleUtil.ISO_TIMESTAMP_FORMAT);
		Calendar cal = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
    	String calStr = dateFormat.format(cal.getTime()).toString();
		Map<String, Object> factMap = getFactMap(fs1, java.util.Calendar.class.getName(), calStr);
		
		YVFSimpleComparableProposition<Calendar> poposition = new YVFSimpleComparableProposition<Calendar>(
				"1", "YVFSimpleComparableProposition", 
				ComparisonOperator.EQUAL_TO, cal,
				yvf, factMap);

		Assert.assertTrue(poposition.apply());
		Assert.assertTrue(poposition.getResult());
		Assert.assertNotNull(poposition.getReport());
		Assert.assertNull(poposition.getReport().getFailureMessage());
		Assert.assertNotNull(poposition.getReport().getSuccessMessage());
	}

	@Test
	public void testSimpleComparableProposition_Calendar_LessThan() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");
		yvf.setFactStructureList(Arrays.asList(fs1));

		SimpleDateFormat dateFormat = new SimpleDateFormat(BusinessRuleUtil.ISO_TIMESTAMP_FORMAT);
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2100, 1, 1, 1, 0);
    	String calStr = dateFormat.format(cal1.getTime()).toString();
		Map<String, Object> factMap = getFactMap(fs1, java.util.Calendar.class.getName(), calStr);
		
		YVFSimpleComparableProposition<Calendar> poposition = new YVFSimpleComparableProposition<Calendar>(
				"1", "YVFSimpleComparableProposition", 
				ComparisonOperator.LESS_THAN, cal2,
				yvf, factMap);

		Assert.assertTrue(poposition.apply());
		Assert.assertTrue(poposition.getResult());
		Assert.assertNotNull(poposition.getReport());
		Assert.assertNull(poposition.getReport().getFailureMessage());
		Assert.assertNotNull(poposition.getReport().getSuccessMessage());
	}

	@Test
	public void testSimpleComparableProposition_Calendar_GreaterThan() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");
		yvf.setFactStructureList(Arrays.asList(fs1));

		SimpleDateFormat dateFormat = new SimpleDateFormat(BusinessRuleUtil.ISO_TIMESTAMP_FORMAT);
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2100, 1, 1, 1, 0);
    	String calStr = dateFormat.format(cal2.getTime()).toString();
		Map<String, Object> factMap = getFactMap(fs1, java.util.Calendar.class.getName(), calStr);
		
		YVFSimpleComparableProposition<Calendar> poposition = new YVFSimpleComparableProposition<Calendar>(
				"1", "YVFSimpleComparableProposition", 
				ComparisonOperator.GREATER_THAN, cal1,
				yvf, factMap);

		Assert.assertTrue(poposition.apply());
		Assert.assertTrue(poposition.getResult());
		Assert.assertNotNull(poposition.getReport());
		Assert.assertNull(poposition.getReport().getFailureMessage());
		Assert.assertNotNull(poposition.getReport().getSuccessMessage());
	}
}
