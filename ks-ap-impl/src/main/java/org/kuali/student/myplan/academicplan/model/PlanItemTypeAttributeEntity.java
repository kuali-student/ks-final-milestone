package org.kuali.student.myplan.academicplan.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KSPL_LRNG_PLAN_ITEM_TYPE_ATTR")
public class PlanItemTypeAttributeEntity extends BaseAttributeEntity<PlanItemTypeEntity> {

    public PlanItemTypeAttributeEntity() {}

    public PlanItemTypeAttributeEntity(Attribute att, PlanItemTypeEntity owner) {
        super(att, owner);
    }
}
