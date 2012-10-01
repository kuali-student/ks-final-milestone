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

package org.kuali.student.lum.workflow;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
//import org.kuali.rice.kew.actionrequest.ActionRequestValue;
//import org.kuali.rice.kew.actiontaken.ActionTakenValue;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.action.ActionRequest;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kew.api.document.WorkflowDocumentService;
import org.kuali.rice.kew.framework.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.framework.postprocessor.AfterProcessEvent;
import org.kuali.rice.kew.framework.postprocessor.BeforeProcessEvent;
import org.kuali.rice.kew.framework.postprocessor.DeleteEvent;
import org.kuali.rice.kew.framework.postprocessor.DocumentLockingEvent;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteLevelChange;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.IDocumentEvent;
import org.kuali.rice.kew.framework.postprocessor.PostProcessor;
import org.kuali.rice.kew.framework.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.student.StudentWorkflowConstants;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.core.proposal.ProposalConstants;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;

public class KualiStudentPostProcessorBase implements PostProcessor{
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(KualiStudentPostProcessorBase.class);

    private ProposalService proposalService;

    public ProcessDocReport afterProcess(AfterProcessEvent arg0) throws Exception {
        return new ProcessDocReport(true);
	}

	public ProcessDocReport beforeProcess(BeforeProcessEvent arg0) throws Exception {
        return new ProcessDocReport(true);
	}

	public ProcessDocReport doActionTaken(ActionTakenEvent actionTakenEvent) throws Exception {
/*		ActionTakenValue actionTaken = KEWServiceLocator.getActionTakenService().findByActionTakenId(actionTakenEvent.getActionTaken().getActionTakenId());
		if (actionTaken == null) {
		    if (LOG.isInfoEnabled()) {
		        LOG.info("Could not find valid ActionTakenValue for doc id '" + actionTakenEvent.getDocumentId() + "'" + 
		                ((actionTakenEvent.getActionTaken() == null) ? "" : " for action: " + actionTakenEvent.getActionTaken().getActionTakenLabel()));
		    }
		    actionTaken = actionTakenEvent.getActionTaken();
		}
*/
		boolean success = true;
        ActionTaken actionTaken = actionTakenEvent.getActionTaken();
        String actionTakeCode = actionTakenEvent.getActionTaken().getActionTaken().getCode();
		// on a save action we may not have access to the proposal object because the transaction may not have committed
		if (!StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, actionTakeCode)) {
            ProposalInfo proposalInfo = getProposalService().getProposalByWorkflowId(actionTakenEvent.getDocumentId().toString());
            if (actionTaken == null) {
                throw new OperationFailedException("No action taken found for document id " + actionTakenEvent.getDocumentId());
            }
    	    if (StringUtils.equals(KewApiConstants.ACTION_TAKEN_SU_DISAPPROVED_CD, actionTakeCode)) {
    	        // the custom method below is needed for the unique problem of the states being set for a Withdraw action in KS
    	        processSuperUserDisapproveActionTaken(actionTakenEvent, actionTaken, proposalInfo);
    	    }
            // only attempt to remove the adhoc permission if the action taken was not an adhoc revocation 
    	    else if (!StringUtils.equals(KewApiConstants.ACTION_TAKEN_ADHOC_REVOKED_CD, actionTakeCode)) {
                List<ActionRequest> actionRequests = getWorkflowDocumentService().getRootActionRequests(actionTakenEvent.getDocumentId());
     			for (ActionRequest actionRequest : actionRequests) {
    		        if (actionRequest.isAdHocRequest() && actionRequest.isUserRequest()) {
    		            processActionTakenOnAdhocRequest(actionTakenEvent, actionRequest);
    		        }
    	        }
            }
            success = processCustomActionTaken(actionTakenEvent, actionTaken, proposalInfo);
		} else {
		    success = processCustomSaveActionTaken(actionTakenEvent, actionTaken);
		}
        return new ProcessDocReport(success);
	}

    protected boolean processCustomActionTaken(ActionTakenEvent actionTakenEvent, ActionTaken actionTaken, ProposalInfo proposalInfo) throws Exception {
        // do nothing
        return true;
    }

    protected boolean processCustomSaveActionTaken(ActionTakenEvent actionTakenEvent, ActionTaken actionTaken) throws Exception {
        // do nothing
        return true;
    }

    protected void processActionTakenOnAdhocRequest(ActionTakenEvent actionTakenEvent, ActionRequest actionRequest) throws Exception {
        WorkflowDocument doc = WorkflowDocumentFactory.createDocument(getPrincipalIdForSystemUser(), actionTakenEvent.getDocumentId());
        LOG.info("Clearing EDIT permissions added via adhoc requests to principal id: " + actionRequest.getPrincipalId());
        removeEditAdhocPermissions(actionRequest.getPrincipalId(), doc);
    }

    protected void processSuperUserDisapproveActionTaken(ActionTakenEvent actionTakenEvent, ActionTaken actionTaken, ProposalInfo proposalInfo) throws Exception {
        LOG.info("Action taken was 'Super User Disapprove' which is a 'Withdraw' in Kuali Student");
        LOG.info("Will set proposal state to '" + ProposalConstants.PROPOSAL_STATE_WITHDRAWN + "'");
        updateProposal(actionTakenEvent, ProposalConstants.PROPOSAL_STATE_WITHDRAWN, proposalInfo);
        processWithdrawActionTaken(actionTakenEvent, proposalInfo);
	}

    protected void processWithdrawActionTaken(ActionTakenEvent actionTakenEvent, ProposalInfo proposalInfo) throws Exception {
        // do nothing but allow for child classes to override
    }

    public ProcessDocReport doDeleteRouteHeader(DeleteEvent arg0) throws Exception {
        return new ProcessDocReport(true);
	}

	public ProcessDocReport doRouteLevelChange(DocumentRouteLevelChange documentRouteLevelChange) throws Exception {
        ProposalInfo proposalInfo = getProposalService().getProposalByWorkflowId(documentRouteLevelChange.getDocumentId());

		// if this is the initial route then clear only edit permissions as per KSLUM-192
		if (StringUtils.equals(StudentWorkflowConstants.DEFAULT_WORKFLOW_DOCUMENT_START_NODE_NAME,documentRouteLevelChange.getOldNodeName())) {
			// remove edit perm for all adhoc action requests to a user for the route node we just exited
	        WorkflowDocument doc = WorkflowDocumentFactory.createDocument(getPrincipalIdForSystemUser(), documentRouteLevelChange.getDocumentId());
                // TODO: evaluate group or role level changes by not using isUserRequest()
			for (ActionRequest actionRequest : doc.getRootActionRequests()) {
				if (actionRequest.isAdHocRequest() && actionRequest.isUserRequest() && 
						StringUtils.equals(documentRouteLevelChange.getOldNodeName(),actionRequest.getNodeName())) {
					LOG.info("Clearing EDIT permissions added via adhoc requests to principal id: " + actionRequest.getPrincipalId());
					removeEditAdhocPermissions(actionRequest.getPrincipalId(), doc);
				}
	        }
		}
		else {
			LOG.warn("Will not clear any permissions added via adhoc requests");
		}
		boolean success = processCustomRouteLevelChange(documentRouteLevelChange, proposalInfo);
		return new ProcessDocReport(success);
	}

	protected boolean processCustomRouteLevelChange(DocumentRouteLevelChange documentRouteLevelChange, ProposalInfo proposalInfo) throws Exception {
	    // do nothing but allow override
	    return true;
	}

	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {
	    boolean success = true;
	    // if document is transitioning from INITIATED to SAVED then transaction prevents us from retrieving the proposal
	    if (StringUtils.equals(KewApiConstants.ROUTE_HEADER_INITIATED_CD, statusChangeEvent.getOldRouteStatus()) &&
	            StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, statusChangeEvent.getNewRouteStatus())) {
	        // assume the proposal status is already correct
            success = processCustomRouteStatusSavedStatusChange(statusChangeEvent);
	    } else {
            ProposalInfo proposalInfo = getProposalService().getProposalByWorkflowId(statusChangeEvent.getDocumentId());
            
            // update the proposal state if the proposalState value is not null (allows for clearing of the state)
            String proposalState = getProposalStateForRouteStatus(proposalInfo.getState(), statusChangeEvent.getNewRouteStatus());
            updateProposal(statusChangeEvent, proposalState, proposalInfo);
            success = processCustomRouteStatusChange(statusChangeEvent, proposalInfo);
	    }
        return new ProcessDocReport(success);
	}

	protected boolean processCustomRouteStatusChange(DocumentRouteStatusChange statusChangeEvent, ProposalInfo proposalInfo) throws Exception {
	    // do nothing but allow override
	    return true;
	}

    protected boolean processCustomRouteStatusSavedStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {
        // do nothing but allow override
        return true;
    }

	public List<String> getDocumentIdsToLock(DocumentLockingEvent arg0) throws Exception {
		return null;
	}

    /**
     * @param currentProposalState - the current state set on the proposal
     * @param newWorkflowStatusCode - the new route status code that is getting set on the workflow document
     * @return the proposal state to set or null if the proposal does not need it's state changed
     */
    protected String getProposalStateForRouteStatus(String currentProposalState, String newWorkflowStatusCode) {
        if (StringUtils.equals(ProposalConstants.PROPOSAL_STATE_WITHDRAWN, currentProposalState)) {
            // if the current proposal state is Withdrawn... don't change the proposal state
            return null;
        }
        if (StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_SAVED);
        } else if (KewApiConstants.ROUTE_HEADER_ENROUTE_CD.equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_ENROUTE);
        } else if (KewApiConstants.ROUTE_HEADER_CANCEL_CD .equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_CANCELLED);
        } else if (KewApiConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_REJECTED);
        } else if (KewApiConstants.ROUTE_HEADER_PROCESSED_CD.equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_APPROVED);
        } else if (KewApiConstants.ROUTE_HEADER_EXCEPTION_CD.equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_EXCEPTION);
        } else {
            // no status to set
            return null;
        }
    }

    /**
     * Default behavior is to return the <code>newProposalState</code> variable only if it differs from the
     * <code>currentProposalState</code> value. Otherwise <code>null</code> will be returned.
     */
    protected String getProposalStateFromNewState(String currentProposalState, String newProposalState) {
        if (LOG.isInfoEnabled()) {
            LOG.info("current proposal state is '" + currentProposalState + "' and new proposal state will be '" + newProposalState + "'");
        }
        return getStateFromNewState(currentProposalState, newProposalState);
    }

    /**
     * Default behavior is to return the <code>newState</code> variable only if it differs from the
     * <code>currentState</code> value. Otherwise <code>null</code> will be returned.
     */
    protected String getStateFromNewState(String currentState, String newState) {
        if (StringUtils.equals(currentState, newState)) {
            if (LOG.isInfoEnabled()) {
                LOG.info("returning null as current state and new state are both '" + currentState + "'");
            }
            return null;
        }
        return newState;
    }

    protected void removeEditAdhocPermissions(String principalId, WorkflowDocument doc) {
        Map<String,String> qualifications = new LinkedHashMap<String,String>();
        qualifications.put(KualiStudentKimAttributes.DOCUMENT_TYPE_NAME,doc.getDocumentTypeName());
        qualifications.put(KualiStudentKimAttributes.QUALIFICATION_DATA_ID,doc.getApplicationDocumentId());
        KimApiServiceLocator.getRoleService().removePrincipalFromRole(principalId, StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAME, qualifications);       
    }

    protected void removeCommentAdhocPermissions(String roleNamespace, String roleName, String principalId, WorkflowDocument doc) {
        Map<String,String> qualifications = new LinkedHashMap<String,String>();
        qualifications.put(KualiStudentKimAttributes.DOCUMENT_TYPE_NAME,doc.getDocumentTypeName());
        qualifications.put(KualiStudentKimAttributes.QUALIFICATION_DATA_ID,doc.getApplicationDocumentId());
        KimApiServiceLocator.getRoleService().removePrincipalFromRole(principalId, StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAME, qualifications);
    }

    protected String getPrincipalIdForSystemUser() {
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
        if (principal == null) {
            throw new RuntimeException("Cannot find Principal for principal name: " + StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
        }
        return principal.getPrincipalId();
    }

    protected void updateProposal(IDocumentEvent iDocumentEvent, String proposalState, ProposalInfo proposalInfo) throws Exception {
        if (LOG.isInfoEnabled()) {
            LOG.info("Setting state '" + proposalState + "' on Proposal with docId='" + proposalInfo.getWorkflowId() + "' and proposalId='" + proposalInfo.getId() + "'");
        }
        boolean requiresSave = false;
        if (proposalState != null) {
            proposalInfo.setState(proposalState);
            requiresSave = true;
        }
        requiresSave |= preProcessProposalSave(iDocumentEvent, proposalInfo);
        if (requiresSave) {
            getProposalService().updateProposal(proposalInfo.getId(), proposalInfo);
        }
    }

    protected boolean preProcessProposalSave(IDocumentEvent iDocumentEvent, ProposalInfo proposalInfo) {
        return false;
    }

    protected ProposalService getProposalService() {
        if (this.proposalService == null) {
            this.proposalService = (ProposalService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/proposal","ProposalService"));
        }
        return this.proposalService;
    }

    protected WorkflowDocumentService getWorkflowDocumentService() {
		return KewApiServiceLocator.getWorkflowDocumentService();
	}

}
