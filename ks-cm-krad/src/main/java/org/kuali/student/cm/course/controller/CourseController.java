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
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.name.EntityNameContract;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.CourseInfoWrapper;
import org.kuali.student.cm.course.form.CourseJointInfoWrapper;
import org.kuali.student.cm.course.form.CourseRuleManagementWrapper;
import org.kuali.student.cm.course.form.CreateCourseForm;
import org.kuali.student.cm.course.form.LoDisplayInfoWrapper;
import org.kuali.student.cm.course.form.LoDisplayWrapperModel;
import org.kuali.student.cm.course.form.OrganizationInfoWrapper;
import org.kuali.student.cm.course.form.RecentlyViewedDocsUtil;
import org.kuali.student.cm.course.form.ReviewInfo;
import org.kuali.student.cm.course.form.SupportingDocumentInfoWrapper;
import org.kuali.student.cm.course.service.CourseInfoMaintainable;
import org.kuali.student.cm.course.service.util.CourseCodeSearchUtil;
import org.kuali.student.core.organization.ui.client.mvc.model.MembershipInfo;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities.DecisionRationaleDetail;
import org.kuali.student.r1.core.proposal.ProposalConstants;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.DtoConstants.DtoState;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.DecisionInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.constants.CommentServiceConstants;
import org.kuali.student.r2.core.constants.DocumentServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.ProposalServiceConstants;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;
import org.kuali.student.r2.core.document.service.DocumentService;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.proposal.service.ProposalService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.CLUConstants;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseVariationInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.kuali.student.logging.FormattedLogger.debug;
import static org.kuali.student.logging.FormattedLogger.error;
import static org.kuali.student.logging.FormattedLogger.info;
import static org.kuali.student.logging.FormattedLogger.warn;

/**
 * This controller handles all the requests from the 'Create a Course' UI.
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
@Controller
@RequestMapping(value = "/courses")
public class CourseController extends CourseRuleEditorController {
        
    private static final String DECISIONS_DIALOG_KEY        = "decisionsDialog";    
    private static final String VIEW_CURRENT_PAGE_ID        = "view.currentPageId";
    private static final String COURSE_MODIFY_DOC_TYPE_NAME = "kuali.proposal.type.course.modify";
    private static final String COURSE_CREATE_DOC_TYPE_NAME = "kuali.proposal.type.course.create";
    private static final String COURSE_RETIRE_DOC_TYPE_NAME = "kuali.proposal.type.course.retire";
    private static final String CREDIT_COURSE_CLU_TYPE_KEY  = "kuali.lu.typeKey.CreditCourse";

    private CourseService courseService;
    private CommentService commentService;
    private DocumentService documentService;
	private SubjectCodeService subjectCodeService;    
    private IdentityService identityService;
    private CluService cluService;
    private ProposalService proposalService;
    
    private enum CourseViewSections {
        CREATE_COURSE_ENTRY("KS-CourseView-createCourseInitialPage"),
        COURSE_INFO("KS-CourseView-CourseInfo-Section"),
        GOVERNANCE("KS-CourseView-Governance-Section"),
        COURSE_LOGISTICS("KS-CourseView-Logistics-Section"),
        LEARNING_OBJECTIVES("KS-CourseView-LearningObjectives-Section"),
        COURSE_REQUISITES("KS-CourseView-CourseRequisites-Section"),
        ACTIVE_DATES("KS-CourseView-ActiveDates-Section"),
        FINANCIALS("KS-CourseView-Financials-Section"),
        AUTHORS_AND_COLLABORATORS("KS-CourseView-AuthorsAndCollaborators-Section"),
        SUPPORTING_DOCUMENTS("KS-CourseView-SupportingDocuments-Section"),
        REVIEW_PROPOSAL("KS-CourseView-ReviewProposalPage");
        
        private String sectionId;
        
        CourseViewSections(String sectionId) {
            this.sectionId = sectionId;
        }
        
        String getSectionId() {
            return this.sectionId;
        }
    }

    private Integer getSectionOrdinal(String selectedSection) {
        for(int idx = 1; idx <= CourseViewSections.values().length; idx++) {
            if(CourseViewSections.values()[idx].getSectionId().equalsIgnoreCase(selectedSection)) {
                return idx-1;
            }
        }
        return 0;
    }

    @Override
    protected CreateCourseForm createInitialForm(HttpServletRequest request) {
        return new CreateCourseForm();
    }
    
    /**
     * After the document is loaded calls method to setup the maintenance object
     */
    @RequestMapping(params = "methodToCall=docHandler")
    public ModelAndView docHandler(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                HttpServletRequest request, HttpServletResponse response) throws Exception {

        final MaintenanceDocumentForm maintenanceDocForm = (MaintenanceDocumentForm) form;

        maintenanceDocForm.setDocTypeName(COURSE_CREATE_DOC_TYPE_NAME);
        maintenanceDocForm.setDataObjectClassName(CourseInfo.class.getName());

        try {
            redrawDecisionTable(maintenanceDocForm);
        }
        catch (Exception e) {
            error("Unable to create decision table: %s", e.getMessage());
        }

        // Create the document in the super method
        final ModelAndView retval = super.docHandler(maintenanceDocForm, result, request, response);

        final CourseInfoMaintainable maintainable = getCourseMaintainableFrom(maintenanceDocForm);

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) maintenanceDocForm.getDocument().getNewMaintainableObject().getDataObject();

        // We can actually get this from the workflow document initiator id. It doesn't need to be stored in the form.
        courseInfoWrapper.setUserId(ContextUtils.getContextInfo().getPrincipalId());

        // Initialize Course Requisites
        final CourseRuleManagementWrapper ruleWrapper = maintainable.getCourseRuleManagementWrapper();
        ruleWrapper.setNamespace(KSKRMSServiceConstants.NAMESPACE_CODE);

        ruleWrapper.setRefDiscriminatorType(CourseServiceConstants.REF_OBJECT_URI_COURSE);
        ruleWrapper.setRefObjectId(courseInfoWrapper.getCourseInfo().getId());

        ruleWrapper.setAgendas(maintainable.getAgendasForRef(ruleWrapper.getRefDiscriminatorType(), ruleWrapper.getRefObjectId()));

        if (KewApiConstants.INITIATE_COMMAND.equals(maintenanceDocForm.getCommand())) {

            // After creating the document, modify the state
            courseInfoWrapper.getCourseInfo().setStateKey(DtoConstants.STATE_DRAFT);
            courseInfoWrapper.setLastUpdated(DateFormatters.SIMPLE_TIMESTAMP_FORMATTER.format(new DateTime()));
            courseInfoWrapper.getCourseInfo().setEffectiveDate(new java.util.Date());

            courseInfoWrapper.getCourseInfo().setTypeKey(CREDIT_COURSE_CLU_TYPE_KEY);

            // Initialize Curriculum Oversight if it hasn't already been.
            if (courseInfoWrapper.getCourseInfo().getUnitsContentOwner() == null) {
                courseInfoWrapper.getCourseInfo().setUnitsContentOwner(new ArrayList<String>());
            }

        }
        else if (ArrayUtils.contains(DOCUMENT_LOAD_COMMANDS, maintenanceDocForm.getCommand()) && maintenanceDocForm.getDocId() != null) {
            ProposalInfo proposal = null;
            try {
                proposal = getProposalService().getProposalByWorkflowId(maintenanceDocForm.getDocument().getDocumentHeader().getDocumentNumber(), ContextUtils.getContextInfo());
                courseInfoWrapper.setProposalInfo(proposal);
            }
            catch (Exception e) {
                warn("Unable to retrieve the proposal: %s", e.getMessage());
            }
        }

        return retval;
    }

    /**
     * load the course proposal review page
     */
    @RequestMapping(params = "methodToCall=reviewCourseProposal")
    public ModelAndView reviewCourseProposal(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        return getUIFModelAndView(form, "KS-CourseView-ReviewProposalPage");
    }

    /**
     * load the course proposal review page
     */
    @RequestMapping(params = "methodToCall=editCourseProposalPage")
    public ModelAndView editCourseProposalPage(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        String displaySectionIdex = form.getActionParameters().get("displaySection");

        if (StringUtils.isBlank(displaySectionIdex)){
            ((CreateCourseForm)form).setSelectedTabIndex(0);
        }  else {
            Integer idx = getSectionOrdinal(displaySectionIdex);
            ((CreateCourseForm)form).setSelectedTabIndex(idx);
        }
        return getUIFModelAndView(form, "KS-CourseView-CoursePage");
    }

    /**
     * After the Craete course initial data is filled call the method to show the navigation panel and
     * setup the maintenance object
     */
    @RequestMapping(params = "methodToCall=continueCreateCourse")
    public ModelAndView continueCreateCourse(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        final CreateCourseForm maintenanceDocForm = (CreateCourseForm) form;


        /**
         * If the user is CS, then make sure the user checked the 'Curriculum Review process'.
         * If checked, create an admin document. Otherwise, create a regular proposal create
         * document.
         */
        if (maintenanceDocForm.isCurriculumSpecialist() && !maintenanceDocForm.isUseReviewProcess()){
            maintenanceDocForm.setDocTypeName(CLUConstants.PROPOSAL_TYPE_COURSE_CREATE_ADMIN);
            super.docHandler(maintenanceDocForm, result, request, response);
            final CourseInfoMaintainable maintainable = getCourseMaintainableFrom(maintenanceDocForm);
            CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) maintenanceDocForm.getDocument().getNewMaintainableObject().getDataObject();
            // After creating the document, modify the state
            courseInfoWrapper.getCourseInfo().setStateKey(DtoConstants.STATE_DRAFT);
            courseInfoWrapper.setLastUpdated(DateFormatters.SIMPLE_TIMESTAMP_FORMATTER.format(new DateTime()));
            courseInfoWrapper.getCourseInfo().setEffectiveDate(new java.util.Date());

            courseInfoWrapper.getCourseInfo().setTypeKey(CREDIT_COURSE_CLU_TYPE_KEY);

            // Initialize Curriculum Oversight if it hasn't already been.
            if (courseInfoWrapper.getCourseInfo().getUnitsContentOwner() == null) {
                courseInfoWrapper.getCourseInfo().setUnitsContentOwner(new ArrayList<String>());
            }
        }

        try {
            redrawDecisionTable(maintenanceDocForm);
        }
        catch (Exception e) {
            error("Unable to create decision table: %s", e.getMessage());
        }

        final CourseInfoMaintainable maintainable = getCourseMaintainableFrom(maintenanceDocForm);

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) maintenanceDocForm.getDocument().getNewMaintainableObject().getDataObject();


        CourseInfo coInfo = courseInfoWrapper.getCourseInfo();

        // We can actually get this from the workflow document initiator id. It doesn't need to be stored in the form.
        courseInfoWrapper.setUserId(ContextUtils.getContextInfo().getPrincipalId());

        // Initialize Course Requisites
        final CourseRuleManagementWrapper ruleWrapper = maintainable.getCourseRuleManagementWrapper();
        ruleWrapper.setNamespace(KSKRMSServiceConstants.NAMESPACE_CODE);

        ruleWrapper.setRefDiscriminatorType(CourseServiceConstants.REF_OBJECT_URI_COURSE);
        ruleWrapper.setRefObjectId(coInfo.getId());

        ruleWrapper.setAgendas(maintainable.getAgendasForRef(ruleWrapper.getRefDiscriminatorType(), ruleWrapper.getRefObjectId()));

        return getUIFModelAndView(form, "KS-CourseView-CoursePage");
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
        }
        catch (Exception e) {
            warn("Failed to get binary data: %s", e.getMessage());
        }

        try {
            getSupportingDocumentService().createDocument("documentType.doc", "documentCategory.proposal", toAdd, ContextUtils.getContextInfo());
        }
        catch (Exception e) {
            warn("Unable to create a document: %s", e.getMessage());
        }

        // Now relate the document to the course
        RefDocRelationInfo docRelation = new RefDocRelationInfo();
        try {
            getSupportingDocumentService().createRefDocRelation("kuali.lu.type.CreditCourse",
                    courseInfoWrapper.getCourseInfo().getId(),
                                                      toAdd.getId(),
                                                      "kuali.org.DocRelation.allObjectTypes",
                                                      docRelation,
                                                      ContextUtils.getContextInfo());
        }
        catch (Exception e) {
            warn("Unable to relate a document with the course: %s", e.getMessage());
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
            warn("Unable to delete document: %s, reason: %s", toRemove.getId(), e.getMessage());
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

        form.setDocTypeName(COURSE_MODIFY_DOC_TYPE_NAME);
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
        form.setDocTypeName(COURSE_RETIRE_DOC_TYPE_NAME);
        form.setDataObjectClassName(CourseInfo.class.getName());
        final ModelAndView retval = super.docHandler(form, result, request, response);

        return retval;
    }

    /**
     * This will save the Course Proposal.
     * @param form {@link MaintenanceDocumentForm} instance used for this action
     * @param result
     * @param request {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @return The new {@link ModelAndView} that contains the newly created/updated {@CourseInfo} and {@ProposalInfo} information.
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=saveProposal")
    public ModelAndView saveProposal(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        final CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);
        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        
        //Clear collection fields (those with matching 'wrapper' collections)
        courseInfoWrapper.getCourseInfo().getJoints().clear();
        courseInfoWrapper.getCourseInfo().getInstructors().clear();
        courseInfoWrapper.getCourseInfo().getUnitsDeployment().clear();
        courseInfoWrapper.getCourseInfo().getCourseSpecificLOs().clear();
        
        //Retrieve the collection display values and get the fully loaded object (containing all the IDs and related IDs)
        if (courseInfoWrapper.getCourseJointWrappers() != null) {
            for (final CourseJointInfoWrapper jointInfoDisplay : courseInfoWrapper.getCourseJointWrappers()) {
                courseInfoWrapper.getCourseInfo().getJoints().add(CourseCodeSearchUtil.getCourseJointInfoWrapper(jointInfoDisplay.getCourseCode(), getCluService()));
            }
        }
        
        if (courseInfoWrapper.getInstructorWrappers() != null) {
            for (final CluInstructorInfoWrapper instructorDisplay : courseInfoWrapper.getInstructorWrappers()) {
                final CluInstructorInfoWrapper retrievedInstructor = getCourseMaintainableFrom(form).getInstructor(getInstructorSearchString(instructorDisplay.getDisplayName()));
                courseInfoWrapper.getCourseInfo().getInstructors().add(retrievedInstructor);
            }
        }

        if (courseInfoWrapper.getAdministeringOrganizations() != null) {
            for (final OrganizationInfoWrapper org : courseInfoWrapper.getAdministeringOrganizations()) {
                courseInfoWrapper.getCourseInfo().getUnitsDeployment().add(org.getOrganizationName());
            }
        }
        
        if (courseInfoWrapper.getLoDisplayWrapperModel() != null && courseInfoWrapper.getLoDisplayWrapperModel().getLoWrappers() != null) {
            List<LoDisplayInfoWrapper> loWrappers = courseInfoWrapper.getLoDisplayWrapperModel().getLoWrappers();
            List<LoDisplayInfo> courseLos = courseInfoWrapper.getCourseInfo().getCourseSpecificLOs();
            for (int i = 0; i < loWrappers.size(); i++) {
                
                LoDisplayInfoWrapper currentLo = loWrappers.get(i);
                
                boolean rootLevel = true;
                int parentIndex = i - 1;
                while (parentIndex >= 0) {
                    LoDisplayInfoWrapper potentialParent = loWrappers.get(parentIndex);
                    boolean parentMatch = currentLo.getIndentLevel() > potentialParent.getIndentLevel();
                    if (parentMatch) {
                        // TODO: Implement this on the 'Find Course' functionality
                        //currentLo.setParentLoRelationid(potentialParent.getLoInfo().getId());
                        //currentLo.setParentRelType(CourseAssemblerConstants.COURSE_LO_RELATION_INCLUDES);
                        potentialParent.getLoDisplayInfoList().add(currentLo);
                        
                        rootLevel = false;
                        break;
                    } else {
                        parentIndex--;
                    }
                }
                
                if (rootLevel) {
                    courseLos.add(currentLo);
                }
            }
        }
        
        // Set derived course fields before saving/updating
        courseInfoWrapper.setCourseInfo(calculateCourseDerivedFields(courseInfoWrapper.getCourseInfo()));
        courseInfoWrapper.setLastUpdated(DateFormatters.SIMPLE_TIMESTAMP_FORMATTER.format(new DateTime()));

        courseInfoWrapper.getCourseInfo().setUnitsContentOwner(new ArrayList<String>());
        for (final KeyValue wrapper : courseInfoWrapper.getUnitsContentOwner()) {
            courseInfoWrapper.getCourseInfo().getUnitsContentOwner().add(wrapper.getValue());
        }

        form.getDocument().getDocumentHeader().setDocumentDescription(courseInfoWrapper.getProposalInfo().getName());

        //Formats
        for (FormatInfo format : courseInfoWrapper.getCourseInfo().getFormats()){
            if (StringUtils.isBlank(format.getId())){ // If it's new
                format.setState(DtoConstants.STATE_DRAFT);
                if (StringUtils.isBlank(format.getTypeKey())){
                    format.setTypeKey(CluServiceConstants.COURSE_FORMAT_TYPE_KEY);
                }
            }
            for (ActivityInfo activity : format.getActivities()){
                if (StringUtils.isBlank(activity.getId())){ // If it's new
                    activity.setState(DtoConstants.STATE_DRAFT);
                }
            }
        }

        try {
//            if (form.getDocument().getDocumentHeader().getWorkflowDocument().isInitiated()) {
                handleFirstTimeSave(form);
//            }
            save(form, result, request, response);
        }
        catch (Exception e) {
            error("Unable to save document: %s", e.getMessage());
        }

        RecentlyViewedDocsUtil.addRecentDoc(form.getDocument().getDocumentHeader().getDocumentDescription(), form.getDocument().getDocumentHeader().getWorkflowDocument().getDocumentHandlerUrl() + "&docId=" + form.getDocument().getDocumentHeader().getWorkflowDocument().getDocumentId());

        // After saving successfully update the reviewInfo
        updateReview(form);
        String nextOrCurrentPage = form.getActionParameters().get("displayPage");
        if (StringUtils.equalsIgnoreCase(nextOrCurrentPage,"NEXT")){
            if ( ((CreateCourseForm)form).getSelectedTabIndex() < 10){
                ((CreateCourseForm)form).setSelectedTabIndex(((CreateCourseForm) form).getSelectedTabIndex() + 1);
            }
//            return getUIFModelAndView(form, getNextSectionId(request.getParameter(VIEW_CURRENT_PAGE_ID)));
            return getUIFModelAndView(form);
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
    protected void updateReview(final DocumentFormBase form) {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();
        // Update course info
        final ReviewInfo reviewData = courseInfoWrapper.getReviewInfo();
        reviewData.getCourseInfo().setCourseTitle(courseInfoWrapper.getCourseInfo().getCourseTitle());
        reviewData.getCourseInfo().setProposalName(courseInfoWrapper.getProposalInfo().getName());
        reviewData.getCourseInfo().setTranscriptTitle(courseInfoWrapper.getCourseInfo().getTranscriptTitle());
        reviewData.getCourseInfo().setSubjectArea(courseInfoWrapper.getCourseInfo().getSubjectArea());
        reviewData.getCourseInfo().setCourseNumberSuffix(courseInfoWrapper.getCourseInfo().getCourseNumberSuffix());

        // Update governance info
        reviewData.getGovernanceInfo().getCampusLocations().clear();
        reviewData.getGovernanceInfo().getCampusLocations().addAll(courseInfoWrapper.getCourseInfo().getCampusLocations());
        reviewData.getGovernanceInfo().getCurriculumOversight().clear();
        reviewData.getGovernanceInfo().getCurriculumOversight().addAll(courseInfoWrapper.getCourseInfo().getUnitsContentOwner());
    }


    /**
     * Handles functionality that should only happen when the document is first saved.
     *
     * @param form {@link MaintenanceDocumentForm} instance
     */
    protected void handleFirstTimeSave(final MaintenanceDocumentForm form) throws Exception {
        final CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject();

        final CourseInfo course = courseInfoWrapper.getCourseInfo();
        for (final CourseVariationInfo variation : course.getVariations()) {
            variation.setTypeKey("kuali.lu.type.Variation");
        }
        if (StringUtils.isBlank(course.getId())){
            courseInfoWrapper.setCourseInfo(getCourseService().createCourse(course, ContextUtils.getContextInfo()));
        } else {
            courseInfoWrapper.setCourseInfo(getCourseService().updateCourse(course.getId(), course, ContextUtils.getContextInfo()));
        }

        info("Saving Proposal for course %s", courseInfoWrapper.getCourseInfo().getId());

        ProposalInfo proposal = courseInfoWrapper.getProposalInfo();
        proposal.setWorkflowId(form.getDocument().getDocumentHeader().getDocumentNumber());
        if (StringUtils.isBlank(proposal.getId())){
            proposal.setState(ProposalConstants.PROPOSAL_STATE_SAVED);     // remove proposal constant, try to use KualiStudentPostProcessorBase
            proposal.setType(ProposalServiceConstants.PROPOSAL_TYPE_COURSE_CREATE_KEY);
            proposal.setProposalReferenceType("kuali.proposal.referenceType.clu");
            proposal.getProposalReference().add(courseInfoWrapper.getCourseInfo().getId());
            proposal.getProposerOrg().clear();
            proposal.getProposerPerson().clear();
        }
                        
        try {
            if (StringUtils.isBlank(proposal.getId())){
                proposal = getProposalService().createProposal(ProposalServiceConstants.PROPOSAL_TYPE_COURSE_CREATE_KEY, proposal, ContextUtils.getContextInfo());
            } else {
                proposal = getProposalService().updateProposal(proposal.getId(), proposal, ContextUtils.getContextInfo());
            }
            courseInfoWrapper.setProposalInfo(proposal);
        }
        catch (Exception e) {
            warn("Unable to create a proposal: %s", e.getMessage());
        }
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
                error("Error creating a new comment. %s", e);
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
            redrawDecisionTable(form);
            return showDialog(DECISIONS_DIALOG_KEY, form, request, response);
        }

        form.getDialogManager().removeDialog(DECISIONS_DIALOG_KEY);
        
        return getUIFModelAndView(form);
    }

    /**
     *
     * @param form the {@link MaintenanceDocumentForm} instance where the decisions are to be stored for the lightbox.
     * @throws InvalidParameterException when incorrect parameters are used for looking up the comments made
     * @throws MissingParameterException when null or empty parameters are used for looking up the comments made
     * @throws OperationFailedException when it cannot be determined what comments were made.
     * @throws PermissionDeniedException when the user doesn't have rights to look up comments.
     */
    protected void redrawDecisionTable(final MaintenanceDocumentForm form) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CommentInfo> commentInfos = null;
        try {
            commentInfos = getCommentService()
                .getCommentsByReferenceAndType("temp_reference_id",
                                               "referenceType.clu.proposal",
                                               ContextUtils.getContextInfo());
        }
        catch (DoesNotExistException e) {
            // Add a dummy row
            // form.getDecisions().add(new DecisionInfo());

            // If there are no comments, don't go any further
            return;
        }

        // Collect person ids to search 
		final List<String> personIds = new ArrayList<String>();
		for (CommentInfo comment : commentInfos) {
			if(comment.getMeta().getCreateId()!=null){
				personIds.add(comment.getMeta().getCreateId());
			}
			else{
				personIds.add("");
			}
		}

        final Map<String, MembershipInfo> members = getNamesForPersonIds(personIds);

        redrawDecisionTable(form, commentInfos, members);
    }
    
    /**
     * Responsible for rebuilding/reloading content within the decision view's HTML table. 
     *
     * @param form is the {@link MaintenanceDocumentForm} for the request
     * @param commentInfos {@link List} of {@link CommentInfo} instances that currently exist. 
     * These are comments for decisions that represent our rationale
     * @param members {@link Map} of {@link MembershipInfo} instances mapped by commenter ids. 
     * Commenter ids are supplied in the {@link CommentInfo} instances. The {@link MembershipInfo} 
     * is used to determine who is responsible for the decision.
     * @see {@link #getNamesForPersonIds(List)} for the method that typically populates the members parameter.
     */
	protected void redrawDecisionTable(final MaintenanceDocumentForm form,
                                       final List<CommentInfo> commentInfos,
                                       final Map<String, MembershipInfo> members) {
	    
        final CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);
        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        
        //final DateTimeFormatter dateFormat = DateTimeFormat.forPattern("MM/dd/yyyy");
        final KSDateTimeFormatter dateFormat = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER;

		if (commentInfos != null) {
			for (final CommentInfo commentInfo : commentInfos) {
			    /* we only want decision rationale comments so if no DecisionRationaleDetail is returned for comment
                 * type then don't add that comment to the table
                 */
			    final DecisionRationaleDetail drDetails = DecisionRationaleDetail.getByType(commentInfo.getTypeKey());
			    if (drDetails != null) {
                    final DecisionInfo decision = new DecisionInfo();
                    decision.setDecision(drDetails.getLabel());
                    decision.setId(commentInfo.getId());

                    final String rationaleDate = dateFormat.format(new DateTime(commentInfo.getMeta().getCreateTime().getTime()));
    				decision.setDate(rationaleDate);
    
    				if (members.get(commentInfo.getMeta().getCreateId()) != null) {
    					final MembershipInfo memberInfo = members.get(commentInfo.getMeta().getCreateId());
    					final StringBuilder memberName = new StringBuilder();
    					memberName.append(memberInfo.getFirstName());
    					memberName.append(" ");
    					memberName.append(memberInfo.getLastName());

    					decision.setActor(memberName.toString());
    				}
    				decision.setRationale(commentInfo.getCommentText().getPlain());
                    courseInfoWrapper.getDecisions().add(decision);
			    }
			}
		}
	}

    /**
     * Retrieve {@link MembershipInfo} instances populated with first and last names using {@link EntityDefault}
     * instances of the personIds
     *
     * @param personIds {@link List} of ids used to get {@link EntityDefault}s with first and last names
     * @param {@link Map} of {@link MembershipInfo} instances with first and last names mapped to each personId
     */
    protected Map<String, MembershipInfo> getNamesForPersonIds(final List<String> personIds) {
        final Map<String, MembershipInfo> identities = new HashMap<String, MembershipInfo>();
        for (String pId : personIds ){
            final EntityDefault entity = getIdentityService().getEntityDefaultByPrincipalId(pId);
            final MembershipInfo memeberEntity = new MembershipInfo();
            memeberEntity.setFirstName(entity.getName().getFirstName());
            memeberEntity.setLastName(entity.getName().getLastName());
            identities.put(pId, memeberEntity);
        }
        return identities;
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
    @RequestMapping(params="methodToCall=addUnitsContentOwner")
    protected ModelAndView addUnitsContentOwner(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form, 
                                                final BindingResult result,
                                                final HttpServletRequest request,
                                                final HttpServletResponse response) {
        info("Adding a unitsContentOwner");
        final CourseInfoMaintainable maintainable = (CourseInfoMaintainable) form.getDocument().getNewMaintainableObject();
        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        if (courseInfoWrapper.getUnitsContentOwnerToAdd() == null) {
            info("Units Content Owner is null");
            return getUIFModelAndView(form);
        }

        final String toAdd = courseInfoWrapper.getUnitsContentOwnerToAdd();
        info("Adding ", toAdd);
        courseInfoWrapper.getUnitsContentOwner().add(getOrganizationBy(courseInfoWrapper.getCourseInfo().getSubjectArea(), toAdd));
        courseInfoWrapper.setUnitsContentOwnerToAdd("");

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
        debug("Using code: %s and orgId: %s for the search", code, orgId);
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
        	error("Error building KeyValues List %s", e);
            throw new RuntimeException(e);
        }
        
        info("Returning a null from org search");
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

        updateReview((DocumentFormBase) form);

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
}
