package org.kuali.student.brms.internal.common.statement.propositions.functions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;

public class MinTest {

	@Test
	public void testX() throws Exception {
	}
	
	@Test
	public void testMin_Integer() throws Exception {
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Integer.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "5"}, 
    			new String[] {"resultColumn.cluId", "resultColumn.credits"});

		Min<Integer> min = new Min<Integer>(fact, "resultColumn.credits");
		Number output1 = min.compute();
		
		Assert.assertEquals(new Integer(3), output1);
	}

	@Test
	public void testMin_Double() throws Exception {
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Double.class.getName()},
				new String[] {"MATH101", "3.1", "MATH103", "4.2", "CHEM101", "5.3"}, 
    			new String[] {"resultColumn.cluId", "resultColumn.credits"});

		Min<Double> min = new Min<Double>(fact, "resultColumn.credits");
		Number output = min.compute();
		
		Assert.assertEquals(new Double("3.1"), output);
	}

	@Test
	public void testMin_BigDecimal() throws Exception {
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), BigDecimal.class.getName()},
				new String[] {"MATH101", "3.1", "MATH103", "4.2", "CHEM101", "5.3"}, 
    			new String[] {"resultColumn.cluId", "resultColumn.credits"});

		Min<BigDecimal> min = new Min<BigDecimal>(fact, "resultColumn.credits");
		Number output = min.compute();
		
		Assert.assertEquals(new BigDecimal("3.1"), output);
	}

	@Test
	public void testMin_BigInteger() throws Exception {
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), BigInteger.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "5"}, 
    			new String[] {"resultColumn.cluId", "resultColumn.credits"});

		Min<BigInteger> min = new Min<BigInteger>(fact, "resultColumn.credits");
		Number output = min.compute();
		
		Assert.assertEquals(new BigInteger("3"), output);
	}

	@Test
	public void testMin_Date() throws Exception {
		Date cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0).getTime();
		Date cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0).getTime();
		Date cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0).getTime();

		String date1 = BusinessRuleUtil.formatIsoDate(cal1);
		String date2 = BusinessRuleUtil.formatIsoDate(cal2);
		String date3 = BusinessRuleUtil.formatIsoDate(cal3);
    	
    	FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Date.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

		Min<Date> min = new Min<Date>(fact, "resultColumn.date");
		Date output1 = min.compute();
		
		Assert.assertEquals(cal1, output1);
	}

	@Test
	public void testMin_Calendar() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
		Calendar cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0);

		String date1 = BusinessRuleUtil.formatIsoDate(cal1.getTime());
		String date2 = BusinessRuleUtil.formatIsoDate(cal2.getTime());
		String date3 = BusinessRuleUtil.formatIsoDate(cal3.getTime());
    	
    	FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Calendar.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

		Min<Calendar> min = new Min<Calendar>(fact, "resultColumn.date");
		Calendar output = min.compute();
		
		Assert.assertEquals(cal1, output);
	}
}