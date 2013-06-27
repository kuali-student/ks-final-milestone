package org.kuali.student.r2.core.scheduling.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name = "KSEN_SCHED_RQST_SET_ATTR")
public class ScheduleRequestSetAttributeEntity extends BaseAttributeEntity<ScheduleRequestSetEntity> {

    public ScheduleRequestSetAttributeEntity() {
        super();
    }

    public ScheduleRequestSetAttributeEntity(Attribute att, ScheduleRequestSetEntity owner) {
        super(att, owner);
    }

}
