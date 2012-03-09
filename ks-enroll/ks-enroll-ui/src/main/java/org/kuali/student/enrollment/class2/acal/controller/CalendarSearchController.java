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
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
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
import org.kuali.student.r2.core.atp.infc.Atp;
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

     /**
     * Method used to search atps
     */
    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        String calendarType = searchForm.getCalendarType();

        resetForm(searchForm);
        if(calendarType.equals(CalendarConstants.HOLIDAYCALENDER)){
               List<HolidayCalendarInfo> hCals = ((CalendarSearchViewHelperService)searchForm.getView().getViewHelperService()).searchForHolidayCalendars(searchForm.getName(), searchForm.getYear(), getContextInfo());
               searchForm.setHolidayCalendars(hCals);
        } else if(calendarType.equals(CalendarConstants.ACADEMICCALENDER)) {
               List<AcademicCalendarInfo> aCals = ((CalendarSearchViewHelperService)searchForm.getView().getViewHelperService()).searchForAcademicCalendars(searchForm.getName(), searchForm.getYear(), getContextInfo());
               searchForm.setAcademicCalendars(aCals);
        } else if(calendarType.equals(CalendarConstants.TERM)){
               List<TermInfo> terms = ((CalendarSearchViewHelperService)searchForm.getView().getViewHelperService()).searchForTerms(searchForm.getName(),searchForm.getYear(),getContextInfo());
               searchForm.setTerms(terms);
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

    private String getUrlforHC(HttpServletRequest request, Properties urlParameters, String viewId, String pageId, String method, String controllerPath, String hcId){
        urlParameters.put("viewId", viewId);
        urlParameters.put("pageId", pageId);
        urlParameters.put("methodToCall", method);
        urlParameters.put("hcId", hcId);
        String appUrl = KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString("application.url");
        String servletPath =  request.getServletPath();
        return appUrl + servletPath + "/" + controllerPath;
    }

     /**
     * Method used to view the atp
     */
    @RequestMapping(params = "methodToCall=view")
    public ModelAndView view(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {

        Object atp = getSelectedAtp(searchForm, "view");
        Properties urlParameters = new  Properties();
        String url = "";
         if(atp instanceof HolidayCalendarInfo){
            url = getUrlforHC(request, urlParameters,
                    CalendarConstants.HOLIDAYCALENDAR_FLOWVIEW,
                    CalendarConstants.HOLIDAYCALENDAR_VIEWPAGE,
                    CalendarConstants.HC_VIEW_METHOD,
                    CalendarConstants.HCAL_CONTROLLER_PATH,
                    ((HolidayCalendarInfo) atp).getId());
         } else if(atp instanceof AcademicCalendarInfo) {

         } else if(atp instanceof TermInfo){

         } else {
             GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: invalid calendar type.");
         }

        return performRedirect(searchForm, url, urlParameters);
    }

     /**
     * Method used to edit the atp
     */
    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object atp = getSelectedAtp(searchForm, "edit");
        Properties urlParameters = new  Properties();
        String url = "";
         if(atp instanceof HolidayCalendarInfo){
            url = getUrlforHC(request, urlParameters,
                    CalendarConstants.HOLIDAYCALENDAR_FLOWVIEW,
                    CalendarConstants.HOLIDAYCALENDAR_EDITPAGE,
                    CalendarConstants.HC_EDIT_METHOD,
                    CalendarConstants.HCAL_CONTROLLER_PATH,
                    ((HolidayCalendarInfo) atp).getId());
         } else if(atp instanceof AcademicCalendarInfo) {

         } else if(atp instanceof TermInfo){

         } else {
             GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: invalid calendar type.");
         }

        return performRedirect(searchForm, url, urlParameters);

    }

     /**
     * Method used to copy the atp
     */
    @RequestMapping(params = "methodToCall=copy")
    public ModelAndView copy(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object atp = getSelectedAtp(searchForm, "copy");
        Properties urlParameters = new  Properties();
        String url = "";
         if(atp instanceof HolidayCalendarInfo){
            url = getUrlforHC(request, urlParameters,
                    CalendarConstants.HOLIDAYCALENDAR_FLOWVIEW,
                    CalendarConstants.HOLIDAYCALENDAR_COPYPAGE,
                    CalendarConstants.HC_COPY_METHOD,
                    CalendarConstants.HCAL_CONTROLLER_PATH,
                    ((HolidayCalendarInfo) atp).getId());
         } else if(atp instanceof AcademicCalendarInfo) {

         } else if(atp instanceof TermInfo){

         } else {
             GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: invalid calendar type.");
         }

        return performRedirect(searchForm, url, urlParameters);

    }

         /**
     * Method used to delete the atp
     */
    @RequestMapping(params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") CalendarSearchForm searchForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object atp = getSelectedAtp(searchForm, "delete");

         if(atp instanceof HolidayCalendarInfo){

         } else if(atp instanceof AcademicCalendarInfo) {

         } else if(atp instanceof TermInfo){

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
