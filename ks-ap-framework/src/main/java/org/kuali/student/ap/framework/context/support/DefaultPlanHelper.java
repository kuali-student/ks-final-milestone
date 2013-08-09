package org.kuali.student.ap.framework.context.support;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.PlanHelper;
import org.kuali.student.myplan.academicplan.dto.LearningPlanInfo;
import org.kuali.student.myplan.academicplan.service.AcademicPlanService;

import java.util.List;

/**
 * Default implementation of the PlanHelper
 */
public class DefaultPlanHelper implements PlanHelper {

    private transient AcademicPlanService academicPlanService;

    /**
     * Retrieves the first plan item of type PlanConstants.Learning_Plan_Type_Plan for the student as the default plan.
     * @see org.kuali.student.ap.framework.context.PlanHelper
     *
     * @return A single learning plan.
     */
    @Override
    public LearningPlanInfo getDefaultLearningPlan() {
        LearningPlanInfo defaultPlan = null;
        String studentId = getUserId();

        List<LearningPlanInfo> learningPlans = null;
        try {
            learningPlans = getAcademicPlanService()
                    .getLearningPlansForStudentByType(
                            studentId,
                            PlanConstants.LEARNING_PLAN_TYPE_PLAN,
                            KsapFrameworkServiceLocator.getContext()
                                    .getContextInfo());
        } catch (Exception e) {
            throw new RuntimeException(String.format(
                    "Could not fetch plan for user [%s].", studentId), e);
        }

        if (learningPlans == null) {
            throw new RuntimeException(
                    String.format(
                            "Could not fetch plan for user [%s]. The query returned null.",
                            studentId));
        }

        if (learningPlans.size() != 0) {
            defaultPlan = learningPlans.get(0);
        }

        return defaultPlan;
    }

    private String getUserId() {
        Person user = GlobalVariables.getUserSession().getPerson();
        return user.getPrincipalId();
    }

    public AcademicPlanService getAcademicPlanService() {
        if (academicPlanService == null) {
            academicPlanService = KsapFrameworkServiceLocator.getAcademicPlanService();
        }
        return academicPlanService;
    }

    public void setAcademicPlanService(AcademicPlanService academicPlanService) {
        this.academicPlanService = academicPlanService;
    }
}
