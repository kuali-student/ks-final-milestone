package org.kuali.student.core.comment.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;
import org.kuali.student.core.entity.RichText;


@Entity
@Table(name = "KSCO_REF")
public class Reference  extends MetaEntity implements AttributeOwner<ReferenceAttribute>{

    
    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private RichText desc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ReferenceAttribute> attributes;
    
    @Column(name = "STATE")
    private String state;
    
    @ManyToOne
    @JoinColumn(name = "TYPE")
    private ReferenceType type;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="ref")
    private List<Tag> tags;
    
    
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    public RichText getDesc() {
        return desc;
    }

    /**
     * @param commentText the commentText to set
     */
    public void setDesc(RichText desc) {
        this.desc = desc;
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
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
    
    @Override
    public List<ReferenceAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<ReferenceAttribute>(0);
        }
        return attributes;
    }

    @Override
    public void setAttributes(List<ReferenceAttribute> attributes) {
        this.attributes = attributes;
        
    }
    
    public List<Tag> getTags(){
        return tags;
    }
    
    public void setTags(List<Tag> tags){
        this.tags=tags;
    }

}
