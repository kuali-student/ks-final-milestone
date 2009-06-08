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
@Table(name = "KSCO_TAG_TYPE")
public class TagType extends Type<TagTypeAttribute> {

    @Id
    @Column(name = "ID")
    private String id;
    
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
    private List<TagTypeAttribute> attributes;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="type")
    private List<Tag> tags;

    /**
     * 
     * @return
     */
    public String getId(){
        return id;
    }

    /**
     * 
     * @param id
     */
    public void setId(String id){
        this.id=id;
    }
    
    /**
     * 
     * @return
     */
    public String getName(){
        return name;
    }
    
    /**
     * 
     * @param name
     */
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * 
     * @return
     */
    public String getDesc(){
        return desc;
    }
    
    /**
     * 
     * @param desc
     */
    public void setDesc(String desc){
        this.desc=desc;
    }
    
    /**
     * @return the effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @param effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

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
