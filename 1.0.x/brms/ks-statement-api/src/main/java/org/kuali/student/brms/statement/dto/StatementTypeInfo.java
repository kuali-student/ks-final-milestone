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

package org.kuali.student.brms.statement.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.kuali.student.core.dto.TypeInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatementTypeInfo extends TypeInfo {

	private static final long serialVersionUID = 1L;

    @XmlElement(name = "reqComponentTypeKey")
    @XmlElementWrapper(name = "allowedReqComponentTypes")
	private List<String> allowedReqComponentTypes;
	
    @XmlElement(name = "statementTypeKey")
    @XmlElementWrapper(name = "allowedStatementTypes")
	private List<String> allowedStatementTypes;

//    @XmlElement
//    private List<LuStatementTypeHeaderTemplateInfo> statementHeader;

    
    public List<String> getAllowedReqComponentTypes() {
        return allowedReqComponentTypes;
    }

    public void setAllowedReqComponentTypes(List<String> allowedReqComponentTypes) {
        this.allowedReqComponentTypes = allowedReqComponentTypes;
    }

    public List<String> getAllowedStatementTypes() {
        return allowedStatementTypes;
    }

    public void setAllowedStatementTypes(List<String> allowedLuStatementTypes) {
        this.allowedStatementTypes = allowedLuStatementTypes;
    }    

//	public List<LuStatementTypeHeaderTemplateInfo> getHeaders() {
//		return statementHeader;
//	}
//
//	public void setHeaders(List<LuStatementTypeHeaderTemplateInfo> header) {
//		this.statementHeader = header;
//	}

	@Override
	public String toString() {
		return "StatementTypeInfo[id=" + getId() + "]";
	}
}
