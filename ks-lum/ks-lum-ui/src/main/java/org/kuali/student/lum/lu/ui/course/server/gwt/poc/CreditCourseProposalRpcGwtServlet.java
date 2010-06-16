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

package org.kuali.student.lum.lu.ui.course.server.gwt.poc;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.ui.server.gwt.poc.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.lu.assembly.ModifyCreditCourseProposalManager;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;

public class CreditCourseProposalRpcGwtServlet extends
		AbstractBaseDataOrchestrationRpcGwtServlet implements
		CreditCourseProposalRpcService {

	private static final long serialVersionUID = 1L;
	final static Logger LOG = Logger.getLogger(CreditCourseProposalRpcGwtServlet.class);

	public static final String COURSE_TYPE = "kuali.lu.type.CreditCourse";
	private static final String WF_TYPE_CLU_DOCUMENT = "CluCreditCourseProposal";
	private static final String DEFAULT_METADATA_STATE = "draft";

	private ModifyCreditCourseProposalManager modifyCourseManager;	
	private CourseService courseService;
	private ProposalService proposalService;
	

	@Override
	protected Object get(String id) throws Exception {
		CourseInfo courseInfo;
		try {
			courseInfo = courseService.getCourse(id);
		} catch (Exception e) {
			//This could be a proposal id
			ProposalInfo proposalInfo = proposalService.getProposal(id);
			String courseId = proposalInfo.getProposalReference().get(0);
			courseInfo = courseService.getCourse(courseId);
		}		
		
		return courseInfo; 
	}

	@Override
	protected Object save(Object dto) throws Exception {
		CourseInfo courseInfo = (CourseInfo)dto;
	
		courseInfo.setType(COURSE_TYPE);
		courseInfo.setState(DEFAULT_METADATA_STATE);
		if (courseInfo.getId() == null){
			courseInfo = courseService.createCourse(courseInfo);
		} else {
			courseInfo = courseService.updateCourse(courseInfo);
		}
		return courseInfo;
	}

	@Override
	public Data getNewProposalWithCopyOfClu(String dataId) throws OperationFailedException {
		try {
			return modifyCourseManager.getNewProposalWithCopyOfClu(dataId);
		} catch (AssemblyException e) {
			LOG.error("Copy Failed on id:"+dataId, e);
			throw new OperationFailedException("Copy Failed on id:"+dataId,e);
		}
	}
	
	@Override
	protected String getDefaultMetaDataState() {
		return DEFAULT_METADATA_STATE;
	}

	@Override
	protected String getDefaultWorkflowDocumentType() {
		return WF_TYPE_CLU_DOCUMENT;
	}

	@Override
	protected boolean checkDocumentLevelPermissions() {
		return true;
	}

	@Override
	protected Class<?> getDtoClass() {
		return CourseInfo.class;
	}

	public void setModifyCourseManager(
			ModifyCreditCourseProposalManager modifyCourseManager) {
		this.modifyCourseManager = modifyCourseManager;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

	
}
