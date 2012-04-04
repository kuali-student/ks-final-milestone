/**
 * 
 */
package org.kuali.student.lum.workflow;

import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kew.framework.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.IDocumentEvent;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.lum.lu.LUConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * A base post processor class for Course document types in Workflow.
 *
 */
@Transactional(readOnly=true, rollbackFor={Throwable.class})
public class CoursePostProcessorBase extends KualiStudentPostProcessorBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CoursePostProcessorBase.class);

    private CourseService courseService;
    private CourseStateChangeServiceImpl courseStateChangeService;

    @Override
    protected void processWithdrawActionTaken(ActionTakenEvent actionTakenEvent, ProposalInfo proposalInfo, ContextInfo contextInfo) throws Exception {
        LOG.info("Will set CLU state to '" + DtoConstants.STATE_SUBMITTED + "'");
        CourseInfo courseInfo = getCourseService().getCourse(getCourseId(proposalInfo), contextInfo);
        updateCourse(actionTakenEvent, DtoConstants.STATE_SUBMITTED, courseInfo, contextInfo);
    }

    @Override
    protected boolean processCustomActionTaken(ActionTakenEvent actionTakenEvent, ActionTaken actionTaken, ProposalInfo proposalInfo, ContextInfo contextInfo) throws Exception {
        String cluId = getCourseId(proposalInfo);
        CourseInfo courseInfo = getCourseService().getCourse(cluId, contextInfo);
        updateCourse(actionTakenEvent, null, courseInfo, contextInfo);
        return true;
    }

    @Override
    protected boolean processCustomRouteStatusChange(DocumentRouteStatusChange statusChangeEvent, ProposalInfo proposalInfo, ContextInfo contextInfo) throws Exception {
        // update the course state if the cluState value is not null (allows for clearing of the state)
        String courseId = getCourseId(proposalInfo);
        CourseInfo courseInfo = getCourseService().getCourse(courseId, contextInfo);
        String courseState = getCluStateForRouteStatus(courseInfo.getStateKey(), statusChangeEvent.getNewRouteStatus(), proposalInfo.getTypeKey());
        updateCourse(statusChangeEvent, courseState, courseInfo, contextInfo);
        return true;
    }

    protected String getCourseId(ProposalInfo proposalInfo) throws OperationFailedException {
        if (proposalInfo.getProposalReference().size() != 1) {
            LOG.error("Found " + proposalInfo.getProposalReference().size() + " CLU objects linked to proposal with proposalId='" + proposalInfo.getId() + "'. Must have exactly 1 linked.");
            throw new OperationFailedException("Found " + proposalInfo.getProposalReference().size() + " CLU objects linked to proposal with docId='" + proposalInfo.getWorkflowId() + "' and proposalId='" + proposalInfo.getId() + "'. Must have exactly 1 linked.");
        }
        return proposalInfo.getProposalReference().get(0);
    }

    /**
     * @param currentCluState - the current state set on the CLU
     * @param newWorkflowStatusCode - the new route status code that is getting set on the workflow document
     * @return the CLU state to set or null if the CLU does not need it's state changed
     */
    protected String getCluStateForRouteStatus(String currentCluState, String newWorkflowStatusCode, String docType) {
        if (StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
        } else if (KewApiConstants.ROUTE_HEADER_CANCEL_CD .equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_NOT_APPROVED);
        } else if (KewApiConstants.ROUTE_HEADER_ENROUTE_CD.equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
        } else if (KewApiConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(newWorkflowStatusCode)) {
            /* current requirements state that on a Withdraw (which is a KEW Disapproval) the 
             * CLU state should be submitted so no special handling required here
             */
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_NOT_APPROVED);
        } else if (KewApiConstants.ROUTE_HEADER_PROCESSED_CD.equals(newWorkflowStatusCode)) {
            if (LUConstants.PROPOSAL_TYPE_COURSE_RETIRE.equals(docType)){
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_RETIRED);
            } else {
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_ACTIVE);
            }
        } else if (KewApiConstants.ROUTE_HEADER_EXCEPTION_CD.equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
        } else {
            // no status to set
            return null;
        }
    }

    /**
     * Default behavior is to return the <code>newCluState</code> variable only if it differs from the
     * <code>currentCluState</code> value. Otherwise <code>null</code> will be returned.
     */
    protected String getCourseStateFromNewState(String currentCourseState, String newCourseState) {
        if (LOG.isInfoEnabled()) {
            LOG.info("current CLU state is '" + currentCourseState + "' and new CLU state will be '" + newCourseState + "'");
        }
        return getStateFromNewState(currentCourseState, newCourseState);
    }

    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    protected void updateCourse(IDocumentEvent iDocumentEvent, String courseState, CourseInfo courseInfo, ContextInfo contextInfo) throws Exception {
        // only change the state if the course is not currently set to that state
        boolean requiresSave = false;
        if (courseState != null) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Setting state '" + courseState + "' on CLU with cluId='" + courseInfo.getId() + "'");
            }
            courseInfo.setStateKey(courseState);
            requiresSave = true;
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("Running preProcessCluSave with cluId='" + courseInfo.getId() + "'");
        }
        requiresSave |= preProcessCourseSave(iDocumentEvent, courseInfo);

        if (requiresSave) {
            getCourseService().updateCourse(courseInfo.getId(), courseInfo, contextInfo);
            
            //For a newly approved course (w/no prior active versions), make the new course the current version.
            if (DtoConstants.STATE_ACTIVE.equals(courseState) && courseInfo.getVersionInfo().getCurrentVersionStart() == null){
            	// TODO: set states of other approved courses to superseded                
                
            	// if current version's state is not active then we can set this course as the active course
            	//if (!DtoConstants.STATE_ACTIVE.equals(getCourseService().getCourse(getCourseService().getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, courseInfo.getVersionInfo().getVersionIndId()).getId()).getState())) { 
            		getCourseService().setCurrentCourseVersion(courseInfo.getId(), null, contextInfo);
            	//}
            }
            
            List<StatementTreeViewInfo> statementTreeViewInfos = courseService.getCourseStatements(courseInfo.getId(), null, null, contextInfo);
            if(statementTreeViewInfos!=null){
	            statementTreeViewInfoStateSetter(courseInfo.getStateKey(), statementTreeViewInfos.iterator());
	            
	            for(Iterator<StatementTreeViewInfo> it = statementTreeViewInfos.iterator(); it.hasNext();)

	        		courseService.updateCourseStatement(courseInfo.getId(), courseState, it.next(), contextInfo);
            }
        }
        
    }

    protected boolean preProcessCourseSave(IDocumentEvent iDocumentEvent, CourseInfo courseInfo) {
        return false;
    }

    protected CourseService getCourseService() {
        if (this.courseService == null) {
            this.courseService = (CourseService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/course","CourseService")); 
        }
        return this.courseService;
    }
    protected CourseStateChangeServiceImpl getCourseStateChangeService() {
        if (this.courseStateChangeService == null) {
            this.courseStateChangeService = new CourseStateChangeServiceImpl();
            this.courseStateChangeService.setCourseService(getCourseService());
        }
        return this.courseStateChangeService;
    }    
    /*
     * Recursively set state for StatementTreeViewInfo
     * TODO: We are not able to reuse the code in CourseStateUtil for dependency reason.
     */   
    public void statementTreeViewInfoStateSetter(String courseState, Iterator<StatementTreeViewInfo> itr) {
    	while(itr.hasNext()) {
        	StatementTreeViewInfo statementTreeViewInfo = (StatementTreeViewInfo)itr.next();
        	statementTreeViewInfo.setState(courseState);
        	List<ReqComponentInfo> reqComponents = statementTreeViewInfo.getReqComponents();
        	for(Iterator<ReqComponentInfo> it = reqComponents.iterator(); it.hasNext();)
        		it.next().setState(courseState);

        	statementTreeViewInfoStateSetter(courseState, statementTreeViewInfo.getStatements().iterator());
        }
    }
}
