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
@Table(name = "KSEN_ENUM_VAL_T")
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

 @Column(name = "SORT_KEY")
 private String sortKey;

 @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "EFF_DT")
  private Date effectiveDate;

 @Temporal(TemporalType.TIMESTAMP)
 @Column(name = "EXPIR_DT")
  private Date expirationDate;

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
     return sortKey;
    }

    public void setSortKey(String sortKey) {
    this.sortKey = sortKey;
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
     return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
     this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
     return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
     this.expirationDate = expirationDate;
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
