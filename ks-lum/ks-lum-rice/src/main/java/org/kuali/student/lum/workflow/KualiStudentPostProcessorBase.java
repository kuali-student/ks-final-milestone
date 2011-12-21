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
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.actionrequest.ActionRequestValue;
import org.kuali.rice.kew.actiontaken.ActionTakenValue;
import org.kuali.rice.kew.dto.ActionRequestDTO;
import org.kuali.rice.kew.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.postprocessor.AfterProcessEvent;
import org.kuali.rice.kew.postprocessor.BeforeProcessEvent;
import org.kuali.rice.kew.postprocessor.DeleteEvent;
import org.kuali.rice.kew.postprocessor.DocumentLockingEvent;
import org.kuali.rice.kew.postprocessor.DocumentRouteLevelChange;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.postprocessor.IDocumentEvent;
import org.kuali.rice.kew.postprocessor.PostProcessor;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
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
		ActionTakenValue actionTaken = KEWServiceLocator.getActionTakenService().findByActionTakenId(actionTakenEvent.getActionTaken().getActionTakenId());
		if (actionTaken == null) {
		    if (LOG.isInfoEnabled()) {
		        LOG.info("Could not find valid ActionTakenValue for doc id '" + actionTakenEvent.getRouteHeaderId() + "'" + 
		                ((actionTakenEvent.getActionTaken() == null) ? "" : " for action: " + actionTakenEvent.getActionTaken().getActionTakenLabel()));
		    }
		    actionTaken = actionTakenEvent.getActionTaken();
		}
		boolean success = true;
		// on a save action we may not have access to the proposal object because the transaction may not have committed
		if (!StringUtils.equals(KEWConstants.ROUTE_HEADER_SAVED_CD, actionTaken.getActionTaken())) {
            ProposalInfo proposalInfo = getProposalService().getProposalByWorkflowId(actionTakenEvent.getRouteHeaderId().toString());
            if (actionTaken == null) {
                throw new OperationFailedException("No action taken found for document id " + actionTakenEvent.getRouteHeaderId());
            }
    	    if (StringUtils.equals(KEWConstants.ACTION_TAKEN_SU_DISAPPROVED_CD, actionTaken.getActionTaken())) {
    	        // the custom method below is needed for the unique problem of the states being set for a Withdraw action in KS
    	        processSuperUserDisapproveActionTaken(actionTakenEvent, actionTaken, proposalInfo);
    	    }
            // only attempt to remove the adhoc permission if the action taken was not an adhoc revocation 
    	    else if (!StringUtils.equals(KEWConstants.ACTION_TAKEN_ADHOC_REVOKED_CD, actionTaken.getActionTaken())) {
    			for (ActionRequestValue actionRequest : actionTaken.getActionRequests()) {
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

    protected boolean processCustomActionTaken(ActionTakenEvent actionTakenEvent, ActionTakenValue actionTaken, ProposalInfo proposalInfo) throws Exception {
        // do nothing
        return true;
    }

    protected boolean processCustomSaveActionTaken(ActionTakenEvent actionTakenEvent, ActionTakenValue actionTaken) throws Exception {
        // do nothing
        return true;
    }

    protected void processActionTakenOnAdhocRequest(ActionTakenEvent actionTakenEvent, ActionRequestValue actionRequestValue) throws Exception {
        WorkflowDocument doc = new WorkflowDocument(getPrincipalIdForSystemUser(), actionTakenEvent.getRouteHeaderId());
        LOG.info("Clearing EDIT permissions added via adhoc requests to principal id: " + actionRequestValue.getPrincipalId());
        removeEditAdhocPermissions(actionRequestValue.getPrincipalId(), doc);
    }

    protected void processSuperUserDisapproveActionTaken(ActionTakenEvent actionTakenEvent, ActionTakenValue actionTaken, ProposalInfo proposalInfo) throws Exception {
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
        ProposalInfo proposalInfo = getProposalService().getProposalByWorkflowId(documentRouteLevelChange.getRouteHeaderId().toString());

		// if this is the initial route then clear only edit permissions as per KSLUM-192
		if (StringUtils.equals(StudentWorkflowConstants.DEFAULT_WORKFLOW_DOCUMENT_START_NODE_NAME,documentRouteLevelChange.getOldNodeName())) {
			// remove edit perm for all adhoc action requests to a user for the route node we just exited
	        WorkflowDocument doc = new WorkflowDocument(getPrincipalIdForSystemUser(), documentRouteLevelChange.getRouteHeaderId());
			for (ActionRequestDTO actionRequestDTO : doc.getActionRequests()) {
				if (actionRequestDTO.isAdHocRequest() && actionRequestDTO.isUserRequest() && 
						StringUtils.equals(documentRouteLevelChange.getOldNodeName(),actionRequestDTO.getNodeName())) {
					LOG.info("Clearing EDIT permissions added via adhoc requests to principal id: " + actionRequestDTO.getPrincipalId());
					removeEditAdhocPermissions(actionRequestDTO.getPrincipalId(), doc);
				}
	        }
		}
		else {
			LOG.warn("Will not clear any permissions added via adhoc requests");
		}
		boolean success = processCustomRouteLevelChange(documentRouteLevelChange, proposalInfo);
		return new ProcessDocReport(success);
	}

	protected boolean processCustomRouteLevelChange(
			DocumentRouteLevelChange documentRouteLevelChange,
			ProposalInfo proposalInfo) throws Exception {
		//Update the proposal with the new node name
		proposalInfo.getAttributes().put("workflowNode", documentRouteLevelChange.getNewNodeName());
		getProposalService().updateProposal(proposalInfo.getId(), proposalInfo);
		return true;
	}

	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {
	    boolean success = true;
	    // if document is transitioning from INITIATED to SAVED then transaction prevents us from retrieving the proposal
	    if (StringUtils.equals(KEWConstants.ROUTE_HEADER_INITIATED_CD, statusChangeEvent.getOldRouteStatus()) && 
	            StringUtils.equals(KEWConstants.ROUTE_HEADER_SAVED_CD, statusChangeEvent.getNewRouteStatus())) {
	        // assume the proposal status is already correct
            success = processCustomRouteStatusSavedStatusChange(statusChangeEvent);
	    } else {
            ProposalInfo proposalInfo = getProposalService().getProposalByWorkflowId(statusChangeEvent.getRouteHeaderId().toString());
            
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

	public List<Long> getDocumentIdsToLock(DocumentLockingEvent arg0) throws Exception {
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
        if (StringUtils.equals(KEWConstants.ROUTE_HEADER_SAVED_CD, newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_SAVED);
        } else if (KEWConstants.ROUTE_HEADER_ENROUTE_CD.equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_ENROUTE);
        } else if (KEWConstants.ROUTE_HEADER_CANCEL_CD .equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_CANCELLED);
        } else if (KEWConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_REJECTED);
        } else if (KEWConstants.ROUTE_HEADER_PROCESSED_CD.equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_APPROVED);
        } else if (KEWConstants.ROUTE_HEADER_EXCEPTION_CD.equals(newWorkflowStatusCode)) {
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
        AttributeSet qualifications = new AttributeSet();
        qualifications.put(KualiStudentKimAttributes.DOCUMENT_TYPE_NAME,doc.getDocumentType());
        qualifications.put(KualiStudentKimAttributes.QUALIFICATION_DATA_ID,doc.getAppDocId());
        KIMServiceLocator.getRoleManagementService().removePrincipalFromRole(principalId, StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAME, qualifications);       
    }

    protected void removeCommentAdhocPermissions(String roleNamespace, String roleName, String principalId, WorkflowDocument doc) {
        AttributeSet qualifications = new AttributeSet();
        qualifications.put(KualiStudentKimAttributes.DOCUMENT_TYPE_NAME,doc.getDocumentType());
        qualifications.put(KualiStudentKimAttributes.QUALIFICATION_DATA_ID,doc.getAppDocId());
        KIMServiceLocator.getRoleManagementService().removePrincipalFromRole(principalId, StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAME, qualifications);
    }

    protected String getPrincipalIdForSystemUser() {
        KimPrincipalInfo principal = KIMServiceLocator.getIdentityManagementService().getPrincipalByPrincipalName(StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
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

}
