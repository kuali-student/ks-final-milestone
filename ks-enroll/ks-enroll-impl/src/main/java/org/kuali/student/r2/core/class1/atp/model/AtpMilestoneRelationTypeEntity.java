package org.kuali.student.r2.core.class1.atp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.TypeEntity;

@Entity
@Table(name = "KSEN_ATPMSTONE_RELTN_TYPE")
public class AtpMilestoneRelationTypeEntity extends TypeEntity<AtpMilestoneRelationAttributeEntity> {

}
