package org.kuali.student.brms.internal.common.runtime.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;

import org.kuali.student.brms.internal.common.parsers.BooleanFunctionLexer;
import org.kuali.student.brms.internal.common.parsers.BooleanFunctionParser;
import org.kuali.student.brms.internal.common.runtime.BooleanMessage;
import org.kuali.student.brms.internal.common.runtime.exceptions.BooleanFunctionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinaryMessageTree {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(BinaryMessageTree.class);

    private BooleanNode root;
	private ArrayList<BooleanNode> nodes;
	private String language;
	
	private Map<String, ? extends BooleanMessage> nodeMessageMap;
	
	private static final TreeAdaptor adaptor = new CommonTreeAdaptor() {
		public Object create(Token payload) {
			return new BooleanNode(payload);
		}
	};
	
	/**
	 * Constructor.
	 */
	public BinaryMessageTree() {
		language = Locale.getDefault().getLanguage();
		nodes = new ArrayList<BooleanNode>();
	}

	/**
	 * Constructor. 
	 * Creates a new binary message tree for the in the specified language
	 * for the boolean message map.
	 *  
	 * @param messageLanguage Language for the message 
	 * @param messageMap map of boolean messages
	 */
	public BinaryMessageTree(String messageLanguage, Map<String, ? extends BooleanMessage> messageMap) {
		language = messageLanguage;
		nodeMessageMap = messageMap;
		nodes = new ArrayList<BooleanNode>();
	}
	
	/**
	 * <p>Builds the binary message tree from the <code>booleanFunction</code>.</p>
	 * <p>Order of boolean operation: ANDs before ORs and 
	 * operations inside parentheses before anything else.</p>
	 * <p>Example: A AND B OR C AND D evaluates to (A AND B) OR (C AND D).</p>
	 * 
	 * @param booleanFunction: Boolean expression 
	 * @return Root boolean node
	 */
	public BooleanNode buildTree(String booleanFunction){
		if (booleanFunction == null || booleanFunction.trim().isEmpty()) {
			return null;
		}
		
		BooleanFunctionLexer lex = new BooleanFunctionLexer(new ANTLRStringStream(booleanFunction) );
		CommonTokenStream tokens = new CommonTokenStream(lex);
		
		BooleanFunctionParser parser = new BooleanFunctionParser(tokens);
		parser.setTreeAdaptor(adaptor);
		
		// parse the expression
		BooleanFunctionParser.booleanExpression_return booleanExpression = null;
        try {
        	booleanExpression = parser.booleanExpression();  
        } catch (RecognitionException e)  {
        	String parserErrorMsg = parser.getErrorMessage(e, parser.getTokenNames());
        	String errorMsg = "Boolean Function Parser Error. " +
        			"Invalid Boolean Expression: '" + booleanFunction + "'; " + parserErrorMsg;  
        	throw new BooleanFunctionException(errorMsg, e);
        }
        // get the root of the AST from the parsed expression
        return root = (BooleanNode) booleanExpression.getTree();
	}
	
	/** 
	 * This method walks the tree depth first, while setting node values with 
	 * setNode(). Setting a nodes parent and adding the node to the ArrayList 
	 * for later. This method has to be called after buildTree. 
	 * 
	 * @param bnode Boolean node
	 * @param parent Parent boolean node
	 */
	public void traverseTreePostOrder(BooleanNode bnode, BooleanNode parent) {
		for (int i = 0; i < bnode.getChildCount(); i++) {
			traverseTreePostOrder((BooleanNode) bnode.getChild(i), bnode);
		}
		setNode(bnode);
		if (parent != null) {
			bnode.setParent(parent);
		}

		if(logger.isDebugEnabled()) {
		    logger.debug(bnode.getText());
		}
		nodes.add(bnode);
	}

	/**
	 * This method walks the tree depth first. Setting a nodes parent and 
	 * adding the node to the ArrayList for later. 
	 * This method has to be called after buildTree. 
	 * 
	 * @param bnode Boolean node
	 * @param parent Parent boolean node
	 */
	public void traverseTreePostOrderDontSetNode(BooleanNode bnode, BooleanNode parent) {
		if (bnode != null) {
			for (int i = 0; i < bnode.getChildCount(); i++ ) {
				traverseTreePostOrderDontSetNode((BooleanNode)bnode.getChild(i), bnode);
			}

			if (parent != null) {
				bnode.setParent(parent);
			}
		    
			if(logger.isDebugEnabled()) {
				logger.debug(bnode.getText());
			}
			nodes.add(bnode);
		}
	}

	/** 
	 * Here we set the value(true or false) and the failure message for each node. 
	 * Setting the failure message here does not mean the function failed, the node just holds the message. 
	 * Failure is determined by the rules engine which will extract the failure message.
	 * 
	 * @param bnode Boolean node
	 */
	private void setNode(BooleanNode bnode) {
		bnode.setLanguage(language);
		
		if (bnode.getChildCount() == 0) {
			// If node is a leaf then set value and message
			BooleanMessage message = nodeMessageMap.get(bnode.getLabel());
			bnode.setValue(message.isSuccesful());
			bnode.setNodeMessage(message.getMessage());
		}
		else {
			// If node is intermediate, compute value and set it
			BooleanNode child0 = (BooleanNode)bnode.getChild(0);
			BooleanNode child1 = (BooleanNode)bnode.getChild(1);
			
			if ( bnode.getLabel().equalsIgnoreCase("+") ) {
				// OR node
				Boolean newValue = child0.getValue() || child1.getValue();
				bnode.setValue(newValue);
				bnode.setNodeMessage("null");
			}
			else if ( bnode.getLabel().equalsIgnoreCase("*") ) {
				// AND node
				Boolean newValue = child0.getValue() && child1.getValue();
				bnode.setValue(newValue);
				bnode.setNodeMessage("null");
			}
		}
	}
	
	/**
	 * Gets all boolean nodes.
	 * 
	 * @return All boolean nodes
	 */
	public List<BooleanNode> getAllNodes() {
		return nodes;
	}

	/**
	 * Gets the root boolean node.
	 * 
	 * @return Root boolean node
	 */
	public BooleanNode getRoot() {
		return root;
	}
}
