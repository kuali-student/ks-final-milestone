package org.kuali.student.r2.core.process.model;

import org.kuali.student.r2.common.entity.TypeEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_ATP_TYPE")
public class InstructionTypeEntity extends TypeEntity<InstructionTypeAttributeEntity> {

}
