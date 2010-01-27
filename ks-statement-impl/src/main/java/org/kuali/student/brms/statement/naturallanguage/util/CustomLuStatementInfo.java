/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.brms.statement.naturallanguage.util;

import java.util.List;

import org.kuali.student.brms.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.brms.statement.dto.StatementTypeInfo;

/**
 * {@link LuStatementInfo} wrapper class.
 */
public class CustomLuStatementInfo {

    private String id;

    private String name;

    private StatementOperatorTypeKey operator;

    private StatementTypeInfo statementType;

    private CustomLuStatementInfo parent;

    private List<CustomLuStatementInfo> children; 
	
    private List<CustomReqComponentInfo> requiredComponents;
	
	public CustomLuStatementInfo() {
	}
	
    public String getId() {
    	return this.id;
    }
    
	public void setId(String id) {
		this.id = id;
	}

	public CustomLuStatementInfo getParent() {
		return this.parent;
	}

	public void setParent(CustomLuStatementInfo parent) {
		this.parent = parent;
	}

    public StatementOperatorTypeKey getOperator() {
        return this.operator;
    }

	public void setOperator(StatementOperatorTypeKey operator) {
		this.operator = operator;
	}

	public List<CustomLuStatementInfo> getChildren() {
		return this.children;
	}
	
	public void setChildren(List<CustomLuStatementInfo> children) {
		this.children = children;
	}

	public List<CustomReqComponentInfo> getRequiredComponents() {
		return this.requiredComponents;
	}

	public void setRequiredComponents(List<CustomReqComponentInfo> requiredComponents) {
		this.requiredComponents = requiredComponents;
	}
	
	public StatementTypeInfo getLuStatementType() {
		return this.statementType;
	}

	public void setLuStatementType(StatementTypeInfo statementType) {
		this.statementType = statementType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CustomLuStatementInfo[id=" + id + ", statementTypeId="
				+ (statementType == null ? "null" : statementType.getId()) 
				+ ", operator=" + operator + "]";
	}
}
