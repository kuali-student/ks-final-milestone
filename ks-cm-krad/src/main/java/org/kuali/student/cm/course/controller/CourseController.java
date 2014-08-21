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

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSessionUtils;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.exception.ValidationException;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.rule.event.RouteDocumentEvent;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.DialogManager;
import org.kuali.rice.krad.util.ErrorMessage;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.common.util.CMUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.CourseViewSections;
import org.kuali.student.cm.course.form.RecentlyViewedDocsUtil;
import org.kuali.student.cm.course.form.wrapper.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.CourseCreateUnitsContentOwner;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.LoDisplayInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.LoDisplayWrapperModel;
import org.kuali.student.cm.course.form.wrapper.ResultValuesGroupInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.SupportingDocumentInfoWrapper;
import org.kuali.student.cm.course.service.CourseMaintainable;
import org.kuali.student.cm.course.util.CourseProposalUtil;
import org.kuali.student.common.object.KSObjectUtils;
import org.kuali.student.common.ui.krad.rules.rule.event.ReturnToPreviousNodeDocumentEvent;
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.constants.CommentServiceConstants;
import org.kuali.student.r2.core.constants.DocumentServiceConstants;
import org.kuali.student.r2.core.constants.ProposalServiceConstants;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.service.DocumentService;
import org.kuali.student.r2.core.proposal.service.ProposalService;
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

    public static class WorkflowActions {
        public static final String WITHDRAW = "withdraw";
        public static final String RETURN_TO_PREVIOUS = "returnToPreviousNode";
    }

    private static final String DECISIONS_DIALOG_KEY = "CM-Proposal-Course-DecisionDialog";

    private CourseService courseService;
    private DocumentService documentService;
    private SubjectCodeService subjectCodeService;
    private ProposalService proposalService;
    private CommentService commentService;

    /**
     * This method creates the form and in the case of a brand new proposal where this method is called after the user uses
     * the Initial Create Proposal screen, this method will also set the document type name based on the request parameter
     * {@link org.kuali.student.cm.course.controller.CourseController.UrlParams#USE_CURRICULUM_REVIEW}
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

        form.getExtensionData().put(CurriculumManagementConstants.Export.EXPORT_TYPE,CurriculumManagementConstants.Export.PDF);

        return form;
    }

    // TODO: Remove this workaround class once KS has been updated to Rice 2.5 (https://jira.kuali.org/browse/KSCM-2560)
    @Override
    protected ModelAndView showDialog(String dialogId, UifFormBase form, HttpServletRequest request,
                                      HttpServletResponse response) {
        ModelAndView modelAndView = super.showDialog(dialogId, form, request, response);
        getCourseInfoWrapper((DocumentFormBase)form).getUiHelper().getDialogExplanations().put(dialogId, "");
        return modelAndView;
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
            formBase.setPageId(CurriculumManagementConstants.CoursePageIds.REVIEW_COURSE_PROPOSAL_PAGE);
        }

        if (formBase.getPageId().equals(CurriculumManagementConstants.CoursePageIds.REVIEW_COURSE_PROPOSAL_PAGE)) {
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

    /**
     * This method handles requests to create a new course proposal by copying from an existing
     * course or an existing proposal and its related course. A course or proposal id to copy from
     * is required has to be present in the reuqest params.
     */
    @Override
    @RequestMapping(params = "methodToCall=" + KRADConstants.Maintenance.METHOD_TO_CALL_COPY)
    public ModelAndView maintenanceCopy(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
               HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*
         * Just initialize the document here. Calling the super method causes the entire maintainableImpl to get serialized
         * which is too much overhead for what we need (Start create a new document and prefill the data from an existing model).
         */
        createDocument(form);

        CourseMaintainable viewHelper = (CourseMaintainable) form.getDocument().getNewMaintainableObject();

        /*
         * Check for copy params and populate the dataObject.
         */
        String copyCluId = request.getParameter(UrlParams.COPY_CLU_ID);
        //  Save the UI Helper because it has already been initialized and we'll lose that info below when we create a new CourseInfoWrapper.
        CourseInfoWrapper.CreateCourseUIHelper uiHelper = ((CourseInfoWrapper) viewHelper.getDataObject()).getUiHelper();

        if (StringUtils.isNotBlank(copyCluId)) {
            //  Populate the Course and Rule data.
            try {
                CourseInfoWrapper target = viewHelper.copyCourse(copyCluId);
                viewHelper.setDataObject(target);
            } catch (Exception e) {
                String msg = String.format("Unable to copy course [%s].", copyCluId);
                LOG.error(msg, e);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CurriculumManagementConstants.MessageKeys.ERROR_COPY_PROPOSAL_FAILED);
            }
        } else {
            String copyProposalId = request.getParameter(UrlParams.COPY_PROPOSAL_ID);
            if (StringUtils.isNotBlank(copyProposalId)) {
                try {
                    CourseInfoWrapper target = viewHelper.copyProposal(copyProposalId);
                    viewHelper.setDataObject(target);
                } catch (Exception e) {
                    String msg = String.format("Unable to copy proposal [%s].", copyProposalId);
                    LOG.error(msg, e);
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CurriculumManagementConstants.MessageKeys.ERROR_COPY_PROPOSAL_FAILED);
                }
            } else {
                //  If not CLU or proposal IDs was given then display an error message.
                LOG.error("No copy id was provided.");
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CurriculumManagementConstants.MessageKeys.ERROR_NO_COPY_ID_PROVIDED);
            }
        }

        //  Replace the UI Wrapper with the one we saved above.
        ((CourseInfoWrapper) viewHelper.getDataObject()).setUiHelper(uiHelper);
        return getUIFModelAndView(form);
    }

    @Override
    @RequestMapping(params = "methodToCall=cancel")
    public ModelAndView cancel(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {
        DocumentFormBase documentForm = (DocumentFormBase) form;
        String dialog = CurriculumManagementConstants.proposalConfirmationDialogs.CANCEL_PROPOSAL_CONFIRMATION_DIALOG;
        if (!hasDialogBeenDisplayed(dialog, documentForm)) {
            return showDialog(dialog, documentForm, request, response);
        }
        else {
            DialogManager dm = documentForm.getDialogManager();
            String dialogId = dm.getCurrentDialogId();
            if(dialogId != null) {
                dm.setDialogAnswer(dialogId, documentForm.getDialogResponse());
                dm.setDialogExplanation(dialogId, documentForm.getDialogExplanation());
                dm.setCurrentDialogId(null);
            }
            if (hasDialogBeenAnswered(dialog, documentForm)) {
                boolean confirmSubmit = getBooleanDialogResponse(dialog, documentForm, request, response);
                if (confirmSubmit) {
                    CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(documentForm);
//                    super.cancel(documentForm,result,request,response);
                    // cannot call super class method as the redirect methods are causing an error when loading indicator is used after dialog confirmation is clicked
                    performWorkflowAction(documentForm, UifConstants.WorkflowAction.CANCEL, false);

                    // setShowMessage boolean decides whether to show the error message or not
                    courseInfoWrapper.getUiHelper().setShowMessage(false);
                    documentForm.getDialogManager().removeDialog(dialog);
                    // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
                    courseInfoWrapper.getUiHelper().setPendingWorkflowAction(true);
                    documentForm.setPageId("CM-Proposal-Review-Course-Page");
                    documentForm.setMethodToCall("docHandler");
                    String href = CourseProposalUtil.buildCourseProposalUrl("docHandler", "CM-Proposal-Review-Course-Page", documentForm.getDocument().getDocumentNumber());
                    return performRedirect(documentForm,href);
                }   else {
                    documentForm.getDialogManager().removeDialog(dialog);
                }
            }
            else{
                return showDialog(dialog, documentForm, request, response);
            }
        }
        return getUIFModelAndView(documentForm);
    }

    /**
     * This method performs the KRAD UI data dictionary and Service layer data dictionary validation before it routes the document instance contained on the form.
     * Based on the validation result user will be shown validation errors on the page or Submit confirmation dialog.
     */
    @Override
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        DialogManager dm = form.getDialogManager();
        String dialogId = dm.getCurrentDialogId();
        if(dialogId != null) {
            dm.setDialogAnswer(dialogId, form.getDialogResponse());
            dm.setDialogExplanation(dialogId, form.getDialogExplanation());
            dm.setCurrentDialogId(null);
        }

        String dialog = CurriculumManagementConstants.proposalConfirmationDialogs.COURSE_SUBMIT_CONFIRMATION_DIALOG;
        if ( ! hasDialogBeenDisplayed(dialog, form)) {
            doValidationForProposal(form, KewApiConstants.ROUTE_HEADER_ENROUTE_CD, null);

            if (!GlobalVariables.getMessageMap().hasErrors()) {
                //redirect back to client to display confirm dialog
                return showDialog(dialog, form, request, response);
            }
        } else {
            if (hasDialogBeenAnswered(dialog, form)) {
                boolean confirmSubmit = getBooleanDialogResponse(dialog, form, request, response);
                if (confirmSubmit) {
                    //route the document
                    // since the route method does not redirect the user, we can use the superclass method
                    ModelAndView modelAndView = super.route(form,result, request,response);
                    form.getDialogManager().removeDialog(dialog);
                    CourseInfoWrapper wrapper = getCourseInfoWrapper(form);
                    // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
                    wrapper.getUiHelper().setPendingWorkflowAction(true);
                    return modelAndView;
                } else {
                    form.getDialogManager().removeDialog(dialog);
                }
            } else {
                return showDialog(dialog, form, request, response);
            }
        }
        return getUIFModelAndView(form);
    }

    /**
     * This method is called from the UI when a user wants to approve proposal.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return ModelAndView object
     */
    @Override
    public ModelAndView approve(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DialogManager dm = form.getDialogManager();
        String dialogId = dm.getCurrentDialogId();
        if(dialogId != null) {
            dm.setDialogAnswer(dialogId, form.getDialogResponse());
            dm.setDialogExplanation(dialogId, form.getDialogExplanation());
            dm.setCurrentDialogId(null);
        }

        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);
        courseInfoWrapper.getUiHelper().setShowMessage(false);
        String dialog = CurriculumManagementConstants.proposalConfirmationDialogs.COURSE_APPROVE_CONFIRMATION_DIALOG;
        if ( ! hasDialogBeenDisplayed(dialog, form)) {
            doValidationForProposal(form, KewApiConstants.ROUTE_HEADER_PROCESSED_CD, DtoConstants.STATE_ACTIVE);

            if (!GlobalVariables.getMessageMap().hasErrors()) {
                //redirect back to client to display confirm dialog
                return showDialog(dialog, form, request, response);
            }
        } else {
            if (hasDialogBeenAnswered(dialog, form)) {
                boolean confirmApprove = getBooleanDialogResponse(dialog, form, request, response);
                if (confirmApprove) {
                    //route the document only if the rationale decision explanation is not null or redirect back to client to display confirm dialog with error
                    if(courseInfoWrapper.getUiHelper().getDialogExplanations().get(dialog)!=null){
//                        super.approve(form,result, request,response);
                        // cannot call super class method as the redirect methods are causing an error when loading indicator is used after dialog confirmation is clicked
                        performWorkflowAction(form, UifConstants.WorkflowAction.APPROVE, true);

                        addDecisionRationale(courseInfoWrapper.getProposalInfo().getId(), courseInfoWrapper.getUiHelper().getDialogExplanations().get(dialog), CommentServiceConstants.WORKFLOW_DECISIONS.APPROVE.getType());
                        // setShowMessage boolean decides whether to show the error message or not
                        courseInfoWrapper.getUiHelper().setShowMessage(false);
                        form.getDialogManager().removeDialog(dialog);
                        // Set the request redirect to false so that the user stays on the same page
                        form.setRequestRedirected(false);

                        // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
                        courseInfoWrapper.getUiHelper().setPendingWorkflowAction(true);
                    } else {
                        form.getDialogManager().resetDialogStatus(dialog);
                        courseInfoWrapper.getUiHelper().setShowMessage(true);
                        return showDialog(dialog, form, request, response);
                    }
                } else {
                    form.getDialogManager().removeDialog(dialog);
                }
            }else{
                return showDialog(dialog, form, request, response);
            }
        }
        return getUIFModelAndView(form);
    }

    /**
     * This method is called form the UI when a user wants to reject/disapprove a proposal.
     * @param form
     * @param result
     * @param request
     * @param response
     * @return modelAndView object
     * @throws Exception
     */
    @Override
    public ModelAndView disapprove(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DialogManager dm = form.getDialogManager();
        String dialogId = dm.getCurrentDialogId();
        if(dialogId != null) {
            dm.setDialogAnswer(dialogId, form.getDialogResponse());
            dm.setDialogExplanation(dialogId, form.getDialogExplanation());
            dm.setCurrentDialogId(null);
        }

        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);
        courseInfoWrapper.getUiHelper().setShowMessage(false);
        String dialog = CurriculumManagementConstants.proposalConfirmationDialogs.COURSE_REJECT_CONFIRMATION_DIALOG;
        if ( ! hasDialogBeenDisplayed(dialog, form)) {
            if (!GlobalVariables.getMessageMap().hasErrors()) {
                //redirect back to client to display reject rationale dialog
                return showDialog(dialog, form, request, response);
            }
        } else {
            if (hasDialogBeenAnswered(dialog, form)) {
                boolean confirmReject = getBooleanDialogResponse(dialog, form, request, response);
                if (confirmReject) {
                    //route the document only if the rationale decision explanation is not null or redirect back to client to display confirm dialog with error
                    if(courseInfoWrapper.getUiHelper().getDialogExplanations().get(dialog)!=null){
//                        super.disapprove(form, result, request, response);
                        // cannot call super class method as the redirect methods are causing an error when loading indicator is used after dialog confirmation is clicked
                        performWorkflowAction(form, UifConstants.WorkflowAction.DISAPPROVE, true);

                        addDecisionRationale(courseInfoWrapper.getProposalInfo().getId(), courseInfoWrapper.getUiHelper().getDialogExplanations().get(dialog), CommentServiceConstants.WORKFLOW_DECISIONS.REJECT.getType());
                        // setShowMessage boolean decides whether to show the error message or not
                        courseInfoWrapper.getUiHelper().setShowMessage(false);
                        form.getDialogManager().removeDialog(dialog);
                        // Set the request redirect to false so that the user stays on the same page
                        form.setRequestRedirected(false);
                        // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
                        courseInfoWrapper.getUiHelper().setPendingWorkflowAction(true);
                    } else {
                        form.getDialogManager().resetDialogStatus(dialog);
                        courseInfoWrapper.getUiHelper().setShowMessage(true);
                        return showDialog(dialog, form, request, response);
                    }
                } else {
                    form.getDialogManager().removeDialog(dialog);
                }
            }else{
                return showDialog(dialog, form, request, response);
            }
        }
        return getUIFModelAndView(form);
    }

    /**
     * This method performs the KRAD UI data dictionary and Service layer data dictionary validation before it routes the document instance contained on the form.
     *
     * @param form - the form of the current users session
     * @param workflowStatusCode - the status code that should be used to perform KRAD view validation
     * @param forcedCourseStateKey - the stateKey that needs to be faked for the CourseService.validationCourse() method (if null, uses state in CourseInfo object)
     */
    protected void doValidationForProposal(DocumentFormBase form, String workflowStatusCode, String forcedCourseStateKey){
        //Perform KRAD UI Data Dictionary Validation
        // manually call the view validation service as this validation cannot be run client-side in current setup
        KRADServiceLocatorWeb.getViewValidationService().validateView(form, workflowStatusCode);

        //Perform Rules validation
        KRADServiceLocatorWeb.getKualiRuleService().applyRules(new RouteDocumentEvent(form.getDocument()));

        List<ValidationResultInfo> validationResultInfoList = null;

        try {
            //Perform Service Layer Data Dictionary validation
            CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);
            CourseInfo courseInfoToValidate = (CourseInfo) ObjectUtils.deepCopy(courseInfoWrapper.getCourseInfo());
            if (StringUtils.isNotBlank(forcedCourseStateKey)) {
                courseInfoToValidate.setStateKey(forcedCourseStateKey);
            }
            validationResultInfoList = getCourseService().validateCourse("OBJECT", courseInfoToValidate, ContextUtils.createDefaultContextInfo());
        } catch (Exception ex) {
            LOG.error("Error occurred while performing service layer validation for Submit", ex);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, KSObjectUtils.unwrapException(20, ex).getMessage());
        }

        bindValidationErrorsToPath(validationResultInfoList, form);
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
        DialogManager dm = form.getDialogManager();
        String dialogId = dm.getCurrentDialogId();
        if(dialogId != null) {
            dm.setDialogAnswer(dialogId, form.getDialogResponse());
            dm.setDialogExplanation(dialogId, form.getDialogExplanation());
            dm.setCurrentDialogId(null);
        }

        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);
        courseInfoWrapper.getUiHelper().setShowMessage(false);
        String dialog = CurriculumManagementConstants.proposalConfirmationDialogs.COURSE_RETURN_TO_PREVIOUS_NODE_DIALOG;
        if ( ! hasDialogBeenDisplayed(dialog, form)) {
            doValidationForProposal(form, KewApiConstants.ROUTE_HEADER_PROCESSED_CD, null);

            if (!GlobalVariables.getMessageMap().hasErrors()) {
                //redirect back to client to display confirm dialog
                return showDialog(dialog, form, request, response);
            }
        } else {
            if (hasDialogBeenAnswered(dialog,form)) {
                boolean confirmReturn = getBooleanDialogResponse(dialog, form, request, response);
                if (confirmReturn) {
                    //route the document only if the rationale decision explanation is not null or redirect back to client to display confirm dialog with error
                    if(courseInfoWrapper.getUiHelper().getDialogExplanations().get(dialog)!=null){
                        performCustomWorkflowAction(form,result, request,response, WorkflowActions.RETURN_TO_PREVIOUS);
                        addDecisionRationale(courseInfoWrapper.getProposalInfo().getId(), courseInfoWrapper.getUiHelper().getDialogExplanations().get(dialog), CommentServiceConstants.WORKFLOW_DECISIONS.RETURN_TO_PREVIOUS.getType());
                        // setShowMessage boolean decides whether to show the error message or not
                        courseInfoWrapper.getUiHelper().setShowMessage(false);
                        form.getDialogManager().removeDialog(dialog);
                        // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
                        courseInfoWrapper.getUiHelper().setPendingWorkflowAction(true);
                    }else{
                        form.getDialogManager().resetDialogStatus(dialog);
                        courseInfoWrapper.getUiHelper().setShowMessage(true);
                        return showDialog(dialog, form, request, response);
                    }
                    /*
                    Here's another location where we diverge from the default KRAD code. Normally here there would be code to handle the persisting
                    of attachments but since CM uses it's own internal SupportingDocuments functionality we can simply ignore the KRAD system.
                    */
                }
//                form.getDialogManager().resetDialogStatus(dialog);
            } else {
                return showDialog(dialog, form, request, response);
            }
        }
        return getUIFModelAndView(form);
    }

    protected void performCustomWorkflowAction(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                                    HttpServletRequest request, HttpServletResponse response, String action) {
        CourseControllerTransactionHelper helper = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseControllerTransactionHelper", CourseControllerTransactionHelper.class.getSimpleName()));
        helper.performCustomWorkflowActionSuper(form, action, this);
        List<ErrorMessage> infoMessages = GlobalVariables.getMessageMap().getInfoMessagesForProperty(KRADConstants.GLOBAL_MESSAGES);
        if (infoMessages != null) {
            for (ErrorMessage message : infoMessages) {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, message.getErrorKey(), message.getMessageParameters());
            }
            GlobalVariables.getMessageMap().removeAllInfoMessagesForProperty(KRADConstants.GLOBAL_MESSAGES);
        }
    }

    /**
     * This method is here to actually do the work of the return to previous and withdraw actions since neither of those actions are built into the KRAD
     * {@link DocumentService}. This is an extrapolation of what would happen in the various KRAD classes in a single method in
     * this controller.
     */
    public void performCustomWorkflowActionSuper(@ModelAttribute("KualiForm") DocumentFormBase form, String action) {
        MaintenanceDocument document = ((MaintenanceDocumentForm) form).getDocument();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Performing custom workflow action " + action + "for document: " + document.getDocumentNumber());
        }

        try {
            String successMessageKey = null;
            switch (action) {
                case WorkflowActions.RETURN_TO_PREVIOUS:
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
                    successMessageKey = CurriculumManagementConstants.MessageKeys.SUCCESS_PROPOSAL_RETURN_TO_PREVIOUS_NODE;
                    break;
                case WorkflowActions.WITHDRAW:
                    //fake the Document.DocumentHeader.WorkflowDocument value for the system user here
                    Person person = KimApiServiceLocator.getPersonService().getPersonByPrincipalName("admin");
                    WorkflowDocument workflowDocument = KRADServiceLocatorWeb.getWorkflowDocumentService().loadWorkflowDocument(document.getDocumentNumber(), person);
                    document.getDocumentHeader().setWorkflowDocument(workflowDocument);
                    // call superuserdisapprove for withdrawing the proposal

                    getDocumentService().superUserDisapproveDocumentWithoutSaving(document,form.getAnnotation());

                    //reset the 'document' variable to have the correct Document.DocumentHeader.WorkflowDocument value for the current user
                    Person personReset = GlobalVariables.getUserSession().getPerson();
                    WorkflowDocument workflowDocumentReset = KRADServiceLocatorWeb.getWorkflowDocumentService().loadWorkflowDocument(document.getDocumentNumber(), personReset);
                    document.getDocumentHeader().setWorkflowDocument(workflowDocumentReset);

                    GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_MESSAGES, CurriculumManagementConstants.MessageKeys.SUCCESS_PROPOSAL_WITHDRAW);
                    successMessageKey = CurriculumManagementConstants.MessageKeys.SUCCESS_PROPOSAL_WITHDRAW;
                    break;
            }
            // push potentially updated document back into the form
            form.setDocument(document);

            if (successMessageKey != null) {
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_MESSAGES, successMessageKey);
            }
        } catch (ValidationException e) {
            // log the error and swallow exception so screen will draw with errors.
            // we don't want the exception to bubble up and the user to see an incident page, but instead just return to
            // the page and display the actual errors. This would need a fix to the API at some point.
            KRADUtils.logErrors();
            LOG.error("Validation Exception occurred for document :" + document.getDocumentNumber(), e);

            // if no errors in map then throw runtime because something bad happened
            if (GlobalVariables.getMessageMap().hasNoErrors()) {
                throw new RiceRuntimeException("Validation Exception with no error message.", e);
            }
        } catch (Exception e) {
            throw new RiceRuntimeException(
                    "Exception trying to invoke custom action " + action + " for document: " + document
                            .getDocumentNumber(), e);
        }

        form.setAnnotation(""); // copied from parent class method
    }

    /**
     * A convenience method to add a decision rationale given the proper proposal id, rationale text, and rationale type
     *
     * @param proposalId
     * @param rationaleText
     * @param rationaleType
     */
    private void addDecisionRationale(String proposalId, String rationaleText, String rationaleType) {
        CommentInfo newDecisionRationale = new CommentInfo();
        RichTextInfo text = new RichTextInfo();
        text.setFormatted(rationaleText);
        text.setPlain(rationaleText);
        newDecisionRationale.setRefObjectUri(StudentIdentityConstants.QUALIFICATION_PROPOSAL_REF_TYPE);
        newDecisionRationale.setRefObjectId(proposalId);
        newDecisionRationale.setCommenterId(GlobalVariables.getUserSession().getPrincipalId());
        newDecisionRationale.setStateKey(CommentServiceConstants.COMMENT_ACTIVE_STATE_KEY);
        newDecisionRationale.setCommentText(text);
        newDecisionRationale.setTypeKey(rationaleType);

        try {
            getCommentService().createComment(proposalId, StudentIdentityConstants.QUALIFICATION_PROPOSAL_REF_TYPE, rationaleType, newDecisionRationale, ContextUtils.createDefaultContextInfo());
        } catch (Exception e) {
            LOG.error("Caught Error adding Decision Rationale", e);
            throw new RuntimeException(e);
        }

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
        CourseInfoWrapper wrapper = getCourseInfoWrapper(form);
        KRADServiceLocatorWeb.getViewValidationService().validateViewAgainstNextState(form);
        if (GlobalVariables.getMessageMap().hasErrors()) {
            wrapper.setMissingRequiredFields(true);
        } else
        {
            wrapper.setMissingRequiredFields(false);
        }
        return getUIFModelAndView(form, CurriculumManagementConstants.CoursePageIds.REVIEW_COURSE_PROPOSAL_PAGE);
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

        return getUIFModelAndView(form, CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE);
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
            return getUIFModelAndView(form, CurriculumManagementConstants.CoursePageIds.REVIEW_COURSE_PROPOSAL_PAGE);
        } else {
            return getUIFModelAndView(form);
        }
    }


    /**
     * This will approve and activate an admin proposal.
     *
     * @param form     {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request  {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @return The new {@link ModelAndView} that contains the newly created/updated {@CourseInfo} and {@ProposalInfo} information.
     */
    @RequestMapping(params = "methodToCall=approveAndActivate")
    public ModelAndView approveAndActivate(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);

        doValidationForProposal(form, KewApiConstants.ROUTE_HEADER_PROCESSED_CD, DtoConstants.STATE_ACTIVE);
        if (!GlobalVariables.getMessageMap().hasErrors()) {
//            super.blanketApprove(form, result, request, response);
            // cannot call super class method as the redirect methods are causing an error when loading indicator is used after dialog confirmation is clicked
            performWorkflowAction(form, UifConstants.WorkflowAction.BLANKETAPPROVE, true);

            // Set the request redirect to false so that the user stays on the same page
            form.setRequestRedirected(false);
            // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
            courseInfoWrapper.getUiHelper().setPendingWorkflowAction(true);
            //redirect back to client to display confirm dialog
            return getUIFModelAndView(form, CurriculumManagementConstants.CoursePageIds.REVIEW_COURSE_PROPOSAL_PAGE);
        } else {
            return getUIFModelAndView(form);
        }

    }

    /**
     * This will FYI an approved proposal.
     *
     * @param form     {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request  {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @return The new {@link ModelAndView} that contains the newly created/updated {@CourseInfo} and {@ProposalInfo} information.
     */
    @Override
    @RequestMapping(params = "methodToCall=fyi")
    public ModelAndView fyi(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);
        super.fyi(form, result, request, response);
        // Set the request redirect to false so that the user stays on the same page
        form.setRequestRedirected(false);
        // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
        courseInfoWrapper.getUiHelper().setPendingWorkflowAction(true);
        return getUIFModelAndView(form);

    }


    /**
     * This will withdraw the proposal.
     *
     * @param form     {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request  {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @return The new {@link ModelAndView} that contains the newly created/updated {@CourseInfo} and {@ProposalInfo} information.
     */
    @RequestMapping(params = "methodToCall=withdraw")
    public ModelAndView withdraw(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {

        DialogManager dm = form.getDialogManager();
        String dialogId = dm.getCurrentDialogId();
        if(dialogId != null) {
            dm.setDialogAnswer(dialogId, form.getDialogResponse());
            dm.setDialogExplanation(dialogId, form.getDialogExplanation());
            dm.setCurrentDialogId(null);
        }

        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);
        courseInfoWrapper.getUiHelper().setShowMessage(false);
        String dialog = CurriculumManagementConstants.proposalConfirmationDialogs.COURSE_WITHDRAW_CONFIRMATION_DIALOG;
        if ( ! hasDialogBeenDisplayed(dialog, form)) {
            doValidationForProposal(form, KewApiConstants.ROUTE_HEADER_PROCESSED_CD, DtoConstants.STATE_ACTIVE);

            if (!GlobalVariables.getMessageMap().hasErrors()) {
                //redirect back to client to display confirm dialog
                return showDialog(dialog, form, request, response);
            }
        } else {
            if (hasDialogBeenAnswered(dialog,form)) {
                boolean confirmBlanketApprove = getBooleanDialogResponse(dialog, form, request, response);
                if (confirmBlanketApprove) {
                    //route the document only if the rationale decision explanation is not null or redirect back to client to display confirm dialog with error
                    if(courseInfoWrapper.getUiHelper().getDialogExplanations().get(dialog)!=null){
                        performCustomWorkflowActionSuper(form,WorkflowActions.WITHDRAW);
                        addDecisionRationale(courseInfoWrapper.getProposalInfo().getId(), courseInfoWrapper.getUiHelper().getDialogExplanations().get(dialog), CommentServiceConstants.WORKFLOW_DECISIONS.BLANKET_APPROVE.getType());
                        // setShowMessage boolean decides whether to show the error message or not
                        courseInfoWrapper.getUiHelper().setShowMessage(false);
                        form.getDialogManager().removeDialog(dialog);
                        // Set the request redirect to false so that the user stays on the same page
                        form.setRequestRedirected(false);
                        // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
                        courseInfoWrapper.getUiHelper().setPendingWorkflowAction(true);
                    } else {
                        form.getDialogManager().resetDialogStatus(dialog);
                        courseInfoWrapper.getUiHelper().setShowMessage(true);
                        return showDialog(dialog, form, request, response);
                    }

                } else {
                    form.getDialogManager().removeDialog(dialog);
                }
            }else{
                return showDialog(dialog, form, request, response);
            }
        }
        return getUIFModelAndView(form);
    }

    /**
     * This method is called from the UI when a user wants to acknowledge proposal.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return ModelAndView object
     */
    @Override
    @RequestMapping(params = "methodToCall=acknowledge")
    public ModelAndView acknowledge(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {

        DialogManager dm = form.getDialogManager();
        String dialogId = dm.getCurrentDialogId();
        if(dialogId != null) {
            dm.setDialogAnswer(dialogId, form.getDialogResponse());
            dm.setDialogExplanation(dialogId, form.getDialogExplanation());
            dm.setCurrentDialogId(null);
        }

        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);
        courseInfoWrapper.getUiHelper().setShowMessage(false);
        String dialog = CurriculumManagementConstants.proposalConfirmationDialogs.COURSE_ACKNOWLEDGE_CONFIRMATION_DIALOG;
        if ( ! hasDialogBeenDisplayed(dialog, form)) {
            if (!GlobalVariables.getMessageMap().hasErrors()) {
                //redirect back to client to display confirm dialog
                return showDialog(dialog, form, request, response);
            }
        } else {
            if (hasDialogBeenAnswered(dialog, form)) {
                boolean confirmAcknowledge = getBooleanDialogResponse(dialog, form, request, response);
                if (confirmAcknowledge) {
                    //route the document only if the rationale decision explanation is not null or redirect back to client to display confirm dialog with error
                    if(courseInfoWrapper.getUiHelper().getDialogExplanations().get(dialog)!=null){
//                        super.acknowledge(form,result, request,response);
                        // cannot call super class method as the redirect methods are causing an error when loading indicator is used after dialog confirmation is clicked
                        performWorkflowAction(form, UifConstants.WorkflowAction.ACKNOWLEDGE, false);

                        addDecisionRationale(courseInfoWrapper.getProposalInfo().getId(), courseInfoWrapper.getUiHelper().getDialogExplanations().get(dialog), CommentServiceConstants.WORKFLOW_DECISIONS.ACKNOWLEDGE.getType());
                        // setShowMessage boolean decides whether to show the error message or not
                        courseInfoWrapper.getUiHelper().setShowMessage(false);
                        form.getDialogManager().removeDialog(dialog);
                        // Set the request redirect to false so that the user stays on the same page
                        form.setRequestRedirected(false);
                    } else {
                        form.getDialogManager().resetDialogStatus(dialog);
                        courseInfoWrapper.getUiHelper().setShowMessage(true);
                        return showDialog(dialog, form, request, response);
                    }
                } else {
                    form.getDialogManager().removeDialog(dialog);
                }
            }else{
                return showDialog(dialog, form, request, response);
            }
        }
        return getUIFModelAndView(form);
    }

    @Override
    @RequestMapping(params = "methodToCall=blanketApprove")
    public ModelAndView blanketApprove(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {

        DialogManager dm = form.getDialogManager();
        String dialogId = dm.getCurrentDialogId();
        if(dialogId != null) {
            dm.setDialogAnswer(dialogId, form.getDialogResponse());
            dm.setDialogExplanation(dialogId, form.getDialogExplanation());
            dm.setCurrentDialogId(null);
        }

        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);
        courseInfoWrapper.getUiHelper().setShowMessage(false);
        String dialog = CurriculumManagementConstants.proposalConfirmationDialogs.COURSE_BLANKET_APPROVE_CONFIRMATION_DIALOG;
        if ( ! hasDialogBeenDisplayed(dialog, form)) {
            doValidationForProposal(form, KewApiConstants.ROUTE_HEADER_PROCESSED_CD, DtoConstants.STATE_ACTIVE);

            if (!GlobalVariables.getMessageMap().hasErrors()) {
                //redirect back to client to display confirm dialog
                return showDialog(dialog, form, request, response);
            }
        } else {
            if (hasDialogBeenAnswered(dialog,form)) {
                boolean confirmBlanketApprove = getBooleanDialogResponse(dialog, form, request, response);
                if (confirmBlanketApprove) {
                    //route the document only if the rationale decision explanation is not null or redirect back to client to display confirm dialog with error
                    if(courseInfoWrapper.getUiHelper().getDialogExplanations().get(dialog)!=null){
//                        super.blanketApprove(form, result, request, response);
                        // cannot call super class method as the redirect methods are causing an error when loading indicator is used after dialog confirmation is clicked
                        performWorkflowAction(form, UifConstants.WorkflowAction.BLANKETAPPROVE, true);

                        addDecisionRationale(courseInfoWrapper.getProposalInfo().getId(), courseInfoWrapper.getUiHelper().getDialogExplanations().get(dialog), CommentServiceConstants.WORKFLOW_DECISIONS.BLANKET_APPROVE.getType());
                        // setShowMessage boolean decides whether to show the error message or not
                        courseInfoWrapper.getUiHelper().setShowMessage(false);
                        form.getDialogManager().removeDialog(dialog);
                        // Set the request redirect to false so that the user stays on the same page
                        form.setRequestRedirected(false);
                    } else {
                        form.getDialogManager().resetDialogStatus(dialog);
                        courseInfoWrapper.getUiHelper().setShowMessage(true);
                        return showDialog(dialog, form, request, response);
                    }

                } else {
                    form.getDialogManager().removeDialog(dialog);
                }
            }else{
                return showDialog(dialog, form, request, response);
            }
        }
        return getUIFModelAndView(form);
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
            throw new RuntimeException("An error has occurred while downloading the file. Please try again.", ex);
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

    /**
     * This is being called from 'review proposal' page when the user clicks on 'copy proposal' action. This method
     * builds the url and call 'maintenaceCopy' method.
     *
     * @param form
     * @return
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=copyProposal")
    public ModelAndView copyProposal(@ModelAttribute("KualiForm") DocumentFormBase form) {

        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);

        Properties urlParameters = new Properties();
        /**
         * It should be always 'curriculum review' for both CS and faculty users for copy.
         */
        urlParameters.put(CourseController.UrlParams.USE_CURRICULUM_REVIEW,Boolean.TRUE.toString());
        urlParameters.put(UifConstants.UrlParams.PAGE_ID, CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE);
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_COPY);
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseInfoWrapper.class.getName());
        urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, CMUtils.getCMHomeUrl() );
        urlParameters.put(UrlParams.COPY_PROPOSAL_ID, courseInfoWrapper.getProposalInfo().getId());
        urlParameters.put(KRADConstants.OVERRIDE_KEYS, CourseController.UrlParams.COPY_CLU_ID);

        String courseBaseUrl = CurriculumManagementConstants.ControllerRequestMappings.COURSE_MAINTENANCE.replaceFirst("/", "");


        return performRedirect(form, courseBaseUrl, urlParameters);

    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=export")
    public ModelAndView export(@ModelAttribute("KualiForm") DocumentFormBase form) {

        String exportType = (String)form.getExtensionData().get(CurriculumManagementConstants.Export.EXPORT_TYPE);

        if (StringUtils.equals(exportType,CurriculumManagementConstants.Export.PDF)){

        } else if (StringUtils.equals(exportType,CurriculumManagementConstants.Export.DOC)){

        }
        return getUIFModelAndView(form);

    }

    /**
     *  Binds the each validation errors with its property path
     * @param validationResultInfoList
     * @param form
     */
    protected void bindValidationErrorsToPath(List<ValidationResultInfo> validationResultInfoList, DocumentFormBase form) {
        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);
        courseInfoWrapper.getReviewProposalDisplay().setShowUnknownErrors(false);

        if (validationResultInfoList != null && !validationResultInfoList.isEmpty()) {
            for( ValidationResultInfo error : validationResultInfoList ) {
                String message = error.getMessage();
                String element = error.getElement().replace("/0","").replace("/","");
                String elementPath = null;

                switch(element) {
                    case "courseTitle":
                        if (StringUtils.equals(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, form.getPageId())) {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.courseTitle";
                        } else {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.courseTitle";
                        }
                        break;
                    case "subjectArea":
                        if (StringUtils.equals(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, form.getPageId())) {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.subjectArea";
                        } else {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.subjectArea";
                        }
                        break;
                    case "courseNumberSuffix":
                        if (StringUtils.equals(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, form.getPageId())) {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.courseNumberSuffix";
                        } else {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.courseNumberSuffix";
                        }
                        break;
                    case "campusLocations":
                        if (StringUtils.equals(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, form.getPageId())) {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".campusLocations";
                        } else {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".reviewProposalDisplay.governanceSection.campusLocationsAsString";
                        }
                        break;
                    case "startTerm":
                        if (StringUtils.equals(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, form.getPageId())) {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.startTerm";
                        } else {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".reviewProposalDisplay.activeDatesSection.startTerm";
                        }
                        break;
                    case "transcriptTitle":
                        if (StringUtils.equals(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, form.getPageId())) {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.transcriptTitle";
                        } else {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.transcriptTitle";
                        }
                        break;
                    case "finalExamStatus":
                        if (StringUtils.equals(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, form.getPageId())) {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".finalExamStatus";
                        } else {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".reviewProposalDisplay.courseLogisticsSection.finalExamStatus";
                        }
                        break;
                    case "finalExamRationale":
                        if (StringUtils.equals(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, form.getPageId())) {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".finalExamRationale";
                        } else {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".reviewProposalDisplay.courseLogisticsSection.finalExamStatusRationale";
                        }
                        break;
                    case "gradingOptions":
                        if (StringUtils.equals(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, form.getPageId())) {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.gradingOptions";
                        } else {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".reviewProposalDisplay.courseLogisticsSection.gradingOptionsAsString";
                        }
                        break;
                    case "unitsContentOwner":
                        if (StringUtils.equals(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, form.getPageId())) {
                            String collectionPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".unitsContentOwner";
                            CollectionGroup collectionGroup = (CollectionGroup) form.getView().getViewIndex().getComponentById("CM-Proposal-Course-Governance-CurriculumOversight-Section");
                            if (collectionGroup != null) {
                                message = collectionGroup.getHeaderText() + ": " + message;
                            }
                            elementPath = collectionPath + "[0].orgId";
                        } else {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".reviewProposalDisplay.governanceSection.curriculumOversightAsString";
                        }
                        break;
                    case "stateKey":    // ignore this one as it's always returned whenever a validation error is thrown
                    case "code":        // ignore this one since it is only valid for the old GWT UI
                        break;

                    default:
                        elementPath = KRADConstants.GLOBAL_ERRORS;
                        courseInfoWrapper.getReviewProposalDisplay().setShowUnknownErrors(true);
                        error.setMessage(error.getElement() + ": " + error.getMessage());
                }
                if (StringUtils.isNotBlank(elementPath)) {
                    GlobalVariables.getMessageMap().putError(elementPath, RiceKeyConstants.ERROR_CUSTOM, message);
                }
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

    protected ProposalService getProposalService() {
        if (proposalService == null) {
            proposalService = (ProposalService) GlobalResourceLoader.getService(new QName(ProposalServiceConstants.NAMESPACE, ProposalServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return proposalService;
    }

    protected CommentService getCommentService() {
        if (commentService == null) {
            commentService = (CommentService) GlobalResourceLoader.getService(new QName(CommentServiceConstants.NAMESPACE, CommentService.class.getSimpleName()));
        }
        return commentService;
    }

}
