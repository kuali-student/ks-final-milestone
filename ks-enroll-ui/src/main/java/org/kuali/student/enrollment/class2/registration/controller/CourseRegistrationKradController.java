package org.kuali.student.enrollment.class2.registration.controller;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.registration.form.CourseRegistrationKradForm;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.dto.CourseSearchResult;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.List;

/**
 * Created by swedev on 1/12/14.
 */
@Controller
@RequestMapping(value = "/courseRegistrationKrad")
public class CourseRegistrationKradController extends UifControllerBase {

    ScheduleOfClassesService scheduleOfClassesService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseRegistrationKradForm();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        CourseRegistrationKradForm perfForm = (CourseRegistrationKradForm) form;

        return getUIFModelAndView(perfForm);
    }

    @RequestMapping(params = "methodToCall=searchForCourses")
    public ModelAndView searchForCourses(@ModelAttribute("KualiForm") CourseRegistrationKradForm form, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        String courseCode = form.getCourseCode();
        String termCode = form.getTermCode();


        List<CourseSearchResult> collectionList;
        try {
            collectionList = getScheduleOfClassesService().loadCourseOfferingsByTermCodeAndCourse(termCode, courseCode);
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }

        form.setCourseOfferingSearchResults(collectionList); // add collection to form.


        return getUIFModelAndView(form);
    }

    private ScheduleOfClassesService getScheduleOfClassesService() {
        if (scheduleOfClassesService == null) {
            scheduleOfClassesService = (ScheduleOfClassesService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "scheduleOfClasses", ScheduleOfClassesService.class.getSimpleName()));
        }
        return scheduleOfClassesService;
    }
}
