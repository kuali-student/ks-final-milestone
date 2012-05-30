package org.kuali.student.r2.core.class1.scheduling.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntityNew;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * Manages scheduling request dynamic attributes
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@Entity
@Table(name = "KSEN_SCHED_RQST_ATTR")
public class ScheduleRequestAttributeEntity extends BaseAttributeEntityNew<ScheduleRequestEntity> {
    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private ScheduleRequestEntity owner;

    public ScheduleRequestAttributeEntity() {
    }

    public ScheduleRequestAttributeEntity(String key, String value) {
        super(key, value);
    }

    public ScheduleRequestAttributeEntity(Attribute att, ScheduleRequestEntity owner) {
        super(att);
        setOwner(owner);
    }

    @Override
    public void setOwner(ScheduleRequestEntity owner) {
        this.owner = owner;

    }

    @Override
    public ScheduleRequestEntity getOwner() {
        return owner;
    }
}