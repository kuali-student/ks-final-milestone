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
import org.kuali.student.lum.lu.LUConstants;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.service.LuServiceConstants;

/**
 * A base post processor class for Course document types in Workflow.
 *
 */
public class CoursePostProcessorBase extends KualiStudentPostProcessorBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CoursePostProcessorBase.class);

    
    private LuService luService;

    @Override
    protected void processWithdrawActionTaken(ActionTakenEvent actionTakenEvent, ProposalInfo proposalInfo) throws Exception {
        LOG.info("Will set CLU state to '" + LUConstants.LU_STATE_SUBMITTED + "'");
        CluInfo cluInfo = getLuService().getClu(getCluId(proposalInfo));
        updateClu(actionTakenEvent, LUConstants.LU_STATE_SUBMITTED, cluInfo);
    }

    @Override
    protected boolean processCustomActionTaken(ActionTakenEvent actionTakenEvent, ActionTakenValue actionTaken, ProposalInfo proposalInfo) throws Exception {
        String cluId = getCluId(proposalInfo);
        CluInfo cluInfo = getLuService().getClu(cluId);
        updateClu(actionTakenEvent, null, cluInfo);
        return true;
    }

    @Override
    protected boolean processCustomRouteStatusChange(DocumentRouteStatusChange statusChangeEvent, ProposalInfo proposalInfo) throws Exception {
        // update the clu state if the cluState value is not null (allows for clearing of the state)
        String cluId = getCluId(proposalInfo);
        CluInfo cluInfo = getLuService().getClu(cluId);
        String cluState = getCluStateForRouteStatus(cluInfo.getState(), statusChangeEvent.getNewRouteStatus());
        updateClu(statusChangeEvent, cluState, cluInfo);
        return true;
    }

    protected String getCluId(ProposalInfo proposalInfo) throws OperationFailedException {
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
            return getCluStateFromNewState(currentCluState, LUConstants.LU_STATE_DRAFT);
        } else if (KEWConstants.ROUTE_HEADER_ENROUTE_CD.equals(newWorkflowStatusCode)) {
            return getCluStateFromNewState(currentCluState, LUConstants.LU_STATE_SUBMITTED);
        } else if (KEWConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(newWorkflowStatusCode)) {
            /* current requirements state that on a Withdraw (which is a KEW Disapproval) the 
             * CLU state should be submitted so no special handling required here
             */
            return getCluStateFromNewState(currentCluState, LUConstants.LU_STATE_SUBMITTED);
        } else if (KEWConstants.ROUTE_HEADER_PROCESSED_CD.equals(newWorkflowStatusCode)) {
            return getCluStateFromNewState(currentCluState, LUConstants.LU_STATE_APPROVED);
        } else if (KEWConstants.ROUTE_HEADER_EXCEPTION_CD.equals(newWorkflowStatusCode)) {
            return getCluStateFromNewState(currentCluState, LUConstants.LU_STATE_SUBMITTED);
        } else {
            // no status to set
            return null;
        }
    }

    /**
     * Default behavior is to return the <code>newCluState</code> variable only if it differs from the
     * <code>currentCluState</code> value. Otherwise <code>null</code> will be returned.
     */
    protected String getCluStateFromNewState(String currentCluState, String newCluState) {
        if (LOG.isInfoEnabled()) {
            LOG.info("current CLU state is '" + currentCluState + "' and new CLU state will be '" + newCluState + "'");
        }
        return getStateFromNewState(currentCluState, newCluState);
    }

    protected void updateClu(IDocumentEvent iDocumentEvent, String cluState, CluInfo cluInfo) throws Exception {
        // only change the state if the clu is not currently set to that state
        boolean requiresSave = false;
        if (cluState != null) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Setting state '" + cluState + "' on CLU with cluId='" + cluInfo.getId() + "'");
            }
            cluInfo.setState(cluState);
            requiresSave = true;
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("Running preProcessCluSave with cluId='" + cluInfo.getId() + "'");
        }
        requiresSave |= preProcessCluSave(iDocumentEvent, cluInfo);
        if (requiresSave) {
            getLuService().updateClu(cluInfo.getId(), cluInfo);
            
            //For a newly approved clu (w/no prior active versions), make the new clu the current version.
            if (LUConstants.LU_STATE_APPROVED.equals(cluState) && cluInfo.getVersionInfo().getCurrentVersionStart() == null){

            	// if current version's state is not active then we can set this course as the active course
            	if (!LUConstants.LU_STATE_ACTIVE.equals(getLuService().getClu(getLuService().getCurrentVersion(LuServiceConstants.CLU_NAMESPACE_URI, cluInfo.getVersionInfo().getVersionIndId()).getId()).getState())) { 
            		getLuService().setCurrentCluVersion(cluInfo.getId(), null);
            	}
            }
        }

    }

    protected boolean preProcessCluSave(IDocumentEvent iDocumentEvent, CluInfo cluInfo) {
        return false;
    }

    protected LuService getLuService() {
        if (this.luService == null) {
            this.luService = (LuService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lu","LuService")); 
        }
        return this.luService;
    }

}
