package org.kuali.student.rules.internal.common.statement.propositions;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;

public class MaxPropositionTest {

	@Test
    public void testMaxProposition_String() throws Exception {
		FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "5"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

    	MaxProposition<String> maxProp = new MaxProposition<String>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, 
    			new String("5"), fact, "resultColumn.credits");

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains("5"));
    }
    
    @Test
    public void testMaxProposition_BigDecimal() throws Exception {
		FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), BigDecimal.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "5"}, 
    			new String[] {"resultColumn.cluId", "resultColumn.credits"});

    	BigDecimal number = new BigDecimal("5.0");
    	MaxProposition<BigDecimal> maxProp = new MaxProposition<BigDecimal>(
    			"A-1", "A", ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, number, 
    			fact, "resultColumn.credits");

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
        Assert.assertTrue(number.compareTo((BigDecimal) maxProp.getResultValues().iterator().next()) == 0);
    }

    @Test
    public void testMaxProposition_Double() throws Exception {
		FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Double.class.getName()},
				new String[] {"MATH101", "3.1", "MATH103", "4.2", "CHEM101", "5.3"}, 
    			new String[] {"resultColumn.cluId", "resultColumn.credits"});

		Double number = new Double(5.3);
    	MaxProposition<Double> maxProp = new MaxProposition<Double>(
    			"A-1", "A", ComparisonOperator.EQUAL_TO, number, 
    			fact, "resultColumn.credits");

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(number));
    }

    @Test
    public void testMaxProposition_Integer() throws Exception {
		FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Integer.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "5"}, 
    			new String[] {"resultColumn.cluId", "resultColumn.credits"});

    	Integer number = new Integer(5);
    	MaxProposition<Integer> maxProp = new MaxProposition<Integer>(
    			"A-1", "A", ComparisonOperator.EQUAL_TO, number, 
    			fact, "resultColumn.credits");

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(number));
    }

    @Test
    public void testMaxProposition_Long() throws Exception {
		FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Long.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "5"}, 
    			new String[] {"resultColumn.cluId", "resultColumn.credits"});

    	Long number = new Long(5);
    	MaxProposition<Long> maxProp = new MaxProposition<Long>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, number, 
    			fact, "resultColumn.credits");

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(number));
    }

    @Test
    public void testMaxProposition_Calendar() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
		Calendar cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0);

		String date1 = BusinessRuleUtil.formatIsoDate(cal1.getTime());
		String date2 = BusinessRuleUtil.formatIsoDate(cal2.getTime());
		String date3 = BusinessRuleUtil.formatIsoDate(cal3.getTime());
    	
    	FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Calendar.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

    	MaxProposition<Calendar> maxProp = new MaxProposition<Calendar>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO, cal3, 
    			fact, "resultColumn.date");

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(cal3));
    }

    @Test
    public void testMaxProposition_Calendar_LessThan() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
		Calendar cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0);

		String date1 = BusinessRuleUtil.formatIsoDate(cal1.getTime());
		String date2 = BusinessRuleUtil.formatIsoDate(cal2.getTime());
		String date3 = BusinessRuleUtil.formatIsoDate(cal3.getTime());
    	
    	FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Calendar.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

    	MaxProposition<Calendar> maxProp = new MaxProposition<Calendar>(
    			"A-1", "A", ComparisonOperator.LESS_THAN, cal1, 
    			fact, "resultColumn.date");

    	Boolean result = maxProp.apply();

    	Assert.assertFalse(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(cal3));
    }

    @Test
    public void testMaxProposition_Calendar_GreaterThan() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
		Calendar cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0);

		String date1 = BusinessRuleUtil.formatIsoDate(cal1.getTime());
		String date2 = BusinessRuleUtil.formatIsoDate(cal2.getTime());
		String date3 = BusinessRuleUtil.formatIsoDate(cal3.getTime());
    	
    	FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Calendar.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

    	MaxProposition<Calendar> maxProp = new MaxProposition<Calendar>(
    			"A-1", "A", ComparisonOperator.GREATER_THAN, cal1, 
    			fact, "resultColumn.date");

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(cal3));
    }

    @Test
    public void testMaxProposition_Date_EqualTo() throws Exception {
		Date cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0).getTime();
		Date cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0).getTime();
		Date cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0).getTime();

		String date1 = BusinessRuleUtil.formatIsoDate(cal1);
		String date2 = BusinessRuleUtil.formatIsoDate(cal2);
		String date3 = BusinessRuleUtil.formatIsoDate(cal3);
    	
    	FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Date.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

    	MaxProposition<Date> maxProp = new MaxProposition<Date>(
    			"A-1", "A", ComparisonOperator.EQUAL_TO, cal3, 
    			fact, "resultColumn.date");

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(cal3));
    }

    @Test
    public void testMaxProposition_Date_LessThan() throws Exception {
		Date cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0).getTime();
		Date cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0).getTime();
		Date cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0).getTime();

		String date1 = BusinessRuleUtil.formatIsoDate(cal1);
		String date2 = BusinessRuleUtil.formatIsoDate(cal2);
		String date3 = BusinessRuleUtil.formatIsoDate(cal3);
    	
    	FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Date.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

    	MaxProposition<Date> maxProp = new MaxProposition<Date>(
    			"A-1", "A", ComparisonOperator.LESS_THAN, cal1, 
    			fact, "resultColumn.date");

    	Boolean result = maxProp.apply();

    	Assert.assertFalse(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(cal3));
    }

    @Test
    public void testMaxProposition_Date_GreaterThan() throws Exception {
		Date cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0).getTime();
		Date cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0).getTime();
		Date cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0).getTime();

		String date1 = BusinessRuleUtil.formatIsoDate(cal1);
		String date2 = BusinessRuleUtil.formatIsoDate(cal2);
		String date3 = BusinessRuleUtil.formatIsoDate(cal3);
    	
    	FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Date.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

    	MaxProposition<Date> maxProp = new MaxProposition<Date>(
    			"A-1", "A", ComparisonOperator.GREATER_THAN, cal1, 
    			fact, "resultColumn.date");

    	Boolean result = maxProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(maxProp.getResultValues().contains(cal3));
    }
}
