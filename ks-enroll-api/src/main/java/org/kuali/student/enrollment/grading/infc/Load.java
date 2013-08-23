package org.kuali.student.enrollment.grading.infc;

import org.kuali.student.r2.common.infc.TypeStateEntity;

public interface Load extends TypeStateEntity {
    /**
     * 
     * This method ...
     * 
     * @return
     */
    public Float getTotalCredits();

    /**
     * Information on if its a full-time, part-time enrollment
     * 
     * @return
     */
    public String getTotalLevelCode();
}
