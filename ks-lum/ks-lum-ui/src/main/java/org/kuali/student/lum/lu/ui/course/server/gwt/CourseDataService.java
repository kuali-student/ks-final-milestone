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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.core.assembly.transform.ProposalWorkflowFilter;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.lum.lu.LUConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.springframework.util.StringUtils;

public class CourseDataService extends AbstractDataService {

	final static Logger LOG = Logger.getLogger(CourseDataService.class);

	private static final String DEFAULT_METADATA_STATE = DtoConstants.STATE_DRAFT;
	
	protected CourseService courseService;
	protected CluService cluService;

	@Override
	protected Object get(String id, ContextInfo contextInfo) throws Exception {
		CourseInfo courseInfo = null;

		try {
			courseInfo = courseService.getCourse(id, contextInfo);
		} catch (DoesNotExistException dne) {
			LOG.info("Course not found for key " + id + ". Course loaded from proposal instead.");
		}		
		
		return courseInfo; 
	}

	@Override
	protected Object save(Object dto, Map<String, Object> properties, ContextInfo contextInfo) throws Exception {
		CourseInfo courseInfo = (CourseInfo)dto;
		
		//For retire course we don't want to actually save anything
        if(LUConstants.PROPOSAL_TYPE_COURSE_RETIRE.equals((String)properties.get(ProposalWorkflowFilter.WORKFLOW_DOC_TYPE))){
            if(courseInfo.getVersionInfo()==null){
                courseInfo = (CourseInfo) get(courseInfo.getId(), contextInfo);
            } 
            String startTerm = courseInfo.getStartTerm();
            Map<String,String> proposalAttributes = new HashMap<String,String>();
            if(startTerm!=null) {
                proposalAttributes.put("prevStartTerm", startTerm);
            }
            properties.put(ProposalWorkflowFilter.PROPOSAL_ATTRIBUTES, proposalAttributes);                             
            return courseInfo;
        }
		
		//Set derived course fields before saving/updating
		courseInfo = calculateCourseDerivedFields(courseInfo);
		
		if(properties!=null&&(LUConstants.PROPOSAL_TYPE_COURSE_MODIFY.equals((String)properties.get(ProposalWorkflowFilter.WORKFLOW_DOC_TYPE))||
				LUConstants.PROPOSAL_TYPE_COURSE_MODIFY_ADMIN.equals((String)properties.get(ProposalWorkflowFilter.WORKFLOW_DOC_TYPE)))){
			//For Modify Course, see if we need to create a new version instead of create
			if(courseInfo.getId() == null){
			    
			    if (isLatestVersion(courseInfo.getVersionInfo().getVersionIndId(), contextInfo)){
	            	String courseIndId = courseInfo.getVersionInfo().getVersionIndId();
	            	
	            	//Get the currentCourse from the service
	            	VersionDisplayInfo versionInfo =  courseService.getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, courseIndId, contextInfo);
	            	CourseInfo originalCourseInfo =  courseService.getCourse(versionInfo.getId(), contextInfo);
	            	
			    	//Save the start and end terms from the old version and put into filter properties
			    	String startTerm = originalCourseInfo.getStartTerm();
			    	String endTerm = originalCourseInfo.getEndTerm();
			    	Map<String,String> proposalAttributes = new HashMap<String,String>();
			    	if(startTerm!=null)
			    		proposalAttributes.put("prevStartTerm",startTerm);
			    	if(endTerm!=null)
			    		proposalAttributes.put("prevEndTerm",endTerm);
			    	
			    	properties.put(ProposalWorkflowFilter.PROPOSAL_ATTRIBUTES, proposalAttributes);
			    	
			        courseInfo = courseService.createNewCourseVersion(courseInfo.getVersionInfo().getVersionIndId(), courseInfo.getVersionInfo().getVersionComment(), contextInfo);
			    } else {
			        throw new OperationFailedException("Error creating new version for course, this course is currently under modification.");
			    }
			} else {
				courseInfo = courseService.updateCourse(courseInfo.getId(), courseInfo, contextInfo);
			}
		}else{
			if (courseInfo.getId() == null){
				courseInfo = courseService.createCourse(courseInfo, contextInfo);
			} else {
				courseInfo = courseService.updateCourse(courseInfo.getId(), courseInfo, contextInfo);
			}
		}
		return courseInfo;
	}
	
	
	@Override
	protected List<ValidationResultInfo> validate(Object dto, ContextInfo contextInfo) throws Exception {
		return courseService.validateCourse("OBJECT", (CourseInfo)dto, contextInfo);
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

	public void setCluService(CluService cluService) {
        this.cluService = cluService;
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
	
	public Boolean isLatestVersion(String versionIndId, ContextInfo contextInfo) throws Exception {
	    VersionDisplayInfo currentVersion = cluService.getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, versionIndId, contextInfo);
        //Perform a search to see if there are any new versions of the course that are approved, draft, etc.
        //We don't want to version if there are
        SearchRequest request = new SearchRequest("lu.search.isVersionable");
        request.addParam("lu.queryParam.versionIndId", versionIndId);
        request.addParam("lu.queryParam.sequenceNumber", currentVersion.getSequenceNumber().toString());
        List<String> states = new ArrayList<String>();
        states.add("Approved");
        states.add("Active");
        states.add("Draft");
        states.add("Superseded");
        request.addParam("lu.queryParam.luOptionalState", states);
        SearchResult result = cluService.search(request);
        
        String resultString = result.getRows().get(0).getCells().get(0).getValue();
        return "0".equals(resultString);
    }
}
