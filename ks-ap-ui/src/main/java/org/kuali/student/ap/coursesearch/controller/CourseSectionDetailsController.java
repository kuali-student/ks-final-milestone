package org.kuali.student.ap.coursesearch.controller;

import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.controller.extension.KsapControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}
