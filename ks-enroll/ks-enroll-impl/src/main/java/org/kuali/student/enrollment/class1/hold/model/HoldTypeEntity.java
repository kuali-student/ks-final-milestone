package org.kuali.student.enrollment.class1.hold.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.TypeEntity;
import org.kuali.student.r2.common.model.AttributeEntity;

@Entity
@Table(name = "KSEN_HOLD_TYPE")
public class HoldTypeEntity extends TypeEntity<AttributeEntity>{

}
