package org.kuali.student.core.document.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;
@Entity
@Table(name="KSDO_REF_DOC_RELTN_TYPE_ATTR")
public class RefDocRelationTypeAttribute extends Attribute<RefDocRelationType>{

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private RefDocRelationType owner;

    @Override
    public RefDocRelationType getOwner() {
        return owner;
    }

    @Override
    public void setOwner(RefDocRelationType owner) {
        this.owner = owner;
    }

}
