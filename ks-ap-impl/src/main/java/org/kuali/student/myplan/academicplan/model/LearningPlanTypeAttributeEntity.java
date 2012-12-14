package org.kuali.student.myplan.academicplan.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KSPL_LRNG_PLAN_TYPE_ATTR")
public class LearningPlanTypeAttributeEntity extends BaseAttributeEntity<LearningPlanTypeEntity> {

    public LearningPlanTypeAttributeEntity() {}

    public LearningPlanTypeAttributeEntity(Attribute att, LearningPlanTypeEntity owner) {
        super(att, owner);
    }
}
