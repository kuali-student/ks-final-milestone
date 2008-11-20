package org.kuali.student.enumeration.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class ContextEntity {
    @Id
    String id;
    String enumerationId;
    
    String contextKey;
    String contextValue;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    List<EnumeratedValueEntity> enumeratedValueEntityList =new ArrayList<EnumeratedValueEntity>();
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

    public String getContextKey() {
        return contextKey;
    }

    public void setContextKey(String type) {
        this.contextKey = type;
    }

    public String getContextValue() {
        return contextValue;
    }

    public void setContextValue(String value) {
        this.contextValue = value;
    }
    public List<EnumeratedValueEntity> getEnumeratedValueEntityList() {
        return enumeratedValueEntityList;
    }
    public void setEnumeratedValueEntityList(List<EnumeratedValueEntity> enumeratedValueEntityList) {
        this.enumeratedValueEntityList = enumeratedValueEntityList;
    }
    
}
