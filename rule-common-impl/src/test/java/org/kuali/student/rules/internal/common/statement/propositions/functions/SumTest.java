package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.internal.common.statement.propositions.functions.Intersection.Operation;

public class SumTest {

	@Test
	public void testSum_Integer() throws Exception {
		Function sum = new Sum<Integer>();
		sum.setOperation(Operation.INTERSECTION.toString());
		Collection<Integer> fact = Arrays.asList(new Integer(1), new Integer(2));
		
		sum.setInput(fact);
		Number output1 = (Number) sum.compute();
		Number output2 = (Number) sum.getOutput();
		
		Assert.assertEquals(new Integer("3"), output1);
		Assert.assertEquals(new Integer("3"), output2);
	}

	@Test
	public void testSum_Double() throws Exception {
		Function sum = new Sum<Double>();
		sum.setOperation(Operation.INTERSECTION.toString());
		Collection<Double> fact = Arrays.asList(new Double(1.25), new Double(2.5));
		
		sum.setInput(fact);
		Number output1 = (Number) sum.compute();
		Number output2 = (Number) sum.getOutput();
		
		Assert.assertEquals(new Double("3.75"), output1);
		Assert.assertEquals(new Double("3.75"), output2);
	}

	@Test
	public void testSum_BigDecimal() throws Exception {
		Function sum = new Sum<BigDecimal>();
		sum.setOperation(Operation.INTERSECTION.toString());
		Collection<BigDecimal> fact = Arrays.asList(new BigDecimal("1.25"), new BigDecimal("2.5"));
		
		sum.setInput(fact);
		Number output1 = (Number) sum.compute();
		Number output2 = (Number) sum.getOutput();
		
		Assert.assertEquals(new BigDecimal("3.75"), output1);
		Assert.assertEquals(new BigDecimal("3.75"), output2);
	}

	@Test
	public void testSum_BigInteger() throws Exception {
		Function sum = new Sum<BigInteger>();
		sum.setOperation(Operation.INTERSECTION.toString());
		Collection<BigInteger> fact = Arrays.asList(new BigInteger("1"), new BigInteger("2"));
		
		sum.setInput(fact);
		Number output1 = (Number) sum.compute();
		Number output2 = (Number) sum.getOutput();
		
		Assert.assertEquals(new BigInteger("3"), output1);
		Assert.assertEquals(new BigInteger("3"), output2);
	}
}