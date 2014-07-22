package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.framework.context.PlanHelper;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.infc.Course;

import java.util.List;

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

    /**
     * Gets the id of the term that the planner should display first.
     *
     * @return Term Id
     */
    @Override
    public String getPlannerFirstTermId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the list of Terms to use in the Planner Calendar using a Start Term.
     *
     * @param startTerm - Term that the calendar starts around
     * @return A full List of terms to display in the calendar.
     */
    @Override
    public List<Term> getPlannerCalendarTerms(Term startTerm) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve the list of plan items for this course in the student's plan
     *
     * @param course - Course that is being displayed
     * @return A List of plan items related to the course.
     */
    @Override
    public List<PlanItem> loadStudentsPlanItemsForCourse(Course course){
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a message regarding the Planned status of the course to be displayed on the page
     *
     * @param planItems - The list of plan items related to the course
     * @return - Formatted message if bookmarked, "" if not planned
     */
    @Override
    public String createPlanningStatusMessages(List<PlanItem> planItems) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a message regarding the Bookmark status of the course to be displayed on the page
     *
     * @param planItems - The list of plan items related to the course
     * @return - Formatted message if bookmarked, "" if not bookmarked
     */
    @Override
    public String createBookmarkStatusMessages(List<PlanItem> planItems) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
