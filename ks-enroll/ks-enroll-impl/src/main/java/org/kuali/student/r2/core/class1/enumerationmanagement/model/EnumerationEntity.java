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

package org.kuali.student.r2.core.class1.enumerationmanagement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.class1.state.model.StateEntity;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.r2.core.enumerationmanagement.infc.Enumeration;

@Entity
@Table(name = "KSEM_ENUM_T")
public class EnumerationEntity extends MetaEntity implements AttributeOwner<EnumerationAttributeEntity> {
    
    @Column(name = "NAME")
    private String name;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private EnumerationRichTextEntity descr;
    
    @Column(name = "ENUM_TYPE")
    private String enumerationType;
    
    @Column(name = "ENUM_STATE")
    private String enumerationState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<EnumerationAttributeEntity> attributes = new ArrayList<EnumerationAttributeEntity>();
    
    public EnumerationEntity() {
    }

    public EnumerationEntity(Enumeration enumeration) {
        super(enumeration);
        this.setId(enumeration.getKey());
        this.setName(enumeration.getName());
        if (enumeration.getDescr() != null) {
            this.setDescr(new EnumerationRichTextEntity(enumeration.getDescr()));
        }
        
        this.setAttributes(new ArrayList<EnumerationAttributeEntity>());
        if (null != enumeration.getAttributes()) {
            for (Attribute att : enumeration.getAttributes()) {
                this.getAttributes().add(new EnumerationAttributeEntity(att, this));
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public EnumerationRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(EnumerationRichTextEntity descr) {
        this.descr = descr;
    }
    
    public String getEnumerationType() {
        return enumerationType;
    }

    public void setEnumerationType(String enumerationType) {
        this.enumerationType = enumerationType;
    }

    public String getEnumerationState() {
        return enumerationState;
    }

    public void setEnumerationState(String enumerationState) {
        this.enumerationState = enumerationState;
    }
    
    @Override
    public void setAttributes(List<EnumerationAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<EnumerationAttributeEntity> getAttributes() {
        return attributes;
    }
   
    public EnumerationInfo toDto() {
        EnumerationInfo enumeration = new EnumerationInfo();
        enumeration.setKey(getId());
        enumeration.setName(name);
        if (enumerationType != null)
            enumeration.setTypeKey(enumerationType);
        if (enumerationState != null)
            enumeration.setStateKey(enumerationState);
        enumeration.setMeta(super.toDTO());
        if (descr != null)
            enumeration.setDescr(descr.toDto());

        List<AttributeInfo> enumerations = new ArrayList<AttributeInfo>();
        for (EnumerationAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            enumerations.add(attInfo);
        }
        enumeration.setAttributes(enumerations);

        return enumeration;
    }

}
