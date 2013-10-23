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

import static org.kuali.student.logging.FormattedLogger.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.name.EntityNameContract;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.cm.course.form.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.CourseJointInfoWrapper;
import org.kuali.student.cm.course.form.LoItem;
import org.kuali.student.cm.course.form.LoItemModel;
import org.kuali.student.cm.course.form.OrganizationInfoWrapper;
import org.kuali.student.cm.course.service.CourseInfoMaintainable;
import org.kuali.student.cm.course.service.impl.LookupableConstants;
import org.kuali.student.cm.course.service.util.CourseCodeSearchUtil;
import org.kuali.student.common.uif.form.KSUifMaintenanceDocumentForm;
import org.kuali.student.core.organization.ui.client.mvc.model.MembershipInfo;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities.DecisionRationaleDetail;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.DtoConstants.DtoState;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.DecisionInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.constants.CommentServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller handles all the request from Academic calendar UI.
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
@Controller
@RequestMapping(value = "/courses")
public class CourseController extends MaintenanceDocumentController {
    
    private static final String DECISIONS_DIALOG_KEY = "decisionsDialog";
    
    private static final String VIEW_CURRENT_PAGE_ID = "view.currentPageId";

    private CourseService courseService;
    private CommentService commentService;
	private SubjectCodeService subjectCodeService;    
    private IdentityService identityService;
    private CluService cluService;
    
    private enum CourseViewPages {
        COURSE_INFO("KS-CourseView-CourseInfoPage"), 
        GOVERNANCE("KS-CourseView-GovernancePage"), 
        COURSE_LOGISTICS("KS-CourseView-CourseLogisticsPage"), 
        LEARNING_OBJECTIVES("KS-CourseView-LearningObjectivesPage"), 
        //COURSE_REQUISITES("KS-CourseView-CourseRequisitesPage"), 
        ACTIVE_DATES("KS-CourseView-ActiveDatesPage"), 
        //FINANCIALS("KS-CourseView-FinancialsPage"),
        AUTHORS_AND_COLLABORATORS("KS-CourseView-AuthorsAndCollaboratorsPage"),
        SUPPORTING_DOCUMENTS("KS-CourseView-SupportingDocumentsPage"),
        REVIEW_PROPOSAL("KS-CourseView-ReviewProposalPage");
        
        private String pageId;
        
        CourseViewPages(String pageId) {
            this.pageId = pageId;
        }
        
        String getPageId() {
            return this.pageId;
        }

    }
    
    /**
     * After the document is loaded calls method to setup the maintenance object
     */
    @Override
    @RequestMapping(params = "methodToCall=docHandler")
    public ModelAndView docHandler(@ModelAttribute("KualiForm") DocumentFormBase formBase, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        final MaintenanceDocumentForm maintenanceDocForm = (MaintenanceDocumentForm) formBase;
        
        try {
            redrawDecisionTable(maintenanceDocForm);
        }
        catch (Exception e) {
            error("Unable to create decision table: %s", e.getMessage());
        }

        // Create the document in the super method
        final ModelAndView retval = super.docHandler(maintenanceDocForm, result, request, response);
        maintenanceDocForm.getDocument().getDocumentHeader().setDocumentDescription("New Course Proposal");
        
        final CourseInfoMaintainable maintainable = getCourseMaintainableFrom(maintenanceDocForm);
        
        // We can actually get this from the workflow document initiator id. It doesn't need to be stored in the form.
        maintainable.setUserId(ContextUtils.getContextInfo().getPrincipalId());

        // After creating the document, modify the state
        maintainable.getCourse().setStateKey(DtoConstants.STATE_DRAFT);
        maintainable.setLastUpdated(DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss").print(new DateTime()));

        // Initialize Curriculum Oversight if it hasn't already been.
        if (maintainable.getCourse().getUnitsContentOwner() == null) {
            maintainable.getCourse().setUnitsContentOwner(new ArrayList<String>());
        }
                
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
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=saveAndContinue")
    public ModelAndView saveAndContinue(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        final CourseInfoMaintainable maintainable = getCourseMaintainableFrom(form);
        
        //Clear collection fields (those with matching 'wrapper' collections)
        maintainable.getCourse().getJoints().clear();
        maintainable.getCourse().getInstructors().clear();
        maintainable.getCourse().getUnitsDeployment().clear();
        
        //Retrieve the collection display values and get the fully loaded object (containing all the IDs and related IDs)
        if (maintainable.getCourseJointWrappers() != null) {
            for (final CourseJointInfoWrapper jointInfoDisplay : maintainable.getCourseJointWrappers()) {
                maintainable.getCourse().getJoints().add(CourseCodeSearchUtil.getCourseJointInfoWrapper(jointInfoDisplay.getCourseCode(), getCluService()));
            }
        }
        
        if (maintainable.getInstructorWrappers() != null) {
            for (final CluInstructorInfoWrapper instructorDisplay : maintainable.getInstructorWrappers()) {
                final CluInstructorInfoWrapper retrievedInstructor = getCourseMaintainableFrom(form).getInstructor(getInstructorSearchString(instructorDisplay.getDisplayName()));
                maintainable.getCourse().getInstructors().add(retrievedInstructor);
            }
        }

        if (maintainable.getAdministeringOrganizations() != null) {
            for (final OrganizationInfoWrapper org : maintainable.getAdministeringOrganizations()) {
                maintainable.getCourse().getUnitsDeployment().add(org.getOrganizationName());
            }
        }
        
        // Set derived course fields before saving/updating
        maintainable.setCourse(calculateCourseDerivedFields(maintainable.getCourse()));
        maintainable.setLastUpdated(DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss").print(new DateTime()));

        maintainable.getCourse().setUnitsContentOwner(new ArrayList<String>());
        for (final KeyValue wrapper : maintainable.getUnitsContentOwner()) {
            maintainable.getCourse().getUnitsContentOwner().add(wrapper.getValue());
        }

        try {
            save(form, result, request, response);
        }
        catch (Exception e) {
            error("Unable to save document: %s", e.getMessage());
        }

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
     * Converts the display name of the instructor into the plain user name (for use in a search query)
     * 
     * @param displayName The display name of the instructor.
     * @return The user name of the instructor.
     */
    private String getInstructorSearchString(String displayName) {
        String searchString = null;
        if (displayName.contains("(") && displayName.contains(")")) {
            searchString = displayName.substring(displayName.lastIndexOf("(") + 1, displayName.lastIndexOf(")"));
        }
        return searchString;
    }
    
    /**
     * Determines to which page to navigate.
     * 
     * @param currentPageId The current page id.
     * @return The id of the next page to navigate to.
     */
    protected String getNextPageId(final String currentPageId) {
        String nextPageId = null;
        final CourseViewPages[] pages = CourseViewPages.values();
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
        
        final String dateStr = DateTimeFormat.forPattern("MMMM dd, yyyy - hh:mmaaa").print(new DateTime());
        final Date date = new Date();
        
        // This needs to be looked at when Reference data is retrieved. Lookup what comments are in the DB
        // prob might move to showComment so that it displays the comments already made.
        //List<CommentInfo> commentInfoFromDB = getCommentService().getCommentsByReferenceAndType(tempRefId, tempRefTypeKey,ContextUtils.getContextInfo());
        
        for (CommentInfo ittCommentInfo : maintainable.getCommentInfos()) {
            CommentInfo commentInfo = ittCommentInfo;
            commentInfo.getCommentText().setFormatted(commentInfo.getCommentText().getPlain());
            //get the userID from the form to save with the comment made.
            commentInfo.setCommenterId(getUserNameLoggedin(maintainable.getUserId()) + " " + dateStr);
            if(commentInfo.getEffectiveDate() == null){
                commentInfo.setEffectiveDate(date);
            }
            commentInfo.setTypeKey("kuali.comment.type.generalRemarks");
            commentInfo.setReferenceId(tempRefId);
            commentInfo.setReferenceTypeKey(tempRefTypeKey);
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
        form.getDialogManager().setDialogReturnMethod("commentsLightBox", UifConstants.MethodToCallNames.START);
        return returnFromLightbox(form, result, request, response);
    }
    
    /**
     * @param userId The id of the person currently logged in.
     * @return returns User name of person currently logged in.
     */
    public String getUserNameLoggedin(String userId){
        Entity kimEntityInfo = getIdentityService().getEntityByPrincipalId(userId);
        return getUserRealNameByEntityInfo(kimEntityInfo);
    }
    
    /**
     * @param kimEntityInfo The (@link Entity) information for the currently logged in user.
     * @return The formatted user name of the currently logged in user.
     */
    protected String getUserRealNameByEntityInfo(Entity kimEntityInfo){
        EntityNameContract kimEntityNameInfo = (kimEntityInfo == null)? null : kimEntityInfo.getDefaultName();
        StringBuilder name = new StringBuilder(); 
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
        
        final DateTimeFormatter dateFormat = DateTimeFormat.forPattern("MM/dd/yyyy");

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

                    final String rationaleDate = dateFormat.print(new DateTime(commentInfo.getMeta().getCreateTime().getTime()));
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
    				maintainable.getDecisions().add(decision);
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
        if (maintainable.getUnitsContentOwnerToAdd() == null) {
            info("Units Content Owner is null");
            return getUIFModelAndView(form);
        }

        final String toAdd = maintainable.getUnitsContentOwnerToAdd();
        info("Adding ", toAdd);
        maintainable.getUnitsContentOwner().add(getOrganizationBy(maintainable.getCourse().getSubjectArea(), toAdd));
        maintainable.setUnitsContentOwnerToAdd("");
        
        return getUIFModelAndView(form);
    }


    /**
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
                String subjectCodeShortName = "";
                String subjectCodeOptionalLongName = "";
                String subjectCodeType = "";
                
                for (final SearchResultCellInfo resultCell : result.getCells()) {
                    if ("subjectCode.resultColumn.orgId".equals(resultCell.getKey())) {
                        subjectCodeId = resultCell.getValue();
                    } else if ("subjectCode.resultColumn.orgShortName".equals(resultCell.getKey())) {
                        subjectCodeShortName = resultCell.getValue();
                    } else if ("subjectCode.resultColumn.orgLongName".equals(resultCell.getKey())) {
                    	subjectCodeOptionalLongName = resultCell.getValue();
                    } else if ("subjectCode.resultColumn.orgType".equals(resultCell.getKey())) {
                    	subjectCodeType = resultCell.getValue();
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
        LoItemModel loItemModel = setupLoModel(form);
        loItemModel.moveUpCurrent();
        clearSelectedLoItem(loItemModel.getLoItems());
        
        return getUIFModelAndView(form);
    }
    
    @RequestMapping(params = "methodToCall=moveLearningObjectiveDown")
    public ModelAndView moveLearningObjectiveDown(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        LoItemModel loItemModel = setupLoModel(form);
        loItemModel.moveDownCurrent();
        clearSelectedLoItem(loItemModel.getLoItems());

        return getUIFModelAndView(form);
    }
    
    @RequestMapping(params = "methodToCall=moveLearningObjectiveRight")
    public ModelAndView moveLearningObjectiveRight(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        LoItemModel loItemModel = setupLoModel(form);
        loItemModel.indentCurrent();
        clearSelectedLoItem(loItemModel.getLoItems());
        
        return getUIFModelAndView(form);
    }
    
    @RequestMapping(params = "methodToCall=moveLearningObjectiveLeft")
    public ModelAndView moveLearningObjectiveLeft(final @ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        LoItemModel loItemModel = setupLoModel(form);
        loItemModel.outdentCurrent();
        clearSelectedLoItem(loItemModel.getLoItems());

        return getUIFModelAndView(form);
    }
    
    private LoItemModel setupLoModel(MaintenanceDocumentForm form) {
        CourseInfoMaintainable courseInfoMaintainable = getCourseMaintainableFrom(form);
        LoItemModel loItemModel = courseInfoMaintainable.getLoItemModel();
        List<LoItem> loItems = loItemModel.getLoItems();
        LoItem selectedLoItem = getSelectedLoItem(loItems);
        loItemModel.setCurrentLoItem(selectedLoItem);
        return loItemModel;
    }
    
    private LoItem getSelectedLoItem(List<LoItem> loItems) {
        LoItem selectedLoItem = null;
        if (loItems != null && !loItems.isEmpty()) {
            for (LoItem loItem : loItems) {
                if (loItem.isSelected()) {
                    selectedLoItem = loItem;
                    break;
                }
            }
        }
        return selectedLoItem;
    }
    
    private void clearSelectedLoItem(List<LoItem> loItems) {
        if (loItems != null && !loItems.isEmpty()) {
            for (LoItem loItem : loItems) {
                loItem.setSelected(false);
            }
        }
    }
     
    /**
     * Retrieves the {@link CourseInfoMaintainable} instance from the {@link MaintenanceDocumentForm} in session
     * 
     * @param form {@link MaintenanceDocumentForm}
     * @param {@link CourseInfoMaintainable}
     */
    protected CourseInfoMaintainable getCourseMaintainableFrom(final MaintenanceDocumentForm form) {
        return (CourseInfoMaintainable) form.getDocument().getNewMaintainableObject();
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
			subjectCodeService = GlobalResourceLoader.getService(new QName(LookupableConstants.NAMESPACE_SUBJECTCODE, SubjectCodeService.class.getSimpleName()));
		}
		return subjectCodeService;
	}	
}
