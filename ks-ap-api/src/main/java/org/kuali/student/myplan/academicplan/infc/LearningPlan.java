package org.kuali.student.myplan.academicplan.infc;

import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.TypeStateEntity;

/**
 * Student's Learning Plan that contains a list of plan items
 *
 * @Author Kamal
 */
public interface LearningPlan extends HasId, TypeStateEntity {

    /**
     * A description of the Learning Plan
     * @name Description
     */

    public RichText getDescr();

    /**
     * Plan's associated student id
     * @name Student Id
     */
    public String getStudentId();

    /**
     *
     * @name Shared
     */
    public Boolean getShared();
}
