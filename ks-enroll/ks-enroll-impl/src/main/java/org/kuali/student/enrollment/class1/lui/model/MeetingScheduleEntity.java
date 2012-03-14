package org.kuali.student.enrollment.class1.lui.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r2.common.dto.MeetingScheduleInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.BaseEntity;
import org.kuali.student.r2.common.infc.MeetingSchedule;

@Entity
@Table(name = "KSEN_LUI_MTG_SCHE")
public class MeetingScheduleEntity extends BaseEntity implements AttributeOwner<MeetingScheduleAttributeEntity> {

    @Column(name = "SPACE_ID")
    private String spaceId;

    @Column(name = "SCHEDULE_ID")
    private String scheduleId;

    @ManyToOne
    @JoinColumn(name = "LUI_ID")
    private LuiEntity lui;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<MeetingScheduleAttributeEntity> attributes;

    public MeetingScheduleEntity() {}

    public MeetingScheduleEntity(MeetingSchedule meetingSchedule) {
        // super(meetingSchedule);
        // try {
        this.setId(meetingSchedule.getId());
        this.setSpaceId(meetingSchedule.getSpaceId());
        this.setScheduleId(meetingSchedule.getScheduleId());
        // this.setVersionNumber((long) 0);

        // Attributes
        // this.setAttributes(new ArrayList<MeetingScheduleAttributeEntity>());
        // if (null != meetingSchedule.getAttributes()) {
        // for (Attribute att : meetingSchedule.getAttributes()) {
        // MeetingScheduleAttributeEntity attEntity = new MeetingScheduleAttributeEntity(att);
        // this.getAttributes().add(attEntity);
        // }
        // }

        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

    public MeetingScheduleInfo toDto() {
        MeetingScheduleInfo obj = new MeetingScheduleInfo();
        obj.setId(getId());
        obj.setSpaceId(spaceId);
        obj.setScheduleId(scheduleId);

        // Attributes
        // List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        // for (MeetingScheduleAttributeEntity att : getAttributes()) {
        // AttributeInfo attInfo = att.toDto();
        // atts.add(attInfo);
        // }
        // obj.setAttributes(atts);

        return obj;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LuiEntity getLui() {
        return lui;
    }

    public void setLui(LuiEntity lui) {
        this.lui = lui;
    }

    @Override
    public void setAttributes(List<MeetingScheduleAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<MeetingScheduleAttributeEntity> getAttributes() {
        return attributes;
    }

}
