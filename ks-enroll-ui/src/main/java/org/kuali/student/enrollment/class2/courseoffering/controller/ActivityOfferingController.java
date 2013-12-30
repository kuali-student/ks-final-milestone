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
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.form.KSUifMaintenanceDocumentForm;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.ActivityOfferingMaintainable;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.common.util.EnrollConstants;
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

    @RequestMapping(params = "methodToCall=loadTSEndTimes")
    public ModelAndView loadTSEndTimes(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        String startTime = activityOfferingWrapper.getNewScheduleRequest().getStartTime();
        String days = activityOfferingWrapper.getNewScheduleRequest().getDays();

        if (!StringUtils.isBlank(startTime) && !StringUtils.isBlank(days)){
            ActivityOfferingMaintainable viewHelper = (ActivityOfferingMaintainable) KSControllerHelper.getViewHelperService(form);

            List<String> endTimes = viewHelper.getEndTimes(days,startTime,activityOfferingWrapper.getTimeSlotType());
            if (endTimes.size() == 1){
                activityOfferingWrapper.getNewScheduleRequest().setEndTime(KSCollectionUtils.getRequiredZeroElement(endTimes));
            } else {
                activityOfferingWrapper.getNewScheduleRequest().setEndTime("");
            }
            activityOfferingWrapper.getNewScheduleRequest().setEndTimes(endTimes);
        }

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=resetNewRDLTime")
    public ModelAndView resetNewRDLTime(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        activityOfferingWrapper.getNewScheduleRequest().setStartTime("");
        activityOfferingWrapper.getNewScheduleRequest().setEndTime("");
        activityOfferingWrapper.getNewScheduleRequest().getEndTimes().clear();

        return getUIFModelAndView(form);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=editScheduleComponent")
    public ModelAndView editScheduleComponent(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        ScheduleWrapper scheduleWrapper = (ScheduleWrapper)getSelectedObject(form);

        activityOfferingWrapper.setNewScheduleRequest(scheduleWrapper);
        activityOfferingWrapper.getRequestedScheduleComponents().remove(scheduleWrapper);
        activityOfferingWrapper.getEditRenderHelper().setScheduleEditInProgress(true);

        String startTime = activityOfferingWrapper.getNewScheduleRequest().getStartTime();
        String days = activityOfferingWrapper.getNewScheduleRequest().getDays();

        ActivityOfferingMaintainable viewHelper = (ActivityOfferingMaintainable) KSControllerHelper.getViewHelperService(form);
        List<String> endTimes = viewHelper.getEndTimes(days,startTime,activityOfferingWrapper.getTimeSlotType());
        activityOfferingWrapper.getNewScheduleRequest().setEndTimes(endTimes);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=deleteScheduleComponent")
    public ModelAndView deleteScheduleComponent( @ModelAttribute("KualiForm") MaintenanceDocumentForm form ) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        ScheduleWrapper scheduleWrapper = (ScheduleWrapper) getSelectedObject(form);

        activityOfferingWrapper.getDeletedScheduleComponents().add(scheduleWrapper);
        activityOfferingWrapper.getRequestedScheduleComponents().remove(scheduleWrapper);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=addScheduleComponent")
    public ModelAndView addScheduleComponent(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        ScheduleWrapper scheduleWrapper = activityOfferingWrapper.getNewScheduleRequest();
        String startTime = StringUtils.substringBefore(scheduleWrapper.getStartTime()," ");
        String startTimeAmPm = StringUtils.substringAfter(scheduleWrapper.getStartTime()," ");
        String endTime = "";
        String endTimeAmPm = "";

        if (StringUtils.isNotBlank(scheduleWrapper.getEndTime())){
            endTime = StringUtils.substringBefore(scheduleWrapper.getEndTime()," ");
            endTimeAmPm = StringUtils.substringAfter(scheduleWrapper.getEndTime()," ");
        }

        if (!scheduleWrapper.isTba() && validateTime(startTime, startTimeAmPm, endTime, endTimeAmPm)) {
            GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.newScheduleRequest.endTime", ActivityOfferingConstants.MSG_ERROR_INVALID_START_TIME);
            form.setJumpToId("TOP");
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

        if (GlobalVariables.getMessageMap().getErrorCount() == 0){
            form.setJumpToId("ActivityOffering-DeliveryLogistic-Actuals");
        } else {
            form.setJumpToId("TOP");
        }

        return getUIFModelAndView(form);
    }

    /**
     * This method is called by a helper service to perform the super.route method. It's indirect, but we need a new
     * transaction boundary around this call to support handling errors without causing a rollback exception in the UI
     *
     * @return model and view
     */
    public ModelAndView routeSuper(DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        return super.route(form, result, request, response);
    }

     /**
      * Extra handling is done here for exceptions in the super.route, and also to perform redirects after routing
      */
     @Override
     @RequestMapping(params = "methodToCall=route")
     public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {
        /**
         * The route method will call ActivityOfferingRule#isDocumentValidForSave() followed by
         * ActivityOfferingMaintainableImpl#saveDataObject(). Validation will happen in isDocumentValidForSave() and
         * stored in the message map. Any problems which happen in saveDataObject() have to be thrown, caught here, unwrapped
         * and put in the message map.
         */
        try {
            //Call the transaction helper to eventually call super.route, but with a new transaction
            //This way if the super.route transaction fails, the current transaction will still succeed and errors can
            //be displayed in the UI
        	
        	ActivityOfferingControllerTransactionHelper helper = CourseOfferingManagementUtil.getActivityOfferingControllerTransactionHelper();
            helper.routeSuper(form, result, request, response, this);
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, unwrapException(e).getMessage());
        }

        if (GlobalVariables.getMessageMap().hasErrors()) {
            return getUIFModelAndView(form);
        }

        String loadNewAO = form.getActionParameters().get("aoId");
        String returnLocation = form.getReturnLocation();

        String url;
        if (StringUtils.contains(returnLocation,"viewId=courseOfferingManagementView") ||
                StringUtils.contains(returnLocation,"pageId=manageTheCourseOfferingPage")) {
            if (!returnLocation.contains("methodToCall=")){ //This happens when we display a list of COs and then user click on Manage action
                url = returnLocation + "&methodToCall=reloadManageCO";
            } else {
                url = returnLocation.replaceFirst("methodToCall=[a-zA-Z0-9]+","methodToCall=reloadManageCO");
            }
        } else {
            url = returnLocation;
        }

        if (StringUtils.isNotBlank(loadNewAO)){
            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();
            Properties urlParameters = new Properties();
            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
            urlParameters.put(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID, loadNewAO);
            urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_ID, activityOfferingWrapper.getAoInfo().getCourseOfferingId());
            urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, ActivityOfferingWrapper.class.getName());
            // UrlParams.SHOW_HISTORY and SHOW_HOME no longer exist
            // https://fisheye.kuali.org/changelog/rice?cs=39034
            // TODO KSENROLL-8469
            //urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
            urlParameters.put(EnrollConstants.GROWL_MESSAGE, ActivityOfferingConstants.MSG_INFO_AO_MODIFIED);
            urlParameters.put("returnLocation", url);

            GlobalVariables.getUifFormManager().removeSessionForm(form);
            return performRedirect(form, "activityOffering", urlParameters);
        }

        // clear current form from session
        GlobalVariables.getUifFormManager().removeSessionForm(form);

        // Doesn't work with performRedirect
        //KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, ActivityOfferingConstants.MSG_INFO_AO_MODIFIED);
        // Alas, this doesn't work either.  See KsUifFormBase.postBind(..) for how it works in a regular form
        Properties urlParameters = new Properties();
        urlParameters.put(EnrollConstants.GROWL_MESSAGE, ActivityOfferingConstants.MSG_INFO_AO_MODIFIED);
        return performRedirect(form, url, urlParameters);
    }

    /**
     * Fish out the original cause.
     */
    private Throwable unwrapException(Throwable e) {
        if (e.getCause() != null) {
             unwrapException(e.getCause());
        }
        return e;
    }

    @RequestMapping(params = "methodToCall=cancel")
    @Override
    public ModelAndView cancel(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        DocumentFormBase documentForm = (DocumentFormBase) form;
        performWorkflowAction(documentForm, UifConstants.WorkflowAction.CANCEL, false);

        String loadNewAO = form.getActionParameters().get("aoId");
        String returnLocation = form.getReturnLocation();

        String url;
        if (StringUtils.contains(returnLocation,"viewId=courseOfferingManagementView")) {
            if (!returnLocation.contains("methodToCall=")){ //This happens when we display a list of COs and then user click on Manage action
                url = returnLocation + "&methodToCall=show";
            } else {
                url = returnLocation.replaceFirst("methodToCall=[a-zA-Z0-9]+","methodToCall=show");
            }
        } else {
            url = returnLocation;
        }

        if (StringUtils.isNotBlank(loadNewAO)){
            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();
            Properties urlParameters = new Properties();
            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
            urlParameters.put(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID, loadNewAO);
            urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_ID, activityOfferingWrapper.getAoInfo().getCourseOfferingId());
            urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, ActivityOfferingWrapper.class.getName());
            // UrlParams.SHOW_HISTORY and SHOW_HOME no longer exist
            // https://fisheye.kuali.org/changelog/rice?cs=39034
            // TODO KSENROLL-8469
            //urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
            urlParameters.put("returnLocation", url);

            GlobalVariables.getUifFormManager().removeSessionForm(form);
            return performRedirect(form, "activityOffering", urlParameters);
        }

        String newUrl = url.replaceAll("growl[^&]*&", "");
        form.setReturnLocation(newUrl);
        return back(form,result,request,response);
    }

    @RequestMapping(params = "methodToCall=breakColo")
    public ModelAndView breakColo(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        activityOfferingWrapper.getColocatedActivities().clear();
        activityOfferingWrapper.setSharedMaxEnrollment(0);
        activityOfferingWrapper.getEditRenderHelper().getManageSeperateEnrollmentList().clear();
        activityOfferingWrapper.setColocatedAO(false);
        activityOfferingWrapper.getNewScheduleRequest().getColocatedAOs().clear();
        activityOfferingWrapper.getRequestedScheduleComponents().clear();
        for (ScheduleWrapper scheduleWrapper : activityOfferingWrapper.getActualScheduleComponents()){
            scheduleWrapper.getColocatedAOs().clear();
        }

        activityOfferingWrapper.setRemovedFromColoSet(true);

        return getUIFModelAndView(form);
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
