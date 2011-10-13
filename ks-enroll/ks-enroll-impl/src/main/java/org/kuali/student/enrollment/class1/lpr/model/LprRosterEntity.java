package org.kuali.student.enrollment.class1.lpr.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.model.StateEntity;

@Entity
@Table(name = "KSEN_LPR_ROSTER")
public class LprRosterEntity extends MetaEntity implements AttributeOwner<LprRosterAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LprRichTextEntity descr;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "KSEN_LPRROSTER_LUI_RELTN", joinColumns = @JoinColumn(name = "LPRROSTER_ID"), inverseJoinColumns = @JoinColumn(name = "LUI_ID"))
    private List<LuiEntity> associatedLuis;

    @Column(name = "MAX_CAPACITY")
    private int maximumCapacity;

    @Column(name = "CHECK_IN_REQ")
    private boolean checkInRequired;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TYPE_ID")
    private LuiPersonRelationTypeEntity lprRosterType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "STATE_ID")
    private StateEntity lprRosterState;

    @Column(name = "ATP_DUR_TYP_KEY")
    private String atpDurationTypeKey;

    @Column(name = "TM_QUANTITY")
    private Integer timeQuantity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LprRosterAttributeEntity> attributes;

    public LprRosterEntity() {

    }

    public LprRosterEntity(LprRosterInfo dto) {
        super(dto);
        this.setCheckInRequired(dto.getCheckInRequired());
        this.setMaximumCapacity(dto.getMaximumCapacity());
        this.setId(dto.getId());
        if (dto.getCheckInFrequency() != null) {
            this.setAtpDurationTypeKey(dto.getCheckInFrequency().getAtpDurationTypeKey());
            this.setTimeQuantity(dto.getCheckInFrequency().getTimeQuantity());
        }
        this.setName(dto.getName());
        if (dto.getDescr() != null) {
            LprRichTextEntity entityDesc = new LprRichTextEntity(dto.getDescr());
            this.setDescr(entityDesc);
        }

        this.setAttributes(new ArrayList<LprRosterAttributeEntity>());
        if (null != dto.getAttributes()) {
            for (Attribute att : dto.getAttributes()) {
                LprRosterAttributeEntity attEntity = new LprRosterAttributeEntity(att);
                this.getAttributes().add(attEntity);
            }
        }
    }

    public List<LuiEntity> getAssociatedLuis() {
        return associatedLuis;
    }

    public void setAssociatedLuis(List<LuiEntity> associatedLuis) {
        this.associatedLuis = associatedLuis;
    }

    public void setCheckInRequired(boolean checkInRequired) {
        this.checkInRequired = checkInRequired;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public Boolean getCheckInRequired() {
        return checkInRequired;
    }

    public void setCheckInRequired(Boolean checkInRequired) {
        this.checkInRequired = checkInRequired;
    }

    public LuiPersonRelationTypeEntity getLprRosterType() {
        return lprRosterType;
    }

    public void setLprRosterType(LuiPersonRelationTypeEntity lprRosterType) {
        this.lprRosterType = lprRosterType;
    }

    public StateEntity getLprRosterState() {
        return lprRosterState;
    }

    public void setLprRosterState(StateEntity lprRosterState) {
        this.lprRosterState = lprRosterState;
    }

    public String getAtpDurationTypeKey() {
        return atpDurationTypeKey;
    }

    public void setAtpDurationTypeKey(String atpDurationTypeKey) {
        this.atpDurationTypeKey = atpDurationTypeKey;
    }

    public Integer getTimeQuantity() {
        return timeQuantity;
    }

    public void setTimeQuantity(Integer timeQuantity) {
        this.timeQuantity = timeQuantity;
    }

    @Override
    public List<LprRosterAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<LprRosterAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public LprRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(LprRichTextEntity descr) {
        this.descr = descr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LprRosterInfo toDto() {
        LprRosterInfo info = new LprRosterInfo();
        info.setId(this.getId());
        TimeAmountInfo timeAmountInfo = new TimeAmountInfo();
        timeAmountInfo.setAtpDurationTypeKey(this.getAtpDurationTypeKey());
        timeAmountInfo.setTimeQuantity(this.getTimeQuantity());
        info.setCheckInFrequency(timeAmountInfo);
        info.setCheckInRequired(this.getCheckInRequired());
        info.setMaximumCapacity(this.getMaximumCapacity());
        info.setName(this.getName());
        info.setStateKey(getLprRosterState().getId());
        info.setTypeKey(getLprRosterType().getId());
        if (getAssociatedLuis() != null) {
            List<String> associatedLuiIds = new ArrayList();
            for (LuiEntity luiEntity : getAssociatedLuis()) {
                associatedLuiIds.add(luiEntity.getId());
            }
            info.setAssociatedLuiIds(associatedLuiIds);
        }

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LprRosterAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        info.setAttributes(atts);

        info.setMeta(super.toDTO());

        if (descr != null) {
            info.setDescr(descr.toDto());
        }

        /**
         * FIXME: Do we need to copy create and update time/id?
         */
        return info;
    }
}
