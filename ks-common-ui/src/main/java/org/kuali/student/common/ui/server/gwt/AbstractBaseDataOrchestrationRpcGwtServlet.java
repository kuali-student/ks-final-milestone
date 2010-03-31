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
import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.rice.StudentIdentityConstants;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Generic implementation of data orchestration calls and workflow calls
 *
 */
public abstract class AbstractBaseDataOrchestrationRpcGwtServlet extends RemoteServiceServlet implements BaseDataOrchestrationRpcService {
	//FIXME issues:
	// -The Type/state config is hardcoded here which will cause troubles with different types and states
	// -Workflow filter should be combined with this for save
	// -The exception handling here needs standardization.  Should RPC errors throw operation failed with just the message and log the message and exception?
	// also should calls that return Boolean ever throw exceptions?
	
	private static final long serialVersionUID = 1L;
	
	final Logger LOG = Logger.getLogger(AbstractBaseDataOrchestrationRpcGwtServlet.class);
	
	private Assembler<Data, Void> assembler;

    private SimpleDocumentActionsWebService simpleDocService;
    private WorkflowUtility workflowUtilityService;
	private PermissionService permissionService;
	private IdentityService identityService;
	
	@Override
	public Data getData(String dataId) {
		try {
			return assembler.get(dataId);
		} catch (AssemblyException e) {
			LOG.error("Error getting Data.",e);
		}
		return null;
	}

	@Override
	public Metadata getMetadata(String idType, String id) {

		try {
		    //FIXME: should not pass empty id. What to do here?
			return assembler.getMetadata(idType, id, getDefaultMetaDataType(), getDefaultMetaDataState());
		} catch (AssemblyException e) {
			LOG.error("Error getting Metadata.",e);
		}
		return null;
	}

	@Override
	public DataSaveResult saveData(Data data) throws OperationFailedException {
		try {
			SaveResult<Data> saveResult = assembler.save(data);
			if (saveResult != null) {
				return new DataSaveResult(saveResult.getValidationResults(), saveResult.getValue());
			}
		} catch (Exception e) {
			LOG.error("Unable to save credit course proposal", e);
			throw new OperationFailedException("Unable to save credit course proposal");
		}
		return null;
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
            e.printStackTrace();
		}
        return new Boolean(true);
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
            return new Boolean(false);
        }
        return new Boolean(true);
	}

	@Override
	public DataSaveResult approveDocumentWithData(Data data) throws OperationFailedException {
		//First Save
		DataSaveResult saveResult = saveData(data);
		if(isValid(saveResult)){
			try{
	            //get a user name
	            String username = getCurrentUser();
	            
	            //Lookup the workflowId from the cluId
	            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), deriveAppIdFromData(data));
	            if(docDetail==null){
	            	throw new OperationFailedException("Error found getting document. " );
	            }
	            
		        String approveComment = "Approved by CluProposalService";
		        
		        StandardResponse stdResp = simpleDocService.approve(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), deriveDocContentFromData(data), approveComment);
	            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
	        		throw new OperationFailedException("Error found approving document: " + stdResp.getErrorMessage());
	        	}

			}catch(Exception e){
	            LOG.error("Could not approve document",e);
	            throw new OperationFailedException("Could not approve document");
			}
		}
		return saveResult;
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
			DocumentContentDTO docContent = workflowUtilityService.getDocumentContent(Long.parseLong(docDetail.getAppDocId()));
            if(docDetail==null){
            	throw new OperationFailedException("Error found getting document. " );
            }
            
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
			DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
			KimPrincipalInfo principal = getIdentityService().getPrincipalByPrincipalName(StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
			if (principal == null) {
				throw new RuntimeException("Cannot find principal for system user principal name: " + StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
			}
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

            String actionsRequested = "";

            String documentStatus = workflowUtilityService.getDocumentStatus(docDetail.getRouteHeaderId());
            
            for(Map.Entry<String,String> entry:results.entrySet()){
            	// if saved or initiated status... must show only 'complete' button
            	if (KEWConstants.ROUTE_HEADER_SAVED_CD.equals(documentStatus) || KEWConstants.ROUTE_HEADER_INITIATED_CD.equals(documentStatus)) {
            		// show only complete button if complete or approve code in this doc status
            		if ( (KEWConstants.ACTION_REQUEST_COMPLETE_REQ.equals(entry.getKey()) || KEWConstants.ACTION_REQUEST_APPROVE_REQ.equals(entry.getKey())) && ("true".equals(entry.getValue())) ) {
            			actionsRequested+="S";
            		}
            		// if not Complete or Approve code then show the standard buttons
            		else {
    	            	if("true".equals(entry.getValue())){
    	            		actionsRequested+=entry.getKey();
    	            	}
            		}
            	}
            	else {
                	if("true".equals(entry.getValue())){
                		actionsRequested+=entry.getKey();
                	}
            	}
            }

            // if user can withdraw document then add withdraw button
            if (getPermissionService().isAuthorizedByTemplateName(principalId, PermissionType.ADD_ADHOC_REVIEWER.getPermissionNamespace(), PermissionType.ADD_ADHOC_REVIEWER.getPermissionTemplateName(), null, new AttributeSet(KimAttributes.DOCUMENT_NUMBER,docDetail.getRouteHeaderId().toString()))) {
            	LOG.info("User '" + principalId + "' is allowed to Withdraw the Proposal");
//            	actionsRequested+="W";
            }

            return actionsRequested;
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
		//First Save
		DataSaveResult saveResult = saveData(data);
		if(isValid(saveResult)){
			try {
	            if(simpleDocService==null){
	            	throw new OperationFailedException("Workflow Service is unavailable");
	            }

	            //get a user name
	            String username = getCurrentUser();

	            //Get the workflow ID
	            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), deriveAppIdFromData(data));

	            if(docDetail==null){
	            	throw new OperationFailedException("Error found getting document. " );
	            }

	            String routeComment = "Routed";

	            StandardResponse stdResp = simpleDocService.route(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), deriveDocContentFromData(data), routeComment);

	            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
	        		throw new OperationFailedException("Error found routing document: " + stdResp.getErrorMessage());
	        	}

	        } catch (Exception e) {
	            LOG.error("Error found routing document",e);
	            throw new OperationFailedException("Error found routing document");
	        }
		}
		return saveResult;
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
            DocumentContentDTO docContent = workflowUtilityService.getDocumentContent(Long.parseLong(docDetail.getAppDocId()));
            
            if(docDetail==null){
            	throw new OperationFailedException("Error found getting document. " );
            }

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
	private boolean isValid(DataSaveResult saveResult) {
		if(saveResult.getValidationResults()!=null){
			for(ValidationResultInfo validationResult:saveResult.getValidationResults()){
				if(ErrorLevel.ERROR.equals(validationResult.getLevel())){
					return false;
				}
			}
		}
		return true;
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

	protected abstract String deriveAppIdFromData(Data data);
	protected abstract String deriveDocContentFromData(Data data);
	protected abstract String getDefaultWorkflowDocumentType();
	protected abstract String getDefaultMetaDataState();
	protected abstract String getDefaultMetaDataType();
	
	//POJO methods
	public void setAssembler(Assembler<Data, Void> assembler) {
		this.assembler = assembler;
	}

	public PermissionService getPermissionService() {
        return permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
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

	protected Assembler<Data, Void> getAssembler() {
		return assembler;
	}

	protected SimpleDocumentActionsWebService getSimpleDocService() {
		return simpleDocService;
	}

	protected WorkflowUtility getWorkflowUtilityService() {
		return workflowUtilityService;
	}


}
