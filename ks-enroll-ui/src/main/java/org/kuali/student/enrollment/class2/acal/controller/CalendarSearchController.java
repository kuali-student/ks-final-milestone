/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package org.kuali.student.enrollment.class2.acal.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.enrollment.class2.acal.dto.AcalSearchResult;
import org.kuali.student.enrollment.class2.acal.form.CalendarSearchForm;
import org.kuali.student.enrollment.class2.acal.service.CalendarSearchViewHelperService;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.enrollment.common.util.EnrollConstants;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.acal.service.facade.AcademicCalendarServiceFacade;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class handles all the request for Searching Holiday calender, Academic Calendars and Academic terms.
 * This handles requests from CalendarSearchView for different calendars and terms.
 * This controller is mapped to the view defined in <code>CalendarSearchView.xml</code>
 *
 * @author Kuali Student Team
 *
 */

@Controller
@RequestMapping(value = "/calendarSearch")
public class CalendarSearchController  extends UifControllerBase {

    private transient AcademicCalendarService acalService;
    private transient AcademicCalendarServiceFacade acalServiceFacade;
    private ContextInfo contextInfo;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CalendarSearchForm();
    }


    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form,
                              HttpServletRequest request, HttpServletResponse response) {
        CalendarSearchForm calendarSearchForm = (CalendarSearchForm)form;

        String calendarSearchType = request.getParameter(CalendarConstants.CALENDAR_SEARCH_TYPE);
        if (null != calendarSearchType) {
            calendarSearchForm.setCalendarType(calendarSearchType);
        }


        // Retrieve growl message key
        String growlMessageKey = request.getParameter(EnrollConstants.GROWL_MESSAGE);

        // Check if growl message key is present
        if(growlMessageKey!=null){
            // If message key is present gather parameters and repopulate growl message
            // This is used when page is a redirect from another as messages are lost when redirecting
            String growlTitle = request.getParameter(EnrollConstants.GROWL_TITLE);
            String temp = request.getParameter(EnrollConstants.GROWL_MESSAGE_PARAMS);
            String[] growlMessageParams;

            // Message paramters are passed as csv to allow for multiple paramters to be pasted at once.
            // Check that a paramters string was found
            if(temp!=null){
                // If parameters' string found seperate into an array of values from csv
                growlMessageParams = temp.split(",");
            }
            else{
                // If no parameters' string found initialize empty string
                growlMessageParams=new String[0];
            }
            // After gathering and decoding message add it to the growl message map
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, growlMessageKey, growlMessageParams);
        }

        return super.start(form, request, response);
    }

    /**
     * Method used to search atps
     */
    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (searchForm.getYear() != null && !searchForm.getYear().isEmpty()) {
            try {
                Integer.parseInt(searchForm.getYear());
            } catch (NumberFormatException e) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: invalid year entered.");
                resetForm(searchForm);
                return getUIFModelAndView(searchForm);
            }
        }

        searchForm.setClickSearchButton(true);
        //if no search criteria was set, it means the search method is called from redirection. Then retrieve search criteria from http session
        HttpSession session = request.getSession(true);
        if ((searchForm.getCalendarType()==null || searchForm.getCalendarType().isEmpty()) &&
                (searchForm.getName()==null || searchForm.getName().isEmpty()) &&
                (searchForm.getYear()==null || searchForm.getYear().isEmpty())) {
            searchForm.setCalendarType((String)session.getAttribute(CalendarConstants.SESSION_CALENDAR_SEARCH_TYPE));
            searchForm.setName((String)session.getAttribute(CalendarConstants.SESSION_CALENDAR_SEARCH_NAME));
            searchForm.setYear((String)session.getAttribute(CalendarConstants.SESSION_CALENDAR_SEARCH_YEAR));
        }

        resetForm(searchForm);
        CalendarSearchViewHelperService viewHelperService = (CalendarSearchViewHelperService) KSControllerHelper.getViewHelperService(searchForm);

        if(searchForm.getCalendarType() != null){
            if (CalendarConstants.HOLIDAYCALENDER.equals(searchForm.getCalendarType()) ||
                    CalendarConstants.ACADEMICCALENDER.equals(searchForm.getCalendarType()) ||
                    CalendarConstants.TERM.equals(searchForm.getCalendarType()) ||
                    CalendarConstants.SUBTERM.equals(searchForm.getCalendarType())){

                List<AcalSearchResult> acalResults = viewHelperService.searchForCalendars(searchForm.getName(), searchForm.getYear(), searchForm.getCalendarType(), getContextInfo());
                searchForm.setSearchResults(acalResults);

            } else {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: invalid calendar type.");
            }
        }

        //set search criteria into http session
        session.setAttribute(CalendarConstants.SESSION_CALENDAR_SEARCH_TYPE, searchForm.getCalendarType());
        session.setAttribute(CalendarConstants.SESSION_CALENDAR_SEARCH_NAME, searchForm.getName());
        session.setAttribute(CalendarConstants.SESSION_CALENDAR_SEARCH_YEAR, searchForm.getYear());
        // Cannot just put null as an argument, since two matching method signatures now exist:
        // getUIFModelAndView(UifFormBase form, String pageId)
        // and
        // getUIFModelAndView(UifFormBase form, Map<String, Object> additionalViewAttributes)
        //
        String pageId = null;
        return getUIFModelAndView(searchForm, pageId);
    }

    /**
     * This is called when the user clicked on Search button in the Calendar Search page.
     *
     * @param searchForm
     * @param result
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(params = "methodToCall=view")
    public ModelAndView view(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        AcalSearchResult atp = getSelectedAtp(searchForm, "view");
        Properties urlParameters;
        String controllerPath;
        CalendarSearchViewHelperService viewHelperService = (CalendarSearchViewHelperService) KSControllerHelper.getViewHelperService(searchForm);

        if(CalendarConstants.HOLIDAYCALENDER.equals(atp.getAcalSearchTypeKey())){
            urlParameters = viewHelperService.buildHCalURLParameters(atp, CalendarConstants.HC_VIEW_METHOD, true, getContextInfo());
            urlParameters.put("flow", searchForm.getFlowKey());
            controllerPath = CalendarConstants.HCAL_CONTROLLER_PATH;
        } else if(CalendarConstants.ACADEMICCALENDER.equals(atp.getAcalSearchTypeKey())) {
            urlParameters = viewHelperService.buildACalURLParameters(atp, CalendarConstants.AC_VIEW_METHOD, true, getContextInfo());
            urlParameters.put("flow", searchForm.getFlowKey());
            controllerPath = CalendarConstants.ACAL_CONTROLLER_PATH;
        } else if(CalendarConstants.TERM.equals(atp.getAcalSearchTypeKey()) || CalendarConstants.SUBTERM.equals(atp.getAcalSearchTypeKey())){
            urlParameters = viewHelperService.buildTermURLParameters(atp, CalendarConstants.AC_VIEW_METHOD, true, getContextInfo());
            urlParameters.put("flow", searchForm.getFlowKey());
            controllerPath = CalendarConstants.ACAL_CONTROLLER_PATH;
        } else {
            throw new RuntimeException("Invalid calendar type. This search supports Acal/HCal/Term/Subterm only");
        }

        return super.performRedirect(searchForm,controllerPath, urlParameters);
    }

    /**
     * This is called when the user clicked on Edit button in the Calendar Search page.
     *
     * @param searchForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        AcalSearchResult atp = getSelectedAtp(searchForm, "edit");

        Properties urlParameters;
        String controllerPath;
        CalendarSearchViewHelperService viewHelperService = (CalendarSearchViewHelperService) KSControllerHelper.getViewHelperService(searchForm);

        if(CalendarConstants.HOLIDAYCALENDER.equals(atp.getAcalSearchTypeKey())){
            urlParameters = viewHelperService.buildHCalURLParameters(atp, CalendarConstants.HC_EDIT_METHOD, false, getContextInfo());
            urlParameters.put("flow", searchForm.getFlowKey());
            controllerPath = CalendarConstants.HCAL_CONTROLLER_PATH;
        } else if(CalendarConstants.ACADEMICCALENDER.equals(atp.getAcalSearchTypeKey())) {
            urlParameters = viewHelperService.buildACalURLParameters(atp, CalendarConstants.AC_EDIT_METHOD, false, getContextInfo());
            urlParameters.put("flow", searchForm.getFlowKey());
            controllerPath = CalendarConstants.ACAL_CONTROLLER_PATH;
        } else if(CalendarConstants.TERM.equals(atp.getAcalSearchTypeKey()) || CalendarConstants.SUBTERM.equals(atp.getAcalSearchTypeKey())){
            urlParameters = viewHelperService.buildTermURLParameters(atp, CalendarConstants.AC_EDIT_METHOD, false, getContextInfo());
            urlParameters.put("flow", searchForm.getFlowKey());
            controllerPath = CalendarConstants.ACAL_CONTROLLER_PATH;
        } else {
            throw new RuntimeException("Invalid calendar type. This search supports Acal/HCal/Term only");
        }

        return super.performRedirect(searchForm,controllerPath, urlParameters);

    }

    /**
     * This is called when the user clicked on Copy button in the Calendar Search page with Holiday calendar selected.
     *
     * @param searchForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=copy")
    public ModelAndView copy(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        AcalSearchResult atp = getSelectedAtp(searchForm, "copy");

        Properties urlParameters;
        String controllerPath;
        CalendarSearchViewHelperService viewHelperService = (CalendarSearchViewHelperService) KSControllerHelper.getViewHelperService(searchForm);

        if(CalendarConstants.HOLIDAYCALENDER.equals(atp.getAcalSearchTypeKey())){
            controllerPath = CalendarConstants.HCAL_CONTROLLER_PATH;
            urlParameters = viewHelperService.buildHCalURLParameters(atp, CalendarConstants.HC_COPY_METHOD, false, getContextInfo());
            urlParameters.put("flow", searchForm.getFlowKey());
        }else if(CalendarConstants.ACADEMICCALENDER.equals(atp.getAcalSearchTypeKey())){
            urlParameters = viewHelperService.buildACalURLParameters(atp, CalendarConstants.AC_COPY_METHOD, false, getContextInfo());
            controllerPath = CalendarConstants.ACAL_CONTROLLER_PATH;
            urlParameters.put("flow", searchForm.getFlowKey());
        } else {
            throw new RuntimeException("Invalid calendar type. This search supports Acal and HCal only");
        }

        return super.performRedirect(searchForm,controllerPath, urlParameters);

    }

    @RequestMapping(params = "methodToCall=createBlankAcademicCalendar")
    public ModelAndView createBlankAcademicCalendar(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                                    HttpServletRequest request, HttpServletResponse response) throws Exception {

        Properties urlParameters = new Properties();
        urlParameters.put(UifParameters.VIEW_ID, CalendarConstants.ACAL_VIEW);
        // UrlParams.SHOW_HISTORY and SHOW_HOME no longer exist
        // https://fisheye.kuali.org/changelog/rice?cs=39034
        // TODO KSENROLL-8469
        //urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
        urlParameters.put("flow", searchForm.getFlowKey());
        urlParameters.put(CalendarConstants.PAGE_ID,CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "startNew");
        String controllerPath = CalendarConstants.ACAL_CONTROLLER_PATH;
        return super.performRedirect(searchForm,controllerPath, urlParameters);
    }

    @RequestMapping(params = "methodToCall=createBlankHolidayCalendar")
    public ModelAndView createBlankHolidayCalendar(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        Properties urlParameters = new Properties();
        urlParameters.put(UifParameters.VIEW_ID, CalendarConstants.HOLIDAYCALENDAR_FLOWVIEW);
        // UrlParams.SHOW_HISTORY and SHOW_HOME no longer exist
        // https://fisheye.kuali.org/changelog/rice?cs=39034
        // TODO KSENROLL-8469
        //urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(true));
        urlParameters.put("flow", searchForm.getFlowKey());
        urlParameters.put(CalendarConstants.PAGE_ID,CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "startNew");
        String controllerPath = CalendarConstants.HCAL_CONTROLLER_PATH;
        return super.performRedirect(searchForm,controllerPath, urlParameters);
    }

    /**
     * This is called when the user clicked on Delete button in the Calendar Search page with Holiday calendar selected.
     *
     * @param searchForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dialog = CalendarConstants.SEARCH_DELETE_CONFIRMATION_DIALOG;
        if (!hasDialogBeenDisplayed(dialog, searchForm)) {
            searchForm.setSelectedCollectionPath(searchForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH));
            searchForm.setSelectedLineIndex(searchForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
            //redirect back to client to display lightbox
            return showDialog(dialog, searchForm, request, response);
        }else{
            if(hasDialogBeenAnswered(dialog,searchForm)){
                boolean confirmDelete = getBooleanDialogResponse(dialog, searchForm, request, response);
                searchForm.getDialogManager().resetDialogStatus(dialog);
                if(!confirmDelete){
                    return getUIFModelAndView(searchForm);
                }
            } else {
                searchForm.setSelectedCollectionPath(searchForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH));
                searchForm.setSelectedLineIndex(searchForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
                //redirect back to client to display lightbox
                return showDialog(dialog, searchForm, request, response);
            }
        }
        searchForm.getActionParameters().put(UifParameters.SELLECTED_COLLECTION_PATH,searchForm.getSelectedCollectionPath());
        searchForm.getActionParameters().put(UifParameters.SELECTED_LINE_INDEX,searchForm.getSelectedLineIndex());

        AcalSearchResult atp = getSelectedAtp(searchForm, "delete");

        if(CalendarConstants.HOLIDAYCALENDER.equals(atp.getAcalSearchTypeKey())){
            StatusInfo status = getAcademicCalendarService().deleteHolidayCalendar(atp.getId(), getContextInfo());
            if (status.getIsSuccess()){
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, CalendarConstants.MessageKeys.INFO_SEARCH_DELETE_SUCCESS, "Holiday calendar", atp.getName());
                searchForm.getSearchResults().remove(atp);
            } else{
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, status.getMessage());
            }
        } else if(CalendarConstants.ACADEMICCALENDER.equals(atp.getAcalSearchTypeKey())) {
            StatusInfo status = getAcalServiceFacade().deleteCalendarCascaded(atp.getId(), getContextInfo());
            if (status.getIsSuccess()){
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS,CalendarConstants.MessageKeys.INFO_SEARCH_DELETE_SUCCESS, "Academic calendar", atp.getName());
                searchForm.getSearchResults().remove(atp);
            } else{
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, status.getMessage());
            }
        } else if(CalendarConstants.TERM.equals(atp.getAcalSearchTypeKey())){
            StatusInfo status = getAcalServiceFacade().deleteTermCascaded(atp.getId(), getContextInfo());
            if (status.getIsSuccess()){
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, CalendarConstants.MessageKeys.INFO_SEARCH_DELETE_SUCCESS, "Academic term", atp.getName());
                searchForm.getSearchResults().remove(atp);
            } else{
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, status.getMessage());
            }
        } else {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: invalid calendar type.");
        }

        return getUIFModelAndView(searchForm);

    }

    private void resetForm(CalendarSearchForm searchForm) {
        searchForm.setSearchResults(new ArrayList<AcalSearchResult>());
    }

    private AcalSearchResult getSelectedAtp(CalendarSearchForm searchForm, String actionLink){
        String selectedCollectionPath = searchForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for " + actionLink);
        }

        int selectedLineIndex = KSControllerHelper.getSelectedCollectionLineIndex(searchForm);

        List<AcalSearchResult> list = ObjectPropertyUtils.getPropertyValue(searchForm, selectedCollectionPath);
        AcalSearchResult atp = list.get(selectedLineIndex);

        return atp;
    }

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
        }
        return contextInfo;
    }

    protected AcademicCalendarService getAcademicCalendarService(){
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

    public AcademicCalendarServiceFacade getAcalServiceFacade() {
        if(acalServiceFacade == null) {
            acalServiceFacade = (AcademicCalendarServiceFacade) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.FACADE_NAMESPACE, AcademicCalendarServiceConstants.FACADE_SERVICE_NAME_LOCAL_PART));
        }
        return this.acalServiceFacade;
    }
}
