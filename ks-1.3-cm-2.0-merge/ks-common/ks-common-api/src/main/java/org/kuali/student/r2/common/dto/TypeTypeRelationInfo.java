/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.kuali.student.r2.common.dto.RelationshipInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.TypeTypeRelation;
//import org.w3c.dom.Element;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeTypeRelationInfo", propOrder = {
                "id", "typeKey", "stateKey",
                "effectiveDate", "expirationDate", "ownerTypeKey", 
                "relatedTypeKey", "rank", 
                "attributes", "meta" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})

public class TypeTypeRelationInfo 
    extends RelationshipInfo
    implements TypeTypeRelation, Serializable {
	    
    @XmlElement
    private String ownerTypeKey;
    
    @XmlElement
    private String relatedTypeKey;
    
    @XmlElement
    private Integer rank;
    
//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;    
	
    /**
     * Constructs a new TypeTypeRelationInfo.
     */
    public TypeTypeRelationInfo() {
    }
	
    /**
     * Constructs a new TypeTypeRelationInfo from another
     * TypeTypeRelation.
     *
     * @param typeTypeRel the relation to copy
     */
    public TypeTypeRelationInfo(TypeTypeRelation typeTypeRel) {
        super(typeTypeRel);
    	this.ownerTypeKey = typeTypeRel.getOwnerTypeKey();
    	this.relatedTypeKey = typeTypeRel.getRelatedTypeKey();
        this.rank = typeTypeRel.getRank();
    	this.rank = typeTypeRel.getRank();
    }
	
    @Override
    public String getOwnerTypeKey() {
        return ownerTypeKey;
    }
    
    public void setOwnerTypeKey(String ownerTypeKey) {
        this.ownerTypeKey = ownerTypeKey;
    }
    
    @Override
    public String getRelatedTypeKey() {
        return relatedTypeKey;
    }

    public void setRelatedTypeKey(String relatedTypeKey) {
        this.relatedTypeKey = relatedTypeKey;
    }

    @Override
    public Integer getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
