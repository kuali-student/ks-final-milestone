package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.dto.ActionRequestDTO;
import org.kuali.rice.kew.dto.RouteNodeInstanceDTO;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.ui.server.gwt.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;
import org.kuali.student.lum.lu.dto.workflow.CluProposalCollabRequestDocInfo;
import org.kuali.student.lum.lu.dto.workflow.CluProposalDocInfo;
import org.kuali.student.lum.lu.dto.workflow.PrincipalIdRoleAttribute;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;

public class CreditCourseProposalRpcGwtServlet extends
		AbstractBaseDataOrchestrationRpcGwtServlet implements
		CreditCourseProposalRpcService {

	private static final long serialVersionUID = 1L;
	final Logger LOG = Logger.getLogger(CreditCourseProposalRpcGwtServlet.class);
    private static final String WF_TYPE_CLU_DOCUMENT = "CluCreditCourseProposal";
    private static final String WF_TYPE_CLU_COLLABORATOR_DOCUMENT =  "CluCollaboratorDocument";
	private static final String DEFAULT_METADATA_STATE = "draft";
	private static final String DEFAULT_METADATA_TYPE = null;
    
	private PermissionService permissionService;
	
    @Override
	protected String deriveAppIdFromData(Data data) {
		CreditCourseProposalHelper cluProposal = CreditCourseProposalHelper.wrap(data);
		if(cluProposal!=null&&cluProposal.getProposal()!=null){
			return cluProposal.getProposal().getId();
		}
		return null;
	}

	@Override
	protected String deriveDocContentFromData(Data data) {
    	try{
    		CreditCourseProposalHelper cluProposal = CreditCourseProposalHelper.wrap(data);
    		
    		CluProposalDocInfo docContent = new CluProposalDocInfo();
    		
    		if(null == cluProposal.getCourse()){
    			throw new OperationFailedException("CluInfo must be set.");
    		}
    		
    		String cluId = cluProposal.getCourse().getId()==null?"":cluProposal.getCourse().getId(); 
    		String adminOrg = cluProposal.getCourse().getDepartment()==null?"":cluProposal.getCourse().getDepartment(); 
    		String proposalId = cluProposal.getProposal().getId()==null?"":cluProposal.getProposal().getId();
    		
    		docContent.setCluId(cluId);
            docContent.setOrgId(adminOrg);
            docContent.setProposalId(proposalId);
            
    		JAXBContext context = JAXBContext.newInstance(docContent.getClass());
    		Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
    		marshaller.marshal(docContent, writer);
    		return writer.toString();

    	} catch(Exception e) {
    		LOG.error("Error creating Document content for Clu. ", e);
    	}
    	return null;
	}

	@Override
	protected String getDefaultMetaDataState() {
		return DEFAULT_METADATA_STATE;
	}

	@Override
	protected String getDefaultMetaDataType() {
		return DEFAULT_METADATA_TYPE;
	}

	@Override
	protected String getDefaultWorkflowDocumentType() {
		return WF_TYPE_CLU_DOCUMENT;
	}

	@Override
    public Boolean addCollaborator(String docId, String recipientPrincipalId, String collabType, boolean participationRequired, String respondBy) throws OperationFailedException{
        if(getSimpleDocService()==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		try{
			//get a user name
            String username=getCurrentUser();

	        String collaborateComment = "Collaborate by CluProposalService";

	        //create and route a Collaborate workflow
	        //Get the document app Id
	        Data cluProposal = getDataFromWorkflowId(docId);
            CreditCourseProposalHelper root = CreditCourseProposalHelper.wrap(cluProposal);

	        
            String title = root.getProposal().getTitle()==null?"NoNameSet":root.getProposal().getTitle();
            
            DocumentResponse docResponse = getSimpleDocService().create(username, docId, WF_TYPE_CLU_COLLABORATOR_DOCUMENT, title);
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

    		docContent.setCluId(root.getCourse().getId());
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
        	}

		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
    }

	@Override
    public HashMap<String, ArrayList<String>> getCollaborators(String docId) throws OperationFailedException{
		try{
			LOG.info("Getting collaborators for docId: "+docId);

	        if(getWorkflowUtilityService()==null){
	        	LOG.error("No workflow Utility Service is available.");
	        	throw new OperationFailedException("Workflow Service is unavailable");
	        }
	
			HashMap<String, ArrayList<String>> results = new HashMap<String, ArrayList<String>>();
	
			ArrayList<String> coAuthors = new ArrayList<String>();
			ArrayList<String> commentors= new ArrayList<String>();
			ArrayList<String> viewers = new ArrayList<String>();
			ArrayList<String> delegates = new ArrayList<String>();
	
			ActionRequestDTO[] items = getWorkflowUtilityService().getAllActionRequests(Long.parseLong(docId));
	        if(items!=null){
	        	for(ActionRequestDTO item:items){
	        		if (item.isActivated() && (!item.isDone())) {
		        		if(KEWConstants.ACTION_REQUEST_FYI_REQ.equals(item.getActionRequested())&&item.getRequestLabel()!=null){
		        			if(item.getRequestLabel().startsWith("Co-Author")){
			        			coAuthors.add(item.getPrincipalId());
			        		}
			        		else if(item.getRequestLabel().startsWith("Commentor")){
			        			commentors.add(item.getPrincipalId());
			        		}
			        		else if(item.getRequestLabel().startsWith("Viewer")){
			        			viewers.add(item.getPrincipalId());
			        		}
			        		else if(item.getRequestLabel().startsWith("Delegate")){
			        			delegates.add(item.getPrincipalId());
			        		}
		        		}
	        		}
	        	}
	        }
	
	        results.put("Co-Author", coAuthors);
	        results.put("Commentor", commentors);
	        results.put("Viewer", viewers);
	        results.put("Delegate", delegates);
	        LOG.info("Returning collaborators: "+results.toString());
	        return results;
		}catch(Exception e){
			LOG.error("Error getting actions Requested.",e);
            throw new OperationFailedException("Error getting actions Requested",e);
		}
    }
	
	@Override
	public Boolean hasPermission(String permName) {
	    //FIXME this is just a stub until the KIM perms are defined
	    //return permissionService.hasPermission(getCurrentUser(), "KS-LUM", permName, null);
	    return true;
	}
	
	public PermissionService getPermissionService() {
        return permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
}
