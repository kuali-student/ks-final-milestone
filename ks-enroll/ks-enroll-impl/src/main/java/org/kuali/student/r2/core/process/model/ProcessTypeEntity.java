package org.kuali.student.r2.core.process.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.TypeEntity;

@Entity
@Table(name = "KSEN_PROCESS_TYPE")
public class ProcessTypeEntity extends TypeEntity<ProcessTypeAttributeEntity> {}