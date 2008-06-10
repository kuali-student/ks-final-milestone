package org.kuali.student.rules.runtime.ast;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;


public class BooleanNode extends CommonTree {
	
	private BooleanNode parent;
	private boolean value;
	private String ruleFailureMessage;
	
	public BooleanNode(Token payload) {
		super(payload);
	}
	
	public BooleanNode getParent(){
		 return parent;
	}
	
	public BooleanNode getLeftNode(){
		 return (BooleanNode) this.getChild(0);
	}
	 
	public BooleanNode getRightNode(){
		 return (BooleanNode) this.getChild(1);
	}
	
	public String getLabel(){
		 return this.getText();
	}
	
	public boolean getValue(){
		 return value;
	}
	
	public String getRuleFailureMessage(){
		 return ruleFailureMessage;
	}
	
	// Setters
	
	// org.antlr.runtime.tree.CommonTree getParent() is not implemented
	// you have to set the parent yourself, see BinaryTree.java
	public void setParent(BooleanNode parent){
		 this.parent = parent;
	}
	
	public void setValue(boolean value){
		 this.value = value;
	}
	
	public void setRuleFailureMessage(String ruleFailureMessage){
		this.ruleFailureMessage = ruleFailureMessage;
	}
	
}
