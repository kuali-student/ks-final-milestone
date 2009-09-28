package org.kuali.student.core.comment.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;



@Entity
@Table(name = "KSCO_REFERENCE_TYPE_ATTR")
public class ReferenceTypeAttribute extends Attribute<ReferenceType>{
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private ReferenceType owner;

    @Override
    public ReferenceType getOwner() {
    	 return owner;
    }

    @Override
    public void setOwner(ReferenceType owner) {
    	this.owner = owner;
    }

}
