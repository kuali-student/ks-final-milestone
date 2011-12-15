package org.kuali.student.r2.common.infc;

/**
 * 
 * This is a description of what this class does - sambit don't forget to fill this in. 
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 *
 */
public interface Amount {

    /**
     * The kind of units associated with the quantity, such as hours/week. It is
     * expected that in usage in other structures, this value will always be
     * enumerated based on that context.
     */
    public String getUnitType();

    /**
     * The amount of units. Allowed values consist of numeric values as well as
     * the string "unbounded".
     */
    public String getUnitQuantity();
}
