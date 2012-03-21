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
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.class2.acal.dto.HolidayWrapper;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
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
 * This class implement controller for HolidayCalendar
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/holidayCalendar")
public class HolidayCalendarController extends UifControllerBase {

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new HolidayCalendarForm();
    }

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
                                //TODO:change to  putError, when error reload fixed
                                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: The adding holiday is already in the collection.");
                                return updateComponent(form, result, request, response);
                            }
                        }
                    }
                }
                else {
                    return updateComponent(form, result, request, response);
                }
            }
        }

        return super.addLine(form, result, request, response);
    }

    private boolean checkHoliday(HolidayWrapper holiday) {
        boolean valid = true;
        Date startDate = holiday.getStartDate();
        Date endDate = holiday.getEndDate();
        String startTime = holiday.getStartTime();
        String endTime = holiday.getEndTime();
        HolidayInfo holidayInfo = holiday.getHolidayInfo();

        if (endDate == null)  {
            holidayInfo.setIsDateRange(false);

            if(StringUtils.isBlank(startTime)){
                holidayInfo.setIsAllDay(true);
            }
        }
        else {
            int timeDiff = startDate.compareTo(endDate);
            if(timeDiff > 0) {
                //TODO:change to  putError, when error reload fixed
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: The adding holiday start date should not be later than the end date.");
                return false;
            }else if (timeDiff == 0 ) {
                holidayInfo.setIsDateRange(false);
            }else {
                holidayInfo.setIsDateRange(true);
            }

            if (StringUtils.isBlank(startTime) & StringUtils.isBlank(endTime)) {
                holidayInfo.setIsAllDay(true);
            }else if(StringUtils.isNotEmpty(startTime)){
                holidayInfo.setIsAllDay(false);
            }
        }

        return valid;
    }

    private boolean isDuplicateHoliday(HolidayWrapper newHoliday, HolidayWrapper sourceHoliday){
        return (newHoliday.getHolidayInfo().getTypeKey().equals(sourceHoliday.getHolidayInfo().getTypeKey()));
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        HolidayCalendarForm hcForm = (HolidayCalendarForm) form;

        String hcId = request.getParameter(CalendarConstants.CALENDAR_ID);

        if ((hcId != null) && !hcId.trim().isEmpty()) {
            String viewId = request.getParameter("viewId");
            if ("holidayCalendarView".equals(viewId)) {
                hcForm.setViewTypeName(UifConstants.ViewType.INQUIRY);
            }

            try {
                getHolidayCalendar(hcId, hcForm);
            } catch (Exception ex) {
            }
        }

        return super.start(form, result, request, response);
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=startNew")
    public ModelAndView startNew( @ModelAttribute("KualiForm") HolidayCalendarForm form, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) {
        HolidayCalendarInfo hcInfo = null;
        try {
            hcInfo = getHolidayCalendarFormHelper(form).getNewestHolidayCalendar();
            form.setHolidayCalendarInfo(hcInfo);
        }
        catch (Exception x) {
            //TODO - what to do here?
        }

        if (null != hcInfo) {
            // do some calculations on probable values for the new calendar
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR);
            cal.setTime(hcInfo.getEndDate());
            int year = cal.get(Calendar.YEAR);
            if (year == currentYear) {
                StringBuilder calName = new StringBuilder();
                calName.append(++year);
                calName.append(" Holiday Calendar");
                form.setNewCalendarName(calName.toString());
                cal.add(Calendar.YEAR, 1);
                form.setNewCalendarEndDate(cal.getTime());
                cal.setTime(hcInfo.getStartDate());
                cal.add(Calendar.YEAR, 1);
                form.setNewCalendarStartDate(cal.getTime());
            }
        }

        return super.start(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=copy")
    public ModelAndView copy( @ModelAttribute("KualiForm") HolidayCalendarForm form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        if ((null == form.getHolidayCalendarInfo()) || (null == form.getHolidayCalendarInfo().getId())) {
            //TODO - display some kind of error
            return getUIFModelAndView(form);
        }

        HolidayCalendarInfo newHCInfo = null;
        try {
            newHCInfo = getHolidayCalendarFormHelper(form).copyHolidayCalendar(form);
        }
        catch (Exception x) {
        }

        if (null == newHCInfo) {
            //TODO - display some kind of error
            return getUIFModelAndView(form);
        }
        else {
            form.setHolidayCalendarInfo(newHCInfo);
            form.setOfficial(newHCInfo.getStateKey().equals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY)? false : true);
            form.setDelete(true);
            form.setHcId(newHCInfo.getId());
            return getUIFModelAndView(form, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
        }
    }

    @RequestMapping(params = "methodToCall=toCreate")
    public ModelAndView toCreate(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){
        hcForm.setHcId(null);
        hcForm.setHolidayCalendarInfo( new HolidayCalendarInfo());
        hcForm.setHolidays(new ArrayList<HolidayWrapper>());
        hcForm.setOfficial(false);
        hcForm.setDelete(false);
        return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
    }

    @RequestMapping(params = "methodToCall=toEdit")
    public ModelAndView toEdit(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){
        return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
    }

    @RequestMapping(params = "methodToCall=toCopy")
    public ModelAndView toCopy(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){
        return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_COPYPAGE);
    }

     /**
     * Method used to save HC
     */
    @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        return updateHolidayCalendarForm(hcForm, "info.enroll.holidaycalendar.saved");
    }

     /**
     * Method used to delete HC
     */
    @RequestMapping(params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        getHolidayCalendarFormHelper(hcForm).deleteHolidayCalendar(hcForm.getHolidayCalendarInfo().getId());
        Properties urlParameters = new  Properties();
        urlParameters.put("viewId", "enrollmentHomeView");
        urlParameters.put("methodToCall", "start");
        return performRedirect(hcForm, request.getRequestURL().toString(), urlParameters);
    }

     /**
     * Method used to set HC official
     */
     @RequestMapping(params = "methodToCall=setOfficial")
     public ModelAndView setOfficial(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {
         hcForm.getHolidayCalendarInfo().setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
         return updateHolidayCalendarForm(hcForm, "info.enroll.holidaycalendar.official");
     }

    private ModelAndView updateHolidayCalendarForm(HolidayCalendarForm hcForm, String updateMsg) throws Exception {
        HolidayCalendarInfo hc = hcForm.getHolidayCalendarInfo();

        if(isValidHolidayCalendar(hc)){
            String hcId = hc.getId();
            if(hc.getId() != null && !hc.getId().trim().isEmpty()){
                // edit hc
               updateHolidayCalendar(hc.getId(), hcForm);
            }
            else {
               // create hc
                createHolidayCalendar(hcForm);
            }

            hcForm.setAdminOrgName(getAdminOrgNameById(hc.getAdminOrgId()));
            hcForm.setStateName(getHolidayCalendarFormHelper(hcForm).getHolidayCalendarState(hc.getStateKey()));
            hcForm.setOfficial(hc.getStateKey().equals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY)? false : true);
            hcForm.setDelete(true);
            hcForm.setHcId(hc.getId());
            GlobalVariables.getMessageMap().putInfo("holidayCalendarInfo.name", updateMsg, hc.getName());

            if (StringUtils.isBlank(hcId)) {
                return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
            }
            else {
                return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_VIEWPAGE);
            }
        }
        else {
            return getUIFModelAndView(hcForm, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
        }
    }

    private boolean isValidHolidayCalendar(HolidayCalendarInfo hc)throws Exception {
        boolean valid = true;
        Date startDate = hc.getStartDate();
        Date endDate = hc.getEndDate();

        if(startDate.after(endDate)) {
            //TODO:change to  putError, when error reload fixed
            //GlobalVariables.getMessageMap().putError("holidayCalendarInfo.name","error.enroll.holidaycalendar.invalidDates", hc.getName());
            GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: " +  hc.getName() + "start date should not be later than the end date.");
            valid = false;
        }

        return valid;
    }

    private void createHolidayCalendar(HolidayCalendarForm hcForm) throws Exception {
        HolidayCalendarInfo hcInfo = getHolidayCalendarFormHelper(hcForm).createHolidayCalendar(hcForm);
        hcForm.setHolidayCalendarInfo(hcInfo);

        createHolidays(hcInfo.getId(), hcForm);
    }

    private void getHolidayCalendar(String hcId, HolidayCalendarForm hcForm) throws Exception {
        HolidayCalendarInfo hcInfo = getHolidayCalendarFormHelper(hcForm).getHolidayCalendar(hcId);
        hcForm.setHolidayCalendarInfo(hcInfo);
        hcForm.setAdminOrgName(getAdminOrgNameById(hcInfo.getAdminOrgId()));
        hcForm.setStateName(getHolidayCalendarFormHelper(hcForm).getHolidayCalendarState(hcInfo.getStateKey()));
        hcForm.setOfficial(hcInfo.getStateKey().equals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY)? false : true);
        hcForm.setDelete(true);
        List<HolidayWrapper> holidays = getHolidayCalendarFormHelper(hcForm).getHolidaysForHolidayCalendar(hcForm);
        hcForm.setHolidays(holidays);
    }

    private void updateHolidayCalendar(String hcId, HolidayCalendarForm hcForm) throws Exception {
        //update hc meta data
        getHolidayCalendarFormHelper(hcForm).updateHolidayCalendar(hcForm);
        hcForm.setHolidayCalendarInfo(getHolidayCalendarFormHelper(hcForm).getHolidayCalendar(hcId));

        //update hc-holidays
        List<HolidayWrapper> holidays = hcForm.getHolidays();
        processHolidays(hcForm, holidays, hcId);
    }

    private void createHolidays(String holidayCalendarId, HolidayCalendarForm hcForm) throws Exception {
        List<HolidayWrapper> holidays = hcForm.getHolidays();

       if(holidays != null && !holidays.isEmpty()){
            for (HolidayWrapper holiday : holidays){
                getHolidayCalendarFormHelper(hcForm).createHoliday(holidayCalendarId, holiday.getHolidayInfo().getTypeKey(), holiday);
            }
        }

    }

    private List<String> getHolidayIds(HolidayCalendarForm hcForm) throws Exception{
        List<HolidayWrapper> holidays = getHolidayCalendarFormHelper(hcForm).getHolidaysForHolidayCalendar(hcForm);
        List<String> holidayIds = new ArrayList<String>();

        if(holidays != null && !holidays.isEmpty()){
            for(HolidayWrapper holiday : holidays){
                holidayIds.add(holiday.getHolidayInfo().getId());
            }
        }

        return holidayIds;
    }

    private void processHolidays(HolidayCalendarForm hcForm, List<HolidayWrapper> holidays, String hcId)throws Exception{
        List<HolidayWrapper> updatedHolidays = new ArrayList<HolidayWrapper>();
        List<String> currentHolidays = getHolidayIds(hcForm);

        if(holidays != null && !holidays.isEmpty()){
            for(HolidayWrapper holiday : holidays){
                if(currentHolidays.contains(holiday.getHolidayInfo().getId())){
                    //update holiday
                    getHolidayCalendarFormHelper(hcForm).updateHoliday(holiday.getHolidayInfo().getId(), holiday);
                    updatedHolidays.add(holiday);
                    currentHolidays.remove(holiday.getHolidayInfo().getId());
                }
                else {
                    //create Holiday
                    getHolidayCalendarFormHelper(hcForm).createHoliday(hcId, holiday.getHolidayInfo().getTypeKey(), holiday);
                    updatedHolidays.add(holiday);
                }
            }
        }

        hcForm.setHolidays(updatedHolidays);

        if (currentHolidays != null && currentHolidays.size() > 0){
            for(String holidayId: currentHolidays){
                getHolidayCalendarFormHelper(hcForm).deleteHoliday(holidayId);
            }
        }

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

    private AcademicCalendarViewHelperService getHolidayCalendarFormHelper(HolidayCalendarForm hcForm) {
        if (hcForm.getView().getViewHelperServiceClassName() != null){
            return (AcademicCalendarViewHelperService)hcForm.getView().getViewHelperService();
        } else {
            return (AcademicCalendarViewHelperService)hcForm.getPostedView().getViewHelperService();
        }
    }
}
