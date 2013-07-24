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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.course.form.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.CourseForm;
import org.kuali.student.cm.course.form.CourseJointInfoWrapper;
import org.kuali.student.cm.course.form.LoDisplayInfoWrapper;
import org.kuali.student.cm.course.service.impl.CourseViewHelperServiceImpl;
import org.kuali.student.cm.course.service.impl.LookupableConstants;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.DtoConstants.DtoState;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LearningObjectiveServiceConstants;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.constants.CommentServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
public class CourseController extends UifControllerBase {

    private CourseService courseService;
	private CommentService commentService;
	private LearningObjectiveService learningObjectiveService;
    
    private enum CourseViewPages {
    	COURSE_INFO("KS-CourseView-CourseInfoPage"), 
    	GOVERNANCE("KS-CourseView-GovernancePage"), 
    	COURSE_LOGISTICS("KS-CourseView-CourseLogisticsPage"), 
    	LEARNING_OBJECTIVES("KS-CourseView-LearningObjectivesPage"), 
    	COURSE_REQUISITES("KS-CourseView-CourseRequisitesPage"), 
    	ACTIVE_DATES("KS-CourseView-ActiveDatesPage"), 
    	FINANCIALS("KS-CourseView-FinancialsPage"),
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
    
    private static final String VIEW_CURRENT_PAGE_ID = "view.currentPageId";
    
    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
    	CourseForm courseForm = new CourseForm();
    	return courseForm;
    }
    
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
	    	for (CourseJointInfoWrapper jointInfoDisplay : form.getCourseJointDisplays()) {
	    		form.getCourseInfo().getJoints().add(CourseViewHelperServiceImpl.getInstance().getJointOfferingCourse(jointInfoDisplay.getCourseCode()));
	    	}
    	}
    	
    	if (form.getInstructorDisplays() != null) {
    		for (CluInstructorInfoWrapper instructorDisplay : form.getInstructorDisplays()) {
    			CluInstructorInfoWrapper retrievedInstructor = CourseViewHelperServiceImpl.getInstance().getInstructor(getInstructorSearchString(instructorDisplay.getDisplayName()));
    			form.getCourseInfo().getInstructors().add(retrievedInstructor);
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
    	form.setCourseInfo(savedCourseInfo);
    	return getUIFModelAndView(form, getNextPageId(request.getParameter(VIEW_CURRENT_PAGE_ID)));
    }
    
    /**
     * Copied this method from CourseDataService.
     * This calculates and sets fields on course object that are derived from other course object fields.
     */
    private CourseInfo calculateCourseDerivedFields(CourseInfo courseInfo) {
        //Course code is not populated in UI, need to derive them from the subject area and suffix fields
        if (StringUtils.hasText(courseInfo.getCourseNumberSuffix()) && StringUtils.hasText(courseInfo.getSubjectArea())) {
            courseInfo.setCode(calculateCourseCode(courseInfo.getSubjectArea(), courseInfo.getCourseNumberSuffix()));
        }

        //Derive course code for crosslistings
        for (CourseCrossListingInfo crossListing : courseInfo.getCrossListings()) {
            if (StringUtils.hasText(crossListing.getCourseNumberSuffix()) && StringUtils.hasText(crossListing.getSubjectArea())) {
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
        final CourseForm courseForm = (CourseForm) form;

    	courseForm.getCourseInfo().setStateKey(DtoConstants.STATE_DRAFT);
        return super.start(courseForm, result, request, response);
    }
     
    @RequestMapping(params = "methodToCall=createComment")
    public ModelAndView createComment(@ModelAttribute("KualiForm") CourseForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String commentDialogKey = "commentsDialog";
        if (!hasDialogBeenAnswered(commentDialogKey, form)){
            
            // redirect back to client to display lightbox
            return showDialog(commentDialogKey, form, request, response);
        }
        
        // Get value from chosen radio button
        boolean choice = getBooleanDialogResponse(commentDialogKey, form, request, response);
        
        // clear dialog history so they can press the button again
        form.getDialogManager().removeDialog(commentDialogKey);
        
        if (choice) {
            CommentInfo commentInfo = form.getCommentInfo();
            commentInfo.getCommentText().setFormatted(commentInfo.getCommentText().getPlain());
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
            form.setCommentInfo(newComment);
        }
    	return getUIFModelAndView(form);
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
        
        // clear dialog history so they can press the button again
        form.getDialogManager().removeDialog(commentDialogKey);
    	return getUIFModelAndView(form);
    }
    
    private List<LoDisplayInfoWrapper> getLoCategories() {
    	List<LoDisplayInfoWrapper> loCategories = new ArrayList<LoDisplayInfoWrapper>();
    	SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(LookupableConstants.LOCATEGORY_SEARCH);
        searchRequest.setSortColumn(LookupableConstants.LO_CATEGORY_NAME_RESULT);
    	try {
    		SearchResultInfo searchResult = getLearningObjectiveService().search(searchRequest, ContextUtils.getContextInfo());
			for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                LoDisplayInfoWrapper loWrapper = new LoDisplayInfoWrapper();
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
    
    private CourseService getCourseService() {
    	if (courseService == null) {
    		courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, CourseServiceConstants.SERVICE_NAME_LOCAL_PART));
    	}
    	return courseService;
    }
    
    private CommentService getCommentService() {
    	if (commentService == null) {
    		commentService = (CommentService) GlobalResourceLoader.getService(new QName(CommentServiceConstants.NAMESPACE, "CommentService"));
    	}
    	return commentService;
    }
    
    private LearningObjectiveService getLearningObjectiveService() {
		if (learningObjectiveService == null) {
			learningObjectiveService = GlobalResourceLoader.getService(new QName(LearningObjectiveServiceConstants.NAMESPACE, LearningObjectiveService.class.getSimpleName()));
		}
		return learningObjectiveService;
	}

}
