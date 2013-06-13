package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.ActivityOfferingMaintainable;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.common.uif.form.KSUifMaintenanceDocumentForm;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping(value = "/activityOffering")
public class ActivityOfferingController extends MaintenanceDocumentController {

    @Override
    protected MaintenanceDocumentForm createInitialForm(HttpServletRequest request) {
        return new KSUifMaintenanceDocumentForm();
    }
    
    /**
     * Setups a new <code>MaintenanceDocumentView</code> with the edit maintenance
     * action
     */
    @RequestMapping(params = "methodToCall=" + KRADConstants.Maintenance.METHOD_TO_CALL_EDIT)
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
        KSUifUtils.populationPreviousFormsMap(request, (KSUifMaintenanceDocumentForm)form);

        return getUIFModelAndView(form);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=editScheduleComponent")
    public ModelAndView editScheduleComponent(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        ScheduleWrapper scheduleWrapper = (ScheduleWrapper)getSelectedObject(form);

        activityOfferingWrapper.setNewScheduleRequest(scheduleWrapper);
        activityOfferingWrapper.getRequestedScheduleComponents().remove(scheduleWrapper);
        activityOfferingWrapper.getEditRenderHelper().setScheduleEditInProgress(true);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=addScheduleComponent")
    public ModelAndView addScheduleComponent(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        ScheduleWrapper scheduleWrapper = activityOfferingWrapper.getNewScheduleRequest();
        if (validateTime(scheduleWrapper.getStartTime(), scheduleWrapper.getStartTimeAMPM(), scheduleWrapper.getEndTime(), scheduleWrapper.getEndTimeAMPM())) {
            GlobalVariables.getMessageMap().putError("requestedDeliveryLogistic", ActivityOfferingConstants.MSG_ERROR_INVALID_START_TIME);
            return getUIFModelAndView(form);
        }

        ActivityOfferingMaintainable viewHelper = (ActivityOfferingMaintainable) KSControllerHelper.getViewHelperService(form);
        boolean success = viewHelper.addScheduleRequestComponent(activityOfferingWrapper);

        if (success){
            activityOfferingWrapper.getEditRenderHelper().setScheduleEditInProgress(false);
            activityOfferingWrapper.setSchedulesModified(true);
            scheduleWrapper.setModified(true);
            if (activityOfferingWrapper.isColocatedAO() && !activityOfferingWrapper.getColocatedActivities().isEmpty()){
                GlobalVariables.getMessageMap().putWarning("ActivityOffering-DeliveryLogistic-Requested", RiceKeyConstants.ERROR_CUSTOM,activityOfferingWrapper.getEditRenderHelper().getColocatedActivitiesAsString());
            }
        }
        
        return getUIFModelAndView(form);
    }


    @Override
    @RequestMapping(params = "methodToCall=route")
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {

        super.route(form,result,request, response);

        // If AO has errors, display them on the same page instead of doing a redirect
        if (GlobalVariables.getMessageMap().hasErrors()) {
            return getUIFModelAndView(form);
        }

        String url;
        if (!form.getReturnLocation().contains("methodToCall=")){ //This happens when we display a list of COs and then user click on Manage action
            url = form.getReturnLocation() + "&methodToCall=show";
        } else {
            url = form.getReturnLocation().replaceFirst("methodToCall=[a-zA-Z0-9]+","methodToCall=show");
        }

        // clear current form from session
        GlobalVariables.getUifFormManager().removeSessionForm(form);

        // Doesn't work with performRedirect
        //KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, ActivityOfferingConstants.MSG_INFO_AO_MODIFIED);
        // Alas, this doesn't work either.  See KsUifFormBase.postBind(..) for how it works in a regular form
        Properties urlParameters = new Properties();
        urlParameters.put(CalendarConstants.GROWL_MESSAGE, ActivityOfferingConstants.MSG_INFO_AO_MODIFIED);
        return performRedirect(form, url, urlParameters);
    }


    @RequestMapping(params = "methodToCall=cancel")
    @Override
    public ModelAndView cancel(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        String url = form.getReturnLocation().replaceFirst("methodToCall="+ UifConstants.MethodToCallNames.START,"methodToCall=show");
        form.setReturnLocation(url);

        DocumentFormBase documentForm = (DocumentFormBase) form;
        performWorkflowAction(documentForm, UifConstants.WorkflowAction.CANCEL, false);

        return back(form,result,request,response);
    }

    private Object getSelectedObject(MaintenanceDocumentForm form){
        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set");
        }

        int selectedLineIndex = KSControllerHelper.getSelectedCollectionLineIndex(form);

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(form, selectedCollectionPath);
        return ((List<Object>) collection).get(selectedLineIndex);
    }
    
    public boolean validateTime (String startTime, String startAmPm, String endTime, String endAmPm) {
        //Set Date objects
        KSDateTimeFormatter timeFormatter = new KSDateTimeFormatter("hh:mm aa");
        DateTime startingTime = timeFormatter.getFormatter().parseDateTime(startTime + " " + startAmPm);
        DateTime endingTime = timeFormatter.getFormatter().parseDateTime(endTime + " " + endAmPm);
        //Compare and throw exception if start time is after end time
        if (DateTimeComparator.getInstance().compare(startingTime, endingTime) > 0 ) {
            return true;
        } else {
            return false;
        }
    }
}
