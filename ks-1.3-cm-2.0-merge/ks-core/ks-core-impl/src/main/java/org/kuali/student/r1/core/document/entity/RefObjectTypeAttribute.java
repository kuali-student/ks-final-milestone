package org.kuali.student.r1.core.document.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.Attribute;;
@Deprecated
@Entity
@Table(name="KSDO_REF_OBJ_TYPE_ATTR")
public class RefObjectTypeAttribute extends Attribute<RefObjectType>{

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private RefObjectType owner;

    @Override
    public RefObjectType getOwner() {
        return owner;
    }

    @Override
    public void setOwner(RefObjectType owner) {
        this.owner = owner;
    }

}
