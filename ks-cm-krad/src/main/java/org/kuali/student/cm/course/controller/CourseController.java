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
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.name.EntityNameContract;
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
import org.kuali.student.cm.course.form.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.CourseCreateUnitsContentOwner;
import org.kuali.student.cm.course.form.CourseInfoWrapper;
import org.kuali.student.cm.course.form.LoDisplayInfoWrapper;
import org.kuali.student.cm.course.form.LoDisplayWrapperModel;
import org.kuali.student.cm.course.form.RecentlyViewedDocsUtil;
import org.kuali.student.cm.course.form.ResultValuesGroupInfoWrapper;
import org.kuali.student.cm.course.form.SupportingDocumentInfoWrapper;
import org.kuali.student.cm.course.service.CourseInfoMaintainable;
import org.kuali.student.cm.course.util.CourseProposalUtil;
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.common.uif.util.KSViewAttributeValueReader;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.constants.DocumentServiceConstants;
import org.kuali.student.r2.core.constants.ProposalServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.service.DocumentService;
import org.kuali.student.r2.core.proposal.service.ProposalService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
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
@RequestMapping(value = CurriculumManagementConstants.ControllerRequestMappings.CREATE_COURSE)
public class CourseController extends CourseRuleEditorController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);

    public static final String URL_PARAM_USE_CURRICULUM_REVIEW = "useCurriculumReview";
    private static final String DECISIONS_DIALOG_KEY = "decisionsDialog";

    private CourseService courseService;
    private CommentService commentService;
    private DocumentService documentService;
    private SubjectCodeService subjectCodeService;
    private IdentityService identityService;
    private CluService cluService;
    private ProposalService proposalService;
    private TypeService typeService;

    /**
     * This method creates the form and in the case of a brand new proposal where this method is called after the user uses
     * the Initial Create Proposal screen, this method will also set the document type name based on the request parameter
     * #URL_PARAM_USE_CURRICULUM_REVIEW
     *
     * @param request
     * @return a new instance of a MaintenanceDocumentForm
     */
    @Override
    protected MaintenanceDocumentForm createInitialForm(HttpServletRequest request) {
        MaintenanceDocumentForm form = new MaintenanceDocumentForm();
        String useReviewProcessParam = request.getParameter(URL_PARAM_USE_CURRICULUM_REVIEW);
        // only do the manually setup of the MaintenanceDocumentForm fields if the URL_PARAM_USE_CURRICULUM_REVIEW param was passed in from initial view
        if (StringUtils.isNotBlank(useReviewProcessParam)) {
            Boolean isUseReviewProcess = new Boolean(useReviewProcessParam);
            // throw an exception if the user is not a CS user but attempts to disable Curriculum Review for a proposal
            if (!isUseReviewProcess && !CourseProposalUtil.isUserCurriculumSpecialist()) {
                throw new RuntimeException("A user (" + GlobalVariables.getUserSession().getPerson().getPrincipalName() + ") who is not allowed to disable Curriculum Review (Workflow Approval) has attempted to.");
            }
            // set the doc type name based on the whether the user is CS and if they have chosen to use curriculum review
            form.setDocTypeName((!isUseReviewProcess) ? CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_CREATE_ADMIN : CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_CREATE);
        }
        return form;
    }

    /**
     * Digs the CourseInfoWrapper out of a DocumentFormBase.
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
     * Currently updates the MaintenanceDocumentForm to set the 'useReviewProcess' property based on the document type name. If
     * the document type name is CurriculumManagementConstants#DocumentTypeNames#CourseProposal#COURSE_CREATE_ADMIN or
     * CurriculumManagementConstants#DocumentTypeNames#CourseProposal#COURSE_MODIFY_ADMIN then set 'useReviewProcss' to
     * false
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

        if (formBase.getPageId().equals(CurriculumManagementConstants.CourseViewPageIds.REVIEW_PROPOSAL)) {
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
            String courseBaseUrl = CurriculumManagementConstants.ControllerRequestMappings.CREATE_COURSE.replaceFirst("/", "");
            ModelAndView mv = performRedirect(formBase, courseBaseUrl, urlParameters);
            formBase.setRequestRedirected(false);
            return mv;
        }
        return modelAndView;
    }

    @Override
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        // manually call the view validation service as this validation cannot be run client-side in current setup
//        KRADServiceLocatorWeb.getViewValidationService().validateView(form.getPostedView(), form, KewApiConstants.ROUTE_HEADER_ENROUTE_CD);
        KSViewAttributeValueReader reader = new KSViewAttributeValueReader(form);
        KRADServiceLocatorWeb.getDictionaryValidationService().validate(reader, true, KewApiConstants.ROUTE_HEADER_ENROUTE_CD, form.getView().getStateMapping());
        if (!GlobalVariables.getMessageMap().hasErrors()) {
            return super.route(form, result, request, response);    //To change body of overridden methods use File | Settings | File Templates.
        }
        return getUIFModelAndView(form);
    }

    /**
     * load the course proposal review page
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=reviewCourseProposal")
    public ModelAndView reviewCourseProposal(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        ((CourseInfoMaintainable) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject()).updateReview();

        //  Validate
        KRADServiceLocatorWeb.getViewValidationService().validateViewAgainstNextState(form);
        if (GlobalVariables.getMessageMap().hasErrors()) {
            CourseInfoWrapper wrapper = getCourseInfoWrapper(form);
            wrapper.setMissingRequiredFields(true);
        }
        return getUIFModelAndView(form, CurriculumManagementConstants.CourseViewPageIds.REVIEW_PROPOSAL);
    }

    /**
     * load the course proposal review page
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
        final CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);

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
                form.getDocument().getDocumentHeader().getWorkflowDocument().getDocumentHandlerUrl() + "&docId="
                        + form.getDocument().getDocumentHeader().getWorkflowDocument().getDocumentId());

        String nextOrCurrentPage = form.getActionParameters().get("displayPage");

        if (StringUtils.equalsIgnoreCase(nextOrCurrentPage, "NEXT")) {
            CourseViewSections currentSection = courseInfoWrapper.getUiHelper().getSelectedSection();
            if (currentSection.ordinal() < CourseViewSections.values().length) {
                CourseViewSections nextSection = CourseViewSections.values()[currentSection.ordinal() + 1];
                courseInfoWrapper.getUiHelper().setSelectedSection(nextSection);
            }
            return getUIFModelAndView(form);
        } else if (StringUtils.equalsIgnoreCase(nextOrCurrentPage, "KS-CourseView-ReviewProposalLink")) {
            return getUIFModelAndView(form, CurriculumManagementConstants.CourseViewPageIds.REVIEW_PROPOSAL);
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
     * Copied this method from CourseDataService.
     * This calculates and sets fields on course object that are derived from other course object fields.
     */
    protected CourseInfo calculateCourseDerivedFields(CourseInfo courseInfo) {
        // Course code is not populated in UI, need to derive them from the subject area and suffix fields
        if (StringUtils.isNotBlank(courseInfo.getCourseNumberSuffix()) && StringUtils.isNotBlank(courseInfo.getSubjectArea())) {
            courseInfo.setCode(calculateCourseCode(courseInfo.getSubjectArea(), courseInfo.getCourseNumberSuffix()));
        }

        // Derive course code for crosslistings
        for (CourseCrossListingInfo crossListing : courseInfo.getCrossListings()) {
            if (StringUtils.isNotBlank(crossListing.getCourseNumberSuffix()) && StringUtils.isNotBlank(crossListing.getSubjectArea())) {
                crossListing.setCode(calculateCourseCode(crossListing.getSubjectArea(), crossListing.getCourseNumberSuffix()));
            }
        }

        return courseInfo;
    }

    /**
     * Copied this method from CourseDataService
     * This method calculates code for course and cross listed course.
     *
     * @param subjectArea
     * @param suffixNumber
     * @return
     */
    protected String calculateCourseCode(final String subjectArea, final String suffixNumber) {
        return subjectArea + suffixNumber;
    }

    /**
     * Converts the display name of the instructor into the plain user name (for use in a search query)
     *
     * @param displayName The display name of the instructor.
     * @return The user name of the instructor.
     */
    protected String getInstructorSearchString(String displayName) {
        String searchString = null;
        if (displayName.contains("(") && displayName.contains(")")) {
            searchString = displayName.substring(displayName.lastIndexOf('(') + 1, displayName.lastIndexOf(')'));
        }
        return searchString;
    }

    /**
     * Determines to which page to navigate.
     *
     * @param currentSectionId The current page id.
     * @return The id of the next page to navigate to.
     */
    protected String getNextSectionId(final String currentSectionId) {
        String nextPageId = null;
        final CourseViewSections[] pages = CourseViewSections.values();
        for (int i = 0; i < pages.length; i++) {
            if (pages[i].getSectionId().equals(currentSectionId)) {
                //Get the next page in the enum, except when it's the last page in the enum
                if (i + 1 < pages.length) {
                    nextPageId = pages[++i].getSectionId();
                    break;
                }
            }
        }
        return nextPageId;
    }


    protected String getPreviousSectionId(final String currentSectionId) {
        String prevPageId = null;
        final CourseViewSections[] pages = CourseViewSections.values();
        for (int i = 1; i < pages.length; i++) {
            if (pages[i].getSectionId().equals(currentSectionId)) {
                prevPageId = pages[--i].getSectionId();
                break;
            }
        }
        return prevPageId;
    }

    /**
     * @param userId The id of the person currently logged in.
     * @return returns User name of person currently logged in.
     */
    public String getUserNameLoggedin(String userId) {
        final Entity kimEntityInfo = getIdentityService().getEntityByPrincipalId(userId);
        return getUserRealNameByEntityInfo(kimEntityInfo);
    }

    /**
     * @param kimEntityInfo The (@link Entity) information for the currently logged in user.
     * @return The formatted user name of the currently logged in user.
     */
    protected String getUserRealNameByEntityInfo(Entity kimEntityInfo) {
        final EntityNameContract kimEntityNameInfo = (kimEntityInfo == null) ? null : kimEntityInfo.getDefaultName();
        final StringBuilder name = new StringBuilder();
        if (kimEntityNameInfo != null) {
            if (!StringUtils.defaultString(kimEntityNameInfo.getFirstName()).trim().isEmpty()) {
                if (!name.toString().isEmpty()) {
                    name.append(" ");
                }
                name.append(StringUtils.defaultString(kimEntityNameInfo.getFirstName()));
            }

            if (!StringUtils.defaultString(kimEntityNameInfo.getMiddleName()).trim().isEmpty()) {
                if (!name.toString().isEmpty()) {
                    name.append(" ");
                }
                name.append(StringUtils.defaultString(kimEntityNameInfo.getMiddleName()));
            }
            if (!StringUtils.defaultString(kimEntityNameInfo.getLastName()).trim().isEmpty()) {
                if (!name.toString().isEmpty()) {
                    name.append(" ");
                }
                name.append(StringUtils.defaultString(kimEntityNameInfo.getLastName()));
            }
        }
        return name.toString();
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
                    : getSubjectCodeService().search(searchRequest, ContextUtils.getContextInfo()).getRows()) {

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

        CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);
        maintainable.setLOActions();

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=moveLearningObjectiveDown")
    public ModelAndView moveLearningObjectiveDown(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form)
    throws Exception {

        LoDisplayWrapperModel loItemModel = setupLoModel(form);
        loItemModel.moveDownCurrent();

        CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);
        maintainable.setLOActions();

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=moveLearningObjectiveRight")
    public ModelAndView moveLearningObjectiveRight(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form)
    throws Exception {

        LoDisplayWrapperModel loItemModel = setupLoModel(form);
        loItemModel.indentCurrent();

        CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);
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
        final CourseInfoMaintainable maintainable = getCourseMaintainableFrom((MaintenanceDocumentForm) form);

        ((CourseInfoMaintainable) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject()).updateReview();

        return retval;
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=moveLearningObjectiveLeft")
    public ModelAndView moveLearningObjectiveLeft(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form)
    throws Exception {

        LoDisplayWrapperModel loItemModel = setupLoModel(form);
        loItemModel.outdentCurrent();

        CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);
        maintainable.setLOActions();

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=deleteLO")
    public ModelAndView deleteLO(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        LoDisplayWrapperModel loModel = setupLoModel(form);

        loModel.deleteLearningObjective(loModel.getCurrentLoWrapper());

        CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);
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

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluService.class.getSimpleName()));
        }
        return cluService;
    }

    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, CourseServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseService;
    }

    protected ProposalService getProposalService() {
        if (proposalService == null) {
            proposalService = (ProposalService) GlobalResourceLoader.getService(new QName(ProposalServiceConstants.NAMESPACE, ProposalServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return proposalService;
    }

    protected DocumentService getSupportingDocumentService() {
        if (documentService == null) {
            documentService = (DocumentService) GlobalResourceLoader.getService(new QName(DocumentServiceConstants.NAMESPACE, "DocumentService"));
        }
        return documentService;
    }

    protected IdentityService getIdentityService() {
        if (identityService == null) {
            identityService = GlobalResourceLoader.getService(new QName(KimConstants.Namespaces.KIM_NAMESPACE_2_0, "identityService"));
        }
        return identityService;
    }

    protected SubjectCodeService getSubjectCodeService() {
        if (subjectCodeService == null) {
            subjectCodeService = GlobalResourceLoader.getService(new QName(CourseServiceConstants.NAMESPACE_SUBJECTCODE, SubjectCodeService.class.getSimpleName()));
        }
        return subjectCodeService;
    }

    protected TypeService getTypeService() {
        if (typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        return typeService;
    }

}
