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
package org.kuali.student.lum.nlt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;

/**
 * Detailed information about a single transient LU statement.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class LuNlStatementInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String name;

    @XmlElement
    private String statementTypeId;
    
    @XmlElement
    private StatementOperatorTypeKey operator;

    @XmlElement
    private List<LuNlStatementInfo> luStatementList = new ArrayList<LuNlStatementInfo>();

    @XmlElement
    private List<ReqComponentInfo> reqComponentList = new ArrayList<ReqComponentInfo>();

    /**
     * Friendly name for the LU statement.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Logical operator used to assemble statements. Acceptable values are restricted to AND and OR. This operator applies to both the LU statements and requirement components contained within this statement.
     */
    public StatementOperatorTypeKey getOperator() {
        return operator;
    }

    public void setOperator(StatementOperatorTypeKey operator) {
        this.operator = operator;
    }

	public List<LuNlStatementInfo> getChildren() {
		return luStatementList;
	}

	public void setChildren(List<LuNlStatementInfo> luStatementList) {
		this.luStatementList = luStatementList;
	}

	public List<ReqComponentInfo> getRequiredComponents() {
		return reqComponentList;
	}

	public void setRequiredComponents(List<ReqComponentInfo> reqComponentList) {
		this.reqComponentList = reqComponentList;
	}

	public String getStatementTypeId() {
		return statementTypeId;
	}

	public void setStatementTypeId(String statementTypeId) {
		this.statementTypeId = statementTypeId;
	}

}
