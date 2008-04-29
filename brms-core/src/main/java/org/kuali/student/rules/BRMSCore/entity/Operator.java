package org.kuali.student.rules.BRMSCore.entity;

import javax.persistence.Embeddable;

/**
 * Contains meta data about the operator of a Rule Proposition. For example, in "completed any 2 of (MATH101, MATH102,
 * MATH103)", the Drool rule operator is '='.
 * 
 * @author Zdenek Zraly (zdenek.zraly@ubc.ca)
 */
@Embeddable
public class Operator {

    ComparisonOperatorType value;

    /**
     * Sets up an empty instance.
     */
    public Operator() {
        value = null;
    }

    /**
     * Sets up a Operator instance.
     * 
     * @param businessEntity
     * @param facts
     * @param className
     */
    public Operator(ComparisonOperatorType value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public final ComparisonOperatorType getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public final void setValue(ComparisonOperatorType value) {
        this.value = value;
    }
}
