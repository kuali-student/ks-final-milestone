/**
 * Copyright 2005-2013 The Kuali Foundation
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
package org.kuali.student.cm.course.controller;

import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.krad.UserSessionUtils;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.exception.ValidationException;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.PropositionParameterEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.TermEditor;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.student.cm.common.util.CMUtils;
import org.kuali.student.common.ui.krad.rules.rule.event.ReturnToPreviousNodeDocumentEvent;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseFeeInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.r2.lum.course.dto.CourseVariationInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.rules.rule.event.RouteDocumentEvent;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.ErrorMessage;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.CourseViewSections;
import org.kuali.student.cm.course.form.wrapper.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.CourseCreateUnitsContentOwner;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.LoDisplayInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.LoDisplayWrapperModel;
import org.kuali.student.cm.course.form.RecentlyViewedDocsUtil;
import org.kuali.student.cm.course.form.wrapper.ResultValuesGroupInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.SupportingDocumentInfoWrapper;
import org.kuali.student.cm.course.service.CourseMaintainable;
import org.kuali.student.cm.course.util.CourseProposalUtil;
import org.kuali.student.common.object.KSObjectUtils;
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.core.constants.DocumentServiceConstants;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.service.DocumentService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * This controller handles all the requests from the 'Create a Course' UI.
 */
@Controller
@RequestMapping(value = CurriculumManagementConstants.ControllerRequestMappings.COURSE_MAINTENANCE)
public class CourseController extends CourseRuleEditorController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);

    /**
     * Request Parameters used by CourseController.
     */
    public static class UrlParams {
        /**
         * A flag which indicates whether workflow should be used or not.
         */
        public static final String USE_CURRICULUM_REVIEW = "useCurriculumReview";
        /**
         * Specifies that this proposal should copy from the CLU with the given Id.
         */
        public static final String COPY_CLU_ID = "copyCluId";
        /**
         * Specifies that this proposal should copy from the ProposalInfo with the given Id.
         */
        public static final String COPY_PROPOSAL_ID = "copyProposalId";

        private UrlParams() {}  /* Constants class. Hide the contructor. */
    }

    private static final String DECISIONS_DIALOG_KEY = "CM-Proposal-Course-DecisionDialog";

    private CourseService courseService;
    private DocumentService documentService;
    private SubjectCodeService subjectCodeService;
    /**
     * This method creates the form and in the case of a brand new proposal where this method is called after the user uses
     * the Initial Create Proposal screen, this method will also set the document type name based on the request parameter
     * {@link org.kuali.student.cm.course.controller.CourseController.UrlParams.USE_CURRICULUM_REVIEW}
     *
     * @param request
     * @return a new instance of a MaintenanceDocumentForm
     */
    @Override
    protected MaintenanceDocumentForm createInitialForm(HttpServletRequest request) {
        MaintenanceDocumentForm form = new MaintenanceDocumentForm();

        String useReviewProcessParam = request.getParameter(UrlParams.USE_CURRICULUM_REVIEW);
        // only do the manually setup of the MaintenanceDocumentForm fields if the URL_PARAM_USE_CURRICULUM_REVIEW param was passed in from initial view
        if (StringUtils.isNotBlank(useReviewProcessParam)) {
            Boolean isUseReviewProcess = new Boolean(useReviewProcessParam);
            // throw an exception if the user is not a CS user but attempts to disable Curriculum Review for a proposal
            if (!isUseReviewProcess && !CourseProposalUtil.isUserCurriculumSpecialist()) {
                throw new RuntimeException(String.format("User (%s) is not allowed to disable Curriculum Review (Workflow Approval).",
                    GlobalVariables.getUserSession().getPerson().getPrincipalName()));
            }
            // set the doc type name based on the whether the user is CS and if they have chosen to use curriculum review
            form.setDocTypeName((!isUseReviewProcess)
                    ? CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_CREATE_ADMIN
                    : CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_CREATE);
        }
        return form;
    }

    /**
     * Digs the CourseInfoWrapper out of DocumentFormBase.
     *
     * @param form The DocumentFormBase.
     * @return The CourseInfoWrapper.
     */
    protected CourseInfoWrapper getCourseInfoWrapper(DocumentFormBase form) {
        return ((CourseInfoWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject());
    }

    /**
     * Overridden because we needed a point at which the form is loaded with the document before we can initialize some
     * form fields. This method is used for when a new document is created.
     *
     * @param form - form instance that contains the doc type parameter and where
     *             the new document instance should be set
     */
    @Override
    protected void createDocument(DocumentFormBase form) throws WorkflowException {
        super.createDocument(form);
        // at this point we have the document type name set on the form so we use it to update the Course specific fields
        updateCourseForm(form);
    }

    /**
     * Overridden because we needed a point at which the form is loaded with the document before we can initialize some
     * form fields. This method is used for loading an existing document.
     *
     * @param form - form instance that contains the doc id parameter and where
     *             the retrieved document instance should be set
     */
    @Override
    protected void loadDocument(DocumentFormBase form) throws WorkflowException {
        super.loadDocument(form);
        // at this point we have the document type name set on the form so we use it to update the Course specific fields
        updateCourseForm(form);
    }

    /**
     * Updates the MaintenanceDocumentForm to set the 'useReviewProcess' property based on the document type name. If
     * the document type name is CurriculumManagementConstants#DocumentTypeNames#CourseProposal#COURSE_CREATE_ADMIN or
     * CurriculumManagementConstants#DocumentTypeNames#CourseProposal#COURSE_MODIFY_ADMIN then set 'useReviewProcss' to
     * false.
     *
     * @param form the DocumentFormBase object to update
     */
    protected void updateCourseForm(DocumentFormBase form) {
        CourseInfoWrapper wrapper = getCourseInfoWrapper(form);
        wrapper.getUiHelper()
                .setUseReviewProcess(!ArrayUtils.contains(CurriculumManagementConstants.DocumentTypeNames.ADMIN_DOC_TYPE_NAMES, form.getDocTypeName()));
        wrapper.getUiHelper()
                .setCurriculumSpecialistUser(CourseProposalUtil.isUserCurriculumSpecialist());
    }

    /**
     * This method is being overridden to validate the Review Proposal page before it is displayed.
     * <p/>
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(params = "methodToCall=docHandler")
    public ModelAndView docHandler(@ModelAttribute("KualiForm") DocumentFormBase formBase, BindingResult result, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = super.docHandler(formBase, result, request, response);

        // if page id is empty, assume we want the REVIEW page (used by workflow)
        if (StringUtils.isBlank(formBase.getPageId())) {
            formBase.setPageId(CurriculumManagementConstants.CourseViewPageIds.REVIEW_COURSE_PROPOSAL);
        }

        if (formBase.getPageId().equals(CurriculumManagementConstants.CourseViewPageIds.REVIEW_COURSE_PROPOSAL)) {
            //  Build a redirect to the reviewCourseProposal handler for validation.
            java.util.Map requestParameterMap = request.getParameterMap();
            Properties urlParameters = new Properties();
            for (Object p : requestParameterMap.entrySet()) {
                Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) p;
                urlParameters.put((String) entry.getKey(), ((String[]) entry.getValue())[0]);
            }

            urlParameters.put("methodToCall", "reviewCourseProposal");
            urlParameters.put("formKey", formBase.getFormKey());

            /*
             * Calling performRedirect() to build the response, but undoing the call to form.setRequestRedirect(true)
             * that happens within performRedirect() before returning. Setting that flag causes the postedView to be
             * discarded in UifControllerHandlerInterceptor#afterCompletion(). If the postedView isn't available the
             * validation in the reviewCourseProposal handler method will fail.
             */
            String courseBaseUrl = CurriculumManagementConstants.ControllerRequestMappings.COURSE_MAINTENANCE.replaceFirst("/", "");
            ModelAndView mv = performRedirect(formBase, courseBaseUrl, urlParameters);
            formBase.setRequestRedirected(false);
            return mv;
        }
        return modelAndView;
    }

    @Override
    @RequestMapping(params = "methodToCall=" + KRADConstants.Maintenance.METHOD_TO_CALL_COPY)
    public ModelAndView maintenanceCopy(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
               HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*
         * Just initialize the document here. Calling the super method causes the entire maintainableImpl to get serialized
         * which is too much overhead for what we need (Start create a new document and prefill the data from an existing model).
         */
        createDocument(form);

        CourseInfoWrapper wrapper = (CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject();

        /*
         * Check for copy params.
         */
        String copyCluId = request.getParameter(UrlParams.COPY_CLU_ID);
        if (StringUtils.isNotBlank(copyCluId)) {
            /*
             * If a CLU id is present then load the Course and all of the related data then reset the data so that new
             * entities are created when the data is persisted.
             */
            try {
                ((CourseMaintainable) form.getDocument().getNewMaintainableObject())
                        .populateCourseAndReviewData(copyCluId, wrapper, false);
            } catch (Exception e) {
                //  !!! Handle Me!
                LOG.error("Couldn't find a course for id {}", copyCluId);
            }
            ((CourseMaintainable) form.getDocument().getNewMaintainableObject()).resetDataObject(wrapper);
        } else {
            /*
             * If proposal ID is present then load the proposal and course data then reset it so that new entities
             * are created on save.
             */
            String proposalId = form.getActionParamaterValue(UrlParams.COPY_PROPOSAL_ID);
            if (StringUtils.isNotBlank(proposalId)) {
                wrapper.getProposalInfo().setName("This is a copy proposal");
            }
        }
        return getUIFModelAndView(form);
    }

    /**
     * This method performs the KRAD UI data dictionary and Service layer data dictionary validation before it routes the document instance contained on the form.
     * Based on the validation result user will be shown validation errors on the page or Submit confirmation dialog.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return ModelAndView
     */
    @Override
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        String dialog = CurriculumManagementConstants.COURSE_SUBMIT_CONFIRMATION_DIALOG;
        if ( ! hasDialogBeenDisplayed(dialog, form)) {

            CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);

            //Perform KRAD UI Data Dictionary Validation
            // manually call the view validation service as this validation cannot be run client-side in current setup
            KRADServiceLocatorWeb.getViewValidationService().validateView(form, KewApiConstants.ROUTE_HEADER_ENROUTE_CD);

            //Perform Rules validation
            KRADServiceLocatorWeb.getKualiRuleService().applyRules(new RouteDocumentEvent(form.getDocument()));

            List<ValidationResultInfo> validationResultInfoList = null;

            try {
                //Perform Service Layer Data Dictionary validation
                validationResultInfoList = getCourseService().validateCourse("OBJECT", courseInfoWrapper.getCourseInfo(), ContextUtils.createDefaultContextInfo());
            } catch (Exception ex) {
                LOG.error("Error occurred while performing service layer validation for Submit", ex);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, KSObjectUtils.unwrapException(20, ex).getMessage());
            }

            bindValidationErrorsToPath(validationResultInfoList);

            if (!GlobalVariables.getMessageMap().hasErrors()) {
                //redirect back to client to display confirm dialog
                return showDialog(dialog, form, request, response);
            }
        } else {
            if (hasDialogBeenAnswered(dialog,form)) {
                boolean confirmSubmit = getBooleanDialogResponse(dialog, form, request, response);
                form.getDialogManager().resetDialogStatus(dialog);
                if (confirmSubmit) {
                    //route the document
                    return super.route(form,result, request,response);
                }
            }
        }
        return getUIFModelAndView(form);
    }

    /**
     * This method is called form the UI when a user wants to return a course proposal to a previous node.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return ModelAndView object
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=returnToPreviousNode")
    public ModelAndView returnToPreviousNode(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) {
        String dialog = CurriculumManagementConstants.COURSE_RETURN_TO_PREVIOUS_NODE_DIALOG;
        if ( ! hasDialogBeenDisplayed(dialog, form)) {

            CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);

            //Perform KRAD UI Data Dictionary Validation
            // manually call the view validation service as this validation cannot be run client-side in current setup
            KRADServiceLocatorWeb.getViewValidationService().validateView(form, KewApiConstants.ROUTE_HEADER_PROCESSED_CD);

            //Perform Rules validation
            KRADServiceLocatorWeb.getKualiRuleService().applyRules(new RouteDocumentEvent(form.getDocument()));

            List<ValidationResultInfo> validationResultInfoList = null;

            try {
                //Perform Service Layer Data Dictionary validation
                validationResultInfoList = getCourseService().validateCourse("OBJECT", courseInfoWrapper.getCourseInfo(), ContextUtils.createDefaultContextInfo());
            } catch (Exception ex) {
                LOG.error("Error occurred while performing service layer validation for Submit", ex);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, KSObjectUtils.unwrapException(20, ex).getMessage());
            }

            bindValidationErrorsToPath(validationResultInfoList);

            if (!GlobalVariables.getMessageMap().hasErrors()) {
                //redirect back to client to display confirm dialog
                return showDialog(dialog, form, request, response);
            }
        } else {
            if (hasDialogBeenAnswered(dialog,form)) {
                boolean confirmSubmit = getBooleanDialogResponse(dialog, form, request, response);
                form.getDialogManager().resetDialogStatus(dialog);
                if (confirmSubmit) {
                    //route the document
                    performReturnToPreviousNode(form,result, request,response);
                    /*
                    Here's another location where we diverge from the default KRAD code. Normally here there would be code to handle the persisting
                    of attachments but since CM uses it's own internal SupportingDocuments functionality we can simply ignore the KRAD system.
                    */
                    return getUIFModelAndView(form);

                }
            }
        }
        return getUIFModelAndView(form);
    }

    protected void performReturnToPreviousNode(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                                    HttpServletRequest request, HttpServletResponse response) {
        CourseControllerTransactionHelper helper = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseControllerTransactionHelper", CourseControllerTransactionHelper.class.getSimpleName()));
        helper.performReturnToPreviousNodeWork(form, this);
        List<ErrorMessage> infoMessages = GlobalVariables.getMessageMap().getInfoMessagesForProperty(KRADConstants.GLOBAL_MESSAGES);
        if (infoMessages != null) {
            for (ErrorMessage message : infoMessages) {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, message.getErrorKey(), message.getMessageParameters());
            }
            GlobalVariables.getMessageMap().removeAllInfoMessagesForProperty(KRADConstants.GLOBAL_MESSAGES);
        }
    }

    /**
     * This method is here to actually do the work of the return to previous action since that action is not built into the KRAD
     * {@link DocumentService}. This is an extrapolation of what would happen in the various KRAD classes in a single method in
     * this controller.
     */
    public void performReturnToPreviousNodeWork(@ModelAttribute("KualiForm") DocumentFormBase form) {
        MaintenanceDocument document = ((MaintenanceDocumentForm) form).getDocument();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Performing workflow action 'return to previous node' for document: " + document.getDocumentNumber());
        }

        try {
            document.prepareForSave();
            Document savedDocument = getDocumentService().validateAndPersistDocument(document, new ReturnToPreviousNodeDocumentEvent(document));
            getDocumentService().prepareWorkflowDocument(savedDocument);
            CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper)((MaintenanceDocument)savedDocument).getNewMaintainableObject().getDataObject();
            savedDocument.getDocumentHeader().getWorkflowDocument().returnToPreviousNode(form.getAnnotation(), courseInfoWrapper.getReviewProposalDisplay().getReturnToPreviousNodeName());
            UserSessionUtils.addWorkflowDocument(GlobalVariables.getUserSession(),
                    savedDocument.getDocumentHeader().getWorkflowDocument());

            /*
            In document service, for most of the ActionEvent calls, this is where the DocumentService.removeAdHocPersonsAndWorkgroups method is
            called but since it's private and since CM does not use that functionality (in favor of our internal Auth/Collab stuff), we're
            ignoring it for now
            */

            // now push potentially updated document back into the form
            form.setDocument(savedDocument);

            GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_MESSAGES, CurriculumManagementConstants.MessageKeys.SUCCESS_PROPOSAL_RETURN_TO_PREVIOUS_NODE);
        } catch (ValidationException e) {
            // log the error and swallow exception so screen will draw with errors.
            // we don't want the exception to bubble up and the user to see an incident page, but instead just return to
            // the page and display the actual errors. This would need a fix to the API at some point.
            KRADUtils.logErrors();
            LOG.error("Validation Exception occured for document :" + document.getDocumentNumber(), e);

            // if no errors in map then throw runtime because something bad happened
            if (GlobalVariables.getMessageMap().hasNoErrors()) {
                throw new RiceRuntimeException("Validation Exception with no error message.", e);
            }
        } catch (Exception e) {
            throw new RiceRuntimeException(
                    "Exception trying to execute the 'return to previous node' for document: " + document
                            .getDocumentNumber(), e);
        }

        form.setAnnotation("");
    }

    /**
     * Load the course proposal review page
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=reviewCourseProposal")
    public ModelAndView reviewCourseProposal(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        ((CourseMaintainable) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject()).updateReview();

        //  Validate
        KRADServiceLocatorWeb.getViewValidationService().validateViewAgainstNextState(form);
        if (GlobalVariables.getMessageMap().hasErrors()) {
            CourseInfoWrapper wrapper = getCourseInfoWrapper(form);
            wrapper.setMissingRequiredFields(true);
        }
        return getUIFModelAndView(form, CurriculumManagementConstants.CourseViewPageIds.REVIEW_COURSE_PROPOSAL);
    }

    /**
     * Load the course proposal review page
     */
    @RequestMapping(params = "methodToCall=editCourseProposalPage")
    public ModelAndView editCourseProposalPage(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        String displaySectionId = form.getActionParameters().get("displaySection");
        CourseInfoWrapper wrapper = getCourseInfoWrapper(form);
        if (wrapper.getInstructorWrappers().size() == 0) {
            wrapper.getInstructorWrappers().add(new CluInstructorInfoWrapper());
        }
        if (wrapper.getCollaboratorWrappers().size() == 0) {
            wrapper.getCollaboratorWrappers().add(new CollaboratorWrapper());
        }
        if (displaySectionId == null) {
            wrapper.getUiHelper().setSelectedSection(CourseViewSections.COURSE_INFO);
        } else {
            CourseViewSections section = CourseViewSections.getSection(displaySectionId);
            wrapper.getUiHelper().setSelectedSection(section);
        }

        return getUIFModelAndView(form, CurriculumManagementConstants.CourseViewPageIds.CREATE_COURSE);
    }

    /**
     * Add a Learning Objective
     *
     * @param form     {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request  {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @return The new {@link ModelAndView} that contains the newly added blank Learning Objective at the top.
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=addLearningObjective")
    public ModelAndView addLearningObjective(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) {
        final CourseMaintainable maintainable = getCourseMaintainableFrom(form);

        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);

        LoDisplayInfoWrapper loDisplayInfoWrapper = new LoDisplayInfoWrapper();
        courseInfoWrapper.getLoDisplayWrapperModel().addLoWrapperAtTop(loDisplayInfoWrapper);

        maintainable.setLOActions();

        return getUIFModelAndView(form);
    }

    /**
     * @param message - the error message (both to log and throw as a new exception)
     */
    protected void logAndThrowRuntime(String message) {
        LOG.error(message);
        throw new RuntimeException(message);
    }

    /**
     * Modify an existing course
     *
     * @param form
     * @param courseId to modify
     */
    @RequestMapping(value = "/modify/{courseId}")
    public ModelAndView initiateModify(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form,
                                       final @PathVariable String courseId,
                                       final BindingResult result,
                                       final HttpServletRequest request,
                                       final HttpServletResponse response) throws Exception {

        form.setDocTypeName(CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_MODIFY);
        form.setDataObjectClassName(CourseInfo.class.getName());
        final ModelAndView retval = super.docHandler(form, result, request, response);

        return retval;
    }

    /**
     * Retire an existing course
     *
     * @param form
     * @param courseId to retire
     */
    @RequestMapping(value = "/retire/{courseId}")
    public ModelAndView initiateRetire(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form,
                                       final @PathVariable String courseId,
                                       final BindingResult result,
                                       final HttpServletRequest request,
                                       final HttpServletResponse response) throws Exception {
        form.setDocTypeName(CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_RETIRE);
        form.setDataObjectClassName(CourseInfo.class.getName());
        final ModelAndView retval = super.docHandler(form, result, request, response);

        return retval;
    }

    public void performWorkflowActionSuper(DocumentFormBase form, UifConstants.WorkflowAction action, boolean checkSensitiveData) {
        super.performWorkflowAction(form, action, checkSensitiveData);
    }

    /**
     * Here we move the success messages displayed in UI from header to growl.
     *
     * @param form
     * @param action
     * @param checkSensitiveData
     */
    @Override
    protected void performWorkflowAction(DocumentFormBase form, UifConstants.WorkflowAction action, boolean checkSensitiveData) {
        CourseControllerTransactionHelper helper = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseControllerTransactionHelper", CourseControllerTransactionHelper.class.getSimpleName()));
        helper.performWorkflowActionSuper(form, action, checkSensitiveData, this);
        List<ErrorMessage> infoMessages = GlobalVariables.getMessageMap().getInfoMessagesForProperty(KRADConstants.GLOBAL_MESSAGES);
        if (infoMessages != null) {
            for (ErrorMessage message : infoMessages) {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, message.getErrorKey(), message.getMessageParameters());
            }
            GlobalVariables.getMessageMap().removeAllInfoMessagesForProperty(KRADConstants.GLOBAL_MESSAGES);
        }
    }

    /**
     * This will save the Course Proposal.
     *
     * @param form     {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request  {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @return The new {@link ModelAndView} that contains the newly created/updated {@CourseInfo} and {@ProposalInfo} information.
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=saveProposal")
    public ModelAndView saveProposal(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);
        form.getDocument().getDocumentHeader().setDocumentDescription(courseInfoWrapper.getProposalInfo().getName());

        ModelAndView modelAndView;

        modelAndView = save(form, result, request, response);

        if (GlobalVariables.getMessageMap().hasErrors()) {
            return modelAndView;
        }

        RecentlyViewedDocsUtil.addRecentDoc(form.getDocument().getDocumentHeader().getDocumentDescription(),
                form.getDocument().getDocumentHeader().getWorkflowDocument().getDocumentHandlerUrl() + "&"
                        + KewApiConstants.COMMAND_PARAMETER + "="
                        + KewApiConstants.DOCSEARCH_COMMAND + "&"
                        + KewApiConstants.DOCUMENT_ID_PARAMETER + "="
                        + form.getDocument().getDocumentHeader().getWorkflowDocument().getDocumentId());

        String nextOrCurrentPage = form.getActionParameters().get("displayPage");

        if (StringUtils.equalsIgnoreCase(nextOrCurrentPage, "NEXT")) {
            CourseViewSections currentSection = courseInfoWrapper.getUiHelper().getSelectedSection();
            if (currentSection.ordinal() < CourseViewSections.values().length) {
                CourseViewSections nextSection = CourseViewSections.values()[currentSection.ordinal() + 1];
                courseInfoWrapper.getUiHelper().setSelectedSection(nextSection);
            }
            return getUIFModelAndView(form);
        } else if (StringUtils.equalsIgnoreCase(nextOrCurrentPage, "CM-Proposal-Course-View-ReviewProposalLink")) {
            return getUIFModelAndView(form, CurriculumManagementConstants.CourseViewPageIds.REVIEW_COURSE_PROPOSAL);
        } else {
            return getUIFModelAndView(form);
        }
    }

    @Override
    @RequestMapping(params = "methodToCall=blanketApprove")
    public ModelAndView blanketApprove(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        saveProposal((MaintenanceDocumentForm) form, result, request, response);
        return super.blanketApprove(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=previousPage")
    public ModelAndView previousPage(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, HttpServletRequest request) {
        return getUIFModelAndView(form);
    }

    /**
     * Downloads supporting documents to the browser.
     *
     * @param form
     * @param response
     * @return
     * @throws Exception
     */
    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=downloadSupportingDoc")
    public ModelAndView downloadSupportingDoc(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, HttpServletResponse response) throws Exception {

        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        final int selectedLineIndex = Integer.parseInt(selectedLine);

        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);

        SupportingDocumentInfoWrapper supportingDocument = courseInfoWrapper.getSupportingDocs().get(selectedLineIndex);
        DocumentInfo document = null;
        String documentName = null;
        byte[] bytes = null;

        if (supportingDocument.isNewDto()) {
            bytes = supportingDocument.getUploadedDoc();
            documentName = supportingDocument.getDocumentName();
        } else {
            try {
                document = getSupportingDocumentService().getDocument(supportingDocument.getDocumentId(), ContextUtils.createDefaultContextInfo());
                bytes = Base64.decodeBase64(document.getDocumentBinary().getBinary());
                documentName = document.getFileName();
            } catch (Exception ex) {
                LOG.error("Exception occurred while retrieving the document", ex);
            }
        }

        BufferedInputStream byteStream = new BufferedInputStream(new ByteArrayInputStream(bytes));

        try {
            KRADUtils.addAttachmentToResponse(response, byteStream,
                    CurriculumManagementConstants.SUPPORTING_DOC_MIME_TYPE, documentName,
                    bytes.length);
        } catch (Exception ex) {
            LOG.error("Exception occurred while attaching the document to response", ex);
            throw new RuntimeException("An error has occurred while downloading the file. Please try again.");
        }

        /*} else {
            LOG.error("Sorry, the file could not be retrieved.  It may not exist, or the server could not be contacted.");
            throw new RuntimeException("Document cannot be found.");
        }*/

        return null;

    }

    /**
     * Server-side action for rendering the decisions lightbox
     *
     * @param form     {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request  {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=showDecisions")
    public ModelAndView showDecisions(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (!hasDialogBeenAnswered(DECISIONS_DIALOG_KEY, form)) {
//            redrawDecisionTable(form);
            return showDialog(DECISIONS_DIALOG_KEY, form, request, response);
        }

        form.getDialogManager().removeDialog(DECISIONS_DIALOG_KEY);

        return getUIFModelAndView(form);
    }

    /**
     * Executed when the add line button is clicked for adding Curriculum Oversight
     *
     * @param form     the {@link MaintenanceDocumentForm} associated with the request
     * @param result
     * @param request  the {@link HttpServletRequest} instance
     * @param response the {@link HttpServletResponse} instance
     * @return ModelAndView of the next view
     */
    @RequestMapping(params = "methodToCall=refreshOversightSection")
    protected ModelAndView refreshOversightSection(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form,
                                                   final BindingResult result,
                                                   final HttpServletRequest request,
                                                   final HttpServletResponse response) {
        LOG.info("Adding a unitsContentOwner");
        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);
        if (StringUtils.isBlank(courseInfoWrapper.getPreviousSubjectCode()) ||
                !StringUtils.equals(courseInfoWrapper.getPreviousSubjectCode(), courseInfoWrapper.getCourseInfo().getSubjectArea())) {
            courseInfoWrapper.getUnitsContentOwner().clear();
            courseInfoWrapper.getUnitsContentOwner().add(new CourseCreateUnitsContentOwner());
            courseInfoWrapper.setPreviousSubjectCode(courseInfoWrapper.getCourseInfo().getSubjectArea());
        }

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=refreshCourseLogistics")
    protected ModelAndView refreshCourseLogistics(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form,
                                                  final BindingResult result,
                                                  final HttpServletRequest request,
                                                  final HttpServletResponse response) {
        LOG.info("Adding a unitsContentOwner");
        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);

        String outComeIndex = request.getParameter("outComeIndex");
        ResultValuesGroupInfoWrapper rvg = courseInfoWrapper.getCreditOptionWrappers().get(Integer.parseInt(outComeIndex));
        rvg.getUiHelper().setResultValue("");

        return getUIFModelAndView(form);
    }

    /**
     * Lookup Organization by subject area and organization id
     *
     * @param code
     * @param orgId
     * @return KeyValue
     */
    protected KeyValue getOrganizationBy(final String code, final String orgId) {
        LOG.debug("Using code: {} and orgId: {} for the search", code, orgId);
        final SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("subjectCode.search.orgsForSubjectCode");
        searchRequest.addParam("subjectCode.queryParam.code", code);
        searchRequest.addParam("subjectCode.queryParam.optionalOrgId", orgId);

        try {
            for (final SearchResultRowInfo result
                    : getSubjectCodeService().search(searchRequest, ContextUtils.createDefaultContextInfo()).getRows()) {

                String subjectCodeId = "";
                String subjectCodeOptionalLongName = "";

                for (final SearchResultCellInfo resultCell : result.getCells()) {
                    if ("subjectCode.resultColumn.orgId".equals(resultCell.getKey())) {
                        subjectCodeId = resultCell.getValue();
                    } else if ("subjectCode.resultColumn.orgLongName".equals(resultCell.getKey())) {
                        subjectCodeOptionalLongName = resultCell.getValue();
                    }
                }
                return new ConcreteKeyValue(subjectCodeOptionalLongName, subjectCodeId);
            }
        } catch (Exception e) {
            LOG.error("Error building KeyValues List", e);
            throw new RuntimeException(e);
        }

        LOG.info("Returning a null from org search");
        return null;
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=moveLearningObjectiveUp")
    public ModelAndView moveLearningObjectiveUp(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form)
    throws Exception {

        LoDisplayWrapperModel loModel = setupLoModel(form);
        loModel.moveUpCurrent();

        CourseMaintainable maintainable = getCourseMaintainableFrom(form);
        maintainable.setLOActions();

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=moveLearningObjectiveDown")
    public ModelAndView moveLearningObjectiveDown(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form)
    throws Exception {

        LoDisplayWrapperModel loItemModel = setupLoModel(form);
        loItemModel.moveDownCurrent();

        CourseMaintainable maintainable = getCourseMaintainableFrom(form);
        maintainable.setLOActions();

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=moveLearningObjectiveRight")
    public ModelAndView moveLearningObjectiveRight(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form)
    throws Exception {

        LoDisplayWrapperModel loItemModel = setupLoModel(form);
        loItemModel.indentCurrent();

        CourseMaintainable maintainable = getCourseMaintainableFrom(form);
        maintainable.setLOActions();

        return getUIFModelAndView(form);
    }

    /**
     * Handles menu navigation between view pages
     */
    @Override
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=navigate")
    public ModelAndView navigate(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) {
        final ModelAndView retval = super.navigate(form, result, request, response);
        final CourseMaintainable maintainable = getCourseMaintainableFrom((MaintenanceDocumentForm) form);

        ((CourseMaintainable) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject()).updateReview();

        return retval;
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=moveLearningObjectiveLeft")
    public ModelAndView moveLearningObjectiveLeft(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form)
    throws Exception {

        LoDisplayWrapperModel loItemModel = setupLoModel(form);
        loItemModel.outdentCurrent();

        CourseMaintainable maintainable = getCourseMaintainableFrom(form);
        maintainable.setLOActions();

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=deleteLO")
    public ModelAndView deleteLO(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        LoDisplayWrapperModel loModel = setupLoModel(form);

        loModel.deleteLearningObjective(loModel.getCurrentLoWrapper());

        CourseMaintainable maintainable = getCourseMaintainableFrom(form);
        maintainable.setLOActions();

        return getUIFModelAndView(form);
    }

    private LoDisplayWrapperModel setupLoModel(MaintenanceDocumentForm form) {

        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        final int selectedLineIndex = Integer.parseInt(selectedLine);

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        LoDisplayWrapperModel loDisplayWrapperModel = courseInfoWrapper.getLoDisplayWrapperModel();
        List<LoDisplayInfoWrapper> loWrappers = loDisplayWrapperModel.getLoWrappers();
        LoDisplayInfoWrapper selectedLoWrapper = loWrappers.get(selectedLineIndex);
        loDisplayWrapperModel.setCurrentLoWrapper(selectedLoWrapper);

        return loDisplayWrapperModel;
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=copyProposal")
    public ModelAndView copyProposal(@ModelAttribute("KualiForm") DocumentFormBase form) {

        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);

        Properties urlParameters = new Properties();
        /**
         * It should be always 'curriculum review' for both CS and faculty users for copy.
         */
        urlParameters.put(CourseController.UrlParams.USE_CURRICULUM_REVIEW,Boolean.TRUE.toString());
        urlParameters.put(UifConstants.UrlParams.PAGE_ID, CurriculumManagementConstants.CourseViewPageIds.CREATE_COURSE);
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_COPY);
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseInfoWrapper.class.getName());
        urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, CMUtils.getCMHomeUrl() );
        urlParameters.put(UrlParams.COPY_PROPOSAL_ID, courseInfoWrapper.getProposalInfo().getId());
        urlParameters.put(KRADConstants.OVERRIDE_KEYS, CourseController.UrlParams.COPY_CLU_ID);

        String courseBaseUrl = CurriculumManagementConstants.ControllerRequestMappings.COURSE_MAINTENANCE.replaceFirst("/", "");


        return performRedirect(form, courseBaseUrl, urlParameters);

    }


    /**
     *  Binds the each validation errors with its property path
     * @param validationResultInfoList
     */
    protected void bindValidationErrorsToPath(List<ValidationResultInfo> validationResultInfoList) {
        if (validationResultInfoList != null && !validationResultInfoList.isEmpty()) {
            for( ValidationResultInfo error : validationResultInfoList ) {
                String element = error.getElement().replace("/0","").replace("/","");
                String elementPath = null;

                switch(element) {
                    case "courseTitle":
                        elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.courseTitle";
                        break;
                    case "subjectArea":
                        elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.subjectArea";
                        break;
                    case "courseNumberSuffix":
                        elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.courseNumberSuffix";
                        break;
                    case "campusLocations":
                        elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".reviewProposalDisplay.governanceSection.campusLocationsAsString";
                        break;
                    case "startTerm":
                        elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".reviewProposalDisplay.activeDatesSection.startTerm";
                        break;
                    case "transcriptTitle":
                        elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.transcriptTitle";
                        break;
                    case "finalExamStatus":
                        elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".reviewProposalDisplay.courseLogisticsSection.finalExamStatus";
                        break;
                    case "gradingOptions":
                        elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".reviewProposalDisplay.courseLogisticsSection.gradingOptionsAsString";
                        break;
                    case "unitsContentOwner":
                        elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".reviewProposalDisplay.governanceSection.curriculumOversightAsString";
                        break;
                    default:
                        elementPath = KRADConstants.GLOBAL_MESSAGES;
                }
                GlobalVariables.getMessageMap().putError(elementPath, RiceKeyConstants.ERROR_CUSTOM, error.getMessage());
            }
        }
    }

    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, CourseServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseService;
    }

    protected DocumentService getSupportingDocumentService() {
        if (documentService == null) {
            documentService = (DocumentService) GlobalResourceLoader.getService(new QName(DocumentServiceConstants.NAMESPACE, "DocumentService"));
        }
        return documentService;
    }

    protected SubjectCodeService getSubjectCodeService() {
        if (subjectCodeService == null) {
            subjectCodeService = GlobalResourceLoader.getService(new QName(CourseServiceConstants.NAMESPACE_SUBJECTCODE, SubjectCodeService.class.getSimpleName()));
        }
        return subjectCodeService;
    }
}
