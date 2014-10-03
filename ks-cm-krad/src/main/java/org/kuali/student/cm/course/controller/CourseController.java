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

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.krad.rules.rule.event.RouteDocumentEvent;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.cm.common.util.CMUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.wrapper.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.CourseCreateUnitsContentOwner;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.LoDisplayInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.LoDisplayWrapperModel;
import org.kuali.student.cm.course.form.wrapper.ResultValuesGroupInfoWrapper;
import org.kuali.student.cm.course.service.CourseMaintainable;
import org.kuali.student.cm.course.service.ExportCourseHelper;
import org.kuali.student.cm.course.service.impl.ExportCourseHelperImpl;
import org.kuali.student.cm.course.util.CourseProposalUtil;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.cm.proposal.util.ProposalUtil;
import org.kuali.student.common.object.KSObjectUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.util.AttributeHelper;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.CourseVariationInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Handles all the requests from the Add/Edit a Course Proposal UI.
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
         * Specifies that this proposal should copy from the CLU with the given Id.
         */
        public static final String COPY_CLU_ID = "copyCluId";
        /**
         * Specifies that this proposal should copy from the ProposalInfo with the given Id.
         */
        public static final String COPY_PROPOSAL_ID = "copyProposalId";

        public static final String MODIFY_ACTION = "modifyAction";

        private UrlParams() {}  /* Constants class. Hide the contructor. */
    }

    private CourseService courseService;
    private SubjectCodeService subjectCodeService;

    protected String getDocumentTypeNameForProposalStart(Boolean isUseReviewProcess, HttpServletRequest request) {
        String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);

        // set the doc type name based on the whether the user is CS and if they have chosen to use curriculum review
        if (StringUtils.equals(methodToCall,CurriculumManagementConstants.ModifyCourseStartOptions.MODIFY_WITH_A_NEW_VERSION)){
            // throw an exception if the user is not a CS user but attempts to disable Curriculum Review for a proposal
            verifyCurriculumReviewOverride(isUseReviewProcess, CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_MODIFY_ADMIN);
            return ((!isUseReviewProcess)
                    ? CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_MODIFY_ADMIN
                    : CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_MODIFY);
        } else if (StringUtils.equals(methodToCall,CurriculumManagementConstants.ModifyCourseStartOptions.MODIFY_THIS_VERSION)){
            // no need to check for curriculum review override access because curriculum review doesn't apply to modify no version
            return CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_MODIFY_ADMIN_NOVERSION;
        } else {
            // throw an exception if the user is not a CS user but attempts to disable Curriculum Review for a proposal
            verifyCurriculumReviewOverride(isUseReviewProcess, CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_CREATE_ADMIN);
            return ((!isUseReviewProcess)
                    ? CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_CREATE_ADMIN
                    : CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_CREATE);
        }
    }

    @Override
    protected void updateFormElements(DocumentFormBase form) {
        super.updateFormElements(form);
        CourseInfoWrapper wrapper = getCourseInfoWrapper(form);
        if (wrapper.isProposalDataUsed() && StringUtils.equals(form.getDocTypeName(),
                CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_MODIFY_ADMIN_NOVERSION)) {
            wrapper.getUiHelper().setModifyWithoutNewVersionProposal(true);
        }
    }

    protected void verifyCurriculumReviewOverride(Boolean isUseReviewProcess, String documentTypeName) {
        if (!isUseReviewProcess && !ProposalUtil.isUserCurriculumSpecialist(documentTypeName)) {
            throw new RuntimeException(String.format("User (%s) is not allowed to disable Curriculum Review (Workflow Approval).",
                    GlobalVariables.getUserSession().getPerson().getPrincipalName()));
        }
    }

    protected CurriculumManagementConstants.UserInterfaceSections getNextSection(CurriculumManagementConstants.UserInterfaceSections selectedSection) {
        CurriculumManagementConstants.CourseViewSections currentSection = (CurriculumManagementConstants.CourseViewSections) selectedSection;
        int ordinal = currentSection.ordinal();
        ordinal = ((ordinal + 1) % 10);     // there are 10 CourseViewSections avilable hence '%' with 10.
        if (currentSection.ordinal() < CurriculumManagementConstants.CourseViewSections.values().length) {
            return CurriculumManagementConstants.CourseViewSections.values()[ordinal];
        }
        // cannot find valid section
        return null;
    }

    protected String getReviewProposalLinkBeanId() {
        return "CM-Proposal-Course-View-ReviewProposalLink";
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
     *
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=modifyThisVersion" )
    public ModelAndView modifyThisVersion(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        super.createDocument(form);

        String courseId = request.getParameter(CurriculumManagementConstants.UrlParams.CLU_ID);

        CourseInfoWrapper courseInfoWrapper = new CourseInfoWrapper();
        courseInfoWrapper.setDisableCourseDefaulting(true);
        CourseMaintainable newMaintainble = (CourseMaintainable) form.getDocument().getNewMaintainableObject();
        newMaintainble.setDataObject(courseInfoWrapper);
        courseInfoWrapper.getUiHelper().setModifyWithoutNewVersionProposal(true);
        newMaintainble.populateCourseAndReviewData(courseId, courseInfoWrapper);
        setupMaintenance(form, request, KRADConstants.MAINTENANCE_NEW_ACTION);
        form.getDocument().getDocumentHeader().setDocumentDescription("Admin Modify: " + courseInfoWrapper.getCourseInfo().getCourseTitle());

        courseInfoWrapper.getProposalInfo().setName(courseInfoWrapper.getCourseInfo().getCourseTitle());

        // If the course state is a superseded which has been previously retired
        if (DtoConstants.STATE_SUPERSEDED.equals(courseInfoWrapper.getCourseInfo().getStateKey()) &&
            courseInfoWrapper.getCourseInfo().getAttributeValue(CurriculumManagementConstants.COURSE_ATTRIBUTE_RETIREMENT_RATIONALE) != null) {

            courseInfoWrapper.setSupersededCourseFormerlyRetired(true);
            String retirementCommentString = courseInfoWrapper.getCourseInfo().getAttributeValue(CurriculumManagementConstants.COURSE_ATTRIBUTE_RETIREMENT_COMMENT);
            RichTextInfo retirementComment = new RichTextInfo();
            retirementComment.setPlain(retirementCommentString);
            retirementComment.setFormatted(retirementCommentString);
            courseInfoWrapper.setRetirementComment(retirementComment);
        }

        return getUIFModelAndView(form);
    }

    /**
     *
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=completeModifyThisVersion" )
    public ModelAndView completeModifyThisVersion(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseInfoWrapper wrapper = getCourseInfoWrapper(form);

        // TODO KSCM-2836 -- validations fire if these proposal fields aren't filled out even thought we don't use proposalInfo on this document
        if (wrapper.getProposalInfo() == null) {
            wrapper.setProposalInfo(new ProposalInfo());
        }
        if (wrapper.getProposalInfo().getRationale() == null) {
            wrapper.getProposalInfo().setRationale(new RichTextInfo());
        }
        wrapper.getProposalInfo().getRationale().setFormatted("dummy");
        wrapper.getProposalInfo().getRationale().setPlain("dummy");
        wrapper.getProposalInfo().setName(wrapper.getCourseInfo().getCourseTitle());

        if (wrapper.getCourseInfo().getStateKey().equals(DtoConstants.STATE_RETIRED)) {
            CourseProposalUtil.addOrUpdateAttributes(wrapper.getCourseInfo().getAttributes(), CurriculumManagementConstants.COURSE_ATTRIBUTE_LAST_TERM_OFFERED, wrapper.getLastTerm());
            CourseProposalUtil.addOrUpdateAttributes(wrapper.getCourseInfo().getAttributes(), CurriculumManagementConstants.COURSE_ATTRIBUTE_RETIREMENT_RATIONALE, wrapper.getRetirementRationale());
        } else if (wrapper.getCourseInfo().getStateKey().equals(DtoConstants.STATE_SUPERSEDED) && wrapper.isSupersededCourseFormerlyRetired()) {
            CourseProposalUtil.addOrUpdateAttributes(wrapper.getCourseInfo().getAttributes(), CurriculumManagementConstants.COURSE_ATTRIBUTE_LAST_TERM_OFFERED, wrapper.getLastTerm());
            CourseProposalUtil.addOrUpdateAttributes(wrapper.getCourseInfo().getAttributes(), CurriculumManagementConstants.COURSE_ATTRIBUTE_RETIREMENT_RATIONALE, wrapper.getRetirementRationale());
            CourseProposalUtil.addOrUpdateAttributes(wrapper.getCourseInfo().getAttributes(), CurriculumManagementConstants.COURSE_ATTRIBUTE_LAST_PUBLICATION_YEAR, wrapper.getPublicationYear());
            CourseProposalUtil.addOrUpdateAttributes(wrapper.getCourseInfo().getAttributes(), CurriculumManagementConstants.COURSE_ATTRIBUTE_RETIREMENT_COMMENT, wrapper.getRetirementComment().getPlain());
        }
//        doValidationForProposal(form, KewApiConstants.ROUTE_HEADER_PROCESSED_CD, null);
        // manually call the view validation service as this validation cannot be run client-side in current setup
        KRADServiceLocatorWeb.getViewValidationService().validateView(form, KewApiConstants.ROUTE_HEADER_PROCESSED_CD);
        //Perform Rules validation
        KRADServiceLocatorWeb.getKualiRuleService().applyRules(new RouteDocumentEvent(form.getDocument()));

        if (!GlobalVariables.getMessageMap().hasErrors()) {
            // cannot call super class method as the redirect methods are causing an error when loading indicator is used after dialog confirmation is clicked
            performWorkflowAction(form, UifConstants.WorkflowAction.BLANKETAPPROVE, true);

            //redirect back to course view page
            return performRedirect(form, CourseProposalUtil.getViewCourseUrl(wrapper.getCourseInfo().getId()));
        } else {
            return getUIFModelAndView(form);
        }
    }

    /**
     *
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=modifyNewVersion" )
    public ModelAndView modifyNewVersion(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {

        super.createDocument(form);

        String versionIndId = request.getParameter(CurriculumManagementConstants.UrlParams.VERSION_IND_ID);

        CourseInfo courseInfo = getCourseService().createNewCourseVersion(versionIndId,"", ContextUtils.createDefaultContextInfo());
        courseInfo.setCourseTitle("Modify: " + courseInfo.getCourseTitle());
        List<AttributeInfo> attributes = courseInfo.getAttributes();
        // kscm-2844: When a new version of a retired course is created, these attributes need to be removed.
        for (Iterator<AttributeInfo> it = attributes.iterator(); it.hasNext();) {
            AttributeInfo attributeInfo = it.next();
            if (attributeInfo.getKey().equals(CurriculumManagementConstants.COURSE_ATTRIBUTE_LAST_TERM_OFFERED)
                    || attributeInfo.getKey().equals(CurriculumManagementConstants.COURSE_ATTRIBUTE_LAST_PUBLICATION_YEAR)
                    || attributeInfo.getKey().equals(CurriculumManagementConstants.COURSE_ATTRIBUTE_RETIREMENT_COMMENT)
                    || attributeInfo.getKey().equals(CurriculumManagementConstants.COURSE_ATTRIBUTE_RETIREMENT_RATIONALE)
                    ) {
                it.remove();
            }
        }
        CourseInfoWrapper courseInfoWrapper = new CourseInfoWrapper();
        courseInfoWrapper.setCourseInfo(courseInfo);
        CourseMaintainable newMaintainble = (CourseMaintainable)form.getDocument().getNewMaintainableObject();
        newMaintainble.setDataObject(courseInfoWrapper);
        newMaintainble.populateCourseAndReviewData(courseInfo.getId(),courseInfoWrapper);

        ProposalInfo proposalInfo = new ProposalInfo();
        courseInfoWrapper.setProposalInfo(proposalInfo);
        proposalInfo.setName(courseInfo.getCourseTitle());

        //  set the Curriculum review status on the uiHelper
        courseInfoWrapper.getUiHelper().setUseReviewProcess(
                request.getParameter(CurriculumManagementConstants.UrlParams.USE_CURRICULUM_REVIEW).equals(Boolean.TRUE.toString()));

        // Get the current version requisities and clear out all the ids so that requisites will be created new for this proposal.
        CourseInfo currentVersion = CourseProposalUtil.getCurrentVersionOfCourse(versionIndId,ContextUtils.createDefaultContextInfo());
        newMaintainble.populateRequisities(courseInfoWrapper,currentVersion.getId());
        newMaintainble.getCourseCopyHelper().resetRequisites(courseInfoWrapper);

        Properties urlParameters = new Properties();

        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "saveModifyVersion");
        urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, CMUtils.getCMHomeUrl());
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseInfoWrapper.class.getName());
        urlParameters.put(KRADConstants.FORM_KEY, form.getFormKey());
        String courseBaseUrl = CurriculumManagementConstants.ControllerRequestMappings.COURSE_MAINTENANCE.replaceFirst("/", "");

        return performRedirect(form, courseBaseUrl, urlParameters);
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
                    ProposalElementsWrapper target = viewHelper.copyProposal(copyProposalId);
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

        //  set the Curriculum review status on the uiHelper
        ((CourseInfoWrapper) viewHelper.getDataObject()).getUiHelper().setUseReviewProcess(
                request.getParameter(CurriculumManagementConstants.UrlParams.USE_CURRICULUM_REVIEW).equals(Boolean.TRUE.toString()));
        return getUIFModelAndView(form);
    }

    protected String getDataObjectClassName() {
        return CourseInfoWrapper.class.getCanonicalName();
    }

    /**
     * Load the course proposal review page
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=reviewCourseProposal")
    public ModelAndView reviewCourseProposal(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        MaintenanceDocumentForm maintForm = (MaintenanceDocumentForm)form;
        CourseInfoWrapper wrapper = getCourseInfoWrapper(form);

        ((CourseMaintainable) maintForm.getDocument().getNewMaintainableObject()).updateReview();

        if (ArrayUtils.contains(CurriculumManagementConstants.DocumentTypeNames.COURSE_MODIFY_DOC_TYPE_NAMES, maintForm.getDocTypeName()) &&
                !maintForm.getWorkflowDocument().isApproved()){

            CourseInfoWrapper compareCourseWrapper = new CourseInfoWrapper();
            CourseMaintainable oldMaintainble = (CourseMaintainable)((MaintenanceDocumentForm)form).getDocument().getOldMaintainableObject();
            CourseInfo currentVersion = CourseProposalUtil.getCurrentVersionOfCourse(wrapper.getCourseInfo().getVersion().getVersionIndId(),ContextUtils.createDefaultContextInfo());
            oldMaintainble.setDataObject(compareCourseWrapper);
            oldMaintainble.populateCourseAndReviewData(currentVersion.getId(),compareCourseWrapper);
            compareCourseWrapper.setVersionText("Original Course");
            wrapper.setVersionText("Proposal");

            ((CourseMaintainable) maintForm.getDocument().getOldMaintainableObject()).balanceCollectionsForCompare(wrapper,compareCourseWrapper);

        }

        //  Validate
        KRADServiceLocatorWeb.getViewValidationService().validateViewAgainstNextState(form);
        if (GlobalVariables.getMessageMap().hasErrors()) {
            wrapper.setMissingRequiredFields(true);
        } else
        {
            wrapper.setMissingRequiredFields(false);
        }

        return getUIFModelAndView(form, getReviewPageKradPageId());
    }

    /**
     * Returns the KRAD pageId that will be used for the Review Page display
     */
    protected String getReviewPageKradPageId() {
        return CurriculumManagementConstants.CoursePageIds.REVIEW_COURSE_PROPOSAL_PAGE;
    }

    protected String getBaseUrlForProposal() {
        return CurriculumManagementConstants.ControllerRequestMappings.COURSE_MAINTENANCE.replaceFirst("/", "");
    }

    protected String getProposalReviewMethodToCall() {
        return "reviewCourseProposal";
    }

    /**
     * Load the course proposal review page
     */
    @RequestMapping(params = "methodToCall=editProposalPage")
    public ModelAndView editProposalPage(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseInfoWrapper wrapper = getCourseInfoWrapper(form);
        if (wrapper.getInstructorWrappers().size() == 0) {
            wrapper.getInstructorWrappers().add(new CluInstructorInfoWrapper());
        }

        // If it's a compare view, we need to cleanup fake collection objects used for compare
        if (((MaintenanceDocumentForm) form).getDocument().getOldMaintainableObject().getDataObject() != null){
            CourseMaintainable maintainable = (CourseMaintainable)((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject();
            maintainable.cleanUpCompareObjects(wrapper);
        }

        return super.editProposalPage(form, result, request, response);
    }

    protected CurriculumManagementConstants.UserInterfaceSections getSectionById(String sectionId) {
        return CurriculumManagementConstants.CourseViewSections.getSection(sectionId);
    }

    protected CurriculumManagementConstants.UserInterfaceSections getDefaultSectionKradIdForEdit() {
        return CurriculumManagementConstants.CourseViewSections.COURSE_INFO;
    }

    protected String getKradPageIdForEdit() {
        return CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE;
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
     * This is a workaround to bypass the transaction issue involved with 'modifyNewVersion' method here. We're
     * redirecting the user to this method for saving a new modify version. It's not intended to use elsewhere.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=saveModifyVersion")
    public ModelAndView saveModifyVersion(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = saveProposal(form, result, request, response);

        if (GlobalVariables.getMessageMap().hasErrors()) {
            return modelAndView;
        }

        /**
         * After save, if review page needs to display, use redirect to handle the validations.
         */
        if (StringUtils.equals(form.getPageId(),getReviewPageKradPageId())){
            Properties urlParameters = ProposalUtil.getProposalUrlProperties("docHandler", getReviewPageKradPageId(), form.getWorkflowDocument().getDocumentId());
            urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, CMUtils.getCMHomeUrl() );
            urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseInfoWrapper.class.getName());
            String courseBaseUrl = CurriculumManagementConstants.ControllerRequestMappings.COURSE_MAINTENANCE.replaceFirst("/", "");
            ModelAndView mv = performRedirect(form, courseBaseUrl, urlParameters);
            form.setRequestRedirected(false);
            return mv;
        }

        return modelAndView;
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
            return getUIFModelAndView(form, getReviewPageKradPageId());
        } else {
            return getUIFModelAndView(form);
        }

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
        urlParameters.put(CurriculumManagementConstants.UrlParams.USE_CURRICULUM_REVIEW, Boolean.TRUE.toString());
        urlParameters.put(UifConstants.UrlParams.PAGE_ID, CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE);
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_COPY);
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseInfoWrapper.class.getName());
        urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, CMUtils.getCMHomeUrl() );
        urlParameters.put(UrlParams.COPY_PROPOSAL_ID, courseInfoWrapper.getProposalInfo().getId());
        urlParameters.put(KRADConstants.OVERRIDE_KEYS, CourseController.UrlParams.COPY_CLU_ID);

        String courseBaseUrl = CurriculumManagementConstants.ControllerRequestMappings.COURSE_MAINTENANCE.replaceFirst("/", "");


        return performRedirect(form, courseBaseUrl, urlParameters);
    }

    /**
     * Handler for component refresh of the CM-Proposal-Course-ActiveDates-CurrentCourseEndTerm data field.
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=refreshCurrentCourseEndTerm")
    public ModelAndView refreshCurrentCourseEndTerm(@ModelAttribute("KualiForm") DocumentFormBase form) {
        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);
        String startTerm = courseInfoWrapper.getCourseInfo().getStartTerm();
        String versionIndependentId = courseInfoWrapper.getCourseInfo().getVersion().getVersionIndId();

        String endTermShortName = CourseProposalUtil.getEndTerm(startTerm, versionIndependentId, ContextUtils.createDefaultContextInfo()).getShortName();

        courseInfoWrapper.setCurrentCourseEndTermShortName(endTermShortName);

        return getUIFModelAndView(form);
    }

    protected ExportCourseHelper getExportHelper(DocumentFormBase form, CurriculumManagementConstants.Export.FileType exportFileType, boolean useSaveHeaders) {
        CourseInfoWrapper wrapper = getCourseInfoWrapper(form);
        return new ExportCourseHelperImpl(wrapper, exportFileType, useSaveHeaders, true);
    }

    /**
     * Method that will run the Kuali student course service validation.
     *
     * @param form - the form of the current users session
     * @param forcedStudentObjectStateKey - the stateKey that needs to be faked for the CourseService.validationCourse() method (if null, uses state in CourseInfo object)
     */
    protected void runStudentServiceValidation(DocumentFormBase form, String forcedStudentObjectStateKey) {
        List<ValidationResultInfo> errors = Collections.EMPTY_LIST;
        CourseInfoWrapper courseInfoWrapper = getCourseInfoWrapper(form);
        try {
            //Perform Service Layer Data Dictionary validation
            CourseInfo courseInfoToValidate = (CourseInfo) ObjectUtils.deepCopy(courseInfoWrapper.getCourseInfo());
            courseInfoToValidate.getUnitsContentOwner().clear();
            for (CourseCreateUnitsContentOwner wrapper : courseInfoWrapper.getUnitsContentOwner()) {
                courseInfoToValidate.getUnitsContentOwner().add(wrapper.getOrgId());
            }
            List<CourseCrossListingInfo> toDelete = new ArrayList<>();
            for (CourseCrossListingInfo crossListing : courseInfoToValidate.getCrossListings()) {
                if (StringUtils.isBlank(crossListing.getSubjectArea()) && StringUtils.isBlank(crossListing.getCourseNumberSuffix())){
                    toDelete.add(crossListing);
                }
            }
            courseInfoToValidate.getCrossListings().removeAll(toDelete);

            List<CourseJointInfo> jointToDelete = new ArrayList<>();
            for (CourseJointInfo joint : courseInfoToValidate.getJoints()) {
                if (StringUtils.isBlank(joint.getCourseId())){
                    jointToDelete.add(joint);
                }
            }
            courseInfoToValidate.getJoints().removeAll(toDelete);

            List<CourseVariationInfo> variationToDelete = new ArrayList<>();
            for (CourseVariationInfo variation : courseInfoToValidate.getVariations()) {
                if (StringUtils.isBlank(variation.getSubjectArea()) && StringUtils.isBlank(variation.getVariationTitle())){
                    variationToDelete.add(variation);
                }
                if (StringUtils.isBlank(variation.getTypeKey())){
                    variation.setTypeKey(ProgramConstants.VARIATION_TYPE_KEY);
                }
            }
            courseInfoToValidate.getVariations().removeAll(variationToDelete);


            String courseAuditAttribute = new AttributeHelper(courseInfoWrapper.getCourseInfo().getAttributes()).get(CurriculumManagementConstants.COURSE_AUDIT);
            String coursePassFailAttribute = new AttributeHelper(courseInfoWrapper.getCourseInfo().getAttributes()).get(CurriculumManagementConstants.COURSE_PASS_FAIL);

            courseInfoToValidate.getAttributes().add(new AttributeInfo(CurriculumManagementConstants.FINAL_EXAM_STATUS, courseInfoWrapper.getFinalExamStatus()));
            courseInfoToValidate.getAttributes().add(new AttributeInfo(CurriculumManagementConstants.FINAL_EXAM_RATIONALE, courseInfoWrapper.getFinalExamRationale()));
            courseInfoToValidate.getAttributes().add(new AttributeInfo(CurriculumManagementConstants.COURSE_ATTRIBUTE_COURSE_AUDIT, courseAuditAttribute));
            courseInfoToValidate.getAttributes().add(new AttributeInfo(CurriculumManagementConstants.COURSE_ATTRIBUTE_COURSE_PASS_FAIL, coursePassFailAttribute));

            if (StringUtils.isNotBlank(forcedStudentObjectStateKey)) {
                courseInfoToValidate.setStateKey(forcedStudentObjectStateKey);
            }
            errors = getCourseService().validateCourse("OBJECT", courseInfoToValidate, ContextUtils.createDefaultContextInfo());
        } catch (Exception ex) {
            LOG.error("Error occurred while performing service layer validation for Submit", ex);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, KSObjectUtils.unwrapException(20, ex).getMessage());
            courseInfoWrapper.getReviewProposalDisplay().setShowUnknownErrors(true);
        }
        bindValidationErrorsToPath(errors, form);
    }

    /**
     *  Binds the each validation errors with its property path
     * @param validationResultInfoList
     * @param form
     */
    protected void bindValidationErrorsToPath(List<ValidationResultInfo> validationResultInfoList, DocumentFormBase form) {
        ProposalElementsWrapper wrapper = (ProposalElementsWrapper)(((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject());
        wrapper.getReviewProposalDisplay().setShowUnknownErrors(false);
        int unknownErrorCount = 0;

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
                    case "endTerm":
                        if (StringUtils.equals(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, form.getPageId())) {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.endTerm";
                        } else {
                            elementPath = CurriculumManagementConstants.DATA_OBJECT_PATH + ".reviewProposalDisplay.activeDatesSection.endTerm";
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
                        wrapper.getReviewProposalDisplay().setShowUnknownErrors(true);
                        error.setMessage(error.getElement() + ": " + error.getMessage());
                        LOG.warn("[" + error.getLevel() + "] - " + error.getMessage());
                        unknownErrorCount++;
                        if(unknownErrorCount==1) {
                            GlobalVariables.getMessageMap().putError(elementPath, CurriculumManagementConstants.MessageKeys.ERROR_KS_LEGACY_VALIDATION);
                        }
                }
                if(StringUtils.isNotBlank(elementPath)) {
                    GlobalVariables.getMessageMap().putError(elementPath, RiceKeyConstants.ERROR_CUSTOM, error.getMessage());
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

    protected SubjectCodeService getSubjectCodeService() {
        if (subjectCodeService == null) {
            subjectCodeService = GlobalResourceLoader.getService(new QName(CourseServiceConstants.NAMESPACE_SUBJECTCODE, SubjectCodeService.class.getSimpleName()));
        }
        return subjectCodeService;
    }

}
