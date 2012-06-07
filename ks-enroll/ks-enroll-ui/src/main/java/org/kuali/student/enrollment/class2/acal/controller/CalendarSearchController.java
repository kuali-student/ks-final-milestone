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
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.form.CalendarSearchForm;
import org.kuali.student.enrollment.class2.acal.service.CalendarSearchViewHelperService;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.mock.utilities.TestHelper;
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
import java.util.List;
import java.util.Properties;

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


    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        CalendarSearchForm calendarSearchForm = (CalendarSearchForm)form;

        String calendarSearchType = request.getParameter(CalendarConstants.CALENDAR_SEARCH_TYPE);
        if (null != calendarSearchType) {
            calendarSearchForm.setCalendarType(calendarSearchType);
        }

        return super.start(form, result, request, response);
    }

     /**
     * Method used to search atps
     */
    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        String calendarType = searchForm.getCalendarType();

        resetForm(searchForm);
        if(calendarType.equals(CalendarConstants.HOLIDAYCALENDER)){
               List<HolidayCalendarInfo> hCals = getViewHelperService(searchForm).searchForHolidayCalendars(searchForm.getName(), searchForm.getYear(), getContextInfo());
               searchForm.setHolidayCalendars(hCals);
        } else if(calendarType.equals(CalendarConstants.ACADEMICCALENDER)) {
               List<AcademicCalendarInfo> aCals = getViewHelperService(searchForm).searchForAcademicCalendars(searchForm.getName(), searchForm.getYear(), getContextInfo());
               searchForm.setAcademicCalendars(aCals);
        } else if(calendarType.equals(CalendarConstants.TERM)){
               List<TermInfo> terms = getViewHelperService(searchForm).searchForTerms(searchForm.getName(),searchForm.getYear(),getContextInfo());
               searchForm.setTerms(terms);
        } else {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: invalid calendar type.");
        }

       return getUIFModelAndView(searchForm, null);
    }

     /**
     * Method used to view the atp
     */
    @RequestMapping(params = "methodToCall=view")
    public ModelAndView view(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {

        Object atp = getSelectedAtp(searchForm, "view");
        Properties urlParameters;
        String controllerPath;
         if(atp instanceof HolidayCalendarInfo){
             urlParameters = getViewHelperService(searchForm).buildHCalURLParameters((HolidayCalendarInfo)atp,CalendarConstants.HC_VIEW_METHOD,true,getContextInfo());
             controllerPath = CalendarConstants.HCAL_CONTROLLER_PATH;
         } else if(atp instanceof AcademicCalendarInfo) {
             urlParameters = getViewHelperService(searchForm).buildACalURLParameters((AcademicCalendarInfo)atp,CalendarConstants.AC_VIEW_METHOD,true,getContextInfo());
             controllerPath = CalendarConstants.ACAL_CONTROLLER_PATH;
         } else if(atp instanceof TermInfo){
             urlParameters = getViewHelperService(searchForm).buildTermURLParameters((TermInfo)atp,CalendarConstants.AC_VIEW_METHOD,true,getContextInfo());
             controllerPath = CalendarConstants.ACAL_CONTROLLER_PATH;
         } else {
             throw new RuntimeException("Invalid calendar type. This search supports Acal/HCal/Term only");
         }

         return super.performRedirect(searchForm,controllerPath, urlParameters);
    }

     /**
     * Method used to edit the atp
     */
    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {

        Object atp = getSelectedAtp(searchForm, "edit");

        Properties urlParameters;
        String controllerPath;

         if(atp instanceof HolidayCalendarInfo){
             urlParameters = getViewHelperService(searchForm).buildHCalURLParameters((HolidayCalendarInfo) atp, CalendarConstants.HC_EDIT_METHOD, false, getContextInfo());
             controllerPath = CalendarConstants.HCAL_CONTROLLER_PATH;
         } else if(atp instanceof AcademicCalendarInfo) {
             urlParameters = getViewHelperService(searchForm).buildACalURLParameters((AcademicCalendarInfo) atp, CalendarConstants.AC_EDIT_METHOD, false, getContextInfo());
             controllerPath = CalendarConstants.ACAL_CONTROLLER_PATH;
         } else if(atp instanceof TermInfo){
             urlParameters = getViewHelperService(searchForm).buildTermURLParameters((TermInfo)atp,CalendarConstants.AC_EDIT_METHOD, false, getContextInfo());
             controllerPath = CalendarConstants.ACAL_CONTROLLER_PATH;
         } else {
             throw new RuntimeException("Invalid calendar type. This search supports Acal/HCal/Term only");
         }

        return super.performRedirect(searchForm,controllerPath, urlParameters);

    }

     /**
     * Method used to copy the atp
     */
    @RequestMapping(params = "methodToCall=copy")
    public ModelAndView copy(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {

        Object atp = getSelectedAtp(searchForm, "copy");

        Properties urlParameters;
        String controllerPath;

         if(atp instanceof HolidayCalendarInfo){
             controllerPath = CalendarConstants.HCAL_CONTROLLER_PATH;
             urlParameters = getViewHelperService(searchForm).buildHCalURLParameters((HolidayCalendarInfo)atp,CalendarConstants.HC_COPY_METHOD,false,getContextInfo());
         }else if(atp instanceof AcademicCalendarInfo) {
             urlParameters = getViewHelperService(searchForm).buildACalURLParameters((AcademicCalendarInfo)atp,CalendarConstants.AC_COPY_METHOD,false,getContextInfo());
             controllerPath = CalendarConstants.ACAL_CONTROLLER_PATH;
         } else {
             throw new RuntimeException("Invalid calendar type. This search supports Acal and HCal only");
         }

        return super.performRedirect(searchForm,controllerPath, urlParameters);

    }

     /**
     * Method used to delete the atp
     */
    @RequestMapping(params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object atp = getSelectedAtp(searchForm, "delete");

         if(atp instanceof HolidayCalendarInfo){
             StatusInfo status = getAcademicCalendarService().deleteHolidayCalendar(((HolidayCalendarInfo)atp).getId(),getContextInfo());
             if (status.getIsSuccess()){
                 GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_MESSAGES,CalendarConstants.MSG_INFO_SEARCH_DELETE_SUCCESS,((HolidayCalendarInfo) atp).getName());
                 searchForm.getHolidayCalendars().remove(atp);
             } else{
                 GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, status.getMessage());
             }
         } else if(atp instanceof AcademicCalendarInfo) {
             StatusInfo status = getAcademicCalendarService().deleteAcademicCalendar(((AcademicCalendarInfo)atp).getId(),getContextInfo());
             if (status.getIsSuccess()){
                 GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_MESSAGES,CalendarConstants.MSG_INFO_SEARCH_DELETE_SUCCESS,((AcademicCalendarInfo) atp).getName());
                 searchForm.getAcademicCalendars().remove(atp);
             } else{
                 GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, status.getMessage());
             }
         } else if(atp instanceof TermInfo){
             StatusInfo status = getAcademicCalendarService().deleteTerm(((TermInfo)atp).getId(),getContextInfo());
             if (status.getIsSuccess()){
                 GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_MESSAGES,CalendarConstants.MSG_INFO_SEARCH_DELETE_SUCCESS,((TermInfo) atp).getName());
                 searchForm.getTerms().remove(atp);
             } else{
                 GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, status.getMessage());
             }
         } else {
             GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: invalid calendar type.");
         }

        return getUIFModelAndView(searchForm);

    }

    private void resetForm(CalendarSearchForm searchForm) {
        searchForm.setHolidayCalendars(new ArrayList<HolidayCalendarInfo>());
        searchForm.setAcademicCalendars(new ArrayList<AcademicCalendarInfo>());
        searchForm.setTerms(new ArrayList<TermInfo>());
    }

    private Object getSelectedAtp(CalendarSearchForm searchForm, String actionLink){
        String selectedCollectionPath = searchForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for " + actionLink);
        }

        int selectedLineIndex = -1;
        String selectedLine = searchForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Selected line index was not set");
        }

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(searchForm, selectedCollectionPath);
        Object atp = ((List<Object>) collection).get(selectedLineIndex);

        return atp;
    }

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
        }
        return contextInfo;
    }

    private CalendarSearchViewHelperService getViewHelperService(CalendarSearchForm form){
         if (form.getView().getViewHelperServiceClass() != null){
             return (CalendarSearchViewHelperService)form.getView().getViewHelperService();
         } else {
             return (CalendarSearchViewHelperService)form.getPostedView().getViewHelperService();
         }
    }

    protected AcademicCalendarService getAcademicCalendarService(){
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }
}
