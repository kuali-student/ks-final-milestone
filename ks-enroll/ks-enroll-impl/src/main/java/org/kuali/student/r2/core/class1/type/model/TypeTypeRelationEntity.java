package org.kuali.student.r2.core.class1.type.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.type.infc.TypeTypeRelation;

@Entity
@Table(name = "KSEN_TYPETYPE_RELTN")
public class TypeTypeRelationEntity extends MetaEntity implements AttributeOwner<TypeTypeRelationAttributeEntity> {

    @Column(name = "OWNER_TYPE_ID")
    private String ownerTypeId;

    @Column(name = "RANK")
    private Integer rank;

    @Column(name = "RELATED_TYPE_ID")
    private String relatedTypeId;

    @Column(name = "TYPETYPE_RELATION_TYPE")
    private String type;

    @Column(name = "TYPETYPE_RELATION_STATE")
    private String state;

    @Column(name = "NAME")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<TypeTypeRelationAttributeEntity> attributes;

    public TypeTypeRelationEntity() {}

    public TypeTypeRelationEntity(TypeTypeRelation typeTypeRel) {
        super(typeTypeRel);

        this.setId(typeTypeRel.getId());
        this.setEffectiveDate(typeTypeRel.getEffectiveDate());
        this.setExpirationDate(typeTypeRel.getExpirationDate());
        this.setRank(typeTypeRel.getRank());
        this.setOwnerTypeId(typeTypeRel.getOwnerTypeKey());
        this.setRelatedTypeId(typeTypeRel.getRelatedTypeKey());
        
        this.setAttributes(new ArrayList<TypeTypeRelationAttributeEntity>());
        if (null != typeTypeRel.getAttributes()) {
            for (Attribute att : typeTypeRel.getAttributes()) {
                this.getAttributes().add(new TypeTypeRelationAttributeEntity(att));
            }
        }

    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

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

  
    public void setAttributes(List<TypeTypeRelationAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public List<TypeTypeRelationAttributeEntity> getAttributes() {
        return attributes;
    }

    public TypeTypeRelationInfo toDto() {
        TypeTypeRelationInfo typeTypeRel = new TypeTypeRelationInfo();

        typeTypeRel.setRank(rank);
        typeTypeRel.setEffectiveDate(new Date(effectiveDate.getTime()));
        typeTypeRel.setExpirationDate(new Date(expirationDate.getTime()));
        typeTypeRel.setId(getId());
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
