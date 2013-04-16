package org.kuali.student.r2.core.class1.state.infc;

import org.kuali.student.r2.common.infc.Relationship;

import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface StateChange extends Relationship
{
    /**
     * Initial or "from" state
     *
     * @name From State Key
     * @readOnly
     * @required
     */
    public String getFromStateKey();

    /**
     * Next or "to" state
     *
     * @name To State Key
     * @readOnly
     * @required
     */
    public String getToStateKey();

    /**
     * Constraints, if any, associated with this state change
     *
     * @name State Constraint Ids
     */
    public List<String> getStateConstraintIds();

    /**
     * Propagations, if any, triggered by this state change
     *
     * @name State Propagation Ids
     */
    public List<String> getStatePropagationIds();
}
