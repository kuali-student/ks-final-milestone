/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.core.atp.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.atp.infc.AtpAtpRelation;
import org.kuali.student.r2.common.dto.RelationshipInfo;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AtpAtpRelationInfo", propOrder = { 
                "id", "typeKey", "stateKey", 
                "atpId", "relatedAtpId", 
                "effectiveDate", "expirationDate", 
                "meta", "attributes", "_futureElements" }) 

public class AtpAtpRelationInfo 
    extends RelationshipInfo 
    implements AtpAtpRelation, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String atpId;
	
    @XmlElement
    private String relatedAtpId;
	
    @XmlAnyElement
    private List<Object> _futureElements;  

    
    /**
     * Constructs a new AtpAtpRelationInfo.
     */
    public AtpAtpRelationInfo() {
    }
    
    /**
     * Constructs a new AtpAtpRelationInfo from another
     * AtpAtpRelation.
     * 
     * @param atpr the AtpAtpRelation to copy
     */
    public AtpAtpRelationInfo(AtpAtpRelation atpr) {
        super(atpr);
        
        if (atpr != null) {
            this.atpId = atpr.getAtpId();
            this.relatedAtpId = atpr.getRelatedAtpId();
        }
    }

    @Override
    public String getAtpId() {
        return atpId;
    }
    
    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }
    
    @Override
    public String getRelatedAtpId() {
        return relatedAtpId;
    }
    
    public void setRelatedAtpId(String relatedAtpId) {
        this.relatedAtpId = relatedAtpId;
    }
}
