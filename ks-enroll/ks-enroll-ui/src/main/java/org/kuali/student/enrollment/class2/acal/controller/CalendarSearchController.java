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

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.AcademicCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.form.CalendarSearchForm;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.test.utilities.TestHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/calendarSearch")
public class CalendarSearchController  extends UifControllerBase {
    private transient AcademicCalendarService acalService;
    private ContextInfo contextInfo;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
         return new CalendarSearchForm();
    }

     /**
     * Method used to search atps
     */
    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
       String calendarType = searchForm.getCalendarType();

        if(calendarType.equals(CalendarConstants.HOLIDAYCALENDER)){

        } else if(calendarType.equals(CalendarConstants.ACADEMICCALENDER)) {

        } else if(calendarType.equals(CalendarConstants.TERM)){

        } else {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: invalid calendar type.");
        }

       return getUIFModelAndView(searchForm);
    }

         /**
     * Method used to view the atp
     */
    @RequestMapping(params = "methodToCall=view")
    public ModelAndView view(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        String calendarType = searchForm.getCalendarType();

         if(calendarType.equals(CalendarConstants.HOLIDAYCALENDER)){

         } else if(calendarType.equals(CalendarConstants.ACADEMICCALENDER)) {

         } else if(calendarType.equals(CalendarConstants.TERM)){

         } else {
             GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: invalid calendar type.");
         }

       return getUIFModelAndView(searchForm);
    }

         /**
     * Method used to edit the atp
     */
    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        String calendarType = searchForm.getCalendarType();

         if(calendarType.equals(CalendarConstants.HOLIDAYCALENDER)){

         } else if(calendarType.equals(CalendarConstants.ACADEMICCALENDER)) {

         } else if(calendarType.equals(CalendarConstants.TERM)){

         } else {
             GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: invalid calendar type.");
         }

       return getUIFModelAndView(searchForm);
    }

         /**
     * Method used to copy the atp
     */
    @RequestMapping(params = "methodToCall=copy")
    public ModelAndView copy(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
       String calendarType = searchForm.getCalendarType();

        if(calendarType.equals(CalendarConstants.HOLIDAYCALENDER)){

        } else if(calendarType.equals(CalendarConstants.ACADEMICCALENDER)) {

        } else if(calendarType.equals(CalendarConstants.TERM)){

        } else {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: invalid calendar type.");
        }

       return getUIFModelAndView(searchForm);
    }

         /**
     * Method used to delete the atp
     */
    @RequestMapping(params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
       String calendarType = searchForm.getCalendarType();

        if(calendarType.equals(CalendarConstants.HOLIDAYCALENDER)){

        } else if(calendarType.equals(CalendarConstants.ACADEMICCALENDER)) {

        } else if(calendarType.equals(CalendarConstants.TERM)){

        } else {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: invalid calendar type.");
        }

       return getUIFModelAndView(searchForm);
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
}
