package org.kuali.student.common.messagebuilder.booleanmessage.ast;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanNode;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.parsers.BooleanFunctionParser;

public class BooleanNodeTest {

	private final static CommonTreeAdaptor adapter = new CommonTreeAdaptor();
	
	@Test
	public void testBooleanNode() throws Exception {
		Token and = adapter.createToken(BooleanFunctionParser.AND, "*");
		Token a = adapter.createToken(BooleanFunctionParser.ALPHA, "A");
		Token b = adapter.createToken(BooleanFunctionParser.ALPHA, "B");
		
		// root node
		BooleanNode andNode = new BooleanNode(and);
		
		BooleanNode aNode = new BooleanNode(a);
		aNode.setParent(andNode);
		aNode.setValue(Boolean.TRUE);
		aNode.setLanguage("en");
		aNode.setNodeMessage("MATH101");

		BooleanNode bNode = new BooleanNode(b);
		bNode.setParent(andNode);
		bNode.setValue(Boolean.FALSE);
		bNode.setNodeMessage("MATH201");
		
		andNode.addChild(aNode); // left node
		andNode.addChild(bNode); // right node
		
		Assert.assertEquals("en", aNode.getLanguage());
		Assert.assertEquals("MATH101", aNode.getNodeMessage());
		Assert.assertEquals(Boolean.TRUE, aNode.getValue());
		Assert.assertSame(andNode, aNode.getParent());
		Assert.assertSame(aNode, andNode.getLeftNode());
		Assert.assertSame(bNode, andNode.getRightNode());
	}
}
