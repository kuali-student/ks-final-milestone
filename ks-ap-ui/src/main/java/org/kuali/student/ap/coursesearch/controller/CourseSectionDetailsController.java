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
import org.kuali.student.ap.academicplan.dto.TypedObjectReferenceInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.academicplan.infc.TypedObjectReference;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsDialogForm;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsForm;
import org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.ap.planner.util.PlanEventUtils;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller handling the interactions of the course section portion of the Course Details Page.
 */
@Controller
@RequestMapping(value = "/course/details/**")
public class CourseSectionDetailsController extends KsapControllerBase {
    private static final Logger LOG = LoggerFactory.getLogger(CourseSectionDetailsController.class);

    private static final String COURSE_SECTION_DETAILS_FORM = "CourseDetails-FormView";
    private static final String COURSE_SECTION_DETAILS_DIALOG = "KSAP-CourseSectionDetailsDialog-FormView";
    private static final String COURSE_SECTION_DETAILS_ADD_DIALOG = "KSAP-CourseDetailsSection-AddCoursePage";
    private static final String COURSE_SECTION_DETAILS_REQUISITES_DIALOG = "KSAP-CourseDetailsSection-Requisites";

    private CourseDetailsViewHelperService viewHelper;

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
                                                  HttpServletResponse response)  {
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
                                                  HttpServletResponse response) throws IOException, ServletException {

        JsonObjectBuilder eventList = Json.createObjectBuilder();

        // Gather information about the registration group
        String regGroupId = request.getParameter("regGroupId");
        RegistrationGroupInfo regGroup = null;
        CourseOffering course = null;
        List<ActivityOfferingInfo> activities = null;
        try {
            regGroup = KsapFrameworkServiceLocator.getCourseOfferingService().getRegistrationGroup(regGroupId, KsapFrameworkServiceLocator.getContext().getContextInfo());
            course = KsapFrameworkServiceLocator.getCourseOfferingService().getCourseOffering(regGroup.getCourseOfferingId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
            activities = KsapFrameworkServiceLocator.getCourseOfferingService().getActivityOfferingsByIds(regGroup.getActivityOfferingIds(), KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        }
        boolean isVariableCreditCourse = getViewHelperService(form).isVariableCreditCourse(new CourseOfferingInfo(course));
        List<ActivityOfferingDetailsWrapper> activityWrappers = new ArrayList<ActivityOfferingDetailsWrapper>();
        Map<String, FormatOfferingInfo> formatOfferingInfoMap = new HashMap<String, FormatOfferingInfo>();
        for(ActivityOfferingInfo activityOfferingInfo : activities){
            ActivityOfferingDetailsWrapper activityOfferingDetailsWrapper = getViewHelperService(form).convertAOInfoToWrapper(activityOfferingInfo, isVariableCreditCourse, formatOfferingInfoMap);
            if (activityOfferingDetailsWrapper.getRegGroupCode() == null || "".equals(activityOfferingDetailsWrapper.getRegGroupCode())) {
                activityOfferingDetailsWrapper.setRegGroupCode(regGroup.getRegistrationCode());
                activityOfferingDetailsWrapper.setRegGroupId(regGroupId);
                activityOfferingDetailsWrapper.setPartOfRegGroup(true);
            }
            activityWrappers.add(activityOfferingDetailsWrapper);
        }

        // Order Planned Activities
        String formatOrder = request.getParameter("formatOrder");
        if(formatOrder!=null){
            String formatOrderArray[] = formatOrder.split("->");
            Map<String,Integer> formatOrderMap = new HashMap<String,Integer>();
            int count = 0;
            for(String temp : formatOrderArray){
                formatOrderMap.put(temp,count);
                count++;
            }
            for(ActivityOfferingDetailsWrapper temp : activityWrappers){
                temp.setFormatIndex(formatOrderMap.get(temp.getActivityFormatName()));
            }
            Collections.sort(activityWrappers, new Comparator<ActivityOfferingDetailsWrapper>() {
                @Override
                public int compare(ActivityOfferingDetailsWrapper a1, ActivityOfferingDetailsWrapper a2) {
                    if (a1.getFormatIndex() == a2.getFormatIndex()) return 0;
                    if (a1.getFormatIndex() > a2.getFormatIndex()) return 1;
                    return -1;
                }
            });
        }


        Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(regGroup.getTermId());
        LearningPlan learningPlan = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan();

        PlanItem coursePlanItem = KsapFrameworkServiceLocator.getPlanHelper().findCourseItem(course.getCourseId(),
                term.getId(), learningPlan.getId());

        // Create the new plan item
        TypedObjectReference planItemRef = new TypedObjectReferenceInfo(PlanConstants.REG_GROUP_TYPE,regGroup.getId());


        // Set the credits if it came through the request
        BigDecimal creditValue = null;
        if (isVariableCreditCourse) {
            String credits = request.getParameter("credits");
            creditValue = new BigDecimal(credits);
        }
        List<String> terms = new ArrayList<String>();
        terms.add(regGroup.getTermId());

        // Create attribute list
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        AttributeInfo rg2CourseLink = new AttributeInfo(AcademicPlanServiceConstants.PLAN_ITEM_RELATION_TYPE_RG2COURSE,coursePlanItem.getId());
        attributes.add(rg2CourseLink);

        // Save the new plan item to the database
        try{
            KsapFrameworkServiceLocator.getPlanHelper().addPlanItem(learningPlan.getId(),
                    coursePlanItem.getCategory(), "", creditValue, terms, planItemRef, attributes);
        }catch (AlreadyExistsException e){
            PlanEventUtils.sendJsonEvents(false,"Course " +course.getCourseCode() + " is already planned for " + term.getName(), response, eventList);
            return null;
        }

        // Get new list of valid registration groups with added plan item
        List<String> validRegGroups = getViewHelperService(form).getValidRegGroupIds(course.getId(), new HashMap<Object, Object>());
        List<String> validRegGroupsToRemain = getViewHelperService(form).getValidRegGroupIdsToRemain(course.getId(), new HashMap<Object, Object>());

        // Create events needed to update the page
        eventList = getViewHelperService(form).createAddSectionEvent(course.getTermId(), course.getCourseOfferingCode(),regGroup.getCourseOfferingId(),regGroup.getFormatOfferingId(), activityWrappers, eventList);
        eventList = getViewHelperService(form).createFilterValidRegGroupsEvent(course.getTermId(), course.getCourseOfferingCode(), regGroup.getFormatOfferingId(), validRegGroups, eventList,
                new HashMap<Object, Object>());
        eventList = getViewHelperService(form).createFilterValidRegGroupsForRemovalEvent(course.getTermId(), course.getCourseOfferingCode(), regGroup.getFormatOfferingId(), validRegGroupsToRemain, eventList);
        List<PlanItem> planItems = KsapFrameworkServiceLocator.getPlanHelper().loadStudentsPlanItemsForCourse(coursePlanItem.getRefObjectId());
        eventList = PlanEventUtils.makeUpdatePlanItemStatusMessage(planItems, eventList);
        PlanEventUtils.sendJsonEvents(true,"Registration Group For " +course.getCourseOfferingCode() + " added for " + term.getName(), response, eventList);
        return null;
    }

    /**
     * Handles the filtering of activities when one is selected on the page
     * Requires the activity id of the one selected
     * Requires the list of activity ids of all activities that are checked.
     * Returns null for the method but writes json objects for the page to use in dynamic updating
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=filterAOs")
    public ModelAndView filterAOs(@ModelAttribute("KualiForm") CourseSectionDetailsForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException, ServletException {
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        List<String> selectedActivities = new ArrayList<String>();
        Map<Object, Object> additionalRestrictions = new HashMap<Object, Object>();

        // Retrieve data from page
        String selectedActivityId = request.getParameter("selectedActivityId");
        String checkedActivitiesStr = request.getParameter("checkedActivities");
        if(!checkedActivitiesStr.isEmpty()){
            String checkedActivities[] = checkedActivitiesStr.split(",");
            for(String str : checkedActivities){
                selectedActivities.add(str);
            }
        }

        // Add selected activity list to restrictions map
        additionalRestrictions.put("selectedActivities", selectedActivities);

        // Retrieve activity offering for the one being interacted with.
        ActivityOfferingInfo activityOfferingInfo = null;
        try {
            activityOfferingInfo = KsapFrameworkServiceLocator.getCourseOfferingService()
                    .getActivityOffering(selectedActivityId,KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        }

        // Retrieve filtered registration groups
        List<String> regGroups = getViewHelperService(form)
                .getValidRegGroupIds(activityOfferingInfo.getCourseOfferingId(), additionalRestrictions);

        //Create events needed to update the page
        eventList = getViewHelperService(form).createFilterValidRegGroupsEvent(activityOfferingInfo.getTermId(),
                activityOfferingInfo.getCourseOfferingCode(),activityOfferingInfo.getFormatOfferingId(), regGroups, eventList,
                additionalRestrictions);
        PlanEventUtils.sendJsonEvents(true,"Filtered Activities for those only those in groups with " +
                activityOfferingInfo.getCourseOfferingCode() + " - " + activityOfferingInfo.getActivityCode(),
                response, eventList);

        return null;
    }

    /**
     * Handles the creation of a dialog form for adding a Registration Group to the Planner
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=startAddDialog")
    public ModelAndView startAddDialog(@ModelAttribute("KualiForm") UifFormBase form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)  {
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
        String formatOrder = request.getParameter("formatOrder");

        boolean variableCredit = Boolean.parseBoolean(request.getParameter("variableCredit"));

        dialogForm.setRegGroupId(regGroupId);
        dialogForm.setFormatOrder(formatOrder);

        RegistrationGroupInfo regGroup = null;
        CourseOffering course = null;
        TermInfo term = null;

        try {
            regGroup = KsapFrameworkServiceLocator.getCourseOfferingService().getRegistrationGroup(regGroupId, KsapFrameworkServiceLocator.getContext().getContextInfo());
            course = KsapFrameworkServiceLocator.getCourseOfferingService().getCourseOffering(regGroup.getCourseOfferingId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
            term = KsapFrameworkServiceLocator.getAcademicCalendarService().getTerm(course.getTermId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        }

        dialogForm.setRegGroupCode(regGroup.getRegistrationCode());
        dialogForm.setCourseOfferingCode(course.getCourseOfferingCode());
        dialogForm.setVariableCredit(variableCredit);
        dialogForm.setCourseOfferingTitle(course.getCourseOfferingTitle());
        dialogForm.setTermId(course.getTermId());
        dialogForm.setTermName(term.getName());
        dialogForm.setCourseOffering(course);
        dialogForm.setCreditsDisplay(course.getCreditCnt());



        return getUIFModelAndView(dialogForm);
    }

    /**
     * Handles the creation of a dialog form for adding a Registration Group to the Planner
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=startRequisitesDialog")
    public ModelAndView startRequisitesDialog(@ModelAttribute("KualiForm") UifFormBase form,
                                       HttpServletRequest request,
                                       HttpServletResponse response)  {
        // Dialog uses separate form from normal
        CourseSectionDetailsDialogForm dialogForm = new CourseSectionDetailsDialogForm();
        super.start(dialogForm, request, response);

        // Fill in basic view information to new form
        dialogForm.setViewId(COURSE_SECTION_DETAILS_DIALOG);
        dialogForm.setPageId(COURSE_SECTION_DETAILS_REQUISITES_DIALOG);
        dialogForm.setView(super.getViewService().getViewById(COURSE_SECTION_DETAILS_DIALOG));

        // Copy information from original view
        dialogForm.setFormPostUrl(form.getFormPostUrl());
        dialogForm.setRequestUrl(form.getRequestUrl());

        dialogForm = getViewHelperService(dialogForm)
                .setupActivityRequisitesDialog(request.getParameter("activityOfferingId"),dialogForm);

        return getUIFModelAndView(dialogForm);
    }

    /**
     * Retrieve the view helper from a form.
     *
     * @param form - Form helper is being retrieved for
     * @return Form's view helper
     */
    private CourseDetailsViewHelperService getViewHelperService(UifFormBase form) {
        if(viewHelper==null){
            if(form.getViewHelperService()==null){
                form.setViewId(COURSE_SECTION_DETAILS_FORM);
                form.setView(super.getViewService().getViewById(COURSE_SECTION_DETAILS_FORM));
            }
            viewHelper = (CourseDetailsViewHelperService) form.getViewHelperService();
        }
        return viewHelper;
    }
}
