/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.authz.dto;


/**
 * Used to return qualified & non-qualfied roles for a principal.
 * Maybe rename to QualfiedRole? 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class QualifiedRoleDTO {
	String id;
    String name;
        
    QualifierDTO qualifier;
    
    boolean descendTree;
    


	/**
	 * @return the descendTree
	 */
	public boolean isDescendTree() {
		return descendTree;
	}

	/**
	 * @param descendTree the descendTree to set
	 */
	public void setDescendTree(boolean descendTree) {
		this.descendTree = descendTree;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the qualifier
	 */
	public QualifierDTO getQualifier() {
		return qualifier;
	}

	/**
	 * @param qualifier the qualifier to set
	 */
	public void setQualifier(QualifierDTO qualifier) {
		this.qualifier = qualifier;
	}

	public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
