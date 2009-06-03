package org.kuali.student.core.enumerationmanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name="KSEM_CTX_META_ENT")
public class ContextMetaEntity {
    @Id
    @Column(name="ID")
    String id;
    @Column(name="ENUM_KEY")
    String enumerationKey;
    @Column(name="TYPE")
    String type;
    @Column(name="DATA_TYPE")
    String dataType;
    @Column(name="MIN_LGTH")
    int minLength;
    @Column(name="MAX_LGTH")
    int maxLength;
    @Column(name="MIN_OCCRS")
    int minOccurs;
    @Column(name="MAX_OCCRS")
    int maxOccurs;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMinOccurs() {
        return minOccurs;
    }

    public void setMinOccurs(int minOccurs) {
        this.minOccurs = minOccurs;
    }

    public int getMaxOccurs() {
        return maxOccurs;
    }

    public void setMaxOccurs(int maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

}
