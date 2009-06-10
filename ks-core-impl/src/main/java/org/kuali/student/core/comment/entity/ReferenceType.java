package org.kuali.student.core.comment.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSCO_REF_TYPE")
public class ReferenceType extends Type<ReferenceTypeAttribute>{

    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "type")
    private List<Reference> references;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ReferenceTypeAttribute> attributes;
    
    
    public List<Reference> getReferences(){
        return references;
    }
    
    public void setReferences(List<Reference> references){
        this.references=references;
    }
    
    @Override
    public List<ReferenceTypeAttribute> getAttributes() {
        
        return attributes;
    }

    @Override
    public void setAttributes(List<ReferenceTypeAttribute> attributes) {
        this.attributes=attributes;
        
    }

}
