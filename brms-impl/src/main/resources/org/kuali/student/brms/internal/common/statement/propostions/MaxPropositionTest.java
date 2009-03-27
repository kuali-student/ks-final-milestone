package org.kuali.student.brms.internal.common.statement.propostions;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionDTO;

public class MaxPropositionTest {
    private List<BigDecimal> gradeList = CommonTestUtil.createList("85.0,75.0,80.0");

	private RulePropositionDTO ruleProposition = new RulePropositionDTO();
	private Date date = new Date();

	@Test
    public void testMaxProposition_String() throws Exception {
    	MaxProposition<String> maxProp = new MaxProposition<String>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, 
    			new String("333"), Arrays.asList(new String[]{"1", "22", "333"}),
    			ruleProposition);

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains("333"));
    }
    
    @Test
    public void testMaxProposition_BigDecimal() throws Exception {
    	BigDecimal number = new BigDecimal(85.0);
    	MaxProposition<BigDecimal> maxProp = new MaxProposition<BigDecimal>(
    			"A-1", "A",
    			ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, number, gradeList,
    			ruleProposition);

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
        Assert.assertTrue(number.compareTo((BigDecimal) maxProp.getResultValues().iterator().next())==0);
    }

    @Test
    public void testMaxProposition_Double() throws Exception {
    	Double number = new Double(3.3);
    	MaxProposition<Double> maxProp = new MaxProposition<Double>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, number, 
    			Arrays.asList(new Double[]{new Double(1.1), new Double(2.2), new Double(3.3)}),
    			ruleProposition);

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(number));
    }

    @Test
    public void testMaxProposition_Integer() throws Exception {
    	Integer number = new Integer(3);
    	MaxProposition<Integer> maxProp = new MaxProposition<Integer>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, number, 
    			Arrays.asList(new Integer[]{new Integer(1), new Integer(2), new Integer(3)}),
    			ruleProposition);

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(number));
    }

    @Test
    public void testMaxProposition_Long() throws Exception {
    	Long number = new Long(3);
    	MaxProposition<Long> maxProp = new MaxProposition<Long>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, number, 
    			Arrays.asList(new Long[]{new Long(1), new Long(2), new Long(3)}),
    			ruleProposition);

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(number));
    }

    @Test
    public void testMaxProposition_Calendar() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
    	MaxProposition<Calendar> maxProp = new MaxProposition<Calendar>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, cal2, 
    			Arrays.asList(new Calendar[]{cal1, cal2}),
    			ruleProposition);

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(cal2));
    }

    @Test
    public void testMaxProposition_Calendar_LessThan() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
    	MaxProposition<Calendar> maxProp = new MaxProposition<Calendar>(
    			"A-1", "A",
    			ComparisonOperator.LESS_THAN, cal1, 
    			Arrays.asList(new Calendar[]{cal1, cal2}),
    			ruleProposition);

    	Boolean result = maxProp.apply();

    	Assert.assertFalse(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(cal2));
    }

    @Test
    public void testMaxProposition_Calendar_GreaterThan() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
    	MaxProposition<Calendar> maxProp = new MaxProposition<Calendar>(
    			"A-1", "A",
    			ComparisonOperator.GREATER_THAN, cal1, 
    			Arrays.asList(new Calendar[]{cal1, cal2}),
    			ruleProposition);

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(cal2));
    }

    @Test
    public void testMaxProposition_Date_EqualTo() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
    	MaxProposition<Date> maxProp = new MaxProposition<Date>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, cal2.getTime(), 
    			Arrays.asList(new Date[]{cal1.getTime(), cal2.getTime()}),
    			ruleProposition);

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(cal2.getTime()));
    }

    @Test
    public void testMaxProposition_Date_LessThan() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
    	MaxProposition<Date> maxProp = new MaxProposition<Date>(
    			"A-1", "A",
    			ComparisonOperator.LESS_THAN, cal1.getTime(), 
    			Arrays.asList(new Date[]{cal1.getTime(), cal2.getTime()}),
    			ruleProposition);

    	Boolean result = maxProp.apply();

    	Assert.assertFalse(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(cal2.getTime()));
    }

    @Test
    public void testMaxProposition_Date_GreaterThan() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
    	MaxProposition<Date> maxProp = new MaxProposition<Date>(
    			"A-1", "A",
    			ComparisonOperator.GREATER_THAN, cal1.getTime(), 
    			Arrays.asList(new Date[]{cal1.getTime(), cal2.getTime()}),
    			ruleProposition);

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(cal2.getTime()));
    }
}
