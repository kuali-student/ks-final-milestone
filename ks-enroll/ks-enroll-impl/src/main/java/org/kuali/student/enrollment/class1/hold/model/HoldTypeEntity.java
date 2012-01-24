package org.kuali.student.enrollment.class1.hold.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.core.class1.type.entity.TypeEntity;

@Entity
@Table(name = "KSEN_HOLD_TYPE")
public class HoldTypeEntity extends TypeEntity<HoldTypeAttributeEntity>{

}
