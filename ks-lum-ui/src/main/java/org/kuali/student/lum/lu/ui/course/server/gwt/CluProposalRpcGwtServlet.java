/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.common.ui.server.mvc.dto.MapContext;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.proposal.dto.ProposalInfo;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in.
 * 
 * @author Kuali Student Team
 */
// Fixme: Replace Object with ProposalService interface class
public class CluProposalRpcGwtServlet extends BaseRpcGwtServletAbstract<LuService> implements CluProposalRpcService {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_USER_ID = "user1";
    private SimpleDocumentActionsWebService simpleDocService; 
    private WorkflowUtility workflowUtilityService;
    private OrganizationService orgService;
     

	private Map<String, ProposalInfo> getProposalInfoMap() {
	    @SuppressWarnings("unchecked")
	    Map<String, ProposalInfo> proposalInfoMap = (Map<String, ProposalInfo>) getThreadLocalRequest().getSession(true).getAttribute("proposal");
        if(proposalInfoMap == null){
        	proposalInfoMap = new HashMap<String, ProposalInfo>();
        	getThreadLocalRequest().getSession(true).setAttribute("proposal", proposalInfoMap);
        }
        return proposalInfoMap;
    }

    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#createProposal(org.kuali.student.lum.lu.ui.course.client.service.CluProposal)
     */
    @Override
    public CluProposal createProposal(CluProposal cluProposal) {
        try {

            //FIXME: Restore code to handle proposal service?
            
            /*  
            ProposalInfo proposalInfo = cluProposal.getProposalInfo();
            proposalInfo.setId(UUIDHelper.genStringUUID());
            proposalInfo.setProposalReferenceType("clu");
             */
                       
            CluInfo parentCluInfo = cluProposal.getCluInfo();
            parentCluInfo = service.createClu(cluProposal.getCluInfo().getType(), parentCluInfo);
            cluProposal.setCluInfo(parentCluInfo);
            String saveComment = "Saved By CluProposalService";
            List<CluInfo> activities = cluProposal.getActivities();
            if (activities != null){
                for (CluInfo cluInfo : activities) {
    
                    cluInfo = service.createClu(cluInfo.getType(), cluInfo);
                    CluCluRelationInfo relInfo = new CluCluRelationInfo();
                    
                    //TODO: Create a proper relation type for activities
                    relInfo.setCluId(parentCluInfo.getId());
                    relInfo.setRelatedCluId(cluInfo.getId());                    
                    relInfo.setType("proposal.actvitiy"); 
    
                    service.createCluCluRelation(parentCluInfo.getId(), cluInfo.getId(), "proposal.actvitiy", relInfo);
                }
            }

            /*
            ArrayList<String> proposalRefIds = new ArrayList<String>();
            proposalRefIds.add(parentCluInfo.getId());
            proposalInfo.setProposalReference(proposalRefIds);
            getProposalInfoMap().put(proposalInfo.getId(), proposalInfo);
             */
            /*-------------------------------------------------------------------------------------------------------------------------------*/
            
            
            //get a user name
            String username=getCurrentUser();
            
            //Create and then route the document 
            String workflowDocTypeId = "CluDocument";
            if(simpleDocService==null){
            	throw new RuntimeException("Workflow Service is unavailable");
            }
            DocumentResponse docResponse = simpleDocService.create(username, parentCluInfo.getId(), workflowDocTypeId, parentCluInfo.getOfficialIdentifier().getLongName());
            if (StringUtils.isNotBlank(docResponse.getErrorMessage())) {
            	throw new RuntimeException("Error found creating document: " + docResponse.getErrorMessage());
            }
            StandardResponse stdResp = simpleDocService.save(docResponse.getDocId(), username, parentCluInfo.getOfficialIdentifier().getLongName(),getCluProposalDocContent(cluProposal), saveComment);
            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
            	throw new RuntimeException("Error found saving document: " + stdResp.getErrorMessage());
            }
            
            cluProposal.setWorkflowId(docResponse.getDocId());
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cluProposal;
    }

    
    
    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#deleteProposal(java.lang.String)
     */
    @Override
    public CluProposal deleteProposal(String id) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#getProposal(java.lang.String)
     */
    @Override
    public CluProposal getProposal(String id) {
        //FIXME: This will need to tie into workflow to work properly?
        ProposalInfo proposalInfo = getProposalInfoMap().get(id);
        if (proposalInfo != null) {
            try {
                CluProposal cluProposal = new CluProposal();
                cluProposal.setProposalInfo(proposalInfo);

                String parentCluId = proposalInfo.getProposalReference().get(0);
                CluInfo parentClu = service.getClu(parentCluId);
                List<CluCluRelationInfo> cluRelations = service.getCluCluRelationsByClu(parentCluId);
                
                List<CluInfo> activities = new ArrayList<CluInfo>();
                for (CluCluRelationInfo relInfo:cluRelations){
                    if (relInfo.getType().equals("proposal.activity")){
                        activities.add(service.getClu(relInfo.getRelatedCluId()));
                    }
                }
                
                cluProposal.setCluInfo(parentClu);
                cluProposal.setActivities(activities);
                return cluProposal;
            } catch (Exception e) {
                // TODO: handle exception
            }

        } 
        
        //FIXME: This is only temporary code and needs to be removed
        if (proposalInfo == null){
            //We will asssume the id passed in is a clu id
            try {
                CluInfo cluInfo = service.getClu(id);
                CluProposal cluProposal = new CluProposal();
                cluProposal.setCluInfo(cluInfo);
                return cluProposal;
            } catch (Exception e) {
            	e.printStackTrace();
            }
            
        }
        return null;
    }

    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#saveProposal(org.kuali.student.lum.lu.ui.course.client.service.CluProposal)
     */
    @Override
    public CluProposal saveProposal(CluProposal cluProposal) {
        try {
        	
            CluInfo parentCluInfo = cluProposal.getCluInfo();
            parentCluInfo = service.updateClu(cluProposal.getCluInfo().getId(), parentCluInfo);
            cluProposal.setCluInfo(parentCluInfo);
            //get a user name
            String username = getCurrentUser();

            /*FIXME: Restore code to handle proposal service?
            ProposalInfo proposalInfo = cluProposal.getProposalInfo();                       
            List<String> proposalReferences = new ArrayList<String>();
            proposalReferences.add(parentCluInfo.getId());
            getProposalInfoMap().put(proposalInfo.getId(), proposalInfo);
            */

            List<CluInfo> activities = cluProposal.getActivities();
            if (activities != null){
                for (CluInfo cluInfo : activities) {
    
                    if (cluInfo.getId() == null){
                        cluInfo = service.createClu(cluInfo.getType(), cluInfo);
                        CluCluRelationInfo relInfo = new CluCluRelationInfo();
                        relInfo.setCluId(parentCluInfo.getId());
                        relInfo.setRelatedCluId(cluInfo.getId());                    
                        relInfo.setType("proposal.actvitiy");
                        service.createCluCluRelation(parentCluInfo.getId(), cluInfo.getId(), "proposal.actvitiy", relInfo);
                    }                
                }
            }
            
            if(simpleDocService==null){
            	throw new RuntimeException("Workflow Service is unavailable");
            }
            
            DocumentResponse docResponse = simpleDocService.getDocument(cluProposal.getWorkflowId(), username);
            
            //Check that the call was successful
            if(docResponse==null||StringUtils.isNotBlank(docResponse.getErrorMessage())){
            	throw new RuntimeException("Error found gettting document: " + docResponse.getErrorMessage());
            }
            
            if ( (KEWConstants.ROUTE_HEADER_INITIATED_CD.equals(docResponse.getDocStatus())) || 
            	 (KEWConstants.ROUTE_HEADER_SAVED_CD.equals(docResponse.getDocStatus())) ) {
                StandardResponse stdResp = simpleDocService.save(docResponse.getDocId(), username, parentCluInfo.getOfficialIdentifier().getLongName(),getCluProposalDocContent(cluProposal), "");
                if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
                	throw new RuntimeException("Error found saving document: " + stdResp.getErrorMessage());
                }
            }
            else {
            	StandardResponse stdResp = simpleDocService.saveDocumentContent(docResponse.getDocId(), username, parentCluInfo.getOfficialIdentifier().getLongName(),getCluProposalDocContent(cluProposal));
            	if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
            		throw new RuntimeException("Error found updating document: " + stdResp.getErrorMessage());
            	}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return cluProposal;
    }

    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#submitProposal(org.kuali.student.lum.lu.ui.course.client.service.CluProposal)
     */
    @Override
    public CluProposal submitProposal(CluProposal cluProposal) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return null;
    }


	private String getCluProposalDocContent(CluProposal cluProposal){
    	try{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            Element root = doc.createElement("cluProposal");
            doc.appendChild(root);
            
            Element cluId = doc.createElement("cluId");
            root.appendChild(cluId);
            
            Text cluIdText = doc.createTextNode(cluProposal.getCluInfo().getId());
            cluId.appendChild(cluIdText);
            
            Element orgId = doc.createElement("orgId");
            root.appendChild(orgId);
            
            // TODO - CluInfo.getAdminOrg() is deprecated; supposed to use
            //            AccreditationInfo now instead
            // question: if > 1 AccreditationInfo in CluInfo.accreditationList,
            // that may mean more than one orgID. What should be used then?
            
            Text orgIdText;
            if(cluProposal.getCluInfo()!=null && cluProposal.getCluInfo().getPrimaryAdminOrg()!=null && cluProposal.getCluInfo().getPrimaryAdminOrg().getOrgId()!=null){
            	orgIdText = doc.createTextNode(cluProposal.getCluInfo().getPrimaryAdminOrg().getOrgId());
            }
            // orgId might not be set yet
            else{
            	orgIdText = doc.createTextNode("");
            }
            orgId.appendChild(orgIdText);
            
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
	    	
            return writer.toString();
            
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return "";
	}
	
	@Override
	public CluProposal startProposalWorkflow(CluProposal cluProposal) {
		try {
            if(simpleDocService==null){
            	throw new RuntimeException("Workflow Service is unavailable");
            }
            
            CluInfo cluInfo = cluProposal.getCluInfo();
            
            //get a user name
            String username = getCurrentUser();

            //Create and then route the document 
            DocumentResponse docResponse = simpleDocService.getDocument(cluProposal.getWorkflowId(), username);
            
            if(docResponse==null||StringUtils.isNotBlank(docResponse.getErrorMessage())){
            	throw new RuntimeException("Error found gettting document: " + docResponse.getErrorMessage());
            }
            
            String routeComment = "Routed By CluProposalService";
            
            StandardResponse stdResp = simpleDocService.route(docResponse.getDocId(), username, cluInfo.getOfficialIdentifier().getLongName(), getCluProposalDocContent(cluProposal), routeComment);
        	
            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new RuntimeException("Error found routing document: " + stdResp.getErrorMessage());
        	}
        	
            cluProposal.setWorkflowId(docResponse.getDocId());
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cluProposal;
	}

	@Override
	public CluProposal getCluProposalFromWorkflowId(String docId) {
        if(simpleDocService==null){
        	throw new RuntimeException("Workflow Service is unavailable");
        }
		
        //get a user name
        String username = getCurrentUser();
        
        DocumentResponse docResponse = simpleDocService.getDocument(docId, username);
        if(docResponse==null||StringUtils.isNotBlank(docResponse.getErrorMessage())){
        	throw new RuntimeException("Error found gettting document: " + docResponse.getErrorMessage());
        }
        
		CluProposal proposal = getProposal(docResponse.getAppDocId());
		proposal.setWorkflowId(docId);
		return proposal;
	}
	
	@Override
	public String getActionsRequested(CluProposal cluProposal) {
        try{
		if(workflowUtilityService==null){
        	throw new RuntimeException("Workflow Service is unavailable");
        }
		
        //get a user name
        String username = getCurrentUser();
        
		//Build up a string of actions requested from the attribute set.  The actions can be S, F,A,C,K. examples are "A" "AF" "FCK" "SCA"
        System.out.println("Calling action requested with user:"+username+" and docId:"+cluProposal.getWorkflowId());
        AttributeSet results = workflowUtilityService.getActionsRequested(username, Long.parseLong(cluProposal.getWorkflowId()));
        String documentStatus = workflowUtilityService.getDocumentStatus(Long.parseLong(cluProposal.getWorkflowId()));
        String actionsRequested = "";
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
        	return actionsRequested;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting actions Requested",e);
        }
	}
	

	@Override
	public Boolean approveProposal(CluProposal cluProposal) {
        if(simpleDocService==null){
        	throw new RuntimeException("Workflow Service is unavailable");
        }
		
		try{
            //get a user name
            String username = getCurrentUser();
            
	        CluInfo cluInfo = cluProposal.getCluInfo();
	        DocumentResponse docResponse = simpleDocService.getDocument(cluProposal.getWorkflowId(), username);
	        if(docResponse==null||StringUtils.isNotBlank(docResponse.getErrorMessage())){
	        	throw new RuntimeException("Error found gettting document: " + docResponse.getErrorMessage());
	        }
	        String approveComment = "Approved by CluProposalService";
	        
	        StandardResponse stdResp = simpleDocService.approve(cluProposal.getWorkflowId(), username, cluInfo.getOfficialIdentifier().getLongName(), docResponse.getDocContent(), approveComment);
            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new RuntimeException("Error found approving document: " + stdResp.getErrorMessage());
        	}
	        
		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
	}
	
	@Override
	public Boolean disapproveProposal(CluProposal cluProposal) {
        if(simpleDocService==null){
        	throw new RuntimeException("Workflow Service is unavailable");
        }
		
		try{
            //get a user name
            String username = getCurrentUser();
            
	        String disapproveComment = "Disapproved by CluProposalService";
	        
	        //String docId, String principalId, String docTitle, String docContent, String annotation
	        StandardResponse stdResp = simpleDocService.disapprove(cluProposal.getWorkflowId(), username, disapproveComment);
            
	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new RuntimeException("Error found disapproving document: " + stdResp.getErrorMessage());
        	}
            
		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
	}
	

	@Override
	public Boolean acknowledgeProposal(CluProposal cluProposal) {
        if(simpleDocService==null){
        	throw new RuntimeException("Workflow Service is unavailable");
        }
		
		try{
			//get a user name
            String username=getCurrentUser();
            
	        String acknowledgeComment = "Acknowledged by CluProposalService";
	        
	        //String docId, String principalId, String docTitle, String docContent, String annotation
	        StandardResponse stdResp = simpleDocService.acknowledge(cluProposal.getWorkflowId(), username, acknowledgeComment);
	        
	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new RuntimeException("Error found acknowledging document: " + stdResp.getErrorMessage());
        	}
	        
		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
	}
	
	@Override
    public Boolean addCollaborator(String docId, String recipientPrincipalId, String collabType, boolean participationRequired, String respondBy){
        if(simpleDocService==null){
        	throw new RuntimeException("Workflow Service is unavailable");
        }
		
		try{
			//get a user name
            String username=getCurrentUser();
            
	        String collaborateComment = "Collaborate by CluProposalService";
	        
	        //create and route a Collaborate workflow
	        //Get the document app Id
	        CluProposal cluProposal = getCluProposalFromWorkflowId(docId);
	        CluInfo cluInfo = cluProposal.getCluInfo();
            String workflowDocTypeId = "CluCollaboratorDocument";//TODO make sure this name is correct
            DocumentResponse docResponse = simpleDocService.create(username, cluInfo.getId(), workflowDocTypeId, cluInfo.getOfficialIdentifier().getLongName());
            if (StringUtils.isNotBlank(docResponse.getErrorMessage())) {
            	throw new RuntimeException("Error found creating document: " + docResponse.getErrorMessage());
            }
            
            //Get the document xml
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	        Document doc = docBuilder.newDocument();
	        
	        Element root = doc.createElement("cluCollaborator");
	        doc.appendChild(root);
	        
	        Element cluIdElement = doc.createElement("cluId");
	        root.appendChild(cluIdElement);
	        
	        Text cluIdText = doc.createTextNode(cluInfo.getId());
	        cluIdElement.appendChild(cluIdText);
	        
	        Element routingPrincipalIdRoleAttribute = doc.createElement("PrincipalIdRoleAttribute");
	        root.appendChild(routingPrincipalIdRoleAttribute);
	        
	        Element recipientPrincipalIdElement = doc.createElement("recipientPrincipalId");
	        routingPrincipalIdRoleAttribute.appendChild(recipientPrincipalIdElement);
	        
	        Text recipientPrincipalIdText = doc.createTextNode(recipientPrincipalId);
	        recipientPrincipalIdElement.appendChild(recipientPrincipalIdText);

	        Element principalIdElement = doc.createElement("principalId");
	        root.appendChild(principalIdElement);
	        
	        Text principalIdText = doc.createTextNode(username);
	        principalIdElement.appendChild(principalIdText);
	        
	        Element docIdElement = doc.createElement("docId");
	        root.appendChild(docIdElement);
	        
	        Text docIdText = doc.createTextNode(docId);
	        docIdElement.appendChild(docIdText);
	        
	        Element collaboratorTypeElement = doc.createElement("collaboratorType");
	        root.appendChild(collaboratorTypeElement);
	        
	        Text collaboratorTypeText = doc.createTextNode(collabType);
	        collaboratorTypeElement.appendChild(collaboratorTypeText);

	        Element participationRequiredElement = doc.createElement("participationRequired");
	        root.appendChild(participationRequiredElement);
	        
	        Text participationRequiredText = doc.createTextNode(Boolean.toString(participationRequired));
	        participationRequiredElement.appendChild(participationRequiredText);
	        
	        Element respondByElement = doc.createElement("respondBy");
	        root.appendChild(respondByElement);
	        
	        Text respondByText = doc.createTextNode(respondBy);
	        respondByElement.appendChild(respondByText);
	        
	        DOMSource domSource = new DOMSource(doc);
	        StringWriter writer = new StringWriter();
	        StreamResult result = new StreamResult(writer);
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.transform(domSource, result);
            
            String docContent = writer.toString();

            //Do the routing
            StandardResponse stdResp = simpleDocService.route(docResponse.getDocId(), username, cluInfo.getOfficialIdentifier().getLongName(), docContent, collaborateComment);
            
            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new RuntimeException("Error found routing document: " + stdResp.getErrorMessage());
        	}
            
		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
    }

	@Override
    public HashMap<String, ArrayList<String>> getCollaborators(String docId){
		//FIXME put in matching for 4 collaborator types
		try{
        if(workflowUtilityService==null){
        	throw new RuntimeException("Workflow Service is unavailable");
        }

		HashMap<String, ArrayList<String>> results = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> coAuthors = new ArrayList<String>();
		ArrayList<String> commentors= new ArrayList<String>();
		ArrayList<String> viewers = new ArrayList<String>();
		ArrayList<String> delegates = new ArrayList<String>();
		
/*        ActionRequestDTO[] items= workflowUtilityService.getAllActionRequests(Long.parseLong(docId));
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
        }*/
        
        results.put("Co-Authors", coAuthors);
        results.put("Commentor", commentors);
        results.put("Viewer", viewers);
        results.put("Delegate", delegates);
        return results;
		}catch(Exception e){
            throw new RuntimeException("Error getting actions Requested",e);
		}
    }
	
	@Override
	public Boolean loginBackdoor(String backdoorId) {
		try{
			//Set spring security principal to the new backdoorId
		    Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();

		    GrantedAuthority[] authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	
		    User u = new User(backdoorId, backdoorId, true, true, true, true, authorities);
		     
		    Authentication auth = new UsernamePasswordAuthenticationToken(u, credentials, authorities);

		    SecurityContextHolder.getContext().setAuthentication(auth);
		}catch(Exception e){
			e.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	private String getCurrentUser() {
        String username=DEFAULT_USER_ID;//FIXME this is bad, need to find some kind of mock security context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        if(auth!=null){
        	Object obj = auth.getPrincipal();
        	if (obj instanceof UserDetails) {
            	username = ((UserDetails)obj).getUsername();
            } else {
            	username = obj.toString();
            }
        }
		return username;
	}

	public void setSimpleDocService(SimpleDocumentActionsWebService simpleDocService) {
		this.simpleDocService = simpleDocService;
	}

	public void setWorkflowUtilityService(WorkflowUtility workflowUtilityService) {
		this.workflowUtilityService = workflowUtilityService;
	}

	public OrganizationService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrganizationService orgService) {
		this.orgService = orgService;
	}

    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#createProposal(org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO)
     */
    @Override
    public CluProposalModelDTO createProposal(CluProposalModelDTO cluProposalDTO) {
        //TODO: Actually return the whole structure.... maybe?
        MapContext ctx = new MapContext();
        //CluProposalModelDTO result = new CluProposalModelDTO();
        try{
            CluInfo cluInfo = (CluInfo)ctx.fromModelDTO(cluProposalDTO);
            cluInfo = service.createClu(cluInfo.getType(), cluInfo);
            //CluProposalModelDTO result = (ModelDTO)ctx.fromBean(cluInfo);
            System.out.println(cluInfo.getId());
            StringType id = new StringType();
            id.set(cluInfo.getId());
            cluProposalDTO.put("id", id);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return cluProposalDTO;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#saveProposal(org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO)
     */
    @Override
    public CluProposalModelDTO saveProposal(CluProposalModelDTO cluProposalDTO) {
        return null;
    }
    
    @Override
    public CluProposalModelDTO getProposalModelDTO(String id){
        MapContext ctx = new MapContext();
        CluProposalModelDTO result = null;
        try {
            //CluProposal cluProposal = new CluProposal();
            //cluProposal.setProposalInfo(proposalInfo);
            
            //String parentCluId = proposalInfo.getProposalReference().get(0);
            CluInfo cluInfo = service.getClu(id);
            //List<CluCluRelationInfo> cluRelations = service.getCluCluRelationsByClu(parentCluId);
            
            /*            List<CluInfo> activities = new ArrayList<CluInfo>();
                       for (CluCluRelationInfo relInfo:cluRelations){
                           if (relInfo.getType().equals("proposal.activity")){
                               activities.add(service.getClu(relInfo.getRelatedCluId()));
                           }
                       }*/
            //cluProposal.setCluInfo(parentClu);
            //cluProposal.setActivities(activities);
            ModelDTO cluModelDTO = (ModelDTO)ctx.fromBean(cluInfo);
            result = new CluProposalModelDTO();
            result.copyFrom(cluModelDTO);

        } catch (Exception e) {
            e.printStackTrace();
            }
        return result;
    }

}
