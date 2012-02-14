package org.kuali.student.enrollment.class1.lpr.model;

import org.kuali.student.enrollment.lpr.dto.LprRosterEntryInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.class1.state.model.StateEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "KSEN_LPR_ROSTER_ENTRY")
public class LprRosterEntryEntity extends MetaEntity implements AttributeOwner<LprRosterEntryAttributeEntity> {

    @Column(name = "LPRROSTER_ID")
    private String lprRosterId;

    @Column(name = "LPR_ID")
    private String lprId;

    @Column(name = "POSITION")
    private String position;

    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RELATION_TYPE_ID")
    private LuiPersonRelationTypeEntity lprEntryRelationType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RELATION_STATE_ID")
    private StateEntity lprEntryRelationState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private List<LprRosterEntryAttributeEntity> attributes;

    public LprRosterEntryEntity(){}

    public LprRosterEntryEntity(LprRosterEntryInfo dto) {
        if (dto != null) {
            this.setId(dto.getId());
            this.setLprId(dto.getLprId());
            this.setLprRosterId(dto.getLprRosterId());
            this.setExpirationDate(dto.getExpirationDate());
            this.setEffectiveDate(dto.getEffectiveDate());
            this.setPosition(dto.getPosition());

            this.setAttributes(new ArrayList<LprRosterEntryAttributeEntity>());
            if (null != dto.getAttributes()) {
                for (Attribute att : dto.getAttributes()) {
                    LprRosterEntryAttributeEntity attEntity = new LprRosterEntryAttributeEntity(att);
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public List<LprRosterEntryAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<LprRosterEntryAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public StateEntity getLprEntryRelationState() {
        return lprEntryRelationState;
    }

    public void setLprEntryRelationState(StateEntity lprEntryRelationState) {
        this.lprEntryRelationState = lprEntryRelationState;
    }

    public LuiPersonRelationTypeEntity getLprEntryRelationType() {
        return lprEntryRelationType;
    }

    public void setLprEntryRelationType(LuiPersonRelationTypeEntity lprEntryRelationType) {
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

        if (getLprEntryRelationState() != null){
            info.setStateKey(getLprEntryRelationState().getId());
        }
        if (getLprEntryRelationType() != null){
            info.setTypeKey(getLprEntryRelationType().getId());
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
