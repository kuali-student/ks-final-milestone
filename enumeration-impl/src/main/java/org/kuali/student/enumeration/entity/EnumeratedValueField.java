package org.kuali.student.enumeration.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class EnumeratedValueField {
    @Id
    String id;
    String enumerationId;

    String key;
    String dataType;
    int minLength;
    int maxLength;
    int minOccors;
    int maxOccors;
    String validChars;
    String invalidChars;
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
        return enumerationId;
    }

    public void setEnumerationId(String enumerationId) {
        this.enumerationId = enumerationId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public int getMinOccors() {
        return minOccors;
    }

    public void setMinOccors(int minOccors) {
        this.minOccors = minOccors;
    }

    public int getMaxOccors() {
        return maxOccors;
    }

    public void setMaxOccors(int maxOccors) {
        this.maxOccors = maxOccors;
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

}
