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
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumContextValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.infc.EnumContextValue;
import org.kuali.student.r2.core.enumerationmanagement.infc.EnumeratedValue;

@Entity
@Table(name = "KSEM_ENUM_VAL_T")
public class EnumeratedValueEntity extends MetaEntity{

    @Column(name = "CD")
    private String code;

    @Column(name = "ABBREV_VAL")
    private String abbrevValue;

    @Column(name = "VAL")
    private String value;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = EnumerationEntity.class)
    @JoinColumn(name = "ENUM_KEY")
    private EnumerationEntity enumeration;

 // kscm-313@Column(name = "SORT_KEY")
 // kscm-313private String sortKey;

 // kscm-313@Temporal(TemporalType.TIMESTAMP)
 // kscm-313 @Column(name = "EFF_DT")
 // kscm-313 private Date effectiveDate;

 // kscm-313 @Temporal(TemporalType.TIMESTAMP)
 // kscm-313 @Column(name = "EXPIR_DT")
 // kscm-313 private Date expirationDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "KSEM_CTX_JN_ENUM_VAL_T", joinColumns = @JoinColumn(name = "ENUM_VAL_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "CTX_ID", referencedColumnName = "ID"))
    List<EnumContextValueEntity> contextValueEntities = new ArrayList<EnumContextValueEntity>();

    public EnumeratedValueEntity() {
    }

    public EnumeratedValueEntity(EnumeratedValue enumeratedValue) {
        super(enumeratedValue);
        this.setCode(enumeratedValue.getCode());
        this.setAbbrevValue(enumeratedValue.getAbbrevValue());
        this.setValue(enumeratedValue.getValue());
        this.setSortKey(enumeratedValue.getSortKey());
        
        this.setContextValueEntities(new ArrayList<EnumContextValueEntity>());
        if (null != enumeratedValue.getContexts()) {
            for (EnumContextValue context : enumeratedValue.getContexts()) {
                this.getContextValueEntities().add(new EnumContextValueEntity(context));
            }
        }

        this.setEffectiveDate(enumeratedValue.getEffectiveDate());
        this.setExpirationDate(enumeratedValue.getExpirationDate());
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAbbrevValue() {
        return abbrevValue;
    }

    public void setAbbrevValue(String abbrevValue) {
        this.abbrevValue = abbrevValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSortKey() {
     // kscm-313return sortKey;
        return null;
    }

    public void setSortKey(String sortKey) {
     // kscm-313this.sortKey = sortKey;
    }

    public List<EnumContextValueEntity> getContextValueEntities() {
        return contextValueEntities;
    }

    public void setContextValueEntities(List<EnumContextValueEntity> contextValueEntities) {
        this.contextValueEntities = contextValueEntities;
    }

    public EnumerationEntity getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(EnumerationEntity enumeration) {
        this.enumeration = enumeration;
    }

    public Date getEffectiveDate() {
     // kscm-313return effectiveDate;
        return null;
    }

    public void setEffectiveDate(Date effectiveDate) {
     // kscm-313 this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
     // kscm-313  return expirationDate;
        return null;
    }

    public void setExpirationDate(Date expirationDate) {
     // kscm-313 this.expirationDate = expirationDate;
    }
    
    public EnumeratedValueInfo toDto() {
        EnumeratedValueInfo enumeratedValue = new EnumeratedValueInfo();
        enumeratedValue.setCode(this.getCode());
        enumeratedValue.setAbbrevValue(this.getAbbrevValue());
        enumeratedValue.setValue(this.getValue());
        enumeratedValue.setSortKey(this.getSortKey());
        enumeratedValue.setMeta(super.toDTO());
        
        List<EnumContextValueInfo> contextInfos = new ArrayList<EnumContextValueInfo>();
        for (EnumContextValueEntity contexts : getContextValueEntities()) {
            EnumContextValueInfo contextInfo = contexts.toDto();
            contextInfos.add(contextInfo);
        }
        enumeratedValue.setContexts(contextInfos);
        
        enumeratedValue.setEnumerationKey(this.getEnumeration().getId());
        
        enumeratedValue.setEffectiveDate(this.getEffectiveDate());
        enumeratedValue.setExpirationDate(this.getExpirationDate());
        
        return enumeratedValue;
    }

}
