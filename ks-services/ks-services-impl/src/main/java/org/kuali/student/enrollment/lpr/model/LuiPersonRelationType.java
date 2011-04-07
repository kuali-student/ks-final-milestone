package org.kuali.student.enrollment.lpr.model;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.lum.lu.entity.LuTypeAttribute;
import org.kuali.student.lum.lu.entity.OneToMany;
import org.kuali.student.r2.common.entity.Type;

/**
 * @author Igor
 */
@Entity
@Table(name = "KSLP_LPR_TYPE")
public class LuiPersonRelationType extends Type<LuiPersionRelationAttribute> implements Serializable {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "LPR_ATTR_ID")
    private List<LuiPersionRelationAttribute> attributes;

}
