package org.kuali.student.enrollment.lpr.infc;

import java.util.List;

import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * LprRoster is a collection or group of LPR Roster entries which can be used to
 * model any collection of LPRs at the class I level, e.g. a garde sheet or a
 * waitlist for a course. The LPR roster entries in an LPR Roster will always
 * belong to of a particular course or to a section(s) within the course. The
 * associated LUIs are a way to show thsi relation.
 * 
 * @author Kuali Student Team (sambit)
 */
public interface LprRoster extends IdEntity {

    /**
     * This method gets the IDs of the associated LUIs for the LPR Roster.
     * {@link LprRoster}
     * 
     * @return
     * @name Associated Lui Ids
     */
    public List<String> getAssociatedLuiIds();

    /**
     * The maximum capacaty of the roster; contraints the maximum number of
     * roster entries in this roster.
     * 
     * @return
     */
    public Integer getMaximumCapacity();

    /**
     * Is check in required to maintain an active status in this roster.
     * 
     * @return
     */
    public Boolean getCheckInRequired();

    /**
     * The frequency in terms of time period that an entry has to check in to
     * maintain active status in this roster. This is only populated if check in
     * is required for the roster.
     * 
     * @return
     */
    public TimeAmountInfo getCheckInFrequency();

}
