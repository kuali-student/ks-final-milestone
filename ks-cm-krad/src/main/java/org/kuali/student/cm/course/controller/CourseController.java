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

import static org.kuali.student.logging.FormattedLogger.debug;
import static org.kuali.student.logging.FormattedLogger.error;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.name.EntityNameContract;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.course.form.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.CourseForm;
import org.kuali.student.cm.course.form.CourseJointInfoWrapper;
import org.kuali.student.cm.course.form.LoCategoryInfoWrapper;
import org.kuali.student.cm.course.form.OrganizationInfoWrapper;
import org.kuali.student.cm.course.service.impl.CourseViewHelperServiceImpl;
import org.kuali.student.cm.course.service.impl.LookupableConstants;
import org.kuali.student.core.organization.ui.client.mvc.model.MembershipInfo;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities.DecisionRationaleDetail;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.DtoConstants.DtoState;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LearningObjectiveServiceConstants;
import org.kuali.student.r2.common.util.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.DecisionInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.constants.CommentServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static org.kuali.student.logging.FormattedLogger.*;

/**
 * This controller handles all the request from Academic calendar UI.
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
@Controller
@RequestMapping(value = "/courses")
public class CourseController extends MaintenanceDocumentController {
    private static final String DECISIONS_DIALOG_KEY = "decisionsDialog";

    private CourseService courseService;
    private CommentService commentService;
    private LearningObjectiveService learningObjectiveService;
    private IdentityService identityService;
    
    private enum CourseViewPages {
        COURSE_INFO("KS-CourseView-CourseInfoPage"), 
        GOVERNANCE("KS-CourseView-GovernancePage"), 
        COURSE_LOGISTICS("KS-CourseView-CourseLogisticsPage"), 
        //LEARNING_OBJECTIVES("KS-CourseView-LearningObjectivesPage"), 
        //COURSE_REQUISITES("KS-CourseView-CourseRequisitesPage"), 
        ACTIVE_DATES("KS-CourseView-ActiveDatesPage"), 
        //FINANCIALS("KS-CourseView-FinancialsPage"),
        //AUTHORS_AND_COLLABORATORS("KS-CourseView-AuthorsAndCollaboratorsPage"),
        SUPPORTING_DOCUMENTS("KS-CourseView-SupportingDocumentsPage");
        //REVIEW_PROPOSAL("KS-CourseView-ReviewProposalPage");
        
        private String pageId;
        
        CourseViewPages(String pageId) {
            this.pageId = pageId;
        }
        
        String getPageId() {
            return this.pageId;
        }

    }
    
    private static final String VIEW_CURRENT_PAGE_ID = "view.currentPageId";
    
    /* We may not want this
    @Override
    protected DocumentFormBase createInitialForm(final HttpServletRequest request) {
        CourseForm courseForm = new CourseForm();
        return courseForm;
    }
    */
    
    /**
     * This will save the Course Proposal.
     * @param form The CourseForm that contains all the needed information to save the proposal.
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=saveAndContinue")
    public ModelAndView saveAndContinue(@ModelAttribute("KualiForm") CourseForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        
        //Clear collection fields (those with matching 'display' collections)
        form.getCourseInfo().getJoints().clear();
        form.getCourseInfo().getInstructors().clear();
        
        //Retrieve the collection display values and get the fully loaded object (containing all the IDs and related IDs)
        if (form.getCourseJointDisplays() != null) {
            for (final CourseJointInfoWrapper jointInfoDisplay : form.getCourseJointDisplays()) {
                form.getCourseInfo().getJoints().add(CourseViewHelperServiceImpl.getInstance().getJointOfferingCourse(jointInfoDisplay.getCourseCode()));
            }
        }
        
        if (form.getInstructorDisplays() != null) {
            for (final CluInstructorInfoWrapper instructorDisplay : form.getInstructorDisplays()) {
                final CluInstructorInfoWrapper retrievedInstructor = CourseViewHelperServiceImpl.getInstance().getInstructor(getInstructorSearchString(instructorDisplay.getDisplayName()));
                form.getCourseInfo().getInstructors().add(retrievedInstructor);
            }
        }

        if (form.getAdministeringOrganizations() != null) {
            for (final OrganizationInfoWrapper org : form.getAdministeringOrganizations()) {
                form.getCourseInfo().getUnitsDeployment().add(org.getOrganizationName());
            }
        }
        
        //Set derived course fields before saving/updating
        form.setCourseInfo(calculateCourseDerivedFields(form.getCourseInfo()));
        
        CourseInfo savedCourseInfo = null;
        if (form.getCourseInfo().getId() == null) {
            debug("Create the course proposal");
            try {
                savedCourseInfo = getCourseService().createCourse_KRAD(
                        form.getCourseInfo(), ContextUtils.getContextInfo());
            } catch (Exception e) {
                throw new RuntimeException(
                        "Error creating a new course.", e);
            }
            
        } else {
            debug("Update the course proposal");
            try {
                savedCourseInfo = courseService.updateCourse_KRAD(form.getCourseInfo().getId(), form.getCourseInfo(), ContextUtils.getContextInfo());
            } catch (Exception e) {
                throw new RuntimeException("Error updating a course with title: " + form.getCourseInfo().getCourseTitle(), e);
            }
            
        }
        savedCourseInfo.setUnitsContentOwner(form.getCourseInfo().getUnitsContentOwner());
        form.setCourseInfo(savedCourseInfo);
        return getUIFModelAndView(form, getNextPageId(request.getParameter(VIEW_CURRENT_PAGE_ID)));
    }
    
    /**
     * Copied this method from CourseDataService.
     * This calculates and sets fields on course object that are derived from other course object fields.
     */
    private CourseInfo calculateCourseDerivedFields(CourseInfo courseInfo) {
        //Course code is not populated in UI, need to derive them from the subject area and suffix fields
        if (StringUtils.isNotBlank(courseInfo.getCourseNumberSuffix()) && StringUtils.isNotBlank(courseInfo.getSubjectArea())) {
            courseInfo.setCode(calculateCourseCode(courseInfo.getSubjectArea(), courseInfo.getCourseNumberSuffix()));
        }

        //Derive course code for crosslistings
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
    private String calculateCourseCode(String subjectArea, String suffixNumber) {
        return subjectArea + suffixNumber;
    }
    
    /**
     * Return the user name of the instructor using the display name
     */
    private String getInstructorSearchString(String displayName) {
        String searchString = null;
        if (displayName.contains("(") && displayName.contains(")")) {
            searchString = displayName.substring(displayName.lastIndexOf("(") + 1, displayName.lastIndexOf(")"));
        }
        return searchString;
    }
    
    private String getNextPageId(String currentPageId) {
        String nextPageId = null;
        CourseViewPages[] pages = CourseViewPages.values();
        for (int i = 0; i < pages.length; i++) {
            if (pages[i].getPageId().equals(currentPageId)) {
                //Get the next page in the enum, except when it's the last page in the enum
                if (i + 1 < pages.length) {
                    nextPageId = pages[++i].getPageId();
                    break;
                }
            }
        }
        return nextPageId;
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) { 
        final MaintenanceDocumentForm courseForm = (MaintenanceDocumentForm) form;


        // We can actually get this from the workflow document initiator id. It doesn't need to be stored in the form.
        // courseForm.setUserId(ContextUtils.getContextInfo().getPrincipalId());
        try {
            redrawDecisionTable(courseForm);
        }
        catch (Exception e) {
            error("Untable to create decision table: %s", e.getMessage());
        }

        // Create the document in the super method
        final ModelAndView retval = super.start(courseForm, result, request, response);

        // After creating the document, modify the state
        // Should look into replacing this with calls to WorkflowUtilities
        ((CourseInfo) courseForm.getDocument().getNewMaintainableObject().getDataObject()).setStateKey(DtoConstants.STATE_DRAFT);

        return retval;
    }

    /**
     * Server-side action for rendering the decisions lightbox
     *
     * @param form {@link CourseForm} instance used for this action
     * @param result
     * @param request {@link HttpServletRequest} instance of the actual HTTP request made
     * @param response The intended {@link HttpServletResponse} sent back to the user
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=showDecisions")
    public ModelAndView showDecisions(@ModelAttribute("KualiForm") CourseForm form, BindingResult result,
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
     * @param form the {@link CourseForm} instance where the decisions are to be stored for the lightbox.
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
     *
     * @param form
     * @param commentInfos
     * @param members
     */
	protected void redrawDecisionTable(final MaintenanceDocumentForm form,
                                       final List<CommentInfo> commentInfos,
                                       final Map<String, MembershipInfo> members) {
		if (commentInfos != null) {
			int rowIndex = 0;
			for (final CommentInfo commentInfo : commentInfos) {
			    /* we only want decision rationale comments so if no DecisionRationaleDetail is returned for comment
                 * type then don't add that comment to the table
                 */
			    final DecisionRationaleDetail drDetails = DecisionRationaleDetail.getByType(commentInfo.getType());
			    if (drDetails != null) {
                    final DecisionInfo decision = new DecisionInfo();
                    decision.setDecision(drDetails.getLabel());
                    decision.setId(commentInfo.getId());

                    final SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
                    
                    final StringBuilder rationaleDate = new StringBuilder(dateformat.format(commentInfo.getMeta().getCreateTime()));
    				decision.setDate(rationaleDate.toString());
    
    				if (members.get(commentInfo.getMeta().getCreateId()) != null) {
    					final MembershipInfo memberInfo = members.get(commentInfo.getMeta().getCreateId());
    					final StringBuilder memberName = new StringBuilder();
    					memberName.append(memberInfo.getFirstName());
    					memberName.append(" ");
    					memberName.append(memberInfo.getLastName());

    					decision.setActor(memberName.toString());
    				}
    				decision.setRationale(commentInfo.getCommentText().getPlain());
                    // form.getDecisions().add(decision);
			    }
			}
		}
	}

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
     
    @RequestMapping(params = "methodToCall=showComment")
    public ModelAndView showComment(@ModelAttribute("KualiForm") CourseForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

            // redirect back to client to display lightbox
            return showDialog("commentsLightBox", form, request, response);
    }
    
    
    @RequestMapping(params = "methodToCall=saveAndCloseComment")
    public ModelAndView saveAndCloseComment(@ModelAttribute("KualiForm") CourseForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        DateFormat df = new SimpleDateFormat("MMMM dd, yyyy - hh:mmaaa");
        Date date = new Date();
        
        for (CommentInfo ittCommentInfo : form.getCommentInfos()) {
            CommentInfo commentInfo = ittCommentInfo;
            commentInfo.getCommentText().setFormatted(commentInfo.getCommentText().getPlain());
            //get the userID from the form to save with the comment made.
            commentInfo.setCommenterId(getUserNameLoggedin(form.getUserId())+" "+df.format(date));
            commentInfo.setEffectiveDate(date);
            commentInfo.setTypeKey("kuali.comment.type.generalRemarks");
            //TODO KSCM-848 : Will need to replace these temp values once we get UMD's reference data
            commentInfo.setReferenceId("temp_reference_id");
            commentInfo.setReferenceTypeKey("referenceType.clu.proposal");
            commentInfo.setStateKey(DtoState.ACTIVE.toString());
            CommentInfo newComment = null;
            try {
                newComment = getCommentService().createComment_KRAD(commentInfo.getReferenceId(),
                        commentInfo.getReferenceTypeKey(), commentInfo.getTypeKey(), commentInfo,
                        ContextUtils.getContextInfo());
            } catch (Exception e) {
                throw new RuntimeException("Error creating a new comment.", e);
            }
            ittCommentInfo = newComment;
        }
        //form.getDialogManager().removeDialog("commentsLightBox");
        /*return getUIFModelAndView(form);*/
        form.getDialogManager().setDialogReturnMethod("commentsLightBox", "start");
        return returnFromLightbox(form, result, request, response);
    }
    
    @RequestMapping(params = "methodToCall=browseForCategories")
    public ModelAndView browseForCategories(@ModelAttribute("KualiForm") CourseForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String commentDialogKey = "loCategoryDialog";
        if (!hasDialogBeenAnswered(commentDialogKey, form)){
            //Get the available categories
            form.getLoDialogWrapper().setLearningObjectiveOptions(getLoCategories());
            
            // redirect back to client to display lightbox
            return showDialog(commentDialogKey, form, request, response);
        }
        
        // Get value from chosen radio button
        boolean choice = getBooleanDialogResponse(commentDialogKey, form, request, response);
        
        if (choice) {
            for (LoCategoryInfoWrapper loCategoryInfoWrapper : form.getLoDialogWrapper().getLearningObjectiveOptions()) {
                if (loCategoryInfoWrapper.isSelected()) {
                    LoDisplayInfo selectedLo = getSelectedLo(form);
                    if (selectedLo != null) {
                        selectedLo.getLoCategoryInfoList().add(loCategoryInfoWrapper);
                    }
                }
            }
            
        }
        
        // clear dialog history so they can press the button again
        form.getDialogManager().removeDialog(commentDialogKey);
        return getUIFModelAndView(form);
    }
    
    private LoDisplayInfo getSelectedLo(CourseForm form){
        String selectedIndex = form.getActionParamaterValue("selectedIndex");
        int index = -1;
        if (StringUtils.isNumeric(selectedIndex)) {
            index = Integer.parseInt(selectedIndex);
        }
        LoDisplayInfo selectedLo = null;
        if (index != -1) {
            selectedLo = form.getCourseInfo().getCourseSpecificLOs().get(index);
        }
        
        return selectedLo;
    }
    
    private List<LoCategoryInfoWrapper> getLoCategories() {
        List<LoCategoryInfoWrapper> loCategories = new ArrayList<LoCategoryInfoWrapper>();
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(LookupableConstants.LOCATEGORY_SEARCH);
        searchRequest.setSortColumn(LookupableConstants.LO_CATEGORY_NAME_RESULT);
        try {
            SearchResultInfo searchResult = getLearningObjectiveService().search(searchRequest, ContextUtils.getContextInfo());
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                LoCategoryInfoWrapper loWrapper = new LoCategoryInfoWrapper();
                for (SearchResultCellInfo cell : cells) {
                    if (LookupableConstants.LO_CATEGORY_ID_RESULT.equals(cell.getKey())) {
                        loWrapper.setId(cell.getValue());
                    } else if (LookupableConstants.LO_CATEGORY_NAME_RESULT.equals(cell.getKey())) {
                        loWrapper.setName(cell.getValue());
                    } else if(LookupableConstants.LO_CATEGORY_TYPE_RESULT.equals(cell.getKey())){
                        loWrapper.setTypeKey(cell.getValue());
                    } else if(LookupableConstants.LO_CATEGORY_TYPE_NAME_RESULT.equals(cell.getKey())){
                        loWrapper.setTypeName(cell.getValue());
                    } else if(LookupableConstants.LO_CATEGORY_STATE_RESULT.equals(cell.getKey())){
                        loWrapper.setStateKey(cell.getValue());
                    }
                }
                loCategories.add(loWrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while searching for the available Learning Categories", e);
        }
        return loCategories;
    }   
    
    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, CourseServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseService;
    }
    
    protected CommentService getCommentService() {
        if (commentService == null) {
            commentService = (CommentService) GlobalResourceLoader.getService(new QName(CommentServiceConstants.NAMESPACE, CommentService.class.getSimpleName()));
        }
        return commentService;
    }
    
    protected LearningObjectiveService getLearningObjectiveService() {
        if (learningObjectiveService == null) {
            learningObjectiveService = GlobalResourceLoader.getService(new QName(LearningObjectiveServiceConstants.NAMESPACE, LearningObjectiveService.class.getSimpleName()));
        }
        return learningObjectiveService;
    }

    protected IdentityService getIdentityService() {
        if (identityService == null) {
            identityService = GlobalResourceLoader.getService(new QName(KimConstants.Namespaces.KIM_NAMESPACE_2_0, "identityService"));
        }
        return identityService;
    }
    
    /**
     * Returns the KimentityInfo of the person logged in.
     * @param userId.
     * @return returns UserName of person logged in.
     */
    public String getUserNameLoggedin(String userId){
        
        Entity kimEntityInfo = getIdentityService().getEntityByPrincipalId(userId);
        return getUserRealNameByEntityInfo(kimEntityInfo);
        
    }
    
    protected String getUserRealNameByEntityInfo(Entity kimEntityInfo){
        EntityNameContract kimEntityNameInfo = (kimEntityInfo == null)? null : kimEntityInfo.getDefaultName();
        StringBuilder name = new StringBuilder(); 
        if (kimEntityNameInfo != null) {
            if (!nvl(kimEntityNameInfo.getFirstName()).trim().isEmpty()) {
                if (!name.toString().isEmpty()) {
                    name.append(" ");
                }
                name.append(nvl(kimEntityNameInfo.getFirstName()));
            }
            
            if (!nvl(kimEntityNameInfo.getMiddleName()).trim().isEmpty()) {
                if (!name.toString().isEmpty()) {
                    name.append(" ");
                }
                name.append(nvl(kimEntityNameInfo.getMiddleName()));
            }
            if (!nvl(kimEntityNameInfo.getLastName()).trim().isEmpty()) {
                if (!name.toString().isEmpty()) {
                    name.append(" ");
                }
                name.append(nvl(kimEntityNameInfo.getLastName()));
            }
        }
        return name.toString();
    }
    
    private String nvl(String inString) {
        return (inString == null)? "" : inString;
    }
    
}
