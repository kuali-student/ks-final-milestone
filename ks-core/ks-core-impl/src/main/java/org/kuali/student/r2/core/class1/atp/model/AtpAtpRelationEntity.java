package org.kuali.student.r2.core.class1.atp.model;

import java.util.*;

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
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.infc.AtpAtpRelation;

@Entity
@Table(name = "KSEN_ATPATP_RELTN")
public class AtpAtpRelationEntity extends MetaEntity implements AttributeOwner<AtpAtpRelationAttributeEntity> {

    @ManyToOne
    @JoinColumn(name = "ATP_ID", nullable = false)
    private AtpEntity atp;
    @ManyToOne
    @JoinColumn(name = "RELATED_ATP_ID", nullable = false)
    private AtpEntity relatedAtp;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
    @Column(name = "ATP_TYPE", nullable = false)
    private String atpType;
    @Column(name = "ATP_STATE", nullable = false)
    private String atpState;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<AtpAtpRelationAttributeEntity> attributes;

    public AtpAtpRelationEntity() {
    }

    public AtpAtpRelationEntity(AtpAtpRelation atpAtpRelation) {
        this.setId(atpAtpRelation.getId());
        this.setAtpType(atpAtpRelation.getTypeKey());
        this.fromDTO(atpAtpRelation);
    }

    public void fromDTO(AtpAtpRelation atpAtpRelation) {
        this.setAtpState(atpAtpRelation.getStateKey());
        this.setEffectiveDate(atpAtpRelation.getEffectiveDate());
        this.setExpirationDate(atpAtpRelation.getExpirationDate());
        this.setAttributes(new HashSet<AtpAtpRelationAttributeEntity>());
        for (Attribute att : atpAtpRelation.getAttributes()) {
            this.getAttributes().add(new AtpAtpRelationAttributeEntity(att, this));
        }
    }

    public AtpEntity getAtp() {
        return atp;
    }

    public void setAtp(AtpEntity atp) {
        this.atp = atp;
    }

    public AtpEntity getRelatedAtp() {
        return relatedAtp;
    }

    public void setRelatedAtp(AtpEntity relatedAtp) {
        this.relatedAtp = relatedAtp;
    }

    public String getAtpType() {
        return atpType;
    }

    public void setAtpType(String atpType) {
        this.atpType = atpType;
    }

    public Date getEffectiveDate() {
        return effectiveDate != null ? new Date(effectiveDate.getTime()) : null;
    }

    public void setEffectiveDate(Date effectiveDate) {
        if (effectiveDate != null) {
            this.effectiveDate = new Date(effectiveDate.getTime());
        }
    }

    public Date getExpirationDate() {
        return expirationDate != null ? new Date(expirationDate.getTime()) : null;
    }

    public void setExpirationDate(Date expirationDate) {
        if (expirationDate != null) {
            this.expirationDate = new Date(expirationDate.getTime());
        }
    }

    public String getAtpState() {
        return atpState;
    }

    public void setAtpState(String atpState) {
        this.atpState = atpState;
    }

    public void setAttributes(Set<AtpAtpRelationAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public Set<AtpAtpRelationAttributeEntity> getAttributes() {
        return attributes;
    }

    public AtpAtpRelationInfo toDto() {
        AtpAtpRelationInfo info = new AtpAtpRelationInfo();
        info.setId(getId());
        info.setAtpId(atp.getId());
        info.setRelatedAtpId(relatedAtp.getId());
        info.setEffectiveDate(effectiveDate);
        info.setExpirationDate(expirationDate);
        info.setStateKey(atpState);
        info.setTypeKey(atpType);
        info.setMeta(super.toDTO());
        if (getAttributes() != null) {
            for (AtpAtpRelationAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                info.getAttributes().add(attInfo);
            }
        }
        return info;
    }
}
