package org.kuali.student.ap.academicplan.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSPL_LRNG_PLAN_ATTR")
public class LearningPlanAttributeEntity extends BaseAttributeEntity<LearningPlanEntity> implements AttributeEntity {

    public LearningPlanAttributeEntity() {
        super();
    }

    public LearningPlanAttributeEntity(Attribute att, LearningPlanEntity owner) {
        super(att, owner);
    }

}
