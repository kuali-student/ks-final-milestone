package org.kuali.student.common.messagebuilder.impl;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanNode;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.parsers.BooleanFunctionParser;
import org.kuali.student.common.messagebuilder.impl.BooleanOperators;
import org.kuali.student.common.messagebuilder.impl.SuccessFailureMessageBuilder;
import org.kuali.student.common.messagebuilder.impl.exceptions.MessageBuilderException;

public class SuccessFailureMessageBuilderTest {
	private final static CommonTreeAdaptor adapter = new CommonTreeAdaptor();
	private SuccessFailureMessageBuilder successFailureMessageBuilder;

	@Before
	public void setUp() throws Exception {
		BooleanOperators bo = new BooleanOperators();
		successFailureMessageBuilder = new SuccessFailureMessageBuilder(bo);
	}

	@Test
	public void testBuildFailureMessage() throws Exception {
		// Rule: (A OR B) AND (C OR D)
		Token and = adapter.createToken(BooleanFunctionParser.AND, "*");
		Token or1 = adapter.createToken(BooleanFunctionParser.OR, "+");
		Token or2 = adapter.createToken(BooleanFunctionParser.OR, "+");
		Token a = adapter.createToken(BooleanFunctionParser.ALPHA, "A");
		Token b = adapter.createToken(BooleanFunctionParser.ALPHA, "B");
		Token c = adapter.createToken(BooleanFunctionParser.ALPHA, "C");
		Token d = adapter.createToken(BooleanFunctionParser.ALPHA, "D");
		
		// root node
		BooleanNode andNode = new BooleanNode(and);
		
		BooleanNode orNode1 = new BooleanNode(or1);
		orNode1.setParent(andNode);
		orNode1.setValue(Boolean.TRUE);

		BooleanNode orNode2 = new BooleanNode(or2);
		orNode2.setParent(andNode);
		orNode2.setValue(Boolean.TRUE);
		
		BooleanNode aNode = new BooleanNode(a);
		aNode.setParent(orNode1);
		aNode.setValue(Boolean.TRUE);
		aNode.setNodeMessage("MATH101");

		BooleanNode bNode = new BooleanNode(b);
		bNode.setParent(orNode1);
		bNode.setValue(Boolean.FALSE);
		bNode.setNodeMessage("MATH201");

		BooleanNode cNode = new BooleanNode(c);
		cNode.setParent(orNode2);
		cNode.setValue(Boolean.FALSE);
		cNode.setNodeMessage("MATH301");
		
		BooleanNode dNode = new BooleanNode(d);
		dNode.setParent(orNode2);
		dNode.setValue(Boolean.TRUE);
		dNode.setNodeMessage("MATH401");

		orNode1.addChild(aNode); // left node
		orNode1.addChild(bNode); // right node
		orNode2.addChild(cNode); // left node
		orNode2.addChild(dNode); // right node
		andNode.addChild(orNode1); // left node
		andNode.addChild(orNode2); // right node
		
		// List of nodes order is important for building failure message
		List<BooleanNode> list = new ArrayList<BooleanNode>();
		list.add(aNode);
		list.add(bNode);
		list.add(cNode);
		list.add(dNode);
		list.add(orNode1);
		list.add(orNode2);
		list.add(andNode);

		String failureMsg = successFailureMessageBuilder.buildMessage(list);
		
		Assert.assertEquals(failureMsg, andNode.getNodeMessage());
		Assert.assertEquals("MATH101 AND MATH401", failureMsg);
	}

	@Test
	public void testBuildSuccessMessageForListOfNodes_Complex() throws Exception {
		// Rule: (A OR B) AND (C OR D)
		Token and = adapter.createToken(BooleanFunctionParser.AND, "*");
		Token or1 = adapter.createToken(BooleanFunctionParser.OR, "+");
		Token or2 = adapter.createToken(BooleanFunctionParser.OR, "+");
		Token a = adapter.createToken(BooleanFunctionParser.ALPHA, "A");
		Token b = adapter.createToken(BooleanFunctionParser.ALPHA, "B");
		Token c = adapter.createToken(BooleanFunctionParser.ALPHA, "C");
		Token d = adapter.createToken(BooleanFunctionParser.ALPHA, "D");
		
		// root node
		BooleanNode andNode = new BooleanNode(and);
		
		BooleanNode orNode1 = new BooleanNode(or1);
		orNode1.setParent(andNode);
		orNode1.setValue(Boolean.TRUE);

		BooleanNode orNode2 = new BooleanNode(or2);
		orNode2.setParent(andNode);
		orNode2.setValue(Boolean.TRUE);
		
		BooleanNode aNode = new BooleanNode(a);
		aNode.setParent(orNode1);
		aNode.setValue(Boolean.TRUE);
		aNode.setNodeMessage("MATH101");

		BooleanNode bNode = new BooleanNode(b);
		bNode.setParent(orNode1);
		bNode.setValue(Boolean.FALSE);
		bNode.setNodeMessage("MATH201");

		BooleanNode cNode = new BooleanNode(c);
		cNode.setParent(orNode2);
		cNode.setValue(Boolean.FALSE);
		cNode.setNodeMessage("MATH301");
		
		BooleanNode dNode = new BooleanNode(d);
		dNode.setParent(orNode2);
		dNode.setValue(Boolean.TRUE);
		dNode.setNodeMessage("MATH401");

		orNode1.addChild(aNode); // left node
		orNode1.addChild(bNode); // right node
		orNode2.addChild(cNode); // left node
		orNode2.addChild(dNode); // right node
		andNode.addChild(orNode1); // left node
		andNode.addChild(orNode2); // right node
		
		// List of nodes order is important for building failure message
		List<BooleanNode> list = new ArrayList<BooleanNode>();
		list.add(aNode);
		list.add(bNode);
		list.add(cNode);
		list.add(dNode);
		list.add(orNode1);
		list.add(orNode2);
		list.add(andNode);

		String failureMsg = successFailureMessageBuilder.buildMessage(list);
		
		Assert.assertEquals(failureMsg, andNode.getNodeMessage());
		Assert.assertEquals("MATH101 AND MATH401", failureMsg);
	}

	@Test
	public void testBuildSuccessMessageForListOfNodes_MultipleRootNodes() throws Exception {
		Token or = adapter.createToken(BooleanFunctionParser.OR, "+");
		Token and = adapter.createToken(BooleanFunctionParser.AND, "*");
		
		// root node1
		BooleanNode orNode = new BooleanNode(or);
		// root node1
		BooleanNode andNode = new BooleanNode(and);
		
		List<BooleanNode> list = new ArrayList<BooleanNode>();
		list.add(orNode);
		list.add(andNode);

		try {
			successFailureMessageBuilder.buildMessage(list);
			Assert.fail("buildMessage should have failed since list has 2 root nodes");
		} catch (MessageBuilderException e) {
			Assert.assertTrue(e.getMessage() != null);
			Assert.assertTrue(e.getMessage().contains("label=+"));
			Assert.assertTrue(e.getMessage().contains("label=*"));
		}
	}
}
