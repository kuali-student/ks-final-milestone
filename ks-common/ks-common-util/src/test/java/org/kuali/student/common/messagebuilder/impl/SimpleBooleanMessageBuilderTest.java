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
import org.kuali.student.common.messagebuilder.impl.exceptions.MessageBuilderException;

public class SimpleBooleanMessageBuilderTest {
	private final static CommonTreeAdaptor adapter = new CommonTreeAdaptor();
	private SimpleBooleanMessageBuilder defaultMessageBuilder;
	private SimpleBooleanMessageBuilder configuredMessageBuilder;

	@Before
	public void setUp() throws Exception {
		BooleanOperators bo = new BooleanOperators("AND", "OR");
		defaultMessageBuilder = new SimpleBooleanMessageBuilder(bo);

		configuredMessageBuilder = new SimpleBooleanMessageBuilder(bo);
		configuredMessageBuilder.setIndentCharacter(32);
		configuredMessageBuilder.setIndentNumberOfSpaces(4);
		configuredMessageBuilder.setIndentString("    ");
		configuredMessageBuilder.setBooleanOperatorPrefix("\n");
		configuredMessageBuilder.setBooleanOperatorSuffix("\n");
	}
	
	@Test
	public void testBuildMessage_DefaultConfig_SingleAndNode() throws Exception {
		// Rule: A AND B
		Token and = adapter.createToken(BooleanFunctionParser.AND, "*");
		Token a = adapter.createToken(BooleanFunctionParser.ALPHA, "A");
		Token b = adapter.createToken(BooleanFunctionParser.ALPHA, "B");
		
		BooleanNode andNode = new BooleanNode(and);
		
		BooleanNode aNode = new BooleanNode(a);
		aNode.setParent(andNode);
		aNode.setNodeMessage("MATH101");

		BooleanNode bNode = new BooleanNode(b);
		bNode.setParent(andNode);
		bNode.setNodeMessage("MATH201");
		
		andNode.addChild(aNode); // left node
		andNode.addChild(bNode); // right node
		
		String msg = defaultMessageBuilder.buildMessage(andNode);
		
		Assert.assertEquals("MATH101 AND MATH201", msg);
	}

	@Test
	public void testBuildMessage_DefaultConfig_ForListOfNodes() throws Exception {
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
		
		BooleanNode aNode = new BooleanNode(a);
		aNode.setParent(andNode);
		aNode.setNodeMessage("MATH101");

		BooleanNode bNode = new BooleanNode(b);
		bNode.setParent(orNode);
		bNode.setNodeMessage("MATH201");

		BooleanNode cNode = new BooleanNode(c);
		cNode.setParent(orNode);
		cNode.setNodeMessage("MATH301");
		
		orNode.addChild(bNode); // left node
		orNode.addChild(cNode); // right node
		andNode.addChild(aNode); // left node
		andNode.addChild(orNode); // right node
		
		// List of nodes order is important for building success message
		List<BooleanNode> list = new ArrayList<BooleanNode>();
		list.add(cNode);
		list.add(bNode);
		list.add(orNode);
		list.add(aNode);
		list.add(andNode);

		defaultMessageBuilder.buildMessage(list);

		Assert.assertEquals("MATH101 AND (MATH201 OR MATH301)", andNode.getNodeMessage());
	}

	@Test
	public void testBuildMessage_ForSingleAndNode() throws Exception {
		// Rule: A AND B
		Token and = adapter.createToken(BooleanFunctionParser.AND, "*");
		Token a = adapter.createToken(BooleanFunctionParser.ALPHA, "A");
		Token b = adapter.createToken(BooleanFunctionParser.ALPHA, "B");
		
		BooleanNode andNode = new BooleanNode(and);
		
		BooleanNode aNode = new BooleanNode(a);
		aNode.setParent(andNode);
		aNode.setNodeMessage("MATH101");

		BooleanNode bNode = new BooleanNode(b);
		bNode.setParent(andNode);
		bNode.setNodeMessage("MATH201");
		
		andNode.addChild(aNode); // left node
		andNode.addChild(bNode); // right node
		
		String msg = configuredMessageBuilder.buildMessage(andNode);
		
		Assert.assertEquals("    MATH101\nAND\n    MATH201", msg);
	}

	@Test
	public void testBuildMessage_ForSingleOrNode() throws Exception {
		// Rule: A OR B
		Token and = adapter.createToken(BooleanFunctionParser.OR, "+");
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
		
		String msg = configuredMessageBuilder.buildMessage(andNode);
		
		Assert.assertEquals("    MATH101\nOR\n    MATH201", msg);
	}

	@Test
	public void testBuildMessage_ForListOfNodes_Complex() throws Exception {
		// Rule: (A AND B) OR (C AND (D OR E))
		Token or1 = adapter.createToken(BooleanFunctionParser.OR, "+");
		Token or2 = adapter.createToken(BooleanFunctionParser.OR, "+");
		Token and1 = adapter.createToken(BooleanFunctionParser.AND, "*");
		Token and2 = adapter.createToken(BooleanFunctionParser.AND, "*");
		Token a = adapter.createToken(BooleanFunctionParser.ALPHA, "A");
		Token b = adapter.createToken(BooleanFunctionParser.ALPHA, "B");
		Token c = adapter.createToken(BooleanFunctionParser.ALPHA, "C");
		Token d = adapter.createToken(BooleanFunctionParser.ALPHA, "D");
		Token e = adapter.createToken(BooleanFunctionParser.ALPHA, "E");
		
		// root node
		BooleanNode orNode1 = new BooleanNode(or1);

		BooleanNode andNode1 = new BooleanNode(and1);
		andNode1.setParent(orNode1);

		BooleanNode andNode2 = new BooleanNode(and2);
		andNode2.setParent(orNode1);
		
		BooleanNode orNode2 = new BooleanNode(or2);
		orNode2.setParent(andNode2);
		
		BooleanNode aNode = new BooleanNode(a);
		aNode.setParent(andNode1);
		aNode.setNodeMessage("MATH101");

		BooleanNode bNode = new BooleanNode(b);
		bNode.setParent(andNode1);
		bNode.setNodeMessage("MATH102");

		BooleanNode cNode = new BooleanNode(c);
		cNode.setParent(andNode2);
		cNode.setNodeMessage("MATH201");
		
		BooleanNode dNode = new BooleanNode(d);
		dNode.setParent(orNode2);
		dNode.setNodeMessage("MATH202");

		BooleanNode eNode = new BooleanNode(e);
		eNode.setParent(orNode2);
		eNode.setNodeMessage("MATH301");
		
		orNode1.addChild(andNode1); // left node
		orNode1.addChild(andNode2); // right node
		andNode1.addChild(aNode); // left node
		andNode1.addChild(bNode); // right node
		andNode2.addChild(cNode); // left node
		andNode2.addChild(orNode2); // right node
		orNode2.addChild(dNode); // right node
		orNode2.addChild(eNode); // right node
		
		// List of nodes order is important for building success message
		List<BooleanNode> list = new ArrayList<BooleanNode>();
		list.add(dNode);
		list.add(eNode);
		list.add(orNode2);
		list.add(aNode);
		list.add(bNode);
		list.add(cNode);
		list.add(andNode1);
		list.add(andNode2);
		list.add(orNode1);

		String successMsg = configuredMessageBuilder.buildMessage(list);

		Assert.assertEquals(successMsg, orNode1.getNodeMessage());
		Assert.assertEquals(
				"        (MATH101" +
				"\n    AND" +
				"\n        MATH102)" +
				"\nOR" +
				"\n        (MATH201" +
				"\n    AND" +
				"\n            (MATH202" +
				"\n        OR" +
				"\n            MATH301))",
				successMsg);
	}

	@Test
	public void testBuildMessage_ForListOfNodes_Complex2() throws Exception {
		// Rule: (A OR (B AND (C OR E))
		Token or1 = adapter.createToken(BooleanFunctionParser.OR, "+");
		Token or2 = adapter.createToken(BooleanFunctionParser.OR, "+");
		Token and1 = adapter.createToken(BooleanFunctionParser.AND, "*");
		Token a = adapter.createToken(BooleanFunctionParser.ALPHA, "A");
		Token b = adapter.createToken(BooleanFunctionParser.ALPHA, "B");
		Token c = adapter.createToken(BooleanFunctionParser.ALPHA, "C");
		Token d = adapter.createToken(BooleanFunctionParser.ALPHA, "D");
		
		// root node
		BooleanNode orNode1 = new BooleanNode(or1);

		BooleanNode andNode1 = new BooleanNode(and1);
		andNode1.setParent(orNode1);

		BooleanNode orNode2 = new BooleanNode(or2);
		orNode2.setParent(andNode1);
		
		BooleanNode aNode = new BooleanNode(a);
		aNode.setParent(orNode1);
		aNode.setNodeMessage("MATH101");

		BooleanNode bNode = new BooleanNode(b);
		bNode.setParent(andNode1);
		bNode.setNodeMessage("MATH201");

		BooleanNode cNode = new BooleanNode(c);
		cNode.setParent(orNode2);
		cNode.setNodeMessage("MATH301");
		
		BooleanNode dNode = new BooleanNode(d);
		dNode.setParent(orNode2);
		dNode.setNodeMessage("MATH401");

		orNode1.addChild(aNode); // left node
		orNode1.addChild(andNode1); // right node
		andNode1.addChild(bNode); // left node
		andNode1.addChild(orNode2); // right node
		orNode2.addChild(cNode); // left node
		orNode2.addChild(dNode); // right node
		
		// List of nodes order is important for building success message
		List<BooleanNode> list = new ArrayList<BooleanNode>();
		list.add(cNode);
		list.add(dNode);
		list.add(orNode2);
		list.add(bNode);
		list.add(andNode1);
		list.add(orNode1);
		list.add(aNode);

		String successMsg = configuredMessageBuilder.buildMessage(list);

		Assert.assertEquals(successMsg, orNode1.getNodeMessage());
		Assert.assertEquals(
				"    MATH101" +
				"\nOR" +
				"\n        (MATH201" +
				"\n    AND" +
				"\n            (MATH301" +
				"\n        OR" +
				"\n            MATH401))", 
				successMsg);
	}


	@Test
	public void testBuildMessage_ForListOfNodes_MultipleRootNodes() throws Exception {
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
			configuredMessageBuilder.buildMessage(list);
			Assert.fail("buildSuccessMessage should have failed since list has 2 root nodes");
		} catch (MessageBuilderException e) {
			Assert.assertTrue(e.getMessage() != null);
			Assert.assertTrue(e.getMessage().contains("label=+"));
			Assert.assertTrue(e.getMessage().contains("label=*"));
		}
	}
}
