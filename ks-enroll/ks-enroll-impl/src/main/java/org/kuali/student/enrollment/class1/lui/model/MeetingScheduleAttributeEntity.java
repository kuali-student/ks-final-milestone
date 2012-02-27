package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LUI_MTG_SCHE_ATTR")
public class MeetingScheduleAttributeEntity extends BaseAttributeEntity<MeetingScheduleEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private MeetingScheduleEntity owner;

    public MeetingScheduleAttributeEntity () {
    }
    
    public MeetingScheduleAttributeEntity(String key, String value) {
        super(key, value);
    }

    public MeetingScheduleAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(MeetingScheduleEntity owner) {
        this.owner = owner;
    }

    @Override
    public MeetingScheduleEntity getOwner() {
        return owner;
    }
}
