package org.kuali.student.core.comment.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;


@Entity
@Table(name = "KSCO_REF_ATTR")
public class ReferenceAttribute extends Attribute<Reference>{

    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private Reference owner;
    
    
    
    @Override
    public Reference getOwner() {
        
        return owner;
    }

    @Override
    public void setOwner(Reference owner) {
        this.owner=owner;
        
    }

}
