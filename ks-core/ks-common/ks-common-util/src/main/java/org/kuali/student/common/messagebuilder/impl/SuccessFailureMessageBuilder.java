package org.kuali.student.common.messagebuilder.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.messagebuilder.MessageTreeBuilder;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanNode;
import org.kuali.student.common.messagebuilder.impl.exceptions.MessageBuilderException;

/**
 * This class builds the success and failure messages for 
 * boolean binary tree nodes.
 */
public class SuccessFailureMessageBuilder implements MessageTreeBuilder {
	/** Boolean operators to use in creating the success and failure messages */
	private BooleanOperators booleanOperators;
	private SuccessMessageBuilder successMessageBuilder;
	private FailureMessageBuilder failureMessageBuilder;

	/**
	 * Creates a success and failure message builders.
	 * 
	 * @param andOperator AND logical operator
	 * @param orOperator OR logical operator
	 */
	public SuccessFailureMessageBuilder(String andOperator, String orOperator) {
		this.booleanOperators = new BooleanOperators(andOperator, orOperator);
		this.successMessageBuilder = new SuccessMessageBuilder(this.booleanOperators);
		this.failureMessageBuilder = new FailureMessageBuilder(this.booleanOperators);
	}

	/**
	 * Creates a success and failure message builders.
	 * 
	 * @param bo Boolean operators
	 */
	public SuccessFailureMessageBuilder(BooleanOperators bo) {
		this.booleanOperators = bo;
		this.successMessageBuilder = new SuccessMessageBuilder(this.booleanOperators);
		this.failureMessageBuilder = new FailureMessageBuilder(this.booleanOperators);
	}

	/**
	 * Builds and sets the success and failure message for each of the list of 
	 * boolean nodes (binary tree nodes).
	 * 
	 * @param nodeList List of boolean nodes 
	 */
	public String buildMessage(List<BooleanNode> nodeList) {
		// List must only contain one root node
		List<BooleanNode> rootNodeList = new ArrayList<BooleanNode>();
		for(BooleanNode node : nodeList) {
			if(node.getParent() == null) {
				rootNodeList.add(node);
			}
			buildMessage(node);
		}
        //Code Changed for JIRA-9075 - SONAR Critical issues - Use get(0) with caution - 5
        int firstBooleanNode = 0;
		if(rootNodeList.size() > 1) {
			throw new MessageBuilderException("Node list contains more than one root node: " + rootNodeList);
		} else if (rootNodeList.isEmpty()) {
            throw new MessageBuilderException("Node list contains no root node: " + rootNodeList);
        }
		return rootNodeList.get(firstBooleanNode).getNodeMessage();
	}

	/**
	 * Builds and sets the success and failure message for a boolean node 
	 * (binary tree node).
	 * 
	 * @param node Boolean node
	 */
	private void buildMessage(final BooleanNode node) {
		this.successMessageBuilder.buildSuccessMessage(node);
		this.failureMessageBuilder.buildFailureMessage(node);
	}
}
