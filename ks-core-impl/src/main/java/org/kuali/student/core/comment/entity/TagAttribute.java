package org.kuali.student.core.comment.entity;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSCO_TAG_ATTR")
public class TagAttribute extends Attribute<Tag>{

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private Tag owner;

    @Override
    public Tag getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Tag owner) {
        this.owner=owner;
        
    }

}
