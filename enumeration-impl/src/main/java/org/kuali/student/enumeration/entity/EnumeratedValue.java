package org.kuali.student.enumeration.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class EnumeratedValue {
    @Id
    String id;

    String enumerationKey;
    String code;
    String abbrevValue;
    String value;
    
    @Temporal(TemporalType.TIMESTAMP)
    Date effectiveDate;
    @Temporal(TemporalType.TIMESTAMP)
    Date expirationDate;
    int sortKey;

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

    public String getEnumerationId() {
        return enumerationKey;
    }

    public void setEnumerationId(String enumerationId) {
        this.enumerationKey = enumerationId;
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

}
