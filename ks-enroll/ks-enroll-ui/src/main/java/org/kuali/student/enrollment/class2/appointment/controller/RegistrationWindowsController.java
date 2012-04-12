package org.kuali.student.enrollment.class2.appointment.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifParameters;
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
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotRuleInfo;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/registrationWindows")
public class RegistrationWindowsController extends UifControllerBase {

    private AcademicCalendarService acalService;

    private AppointmentService appointmentService;

    private PopulationService populationService;

    private ContextInfo contextInfo;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new RegistrationWindowsManagementForm();
    }

    @RequestMapping(params = "methodToCall=assignStudents")
    public ModelAndView assignStudents(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm uifForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        ///First save all the windows
        _saveWindows(uifForm);

        //Now do the assignments of slots and students
        AppointmentWindowWrapper window = _getSelectedWindow(uifForm);
        if(window!=null){
            List<AppointmentSlotInfo> slots = getAppointmentService().generateAppointmentSlotsByWindow(window.getAppointmentWindowInfo().getId(), new ContextInfo());
            StatusInfo status = getAppointmentService().generateAppointmentsByWindow(window.getAppointmentWindowInfo().getId(), window.getAppointmentWindowInfo().getTypeKey(), new ContextInfo());
            //TODO update window state
            if(status.getIsSuccess()){
                GlobalVariables.getMessageMap().putInfo( KRADConstants.GLOBAL_MESSAGES,
                        AppointmentServiceConstants.APPOINTMENT_MSG_INFO_ASSIGNED,window.getAppointmentWindowInfo().getName(), status.getMessage(), String.valueOf(slots.size()));
            }else{
                //There was an error
                GlobalVariables.getMessageMap().putInfo( KRADConstants.GLOBAL_MESSAGES,
                        AppointmentServiceConstants.APPOINTMENT_MSG_ERROR_TOO_MANY_STUDENTS, status.getMessage());
            }
        }

        return updateComponent(uifForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=breakAppointments")
    public ModelAndView breakAppointments(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm uifForm, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {

        AppointmentWindowWrapper window = _getSelectedWindow(uifForm);
        if(window!=null){
            getAppointmentService().deleteAppointmentSlotsByWindow(window.getAppointmentWindowInfo().getId(), new ContextInfo());
            //TODO update window state
        }

        return updateComponent(uifForm, result, request, response);
    }

    @Override
    public ModelAndView deleteLine(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        RegistrationWindowsManagementForm theForm = (RegistrationWindowsManagementForm)uifForm;

        AppointmentWindowWrapper window = _getSelectedWindow(theForm);
        if(window!=null&&window.getAppointmentWindowInfo().getId() != null && !theForm.getAppointmentWindowIdsToDelete().contains(window.getAppointmentWindowInfo().getId())){
            //Add to the list of windows to delete
            theForm.getAppointmentWindowIdsToDelete().add(window.getAppointmentWindowInfo().getId());
        }

        return super.deleteLine(uifForm, result, request, response);
    }

    private AppointmentWindowWrapper _getSelectedWindow(RegistrationWindowsManagementForm uifForm) {
        //Get the index of the selected line that is to be deleted
        int selectedLineIndex = -1;
        String selectedLine = uifForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        //Add the window id to the list of ids to be deleted
        if(selectedLineIndex>=0){
            return uifForm.getAppointmentWindows().get(selectedLineIndex);
        }
        return null;
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
     * Method used to search atps
     */
    @RequestMapping(params = "methodToCall=searchForTerm")
    public ModelAndView searchForTerm(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm searchForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        String termType = searchForm.getTermType();
        String termYear = searchForm.getTermYear();

        // resetForm(searchForm);
        getViewHelperService(searchForm).searchForTerm(termType, termYear, searchForm);
        return getUIFModelAndView(searchForm, AppointmentConstants.REGISTRATION_WINDOWS_EDIT_PAGE);
    }

    @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        //Delete anything that needs to be deleted
        for(String windowId:form.getAppointmentWindowIdsToDelete()){
            getAppointmentService().deleteAppointmentWindow(windowId, new ContextInfo());
        }
        form.getAppointmentWindowIdsToDelete().clear();

        //Loop through the form's appointment windows and create/update them using the appointmentService
         _saveWindows(form);


        return getUIFModelAndView(form);
    }

    private void _saveWindows(RegistrationWindowsManagementForm form) throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException, VersionMismatchException {
        if(form.getAppointmentWindows()!=null){

            for(AppointmentWindowWrapper appointmentWindowWrapper:form.getAppointmentWindows()){

                //Copy the form data from the wrapper to the bean. //TODO(should this be done in the wrapper bean?)
                AppointmentWindowInfo appointmentWindowInfo = appointmentWindowWrapper.getAppointmentWindowInfo();
                appointmentWindowInfo.setTypeKey(appointmentWindowWrapper.getWindowTypeKey());
                appointmentWindowInfo.setPeriodMilestoneId(appointmentWindowWrapper.getPeriodKey());
                appointmentWindowInfo.setStartDate(_updateTime(appointmentWindowWrapper.getStartDate(), appointmentWindowWrapper.getStartTime(), appointmentWindowWrapper.getStartTimeAmPm()));
                appointmentWindowInfo.setEndDate(_updateTime(appointmentWindowWrapper.getEndDate(), appointmentWindowWrapper.getEndTime(), appointmentWindowWrapper.getEndTimeAmPm()));

                //TODO Periods are not working since the term search isn't working. Delete this block when fixed
                if(appointmentWindowInfo.getPeriodMilestoneId() == null || appointmentWindowInfo.getPeriodMilestoneId().isEmpty()){
                    appointmentWindowInfo.setPeriodMilestoneId("DUMMY_ID");
                }

                //TODO Default to some value if nothing is entered(Service team needs to make up some real types or make not nullable)
                if(appointmentWindowInfo.getAssignedOrderTypeKey() == null || appointmentWindowInfo.getAssignedOrderTypeKey().isEmpty()){
                    appointmentWindowInfo.setAssignedOrderTypeKey("DUMMY_ID");
                }

                //Default to single slot type if nothing is entered
                if(appointmentWindowInfo.getTypeKey() == null || appointmentWindowInfo.getTypeKey().isEmpty()){
                    appointmentWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY);
                }

                if(appointmentWindowInfo.getId()==null||appointmentWindowInfo.getId().isEmpty()){
                    //Default the state to active
                    appointmentWindowInfo.setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY);

                    //Default the Weekdays to a value since the DB schema does not allow null values
                    appointmentWindowInfo.setSlotRule(new AppointmentSlotRuleInfo());
                    appointmentWindowInfo.getSlotRule().setWeekdays(new ArrayList<Integer>());
                    appointmentWindowInfo.getSlotRule().getWeekdays().add(1);

                    appointmentWindowInfo = getAppointmentService().createAppointmentWindow(appointmentWindowInfo.getTypeKey(),appointmentWindowInfo,new ContextInfo());
                }else{
                    appointmentWindowInfo = getAppointmentService().updateAppointmentWindow(appointmentWindowInfo.getId(),appointmentWindowInfo,new ContextInfo());
                }

                //Reset the windowInfo from the service's returned value
                appointmentWindowWrapper.setAppointmentWindowInfo(appointmentWindowInfo);

            }
            //Add a success message
            GlobalVariables.getMessageMap().putInfo( KRADConstants.GLOBAL_MESSAGES,
                    AppointmentServiceConstants.APPOINTMENT_MSG_INFO_SAVED);
        }
    }

    //Copied from AcademicCalendarViewHelperServiceImpl //TODO(should be moved into common util class)
    private Date _updateTime(Date date,String time,String amPm){

        if(date == null || time == null || amPm == null){
            return null;
        }

        //FIXME: Use Joda DateTime

        // Get Calendar object set to the date and time of the given Date object
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // Set time fields to zero
        cal.set(Calendar.HOUR, Integer.parseInt(StringUtils.substringBefore(time,":")));
        cal.set(Calendar.MINUTE, Integer.parseInt(StringUtils.substringAfter(time,":")));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (StringUtils.isNotBlank(amPm)){
            if (StringUtils.equalsIgnoreCase(amPm,"am")){
                cal.set(Calendar.AM_PM,Calendar.AM);
            }else if(StringUtils.equalsIgnoreCase(amPm,"pm")){
                cal.set(Calendar.AM_PM,Calendar.PM);
            }else{
                throw new RuntimeException("Unknown AM/PM format.");
            }
        }

        return cal.getTime();
    }

    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {


        String periodId = form.getPeriodId();
        String periodInfoDetails = "";

        //Clear all the windows
        form.getAppointmentWindows().clear();
        form.getAppointmentWindowIdsToDelete().clear();


        if (!periodId.isEmpty() && !periodId.equals("all")) {

            //Lookup the period information
            KeyDateInfo period = getAcalService().getKeyDate(periodId,getContextInfo());

            //pull in the windows for this period
            List<KeyDateInfo> periods = new ArrayList<KeyDateInfo>();
            periods.add(period);
            _loadWindowsInfoForm(periods, form);

            // display the period start/end time in details and the period name in the AddLine
            AppointmentWindowWrapper addLine= (AppointmentWindowWrapper)form.getNewCollectionLines().get("appointmentWindows");

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
    
    private String _getSimpleDate(Date date) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(date);

    }

    private void _loadWindowsInfoForm(List<KeyDateInfo> periods, RegistrationWindowsManagementForm form) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        for(KeyDateInfo period:periods){
            List<AppointmentWindowInfo> windows = getAppointmentService().getAppointmentWindowsByPeriod(period.getId(), new ContextInfo());
            if(windows!=null){
                for(AppointmentWindowInfo window:windows){

                    //Look up the population
                    PopulationInfo population = getPopulationService().getPopulation(window.getAssignedPopulationId(), new ContextInfo());

                    AppointmentWindowWrapper windowWrapper = new AppointmentWindowWrapper();

                    windowWrapper.setAppointmentWindowInfo(window);
                    windowWrapper.setPeriodKey(window.getPeriodMilestoneId());
                    windowWrapper.setPeriodName(period.getName());

                    windowWrapper.setAssignedPopulationName(population.getName());
                    windowWrapper.setWindowTypeKey(window.getTypeKey());

                    windowWrapper.setStartDate(_parseDate(window.getStartDate()));
                    windowWrapper.setStartTime(_parseTime(window.getStartDate()));
                    windowWrapper.setStartTimeAmPm(_parseAmPm(window.getStartDate()));

                    windowWrapper.setEndDate(_parseDate(window.getEndDate()));
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

    private Date _parseDate(Date date) {
        return date;
    }


    private AppointmentViewHelperService getViewHelperService(RegistrationWindowsManagementForm appointmentForm){
        if (appointmentForm.getView().getViewHelperServiceClassName() != null){
            return (AppointmentViewHelperService)appointmentForm.getView().getViewHelperService();
        }else{
            return (AppointmentViewHelperService)appointmentForm.getPostedView().getViewHelperService();
        }
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
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, PopulationService.class.getSimpleName()));
        }
        return populationService;
    }


    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
        }
        return contextInfo;
    }

}
