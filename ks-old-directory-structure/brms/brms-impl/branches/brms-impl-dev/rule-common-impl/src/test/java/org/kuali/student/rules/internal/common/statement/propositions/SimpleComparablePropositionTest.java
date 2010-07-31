package org.kuali.student.rules.internal.common.statement.propositions;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

public class SimpleComparablePropositionTest {

	@Test
    public void testSimpleComparableProposition_String() throws Exception {
    	SimpleComparableProposition<String> comparableProp = new SimpleComparableProposition<String>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, "Hi Kuali", "Hi Kuali");

    	Boolean result = comparableProp.apply();

        Assert.assertTrue(result);
    	Assert.assertTrue(comparableProp.getResultValues().contains(new Boolean(true)));
    }
    
    @Test
    public void testSimpleComparableProposition_Double() throws Exception {
    	Double number = new Double("100.1");
    	SimpleComparableProposition<Double> comparableProp = new SimpleComparableProposition<Double>(
    			"A-1", "A",
    			ComparisonOperator.LESS_THAN, number, new Double(100d));

    	Boolean result = comparableProp.apply();

        Assert.assertTrue(result);
		Assert.assertTrue(comparableProp.getResultValues().contains(new Boolean(true)));
    }
    
    @Test
    public void testSimpleComparableProposition_Integer() throws Exception {
    	SimpleComparableProposition<Integer> comparableProp = new SimpleComparableProposition<Integer>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, new Integer("100"), new Integer(100));

    	Boolean result = comparableProp.apply();

        Assert.assertTrue(result);
		Assert.assertTrue(comparableProp.getResultValues().contains(new Boolean(true)));
    }
    
    @Test
    public void testSimpleComparableProposition_Long() throws Exception {
    	SimpleComparableProposition<Long> comparableProp = new SimpleComparableProposition<Long>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, new Long("1000000000000000000"), new Long(1000000000000000000l));

    	Boolean result = comparableProp.apply();

        Assert.assertTrue(result);
		Assert.assertTrue(comparableProp.getResultValues().contains(new Boolean(true)));
    }
    
    @Test
    public void testSimpleComparableProposition_BigDecimal() throws Exception {
    	SimpleComparableProposition<BigDecimal> comparableProp = new SimpleComparableProposition<BigDecimal>(
    			"A-1", "A",
    			ComparisonOperator.GREATER_THAN, new BigDecimal(123.456789), new BigDecimal(999));

    	Boolean result = comparableProp.apply();

        Assert.assertTrue(result);
		Assert.assertTrue(comparableProp.getResultValues().contains(new Boolean(true)));
    }
    
    @Test
    public void testSimpleComparableProposition_Calendar_EqualTo() throws Exception {
		Calendar cal = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
    	SimpleComparableProposition<Calendar> comparableProp = new SimpleComparableProposition<Calendar>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, cal, cal);

    	Boolean result = comparableProp.apply();

        Assert.assertTrue(result);
		Assert.assertTrue(comparableProp.getResultValues().contains(new Boolean(true)));
    }
    
    @Test
    public void testSimpleComparableProposition_Calendar_LessThan() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
    	SimpleComparableProposition<Calendar> comparableProp = new SimpleComparableProposition<Calendar>(
    			"A-1", "A",
    			ComparisonOperator.LESS_THAN, cal2, cal1);

    	Boolean result = comparableProp.apply();

        Assert.assertTrue(result);
		Assert.assertTrue(comparableProp.getResultValues().contains(new Boolean(true)));
    }
    
    @Test
    public void testSimpleComparableProposition_Calendar_GreaterThan() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
    	SimpleComparableProposition<Calendar> comparableProp = new SimpleComparableProposition<Calendar>(
    			"A-1", "A",
    			ComparisonOperator.GREATER_THAN, cal1, cal2);

    	Boolean result = comparableProp.apply();

        Assert.assertTrue(result);
		Assert.assertTrue(comparableProp.getResultValues().contains(new Boolean(true)));
    }
    
    @Test
    public void testSimpleComparableProposition_Date_EqualTo() throws Exception {
		Calendar cal = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
    	SimpleComparableProposition<Date> comparableProp = new SimpleComparableProposition<Date>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, cal.getTime(), cal.getTime());

    	Boolean result = comparableProp.apply();

        Assert.assertTrue(result);
		Assert.assertTrue(comparableProp.getResultValues().contains(new Boolean(true)));
    }
    
    @Test
    public void testSimpleComparableProposition_Date_LessThan() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
    	SimpleComparableProposition<Date> comparableProp = new SimpleComparableProposition<Date>(
    			"A-1", "A",
    			ComparisonOperator.LESS_THAN, cal2.getTime(), cal1.getTime());

    	Boolean result = comparableProp.apply();

        Assert.assertTrue(result);
		Assert.assertTrue(comparableProp.getResultValues().contains(new Boolean(true)));
    }
    
    @Test
    public void testSimpleComparableProposition_Date_GreaterThan() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
    	SimpleComparableProposition<Date> comparableProp = new SimpleComparableProposition<Date>(
    			"A-1", "A",
    			ComparisonOperator.GREATER_THAN, cal1.getTime(), cal2.getTime());

    	Boolean result = comparableProp.apply();

        Assert.assertTrue(result);
		Assert.assertTrue(comparableProp.getResultValues().contains(new Boolean(true)));
    }
}
