package org.kuali.student.enrollment.academicrecord.infc.attic;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.Date;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface Honors extends IdEntity {
    /**
     * Date awarded
     * 
     * @name Date Awarded
     * @readOnly
     * @required
     */
    public Date getDateAwarded();

    /**
     * Name of the term
     * 
     * @name Term Name
     * @readOnly
     */
    public String getTermName();

    /**
     * Key for the term
     * 
     * @name Term Key
     * @readOnly
     */
    public String getTermKey();

    /**
     * Start date of the term
     * 
     * @name Term Start Date
     * @readOnly
     * @required
     */
    public Date getTermStartDate();

    /**
     * End date of the term
     * 
     * @name Term End Date
     * @readOnly
     * @required
     */
    public Date getTermEndDate();
}
