package org.kuali.student.enumeration.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class ContextEntity {
    @Id
    String id;
    String enumerationId;
    
    String contextKey;
    String contextValue;

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
        return contextKey;
    }

    public void setType(String type) {
        this.contextKey = type;
    }

    public String getValue() {
        return contextValue;
    }

    public void setValue(String value) {
        this.contextValue = value;
    }
}
