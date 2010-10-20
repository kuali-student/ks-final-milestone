/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.messagebuilder.booleanmessage.ast;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

/**
 * This class is a boolean node in a binary tree.
 */
public class BooleanNode extends CommonTree {
	
	private BooleanNode parent;
	private Boolean value;
	private String nodeMessage;
	private String language;

	/**
	 * Construct a boolean node for a particular token in a binary tree. 
	 * Tokens are +, *, (, ) and propositions e.g. tokens: (M1*M2)+M3
	 * See BooleanFunctionParser.java
	 * 
	 * @param payload Node token
	 */
	public BooleanNode(Token payload) {
		super(payload);
	}

	/**
	 * Gets the parent boolean node.
	 * 
	 * @return Parent boolean node
	 */
	public BooleanNode getParent(){
		 return parent;
	}

	/**
	 * Gets the b-tree left node.
	 * 
	 * @return B-tree left boolean node
	 */
	public BooleanNode getLeftNode(){
		 return (BooleanNode) this.getChild(0);
	}

	/**
	 * Gets the b-tree right node.
	 * 
	 * @return B-tree right boolean node
	 */
	public BooleanNode getRightNode(){
		 return (BooleanNode) this.getChild(1);
	}

	/**
	 * Gets the label of the node.
	 * 
	 * @return Node label
	 */
	public String getLabel(){
		 return this.getText();
	}

	/**
	 * Gets the boolean value (true or false).
	 * 
	 * @return Boolean value
	 */
	public Boolean getValue(){
		 return value;
	}

	/**
	 * Gets the node message.
	 * 
	 * @return Node message
	 */
	public String getNodeMessage(){
		 return nodeMessage;
	}

	/**
	 * Gets the message language.
	 * 
	 * @return Message language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * org.antlr.runtime.tree.CommonTree.getParent() is not implemented
	 * you have to set the parent yourself, BinaryMessageTree.java
	 * 
	 * @param parent Parent node
	 */
	public void setParent(BooleanNode parent){
		 this.parent = parent;
	}

	/**
	 * Sets the boolean value (true or false).
	 * 
	 * @param value Boolean value
	 */
	public void setValue(Boolean value){
		 this.value = value;
	}

	/**
	 * Sets the node message.
	 * 
	 * @param nodeMessage Node message
	 */
	public void setNodeMessage(String nodeMessage){
		this.nodeMessage = nodeMessage;
	}

	/**
	 * Sets the message language.
	 * 
	 * @param language Message language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "BooleanNode["
				+ "label=" + getLabel() 
				+ ", value=" + value
				+ ", leftNodeLabel=" + (getLeftNode() == null ? null : getLeftNode().getLabel()) 
				+ ", rightNodeLabel=" + (getRightNode() == null ? null : getRightNode().getLabel())
				+ ", parentLabel=" + (parent == null ? null : parent.getLabel())
				+ "]";
	}
	
}
