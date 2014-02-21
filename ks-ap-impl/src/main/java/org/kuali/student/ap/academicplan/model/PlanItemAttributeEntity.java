package org.kuali.student.ap.academicplan.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KSPL_LRNG_PLAN_ITEM_ATTR")
public class PlanItemAttributeEntity extends BaseAttributeEntity<PlanItemEntity> implements AttributeEntity {

    public PlanItemAttributeEntity() {}

    public PlanItemAttributeEntity(Attribute att, PlanItemEntity owner) {
        super(att, owner);
    }

}
