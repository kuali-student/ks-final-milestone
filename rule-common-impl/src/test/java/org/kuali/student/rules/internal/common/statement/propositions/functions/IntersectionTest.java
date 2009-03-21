package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.internal.common.statement.propositions.functions.Intersection.Operation;

public class IntersectionTest {

	@Test
	public void testIntersection() throws Exception {
		Function intersection = new Intersection<String>();
		intersection.setOperation(Operation.INTERSECTION.toString());
		Collection<String> criteria = Arrays.asList("MATH100", "CHEM100", "CPSC100");
		Collection<String> fact = Arrays.asList("MATH100", "PSYC100", "CPSC100");
		
		List<Collection<String>> list = new ArrayList<Collection<String>>();
		list.add(criteria);
		list.add(fact);
		
		intersection.setInput(list);
		intersection.compute();
		Collection<String> output = (Collection<String>) intersection.getOutput();
		
		Assert.assertEquals(2, output.size());
		Assert.assertEquals("[MATH100, CPSC100]", output.toString());
	}
}