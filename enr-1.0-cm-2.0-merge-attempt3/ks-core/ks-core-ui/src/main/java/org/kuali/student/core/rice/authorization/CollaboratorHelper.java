package org.kuali.student.core.rice.authorization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.action.ActionRequest;
import org.kuali.rice.kew.api.action.ActionRequestStatus;
import org.kuali.rice.kew.api.action.ActionRequestType;
import org.kuali.rice.kew.api.action.AdHocToPrincipal;
import org.kuali.rice.kew.api.action.DocumentActionParameters;
import org.kuali.rice.kew.api.action.DocumentActionResult;
import org.kuali.rice.kew.api.action.WorkflowDocumentActionsService;
import org.kuali.rice.kew.api.doctype.DocumentType;
import org.kuali.rice.kew.api.doctype.DocumentTypeService;
import org.kuali.rice.kew.api.document.DocumentDetail;
import org.kuali.rice.kew.api.document.WorkflowDocumentService;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.common.rice.StudentWorkflowConstants;
import org.kuali.student.r1.common.rice.authorization.PermissionType;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.r1.core.workflow.dto.WorkflowPersonInfo;

public class CollaboratorHelper implements Serializable {
	protected IdentityService identityService;
	protected RoleService roleService;
	private WorkflowDocumentActionsService workflowDocumentActionsService;
    private WorkflowDocumentService workflowDocumentService;
    private DocumentTypeService documentTypeService;
	private PermissionService permissionService;
	
	private static final long serialVersionUID = 1L;
	final static Logger LOG = Logger.getLogger(CollaboratorHelper.class);
	
    public Boolean addCollaborator(String docId, String dataId, String dataTitle, String recipientPrincipalId, String selectedPermissionCode, String actionRequestTypeCode, boolean participationRequired, String respondBy) throws OperationFailedException {
        if(getWorkflowDocumentActionsService()==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		//get a user name
        String currentUserPrincipalId = SecurityUtils.getCurrentUserId();

        StudentWorkflowConstants.ActionRequestType actionRequestEnum = StudentWorkflowConstants.ActionRequestType.getByCode(actionRequestTypeCode);
        if (actionRequestEnum == null) {
        	throw new OperationFailedException("No valid action request type found for code: " + actionRequestTypeCode);
        }
        
        DocumentActionParameters docActionParams = DocumentActionParameters.Builder.create(docId, currentUserPrincipalId).build();
        ActionRequestType actionRequestType = ActionRequestType.fromCode(actionRequestTypeCode);
        AdHocToPrincipal.Builder ahtpBuilder = AdHocToPrincipal.Builder.create(actionRequestType, null, recipientPrincipalId);
        ahtpBuilder.setForceAction(true);

        DocumentActionResult stdResp = null;
        try {
            if (StudentWorkflowConstants.ActionRequestType.APPROVE.equals(actionRequestEnum)) {
                ahtpBuilder.setResponsibilityDescription(KewApiConstants.ACTION_REQUEST_APPROVE_REQ_LABEL);
                stdResp = workflowDocumentActionsService.adHocToPrincipal(docActionParams, ahtpBuilder.build());
            } else if (StudentWorkflowConstants.ActionRequestType.ACKNOWLEDGE.equals(actionRequestEnum)) {
                ahtpBuilder.setResponsibilityDescription(KewApiConstants.ACTION_REQUEST_ACKNOWLEDGE_REQ_LABEL);
                stdResp = workflowDocumentActionsService.adHocToPrincipal(docActionParams, ahtpBuilder.build());
            } else if (StudentWorkflowConstants.ActionRequestType.FYI.equals(actionRequestEnum)) {
                ahtpBuilder.setResponsibilityDescription(KewApiConstants.ACTION_REQUEST_FYI_REQ_LABEL);
                stdResp = workflowDocumentActionsService.adHocToPrincipal(docActionParams, ahtpBuilder.build());
            } else {
                throw new OperationFailedException("Invalid action request type '"
                        + actionRequestEnum.getActionRequestLabel() + "'");
            }
        } catch (RuntimeException e) {
            if (e.getMessage() == null) {
                throw new OperationFailedException("Error found in Collab Adhoc Request ("
                        + actionRequestType.getLabel() + ")");
            } else {
                throw new OperationFailedException("Error found in Collab Adhoc Request ("
                        + actionRequestType.getLabel() + "): " + e.getMessage().trim());
            }
        }        

        PermissionType selectedPermType = PermissionType.getByCode(selectedPermissionCode);
        if (selectedPermType == null) {
        	throw new OperationFailedException("No valid permission type found for code: " + selectedPermissionCode);
        }
        try {
//            List<KimRoleInfo> matchingRoles = new ArrayList<KimRoleInfo>();
//            List<KimPermissionInfo> permissions = getPermissionService().getPermissionsByTemplateName(selectedPermType.getPermissionNamespace(), selectedPermType.getPermissionTemplateName());
//            List<String> roleIds = getPermissionService().getRoleIdsForPermissions(permissions);
//            RoleService roleService;
//            List<KimRoleInfo> roles = getRoleService().getRoles(roleIds);
            if (PermissionType.EDIT.equals(selectedPermType)) {
            	addRoleMember(StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAME, docId, dataId, recipientPrincipalId);       	
            }
            else if (PermissionType.ADD_COMMENT.equals(selectedPermType)) {
            	addRoleMember(StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAME, docId, dataId, recipientPrincipalId);
            }
            return Boolean.TRUE;
        } catch (WorkflowException e) {
            LOG.error("Error adding principal id to adhoc permission roles.",e);
            throw new OperationFailedException("Error adding principal id to adhoc permission roles",e);
        }
    }
    
    public Boolean removeCollaborator(String docId, String dataId, String actionRequestId) throws OperationFailedException {
        //get the current user
        String currentUserPrincipalId = SecurityUtils.getCurrentUserId();

        try {
            String recipientPrincipalId = null;
            List<ActionRequest> actionRequests = getWorkflowDocumentService().getRootActionRequests(docId);
            for (ActionRequest actionRequest : actionRequests) {
                if (actionRequestId.equals(actionRequest.getId().toString())) {
                    recipientPrincipalId = actionRequest.getPrincipalId();
                    break;
                }
            }
            if (recipientPrincipalId == null) {
                throw new OperationFailedException("Unable to find Principal ID for action request id: " + actionRequestId);
            }
            DocumentActionParameters docActionParams = DocumentActionParameters.Builder.create(docId, currentUserPrincipalId).build();
            try {
                DocumentActionResult stdResp = getWorkflowDocumentActionsService().revokeAdHocRequestById(
                        docActionParams, actionRequestId);
            } catch (RuntimeException e) {
                throw new OperationFailedException("Error found trying to remove collaborator");
            }
            
            // remove principal from edit permission
            removeRoleMemberIfNeccesary(StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAME, docId, dataId, recipientPrincipalId);
            // remove principal from comment permission
            removeRoleMemberIfNeccesary(StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAME, docId, dataId, recipientPrincipalId);
            return Boolean.TRUE;
        } catch (WorkflowException e) {
            LOG.error("Error getting actions Requested for principal id fetch.",e);
            throw new OperationFailedException("Error getting actions Requested for principal id fetch",e);
        }
    }
    
    public List<WorkflowPersonInfo> getCollaborators(String docId, String dataId, String docType) throws OperationFailedException{
		//Check if there is no doc id
    	if(docId==null){
			return Collections.<WorkflowPersonInfo>emptyList();
		}
    	try{
			LOG.info("Getting collaborators for docId: "+docId);

	        if(getWorkflowDocumentService()==null){
	        	LOG.error("No workflow Utility Service is available.");
	        	throw new OperationFailedException("Workflow Service is unavailable");
	        }
			
			List<WorkflowPersonInfo> people = new ArrayList<WorkflowPersonInfo>();
			
			Map<String,String> qualification = new LinkedHashMap<String,String>();
			qualification.put("documentNumber", docId);
			qualification.put(StudentIdentityConstants.QUALIFICATION_DATA_ID, dataId);
            qualification.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, docType);
            
			List<ActionRequest> actionRequests = getWorkflowDocumentService().getRootActionRequests(docId);
	        if(actionRequests!=null){
	        	for(ActionRequest actionRequest :actionRequests){
	        		if (actionRequest.isAdHocRequest()) {
	                    // if action request is complete and action taken was a 'revoke action' we do not want to show the person
	                    if (actionRequest.isDone() && (actionRequest.getActionTaken() != null) && KewApiConstants.ACTION_TAKEN_ADHOC_REVOKED_CD.equals(actionRequest.getActionTaken().getActionTaken())) {
	                        continue;
	                    }
	                    
	        			WorkflowPersonInfo person = new WorkflowPersonInfo();
	        			person.setPrincipalId(actionRequest.getPrincipalId());

	        			EntityDefault info =  getIdentityService().getEntityDefaultByPrincipalId(actionRequest.getPrincipalId());

                        EntityName name = info.getName();
	        			if(info != null && name != null){
	        				person.setFirstName(name.getFirstName());
	        				person.setLastName(name.getLastName());
	        			}
	        			
	        			Map<String,String> permissionDetails = new LinkedHashMap<String,String>();
	        			boolean editAuthorized = Boolean.valueOf(getPermissionService().isAuthorizedByTemplate(actionRequest.getPrincipalId(), PermissionType.EDIT.getPermissionNamespace(),
	        					PermissionType.EDIT.getPermissionTemplateName(), permissionDetails, qualification));
	        			boolean openAuthorized = Boolean.valueOf(getPermissionService().isAuthorizedByTemplate(actionRequest.getPrincipalId(), PermissionType.OPEN.getPermissionNamespace(),
	        					PermissionType.OPEN.getPermissionTemplateName(), permissionDetails, qualification));
	        			boolean commentAuthorized = Boolean.valueOf(getPermissionService().isAuthorizedByTemplate(actionRequest.getPrincipalId(), PermissionType.ADD_COMMENT.getPermissionNamespace(),
	        					PermissionType.ADD_COMMENT.getPermissionTemplateName(), permissionDetails, qualification));

	        			if(editAuthorized){
	        				person.setPermission(PermissionType.EDIT.getCode());
	        			} else if (commentAuthorized){
	        				person.setPermission(PermissionType.ADD_COMMENT.getCode());
	        			} else if (openAuthorized){
	        				person.setPermission(PermissionType.OPEN.getCode());
	        			}

        				ActionRequestType requestType = actionRequest.getActionRequested();
	        			person.setAction(requestType.getCode());

	        			person.setActionRequestStatus(actionRequest.getStatus().getLabel());
	        			person.setActionRequestId(actionRequest.getId());

	        			if (!actionRequest.isDone()) {
	        				person.setCanRevokeRequest(true);
	        			}
	        			people.add(person);
	        		}
	        	}
	        }
	
	        LOG.info("Returning collaborators: "+ people.toString());
	        return people;
		}catch(Exception e){
			LOG.error("Error getting actions Requested.",e);
            throw new OperationFailedException("Error getting actions Requested",e);
		}
    }
    
    private String getActionRequestStatusLabel(String key) {
        Map<String,String> newArStatusLabels = new HashMap<String,String>();
        newArStatusLabels.put(ActionRequestStatus.ACTIVATED.getCode(), "Active");
        newArStatusLabels.put(ActionRequestStatus.INITIALIZED.getCode(), "Pending");
        newArStatusLabels.put(ActionRequestStatus.DONE.getCode(), "Completed");
        return newArStatusLabels.get(key);
    }
	
	private void addRoleMember(String roleNamespace, String roleName, String docId, String dataId, String recipientPrincipalId) throws OperationFailedException, org.kuali.rice.kew.api.exception.WorkflowException {
    	DocumentDetail docDetail = getWorkflowDocumentService().getDocumentDetail(docId);
    	DocumentType docType = getDocumentTypeService().getDocumentTypeById(docDetail.getDocument().getDocumentTypeId());
    	Map<String,String> roleMemberQuals = new LinkedHashMap<String,String>();
    	roleMemberQuals.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME,docType.getName());
    	roleMemberQuals.put(StudentIdentityConstants.QUALIFICATION_DATA_ID,dataId);
    	getRoleService().assignPrincipalToRole(recipientPrincipalId, roleNamespace, roleName, roleMemberQuals);
	}

	private void removeRoleMemberIfNeccesary(String roleNamespace, String roleName, String docId, String dataId, String recipientPrincipalId) throws OperationFailedException, org.kuali.rice.kew.api.exception.WorkflowException {
        DocumentDetail docDetail = getWorkflowDocumentService().getDocumentDetail(docId);
        DocumentType docType = getDocumentTypeService().getDocumentTypeById(docDetail.getDocument().getDocumentTypeId());
        Map<String,String> roleMemberQuals = new LinkedHashMap<String,String>();
        roleMemberQuals.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME,docType.getName());
        roleMemberQuals.put(StudentIdentityConstants.QUALIFICATION_DATA_ID,dataId);
	    getRoleService().removePrincipalFromRole(recipientPrincipalId, roleNamespace, roleName, roleMemberQuals);
	}

	public Boolean isAuthorizedAddReviewer(String docId) throws OperationFailedException {
		if (docId != null && (!"".equals(docId.trim()))) {
			Map<String,String> permissionDetails = new LinkedHashMap<String,String>();
			Map<String,String> roleQuals = new LinkedHashMap<String,String>();
			roleQuals.put(StudentIdentityConstants.DOCUMENT_NUMBER,docId);
			return Boolean.valueOf(getPermissionService().isAuthorizedByTemplate(SecurityUtils.getCurrentUserId(), PermissionType.ADD_ADHOC_REVIEWER.getPermissionNamespace(), 
					PermissionType.ADD_ADHOC_REVIEWER.getPermissionTemplateName(), permissionDetails, roleQuals));
		}
		return Boolean.FALSE;
    }
	
	public IdentityService getIdentityService() throws OperationFailedException {
	    if (identityService == null) {
	        throw new OperationFailedException("unable to find valid identityService");
	    }
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}
	
	public RoleService getRoleService() throws OperationFailedException {
        if (roleService == null) {
            throw new OperationFailedException("unable to find valid roleService");
        }
    	return roleService;
    }

	public void setRoleService(RoleService roleService) {
    	this.roleService = roleService;
    }

	public WorkflowDocumentActionsService getWorkflowDocumentActionsService() throws OperationFailedException {
        if (workflowDocumentActionsService == null) {
            throw new OperationFailedException("unable to find valid workflowDocumentActionsService");
        }
		return workflowDocumentActionsService;
	}

	public void setWorkflowDocumentActionsService(WorkflowDocumentActionsService workflowDocumentActionsService) {
		this.workflowDocumentActionsService = workflowDocumentActionsService;
	}


	public WorkflowDocumentService getWorkflowDocumentService() throws OperationFailedException {
        if (workflowDocumentService == null) {
            throw new OperationFailedException("unable to find valid workflowDocumentService");
        }
		return workflowDocumentService;
	}

	public void setWorkflowDocumentService(WorkflowDocumentService workflowDocumentService) {
		this.workflowDocumentService = workflowDocumentService;
	}


    public DocumentTypeService getDocumentTypeService() throws OperationFailedException {
        if (documentTypeService == null) {
            throw new OperationFailedException("unable to find valid documentTypeService");
        }
		return documentTypeService;
	}

	public void setDocumentTypeService(DocumentTypeService documentTypeService) {
		this.documentTypeService = documentTypeService;
	}

	public PermissionService getPermissionService() throws OperationFailedException {
        if (permissionService == null) {
            throw new OperationFailedException("unable to find valid permissionService");
        }
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
}
