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

package org.kuali.student.core.enumerationmanagement.entity;

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

import org.kuali.student.core.entity.BaseEntity;

@Entity
@Table(name="KSEM_ENUM_VAL_T")
public class EnumeratedValue extends BaseEntity {

	@Column(name="CD")
    String code;
    
    @Column(name="VAL")
    String value;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity=Enumeration.class )
    @JoinColumn(name="ENUM_KEY")
    Enumeration enumeration;
        
    @Column(name="ABBREV_VAL")
    String abbrevValue;
        
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFF_DT")
    Date effectiveDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIR_DT")
    Date expirationDate;
    
    @Column(name="SORT_KEY")
    int sortKey;
    
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name="KSEM_CTX_JN_ENUM_VAL_T",
      joinColumns=
            @JoinColumn(name="ENUM_VAL_ID", referencedColumnName="ID"),
      inverseJoinColumns=
            @JoinColumn(name="CTX_ID", referencedColumnName="ID")
    )
    List<ContextEntity> contextEntityList = new ArrayList<ContextEntity>();

    public Enumeration getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(Enumeration enumeration) {
        this.enumeration = enumeration;
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

    public int getSortKey() {
        return sortKey;
    }

    public void setSortKey(int sortKey) {
        this.sortKey = sortKey;
    }
    
    public List<ContextEntity> getContextEntityList() {
        return contextEntityList;
    }
    
    public void setContextEntityList(List<ContextEntity> contextEntityList) {
        this.contextEntityList = contextEntityList;
    }
}
