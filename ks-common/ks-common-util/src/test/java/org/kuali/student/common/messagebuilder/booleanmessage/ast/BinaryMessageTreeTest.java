package org.kuali.student.common.messagebuilder.booleanmessage.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.messagebuilder.booleanmessage.BooleanMessage;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BinaryMessageTree;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanMessageImpl;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanNode;

public class BinaryMessageTreeTest {
	private Map<String,BooleanMessage> messageMap;
	
	@Before
	public void setUp() throws Exception {
		messageMap = new HashMap<String, BooleanMessage>();
		messageMap.put("A", new BooleanMessageImpl("A", true, "A is true"));
		messageMap.put("B", new BooleanMessageImpl("B", true, "B is true"));
	}
	
	@Test
	public void testGetRoot() throws Exception {
		BinaryMessageTree btree = new BinaryMessageTree("en", messageMap);
		BooleanNode rootNode = btree.buildTree("A*B");
		
		Assert.assertSame(rootNode, btree.getRoot());
	}

	@Test
	public void testBuildTree() throws Exception {
		BinaryMessageTree btree = new BinaryMessageTree("en", messageMap);
		BooleanNode rootNode = btree.buildTree("A*B");
		
		Assert.assertEquals(2, rootNode.getChildren().size());
		Assert.assertEquals("*", rootNode.getLabel());
		Assert.assertEquals("A", rootNode.getLeftNode().getLabel());
		Assert.assertEquals("B", rootNode.getRightNode().getLabel());
	}

	@Test
	public void testTraverseTreePostOrder_GetAllNodes() throws Exception {
		BinaryMessageTree btree = new BinaryMessageTree("en", messageMap);
		BooleanNode rootNode = btree.buildTree("A*B");
		btree.traverseTreePostOrder(rootNode, null);
		List<BooleanNode> nodeList = btree.getAllNodes();

		Assert.assertEquals(3, nodeList.size());
	}

	@Test
	public void testTraverseTreePostOrderDontSetNode_GetAllNodes2() throws Exception {
		BinaryMessageTree btree = new BinaryMessageTree();
		BooleanNode rootNode = btree.buildTree("A*B");
		btree.traverseTreePostOrderDontSetNode(rootNode, null);
		List<BooleanNode> nodeList = btree.getAllNodes();

		Assert.assertEquals(3, nodeList.size());
	}
}
