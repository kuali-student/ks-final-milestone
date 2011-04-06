package org.kuali.student.enrollment.lpr.model;

import java.io.Serializable;

import org.kuali.student.r2.common.entity.Type;

/**
 * @author Igor
 */
@Entity
@Table(name = "KSLP_LPR_TYPE")
public class LuiPersonRelationType extends Type<LuiPersonRelationTypeAttribute> implements Serializable {
}
