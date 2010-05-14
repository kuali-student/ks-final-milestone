package org.kuali.student.rules.internal.common.runtime.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;

import org.kuali.student.rules.internal.common.parsers.BooleanFunctionLexer;
import org.kuali.student.rules.internal.common.parsers.BooleanFunctionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinaryTree {

    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(BinaryTree.class);

    private static BooleanNode root;
	private static ArrayList<BooleanNode> nodes;
	
	private HashMap<String, Boolean> nodeValueMap;
	private HashMap<String, String> nodeMessageMap;
	
	public BinaryTree() {
		nodes = new ArrayList<BooleanNode>();
	}
	
	public BinaryTree(HashMap<String, Boolean> nodeValueMap, HashMap<String, String> nodeFailureMessageMap) {
		this.nodeValueMap = nodeValueMap;
		this.nodeMessageMap = nodeFailureMessageMap;
		nodes = new ArrayList<BooleanNode>();
	}
	
	static final TreeAdaptor adaptor = new CommonTreeAdaptor() {
		public Object create(Token payload) {
			return new BooleanNode(payload);
		}
	};
	
	public BooleanNode buildTree(String function){
		if (function == null || function.trim().isEmpty()) {
			return null;
		}
		
		BooleanFunctionLexer lex = new BooleanFunctionLexer(new ANTLRStringStream(function) );
		CommonTokenStream tokens = new CommonTokenStream(lex);
		
		BooleanFunctionParser parser = new BooleanFunctionParser(tokens);
		parser.setTreeAdaptor(adaptor);
		
		// parse the expression
		BooleanFunctionParser.boolexpr_return boolexpr_ret = null;
        try {
            boolexpr_ret = parser.boolexpr();  
        } catch (RecognitionException e)  {
            throw new RuntimeException(e);
        }
        // get the root of the AST from the parsed expression
        return root = (BooleanNode) boolexpr_ret.getTree();
	}
	
	public void traverseTreePostOrderDontSetNode(BooleanNode bnode, BooleanNode parent) {
		if ( bnode != null ) {
			for ( int i = 0; i < bnode.getChildCount(); i++ ) {
				traverseTreePostOrderDontSetNode( (BooleanNode)bnode.getChild(i), bnode );
				logger.debug(bnode.getChild(i).toString());
			}
		if ( parent != null ) {
		    logger.debug(bnode.getLabel() + "'s parent is " + parent.getLabel());
			bnode.setParent(parent);
		}
		    logger.debug(bnode.getText());
			nodes.add(bnode);
		}
	}
	
	/** This method walks the tree depth first, while setting node values with setNode()
	 *  Setting a nodes parent and adding the node to the ArrayList for later. This method
	 *  has to be called after buildTree. I guess we could call it from build tree.
	 * 
	 * @param bnode
	 * @param parent
	 */
	public void traverseTreePostOrder(BooleanNode bnode, BooleanNode parent) {
		if ( bnode != null ) {
			for ( int i = 0; i < bnode.getChildCount(); i++ ) {
				traverseTreePostOrder( (BooleanNode)bnode.getChild(i), bnode );
				logger.debug(bnode.getChild(i).toString());
			}
			setNode( bnode );
		if ( parent != null ) {
		    logger.debug(bnode.getLabel() + "'s parent is " + parent.getLabel());
			bnode.setParent(parent);
		}
		    logger.debug(bnode.getText());
			nodes.add(bnode);
		}
	}
	
	/** Here we set the value(true or false) and the failure message for each node. 
	 *  Setting the failure message here does not mean the function failed, the node just holds the message. 
	 *  Failure is determined by the rules engine which will extract the failure message.
	 * @param bnode
	 */
	private void setNode(BooleanNode bnode) {
		
		if (bnode.getChildCount() == 0) {
			// node is a leaf, set value
			Boolean value = nodeValueMap.get(bnode.getLabel());
			bnode.setValue(value);
			// set the message
			String ruleMessage = nodeMessageMap.get(bnode.getLabel());
			bnode.setNodeMessage(ruleMessage);
			logger.debug("Setting node " + bnode.getLabel() );
		}
		else {
			// node is intermediate, compute value and set it
			BooleanNode child0 = (BooleanNode)bnode.getChild(0);
			BooleanNode child1 = (BooleanNode)bnode.getChild(1);
			
			if ( bnode.getLabel().equalsIgnoreCase("+") ) {
				Boolean newValue = child0.getValue() || child1.getValue();
				bnode.setValue(newValue);
				bnode.setNodeMessage("null");
				logger.debug("OR = + ");
			}
			else if ( bnode.getLabel().equalsIgnoreCase("*") ) {
				Boolean newValue = child0.getValue() && child1.getValue();
				bnode.setValue(newValue);
				bnode.setNodeMessage("null");
				logger.debug("AND = * ");
			}
		}
	}
	
	public List<BooleanNode> getAllNodes() {
		return nodes;
	}
	
	public BooleanNode getRoot() {
		return root;
	}
}
