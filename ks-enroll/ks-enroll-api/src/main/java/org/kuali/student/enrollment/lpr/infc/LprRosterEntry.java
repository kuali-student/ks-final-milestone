package org.kuali.student.enrollment.lpr.infc;

import org.kuali.student.r2.common.infc.Relationship;

/**
 * @author Kuali Student Team (sambit)
 */
public interface LprRosterEntry extends Relationship {
    /**
     * The LPR Roster Id this LPR roster entry is a part of. 
     * 
     * @name Lui Person Relation Roster Id
     */
    public String getLprRosterId();

    /**
     * The LPR that this roster entry represents.
     * 
     * @name Lui Person Relation Id
     */
    public String getLprId();

    /**
     * The position of this entry within the LPR Roster.
     * 
     * If not supplied when the entry is created the service should set it to the next 
     * sequential number available.
     * 
     * The service does not have to guarantee uniqueness of this field within a roster.
     * 
     * This field cannot be relied upon to determine exactly how many people are 
     * ahead of a person in the roster because the sequence may contain
     * gaps (because of deletes) or duplicates.  Moreover a particular roster may 
     * order entries using different algorithms, for example it may be FIFO or LIFO or 
     * it may take other factors into account, such as the state, to determine which entry 
     * process next.
     * 
     * @name Position
     * @required
     */
    public Integer getPosition();
}
