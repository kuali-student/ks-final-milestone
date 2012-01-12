package org.kuali.student.r2.core.class1.type.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.core.class1.type.entity.TypeEntity;

@Entity
@Table(name = "KSEN_ATP_TYPE")
public class AtpTypeEntity extends TypeEntity<AtpTypeAttributeEntity>{

}
