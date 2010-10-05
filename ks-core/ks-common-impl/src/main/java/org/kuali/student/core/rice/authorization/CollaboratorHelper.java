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
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.rice.kim.service.RoleUpdateService;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.rice.StudentIdentityConstants;
import org.kuali.student.core.rice.StudentWorkflowConstants;
import org.kuali.student.core.rice.StudentWorkflowConstants.ActionRequestType;
import org.kuali.student.core.workflow.dto.WorkflowPersonInfo;

public class CollaboratorHelper implements Serializable {
	protected IdentityService identityService;
	protected RoleUpdateService roleUpdateService;
	private SimpleDocumentActionsWebService simpleDocService;
    private WorkflowUtility workflowUtilityService;
	private PermissionService permissionService;
	
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
        	throw new RuntimeException("No valid action request type found for code: " + actionRequestTypeCode);
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
        	throw new RuntimeException("Invalid action request type '" + actionRequestType.getActionRequestLabel() + "'");
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
        	throw new RuntimeException("No valid permission type found for code: " + selectedPermissionCode);
        }
//      List<KimRoleInfo> matchingRoles = new ArrayList<KimRoleInfo>();
        if (PermissionType.EDIT.equals(selectedPermType)) {
//          List<KimPermissionInfo> permissions = getPermissionService().getPermissionsByTemplateName(selectedPermission.getPermissionNamespace(), selectedPermission.getPermissionTemplateName())
//	        List<String> roleIds = getPermissionService().getRoleIdsForPermissions(permissions);
//	        RoleService roleService;
//	        List<KimRoleInfo> roles = roleService.getRoles(roleIds);
 
        	addRoleMember(StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAME, docId, dataId, recipientPrincipalId);       	
        }
        else if (PermissionType.ADD_COMMENT.equals(selectedPermType)) {
        	addRoleMember(StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAME, docId, dataId, recipientPrincipalId);
        }
        return Boolean.TRUE;
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
	
			ActionRequestDTO[] items = getWorkflowUtilityService().getAllActionRequests(Long.parseLong(docId));
	        if(items!=null){
	        	for(ActionRequestDTO item:items){
	        		if (item.isAdHocRequest()) {
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
	
	private void addRoleMember(String roleNamespace, String roleName, String docId, String dataId, String recipientPrincipalId) {
		try {
	    	DocumentDetailDTO docDetail = getWorkflowUtilityService().getDocumentDetail(Long.valueOf(docId));
	    	DocumentTypeDTO docType = getWorkflowUtilityService().getDocumentType(docDetail.getDocTypeId());
	    	AttributeSet roleMemberQuals = new AttributeSet();
	    	roleMemberQuals.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME,docType.getName());
	    	roleMemberQuals.put(StudentIdentityConstants.QUALIFICATION_DATA_ID,dataId);
	    	getRoleUpdateService().assignPrincipalToRole(recipientPrincipalId, roleNamespace, roleName, roleMemberQuals);
		}
		catch (WorkflowException e) {
			LOG.error("Workflow threw exception for document id: " + docId, e);
			throw new RuntimeException("Workflow threw exception for document id: " + docId, e);
		}
	}
	
    public Boolean isAuthorizedAddReviewer(String docId) {
		if (docId != null && (!"".equals(docId.trim()))) {
			AttributeSet permissionDetails = new AttributeSet();
			AttributeSet roleQuals = new AttributeSet();
			roleQuals.put(StudentIdentityConstants.DOCUMENT_NUMBER,docId);
			return Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(SecurityUtils.getCurrentUserId(), PermissionType.ADD_ADHOC_REVIEWER.getPermissionNamespace(), 
					PermissionType.ADD_ADHOC_REVIEWER.getPermissionTemplateName(), permissionDetails, roleQuals));
		}
		return Boolean.FALSE;
    }
	
	public IdentityService getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}
	
	public RoleUpdateService getRoleUpdateService() {
    	return roleUpdateService;
    }

	public void setRoleUpdateService(RoleUpdateService roleUpdateService) {
    	this.roleUpdateService = roleUpdateService;
    }

	public SimpleDocumentActionsWebService getSimpleDocService() {
		return simpleDocService;
	}

	public void setSimpleDocService(SimpleDocumentActionsWebService simpleDocService) {
		this.simpleDocService = simpleDocService;
	}

	public WorkflowUtility getWorkflowUtilityService() {
		return workflowUtilityService;
	}

	public void setWorkflowUtilityService(WorkflowUtility workflowUtilityService) {
		this.workflowUtilityService = workflowUtilityService;
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
}
