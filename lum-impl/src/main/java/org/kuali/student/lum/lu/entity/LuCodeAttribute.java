package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KS_LU_CODE_ATTR_T")
public class LuCodeAttribute extends Attribute<LuCode> {

}
