package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/activityOffering")
public class ActivityOfferingController extends MaintenanceDocumentController{

    @RequestMapping(params = "methodToCall=reviseSchedule")
    public ModelAndView reviseSchedule(@ModelAttribute("KualiForm") MaintenanceForm form) throws Exception {

        return getUIFModelAndView(form);
    }
}
