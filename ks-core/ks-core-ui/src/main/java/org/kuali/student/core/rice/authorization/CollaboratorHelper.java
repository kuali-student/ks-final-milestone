package org.kuali.student.core.rice.authorization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.dto.ActionRequestDTO;
import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.dto.DocumentTypeDTO;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.rice.kim.bo.entity.dto.KimEntityDefaultInfo;
import org.kuali.rice.core.util.AttributeSet;
import org.kuali.rice.kim.api.services.IdentityManagementService;
import org.kuali.rice.kim.service.RoleUpdateService;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.common.rice.StudentWorkflowConstants;
import org.kuali.student.common.rice.StudentWorkflowConstants.ActionRequestType;
import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.workflow.dto.WorkflowPersonInfo;

public class CollaboratorHelper implements Serializable {
	protected IdentityManagementService identityService;
	protected RoleUpdateService roleUpdateService;
	private SimpleDocumentActionsWebService simpleDocService;
    private WorkflowUtility workflowUtilityService;
	private IdentityManagementService permissionService;
	
	private static final long serialVersionUID = 1L;
	final static Logger LOG = Logger.getLogger(CollaboratorHelper.class);
	
    public Boolean addCollaborator(String docId, String dataId, String dataTitle, String recipientPrincipalId, String selectedPermissionCode, String actionRequestTypeCode, boolean participationRequired, String respondBy) throws OperationFailedException {
        if(getSimpleDocService()==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		//get a user name
        String currentUserPrincipalId = SecurityUtils.getCurrentUserId();

        ActionRequestType actionRequestType = ActionRequestType.getByCode(actionRequestTypeCode);
        if (actionRequestType == null) {
        	throw new OperationFailedException("No valid action request type found for code: " + actionRequestTypeCode);
        }
        StandardResponse stdResp = null;
        if (ActionRequestType.APPROVE.equals(actionRequestType)) {
            stdResp = getSimpleDocService().requestAdHocApproveToPrincipal(docId, currentUserPrincipalId, recipientPrincipalId, "");
        }
        else if (ActionRequestType.ACKNOWLEDGE.equals(actionRequestType)) {
            stdResp = getSimpleDocService().requestAdHocAckToPrincipal(docId,currentUserPrincipalId, recipientPrincipalId, "");
        }
        else if (ActionRequestType.FYI.equals(actionRequestType)) {
            stdResp = getSimpleDocService().requestAdHocFyiToPrincipal(docId,currentUserPrincipalId, recipientPrincipalId, "");
        }
        else {
        	throw new OperationFailedException("Invalid action request type '" + actionRequestType.getActionRequestLabel() + "'");
        }
        if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
            if(stdResp==null){
            	throw new OperationFailedException("Error found in Collab Adhoc Request (" + actionRequestType.getActionRequestLabel() + ")");
            }else{
            	throw new OperationFailedException("Error found in Collab Adhoc Request (" + actionRequestType.getActionRequestLabel() + "): " + stdResp.getErrorMessage());
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
            ActionRequestDTO[] actionRequests = getWorkflowUtilityService().getAllActionRequests(docId);
            for (ActionRequestDTO actionRequestDTO : actionRequests) {
                if (StringUtils.equals(actionRequestId, actionRequestDTO.getActionRequestId().toString())) {
                    recipientPrincipalId = actionRequestDTO.getPrincipalId();
                    break;
                }
            }
            if (recipientPrincipalId == null) {
                throw new OperationFailedException("Unable to find Principal ID for action request id: " + actionRequestId);
            }
            StandardResponse stdResp = getSimpleDocService().revokeAdHocRequestsByActionRequestId(docId, currentUserPrincipalId, null, null, actionRequestId, "");
            if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
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
    
    public List<WorkflowPersonInfo> getCollaborators(String docId) throws OperationFailedException{
		try{
			LOG.info("Getting collaborators for docId: "+docId);

	        if(getWorkflowUtilityService()==null){
	        	LOG.error("No workflow Utility Service is available.");
	        	throw new OperationFailedException("Workflow Service is unavailable");
	        }
			
			List<WorkflowPersonInfo> people = new ArrayList<WorkflowPersonInfo>();
			
			AttributeSet qualification = new AttributeSet();
			qualification.put("documentNumber", docId);
	
			ActionRequestDTO[] items = getWorkflowUtilityService().getAllActionRequests(docId);
	        if(items!=null){
	        	for(ActionRequestDTO item:items){
	        		if (item.isAdHocRequest()) {
	                    // if action request is complete and action taken was a 'revoke action' we do not want to show the person
	                    if (item.isDone() && (item.getActionTaken() != null) && (StringUtils.equals(KEWConstants.ACTION_TAKEN_ADHOC_REVOKED_CD,item.getActionTaken().getActionTaken()))) {
	                        continue;
	                    }
	                    
	        			WorkflowPersonInfo person = new WorkflowPersonInfo();
	        			person.setPrincipalId(item.getPrincipalId());
	        			
	        			KimEntityDefaultInfo info =  getIdentityService().getEntityDefaultInfoByPrincipalId(item.getPrincipalId());
	        			
	        			if(info != null && info.getDefaultName() != null){
	        				person.setFirstName(info.getDefaultName().getFirstName());
	        				person.setLastName(info.getDefaultName().getLastName());
	        			}
	        			
	        			boolean editAuthorized = Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(item.getPrincipalId(), PermissionType.EDIT.getPermissionNamespace(),
	        					PermissionType.EDIT.getPermissionTemplateName(), null, qualification));
	        			boolean openAuthorized = Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(item.getPrincipalId(), PermissionType.OPEN.getPermissionNamespace(),
	        					PermissionType.OPEN.getPermissionTemplateName(), null, qualification));
	        			boolean commentAuthorized = Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(item.getPrincipalId(), PermissionType.ADD_COMMENT.getPermissionNamespace(),
	        					PermissionType.ADD_COMMENT.getPermissionTemplateName(), null, qualification));
	        			
	        			if(editAuthorized){
	        				person.setPermission(PermissionType.EDIT.getCode());
	        			} else if (commentAuthorized){
	        				person.setPermission(PermissionType.ADD_COMMENT.getCode());
	        			} else if (openAuthorized){
	        				person.setPermission(PermissionType.OPEN.getCode());
	        			}
	        			
        				String request = item.getActionRequested();
	        			person.setAction(request);
	        			
	        			person.setActionRequestStatus(getActionRequestStatusLabel(item.getStatus()));
	        			person.setActionRequestId(item.getActionRequestId().toString());
	        			
	        			if (!item.isDone()) {
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
        newArStatusLabels.put(KEWConstants.ACTION_REQUEST_ACTIVATED, "Active");
        newArStatusLabels.put(KEWConstants.ACTION_REQUEST_INITIALIZED, "Pending");
        newArStatusLabels.put(KEWConstants.ACTION_REQUEST_DONE_STATE, "Completed");
        return newArStatusLabels.get(key);
    }
	
	private void addRoleMember(String roleNamespace, String roleName, String docId, String dataId, String recipientPrincipalId) throws OperationFailedException, WorkflowException {
    	DocumentDetailDTO docDetail = getWorkflowUtilityService().getDocumentDetail(docId);
    	DocumentTypeDTO docType = getWorkflowUtilityService().getDocumentType(docDetail.getDocTypeId());
    	AttributeSet roleMemberQuals = new AttributeSet();
    	roleMemberQuals.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME,docType.getName());
    	roleMemberQuals.put(StudentIdentityConstants.QUALIFICATION_DATA_ID,dataId);
    	getRoleUpdateService().assignPrincipalToRole(recipientPrincipalId, roleNamespace, roleName, roleMemberQuals);
	}

	private void removeRoleMemberIfNeccesary(String roleNamespace, String roleName, String docId, String dataId, String recipientPrincipalId) throws OperationFailedException, WorkflowException {
        DocumentDetailDTO docDetail = getWorkflowUtilityService().getDocumentDetail(docId);
        DocumentTypeDTO docType = getWorkflowUtilityService().getDocumentType(docDetail.getDocTypeId());
        AttributeSet roleMemberQuals = new AttributeSet();
        roleMemberQuals.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME,docType.getName());
        roleMemberQuals.put(StudentIdentityConstants.QUALIFICATION_DATA_ID,dataId);
	    getRoleUpdateService().removePrincipalFromRole(recipientPrincipalId, roleNamespace, roleName, roleMemberQuals);
	}

	public Boolean isAuthorizedAddReviewer(String docId) throws OperationFailedException {
		if (docId != null && (!"".equals(docId.trim()))) {
			AttributeSet permissionDetails = new AttributeSet();
			AttributeSet roleQuals = new AttributeSet();
			roleQuals.put(StudentIdentityConstants.DOCUMENT_NUMBER,docId);
			return Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(SecurityUtils.getCurrentUserId(), PermissionType.ADD_ADHOC_REVIEWER.getPermissionNamespace(), 
					PermissionType.ADD_ADHOC_REVIEWER.getPermissionTemplateName(), permissionDetails, roleQuals));
		}
		return Boolean.FALSE;
    }
	
	public IdentityManagementService getIdentityService() throws OperationFailedException {
	    if (identityService == null) {
	        throw new OperationFailedException("unable to find valid identityService");
	    }
		return identityService;
	}

	public void setIdentityService(IdentityManagementService identityService) {
		this.identityService = identityService;
	}
	
	public RoleUpdateService getRoleUpdateService() throws OperationFailedException {
        if (roleUpdateService == null) {
            throw new OperationFailedException("unable to find valid roleUpdateService");
        }
    	return roleUpdateService;
    }

	public void setRoleUpdateService(RoleUpdateService roleUpdateService) {
    	this.roleUpdateService = roleUpdateService;
    }

	public SimpleDocumentActionsWebService getSimpleDocService() throws OperationFailedException {
        if (simpleDocService == null) {
            throw new OperationFailedException("unable to find valid simpleDocService");
        }
		return simpleDocService;
	}

	public void setSimpleDocService(SimpleDocumentActionsWebService simpleDocService) {
		this.simpleDocService = simpleDocService;
	}

	public WorkflowUtility getWorkflowUtilityService() throws OperationFailedException {
        if (workflowUtilityService == null) {
            throw new OperationFailedException("unable to find valid workflowUtilityService");
        }
		return workflowUtilityService;
	}

	public void setWorkflowUtilityService(WorkflowUtility workflowUtilityService) {
		this.workflowUtilityService = workflowUtilityService;
	}

	public IdentityManagementService getPermissionService() throws OperationFailedException {
        if (permissionService == null) {
            throw new OperationFailedException("unable to find valid permissionService");
        }
		return permissionService;
	}

	public void setPermissionService(IdentityManagementService permissionService) {
		this.permissionService = permissionService;
	}
}
