package org.kuali.student.enrollment.academicrecord.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface Load extends IdNamelessEntity {

    /**
     * Total number of credits
     * 
     * @name Total Credits
     * @required
     */
    public String getTotalCredits();

    /**
     * Code indicating the load level, for example, Full Time, Half Time
     *
     * @name Load Level Type Key
     * @readOnly
     * @required
     */
    public String getLoadLevelTypeKey();
}
