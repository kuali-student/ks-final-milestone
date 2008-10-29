package org.kuali.student.enumeration.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class Context {
    @Id
    String id;

    String type;
    String value;
    String enumeratedValueId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEnumeratedValueId() {
        return enumeratedValueId;
    }

    public void setEnumeratedValueId(String enumeratedValueId) {
        this.enumeratedValueId = enumeratedValueId;
    }

}
