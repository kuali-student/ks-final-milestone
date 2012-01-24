package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.core.class1.type.entity.TypeEntity;

@Entity
@Table(name = "KSEN_LUI_TYPE")
public class LuiTypeEntity extends TypeEntity<LuiTypeAttributeEntity>{

}