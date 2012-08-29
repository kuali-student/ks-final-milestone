package org.kuali.student.enrollment.academicrecord.infc.attic;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface CreditsGrade extends IdNamelessEntity {
    /**
     * Number of credits
     * 
     * @name Credits
     * @required
     */
    public String getCredits();

    /**
     * Unique identifier of the grade
     * 
     * @name Grade Key
     * @required
     */
    public String getGradeKey();
}
