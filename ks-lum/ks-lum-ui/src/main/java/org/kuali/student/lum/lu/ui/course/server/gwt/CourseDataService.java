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

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;

public class CourseDataService extends AbstractDataService {

	private static final long serialVersionUID = 1L;
	final static Logger LOG = Logger.getLogger(CourseDataService.class);

	private static final String DEFAULT_METADATA_STATE = "draft";
	
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
	protected Object save(Object dto) throws Exception {
		CourseInfo courseInfo = (CourseInfo)dto;
		
		if (courseInfo.getId() == null){
			courseInfo = courseService.createCourse(courseInfo);
		} else {
			courseInfo = courseService.updateCourse(courseInfo);
		}
		return courseInfo;
	}
	
	@Override
	protected String getDefaultMetaDataState() {
		return DEFAULT_METADATA_STATE;
	}

	@Override
	protected String getDefaultWorkflowDocumentType() {
		return "CluCreditCourseProposal";
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

}
