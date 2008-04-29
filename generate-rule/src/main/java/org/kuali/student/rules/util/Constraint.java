/**
 * 
 */
package org.kuali.student.rules.util;

public class Constraint {

    private ConstraintStrategy strategy;

    public Constraint(ConstraintStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Applies the constraint.
     * 
     * @return <code>true</code> if the constraint is met.
     */
    public Boolean apply(String propVar) {
        return strategy.apply(propVar);
    }

    /**
     * @return the constraintID
     */
    public String getConstraintID() {
        return strategy.constraintID;
    }

    /**
     * The instance the constraint is being applied to
     * 
     * @param request
     */
    public void setRequest(Object request) {
        strategy.setRequest(request);
    }
}
