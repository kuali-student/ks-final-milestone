package org.kuali.student.enrollment.class2.appointment.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;
import org.kuali.student.enrollment.class2.appointment.form.RegistrationWindowsManagementForm;
import org.kuali.student.enrollment.class2.appointment.service.AppointmentViewHelperService;
import org.kuali.student.enrollment.class2.appointment.util.AppointmentConstants;
import org.kuali.student.enrollment.class2.appointment.util.AppointmentSlotRuleTypeConversion;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * This class manages UI actions for registration windows
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/registrationWindows")
public class RegistrationWindowsController extends UifControllerBase {

    private AppointmentViewHelperService viewHelperService;

    private AcademicCalendarService acalService;

    private AppointmentService appointmentService;

    private PopulationService populationService;

    @Override
    protected UifFormBase createInitialForm(@SuppressWarnings("unused") HttpServletRequest request) {
        return new RegistrationWindowsManagementForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        if (!(form instanceof RegistrationWindowsManagementForm)) {
            throw new IllegalArgumentException("Expected RegistrationWindowsManagementForm, got " + form.getClass().getSimpleName());
        }

        RegistrationWindowsManagementForm theForm = (RegistrationWindowsManagementForm) form;
        String termId = request.getParameter("termId");

        if (StringUtils.isNotBlank(termId)) {
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
    public ModelAndView searchForTerm(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm searchForm, @SuppressWarnings("unused") BindingResult result,
                                      @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        String termType = searchForm.getTermType();
        String termYear = searchForm.getTermYear();

        getViewHelperService(searchForm).searchForTerm(termType, termYear, searchForm);

        if (GlobalVariables.getMessageMap().hasErrors()) {
            return getUIFModelAndView(searchForm, AppointmentConstants.SELECT_TERM_PAGE);
        }

        return getUIFModelAndView(searchForm, AppointmentConstants.REGISTRATION_WINDOWS_EDIT_PAGE);
    }

    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm form, @SuppressWarnings("unused") BindingResult result,
                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        if (!form.isShowAddWindows()) {
            form.setShowAddWindows(true);
        }

        form = populatePeriodCollections(form);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm form, @SuppressWarnings("unused") BindingResult result,
                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //Loop through the form's appointment windows and create/update them using the appointmentService
        getViewHelperService(form).saveWindows(form);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=assignStudents")
    public ModelAndView assignStudents(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm uifForm, @SuppressWarnings("unused") BindingResult result,
                                       @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        ///First save selected window
        AppointmentWindowWrapper window = _getSelectedWindow(uifForm, "Assign Students");
        boolean isValid = getViewHelperService(uifForm).validateApptWidnow(window, false);
        if (isValid) {
            boolean isSaved = getViewHelperService(uifForm).saveApptWindow(window);

            //Now do the assignments of slots and students
            if (window != null && isSaved) {
                //Create the appointment slots and assign students
                List<AppointmentSlotInfo> slots = getAppointmentService().generateAppointmentSlotsByWindow(window.getAppointmentWindowInfo().getId(), new ContextInfo());
                StatusInfo status = getAppointmentService().generateAppointmentsByWindow(window.getAppointmentWindowInfo().getId(), window.getAppointmentWindowInfo().getTypeKey(), new ContextInfo());

                //Get feedback to the user6
                if (status.getIsSuccess()) {
                    GlobalVariables.getMessageMap().addGrowlMessage("",
                            AppointmentConstants.APPOINTMENT_MSG_INFO_ASSIGNED, window.getAppointmentWindowInfo().getName(), status.getMessage(), String.valueOf(slots.size()));
                    //Update window state
                    window.getAppointmentWindowInfo().setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ASSIGNED_KEY);
                } else {
                    //There was an error
                    GlobalVariables.getMessageMap().addGrowlMessage("",
                            AppointmentConstants.APPOINTMENT_MSG_ERROR_TOO_MANY_STUDENTS, status.getMessage());
                }
            }
        }
        return getUIFModelAndView(uifForm);
    }

    @RequestMapping(params = "methodToCall=breakAppointments")
    public ModelAndView breakAppointments(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm uifForm, @SuppressWarnings("unused") BindingResult result,
                                          @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "refreshAfterDialog");
        urlParameters.put(UifParameters.VIEW_ID, AppointmentConstants.REGISTRATION_WINDOWS_MANAGEMENT_VIEW);
        urlParameters.put(UifParameters.PAGE_ID, AppointmentConstants.REGISTRATION_WINDOWS_EDIT_PAGE);
        urlParameters.put("periodId", uifForm.getPeriodId());
        urlParameters.put("termType", uifForm.getTermType());
        urlParameters.put("termYear", uifForm.getTermYear());
        urlParameters.put(UifConstants.UrlParams.SHOW_HISTORY, BooleanUtils.toStringTrueFalse(false));
        String controllerPath = AppointmentConstants.REGISTRATION_WINDOWS_CONTROLLER_PATH;

        try {
            String dialog = AppointmentConstants.Registration_Windows_ConfirmBreak_Dialog;
            if (!hasDialogBeenDisplayed(dialog, uifForm)) {
                AppointmentWindowWrapper window = _getSelectedWindow(uifForm, "Break Appointments");
                uifForm.setSelectedAppointmentWindow(window);

                //redirect back to client to display lightbox
                return showDialog(dialog, uifForm, request, response);
            }

            boolean confirmDelete = getBooleanDialogResponse(dialog, uifForm, request, response);
            uifForm.getDialogManager().resetDialogStatus(dialog);
            if (!confirmDelete) {
                return super.performRedirect(uifForm, controllerPath, urlParameters);
            }
        } catch (Exception e) {
            //TODO: log exception
            return getUIFModelAndView(uifForm);
        }

        AppointmentWindowWrapper window = uifForm.getSelectedAppointmentWindow();
        if (window != null) {
            //Delete the appointment slots and appointments for this window
            StatusInfo status = getAppointmentService().deleteAppointmentSlotsByWindowCascading(window.getAppointmentWindowInfo().getId(), new ContextInfo());
            if (status.getIsSuccess()) {
                //GlobalVariables.getMessageMap().addGrowlMessage( "", AppointmentConstants.APPOINTMENT_MSG_INFO_BREAK_APPOINTMENTS_SUCCESS);
                urlParameters.put("growlMessage", AppointmentConstants.APPOINTMENT_MSG_INFO_BREAK_APPOINTMENTS_SUCCESS);

                //Update window state back to draft
                window.getAppointmentWindowInfo().setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY);
            } else {
                //There was an error
                //GlobalVariables.getMessageMap().addGrowlMessage( "", AppointmentConstants.APPOINTMENT_MSG_ERROR_BREAK_APPOINTMENTS_FAILURE, status.getMessage());
                urlParameters.put("growlMessage", AppointmentConstants.APPOINTMENT_MSG_ERROR_BREAK_APPOINTMENTS_FAILURE);
                urlParameters.put("windowName", status.getMessage());
            }
        }

        return super.performRedirect(uifForm, controllerPath, urlParameters);
    }

    @RequestMapping(params = "methodToCall=deleteLineThroughDialog")
    public ModelAndView deleteLineWithDialog(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm uifForm, @SuppressWarnings("unused") BindingResult result, @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "refreshAfterDialog");
        urlParameters.put(UifParameters.VIEW_ID, AppointmentConstants.REGISTRATION_WINDOWS_MANAGEMENT_VIEW);
        urlParameters.put(UifParameters.PAGE_ID, AppointmentConstants.REGISTRATION_WINDOWS_EDIT_PAGE);
        urlParameters.put("periodId", uifForm.getPeriodId());
        urlParameters.put("termType", uifForm.getTermType());
        urlParameters.put("termYear", uifForm.getTermYear());
        urlParameters.put(UifConstants.UrlParams.SHOW_HISTORY, BooleanUtils.toStringTrueFalse(false));
        String controllerPath = AppointmentConstants.REGISTRATION_WINDOWS_CONTROLLER_PATH;

        try {
            String dialog = AppointmentConstants.Registration_Windows_ConfirmDelete_Dialog;
            if (!hasDialogBeenDisplayed(dialog, uifForm)) {
                AppointmentWindowWrapper window = _getSelectedWindow(uifForm, "Delete a Window");
                uifForm.setSelectedAppointmentWindow(window);

                //redirect back to client to display lightbox
                return showDialog(dialog, uifForm, request, response);
            }

            boolean confirmDelete = getBooleanDialogResponse(dialog, uifForm, request, response);
            uifForm.getDialogManager().resetDialogStatus(dialog);
            if (!confirmDelete) {
                return super.performRedirect(uifForm, controllerPath, urlParameters);
            }
        } catch (Exception e) {
            //TODO: log exception
            return getUIFModelAndView(uifForm);
        }
        try {
            AppointmentWindowWrapper window = uifForm.getSelectedAppointmentWindow();
            if (window != null) {

                if (AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ASSIGNED_KEY.equals(window.getAppointmentWindowInfo().getStateKey())) {
                    //need to break Assignment first
                    //Delete the appointment slots and appointments for this window
                    StatusInfo status = getAppointmentService().deleteAppointmentSlotsByWindowCascading(window.getAppointmentWindowInfo().getId(), new ContextInfo());
                    if (status.getIsSuccess()) {
                        getAppointmentService().deleteAppointmentWindowCascading(window.getId(), new ContextInfo());
                        uifForm.removeSelectedAppointmentWindow(window);
                        urlParameters.put("growlMessage", AppointmentConstants.APPOINTMENT_MSG_INFO_DELETED);
                        urlParameters.put("windowName", window.getWindowName());

                        return super.performRedirect(uifForm, controllerPath, urlParameters);
                    } else {
                        //There was an error
                        urlParameters.put("growlMessage", AppointmentConstants.APPOINTMENT_MSG_ERROR_BREAK_APPOINTMENTS_FAILURE);
                        urlParameters.put("windowName", status.getMessage());

                        return super.performRedirect(uifForm, controllerPath, urlParameters);
                    }
                } else {
                    getAppointmentService().deleteAppointmentWindowCascading(window.getId(), new ContextInfo());
                    uifForm.removeSelectedAppointmentWindow(window);
                    urlParameters.put("growlMessage", AppointmentConstants.APPOINTMENT_MSG_INFO_DELETED);
                    urlParameters.put("windowName", window.getWindowName());

                    return super.performRedirect(uifForm, controllerPath, urlParameters);
                }
            } else {
                //TODO: log window == null message
                return super.performRedirect(uifForm, controllerPath, urlParameters);
            }
        } catch (Exception e) {
            //TODO: log exception
            return super.performRedirect(uifForm, controllerPath, urlParameters);
        }
    }

    @RequestMapping(params = "methodToCall=refreshAfterDialog")
    public ModelAndView refreshAfterDialog(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm uifForm,
                                           @SuppressWarnings("unused") BindingResult result,
                                           @SuppressWarnings("unused") HttpServletRequest request,
                                           @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        String windowName = request.getParameter("windowName");
        String growlMessage = request.getParameter("growlMessage");
        if (growlMessage != null) {
            if (windowName != null) {
                GlobalVariables.getMessageMap().addGrowlMessage("", growlMessage, windowName);
            } else {
                GlobalVariables.getMessageMap().addGrowlMessage("", growlMessage);
            }
        }

        getViewHelperService(uifForm).searchForTerm(uifForm.getTermType(), uifForm.getTermYear(), uifForm);

        if (!uifForm.isShowAddWindows()) {
            uifForm.setShowAddWindows(true);
        }
        uifForm = populatePeriodCollections(uifForm);

        return getUIFModelAndView(uifForm);
    }

    private RegistrationWindowsManagementForm populatePeriodCollections(RegistrationWindowsManagementForm uifForm)
            throws Exception {
        String periodId = uifForm.getPeriodId();
        String periodInfoDetails = "";

        //Clear all the windows
        uifForm.getAppointmentWindows().clear();

        if (periodId == null || periodId.isEmpty()) {
            uifForm.setPeriodInfoDetails(periodInfoDetails);
            uifForm.setShowAddWindows(false);
            uifForm.getAppointmentWindows().clear();
        } else if (!periodId.isEmpty() && !periodId.equals("all")) {

            //Lookup the period information
            KeyDateInfo period = getAcalService().getKeyDate(periodId, ContextUtils.getContextInfo());

            //pull in the windows for this period
            List<KeyDateInfo> periods = new ArrayList<KeyDateInfo>();
            periods.add(period);
            _loadWindowsInfoForm(periods, uifForm);

            // display the period start/end time in details and the period name in the AddLine
            AppointmentWindowWrapper addLine = (AppointmentWindowWrapper) uifForm.getNewCollectionLines().get("appointmentWindows");
            if (addLine == null) {
                addLine = new AppointmentWindowWrapper();
                uifForm.getNewCollectionLines().put("appointmentWindows", addLine);
            }

            if (period.getName() != null) {
                periodInfoDetails = period.getName() + " Start Date: " + _getSimpleDate(period.getStartDate()) + "<br>"
                        + period.getName() + " End Date: " + _getSimpleDate(period.getEndDate());
                uifForm.setPeriodName(period.getName());
                uifForm.setPeriodId(period.getId());
                addLine.setPeriodName(period.getName());
                addLine.setPeriodKey(period.getId());
            } else {
                periodInfoDetails = period.getId() + " Start Date: " + _getSimpleDate(period.getStartDate()) + "<br>"
                        + period.getId() + " End Date: " + _getSimpleDate(period.getEndDate());
                uifForm.setPeriodName(period.getId());
                uifForm.setPeriodId(period.getId());
                addLine.setPeriodName(period.getId());
                addLine.setPeriodKey(period.getId());
            }
            uifForm.setPeriodInfoDetails(periodInfoDetails);

            //TODO: pull out all windows for that period and add to the collection
        } else if (periodId.equals("all")) {
            List<KeyDateInfo> periodMilestones = uifForm.getPeriodMilestones();
            if (periodMilestones.isEmpty()) {
                TermInfo term = uifForm.getTermInfo();
                if (term.getId() != null && !term.getId().isEmpty()) {
                    getViewHelperService(uifForm).loadPeriods(term.getId(), uifForm);
                    periodMilestones = uifForm.getPeriodMilestones();
                }
            }
            for (KeyDateInfo period : periodMilestones) {

                if (period.getName() != null) {
                    periodInfoDetails = periodInfoDetails.concat(period.getName() + " Start Date: " + _getSimpleDate(period.getStartDate()) + "<br>"
                            + period.getName() + " End Date: " + _getSimpleDate(period.getEndDate()) + "<br>");
                } else {
                    periodInfoDetails = periodInfoDetails.concat(period.getId() + " Start Date: " + period.getStartDate() + "<br>"
                            + period.getId() + " End Date: " + _getSimpleDate(period.getEndDate()) + "<br>");
                }
            }
            uifForm.setPeriodInfoDetails(periodInfoDetails);
            _loadWindowsInfoForm(periodMilestones, uifForm);
        }

        return uifForm;
    }

    // this is a more generic way to get a selected window when the form contains more than one collection
    private AppointmentWindowWrapper _getSelectedWindow(RegistrationWindowsManagementForm theForm, String actionLink) {
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

        return (AppointmentWindowWrapper) window;
    }

    private String _getSimpleDate(Date date) {
        if (date == null) {
            return "";
        }
        return DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(date);
    }

    private void _loadWindowsInfoForm(List<KeyDateInfo> periods, RegistrationWindowsManagementForm form) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        for (KeyDateInfo period : periods) {
            List<AppointmentWindowInfo> windows = getAppointmentService().getAppointmentWindowsByPeriod(period.getId(), new ContextInfo());
            if (windows != null && windows.size() > 0) {
                for (AppointmentWindowInfo window : windows) {

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
                    if (!AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY.equals(window.getTypeKey())) {
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
        if (date == null) {
            return null;
        }
        return DateFormatters.AM_PM_TIME_FORMATTER.format(date);
    }

    private String _parseTime(Date date) {
        if (date == null) {
            return null;
        }
        return DateFormatters.HOUR_MINUTE_TIME_FORMATTER.format(date);
    }

    public AppointmentViewHelperService getViewHelperService(RegistrationWindowsManagementForm appointmentForm) {
        if (viewHelperService == null) {
            if (appointmentForm.getView().getViewHelperServiceClass() != null) {
                viewHelperService = (AppointmentViewHelperService) appointmentForm.getView().getViewHelperService();
            } else {
                viewHelperService = (AppointmentViewHelperService) appointmentForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }

    public AcademicCalendarService getAcalService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }


    public AppointmentService getAppointmentService() {
        if (appointmentService == null) {
            appointmentService = (AppointmentService) GlobalResourceLoader.getService(new QName(AppointmentServiceConstants.NAMESPACE, AppointmentServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return appointmentService;
    }

    public PopulationService getPopulationService() {
        if (populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationMockService")); // TODO: Refactor later with real PopService
        }
        return populationService;
    }

}
