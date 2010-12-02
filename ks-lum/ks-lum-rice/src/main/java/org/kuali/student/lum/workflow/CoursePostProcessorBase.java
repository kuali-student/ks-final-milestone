/**
 * 
 */
package org.kuali.student.lum.workflow;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.actiontaken.ActionTakenValue;
import org.kuali.rice.kew.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.postprocessor.IDocumentEvent;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.CourseServiceConstants;
import org.kuali.student.lum.lu.LUConstants;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuServiceConstants;

/**
 * A base post processor class for Course document types in Workflow.
 *
 */
public class CoursePostProcessorBase extends KualiStudentPostProcessorBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CoursePostProcessorBase.class);

    // TODO: switch to courseService so validation is correct
    private CourseService courseService;

    @Override
    protected void processWithdrawActionTaken(ActionTakenEvent actionTakenEvent, ProposalInfo proposalInfo) throws Exception {
        LOG.info("Will set CLU state to '" + LUConstants.LU_STATE_SUBMITTED + "'");
        CourseInfo courseInfo = getCourseService().getCourse(getCourseId(proposalInfo));
        updateCourse(actionTakenEvent, LUConstants.LU_STATE_SUBMITTED, courseInfo);
    }

    @Override
    protected boolean processCustomActionTaken(ActionTakenEvent actionTakenEvent, ActionTakenValue actionTaken, ProposalInfo proposalInfo) throws Exception {
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
        if (StringUtils.equals(KEWConstants.ROUTE_HEADER_SAVED_CD, newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, LUConstants.LU_STATE_DRAFT);
        } else if (KEWConstants.ROUTE_HEADER_CANCEL_CD .equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, LUConstants.LU_STATE_DRAFT);
        } else if (KEWConstants.ROUTE_HEADER_ENROUTE_CD.equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, LUConstants.LU_STATE_SUBMITTED);
        } else if (KEWConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(newWorkflowStatusCode)) {
            /* current requirements state that on a Withdraw (which is a KEW Disapproval) the 
             * CLU state should be submitted so no special handling required here
             */
            return getCourseStateFromNewState(currentCluState, LUConstants.LU_STATE_SUBMITTED);
        } else if (KEWConstants.ROUTE_HEADER_PROCESSED_CD.equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, LUConstants.LU_STATE_APPROVED);
        } else if (KEWConstants.ROUTE_HEADER_EXCEPTION_CD.equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, LUConstants.LU_STATE_SUBMITTED);
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
            if (LUConstants.LU_STATE_APPROVED.equals(courseState) && courseInfo.getVersionInfo().getCurrentVersionStart() == null){

            	// if current version's state is not active then we can set this course as the active course
            	if (!LUConstants.LU_STATE_ACTIVE.equals(getCourseService().getCourse(getCourseService().getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, courseInfo.getVersionInfo().getVersionIndId()).getId()).getState())) { 
            		getCourseService().setCurrentCourseVersion(courseInfo.getId(), null);
            	}
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

}
