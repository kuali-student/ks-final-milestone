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


import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.KsUifFormBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.class2.acal.dto.HolidayWrapper;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.HolidayCalendarViewHelperService;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.enrollment.class2.acal.util.CommonUtils;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * This class handles all the requests for Managing HolidayCalendar, such as, view, edit, copy, delete, etc. This controller is mapped to the view defined in <code>HolidayCalendarView.xml</code>
 * and its subviews <code>HolidayCalendarEditPage.xml</code>, <code>HolidayCalendarCopyPage.xml</code>, and <code>HolidayCalendarViewPage.xml</code>
 *
 * @author Kuali Student Team
 *
 */

@Controller
@RequestMapping(value = "/holidayCalendar")
public class HolidayCalendarController extends UifControllerBase {

    @Override
    protected KsUifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new HolidayCalendarForm();
    }

    /**
     * This is the add action when the add button is pushed in the Holiday calendar edit/create page.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */

    @Override
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=addLine")
    public ModelAndView addLine(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        HolidayCalendarForm hcForm = (HolidayCalendarForm) form;
        List<HolidayWrapper> holidays = hcForm.getHolidays();
        Map<String, Object> newCollectionLines = form.getNewCollectionLines();

        if(newCollectionLines != null && !newCollectionLines.isEmpty()){
            for (Map.Entry<String, Object> entry : newCollectionLines.entrySet()){
                HolidayWrapper newHoliday = (HolidayWrapper)entry.getValue();
                if(checkHoliday(newHoliday)){
                    if(holidays != null && !holidays.isEmpty()){
                        for(HolidayWrapper holiday : holidays){
                            boolean duplicated = isDuplicateHoliday(newHoliday, holiday);
                            if(duplicated){
                                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: The holiday being added is already in the collection.");
                                return getUIFModelAndView(form);
                            }
                        }
                    }
                }
                else {
                    return getUIFModelAndView(form);
                }
            }
        }

        return super.addLine(form, result, request, response);
    }

    private boolean checkHoliday(HolidayWrapper holiday) {
        boolean valid = true;
//        Date startDate = holiday.getStartDate();
//        Date endDate = holiday.getEndDate();
//        String startTime = holiday.getStartTime();
//        String endTime = holiday.getEndTime();
//        HolidayInfo holidayInfo = holiday.getHolidayInfo();
//
//        if (endDate == null)  {
//            holidayInfo.setIsDateRange(false);
//
//            if(StringUtils.isBlank(startTime)){
//                holidayInfo.setIsAllDay(true);
//            }
//        }
//        else {
//            int timeDiff = startDate.compareTo(endDate);
//            if(timeDiff > 0) {
//                //TODO:change to  putError, when error reload fixed
//                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: The adding holiday start date should not be later than the end date.");
//                return false;
//            }else if (timeDiff == 0 ) {
//                holidayInfo.setIsDateRange(false);
//            }else {
//                holidayInfo.setIsDateRange(true);
//            }
//
//            if (StringUtils.isBlank(startTime) & StringUtils.isBlank(endTime)) {
//                holidayInfo.setIsAllDay(true);
//            }else if(StringUtils.isNotEmpty(startTime)){
//                holidayInfo.setIsAllDay(false);
//            }
//        }

        return valid;
    }

    private boolean isDuplicateHoliday(HolidayWrapper newHoliday, HolidayWrapper sourceHoliday){
        return (newHoliday.getTypeKey().equals(sourceHoliday.getTypeKey()));
    }

    /**
     * This is starting page before we enter any Holiday management activities.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        HolidayCalendarForm hcForm = (HolidayCalendarForm) form;

        String hcId = request.getParameter(CalendarConstants.CALENDAR_ID);

        if ((hcId != null) && !hcId.trim().isEmpty()) {
            String pageId = request.getParameter("pageId");
            if (CalendarConstants.HOLIDAYCALENDAR_VIEWPAGE.equals(pageId)) {
                hcForm.setViewTypeName(UifConstants.ViewType.INQUIRY);
            }

            try {
                getHolidayCalendar(hcId, hcForm);
            } catch (Exception ex) {
                 throw new RuntimeException("unable to getHolidayCalendar ");
            }
        }

        return super.start(form, result, request, response);
    }

    /**
     * This is called when the user clicked on Create Holiday Calendar link.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=startNew")
    public ModelAndView startNew( @ModelAttribute("KualiForm") HolidayCalendarForm form, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) {
        HolidayCalendarInfo hcInfo = null;

        try {
            String calendarId = request.getParameter(CalendarConstants.CALENDAR_ID);
            if (calendarId == null || calendarId.trim().isEmpty()) {
                hcInfo = getHolidayCalendarFormHelper(form).getNewestHolidayCalendar();
            }
            else {
                hcInfo = getHolidayCalendarFormHelper(form).getHolidayCalendar(calendarId);
            }
        }
        catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Unexpected error; could not get holiday to copy: " + e.getMessage());
        }

        form.setHolidayCalendarInfo(hcInfo);

        if (null != hcInfo) {
            // do some calculations on probable values for the new calendar
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR);
            cal.setTime(hcInfo.getEndDate());
            int endYear = cal.get(Calendar.YEAR);
            if (endYear == currentYear) {
                cal.setTime(hcInfo.getStartDate());
                int startYear = cal.get(Calendar.YEAR);
                StringBuilder calName = new StringBuilder();
                if (startYear < endYear) {
                    calName.append(++startYear);
                    calName.append("-");
                }
                calName.append(++endYear);
                calName.append(" Holiday Calendar");
                form.setNewCalendarName(calName.toString());
                /* force user to enter dates
                cal.add(Calendar.YEAR, 1);
                form.setNewCalendarEndDate(cal.getTime());
                cal.setTime(hcInfo.getStartDate());
                cal.add(Calendar.YEAR, 1);
                form.setNewCalendarStartDate(cal.getTime());
                */
            }
        }

        return super.start(form, result, request, response);
    }

    /**
     * This is called when the user clicked on Copy button in the Calendar Search page with Holiday calendar selected.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=copyForNew")
    public ModelAndView copyForNew( @ModelAttribute("KualiForm") HolidayCalendarForm form, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) {
        HolidayCalendarInfo hcInfo = null;

        try {
            String calendarId = request.getParameter(CalendarConstants.CALENDAR_ID);
            if (calendarId == null || calendarId.trim().isEmpty()) {
                hcInfo = getHolidayCalendarFormHelper(form).getNewestHolidayCalendar();
            }
            else {
                hcInfo = getHolidayCalendarFormHelper(form).getHolidayCalendar(calendarId);
            }
        }
        catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Unexpected error; could not get holiday to copy: " + e.getMessage());
        }

        form.setHolidayCalendarInfo(hcInfo);

        return toCopy(form, result, request, response);
    }

    /**
     * This is called when the user clicked on Copy Holiday Calendar in the Calendar copy page.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=copy")
    public ModelAndView copy( @ModelAttribute("KualiForm") HolidayCalendarForm form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        restoreForm(form);
        if ((null == form.getHolidayCalendarInfo()) || (null == form.getHolidayCalendarInfo().getId())) {
            // this should never happen
            GlobalVariables.getMessageMap().putError( KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,
                                                      "Unexpected error; the holiday to copy has been misplaced.");
            return getUIFModelAndView(form);
        }

        List<HolidayWrapper> newHolidays;
        try {
            newHolidays = getHolidayCalendarFormHelper(form).getHolidayWrappersForHolidayCalendar(
                    form.getHolidayCalendarInfo().getId());
        }
        catch (Exception x) {
            throw new RuntimeException(x);
        }

        for (HolidayWrapper holidayWrapper : newHolidays) {
            // decision #14, copy over all information (types, dates, times, flags, etc) for Holidays from the source calendar
            holidayWrapper.getHolidayInfo().setId(null); // else the old rcd will be updated
        }

        form.setOrgHcId(form.getHolidayCalendarInfo().getId());
        form.getHolidayCalendarInfo().setId(null);
        form.getHolidayCalendarInfo().setName(form.getNewCalendarName());
        form.getHolidayCalendarInfo().setStartDate(form.getNewCalendarStartDate());
        form.getHolidayCalendarInfo().setEndDate(form.getNewCalendarEndDate());
        form.getHolidayCalendarInfo().setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        // after changing the state in the info back to the default, reset the isOfficialCalendar flag on the form
        form.setOfficialCalendar(false);
        form.getHolidayCalendarInfo().setDescr(CommonUtils.buildDesc(form.getNewCalendarName()));
        form.setHolidays(newHolidays);
        form.setHcId(null);

        return getUIFModelAndView(form, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
    }

    /**
     * redirect to search Calendar page
     * This is called when the user clicked on 'Choose a Different Calendar' action link in the create Holiday Calendar page.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") HolidayCalendarForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        String controllerPath = CalendarConstants.CALENDAR_SEARCH_CONTROLLER_PATH;
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        urlParameters.put(UifParameters.VIEW_ID, CalendarConstants.CALENDAR_SEARCH_VIEW);
        urlParameters.put(UifConstants.UrlParams.SHOW_HISTORY, BooleanUtils.toStringTrueFalse(false));
        urlParameters.put(CalendarConstants.CALENDAR_SEARCH_TYPE, CalendarConstants.HOLIDAYCALENDER);
        return super.performRedirect(form,controllerPath, urlParameters);
    }

    /**
     * This is called when the user clicked on Start a blank calendar instead? action link in the create Holiday Calendar page.
     *
     * @param hcForm
     * @param result
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(params = "methodToCall=toCreate")
    public ModelAndView toCreate(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){
        hcForm.setHcId(null);
        hcForm.setHolidayCalendarInfo( new HolidayCalendarInfo());
        hcForm.setHolidays(new ArrayList<HolidayWrapper>());
        return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
    }

    private void restoreForm(HolidayCalendarForm hcForm){
        HolidayCalendarInfo hcalInfo = hcForm.getHolidayCalendarInfo();

        if (StringUtils.isBlank(hcalInfo.getId()) && StringUtils.isNotBlank(hcForm.getOrgHcId())){
            try{
                getHolidayCalendar(hcForm.getOrgHcId(), hcForm);
                hcForm.setHcId(hcForm.getOrgHcId());
                hcForm.setOrgHcId(null);
            } catch (Exception ex) {
                 throw new RuntimeException("unable to getHolidayCalendar");
            }
        }
    }

    @RequestMapping(params = "methodToCall=toEdit")
    public ModelAndView toEdit(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){
        restoreForm(hcForm);
        return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
    }

    @RequestMapping(params = "methodToCall=toCopy")
    public ModelAndView toCopy(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){
        hcForm.setNewCalendarName(null);
        hcForm.setNewCalendarStartDate(null);
        hcForm.setNewCalendarEndDate(null);
        hcForm.setNewCalendar(true);
        return copy(hcForm, result, request, response);
    }

     /**
      * Method used to save HC
      * This is the add action when the Update Official button is pushed in the Holiday calendar edit/create page.
      *
      * @param hcForm
      * @param result
      * @param request
      * @param response
      * @return
      */
     @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        return updateHolidayCalendarForm(hcForm, CalendarConstants.MessageKeys.INFO_HOLIDAY_CALENDAR_SAVED,false);
    }

    /**
     * This is called when the user clicked on  Delete Draft in the  holidayCalendarCopyPage.
     *
     * @param hcForm
     * @param result
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dialog = CalendarConstants.HOLIDAY_DELETE_CONFIRMATION_DIALOG;
        if (!hasDialogBeenDisplayed(dialog, hcForm)) {

            //redirect back to client to display lightbox
            return showDialog(dialog, hcForm, request, response);
        }else{
            if(hasDialogBeenAnswered(dialog,hcForm)){
                boolean confirmDelete = getBooleanDialogResponse(dialog, hcForm, request, response);
                hcForm.getDialogManager().resetDialogStatus(dialog);
                if(!confirmDelete){
                    return getUIFModelAndView(hcForm);
                }
            } else {

                //redirect back to client to display lightbox
                return showDialog(dialog, hcForm, request, response);
            }
        }

        getHolidayCalendarFormHelper(hcForm).deleteHolidayCalendar(hcForm.getHolidayCalendarInfo().getId());
        Properties urlParameters = new  Properties();
        urlParameters.put("viewId", CalendarConstants.ENROLLMENT_HOME_VIEW);
        urlParameters.put("methodToCall", "start");
        urlParameters.put(UifConstants.UrlParams.SHOW_HISTORY, BooleanUtils.toStringTrueFalse(false));

        HolidayCalendarInfo hCalInfo = hcForm.getHolidayCalendarInfo();
        urlParameters.put("growlMessage", CalendarConstants.MessageKeys.INFO_HOLIDAY_CALENDAR_DELETED);
        urlParameters.put("growlMessageParam", hCalInfo.getName());

        return performRedirect(hcForm, request.getRequestURL().toString(), urlParameters);
    }

    /**
     * Method used to set HC official
     * This is called when the Make Official button is pushed in the Holiday calendar create page.
     *
     * @param hcForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=makeOfficial")
     public ModelAndView makeOfficial(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {
//         hcForm.getHolidayCalendarInfo().setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
         return updateHolidayCalendarForm(hcForm, CalendarConstants.MessageKeys.INFO_HOLIDAY_CALENDAR_OFFICIAL,true);
     }

    /**
     * This is called when the user clicked on  delete button in the Holiday calendar edit page.
     *
     * @param hcForm
     * @param result
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=deleteHoliday")
    public ModelAndView deleteHoliday(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = hcForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = hcForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("unable to determine the selected line index");
        }

        try{
          getHolidayCalendarFormHelper(hcForm).deleteHoliday(selectedLineIndex,hcForm);
        }catch (Exception e){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Error deleting holiday - " + e.getMessage());
            e.printStackTrace();
        }

        return getUIFModelAndView(hcForm);
    }

    private ModelAndView updateHolidayCalendarForm(HolidayCalendarForm hcForm, String updateMsg, boolean isSetOfficial) throws Exception {

        getHolidayCalendarFormHelper(hcForm).validateHolidayCalendar(hcForm);
        getHolidayCalendarFormHelper(hcForm).populateHolidayCalendarDefaults(hcForm);

        if (GlobalVariables.getMessageMap().getErrorCount() > 0){
           return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
        }

        if (!isValidHolidayCalendar(hcForm.getHolidayCalendarInfo())) {
           return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
        }

        // passed edits, so update the holiday calendar
        getHolidayCalendarFormHelper(hcForm).saveHolidayCalendar(hcForm,isSetOfficial);
        if (GlobalVariables.getMessageMap().hasErrors()){
            return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
        }

        HolidayCalendarInfo hCalInfo = hcForm.getHolidayCalendarInfo();
        hcForm.setAdminOrgName(getAdminOrgNameById(hCalInfo.getAdminOrgId()));
        hcForm.setStateName(getHolidayCalendarFormHelper(hcForm).getHolidayCalendarState(hCalInfo.getStateKey()));
        hcForm.setNewCalendar(false);
        hcForm.setOfficialCalendar(hCalInfo.getStateKey().equals(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_OFFICIAL_STATE_KEY));
        hcForm.setHcId(hCalInfo.getId());

        // sort holidays again, in case any were added
        Collections.sort(hcForm.getHolidays());

        GlobalVariables.getMessageMap().addGrowlMessage("", updateMsg, hCalInfo.getName());
        if (isSetOfficial) {
            return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_VIEWPAGE);
        } else {
            return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
        }
    }

    private boolean isValidHolidayCalendar(HolidayCalendarInfo hc)throws Exception {
        boolean valid = true; //CommonUtils.isValidDateRange(hc.getStartDate(),hc.getEndDate());
        Date startDate = hc.getStartDate();
        Date endDate = hc.getEndDate();

        if(startDate.after(endDate)) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: " +  hc.getName() + "start date should not be later than the end date.");
            valid = false;
        }

        return valid;
    }

    private void getHolidayCalendar(String hcId, HolidayCalendarForm hcForm) throws Exception {
        HolidayCalendarInfo hcInfo = getHolidayCalendarFormHelper(hcForm).getHolidayCalendar(hcId);
        hcForm.setHolidayCalendarInfo(hcInfo);
        hcForm.setAdminOrgName(getAdminOrgNameById(hcInfo.getAdminOrgId()));
        hcForm.setStateName(getHolidayCalendarFormHelper(hcForm).getHolidayCalendarState(hcInfo.getStateKey()));
        hcForm.setNewCalendar(false);
        hcForm.setOfficialCalendar(hcInfo.getStateKey().equals(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_OFFICIAL_STATE_KEY));
        //List<HolidayWrapper> holidays = getHolidayCalendarFormHelper(hcForm).getHolidaysForHolidayCalendar(hcForm);
        List<HolidayWrapper> holidays =
                getHolidayCalendarFormHelper(hcForm).getHolidayWrappersForHolidayCalendar(hcInfo.getId());
        Collections.sort(holidays);
        hcForm.setHolidays(holidays);
    }

    private String getAdminOrgNameById(String id){
        //TODO: hard-coded for now, going to call OrgService
        String adminOrgName = null;
        Map<String, String> allHcOrgs = new HashMap<String, String>();
        allHcOrgs.put("102", "Registrar's Office");

        if(allHcOrgs.containsKey(id)){
            adminOrgName = allHcOrgs.get(id);
        }

        return adminOrgName;
    }

    private HolidayCalendarViewHelperService getHolidayCalendarFormHelper(HolidayCalendarForm hcForm) {
        if (hcForm.getView() != null && hcForm.getView().getViewHelperServiceClass() != null){
            return (HolidayCalendarViewHelperService)hcForm.getView().getViewHelperService();
        } else {
            return (HolidayCalendarViewHelperService)hcForm.getPostedView().getViewHelperService();
        }
    }

}