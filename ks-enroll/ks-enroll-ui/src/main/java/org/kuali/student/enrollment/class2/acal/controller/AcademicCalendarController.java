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

import org.apache.commons.lang.StringUtils;

import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.dto.AcalEventWrapper;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.test.utilities.TestHelper;

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
import java.util.Map;
import java.util.HashMap;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/academicCalendar")
public class AcademicCalendarController extends UifControllerBase {
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

        String acalId = request.getParameter("acalId");
        if (acalId != null && !acalId.trim().isEmpty()) {
            String viewId = request.getParameter("viewId");
            if ("academicCalendarEditView".equals(viewId)) {
                acalForm.setViewTypeName(UifConstants.ViewType.INQUIRY);
            }

            try {
                getAcademicCalendar(acalId, acalForm);
            } catch (Exception ex) {
                //TODO: handle exception properly
            }
        }

        return super.start(form, result, request, response);
    }

    /**
     * Method used to save AcademicCalendar
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception{
        AcademicCalendarInfo academicCalendarInfo = academicCalendarForm.getAcademicCalendarInfo();

        if(academicCalendarInfo.getId() != null && !academicCalendarInfo.getId().trim().isEmpty()){
            // update acal
            AcademicCalendarInfo acalInfo = getAcademicCalendarViewHelperService(academicCalendarForm).updateAcademicCalendar(academicCalendarForm);
            academicCalendarForm.setAcademicCalendarInfo(getAcademicCalendarViewHelperService(academicCalendarForm).getAcademicCalendar(acalInfo.getId()));

            //update acalEvents
            List<AcalEventWrapper> events = academicCalendarForm.getEvents();
            processEvents(academicCalendarForm, events, acalInfo.getId());
        }
        else {
            // create acalInfo
            AcademicCalendarInfo acalInfo = getAcademicCalendarViewHelperService(academicCalendarForm).createAcademicCalendar(academicCalendarForm);
            academicCalendarForm.setAcademicCalendarInfo(acalInfo);
            // then create events if any
            createEvents(acalInfo.getId(), academicCalendarForm);
        }
        return getUIFModelAndView(academicCalendarForm);
    }

    /**
     * Method used to save AcademicCalendar
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=saveTerm")
    public ModelAndView saveTerm(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
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

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(selectedLineIndex);

        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();

        try{
            ((AcademicCalendarViewHelperService)academicCalendarForm.getView().getViewHelperService()).saveTerm(termWrapper, context);
            GlobalVariables.getMessageMap().putInfo("name","info.enroll.term.saved",termWrapper.getTermNameForUI());
        }catch (Exception e){
            //TODO:For now, throw RTE, have to look into proper way of handling exceptions.
           throw new RuntimeException(e);
        }

        return updateComponent(academicCalendarForm, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=setTermOfficial")
    public ModelAndView setTermOfficial(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
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

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(selectedLineIndex);

        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();

        if (termWrapper.getKeydates() == null || termWrapper.getKeydates().isEmpty()){
            GlobalVariables.getMessageMap().putError("termWrapperList","error.enroll.term.nokeydates",termWrapper.getTermNameForUI());
            return updateComponent(academicCalendarForm, result, request, response);
        }

        try{
            ((AcademicCalendarViewHelperService)academicCalendarForm.getView().getViewHelperService()).setTermOfficial(termWrapper, context);
            GlobalVariables.getMessageMap().putInfo("name","info.enroll.term.official",termWrapper.getTermNameForUI());
        }catch (Exception e){
            //TODO:For now, throw RTE, have to look into proper way of handling exceptions.
           throw new RuntimeException(e);
        }

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
            ((AcademicCalendarViewHelperService)academicCalendarForm.getView().getViewHelperService()).deleteTerm(academicCalendarForm.getTermWrapperList(),selectedLineIndex,context);
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

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(Integer.parseInt(selectedTermIndex));
        try {
            ((AcademicCalendarViewHelperService)academicCalendarForm.getView().getViewHelperService()).deleteKeyDate(termWrapper.getKeydates(),selectedLineIndex,context);
        } catch (Exception e) {
            //TODO:For now, throw RTE, have to look into proper way of handling exceptions.
            throw new RuntimeException(e);
        }

        return updateComponent(academicCalendarForm, result, request, response);

    }

    private void getAcademicCalendar(String acalId, AcademicCalendarForm acalForm) throws Exception {
        AcademicCalendarInfo acalInfo = getAcademicCalendarViewHelperService(acalForm).getAcademicCalendar(acalId);
        acalForm.setAcademicCalendarInfo(acalInfo);
        acalForm.setAdminOrgName(getAdminOrgNameById(acalInfo.getAdminOrgId()));

        List<AcalEventWrapper> events = getAcademicCalendarViewHelperService(acalForm).getEventsForAcademicCalendar(acalForm);
        if (events.size() == 0)  
            System.out.println(">>> didn't find any event associated with Academic Calendar: "+acalInfo.getName());
        acalForm.setEvents(events);
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

    private void processEvents(AcademicCalendarForm acalForm, List<AcalEventWrapper> events, String acalId)throws Exception{
        List<AcalEventWrapper> updatedEvents = new ArrayList<AcalEventWrapper>();
        List<String> currentEventIds = getEventIds(acalForm);

        if(events != null && !events.isEmpty()){
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
        }

        acalForm.setEvents(updatedEvents);

        if (currentEventIds != null && currentEventIds.size() > 0){
            for(String eventId: currentEventIds){
                //TODO: delete completely from db, when "deleted" state is available, update the event with state ="deleted"
                getAcademicCalendarViewHelperService(acalForm).deleteEvent(eventId);
            }
        }

    }

    private List<String> getEventIds(AcademicCalendarForm acalForm) throws Exception{
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
        return (AcademicCalendarViewHelperService)academicCalendarForm.getView().getViewHelperService();
    }

}
