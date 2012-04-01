package org.kuali.student.r2.core.class1.state.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntityNew;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_STATE_LIFECYCLE_ATTR")
public class LifecycleAttributeEntity extends BaseAttributeEntityNew<StateEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private StateEntity owner;

    public LifecycleAttributeEntity () {
    }
    
    public LifecycleAttributeEntity(String key, String value) {
        super(key, value);
    }
    public LifecycleAttributeEntity(Attribute att, StateEntity owner) {
        super(att);
        this.owner = owner;
    }

    public LifecycleAttributeEntity(Attribute att) {
        super(att);
    }

    public void setOwner(StateEntity owner) {
        this.owner = owner;
    }

    public StateEntity getOwner() {
        return owner;
    }
}
