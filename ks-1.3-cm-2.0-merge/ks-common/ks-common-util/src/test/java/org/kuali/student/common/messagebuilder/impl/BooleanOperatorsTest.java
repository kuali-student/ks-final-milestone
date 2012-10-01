package org.kuali.student.common.messagebuilder.impl;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.messagebuilder.impl.BooleanOperators;

public class BooleanOperatorsTest {
	
	@Test
	public void testBooleanOperators1() throws Exception {
		BooleanOperators bo = new BooleanOperators();
		Assert.assertEquals("AND", bo.getAndOperator());
		Assert.assertEquals("OR", bo.getOrOperator());
	}

	@Test
	public void testBooleanOperators2() throws Exception {
		BooleanOperators bo = new BooleanOperators("und", "oder");
		Assert.assertEquals("und", bo.getAndOperator());
		Assert.assertEquals("oder", bo.getOrOperator());
	}
}
