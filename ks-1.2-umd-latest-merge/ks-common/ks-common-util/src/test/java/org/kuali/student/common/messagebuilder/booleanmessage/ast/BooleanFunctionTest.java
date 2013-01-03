package org.kuali.student.common.messagebuilder.booleanmessage.ast;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanFunction;

public class BooleanFunctionTest {

	@Test
	public void testVariables() throws Exception {
		BooleanFunction bf = new BooleanFunction("(A*B)+(C*D)");
		List<String> variables = bf.getVariables();
		
		Assert.assertEquals(4, variables.size());
		Assert.assertTrue(variables.contains("A"));
		Assert.assertTrue(variables.contains("B"));
		Assert.assertTrue(variables.contains("C"));
		Assert.assertTrue(variables.contains("D"));
	}

	@Test
	public void testSymbols() throws Exception {
		BooleanFunction bf = new BooleanFunction("(A*B)+(C*D)");
		List<String> symbols = bf.getSymbols();
		
		Assert.assertEquals(11, symbols.size());
		Assert.assertTrue(symbols.contains("("));
		Assert.assertTrue(symbols.contains("A"));
		Assert.assertTrue(symbols.contains("*"));
		Assert.assertTrue(symbols.contains("B"));
		Assert.assertTrue(symbols.contains(")"));
		Assert.assertTrue(symbols.contains("+"));
		Assert.assertTrue(symbols.contains("("));
		Assert.assertTrue(symbols.contains("C"));
		Assert.assertTrue(symbols.contains("*"));
		Assert.assertTrue(symbols.contains("D"));
		Assert.assertTrue(symbols.contains(")"));
	}
}
