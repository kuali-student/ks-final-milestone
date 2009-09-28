package org.kuali.student.core.enumerationmanagement.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name="KSEM_ENUM_VAL_ENT")
public class EnumeratedValueEntity {
    @Id
    @Column(name="ID")
    String id;

    @Column(name="ENUM_KEY")
    String enumerationKey;
    @Column(name="CD")
    String code;
    @Column(name="ABBREV_VAL")
    String abbrevValue;
    @Column(name="VAL")
    String value;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFF_DT")
    Date effectiveDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIR_DT")
    Date expirationDate;
    
    @Column(name="SORT_KEY")
    int sortKey;
    
    //@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @OneToMany(/*mappedBy="enumeratedValueEntityList",*/fetch = FetchType.LAZY,cascade={CascadeType.ALL})
    @JoinColumn(name="ENUM_VAL_ENT_ID")
    List<ContextEntity> contextEntityList =new ArrayList<ContextEntity>();

    /**
     * AutoGenerate the id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnumerationKey() {
        return enumerationKey;
    }

    public void setEnumerationKey(String enumerationKey) {
        this.enumerationKey = enumerationKey;
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
