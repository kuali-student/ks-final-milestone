package org.kuali.student.common.messagebuilder.booleanmessage.ast;

import junit.framework.Assert;

import org.junit.Test;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanFunctionResult;

public class BooleanFunctionResultTest {

	@Test
	public void testBooleanFunctionResult() throws Exception {
		BooleanFunctionResult bfr = new BooleanFunctionResult("A*B", Boolean.TRUE, "A and B are true");

		Assert.assertEquals("A*B", bfr.getBooleanFunction());
		Assert.assertEquals(Boolean.TRUE, bfr.getResult());
		Assert.assertEquals("A and B are true", bfr.getMessage());
	}
}
