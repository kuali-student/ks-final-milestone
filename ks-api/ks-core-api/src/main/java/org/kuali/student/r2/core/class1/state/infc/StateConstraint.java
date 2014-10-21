package org.kuali.student.r2.core.class1.state.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface StateConstraint extends IdNamelessEntity {

    /**
     * Specifies how the constraints are applied, that is, existence of some, all or none
     *
     * @name State Constraint Operator
     * @required
     */
    public StateConstraintOperator getStateConstraintOperator();

    /**
     * Related objects' state key
     *
     * @name Related Object State Keys
     * @impl For example, if the StateConstraintOperator is 'none', then none of the related objects should exist in the given states
     */
    public List<String> getRelatedObjectStateKeys();

    /**
     * Business rule that specifies the constraint
     *
     * @name Agenda Id
     * @impl For example, "Can't cancel course with > 10 students"
     */
    public String getAgendaId();
}
