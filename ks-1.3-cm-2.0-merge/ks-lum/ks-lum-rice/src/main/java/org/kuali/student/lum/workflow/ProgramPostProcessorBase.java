package org.kuali.student.lum.workflow;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kew.framework.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r1.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.ProgramServiceConstants;
import org.kuali.student.r2.lum.program.service.ProgramService;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true, rollbackFor={Throwable.class})
public class ProgramPostProcessorBase extends KualiStudentPostProcessorBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ProgramPostProcessorBase.class);

    private ProgramService programService;
	private StateChangeService stateChangeService;

    @Override
    protected void processWithdrawActionTaken(ActionTakenEvent actionTakenEvent, ProposalInfo proposalInfo) throws Exception {
        LOG.info("Will set CLU state to '" + DtoConstants.STATE_DRAFT + "'");
        String programId = getProgramId(proposalInfo);
        getStateChangeService().changeState(programId, DtoConstants.STATE_DRAFT, ContextUtils.getContextInfo());
    }

    @Override
    protected boolean processCustomActionTaken(ActionTakenEvent actionTakenEvent,  ActionTaken actionTaken, ProposalInfo proposalInfo) throws Exception {
    	//TODO Why is this method implemented in course post processor? it might be important for program as well
        return true;
    }

    @Override
    protected boolean processCustomRouteStatusChange(DocumentRouteStatusChange statusChangeEvent, ProposalInfo proposalInfo) throws Exception {
        // update the program state based on the route status
    	// Mainly used to approve a proposal
        String programId = getProgramId(proposalInfo);
        String endEntryTerm = proposalInfo.getAttributes().get("prevEndProgramEntryTerm");
        String endEnrollTerm = proposalInfo.getAttributes().get("prevEndTerm");
        String endInstAdmitTerm = proposalInfo.getAttributes().get("prevEndInstAdmitTerm");
        getStateChangeService().changeState(endEntryTerm, endEnrollTerm, endInstAdmitTerm, programId, getCluStateForRouteStatus("",statusChangeEvent.getNewRouteStatus()), ContextUtils.getContextInfo());
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
            return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_ACTIVE);
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
            stateChangeService.setAtpService((AtpService)GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/atp","AtpService")));
            this.stateChangeService = stateChangeService;
        }
        
        return this.stateChangeService;
    }
}
