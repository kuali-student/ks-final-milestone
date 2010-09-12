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

package org.kuali.student.common.ui.server.gwt;

import java.util.ArrayList;
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
import org.kuali.rice.kim.bo.impl.KimAttributes;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcService;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.transform.AuthorizationFilter;
import org.kuali.student.core.assembly.transform.MetadataFilter;
import org.kuali.student.core.assembly.transform.TransformationManager;
import org.kuali.student.core.assembly.transform.WorkflowFilter;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.rice.StudentIdentityConstants;
import org.kuali.student.core.rice.authorization.PermissionType;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Generic implementation of data gwt data operations calls and workflow operations
 *
 */
public abstract class AbstractBaseDataOrchestrationRpcGwtServlet extends RemoteServiceServlet implements BaseDataOrchestrationRpcService {

	private static final long serialVersionUID = 1L;

	final Logger LOG = Logger.getLogger(AbstractBaseDataOrchestrationRpcGwtServlet.class);

	private TransformationManager transformationManager;
	
	private SimpleDocumentActionsWebService simpleDocService;
    private WorkflowUtility workflowUtilityService;
	private IdentityService identityService;
	private PermissionService permissionService;

	public Map<String,String> getDefaultFilterProperties(){
		Map<String, String> filterProperties = new HashMap<String,String>();
		filterProperties.put(MetadataFilter.METADATA_ID_TYPE, StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID);
		filterProperties.put(WorkflowFilter.WORKFLOW_USER, getCurrentUser());
		
		return filterProperties;
	}
	
	@Override
	public Data getData(String id) {
		Map<String, String> filterProperties = getDefaultFilterProperties();
		filterProperties.put(MetadataFilter.METADATA_ID_VALUE, id);
		
		try {
			Object dto = get(id);
			if (dto != null){
				return transformationManager.transform(dto, filterProperties);
			}
		} catch (Exception e){
			LOG.error("Error getting Data.",e);			
		}
		
		return null;
	}

	@Override
	public Metadata getMetadata(String idType, String id) {
		Map<String, String> filterProperties = getDefaultFilterProperties();
		
		if (idType == null){
			filterProperties.remove(MetadataFilter.METADATA_ID_TYPE);
		} else {
			filterProperties.put(MetadataFilter.METADATA_ID_TYPE, idType);
		}
		filterProperties.put(MetadataFilter.METADATA_ID_VALUE, id);
		filterProperties.put(WorkflowFilter.WORKFLOW_DOC_TYPE, getDefaultWorkflowDocumentType());
		if (checkDocumentLevelPermissions()){
			filterProperties.put(AuthorizationFilter.DOC_LEVEL_PERM_CHECK, Boolean.TRUE.toString());
		}
		
		try {
			Metadata metadata = transformationManager.getMetadata(getDtoClass().getName(), filterProperties); 
			return metadata;
		} catch (Exception e) {
			throw new RuntimeException("Failed to get metadata");
		}		
	}

	@Override
	public DataSaveResult saveData(Data data) throws OperationFailedException {
		Map<String, String> filterProperties = getDefaultFilterProperties();
		filterProperties.put(MetadataFilter.METADATA_ID_VALUE, (String)data.query("proposalId"));

		try {
			Object dto = transformationManager.transform(data, getDtoClass(), filterProperties);
			dto = save(dto);
				
			Data persistedData = transformationManager.transform(dto, filterProperties);
			return new DataSaveResult(null, persistedData);
		} catch (DataValidationErrorException dvee){
			return new DataSaveResult(dvee.getValidationResults(), null);
		} catch (Exception e) {
			LOG.error("Unable to save", e);
			throw new OperationFailedException("Unable to save");
		}
	}

	protected DataSaveResult _saveData(Data data, Map<String,String> filterProperties) throws OperationFailedException{
		try {
			filterProperties.put(MetadataFilter.METADATA_ID_VALUE, (String)data.query("id"));	

			Object dto = transformationManager.transform(data, getDtoClass(),filterProperties);
			dto = save(dto);
				
			Data persistedData = transformationManager.transform(dto,filterProperties);
			return new DataSaveResult(null, persistedData);
		} catch (DataValidationErrorException dvee){
			return new DataSaveResult(dvee.getValidationResults(), null);
		} catch (Exception e) {
			LOG.error("Unable to save", e);
			throw new OperationFailedException("Unable to save");
		}		
	}
	
	@Override
	public Boolean acknowledgeDocumentWithId(String dataId) throws OperationFailedException {
		if(simpleDocService==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		try{
			//get a user name
            String username=getCurrentUser();

            //Lookup the workflowId from the dataId
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);

            if(docDetail==null){
            	throw new OperationFailedException("Error found gettting document. " );
            }

	        String acknowledgeComment = "Acknowledged";

	        StandardResponse stdResp = simpleDocService.acknowledge(docDetail.getRouteHeaderId().toString(), username, acknowledgeComment);

	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new OperationFailedException("Error found acknowledging document: " + stdResp.getErrorMessage());
        	}

		}catch(Exception e){
            LOG.error("Error acknowledging Document with dataId:"+dataId,e);
            throw new OperationFailedException("Could not acknowledge");
		}
        return Boolean.valueOf(true);
	}

	@Override
	public Boolean adhocRequest(String docId, String recipientPrincipalId,
			RequestType requestType, String annotation) throws OperationFailedException {
        if (simpleDocService == null) {
            throw new OperationFailedException("Workflow Service is unavailable");
        }

        try {
            //Get a user name
            String username = getCurrentUser();

            String fyiAnnotation = "FYI";
            String approveAnnotation = "Approve";
            String ackAnnotation = "Ack";

            if (RequestType.FYI.equals(requestType)) {
                StandardResponse stdResp = simpleDocService.requestAdHocFyiToPrincipal(docId,recipientPrincipalId, username, fyiAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new OperationFailedException("Error found in Adhoc FYI: " + stdResp.getErrorMessage());
                }
            }
            if (RequestType.APPROVE.equals(requestType)) {
                StandardResponse stdResp = simpleDocService.requestAdHocApproveToPrincipal(docId, recipientPrincipalId,username, approveAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new OperationFailedException("Error found in Adhoc Approve: " + stdResp.getErrorMessage());
                }
            }
            if (RequestType.ACKNOWLEDGE.equals(requestType)) {
                StandardResponse stdResp = simpleDocService.requestAdHocAckToPrincipal(docId,recipientPrincipalId,username, ackAnnotation);
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
	public DataSaveResult approveDocumentWithData(Data data) throws OperationFailedException {
		Map<String,String> filterProperties = getDefaultFilterProperties();
	
		filterProperties.put(WorkflowFilter.WORKFLOW_ACTION, WorkflowFilter.WORKFLOW_APPROVE);
		
		return _saveData(data,filterProperties);
	}

	@Override
	public Boolean approveDocumentWithId(String dataId) throws OperationFailedException {

        if(simpleDocService==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		try{
            //get a user name
            String username = getCurrentUser();

            //Lookup the workflowId from the id
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
            if(docDetail==null){
            	throw new OperationFailedException("Error found getting document. " );
            }
            DocumentContentDTO docContent = workflowUtilityService.getDocumentContent(Long.parseLong(docDetail.getAppDocId()));

	        String approveComment = "Approved";

	        StandardResponse stdResp = simpleDocService.approve(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), docContent.getApplicationContent(), approveComment);
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
	public Boolean disapproveDocumentWithId(String dataId) {
        if(simpleDocService==null){
        	LOG.error("Workflow Service is unavailable");
        	return Boolean.FALSE;
        }

		try{
			DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
            //get a user name
            String username = getCurrentUser();
	        String disapproveComment = "Disapproved";

	        StandardResponse stdResp = simpleDocService.disapprove(docDetail.getRouteHeaderId().toString(), username, disapproveComment);
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
	public Boolean fyiDocumentWithId(String dataId) {
        if(simpleDocService==null){
        	LOG.error("Workflow Service is unavailable");
        	return Boolean.FALSE;
        }

		try{
			DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
            //get a user name
            String username = getCurrentUser();

	        StandardResponse stdResp = simpleDocService.fyi(docDetail.getRouteHeaderId().toString(), username);
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
	public Boolean withdrawDocumentWithId(String dataId) {
        if(simpleDocService==null){
        	LOG.error("Workflow Service is unavailable");
        	return Boolean.FALSE;
        }

		try{
			KimPrincipalInfo principal = getIdentityService().getPrincipalByPrincipalName(StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
			if (principal == null) {
				throw new RuntimeException("Cannot find principal for system user principal name: " + StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
			}
			
//			DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
//	        StandardResponse stdResp = simpleDocService.superUserDisapprove(docDetail.getRouteHeaderId().toString(), principal.getPrincipalId(), "");
//	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())) {
//        		LOG.error("Error withdrawing document: " + stdResp.getErrorMessage());
//        		return Boolean.FALSE;
//        	}
		}catch(Exception e){
            LOG.error("Error withdrawing document",e);
            return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public String getActionsRequested(String dataId) throws OperationFailedException {
        try{
    		if(workflowUtilityService==null){
            	throw new OperationFailedException("Workflow Service is unavailable");
            }

    		if(null==dataId){
    			LOG.info("No Id was set for the data");
    			return "";
    		}

            //get a user name
            String principalId = getCurrentUser();

            //Lookup the workflowId from the cluId
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
            if(docDetail==null){
            	throw new OperationFailedException("Error found gettting document. " );
            }

    		//Build up a string of actions requested from the attribute set.  The actions can be S, F,A,C,K. examples are "A" "AF" "FCK" "SCA"
            LOG.debug("Calling action requested with user:"+principalId+" and docId:"+docDetail.getRouteHeaderId());

            Map<String,String> results = new HashMap<String,String>();
            AttributeSet kewActionsRequested = workflowUtilityService.getActionsRequested(principalId, docDetail.getRouteHeaderId());
            for (String key : kewActionsRequested.keySet()) {
            	if ("true".equalsIgnoreCase(kewActionsRequested.get(key))) {
            		results.put(key,"true");
            	}
            }

            //Use StringBuilder to avoid using string concatenations in the for loop.
            StringBuilder actionsRequestedBuffer = new StringBuilder();

            String documentStatus = workflowUtilityService.getDocumentStatus(docDetail.getRouteHeaderId());

            for(Map.Entry<String,String> entry:results.entrySet()){
            	// if saved or initiated status... must show only 'complete' button
            	if (KEWConstants.ROUTE_HEADER_SAVED_CD.equals(documentStatus) || KEWConstants.ROUTE_HEADER_INITIATED_CD.equals(documentStatus)) {
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
                	}
            	}
            }

            // if user can withdraw document then add withdraw button
            if (getPermissionService().isAuthorizedByTemplateName(principalId, PermissionType.ADD_ADHOC_REVIEWER.getPermissionNamespace(), PermissionType.ADD_ADHOC_REVIEWER.getPermissionTemplateName(), null, new AttributeSet(KimAttributes.DOCUMENT_NUMBER,docDetail.getRouteHeaderId().toString()))) {
            	LOG.info("User '" + principalId + "' is allowed to Withdraw the Document");
//            	actionsRequestedBuffer.append("W");
            }

            return actionsRequestedBuffer.toString();
        } catch (Exception e) {
        	LOG.error("Error getting actions Requested",e);
            throw new OperationFailedException("Error getting actions Requested");
        }
	}

	@Override
	public Data getDataFromWorkflowId(String workflowId) throws OperationFailedException {
        if(simpleDocService==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }
        String username = getCurrentUser();

        DocumentResponse docResponse = simpleDocService.getDocument(workflowId, username);
        if(docResponse==null||StringUtils.isNotBlank(docResponse.getErrorMessage())){
        	throw new OperationFailedException("Error found gettting document: " + docResponse.getErrorMessage());
        }

        return getData(docResponse.getAppDocId());
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
	public String getWorkflowIdFromDataId(String dataId) throws OperationFailedException {
		if(null==simpleDocService){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

        DocumentDetailDTO docDetail;
		try {
			docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
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
	public DataSaveResult submitDocumentWithData(Data data) throws OperationFailedException {
		Map<String,String> filterProperties = getDefaultFilterProperties();
		filterProperties.put(WorkflowFilter.WORKFLOW_ACTION, WorkflowFilter.WORKFLOW_SUBMIT);

		return _saveData(data,filterProperties);
	}

	@Override
	public Boolean submitDocumentWithId(String dataId) {
		try {
            if(simpleDocService==null){
            	throw new OperationFailedException("Workflow Service is unavailable");
            }

            //get a user name
            String username = getCurrentUser();

            //Get the workflow ID
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
            if(docDetail==null){
            	throw new OperationFailedException("Error found getting document. " );
            }
            DocumentContentDTO docContent = workflowUtilityService.getDocumentContent(Long.parseLong(docDetail.getAppDocId()));

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

	protected String getCurrentUser() {
		String username = SecurityUtils.getCurrentUserId();
		//backdoorId is only for convenience
		if(username==null&&this.getThreadLocalRequest().getSession().getAttribute("backdoorId")!=null){
			username=(String)this.getThreadLocalRequest().getSession().getAttribute("backdoorId");
        }
		return username;
	}

	protected boolean checkDocumentLevelPermissions() {
		return false;
	}

	public Boolean isAuthorized(PermissionType type, Map<String,String> attributes) {
		String user = getCurrentUser();
		boolean result = false;
		if (checkDocumentLevelPermissions()) {
			if (type == null) {
				return null;
			}
			String namespaceCode = type.getPermissionNamespace();
			String permissionTemplateName = type.getPermissionTemplateName();
			AttributeSet roleQuals = new AttributeSet("documentTypeName", getDefaultWorkflowDocumentType());
			if (attributes != null) {
				roleQuals.putAll(attributes);
			}
			if (StringUtils.isNotBlank(namespaceCode) && StringUtils.isNotBlank(permissionTemplateName)) {
				LOG.info("Checking Permission '" + namespaceCode + "/" + permissionTemplateName + "' for user '" + user + "'");
				result = getPermissionService().isAuthorizedByTemplateName(user, namespaceCode, permissionTemplateName, null, roleQuals);
			}
			else {
				LOG.info("Can not check Permission with namespace '" + namespaceCode + "' and template name '" + permissionTemplateName + "' for user '" + user + "'");
				return Boolean.TRUE;
			}
		}
		else {
			LOG.info("Will not check for document level permissions. Defaulting authorization to true.");
			result = true;
		}
		LOG.info("Result of authorization check for user '" + user + "': " + result);
		return Boolean.valueOf(result);
	}

	public IdentityService getIdentityService() {
    	return identityService;
    }

	public void setIdentityService(IdentityService identityService) {
    	this.identityService = identityService;
    }

	public void setSimpleDocService(SimpleDocumentActionsWebService simpleDocService) {
		this.simpleDocService = simpleDocService;
	}

	public void setWorkflowUtilityService(WorkflowUtility workflowUtilityService) {
		this.workflowUtilityService = workflowUtilityService;
	}

	protected SimpleDocumentActionsWebService getSimpleDocService() {
		return simpleDocService;
	}

	protected WorkflowUtility getWorkflowUtilityService() {
		return workflowUtilityService;
	}

	public TransformationManager getTransformationManager() {
		return transformationManager;
	}

	public void setTransformationManager(TransformationManager transformationManager) {
		this.transformationManager = transformationManager;
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	protected abstract String getDefaultWorkflowDocumentType();
	
	protected abstract String getDefaultMetaDataState();
	
	protected abstract Object get(String id) throws Exception;
	
	protected abstract Object save(Object dto) throws Exception;
	
	protected abstract Class<?> getDtoClass();
}
