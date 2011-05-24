/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.core.assembly.transform.ProposalWorkflowFilter;
import org.kuali.student.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.lu.LUConstants;
import org.springframework.util.StringUtils;

public class CourseDataService extends AbstractDataService {

	private static final long serialVersionUID = 1L;
	final static Logger LOG = Logger.getLogger(CourseDataService.class);

	private static final String DEFAULT_METADATA_STATE = DtoConstants.STATE_DRAFT;
	
	private CourseService courseService;

	@Override
	protected Object get(String id) throws Exception {
		CourseInfo courseInfo = null;

		try {
			courseInfo = courseService.getCourse(id);
		} catch (DoesNotExistException dne) {
			LOG.info("Course not found for key " + id + ". Course loaded from proposal instead.");
		}		
		
		return courseInfo; 
	}

	@Override
	protected Object save(Object dto, Map<String, Object> properties) throws Exception {
		CourseInfo courseInfo = (CourseInfo)dto;
		
		//Set derived course fields before saving/updating
		courseInfo = calculateCourseDerivedFields(courseInfo);
		
		if(properties!=null&&LUConstants.PROPOSAL_TYPE_COURSE_MODIFY.equals((String)properties.get(ProposalWorkflowFilter.WORKFLOW_DOC_TYPE))){
			//For Modify Course, see if we need to create a new version instead of create
			if(courseInfo.getId() == null){
				courseInfo = courseService.createNewCourseVersion(courseInfo.getVersionInfo().getVersionIndId(), courseInfo.getVersionInfo().getVersionComment());
			}else{
				courseInfo = courseService.updateCourse(courseInfo);
			}
		}else{
			if (courseInfo.getId() == null){
				courseInfo = courseService.createCourse(courseInfo);
			} else {
				courseInfo = courseService.updateCourse(courseInfo);
			}
		}
		return courseInfo;
	}
	
	
	@Override
	protected List<ValidationResultInfo> validate(Object dto) throws Exception {
		return courseService.validateCourse("OBJECT", (CourseInfo)dto);
	}

	@Override
	protected String getDefaultMetaDataState() {
		return DEFAULT_METADATA_STATE;
	}

	@Override
	protected String getDefaultWorkflowDocumentType() {
		return LUConstants.PROPOSAL_TYPE_COURSE_CREATE;
	}

	@Override
	protected boolean checkDocumentLevelPermissions() {
		return true;
	}

	@Override
	protected Class<?> getDtoClass() {
		return CourseInfo.class;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}


	/**
	 * This calculates and sets fields on course object that are derived from other course object fields.
	 */
	protected CourseInfo calculateCourseDerivedFields(CourseInfo courseInfo){
		//Course code is not populated in UI, need to derive them from the subject area and suffix fields
		if(StringUtils.hasText(courseInfo.getCourseNumberSuffix()) && StringUtils.hasText(courseInfo.getSubjectArea())){
			courseInfo.setCode(calculateCourseCode(courseInfo.getSubjectArea(),courseInfo.getCourseNumberSuffix()));
		}
		
		//Derive course code for crosslistings
		for(CourseCrossListingInfo crossListing:courseInfo.getCrossListings()){
			 if(StringUtils.hasText(crossListing.getCourseNumberSuffix()) && StringUtils.hasText(crossListing.getSubjectArea())){
				 crossListing.setCode(calculateCourseCode(crossListing.getSubjectArea(), crossListing.getCourseNumberSuffix()));         
			 }
		}
		
		return courseInfo;
	}
	
	/**
	 * 
	 * This method calculates code for course and cross listed course.
	 * 
	 * @param subjectArea
	 * @param suffixNumber
	 * @return
	 */
	private String calculateCourseCode(String subjectArea, String suffixNumber) {
	    return subjectArea + suffixNumber;
	}
	
}
