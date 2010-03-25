package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.dto.ActionRequestDTO;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.rice.kim.bo.entity.dto.KimEntityDefaultInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.ui.server.gwt.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.lum.lu.dto.workflow.WorkflowPersonInfo;
import org.kuali.student.lum.lu.ui.course.client.service.WorkflowToolRpcService;
//FIXME this servlet should take not extend abstract base 
public class WorkflowToolRpcGwtServlet extends AbstractBaseDataOrchestrationRpcGwtServlet implements WorkflowToolRpcService{
	
    public static final String EDIT_DOCUMENT_PERM = "Edit Document";
    public static final String OPEN_DOCUMENT_PERM = "Open Document";
    public static final String COMMENT_DOCUMENT_PERM = "Comment on Document";
    public static final String EDIT = "Edit";
    public static final String OPEN = "View";
    public static final String COMMENT= "Comment";
    public static final String DEFAULT_NAMESPACE = "KS-SYS";
	
	private static final long serialVersionUID = 1L;
	final Logger LOG = Logger.getLogger(WorkflowToolRpcGwtServlet.class);
	protected MetadataServiceImpl metadataService;
	protected IdentityService identityService;
	
	public IdentityService getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
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
    public Boolean addCollaborator(String docId, String dataId, String dataTitle, String recipientPrincipalId, String selectedPermission, boolean participationRequired, String respondBy) throws OperationFailedException{
        if(getSimpleDocService()==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		try{
			//get a user name
            String username=getCurrentUser();

	        //String collaborateComment = "Collaborate by CluProposalService";

	        //create and route a Collaborate workflow
	        //Get the document app Id
	        
	        //Removed - do not do anything data specific in here!
	       
	        //Data cluProposal = getDataFromWorkflowId(docId);
            //CreditCourseProposalHelper root = CreditCourseProposalHelper.wrap(cluProposal);
	        
            StandardResponse stdResp = getSimpleDocService().requestAdHocFyiToPrincipal(docId,recipientPrincipalId, username, "");
            if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                throw new OperationFailedException("Error found in Collab Adhoc FYI: " + stdResp.getErrorMessage());
            }

            //String title = root.getProposal().getTitle()==null?"NoNameSet":root.getProposal().getTitle();
            
/*            DocumentResponse docResponse = getSimpleDocService().create(username, docId, WF_TYPE_CLU_COLLABORATOR_DOCUMENT, dataTitle);
            if (StringUtils.isNotBlank(docResponse.getErrorMessage())) {
            	throw new OperationFailedException("Error found creating document: " + docResponse.getErrorMessage());
            }

            //Get the current routeNodeName
            String routeNodeName="";
            RouteNodeInstanceDTO[] activeNodes = getWorkflowUtilityService().getActiveNodeInstances(Long.decode(docId));
    		if (activeNodes != null && activeNodes.length > 0) {
	    		if (activeNodes.length == 1) {
					routeNodeName = activeNodes[0].getName();
				}
    		}

            //Get the document xml
    		CluProposalCollabRequestDocInfo docContent = new CluProposalCollabRequestDocInfo();

    		docContent.setCluId(dataId);
    		docContent.setPrincipalIdRoleAttribute(new PrincipalIdRoleAttribute());
    		docContent.getPrincipalIdRoleAttribute().setRecipientPrincipalId(recipientPrincipalId);
    		docContent.setPrincipalId(username);
    		docContent.setDocId(docId);
    		docContent.setCollaboratorType(collabType);
    		docContent.setParticipationRequired(participationRequired);
    		docContent.setRespondBy(respondBy);
    		docContent.setRouteNodeName(routeNodeName);

    		JAXBContext context = JAXBContext.newInstance(docContent.getClass());
    		Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
    		marshaller.marshal(docContent, writer);

            String docContentString = writer.toString();

            //Do the routing
            StandardResponse stdResp = getSimpleDocService().route(docResponse.getDocId(), username, docResponse.getTitle(), docContentString, collaborateComment);

            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new OperationFailedException("Error found routing document: " + stdResp.getErrorMessage());
        	}*/

		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
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
	        			
	        			KimEntityDefaultInfo info =  identityService.getEntityDefaultInfoByPrincipalId(item.getPrincipalId());
	        			
	        			if(info != null && info.getDefaultName() != null){
	        				person.setFirstName(info.getDefaultName().getFirstName());
	        				person.setLastName(info.getDefaultName().getLastName());
	        			}
	        			
	        			boolean editAuthorized = Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(item.getPrincipalId(), DEFAULT_NAMESPACE,
	        	                EDIT_DOCUMENT_PERM, null, qualification));
	        			boolean openAuthorized = Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(item.getPrincipalId(), DEFAULT_NAMESPACE,
	        	                OPEN_DOCUMENT_PERM, null, qualification));
	        			boolean commentAuthorized = Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(item.getPrincipalId(), DEFAULT_NAMESPACE,
	        	                COMMENT_DOCUMENT_PERM, null, qualification));
	        			
	        			if(editAuthorized){
	        				person.getPermList().add(EDIT);
	        			}
	        			
	        			if(openAuthorized){
	        				person.getPermList().add(OPEN);
	        			}
	        			
	        			if(commentAuthorized){
	        				person.getPermList().add(COMMENT);
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
