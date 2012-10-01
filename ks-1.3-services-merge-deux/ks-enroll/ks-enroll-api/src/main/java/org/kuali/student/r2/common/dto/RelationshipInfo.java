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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kuali.student.r2.common.infc.Relationship;

/**
 * Information about a Relationship entity.
 *
 * @author tom
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlTransient
public abstract class RelationshipInfo 
    extends HasAttributesAndMetaInfo 
    implements Relationship, Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * Constructs a new RelationshipInfo.
     */
    public RelationshipInfo() {
    }

    /**
     * Constructs a new RelationshipInfo from another Relationship.
     *
     * @param relationship the Relationship top copy
     */
    public RelationshipInfo(Relationship relationship) {
        super(relationship);
        
        if (relationship != null) {
            this.id = relationship.getId();
            this.typeKey = relationship.getTypeKey();
            this.stateKey = relationship.getStateKey();

            if (relationship.getEffectiveDate() != null) {
                this.effectiveDate = new Date(relationship.getEffectiveDate().getTime());
            } 

            if (relationship.getExpirationDate() != null) {
                this.expirationDate = new Date(relationship.getExpirationDate().getTime());
            }
        }
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


    // Compatibility 

    @Override
    @Deprecated
    public String getType() {
        return getTypeKey();
    }

    @Deprecated
    public void setType(String typeKey) {
        setTypeKey(typeKey);
    }

    @Override
    @Deprecated
    public String getState() {
        return getStateKey();
    }

    @Deprecated
    public void setState(String stateKey) {
        setStateKey(stateKey);
    }
}
