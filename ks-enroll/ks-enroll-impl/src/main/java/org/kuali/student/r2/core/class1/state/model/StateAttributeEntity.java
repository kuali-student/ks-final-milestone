package org.kuali.student.r2.core.class1.state.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_STATE_ATTR")
public class StateAttributeEntity extends BaseAttributeEntity<StateEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private StateEntity owner;

    public StateAttributeEntity () {
    }
    
    public StateAttributeEntity(String key, String value) {
        super(key, value);
    }
    public StateAttributeEntity(Attribute att, StateEntity owner) {
        super(att);
        this.owner = owner;
    }

    public StateAttributeEntity(Attribute att) {
        super(att);
    }

    public void setOwner(StateEntity owner) {
        this.owner = owner;
    }

    public StateEntity getOwner() {
        return owner;
    }
}
