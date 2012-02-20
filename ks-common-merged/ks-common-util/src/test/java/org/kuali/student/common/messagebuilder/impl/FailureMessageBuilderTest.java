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
import org.kuali.student.common.messagebuilder.impl.FailureMessageBuilder;
import org.kuali.student.common.messagebuilder.impl.exceptions.MessageBuilderException;

public class FailureMessageBuilderTest {
	private final static CommonTreeAdaptor adapter = new CommonTreeAdaptor();
	private FailureMessageBuilder failureMessageBuilder;

	@Before
	public void setUp() throws Exception {
		BooleanOperators bo = new BooleanOperators();
		failureMessageBuilder = new FailureMessageBuilder(bo);
	}
	
	@Test
	public void testBuildFailureMessageForSingleOrNode() throws Exception {
		// Rule: A OR B
		Token or = adapter.createToken(BooleanFunctionParser.OR, "+");
		Token a = adapter.createToken(BooleanFunctionParser.ALPHA, "A");
		Token b = adapter.createToken(BooleanFunctionParser.ALPHA, "B");
		
		// root node
		BooleanNode orNode = new BooleanNode(or);
		
		BooleanNode aNode = new BooleanNode(a);
		aNode.setParent(orNode);
		aNode.setValue(Boolean.FALSE);
		aNode.setNodeMessage("MATH101");

		BooleanNode bNode = new BooleanNode(b);
		bNode.setParent(orNode);
		bNode.setValue(Boolean.FALSE);
		bNode.setNodeMessage("MATH201");
		
		orNode.addChild(aNode); // left node
		orNode.addChild(bNode); // right node
		
		String failureMsg = failureMessageBuilder.buildFailureMessage(orNode);
		
		Assert.assertEquals(failureMsg, orNode.getNodeMessage());
		Assert.assertEquals("MATH101 OR MATH201", failureMsg);
	}

	@Test
	public void testBuildFailureMessageForSingleAnd1Node() throws Exception {
		// Rule: A AND B
		Token and = adapter.createToken(BooleanFunctionParser.AND, "*");
		Token a = adapter.createToken(BooleanFunctionParser.ALPHA, "A");
		Token b = adapter.createToken(BooleanFunctionParser.ALPHA, "B");
		
		// root node
		BooleanNode andNode = new BooleanNode(and);
		
		BooleanNode aNode = new BooleanNode(a);
		aNode.setParent(andNode);
		aNode.setValue(Boolean.TRUE);
		aNode.setNodeMessage("MATH101");

		BooleanNode bNode = new BooleanNode(b);
		bNode.setParent(andNode);
		bNode.setValue(Boolean.FALSE);
		bNode.setNodeMessage("MATH201");
		
		andNode.addChild(aNode); // left node
		andNode.addChild(bNode); // right node
		
		String failureMsg = failureMessageBuilder.buildFailureMessage(andNode);
		
		Assert.assertEquals(failureMsg, andNode.getNodeMessage());
		Assert.assertEquals("MATH201", failureMsg);
	}

	@Test
	public void testBuildFailureMessageForSingleAnd2Node() throws Exception {
		// Rule: A AND B
		Token and = adapter.createToken(BooleanFunctionParser.AND, "*");
		Token a = adapter.createToken(BooleanFunctionParser.ALPHA, "A");
		Token b = adapter.createToken(BooleanFunctionParser.ALPHA, "B");
		
		// root node
		BooleanNode andNode = new BooleanNode(and);
		
		BooleanNode aNode = new BooleanNode(a);
		aNode.setParent(andNode);
		aNode.setValue(Boolean.FALSE);
		aNode.setNodeMessage("MATH101");

		BooleanNode bNode = new BooleanNode(b);
		bNode.setParent(andNode);
		bNode.setValue(Boolean.FALSE);
		bNode.setNodeMessage("MATH201");
		
		andNode.addChild(aNode); // left node
		andNode.addChild(bNode); // right node
		
		String failureMsg = failureMessageBuilder.buildFailureMessage(andNode);
		
		Assert.assertEquals(failureMsg, andNode.getNodeMessage());
		Assert.assertEquals("MATH101 AND MATH201", failureMsg);
	}

	@Test
	public void testBuildFailureMessageForListOfNodes_Simple() throws Exception {
		// Rule: A AND (B OR C)
		Token and = adapter.createToken(BooleanFunctionParser.AND, "*");
		Token or = adapter.createToken(BooleanFunctionParser.OR, "+");
		Token a = adapter.createToken(BooleanFunctionParser.ALPHA, "A");
		Token b = adapter.createToken(BooleanFunctionParser.ALPHA, "B");
		Token c = adapter.createToken(BooleanFunctionParser.ALPHA, "C");
		
		// root node
		BooleanNode andNode = new BooleanNode(and);
		BooleanNode orNode = new BooleanNode(or);
		orNode.setParent(andNode);
		orNode.setValue(Boolean.FALSE);
		
		BooleanNode aNode = new BooleanNode(a);
		aNode.setParent(andNode);
		aNode.setValue(Boolean.FALSE);
		aNode.setNodeMessage("MATH101");

		BooleanNode bNode = new BooleanNode(b);
		bNode.setParent(orNode);
		bNode.setValue(Boolean.FALSE);
		bNode.setNodeMessage("MATH201");

		BooleanNode cNode = new BooleanNode(c);
		cNode.setParent(orNode);
		cNode.setValue(Boolean.FALSE);
		cNode.setNodeMessage("MATH301");
		
		orNode.addChild(bNode); // left node
		orNode.addChild(cNode); // right node
		andNode.addChild(aNode); // left node
		andNode.addChild(orNode); // right node
		
		// List of nodes order is important for building failure message
		List<BooleanNode> list = new ArrayList<BooleanNode>();
		list.add(cNode);
		list.add(bNode);
		list.add(orNode);
		list.add(aNode);
		list.add(andNode);

		String failureMsg = failureMessageBuilder.buildFailureMessage(list);
		
		Assert.assertEquals(failureMsg, andNode.getNodeMessage());
		Assert.assertEquals("MATH101 AND (MATH201 OR MATH301)", failureMsg);
	}

	@Test
	public void testBuildFailureMessageForListOfNodes_Complex() throws Exception {
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
		orNode2.setValue(Boolean.FALSE);
		
		BooleanNode aNode = new BooleanNode(a);
		aNode.setParent(orNode1);
		aNode.setValue(Boolean.TRUE);
		aNode.setNodeMessage("MATH101");

		BooleanNode bNode = new BooleanNode(b);
		bNode.setParent(orNode1);
		bNode.setValue(Boolean.TRUE);
		bNode.setNodeMessage("MATH201");

		BooleanNode cNode = new BooleanNode(c);
		cNode.setParent(orNode2);
		cNode.setValue(Boolean.FALSE);
		cNode.setNodeMessage("MATH301");
		
		BooleanNode dNode = new BooleanNode(d);
		dNode.setParent(orNode2);
		dNode.setValue(Boolean.FALSE);
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

		String failureMsg = failureMessageBuilder.buildFailureMessage(list);
		
		Assert.assertEquals(failureMsg, andNode.getNodeMessage());
		Assert.assertEquals("(MATH301 OR MATH401)", failureMsg);
	}

	@Test
	public void testBuildFailureMessageForListOfNodes_MultipleRootNodes() throws Exception {
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
			failureMessageBuilder.buildFailureMessage(list);
			Assert.fail("buildFailureMessage should have failed since list has 2 root nodes");
		} catch (MessageBuilderException e) {
			Assert.assertTrue(e.getMessage() != null);
			Assert.assertTrue(e.getMessage().contains("label=+"));
			Assert.assertTrue(e.getMessage().contains("label=*"));
		}
	}
}
