package org.kuali.student.r2.core.class1.process.model;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.core.class1.type.entity.TypeEntity;

@Entity
@Table(name = "KSEN_INSTR_TYPE")
public class InstructionTypeEntity extends TypeEntity<InstructionTypeAttributeEntity> {

}
