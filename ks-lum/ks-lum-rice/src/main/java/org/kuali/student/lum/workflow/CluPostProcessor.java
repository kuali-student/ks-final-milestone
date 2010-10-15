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
import java.security.InvalidParameterException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.actionrequest.ActionRequestValue;
import org.kuali.rice.kew.actiontaken.ActionTakenValue;
import org.kuali.rice.kew.dto.ActionRequestDTO;
import org.kuali.rice.kew.dto.DocumentContentDTO;
import org.kuali.rice.kew.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.postprocessor.AfterProcessEvent;
import org.kuali.rice.kew.postprocessor.BeforeProcessEvent;
import org.kuali.rice.kew.postprocessor.DeleteEvent;
import org.kuali.rice.kew.postprocessor.DocumentLockingEvent;
import org.kuali.rice.kew.postprocessor.DocumentRouteLevelChange;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.postprocessor.PostProcessor;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kew.service.WorkflowInfo;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.student.StudentWorkflowConstants;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.rice.StudentIdentityConstants;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
public class CluPostProcessor implements PostProcessor{
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CluPostProcessor.class);

	private static final String CLU_STATE_ACTIVE = "Active";

    public ProcessDocReport afterProcess(AfterProcessEvent arg0) throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport beforeProcess(BeforeProcessEvent arg0) throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doActionTaken(ActionTakenEvent actionTakenEvent) throws Exception {
		WorkflowDocument doc = new WorkflowDocument(getPrincipalIdForSystemUser(), actionTakenEvent.getRouteHeaderId());
		ActionTakenValue actionTaken = KEWServiceLocator.getActionTakenService().findByActionTakenId(actionTakenEvent.getActionTaken().getActionTakenId());
		if (actionTaken != null) {
		    // only attempt to remove the adhoc permission if the action taken was not an adhoc revocation 
	        if (!StringUtils.equals(KEWConstants.ACTION_TAKEN_ADHOC_REVOKED_CD, actionTaken.getActionTaken())) {
    			for (ActionRequestValue actionRequest : actionTaken.getActionRequests()) {
    		        if (actionRequest.isAdHocRequest() && actionRequest.isUserRequest()) {
    					LOG.info("Clearing EDIT permissions added via adhoc requests to principal id: " + actionRequest.getPrincipalId());
    					removeEditAdhocPermissions(actionRequest.getPrincipalId(), doc);
    		        }
    	        }
	        }
		}
		else {
			LOG.warn("Could not find valid ActionTakenValue for doc id '" + actionTakenEvent.getRouteHeaderId() + "'" + 
					((actionTakenEvent.getActionTaken() == null) ? "" : " for action: " + actionTakenEvent.getActionTaken().getActionTakenLabel()));
		}
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doDeleteRouteHeader(DeleteEvent arg0) throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doRouteLevelChange(DocumentRouteLevelChange documentRouteLevelChange) throws Exception {
		WorkflowDocument doc = new WorkflowDocument(getPrincipalIdForSystemUser(), documentRouteLevelChange.getRouteHeaderId());

		// if this is the initial route then clear only edit permissions as per KSLUM-192
		if (StringUtils.equals(StudentWorkflowConstants.DEFAULT_WORKFLOW_DOCUMENT_START_NODE_NAME,documentRouteLevelChange.getOldNodeName())) {
			// remove edit perm for all adhoc action requests to a user for the route node we just exited
			for (ActionRequestDTO actionRequestDTO : doc.getActionRequests()) {
				if (actionRequestDTO.isAdHocRequest() && actionRequestDTO.isUserRequest() && 
						StringUtils.equals(documentRouteLevelChange.getOldNodeName(),actionRequestDTO.getNodeName())) {
					LOG.info("Clearing EDIT permissions added via adhoc requests to principal id: " + actionRequestDTO.getPrincipalId());
					removeEditAdhocPermissions(actionRequestDTO.getPrincipalId(), doc);
				}
	        }
		}
		else {
			LOG.info("Will not clear any permissions added via adhoc requests");
		}

//		//Clear all the current Collab FYIs on the current document
//		Collection<ActionItem> actionItems = KEWServiceLocator.getActionListService().getActionListForSingleDocument(routeHeaderId);
//        Map<Long,Long> fyisToClear = new HashMap<Long,Long>();
//		for (Iterator<ActionItem> iterator = actionItems.iterator(); iterator.hasNext();) {
//            ActionItem item = iterator.next();
//            if(KEWConstants.ACTION_REQUEST_FYI_REQ.equals(item.getActionRequestCd())&&item.getRequestLabel()!=null
//            		&&(item.getRequestLabel().startsWith("Co-Author")||
//            		   item.getRequestLabel().startsWith("Commentor")||
//            		   item.getRequestLabel().startsWith("Viewer")||
//            		   item.getRequestLabel().startsWith("Delegate"))){
//            	fyisToClear.put(item.getActionRequestId(), item.getRouteHeaderId());
//            }
//        }
//		for(Map.Entry<Long, Long> fyiToClear:fyisToClear.entrySet()){
//    		WorkflowDocument workflowDocument = new WorkflowDocument(KS_SYS_PRINCIPAL, fyiToClear.getValue());
//    		workflowDocument.superUserActionRequestApprove(fyiToClear.getKey(), "Cleared by KS because CluProposal status has changed");
//		}
//		
//		//Clear all pending Collab request documents for this document
//		Collection<Long> pendingCollabIds = KEWServiceLocator.getRouteHeaderService().findByDocTypeAndAppId("CluCollaboratorDocument", routeHeaderId.toString());
//		if(pendingCollabIds!=null){
//			Set<Long> uniquePendingCollagIds = new HashSet<Long>(pendingCollabIds);
//        	for(Long pendingCollabId:uniquePendingCollagIds){
//        		WorkflowDocument workflowDocument = new WorkflowDocument(KS_SYS_PRINCIPAL, pendingCollabId);
//        		if (workflowDocument.stateIsInitiated() || workflowDocument.stateIsSaved() || 
//        			workflowDocument.stateIsEnroute() || workflowDocument.stateIsException()) {
////         		String routeStatus = workflowDocument.getRouteHeader().getDocRouteStatus();
////        		if(!KEWConstants.ROUTE_HEADER_FINAL_CD.equals(routeStatus) &&
////        		   !KEWConstants.ROUTE_HEADER_APPROVED_CD.equals(routeStatus) &&
////        		   !KEWConstants.ROUTE_HEADER_PROCESSED_CD.equals(routeStatus) &&
////        		   !KEWConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(routeStatus)){
//        			workflowDocument.superUserDisapprove("Collaboration request has been revoked because CluProposal status has changed");
//        		}
//        	}
//        }
		return new ProcessDocReport(true, "");
	}

	private void removeEditAdhocPermissions(String principalId, WorkflowDocument doc) {
    	AttributeSet qualifications = new AttributeSet();
    	qualifications.put(KualiStudentKimAttributes.DOCUMENT_TYPE_NAME,doc.getDocumentType());
    	qualifications.put(KualiStudentKimAttributes.QUALIFICATION_DATA_ID,doc.getAppDocId());
        KIMServiceLocator.getRoleManagementService().removePrincipalFromRole(principalId, StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAME, qualifications);		
	}

	private void removeCommentAdhocPermissions(String roleNamespace, String roleName, String principalId, WorkflowDocument doc) {
    	AttributeSet qualifications = new AttributeSet();
    	qualifications.put(KualiStudentKimAttributes.DOCUMENT_TYPE_NAME,doc.getDocumentType());
    	qualifications.put(KualiStudentKimAttributes.QUALIFICATION_DATA_ID,doc.getAppDocId());
        KIMServiceLocator.getRoleManagementService().removePrincipalFromRole(principalId, StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAME, qualifications);
	}

	private String getPrincipalIdForSystemUser() {
		KimPrincipalInfo principal = KIMServiceLocator.getIdentityManagementService().getPrincipalByPrincipalName(StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
		if (principal == null) {
			throw new RuntimeException("Cannot find Principal for principal name: " + StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
		}
		return principal.getPrincipalId();
	}

	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {
		if (statusChangeEvent.getNewRouteStatus().equals(KEWConstants.ROUTE_HEADER_APPROVED_CD)) {
			LOG.info("CluApprovalPostProcessor: Status change to APPROVED");
			WorkflowInfo workflowInfo = new WorkflowInfo();
			DocumentContentDTO document = workflowInfo.getDocumentContent(statusChangeEvent.getRouteHeaderId());
			updateCluStatus(document);	
		}
        return new ProcessDocReport(true, "");
	}

	public List<Long> getDocumentIdsToLock(DocumentLockingEvent arg0) throws Exception {
		return null;
	}

    private void updateCluStatus(DocumentContentDTO document) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, org.kuali.student.core.exceptions.InvalidParameterException, VersionMismatchException{
    	LuService luService = (LuService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lu","LuService"));
    	
    	Pattern pattern = Pattern.compile("<cluId>\\s*([^<\\s]+)");
		Matcher matcher = pattern.matcher(document.getApplicationContent());
		matcher.find();
		String cluId = matcher.group(1);

		try {
			CluInfo clu = luService.getClu(cluId);
	    	clu.setState(CLU_STATE_ACTIVE);
			luService.updateClu(cluId, clu);
			luService.setCurrentCluVersion(cluId, null);
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
    }
}
