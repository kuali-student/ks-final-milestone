package org.kuali.student.ap.academicplan.infc;

import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.TypeStateEntity;

/**
 * Student's Learning Plan that contains a list of plan items
 *
 * @Author Kamal
 */
public interface LearningPlan extends IdEntity {

    /**
     * Plan's associated student id
     * @name Student Id
     */
    public String getStudentId();

    //TODO:   KSAP-1014 - Add 'name' attribute to LearningPlan and PlanItem

    /**
     * A description of the Learning Plan
     * @name Description
     */
    public RichText getDescr();

    /**
     *
     * @name Shared
     */
    public Boolean isShared();
}
