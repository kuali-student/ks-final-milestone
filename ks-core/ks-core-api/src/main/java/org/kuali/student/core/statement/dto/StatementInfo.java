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

/**
 *Detailed information about a single LU statement.
 */
public class StatementInfo extends AbstractStatementInfo {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> statementIds;

    @XmlElement
    private List<String> reqComponentIds;
    /**
     * List of LU statement identifiers.
     */
    public List<String> getStatementIds() {
        if (statementIds == null) {
            statementIds = new ArrayList<String>(0);
        }
        return statementIds;
    }

    public void setStatementIds(List<String> statementIds) {
        this.statementIds = statementIds;
    }

    /**
     * List of requirement component identifiers.
     */
    public List<String> getReqComponentIds() {
        if (reqComponentIds == null) {
            reqComponentIds = new ArrayList<String>(0);
        }
        return reqComponentIds;
    }

    public void setReqComponentIds(List<String> reqComponentIds) {
        this.reqComponentIds = reqComponentIds;
    }

    @Override
	public String toString() {
		return "StatementInfo[id=" + getId() + ", operator=" + getOperator() + ", type="
				+ getType() + ", state=" + getState() + "]";
	}
}
