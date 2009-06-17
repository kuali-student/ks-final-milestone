package org.kuali.student.core.comment.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;



@Entity
@Table(name = "KSCO_REF_TYPE_ATTR")
public class ReferenceTypeAttribute extends Attribute<ReferenceType>{

    @Override
    public ReferenceType getOwner() {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public void setOwner(ReferenceType owner) {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        
    }

}
