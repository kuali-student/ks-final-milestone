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

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r1.core.proposal.service.ProposalService;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.server.gwt.DataGwtServlet;
import org.kuali.student.core.statement.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.core.statement.ui.client.widgets.rules.RulesUtil;
import org.kuali.student.lum.common.server.StatementUtil;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsDataModel;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService;

public class CourseRpcGwtServlet extends DataGwtServlet implements CourseRpcService {

	private static final long serialVersionUID = 1L;

    private CourseService courseService;
    /**
     * The proposal service is injected via spring.
     */
    private ProposalService proposalService;
    private CluService cluService;
	private StatementService statementService;
	private CourseStateChangeServiceImpl stateChangeService;

    @Override
    public List<StatementTreeViewInfo> getCourseStatements(String courseId, String nlUsageTypeKey, String language) throws Exception {
        try
        {
            List<StatementTreeViewInfo> rules = courseService.getCourseStatements(courseId, nlUsageTypeKey, language,
                    ContextUtils.getContextInfo());
            if (rules != null) {
                for (StatementTreeViewInfo rule : rules) {
                    setReqCompNL(rule, ContextUtils.getContextInfo());
                }
            }
            return rules;
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public Map<Integer, StatementTreeViewInfo> storeCourseStatements(String courseId, String courseState, Map<Integer, CourseRequirementsDataModel.requirementState> states,
            Map<Integer, StatementTreeViewInfo> rules) throws Exception {
        try
        {
            Map<Integer, StatementTreeViewInfo> storedRules = new HashMap<Integer, StatementTreeViewInfo>();

            for (Integer key : rules.keySet()) {
                StatementTreeViewInfo rule = rules.get(key);
                switch (states.get(key)) {
                    case STORED:
                        //rule was not changed so continue
                        storedRules.put(key, null);
                        break;
                    case ADDED:
                        storedRules.put(key, createCourseStatement(courseId, courseState, rule));
                        break;
                    case EDITED:
                        storedRules.put(key, updateCourseStatement(courseId, courseState, rule));
                        break;
                    case DELETED:
                        storedRules.put(key, null);
                        deleteCourseStatement(courseId, rule);
                        break;
                    default:
                        break;
                }
            }
            return storedRules;
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public StatementTreeViewInfo createCourseStatement(String courseId, String courseState, StatementTreeViewInfo statementTreeViewInfo) throws Exception {
        try
        {
            StatementUtil.updateStatementTreeViewInfoState(courseState, statementTreeViewInfo);
            CourseRequirementsDataModel.stripStatementIds(statementTreeViewInfo);
            StatementTreeViewInfo rule = courseService.createCourseStatement(courseId, statementTreeViewInfo,
                    ContextUtils.getContextInfo());
            setReqCompNL(rule, ContextUtils.getContextInfo());
            return rule;
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public StatusInfo deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws Exception {
        try
        {
            return courseService.deleteCourseStatement(courseId, statementTreeViewInfo, ContextUtils.getContextInfo());
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public StatementTreeViewInfo updateCourseStatement(String courseId, String courseState, StatementTreeViewInfo statementTreeViewInfo) throws Exception {
        try
        {
            StatementUtil.updateStatementTreeViewInfoState(courseState, statementTreeViewInfo);
            CourseRequirementsDataModel.stripStatementIds(statementTreeViewInfo);
            StatementTreeViewInfo rule = courseService.updateCourseStatement(courseId, null, statementTreeViewInfo,
                    ContextUtils.getContextInfo());
            setReqCompNL(rule, ContextUtils.getContextInfo());
            return rule;
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

	@Override
	public DataSaveResult createCopyCourse(String originalCluId)
			throws Exception {
		throw new UnsupportedOperationException("Copy is not implemented without a proposal.");
	}

	@Override
	public DataSaveResult createCopyCourseProposal(String originalProposalId, String documentType)
			throws Exception {
		throw new UnsupportedOperationException("Copy is not implemented without a proposal.");
	}

	@Override  
    public StatusInfo changeState(String courseId, String newState) throws Exception {
    	return changeState(courseId, newState, null);
    }
	
	@Override
    public StatusInfo changeState(String courseId, String newState, String prevEndTerm) throws Exception {
        try
        {
            return stateChangeService.changeState(courseId, newState, prevEndTerm, ContextUtils.getContextInfo());
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
	
	/**
     * Perform a search using the proposal service to look for any retire
     * proposals in saved or enroute state.  Note that you can run this
     * search through soapUI for quick testing/debugging.  
     * <p>
     */
    public Boolean isAnyOtherRetireProposalsInWorkflow(String courseCluId) throws Exception{
        try
        {
            // Fill the request with the key to identify the search
            SearchRequestInfo request = new SearchRequestInfo("proposal.search.countForProposals");

            // Add search parms.  In this case, we will use the cluId of the course
            // we are trying to retire
            request.addParam("proposal.queryParam.cluId", courseCluId);

            // Perform search and get the result
            SearchResultInfo result = proposalService.search(request, ContextUtils.getContextInfo());
            String resultString = result.getRows().get(0).getCells().get(0).getValue();

            // If there are no retire proposals enroute or in saved status/
            // return false, else return true
            return !"0".equals(resultString);
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void setReqCompNL(StatementTreeViewInfo tree,ContextInfo contextInfo) throws Exception {
        List<StatementTreeViewInfo> statements = tree.getStatements();
        List<ReqComponentInfo> reqComponentInfos = tree.getReqComponents();

         if ((statements != null) && (statements.size() > 0)) {
            // retrieve all statements
            for (StatementTreeViewInfo statement : statements) {
                setReqCompNL(statement,contextInfo); // inside set the children of this statementTreeViewInfo
            }
        } else if ((reqComponentInfos != null) && (reqComponentInfos.size() > 0)) {
            // retrieve all req. component LEAFS
        	for (int i = 0; i < reqComponentInfos.size(); i++) {
        		ReqComponentInfoUi reqUi = RulesUtil.clone(reqComponentInfos.get(i));
        		reqUi.setNaturalLanguageTranslation(statementService.translateReqComponentToNL(reqUi, "KUALI.RULE", "en"));
        		reqUi.setPreviewNaturalLanguageTranslation(statementService.translateReqComponentToNL(reqUi, "KUALI.RULE.PREVIEW", "en"));
        		reqComponentInfos.set(i, reqUi);
        	}
        }
    }

    @Override
	public Boolean isLatestVersion(String versionIndId, Long versionSequenceNumber) throws Exception {
    	//Perform a search to see if there are any new versions of the course that are approved, draft, etc.
        //We don't want to version if there are
        try
        {
            SearchRequestInfo request = new SearchRequestInfo("lu.search.isVersionable");
            request.addParam("lu.queryParam.versionIndId", versionIndId);
            request.addParam("lu.queryParam.sequenceNumber", versionSequenceNumber.toString());
            List<String> states = new ArrayList<String>();
            states.add("Approved");
            states.add("Active");
            states.add("Draft");
            states.add("Superseded");
            request.addParam("lu.queryParam.luOptionalState", states);
            SearchResultInfo result = cluService.search(request, ContextUtils.getContextInfo());

            String resultString = result.getRows().get(0).getCells().get(0).getValue();
            return "0".equals(resultString);
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
	}
    
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

	public void setStateChangeService(
			CourseStateChangeServiceImpl stateChangeService) {
		this.stateChangeService = stateChangeService;
	}

	public void setCluService(CluService cluService) {
		this.cluService = cluService;
	}
	
	public void setProposalService(ProposalService proposalService) {
        this.proposalService = proposalService;
    }
	
}
