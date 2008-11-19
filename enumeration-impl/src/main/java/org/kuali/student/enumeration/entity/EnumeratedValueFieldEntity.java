package org.kuali.student.enumeration.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class EnumeratedValueFieldEntity {
    @Id
    String id;
    String dataType;
    String fieldKey;
    int minLength;
    int maxLength;
    int minOccurs;
    int maxOccurs;
    String validChars;
    String invalidChars;
    
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    EnumerationMetaEntity enumerationMetaEntity = new EnumerationMetaEntity(); 

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


    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String key) {
        this.fieldKey = key;
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

    public String getValidChars() {
        return validChars;
    }

    public void setValidChars(String validChars) {
        this.validChars = validChars;
    }

    public String getInvalidChars() {
        return invalidChars;
    }

    public void setInvalidChars(String invalidChars) {
        this.invalidChars = invalidChars;
    }
    public EnumerationMetaEntity getEnumerationMetaEntity() {
        return enumerationMetaEntity;
    }
    public void setEnumerationMetaEntity(EnumerationMetaEntity enumerationMetaEntity) {
        this.enumerationMetaEntity = enumerationMetaEntity;
    }

}
