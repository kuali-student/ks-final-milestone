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
     * The position of this entry in the LPR Roster.
     * 
     * @name Position
     */
    public Integer getPosition();
}
