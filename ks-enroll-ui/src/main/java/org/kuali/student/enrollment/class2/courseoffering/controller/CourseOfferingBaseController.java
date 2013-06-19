package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

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
            String crossListedAlias = request.getParameter("editCrossListedCoAlias");
            if(StringUtils.equals(crossListedAlias, "true")) {
                Object selectedObject =  form.getDocument().getNewMaintainableObject().getDataObject();
                if(selectedObject instanceof CourseOfferingEditWrapper) {
                    ((CourseOfferingEditWrapper) selectedObject).setEditCrossListedCoAlias(true);
                }
            }
//            form.setEditAuthz(checkEditViewAuthz(form));
        }

        //populate the previousFormsMap of the form. The map contains info about the previous view to generate customized breadcrumb
//        KSUifUtils.populationPreviousFormsMap(request, (KSUifMaintenanceDocumentForm) form);

        return getUIFModelAndView(form);
    }

    @Override
    @RequestMapping(params = "methodToCall=route")
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        super.route(form, result, request, response);

        // If Edit CO has errors, don't navigate back to Manage screen, display errors instead.
        if (GlobalVariables.getMessageMap().hasErrors()) {
            return getUIFModelAndView(form);
        }

        String url = form.getReturnLocation().replaceFirst("methodToCall="+ UifConstants.MethodToCallNames.START,"methodToCall=show");
        form.setReturnLocation(url);

        Properties urlParameters = new Properties();

        if (!(this instanceof CourseOfferingCreateController))   {
            CourseOfferingEditWrapper dataObject = (CourseOfferingEditWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();
            urlParameters.put(CalendarConstants.GROWL_MESSAGE, CourseOfferingConstants.COURSE_OFFERING_EDIT_SUCCESS);
            urlParameters.put(CalendarConstants.GROWL_MESSAGE_PARAMS,dataObject.getCourseOfferingCode());
        }

        // clear current form from session
        GlobalVariables.getUifFormManager().removeSessionForm(form);

        return performRedirect(form, form.getReturnLocation(), urlParameters);
    }


    @RequestMapping(params = "methodToCall=cancel")
    @Override
    public ModelAndView cancel(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        DocumentFormBase documentForm = (DocumentFormBase) form;
        performWorkflowAction(documentForm, UifConstants.WorkflowAction.CANCEL, false);

        return back(form,result,request,response);
    }

}
