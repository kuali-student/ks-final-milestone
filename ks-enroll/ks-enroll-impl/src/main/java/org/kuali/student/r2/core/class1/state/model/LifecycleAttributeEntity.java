package org.kuali.student.r2.core.class1.state.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_STATE_LIFECYCLE_ATTR")
public class LifecycleAttributeEntity extends BaseAttributeEntity<LifecycleEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private LifecycleEntity owner;

    public LifecycleAttributeEntity () {
    }
    
    public LifecycleAttributeEntity(String key, String value) {
        super(key, value);
    }
    public LifecycleAttributeEntity(Attribute att, LifecycleEntity owner) {
        super(att);
        this.owner = owner;
    }

    public LifecycleAttributeEntity(Attribute att) {
        super(att);
    }

    public void setOwner(LifecycleEntity owner) {
        this.owner = owner;
    }

    public LifecycleEntity getOwner() {
        return owner;
    }
}
