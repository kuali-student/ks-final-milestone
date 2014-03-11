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
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.control.SelectControl;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.util.UifKeyValue;
import org.kuali.rice.krad.uif.view.DialogManager;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.dto.AcalEventWrapper;
import org.kuali.student.enrollment.class2.acal.dto.ExamPeriodWrapper;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.HolidayWrapper;
import org.kuali.student.enrollment.class2.acal.dto.KeyDateWrapper;
import org.kuali.student.enrollment.class2.acal.dto.KeyDatesGroupWrapper;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.enrollment.class2.acal.util.AcalCommonUtils;
import org.kuali.student.enrollment.common.util.EnrollConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.AcalEventInfo;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.acal.service.facade.AcademicCalendarServiceFacade;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
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

    private static final Logger LOG = LoggerFactory.getLogger(AcademicCalendarController.class);

    private AcademicCalendarService acalService;
    private AcademicCalendarServiceFacade academicCalendarServiceFacade;

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

        return getUIFModelAndView(form);
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

        Properties urlParameters = new Properties();
        urlParameters.put(UifParameters.VIEW_ID, CalendarConstants.ACAL_VIEW);
        urlParameters.put("flow", acalForm.getFlowKey());
        urlParameters.put(CalendarConstants.PAGE_ID,CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "startNew");
        String controllerPath = CalendarConstants.ACAL_CONTROLLER_PATH;
        return super.performRedirect(acalForm,controllerPath, urlParameters);

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
            acalForm.setMeta(acalInfo.getMeta());
        } catch (Exception e) {
            throw getAcalViewHelperService(acalForm).convertServiceExceptionsToUI(e);
        }

        return super.start(acalForm, result, request, response);
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

        // Anything that extends KSUIFForm.java will have meta information
        if(acalInfo != null){
            acalForm.setMeta(acalInfo.getMeta());
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
        // UrlParams.SHOW_HISTORY and SHOW_HOME no longer exist
        // https://fisheye.kuali.org/changelog/rice?cs=39034
        // TODO KSENROLL-8469
        //urlParameters.put(UifConstants.UrlParams.SHOW_HISTORY, BooleanUtils.toStringTrueFalse(false));
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
        AcademicCalendarForm acal = saveAcademicCalendarDirtyFields(academicCalendarForm);
        return getUIFModelAndView(acal);
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

        StatusInfo statusInfo;
        try {
            statusInfo = getAcademicCalendarServiceFacade().deleteCalendarCascaded(acalForm.getAcademicCalendarInfo().getId(), getAcalViewHelperService(acalForm).createContextInfo());
        } catch (Exception e) {
            throw getAcalViewHelperService(acalForm).convertServiceExceptionsToUI(e);
        }

        if (statusInfo.getIsSuccess()){
            // If delete successful, populate growl message parameters and redirect to the search page.
            Properties urlParameters = new Properties();
            urlParameters.put(EnrollConstants.GROWL_TITLE,"");
            urlParameters.put(EnrollConstants.GROWL_MESSAGE, CalendarConstants.MessageKeys.INFO_ACADEMIC_CALENDAR_DELETED);
            urlParameters.put(EnrollConstants.GROWL_MESSAGE_PARAMS,acalForm.getAcademicCalendarInfo().getName());
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
        academicCalendarForm.setFieldsToSave(processDirtyFields(academicCalendarForm));
        int selectedLineIndex = KSControllerHelper.getSelectedCollectionLineIndex(academicCalendarForm);

        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(selectedLineIndex);

        AcademicCalendarViewHelperService viewHelperService = getAcalViewHelperService(academicCalendarForm);

        if (termWrapper.isNew()){
            academicCalendarForm.getTermWrapperList().remove(selectedLineIndex);
            if (termWrapper.isHasSubterm()){
                List<AcademicTermWrapper> subTerms = termWrapper.getSubterms();
                for(AcademicTermWrapper subTerm : subTerms){
                    academicCalendarForm.getTermWrapperList().remove(subTerm);
                }
            }
        }else{  //this part will not be called since cancel Term link does not show up after a term is persisted in DB.
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
     * This method makes a term official and persist to DB. User can selectively make a term official.
     *
     * @param academicCalendarForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=makeTermOfficial")
    public ModelAndView makeTermOfficial(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response) {
        academicCalendarForm.setFieldsToSave(processDirtyFields(academicCalendarForm));

        int selectedLineIndex;

        AcademicTermWrapper termWrapper;

        String dialog=null;
        if(academicCalendarForm.isOfficialCalendar()){
            dialog = CalendarConstants.ACADEMIC_TERM_OFFICIAL_CONFIRMATION_DIALOG;
        }else{
            dialog = CalendarConstants.ACADEMIC_TERM_AND_CALENDAR_OFFICIAL_CONFIRMATION_DIALOG;
        }

        // Dialog confirmation to make terms official
        if (!hasDialogBeenDisplayed(dialog, academicCalendarForm)) {
            selectedLineIndex= KSControllerHelper.getSelectedCollectionLineIndex(academicCalendarForm);
            termWrapper= academicCalendarForm.getTermWrapperList().get(selectedLineIndex);
            academicCalendarForm.setSelectedCollectionPath(academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH));
            academicCalendarForm.setSelectedLineIndex(academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
            academicCalendarForm.setMakeOfficialName(termWrapper.getName());
            academicCalendarForm.setMakeOfficialIsSubterm(termWrapper.isSubTerm());
            if(termWrapper.getParentTermInfo()!=null){
                academicCalendarForm.setMakeOfficialParentTermName(termWrapper.getParentTermInfo().getName());
                academicCalendarForm.setOfficialParentTerm(false);
                for(AcademicTermWrapper term : academicCalendarForm.getTermWrapperList()){
                    if(term.getTermInfo().getId().equals(termWrapper.getParentTermInfo().getId())){
                        academicCalendarForm.setOfficialParentTerm(term.isOfficial());
                    }
                }
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
                selectedLineIndex= KSControllerHelper.getSelectedCollectionLineIndex(academicCalendarForm);
                termWrapper= academicCalendarForm.getTermWrapperList().get(selectedLineIndex);
                academicCalendarForm.setSelectedCollectionPath(academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH));
                academicCalendarForm.setSelectedLineIndex(academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
                academicCalendarForm.setMakeOfficialName(termWrapper.getName());
                academicCalendarForm.setMakeOfficialIsSubterm(termWrapper.isSubTerm());
                if(termWrapper.getParentTermInfo()!=null){
                    academicCalendarForm.setMakeOfficialParentTermName(termWrapper.getParentTermInfo().getName());
                    academicCalendarForm.setOfficialParentTerm(false);
                    for(AcademicTermWrapper term : academicCalendarForm.getTermWrapperList()){
                        if(term.getTermInfo().getId().equals(termWrapper.getParentTermInfo().getId())){
                            academicCalendarForm.setOfficialParentTerm(term.isOfficial());
                        }
                    }
                }


                //redirect back to client to display lightbox
                return showDialog(dialog, academicCalendarForm, request, response);
            }
        }

        academicCalendarForm.getActionParameters().put(UifParameters.SELLECTED_COLLECTION_PATH, academicCalendarForm.getSelectedCollectionPath());
        if (academicCalendarForm.getSelectedLineIndex() != null && academicCalendarForm.getSelectedLineIndex().indexOf(",") > -1) {
            academicCalendarForm.getActionParameters().put(UifParameters.SELECTED_LINE_INDEX, academicCalendarForm.getSelectedLineIndex().substring(0,academicCalendarForm.getSelectedLineIndex().indexOf(",")));
        } else {
            academicCalendarForm.getActionParameters().put(UifParameters.SELECTED_LINE_INDEX, academicCalendarForm.getSelectedLineIndex());
        }
        selectedLineIndex= KSControllerHelper.getSelectedCollectionLineIndex(academicCalendarForm);
        termWrapper= academicCalendarForm.getTermWrapperList().get(selectedLineIndex);

        boolean acalOfficial=true;

        if(!academicCalendarForm.isOfficialCalendar()){
            acalOfficial = makeAcalOfficial(academicCalendarForm);
        }

        if(acalOfficial){
            makeTermOfficial(termWrapper,academicCalendarForm);
        }
        getAcalViewHelperService(academicCalendarForm).populateAcademicCalendar(academicCalendarForm.getAcademicCalendarInfo().getId(), academicCalendarForm);
        academicCalendarForm.setDefaultTabToShow(CalendarConstants.ACAL_TERM_TAB);
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
        academicCalendarForm.setFieldsToSave(processDirtyFields(academicCalendarForm));
        ((HolidayCalendarWrapper)academicCalendarForm.getNewCollectionLines().get("holidayCalendarList")).setId(StringUtils.EMPTY);

        return getUIFModelAndView(academicCalendarForm);
    }

    private void setDeleteTermMessageWithContext(AcademicCalendarForm academicCalendarForm){
        academicCalendarForm.setSelectedCollectionPath(academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH));
        academicCalendarForm.setSelectedLineIndex(academicCalendarForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        academicCalendarForm.getActionParameters().put(UifParameters.SELLECTED_COLLECTION_PATH, academicCalendarForm.getSelectedCollectionPath());
        if (academicCalendarForm.getSelectedLineIndex() != null && academicCalendarForm.getSelectedLineIndex().indexOf(",") > -1) {
            academicCalendarForm.getActionParameters().put(UifParameters.SELECTED_LINE_INDEX, academicCalendarForm.getSelectedLineIndex().substring(0,academicCalendarForm.getSelectedLineIndex().indexOf(",")));
        } else {
            academicCalendarForm.getActionParameters().put(UifParameters.SELECTED_LINE_INDEX, academicCalendarForm.getSelectedLineIndex());
        }

        int selectedLineIndex = KSControllerHelper.getSelectedCollectionLineIndex(academicCalendarForm);

        AcademicTermWrapper selectedTermWrapper = academicCalendarForm.getTermWrapperList().get(selectedLineIndex);
        if(selectedTermWrapper.isSubTerm()){
            academicCalendarForm.setMessageForDeleteTermOrSubterm(CalendarConstants.MESSAGE_CONFIRM_TO_DELETE_SUBTERM);
        }else if(selectedTermWrapper.isHasSubterm()){
            academicCalendarForm.setMessageForDeleteTermOrSubterm(CalendarConstants.MESSAGE_CONFIRM_TO_DELETE_TERM_WITH_SUBTERM);
        }else{
            academicCalendarForm.setMessageForDeleteTermOrSubterm(CalendarConstants.MESSAGE_CONFIRM_TO_DELETE_TERM_ONLY);
        }
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
        academicCalendarForm.setFieldsToSave(processDirtyFields(academicCalendarForm));

        String dialog = CalendarConstants.TERM_DELETE_CONFIRMATION_DIALOG;
        if (!hasDialogBeenDisplayed(dialog, academicCalendarForm)) {
            setDeleteTermMessageWithContext(academicCalendarForm);
            //redirect back to client to display lightbox
            return showDialog(dialog, academicCalendarForm, request, response);
        }else{
            if(hasDialogBeenAnswered(dialog,academicCalendarForm)){
                boolean confirmDelete = getBooleanDialogResponse(dialog, academicCalendarForm, request, response);
                academicCalendarForm.getDialogManager().resetDialogStatus(dialog);
                if(!confirmDelete){
                    return getUIFModelAndView(academicCalendarForm);
                }
            } else {
                setDeleteTermMessageWithContext(academicCalendarForm);
                //redirect back to client to display lightbox
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

        AcademicTermWrapper theTermWrapper = academicCalendarForm.getTermWrapperList().get(selectedLineIndex);

        if (!theTermWrapper.isNew()){
            academicCalendarForm.getTermsToDeleteOnSave().add(theTermWrapper);
        }
        academicCalendarForm.getTermWrapperList().remove(selectedLineIndex);

        if(theTermWrapper.isHasSubterm())  { //get all subterms and remove them as well
            List<AcademicTermWrapper> subTerms = theTermWrapper.getSubterms();
            for(AcademicTermWrapper subTerm : subTerms){
                academicCalendarForm.getTermWrapperList().remove(subTerm);
            }
        }
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
        academicCalendarForm.setFieldsToSave(processDirtyFields(academicCalendarForm));
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

        academicCalendarForm.getAddedCollectionItems().remove(keyDateWrapper);

        return getUIFModelAndView(academicCalendarForm);

    }

    /**
     * Like term, this would mark an exam period for deletion.
     *
     * @param academicCalendarForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=markExamPeriodtoDelete")
    public ModelAndView markExamPeriodtoDelete(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) {
        academicCalendarForm.setFieldsToSave(processDirtyFields(academicCalendarForm));
        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = KSControllerHelper.getSelectedCollectionLineIndex(academicCalendarForm);

        String selectedTermIndex = StringUtils.substringBetween(selectedCollectionPath,"termWrapperList[","]");
        AcademicTermWrapper termWrapper = academicCalendarForm.getTermWrapperList().get(Integer.parseInt(selectedTermIndex));
        ExamPeriodWrapper examPeriodWrapper = termWrapper.getExamdates().get(selectedLineIndex);

        if (StringUtils.isNotBlank(examPeriodWrapper.getExamPeriodInfo().getId())){
            termWrapper.getExamPeriodsToDeleteOnSave().add(examPeriodWrapper);
        }
        termWrapper.getExamdates().remove(selectedLineIndex);

        return getUIFModelAndView(academicCalendarForm);

    }

    @RequestMapping(params = "methodToCall=deleteKeyDateGroup")
    public ModelAndView deleteKeyDateGroup(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {
        academicCalendarForm.setFieldsToSave(processDirtyFields(academicCalendarForm));
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

    /**
     * Removes the the selected holiday for delete from the list of holidays.
     * No changes are made to database until save.
     *
     * @param academicCalendarForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=deleteHolidayCalendar")
    public ModelAndView deleteHolidayCalendar(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) {
        academicCalendarForm.setFieldsToSave(processDirtyFields(academicCalendarForm));
        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = KSControllerHelper.getSelectedCollectionLineIndex(academicCalendarForm);

        academicCalendarForm.getHolidayCalendarList().remove(selectedLineIndex);

        return getUIFModelAndView(academicCalendarForm);

    }

    /**
     * Removes the the selected event for delete from the list of events.
     * Event is added to the list of events to be deleted during save if it is in the database.
     * No changes are made to database until save.
     *
     * @param academicCalendarForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=deleteAcalEvent")
    public ModelAndView deleteAcalEvent(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {
        academicCalendarForm.setFieldsToSave(processDirtyFields(academicCalendarForm));
        String selectedCollectionPath = academicCalendarForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("unable to determine the selected collection path");
        }

        int selectedLineIndex = KSControllerHelper.getSelectedCollectionLineIndex(academicCalendarForm);
        AcalEventWrapper deletedEvent = academicCalendarForm.getEvents().get(selectedLineIndex);
        if(deletedEvent.getAcalEventInfo().getId()!=null){
            academicCalendarForm.getEventsToDeleteOnSave().add(deletedEvent);
        }

        academicCalendarForm.getEvents().remove(selectedLineIndex);
        academicCalendarForm.getAddedCollectionItems().remove(deletedEvent);

        return getUIFModelAndView(academicCalendarForm);

    }

    /**
     * Method used to set Acal as official
     */
    @RequestMapping(params = "methodToCall=makeAcalOfficial")
    public ModelAndView makeAcalOfficial(@ModelAttribute("KualiForm") AcademicCalendarForm acalForm, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response) {
        // Dialog confirmation to make the calendar official
        String dialog = CalendarConstants.ACADEMIC_CALENDAR_OFFICIAL_CONFIRMATION_DIALOG;
        if (!hasDialogBeenDisplayed(dialog, acalForm)) {
            acalForm.setMakeOfficialName(acalForm.getAcademicCalendarInfo().getName());
            //redirect back to client to display lightbox
            return showDialog(dialog, acalForm, request, response);
        }else{
            if(hasDialogBeenAnswered(dialog,acalForm)){
                boolean confirm = getBooleanDialogResponse(dialog, acalForm, request, response);
                acalForm.getDialogManager().resetDialogStatus(dialog);
                if(!confirm){
                    return getUIFModelAndView(acalForm);
                }
            } else {

                //redirect back to client to display lightbox
                return showDialog(dialog, acalForm, request, response);
            }
        }

        makeAcalOfficial(acalForm);

        return getUIFModelAndView(acalForm);
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }

    public AcademicCalendarServiceFacade getAcademicCalendarServiceFacade() {
        if(academicCalendarServiceFacade == null) {
            academicCalendarServiceFacade = (AcademicCalendarServiceFacade) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.FACADE_NAMESPACE, AcademicCalendarServiceConstants.FACADE_SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarServiceFacade;
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
        urlParameters.put("methodToCall", KRADConstants.SEARCH_METHOD);
        // UrlParams.SHOW_HISTORY and SHOW_HOME no longer exist
        // https://fisheye.kuali.org/changelog/rice?cs=39034
        // TODO KSENROLL-8469
        //urlParameters.put(UifConstants.UrlParams.SHOW_HISTORY, BooleanUtils.toStringTrueFalse(false));
        String uri = request.getRequestURL().toString().replace("academicCalendar","calendarSearch");
        return performRedirect(academicCalendarForm, uri, urlParameters);
    }

    /**
     * Changes the state of the term from draft to official
     *
     * @param term - The Term to make official
     * @param acalForm - Tee page form
     * @return true if the term was made official successfully, false otherwise.
     */
    private boolean makeTermOfficial(AcademicTermWrapper term, AcademicCalendarForm acalForm){
        AcademicCalendarViewHelperService viewHelperService = getAcalViewHelperService(acalForm);
        StatusInfo statusInfo;
        try {
            // no need to check if the term is a sub term.  makeTermOfficialCascaded method works for both sub term and non-sub term
            statusInfo = getAcademicCalendarServiceFacade().makeTermOfficialCascaded(term.getTermInfo().getId(), viewHelperService.createContextInfo());

            if (!statusInfo.getIsSuccess()) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, RiceKeyConstants.ERROR_CUSTOM, statusInfo.getMessage());
                return false;
            }
            term.setTermInfo(getAcalService().getTerm(term.getTermInfo().getId(),viewHelperService.createContextInfo()));
            if(term.isSubTerm()){ // update the parent term so the view displays the proper states (draft vs official)
                for(AcademicTermWrapper termWrapper : acalForm.getTermWrapperList()){
                    if(termWrapper.getTermInfo().getId().equals(term.getParentTermInfo().getId())){
                        TermInfo updatedParentTerm = getAcalService().getTerm(term.getParentTermInfo().getId(),viewHelperService.createContextInfo());
                        // Make sure the parent term is updated in both the term and the termWrapperList.
                        term.setParentTermInfo(updatedParentTerm);
                        termWrapper.setTermInfo(updatedParentTerm); // the screen looks at this variable
                    }
                }
            }
            for (KeyDatesGroupWrapper groupWrapper : term.getKeyDatesGroupWrappers()){
                for (KeyDateWrapper keyDateWrapper : groupWrapper.getKeydates()) {
                    //...skip [unsaved] KeyDates that have null Id ...to avoid exception
                    //note: the UI policy for this page: user must select 'Save' to save changes as make official does not save anything)
                    if (keyDateWrapper.getKeyDateInfo() != null && keyDateWrapper.getKeyDateInfo().getId()!=null) {
                        keyDateWrapper.setKeyDateInfo(getAcalService().getKeyDate(keyDateWrapper.getKeyDateInfo().getId(),viewHelperService.createContextInfo()));
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Make Official Failed for Term",e);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES,CalendarConstants.MessageKeys.ERROR_ACAL_SAVE_TERM_OFFICIAL_FAILED,e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Attempts to make the academic calendar official.
     *
     * @param acalForm - Tee page form
     * @return True if calendar state is successfully changed
     */
    private boolean makeAcalOfficial(AcademicCalendarForm acalForm){
        AcademicCalendarViewHelperService viewHelperService = getAcalViewHelperService(acalForm);
        try {
            StatusInfo statusInfo = null;
            statusInfo = getAcalService().changeAcademicCalendarState(acalForm.getAcademicCalendarInfo().getId(), AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_OFFICIAL_STATE_KEY,viewHelperService.createContextInfo());
            if (!statusInfo.getIsSuccess()){
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, RiceKeyConstants.ERROR_CUSTOM, statusInfo.getMessage());
                return false;
            } else{
                // Refresh form information
                acalForm.setAcademicCalendarInfo(getAcalService().getAcademicCalendar(acalForm.getAcademicCalendarInfo().getId(), viewHelperService.createContextInfo()));
                acalForm.setOfficialCalendar(true);
                for (AcalEventWrapper eventWrapper : acalForm.getEvents()) {
                    eventWrapper.setAcalEventInfo(getAcalService().getAcalEvent(eventWrapper.getAcalEventInfo().getId(),viewHelperService.createContextInfo()));
                }
            }
        } catch (Exception e) {
            LOG.error("Make Official Failed for Acal",e);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_ACAL_OFFICIAL_FAILED ,e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Saves changes made to an academic calendar.
     *
     * @param academicCalendarForm - Form containing the data from the page.
     * @return The updated calendar after save.
     */
    private AcademicCalendarForm saveAcademicCalendarDirtyFields(AcademicCalendarForm academicCalendarForm){
        AcademicCalendarViewHelperService viewHelperService = getAcalViewHelperService(academicCalendarForm);

        // Convert Raw UI data and prepare it for save.
        viewHelperService.populateAcademicCalendarDefaults(academicCalendarForm);
        // Validate Acal
        viewHelperService.validateAcademicCalendar(academicCalendarForm);

        if (GlobalVariables.getMessageMap().getErrorCount() > 0){
            // If there are errors in the validation return current calendar without saving
            // sort the term wrappers so that error/warning messages can be displayed in the correct sections
            viewHelperService.sortTermWrappers(academicCalendarForm.getTermWrapperList());
            return academicCalendarForm;
        }

        // Create list of dirty fields by seperating string from page (See enroll.js:saveAcalPreProcess())
        List<String> dirtyFields = processDirtyFields(academicCalendarForm);

        // Save the base Academic calendar info and refresh it in the form
        AcademicCalendarInfo newAcal = saveAcal(academicCalendarForm.getAcademicCalendarInfo(), academicCalendarForm, viewHelperService);
        academicCalendarForm.setAcademicCalendarInfo(newAcal);

        // Save the dirty field information
        academicCalendarForm = saveDirtyFieldChanges(academicCalendarForm, dirtyFields,viewHelperService);

        // Check for new Acal Events and save them
        academicCalendarForm = saveAcalEvents(academicCalendarForm,viewHelperService);

        // Check for deleted Acal Events and delete them from database
        deleteAcalEvents(academicCalendarForm,viewHelperService);

        // Check for new terms and save them
        academicCalendarForm = saveTerms(academicCalendarForm,viewHelperService);

        // Check for deleted terms and delete them from database
        deleteTerms(academicCalendarForm, viewHelperService);

        // Check keydates in terms
        for(int i = 0; i < academicCalendarForm.getTermWrapperList().size();i++){
            // Check for new key dates and save them
            academicCalendarForm = saveKeyDates(academicCalendarForm,i,viewHelperService);

            // Check for deleted key dates and delete them from database
            AcademicTermWrapper term = academicCalendarForm.getTermWrapperList().get(i);
            deleteKeyDates(term, viewHelperService);

            // update instructionalDays
            try{
                term.setInstructionalDays(getAcalService().getInstructionalDaysForTerm(term.getTermInfo().getId(), viewHelperService.createContextInfo()));
            }catch (Exception ex){
                // If the lookup fails message user
                LOG.error("Unable to load instructional days",ex);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_CALCULATING_INSTRUCTIONAL_DAYS, term.getStartDate().toString(), term.getEndDate().toString());
            }

            //process exam periods
            academicCalendarForm = processExamPeriods(academicCalendarForm,i,viewHelperService);
        }

        // Reset values
        academicCalendarForm.getEventsToDeleteOnSave().clear();
        academicCalendarForm.getFieldsToSave().clear();
        academicCalendarForm.setDirtyFields("");
        academicCalendarForm.getTermsToDeleteOnSave().clear();
        academicCalendarForm.setNewCalendar(false);

        // Successful save message
        if(!(GlobalVariables.getMessageMap().getErrorCount()>0)){
            GlobalVariables.getMessageMap().addGrowlMessage("", CalendarConstants.MessageKeys.INFO_ACADEMIC_CALENDAR_SAVED, academicCalendarForm.getAcademicCalendarInfo().getName());
        }

        academicCalendarForm.setMeta(academicCalendarForm.getAcademicCalendarInfo().getMeta());

        // Reseting added collection items since they were saved
        academicCalendarForm.setAddedCollectionItems(new ArrayList<Object>());

        //sort term wrappers by start date
        viewHelperService.sortTermWrappers(academicCalendarForm.getTermWrapperList());

        // Return new form
        return academicCalendarForm;
    }

    /**
     * Cycles through the list of dirty fields, determines what property they correspond to and updates them.
     *
     * @param form - View form containing the Calendar information
     * @param dirtyFields - List of properties that have unsaved information
     * @param helperService - View Helper service
     * @return The updated form
     */
    private AcademicCalendarForm saveDirtyFieldChanges(AcademicCalendarForm form, List<String>dirtyFields, AcademicCalendarViewHelperService helperService){
        List<String> updatedFields = new ArrayList<String>();

        // Cycle through the dirty field list and save the individual properties.
        for(String field : dirtyFields){
            if(field.isEmpty()) continue;

            // Remove sub properties from the one to save
            field = field.substring(0,field.lastIndexOf("."));

            // Check if the property has already been saved.
            boolean alreadyUpdated = false;
            for(int j=0;j<updatedFields.size();j++){
                if(field.compareTo(updatedFields.get(j))==0){
                    alreadyUpdated=true;
                    break;
                }
            }
            if(alreadyUpdated) continue;

            // Add field to list of saved fields
            updatedFields.add(field);

            // Search for the save process for the field
            if(field.contains("newCollectionLines")){
                // Catch known dirty field that are not handled by save
                continue;
            }else if(field.contains("academicCalendarInfo")){
                // Academic calendar info is always saved
                continue;
            } else if(field.contains("events")){

                // Save an individual event and refresh it in the form
                int index = processFieldIndex(field);
                AcalEventWrapper event = form.getEvents().get(index);
                AcalEventWrapper newEvent = saveAcalEvent(event,form, helperService);
                form.getEvents().set(index, newEvent);

            } else if(field.contains("keydates")){

                // Save an individual key date change and refresh it in the form
                int termIndex = processFieldIndex(field.substring(0, field.indexOf(".")));
                int keyDateGroupIndex = processFieldIndex(field.substring(field.indexOf("."), field.lastIndexOf(".")));
                int keyDateIndex = processFieldIndex( field.substring(field.lastIndexOf(".")));
                KeyDateWrapper  keyDateWrapper = form.getTermWrapperList().get(termIndex).getKeyDatesGroupWrappers().get(keyDateGroupIndex).getKeydates().get(keyDateIndex);
                KeyDateWrapper newKeyDateWrapper = saveKeyDate(keyDateWrapper, form.getTermWrapperList().get(termIndex), helperService);
                form.getTermWrapperList().get(termIndex).getKeyDatesGroupWrappers().get(keyDateGroupIndex).getKeydates().set(keyDateIndex,newKeyDateWrapper);

            } else if (field.contains("examdates")) {
                //exempt exam period from dirty field update for now
                continue;
            } else if(field.contains("termWrapperList")){

                // Save and individual even and refresh it in the form
                int index = processFieldIndex(field);
                AcademicTermWrapper term = form.getTermWrapperList().get(index);
                AcademicTermWrapper newTerm = saveTerm(term, form, helperService);
                form.getTermWrapperList().set(index,newTerm);

            } else {
                // Signal that there is an unknown field found.
                LOG.warn("Unknown field encounter during save: {}", field);
            }
        }
        return form;
    }

    /**
     * Save changes to a calendar or create it if it has not already been saved
     *
     * @param acal - Calendar to be saved
     * @param form - View form containing the Calendar information
     * @param helperService - View Helper service
     * @return The updated calendar with information filled in from the save/create
     */
    private AcademicCalendarInfo saveAcal(AcademicCalendarInfo acal, AcademicCalendarForm form, AcademicCalendarViewHelperService helperService){
        // Process holiday calendar info and get calendar info base
        AcademicCalendarInfo acalInfo = processHolidayCalendars(form);

        // Save term to database
        try {
            if (StringUtils.isBlank(acal.getId())) {
                // Fill in calendar information starting values
                acalInfo.setStateKey(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_DRAFT_STATE_KEY);
                acalInfo.setTypeKey(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY);
                RichTextInfo rti = new RichTextInfo();
                rti.setPlain(acalInfo.getName());
                acalInfo.setDescr(rti);

                // Save the key date to the database and return created information
                AcademicCalendarInfo newAcal = getAcalService().createAcademicCalendar(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY, acalInfo, helperService.createContextInfo());
                return newAcal;
            } else {

                // Update the key date in the datebase and return updated information.
                AcademicCalendarInfo updatedAcal = getAcalService().updateAcademicCalendar(acal.getId(), acal, helperService.createContextInfo());
                return updatedAcal;

            }
        } catch (VersionMismatchException e){

            // If the calendar being worked on is out of date set up reload and message user
            form.setReload(true);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, RiceKeyConstants.ERROR_CUSTOM,"You are saving an older version of this calendar. Please click on the reload button to get the newer version.");
            return acal;

        } catch(Exception e) {

            // If the save fails message user
            LOG.error("Add/update Academic calendar failed", e);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_ACAL_SAVE_FAILED);
            return acal;
        }
    }

    /**
     * Cycles through the list of terms and saves any new terms detected.
     *
     * @param form - View form containing the Calendar information
     * @param helperService - View Helper service
     * @return The updated form.
     */
    private AcademicCalendarForm saveTerms(AcademicCalendarForm form, AcademicCalendarViewHelperService helperService){
        // Check for new terms and save them
        List<AcademicTermWrapper> subTermsToSave = new ArrayList<AcademicTermWrapper>();
        for(int i=0;i<form.getTermWrapperList().size();i++){
            AcademicTermWrapper term = form.getTermWrapperList().get(i);
            if(term.getTermInfo().getId()==null){
                if(term.isSubTerm()){
                    // Subterms require the parent term to exist so wait and save all other terms are saved
                    subTermsToSave.add(term);
                } else{
                    // Save the new term and refresh it in the form
                    AcademicTermWrapper newTerm = saveTerm(term,form,helperService);
                    form.getTermWrapperList().set(i,newTerm);
                }
            }
        }
        // Save subterms from before
        for(AcademicTermWrapper subterm : subTermsToSave){
            int index = form.getTermWrapperList().indexOf(subterm);
            // Save the new term and refresh it in the form
            AcademicTermWrapper newTerm = saveTerm(subterm,form,helperService);
            form.getTermWrapperList().set(index,newTerm);
        }

        return form;
    }

    /**
     * Save changes to a term or create it if it has not already been saved
     *
     * @param termWrapper - The term to be saved
     * @param form - View form containing the Calendar information
     * @param helperService - View Helper service
     * @return The updated term with information filled in from the save/create
     */
    private AcademicTermWrapper saveTerm(AcademicTermWrapper termWrapper, AcademicCalendarForm form, AcademicCalendarViewHelperService helperService){
        // Create term info base
        TermInfo term = termWrapper.getTermInfo();

        // Fill in key date info from the wrapper
        term.setEndDate(termWrapper.getEndDate());
        term.setStartDate(termWrapper.getStartDate());
        term.setName(termWrapper.getName());
        term.setTypeKey(termWrapper.getTermType());

        // Save term to database
        try{
            if (termWrapper.isNew() && !termWrapper.isSubTerm()){
                // If term is new and not a sub term.
                // Save the term to the database and update wrapper information.
                TermInfo newTerm = getAcalService().createTerm(termWrapper.getTermType(),term,helperService.createContextInfo());
                termWrapper.setTermInfo(newTerm);
                // Add term to the calendar
                getAcalService().addTermToAcademicCalendar(form.getAcademicCalendarInfo().getId(),termWrapper.getTermInfo().getId(),helperService.createContextInfo());
            }else if(termWrapper.isNew() && termWrapper.isSubTerm()){
                // If term is new and is a sub term.

                //Find parent term
                String parentTermTypeKey = termWrapper.getParentTerm();
                TermInfo parentTermInfo = getParentTerm(form.getAcademicCalendarInfo().getId(), parentTermTypeKey,helperService);

                // Check if Parent term exists
                if(parentTermInfo == null){
                    termWrapper.setParentTermInfo(null); //fix for ac copy
                    for (AcademicTermWrapper acTermWrapper : form.getTermWrapperList() ) {
                        if (acTermWrapper.getTermType().equals(parentTermTypeKey)) {
                            termWrapper.setParentTermInfo(acTermWrapper.getTermInfo());
                            termWrapper.setParentTerm(acTermWrapper.getTermCode());
                            termWrapper.setParentTermName(acTermWrapper.getName());
                            break;
                        }
                    }
                    // If null throw exception
                    if(termWrapper.getParentTermInfo() == null) {
                        throw new Exception("Parent Term does not exist. Therefor unable to save subterm.");
                    }
                }else{
                    // If parent exist fill in parent information in term.
                    termWrapper.setParentTermInfo(parentTermInfo);
                    // Save the term to the database and update wrapper information.
                    TermInfo newTerm = getAcalService().createTerm(termWrapper.getTermType(),term,helperService.createContextInfo());
                    termWrapper.setTermInfo(newTerm);
                    // Add link to parent term
                    getAcalService().addTermToTerm(termWrapper.getParentTermInfo().getId(), termWrapper.getTermInfo().getId(), helperService.createContextInfo());

                    // Add link to calendar
                    getAcalService().addTermToAcademicCalendar(form.getAcademicCalendarInfo().getId(),termWrapper.getTermInfo().getId(),helperService.createContextInfo());
                }
            }else {
                //If term already exists
                // Update the term in the datebase adn update wrapper information.
                TermInfo updatedTerm = getAcalService().updateTerm(term.getId(),term,helperService.createContextInfo());
                termWrapper.setTermInfo(updatedTerm);
            }
        }catch(Exception e){
            LOG.error("Save term has failed", e);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_ACAL_SAVE_TERM_SAVE_FAILED,
                    termWrapper.getName(), e.getLocalizedMessage());
        }

        return termWrapper;
    }

    /**
     * Determines terms that have been deleted in the UI and deletes them from the database
     *
     * @param form - View form containing the Calendar information
     * @param helperService - View Helper service
     */
    private void deleteTerms(AcademicCalendarForm form, AcademicCalendarViewHelperService helperService){
        for (AcademicTermWrapper term : form.getTermsToDeleteOnSave()){
            try{
                getAcademicCalendarServiceFacade().deleteTermCascaded(term.getTermInfo().getId(),helperService.createContextInfo());
            }catch(Exception e){
                LOG.error("Delete term has failed",e);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_DELETING,"Term",term.getName());

            }
        }
    }

    /**
     * Cycles through the key date groups and their list of key dates and saves any new keydates detected
     *
     * @param form - View form containing the Calendar information
     * @param termIndex - The index of the term.
     * @param helperService - View Helper service
     * @return The updated form.
     */
    private AcademicCalendarForm saveKeyDates(AcademicCalendarForm form, int termIndex, AcademicCalendarViewHelperService helperService){
        AcademicTermWrapper term = form.getTermWrapperList().get(termIndex);
        for( int j = 0; j<term.getKeyDatesGroupWrappers().size();j++){
            KeyDatesGroupWrapper keyDateGroup = term.getKeyDatesGroupWrappers().get(j);
            for(int k = 0; k<keyDateGroup.getKeydates().size();k++){
                KeyDateWrapper keyDate = keyDateGroup.getKeydates().get(k);
                if(keyDate.getKeyDateInfo().getId() ==  null){
                    KeyDateWrapper newKeyDate= saveKeyDate(keyDate,term,helperService);
                    form.getTermWrapperList().get(termIndex).getKeyDatesGroupWrappers().get(j).getKeydates().set(k,newKeyDate);
                }
            }
        }
        return form;
    }

    /**
     * Save changes to a keydate or create it if it has not already been saved
     *
     * @param keyDateWrapper - KeyDate to be saved
     * @param term - Term containing the key date
     * @param helperService - View helper service
     * @return The updated keydate with information filled in from the save/create
     */
    private KeyDateWrapper saveKeyDate(KeyDateWrapper keyDateWrapper, AcademicTermWrapper term, AcademicCalendarViewHelperService helperService){
        // Create key date info base
        KeyDateInfo keyDate = keyDateWrapper.getKeyDateInfo();

        // Fill in key date info from the wrapper
        keyDate.setTypeKey(keyDateWrapper.getKeyDateType());
        keyDate.setName(keyDateWrapper.getKeyDateNameUI());
        keyDate.setIsAllDay(keyDateWrapper.isAllDay());
        keyDate.setIsDateRange(keyDateWrapper.isDateRange());
        keyDate.setStartDate(getDateInfoForKeyDate(keyDateWrapper.isAllDay(),keyDateWrapper.getStartDate(),keyDateWrapper.getStartTime(),keyDateWrapper.getStartTimeAmPm()));
        if (keyDateWrapper.isDateRange()){
            keyDate.setEndDate(getDateInfoForKeyDate(keyDateWrapper.isAllDay(),keyDateWrapper.getEndDate(),keyDateWrapper.getEndTime(),keyDateWrapper.getEndTimeAmPm()));
        } else{
            keyDate.setEndDate(null);
        }

        // Save Key date to database
        try{
            if (keyDateWrapper.isNew()){
                // Save the key date to the database and update wrapper information.
                keyDate.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
                KeyDateInfo createdKeyDate = getAcalService().createKeyDate(term.getTermInfo().getId(),keyDate.getTypeKey(),keyDate,helperService.createContextInfo());
                keyDateWrapper.setKeyDateInfo(createdKeyDate);
            } else {
                // Update the key date in the datebase adn update wrapper information.
                KeyDateInfo updatedKeyDate = getAcalService().updateKeyDate(keyDate.getId(), keyDate, helperService.createContextInfo());
                keyDateWrapper.setKeyDateInfo(updatedKeyDate);
            }
        }catch(Exception e){
            LOG.error("Save keydate has failed",e);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_ACAL_SAVE_TERM_KEYDATE_FAILED,keyDateWrapper.getKeyDateNameUI(),term.getName());

        }

        return keyDateWrapper;
    }

    /**
     * Cycles through the exam periods and process each of them
     *
     * @param form - View form containing the Calendar information
     * @param termIndex - The index of the term.
     * @param helperService - View Helper service
     * @return The updated form.
     */
    private AcademicCalendarForm processExamPeriods(AcademicCalendarForm form, int termIndex, AcademicCalendarViewHelperService helperService){
        //process add/update of exam period
        AcademicTermWrapper term = form.getTermWrapperList().get(termIndex);
        for(int i=0; i<term.getExamdates().size(); i++ ) {
            ExamPeriodWrapper examPeriodWrapper = term.getExamdates().get(i);

            ExamPeriodWrapper newExamPeriodWrapper = saveExamPeriod(form, examPeriodWrapper, term, termIndex, helperService);
            form.getTermWrapperList().get(termIndex).getExamdates().set(i,newExamPeriodWrapper);
        }

        //process the deletion of exam period
        for (ExamPeriodWrapper examPeriodToDelete : term.getExamPeriodsToDeleteOnSave()) {
            try {
                getAcalService().deleteExamPeriod(examPeriodToDelete.getExamPeriodInfo().getId(), helperService.createContextInfo());
            } catch (Exception e) {
                LOG.error("Delete exam period has failed",e);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_DELETING,term.getName(),examPeriodToDelete.getExamPeriodNameUI());
            }
        }
        term.getExamPeriodsToDeleteOnSave().clear();

        return form;
    }

    /**
     * Save changes to a exam period or create it if it has not already been saved
     *
     * @param examPeriodWrapper - ExamPeriod to be saved
     * @param term - Term containing the key date
     * @param helperService - View helper service
     * @return The updated keydate with information filled in from the save/create
     */
    private ExamPeriodWrapper saveExamPeriod(AcademicCalendarForm form, ExamPeriodWrapper examPeriodWrapper, AcademicTermWrapper term, int termIndex, AcademicCalendarViewHelperService helperService){
        // Create exam period info base
        ExamPeriodInfo examPeriodInfo = examPeriodWrapper.getExamPeriodInfo();
        String examPeriodName = examPeriodWrapper.getExamPeriodNameUI() + " " + term.getName();
        // Fill in key date info from the wrapper
        examPeriodInfo.setTypeKey(examPeriodWrapper.getExamPeriodType());
        examPeriodInfo.setName(examPeriodName);
        examPeriodInfo.setEndDate(examPeriodWrapper.getEndDate());
        examPeriodInfo.setStartDate(examPeriodWrapper.getStartDate());
        examPeriodInfo.setStateKey(term.getTermInfo().getStateKey()); //the state of the exam period is the same as the term state
        setExamPeriodAttr(examPeriodInfo, AcademicCalendarServiceConstants.EXAM_PERIOD_EXCLUDE_SATURDAY_ATTR, String.valueOf(examPeriodWrapper.isExcludeSaturday()));
        setExamPeriodAttr(examPeriodInfo, AcademicCalendarServiceConstants.EXAM_PERIOD_EXCLUDE_SUNDAY_ATTR, String.valueOf(examPeriodWrapper.isExcludeSunday()));

        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(examPeriodName);
        rti.setFormatted(examPeriodName);
        examPeriodInfo.setDescr(rti);

        // Save Exam Period to database
        try{
            if (examPeriodWrapper.isNew()){
                // Save the exam period to the database and update wrapper information.
                List<String> termTypeKeyList = new ArrayList<String>();
                termTypeKeyList.add(term.getTermType());
                ExamPeriodInfo createdExamPeriodInfo = getAcademicCalendarServiceFacade().addExamPeriod(examPeriodInfo.getTypeKey(), termTypeKeyList, examPeriodInfo, helperService.createContextInfo());
                getAcalService().addExamPeriodToTerm(term.getTermInfo().getId(), createdExamPeriodInfo.getId(), helperService.createContextInfo());
                examPeriodWrapper.setExamPeriodInfo(createdExamPeriodInfo);
            } else {
                // Update the exam period in the datebase and update wrapper information.
                ExamPeriodInfo updatedExamPeriodInfo = getAcalService().updateExamPeriod(examPeriodInfo.getId(), examPeriodInfo, helperService.createContextInfo());
                examPeriodWrapper.setExamPeriodInfo(updatedExamPeriodInfo);
            }
        } catch (OperationFailedException oe){
            LOG.error("Save exam period has failed",oe);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_ACAL_SAVE_TERM_EXAMPERIOD_FAILED,examPeriodWrapper.getExamPeriodNameUI(),term.getName() +". FEP is not allowed for the selected term.");
        }
        catch(Exception e){
            LOG.error("Save exam period has failed",e);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_ACAL_SAVE_TERM_EXAMPERIOD_FAILED,examPeriodWrapper.getExamPeriodNameUI(),term.getName());
        }

        return examPeriodWrapper;
    }


    /**
     * Determines kay dates that have been deleted in the UI and deletes them from the database
     *
     * @param term - term wrapper from form
     * @param helperService - View Helper service
     */
    private void deleteKeyDates(AcademicTermWrapper term, AcademicCalendarViewHelperService helperService){
        for(KeyDateWrapper keyDate : term.getKeyDatesToDeleteOnSave()){
            try{
                getAcalService().deleteKeyDate(keyDate.getKeyDateInfo().getId(),helperService.createContextInfo());
            }catch(Exception e){
                LOG.error("Delete key date has failed",e);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_DELETING,term.getName(),keyDate.getKeyDateNameUI());
            }
        }
        term.getKeyDatesToDeleteOnSave().clear();
    }

    /**
     * Cycles through the list of events and saves any new events detected
     *
     * @param form - View form containing the Calendar information
     * @param helperService - View Helper service
     * @return The updated form.
     */
    private AcademicCalendarForm saveAcalEvents(AcademicCalendarForm form, AcademicCalendarViewHelperService helperService){
        for(int i=0;i<form.getEvents().size();i++){
            AcalEventWrapper event = form.getEvents().get(i);
            if(event.getAcalEventInfo().getId()==null){
                AcalEventWrapper newEvent = saveAcalEvent(event,form,helperService);
                form.getEvents().set(i,newEvent);
            }
        }
        return form;
    }

    /**
     * Save changes to an event or create it if it has not already been saved
     *
     * @param event - The event to be created
     * @param form - View form containing the Calendar information
     * @param helperService - The view helper service
     * @return The updated event with the saved information from its creation.
     */
    private AcalEventWrapper saveAcalEvent(AcalEventWrapper event, AcademicCalendarForm form, AcademicCalendarViewHelperService helperService){
        // Create event info base
        AcalEventInfo eventInfo = event.getAcalEventInfo();

        // Fill in event info from the wrapper
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(event.getEventTypeKey());
        eventInfo.setDescr(rti);
        eventInfo.setTypeKey(event.getEventTypeKey());
        eventInfo.setIsAllDay(event.isAllDay());
        eventInfo.setIsDateRange(event.isDateRange());
        eventInfo.setStartDate(getDateInfoForKeyDate(event.isAllDay(),event.getStartDate(),event.getStartTime(),event.getStartTimeAmPm()));
        if(event.isDateRange()){
            eventInfo.setEndDate(getDateInfoForKeyDate(event.isAllDay(),event.getEndDate(),event.getEndTime(),event.getEndTimeAmPm()));
        } else{
            eventInfo.setEndDate(null);
        }
        // If calendar is official event is too
        if (!form.isOfficialCalendar()){
            eventInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        } else {
            eventInfo.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        }

        // Save Event to database
        try{
            if(eventInfo.getId()==null){
                // Save the event to the database and update wrapper information.
                AcalEventInfo createdEventInfo = getAcalService().createAcalEvent(form.getAcademicCalendarInfo().getId(), eventInfo.getTypeKey(), eventInfo, helperService.createContextInfo());
                event.setAcalEventInfo(createdEventInfo);
            }else{
                // Update the event already in the database and update wrapper information.
                AcalEventInfo updatedEventInfo = getAcalService().updateAcalEvent(eventInfo.getId(), eventInfo, helperService.createContextInfo());
                event.setAcalEventInfo(updatedEventInfo);
            }
        }catch(Exception e){
            LOG.error("Save calendar event has failed" , e);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_ACAL_SAVE_EVENT_FAILED,event.getEventTypeName());

        }

        return event;
    }

    /**
     * Determines events that have been deleted in the UI and deletes them from the database
     *
     * @param form - View form containing the Calendar information
     * @param helperService - View Helper service
     */
    private void deleteAcalEvents(AcademicCalendarForm form, AcademicCalendarViewHelperService helperService){
        for(AcalEventWrapper event : form.getEventsToDeleteOnSave()){
            try{
                getAcalService().deleteAcalEvent(event.getAcalEventInfo().getId(),helperService.createContextInfo());
            }catch(Exception e){
                LOG.error("Delete calendar event has failed" , e);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_DELETING,"Calendar event",event.getEventTypeName());
            }
        }
    }

    /**
     * Finds and returns an array index of a property from its field name
     *
     * @param field - The property field string in the form propertyName[#]
     * @return The index of the array slot referenced in the property.
     */
    private int processFieldIndex(String field){
        String indexChar = field.substring(field.indexOf("[")+1, field.lastIndexOf("]"));
        return Integer.parseInt(indexChar);
    }

    /**
     * Finds and returns the parent term of a subterm
     *
     * @param acalId - Id of the calendar containing the subterm
     * @param parentTermTypeKey - The type key of the parent term
     * @param helperService - the view helper service
     * @return The term info for the parent term
     * @throws Exception - Exception thrown by the call to the academic calendar service
     */
    private TermInfo getParentTerm(String acalId, String parentTermTypeKey, AcademicCalendarViewHelperService helperService) throws Exception{

        List<TermInfo> termInfoList =  getAcalService().getTermsForAcademicCalendar(acalId, helperService.createContextInfo());
        for(TermInfo termInfo : termInfoList){
            if (parentTermTypeKey.equals(termInfo.getTypeKey())) {
                return termInfo;
            }
        }
        return null;
    }

    /**
     * Creates a Date object with just date or date and time based on if the event is all day or not
     *
     * @param isAllDay - Whether event is all day or not
     * @param date - The MM/dd/yyyy date
     * @param time - A string of the time
     * @param ampm - A string of whether the time is am or pm
     * @return A completed date object based on isAllDay
     */
    private Date getDateInfoForKeyDate(boolean isAllDay, Date date, String time, String ampm){
        if(!isAllDay){
            return AcalCommonUtils.getDateWithTime(date, time, ampm);
        }
        return date;
    }

    /**
     * Compiles the list of Holiday ids saved in the form to a new list and adds it to the academicCalendarInfo.
     *
     * @param academicCalendarForm - View form containing the Calendar information
     * @return An updated academic calendar info with list of hcals
     */
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
        acalInfo.setHolidayCalendarIds(holidayCalendarIds);
        academicCalendarForm.setAcademicCalendarInfo(acalInfo);

        return acalInfo;
    }

    /**
     * Processes the the string of dirty fields on the form and stores
     * (Mainly to prevent loss during redirects during dialogs)
     *
     * @param academicCalendarForm - View form containing the Calendar information
     * @return List of diry fields passed from the screen.
     */
    private List<String> processDirtyFields(AcademicCalendarForm academicCalendarForm){
        String[] tempFields = academicCalendarForm.getDirtyFields().split(",");
        List<String> dirtyFields = academicCalendarForm.getFieldsToSave();
        StringBuilder completeDirtyFields = new StringBuilder("");
        for(String field : tempFields){
            boolean alreadySeen = false;
            for(String field2 : dirtyFields){
                if(field2.compareTo(field)==0){
                    alreadySeen=true;
                    break;
                }
            }
            if(!alreadySeen){
                dirtyFields.add(field);

            }
        }
        for(String field : dirtyFields){
            completeDirtyFields.append(field);
            completeDirtyFields.append(",");
        }
        academicCalendarForm.setDirtyFields(completeDirtyFields.toString());
        return dirtyFields;

    }

    /**
     * Override of the Krad lightbox return function to allow for returning to the controller without a redirect.
     * Redirect causes a page refresh.
     */
    @Override
    @RequestMapping(params = "methodToCall=returnFromLightbox")
    public ModelAndView returnFromLightbox(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {

        String newMethodToCall;

        // Save user responses from dialog
        DialogManager dm = form.getDialogManager();
        String dialogId = dm.getCurrentDialogId();
        if (dialogId == null) {
            newMethodToCall = "start";
        } else {
            dm.setDialogAnswer(dialogId, form.getDialogResponse());
            dm.setDialogExplanation(dialogId, form.getDialogExplanation());
            newMethodToCall = dm.getDialogReturnMethod(dialogId);
            dm.setCurrentDialogId(null);
        }

        // KSENROLL Code Start
        form.setMethodToCall(newMethodToCall);

        // Attempt to return to the controller method directly using reflection (look for the matching methodToCall)
        for (Method m : this.getClass().getMethods()) {
            RequestMapping a = m.getAnnotation(RequestMapping.class);
            if (a != null) {
                String[] annotationsParams = a.params();
                for (String param : annotationsParams) {
                    if (param.contains("methodToCall=" + newMethodToCall)) {
                        try {
                            return (ModelAndView) m.invoke(this, form, result, request, response);
                        } catch (IllegalAccessException iae) {
                            LOG.error("Reflection Invocation failed", iae);
                            throw new RuntimeException("Error using reflection in returnFromLightbox", iae);
                        } catch (InvocationTargetException ite) {
                            LOG.error("Reflection Invocation failed", ite);
                            throw new RuntimeException("Error using reflection in returnFromLightbox", ite);
                        } catch (IllegalArgumentException iae) {
                            LOG.error("Reflection Invocation failed", iae);
                            throw new RuntimeException("Error using reflection in returnFromLightbox", iae);
                        }
                    }
                }

            }
        }
        // KSENROLL Code End

        // call intended controller method
        Properties props = new Properties();
        props.put(UifParameters.METHOD_TO_CALL, newMethodToCall);
        props.put(UifParameters.VIEW_ID, form.getViewId());
        props.put(UifParameters.FORM_KEY, form.getFormKey());
        props.put(UifParameters.AJAX_REQUEST, "false");

        return performRedirect(form, form.getFormPostUrl(), props);
    }

    /**
     * Override to process and save dirty fields when adding values.
     */
    @Override
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=addLine")
    public ModelAndView addLine(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                HttpServletRequest request, HttpServletResponse response) {
        ((AcademicCalendarForm)uifForm).setFieldsToSave(processDirtyFields((AcademicCalendarForm)uifForm));
        return super.addLine(uifForm,result,request,response);
    }

    private void setExamPeriodAttr(ExamPeriodInfo examPeriodInfo, String attrKey, String attrValue) {
        AttributeInfo attributeInfo = getExamPeriodAttrForKey(examPeriodInfo, attrKey);
        if (attributeInfo != null) {
            attributeInfo.setValue(attrValue);
        } else {
            attributeInfo = AcalCommonUtils.createAttribute(attrKey, attrValue);
            examPeriodInfo.getAttributes().add(attributeInfo);
        }
    }

    private AttributeInfo getExamPeriodAttrForKey(ExamPeriodInfo examPeriodInfo, String key) {
        for (AttributeInfo info : examPeriodInfo.getAttributes()) {
            if (info.getKey().equals(key)) {
                return info;
            }
        }
        return null;
    }



}
