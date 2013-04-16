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

import org.kuali.student.r1.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.statement.infc.ReqComponent;
import org.kuali.student.r2.core.statement.infc.StatementTreeView;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatementTreeViewInfo", propOrder = {"id", "typeKey", "stateKey",
        "name", "descr", "operator", "statements", "reqComponents", "meta", "attributes", "_futureElements" }) 
public class StatementTreeViewInfo extends IdEntityInfo implements StatementTreeView {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private StatementOperator operator;
    @XmlElement
    private List<StatementTreeViewInfo> statements;
    @XmlElement
    private List<ReqComponentInfo> reqComponents;
    @XmlAnyElement
    private List<Object> _futureElements;  

    public StatementTreeViewInfo() {
    }

    public StatementTreeViewInfo(StatementTreeView statementTreeView) {
        super(statementTreeView);
        if (null != statementTreeView) {
            this.operator = statementTreeView.getOperator();
            this.statements = new ArrayList<StatementTreeViewInfo>();
            for (StatementTreeView statementTreeViewTemp : statementTreeView.getStatements()) {
                // note: this call is potentially recursive
                this.statements.add(new StatementTreeViewInfo(statementTreeViewTemp));
            }
            this.reqComponents = new ArrayList<ReqComponentInfo>();
            for (ReqComponent reqComponentInfo : statementTreeView.getReqComponents()) {
                this.reqComponents.add(new ReqComponentInfo(reqComponentInfo));
            }
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
    public List<StatementTreeViewInfo> getStatements() {
        if (this.statements == null) {
            this.statements = new ArrayList<StatementTreeViewInfo>(0);
        }
        return this.statements;
    }

    public void setStatements(List<StatementTreeViewInfo> statements) {
        this.statements = statements;
    }

    @Override
    public List<ReqComponentInfo> getReqComponents() {
        if (this.reqComponents == null) {
            this.reqComponents = new ArrayList<ReqComponentInfo>(0);
        }
        return this.reqComponents;
    }

    public void setReqComponents(List<ReqComponentInfo> reqComponents) {
        this.reqComponents = reqComponents;
    }

    @Override
    public String toString() {
        return "StatementTreeViewInfo[id=" + getId() + "]";
    }
}
