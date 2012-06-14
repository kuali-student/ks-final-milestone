package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/courseOffering")
public class CourseOfferingController extends MaintenanceDocumentController {

    private CourseService courseService;

    @RequestMapping(params = "methodToCall=loadCourseCatalog")
    public ModelAndView loadCourseCatalog(@ModelAttribute("KualiForm") MaintenanceForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingCreateWrapper coWrapper = ((CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject());
        String courseCode = coWrapper.getCatalogCourseCode();

        CourseInfo course = getCourseService().getCourse("db3e3c39-1ad8-48a4-a9ba-4004c8d86a4a");
        coWrapper.setCourse(course);
        coWrapper.setCreditCount(course.getCreditOptions().get(0).getResultValues().get(0));
        coWrapper.setShowAllSections(true);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=createFromCatalog")
    public ModelAndView createFromCatalog(@ModelAttribute("KualiForm") MaintenanceForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        ((CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject()).setShowCatalogLink(false);
        ((CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject()).setShowTermOfferingLink(true);


        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=createFromTermOffering")
    public ModelAndView createFromTermOffering(@ModelAttribute("KualiForm") MaintenanceForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        ((CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject()).setShowCatalogLink(true);
        ((CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject()).setShowTermOfferingLink(false);


        return getUIFModelAndView(form);
    }

    private CourseService getCourseService() {
        if(courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }


}
