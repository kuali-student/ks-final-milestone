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
import org.joda.time.DateTime;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.name.EntityNameContract;
import org.kuali.rice.krad.datadictionary.validation.result.DictionaryValidationResult;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.ErrorMessage;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.CourseViewSections;
import org.kuali.student.cm.course.form.CourseInfoWrapper;
import org.kuali.student.cm.course.form.LoDisplayInfoWrapper;
import org.kuali.student.cm.course.form.LoDisplayWrapperModel;
import org.kuali.student.cm.course.form.RecentlyViewedDocsUtil;
import org.kuali.student.cm.course.form.SupportingDocumentInfoWrapper;
import org.kuali.student.cm.course.service.CourseInfoMaintainable;
import org.kuali.student.cm.course.util.CourseProposalUtil;
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.common.uif.util.KSViewAttributeValueReader;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.DtoConstants.DtoState;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.constants.CommentServiceConstants;
import org.kuali.student.r2.core.constants.DocumentServiceConstants;
import org.kuali.student.r2.core.constants.ProposalServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;
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
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.Date;
import java.util.List;
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
            // throw an exception if the user is not a CS use but attempts to disable Curriculum Review for a proposal
            if (!isUseReviewProcess && !CourseProposalUtil.isUserCurriculumSpecialist()) {
                throw new RuntimeException("A user " + GlobalVariables.getUserSession().getPerson().getPrincipalName() + " who is not allowed to disable Curriculum Review (Workflow Approval) has attempted to.");
            }
            // set the doc type name based on the whether the user is CS and if they have chosen to use curriculum review
            form.setDocTypeName((!isUseReviewProcess) ? CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_CREATE_ADMIN : CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_CREATE);
        }
        return form;
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
        CourseInfoWrapper wrapper = ((CourseInfoWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject());
        wrapper.getUiHelper()
                .setUseReviewProcess(!ArrayUtils.contains(CurriculumManagementConstants.DocumentTypeNames.ADMIN_DOC_TYPE_NAMES, form.getDocTypeName()));
        wrapper.getUiHelper()
                .setCurriculumSpecialistUser(CourseProposalUtil.isUserCurriculumSpecialist());
    }

    /**
     * This method is being overridden to validate the Review Proposal page before it is displayed.
     *
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
            for (Object p  : requestParameterMap.keySet()) {
                urlParameters.put((String) p, ((String[]) requestParameterMap.get(p))[0]);
            }
            urlParameters.put("methodToCall", "reviewCourseProposal");
            urlParameters.put("formKey", formBase.getFormKey());

            /*
             * Calling performRedirect() to build the response, but undoing the call to form.setRequestRedirect(true)
             * that happens within performRedirect() before returning. Setting that flag causes the postedView to be
             * discarded in UifControllerHandlerInterceptor#afterCompletion(). If the postedView isn't available the
             * validation in the reviewCourseProposal handler method will fail.
             */
            ModelAndView mv = performRedirect(formBase, "courses", urlParameters);
            formBase.setRequestRedirected(false);
            return mv;
        }
        return modelAndView;
    }

    @Override
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        // manually call the view validation service as this validation cannot be run client-side in current setup
//        KRADServiceLocatorWeb.getViewValidationService().validateView(form.getPostedView(), form, KewApiConstants.ROUTE_HEADER_ENROUTE_CD);
        KSViewAttributeValueReader reader = new KSViewAttributeValueReader(form.getPostedView(), form);
        KRADServiceLocatorWeb.getDictionaryValidationService().validate(reader, true, KewApiConstants.ROUTE_HEADER_ENROUTE_CD, form.getPostedView().getStateMapping());
        if (!GlobalVariables.getMessageMap().hasErrors()) {
            return super.route(form, result, request, response);    //To change body of overridden methods use File | Settings | File Templates.
        }
        return getUIFModelAndView(form);
    }

    /**
     * load the course proposal review page
     */
    @RequestMapping(params = "methodToCall=reviewCourseProposal")
    public ModelAndView reviewCourseProposal(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        ((CourseInfoMaintainable)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject()).updateReview();

        if (GlobalVariables.getMessageMap().hasErrors()) {
            GlobalVariables.getMessageMap().clearErrorMessages();
        }

        //  Validate
        DictionaryValidationResult o= KRADServiceLocatorWeb.getViewValidationService().validateViewAgainstNextState(form.getPostedView(), form);

        return getUIFModelAndView(form, CurriculumManagementConstants.CourseViewPageIds.REVIEW_PROPOSAL);
    }

    /**
     * load the course proposal review page
     */
    @RequestMapping(params = "methodToCall=editCourseProposalPage")
    public ModelAndView editCourseProposalPage(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {


        String displaySectionId = form.getActionParameters().get("displaySection");
        CourseInfoWrapper wrapper = ((CourseInfoWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject());

        if (displaySectionId == null) {
            wrapper.getUiHelper().setSelectedSection(CourseViewSections.COURSE_INFO);
        }  else {
            CourseViewSections section = CourseViewSections.getSection(displaySectionId);
            wrapper.getUiHelper().setSelectedSection(section);
        }

        return getUIFModelAndView(form, CurriculumManagementConstants.CourseViewPageIds.CREATE_COURSE);
    }

    /**
    * Add a Supporting Document line
    *
    *
    * @param form {@link MaintenanceDocumentForm} instance used for this action
    * @param result
    * @param request {@link HttpServletRequest} instance of the actual HTTP request made
    * @param response The intended {@link HttpServletResponse} sent back to the user
    * @return The new {@link ModelAndView} that contains the newly created/updated Supporting document information.
    */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=addSupportingDocument")
    public ModelAndView addSupportingDocument(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                HttpServletRequest request, HttpServletResponse response) {
        final CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);
        final ModelAndView retval = addLine(form, result, request, response);

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject();


        // Resulting Add Line is at the bottom
        final SupportingDocumentInfoWrapper addLineResult = courseInfoWrapper.getDocumentsToAdd().get(courseInfoWrapper.getDocumentsToAdd().size() - 1);

        // New document
        DocumentInfo toAdd = new DocumentInfo();
        toAdd.setFileName(addLineResult.getDocumentUpload().getOriginalFilename());
        toAdd.setDescr(new RichTextInfo() {{
            setPlain(addLineResult.getDescription());
            setFormatted(addLineResult.getDescription());
        }});
        toAdd.setName(toAdd.getFileName());
        
        try {
            toAdd.getDocumentBinary().setBinary(new String(Base64.encodeBase64(addLineResult.getDocumentUpload().getBytes())));
            getSupportingDocumentService().createDocument("documentType.doc", "documentCategory.proposal", toAdd, ContextUtils.getContextInfo());

            // Now relate the document to the course
            RefDocRelationInfo docRelation = new RefDocRelationInfo();
            getSupportingDocumentService().createRefDocRelation("kuali.lu.type.CreditCourse",
                    courseInfoWrapper.getCourseInfo().getId(),
                    toAdd.getId(),
                    "kuali.org.DocRelation.allObjectTypes",
                    docRelation,
                    ContextUtils.getContextInfo());
        } catch (Exception e) {
            LOG.warn("Unable to add supporting document to the course for file: " + toAdd.getFileName(), e);
        }

        return retval;
    }

    /**
     * Delete a Supporting Document line
     *
     *
     * @param form {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @return The new {@link ModelAndView} that contains the newly created/updated Supporting document information.
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=removeSupportingDocument")
    public ModelAndView removeSupportingDocument(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                HttpServletRequest request, HttpServletResponse response) {
        final CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        // final ModelAndView retval = super.deleteLine(form, result, request, response);

        final String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            GlobalVariables.getMessageMap().putErrorForSectionId(CourseViewSections.SUPPORTING_DOCUMENTS.getSectionId(), CurriculumManagementConstants.MessageKeys.UNABLE_TO_ADD_LINE);
            return getUIFModelAndView(form);
        }

        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        final int selectedLineIndex;
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        } else {
            selectedLineIndex = -1;
        }

        if (selectedLineIndex == -1) {
            GlobalVariables.getMessageMap().putErrorForSectionId(CourseViewSections.SUPPORTING_DOCUMENTS.getSectionId(), CurriculumManagementConstants.MessageKeys.UNABLE_TO_DELETE_LINE);
            return getUIFModelAndView(form);
        }
        
        final DocumentInfo toRemove = courseInfoWrapper.getSupportingDocuments().remove(selectedLineIndex);
        try {
            getSupportingDocumentService().deleteDocument(toRemove.getId(), ContextUtils.getContextInfo());
        }
        catch (Exception e) {
            LOG.warn("Unable to delete document: " + toRemove.getId(),e);
            throw new RuntimeException("Unable to delete document: " + toRemove.getId(), e);
        }
        
        return deleteLine(form, result, request, response);
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
     *
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
        super.performWorkflowAction(form,action,checkSensitiveData);
    }

    /**
     * Here we move the success messages displayed in UI from header to growl.
     * @param form
     * @param action
     * @param checkSensitiveData
     */
    @Override
    protected void performWorkflowAction(DocumentFormBase form, UifConstants.WorkflowAction action, boolean checkSensitiveData) {
        CourseControllerTransactionHelper helper = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseControllerTransactionHelper", CourseControllerTransactionHelper.class.getSimpleName()));
        helper.performWorkflowActionSuper(form, action, checkSensitiveData, this);
        AutoPopulatingList<ErrorMessage> infoMessages = GlobalVariables.getMessageMap().getInfoMessagesForProperty(KRADConstants.GLOBAL_MESSAGES);
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
     * @param form {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @return The new {@link ModelAndView} that contains the newly created/updated {@CourseInfo} and {@ProposalInfo} information.
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=saveProposal")
    public ModelAndView saveProposal(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
//        final CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);
//        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
//
//

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        form.getDocument().getDocumentHeader().setDocumentDescription(courseInfoWrapper.getProposalInfo().getName());

        ModelAndView modelAndView;

        modelAndView = save(form, result, request, response);

        if (GlobalVariables.getMessageMap().hasErrors()){
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
        saveProposal((MaintenanceDocumentForm)form,result,request,response);
        return super.blanketApprove(form,result,request,response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=previousPage")
    public ModelAndView previousPage(@ModelAttribute("KualiForm") MaintenanceDocumentForm form,HttpServletRequest request) {
        return getUIFModelAndView(form);
    }

    /**
     *
     * @param form
     */
    /*protected void updateReview(final DocumentFormBase form) {
        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();
        CourseInfo savedCourseInfo = courseInfoWrapper.getCourseInfo();

        // Update course section
        final ReviewProposalDisplay reviewData = courseInfoWrapper.getReviewProposalDisplay();
        reviewData.getcourseSection().setProposalName(courseInfoWrapper.getProposalInfo().getName());
        reviewData.getcourseSection().setCourseTitle(savedCourseInfo.getCourseTitle());
        reviewData.getcourseSection().setTranscriptTitle(savedCourseInfo.getTranscriptTitle());
        reviewData.getcourseSection().setSubjectArea(savedCourseInfo.getSubjectArea());
        reviewData.getcourseSection().setCourseNumberSuffix(savedCourseInfo.getCourseNumberSuffix());

        // Update governance section
        reviewData.getgovernanceSection().getCampusLocations().clear();
        reviewData.getgovernanceSection().getCampusLocations().addAll(savedCourseInfo.getCampusLocations());
        reviewData.getgovernanceSection().getCurriculumOversight().clear();
        reviewData.getgovernanceSection().getCurriculumOversight().addAll(savedCourseInfo.getUnitsContentOwner());

        // update course logistics section
        reviewData.getcourseLogisticsSection().getTerms().clear();
        try {
            for(String termType : savedCourseInfo.getTermsOffered())  {
                TypeInfo term = getTypeService().getType(termType, ContextUtils.getContextInfo());
                reviewData.getcourseLogisticsSection().getTerms().add(term.getName());
            }
        } catch (Exception e) {
            throw new RiceIllegalStateException(e);
        }

      if(savedCourseInfo.getDuration() != null &&  StringUtils.isNotBlank(savedCourseInfo.getDuration().getAtpDurationTypeKey())) {
        try{
                TypeInfo term = getTypeService().getType(savedCourseInfo.getDuration().getAtpDurationTypeKey(), ContextUtils.getContextInfo());
                reviewData.getcourseLogisticsSection().setAtpDurationType(term.getName());
            } catch (Exception e) {
                throw new RiceIllegalStateException(e);
            }
      }

        reviewData.getcourseLogisticsSection().setTimeQuantity(savedCourseInfo.getDuration().getTimeQuantity());

        // update learning Objectives Section;
        // update  course Requisites Section;
        // update  active Dates Section;
        // update  financials Section;
        // update  collaborator Section;
        // update  supporting Documents Section;
    }*/

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
     * Server-side action for rendering the comments lightbox
     * 
     * @param form {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=showComment")
    public ModelAndView showComment(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

            // redirect back to client to display lightbox
            return showDialog("commentsLightBox", form, request, response);
    }

    /**
     * This is called from the Comments lightbox. This is used to save the comments.
     * 
     * @param form {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=saveAndCloseComment")
    public ModelAndView saveAndCloseComment(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        
        //TODO KSCM-848 : Will need to replace these temp values once we get UMD's reference data
        
        String tempRefId =  "temp_reference_id";
        String tempRefTypeKey = "referenceType.clu.proposal";
        
        final CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);
        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        
        final String dateStr = DateFormatters.MONTH_DATE_YEAR_HOUR_MIN_CONCAT_AMPM_FORMATTER.format(new DateTime());
        final Date date = new Date();
        
        // This needs to be looked at when Reference data is retrieved. Lookup what comments are in the DB
        // prob might move to showComment so that it displays the comments already made.
        //List<CommentInfo> commentInfoFromDB = getCommentService().getCommentsByReferenceAndType(tempRefId, tempRefTypeKey,ContextUtils.getContextInfo());
        
        for (CommentInfo ittCommentInfo : courseInfoWrapper.getCommentInfos()) {
            CommentInfo commentInfo = ittCommentInfo;
            commentInfo.getCommentText().setFormatted(commentInfo.getCommentText().getPlain());
            //get the userID from the form to save with the comment made.
            commentInfo.setCommenterId(getUserNameLoggedin(courseInfoWrapper.getUserId()) + " " + dateStr);
            if(commentInfo.getEffectiveDate() == null){
                commentInfo.setEffectiveDate(date);
            }
            commentInfo.setTypeKey("kuali.comment.type.generalRemarks");
            commentInfo.setReferenceId(tempRefId);
            commentInfo.setReferenceTypeKey(tempRefTypeKey);
            commentInfo.setStateKey(DtoState.ACTIVE.toString());
            CommentInfo newComment = null;
            try {
                newComment = getCommentService().createComment(commentInfo.getReferenceId(),
                        commentInfo.getReferenceTypeKey(), commentInfo.getTypeKey(), commentInfo,
                        ContextUtils.getContextInfo());
            } catch (Exception e) {
                LOG.error("Error creating a new comment", e);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS,CurriculumManagementConstants.MessageKeys.ERROR_CREATE_COMMENT);
                return getUIFModelAndView(form);
            }
            ittCommentInfo = newComment;
        }
        //form.getDialogManager().removeDialog("commentsLightBox");
        /*return getUIFModelAndView(form);*/
        form.getDialogManager().setDialogReturnMethod("commentsLightBox", UifConstants.MethodToCallNames.START);
        return returnFromLightbox(form, result, request, response);
    }
    
    /**
     * @param userId The id of the person currently logged in.
     * @return returns User name of person currently logged in.
     */
    public String getUserNameLoggedin(String userId){
        final Entity kimEntityInfo = getIdentityService().getEntityByPrincipalId(userId);
        return getUserRealNameByEntityInfo(kimEntityInfo);
    }
    
    /**
     * @param kimEntityInfo The (@link Entity) information for the currently logged in user.
     * @return The formatted user name of the currently logged in user.
     */
    protected String getUserRealNameByEntityInfo(Entity kimEntityInfo){
        final EntityNameContract kimEntityNameInfo = (kimEntityInfo == null)? null : kimEntityInfo.getDefaultName();
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
     * @param form {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request {@link HttpServletRequest} instance of the actual HTTP request made
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
     * @param form the {@link MaintenanceDocumentForm} associated with the request
     * @param result
     * @param request the {@link HttpServletRequest} instance
     * @param response the {@link HttpServletResponse} instance
     * @return ModelAndView of the next view
     */
    @RequestMapping(params="methodToCall=refreshOversightSection")
    protected ModelAndView refreshOversightSection(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form,
                                                final BindingResult result,
                                                final HttpServletRequest request,
                                                final HttpServletResponse response) {
        LOG.info("Adding a unitsContentOwner");
        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        if(StringUtils.isBlank(courseInfoWrapper.getPreviousSubjectCode()) ||
                !StringUtils.equals(courseInfoWrapper.getPreviousSubjectCode(), courseInfoWrapper.getCourseInfo().getSubjectArea())) {
            courseInfoWrapper.getUnitsContentOwner().clear();
            courseInfoWrapper.setPreviousSubjectCode(courseInfoWrapper.getCourseInfo().getSubjectArea());
        }

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
    
    @RequestMapping(params = "methodToCall=moveLearningObjectiveUp")
    public ModelAndView moveLearningObjectiveUp(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        LoDisplayWrapperModel loModel = setupLoModel(form);
        loModel.moveUpCurrent();
        clearSelectedLoItem(loModel.getLoWrappers());
        
        return getUIFModelAndView(form);
    }
    
    @RequestMapping(params = "methodToCall=moveLearningObjectiveDown")
    public ModelAndView moveLearningObjectiveDown(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        LoDisplayWrapperModel loItemModel = setupLoModel(form);
        loItemModel.moveDownCurrent();
        clearSelectedLoItem(loItemModel.getLoWrappers());

        return getUIFModelAndView(form);
    }
    
    @RequestMapping(params = "methodToCall=moveLearningObjectiveRight")
    public ModelAndView moveLearningObjectiveRight(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        LoDisplayWrapperModel loItemModel = setupLoModel(form);
        loItemModel.indentCurrent();
        clearSelectedLoItem(loItemModel.getLoWrappers());
        
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

        ((CourseInfoMaintainable)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject()).updateReview();

        return retval;
    }
    
    @RequestMapping(params = "methodToCall=moveLearningObjectiveLeft")
    public ModelAndView moveLearningObjectiveLeft(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        LoDisplayWrapperModel loItemModel = setupLoModel(form);
        loItemModel.outdentCurrent();
        clearSelectedLoItem(loItemModel.getLoWrappers());

        return getUIFModelAndView(form);
    }
    
    private LoDisplayWrapperModel setupLoModel(MaintenanceDocumentForm form) {
        final CourseInfoMaintainable courseInfoMaintainable = getCourseMaintainableFrom(form);
        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        LoDisplayWrapperModel loDisplayWrapperModel = courseInfoWrapper.getLoDisplayWrapperModel();
        List<LoDisplayInfoWrapper> loWrappers = loDisplayWrapperModel.getLoWrappers();
        LoDisplayInfoWrapper selectedLoWrapper = getSelectedLoWrapper(loWrappers);
        loDisplayWrapperModel.setCurrentLoWrapper(selectedLoWrapper);
        return loDisplayWrapperModel;
    }
    
    private LoDisplayInfoWrapper getSelectedLoWrapper(List<LoDisplayInfoWrapper> loWrappers) {
        LoDisplayInfoWrapper selectedLo = null;
        if (loWrappers != null && !loWrappers.isEmpty()) {
            for (LoDisplayInfoWrapper loItem : loWrappers) {
                if (loItem.isSelected()) {
                    selectedLo = loItem;
                    break;
                }
            }
        }
        return selectedLo;
    }
    
    private void clearSelectedLoItem(List<LoDisplayInfoWrapper> loWrappers) {
        if (loWrappers != null && !loWrappers.isEmpty()) {
            for (LoDisplayInfoWrapper loWrapper : loWrappers) {
                loWrapper.setSelected(false);
            }
        }
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
    
    protected CommentService getCommentService() {
        if (commentService == null) {
            commentService = (CommentService) GlobalResourceLoader.getService(new QName(CommentServiceConstants.NAMESPACE, CommentService.class.getSimpleName()));
        }
        return commentService;
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
        if(typeService == null) {
            typeService =  (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        return typeService;
    }
}
