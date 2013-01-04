package org.kuali.student.enrollment.class1.roster.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LPR_ROSTER_ENTRY")
public class LprRosterEntryEntity extends MetaEntity implements AttributeOwner<LprRosterEntryAttributeEntity>{

    @Column(name = "LPRROSTER_ID")
    private String lprRosterId;

    @Column(name = "LPR_ID")
    private String lprId;

    @Column(name = "POSITION")
    private Integer position;

    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Column(name = "RELATION_TYPE_ID")
    private String lprEntryRelationType;

    @Column(name = "RELATION_STATE_ID")
    private String lprEntryRelationState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval=true)
    private Set<LprRosterEntryAttributeEntity> attributes;

    public LprRosterEntryEntity() {}

    public LprRosterEntryEntity(LprRosterEntryInfo dto) {
        if (dto != null) {
            this.setId(dto.getId());
            this.setLprId(dto.getLprId());
            this.setLprRosterId(dto.getLprRosterId());
            this.setExpirationDate(dto.getExpirationDate());
            this.setEffectiveDate(dto.getEffectiveDate());
            this.setPosition(dto.getPosition());
            if (dto.getStateKey() != null) {
                this.setLprEntryRelationState(dto.getStateKey());
            }
            this.setAttributes(new HashSet<LprRosterEntryAttributeEntity>());
            if (null != dto.getAttributes()) {
                for (Attribute att : dto.getAttributes()) {
                    LprRosterEntryAttributeEntity attEntity = new LprRosterEntryAttributeEntity(att, this);
                    this.getAttributes().add(attEntity);
                }
            }
        }
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getLprId() {
        return lprId;
    }

    public void setLprId(String lprId) {
        this.lprId = lprId;
    }

    public String getLprRosterId() {
        return lprRosterId;
    }

    public void setLprRosterId(String lprRosterId) {
        this.lprRosterId = lprRosterId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Set<LprRosterEntryAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<LprRosterEntryAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public String getLprEntryRelationState() {
        return lprEntryRelationState;
    }

    public void setLprEntryRelationState(String lprEntryRelationState) {
        this.lprEntryRelationState = lprEntryRelationState;
    }

    public String getLprEntryRelationType() {
        return lprEntryRelationType;
    }

    public void setLprEntryRelationType(String lprEntryRelationType) {
        this.lprEntryRelationType = lprEntryRelationType;
    }

    public LprRosterEntryInfo toDto() {
        LprRosterEntryInfo info = new LprRosterEntryInfo();
        info.setId(this.getId());
        info.setEffectiveDate(getEffectiveDate());
        info.setExpirationDate(getExpirationDate());
        info.setLprId(getLprId());
        info.setLprRosterId(getLprRosterId());
        info.setPosition(getPosition());

        if (getLprEntryRelationState() != null) {
            info.setStateKey(getLprEntryRelationState());
        }
        if (getLprEntryRelationType() != null) {
            info.setTypeKey(getLprEntryRelationType());
        }

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LprRosterEntryAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        info.setAttributes(atts);
        info.setMeta(super.toDTO());

        return info;
    }

}
