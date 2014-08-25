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

package org.kuali.student.ap.planner.service.impl;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.coursesearch.util.CourseDetailsUtil;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.planner.dataobject.CourseSummaryPopoverDetailsWrapper;
import org.kuali.student.ap.planner.form.AddCourseToPlanForm;
import org.kuali.student.ap.planner.service.PlannerViewHelperService;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.infc.Course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public class PlannerViewHelperServiceImpl extends ViewHelperServiceImpl implements PlannerViewHelperService {

    /**
     * @see org.kuali.student.ap.planner.service.PlannerViewHelperService#loadAddToPlanDialogForm(org.kuali.rice.krad.web.form.UifFormBase, org.kuali.student.ap.planner.form.AddCourseToPlanForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public UifFormBase loadAddToPlanDialogForm(UifFormBase submittedForm, AddCourseToPlanForm dialogForm, HttpServletRequest request, HttpServletResponse response){
        String courseId = request.getParameter("courseId");

        Course course = KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourse(courseId);

        dialogForm.setCourseId(courseId);
        dialogForm.setCourseCode(course.getCode());
        dialogForm.setCourseTitle(course.getCourseTitle());
        dialogForm.setUniqueId(UUID.randomUUID().toString());

        // Set Credits to display for course
        String creditString = CreditsFormatter.formatCredits(course);
        dialogForm.setCreditsDisplay(creditString);

        // Set if course is variable credits
        boolean isVariableCredits = false;
        CreditsFormatter.Range range = CreditsFormatter.getRange(course);
        if(range.getMultiple()!=null && !range.getMultiple().isEmpty()) isVariableCredits = true;
        isVariableCredits = !range.getMax().equals(range.getMin());
        dialogForm.setVariableCredit(isVariableCredits);

        CourseSummaryPopoverDetailsWrapper courseDetails = new CourseSummaryPopoverDetailsWrapper();

        // courseDetails.setCourseRequisites(CourseDetailsUtil.getCourseRequisites(course));
        courseDetails.setCourseRequisitesMap(CourseDetailsUtil.getCourseRequisitesMap(course));

        // Load Term information
        courseDetails.setScheduledTerms(KsapFrameworkServiceLocator.getCourseHelper()
                .getScheduledTermsForCourse(course));
        courseDetails.setProjectedTerms(KsapFrameworkServiceLocator.getCourseHelper().getProjectedTermsForCourse(course));

        // Load Last Offered Term information if course is not scheduled
        if(courseDetails.getScheduledTerms().isEmpty()){
            Term lastOfferedTerm = KsapFrameworkServiceLocator.getCourseHelper().getLastOfferedTermForCourse(course);
            if (lastOfferedTerm != null){
                courseDetails.setLastOffered(lastOfferedTerm.getName());
            }
            else {
                // If no last offered is found set as null
                courseDetails.setLastOffered(null);
            }
        }else{
            courseDetails.setLastOffered(null);
        }

        dialogForm.setCourseSummaryDetails(courseDetails);

        //Find terms that already contain this planned course
        List<PlanItem> planItems = KsapFrameworkServiceLocator.getPlanHelper().loadStudentsPlanItemsForCourse(course);
        List<String> plannedTermIds = KsapFrameworkServiceLocator.getPlanHelper().getTermIdsForPlanItems(planItems);
        dialogForm.setPlannedTermIds(plannedTermIds);

        return dialogForm;
    }

}
