package org.kuali.student.core.workflow.ui.server.gwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.dto.DocumentContentDTO;
import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.dto.RouteNodeInstanceDTO;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.rice.StudentIdentityConstants;
import org.kuali.student.core.rice.StudentWorkflowConstants.ActionRequestType;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class WorkflowRpcGwtServlet extends RemoteServiceServlet implements WorkflowRpcService {

	private static final long serialVersionUID = 1L;

	final Logger LOG = Logger.getLogger(WorkflowRpcGwtServlet.class);
	
	private SimpleDocumentActionsWebService simpleDocService;
    private WorkflowUtility workflowUtilityService;
	private IdentityService identityService;
	private PermissionService permissionService;

	@Override
	public Boolean acknowledgeDocumentWithId(String workflowId) throws OperationFailedException {
		try{
			//get a user name
            String username= SecurityUtils.getCurrentUserId();

	        StandardResponse stdResp = getSimpleDocService().acknowledge(workflowId, username, "");

	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new OperationFailedException("Error found acknowledging document: " + stdResp.getErrorMessage());
        	}

		}catch(OperationFailedException e){
            LOG.error("Error acknowledging Document with workflow id:" + workflowId, e);
            throw new OperationFailedException("Could not acknowledge");
		}
        return Boolean.valueOf(true);
	}

	@Override
	public Boolean adhocRequest(String workflowId, String recipientPrincipalId,
			ActionRequestType requestType, String annotation) throws OperationFailedException {

        try {
            //Get a user name
            String username = SecurityUtils.getCurrentUserId();

            String fyiAnnotation = "";
            String approveAnnotation = "";
            String ackAnnotation = "";

            if (ActionRequestType.FYI.equals(requestType)) {
                StandardResponse stdResp = getSimpleDocService().requestAdHocFyiToPrincipal(workflowId,recipientPrincipalId, username, fyiAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new OperationFailedException("Error found in Adhoc FYI: " + stdResp.getErrorMessage());
                }
            }
            if (ActionRequestType.APPROVE.equals(requestType)) {
                StandardResponse stdResp = getSimpleDocService().requestAdHocApproveToPrincipal(workflowId, recipientPrincipalId,username, approveAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new OperationFailedException("Error found in Adhoc Approve: " + stdResp.getErrorMessage());
                }
            }
            if (ActionRequestType.ACKNOWLEDGE.equals(requestType)) {
                StandardResponse stdResp = getSimpleDocService().requestAdHocAckToPrincipal(workflowId,recipientPrincipalId,username, ackAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new OperationFailedException("Error found in Adhoc Ack: " + stdResp.getErrorMessage());
                }
            }

        } catch (Exception e) {
            LOG.error("Error adhoc routing",e);
            throw new OperationFailedException("Could not adhoc route");
        }
        return  Boolean.valueOf(true);
	}

	@Override
	public Boolean approveDocumentWithId(String workflowId) throws OperationFailedException {

		try{
            //get a user name
            String username = SecurityUtils.getCurrentUserId();

            //Lookup the workflowId from the id
//            DocumentDetailDTO docDetail = getWorkflowUtilityService().getDocumentDetail(Long.parseLong(workflowId));
//            if(docDetail==null){
//            	throw new OperationFailedException("Error found getting document. " );
//            }
//            DocumentContentDTO docContent = workflowUtilityService.getDocumentContent(Long.parseLong(workflowId));
//	        StandardResponse stdResp = getSimpleDocService().approve(workflowId, username, docDetail.getDocTitle(), docContent.getApplicationContent(), approveComment);
            StandardResponse stdResp = getSimpleDocService().approve(workflowId, username, null, null, "");
            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new OperationFailedException("Error found approving document: " + stdResp.getErrorMessage());
        	}

		}catch(Exception e){
            LOG.error("Error approving document",e);
            return Boolean.FALSE;
		}
        return Boolean.TRUE;
	}

	@Override
	public Boolean disapproveDocumentWithId(String workflowId) {

		try{
            //get a user name
            String username = SecurityUtils.getCurrentUserId();
	        String disapproveComment = "Disapproved";

	        StandardResponse stdResp = getSimpleDocService().disapprove(workflowId, username, disapproveComment);
	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		LOG.error("Error  disapproving document: " + stdResp.getErrorMessage());
        		return Boolean.FALSE;
        	}
		}catch(Exception e){
            LOG.error("Error disapproving document",e);
            return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public Boolean fyiDocumentWithId(String workflowId) {
		try{
            //get a user name
            String username = SecurityUtils.getCurrentUserId();

	        StandardResponse stdResp = getSimpleDocService().fyi(workflowId, username);
	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		LOG.error("Error FYIing document: " + stdResp.getErrorMessage());
        		return Boolean.FALSE;
        	}
		}catch(Exception e){
            LOG.error("Error FYIing document",e);
            return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public Boolean withdrawDocumentWithId(String workflowId) {
        if(simpleDocService==null){
        	LOG.error("Workflow Service is unavailable");
        	return Boolean.FALSE;
        }

		try{
            String username = SecurityUtils.getCurrentUserId();
			KimPrincipalInfo systemPrincipal = getIdentityService().getPrincipalByPrincipalName(StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
			if (systemPrincipal == null) {
				throw new RuntimeException("Cannot find principal for system user principal name: " + StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
			}
			String annotation = "Document was withdrawn by " + username;

			StandardResponse stdResp = simpleDocService.superUserDisapprove(workflowId, systemPrincipal.getPrincipalId(), null, null, annotation);
	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())) {
        		LOG.error("Error withdrawing document: " + stdResp.getErrorMessage());
        		return Boolean.FALSE;
        	}
		}catch(Exception e){
            LOG.error("Error withdrawing document",e);
            return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean returnDocumentWithId(String workflowId, String nodeName) {

        try{
            //get the current user username
            String username = SecurityUtils.getCurrentUserId();
            StandardResponse stdResp = getSimpleDocService().returnToPreviousNode(workflowId, username, "", nodeName);
            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
                throw new OperationFailedException("Error found approving document: " + stdResp.getErrorMessage());
            }

        }catch(Exception e){
            LOG.error("Error approving document",e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
	}

    public List<String> getPreviousRouteNodeNames(String workflowId) throws OperationFailedException {
        try {
            String[] nodeNames = getWorkflowUtilityService().getPreviousRouteNodeNames(Long.parseLong(workflowId));
            return Arrays.asList(nodeNames);
        } catch (Exception e) {
            LOG.error("Error approving document",e);
            throw new OperationFailedException("Error getting previous node names");
        }
    }

	@Override
	public String getActionsRequested(String workflowId) throws OperationFailedException {
        try{
    		if(null==workflowId || workflowId.isEmpty()){
    			LOG.info("No workflow id was provided.");
    			return "";
    		}

            //get a user name
            String principalId = SecurityUtils.getCurrentUserId();

    		//Build up a string of actions requested from the attribute set.  The actions can be R,W,S,F,A,C,K. examples are "A" "AF" "FCK" "SCA"
            LOG.debug("Calling action requested with user:"+principalId+" and workflowId:" + workflowId);

            Map<String,String> results = new HashMap<String,String>();
            AttributeSet kewActionsRequested = getWorkflowUtilityService().getActionsRequested(principalId, Long.parseLong(workflowId));
            for (String key : kewActionsRequested.keySet()) {
            	if ("true".equalsIgnoreCase(kewActionsRequested.get(key))) {
            		results.put(key,"true");
            	}
            }

            //Use StringBuilder to avoid using string concatenations in the for loop.
            StringBuilder actionsRequestedBuffer = new StringBuilder();

            DocumentDetailDTO docDetail = getWorkflowUtilityService().getDocumentDetail(Long.parseLong(workflowId));

            for(Map.Entry<String,String> entry:results.entrySet()){
            	// if saved or initiated status... must show only 'complete' button
            	if (KEWConstants.ROUTE_HEADER_SAVED_CD.equals(docDetail.getDocRouteStatus()) || KEWConstants.ROUTE_HEADER_INITIATED_CD.equals(docDetail.getDocRouteStatus())) {
            		// show only complete button if complete or approve code in this doc status
            		if ( (KEWConstants.ACTION_REQUEST_COMPLETE_REQ.equals(entry.getKey()) || KEWConstants.ACTION_REQUEST_APPROVE_REQ.equals(entry.getKey())) && ("true".equals(entry.getValue())) ) {
            			actionsRequestedBuffer.append("S");
            		}
            		// if not Complete or Approve code then show the standard buttons
            		else {
    	            	if("true".equals(entry.getValue())){
    	            		actionsRequestedBuffer.append(entry.getKey());
    	            	}
            		}
            	}
            	else {
                	if("true".equals(entry.getValue())){
                		actionsRequestedBuffer.append(entry.getKey());
                        // show the return to previous button if there is a COMPLETE or APPROVE action request
                        if ( (KEWConstants.ACTION_REQUEST_COMPLETE_REQ.equals(entry.getKey()) || KEWConstants.ACTION_REQUEST_APPROVE_REQ.equals(entry.getKey())) && ("true".equals(entry.getValue())) ) {
                            actionsRequestedBuffer.append("R");
                        }
                	}
            	}
            }

            String docTypeName = getWorkflowUtilityService().getDocumentType(docDetail.getDocTypeId()).getName();
            // if user can withdraw document then add withdraw button
            AttributeSet permDetails = new AttributeSet();
            permDetails.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME,docTypeName);
            permDetails.put(StudentIdentityConstants.ROUTE_STATUS_CODE,docDetail.getDocRouteStatus());
            if (getPermissionService().isAuthorizedByTemplateName(principalId, 
            		PermissionType.WITHDRAW.getPermissionNamespace(), 
            		PermissionType.WITHDRAW.getPermissionTemplateName(), permDetails, 
            		new AttributeSet(StudentIdentityConstants.DOCUMENT_NUMBER,workflowId))) {
            	LOG.info("User '" + principalId + "' is allowed to Withdraw the Document");
            	actionsRequestedBuffer.append("W");
            }

            return actionsRequestedBuffer.toString();
        } catch (Exception e) {
        	LOG.error("Error getting actions Requested",e);
            throw new OperationFailedException("Error getting actions Requested");
        }
	}

	@Override
	public String getDocumentStatus(String workflowId)
			throws OperationFailedException {
		if (workflowId != null && !workflowId.isEmpty()){
			try {
				Long documentId = Long.valueOf(workflowId);
				return workflowUtilityService.getDocumentStatus(documentId);
			} catch (Exception e) {
				throw new OperationFailedException("Error getting document status. " + e.getMessage());
			}
		}

		return null;
	}

	@Override
	/**
	 * NOTE: This method may no longer be required if workflow id is stored in the proposal.
	 */
	public String getWorkflowIdFromDataId(String workflowDocType, String dataId) throws OperationFailedException {
		if(null==simpleDocService){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

        DocumentDetailDTO docDetail;
		try {
			docDetail = workflowUtilityService.getDocumentDetailFromAppId(workflowDocType, dataId);
	        if(null==docDetail){
	        	LOG.error("Nothing found for id: "+dataId);
	        	return null;
	        }
	        Long workflowId=docDetail.getRouteHeaderId();
	        return null==workflowId?null:workflowId.toString();
		} catch (Exception e) {
			LOG.error("Call Failed getting workflowId for id: "+dataId, e);
		}
		return null;
	}

	
	@Override
	public String getDataIdFromWorkflowId(String workflowId) throws OperationFailedException {
        String username = SecurityUtils.getCurrentUserId();

        DocumentResponse docResponse = getSimpleDocService().getDocument(workflowId, username);
        if(docResponse==null||StringUtils.isNotBlank(docResponse.getErrorMessage())){
        	throw new OperationFailedException("Error found gettting document: " + docResponse.getErrorMessage());
        }
        
        return docResponse.getAppDocId();
	}

	@Override
	public List<String> getWorkflowNodes(String workflowId)
			throws OperationFailedException {
		List<String> routeNodeNames = new ArrayList<String>();

		Long documentId = Long.valueOf(workflowId);
		try{
			RouteNodeInstanceDTO[] routeNodes = workflowUtilityService.getActiveNodeInstances(documentId);
			if (routeNodes != null){
				for (RouteNodeInstanceDTO routeNodeInstanceDTO : routeNodes) {
					routeNodeNames.add(routeNodeInstanceDTO.getName());
				}
			}

		} catch (Exception e) {
			throw new OperationFailedException(e.getMessage());
		}

		return routeNodeNames;
	}

	@Override
	public Boolean submitDocumentWithId(String workflowId) {
		try {
            if(simpleDocService==null){
            	throw new OperationFailedException("Workflow Service is unavailable");
            }

            //get a user name
            String username = SecurityUtils.getCurrentUserId();

            //Get the workflow ID
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetail(Long.parseLong(workflowId));
            if(docDetail==null){
            	throw new OperationFailedException("Error found getting document. " );
            }
            DocumentContentDTO docContent = workflowUtilityService.getDocumentContent(Long.parseLong(workflowId));

            String routeComment = "Routed";

            StandardResponse stdResp = simpleDocService.route(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), docContent.getApplicationContent(), routeComment);

            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new OperationFailedException("Error found routing document: " + stdResp.getErrorMessage());
        	}

        } catch (Exception e) {
            LOG.error("Error found routing document",e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
	}

	@Override
    public Boolean isAuthorizedAddReviewer(String docId) throws OperationFailedException{
		if (docId != null && (!"".equals(docId.trim()))) {
			AttributeSet permissionDetails = new AttributeSet();
			AttributeSet roleQuals = new AttributeSet();
			roleQuals.put(StudentIdentityConstants.DOCUMENT_NUMBER,docId);
			return Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(SecurityUtils.getCurrentUserId(), PermissionType.ADD_ADHOC_REVIEWER.getPermissionNamespace(), 
					PermissionType.ADD_ADHOC_REVIEWER.getPermissionTemplateName(), permissionDetails, roleQuals));
		}
		return Boolean.FALSE;
    }
	
	public void setSimpleDocService(SimpleDocumentActionsWebService simpleDocService) {
		this.simpleDocService = simpleDocService;
	}
	
	public SimpleDocumentActionsWebService getSimpleDocService() throws OperationFailedException{
		if(simpleDocService==null){
        	throw new OperationFailedException("Workflow Simple Document Service is unavailable");
        }
		
		return simpleDocService;
	}

	public void setWorkflowUtilityService(WorkflowUtility workflowUtilityService) {
		this.workflowUtilityService = workflowUtilityService;
	}

	public WorkflowUtility getWorkflowUtilityService() throws OperationFailedException{
		if(workflowUtilityService==null){
        	throw new OperationFailedException("Workflow Utility Service is unavailable");
        }
		
		return workflowUtilityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public IdentityService getIdentityService() throws OperationFailedException{
		if(identityService==null){
        	throw new OperationFailedException("Identity Service is unavailable");
        }
		
		return identityService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public PermissionService getPermissionService()throws OperationFailedException{
		if(permissionService==null){
        	throw new OperationFailedException("Permission Service is unavailable");
        }

		return permissionService;
	}
}
