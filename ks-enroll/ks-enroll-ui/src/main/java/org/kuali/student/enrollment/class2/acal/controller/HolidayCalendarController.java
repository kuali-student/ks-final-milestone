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
 * Created by Li Pan on 1/18/12
 */
package org.kuali.student.enrollment.class2.acal.controller;

import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        HolidayCalendarForm hcForm = (HolidayCalendarForm) form;

        String hcId = request.getParameter("hcId");
        if(hcId != null && !hcId.trim().isEmpty()){
            try{
                getHolidayCalendar(hcId, hcForm);
            } catch (Exception ex){

            }
        }

        return super.start(form, result, request, response);
    }

     /**
     * Method used to save HC
     */
    @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") HolidayCalendarForm hcForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        HolidayCalendarInfo hc = hcForm.getHolidayCalendarInfo();

        if(hc.getId() != null && !hc.getId().trim().isEmpty()){
            // edit hc
           updateHolidayCalendar(hc.getId(), hcForm);
        }
        else {
           // create hc
            createHolidayCalendar(hcForm);
        }
        return getUIFModelAndView(hcForm);
    }

    private void createHolidayCalendar(HolidayCalendarForm hcForm) throws Exception {
        HolidayCalendarInfo hcInfo = getAcademicCalendarViewHelperService(hcForm).createHolidayCalendar(hcForm);
        hcForm.setHolidayCalendarInfo(hcInfo);

        //TODO: fix bugs in service impl
       //createHolidays(hcInfo.getId(), hcForm);
    }

    private void getHolidayCalendar(String hcId, HolidayCalendarForm hcForm) throws Exception {
        HolidayCalendarInfo hcInfo = getAcademicCalendarViewHelperService(hcForm).getHolidayCalendar(hcId);
        hcForm.setHolidayCalendarInfo(hcInfo);

        List<HolidayInfo> holidays = getAcademicCalendarViewHelperService(hcForm).getHolidaysForHolidayCalendar(hcForm);
        if (holidays != null && !holidays.isEmpty()){
            hcForm.setHolidays(holidays);
        }
    }

    public void updateHolidayCalendar(String hcId, HolidayCalendarForm hcForm) throws Exception {
        //update hc meta data
        getAcademicCalendarViewHelperService(hcForm).updateHolidayCalendar(hcForm);
        hcForm.setHolidayCalendarInfo(getAcademicCalendarViewHelperService(hcForm).getHolidayCalendar(hcId));

        //update hc-holidays

    }

    private void createHolidays(String holidayCalendarId, HolidayCalendarForm hcForm) throws Exception {
        List<HolidayInfo> holidays = hcForm.getHolidays();
        List<HolidayInfo> createdHolidays = new ArrayList<HolidayInfo>();

        if(holidays != null && !holidays.isEmpty()){
            for (HolidayInfo holiday : holidays){
                createdHolidays.add(createHoliday(holidayCalendarId, holiday.getTypeKey(), holiday, hcForm));
            }

            hcForm.setHolidays(createdHolidays);
        }

    }

    private HolidayInfo createHoliday(String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo, HolidayCalendarForm hcForm)throws Exception {
        return getAcademicCalendarViewHelperService(hcForm).createHoliday(holidayCalendarId, holidayTypeKey, holidayInfo);
    }

    private AcademicCalendarViewHelperService getAcademicCalendarViewHelperService(HolidayCalendarForm hcForm){
        return (AcademicCalendarViewHelperService)hcForm.getView().getViewHelperService();
    }
}
