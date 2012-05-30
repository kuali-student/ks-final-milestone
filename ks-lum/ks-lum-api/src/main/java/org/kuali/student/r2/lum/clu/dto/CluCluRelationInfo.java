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

import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.lum.clu.infc.CluCluRelation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CluCluRelationInfo", propOrder = {"id", "typeKey",
        "stateKey", "cluId", "relatedCluId", "isCluRelationRequired", "effectiveDate",
        "expirationDate", "meta", "attributes" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
public class CluCluRelationInfo extends RelationshipInfo implements CluCluRelation, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String cluId;
    @XmlElement
    private String relatedCluId;
    @XmlElement
    private Boolean isCluRelationRequired;
//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public CluCluRelationInfo() {

    }

    public CluCluRelationInfo(CluCluRelation cluCluRelation) {
        super(cluCluRelation);
        if (null != cluCluRelation) {
            this.cluId = cluCluRelation.getCluId();
            this.relatedCluId = cluCluRelation.getRelatedCluId();
            this.isCluRelationRequired = cluCluRelation.getIsCluRelationRequired();
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
    public String getRelatedCluId() {
        return relatedCluId;
    }

    public void setRelatedCluId(String relatedCluId) {
        this.relatedCluId = relatedCluId;
    }

    @Override
    public Boolean getIsCluRelationRequired() {
        return isCluRelationRequired;
    }

    public void setIsCluRelationRequired(Boolean isCluRelationRequired) {
        this.isCluRelationRequired = isCluRelationRequired;
    }

    @Override
    public String toString() {
        return "CluCluRelationInfo[id=" + this.getId() + ", cluId=" + cluId + ", relatedCluId=" + relatedCluId + ", type=" + this.getTypeKey() + ", cluRelationRequired=" + isCluRelationRequired + "]";
    }

}
