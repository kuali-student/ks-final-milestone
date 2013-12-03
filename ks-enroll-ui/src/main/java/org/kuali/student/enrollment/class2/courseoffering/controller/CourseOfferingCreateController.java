/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.JointCourseWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingCreateMaintainableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.enrollment.common.util.EnrollConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;


/**
 * This is the controller class what handles all the requests (actions) from the <i>'create course offering'</i> ui.
 * <p>
 *      Wireframes at <a href="http://ux.ks.kuali.org.s3.amazonaws.com/wireframes/ENR/Course_Offering/v17/start.html">
 *          http://ux.ks.kuali.org.s3.amazonaws.com/wireframes/ENR/Course_Offering/v17/start.html</a> and
 *
 *       <a href="http://ux.ks.kuali.org.s3.amazonaws.com/wireframes/ENR/Complex%20Course%20Offerings/Sandbox/start.html">
 *           http://ux.ks.kuali.org.s3.amazonaws.com/wireframes/ENR/Complex%20Course%20Offerings/Sandbox/start.html</a>
 * </p>
 *
 * @see CourseOfferingCreateWrapper
 * @see JointCourseWrapper
 * @see org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper
 * @see CourseOfferingCreateMaintainableImpl
 */
@Controller
@RequestMapping(value = "/courseOfferingCreate")
public class CourseOfferingCreateController extends CourseOfferingBaseController {


    /**
     * Initial method called when requesting a new view instance.
     *
     */
    @Override
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) form;
        setupMaintenance(maintenanceForm, request, KRADConstants.MAINTENANCE_NEW_ACTION);

        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);

            // check if creating CO and populate the form
            String createCO = request.getParameter(CourseOfferingConstants.CREATE_COURSEOFFERING);
            if (createCO != null && createCO.equals("true")) {
                CourseOfferingControllerPopulateUIForm.populateCreateCourseOfferingForm(maintenanceForm, request);
            }
            // done with creating CO

            String pageId = request.getParameter("pageId");
            if (pageId != null && pageId.equals("courseOfferingCopyPage")){
                Object selectedObject =  maintenanceForm.getDocument().getNewMaintainableObject().getDataObject();
                if (selectedObject instanceof CourseOfferingCreateWrapper)  {
                    CourseOfferingCreateWrapper coCreateWrapper = (CourseOfferingCreateWrapper)selectedObject;
                    String targetTermCode = request.getParameter("targetTermCode");
                    String catalogCourseCode = request.getParameter("catalogCourseCode");
                    String coId = request.getParameter("courseOfferingId");

                    CourseOfferingControllerPopulateUIForm.copyCourseOfferingInfo(coCreateWrapper, targetTermCode, catalogCourseCode, coId);
                }
            }
            checkViewAuthorization(form, methodToCall);
        }

        return getUIFModelAndView(maintenanceForm);
    }

    @Override
    @RequestMapping(params = "methodToCall=route")
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {

        super.route(form, result, request, response);

        if( GlobalVariables.getMessageMap().hasErrors() ) {
            return handleRouteForErrors( form );
        }

        return handleRouteForCoCreate( form );
    }

    /* Returns a ModelAndView for the route()-method to return a new view if we are creating a CO */
    private ModelAndView handleRouteForCoCreate( DocumentFormBase form ) {

        Properties urlParameters = new Properties();

        CourseOfferingEditWrapper dataObject = (CourseOfferingEditWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();

        //display the correct growl message based on the availability of exam period and final exam type
        if (StringUtils.isEmpty(dataObject.getExamPeriodId())) {
            urlParameters.put(EnrollConstants.GROWL_MESSAGE, CourseOfferingConstants.COURSE_OFFERING_CREATE_SUCCESS_WITH_MISSING_EXAMPERIOD);
        } else if (!StringUtils.equals(dataObject.getCourseOfferingInfo().getFinalExamType(), CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_STANDARD)) {
            urlParameters.put(EnrollConstants.GROWL_MESSAGE, CourseOfferingConstants.COURSE_OFFERING_CREATE_SUCCESS);
        } else {
            urlParameters.put(EnrollConstants.GROWL_MESSAGE, CourseOfferingConstants.COURSE_OFFERING_CREATE_SUCCESS_WITH_EXAMOFFERING_GENERATED);
        }
        urlParameters.put(EnrollConstants.GROWL_MESSAGE_PARAMS, dataObject.getCourseOfferingCode());

        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "show");
        urlParameters.put("termCode", dataObject.getTerm().getCode());
        if (dataObject.getCourseOfferingInfo().getCourseNumberSuffix() != null && !StringUtils.isBlank(dataObject.getCourseOfferingInfo().getCourseNumberSuffix())) {
            urlParameters.put("inputCode", dataObject.getCourseOfferingInfo().getCourseOfferingCode().concat(dataObject.getCourseOfferingInfo().getCourseNumberSuffix()));
        } else {
            urlParameters.put("inputCode", dataObject.getCourseOfferingInfo().getCourseOfferingCode());
        }
        urlParameters.put("viewId", CourseOfferingConstants.MANAGE_CO_VIEW_ID);
        urlParameters.put("pageId", CourseOfferingConstants.MANAGE_THE_CO_PAGE);
        urlParameters.put("withinPortal", "false");

        return super.performRedirect(form, CourseOfferingConstants.MANAGE_CO_CONTROLLER_PATH, urlParameters);
    }

    /**
     * This is mapped to the link to to toggle between creating a new format offering or
     * copy from existing joint format offerings.
     *
     */
    @RequestMapping(params = "methodToCall=showCreateFormatSection")
    public ModelAndView showCreateFormatSection(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        wrapper.setShowCreateFormatSection(true);
        wrapper.setShowCopyFormatSection(false);

        return getUIFModelAndView(form);
    }

    /**
     * This is mapped to the link to to toggle between creating a new format offering or
     * copy from existing joint format offerings.
     *
     */
    @RequestMapping(params = "methodToCall=showCopyFromJointOffering")
    public ModelAndView showCopyFromJointOffering(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        wrapper.setShowCreateFormatSection(false);
        wrapper.setShowCopyFormatSection(true);

        return getUIFModelAndView(form);
    }

    /**
     * This will be called whenever the user selects/deselects a joint course. If user deselects a joint course, make sure
     * it doesnt have associated formats. If exists, display a confirmation dialog with all the available formats.
     *
     * XML reference at CourseOfferingCreateMaintenanceView.xml
     *
     */
    @RequestMapping(params = "methodToCall=markCourseForJointOffering")
    public ModelAndView markCourseForJointOffering(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, @SuppressWarnings("unused") BindingResult result,
                HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        int index = wrapper.getSelectedJointCourseIndex();
        if(form.getActionParameters().size() > 1)   {
            String lineIndex = form.getActionParameters().get("selectedLineIndex");
            index = Integer.parseInt(lineIndex);
            wrapper.setSelectedJointCourseIndex(index);
        }
        JointCourseWrapper joint = wrapper.getJointCourses().get(index);

        if (joint.isSelectedToJointlyOfferred()){
            String dialogName = CourseOfferingConstants.JOINT_COURSE_FORMATS_DELETE_DIALOG;
            
            if (!hasDialogBeenAnswered(dialogName, form)) {
                wrapper.setSelectedJointCourseCode(joint.getCourseCode());
                wrapper.setDialogFormatOfferingWrapperList(joint.getFormatOfferingWrappers());
                return showDialog(dialogName, form, request, response);
            }

            boolean dialogAnswer = getBooleanDialogResponse(dialogName, form, request, response);
            form.getDialogManager().resetDialogStatus(dialogName);

            if (dialogAnswer) {
                joint.setSelectedToJointlyOfferred(false);
                String jointCodes = StringUtils.remove(wrapper.getJointCourseCodes(), ", " + joint.getCourseCode());
                wrapper.setJointCourseCodes(jointCodes);
                wrapper.getFormatOfferingWrappers().removeAll(joint.getFormatOfferingWrappers());
            }

        } else {
            wrapper.setJointCourseCodes(wrapper.getJointCourseCodes() + ", " + joint.getCourseCode());
            joint.setSelectedToJointlyOfferred(true);
        }

        return getUIFModelAndView(form);
    }

    /**
     * Mapped to the <i>'Add'</i> button at the format section. This either copies from the user selected joint
     * offerings or create a new format.
     *
     */
    @RequestMapping(params = "methodToCall=addFormat")
        public ModelAndView addFormat(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        CourseOfferingCreateMaintainableImpl maintainable = (CourseOfferingCreateMaintainableImpl)KSControllerHelper.getViewHelperService(form);

        //If the user creates a new format
        if (wrapper.isShowCreateFormatSection()){
            maintainable.addFormatOffering(wrapper);
        } else { //If the user copies from existing joint formats
            maintainable.copyJointFormatOfferings(wrapper);
        }

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=continueFromCreate")
    public ModelAndView continueFromCreate(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingCreateWrapper coWrapper = ((CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject());
        String courseCode = coWrapper.getCatalogCourseCode();
        String termCode = coWrapper.getTargetTermCode();

        // check if term or course is empty
        if( StringUtils.isBlank(termCode) ) {
//            KULRICE-10323
//            GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.targetTermCode", CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_PARAMETER_IS_REQUIRED, "Term");
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_PARAMETER_IS_REQUIRED, "Term");
        }
        if( StringUtils.isBlank(courseCode) ) {
//            KULRICE-10323
//            GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.catalogCourseCode", CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_PARAMETER_IS_REQUIRED, "Course Code");
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_PARAMETER_IS_REQUIRED, "Course Code");
        }
        if (GlobalVariables.getMessageMap().getErrorCount() > 0) {
            return getUIFModelAndView(form);
        }

        TermInfo term = CourseOfferingManagementUtil.getTerm(termCode);
        if (term == null) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_TERM_INVALID, termCode);
            return getUIFModelAndView(form);
        }

        coWrapper.setTerm(term);

        List<CourseInfo> matchingCourses = CourseOfferingManagementUtil.retrieveMatchingCourses(courseCode, term);
        coWrapper.clear();
        int firstMatchingCourse = 0;
        if (matchingCourses.size() == 1) {
            CourseInfo course = matchingCourses.get(firstMatchingCourse);

            // set organization IDs and check if the user is authorized to create a course
            List<String> orgIds = course.getUnitsContentOwner();
            if(orgIds != null && !orgIds.isEmpty()){
                StringBuilder orgIDs = new StringBuilder();
                for (String orgId : orgIds) {
                    orgIDs.append(orgId + ",");
                }
                if (orgIDs.length() > 0) {
                    coWrapper.setAdminOrg(orgIDs.substring(0, orgIDs.length()-1));
                }
            }
            Person user = GlobalVariables.getUserSession().getPerson();
            boolean canOpenView = form.getView().getAuthorizer().canOpenView(form.getView(), form, user);

            if (!canOpenView) {    // checking authz for course
//                KULRICE-10323
//                GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.catalogCourseCode", CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_COURSE_RESTRICTED, courseCode);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_COURSE_RESTRICTED, courseCode);
                coWrapper.setAdminOrg(null);
                coWrapper.setCourse(null);

                return getUIFModelAndView(form);
            } else {
                // check if SOC state is "published"
                ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
                List<String> socIds = CourseOfferingManagementUtil.getCourseOfferingSetService().getSocIdsByTerm(term.getId(), contextInfo);
                int firstSocID = 0;
                if (socIds != null && !socIds.isEmpty()){
                    // check if user authz for the soc
                    SocInfo soc = CourseOfferingManagementUtil.getCourseOfferingSetService().getSoc(socIds.get(firstSocID), contextInfo);
                    coWrapper.setSocInfo(soc);
                    boolean canOpenViewSoc = form.getView().getAuthorizer().canOpenView(form.getView(), form, user);

                    if(!canOpenViewSoc) {   // check if user authz for the soc
//                      KULRICE-10323
//                        GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.targetTermCode", CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_TERM_RESTRICTED);
                        GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_TERM_RESTRICTED);
                        coWrapper.setSocInfo(null);

                        return getUIFModelAndView(form);
                    } else {
                        if (coWrapper.isCreateFromCatalog()) {
                            Properties urlParameters = CourseOfferingManagementUtil._buildCOURLParameters(course.getId(), term.getId(), soc.getId(), KRADConstants.Maintenance.METHOD_TO_CALL_NEW);
                            return super.performRedirect(form, CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_CREATE_MAINTENANCE, urlParameters);
                        } else {  // Copy part
                            //Get all the course offerings in a term
                            CourseOfferingControllerPopulateUIForm.continueFromCreateCopyCourseOfferingInfo(coWrapper, course, term);

                            //sort existing COs by term code
                            Collections.sort(coWrapper.getExistingTermOfferings(), new Comparator<CourseOfferingEditWrapper>() {
                                @Override
                                public int compare(CourseOfferingEditWrapper co1, CourseOfferingEditWrapper co2) {
                                    return co2.getTerm().getCode().compareTo(co1.getTerm().getCode());
                                }
                            });

                            CourseOfferingCreateMaintainableImpl maintainable = (CourseOfferingCreateMaintainableImpl)KSControllerHelper.getViewHelperService(form);
                            maintainable.loadCourseJointInfos(coWrapper, form.getViewId());
                            //Enable the create button
                            coWrapper.setEnableCreateButton(true);

                            return getUIFModelAndView(form, CourseOfferingConstants.COPY_COURSEOFFERING_PAGE);
                        }
                    }
                } else {
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_SOC_NOT_EXISTS);
                    return getUIFModelAndView(form);
                }
            }
        } else {
            if (matchingCourses.size() > 1) {
//              KULRICE-10323
//                GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.catalogCourseCode", CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_MULTIPLE_COURSE_MATCHES, courseCode);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_MULTIPLE_COURSE_MATCHES, courseCode);
            } else if (matchingCourses.isEmpty()) {
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.ERROR_INVALID_CLU_VERSION, courseCode, termCode);
            }

            return getUIFModelAndView(form);
        }
    }

    @RequestMapping(params = "methodToCall=createFromCopy")
    public ModelAndView createFromCopy(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, @SuppressWarnings("unused") BindingResult result,
         @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingCreateWrapper createWrapper = (CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        CourseOfferingInfo existingCO = null;
        String examPeriodId = null;

        //the first CO that is selected or if there is only one, grab that one
        for(CourseOfferingEditWrapper eco : createWrapper.getExistingTermOfferings()){
            if(eco.getIsChecked() || createWrapper.getExistingTermOfferings().size() == 1){
                existingCO = eco.getCourseOfferingInfo();
                break;
            }
        }

        if (existingCO == null){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS,CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_PARAMETER_IS_REQUIRED, "Selected Course");
            return getUIFModelAndView(form);
        }

        List<String> optionKeys = CourseOfferingControllerPopulateUIForm.getOptionKeys(createWrapper, existingCO);
        optionKeys.add(CourseOfferingSetServiceConstants.CONTINUE_WITHOUT_EXAM_OFFERINGS_OPTION_KEY);
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        SocRolloverResultItemInfo item = CourseOfferingManagementUtil.getCourseOfferingService().rolloverCourseOffering(existingCO.getId(),
                createWrapper.getTerm().getId(),
                optionKeys,
                contextInfo);
        CourseOfferingInfo courseOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(item.getTargetCourseOfferingId(), contextInfo);

        // retrieve the exam period id
        try {
            examPeriodId = CourseOfferingManagementUtil.getExamOfferingServiceFacade().getExamPeriodId(courseOfferingInfo.getTermId(), contextInfo);
        } catch (DoesNotExistException e) {
            LOG.warn("The Term " + courseOfferingInfo.getTermId() + " that the course offering " + courseOfferingInfo.getCourseOfferingCode() + " is attached to doesn't have an exam period to create exam offerings.");
        }

        Properties urlParameters;
        urlParameters = new Properties();

        //display the correct growl message based on the availability of exam period and final exam type
        if (StringUtils.isEmpty(examPeriodId)) {
            urlParameters.put(EnrollConstants.GROWL_MESSAGE, CourseOfferingConstants.COURSE_OFFERING_CREATE_SUCCESS_WITH_MISSING_EXAMPERIOD);
        } else if (!StringUtils.equals(courseOfferingInfo.getFinalExamType(), CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_STANDARD)) {
            urlParameters.put(EnrollConstants.GROWL_MESSAGE, CourseOfferingConstants.COURSE_OFFERING_CREATE_SUCCESS);
        } else {
            urlParameters.put(EnrollConstants.GROWL_MESSAGE, CourseOfferingConstants.COURSE_OFFERING_CREATE_SUCCESS_WITH_EXAMOFFERING_GENERATED);
        }
        urlParameters.put(EnrollConstants.GROWL_MESSAGE_PARAMS, courseOfferingInfo.getCourseOfferingCode());


        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "show");
        urlParameters.put("termCode",createWrapper.getTargetTermCode());
        urlParameters.put("inputCode",courseOfferingInfo.getCourseOfferingCode());
        urlParameters.put("viewId",CourseOfferingConstants.MANAGE_CO_VIEW_ID);
        urlParameters.put("pageId",CourseOfferingConstants.MANAGE_THE_CO_PAGE);
        urlParameters.put("withinPortal","false");

        return super.performRedirect(form, CourseOfferingConstants.MANAGE_CO_CONTROLLER_PATH, urlParameters);
    }

}
