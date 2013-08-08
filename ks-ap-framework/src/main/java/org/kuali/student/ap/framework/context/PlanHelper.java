package org.kuali.student.ap.framework.context;

import org.kuali.student.myplan.academicplan.dto.LearningPlanInfo;

/**
 * Helper that handles configurable actions for accessing learning plans and Plan items.
 */
public interface PlanHelper {

    /**
     * Retrieves the default learning plan.
     *
     * @return Default Learning Plan
     */
    public LearningPlanInfo getDefaultLearningPlan();

}
