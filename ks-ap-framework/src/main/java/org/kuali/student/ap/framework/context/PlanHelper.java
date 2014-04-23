package org.kuali.student.ap.framework.context;

import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.infc.Course;

import java.util.List;

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

    /**
     * Gets the id of the term that the planner should display first.
     *
     * @return Term Id
     */
    public String getPlannerFirstTermId();

    /**
     * Gets the list of Terms to use in the Planner Calendar using a Start Term.
     *
     * @param startTerm - Term that the calendar starts around
     * @return A full List of terms to display in the calendar.
     */
    public List<Term> getPlannerCalendarTerms(Term startTerm);


    /**
     * Retrieve the list of plan items for this course in the student's plan
     *
     * @param course - Course that is being displayed
     * @return A List of plan items related to the course.
     */
    public List<PlanItem> loadStudentsPlanItemsForCourse(Course course);


}
