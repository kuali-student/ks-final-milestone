package org.kuali.student.common.messagebuilder.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.messagebuilder.MessageTreeBuilder;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanNode;
import org.kuali.student.common.messagebuilder.impl.exceptions.MessageBuilderException;

/**
 * This class creates success messages for boolean binary tree nodes.
 *
 * Default display is a single line message (e.g. A OR (B AND (C OR D))).  
 * To display as multiple lines where each operator and message is on its own line.
 * set the following:
 * <pre>
 * INDENT_CHAR = ' '
 * INDENT_NUMBER_OF_SPACES = 4
 * BOOLEAN_OPERATOR_PREFIX = "\n"
 * BOOLEAN_OPERATOR_SUFFIX = "\n"
 * Example:
 * </pre>
 * <pre>
 *    MATH101
 * OR
 *        (MATH201
 *    AND
 *            (MATH301
 *        OR
 *            MATH401))
 * </pre>
 */
public class SimpleBooleanMessageBuilder implements MessageTreeBuilder {
	// Default indentation character
	private final static char INDENT_CHAR = 0;
	// Default number of spaces to indent
	private final static int INDENT_NUMBER_OF_SPACES = 0;
	// Default indentation string
	private final static String INDENT_STRING = getString(INDENT_NUMBER_OF_SPACES, INDENT_CHAR);
	// Default boolean operator prefix string
	private final static String BOOLEAN_OPERATOR_PREFIX = " ";
	// Default boolean operator suffix string
	private final static String BOOLEAN_OPERATOR_SUFFIX = " ";

	// Indentation character
	private int indentCharacter = INDENT_CHAR;
	// Number of spaces to indent
	private int indentNumberOfSpaces = INDENT_NUMBER_OF_SPACES;
	// Indentation string
	private String indentString = INDENT_STRING;
	// Boolean operator prefix string
	private String booleanOperatorPrefix = BOOLEAN_OPERATOR_PREFIX;
	// Boolean operator suffix string
	private String booleanOperatorSuffix = BOOLEAN_OPERATOR_SUFFIX;
	
	/** Boolean operators to use in creating the success message */
	private BooleanOperators booleanOperators;

	/**
	 * Creates a success message builder with boolean operators to use in 
	 * building the success message.
	 * 
	 * @param andOperator AND logical operator
	 * @param orOperator OR logical operator
	 */
	public SimpleBooleanMessageBuilder(String andOperator, String orOperator) {
		this.booleanOperators = new BooleanOperators(andOperator, orOperator);
	}

	/**
	 * Creates a success message builder with boolean operators to use in 
	 * building the success message.
	 * 
	 * @param bo Boolean operators to build success message
	 */
	public SimpleBooleanMessageBuilder(BooleanOperators bo) {
		this.booleanOperators = bo;
	}

	public void setIndentCharacter(int indentChar) {
		this.indentCharacter = indentChar;
	}

	public void setIndentNumberOfSpaces(int indentNumberOfSpaces) {
		this.indentNumberOfSpaces = indentNumberOfSpaces;
	}

	public void setIndentString(String indentString) {
		this.indentString = indentString;
	}

	public void setBooleanOperatorPrefix(String booleanOperatorPrefix) {
		this.booleanOperatorPrefix = booleanOperatorPrefix;
	}

	public void setBooleanOperatorSuffix(String booleanOperatorSuffix) {
		this.booleanOperatorSuffix = booleanOperatorSuffix;
	}

	/**
	 * Builds and sets the success message for each of the boolean nodes 
	 * (binary tree) in the <code>nodeList</code>.
	 * 
	 * @param nodeList List of boolean nodes 
	 * @return Complete success message 
	 */
	public String buildMessage(List<BooleanNode> nodeList) throws MessageBuilderException {
		// List must only contain one root node
		List<BooleanNode> rootNodeList = new ArrayList<BooleanNode>();
		for(BooleanNode node : nodeList) {
			if(node.getParent() == null) {
				rootNodeList.add(node);
			}
			buildMessage(node);
		}
		if(rootNodeList.size() > 1) {
			throw new MessageBuilderException("Node list contains more than one root node: " + rootNodeList);
		}
		return rootNodeList.get(0).getNodeMessage();
	}

	/**
	 * Builds and sets the success message for a single 
	 * boolean <code>node</code> (b-tree node).
	 * 
	 * @param node Boolean node
	 */
	public String buildMessage(final BooleanNode node) {
		// AND node
		if(node.getLabel().equals("*")) {
			buildAndNodeSuccessMessage(node);
		} 
		// OR node
		else if(node.getLabel().equals("+")) {
			buildOrNodeSuccessMessage(node);
		}
		return node.getNodeMessage();
	}

	/**
	 * Builds a success message for an AND node (b-tree node) where 
	 * left node and right node are true.
	 * 
	 * @param node Boolean node
	 */
	private void buildAndNodeSuccessMessage(BooleanNode node) {
		if(node.getLabel().equals("*") && 
				node.getLeftNode() != null && 
				node.getRightNode() != null &&
				node.getLeftNode().getNodeMessage() != null && 
				node.getRightNode().getNodeMessage() != null) {
			String preIndent = "";
			if(node.getLeftNode().getChildCount() == 0) {
				preIndent = getIndent(node, 0);
			}
			String postIndent = "";
			if(node.getRightNode().getChildCount() == 0) {
				postIndent = getIndent(node, 0);
			}

			String logMessage = this.indentString + node.getLeftNode().getNodeMessage() + 
				this.booleanOperatorPrefix + 
				preIndent + this.booleanOperators.getAndOperator() + 
				this.booleanOperatorSuffix + 
				postIndent + this.indentString + node.getRightNode().getNodeMessage();

			if(node.getParent() != null && 
					((node.getLabel().equals("+") && 
							node.getParent().getLabel().equals("*")) || 
							(node.getLabel().equals("*") && 
									node.getParent().getLabel().equals("+")))) {
				logMessage = node.getLeftNode().getNodeMessage() + 
					this.booleanOperatorPrefix + 
					preIndent + this.booleanOperators.getAndOperator() + 
					this.booleanOperatorSuffix + 
					postIndent + this.indentString + node.getRightNode().getNodeMessage();
				    logMessage = preIndent + "(" + logMessage + ")";
			}
			node.setNodeMessage(logMessage);
		}
	}

	/**
	 * Builds a success message for an OR node (b-tree node) where 
	 * left node and right node are true.
	 * 
	 * @param node
	 */
	private void buildOrNodeSuccessMessage(BooleanNode node) {
		// OR2
		if(node.getLabel().equals("+") &&
				node.getLeftNode() != null && 
				node.getRightNode() != null &&
				node.getLeftNode().getNodeMessage() != null && 
				node.getRightNode().getNodeMessage() != null) {
			String preIndent = "";
			if(node.getLeftNode().getChildCount() == 0) {
				preIndent = getIndent(node, 0);
			}
			String postIndent = "";
			if(node.getRightNode().getChildCount() == 0) {
				postIndent = getIndent(node, 0);
			}

			String logMessage = this.indentString + node.getLeftNode().getNodeMessage() + 
			this.booleanOperatorPrefix + 
				preIndent + this.booleanOperators.getOrOperator() + 
				this.booleanOperatorSuffix + 
				postIndent + this.indentString + node.getRightNode().getNodeMessage();

			if(node.getParent() != null && 
					((node.getLabel().equals("+") && 
							node.getParent().getLabel().equals("*")) || 
							(node.getLabel().equals("*") && 
									node.getParent().getLabel().equals("+")))) {
				logMessage = node.getLeftNode().getNodeMessage() + 
					this.booleanOperatorPrefix + 
					preIndent + this.booleanOperators.getOrOperator() + 
					this.booleanOperatorSuffix + 
					postIndent + this.indentString + node.getRightNode().getNodeMessage();
				    logMessage = preIndent + "(" + logMessage + ")";
			}
			node.setNodeMessage(logMessage);
		} 
	}
	
	private int parentCount;
	
	private void countParents(BooleanNode node) {
		if(node.getParent() != null) {
			this.parentCount++;
			countParents(node.getParent());
		}
	}
	
	private String getIndent(BooleanNode node, int multiplier) {
		this.parentCount = 0;
		countParents(node);
		if(this.parentCount > 0) {
			return getString(this.parentCount * this.indentNumberOfSpaces + multiplier, (char) this.indentCharacter);
		}
		return "";
	}
	
	private static String getString(int charCount, char indentChr) {
		char[] chr = new char[charCount];
		for(int i=0; i<chr.length; i++) {
			chr[i] = indentChr;
		}
		return String.valueOf(chr);
	}
}
