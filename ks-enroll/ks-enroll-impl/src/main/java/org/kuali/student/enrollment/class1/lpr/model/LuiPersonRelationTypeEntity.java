package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.TypeEntity;

/**
 * @author Igor
 */
@Entity
@Table(name = "KSLP_LPR_TYPE")
public class LuiPersonRelationTypeEntity extends TypeEntity<LuiPersonRelationAttributeEntity> {
}
