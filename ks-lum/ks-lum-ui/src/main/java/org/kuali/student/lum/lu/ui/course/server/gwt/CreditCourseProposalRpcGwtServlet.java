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

import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.server.gwt.DataGwtServlet;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsDataModel;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreditCourseProposalRpcGwtServlet extends DataGwtServlet implements
        CreditCourseProposalRpcService {

	private static final Logger LOG = LoggerFactory.getLogger(CreditCourseProposalRpcGwtServlet.class);

	private static final long serialVersionUID = 1L;
	private CopyCourseServiceImpl copyCourseService;
	
	@Override
	public DataSaveResult createCopyCourse(String originalCluId)
			throws Exception {
		try {
			return copyCourseService.createCopyCourse(originalCluId, ContextUtils.getContextInfo());
		} catch (Exception e) {
			LOG.error(String.format("Error copying course with id: %s", originalCluId), e);
			throw e;
		}
	}

	@Override
	public DataSaveResult createCopyCourseProposal(String originalProposalId, String documentType)
			throws Exception {
		try {
			return copyCourseService.createCopyCourseProposal(originalProposalId, documentType, ContextUtils.getContextInfo());
		} catch (Exception e) {
			LOG.error(String.format("Error copying proposal with id: %s", originalProposalId), e);
			throw e;
		}

	}
	
    @Override
    public List<StatementTreeViewInfo> getCourseStatements(String courseId, String nlUsageTypeKey, String language) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }

    @Override
    public Map<Integer, StatementTreeViewInfo> storeCourseStatements(String courseId, String courseState, Map<Integer, CourseRequirementsDataModel.requirementState> states,
    			Map<Integer, StatementTreeViewInfo> rules) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }

    @Override
    public StatementTreeViewInfo createCourseStatement(String courseId, String courseState, StatementTreeViewInfo statementTreeViewInfo) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }

    @Override
    public StatusInfo deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }
    
    @Override
    public StatementTreeViewInfo updateCourseStatement(String courseId, String courseState, StatementTreeViewInfo statementTreeViewInfo) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }
    
    @Override
    public StatusInfo changeState(String courseId, String newState) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }
    
    public StatusInfo changeState(String courseId, String newState, String prevEndTerm) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }
	
    @Override
	public Boolean isLatestVersion(String versionIndId, Long versionSequenceNumber) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
	}
    
    @Override
    public Boolean isAnyOtherRetireProposalsInWorkflow(String courseCluId) {
        throw new UnsupportedOperationException("This method is not implemented.");
    }

	public void setCopyCourseService(CopyCourseServiceImpl copyCourseService) {
		this.copyCourseService = copyCourseService;
	}

}