package org.kuali.student.core.workflow.ui.server.gwt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.action.ActionRequestType;
import org.kuali.rice.kew.api.action.DocumentActionParameters;
import org.kuali.rice.kew.api.action.ReturnPoint;
import org.kuali.rice.kew.api.action.WorkflowDocumentActionsService;
import org.kuali.rice.kew.api.doctype.DocumentType;
import org.kuali.rice.kew.api.doctype.DocumentTypeService;
import org.kuali.rice.kew.api.document.Document;
import org.kuali.rice.kew.api.document.DocumentContent;
import org.kuali.rice.kew.api.document.DocumentContentUpdate;
import org.kuali.rice.kew.api.document.DocumentDetail;
import org.kuali.rice.kew.api.document.DocumentUpdate;
import org.kuali.rice.kew.api.document.WorkflowDocumentService;
import org.kuali.rice.kew.api.document.node.RouteNodeInstance;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.common.rice.authorization.PermissionType;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class WorkflowRpcGwtServlet extends RemoteServiceServlet implements WorkflowRpcService {

	private static final long serialVersionUID = 1L;

	final Logger LOG = Logger.getLogger(WorkflowRpcGwtServlet.class);
	
	private WorkflowDocumentActionsService workflowDocumentActionsService;
    private WorkflowDocumentService workflowDocumentService;
    private DocumentTypeService documentTypeService;
	private IdentityService identityService;
	private PermissionService permissionService;

    public static final String WORKFLOW_DOCUMENT_ACTION_ACKNOWLEGE = "Acknowlege";
    public static final String WORKFLOW_DOCUMENT_ACTION_APPROVE = "Approve";
    public static final String WORKFLOW_DOCUMENT_ACTION_BLANKET_APPROVE = "Blanket Approve";
    public static final String WORKFLOW_DOCUMENT_ACTION_CANCEL = "Cancel";
    public static final String WORKFLOW_DOCUMENT_ACTION_DISAPPROVE = "Disapprove";
    public static final String WORKFLOW_DOCUMENT_ACTION_FYI = "FYI";
    public static final String WORKFLOW_DOCUMENT_ACTION_RETURN = "Return";
    public static final String WORKFLOW_DOCUMENT_ACTION_SUBMIT = "Submit";
    public static final String WORKFLOW_DOCUMENT_ACTION_WITHDRAW = "Withdraw";



    // targetNode should be null except for returnToPrevious or other node directed actions
    public Boolean performWorkflowDocumentAction(String action, String workflowId, String targetNodeName) throws OperationFailedException {
        try{
			String annotation = action;

			// get the current user name
            String username = SecurityUtils.getCurrentUserId();

            // for all actions that require super user add an or condition here
            if (StringUtils.equals(action,WORKFLOW_DOCUMENT_ACTION_WITHDRAW)) {
                Principal systemPrincipal = null;
                try {
                    systemPrincipal = getIdentityService().getPrincipalByPrincipalName(StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
                } catch (OperationFailedException ex) {
                    java.util.logging.Logger.getLogger(WorkflowRpcGwtServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (systemPrincipal == null) {
                    throw new RuntimeException("Cannot find principal for system user principal name: " + StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
                }
                annotation = action + " Document performed as SuperUser by " + username;
                username = systemPrincipal.getPrincipalId();
            }


            DocumentActionParameters.Builder dapBuilder = DocumentActionParameters.Builder.create(workflowId, username);

            // for all actions that require populating the document info and content add an or condition here
            if (StringUtils.equals(action,WORKFLOW_DOCUMENT_ACTION_SUBMIT)) {
                           //Get the workflow ID
                DocumentDetail docDetail = getWorkflowDocumentService().getDocumentDetail(workflowId);
                if(docDetail==null){
                    throw new OperationFailedException("Error found getting document. " );
                }
                DocumentContent docContent = workflowDocumentService.getDocumentContent(workflowId);
                DocumentUpdate docUpdate = DocumentUpdate.Builder.create(docDetail.getDocument()).build();
                dapBuilder.setDocumentUpdate(docUpdate);
                DocumentContentUpdate docContentUpdate = DocumentContentUpdate.Builder.create(docContent).build();
                dapBuilder.setDocumentContentUpdate(docContentUpdate);
            }
            dapBuilder.setAnnotation(annotation);
            DocumentActionParameters docActionParams = dapBuilder.build();

	        if(StringUtils.equals(action,WORKFLOW_DOCUMENT_ACTION_ACKNOWLEGE)) {
                getWorkflowDocumentActionsService().acknowledge(docActionParams);
            } else if (StringUtils.equals(action,WORKFLOW_DOCUMENT_ACTION_APPROVE)) {
                getWorkflowDocumentActionsService().approve(docActionParams);
            } else if (StringUtils.equals(action,WORKFLOW_DOCUMENT_ACTION_BLANKET_APPROVE)) {
                getWorkflowDocumentActionsService().blanketApprove(docActionParams);
            } else if (StringUtils.equals(action,WORKFLOW_DOCUMENT_ACTION_CANCEL)) {
                getWorkflowDocumentActionsService().cancel(docActionParams);
            } else if (StringUtils.equals(action,WORKFLOW_DOCUMENT_ACTION_DISAPPROVE)) {
                getWorkflowDocumentActionsService().disapprove(docActionParams);
            } else if (StringUtils.equals(action,WORKFLOW_DOCUMENT_ACTION_FYI)) {
                getWorkflowDocumentActionsService().clearFyi(docActionParams);
            } else if (StringUtils.equals(action,WORKFLOW_DOCUMENT_ACTION_RETURN)) {
                ReturnPoint returnPoint = ReturnPoint.create(targetNodeName);
                getWorkflowDocumentActionsService().returnToPreviousNode(docActionParams, returnPoint);
            } else if (StringUtils.equals(action,WORKFLOW_DOCUMENT_ACTION_WITHDRAW)) {
                getWorkflowDocumentActionsService().superUserDisapprove(docActionParams,true);
            } else if (StringUtils.equals(action, WORKFLOW_DOCUMENT_ACTION_SUBMIT)) {
                getWorkflowDocumentActionsService().route(docActionParams);
            } else {
                throw new OperationFailedException("Invalid Action requested:" + action);
            }

		} catch(Exception e){
            LOG.error("Error attempting to " + action + " Document with workflow id:" + workflowId, e);
            throw new OperationFailedException("Could not " + action + " Document");
		}
        return Boolean.valueOf(true);
    }

	@Override
	public Boolean acknowledgeDocumentWithId(String workflowId) throws OperationFailedException {
		return performWorkflowDocumentAction(WORKFLOW_DOCUMENT_ACTION_ACKNOWLEGE, workflowId, null);
	}

	@Override
	public Boolean approveDocumentWithId(String workflowId) throws OperationFailedException {
		return performWorkflowDocumentAction(WORKFLOW_DOCUMENT_ACTION_APPROVE, workflowId, null);
	}

    @Override
    public Boolean blanketApproveDocumentWithId(String workflowId) throws OperationFailedException {
		return performWorkflowDocumentAction(WORKFLOW_DOCUMENT_ACTION_BLANKET_APPROVE, workflowId, null);
    }

	@Override
	public Boolean disapproveDocumentWithId(String workflowId) throws OperationFailedException {
		return performWorkflowDocumentAction(WORKFLOW_DOCUMENT_ACTION_DISAPPROVE, workflowId, null);
	}

	@Override
	public Boolean fyiDocumentWithId(String workflowId) throws OperationFailedException {
		return performWorkflowDocumentAction(WORKFLOW_DOCUMENT_ACTION_FYI, workflowId, null);
    }

	@Override
	public Boolean withdrawDocumentWithId(String workflowId) throws OperationFailedException {
		return performWorkflowDocumentAction(WORKFLOW_DOCUMENT_ACTION_WITHDRAW, workflowId, null);
    }
	
	@Override
	public Boolean returnDocumentWithId(String workflowId, String nodeName) throws OperationFailedException {
		return performWorkflowDocumentAction(WORKFLOW_DOCUMENT_ACTION_RETURN, workflowId, null);
	}
    @Override
    public Boolean cancelDocumentWithId(String workflowId) throws OperationFailedException {
		return performWorkflowDocumentAction(WORKFLOW_DOCUMENT_ACTION_CANCEL, workflowId, null);
    }

    @Override
	public Boolean submitDocumentWithId(String workflowId) throws OperationFailedException {
		return performWorkflowDocumentAction(WORKFLOW_DOCUMENT_ACTION_SUBMIT, workflowId, null);
	}



    public List<String> getPreviousRouteNodeNames(String workflowId) throws OperationFailedException {
        try {
            return getWorkflowDocumentService().getPreviousRouteNodeNames(workflowId);
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

            Set<ActionRequestType> actionRequestTypes = getWorkflowDocumentActionsService().determineRequestedActions(workflowId, principalId).getRequestedActions();            

            //Use StringBuilder to avoid using string concatenations in the for loop.
            StringBuilder actionsRequestedBuffer = new StringBuilder();

            DocumentDetail docDetail = getWorkflowDocumentService().getDocumentDetail(workflowId);

            for(ActionRequestType actionRequestType : actionRequestTypes){
            	// if saved or initiated status... must show only 'complete' button
            	if (StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, docDetail.getDocument().getStatus().getCode()) || StringUtils.equals(KewApiConstants.ROUTE_HEADER_INITIATED_CD, docDetail.getDocument().getStatus().getCode())) {
            		// show only complete button if complete or approve code in this doc status
            		if ( actionRequestType.equals(ActionRequestType.COMPLETE) || actionRequestType.equals(ActionRequestType.APPROVE) ) {
            			actionsRequestedBuffer.append("S");
                        actionsRequestedBuffer.append("C");
            		}
            		// if not Complete or Approve code then show the standard buttons
            		else {
                        actionsRequestedBuffer.append(actionRequestType.getCode());
    	            	}
            		}
            	else {
                    actionsRequestedBuffer.append(actionRequestType.getCode());
                        // show the return to previous button if there is a COMPLETE or APPROVE action request
                    if ( actionRequestType.equals(ActionRequestType.COMPLETE) || actionRequestType.equals(ActionRequestType.APPROVE) ) {
                            actionsRequestedBuffer.append("R");
                        }
                	}
            	}
            String docTypeName = getDocumentTypeService().getDocumentTypeById(docDetail.getDocument().getDocumentTypeId()).getName();
            // if user can withdraw document then add withdraw button
            Map<String,String> permDetails = new LinkedHashMap<String,String>();
            permDetails.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME,docTypeName);
            permDetails.put(StudentIdentityConstants.ROUTE_STATUS_CODE,docDetail.getDocument().getStatus().getCode());
            Map<String,String> workflowDetails = new LinkedHashMap<String,String> ();
            workflowDetails.put (StudentIdentityConstants.DOCUMENT_NUMBER,workflowId);
            if (getPermissionService().isAuthorizedByTemplate(principalId, 
            		PermissionType.WITHDRAW.getPermissionNamespace(), 
            		PermissionType.WITHDRAW.getPermissionTemplateName(), 
                        permDetails, 
            		workflowDetails)) {
            	LOG.info("User '" + principalId + "' is allowed to Withdraw the Document");
            	actionsRequestedBuffer.append("W");
            }

            Map<String,String> permDetails2 = new HashMap<String,String>();
            permDetails2.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME,docTypeName);
            permDetails2.put(StudentIdentityConstants.ROUTE_STATUS_CODE,docDetail.getDocument().getStatus().getCode());
            // first check permission with no node name
            Map<String,String> qualifiers = new LinkedHashMap ();
            qualifiers.put (StudentIdentityConstants.DOCUMENT_NUMBER,workflowId);
            boolean canBlanketApprove = getPermissionService().isAuthorizedByTemplate(principalId, 
                    PermissionType.BLANKET_APPROVE.getPermissionNamespace(), 
                    PermissionType.BLANKET_APPROVE.getPermissionTemplateName(), new LinkedHashMap<String,String>(permDetails2), 
                    qualifiers);
            for (String nodeName : getCurrentActiveNodeNames(docDetail.getDocument().getStatus().getCode())) {
                if (canBlanketApprove) {
                    break;
                }
                Map<String,String> newSet = new LinkedHashMap<String,String>(permDetails2);
                newSet.put(StudentIdentityConstants.ROUTE_NODE_NAME, nodeName);
                qualifiers = new LinkedHashMap ();
                qualifiers.put (StudentIdentityConstants.DOCUMENT_NUMBER,workflowId);
                canBlanketApprove = getPermissionService().isAuthorizedByTemplate(principalId, 
                        PermissionType.BLANKET_APPROVE.getPermissionNamespace(), 
                        PermissionType.BLANKET_APPROVE.getPermissionTemplateName(), newSet, 
                        qualifiers);
            }
            if (canBlanketApprove) {
                LOG.info("User '" + principalId + "' is allowed to Blanket Approve the Document");
                actionsRequestedBuffer.append("B");
            }

            return actionsRequestedBuffer.toString();
        } catch (Exception e) {
        	LOG.error("Error getting actions Requested",e);
            throw new OperationFailedException("Error getting actions Requested");
        }
	}

	protected List<String> getCurrentActiveNodeNames(String routeHeaderId) throws OperationFailedException, WorkflowException {
        List<String> currentActiveNodeNames = new ArrayList<String>();
        List<RouteNodeInstance> nodeInstances = getWorkflowDocumentService().getActiveRouteNodeInstances(routeHeaderId);
        if (null != nodeInstances) {
            for (RouteNodeInstance routeNodeInstance : nodeInstances) {
                currentActiveNodeNames.add(routeNodeInstance.getName());
            }
        }
        return currentActiveNodeNames;
	}

	@Override
	public String getDocumentStatus(String workflowId)
			throws OperationFailedException {
		if (workflowId != null && !workflowId.isEmpty()){
			try {
				return workflowDocumentService.getDocumentStatus(workflowId).getCode();
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
		if(null== workflowDocumentActionsService){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

        DocumentDetail docDetail;
		try {
			docDetail = workflowDocumentService.getDocumentDetailByAppId(workflowDocType, dataId);
	        if(null==docDetail){
	        	LOG.error("Nothing found for id: "+dataId);
	        	return null;
	        }
	        return docDetail.getDocument().getDocumentId();
		} catch (Exception e) {
			LOG.error("Call Failed getting workflowId for id: "+dataId, e);
		}
		return null;
	}

	
	@Override
    public String getDataIdFromWorkflowId(String workflowId) throws OperationFailedException {
        try
        {
            String username = SecurityUtils.getCurrentUserId();
            Document docResponse = getWorkflowDocumentService().getDocument(workflowId);

            return docResponse.getApplicationDocumentId();
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

	@Override
	public List<String> getWorkflowNodes(String workflowId)
			throws OperationFailedException {
		List<String> routeNodeNames = new ArrayList<String>();

		try{
			List<RouteNodeInstance> routeNodes = workflowDocumentService.getActiveRouteNodeInstances(workflowId);
			if (routeNodes != null){
				for (RouteNodeInstance routeNodeInstance : routeNodes) {
					routeNodeNames.add(routeNodeInstance.getName());
				}
			}

		} catch (Exception e) {
			throw new OperationFailedException(e.getMessage());
		}

		return routeNodeNames;
	}



	@Override
    public Boolean isAuthorizedAddReviewer(String docId) throws OperationFailedException {
        try
        {
            if (docId != null && (!"".equals(docId.trim()))) {
                Map<String, String> permissionDetails = new LinkedHashMap<String, String>();
                Map<String, String> roleQuals = new LinkedHashMap<String, String>();
                roleQuals.put(StudentIdentityConstants.DOCUMENT_NUMBER, docId);
                return Boolean.valueOf(getPermissionService().isAuthorizedByTemplate(SecurityUtils.getCurrentUserId(),
                        PermissionType.ADD_ADHOC_REVIEWER.getPermissionNamespace(),
                        PermissionType.ADD_ADHOC_REVIEWER.getPermissionTemplateName(), permissionDetails, roleQuals));
            }
            return Boolean.FALSE;
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

	public Boolean isAuthorizedRemoveReviewers(String docId) throws OperationFailedException {
	    try {
            if (docId != null && (!"".equals(docId.trim()))) {
                DocumentDetail docDetail = getWorkflowDocumentService().getDocumentDetail(docId);
                DocumentType docType = getDocumentTypeService().getDocumentTypeById(docDetail.getDocument().getDocumentTypeId());
                Map<String,String> permissionDetails = new LinkedHashMap<String,String>();
                permissionDetails.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME,docType.getName());
                Map<String,String> roleQuals = new LinkedHashMap<String,String>();
                roleQuals.put(StudentIdentityConstants.DOCUMENT_NUMBER,docId);
                boolean returnValue = getPermissionService().isAuthorizedByTemplate(SecurityUtils.getCurrentUserId(), PermissionType.REMOVE_ADHOC_REVIEWERS.getPermissionNamespace(), 
                        PermissionType.REMOVE_ADHOC_REVIEWERS.getPermissionTemplateName(), permissionDetails, roleQuals);
                return Boolean.valueOf(returnValue);
            }
            return Boolean.FALSE;
	    } catch (Exception e) {
	        LOG.error("Unable to get document information from Workflow for doc id " + docId);
	        throw new OperationFailedException("Unable to get document information from Workflow for doc id " + docId);
	    }
	}
	
	public void setWorkflowDocumentActionsService(WorkflowDocumentActionsService workflowDocumentActionsService) {
		this.workflowDocumentActionsService = workflowDocumentActionsService;
	}
	
	public WorkflowDocumentActionsService getWorkflowDocumentActionsService() throws OperationFailedException{
		if(workflowDocumentActionsService ==null){
        	throw new OperationFailedException("Workflow Simple Document Service is unavailable");
        }
		
		return workflowDocumentActionsService;
	}

	public void setWorkflowDocumentService(WorkflowDocumentService workflowDocumentService) {
		this.workflowDocumentService = workflowDocumentService;
	}

	public WorkflowDocumentService getWorkflowDocumentService() throws OperationFailedException {
		if(workflowDocumentService ==null){
        	throw new OperationFailedException("Workflow Document Service is unavailable");
        }
		
		return workflowDocumentService;
	}


    public DocumentTypeService getDocumentTypeService() throws OperationFailedException {
        if(documentTypeService ==null){
        	throw new OperationFailedException("Document Type Service is unavailable");
        }

        return documentTypeService;
    }

    public void setDocumentTypeService(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
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
