/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.ap.framework.context;

import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants.ItemCategory;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.Placeholder;
import org.kuali.student.ap.academicplan.infc.PlaceholderInstance;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.academicplan.infc.TypedObjectReference;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.ap.planner.PlannerItem;
import org.kuali.student.ap.planner.PlannerTerm;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.infc.Course;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
     * Update the default learning plan for the current session.
     *
     * @param learningPlanId - Id for the learning plan
     */
    public void setDefaultLearningPlan(String learningPlanId);

    /**
     * Retrieves a learning plan for the current student by ID.
     *
     * @param learningPlanId - plan ID
     * @return Learning Plan
     */
    public LearningPlanInfo getLearningPlan(String learningPlanId);

    /**
     * Gets the plan items in a learning plan.
     *
     * @param learningPlanId - Id for the learning plan
     * @return A list of items for the learning plan
     */
    public List<PlanItem> getPlanItems(String learningPlanId);

    /**
     * Load a plan item from any of the student's learning plans by ID.
     *
     * @param planItemId plan item ID
     * @return a plan item from one of the student's learning plans by ID
     */
    public PlanItem getPlanItem(String planItemId);

    /**
     * Adds a plan item to a learning plan.
     *
     * @param learningPlanId - learning plan ID
     * @param category - item category
     * @param descr - plan item description (course note)
     * @param credits - number of credits/units
     * @param termIds - planned term IDs
     * @param ref - Reference for the plan item
     * @param attributes - attributes to be added to the plan item
     * @return - Copy of the new plan item created
     */
    public PlanItem addPlanItem(String learningPlanId, ItemCategory category, String descr, BigDecimal credits,
                         List<String> termIds, TypedObjectReference ref, List<AttributeInfo> attributes)
            throws AlreadyExistsException;

    /**
     * Updates a plan item in a learning plan.
     *
     * <p>
     * In addition to calling {@link AcademicPlanService#updatePlanItem(String, PlanItemInfo, ContextInfo)}, this
     * method should ensure that session state and cached data are updated accordingly. and/or cleared as needed to
     * reflect the update.
     * </p>
     *
     * @param item - New version of the plan item
     */
    public PlanItem updatePlanItem(PlanItem item);

    /**
     * Removes a plan item.
     *
     * <p>
     * In addition to calling {@link AcademicPlanService#deletePlanItem(String, ContextInfo)}, this method should
     * ensure that session state and cached data are updated accordingly. and/or cleared as needed to reflect the
     * update.
     * </p>
     *
     * @param planItemId - Id of the plan item to remove
     */
    public void removePlanItem(String planItemId);

    /**
     * Updates a term note for a learning plan.
     *
     * @param learningPlanId - learning plan ID
     * @param termId - term ID
     * @param note - updated term note. May be null to remove the note.
     */
    public void editTermNote(String learningPlanId, String termId, String note);

    /**
     * Gets the completed course records (via AcademicRecordService) for the student by ID.
     *
     * @return completed course records
     */
    public List<StudentCourseRecordInfo> getCompletedRecords();

    /**
     * Gets the id of the term that the planner should display first.
     *
     * @return Term Id
     */
    public String getPlannerFirstTermId();

    /**
     * Determines if two typed object references refer to the same object.
     *
     * @param ref1 typed reference
     * @param ref2 typed reference
     * @return True if ref1 and ref2 both refer to the same typed object.
     */
    public boolean isSame(TypedObjectReference ref1, TypedObjectReference ref2);

    /**
     * Determines if one typed object reference is encompassed by another.
     *
     * @param inner typed reference
     * @param outer typed reference
     * @return True if all courses referred to by inner are also referred to by outer.
     */
    public boolean isEncompassed(TypedObjectReference inner, TypedObjectReference outer);

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
     * @param courseVersionId - Id of course that is being displayed
     * @return A List of plan items related to the course.
     */
    List<PlanItem> loadStudentsPlanItemsForCourse(String courseVersionId);

    /**
     * Retrieve the list of plan items for this course in the student's plan
     *
     * @param course - Course that is being displayed
     * @return A List of plan items related to the course.
     */
    public List<PlanItem> loadStudentsPlanItemsForCourse(Course course);

    /**
     * Get the course from a TypedObjectReference
     *
     * @param ref - Reference for the plan item
     * @return course or null if the reference is not for a placeholder.
     */
    public Course getCourse(TypedObjectReference ref);

    /**
     * Get the placeholder from a TypedObjectReference
     *
     * @param ref - Reference for the plan item
     * @return placeholder or null if the reference is not for a placeholder.
     */
    public Placeholder getPlaceHolder(TypedObjectReference ref);

    /**
     * Get the placeholder instance from a TypedObjectReference
     *
     * @param ref - Reference for the plan item
     * @return placeholder instance or null if the reference is not for a placeholder instance.
     */

    public PlaceholderInstance getPlaceHolderInstance(TypedObjectReference ref);

    /**
     * Get the course IDs for courses that satisfy a given PlaceHolder
     *
     * @param placeholder the Placeholder to resolve
     * @return a set of one or more course IDs
     */
    public Set<String> getCourseIdsForPlaceHolder(Placeholder placeholder);

    /**
     * Creates a message regarding the Planned status of the course to be displayed on the page
     *
     * @param planItems - The list of plan items related to the course
     * @return - Formatted message if bookmarked, "" if not planned
     */
    public String createPlanningStatusMessages(List<PlanItem> planItems);

    /**
     * Creates a message regarding the Bookmark status of the course to be displayed on the page
     *
     * @param planItems - The list of plan items related to the course
     * @return - Formatted message if bookmarked, "" if not bookmarked
     */
    public String createBookmarkStatusMessages(List<PlanItem> planItems);

    /**
     * Get a list of terms IDs that contain the planned items
     * @param planItems
     * @return
     */
    List<String> getTermIdsForPlanItems(List<PlanItem> planItems);

    /**
     * Retrieves the plan item associated with the course in a specific term
     *
     * @param courseId - Id of the course
     * @param termId - Id of the term the plan item is in
     * @param planId - Id for the plan to search in
     * @return Plan Item found for course in the term.
     */
    public PlanItem findCourseItem(String courseId, String termId, String planId)
    ;

    /**
     * Loads information from a course record into a planner item for display
     *
     * @param courseRecord - Item to load
     * @return Filled in planner item
     */
    public PlannerItem createPlannerItem(StudentCourseRecordInfo courseRecord);

    /**
     * Loads information from a planItem into a planner item for display
     *
     * @param planItem - Item to load
     * @return Filled in planner item
     */
    public PlannerItem createPlannerItem(PlanItem planItem);

    /**
     * Returns an ordered list of planner terms to display in the planner calendar
     *
     * @param learningPlan - Learning plan being displayed
     * @return Order list of planner terms.
     */
    public List<PlannerTerm> getPlannerTerms(LearningPlan learningPlan);

    /**
     * @see PlanHelper#createPlannerItem(org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo)
     */
    PlannerItem createPlannerItem(CourseRegistrationInfo registrationRecord);
}
