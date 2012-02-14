package org.kuali.student.enrollment.class1.lrc.model;

import org.kuali.student.r2.core.class1.type.entity.TypeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LRC_TYPE")
public class LrcTypeEntity extends TypeEntity<ResultScaleAttributeEntity> {

}