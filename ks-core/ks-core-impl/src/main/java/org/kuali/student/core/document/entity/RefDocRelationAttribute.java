package org.kuali.student.core.document.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.common.entity.Attribute;;
@Entity
@Table(name = "KSDO_REF_DOC_REL_ATTR")
public class RefDocRelationAttribute extends Attribute<RefDocRelation>{

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private RefDocRelation owner;

    @Override
    public RefDocRelation getOwner() {
        return owner;
    }

    @Override
    public void setOwner(RefDocRelation owner) {
        this.owner = owner;
    }

}
