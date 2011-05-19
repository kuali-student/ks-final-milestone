/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational
 * Community License, Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a
 * copy of the License at http://www.opensource.org/licenses/ecl1.php
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.Relationship;

/**
 * Information about a Relationship.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlTransient
public abstract class RelationshipInfo extends HasAttributesAndMetaInfo implements Relationship, Serializable {

    @XmlAttribute
    private String id;
    @XmlAttribute
    private String typeKey;
    @XmlAttribute
    private String stateKey;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;

    public RelationshipInfo() {
        id = null;
        typeKey = null;
        stateKey = null;
        effectiveDate = null;
        expirationDate = null;
    }

    public RelationshipInfo(Relationship relationship) {
        super(relationship);
        this.id = relationship.getId();
        this.typeKey = relationship.getTypeKey();
        this.stateKey = relationship.getStateKey();        
        this.effectiveDate = relationship.getEffectiveDate();
        this.expirationDate = relationship.getExpirationDate();
    }

    
    @Override
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    @Override
    public String getStateKey() {
        return stateKey;
    }
    
    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
