package org.kuali.student.enrollment.class2.appointment.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;
import org.kuali.student.enrollment.class2.appointment.form.RegistrationWindowsManagementForm;
import org.kuali.student.enrollment.class2.appointment.service.AppointmentViewHelperService;
import org.kuali.student.enrollment.class2.appointment.util.AppointmentConstants;
import org.kuali.student.enrollment.class2.appointment.util.AppointmentSlotRuleTypeConversion;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class manages UI actions for registration windows
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/registrationWindows")
public class RegistrationWindowsController extends UifControllerBase {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RegistrationWindowsController.class);

    private AppointmentViewHelperService viewHelperService;
    
    private AcademicCalendarService acalService;

    private AppointmentService appointmentService;

    private PopulationService populationService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new RegistrationWindowsManagementForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        RegistrationWindowsManagementForm theForm = (RegistrationWindowsManagementForm)form;
        String termId = request.getParameter("termId");

        if (StringUtils.isNotBlank(termId)){
            try {
                getViewHelperService(theForm).loadTermAndPeriods(termId, theForm);
                return getUIFModelAndView(theForm);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return super.start(theForm, result, request, response);
    }

    /**
     * Method used to search term
     */
    @RequestMapping(params = "methodToCall=searchForTerm")
    public ModelAndView searchForTerm(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm searchForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        String termType = searchForm.getTermType();
        String termYear = searchForm.getTermYear();

        getViewHelperService(searchForm).searchForTerm(termType, termYear, searchForm);

        if (GlobalVariables.getMessageMap().hasErrors()){
            return getUIFModelAndView(searchForm, AppointmentConstants.SELECT_TERM_PAGE);
        }

        return getUIFModelAndView(searchForm, AppointmentConstants.REGISTRATION_WINDOWS_EDIT_PAGE);
    }

    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (!form.isShowAddWindows()){
            form.setShowAddWindows(true);
        }
        String periodId = form.getPeriodId();
        String periodInfoDetails = "";

        //Clear all the windows
        form.getAppointmentWindows().clear();

        if(periodId == null || periodId.isEmpty()) {
            form.setPeriodInfoDetails(periodInfoDetails);
            form.setShowAddWindows(false);
            form.getAppointmentWindows().clear();
        }
        else if (!periodId.isEmpty() && !periodId.equals("all")) {

            //Lookup the period information
            KeyDateInfo period = getAcalService().getKeyDate(periodId,ContextInfo.createDefaultContextInfo());

            //pull in the windows for this period
            List<KeyDateInfo> periods = new ArrayList<KeyDateInfo>();
            periods.add(period);
            _loadWindowsInfoForm(periods, form);

            // display the period start/end time in details and the period name in the AddLine
            AppointmentWindowWrapper addLine= (AppointmentWindowWrapper)form.getNewCollectionLines().get("appointmentWindows");
            if (addLine == null){
                addLine = new AppointmentWindowWrapper();
                form.getNewCollectionLines().put("appointmentWindows", addLine);
            }

            if (period.getName() != null) {
                periodInfoDetails = period.getName()+" Start Date: "+_getSimpleDate(period.getStartDate())+ "<br>"
                        + period.getName()+" End Date: "+_getSimpleDate(period.getEndDate());
                form.setPeriodName(period.getName());
                form.setPeriodId(period.getId());
                addLine.setPeriodName(period.getName());
                addLine.setPeriodKey(period.getId());
            } else {
                periodInfoDetails = period.getId()+" Start Date: "+_getSimpleDate(period.getStartDate())+ "<br>"
                        + period.getId()+" End Date: "+_getSimpleDate(period.getEndDate());
                form.setPeriodName(period.getId());
                form.setPeriodId(period.getId());
                addLine.setPeriodName(period.getId());
                addLine.setPeriodKey(period.getId());
            }
            form.setPeriodInfoDetails(periodInfoDetails);

            //TODO: pull out all windows for that period and add to the collection
        }
        else if (periodId.equals("all")) {
            List<KeyDateInfo> periodMilestones = form.getPeriodMilestones();
            if(periodMilestones.isEmpty()) {
                TermInfo term = form.getTermInfo();
                if (term.getId() != null && !term.getId().isEmpty()) {
                    getViewHelperService(form).loadPeriods(term.getId(), form);
                    periodMilestones = form.getPeriodMilestones();
                }
            }
            for (KeyDateInfo period : periodMilestones){

                if (period.getName() != null) {
                    periodInfoDetails = periodInfoDetails.concat(period.getName()+" Start Date: "+_getSimpleDate(period.getStartDate())+ "<br>"
                            + period.getName()+" End Date: "+_getSimpleDate(period.getEndDate())+"<br>");
                } else {
                    periodInfoDetails = periodInfoDetails.concat(period.getId()+" Start Date: "+period.getStartDate()+ "<br>"
                            + period.getId()+" End Date: "+_getSimpleDate(period.getEndDate())+"<br>");
                }
            }
            form.setPeriodInfoDetails(periodInfoDetails);
            _loadWindowsInfoForm(periodMilestones, form);
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        //Loop through the form's appointment windows and create/update them using the appointmentService
        getViewHelperService(form).saveWindows(form);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=assignStudents")
    public ModelAndView assignStudents(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm uifForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        ///First save selected window
        AppointmentWindowWrapper window = _getSelectedWindow(uifForm, "Assign Students");
        boolean isValid = getViewHelperService(uifForm).validateApptWidnow(window);
        if (isValid) {
            boolean isSaved = getViewHelperService(uifForm).saveApptWindow(window);

            //Now do the assignments of slots and students
            if(window!=null && isSaved){
                //Create the appointment slots and assign students
                List<AppointmentSlotInfo> slots = getAppointmentService().generateAppointmentSlotsByWindow(window.getAppointmentWindowInfo().getId(), new ContextInfo());
                StatusInfo status = getAppointmentService().generateAppointmentsByWindow(window.getAppointmentWindowInfo().getId(), window.getAppointmentWindowInfo().getTypeKey(), new ContextInfo());

                //Get feedback to the user6
                if(status.getIsSuccess()){
                    GlobalVariables.getMessageMap().putInfo( KRADConstants.GLOBAL_MESSAGES,
                            AppointmentConstants.APPOINTMENT_MSG_INFO_ASSIGNED,window.getAppointmentWindowInfo().getName(), status.getMessage(), String.valueOf(slots.size()));
                    //Update window state
                    window.getAppointmentWindowInfo().setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ASSIGNED_KEY);
                }else{
                    //There was an error
                    GlobalVariables.getMessageMap().putInfo( KRADConstants.GLOBAL_MESSAGES,
                            AppointmentConstants.APPOINTMENT_MSG_ERROR_TOO_MANY_STUDENTS, status.getMessage());
                }
            }
        }
        return getUIFModelAndView(uifForm);
    }

    @RequestMapping(params = "methodToCall=breakAppointments")
    public ModelAndView breakAppointments(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm uifForm, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {

        AppointmentWindowWrapper window = _getSelectedWindow(uifForm, "Break Appointments");
        if(window!=null){
            //Delete the appointment slots and appointments for this window
            StatusInfo status = getAppointmentService().deleteAppointmentSlotsByWindowCascading(window.getAppointmentWindowInfo().getId(), new ContextInfo());
            if(status.getIsSuccess()){
                GlobalVariables.getMessageMap().putInfo( KRADConstants.GLOBAL_MESSAGES,
                        AppointmentConstants.APPOINTMENT_MSG_INFO_BREAK_APPOINTMENTS_SUCCESS);

                //Update window state back to draft
                window.getAppointmentWindowInfo().setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY);
            }else{
                //There was an error
                GlobalVariables.getMessageMap().putInfo( KRADConstants.GLOBAL_MESSAGES,
                        AppointmentConstants.APPOINTMENT_MSG_ERROR_BREAK_APPOINTMENTS_FAILURE, status.getMessage());
            }
        }

        return getUIFModelAndView(uifForm);
    }

    @Override
    public ModelAndView deleteLine(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        RegistrationWindowsManagementForm theForm = (RegistrationWindowsManagementForm)uifForm;
        try{
            AppointmentWindowWrapper window = _getSelectedWindow(theForm, "Delete a Window");
            if (window != null){
                if (AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ASSIGNED_KEY.equals(window.getAppointmentWindowInfo().getStateKey())){
                    //need to break Assignment first
                    //Delete the appointment slots and appointments for this window
                    StatusInfo status = getAppointmentService().deleteAppointmentSlotsByWindowCascading(window.getAppointmentWindowInfo().getId(), new ContextInfo());
                    if(status.getIsSuccess()){
                        getAppointmentService().deleteAppointmentWindowCascading(window.getId(), new ContextInfo());
                        return super.deleteLine(uifForm, result, request, response);
                    }else{
                        //There was an error
                        GlobalVariables.getMessageMap().putInfo( KRADConstants.GLOBAL_MESSAGES,
                                AppointmentConstants.APPOINTMENT_MSG_ERROR_BREAK_APPOINTMENTS_FAILURE, status.getMessage());
                        return getUIFModelAndView(uifForm);
                    }
                }else {
                    getAppointmentService().deleteAppointmentWindowCascading(window.getId(), new ContextInfo());
                    return super.deleteLine(uifForm, result, request, response);
                }
            }else {
                //TODO: log window == null message
                return getUIFModelAndView(uifForm);
            }
        }catch (Exception e){
            //TODO: log exception
            return getUIFModelAndView(uifForm);

        }
    }

    // this is a more generic way to get a selected window when the form contains more than one collection
    private AppointmentWindowWrapper _getSelectedWindow(RegistrationWindowsManagementForm theForm, String actionLink){
        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for " + actionLink);
        }

        int selectedLineIndex = -1;
        String selectedLine = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Selected line index was not set");
        }

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(theForm, selectedCollectionPath);
        Object window = ((List<Object>) collection).get(selectedLineIndex);

        return (AppointmentWindowWrapper)window;
    }


    private String _getSimpleDate(Date date) {
        if (date == null){
            return "";
        }
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(date);
    }

    private void _loadWindowsInfoForm(List<KeyDateInfo> periods, RegistrationWindowsManagementForm form) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        for(KeyDateInfo period:periods){
            List<AppointmentWindowInfo> windows = getAppointmentService().getAppointmentWindowsByPeriod(period.getId(), new ContextInfo());
            if(windows!=null && windows.size()>0){
                for(AppointmentWindowInfo window:windows){

                    //Look up the population
                    PopulationInfo population = getPopulationService().getPopulation(window.getAssignedPopulationId(), new ContextInfo());

                    AppointmentWindowWrapper windowWrapper = new AppointmentWindowWrapper();

                    windowWrapper.setAppointmentWindowInfo(window);
                    windowWrapper.setId(window.getId());
                    windowWrapper.setWindowName(window.getName());
                    windowWrapper.setPeriodKey(window.getPeriodMilestoneId());
                    windowWrapper.setPeriodName(period.getName());
                    windowWrapper.setAssignedPopulationName(population.getName());
                    windowWrapper.setWindowTypeKey(window.getTypeKey());
                    if(!AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY.equals(window.getTypeKey())) {
                        windowWrapper.setSlotRuleEnumType(AppointmentSlotRuleTypeConversion.convToAppointmentSlotRuleCode(window.getSlotRule()));
                    }
                    windowWrapper.setStartDate(window.getStartDate());
                    windowWrapper.setStartTime(_parseTime(window.getStartDate()));
                    windowWrapper.setStartTimeAmPm(_parseAmPm(window.getStartDate()));

                    windowWrapper.setEndDate(window.getEndDate());
                    windowWrapper.setEndTime(_parseTime(window.getEndDate()));
                    windowWrapper.setEndTimeAmPm(_parseAmPm(window.getEndDate()));

                    form.getAppointmentWindows().add(windowWrapper);
                }
            }
        }
    }

    private String _parseAmPm(Date date) {
        if(date==null){
            return null;
        }
        DateFormat df = new SimpleDateFormat("a");
        return df.format(date);
    }

    private String _parseTime(Date date) {
        if(date==null){
            return null;
        }
        DateFormat df = new SimpleDateFormat("hh:mm");
        return df.format(date);
    }

    public AppointmentViewHelperService getViewHelperService(RegistrationWindowsManagementForm appointmentForm){
        if (viewHelperService == null) {
            if (appointmentForm.getView().getViewHelperServiceClass() != null){
                viewHelperService = (AppointmentViewHelperService)appointmentForm.getView().getViewHelperService();
            }else{
                viewHelperService= (AppointmentViewHelperService)appointmentForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }
    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }


    public AppointmentService getAppointmentService() {
        if(appointmentService == null) {
            appointmentService = (AppointmentService) GlobalResourceLoader.getService(new QName(AppointmentServiceConstants.NAMESPACE, AppointmentServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return appointmentService;
    }

    public PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationMockService")); // TODO: Refactor later with real PopService
        }
        return populationService;
    }

}
