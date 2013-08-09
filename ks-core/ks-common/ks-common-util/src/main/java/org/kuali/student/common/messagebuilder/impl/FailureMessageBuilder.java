package org.kuali.student.common.messagebuilder.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanNode;
import org.kuali.student.common.messagebuilder.impl.exceptions.MessageBuilderException;

/**
 * This class creates failure messages for boolean binary tree nodes.
 */
public class FailureMessageBuilder {
	/** Boolean operators to use in creating the failure message */
	private BooleanOperators booleanOperators;

	/**
	 * Creates a failure message builder with boolean operators to use in 
	 * building the success message.
	 * 
	 * @param bo Boolean operators to build success message
	 */
	public FailureMessageBuilder(BooleanOperators bo) {
		this.booleanOperators = bo;
	}

	/**
	 * Builds and sets the failure message for each of the 
	 * boolean nodes (binary tree) in the <code>nodeList</code>.
	 * 
	 * @param nodeList List of boolean nodes
	 * @return Complete failure message 
	 */
	public String buildFailureMessage(List<BooleanNode> nodeList) throws MessageBuilderException {
		// List must only contain one root node
		List<BooleanNode> rootNodeList = new ArrayList<BooleanNode>();
		for(BooleanNode node : nodeList) {
			if(node.getParent() == null) {
				rootNodeList.add(node);
			}
			buildFailureMessage(node);
		}
		if(rootNodeList.size() > 1) {
			throw new MessageBuilderException("Node list contains more than one root node: " + rootNodeList);
		}
		return rootNodeList.get(0).getNodeMessage();
	}

	/**
	 * Builds and sets the failure message for a single 
	 * boolean <code>node</code> (b-tree node).
	 * 
	 * @param node Boolean node
	 */
	public String buildFailureMessage(BooleanNode node) {
		// OR
		if(node.getLabel().equals("+")){
			buildOrNodeFailureMessage(node);
		}
		// AND
		else if(node.getLabel().equals("*")) {
			buildAnd1NodeFailureMessage(node);
			buildAnd2NodeFailureMessage(node);
		}
		return node.getNodeMessage();
	}

	/**
	 * Builds a failure message for an OR node (b-tree node).
	 * 
	 * @param node Boolean node
	 */
	private void buildOrNodeFailureMessage(BooleanNode node) {
		// OR
		if(node.getLabel().equals("+") &&
				node.getLeftNode() != null && 
				node.getRightNode() != null &&
				node.getLeftNode().getValue() == false && 
				node.getRightNode().getValue() == false &&
				node.getLeftNode().getNodeMessage() != null && 
				node.getRightNode().getNodeMessage() != null) {
	        String logMessage = node.getLeftNode().getNodeMessage() + " " + 
	        	this.booleanOperators.getOrOperator() + " " + 
	        	node.getRightNode().getNodeMessage();

	        if(node.getParent() != null && 
					( (node.getLabel().equals("+") && 
							node.getParent().getLabel().equals("*")) || 
							(node.getLabel().equals("*") && 
									node.getParent().getLabel().equals("+")))) {
			    logMessage = "(" + logMessage + ")";
			}
			node.setNodeMessage(logMessage);
		}
	}

	/**
	 * Builds a failure message for an AND node (b-tree node) where 
	 * left node is true and right node is false or 
	 * left node is false and right node is true.
	 * 
	 * @param node Boolean node
	 */
	private void buildAnd1NodeFailureMessage(BooleanNode node) {
		// AND1
		if(node.getLabel().equals("*") &&
				node.getLeftNode() != null && 
				node.getRightNode() != null &&
				((node.getLeftNode().getValue() == false && 
						node.getRightNode().getValue() == true && 
						node.getLeftNode().getNodeMessage() != null ) || 
				(node.getLeftNode().getValue() == true && 
						node.getRightNode().getValue() == false && 
						node.getRightNode().getNodeMessage() != null))) {
			String logMessage = "test";
			
			if (node.getLeftNode().getValue() == false)
				logMessage = node.getLeftNode().getNodeMessage();
			else if (node.getRightNode().getValue() == false)
				logMessage = node.getRightNode().getNodeMessage();
			
			node.setNodeMessage(logMessage);
		}
	}

	/**
	 * Builds a failure message for an AND node (b-tree node) where 
	 * left node and right node are false.
	 * 
	 * @param node Boolean node
	 */
	private void buildAnd2NodeFailureMessage(BooleanNode node) {
		// AND2
		if(node.getLabel().equals("*") && 
				node.getLeftNode() != null && 
				node.getRightNode() != null &&
				node.getLeftNode().getValue() == false && 
				node.getRightNode().getValue() == false && 
				node.getLeftNode().getNodeMessage() != null && 
				node.getRightNode().getNodeMessage() != null) {
	        String logMessage = node.getLeftNode().getNodeMessage() + " " + 
	        	this.booleanOperators.getAndOperator() + " " + 
	        	node.getRightNode().getNodeMessage();
			
			if(node.getParent() != null && 
					( (node.getLabel().equals("+") && 
							node.getParent().getLabel().equals("*")) || 
							(node.getLabel().equals("*") && 
									node.getParent().getLabel().equals("+")))) {
			    logMessage = "(" + logMessage + ")";
			}
			node.setNodeMessage(logMessage);
		}
	}
}
