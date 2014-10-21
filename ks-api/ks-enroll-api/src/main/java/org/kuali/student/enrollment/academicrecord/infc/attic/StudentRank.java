package org.kuali.student.enrollment.academicrecord.infc.attic;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface StudentRank extends IdNamelessEntity {
    /**
     * Rank 
     * 
     * @name Rank
     * @readOnly
     */
    public String getRank();

    /**
     * Population size
     * 
     * @name Population Size
     * @readOnly
     */
    public Integer  getPopulationSize();

    /**
     * Population defining the cohort that is being ranked
     * 
     * @name Population Key
     * @readOnly
     */
    public String getPopulationKey();
}
