package org.kuali.student.rules.internal.common.runtime.ast;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

public class BooleanNode extends CommonTree {
	
	private BooleanNode parent;
	private Boolean value;
	private String nodeMessage;
	
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
	
	public Boolean getValue(){
		 return value;
	}
	
	public String getNodeMessage(){
		 return nodeMessage;
	}
	
	// org.antlr.runtime.tree.CommonTree getParent() is not implemented
	// you have to set the parent yourself, see BinaryTree.java
	public void setParent(BooleanNode parent){
		 this.parent = parent;
	}
	
	public void setValue(Boolean value){
		 this.value = value;
	}
	
	public void setNodeMessage(String nodeMessage){
		this.nodeMessage = nodeMessage;
	}
	
}
