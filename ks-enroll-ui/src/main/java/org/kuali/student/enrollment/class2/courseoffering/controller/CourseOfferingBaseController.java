package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.uif.form.KSUifMaintenanceDocumentForm;
import org.kuali.student.enrollment.uif.util.KSUifUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/courseOffering")
public class CourseOfferingBaseController extends MaintenanceDocumentController {
    /**
     * Initial method called when requesting a new view instance.
     *
     */
    @Override
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) form;
        setupMaintenance(maintenanceForm, request, KRADConstants.MAINTENANCE_NEW_ACTION);
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(form, methodToCall);

        }
        return getUIFModelAndView(maintenanceForm);
    }

    @Override
    public ModelAndView maintenanceEdit(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        setupMaintenance(form, request, KRADConstants.MAINTENANCE_EDIT_ACTION);

        // check view authorization
        // TODO: this needs to be invoked for each request
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(form, methodToCall);
//            form.setEditAuthz(checkEditViewAuthz(form));
        }

        //populate the previousFormsMap of the form. The map contains info about the previous view to generate customized breadcrumb
//        KSUifUtils.populationPreviousFormsMap(request, (KSUifMaintenanceDocumentForm) form);

        return getUIFModelAndView(form);
    }

}
