package org.kuali.student.r2.core.process.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.core.class1.type.entity.TypeEntity;
import org.kuali.student.r2.core.process.model.ProcessTypeAttributeEntity;

@Entity
@Table(name = "KSEN_PROCESS_TYPE")
public class ProcessTypeEntity extends TypeEntity<ProcessTypeAttributeEntity> {}