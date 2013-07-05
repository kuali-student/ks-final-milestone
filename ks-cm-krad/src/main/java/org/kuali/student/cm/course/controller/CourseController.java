/*
 * Copyright 2011 The Kuali Foundation
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

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.course.form.CluInstructorInfoDisplay;
import org.kuali.student.cm.course.form.CourseForm;
import org.kuali.student.cm.course.form.CourseJointInfoDisplay;
import org.kuali.student.cm.course.service.impl.CourseViewHelperServiceImpl;
import org.kuali.student.logging.FormattedLogger;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
public class CourseController extends UifControllerBase {

    private CourseService courseService;
    
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
	    	for (CourseJointInfoDisplay jointInfoDisplay : form.getCourseJointDisplays()) {
	    		//Should only get one entry back
	    		List<CourseJointInfoDisplay> loadedCourseJointDisplays = CourseViewHelperServiceImpl.getInstance().getJointOfferingCourseNumbersForSuggest(jointInfoDisplay.getCourseCode());
	    		form.getCourseInfo().getJoints().add(loadedCourseJointDisplays.get(0));
	    	}
    	}
    	
    	if (form.getInstructorDisplays() != null) {
    		for (CluInstructorInfoDisplay instructorDisplay : form.getInstructorDisplays()) {
    			//Should only get one entry back
    			List<CluInstructorInfoDisplay> loadedInstructorDisplays = CourseViewHelperServiceImpl.getInstance().getInstructorsForSuggest(getInstructorSearchString(instructorDisplay.getDisplayName()));
    			form.getCourseInfo().getInstructors().add(loadedInstructorDisplays.get(0));
    		}
    	}
    	
//    	//Merge the helper 'display' attributes
//    	form.getCourseInfo().getJoints().clear();
//    	if (form.getCourseJointDisplays() != null) {
//	    	for (CourseJointInfoDisplay jointInfoDisplay : form.getCourseJointDisplays()) {
//	    		form.getCourseInfo().getJoints().add(jointInfoDisplay);
//	    	}
//    	}
//    	
//    	form.getCourseInfo().getInstructors().clear();
//    	if (form.getInstructorDisplays() != null) {
//    		for (CluInstructorInfoDisplay instructorDisplay : form.getInstructorDisplays()) {
//	    		form.getCourseInfo().getInstructors().add(instructorDisplay);
//	    	}
//    	}
    	
    	//Set derived course fields before saving/updating
        form.setCourseInfo(calculateCourseDerivedFields(form.getCourseInfo()));
    	
    	CourseInfo savedCourseInfo = null;
    	if (form.getCourseInfo().getId() == null) {
			FormattedLogger.debug("Create the course proposal");
			try {
				savedCourseInfo = getCourseService().createCourse_KRAD(
						form.getCourseInfo(), ContextUtils.getContextInfo());
			} catch (Exception e) {
				throw new RuntimeException(
						"Error creating a new course.", e);
			}
			
    	} else {
    		FormattedLogger.debug("Update the course proposal");
    		try {
    			savedCourseInfo = courseService.updateCourse_KRAD(form.getCourseInfo().getId(), form.getCourseInfo(), ContextUtils.getContextInfo());
    		} catch (Exception e) {
    			throw new RuntimeException("Error updating a course with title: " + form.getCourseInfo().getCourseTitle());
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
    
    
    
    private CourseService getCourseService() {
    	if (courseService == null) {
    		courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, CourseServiceConstants.SERVICE_NAME_LOCAL_PART));
    	}
    	return courseService;
    }

}
