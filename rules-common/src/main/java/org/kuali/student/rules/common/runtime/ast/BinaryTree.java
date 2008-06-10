package org.kuali.student.rules.common.runtime.ast;

import java.util.*;

import org.kuali.student.rules.parsers.*;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

public class BinaryTree {

	private static BooleanNode root;
	private static ArrayList<BooleanNode> nodes;
	
	private HashMap<String, Boolean> nodeValueMap;
	private HashMap<String, String> nodeFailureMessageMap;
	
	public BinaryTree() {
		nodes = new ArrayList<BooleanNode>();
	}
	
	public BinaryTree(HashMap<String, Boolean> nodeValueMap, HashMap<String, String> nodeFailureMessageMap) {
		this.nodeValueMap = nodeValueMap;
		this.nodeFailureMessageMap = nodeFailureMessageMap;
		nodes = new ArrayList<BooleanNode>();
	}
	
	static final TreeAdaptor adaptor = new CommonTreeAdaptor() {
		public Object create(Token payload) {
			return new BooleanNode(payload);
		}
	};
	
	public BooleanNode buildTree(String function){
		
		BooleanFunctionLexer lex = new BooleanFunctionLexer(new ANTLRStringStream(function) );
		CommonTokenStream tokens = new CommonTokenStream(lex);
		
		BooleanFunctionParser parser = new BooleanFunctionParser(tokens);
		parser.setTreeAdaptor(adaptor);
		
		// parse the expression
		BooleanFunctionParser.boolexpr_return boolexpr_ret = null;
        try {
            boolexpr_ret = parser.boolexpr();  
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }
        // get the root of the AST from the parsed expression
        return root = (BooleanNode) boolexpr_ret.getTree();
	}
	
	public void traverseTreePostOrderDontSetNode(BooleanNode bnode, BooleanNode parent) {
		if ( bnode != null ) {
			for ( int i = 0; i < bnode.getChildCount(); i++ ) {
				traverseTreePostOrderDontSetNode( (BooleanNode)bnode.getChild(i), bnode );
				//System.out.println(bnode.getChild(i).toString());
			}
		if ( parent != null ) {
			//System.out.println(bnode.getLabel() + "'s parent is " + parent.getLabel());
			bnode.setParent(parent);
		}
			//System.out.println(bnode.getText());
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
				//System.out.println(bnode.getChild(i).toString());
			}
			setNode( bnode );
		if ( parent != null ) {
			//System.out.println(bnode.getLabel() + "'s parent is " + parent.getLabel());
			bnode.setParent(parent);
		}
			//System.out.println(bnode.getText());
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
			String ruleFailureMessage = nodeFailureMessageMap.get(bnode.getLabel());
			bnode.setRuleFailureMessage(ruleFailureMessage);
			//System.out.println("Setting node " + bnode.getLabel() );
		}
		else {
			// node is intermediate, compute value and set it
			BooleanNode child0 = (BooleanNode)bnode.getChild(0);
			BooleanNode child1 = (BooleanNode)bnode.getChild(1);
			
			if ( bnode.getLabel().equalsIgnoreCase("+") ) {
				Boolean newValue = child0.getValue() || child1.getValue();
				bnode.setValue(newValue);
				bnode.setRuleFailureMessage("null");
				//System.out.println("IM IN + ");
			}
			else if ( bnode.getLabel().equalsIgnoreCase("*") ) {
				Boolean newValue = child0.getValue() && child1.getValue();
				bnode.setValue(newValue);
				bnode.setRuleFailureMessage("null");
				//System.out.println("IM IN * ");
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
