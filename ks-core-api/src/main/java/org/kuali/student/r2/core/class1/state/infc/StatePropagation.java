package org.kuali.student.r2.core.class1.state.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface StatePropagation extends IdNamelessEntity {

    /**
     * Target StateChange associated with this Propagation
     *
     * @name Target State Change Id
     * @required
     */
    public String getTargetStateChangeId();

    /**
     * This Propagation could be blocked by these constraints, if any
     *
     * @name State Constraint Ids
     */
    List<String> getStateConstraintIds();
}
