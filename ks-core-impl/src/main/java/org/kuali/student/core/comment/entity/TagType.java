package org.kuali.student.core.comment.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;


@Entity
@Table(name = "KSCO_TAG_TYPE")
public class TagType extends Type<TagTypeAttribute> {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<TagTypeAttribute> attributes;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="type")
    private List<Tag> tags;

    /**
     * 
     * @return
     */
    @Override
    public List<TagTypeAttribute> getAttributes() {
        
        return attributes;
    }

    /**
     * 
     * @param attributes
     */
    @Override
    public void setAttributes(List<TagTypeAttribute> attributes) {
        this.attributes=attributes;
        
    }
    
    /**
     * 
     * @return
     */
    public List<Tag> getTag(){
        return tags;
    }
    
    
    /**
     * 
     * @param tags
     */
    public void setTag(List<Tag> tags){
        this.tags=tags;
    }
    
    
}
