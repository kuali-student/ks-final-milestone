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

package org.kuali.student.brms.internal.common.runtime.ast;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

public class BooleanNode extends CommonTree {
	
	private BooleanNode parent;
	private Boolean value;
	private String nodeMessage;
	private String language;
	
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

	public String getLanguage() {
		return language;
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
	
	public void setLanguage(String language) {
		this.language = language;
	}
}
