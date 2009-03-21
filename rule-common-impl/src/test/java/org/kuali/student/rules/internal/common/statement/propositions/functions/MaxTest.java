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

public class MaxTest {

	@Test
	public void testMax_Integer() throws Exception {
		Function max = new Max<Integer>();
		Collection<Integer> fact = Arrays.asList(new Integer(1), new Integer(2),new Integer(3));
		
		max.setInput(fact);
		Number output1 = (Number) max.compute();
		Number output2 = (Number) max.getOutput();
		
		Assert.assertEquals(new Integer(3), output1);
		Assert.assertEquals(new Integer(3), output2);
	}

	@Test
	public void testMax_Double() throws Exception {
		Function max = new Max<Double>();
		Collection<Double> fact = Arrays.asList(new Double(1.11), new Double(2.22),new Double(3.33));
		
		max.setInput(fact);
		Number output1 = (Number) max.compute();
		Number output2 = (Number) max.getOutput();
		
		Assert.assertEquals(new Double("3.33"), output1);
		Assert.assertEquals(new Double("3.33"), output2);
	}

	@Test
	public void testMax_BigDecimal() throws Exception {
		Function max = new Max<Integer>();
		Collection<BigDecimal> fact = Arrays.asList(new BigDecimal("1.23"), new BigDecimal("2.34"),new BigDecimal("3.45"));
		
		max.setInput(fact);
		Number output1 = (Number) max.compute();
		Number output2 = (Number) max.getOutput();
		
		Assert.assertEquals(new BigDecimal("3.45"), output1);
		Assert.assertEquals(new BigDecimal("3.45"), output2);
	}

	@Test
	public void testMax_BigInteger() throws Exception {
		Function max = new Max<BigInteger>();
		Collection<BigInteger> fact = Arrays.asList(new BigInteger("1"), new BigInteger("2"),new BigInteger("3"));
		
		max.setInput(fact);
		Number output1 = (Number) max.compute();
		Number output2 = (Number) max.getOutput();
		
		Assert.assertEquals(new BigInteger("3"), output1);
		Assert.assertEquals(new BigInteger("3"), output2);
	}

	@Test
	public void testMax_Date() throws Exception {
		Function max = new Max<BigInteger>();
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2100, 1, 1, 1, 0);
		Calendar cal3 = CommonTestUtil.createDate(2200, 1, 1, 1, 0);

		Collection<Date> fact = Arrays.asList(cal1.getTime(), cal2.getTime(), cal3.getTime());
		
		max.setInput(fact);
		Date output1 = (Date) max.compute();
		Date output2 = (Date) max.getOutput();
		
		Assert.assertEquals(cal3.getTime(), output1);
		Assert.assertEquals(cal3.getTime(), output2);
	}

	@Test
	public void testMax_Calendar() throws Exception {
		Function max = new Max<BigInteger>();
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2100, 1, 1, 1, 0);
		Calendar cal3 = CommonTestUtil.createDate(2200, 1, 1, 1, 0);

		Collection<Calendar> fact = Arrays.asList(cal1, cal2, cal3);
		
		max.setInput(fact);
		Calendar output1 = (Calendar) max.compute();
		Calendar output2 = (Calendar) max.getOutput();
		
		Assert.assertEquals(cal3, output1);
		Assert.assertEquals(cal3, output2);
	}
}