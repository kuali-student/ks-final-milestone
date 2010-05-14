package org.kuali.student.common.messagebuilder.booleanmessage.ast;

import junit.framework.Assert;

import org.junit.Test;
import org.kuali.student.common.messagebuilder.booleanmessage.BooleanMessage;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanMessageImpl;

public class BooleanMessageImplTest {

	@Test
	public void testBooleanMessage() throws Exception {
		BooleanMessage bm = new BooleanMessageImpl("123456", Boolean.FALSE, "A is true");

		Assert.assertEquals("123456", bm.getMessageId());
		Assert.assertFalse(bm.isSuccesful());
		Assert.assertEquals("A is true", bm.getMessage());
	}
}
