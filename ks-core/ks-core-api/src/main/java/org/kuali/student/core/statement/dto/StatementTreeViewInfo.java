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

package org.kuali.student.core.statement.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class StatementTreeViewInfo extends AbstractStatementInfo {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<StatementTreeViewInfo> statements;
    @XmlElement
    private List<ReqComponentInfo> reqComponents;

    /**
     * <code>naturalLanguageTranslation</code> element is a read-only
     * element which is generated on-the-fly and should not be persisted.
     */
    @XmlElement
    private String naturalLanguageTranslation;


    public List<StatementTreeViewInfo> getStatements() {
        return statements;
    }

    public void setStatements(List<StatementTreeViewInfo> statements) {
    	if (this.statements == null) {
    		this.statements = new ArrayList<StatementTreeViewInfo>(0);
    	}
        this.statements = statements;
    }

    public List<ReqComponentInfo> getReqComponents() {
        return reqComponents;
    }

    public void setReqComponents(List<ReqComponentInfo> reqComponents) {
    	if (this.reqComponents == null) {
    		this.reqComponents = new ArrayList<ReqComponentInfo>(0);
    	}
        this.reqComponents = reqComponents;
    }


	public String getNaturalLanguageTranslation() {
		return naturalLanguageTranslation;
	}

	public void setNaturalLanguageTranslation(String naturalLanguageTranslation) {
		this.naturalLanguageTranslation = naturalLanguageTranslation;
	}

	@Override
	public String toString() {
		return "StatementTreeViewInfo[id=" + getId() + "]";
	}
}
