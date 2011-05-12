package org.kuali.student.r2.core.classI.atp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.infc.Milestone;

@Entity
@Table(name = "KSEN_MSTONE")
public class MilestoneEntity extends MetaEntity implements AttributeOwner<AtpAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private AtpRichTextEntity descr;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DT")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DT")
    private Date endDate;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MILESTONE_TYPE_ID")
    private MilestoneTypeEntity milestoneType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MILESTONE_STATE_ID")
    private AtpStateEntity atpState;
    
    @Column(name="IS_ALL_DAY")
    private boolean isAllDay;
    
    @Column(name="IS_DATE_RANGE")
    private boolean isDateRange;
    
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<AtpAttributeEntity> attributes;

    public MilestoneEntity() {
    }

    public MilestoneEntity(Milestone milestone) {
        this.name = milestone.getName();
        this.descr = new AtpRichTextEntity(milestone.getDescr());
        this.startDate = milestone.getStartDate();
        this.endDate = milestone.getEndDate();
        
        // TODO Type and State
        
        this.setAttributes(new ArrayList<AtpAttributeEntity>());
        if (null != milestone.getAttributes()) {
            for (Attribute att : milestone.getAttributes()) {
                this.getAttributes().add(new AtpAttributeEntity(att));
            }
        }
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AtpRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(AtpRichTextEntity descr) {
        this.descr = descr;
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

    public MilestoneTypeEntity getMilestoneType() {
        return milestoneType;
    }

    public void setMilestoneType(MilestoneTypeEntity atpType) {
        this.milestoneType = atpType;
    }

    public AtpStateEntity getAtpState() {
        return atpState;
    }

    public void setAtpState(AtpStateEntity atpState) {
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

    @Override
    public void setAttributes(List<AtpAttributeEntity> attributes) {
       this.attributes = attributes;
        
    }

    @Override
    public List<AtpAttributeEntity> getAttributes() {
        return attributes;
    }
    
    public MilestoneInfo toDto() {
        MilestoneInfo info = MilestoneInfo.newInstance();
        
        // TODO uncomment after builder pattern removed from dto superclasses
        info.setName(getName());
        info.setTypeKey(getMilestoneType().getId());
        info.setStateKey(getAtpState().getId());
        info.setStartDate(getStartDate());
        info.setEndDate(getEndDate());
        info.setAllDay(isAllDay());
        info.setDateRange(isDateRange());
        
        List<Attribute> atts = new ArrayList<Attribute>();
        for (AtpAttributeEntity att : getAttributes()) {
            Attribute attInfo = att.toDto();
            atts.add(attInfo);
        }
        // TODO, same reason as above info.setAttributes(atts);
        
        return info;
    }

}
