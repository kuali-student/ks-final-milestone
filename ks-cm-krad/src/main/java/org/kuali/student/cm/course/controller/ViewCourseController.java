/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by venkat on 7/18/14
 */
package org.kuali.student.cm.course.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.view.DialogManager;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.KsUifControllerBase;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.common.util.CMUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.Export.FileType;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.UrlParams;
import org.kuali.student.cm.course.form.ViewCourseForm;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.RetireCourseWrapper;
import org.kuali.student.cm.course.service.CourseMaintainable;
import org.kuali.student.cm.course.service.impl.ExportCourseHelperImpl;
import org.kuali.student.cm.course.util.CourseProposalUtil;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 * This controller maps to 'View Course' View.
 * XML: ViewCourseView.xml
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = CurriculumManagementConstants.ControllerRequestMappings.VIEW_COURSE)
public class ViewCourseController extends KsUifControllerBase {

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new ViewCourseForm();
    }

    /**
     * This method populates the course details model to be displayed at 'course view'.
     *
     * @param form
     * @param request
     * @return
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                              HttpServletResponse response) {

        ViewCourseForm detailedViewForm = (ViewCourseForm) form;

        boolean isComparison = false;

        String courseId = request.getParameter(UrlParams.COURSE_ID);
        String compareCourseId = request.getParameter(UrlParams.COMPARE_COURSE_ID);

        if (StringUtils.isBlank(courseId)) {
            throw new RuntimeException("Missing Course Id");
        }

        if (StringUtils.isNotBlank(compareCourseId)) {
            isComparison = true;
        }

        try {
            //  Load the data for the primary/courseId course (including the version data).
            CourseInfoWrapper courseWrapper = new CourseInfoWrapper();
            courseWrapper.setProposalDataRequired(false);
            ((CourseMaintainable) form.getViewHelperService()).setDataObject(courseWrapper);
            ((CourseMaintainable) form.getViewHelperService()).populateCourseAndReviewData(courseId, courseWrapper, true);
            detailedViewForm.setCourseInfoWrapper(courseWrapper);
            detailedViewForm.setCanRetireCourse(!((CourseMaintainable) form.getViewHelperService()).hasInProgressProposalForCourse());

            //  Load the data for the other/compare-to course.
            if (isComparison) {
                CourseInfoWrapper compareCourseWrapper = new CourseInfoWrapper();
                compareCourseWrapper.setProposalDataRequired(false);
                ((CourseMaintainable) form.getViewHelperService()).setDataObject(compareCourseWrapper);
                ((CourseMaintainable)form.getViewHelperService()).populateCourseAndReviewData(compareCourseId, compareCourseWrapper, true);
                detailedViewForm.setCompareCourseInfoWrapper(compareCourseWrapper);
            }
            detailedViewForm.setModifiableCourse(((CourseMaintainable)form.getViewHelperService()).isModifiableCourse(courseWrapper.getCourseInfo(), ContextUtils.createDefaultContextInfo()));
            detailedViewForm.setComparison(isComparison);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        form.getExtensionData().put(CurriculumManagementConstants.Export.UrlParams.EXPORT_TYPE,
                CurriculumManagementConstants.Export.FileType.PDF);

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=copyCourse")
    public ModelAndView copyCourse(@ModelAttribute("KualiForm") UifFormBase form) {

        ViewCourseForm detailedViewForm = (ViewCourseForm) form;

        Properties urlParameters = new Properties();
        /**
         * It should be always 'curriculum review' for both CS and faculty users for copy.
         */
        urlParameters.put(CurriculumManagementConstants.UrlParams.USE_CURRICULUM_REVIEW, Boolean.TRUE.toString());

        urlParameters.put(UifConstants.UrlParams.PAGE_ID, CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE);
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_COPY);
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseInfoWrapper.class.getName());
        urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, CMUtils.getCMHomeUrl());
        urlParameters.put(CourseController.UrlParams.COPY_CLU_ID, detailedViewForm.getCourseInfoWrapper().getCourseInfo().getId());
        String courseBaseUrl = CurriculumManagementConstants.ControllerRequestMappings.COURSE_MAINTENANCE.replaceFirst("/", "");
        return performRedirect(form, courseBaseUrl, urlParameters);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=retireCourse")
    public ModelAndView retireCourse(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                     HttpServletResponse response) {
        ViewCourseForm detailedViewForm = (ViewCourseForm) form;
        String courseBaseUrl = CurriculumManagementConstants.ControllerRequestMappings.CM_RETIRE_COURSE.replaceFirst("/", "");

        // if user is not a curriculum specialist, force them straight to the retire page
        if (!CourseProposalUtil.isUserCurriculumSpecialist()) {
            return performRedirect(form, courseBaseUrl, buildRetireCourseUrlParameters(detailedViewForm, true));
        }

        DialogManager dm = form.getDialogManager();
        String dialogId = dm.getCurrentDialogId();
        if (dialogId != null) {
            dm.setDialogAnswer(dialogId, form.getDialogResponse());
            dm.setDialogExplanation(dialogId, form.getDialogExplanation());
            dm.setCurrentDialogId(null);
        }

        String dialog = CurriculumManagementConstants.ProposalConfirmationDialogs.RETIRE_CONFIRMATION_DIALOG;
        if (!hasDialogBeenDisplayed(dialog, form)) {
            if (!GlobalVariables.getMessageMap().hasErrors()) {
                //redirect back to client to display confirm dialog
                return showDialog(dialog, form, request, response);
            }
        } else {
            if (hasDialogBeenAnswered(dialog, form)) {
                boolean confirmRetire = getBooleanDialogResponse(dialog, form, request, response);
                if (confirmRetire) {
                    boolean useCurriculumReview = Boolean.valueOf(detailedViewForm.isUseReviewProcess());
                    return performRedirect(form, courseBaseUrl, buildRetireCourseUrlParameters(detailedViewForm, useCurriculumReview));
                } else {
                    form.getDialogManager().removeDialog(dialog);
                }
            } else {
                return showDialog(dialog, form, request, response);
            }
        }
        return getUIFModelAndView(form);
    }

    protected Properties buildRetireCourseUrlParameters(ViewCourseForm detailedViewForm, boolean useCurriculumReview) {
        Properties urlParameters = new Properties();

        urlParameters.put(CurriculumManagementConstants.UrlParams.USE_CURRICULUM_REVIEW, Boolean.toString(useCurriculumReview));
        urlParameters.put(UifConstants.UrlParams.PAGE_ID, CurriculumManagementConstants.CoursePageIds.RETIRE_COURSE_PAGE);
        urlParameters.put(UifConstants.UrlParams.VIEW_ID, CurriculumManagementConstants.CourseViewIds.RETIRE_COURSE_VIEW);
        urlParameters.put(KRADConstants.PARAMETER_COMMAND, KewApiConstants.INITIATE_COMMAND);
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, RetireCourseWrapper.class.getName());
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.DOC_HANDLER_METHOD);
        urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, CourseProposalUtil.getViewCourseUrl());
        urlParameters.put(CurriculumManagementConstants.UrlParams.CLU_ID, detailedViewForm.getCourseInfoWrapper().getCourseInfo().getId());
        return urlParameters;

    }

    @RequestMapping(params = "methodToCall=modifyCourseCurriculumSpecialist")
    public ModelAndView modifyCourseCurriculumSpecialist(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {

        ViewCourseForm detailedViewForm = (ViewCourseForm) form;
        Properties urlParameters = new Properties();
        urlParameters.put(UifConstants.UrlParams.PAGE_ID, "CM-Modify-Version-Start-Page");
        urlParameters.put(UifConstants.UrlParams.VIEW_ID, "startProposalView");
        urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, CMUtils.getCMHomeUrl());
        urlParameters.put(CurriculumManagementConstants.UrlParams.VERSION_IND_ID, detailedViewForm.getCourseInfoWrapper().getCourseInfo().getVersion().getVersionIndId());
        urlParameters.put(CurriculumManagementConstants.UrlParams.CLU_ID, detailedViewForm.getCourseInfoWrapper().getCourseInfo().getId());
        String courseBaseUrl = CurriculumManagementConstants.ControllerRequestMappings.START_PROPOSAL.replaceFirst("/", "");
        return performRedirect(form, courseBaseUrl, urlParameters);
    }

    @RequestMapping(params = "methodToCall=modifyCourseFaculty")
    public ModelAndView modifyCourseFaculty(@ModelAttribute("KualiForm") UifFormBase form) {

        ViewCourseForm detailedViewForm = (ViewCourseForm) form;

        Properties urlParameters = new Properties();
        urlParameters.put(UifConstants.UrlParams.PAGE_ID, CurriculumManagementConstants.CoursePageIds.REVIEW_COURSE_PROPOSAL_PAGE);
        urlParameters.put(UifConstants.UrlParams.VIEW_ID, CurriculumManagementConstants.CourseViewIds.CREATE_COURSE_VIEW);
        urlParameters.put(CurriculumManagementConstants.UrlParams.USE_CURRICULUM_REVIEW,Boolean.TRUE.toString());
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, CurriculumManagementConstants.ModifyCourseStartOptions.MODIFY_WITH_A_NEW_VERSION);
        urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, CMUtils.getCMHomeUrl());
        urlParameters.put(CurriculumManagementConstants.UrlParams.VERSION_IND_ID, detailedViewForm.getCourseInfoWrapper().getCourseInfo().getVersion().getVersionIndId());

        String courseBaseUrl = CurriculumManagementConstants.ControllerRequestMappings.COURSE_MAINTENANCE.replaceFirst("/", "");

        return performRedirect(form, courseBaseUrl, urlParameters);
    }

    /**
     * This method is used for export and print.
     */
    @MethodAccessible
    @ResponseBody
    @RequestMapping(params = "methodToCall=export", method = RequestMethod.POST)
    public ResponseEntity<byte[]> export(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        /*
         * For export the "save" headers should be returned to the client. Default to true.
         * The print action link should set this param.
         */
        boolean useSaveHeaders = true;
        String saveHeader = request.getParameter(CurriculumManagementConstants.Export.UrlParams.RETURN_SAVE_HEADERS);
        if (StringUtils.isNotBlank(saveHeader)) {
            useSaveHeaders = Boolean.valueOf(saveHeader);
        }
        /*
         * PDF is the default document type.
         */
        String exportFileTypeText = request.getParameter(CurriculumManagementConstants.Export.UrlParams.EXPORT_TYPE);
        if (StringUtils.isBlank(exportFileTypeText)) {
            exportFileTypeText = FileType.PDF.toString();
        }
        FileType exportFileType = FileType.valueOf(exportFileTypeText);

        ViewCourseForm detailedViewForm = (ViewCourseForm) form;
        CourseInfoWrapper courseInfoWrapper = detailedViewForm.getCourseInfoWrapper();

        ExportCourseHelperImpl exportCourseHelper = new ExportCourseHelperImpl(courseInfoWrapper, exportFileType, useSaveHeaders);

        return exportCourseHelper.getResponseEntity();
    }


    @RequestMapping(params = "methodToCall=viewCurrentVersion")
    public ModelAndView viewCurrentVersion(@ModelAttribute("KualiForm") UifFormBase form) {

        ViewCourseForm detailedViewForm = (ViewCourseForm) form;

        try {
            CourseInfo currentCourse = ((CourseMaintainable) form.getViewHelperService()).getCurrentVersionOfCourse(detailedViewForm.getCourseInfoWrapper().getCourseInfo().getVersion().getVersionIndId(), ContextUtils.createDefaultContextInfo());
            CourseInfoWrapper courseWrapper = new CourseInfoWrapper();
            courseWrapper.setProposalDataRequired(false);
            ((CourseMaintainable) form.getViewHelperService()).setDataObject(courseWrapper);
            ((CourseMaintainable) form.getViewHelperService()).populateCourseAndReviewData(currentCourse.getId(), courseWrapper, true);
            detailedViewForm.setCourseInfoWrapper(courseWrapper);
            detailedViewForm.setCompareCourseInfoWrapper(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return getUIFModelAndView(form);
    }

}
