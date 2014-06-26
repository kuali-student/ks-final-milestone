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
import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
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
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 6/6/14
 * Time: 8:57 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/course/details/**")
public class CourseSectionDetailsController extends KsapControllerBase {

    private static final String COURSE_SECTION_DETAILS_FORM = "CourseDetails-FormView";
    private static final String COURSE_SECTION_DETAILS_DIALOG = "KSAP-CourseSectionDetailsDialog-FormView";
    private static final String COURSE_SECTION_DETAILS_ADD_DIALOG = "KSAP-CourseDetailsSection-AddCoursePage";

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new CourseSectionDetailsForm();
    }

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

    private CourseDetailsViewHelperService getViewHelperService(CourseSectionDetailsForm form) {
        CourseDetailsViewHelperService viewHelperService = (CourseDetailsViewHelperService) form.getViewHelperService();
        return viewHelperService;
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=addRegGroup")
    public ModelAndView addRegGroupFromSingleAO(@ModelAttribute("KualiForm") CourseSectionDetailsForm form,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) throws Exception {
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        String regGroupId = request.getParameter("regGroupId");
        RegistrationGroupInfo regGroup = KsapFrameworkServiceLocator.getCourseOfferingService().getRegistrationGroup(regGroupId, KsapFrameworkServiceLocator.getContext().getContextInfo());
        CourseOffering course = KsapFrameworkServiceLocator.getCourseOfferingService().getCourseOffering(regGroup.getCourseOfferingId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
        List<ActivityOfferingInfo> activities = KsapFrameworkServiceLocator.getCourseOfferingService().getActivityOfferingsByIds(regGroup.getActivityOfferingIds(), KsapFrameworkServiceLocator.getContext().getContextInfo());
        List<ActivityOfferingDetailsWrapper> activityWrappers = new ArrayList<ActivityOfferingDetailsWrapper>();
        for(ActivityOfferingInfo activityOfferingInfo : activities){
            activityWrappers.add(getViewHelperService(form).convertAOInfoToWrapper(activityOfferingInfo));
        }

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

        try{
            KsapFrameworkServiceLocator.getAcademicPlanService().createPlanItem(newPlanItem,KsapFrameworkServiceLocator.getContext().getContextInfo());
        }catch (AlreadyExistsException e){
            PlanEventUtils.sendJsonEvents(false,"Course " +course.getCourseCode() + " is already planned for " + term.getName(), response, eventList);
            return null;
        }
        for(ActivityOfferingDetailsWrapper activityOfferingDetailsWrapper : activityWrappers){
            eventList = createAddSectionEvent(activityOfferingDetailsWrapper, eventList);
        }

        PlanEventUtils.sendJsonEvents(true,"Registration Group For " +course.getCourseOfferingCode() + " added for " + term.getName(), response, eventList);
        return null;
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=startAddDialog")
    public ModelAndView startAddDialog(@ModelAttribute("KualiForm") UifFormBase form,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {

        CourseSectionDetailsDialogForm dialogForm = new CourseSectionDetailsDialogForm();
        super.start(dialogForm, request, response);
        dialogForm.setViewId(COURSE_SECTION_DETAILS_DIALOG);
        dialogForm.setPageId(COURSE_SECTION_DETAILS_ADD_DIALOG);
        dialogForm.setView(super.getViewService().getViewById(COURSE_SECTION_DETAILS_DIALOG));
        dialogForm.setFormPostUrl(form.getFormPostUrl());
        dialogForm.setRequestUrl(form.getRequestUrl());
        String regGroupId = request.getParameter("regGroupId");
        dialogForm.setRegGroupId(regGroupId);
        return getUIFModelAndView(dialogForm);
    }

    private JsonObjectBuilder createAddSectionEvent(ActivityOfferingDetailsWrapper activity, JsonObjectBuilder eventList){
        JsonObjectBuilder addEvent = Json.createObjectBuilder();
        addEvent.add("activityOfferingId", activity.getActivityOfferingId());

        eventList.add("COURSE_SECTION_ADDED", addEvent);
        return eventList;
    }
}
