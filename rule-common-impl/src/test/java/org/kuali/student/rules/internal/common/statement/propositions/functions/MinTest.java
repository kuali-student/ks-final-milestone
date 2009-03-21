package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;

public class MinTest {

	@Test
	public void testMin_Integer() throws Exception {
		Function min = new Min<Integer>();
		Collection<Integer> fact = Arrays.asList(new Integer(1), new Integer(2),new Integer(3));
		
		min.setInput(fact);
		Number output1 = (Number) min.compute();
		Number output2 = (Number) min.getOutput();
		
		Assert.assertEquals(new Integer(1), output1);
		Assert.assertEquals(new Integer(1), output2);
	}

	@Test
	public void testMin_Double() throws Exception {
		Function min = new Min<Double>();
		Collection<Double> fact = Arrays.asList(new Double(1.11), new Double(2.22),new Double(3.33));
		
		min.setInput(fact);
		Number output1 = (Number) min.compute();
		Number output2 = (Number) min.getOutput();
		
		Assert.assertEquals(new Double("1.11"), output1);
		Assert.assertEquals(new Double("1.11"), output2);
	}

	@Test
	public void testMin_BigDecimal() throws Exception {
		Function min = new Min<Integer>();
		Collection<BigDecimal> fact = Arrays.asList(new BigDecimal("1.23"), new BigDecimal("2.34"),new BigDecimal("3.45"));
		
		min.setInput(fact);
		Number output1 = (Number) min.compute();
		Number output2 = (Number) min.getOutput();
		
		Assert.assertEquals(new BigDecimal("1.23"), output1);
		Assert.assertEquals(new BigDecimal("1.23"), output2);
	}

	@Test
	public void testMin_BigInteger() throws Exception {
		Function min = new Min<BigInteger>();
		Collection<BigInteger> fact = Arrays.asList(new BigInteger("1"), new BigInteger("2"),new BigInteger("3"));
		
		min.setInput(fact);
		Number output1 = (Number) min.compute();
		Number output2 = (Number) min.getOutput();
		
		Assert.assertEquals(new BigInteger("1"), output1);
		Assert.assertEquals(new BigInteger("1"), output2);
	}

	@Test
	public void testMin_Date() throws Exception {
		Function min = new Min<BigInteger>();
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2100, 1, 1, 1, 0);
		Calendar cal3 = CommonTestUtil.createDate(2200, 1, 1, 1, 0);

		Collection<Date> fact = Arrays.asList(cal1.getTime(), cal2.getTime(), cal3.getTime());
		
		min.setInput(fact);
		Date output1 = (Date) min.compute();
		Date output2 = (Date) min.getOutput();
		
		Assert.assertEquals(cal1.getTime(), output1);
		Assert.assertEquals(cal1.getTime(), output2);
	}

	@Test
	public void testMin_Calendar() throws Exception {
		Function min = new Min<BigInteger>();
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2100, 1, 1, 1, 0);
		Calendar cal3 = CommonTestUtil.createDate(2200, 1, 1, 1, 0);

		Collection<Calendar> fact = Arrays.asList(cal1, cal2, cal3);
		
		min.setInput(fact);
		Calendar output1 = (Calendar) min.compute();
		Calendar output2 = (Calendar) min.getOutput();
		
		Assert.assertEquals(cal1, output1);
		Assert.assertEquals(cal1, output2);
	}
}