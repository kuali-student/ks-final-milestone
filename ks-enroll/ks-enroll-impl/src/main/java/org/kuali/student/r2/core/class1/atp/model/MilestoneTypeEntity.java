package org.kuali.student.r2.core.class1.atp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.TypeEntity;

@Entity
@Table(name = "KSEN_MSTONE_TYPE")
public class MilestoneTypeEntity extends TypeEntity<AtpAttributeEntity> {

}
