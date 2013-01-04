package org.kuali.student.r2.core.scheduling.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Persistence entity class for dynamic attributes assigned to a Schedule
 *
 * @author andrewlubbers
 */
@Entity
@Table(name = "KSEN_SCHED_ATTR")
public class ScheduleAttributeEntity extends BaseAttributeEntity<ScheduleEntity> {

    public ScheduleAttributeEntity() {
        super();
    }

    public ScheduleAttributeEntity(Attribute att, ScheduleEntity owner) {
        super(att, owner);
    }

}
