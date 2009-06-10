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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;



@Entity
@Table(name = "KSCO_TAG")
@NamedQueries( {
        @NamedQuery(name = "Tag.getTags", query = "SELECT  tag FROM Tag tag JOIN tag.ref r1 WHERE tag.ref.id =:refId AND r1.type.id=:refTypeId"),
        @NamedQuery(name = "Tag.getTagsByType", query = "SELECT  tag FROM Tag tag JOIN tag.ref r1 WHERE tag.ref.id =:refId AND r1.type.id=:refTypeId AND tag.type.id=:tagTypeId")})
public class Tag extends MetaEntity implements AttributeOwner<TagAttribute>{

    @Id
    @Column(name = "ID")
    private String id;


    @Column(name = "NAME_SPACE")
    private String nameSpace;
    
    @Column(name = "PREDICATE")
    private String predicate;
    
    @Column(name = "VAL")
    private String value;
    
    
    @ManyToOne
    @JoinColumn(name = "REF")
    private Reference ref;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<TagAttribute> attributes;

    @ManyToOne
    @JoinColumn(name = "TYPE")
    private TagType type;

    @Column(name = "STATE")
    private String state;

    /**
     * AutoGenerate the Id
     */
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
    

    public String getNameSpace(){
        return nameSpace;
    }
    
    public void setNameSpace(String nameSpace){
        this.nameSpace=nameSpace;
    }
    
    public String getPredicate(){
        return predicate;
    }
    
    public void setPredicate(String predicate){
        this.predicate=predicate;
    }
    
    public String getValue(){
        return value;
    }
    
    public void setValue(String value){
        this.value=value;
    }
    
    
    public Reference getReferennce(){
        return ref;
    }
    
    public void setReference(Reference ref){
        this.ref=ref;
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


    @Override
    public List<TagAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<TagAttribute>(0);
        }
        return attributes;
    }

    @Override
    public void setAttributes(List<TagAttribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the type
     */
    public TagType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(TagType type) {
        this.type = type;
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
    
}
