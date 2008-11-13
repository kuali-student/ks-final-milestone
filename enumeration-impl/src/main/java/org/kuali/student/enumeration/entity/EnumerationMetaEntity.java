package org.kuali.student.enumeration.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class EnumerationMetaEntity implements Serializable{
    @Id
    String id;

    String enumerationKey;
    String name;
    String enumerationMetaKeyDesc;
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

    public void setEnumerationKey(String key) {
        this.enumerationKey = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnumerationMetaKeyDesc() {
        return enumerationMetaKeyDesc;
    }

    public void setEnumerationMetaKeyDesc(String desc) {
        this.enumerationMetaKeyDesc = desc;
    }

}
