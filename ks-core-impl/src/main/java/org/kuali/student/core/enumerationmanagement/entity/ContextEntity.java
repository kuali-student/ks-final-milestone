package org.kuali.student.core.enumerationmanagement.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name="KSEM_CTX_ENT", uniqueConstraints={@UniqueConstraint(columnNames={"ENUM_VAL_ENT_ID", "CTX_KEY", "CTX_VAL"})})
public class ContextEntity {
    @Id
    @Column(name="ID")
    String id;
    
    @Column(name="ENUM_ID")
    String enumerationId;
    
    @Column(name="CTX_KEY")
    String contextKey;
    
    @Column(name="CTX_VAL")
    String contextValue;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="ENUM_VAL_ENT_ID")
    EnumeratedValueEntity enumeratedValueEntityList =new EnumeratedValueEntity();
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
    
    public EnumeratedValueEntity getEnumeratedValueEntity() {
        return enumeratedValueEntityList;
    }
    public void setEnumeratedValueEntity(EnumeratedValueEntity enumeratedValueEntityList) {
        this.enumeratedValueEntityList = enumeratedValueEntityList;
    }
    
}
