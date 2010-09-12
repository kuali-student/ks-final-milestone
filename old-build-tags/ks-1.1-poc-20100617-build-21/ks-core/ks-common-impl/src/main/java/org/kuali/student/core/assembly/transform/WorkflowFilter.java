package org.kuali.student.core.assembly.transform;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kew.webservice.StandardResponse;

public abstract class WorkflowFilter extends AbstractDTOFilter {
    
	public static final String WORKFLOW_ACTION = "WorkflowFilter.Action";
    public static final String WORKFLOW_DOC_TYPE = "WorkflowFilter.DocumentType";
    public static final String WORKFLOW_USER	= "WorkflowFilter.WorkflowUser";
    
    public static final String WORKFLOW_SUBMIT = "Submit";
    public static final String WORKFLOW_APPROVE = "Approve";
    
    final Logger LOG = Logger.getLogger(WorkflowFilter.class);
    
    private WorkflowUtility workflowUtilityService;
	private SimpleDocumentActionsWebService simpleDocService;
    
	@Override
	public void applyInboundDtoFilter(Object dto, Map<String, String> properties)
			throws Exception {
		
	}

	@Override
	public void applyOutboundDtoFilter(Object data, Map<String, String> properties) throws Exception {
        if(simpleDocService==null){
        	throw new Exception("Workflow Service is unavailable");
        }
		String workflowAction = properties.get(WORKFLOW_ACTION);
		if (workflowAction != null){
			try{
				
				//get doc type
				String documentType = properties.get(WORKFLOW_DOC_TYPE);
				
				//get a user name
		        String username = properties.get(WORKFLOW_USER);
		        	        
		        //Setting the app id to the id of data object
		        String appId = deriveAppId(data);
		
		        //Lookup the workflowId from the object id
		        DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(documentType, appId);
		        if(docDetail==null){
		        	throw new Exception("Error found getting document. " );
		        }
	
		        StandardResponse stdResp;
		        if (WORKFLOW_SUBMIT.equals(workflowAction)){
		            String routeComment = "Routed";	
		            stdResp = simpleDocService.route(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), deriveDocContentFromData(data), routeComment);
		        } else {		
		        	stdResp = simpleDocService.approve(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), deriveDocContentFromData(data), "");
		        }
	
		        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
		    		throw new Exception("Error found approving document: " + stdResp.getErrorMessage());
		    	}
			}catch(Exception e){
		        LOG.error("Could not approve document",e);
		        throw new Exception("Could not approve document");
			}
		}
	}

	

	public void setWorkflowUtilityService(WorkflowUtility workflowUtilityService) {
		this.workflowUtilityService = workflowUtilityService;
	}
	
	public void setSimpleDocService(SimpleDocumentActionsWebService simpleDocService) {
		this.simpleDocService = simpleDocService;
	}

	public abstract String deriveDocContentFromData(Object data);
	
	public abstract String deriveAppId(Object data);
}
