package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.Placeholder;
import org.kuali.student.ap.academicplan.infc.PlaceholderInstance;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.academicplan.infc.TypedObjectReference;
import org.kuali.student.ap.framework.context.PlanHelper;
import org.kuali.student.ap.planner.PlannerItem;
import org.kuali.student.ap.planner.PlannerTerm;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.infc.Course;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
     * Update the default learning plan for the current session.
     *
     * @param learningPlanId learning plan ID
     */
    @Override
    public void setDefaultLearningPlan(String learningPlanId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a learning plan for the current student by ID.
     *
     * @param learningPlanId plan ID
     * @return Learning Plan
     */
    @Override
    public LearningPlanInfo getLearningPlan(String learningPlanId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the plan items in a learning plan.
     *
     * @param planId The learning plan ID.
     * @return Default Learning Plan
     */
    @Override
    public List<PlanItem> getPlanItems(String planId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Load a plan item from any of the student's learning plans by ID.
     *
     * @param planItemId plan item ID
     * @return a plan item from one of the student's learning plans by ID
     */
    @Override
    public PlanItem getPlanItem(String planItemId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Adds a plan item to a learning plan.
     *
     * @param learningPlanId learning plan ID
     * @param category       item category
     * @param descr          plan item description (course note)
     * @param units          number of credits/units
     * @param termIds        planned term IDs
     * @param ref            course/placeholder reference data
     * @return
     */
    @Override
    public PlanItem addPlanItem(String learningPlanId, AcademicPlanServiceConstants.ItemCategory category, String descr, BigDecimal units, List<String> termIds, TypedObjectReference ref,List<AttributeInfo> attributes) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates a plan item in a learning plan.
     * <p/>
     * <p>
     * In addition to calling {@link org.kuali.student.ap.academicplan.service.AcademicPlanService#updatePlanItem(String, org.kuali.student.ap.academicplan.dto.PlanItemInfo, org.kuali.student.r2.common.dto.ContextInfo)}, this
     * method should ensure that session state and cached data are updated accordingly. and/or cleared as needed to
     * reflect the update.
     * </p>
     *
     * @param item plan item
     */
    @Override
    public PlanItem updatePlanItem(PlanItem item) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Removes a plan item.
     * <p/>
     * <p>
     * In addition to calling {@link org.kuali.student.ap.academicplan.service.AcademicPlanService#deletePlanItem(String, org.kuali.student.r2.common.dto.ContextInfo)}, this method should
     * ensure that session state and cached data are updated accordingly. and/or cleared as needed to reflect the
     * update.
     * </p>
     *
     * @param planItemId plan item ID
     */
    @Override
    public void removePlanItem(String planItemId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates a term note for a learning plan.
     *
     * @param learningPlanId learning plan ID
     * @param termId         term ID
     * @param note           updated term note. May be null to remove the note.
     */
    @Override
    public void editTermNote(String learningPlanId, String termId, String note) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the completed course records (via AcademicRecordService) for the student by ID.
     *
     * @return completed course records
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedRecords() {
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
     * Determines if two typed object references refer to the same object.
     *
     * @param ref1 typed reference
     * @param ref2 typed reference
     * @return True if ref1 and ref2 both refer to the same typed object.
     */
    @Override
    public boolean isSame(TypedObjectReference ref1, TypedObjectReference ref2) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Determines if one typed object reference is encompassed by another.
     *
     * @param inner typed reference
     * @param outer typed reference
     * @return True if all courses referred to by inner are also referred to by
     *         outer.
     */
    @Override
    public boolean isEncompassed(TypedObjectReference inner, TypedObjectReference outer) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
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
     * @param courseId - Id of course that is being displayed
     * @return A List of plan items related to the course.
     */
    @Override
    public List<PlanItem> loadStudentsPlanItemsForCourse(String courseId) {
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
     * Get the course from a TypedOjbectReference
     *
     * @param ref
     * @return course or null if the reference is not for a placeholder.
     */
    @Override
    public Course getCourse(TypedObjectReference ref) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get the placeholder from a TypedOjbectReference
     *
     * @param ref
     * @return placeholder or null if the reference is not for a placeholder.
     */
    @Override
    public Placeholder getPlaceHolder(TypedObjectReference ref) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get the placeholder instance from a TypedOjbectReference
     *
     * @param ref
     * @return placeholder instance or null if the reference is not for a placeholder instance.
     */
    @Override
    public PlaceholderInstance getPlaceHolderInstance(TypedObjectReference ref) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get the course IDs for courses that satisfy a given PlaceHolder
     *
     * @param placeholder the Placeholder to resolve
     * @return a set of one or more course IDs
     */
    @Override
    public Set<String> getCourseIdsForPlaceHolder(Placeholder placeholder) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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

    @Override
    public List<String> getTermIdsForPlanItems(List<PlanItem> planItems) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the plan item associated with the course in a specific term
     *
     * @param courseId - Id of the course
     * @param termId   - Id of the term the plan item is in
     * @param planId   - Id for the plan to search in
     * @return Plan Item found for course in the term.
     */
    @Override
    public PlanItem findCourseItem(String courseId, String termId, String planId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Loads information from a course record into a planner item for display
     *
     * @param courseRecord - Item to load
     * @return Filled in planner item
     */
    @Override
    public PlannerItem createPlannerItem(StudentCourseRecordInfo courseRecord) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Loads information from a planItem into a planner item for display
     *
     * @param planItem - Item to load
     * @return Filled in planner item
     */
    @Override
    public PlannerItem createPlannerItem(PlanItem planItem) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<PlannerTerm> getPlannerCalendarTerms(LearningPlan learningPlan) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
