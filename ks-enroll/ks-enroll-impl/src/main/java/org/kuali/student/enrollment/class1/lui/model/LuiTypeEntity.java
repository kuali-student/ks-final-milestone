package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.TypeEntity;
import org.kuali.student.r2.common.model.AttributeEntity;

@Entity
@Table(name = "KSEN_LUI_TYPE")
public class LuiTypeEntity extends TypeEntity<AttributeEntity>{

}
