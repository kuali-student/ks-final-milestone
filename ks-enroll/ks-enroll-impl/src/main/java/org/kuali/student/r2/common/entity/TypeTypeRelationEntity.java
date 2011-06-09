package org.kuali.student.r2.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.TypeTypeRelation;
import org.kuali.student.r2.core.class1.atp.model.AtpAtpRelationAttributeEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpRichTextEntity;

@Entity
@Table(name = "KSEN_TYPETYPE_RELTN")
public class TypeTypeRelationEntity extends MetaEntity {
    
//    @ManyToOne
//    @JoinColumn(name="OWNER_TYPE_ID")
//    private TypeEntity<? extends BaseAttributeEntity> ownerType;
	@Column(name="OWNER_TYPE_ID")
	private String ownerTypeId;
    
//    @ManyToOne
//    @JoinColumn(name="RELATED_TYPE_ID")
//    private TypeEntity<? extends BaseAttributeEntity> relatedType;
	@Column(name="RELATED_TYPE_ID")
	private String relatedTypeId;
    
	@Column(name="TYPETYPE_RELATION_TYPE")
	private String type;
    
    @Column(name = "RANK")
    private Integer rank;

    @Column(name = "NAME")
    private String name;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private AtpRichTextEntity descr;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<TypeTypeRelationAttributeEntity> attributes;

	public TypeTypeRelationEntity() {}
    
    public TypeTypeRelationEntity(TypeTypeRelation typeTypeRel){
        super(typeTypeRel);
        this.setId(typeTypeRel.getKey());
        this.setEffectiveDate(typeTypeRel.getEffectiveDate());
        this.setExpirationDate(typeTypeRel.getExpirationDate());
        this.setAttributes(new ArrayList<TypeTypeRelationAttributeEntity>());
        if (null != typeTypeRel.getAttributes()) {
            for (Attribute att : typeTypeRel.getAttributes()) {
                this.getAttributes().add(new TypeTypeRelationAttributeEntity(att));
            }
        }
    }
    
//    /**
//     * @return the ownerType
//     */
//    public TypeEntity<? extends BaseAttributeEntity> getOwnerType() {
//        return ownerType;
//    }
//
//    /**
//     * @param ownerType the ownerType to set
//     */
//    public void setOwnerType(TypeEntity<? extends BaseAttributeEntity> ownerType) {
//        this.ownerType = ownerType;
//    }
//
//    /**
//     * @return the relatedType
//     */
//    public TypeEntity<? extends BaseAttributeEntity> getRelatedType() {
//        return relatedType;
//    }
//
//    /**
//     * @param relatedType the relatedType to set
//     */
//    public void setRelatedType(TypeEntity<? extends BaseAttributeEntity> relatedType) {
//        this.relatedType = relatedType;
//    }

    public void setOwnerTypeId(String ownerTypeId) {
        this.ownerTypeId = ownerTypeId;
    }

    public String getOwnerTypeId() {
        return ownerTypeId;
    }

    public void setRelatedTypeId(String relatedTypeId) {
        this.relatedTypeId = relatedTypeId;
    }

    public String getRelatedTypeId() {
        return relatedTypeId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
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
     * @return the rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescr(AtpRichTextEntity descr) {
        this.descr = descr;
    }

    public AtpRichTextEntity getDescr() {
        return descr;
    }

    public void setAttributes(List<TypeTypeRelationAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public List<TypeTypeRelationAttributeEntity> getAttributes() {
        return attributes;
    }

    public TypeTypeRelationInfo toDto() {
        TypeTypeRelationInfo typeTypeRel = new TypeTypeRelationInfo();
        
        typeTypeRel.setDescr(null != descr ? descr.toDto() : null);
        typeTypeRel.setRank(rank);
        typeTypeRel.setEffectiveDate(new Date(effectiveDate.getTime()));
        typeTypeRel.setExpirationDate(new Date(expirationDate.getTime()));
        typeTypeRel.setKey(getId());
        typeTypeRel.setName(name);
        typeTypeRel.setOwnerTypeKey(ownerTypeId);
        typeTypeRel.setRelatedTypeKey(relatedTypeId);
        typeTypeRel.setMeta(super.toDTO());
        
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (TypeTypeRelationAttributeEntity att : getAttributes()) {
        	AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        typeTypeRel.setAttributes(atts);
        
        return typeTypeRel;
    }
}
