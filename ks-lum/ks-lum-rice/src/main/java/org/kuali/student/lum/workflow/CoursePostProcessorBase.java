/**
 * 
 */
package org.kuali.student.lum.workflow;

import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
//import org.kuali.rice.kew.actiontaken.ActionTakenValue;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kew.framework.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.IDocumentEvent;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.CourseServiceConstants;
import org.springframework.transaction.annotation.Transactional;

/**
 * A base post processor class for Course document types in Workflow.
 *
 */
@Transactional(readOnly=true, rollbackFor={Throwable.class})
public class CoursePostProcessorBase extends KualiStudentPostProcessorBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CoursePostProcessorBase.class);

    private CourseService courseService;

    @Override
    protected void processWithdrawActionTaken(ActionTakenEvent actionTakenEvent, ProposalInfo proposalInfo) throws Exception {
        LOG.info("Will set CLU state to '" + DtoConstants.STATE_SUBMITTED + "'");
        CourseInfo courseInfo = getCourseService().getCourse(getCourseId(proposalInfo));
        updateCourse(actionTakenEvent, DtoConstants.STATE_SUBMITTED, courseInfo);
    }

    @Override
    protected boolean processCustomActionTaken(ActionTakenEvent actionTakenEvent, ActionTaken actionTaken, ProposalInfo proposalInfo) throws Exception {
        String cluId = getCourseId(proposalInfo);
        CourseInfo courseInfo = getCourseService().getCourse(cluId);
        updateCourse(actionTakenEvent, null, courseInfo);
        return true;
    }

    @Override
    protected boolean processCustomRouteStatusChange(DocumentRouteStatusChange statusChangeEvent, ProposalInfo proposalInfo) throws Exception {
        // update the course state if the cluState value is not null (allows for clearing of the state)
        String courseId = getCourseId(proposalInfo);
        CourseInfo courseInfo = getCourseService().getCourse(courseId);
        String courseState = getCluStateForRouteStatus(courseInfo.getState(), statusChangeEvent.getNewRouteStatus());
        updateCourse(statusChangeEvent, courseState, courseInfo);
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
    protected String getCluStateForRouteStatus(String currentCluState, String newWorkflowStatusCode) {
        if (StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
        } else if (KewApiConstants.ROUTE_HEADER_CANCEL_CD .equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
        } else if (KewApiConstants.ROUTE_HEADER_ENROUTE_CD.equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_SUBMITTED);
        } else if (KewApiConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(newWorkflowStatusCode)) {
            /* current requirements state that on a Withdraw (which is a KEW Disapproval) the 
             * CLU state should be submitted so no special handling required here
             */
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_SUBMITTED);
        } else if (KewApiConstants.ROUTE_HEADER_PROCESSED_CD.equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_APPROVED);
        } else if (KewApiConstants.ROUTE_HEADER_EXCEPTION_CD.equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_SUBMITTED);
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

    @Transactional(readOnly=false)
    protected void updateCourse(IDocumentEvent iDocumentEvent, String courseState, CourseInfo courseInfo) throws Exception {
        // only change the state if the course is not currently set to that state
        boolean requiresSave = false;
        if (courseState != null) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Setting state '" + courseState + "' on CLU with cluId='" + courseInfo.getId() + "'");
            }
            courseInfo.setState(courseState);
            requiresSave = true;
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("Running preProcessCluSave with cluId='" + courseInfo.getId() + "'");
        }
        requiresSave |= preProcessCourseSave(iDocumentEvent, courseInfo);

        if (requiresSave) {
            getCourseService().updateCourse(courseInfo);
            
            //For a newly approved course (w/no prior active versions), make the new course the current version.
            if (DtoConstants.STATE_APPROVED.equals(courseState) && courseInfo.getVersionInfo().getCurrentVersionStart() == null){
            	// TODO: set states of other approved courses to superseded                
                
            	// if current version's state is not active then we can set this course as the active course
            	if (!DtoConstants.STATE_ACTIVE.equals(getCourseService().getCourse(getCourseService().getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, courseInfo.getVersionInfo().getVersionIndId()).getId()).getState())) { 
            		getCourseService().setCurrentCourseVersion(courseInfo.getId(), null);
            	}
            }
            
            List<StatementTreeViewInfo> statementTreeViewInfos = courseService.getCourseStatements(courseInfo.getId(), null, null);
            
            statementTreeViewInfoStateSetter(courseInfo.getState(), statementTreeViewInfos.iterator());
            
            for(Iterator<StatementTreeViewInfo> it = statementTreeViewInfos.iterator(); it.hasNext();)
        		courseService.updateCourseStatement(courseInfo.getId(), it.next());
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
