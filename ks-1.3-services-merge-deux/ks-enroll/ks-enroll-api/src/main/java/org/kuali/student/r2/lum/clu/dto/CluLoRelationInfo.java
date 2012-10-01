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

package org.kuali.student.r2.lum.clu.dto;

import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.lum.clu.infc.CluLoRelation;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CluLoRelationInfo", propOrder = {"id", "typeKey",
        "stateKey", "cluId", "loId", "effectiveDate",
        "expirationDate", "meta", "attributes", "_futureElements"})
public class CluLoRelationInfo extends RelationshipInfo implements CluLoRelation, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String cluId;

    @XmlElement
    private String loId;

    @XmlAnyElement
    private List<Element> _futureElements;

    public CluLoRelationInfo() {

    }

    public CluLoRelationInfo(CluLoRelation cluLoRelation) {
        super(cluLoRelation);
        if (null != cluLoRelation) {
            this.cluId = cluLoRelation.getCluId();
            this.loId = cluLoRelation.getLoId();
        }
    }

    @Override
    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    @Override
    public String getLoId() {
        return loId;
    }

    public void setLoId(String loId) {
        this.loId = loId;
    }

    @Override
    public String toString() {
        return "CLuLoRelationInfo[id=" + this.getId() + ", cluId=" + this.getCluId() + ", loId=" + this.getLoId() + "]";
    }

}