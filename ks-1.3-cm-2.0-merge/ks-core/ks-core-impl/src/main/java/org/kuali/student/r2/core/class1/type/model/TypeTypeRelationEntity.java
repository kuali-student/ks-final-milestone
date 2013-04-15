package org.kuali.student.r2.core.class1.type.model;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.infc.TypeTypeRelation;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "KSEN_TYPETYPE_RELTN")
public class TypeTypeRelationEntity extends MetaEntity implements AttributeOwner<TypeTypeRelationAttributeEntity> {

    @Column(name = "OWNER_TYPE_ID")
    private String ownerTypeId;
    @Column(name = "RANK")
    private Integer rank;
    @Column(name = "RELATED_TYPE_ID")
    private String relatedTypeId;
    @Column(name = "TYPETYPE_RELTN_TYPE")
    private String type;
    @Column(name = "TYPETYPE_RELTN_STATE")
    private String state;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<TypeTypeRelationAttributeEntity> attributes = new HashSet<TypeTypeRelationAttributeEntity>();

    public TypeTypeRelationEntity() {
    }

    public TypeTypeRelationEntity(TypeTypeRelation typeTypeRel) {
        // Note: readonly fields are set here
        super(typeTypeRel);
        this.setId(typeTypeRel.getId());
        this.setOwnerTypeId(typeTypeRel.getOwnerTypeKey());
        this.setRelatedTypeId(typeTypeRel.getRelatedTypeKey());
        this.setType(typeTypeRel.getTypeKey());
        this.setState(typeTypeRel.getStateKey());
        // updatable fields are set here
        this.fromDto(typeTypeRel);
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
     * @param effectiveDate
     *            the effectiveDate to set
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
     * @param expirationDate
     *            the expirationDate to set
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
     * @param rank
     *            the rank to set
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void setAttributes(Set<TypeTypeRelationAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public Set<TypeTypeRelationAttributeEntity> getAttributes() {
        return attributes;
    }

    public void fromDto(TypeTypeRelation typeTypeRel) {
        // NOTE: fields that are readonly are only set in the constructor above
        this.setEffectiveDate(typeTypeRel.getEffectiveDate());
        this.setExpirationDate(typeTypeRel.getExpirationDate());
        this.setRank(typeTypeRel.getRank());
        this.setAttributes(new HashSet<TypeTypeRelationAttributeEntity>());
        for (Attribute att : typeTypeRel.getAttributes()) {
            this.getAttributes().add(new TypeTypeRelationAttributeEntity(att, this));
        }
    }

    public TypeTypeRelationInfo toDto() {
        TypeTypeRelationInfo typeTypeRel = new TypeTypeRelationInfo();
        typeTypeRel.setRank(rank);
        typeTypeRel.setEffectiveDate(effectiveDate);
        typeTypeRel.setExpirationDate(expirationDate);
        typeTypeRel.setId(getId());
        typeTypeRel.setOwnerTypeKey(ownerTypeId);
        typeTypeRel.setRelatedTypeKey(relatedTypeId);
        typeTypeRel.setTypeKey(this.getType());
        typeTypeRel.setStateKey(this.getState());
        typeTypeRel.setMeta(super.toDTO());
        if (attributes != null) {
            for (TypeTypeRelationAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                typeTypeRel.getAttributes().add(attInfo);
            }
        }
        return typeTypeRel;
    }
}
