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
 * Created by delyea on 9/8/14
 */
package org.kuali.student.cm.proposal.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
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
import org.kuali.rice.krad.uif.view.DialogManager;
import org.kuali.rice.krad.util.ErrorMessage;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.controller.RuleEditorController;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.RecentlyViewedDocsUtil;
import org.kuali.student.cm.course.util.CourseProposalUtil;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.cm.proposal.form.wrapper.SupportingDocumentInfoWrapper;
import org.kuali.student.cm.proposal.service.ProposalMaintainable;
import org.kuali.student.cm.proposal.util.ProposalUtil;
import org.kuali.student.common.ui.krad.rules.rule.event.ReturnToPreviousNodeDocumentEvent;
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.constants.CommentServiceConstants;
import org.kuali.student.r2.core.constants.DocumentServiceConstants;
import org.kuali.student.r2.core.constants.ProposalServiceConstants;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.service.DocumentService;
import org.kuali.student.r2.core.proposal.service.ProposalService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
 * This class is an abstract parent class for CM Controllers that use the Proposal process
 *
 * @author Kuali Student Team
 */
public abstract class ProposalController extends RuleEditorController {

    private static final String DECISIONS_DIALOG_KEY = "CM-Proposal-Course-DecisionDialog";

    private ProposalService proposalService;
    private CommentService commentService;
    private DocumentService documentService;

    /**
     * This method creates the form and in the case of a brand new proposal where this method is called after the user uses
     * the Initial Create Proposal screen, this method will also set the document type name based on the request parameter
     * {@link org.kuali.student.cm.common.util.CurriculumManagementConstants.UrlParams#USE_CURRICULUM_REVIEW}
     */
    @Override
    protected MaintenanceDocumentForm createInitialForm(HttpServletRequest request) {
        MaintenanceDocumentForm form = new MaintenanceDocumentForm();

        String useReviewProcessParam = request.getParameter(CurriculumManagementConstants.UrlParams.USE_CURRICULUM_REVIEW);
        // only do the manually setup of the MaintenanceDocumentForm fields if the USE_CURRICULUM_REVIEW param was passed in from initial view
        if (StringUtils.isNotBlank(useReviewProcessParam)) {
            Boolean isUseReviewProcess = Boolean.valueOf(useReviewProcessParam);
            form.setDocTypeName(getDocumentTypeNameForProposalStart(isUseReviewProcess, request));
        }

        form.getExtensionData().put(CurriculumManagementConstants.Export.UrlParams.EXPORT_TYPE, CurriculumManagementConstants.Export.FileType.PDF);

        return form;
    }

    protected abstract String getDocumentTypeNameForProposalStart(Boolean isUseReviewProcess, HttpServletRequest request);

    // TODO: Remove this workaround class once KS has been updated to Rice 2.5 (https://jira.kuali.org/browse/KSCM-2560)
    @Override
    protected ModelAndView showDialog(String dialogId, UifFormBase form, HttpServletRequest request,
                                      HttpServletResponse response) {
        ModelAndView modelAndView = super.showDialog(dialogId, form, request, response);
        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject();
        proposalElementsWrapper.getUiHelper().getDialogExplanations().put(dialogId, "");
        return modelAndView;
    }

    /**
     * Digs the ProposalElementsWrapper out of DocumentFormBase.
     *
     * @param form The DocumentFormBase.
     * @return The ProposalElementsWrapper.
     */
    protected ProposalElementsWrapper getProposalElementsWrapper(DocumentFormBase form) {
        return ((ProposalElementsWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject());
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
        // at this point we have the document type name set on the form so we use it to update the form specific fields
        updateFormElements(form);
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
        // at this point we have the document type name set on the form so we use it to update the form specific fields
        updateFormElements(form);
    }

    /**
     * Updates the MaintenanceDocumentForm to set the 'useReviewProcess' property based on the document type name. If
     * the document type name is in the array CurriculumManagementConstants.DocumentTypeNames.ADMIN_DOC_TYPE_NAMES then
     * set 'useReviewProcess' to false.
     *
     * @param form the DocumentFormBase object to update
     */
    protected void updateFormElements(DocumentFormBase form) {
        ProposalElementsWrapper wrapper = (ProposalElementsWrapper)((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject();
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
            formBase.setPageId(getReviewPageKradPageId());
        }

        if (formBase.getPageId().equals(getReviewPageKradPageId())) {
            //  Build a redirect to the review proposal handler for validation.
            java.util.Map requestParameterMap = request.getParameterMap();
            Properties urlParameters = new Properties();
            for (Object p : requestParameterMap.entrySet()) {
                Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) p;
                urlParameters.put((String) entry.getKey(), ((String[]) entry.getValue())[0]);
            }

            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, getProposalReviewMethodToCall());
            urlParameters.put(KRADConstants.FORM_KEY, formBase.getFormKey());

            /*
             * Calling performRedirect() to build the response, but undoing the call to form.setRequestRedirect(true)
             * that happens within performRedirect() before returning. Setting that flag causes the postedView to be
             * discarded in UifControllerHandlerInterceptor#afterCompletion(). If the postedView isn't available the
             * validation in the review proposal handler method will fail.
             */
            ModelAndView mv = performRedirect(formBase, getBaseUrlForProposal(), urlParameters);
            formBase.setRequestRedirected(false);
            return mv;
        }
        return modelAndView;
    }

    protected abstract String getReviewPageKradPageId();

    protected abstract String getProposalReviewMethodToCall();

    protected abstract String getBaseUrlForProposal();

    /**
     * Constructs a text URL for a particular proposal.
     */
    protected String buildProposalUrl(String methodToCall, String pageId, String workflowDocId, String proposalType) {
        Properties props = ProposalUtil.getProposalUrlProperties(methodToCall, pageId, workflowDocId);
        props.put(UifParameters.DATA_OBJECT_CLASS_NAME, getDataObjectClassName());
        String controllerRequestMapping = CurriculumManagementConstants.ControllerRequestMappings.MappingsByProposalType.getControllerMapping(proposalType);
        if (StringUtils.isBlank(controllerRequestMapping)) {
            throw new RuntimeException("Cannot find request mapping for proposal type: " + proposalType);
        }
        String proposalBaseUrl = controllerRequestMapping.replaceFirst("/", "");
        return UrlFactory.parameterizeUrl(proposalBaseUrl, props);
    }

    protected abstract String getDataObjectClassName();

    protected void performCustomWorkflowAction(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                               HttpServletRequest request, HttpServletResponse response, String action) {
        ProposalControllerTransactionHelper helper = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "proposalControllerTransactionHelper", ProposalControllerTransactionHelper.class.getSimpleName()));
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
     * {@link org.kuali.student.r2.core.document.service.DocumentService}. This is an extrapolation of what would happen in the various KRAD classes in a single method in
     * this controller.
     */
    public void performCustomWorkflowActionSuper(@ModelAttribute("KualiForm") DocumentFormBase form, String action) {
        MaintenanceDocument document = ((MaintenanceDocumentForm) form).getDocument();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Performing custom workflow action " + action + "for document: " + document.getDocumentNumber());
        }

        try {
            String successMessageKey = null;
            String annotation = getCustomWorkflowActionAnnotation(form, action);
            switch (action) {
                case CurriculumManagementConstants.WorkflowActions.RETURN_TO_PREVIOUS:
                    document.prepareForSave();
                    Document savedDocument = getDocumentService().validateAndPersistDocument(document, new ReturnToPreviousNodeDocumentEvent(document));
                    getDocumentService().prepareWorkflowDocument(savedDocument);
                    ProposalElementsWrapper proposalElementsWrapper = getProposalElementsWrapper(form);
                    savedDocument.getDocumentHeader().getWorkflowDocument().returnToPreviousNode(annotation, proposalElementsWrapper.getReviewProposalDisplay().getReturnToPreviousNodeName());
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
                case CurriculumManagementConstants.WorkflowActions.WITHDRAW:
                    //fake the Document.DocumentHeader.WorkflowDocument value for the system user here
                    Person person = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
                    WorkflowDocument workflowDocument = KRADServiceLocatorWeb.getWorkflowDocumentService().loadWorkflowDocument(document.getDocumentNumber(), person);
                    document.getDocumentHeader().setWorkflowDocument(workflowDocument);
                    // call superuserdisapprove for withdrawing the proposal

                    getDocumentService().superUserDisapproveDocumentWithoutSaving(document, annotation);

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
                throw new RuntimeException("Validation Exception with no error message.", e);
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Exception trying to invoke custom action " + action + " for document: " + document
                            .getDocumentNumber(), e);
        }

        form.setAnnotation(""); // copied from parent class method
    }

    protected String getCustomWorkflowActionAnnotation(DocumentFormBase form, String action) {
        String annotation = form.getAnnotation();
        switch (action) {
            case CurriculumManagementConstants.WorkflowActions.WITHDRAW:
                annotation = "Withdraw Document action performed by " + GlobalVariables.getUserSession().getPrincipalId();
                break;
            case CurriculumManagementConstants.WorkflowActions.RETURN_TO_PREVIOUS:
                break;
        }
        return annotation;
    }

    /**
     * A convenience method to add a decision rationale given the proper proposal id, rationale text, and rationale type
     *
     * @param proposalId
     * @param rationaleText
     * @param rationaleType
     */
    protected void addDecisionRationale(String proposalId, String rationaleText, String rationaleType) {
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
     * Here we move the success messages displayed in UI from header to growl.
     *
     * @param form
     * @param action
     * @param checkSensitiveData
     */
    @Override
    protected void performWorkflowAction(DocumentFormBase form, UifConstants.WorkflowAction action, boolean checkSensitiveData) {
        ProposalControllerTransactionHelper helper = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "proposalControllerTransactionHelper", ProposalControllerTransactionHelper.class.getSimpleName()));
        helper.performWorkflowActionSuper(form, action, checkSensitiveData, this);
        List<ErrorMessage> infoMessages = GlobalVariables.getMessageMap().getInfoMessagesForProperty(KRADConstants.GLOBAL_MESSAGES);
        if (infoMessages != null) {
            for (ErrorMessage message : infoMessages) {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, message.getErrorKey(), message.getMessageParameters());
            }
            GlobalVariables.getMessageMap().removeAllInfoMessagesForProperty(KRADConstants.GLOBAL_MESSAGES);
        }
    }

    public void performWorkflowActionSuper(DocumentFormBase form, UifConstants.WorkflowAction action, boolean checkSensitiveData) {
        super.performWorkflowAction(form, action, checkSensitiveData);
    }

    /**
     * This will save the Proposal.
     *
     * @param form     {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request  {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @return The new {@link ModelAndView} that contains the newly created/updated {@ProposalInfo} information.
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=saveProposal")
    public ModelAndView saveProposal(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {

        ProposalElementsWrapper proposalElementsWrapper = getProposalElementsWrapper(form);
        form.getDocument().getDocumentHeader().setDocumentDescription(proposalElementsWrapper.getProposalInfo().getName());

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
            CurriculumManagementConstants.UserInterfaceSections nextSection = getNextSection(proposalElementsWrapper.getUiHelper().getSelectedSection());
            if (nextSection != null) {
                proposalElementsWrapper.getUiHelper().setSelectedSection(nextSection);
            }
            return getUIFModelAndView(form);
        } else if (StringUtils.equalsIgnoreCase(nextOrCurrentPage, getReviewProposalLinkBeanId())) {
            return getUIFModelAndView(form, getReviewPageKradPageId());
        } else {
            return getUIFModelAndView(form);
        }
    }

    protected abstract CurriculumManagementConstants.UserInterfaceSections getNextSection(CurriculumManagementConstants.UserInterfaceSections selectedSection);

    protected abstract String getReviewProposalLinkBeanId();

    @Override
    @RequestMapping(params = "methodToCall=cancel")
    public ModelAndView cancel(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {
        DocumentFormBase documentForm = (DocumentFormBase) form;
        String dialog = CurriculumManagementConstants.ProposalConfirmationDialogs.CANCEL_PROPOSAL_CONFIRMATION_DIALOG;
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
                    ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject();
//                    super.cancel(documentForm,result,request,response);
                    // cannot call super class method as the redirect methods are causing an error when loading indicator is used after dialog confirmation is clicked
                    performWorkflowAction(documentForm, UifConstants.WorkflowAction.CANCEL, false);

                    // setShowMessage boolean decides whether to show the error message or not
                    proposalElementsWrapper.getUiHelper().setShowMessage(false);
                    documentForm.getDialogManager().removeDialog(dialog);
                    // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
                    proposalElementsWrapper.getUiHelper().setPendingWorkflowAction(true);
                    documentForm.setPageId(getReviewPageKradPageId());
                    documentForm.setMethodToCall(KRADConstants.DOC_HANDLER_METHOD);
                    String href = buildProposalUrl(KRADConstants.DOC_HANDLER_METHOD, getReviewPageKradPageId(), documentForm.getDocument().getDocumentNumber(), proposalElementsWrapper.getProposalInfo().getTypeKey());
                    return performRedirect(documentForm,href);
                } else {
                    documentForm.getDialogManager().removeDialog(dialog);
                }
            } else {
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

        String dialog = CurriculumManagementConstants.ProposalConfirmationDialogs.SUBMIT_CONFIRMATION_DIALOG;
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
                    ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject();
                    // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
                    proposalElementsWrapper.getUiHelper().setPendingWorkflowAction(true);
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

        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject();
        proposalElementsWrapper.getUiHelper().setShowMessage(false);
        String dialog = CurriculumManagementConstants.ProposalConfirmationDialogs.APPROVE_CONFIRMATION_DIALOG;
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
                    if(proposalElementsWrapper.getUiHelper().getDialogExplanations().get(dialog)!=null){
//                        super.approve(form,result, request,response);
                        // cannot call super class method as the redirect methods are causing an error when loading indicator is used after dialog confirmation is clicked
                        performWorkflowAction(form, UifConstants.WorkflowAction.APPROVE, true);

                        addDecisionRationale(proposalElementsWrapper.getProposalInfo().getId(), proposalElementsWrapper.getUiHelper().getDialogExplanations().get(dialog), CommentServiceConstants.WORKFLOW_DECISIONS.APPROVE.getType());
                        // setShowMessage boolean decides whether to show the error message or not
                        proposalElementsWrapper.getUiHelper().setShowMessage(false);
                        form.getDialogManager().removeDialog(dialog);
                        // Set the request redirect to false so that the user stays on the same page
                        form.setRequestRedirected(false);

                        // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
                        proposalElementsWrapper.getUiHelper().setPendingWorkflowAction(true);
                    } else {
                        form.getDialogManager().removeDialog(dialog);
                        form.setDialogResponse(null);
                        proposalElementsWrapper.getUiHelper().setShowMessage(true);
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

        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject();
        proposalElementsWrapper.getUiHelper().setShowMessage(false);
        String dialog = CurriculumManagementConstants.ProposalConfirmationDialogs.REJECT_CONFIRMATION_DIALOG;
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
                    if(proposalElementsWrapper.getUiHelper().getDialogExplanations().get(dialog)!=null){
//                        super.disapprove(form, result, request, response);
                        // cannot call super class method as the redirect methods are causing an error when loading indicator is used after dialog confirmation is clicked
                        performWorkflowAction(form, UifConstants.WorkflowAction.DISAPPROVE, true);

                        addDecisionRationale(proposalElementsWrapper.getProposalInfo().getId(), proposalElementsWrapper.getUiHelper().getDialogExplanations().get(dialog), CommentServiceConstants.WORKFLOW_DECISIONS.REJECT.getType());
                        // setShowMessage boolean decides whether to show the error message or not
                        proposalElementsWrapper.getUiHelper().setShowMessage(false);
                        form.getDialogManager().removeDialog(dialog);
                        // Set the request redirect to false so that the user stays on the same page
                        form.setRequestRedirected(false);
                        // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
                        proposalElementsWrapper.getUiHelper().setPendingWorkflowAction(true);
                    } else {
                        form.getDialogManager().removeDialog(dialog);
                        form.setDialogResponse(null);
                        proposalElementsWrapper.getUiHelper().setShowMessage(true);
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
     * This will FYI an approved proposal.
     *
     * @param form     {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request  {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @return The new {@link ModelAndView} that contains the newly created/updated {@ProposalInfo} information.
     */
    @Override@RequestMapping(params = "methodToCall=fyi")
    public ModelAndView fyi(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject();
        super.fyi(form, result, request, response);
        // Set the request redirect to false so that the user stays on the same page
        form.setRequestRedirected(false);
        // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
        proposalElementsWrapper.getUiHelper().setPendingWorkflowAction(true);
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

        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject();
        proposalElementsWrapper.getUiHelper().setShowMessage(false);
        String dialog = CurriculumManagementConstants.ProposalConfirmationDialogs.ACKNOWLEDGE_CONFIRMATION_DIALOG;
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
                    if(proposalElementsWrapper.getUiHelper().getDialogExplanations().get(dialog)!=null){
//                        super.acknowledge(form,result, request,response);
                        // cannot call super class method as the redirect methods are causing an error when loading indicator is used after dialog confirmation is clicked
                        performWorkflowAction(form, UifConstants.WorkflowAction.ACKNOWLEDGE, false);

                        addDecisionRationale(proposalElementsWrapper.getProposalInfo().getId(), proposalElementsWrapper.getUiHelper().getDialogExplanations().get(dialog), CommentServiceConstants.WORKFLOW_DECISIONS.ACKNOWLEDGE.getType());
                        // setShowMessage boolean decides whether to show the error message or not
                        proposalElementsWrapper.getUiHelper().setShowMessage(false);
                        form.getDialogManager().removeDialog(dialog);
                        // Set the request redirect to false so that the user stays on the same page
                        form.setRequestRedirected(false);
                        proposalElementsWrapper.getUiHelper().setPendingWorkflowAction(true);
                    } else {
                        form.getDialogManager().removeDialog(dialog);
                        form.setDialogResponse(null);
                        proposalElementsWrapper.getUiHelper().setShowMessage(true);
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

        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject();
        proposalElementsWrapper.getUiHelper().setShowMessage(false);
        String dialog = CurriculumManagementConstants.ProposalConfirmationDialogs.BLANKET_APPROVE_CONFIRMATION_DIALOG;
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
                    if(proposalElementsWrapper.getUiHelper().getDialogExplanations().get(dialog)!=null){
//                        super.blanketApprove(form, result, request, response);
                        // cannot call super class method as the redirect methods are causing an error when loading indicator is used after dialog confirmation is clicked
                        performWorkflowAction(form, UifConstants.WorkflowAction.BLANKETAPPROVE, true);

                        addDecisionRationale(proposalElementsWrapper.getProposalInfo().getId(), proposalElementsWrapper.getUiHelper().getDialogExplanations().get(dialog), CommentServiceConstants.WORKFLOW_DECISIONS.BLANKET_APPROVE.getType());
                        // setShowMessage boolean decides whether to show the error message or not
                        proposalElementsWrapper.getUiHelper().setShowMessage(false);
                        form.getDialogManager().removeDialog(dialog);
                        // Set the request redirect to false so that the user stays on the same page
                        form.setRequestRedirected(false);
                        proposalElementsWrapper.getUiHelper().setPendingWorkflowAction(true);
                    } else {
                        form.getDialogManager().removeDialog(dialog);
                        form.setDialogResponse(null);
                        proposalElementsWrapper.getUiHelper().setShowMessage(true);
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
     * This method is called form the UI when a user wants to return a proposal to a previous node.
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

        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject();
        proposalElementsWrapper.getUiHelper().setShowMessage(false);
        String dialog = CurriculumManagementConstants.ProposalConfirmationDialogs.RETURN_TO_PREVIOUS_NODE_DIALOG;
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
                    if(proposalElementsWrapper.getUiHelper().getDialogExplanations().get(dialog)!=null){
                        performCustomWorkflowAction(form, result, request, response, CurriculumManagementConstants.WorkflowActions.RETURN_TO_PREVIOUS);
                        addDecisionRationale(proposalElementsWrapper.getProposalInfo().getId(), proposalElementsWrapper.getUiHelper().getDialogExplanations().get(dialog), CommentServiceConstants.WORKFLOW_DECISIONS.RETURN_TO_PREVIOUS.getType());
                        // setShowMessage boolean decides whether to show the error message or not
                        proposalElementsWrapper.getUiHelper().setShowMessage(false);
                        form.getDialogManager().removeDialog(dialog);
                        // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
                        proposalElementsWrapper.getUiHelper().setPendingWorkflowAction(true);
                    }else{
                        form.getDialogManager().removeDialog(dialog);
                        form.setDialogResponse(null);
                        proposalElementsWrapper.getUiHelper().setShowMessage(true);
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

    /**
     * This will withdraw the proposal.
     *
     * @param form     {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request  {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @return The new {@link ModelAndView} that contains the newly created/updated {@ProposalInfo} information.
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

        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject();
        proposalElementsWrapper.getUiHelper().setShowMessage(false);
        String dialog = CurriculumManagementConstants.ProposalConfirmationDialogs.WITHDRAW_CONFIRMATION_DIALOG;
        if ( ! hasDialogBeenDisplayed(dialog, form)) {
            doValidationForProposal(form, KewApiConstants.ROUTE_HEADER_ENROUTE_CD, null);

            if (!GlobalVariables.getMessageMap().hasErrors()) {
                //redirect back to client to display confirm dialog
                return showDialog(dialog, form, request, response);
            }
        } else {
            if (hasDialogBeenAnswered(dialog,form)) {
                boolean confirmBlanketApprove = getBooleanDialogResponse(dialog, form, request, response);
                if (confirmBlanketApprove) {
                    //route the document only if the rationale decision explanation is not null or redirect back to client to display confirm dialog with error
                    if(proposalElementsWrapper.getUiHelper().getDialogExplanations().get(dialog)!=null){
                        performCustomWorkflowAction(form, result, request, response, CurriculumManagementConstants.WorkflowActions.WITHDRAW);
                        addDecisionRationale(proposalElementsWrapper.getProposalInfo().getId(), proposalElementsWrapper.getUiHelper().getDialogExplanations().get(dialog), CommentServiceConstants.WORKFLOW_DECISIONS.WITHDRAW.getType());
                        // setShowMessage boolean decides whether to show the error message or not
                        proposalElementsWrapper.getUiHelper().setShowMessage(false);
                        form.getDialogManager().removeDialog(dialog);
                        // Set the request redirect to false so that the user stays on the same page
                        form.setRequestRedirected(false);
                        // Hide all the workflow action buttons on the review proposal page while the document is still in Enroute state(It is being processed at the back-end)
                        proposalElementsWrapper.getUiHelper().setPendingWorkflowAction(true);
                    } else {
                        form.getDialogManager().removeDialog(dialog);
                        form.setDialogResponse(null);
                        proposalElementsWrapper.getUiHelper().setShowMessage(true);
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

        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject();

        SupportingDocumentInfoWrapper supportingDocument = proposalElementsWrapper.getSupportingDocs().get(selectedLineIndex);
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
     * Handles menu navigation between view pages
     */
    @Override
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=navigate")
    public ModelAndView navigate(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) {
        final ModelAndView retval = super.navigate(form, result, request, response);

        ((ProposalMaintainable) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject()).updateReview();

        return retval;
    }

    /**
     * Load the proposal edit page
     */
    @RequestMapping(params = "methodToCall=editProposalPage")
    public ModelAndView editProposalPage(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        String displaySectionId = form.getActionParameters().get("displaySection");
        ProposalElementsWrapper wrapper = getProposalElementsWrapper(form);
        if (displaySectionId == null) {
            wrapper.getUiHelper().setSelectedSection(getDefaultSectionKradIdForEdit());
        } else {
            wrapper.getUiHelper().setSelectedSection(getSectionById(displaySectionId));
        }

        return getUIFModelAndView(form, getKradPageIdForEdit());
    }

    protected abstract CurriculumManagementConstants.UserInterfaceSections getSectionById(String sectionId);

    protected abstract CurriculumManagementConstants.UserInterfaceSections getDefaultSectionKradIdForEdit();

    protected abstract String getKradPageIdForEdit();

    /**
     * This method performs the KRAD UI data dictionary and Service layer data dictionary validation before it routes the document instance contained on the form.
     *
     * @param form - the form of the current users session
     * @param workflowStatusCode - the status code that should be used to perform KRAD view validation
     * @param forcedStudentObjectStateKey - the stateKey that needs to be faked for the Kuali Student Service validation method (if null, uses existing state from the object)
     */
    protected void doValidationForProposal(DocumentFormBase form, String workflowStatusCode, String forcedStudentObjectStateKey) {
        //Perform KRAD UI Data Dictionary Validation
        // manually call the view validation service as this validation cannot be run client-side in current setup
        KRADServiceLocatorWeb.getViewValidationService().validateView(form, workflowStatusCode);

        //Perform Rules validation
        KRADServiceLocatorWeb.getKualiRuleService().applyRules(new RouteDocumentEvent(form.getDocument()));

        runStudentServiceValidation(form, forcedStudentObjectStateKey);
    }

    protected abstract void runStudentServiceValidation(DocumentFormBase form, String forcedStudentObjectStateKey);

    protected DocumentService getSupportingDocumentService() {
        if (documentService == null) {
            documentService = (DocumentService) GlobalResourceLoader.getService(new QName(DocumentServiceConstants.NAMESPACE, "DocumentService"));
        }
        return documentService;
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
