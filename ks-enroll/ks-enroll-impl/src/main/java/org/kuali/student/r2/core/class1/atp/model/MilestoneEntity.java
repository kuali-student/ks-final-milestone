package org.kuali.student.r2.core.class1.atp.model;

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

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.infc.Milestone;

@Entity
@Table(name = "KSEN_MSTONE")
public class MilestoneEntity extends MetaEntity implements AttributeOwner<MilestoneAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DT", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DT", nullable = true)
    private Date endDate;

    @Column(name = "MSTONE_TYPE", nullable = false)
    private String atpType;

    @Column(name = "MSTONE_STATE", nullable = false)
    private String atpState;

    @Column(name = "IS_ALL_DAY", nullable = false)
    private boolean isAllDay;

    @Column(name = "IS_INSTRCT_DAY", nullable = false)
    private boolean isInstructionalDay;

    @Column(name = "IS_DATE_RANGE", nullable = false)
    private boolean isDateRange;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = false)
    private String plain;

    @Column(name = "IS_RELATIVE", nullable = false)
    private boolean isRelative;

    @ManyToOne
    @JoinColumn(name = "RELATIVE_ANCHOR_MSTONE_ID")
    private MilestoneEntity relativeAnchorMilestone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<MilestoneAttributeEntity> attributes;

    public MilestoneEntity() {}

    public MilestoneEntity(Milestone milestone) {
        super(milestone);
        this.setId(milestone.getId());
        this.setAllDay( null != milestone.getIsAllDay() ? milestone.getIsAllDay() : false);
        this.setIsInstructionalDay(null != milestone.getIsInstructionalDay() ? milestone.getIsInstructionalDay() : false);
        this.setDateRange(null != milestone.getIsDateRange() ? milestone.getIsDateRange() : false);
        this.setAtpState(milestone.getStateKey());
        this.setAtpType(milestone.getTypeKey());
        this.name = milestone.getName();
        if (milestone.getDescr() != null) {
            RichText rt = milestone.getDescr();
            this.setDescrFormatted(rt.getFormatted());
            this.setDescrPlain(rt.getPlain());
        } else {
            this.setDescrFormatted(new String());
            this.setDescrPlain(new String());
        }
        this.startDate = null != milestone.getStartDate() ? new Date(milestone.getStartDate().getTime()) : null;
        this.endDate = null != milestone.getEndDate() ? new Date(milestone.getEndDate().getTime()) : null;
        this.isRelative = (null != milestone.getIsRelative()) ? milestone.getIsRelative() : false;

        this.setAttributes(new ArrayList<MilestoneAttributeEntity>());
        if (null != milestone.getAttributes()) {
            for (Attribute att : milestone.getAttributes()) {
                this.getAttributes().add(new MilestoneAttributeEntity(att, this));
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAtpType() {
        return atpType;
    }

    public void setAtpType(String atpType) {
        this.atpType = atpType;
    }

    public String getAtpState() {
        return atpState;
    }

    public void setAtpState(String atpState) {
        this.atpState = atpState;
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    public void setAllDay(boolean isAllDay) {
        this.isAllDay = isAllDay;
    }

    public boolean isDateRange() {
        return isDateRange;
    }

    public void setDateRange(boolean isDateRange) {
        this.isDateRange = isDateRange;
    }

    public boolean isRelative() {
        return isRelative;
    }

    public void setRelative(boolean relative) {
        isRelative = relative;
    }

    public MilestoneEntity getRelativeAnchorMilestone() {
        return relativeAnchorMilestone;
    }

    public void setRelativeAnchorMilestone(MilestoneEntity relativeAnchorMilestone) {
        this.relativeAnchorMilestone = relativeAnchorMilestone;
    }

    @Override
    public void setAttributes(List<MilestoneAttributeEntity> attributes) {
        this.attributes = attributes;

    }

    @Override
    public List<MilestoneAttributeEntity> getAttributes() {
        return attributes;
    }

    public MilestoneInfo toDto() {
        MilestoneInfo info = new MilestoneInfo();

        info.setId(getId());
        info.setName(getName());
        info.setTypeKey(null != atpType ? atpType : null);
        info.setStateKey(null != atpState ? atpState : null);
        info.setStartDate(getStartDate());
        info.setEndDate(getEndDate());
        info.setIsAllDay(isAllDay());
        info.setIsDateRange(isDateRange());
        info.setIsInstructionalDay(getIsInstructionalDay());
        info.setIsRelative(isRelative);
        info.setRelativeAnchorMilestoneId(null != relativeAnchorMilestone ? relativeAnchorMilestone.getId() : null);
        info.setMeta(super.toDTO());
        if (getDescrPlain() != null) {
            RichTextInfo rti = new RichTextInfo();
            rti.setPlain(getDescrPlain());
            rti.setFormatted(getDescrFormatted());
            info.setDescr(rti);
        } else {
            info.setDescr(null);
        }

        if (getAttributes() != null) {
            List<AttributeInfo> atts = new ArrayList<AttributeInfo>(getAttributes().size());
            for (MilestoneAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                atts.add(attInfo);
            }

            info.setAttributes(atts);
        }

        return info;
    }

    public String getDescrFormatted() {
        return formatted;
    }

    public void setDescrFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getDescrPlain() {
        return plain;
    }

    public void setDescrPlain(String plain) {
        this.plain = plain;
    }

    public boolean getIsInstructionalDay() {
        return isInstructionalDay;
    }

    public void setIsInstructionalDay(boolean isInstructionalDay) {
        this.isInstructionalDay = isInstructionalDay;
    }

}
