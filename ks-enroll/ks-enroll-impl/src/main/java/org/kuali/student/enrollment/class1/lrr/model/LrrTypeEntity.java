package org.kuali.student.enrollment.class1.lrr.model;

import org.kuali.student.r2.core.class1.type.entity.TypeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LRR_TYPE")
public class LrrTypeEntity extends TypeEntity<LrrAttributeEntity> {

}
