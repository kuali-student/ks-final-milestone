package org.kuali.student.enumeration.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;
@Entity
@Table(name = "Enumerated_Value_Contexts")
public class EnumeratedValueContext implements Serializable {
    @Id
    String id;
    String enumeratedValueId;
    String contextType;
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

    public String getEnumeratedValueId() {
        return enumeratedValueId;
    }

    public void setEnumeratedValueId(String enumeratedValueId) {
        this.enumeratedValueId = enumeratedValueId;
    }

    public String getContextType() {
        return contextType;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }

    public String getContextValue() {
        return contextValue;
    }

    public void setContextValue(String contextValue) {
        this.contextValue = contextValue;
    }

}
