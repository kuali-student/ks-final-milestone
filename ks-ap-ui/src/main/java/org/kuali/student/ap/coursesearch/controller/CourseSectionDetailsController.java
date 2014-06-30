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
package org.kuali.student.ap.coursesearch.controller;

import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.controller.extension.KsapControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsForm;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsDialogForm;
import org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.planner.util.PlanEventUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.core.acal.infc.Term;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller handling the interactions of the course section portion of the Course Details Page.
 */
@Controller
@RequestMapping(value = "/course/details/**")
public class CourseSectionDetailsController extends KsapControllerBase {

    private static final String COURSE_SECTION_DETAILS_FORM = "CourseDetails-FormView";
    private static final String COURSE_SECTION_DETAILS_DIALOG = "KSAP-CourseSectionDetailsDialog-FormView";
    private static final String COURSE_SECTION_DETAILS_ADD_DIALOG = "KSAP-CourseDetailsSection-AddCoursePage";

    /**
     * @see org.kuali.rice.krad.web.controller.UifControllerBase
     */
    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new CourseSectionDetailsForm();
    }

    /**
     * Handles the initial loading of the page content for the course section details
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=startCourseSectionDetails")
    public ModelAndView startCourseSectionDetails(@RequestParam(value = "courseId") String courseId,
                                                  @ModelAttribute("KualiForm") CourseSectionDetailsForm form,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) throws Exception {
        super.start(form, request, response);

        form.setViewId(COURSE_SECTION_DETAILS_FORM);
        form.setView(super.getViewService().getViewById(COURSE_SECTION_DETAILS_FORM));

        getViewHelperService(form).loadCourseSectionDetails(form, courseId);

        return getUIFModelAndView(form);
    }

    /**
     * Handles the addition of a registration group to the plan.
     * Requires the regGroupId to be set in the form
     * Returns null for the method but writes json objects for the page to use in dynamic updating
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=addRegGroup")
    public ModelAndView addRegGroup(@ModelAttribute("KualiForm") CourseSectionDetailsForm form,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) throws Exception {
        JsonObjectBuilder eventList = Json.createObjectBuilder();

        // Gather information about the registration group
        String regGroupId = request.getParameter("regGroupId");
        RegistrationGroupInfo regGroup = KsapFrameworkServiceLocator.getCourseOfferingService().getRegistrationGroup(regGroupId, KsapFrameworkServiceLocator.getContext().getContextInfo());
        CourseOffering course = KsapFrameworkServiceLocator.getCourseOfferingService().getCourseOffering(regGroup.getCourseOfferingId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
        List<ActivityOfferingInfo> activities = KsapFrameworkServiceLocator.getCourseOfferingService().getActivityOfferingsByIds(regGroup.getActivityOfferingIds(), KsapFrameworkServiceLocator.getContext().getContextInfo());
        List<ActivityOfferingDetailsWrapper> activityWrappers = new ArrayList<ActivityOfferingDetailsWrapper>();
        for(ActivityOfferingInfo activityOfferingInfo : activities){
            activityWrappers.add(getViewHelperService(form).convertAOInfoToWrapper(activityOfferingInfo));
        }

        // Create the new plan item
        Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(regGroup.getTermId());
        LearningPlan learningPlan = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan();
        PlanItemInfo newPlanItem = new PlanItemInfo();
        newPlanItem.setLearningPlanId(learningPlan.getId());
        newPlanItem.setCategory(AcademicPlanServiceConstants.ItemCategory.PLANNED);
        newPlanItem.setRefObjectId(regGroup.getId());
        newPlanItem.setRefObjectType(PlanConstants.REG_GROUP_TYPE);
        newPlanItem.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
        newPlanItem.setStateKey(PlanConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);
        List<String> terms = new ArrayList<String>();
        terms.add(regGroup.getTermId());
        newPlanItem.setPlanTermIds(terms);

        // Save the new plan item to the database
        try{
            KsapFrameworkServiceLocator.getAcademicPlanService().createPlanItem(newPlanItem,KsapFrameworkServiceLocator.getContext().getContextInfo());
        }catch (AlreadyExistsException e){
            PlanEventUtils.sendJsonEvents(false,"Course " +course.getCourseCode() + " is already planned for " + term.getName(), response, eventList);
            return null;
        }

        eventList = getViewHelperService(form).createAddSectionEvent(regGroup.getCourseOfferingId(), activityWrappers, eventList);

        //Create events needed to update the page
        PlanEventUtils.sendJsonEvents(true,"Registration Group For " +course.getCourseOfferingCode() + " added for " + term.getName(), response, eventList);
        return null;
    }

    /**
     * Handles the creation of a dialog form for adding a Registration Gorup to the Planner
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=startAddDialog")
    public ModelAndView startAddDialog(@ModelAttribute("KualiForm") UifFormBase form,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        // Dialog uses separate form from normal
        CourseSectionDetailsDialogForm dialogForm = new CourseSectionDetailsDialogForm();
        super.start(dialogForm, request, response);

        // Fill in basic view information to new form
        dialogForm.setViewId(COURSE_SECTION_DETAILS_DIALOG);
        dialogForm.setPageId(COURSE_SECTION_DETAILS_ADD_DIALOG);
        dialogForm.setView(super.getViewService().getViewById(COURSE_SECTION_DETAILS_DIALOG));

        // Copy information from original view
        dialogForm.setFormPostUrl(form.getFormPostUrl());
        dialogForm.setRequestUrl(form.getRequestUrl());

        // Fill in addition information needed by the add dialog
        String regGroupId = request.getParameter("regGroupId");
        dialogForm.setRegGroupId(regGroupId);

        return getUIFModelAndView(dialogForm);
    }

    /**
     * Retrieve the view helper from a form.
     *
     * @param form - Form helper is being retrieved for
     * @return Form's view helper
     */
    private CourseDetailsViewHelperService getViewHelperService(CourseSectionDetailsForm form) {
        CourseDetailsViewHelperService viewHelperService = (CourseDetailsViewHelperService) form.getViewHelperService();
        return viewHelperService;
    }
}
