package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
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
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.SeatPoolWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.ActivityOfferingMaintainable;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.population.util.PopulationConstants;
import org.kuali.student.enrollment.courseoffering.dto.ColocatedOfferingSetInfo;
import org.kuali.student.enrollment.uif.form.KSUifMaintenanceDocumentForm;
import org.kuali.student.enrollment.uif.util.GrowlIcon;
import org.kuali.student.enrollment.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.uif.util.KSUifUtils;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/activityOffering")
public class ActivityOfferingController extends MaintenanceDocumentController {
    private transient PopulationService populationService;

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

    /*@RequestMapping(params = "methodToCall=reviseSchedule")
    public ModelAndView reviseSchedule(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        ActivityOfferingMaintainable viewHelper = (ActivityOfferingMaintainable) KSControllerHelper.getViewHelperService(form);
        viewHelper.prepareForScheduleRevise(activityOfferingWrapper);
        form.setDeliveryLogisiticsAddButtonText("Add");

        return getUIFModelAndView(form,MaintenanceDocumentForm.SCHEDULE_PAGE);
    }*/

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=editScheduleComponent")
    public ModelAndView editScheduleComponent(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        ScheduleWrapper scheduleWrapper = (ScheduleWrapper)getSelectedObject(form);

        ScheduleWrapper newSchedule = new ScheduleWrapper();
        newSchedule.copyForEditing(scheduleWrapper);
        activityOfferingWrapper.setNewScheduleRequest(newSchedule);
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
//            form.setDeliveryLogisiticsAddButtonText("Add");
            activityOfferingWrapper.getEditRenderHelper().setScheduleEditInProgress(false);
            activityOfferingWrapper.setSchedulesModified(true);
            if (activityOfferingWrapper.isColocatedAO() && !activityOfferingWrapper.getColocatedActivities().isEmpty()){
                GlobalVariables.getMessageMap().putWarning("ActivityOffering-DeliveryLogistic-Requested", RiceKeyConstants.ERROR_CUSTOM,activityOfferingWrapper.getEditRenderHelper().getColocatedActivitiesAsString());
            }
        }
        
        return getUIFModelAndView(form);
    }

    /*@RequestMapping(params = "methodToCall=saveRevisedSchedules")
    public ModelAndView saveRevisedSchedules(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        if (form.isScheduleEditInProgress()){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Editing a schedule request in progress. Please update it first before processing");
            return getUIFModelAndView(form,MaintenanceDocumentForm.SCHEDULE_PAGE);
        }

        activityOfferingWrapper.getRequestedScheduleComponents().clear();

        for (ScheduleWrapper scheduleWrapper : activityOfferingWrapper.getRevisedScheduleRequestComponents()){
            activityOfferingWrapper.getRequestedScheduleComponents().add(scheduleWrapper);
        }

        activityOfferingWrapper.setNewScheduleRequest(new ScheduleWrapper());
        activityOfferingWrapper.getRevisedScheduleRequestComponents().clear();

        return getUIFModelAndView(form,MaintenanceDocumentForm.MAIN_PAGE);
    }

    @RequestMapping(params = "methodToCall=processRevisedSchedules")
    public ModelAndView processRevisedSchedules(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        if (form.isScheduleEditInProgress()){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Editing a schedule request in progress. Please update it first before processing");
            return getUIFModelAndView(form,MaintenanceDocumentForm.SCHEDULE_PAGE);
        }

        activityOfferingWrapper.getRequestedScheduleComponents().clear();

        for (ScheduleWrapper scheduleWrapper : activityOfferingWrapper.getRevisedScheduleRequestComponents()){
            activityOfferingWrapper.getRequestedScheduleComponents().add(scheduleWrapper);
        }

        ActivityOfferingMaintainable viewHelper = (ActivityOfferingMaintainable) KSControllerHelper.getViewHelperService(form);
        viewHelper.processRevisedSchedules(activityOfferingWrapper);

        activityOfferingWrapper.setNewScheduleRequest(new ScheduleWrapper());
        activityOfferingWrapper.getRevisedScheduleRequestComponents().clear();
        activityOfferingWrapper.setSchedulesModified(false);

        GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM, "Schedule has been successfully processed");

        return getUIFModelAndView(form,MaintenanceDocumentForm.MAIN_PAGE);
    }

    @RequestMapping(params = "methodToCall=cancelScheduleRevise")
    public ModelAndView cancelScheduleRevise(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        activityOfferingWrapper.getRevisedScheduleRequestComponents().clear();
        activityOfferingWrapper.setSchedulesModified(false);

        return getUIFModelAndView(form,MaintenanceDocumentForm.MAIN_PAGE);
    }*/
    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, PopulationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return populationService;
    }

    public ContextInfo createContextInfo(){
        return ContextUtils.createDefaultContextInfo();
    }

    private boolean _validateSeatpools(ActivityOfferingWrapper activityOfferingWrapper){
        List<SeatPoolWrapper> seatPoolWrappers = activityOfferingWrapper.getSeatpools();
        String errorMsgInvalidPop  = "";
        String errorMsgDupPop = "";
        Set<String> populationIds = new HashSet<String>();
        boolean validFlag = true;

        for (SeatPoolWrapper seatPool : seatPoolWrappers) {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.equal("populationState", PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY),
                    PredicateFactory.equalIgnoreCase("name", seatPool.getSeatPoolPopulation().getName())));
            QueryByCriteria criteria = qbcBuilder.build();

            try {
                List<PopulationInfo> populationInfoList = getPopulationService().searchForPopulations(criteria, createContextInfo());
                //check if the population is valid
                if(populationInfoList == null || populationInfoList.isEmpty()){
                    if (errorMsgInvalidPop.isEmpty()) {
                        errorMsgInvalidPop = seatPool.getSeatPoolPopulation().getName();
                    } else {
                        errorMsgInvalidPop += ", " + seatPool.getSeatPoolPopulation().getName();
                    }
                } else {
                    seatPool.getSeatPoolPopulation().setName(populationInfoList.get(0).getName());
                    seatPool.getSeatPoolPopulation().setId(populationInfoList.get(0).getId());

                    //check if the population is duplicated. If the id can't be added into the populationIds, it means it is duplicated
                    if(!populationIds.add(populationInfoList.get(0).getId())) {
                        if (errorMsgDupPop.isEmpty()) {
                            errorMsgDupPop = seatPool.getSeatPoolPopulation().getName();
                        } else {
                            errorMsgDupPop += ", " + seatPool.getSeatPoolPopulation().getName();
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if(!errorMsgInvalidPop.isEmpty()) {
            GlobalVariables.getMessageMap().putErrorForSectionId("ao-seatpoolgroup", PopulationConstants.POPULATION_MSG_ERROR_POPULATION_NOT_FOUND, errorMsgInvalidPop);
            validFlag = false;
        }
        if (!errorMsgDupPop.isEmpty()) {
            GlobalVariables.getMessageMap().putErrorForSectionId("ao-seatpoolgroup", PopulationConstants.POPULATION_MSG_ERROR_NAME_IS_NOT_UNIQUE, errorMsgDupPop);
            validFlag = false;
        }

        return validFlag;
    }

    @Override
    @RequestMapping(params = "methodToCall=route")
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        //validate Seat Pool population
        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();
        if (!_validateSeatpools(activityOfferingWrapper)) {
            return getUIFModelAndView(form);
        }

        ModelAndView modelAndView = super.route(form, result, request, response);

        GlobalVariables.getMessageMap().addGrowlMessage("", "activityOffering.modified" );

//        if( form.getHistoryFlow().FormHistory().getHistoryEntries().isEmpty()){
//            return modelAndView;
//        }
//
//        //Redirect to last page (some hackery here that I'd rather not do
//        //The url has lots of dulpicate params which prevents history from working correctly
//        String url = form.getFormHistory().getHistoryEntries().get(form.getFormHistory().getHistoryEntries().size() - 1).getUrl();
//        url = url.replaceAll("&methodToCall=[a-zA-Z]+","");
//        url = url.replaceAll("&pageId=[a-zA-Z]+","");
//        url = url.replaceAll("\\?methodToCall=[a-zA-Z]+&","?");
//        url = url.replaceAll("\\?pageId=[a-zA-Z]+&","?");
//        form.getFormHistory().getHistoryEntries().get(form.getFormHistory().getHistoryEntries().size() - 1).setUrl(url);
//        modelAndView = returnToHistory(form, false);
//        modelAndView.setViewName(modelAndView.getViewName().replaceFirst("methodToCall="+ UifConstants.MethodToCallNames.REFRESH,"methodToCall=show"));
        return modelAndView;
    }

    /*@RequestMapping(method = RequestMethod.POST, params = "methodToCall=deleteLine")
    public ModelAndView deleteLine(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);

        if (StringUtils.endsWith(selectedCollectionPath,"revisedScheduleRequestComponents")){
            if (activityOfferingWrapper.getRequestedScheduleComponents().size() == 1){
                String dialogName = "deleteScheduleConfirmDialog";

                if (!hasDialogBeenAnswered(dialogName, form)) {
                    return showDialog(dialogName, form, request, response);
                }

                boolean dialogAnswer = getBooleanDialogResponse(dialogName, form, request, response);
                form.getDialogManager().resetDialogStatus(dialogName);

                if (!dialogAnswer) {
                    return getUIFModelAndView(form);
                }
            }
        }

        return super.deleteLine(form,result,request,response);

    }*/

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

    @RequestMapping(params = "methodToCall=detachAOFromColocation")
     public ModelAndView detachAOFromColocation(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        if(activityOfferingWrapper.isPartOfColoSetOnLoadAlready()){
            ColocatedOfferingSetInfo colocatedOfferingSetInfo = activityOfferingWrapper.getColocatedOfferingSetInfo();
            boolean maxEnrollmentShared = colocatedOfferingSetInfo.getIsMaxEnrollmentShared();

            ActivityOfferingMaintainable viewHelper = (ActivityOfferingMaintainable) KSControllerHelper.getViewHelperService(form);
            //viewHelper.detachAOFromColocation(form.getDocument(), activityOfferingWrapper);

            if(maxEnrollmentShared){
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.COLOCATION_MAX_ENR_SHARED);
            }else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.COLOCATION_MAX_ENR_SEPARATED);
            }

        }
        return getUIFModelAndView(form);
      }
}
