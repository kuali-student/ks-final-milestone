package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.internal.common.statement.propositions.functions.Intersection.Operation;

public class AverageTest {

	@Test
	public void testAverage_Integer() throws Exception {
		Function avg = new Average<Integer>();
		Collection<Integer> fact = Arrays.asList(new Integer(1), new Integer(2), new Integer(6));
		
		avg.setInput(fact);
		Number output1 = (Number) avg.compute();
		Number output2 = (Number) avg.getOutput();
		
		Assert.assertEquals(new BigDecimal("3"), output1);
		Assert.assertEquals(new BigDecimal("3"), output2);
	}

	@Test
	public void testAverage_Double() throws Exception {
		Function avg = new Average<Double>();
		Collection<Double> fact = Arrays.asList(new Double(1.22), new Double(2.33), new Double(3.44));
		
		avg.setInput(fact);
		Number output1 = (Number) avg.compute();
		Number output2 = (Number) avg.getOutput();
		
		Assert.assertEquals(new BigDecimal("2.33"), output1);
		Assert.assertEquals(new BigDecimal("2.33"), output2);
	}

	@Test
	public void testAverage_BigDecimal() throws Exception {
		Function avg = new Average<BigDecimal>();
		Collection<BigDecimal> fact = Arrays.asList(new BigDecimal(1.23), new BigDecimal(2.34), new BigDecimal(3.45));
		
		avg.setInput(fact);
		BigDecimal output1 = (BigDecimal) avg.compute();
		BigDecimal output2 = (BigDecimal) avg.getOutput();
		
		output1 = new BigDecimal(output1.toString(), new MathContext(3));
		output2 = new BigDecimal(output2.toString(), new MathContext(3));
		
		Assert.assertEquals(new BigDecimal("2.34").toString(), output1.toString());
		Assert.assertEquals(new BigDecimal("2.34").toString(), output2.toString());
	}

	@Test
	public void testAverage_BigInteger1() throws Exception {

		Function avg = new Average<BigInteger>();
		Collection<BigInteger> fact = Arrays.asList(new BigInteger("1"), new BigInteger("2"), new BigInteger("5"));
		
		avg.setInput(fact);
		BigDecimal output1 = (BigDecimal) avg.compute();
		BigDecimal output2 = (BigDecimal) avg.getOutput();
		
		output1 = new BigDecimal(output1.toString(), new MathContext(3));
		output2 = new BigDecimal(output2.toString(), new MathContext(3));

		Assert.assertEquals(new BigDecimal("2.67").toString(), output1.toString());
		Assert.assertEquals(new BigDecimal("2.67").toString(), output2.toString());
	}

	@Test
	public void testAverage_BigInteger2() throws Exception {

		Function avg = new Average<BigInteger>();
		Collection<BigInteger> fact = Arrays.asList(new BigInteger("1"), new BigInteger("2"), new BigInteger("4"));
		
		avg.setInput(fact);
		BigDecimal output1 = (BigDecimal) avg.compute();
		BigDecimal output2 = (BigDecimal) avg.getOutput();
		
		output1 = new BigDecimal(output1.toString(), new MathContext(3));
		output2 = new BigDecimal(output2.toString(), new MathContext(3));

		Assert.assertEquals(new BigDecimal("2.33").toString(), output1.toString());
		Assert.assertEquals(new BigDecimal("2.33").toString(), output2.toString());
	}
}