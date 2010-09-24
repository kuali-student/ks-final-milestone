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

package org.kuali.student.core.enumerationmanagement.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.rice.kns.bo.Inactivateable;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;


@Entity
@Table(name="KSEM_ENUM_VAL_T")
public class EnumerationValue extends PersistableBusinessObjectBase implements Inactivateable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="ID")
    Long id;
    
    Long enumerationTableId;
    
    @Column(name="CD")
    String code;
    @Column(name="ABBREV_VAL")
    String abbrevValue;
    @Column(name="VAL")
    String value;
    
    boolean active;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFF_DT")
    Timestamp effectiveDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIR_DT")
    Timestamp expirationDate;
    
    @Column(name="SORT_KEY")
    int sortKey;
    
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name="KSEM_CTX_JN_ENUM_VAL_T",
      joinColumns=
            @JoinColumn(name="ENUM_VAL_ID", referencedColumnName="ID"),
      inverseJoinColumns=
            @JoinColumn(name="CTX_ID", referencedColumnName="ID")
    )
    List<EnumerationContextValue> enumerationContextValueList = new ArrayList<EnumerationContextValue>();

    
    @Override
    protected LinkedHashMap<String, Object> toStringMapper() {
        LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
        
        toStringMap.put("id", id);
        toStringMap.put("enumerationTableId", enumerationTableId);
        toStringMap.put("code", code);
        toStringMap.put("abbrevValue", abbrevValue);
        toStringMap.put("value", value);
        toStringMap.put("active", active);
        toStringMap.put("effectiveDate", effectiveDate);
        toStringMap.put("expirationDate", expirationDate);
        toStringMap.put("sortKey", sortKey);
        
        return toStringMap;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Timestamp getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Timestamp effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getSortKey() {
        return sortKey;
    }

    public void setSortKey(int sortKey) {
        this.sortKey = sortKey;
    }
    public List<EnumerationContextValue> getEnumerationContextValueList() {
        return enumerationContextValueList;
    }
    public void setEnumerationContextValueList(List<EnumerationContextValue> enumerationContextValueList) {
        this.enumerationContextValueList = enumerationContextValueList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getEnumerationTableId() {
        return enumerationTableId;
    }

    public void setEnumerationTableId(Long enumerationTableId) {
        this.enumerationTableId = enumerationTableId;
    }

}
