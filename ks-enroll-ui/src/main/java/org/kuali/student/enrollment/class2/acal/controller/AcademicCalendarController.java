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
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.dto.AcalEventWrapper;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.KeyDateWrapper;
import org.kuali.student.enrollment.class2.acal.dto.KeyDatesGroupWrapper;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.class2.acal.form.CalendarSearchForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
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
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This controller handles all the request from Academic calendar UI.
 *
 * These are the related xmls from where requests come to this controller
 * 1. AcademicCalendarView.xml
 * 2. AcademicCalendarEditPage.xml
 * 3. AcademicCalendarCopyPage.xml
 * 4. AcademicTermPage.xml
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/academicCalendar")
public class AcademicCalendarController extends UifControllerBase {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(AcademicCalendarController.class);

    private AcademicCalendarService acalService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new AcademicCalendarForm();
    }

    /**
     * This method loads an academic calendar based on the parameters passed into the request.
     *
     * These are the supported request parameters (specific to Acal)
     * 1. id - Academic Calendar Id to load in to UI
     * 2. readOnlyView - If true, sets the view as read only
     * 3. selectTab - can be 'info' or 'term'
     *
     */
    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        AcademicCalendarForm acalForm = (AcademicCalendarForm) form;

        String acalId = request.getParameter(CalendarConstants.CALENDAR_ID);

        if (StringUtils.isNotBlank(acalId)){
            getAcalViewHelperService(acalForm).populateAcademicCalendar(acalId, acalForm);
        }

        String readOnlyView = request.getParameter(CalendarConstants.READ_ONLY_VIEW);
        acalForm.getView().setReadOnly(BooleanUtils.toBoolean(readOnlyView));

        if (StringUtils.isNotBlank(request.getParameter(CalendarConstants.SELECT_TAB))) {
            acalForm.setDefaultTabToShow(request.getParameter(CalendarConstants.SELECT_TAB));
        }

        return super.start(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=reload")
    public ModelAndView reload(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) {
        String acalId = acalForm.getAcademicCalendarInfo().getId();
        getAcalViewHelperService(acalForm).populateAcademicCalendar(acalId, acalForm);
        acalForm.setReload(false);
        return getUIFModelAndView(acalForm, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
    }

    /**
     * This method creates a blank academic calendar.
     */
    @RequestMapping(params = "methodToCall=createBlankCalendar")
    public ModelAndView createBlankCalendar(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response){

        acalForm.setAcademicCalendarInfo(new AcademicCalendarInfo());
        acalForm.setEvents(new ArrayList<AcalEventWrapper>());
        acalForm.setHolidayCalendarList(new ArrayList<HolidayCalendarWrapper>());
        acalForm.setTermWrapperList(new ArrayList<AcademicTermWrapper>());

        return getUIFModelAndView(acalForm, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
    }

    /**
     * This method is called before the Create New Academic Calendar page is displayed
     *
     * It fills in the original Acal for the form with the latest calendar found, by default
     */
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=startNew")
    public ModelAndView startNew( @ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) {

        try {
            AcademicCalendarInfo acalInfo = getAcalViewHelperService(acalForm).getLatestAcademicCalendar();
            acalForm.setCopyFromAcal(acalInfo);
        } catch (Exception e) {
            throw getAcalViewHelperService(acalForm).convertServiceExceptionsToUI(e);
        }

        return getUIFModelAndView(acalForm);
    }

    /**
     * This method will be called when user clicks on the edit link at the header portion of acal.
     *
     * @param acalForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=toEdit")
    public ModelAndView toEdit(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){
        AcademicCalendarInfo acalInfo = acalForm.getAcademicCalendarInfo();
        AcademicCalendarInfo orgAcalInfo = acalForm.getCopyFromAcal();

        if (StringUtils.isBlank(acalInfo.getId()) && StringUtils.isNotBlank(orgAcalInfo.getId())){
            getAcalViewHelperService(acalForm).populateAcademicCalendar(orgAcalInfo.getId(), acalForm);
            acalForm.setCopyFromAcal(new AcademicCalendarInfo());
        }

        acalForm.getView().setReadOnly(false);

        return getUIFModelAndView(acalForm, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
    }

    /**
     * Passes on request from Acal view to copy the Acal to the copyForNew method.  Needed a forwarder here because the
     * call is a GET rather than a POST
     *
     * @param acalForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=toCopy")
    public ModelAndView toCopy(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){

        AcademicCalendarInfo acalInfo = null;

        String acalId = request.getParameter(CalendarConstants.CALENDAR_ID);
        if (acalId != null && !acalId.trim().isEmpty()) {
            String pageId = request.getParameter(CalendarConstants.PAGE_ID);
            if (CalendarConstants.ACADEMIC_CALENDAR_COPY_PAGE.equals(pageId)) {

                try {
                    acalInfo= getAcalService().getAcademicCalendar(acalId, getAcalViewHelperService(acalForm).createContextInfo());
                    acalForm.setCopyFromAcal(acalInfo);
                } catch (Exception ex) {
                    throw getAcalViewHelperService(acalForm).convertServiceExceptionsToUI(ex);
                }
            }
        }
        else {
            // try to get the AC from the form (in case of copy from the Acal view page) and set it as the Original Acal
            try {
                acalInfo = acalForm.getAcademicCalendarInfo();
                acalForm.reset();
                acalForm.setNewCalendar(true);
                acalForm.setCopyFromAcal(acalInfo);
            }
            catch (Exception e) {
                throw getAcalViewHelperService(acalForm).convertServiceExceptionsToUI(e);
            }

        }

        return copy(acalForm, result, request, response);
    }

    /**
     * Copy over from the existing AcalInfo to create a new.
     *
     * @param acalForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params="methodToCall=copy")
    public ModelAndView copy( @ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {

        getAcalViewHelperService(acalForm).copyToCreateAcademicCalendar(acalForm);

        return getUIFModelAndView(acalForm, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);

    }

    //Editing Hcal is implemented fully and it's working.. but we're having some issues with KRAD form management
    //This will be implemented in future milestones
    /*@RequestMapping(method = RequestMethod.POST, params="methodToCall=editHolidayCalendar")
    public ModelAndView editHolidayCalendar( @ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = acalForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = acalForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("unable to determine the selected line index");
        }

        Properties hcalURLParam = new Properties();
        hcalURLParam.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, CalendarConstants.HC_EDIT_METHOD);
        hcalURLParam.put(CalendarConstants.CALENDAR_ID, acalForm.getHolidayCalendarList().get(selectedLineIndex).getId());
        hcalURLParam.put(UifParameters.VIEW_ID, CalendarConstants.HOLIDAYCALENDAR_FLOWVIEW);
        hcalURLParam.put(UifParameters.PAGE_ID, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
        hcalURLParam.put(UifParameters.RETURN_FORM_KEY, acalForm.getFormKey());

        Properties acalReturnURLParams = new Properties();
        acalReturnURLParams.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, CalendarConstants.AC_EDIT_METHOD);
        acalReturnURLParams.put(UifParameters.VIEW_ID, CalendarConstants.ACAL_VIEW);
        acalReturnURLParams.put(UifParameters.PAGE_ID, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
        String returnUrl = UrlFactory.parameterizeUrl(CalendarConstants.ACAL_CONTROLLER_PATH, acalReturnURLParams);
        hcalURLParam.put(UifParameters.RETURN_LOCATION, returnUrl);

        return super.performRedirect(acalForm,CalendarConstants.HCAL_CONTROLLER_PATH, hcalURLParam);

    }*/


    /**
     * Redirects the user to the search Calendar page
     *
     * @param acalForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) {

        String controllerPath = CalendarConstants.CALENDAR_SEARCH_CONTROLLER_PATH;
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        urlParameters.put(UifParameters.VIEW_ID, CalendarConstants.CALENDAR_SEARCH_VIEW);
        urlParameters.put(UifConstants.UrlParams.SHOW_HISTORY, BooleanUtils.toStringTrueFalse(false));
        return super.performRedirect(acalForm,controllerPath, urlParameters);
    }

    /**
     * This will save the academic calendar and terms.  Uses dialog confirmations to verify changes from draft to
     * offical state.
     *
     * @param academicCalendarForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) {
        boolean makeOfficial = academicCalendarForm.isMakeOfficial();
        ModelAndView page;
        Properties urlParameters = new Properties();
        String dialog=null;

        // Find any terms that are marked to be made official
        List<AcademicTermWrapper> terms = academicCalendarForm.getTermWrapperList();
        ArrayList<Integer> termsToMakeOfficial = new ArrayList<Integer>();
        for(int i=0;i<terms.size();i++){
            if(terms.get(i).isMakeOfficial()){
                termsToMakeOfficial.add(i);
            }
        }

        // Check if terms were found to make official
        if(termsToMakeOfficial.size()>0){
            makeOfficial=true;

            // Select dialog for confirmation based on calendars official status
            if(academicCalendarForm.isOfficialCalendar()){
                dialog = CalendarConstants.ACADEMIC_TERM_OFFICIAL_CONFIRMATION_DIALOG;
            }else{
                dialog = CalendarConstants.ACADEMIC_TERM_AND_CALENDAR_OFFICIAL_CONFIRMATION_DIALOG;
            }

            // Dialog confirmation to make terms official
            if (!hasDialogBeenDisplayed(dialog, academicCalendarForm)) {
                if(termsToMakeOfficial.size()>1){
                    academicCalendarForm.setMakeOfficialName(terms.get(termsToMakeOfficial.get(0)).getName()+" and others");
                }else{
                    academicCalendarForm.setMakeOfficialName(terms.get(termsToMakeOfficial.get(0)).getName());
                }
                //redirect back to client to display lightbox
                return showDialog(dialog, academicCalendarForm, request, response);
            }else{
                if(hasDialogBeenAnswered(dialog,academicCalendarForm)){
                    boolean confirm = getBooleanDialogResponse(dialog, academicCalendarForm, request, response);
                    academicCalendarForm.getDialogManager().resetDialogStatus(dialog);
                    if(!confirm){
                        return getUIFModelAndView(academicCalendarForm);
                    }
                } else {

                    //redirect back to client to display lightbox
                    return showDialog(dialog, academicCalendarForm, request, response);
                }
            }
        }


        // Check if making calendar or terms official.
        if(makeOfficial){

            // If confirmation for terms has not been displayed do so for the calendar
            if(dialog==null){

                // Dialog confirmation to make the calendar official
                dialog = CalendarConstants.ACADEMIC_CALENDAR_OFFICIAL_CONFIRMATION_DIALOG;
                if (!hasDialogBeenDisplayed(dialog, academicCalendarForm)) {
                     academicCalendarForm.setMakeOfficialName(academicCalendarForm.getAcademicCalendarInfo().getName());
                    //redirect back to client to display lightbox
                    return showDialog(dialog, academicCalendarForm, request, response);
                }else{
                    if(hasDialogBeenAnswered(dialog,academicCalendarForm)){
                        boolean confirm = getBooleanDialogResponse(dialog, academicCalendarForm, request, response);
                        academicCalendarForm.getDialogManager().resetDialogStatus(dialog);
                        if(!confirm){
                            return getUIFModelAndView(academicCalendarForm);
                        }
                    } else {

                        //redirect back to client to display lightbox
                        return showDialog(dialog, academicCalendarForm, request, response);
                    }
                }
            }

            // Save and make the selected page official
            page = saveAcademicCalendar(academicCalendarForm, CalendarConstants.MessageKeys.INFO_ACADEMIC_CALENDAR_OFFICIAL, true);

            // Make any selected terms official
            for(int i=0;i<termsToMakeOfficial.size();i++){
               makeTermOfficial(terms.get(termsToMakeOfficial.get(i)),academicCalendarForm);
            }

            urlParameters.put(CalendarConstants.GROWL_MESSAGE, CalendarConstants.MessageKeys.INFO_HOLIDAY_CALENDAR_OFFICIAL);
        }else{
            // Save the calendar and retrieve the page
            page = saveAcademicCalendar(academicCalendarForm, CalendarConstants.MessageKeys.INFO_ACADEMIC_CALENDAR_SAVED, false);
            urlParameters.put(CalendarConstants.GROWL_MESSAGE, CalendarConstants.MessageKeys.INFO_ACADEMIC_CALENDAR_SAVED);
        }

        // Check for errors or warnings during the save
        if(GlobalVariables.getMessageMap().getErrorCount()>0 ||GlobalVariables.getMessageMap().getWarningCount()>0){
            // If errors or warnings present redisplay page for correction
            return page;
        }

        // If page is saved without errors or warnings populate growl messaging information and redirect to search page as no further work is needed on page.
        urlParameters.put(CalendarConstants.GROWL_TITLE,"");
        urlParameters.put(CalendarConstants.GROWL_MESSAGE_PARAMS,academicCalendarForm.getAcademicCalendarInfo().getName());
        return redirectToSearch(academicCalendarForm, request, urlParameters);
    }

    /**
     * Method used to delete AcademicCalendar
     *
     * @param acalForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) {
        String dialog = CalendarConstants.ACADEMIC_DELETE_CONFIRMATION_DIALOG;
        if (!hasDialogBeenDisplayed(dialog, acalForm)) {

            //redirect back to client to display lightbox
            return showDialog(dialog, acalForm, request, response);
        }else{
            if(hasDialogBeenAnswered(dialog,acalForm)){
                boolean confirmDelete = getBooleanDialogResponse(dialog, acalForm, request, response);
                acalForm.getDialogManager().resetDialogStatus(dialog);
                if(!confirmDelete){
                    return getUIFModelAndView(acalForm);
                }
            } else {

                //redirect back to client to display lightbox
                return showDialog(dialog, acalForm, request, response);
            }
        }

        StatusInfo statusInfo = null;
        try {
            statusInfo = getAcalService().deleteAcademicCalendar(acalForm.getAcademicCalendarInfo().getId(), getAcalViewHelperService(acalForm).createContextInfo());
        } catch (Exception e) {
            throw getAcalViewHelperService(acalForm).convertServiceExceptionsToUI(e);
        }

        if (statusInfo.getIsSuccess()){
            // If delete successful, populate growl message parameters and redirect to the search page.
            Properties urlParameters = new Properties();
            urlParameters.put(CalendarConstants.GROWL_TITLE,"");
            urlParameters.put(CalendarConstants.GROWL_MESSAGE, CalendarConstants.MessageKeys.INFO_ACADEMIC_CALENDAR_DELETED);
            urlParameters.put(CalendarConstants.GROWL_MESSAGE_PARAMS,acalForm.getAcademicCalendarInfo().getName());
            return redirectToSearch(acalForm,request,urlParameters);
        } else {
            GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_DELETING, acalForm.getAcademicCalendarInfo().getName(),statusInfo.getMessage());
            return getUIFModelAndView(acalForm);
        }


    }

    /**
     * Cancel editing a term. This will undo all the changes done by the users. Basically, it loads the term info
     * from the DB.
     *
     * @param academicCalendarForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=cancelTerm")
    public ModelAndView cancelTerm(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        int selectedLineIndex = KSControllerHelper.getSelectedCollectionLineIndex(academicCalendarForm);

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(selectedLineIndex);

        AcademicCalendarViewHelperService viewHelperService = getAcalViewHelperService(academicCalendarForm);

        if (termWrapper.isNew()){
           academicCalendarForm.getTermWrapperList().remove(selectedLineIndex);
        }else{
            try {
                TermInfo termInfo = getAcalService().getTerm(termWrapper.getTermInfo().getId(), viewHelperService.createContextInfo());
                boolean calculateInstrDays = !academicCalendarForm.getHolidayCalendarList().isEmpty();
                AcademicTermWrapper termWrapperFromDB = viewHelperService.populateTermWrapper(termInfo, false,calculateInstrDays);
                academicCalendarForm.getTermWrapperList().set(selectedLineIndex,termWrapperFromDB);
            } catch (Exception e) {
                throw getAcalViewHelperService(academicCalendarForm).convertServiceExceptionsToUI(e);
            }
        }

        return getUIFModelAndView(academicCalendarForm);
    }

    /**
     *
     * Improvement jira KSENROLL-3568  to clear all the fields at the client side instead of at server side.
     * @param academicCalendarForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=cancelAddingHoliday")
    public ModelAndView cancelAddingHoliday(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        ((HolidayCalendarWrapper)academicCalendarForm.getNewCollectionLines().get("holidayCalendarList")).setId(StringUtils.EMPTY);

        return getUIFModelAndView(academicCalendarForm);
    }

    /**
     * This would mark a term for deletion if it's already there in the DB. If it's a newly added term, it just deletes
     * that.
     *
     * @param academicCalendarForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=deleteTerm")
    public ModelAndView deleteTerm(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        String dialog = CalendarConstants.TERM_DELETE_CONFIRMATION_DIALOG;
        if (!hasDialogBeenDisplayed(dialog, academicCalendarForm)) {
            //redirect back to client to display lightbox
            academicCalendarForm.setSelectedCollectionPath(academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH));
            academicCalendarForm.setSelectedLineIndex(academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
            return showDialog(dialog, academicCalendarForm, request, response);
        }else{
            if(hasDialogBeenAnswered(dialog,academicCalendarForm)){
                boolean confirmDelete = getBooleanDialogResponse(dialog, academicCalendarForm, request, response);
                academicCalendarForm.getDialogManager().resetDialogStatus(dialog);
                if(!confirmDelete){
                    return getUIFModelAndView(academicCalendarForm);
                }
            } else {
                //redirect back to client to display lightbox
                academicCalendarForm.setSelectedCollectionPath(academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH));
                academicCalendarForm.setSelectedLineIndex(academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
                return showDialog(dialog, academicCalendarForm, request, response);
            }
        }

        academicCalendarForm.getActionParameters().put(UifParameters.SELLECTED_COLLECTION_PATH, academicCalendarForm.getSelectedCollectionPath());
        if (academicCalendarForm.getSelectedLineIndex() != null && academicCalendarForm.getSelectedLineIndex().indexOf(",") > -1) {
            academicCalendarForm.getActionParameters().put(UifParameters.SELECTED_LINE_INDEX, academicCalendarForm.getSelectedLineIndex().substring(0,academicCalendarForm.getSelectedLineIndex().indexOf(",")));
        } else {
            academicCalendarForm.getActionParameters().put(UifParameters.SELECTED_LINE_INDEX, academicCalendarForm.getSelectedLineIndex());
        }

        int selectedLineIndex = KSControllerHelper.getSelectedCollectionLineIndex(academicCalendarForm);

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(selectedLineIndex);

        if (!termWrapper.isNew()){
            academicCalendarForm.getTermsToDeleteOnSave().add(termWrapper);
        }

        academicCalendarForm.getTermWrapperList().remove(selectedLineIndex);

        return getUIFModelAndView(academicCalendarForm);
    }

    /**
     * Like term, this would mark a key date for deletion. If it's newly added, just deletes it.
     *
     * @param academicCalendarForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=deleteKeyDate")
    public ModelAndView deleteKeyDate(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = KSControllerHelper.getSelectedCollectionLineIndex(academicCalendarForm);

        String selectedTermIndex = StringUtils.substringBetween(selectedCollectionPath,"termWrapperList[","]");
        String selectedKeyDateGroup = StringUtils.substringBetween(selectedCollectionPath,"keyDatesGroupWrappers[","]");

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(Integer.parseInt(selectedTermIndex));
        KeyDatesGroupWrapper keydateGroup = termWrapper.getKeyDatesGroupWrappers().get(Integer.parseInt(selectedKeyDateGroup));
        KeyDateWrapper keyDateWrapper = keydateGroup.getKeydates().get(selectedLineIndex);

        if (StringUtils.isNotBlank(keyDateWrapper.getKeyDateInfo().getId())){
            termWrapper.getKeyDatesToDeleteOnSave().add(keyDateWrapper);
        }

        keydateGroup.getKeydates().remove(selectedLineIndex);

        return getUIFModelAndView(academicCalendarForm);

    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=deleteKeyDateGroup")
    public ModelAndView deleteKeyDateGroup(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = KSControllerHelper.getSelectedCollectionLineIndex(academicCalendarForm);

        String selectedTermIndex = StringUtils.substringBetween(selectedCollectionPath, "termWrapperList[", "]");

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(Integer.parseInt(selectedTermIndex));
        KeyDatesGroupWrapper keydateGroup = termWrapper.getKeyDatesGroupWrappers().get(selectedLineIndex);
        for (KeyDateWrapper keyDateWrapper : keydateGroup.getKeydates()) {
            if (StringUtils.isNotBlank(keyDateWrapper.getKeyDateInfo().getId())){
                termWrapper.getKeyDatesToDeleteOnSave().add(keyDateWrapper);
            }
        }

        termWrapper.getKeyDatesGroupWrappers().remove(keydateGroup);

        return getUIFModelAndView(academicCalendarForm);

    }

    private AcademicCalendarInfo processHolidayCalendars(AcademicCalendarForm academicCalendarForm)    {
        AcademicCalendarInfo acalInfo = academicCalendarForm.getAcademicCalendarInfo();
        List<HolidayCalendarWrapper> holidayCalendarList = academicCalendarForm.getHolidayCalendarList();
        List<String> holidayCalendarIds = new ArrayList<String>();
        if (holidayCalendarList != null && !holidayCalendarList.isEmpty()) {
            for (HolidayCalendarWrapper hcWrapper : holidayCalendarList){
                holidayCalendarIds.add(hcWrapper.getHolidayCalendarInfo().getId());
            }
        }

        // if the list from the form is empty, then all holiday calendars have been removed (or none have been assigned)
        // so an empty list will be assigned to the AcademicCalendarInfo
        acalInfo.setHolidayCalendarIds(holidayCalendarIds);
        academicCalendarForm.setAcademicCalendarInfo(acalInfo);

        return acalInfo;
    }

    private ModelAndView saveAcademicCalendar(AcademicCalendarForm academicCalendarForm, String keyToDisplayOnSave, boolean isOfficial) {

        if (LOG.isDebugEnabled()){
            LOG.debug("Saving Academic Calendar...");
        }

        AcademicCalendarInfo academicCalendarInfo = academicCalendarForm.getAcademicCalendarInfo();
        AcademicCalendarViewHelperService viewHelperService = getAcalViewHelperService(academicCalendarForm);

        // Populate default times
        viewHelperService.populateAcademicCalendarDefaults(academicCalendarForm);

        // Validate Acal
        viewHelperService.validateAcademicCalendar(academicCalendarForm);
        if (GlobalVariables.getMessageMap().getErrorCount() > 0){
            return getUIFModelAndView(academicCalendarForm);
        }

        // If validation succeeds, continue save
        try {
            if (StringUtils.isNotBlank(academicCalendarInfo.getId())) {
                // 1. update acal and AC-HC relationships
                academicCalendarInfo = processHolidayCalendars(academicCalendarForm);
                AcademicCalendarInfo acalInfo = getAcalService().updateAcademicCalendar(academicCalendarInfo.getId(), academicCalendarInfo, viewHelperService.createContextInfo());
                //academicCalendarForm.setAcademicCalendarInfo(getAcalService().getAcademicCalendar(acalInfo.getId(), viewHelperService.createContextInfo()));
                academicCalendarForm.setAcademicCalendarInfo(acalInfo);

                // 2. update acalEvents if any
                List<AcalEventWrapper> events = academicCalendarForm.getEvents();
                processEvents(academicCalendarForm, events, acalInfo.getId());
            } else {
                AcademicCalendarInfo acalInfo = null;
                // 1. create  a new acalInfo with a list of HC Ids
                processHolidayCalendars(academicCalendarForm);
                acalInfo = viewHelperService.createAcademicCalendar(academicCalendarForm);
                academicCalendarForm.setAcademicCalendarInfo(getAcalService().getAcademicCalendar(acalInfo.getId(), viewHelperService.createContextInfo()));
                // 2. create new events if any
                createEvents(acalInfo.getId(), academicCalendarForm);
            }
        } catch (VersionMismatchException e){
            academicCalendarForm.setReload(true);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, RiceKeyConstants.ERROR_CUSTOM,"You are saving an older version of this calendar. Please click on the reload button to get the newer version.");
            return getUIFModelAndView(academicCalendarForm, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
        } catch(Exception e) {
            if (LOG.isDebugEnabled()){
                LOG.error("Add/update Academic calendar failed - " + e.getMessage());
            }
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_ACAL_SAVE_FAILED);
            return getUIFModelAndView(academicCalendarForm, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
        }

        // Delete terms which are deleted by the user in the ui
        for (AcademicTermWrapper termWrapper : academicCalendarForm.getTermsToDeleteOnSave()){
            String termId = termWrapper.getTermInfo().getId();
            try {
                getAcalService().deleteTerm(termId, viewHelperService.createContextInfo());
            } catch(Exception e) {
                LOG.error(String.format("Unable to delete term [%s].", termId), e);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_ACAL_SAVE_FAILED);
                return getUIFModelAndView(academicCalendarForm, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
            }
        }

        academicCalendarForm.getTermsToDeleteOnSave().clear();

        boolean calculateInstrDays = !academicCalendarForm.getHolidayCalendarList().isEmpty();

        // Save Term and keydates
        for (AcademicTermWrapper termWrapper : academicCalendarForm.getTermWrapperList()){
            try {
                viewHelperService.saveTerm(termWrapper, academicCalendarForm.getAcademicCalendarInfo().getId(), false,calculateInstrDays);
            } catch(Exception e) {
                if (LOG.isDebugEnabled()){
                    LOG.error(String.format("Unable to save term [%s].", termWrapper.getName()), e);
                }
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_ACAL_SAVE_TERM_SAVE_FAILED,
                        termWrapper.getName(), e.getLocalizedMessage());
                return getUIFModelAndView(academicCalendarForm, CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
            }
        }

        if (isOfficial){
            StatusInfo statusInfo = null;
            try {
                statusInfo = getAcalService().changeAcademicCalendarState(academicCalendarForm.getAcademicCalendarInfo().getId(), AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_OFFICIAL_STATE_KEY,viewHelperService.createContextInfo());
                if (!statusInfo.getIsSuccess()){
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, RiceKeyConstants.ERROR_CUSTOM, statusInfo.getMessage());
                } else{
                    academicCalendarForm.setAcademicCalendarInfo(getAcalService().getAcademicCalendar(academicCalendarForm.getAcademicCalendarInfo().getId(), viewHelperService.createContextInfo()));
                    academicCalendarForm.setOfficialCalendar(true);
                    academicCalendarForm.getView().setReadOnly(true);
                    for (AcalEventWrapper eventWrapper : academicCalendarForm.getEvents()) {
                        eventWrapper.setAcalEventInfo(getAcalService().getAcalEvent(eventWrapper.getAcalEventInfo().getId(),viewHelperService.createContextInfo()));
                    }
                }
            } catch (Exception e) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_ACAL_SAVE_FAILED + " - " + e.getMessage());
            }
        }

        academicCalendarForm.setNewCalendar(false);

        GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_MESSAGES, keyToDisplayOnSave, academicCalendarForm.getAcademicCalendarInfo().getName());


        return getUIFModelAndView(academicCalendarForm);
    }

    private void createEvents(String acalId, AcademicCalendarForm acalForm) throws Exception {
        List<AcalEventWrapper> events = acalForm.getEvents();

        AcademicCalendarViewHelperService viewHelperService = getAcalViewHelperService(acalForm);

        if(events != null && !events.isEmpty()){
            List<AcalEventWrapper> createdEvents = new ArrayList<AcalEventWrapper>();
            for (AcalEventWrapper event : events){
                createdEvents.add(viewHelperService.createEvent(acalId, event,false));
            }
            acalForm.setEvents(createdEvents);
        }

    }

    /**
     * Update existing events, create new events, and delete events that do not exist any more when a user modifies and saves an Academic Calendar
     */
    private void processEvents(AcademicCalendarForm acalForm, List<AcalEventWrapper> events, String acalId) throws Exception{
        List<AcalEventWrapper> updatedEvents = new ArrayList<AcalEventWrapper>();
        List<String> currentEventIds = getExistingEventIds(acalForm);
        AcademicCalendarViewHelperService viewHelperService = getAcalViewHelperService(acalForm);
        if(events != null && !events.isEmpty()){
            for(AcalEventWrapper event : events){
                if(currentEventIds.contains(event.getAcalEventInfo().getId())){
                    //update event
                    AcalEventWrapper updatedEvent = viewHelperService.updateEvent(event.getAcalEventInfo().getId(), event);
                    updatedEvents.add(updatedEvent);
                    currentEventIds.remove(event.getAcalEventInfo().getId());
                }
                else {
                    //If acal is already official, set the Event as official
                    boolean isAcalOfficial = StringUtils.equals(acalForm.getAcademicCalendarInfo().getStateKey(),AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
                    //create a new event
                    AcalEventWrapper createdEvent = viewHelperService.createEvent(acalId, event,isAcalOfficial);
                    updatedEvents.add(createdEvent);
                }
            }
        }
        acalForm.setEvents(updatedEvents);

        //delete events that have been removed by the user
        if (currentEventIds != null && currentEventIds.size() > 0){
            for(String eventId: currentEventIds){
                getAcalService().deleteAcalEvent(eventId, getAcalViewHelperService(acalForm).createContextInfo());
            }
        }

    }

    private List<String> getExistingEventIds(AcademicCalendarForm acalForm) throws Exception{
        AcademicCalendarViewHelperService viewHelperService = getAcalViewHelperService(acalForm);
        List<AcalEventWrapper> events = viewHelperService.populateEventWrappers(acalForm.getAcademicCalendarInfo().getId());
        List<String> eventIds = new ArrayList<String>();

        if(events != null && !events.isEmpty()){
            for(AcalEventWrapper event : events){
                eventIds.add(event.getAcalEventInfo().getId());
            }
        }
        return eventIds;
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }

    protected AcademicCalendarViewHelperService getAcalViewHelperService(AcademicCalendarForm acalForm){
        AcademicCalendarViewHelperService viewHelperService = (AcademicCalendarViewHelperService) KSControllerHelper.getViewHelperService(acalForm);
        return viewHelperService;
    }

    /**
     * Redirects from an academic calendar page to the calendar search page
     *
     * @param academicCalendarForm - Calendar form backing the page
     * @param request - Http requests parameters
     * @param urlParameters - Additional parameters to pass when redirecting
     * @return The Calendar search page.
     */
    private ModelAndView redirectToSearch(AcademicCalendarForm academicCalendarForm,HttpServletRequest request, Properties urlParameters){
        urlParameters.put("viewId", CalendarConstants.CALENDAR_SEARCH_VIEW);
        urlParameters.put("methodToCall", KRADConstants.START_METHOD);
        urlParameters.put(UifConstants.UrlParams.SHOW_HISTORY, BooleanUtils.toStringTrueFalse(false));
        String uri = request.getRequestURL().toString().replace("academicCalendar","calendarSearch");
        return performRedirect(academicCalendarForm, uri, urlParameters);
    }

    /**
     * Changes the state of the term from draft to official
     *
     * @param term - The Term to make official
     * @param acalForm - THe page form
     * @return true if the term was made official successfully, false otherwise.
     */
    private boolean makeTermOfficial(AcademicTermWrapper term, AcademicCalendarForm acalForm){
        AcademicCalendarViewHelperService viewHelperService = getAcalViewHelperService(acalForm);

        try{
            boolean alreadyOfficial = StringUtils.equals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY,acalForm.getAcademicCalendarInfo().getStateKey());
            String messageText = alreadyOfficial ? "info.enroll.term.saved" : "info.enroll.term.official";
            boolean calculateInstrDays = !acalForm.getHolidayCalendarList().isEmpty();
            viewHelperService.saveTerm(term, acalForm.getAcademicCalendarInfo().getId(), true,calculateInstrDays);
            if (!GlobalVariables.getMessageMap().hasErrors()){
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, messageText, term.getTermNameForUI());
            }
        }catch (Exception e){
            if (LOG.isDebugEnabled()){
                LOG.debug("Error Saving term " + term.getTermNameForUI() + " - " + e.getMessage());
            }
            throw viewHelperService.convertServiceExceptionsToUI(e);
        }
        return true;
    }

}
