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

package org.kuali.student.ap.planner.service;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.planner.PlannerForm;
import org.kuali.student.ap.planner.form.AddCourseToPlanForm;
import org.kuali.student.ap.planner.form.CourseNoteForm;
import org.kuali.student.ap.planner.form.CourseSummaryForm;
import org.kuali.student.ap.planner.form.DeletePlanItemForm;
import org.kuali.student.ap.planner.form.PlanItemEditForm;
import org.kuali.student.ap.planner.form.QuickAddCourseToPlanForm;
import org.kuali.student.ap.planner.form.TermNoteForm;
import org.kuali.student.r2.lum.course.infc.Course;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PlannerViewHelperService extends PlanEventViewHelperService{

    /**
     * Creates and fills in the needed information for displaying a dialog to add a course to the plan.
     *
     * @param submittedForm - Original form submitted
     * @param dialogForm - Dialog form to build on
     * @param request - Server request object
     * @param response - Server response object
     * @return Filled in form for displaying the Add to Plan Dialog for Courses
     */
    public UifFormBase loadAddToPlanDialogForm(UifFormBase submittedForm, AddCourseToPlanForm dialogForm, HttpServletRequest request, HttpServletResponse response);

    /**
     * Creates and fills in the needed information for displaying a dialog to quick add a course to the plan.
     *
     * @param submittedForm - Original form submitted
     * @param dialogForm - Dialog form to build on
     * @param request - Server request object
     * @param response - Server response object
     * @return Filled in form for displaying the Quick Add to Plan Dialog for Courses
     */
    public UifFormBase loadQuickAddToPlanDialogForm(UifFormBase submittedForm, QuickAddCourseToPlanForm dialogForm, HttpServletRequest request, HttpServletResponse response);

    /**
     * Creates and fills in the needed information for displaying a dialog to handle a term note in the plan.
     *
     * @param submittedForm - Original form submitted
     * @param dialogForm - Dialog form to build on
     * @param request - Server request object
     * @param response - Server response object
     * @return Filled in form for displaying the Term Note Dialog for Courses
     */
    public UifFormBase loadTermNoteDialogForm(UifFormBase submittedForm, TermNoteForm dialogForm, HttpServletRequest request, HttpServletResponse response);

    /**
     * Creates and fills in the needed information for displaying a dialog to handle editing an item in the plan.
     *
     * @param submittedForm - Original form submitted
     * @param dialogForm - Dialog form to build on
     * @param request - Server request object
     * @param response - Server response object
     * @return Filled in form for displaying the Edit Plan Item Dialog for Courses
     */
    public UifFormBase loadPlanItemEditForm(UifFormBase submittedForm, PlanItemEditForm dialogForm, HttpServletRequest request, HttpServletResponse response);

    /**
     * Creates and fills in the needed information for displaying a dialog to handle a course note in the plan.
     *
     * @param submittedForm - Original form submitted
     * @param dialogForm - Dialog form to build on
     * @param request - Server request object
     * @param response - Server response object
     * @return Filled in form for displaying the Course Note Dialog for Courses
     */

    public UifFormBase loadCourseNotePlanForm(UifFormBase submittedForm, CourseNoteForm dialogForm,
            HttpServletRequest request, HttpServletResponse response);

    /**
     * Creates and fills in the needed information for displaying a dialog to display the course summary.
     *
     * @param submittedForm - Original form submitted
     * @param dialogForm - Dialog form to build on
     * @param request - Server request object
     * @param response - Server response object
     * @return Filled in form for displaying the Course Note Dialog for Courses
     */

    public UifFormBase loadCourseSummaryForm(UifFormBase submittedForm, CourseSummaryForm dialogForm,
                                              HttpServletRequest request, HttpServletResponse response);

    /**
     * Creates and fills in the needed information for displaying a dialog to delete a plan item.
     *
     * @param submittedForm - Original form submitted
     * @param dialogForm - Dialog form to build on
     * @param request - Server request object
     * @param response - Server response object
     * @return Filled in form for displaying the Course Note Dialog for Courses
     */

    public UifFormBase loadDeletePlanItemForm(UifFormBase submittedForm, DeletePlanItemForm dialogForm,
                                             HttpServletRequest request, HttpServletResponse response);

    /**
     * Helps with adding courses to the student's plan.
     * Creates a new or retrieves an existing learning plan item and fills in the proper information before
     * saving it to the database.
     *
     * @param plan - The student's learning plan
     * @param form - Form containing all information entered for the new plan item
     * @param course - Course plan item is being created for
     * @param termId - Id of the term course is being added to
     * @param response - Service response object
     * @throws java.io.IOException -
     * @throws javax.servlet.ServletException
     */
    public void finishAddCourse(LearningPlan plan, PlannerForm form, Course course, String termId,
                                 HttpServletResponse response) throws IOException, ServletException;
}
