/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.core.statement.infc.RefStatementRelation;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;
// TODO KSCM Bug was log in Jira KSCM-430 RefStatementRelationInfo: Not tested against the data dictionary
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RefStatementRelationInfo", propOrder = {"id", "typeKey", "stateKey", "refObjectTypeKey", "refObjectId", "statementId", "effectiveDate", "expirationDate", "meta", "attributes", "_futureElements" }) 
public class RefStatementRelationInfo extends RelationshipInfo implements RefStatementRelation, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String refObjectTypeKey;
    @XmlElement
    private String refObjectId;
    @XmlElement
    private String statementId;
    @XmlAnyElement
    private List<Object> _futureElements;  

    public RefStatementRelationInfo() {
    }

    public RefStatementRelationInfo(RefStatementRelation refStatementRelation) {
        super(refStatementRelation);
        if (null != refStatementRelation) {
            this.refObjectId = refStatementRelation.getRefObjectId();
            this.statementId = refStatementRelation.getStatementId();
        }
    }

    @Override
    public String getRefObjectTypeKey() {
        return this.refObjectTypeKey;
    }

    public void setRefObjectTypeKey(String refObjectTypeKey) {
        this.refObjectTypeKey = refObjectTypeKey;
    }

    @Override
    public String getRefObjectId() {
        return this.refObjectId;
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }

    @Override
    public String getStatementId() {
        return this.statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

}
