package org.kuali.student.r1.core.document.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.Attribute;;
@Deprecated
@Entity
@Table(name="KSDO_REF_OBJ_SUB_TYPE_ATTR")
public class RefObjectSubTypeAttribute extends Attribute<RefObjectSubType>{

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private RefObjectSubType owner;

    @Override
    public RefObjectSubType getOwner() {
        return owner;
    }

    @Override
    public void setOwner(RefObjectSubType owner) {
        this.owner = owner;
    }

}
