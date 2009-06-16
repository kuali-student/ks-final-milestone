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

    @Id
    @Column(name = "ID")
    private String id;
    
    @Column(name ="REFERENCE_TYPE")
    private String referenceTypeKey;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "DESCRIPTION")
    private String desc;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ReferenceTypeAttribute> attributes;
    
    
    @Override
    public List<ReferenceTypeAttribute> getAttributes() {
        
        return attributes;
    }

    @Override
    public void setAttributes(List<ReferenceTypeAttribute> attributes) {
        this.attributes=attributes;
        
    }

}
