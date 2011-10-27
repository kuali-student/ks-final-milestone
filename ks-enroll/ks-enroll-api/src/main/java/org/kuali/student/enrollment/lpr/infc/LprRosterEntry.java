package org.kuali.student.enrollment.lpr.infc;

import org.kuali.student.r2.common.infc.Relationship;

/**
 * @author Kuali Student Team (sambit)
 */
public interface LprRosterEntry extends Relationship {
    /**
     * The LPR Roster Id this LPR roster entry is a part of. 
     * 
     * @return
     */
    public String getLprRosterId();

    /**
     * The LPR that this roster entry represents.
     * 
     * @return
     */
    public String getLprId();

    /**
     * The position of this entry in the LPR Roster.
     * 
     * @return
     */
    public String getPosition();
}
