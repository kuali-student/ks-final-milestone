package org.kuali.student.core.assembly.transform;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kew.webservice.StandardResponse;

public abstract class WorkflowFilter extends AbstractDTOFilter {
    
	public static final String WORKFLOW_ACTION		= "WorkflowFilter.Action";
    public static final String WORKFLOW_DOC_TYPE	= "WorkflowFilter.DocumentType";
    public static final String WORKFLOW_USER		= "WorkflowFilter.WorkflowUser";
    
    public static final String WORKFLOW_SUBMIT		= "Submit";
    public static final String WORKFLOW_APPROVE		= "Approve";
    
    final Logger LOG = Logger.getLogger(WorkflowFilter.class);
    
    private WorkflowUtility workflowUtilityService;
	private SimpleDocumentActionsWebService simpleDocService;
    
	/**
	 * This intercepts a newly created/updated object and initiates a workflow, or submits/approves 
	 * the workflow document for the object. 
	 */
	@Override
	public void applyOutboundDtoFilter(Object data, Map<String, String> properties) throws Exception {
        if(simpleDocService==null){
        	throw new Exception("Workflow Service is unavailable");
        }
		
        //get workflow action
        String workflowAction = properties.get(WORKFLOW_ACTION);
	
		//get a user name
        String username = properties.get(WORKFLOW_USER);
        	        
        //Setting the app id to the id of data object
        String appId = getObjectId(data);

        //Lookup the workflowId from the object id
        DocumentDetailDTO docDetail;
        try {
        	docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDocumentType(), appId);
        } catch (Exception e){
        	docDetail = null;
        }
        	

        if (workflowAction != null){
			try{						
		        if(docDetail==null){
		        	throw new Exception("Error found getting document for workflow submit/approval. Worfklow document may not exist." );
		        }
				
	            //Generate the document content xml
	            String docContent = getDocumentContent(data);

		        StandardResponse stdResp;
		        if (WORKFLOW_SUBMIT.equals(workflowAction)){
		            LOG.info("Submitting Workflow Document.");
		        	String routeComment = "Routed";	
		            stdResp = simpleDocService.route(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), docContent, routeComment);
		        } else {
		            LOG.info("Approving Workflow Document.");
		        	stdResp = simpleDocService.approve(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), docContent, "");
		        }		        		       
	
		        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
		    		throw new Exception("Error found approving document: " + stdResp.getErrorMessage());
		    	}
			}catch(Exception e){
		        LOG.error("Could not approve document",e);
		        throw new Exception("Could not approve document");
			}
		} else {
			if (docDetail == null) {
				//No workflow details found, create a new workflow document
				String docTitle = getDocumentTitle(data);
	            docTitle = docTitle==null ? "Unnamed":docTitle;
	            
	            LOG.info("Creating Workflow Document.");
	            DocumentResponse docResponse = simpleDocService.create(username, appId, getDocumentType(), docTitle);
	            if (StringUtils.isNotBlank(docResponse.getErrorMessage())) {
	            	throw new RuntimeException("Error found creating document: " + docResponse.getErrorMessage());
	            }
	            
	            //Lookup the workflow document detail to see if create was successful
				try {
					docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDocumentType(), appId);
				} catch (Exception e) {
	            	throw new RuntimeException("Error found gettting document for newly created object with id " + appId);
				}
			}

            //Generate the document content xml
            String docContent = getDocumentContent(data);
            
            //Save
            StandardResponse stdResp;
            if ( (KEWConstants.ROUTE_HEADER_INITIATED_CD.equals(docDetail.getDocRouteStatus())) ||
            	 (KEWConstants.ROUTE_HEADER_SAVED_CD.equals(docDetail.getDocRouteStatus())) ) {
            	//if the route status is initial, then save initial
            	stdResp = simpleDocService.save(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), docContent, "");
            } else {
            	//Otherwise just update the doc content
            	stdResp = simpleDocService.saveDocumentContent(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), docContent);
            }

            //Check if there were errors saving
            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
            	if(stdResp==null){
            		throw new RuntimeException("Error found updating document");
            	}else{
            		throw new RuntimeException("Error found updating document: " + stdResp.getErrorMessage());
            	}
        	}            						
		}
	}

	
	/**
	 * Used to set the workflow utility service required by this filter
	 * 
	 * @param workflowUtilityService
	 */
	public void setWorkflowUtilityService(WorkflowUtility workflowUtilityService) {
		this.workflowUtilityService = workflowUtilityService;
	}
	
	/**
	 * Used to set the simple doc service required by this filter
	 * @param simpleDocService
	 */
	public void setSimpleDocService(SimpleDocumentActionsWebService simpleDocService) {
		this.simpleDocService = simpleDocService;
	}

	
	/**
	 * @return The doctype of the workflow document to be associated with this workflow process.
	 */
	public abstract String getDocumentType();
	

	/**
	 * This method should be implemented to return the id to be used to link the workflow document to
	 * the to a data object. Normally this is simply the id of the data object
	 * 
	 * @param data
	 * @return The object id used to link a workflow document to the application object
	 */
	public abstract String getObjectId(Object dto);
	

	/**
	 * The title to associate with the workflow process.
	 * 
	 * @param data
	 * @return
	 */
	public abstract String getDocumentTitle(Object dto);
	
	/**
	 * This method should be implemented to provide the document content required to properly
	 * handle the workflow associated with the object being processed.
	 * 
	 * @param data
	 * @return the document content required by the workflow process
	 */
	public abstract String getDocumentContent(Object dto);

}
