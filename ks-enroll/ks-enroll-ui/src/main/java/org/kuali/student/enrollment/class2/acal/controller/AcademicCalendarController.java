/*
 * Copyright 2011 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.acal.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.dto.AcalEventWrapper;
import org.kuali.student.enrollment.class2.acal.dto.KeyDatesGroupWrapper;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.test.utilities.TestHelper;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.*;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/academicCalendar")
public class AcademicCalendarController extends UifControllerBase {

    private AcademicCalendarService acalService;
    private ContextInfo contextInfo;
    /**
     * @see org.kuali.rice.krad.web.controller.UifControllerBase#createInitialForm(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new AcademicCalendarForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        AcademicCalendarForm acalForm = (AcademicCalendarForm) form;

        String acalId = request.getParameter(CalendarConstants.CALENDAR_ID);

        if (StringUtils.isNotBlank(acalId)){
            try {
                loadAcademicCalendar(acalId, acalForm);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        String readOnlyView = request.getParameter(CalendarConstants.READ_ONLY_VIEW);
        acalForm.getView().setReadOnly(BooleanUtils.toBoolean(readOnlyView));

        return super.start(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=toCreate")
    public ModelAndView toCreate(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response){
        acalForm.setAcademicCalendarInfo(new AcademicCalendarInfo());
        acalForm.setEvents(new ArrayList<AcalEventWrapper>());
        acalForm.setHolidayCalendarList(new ArrayList<HolidayCalendarWrapper>());
        acalForm.setTermWrapperList(new ArrayList<AcademicTermWrapper>());
        acalForm.setOfficial(false);
        acalForm.setDelete(false);
        return getUIFModelAndView(acalForm, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
    }

    // if acalId is not empty, use the acalInfo of that acalId as the template for copying
    //otherwise, find the latest acal and use it as the template for copying
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=copyForNew")
    public ModelAndView copyForNew( @ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) {

        AcademicCalendarInfo acalInfo = null;

        String acalId = request.getParameter(CalendarConstants.CALENDAR_ID);
        if (acalId != null && !acalId.trim().isEmpty()) {
            String pageId = request.getParameter(CalendarConstants.PAGE_ID);
            if (CalendarConstants.ACADEMIC_CALENDAR_COPY_PAGE.equals(pageId)) {
//                acalForm.setViewTypeName(UifConstants.ViewType.INQUIRY);
                try {
                    acalInfo= getAcalService().getAcademicCalendar(acalId, getContextInfo());
                    acalForm.setOrgAcalInfo(acalInfo);

                } catch (Exception ex) {
                    //TODO: handle exception properly
                }
            }
        }
        else {
            // try to get the latest AC from DB
            try {
                acalInfo = getAcademicCalendarViewHelperService(acalForm).getLatestAcademicCalendar();
                acalForm.setOrgAcalInfo(acalInfo);
            }
            catch (Exception x) {
                //TODO - what to do here?
            }

//            if (null != acalInfo) {
//                // do some calculations on probable values for the new calendar
//                Calendar cal=Calendar.getInstance();
//                cal.setTime(acalInfo.getStartDate());
//                cal.add(Calendar.YEAR, 1);
//                form.setNewCalendarStartDate(cal.getTime());
//                cal.setTime(acalInfo.getEndDate());
//                cal.add(Calendar.YEAR, 1);
//                form.setNewCalendarEndDate(cal.getTime());
//            }

        }
        //return super.start(acalForm, result, request, response);
        return getUIFModelAndView(acalForm);
    }

    //copy over from the existing AcalInfo to create a new
    @RequestMapping(method = RequestMethod.POST, params="methodToCall=copy")
    public ModelAndView copy( @ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        if ( null == acalForm.getOrgAcalInfo() || null == acalForm.getOrgAcalInfo().getId()) {
            //TODO - display some kind of error
            return getUIFModelAndView(acalForm);
        }


        try {
           getAcademicCalendarViewHelperService(acalForm).copyToCreateAcademicCalendar(acalForm);
            //???
           acalForm.setDelete(true);
        }catch (Exception ex) {

        }
        return getUIFModelAndView(acalForm, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);

    }

    /**
     * redirect to search Calendar page
     */
    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") AcademicCalendarForm searchForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        String controllerPath = CalendarConstants.CALENDAR_SEARCH_CONTROLLER_PATH;
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "start");
        urlParameters.put(UifParameters.VIEW_ID, CalendarConstants.CALENDAR_SEARCH_VIEW);
        return super.performRedirect(searchForm,controllerPath, urlParameters);
    }


    /**
     * Method used to save AcademicCalendar
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception{
        AcademicCalendarInfo academicCalendarInfo = academicCalendarForm.getAcademicCalendarInfo();

        if(academicCalendarInfo.getId() != null && !academicCalendarInfo.getId().trim().isEmpty()){
            // 1. update acal
            AcademicCalendarInfo acalInfo = getAcalService().updateAcademicCalendar(academicCalendarInfo.getId(), academicCalendarInfo, getContextInfo() );
            academicCalendarForm.setAcademicCalendarInfo(getAcalService().getAcademicCalendar(acalInfo.getId(), getContextInfo()));

            // 2. update acalEvents if any
            List<AcalEventWrapper> events = academicCalendarForm.getEvents();
            if(events != null && !events.isEmpty()){
                processEvents(academicCalendarForm, events, acalInfo.getId());
            }
            // 3. update AC-HC relationships
            List<HolidayCalendarWrapper> holidayCalendarList = academicCalendarForm.getHolidayCalendarList();
            if (holidayCalendarList != null && !holidayCalendarList.isEmpty()) {
//                updateACAndHCRelationships (academicCalendarForm, holidayCalendarList, acalInfo.getId());
            }
        }
        else {
            // 1. create  a new acalInfo
            AcademicCalendarInfo acalInfo = getAcademicCalendarViewHelperService(academicCalendarForm).createAcademicCalendar(academicCalendarForm);
            academicCalendarForm.setAcademicCalendarInfo(acalInfo);
            // 2. create new events if any
            createEvents(acalInfo.getId(), academicCalendarForm);
            // 3. create AC-HC relationships
//            createACandHCRelationships(acalInfo.getId(), academicCalendarForm);

        }

        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();


        //Validate Term
        getAcademicCalendarViewHelperService(academicCalendarForm).validateTerm(academicCalendarForm.getTermWrapperList(),context);

        if (GlobalVariables.getMessageMap().getErrorCount() > 0){
           return getUIFModelAndView(academicCalendarForm);
        }


        //Save Term and keydates
        for(AcademicTermWrapper termWrapper : academicCalendarForm.getTermWrapperList()){
            getAcademicCalendarViewHelperService(academicCalendarForm).saveTerm(termWrapper, academicCalendarForm.getAcademicCalendarInfo().getId(), context);
        }

        //Calculate instructional days (if HC exists)
        if (academicCalendarForm.getHolidayCalendarList() != null && !academicCalendarForm.getHolidayCalendarList().isEmpty()) {
           getAcademicCalendarViewHelperService(academicCalendarForm).populateInstructionalDays(academicCalendarForm.getTermWrapperList(),context);
        }

        return getUIFModelAndView(academicCalendarForm);
    }

    /**
     * Method used to save AcademicCalendar
     */
//    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=saveTerm")
//    public ModelAndView saveTerm(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
//                             HttpServletRequest request, HttpServletResponse response) {
//
//        if(StringUtils.isBlank(academicCalendarForm.getAcademicCalendarInfo().getId())){
//             GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Please save the academic calendar first");
//             return updateComponent(academicCalendarForm, result, request, response);
//        }
//
//        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
//        if (StringUtils.isBlank(selectedCollectionPath)) {
//            throw new RuntimeException("unable to determine the selected collection path");
//        }
//
//        int selectedLineIndex = -1;
//        String selectedLine = academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
//        if (StringUtils.isNotBlank(selectedLine)) {
//            selectedLineIndex = Integer.parseInt(selectedLine);
//        }
//
//        if (selectedLineIndex == -1) {
//            throw new RuntimeException("unable to determine the selected line index");
//        }
//
//        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(selectedLineIndex);
//
//        //TODO:Build real context.
//        ContextInfo context = TestHelper.getContext1();
//
//        try{
//            ((AcademicCalendarViewHelperService)academicCalendarForm.getPostedView().getViewHelperService()).saveTerm(termWrapper, academicCalendarForm.getAcademicCalendarInfo().getId(), context);
//            GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS,"info.enroll.term.saved",termWrapper.getTermNameForUI());
//        }catch (Exception e){
//            //TODO:For now, throw RTE, have to look into proper way of handling exceptions.
//           throw new RuntimeException(e);
//        }
//
//        return updateComponent(academicCalendarForm, result, request, response);
//    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=setTermOfficial")
    public ModelAndView setTermOfficial(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        if(StringUtils.isBlank(academicCalendarForm.getAcademicCalendarInfo().getId())){
             GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Please save the academic calendar first");
             return updateComponent(academicCalendarForm, result, request, response);
        }

        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("unable to determine the selected line index");
        }

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(selectedLineIndex);

        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();

        if (termWrapper.getKeyDatesGroupWrappers() == null || termWrapper.getKeyDatesGroupWrappers().isEmpty()){
            GlobalVariables.getMessageMap().putError("termWrapperList","error.enroll.term.nokeydates",termWrapper.getTermNameForUI());
            return updateComponent(academicCalendarForm, result, request, response);
        }else{
            for (KeyDatesGroupWrapper keyDatesGroup : termWrapper.getKeyDatesGroupWrappers()) {
                if (keyDatesGroup.getKeydates() == null || keyDatesGroup.getKeydates().isEmpty()){
                    GlobalVariables.getMessageMap().putError("termWrapperList","error.enroll.term.nokeydates",termWrapper.getTermNameForUI());
                    return updateComponent(academicCalendarForm, result, request, response);
                }
            }
        }

        try{
            getAcademicCalendarViewHelperService(academicCalendarForm).setTermOfficial(termWrapper, academicCalendarForm.getAcademicCalendarInfo().getId(), context);
            GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS,"info.enroll.term.official",termWrapper.getTermNameForUI());
        }catch (Exception e){
            //TODO:For now, throw RTE, have to look into proper way of handling exceptions.
           throw new RuntimeException(e);
        }

        return updateComponent(academicCalendarForm, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=cancelAddingTerm")
    public ModelAndView cancelAddingTerm(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        ((AcademicTermWrapper)academicCalendarForm.getNewCollectionLines().get("termWrapperList")).clear();

        return updateComponent(academicCalendarForm, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=deleteTerm")
    public ModelAndView deleteTerm(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("unable to determine the selected line index");
        }

        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(selectedLineIndex);
        try {
            getAcademicCalendarViewHelperService(academicCalendarForm).deleteTerm(academicCalendarForm.getTermWrapperList(),selectedLineIndex,academicCalendarForm.getAcademicCalendarInfo().getId(),context);
        } catch (Exception e) {
            //TODO:For now, throw RTE, have to look into proper way of handling exceptions.
            throw new RuntimeException(e);
        }

        return updateComponent(academicCalendarForm, result, request, response);

    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=deleteKeyDate")
    public ModelAndView deleteKeyDate(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("unable to determine the selected line index");
        }

        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();

        String selectedTermIndex = StringUtils.substringBetween(selectedCollectionPath,"termWrapperList[","]");
        String selectedKeyDateGroup = StringUtils.substringBetween(selectedCollectionPath,"keyDatesGroupWrappers[","]");

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(Integer.parseInt(selectedTermIndex));
        KeyDatesGroupWrapper keydateGroup = termWrapper.getKeyDatesGroupWrappers().get(Integer.parseInt(selectedKeyDateGroup));
        try {

            getAcademicCalendarViewHelperService(academicCalendarForm).deleteKeyDate(keydateGroup,selectedLineIndex,context);
        } catch (Exception e) {
            //TODO:For now, throw RTE, have to look into proper way of handling exceptions.
            throw new RuntimeException(e);
        }

        return updateComponent(academicCalendarForm, result, request, response);

    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=deleteKeyDateGroup")
    public ModelAndView deleteKeyDateGroup(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("unable to determine the selected line index");
        }

        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();

        String selectedTermIndex = StringUtils.substringBetween(selectedCollectionPath,"termWrapperList[","]");

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(Integer.parseInt(selectedTermIndex));
        try {
            getAcademicCalendarViewHelperService(academicCalendarForm).deleteKeyDateGroup(termWrapper,selectedLineIndex,context);
        } catch (Exception e) {
            //TODO:For now, throw RTE, have to look into proper way of handling exceptions.
            throw new RuntimeException(e);
        }

        return updateComponent(academicCalendarForm, result, request, response);

    }

    private void loadAcademicCalendar(String acalId, AcademicCalendarForm acalForm) throws Exception {

        AcademicCalendarInfo acalInfo = getAcalService().getAcademicCalendar(acalId,getContextInfo());
        acalForm.setAcademicCalendarInfo(acalInfo);
        acalForm.setAdminOrgName(getAdminOrgNameById(acalInfo.getAdminOrgId()));

        List<AcalEventWrapper> events = getAcademicCalendarViewHelperService(acalForm).getEventsForAcademicCalendar(acalForm);
        if (events.size() == 0){
            System.out.println(">>> didn't find any event associated with Academic Calendar: "+acalInfo.getName());
        }

        acalForm.setEvents(events);

        List<AcademicTermWrapper> termWrappers = getAcademicCalendarViewHelperService(acalForm).loadTerms(acalId,getContextInfo());
        acalForm.setTermWrapperList(termWrappers);

        //Calculate instructional days (if HC exists)
        if (acalForm.getHolidayCalendarList() != null && !acalForm.getHolidayCalendarList().isEmpty()) {
           getAcademicCalendarViewHelperService(acalForm).populateInstructionalDays(acalForm.getTermWrapperList(),getContextInfo());
        }

    }

    private String getAdminOrgNameById(String id){
        //TODO: hard-coded for now, going to call OrgService
        String adminOrgName = null;
        Map<String, String> allAcalOrgs = new HashMap<String, String>();
        allAcalOrgs.put("102", "Registrar's Office");
        allAcalOrgs.put("34", "Medical School");

        if(allAcalOrgs.containsKey(id)){
            adminOrgName = allAcalOrgs.get(id);
        }

        return adminOrgName;
    }

    private void createEvents(String acalId, AcademicCalendarForm acalForm) throws Exception {
        List<AcalEventWrapper> events = acalForm.getEvents();

        if(events != null && !events.isEmpty()){
            List<AcalEventWrapper> createdEvents = new ArrayList<AcalEventWrapper>();
            for (AcalEventWrapper event : events){
                createdEvents.add(getAcademicCalendarViewHelperService(acalForm).createEvent(acalId, event));
            }
            acalForm.setEvents(createdEvents);
        }

    }

    /**
     * Update existing events, create new events, and delete events that do not exist any more when a user modifies and saves an Academic Calendar
     */
    private void processEvents(AcademicCalendarForm acalForm, List<AcalEventWrapper> events, String acalId)throws Exception{
        List<AcalEventWrapper> updatedEvents = new ArrayList<AcalEventWrapper>();
        List<String> currentEventIds = getExistingEventIds(acalForm);
        for(AcalEventWrapper event : events){
            if(currentEventIds.contains(event.getAcalEventInfo().getId())){
                //update event
                AcalEventWrapper updatedEvent = getAcademicCalendarViewHelperService(acalForm).updateEvent(event.getAcalEventInfo().getId(), event);
                updatedEvents.add(updatedEvent);
                currentEventIds.remove(event.getAcalEventInfo().getId());
            }
            else {
                //create a new event
                AcalEventWrapper createdEvent = getAcademicCalendarViewHelperService(acalForm).createEvent(acalId, event);
                updatedEvents.add(createdEvent);
            }
        }
        acalForm.setEvents(updatedEvents);

        //delete events that have been removed by the user
        if (currentEventIds != null && currentEventIds.size() > 0){
            for(String eventId: currentEventIds){
                //TODO: delete completely from db, when "deleted" state is available, update the event with state ="deleted"
                getAcademicCalendarViewHelperService(acalForm).deleteEvent(eventId);
            }
        }

    }

    private List<String> getExistingEventIds(AcademicCalendarForm acalForm) throws Exception{
        List<AcalEventWrapper> events = getAcademicCalendarViewHelperService(acalForm).getEventsForAcademicCalendar(acalForm);
        List<String> eventIds = new ArrayList<String>();

        if(events != null && !events.isEmpty()){
            for(AcalEventWrapper event : events){
                eventIds.add(event.getAcalEventInfo().getId());
            }
        }
        return eventIds;
    }


    private AcademicCalendarViewHelperService getAcademicCalendarViewHelperService(AcademicCalendarForm academicCalendarForm){
        if (academicCalendarForm.getView().getViewHelperServiceClassName() != null){
            return (AcademicCalendarViewHelperService)academicCalendarForm.getView().getViewHelperService();
        }else{
            return (AcademicCalendarViewHelperService)academicCalendarForm.getPostedView().getViewHelperService();
        }
    }

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
        }
        return contextInfo;
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }


}
