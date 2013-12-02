package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.framework.context.PlanHelper;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlanHelperMockTest implements PlanHelper{
    /**
     * Retrieves the default learning plan.
     *
     * @return Default Learning Plan
     */
    @Override
    public LearningPlanInfo getDefaultLearningPlan() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
