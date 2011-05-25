package org.kuali.student.lum.workflow;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.actiontaken.ActionTakenValue;
import org.kuali.rice.kew.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.lum.program.server.MajorDisciplineStateChangeServiceImpl;
import org.kuali.student.lum.program.server.StateChangeService;
import org.kuali.student.lum.program.service.ProgramService;
import org.kuali.student.lum.program.service.ProgramServiceConstants;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true, rollbackFor={Throwable.class})
public class ProgramPostProcessorBase extends KualiStudentPostProcessorBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ProgramPostProcessorBase.class);

    private ProgramService programService;
	private StateChangeService stateChangeService;

    @Override
    protected void processWithdrawActionTaken(ActionTakenEvent actionTakenEvent, ProposalInfo proposalInfo) throws Exception {
        LOG.info("Will set CLU state to '" + DtoConstants.STATE_SUBMITTED + "'");
        String programId = getProgramId(proposalInfo);
        stateChangeService.changeState(programId, DtoConstants.STATE_SUBMITTED);
    }

    @Override
    protected boolean processCustomActionTaken(ActionTakenEvent actionTakenEvent, ActionTakenValue actionTaken, ProposalInfo proposalInfo) throws Exception {
    	//TODO Why is this method implemented in course post processor? it might be important for program as well
        return true;
    }

    @Override
    protected boolean processCustomRouteStatusChange(DocumentRouteStatusChange statusChangeEvent, ProposalInfo proposalInfo) throws Exception {
        // update the program state based on the route status
    	// Mainly used to approve a proposal
        String programId = getProgramId(proposalInfo);
       	stateChangeService.changeState(programId, getCluStateForRouteStatus("",statusChangeEvent.getNewRouteStatus()));
        return true;
    }

    protected String getProgramId(ProposalInfo proposalInfo) throws OperationFailedException {
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
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
        } else if (KEWConstants.ROUTE_HEADER_CANCEL_CD .equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
        } else if (KEWConstants.ROUTE_HEADER_ENROUTE_CD.equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_SUBMITTED);
        } else if (KEWConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(newWorkflowStatusCode)) {
            /* current requirements state that on a Withdraw (which is a KEW Disapproval) the 
             * CLU state should be submitted so no special handling required here
             */
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_SUBMITTED);
        } else if (KEWConstants.ROUTE_HEADER_PROCESSED_CD.equals(newWorkflowStatusCode)) {
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_APPROVED);
        } else if (KEWConstants.ROUTE_HEADER_EXCEPTION_CD.equals(newWorkflowStatusCode)) {
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


    protected ProgramService getProgramService() {
        if (this.programService == null) {
            this.programService = (ProgramService) GlobalResourceLoader.getService(new QName(ProgramServiceConstants.PROGRAM_NAMESPACE,"ProgramService")); 
        }
        return this.programService;
    }
    
    protected StateChangeService getStateChangeService() {
        if (this.stateChangeService == null) {
        	MajorDisciplineStateChangeServiceImpl stateChangeService = new MajorDisciplineStateChangeServiceImpl();
            stateChangeService.setProgramService(getProgramService());
        	this.stateChangeService = stateChangeService;
        }
        
        return this.stateChangeService;
    }

}
