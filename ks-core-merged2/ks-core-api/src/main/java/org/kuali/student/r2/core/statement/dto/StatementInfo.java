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

package org.kuali.student.r2.core.statement.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.statement.infc.Statement;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatementInfo", propOrder = {"id", "typeKey", "stateKey",
        "name", "descr", "operator", "statementIds", "reqComponentIds", "meta", "attributes", "_futureElements"})
public class StatementInfo extends IdEntityInfo implements Statement, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private StatementOperator operator;
    @XmlElement
    private List<String> statementIds;
    @XmlElement
    private List<String> reqComponentIds;
    @XmlAnyElement
    private List<Element> _futureElements;

    public StatementInfo() {
    }

    public StatementInfo(Statement statement) {
        super(statement);
        if (null != statement) {
            // shallow copy is fine here
            this.operator = statement.getOperator();
            this.statementIds = new ArrayList<String>(statement.getStatementIds());
            this.reqComponentIds = new ArrayList<String>(statement.getReqComponentIds());
        }
    }

    @Override
    public StatementOperator getOperator() {
        return this.operator;
    }

    public void setOperator(StatementOperator operator) {
        this.operator = operator;
    }

    @Override
    public List<String> getStatementIds() {
        if (this.statementIds == null) {
            this.statementIds = new ArrayList<String>(0);
        }
        return this.statementIds;
    }

    public void setStatementIds(List<String> statementIds) {
        this.statementIds = statementIds;
    }

    @Override
    public List<String> getReqComponentIds() {
        if (this.reqComponentIds == null) {
            this.reqComponentIds = new ArrayList<String>(0);
        }
        return this.reqComponentIds;
    }

    public void setReqComponentIds(List<String> reqComponentIds) {
        this.reqComponentIds = reqComponentIds;
    }

    @Override
    public String toString() {
        return "StatementInfo[id=" + getId() + "]";
    }

}
