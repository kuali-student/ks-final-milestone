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

import org.kuali.rice.krad.inquiry.Inquirable;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        if (hcId != null && !hcId.trim().isEmpty()) {
            String viewId = request.getParameter("viewId");
            if ("holidayCalendarView".equals(viewId)) {
                hcForm.setViewTypeName(UifConstants.ViewType.INQUIRY);
                /* OUT UNTIL HOLIDAYCALENDARVIEW.XML USES INQUIRYVIEW INSTEAD OF FORMVIEW
                HolidayCalendarViewHelperServiceInquirableImpl hcHelper =
                        (HolidayCalendarViewHelperServiceInquirableImpl) hcForm.getView().getViewHelperService();
                HolidayCalendarInfo hcInfo = (HolidayCalendarInfo) ((Inquirable)hcHelper).retrieveDataObject(
                                KRADUtils.translateRequestParameterMap(request.getParameterMap()));
                hcForm.setHolidayCalendarInfo(hcInfo);
                try {
                    hcForm.setHolidays(hcHelper.getHolidaysForHolidayCalendar(hcForm));
                }
                catch(Exception ex) {
                }
                */
            }
            //else {
                try {
                    getHolidayCalendar(hcId, hcForm);
                } catch (Exception ex) {
                }
            //}
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

        createHolidays(hcInfo.getId(), hcForm);
    }

    private void getHolidayCalendar(String hcId, HolidayCalendarForm hcForm) throws Exception {
        HolidayCalendarInfo hcInfo = getAcademicCalendarViewHelperService(hcForm).getHolidayCalendar(hcId);
        hcForm.setHolidayCalendarInfo(hcInfo);
        hcForm.setAdminOrg(getAdminOrgById(hcInfo.getAdminOrgId()));

        List<HolidayInfo> holidays = getAcademicCalendarViewHelperService(hcForm).getHolidaysForHolidayCalendar(hcForm);
        hcForm.setHolidays(holidays);
    }

    public void updateHolidayCalendar(String hcId, HolidayCalendarForm hcForm) throws Exception {
        //update hc meta data
        getAcademicCalendarViewHelperService(hcForm).updateHolidayCalendar(hcForm);
        hcForm.setHolidayCalendarInfo(getAcademicCalendarViewHelperService(hcForm).getHolidayCalendar(hcId));

        //update hc-holidays
        List<HolidayInfo> holidays = hcForm.getHolidays();
        processHolidays(hcForm, holidays, hcId);
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
        //create dummy descr for db MilestoneEntity.plain is not nullable
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(holidayInfo.getTypeKey());
        holidayInfo.setDescr(rti);
        return getAcademicCalendarViewHelperService(hcForm).createHoliday(holidayCalendarId, holidayTypeKey, holidayInfo);
    }

    private List<String> getHolidayIds(HolidayCalendarForm hcForm) throws Exception{
        List<HolidayInfo> holidays = getAcademicCalendarViewHelperService(hcForm).getHolidaysForHolidayCalendar(hcForm);
        List<String> holidayIds = new ArrayList<String>();

        if(holidays != null && !holidays.isEmpty()){
            for(HolidayInfo holiday : holidays){
                holidayIds.add(holiday.getId());
            }
        }

        return holidayIds;
    }

    private void processHolidays(HolidayCalendarForm hcForm, List<HolidayInfo> holidays, String hcId)throws Exception{
        List<HolidayInfo> updatedHolidays = new ArrayList<HolidayInfo>();
        List<String> currentHolidays = getHolidayIds(hcForm);

        if(holidays != null && !holidays.isEmpty()){
            for(HolidayInfo holiday : holidays){
                if(currentHolidays.contains(holiday.getId())){
                    //upate holiday
                    HolidayInfo updatedHoliday = upateHoliday(holiday.getId(), holiday, hcForm);
                    updatedHolidays.add(updatedHoliday);
                    currentHolidays.remove(holiday.getId());
                }
                else {
                    //create Holiday
                    HolidayInfo createdHoliday = createHoliday(hcId, holiday.getTypeKey(), holiday, hcForm);
                    updatedHolidays.add(createdHoliday);
                }
            }
        }

        hcForm.setHolidays(updatedHolidays);

        if (currentHolidays != null && currentHolidays.size() > 0){
            for(String holidayId: currentHolidays){
                //delete holiday
                //TODO: delete completely from db, when "deleted" state is available, update the holiday with state ="deleted"
                deleteHoliday(holidayId, hcForm);
            }
        }

	}

    private HolidayInfo upateHoliday(String holidayId, HolidayInfo holidayInfo, HolidayCalendarForm hcForm)throws Exception {
        return getAcademicCalendarViewHelperService(hcForm).updateHoliday(holidayId, holidayInfo);
    }

    private void deleteHoliday(String holidayId, HolidayCalendarForm hcForm)throws Exception {
        getAcademicCalendarViewHelperService(hcForm).deleteHoliday(holidayId);
    }

    private String getAdminOrgById(String id){
        //TODO: harcoded for now, going to call OrgService
        String adminOrg = null;
        Map<String, String> allHcOrgs = new HashMap<String, String>();
        allHcOrgs.put("102", "Registrar's Office");

        if(allHcOrgs.containsKey(id)){
            adminOrg = allHcOrgs.get(id);
        }

        return adminOrg;
    }

    private AcademicCalendarViewHelperService getAcademicCalendarViewHelperService(HolidayCalendarForm hcForm){
        return (AcademicCalendarViewHelperService)hcForm.getView().getViewHelperService();
    }
}
