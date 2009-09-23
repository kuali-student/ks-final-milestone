package org.kuali.student.core.comment.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;



@Entity
@Table(name = "KSCO_TAG_TYPE_ATTR")
public class TagTypeAttribute extends Attribute<TagType> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private TagType owner;
    
    
    @Override
    public TagType getOwner() {
        return owner;
    }

    @Override
    public void setOwner(TagType owner) {
        this.owner=owner;
        
    }

}
