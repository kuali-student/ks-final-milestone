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

package org.kuali.student.lum.lu.ui.course.server.gwt.old;

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
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.rice.kim.bo.entity.dto.KimEntityDefaultInfo;
import org.kuali.rice.kim.bo.impl.KimAttributes;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.rice.kim.service.RoleUpdateService;
import org.kuali.rice.student.StudentWorkflowConstants;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.ui.server.gwt.old.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.old.MetadataServiceImpl;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.lum.lu.dto.workflow.WorkflowPersonInfo;
import org.kuali.student.lum.lu.ui.course.client.service.WorkflowToolRpcService;

//FIXME this servlet should take not extend AbstractBaseDataOrchestrationRpcGwtServlet, does not use metadata and not processed through workflow
public class WorkflowToolRpcGwtServlet extends AbstractBaseDataOrchestrationRpcGwtServlet implements WorkflowToolRpcService{
	
	private static final long serialVersionUID = 1L;
	final static Logger LOG = Logger.getLogger(WorkflowToolRpcGwtServlet.class);

	protected MetadataServiceImpl metadataService;
	protected IdentityService identityService;
	protected RoleUpdateService roleUpdateService;
	
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

	@Override
	public Metadata getMetadata(String idType, String id) {
		return metadataService.getMetadata(idType, null, null);
	};
	
	public MetadataServiceImpl getMetadataService() {
		return metadataService;
	}

	public void setMetadataService(MetadataServiceImpl metadataService) {
		this.metadataService = metadataService;
	}

	@Override
    public Boolean addCollaborator(String docId, String dataId, String dataTitle, String recipientPrincipalId, String selectedPermissionCode, String actionRequestTypeCode, boolean participationRequired, String respondBy) throws OperationFailedException{
        if(getSimpleDocService()==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		//get a user name
        String currentUserPrincipalId = getCurrentUser();

        ActionRequestType actionRequestType = ActionRequestType.getByCode(actionRequestTypeCode);
        if (actionRequestType == null) {
        	throw new RuntimeException("No valid action request type found for code: " + actionRequestTypeCode);
        }
        StandardResponse stdResp = null;
        // TODO: This below are hacked versions of what the call should be. See Jira issue KULRICE-4050 about SimpleDocumentActionsWebServiceImpl
        if (ActionRequestType.APPROVE.equals(actionRequestType)) {
            stdResp = getSimpleDocService().requestAdHocApproveToPrincipal(docId,recipientPrincipalId, currentUserPrincipalId, "");
        }
        else if (ActionRequestType.ACKNOWLEDGE.equals(actionRequestType)) {
            stdResp = getSimpleDocService().requestAdHocAckToPrincipal(docId,recipientPrincipalId, currentUserPrincipalId, "");
        }
        else if (ActionRequestType.FYI.equals(actionRequestType)) {
            stdResp = getSimpleDocService().requestAdHocFyiToPrincipal(docId,recipientPrincipalId, currentUserPrincipalId, "");
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

	private void addRoleMember(String roleNamespace, String roleName, String docId, String dataId, String recipientPrincipalId) {
		try {
	    	DocumentDetailDTO docDetail = getWorkflowUtilityService().getDocumentDetail(Long.valueOf(docId));
	    	DocumentTypeDTO docType = getWorkflowUtilityService().getDocumentType(docDetail.getDocTypeId());
	    	AttributeSet roleMemberQuals = new AttributeSet();
	    	roleMemberQuals.put(KualiStudentKimAttributes.DOCUMENT_TYPE_NAME,docType.getName());
	    	roleMemberQuals.put(KualiStudentKimAttributes.QUALIFICATION_DATA_ID,dataId);
	    	getRoleUpdateService().assignPrincipalToRole(recipientPrincipalId, roleNamespace, roleName, roleMemberQuals);
		}
		catch (WorkflowException e) {
			LOG.error("Workflow threw exception for document id: " + docId, e);
			throw new RuntimeException("Workflow threw exception for document id: " + docId, e);
		}
	}
	
	@Override
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
	        				person.getPermList().add(PermissionType.EDIT.getLabel());
	        			}
	        			
	        			if(commentAuthorized){
	        				person.getPermList().add(PermissionType.ADD_COMMENT.getLabel());
	        			}
	        			
	        			if(openAuthorized){
	        				person.getPermList().add(PermissionType.OPEN.getLabel());
	        			}
	        			
	        			String request = item.getRequestLabel();
	        			if (request == null || ("".equals(request))) {
	        				request = KEWConstants.ACTION_REQUEST_CD.get(item.getActionRequested());
	        			}
	        			person.getActionList().add(request);
	        			
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

	@Override
    public Boolean isAuthorizedAddReviewer(String docId) {
		if (docId != null && (!"".equals(docId.trim()))) {
			AttributeSet permissionDetails = new AttributeSet();
			AttributeSet roleQuals = new AttributeSet();
			roleQuals.put(KimAttributes.DOCUMENT_NUMBER,docId);
			return Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(getCurrentUser(), PermissionType.ADD_ADHOC_REVIEWER.getPermissionNamespace(), 
					PermissionType.ADD_ADHOC_REVIEWER.getPermissionTemplateName(), permissionDetails, roleQuals));
		}
		return Boolean.FALSE;
    }

	@Override
	protected String deriveAppIdFromData(Data data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String deriveDocContentFromData(Data data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getDefaultMetaDataState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getDefaultMetaDataType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getDefaultWorkflowDocumentType() {
		// TODO Auto-generated method stub
		return null;
	}

    private String getActionRequestStatusLabel(String key) {
        Map<String,String> newArStatusLabels = new HashMap<String,String>();
        newArStatusLabels.put(KEWConstants.ACTION_REQUEST_ACTIVATED, "Active");
        newArStatusLabels.put(KEWConstants.ACTION_REQUEST_INITIALIZED, "Pending");
        newArStatusLabels.put(KEWConstants.ACTION_REQUEST_DONE_STATE, "Completed");
        return newArStatusLabels.get(key);
    }

}
