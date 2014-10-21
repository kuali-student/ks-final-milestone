/**
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.lum.lo.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.lum.lo.infc.LoLoRelation;

@XmlType(name = "LoLoRelationInfo", propOrder = {"id", "typeKey", "stateKey", "loId", "relatedLoId", "effectiveDate", "expirationDate", "meta", "attributes" , "_futureElements" }) 
@XmlAccessorType(XmlAccessType.FIELD)
public class LoLoRelationInfo extends RelationshipInfo implements LoLoRelation, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String loId;

    @XmlElement
    private String relatedLoId;
    
    @XmlElement
    private List<Object>_futureElements;

    public LoLoRelationInfo() {

    }

    public LoLoRelationInfo(LoLoRelation loLoRelation) {
        super(loLoRelation);
        if (loLoRelation != null) {
            this.loId = loLoRelation.getLoId();
            this.relatedLoId = loLoRelation.getRelatedLoId();

        }
    }

    @Override
    public String getLoId() {
        return loId;
    }

    public void setLoId(String loId) {
        this.loId = loId;
    }

    @Override
    public String getRelatedLoId() {
        return relatedLoId;
    }

    public void setRelatedLoId(String relatedLoId) {
        this.relatedLoId = relatedLoId;
    }

}