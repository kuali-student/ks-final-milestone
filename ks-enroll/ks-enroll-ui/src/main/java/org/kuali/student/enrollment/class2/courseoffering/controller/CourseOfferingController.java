package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
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

        String courseCode = ((CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject()).getCatalogCourseCode();

//        CourseInfo course = getCourseService().getCourse(courseCode);
        CourseInfo course = new CourseInfo();
        course.setCourseTitle("Test");
        course.setCode(courseCode);
        ((CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject()).setCourse(course);
        ((CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject()).setCreditCount("2");

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

}
