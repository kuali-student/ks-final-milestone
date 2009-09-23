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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.dto.ActionRequestDTO;
import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.common.ui.server.mvc.dto.BeanMappingException;
import org.kuali.student.common.ui.server.mvc.dto.MapContext;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.workflow.CluProposalCollabRequestDocInfo;
import org.kuali.student.lum.lu.dto.workflow.CluProposalDocInfo;
import org.kuali.student.lum.lu.dto.workflow.PrincipalIdRoleAttribute;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in.
 *
 * @author Kuali Student Team
 */
// Fixme: Replace Object with ProposalService interface class
public class CluProposalRpcGwtServlet extends BaseRpcGwtServletAbstract<LuService> implements CluProposalRpcService {

    final Logger logger = Logger.getLogger(CluProposalRpcGwtServlet.class);

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_USER_ID = "user1";
    private static final String WF_TYPE_CLU_DOCUMENT = "CluDocument";
    private static final String WF_TYPE_CLU_COLLABORATOR_DOCUMENT =  "CluCollaboratorDocument";
    private SimpleDocumentActionsWebService simpleDocService;
    private WorkflowUtility workflowUtilityService;
    private OrganizationService orgService;




	@Override
	public CluProposalModelDTO getCluProposalFromWorkflowId(String docId) {
        if(simpleDocService==null){
        	throw new RuntimeException("Workflow Service is unavailable");
        }

        //get a user name
        String username = getCurrentUser();

        DocumentResponse docResponse = simpleDocService.getDocument(docId, username);
        if(docResponse==null||StringUtils.isNotBlank(docResponse.getErrorMessage())){
        	throw new RuntimeException("Error found gettting document: " + docResponse.getErrorMessage());
        }

        CluProposalModelDTO proposal = getProposal(docResponse.getAppDocId());

		return proposal;
	}
	
	@Override
	public String getWorkflowIdFromCluId(String cluId) {
        if(null==simpleDocService){
        	throw new RuntimeException("Workflow Service is unavailable");
        }

        DocumentDetailDTO docDetail;
		try {
			docDetail = workflowUtilityService.getDocumentDetailFromAppId(WF_TYPE_CLU_DOCUMENT, cluId);
	        if(null==docDetail){
	        	return null;
	        }
	        Long workflowId=docDetail.getRouteHeaderId();
	        return null==workflowId?null:workflowId.toString();
		} catch (Exception e) {
			logger.info("Call Failed getting workflowId for cluId: "+cluId, e);
		}
		return null;
    }

	@Override
	public String getActionsRequested(CluProposalModelDTO cluProposal) {
        try{
		if(workflowUtilityService==null){
        	throw new RuntimeException("Workflow Service is unavailable");
        }
		if(null==cluProposal){
			throw new RuntimeException("Null cluProposal sent");
		}
		if(null==cluProposal.get("id")){
			logger.info("No Clu Id was set for the proposal");
			return "";
		}

        //get a user name
        String username = getCurrentUser();

        //Lookup the workflowId from the cluId
        String workflowDocTypeId = "CluDocument";
        DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(workflowDocTypeId, ((StringType)cluProposal.get("id")).get());
        if(docDetail==null){
        	throw new RuntimeException("Error found gettting document. " );
        }
        
		//Build up a string of actions requested from the attribute set.  The actions can be S, F,A,C,K. examples are "A" "AF" "FCK" "SCA"
        logger.debug("Calling action requested with user:"+username+" and docId:"+docDetail.getRouteHeaderId());

        Map<String,String> results = new HashMap<String,String>();
        for(ActionRequestDTO request:docDetail.getActionRequests()){
    		if(request.getPrincipalId()!=null&&request.getPrincipalId().equals(username)){
    			results.put(request.getActionRequested(), "true");
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
        	return actionsRequested;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting actions Requested",e);
        }
	}


	@Override
	public Boolean approveProposal(CluProposalModelDTO cluProposal) {
        if(simpleDocService==null){
        	throw new RuntimeException("Workflow Service is unavailable");
        }

		try{
            //get a user name
            String username = getCurrentUser();

            //Lookup the workflowId from the cluId
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(WF_TYPE_CLU_DOCUMENT, ((StringType)cluProposal.get("id")).get());
            if(docDetail==null){
            	throw new RuntimeException("Error found gettting document. " );
            }
            
	        String approveComment = "Approved by CluProposalService";

	        StandardResponse stdResp = simpleDocService.approve(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), getCluProposalDocContent(cluProposal), approveComment);
            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new RuntimeException("Error found approving document: " + stdResp.getErrorMessage());
        	}

		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
	}

	@Override
	public Boolean disapproveProposal(CluProposalModelDTO cluProposal) {
        if(simpleDocService==null){
        	throw new RuntimeException("Workflow Service is unavailable");
        }

		try{
            //get a user name
            String username = getCurrentUser();
            
            //Lookup the workflowId from the cluId
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(WF_TYPE_CLU_DOCUMENT, ((StringType)cluProposal.get("id")).get());
            if(docDetail==null){
            	throw new RuntimeException("Error found gettting document. " );
            }
            
	        String disapproveComment = "Disapproved by CluProposalService";

	        //String docId, String principalId, String docTitle, String docContent, String annotation
	        StandardResponse stdResp = simpleDocService.disapprove(docDetail.getRouteHeaderId().toString(), username, disapproveComment);

	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new RuntimeException("Error found disapproving document: " + stdResp.getErrorMessage());
        	}

		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
	}


	@Override
	public Boolean acknowledgeProposal(CluProposalModelDTO cluProposal) {
        if(simpleDocService==null){
        	throw new RuntimeException("Workflow Service is unavailable");
        }

		try{
			//get a user name
            String username=getCurrentUser();
            //Lookup the workflowId from the cluId
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(WF_TYPE_CLU_DOCUMENT, ((StringType)cluProposal.get("id")).get());
            if(docDetail==null){
            	throw new RuntimeException("Error found gettting document. " );
            }
            
	        String acknowledgeComment = "Acknowledged by CluProposalService";

	        //String docId, String principalId, String docTitle, String docContent, String annotation
	        StandardResponse stdResp = simpleDocService.acknowledge(docDetail.getRouteHeaderId().toString(), username, acknowledgeComment);

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
	        CluProposalModelDTO cluProposal = getCluProposalFromWorkflowId(docId);
            
            StringType titleType = ((StringType)cluProposal.get("/officialIdentifier/longName"));
            String title = titleType==null?"NoLongNameSet":titleType.get();
            
            DocumentResponse docResponse = simpleDocService.create(username, docId, WF_TYPE_CLU_COLLABORATOR_DOCUMENT, title);
            if (StringUtils.isNotBlank(docResponse.getErrorMessage())) {
            	throw new RuntimeException("Error found creating document: " + docResponse.getErrorMessage());
            }

            //Get the document xml
    		CluProposalCollabRequestDocInfo docContent = new CluProposalCollabRequestDocInfo();

    		docContent.setCluId(((StringType)cluProposal.get("id")).get());
    		docContent.setPrincipalIdRoleAttribute(new PrincipalIdRoleAttribute());
    		docContent.getPrincipalIdRoleAttribute().setRecipientPrincipalId(recipientPrincipalId);
    		docContent.setPrincipalId(username);
    		docContent.setDocId(docId);
    		docContent.setCollaboratorType(collabType);
    		docContent.setParticipationRequired(participationRequired);
    		docContent.setRespondBy(respondBy);

    		JAXBContext context = JAXBContext.newInstance(docContent.getClass());
    		Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
    		marshaller.marshal(docContent, writer);

            String docContentString = writer.toString();

            //Do the routing
            StandardResponse stdResp = simpleDocService.route(docResponse.getDocId(), username, docResponse.getTitle(), docContentString, collaborateComment);

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
		try{
        if(workflowUtilityService==null){
        	throw new RuntimeException("Workflow Service is unavailable");
        }

		HashMap<String, ArrayList<String>> results = new HashMap<String, ArrayList<String>>();

		ArrayList<String> coAuthors = new ArrayList<String>();
		ArrayList<String> commentors= new ArrayList<String>();
		ArrayList<String> viewers = new ArrayList<String>();
		ArrayList<String> delegates = new ArrayList<String>();

		ActionRequestDTO[] items= workflowUtilityService.getAllActionRequests(Long.parseLong(docId));
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

    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#createProposal(org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO)
     */
    @Override
    public CluProposalModelDTO createProposal(CluProposalModelDTO cluProposalDTO) {
        MapContext ctx = new MapContext();
        //CluProposalModelDTO result = new CluProposalModelDTO();
        try{
            CluInfo cluInfo = (CluInfo)ctx.fromModelDTO(cluProposalDTO);
            cluInfo = service.createClu(cluInfo.getType(), cluInfo);
            ModelDTO cluModelDTO = (ModelDTO)ctx.fromBean(cluInfo);
            cluProposalDTO.copyFrom(cluModelDTO);
            
            saveCourseFormats(cluProposalDTO);
            
            //Do Workflow Create and save docContent
            //get a user name
            String username=getCurrentUser();

            String title = cluInfo.getOfficialIdentifier()==null?"NoLongNameSet":cluInfo.getOfficialIdentifier().getLongName();
            title = title==null?"NoLongNameSet":title;
            
            DocumentResponse docResponse = simpleDocService.create(username, cluInfo.getId(), WF_TYPE_CLU_DOCUMENT, title);
            if (StringUtils.isNotBlank(docResponse.getErrorMessage())) {
            	throw new RuntimeException("Error found creating document: " + docResponse.getErrorMessage());
            }
            
            if(null!=cluProposalDTO.get("adminOrg")){
	            String saveComment = "Created By CluProposalService";
	            StandardResponse stdResp = simpleDocService.save(docResponse.getDocId(), username, title,getCluProposalDocContent(cluProposalDTO), saveComment);
	            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
	            	throw new RuntimeException("Error found saving document: " + stdResp.getErrorMessage());
	            }
            }
        }
        catch(Exception e){
            if (!e.getMessage().contains("No remote services available")){
                e.printStackTrace();
                throw new RuntimeException("Error saving Proposal. ",e);
            }
        }

        return cluProposalDTO;
    }

    private void saveCourseFormats(CluProposalModelDTO cluProposalDTO) throws Exception{
        ModelDTOValue.ListType courseFormatListType = (ModelDTOValue.ListType)cluProposalDTO.get("courseFormats");
        MapContext ctx = new MapContext();
        
        if (courseFormatListType != null){
            List<ModelDTOValue> courseFormatList = courseFormatListType.get();
            
            for (ModelDTOValue value:courseFormatList){
                ModelDTO courseFormatModelDTO = ((ModelDTOValue.ModelDTOType)value).get();
                
                CluInfo courseFormatShell = (CluInfo)ctx.fromModelDTO(courseFormatModelDTO);
                
                System.out.println(courseFormatShell.getType());
                                
            }
        }
        
    }
    
    /**
     * This overridden method ...
     *
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#saveProposal(org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO)
     */
    @Override
    public CluProposalModelDTO saveProposal(CluProposalModelDTO cluProposalDTO) {
        MapContext ctx = new MapContext();
        try{
            logger.debug("DOING AN UPDATE");
            CluInfo cluInfo = (CluInfo)ctx.fromModelDTO(cluProposalDTO);
            cluInfo = service.updateClu(cluInfo.getId(), cluInfo);
            ModelDTO cluModelDTO = (ModelDTO)ctx.fromBean(cluInfo);
            cluProposalDTO = new CluProposalModelDTO();
            cluProposalDTO.copyFrom(cluModelDTO);
            
            saveCourseFormats(cluProposalDTO);
            //get a user name
            String username = getCurrentUser();
            
            //Lookup the workflowId from the cluId
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(WF_TYPE_CLU_DOCUMENT, cluInfo.getId());
            
            //Check that the call was successful
            if(docDetail==null){
            	throw new RuntimeException("Error found gettting document: ");
            }
            
            String title = cluInfo.getOfficialIdentifier()==null?"NoLongNameSet":cluInfo.getOfficialIdentifier().getLongName();
            title = title==null?"NoLongNameSet":title;
            
            if ( (KEWConstants.ROUTE_HEADER_INITIATED_CD.equals(docDetail.getDocRouteStatus())) ||
            	 (KEWConstants.ROUTE_HEADER_SAVED_CD.equals(docDetail.getDocRouteStatus())) ) {
                StandardResponse stdResp = simpleDocService.save(docDetail.getRouteHeaderId().toString(), username, title, getCluProposalDocContent(cluProposalDTO), "");
                if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
                	throw new RuntimeException("Error found saving document: " + stdResp.getErrorMessage());
                }
            }
            else {
            	StandardResponse stdResp = simpleDocService.saveDocumentContent(docDetail.getRouteHeaderId().toString(), username, title, getCluProposalDocContent(cluProposalDTO));
            	if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
            		throw new RuntimeException("Error found updating document: " + stdResp.getErrorMessage());
            	}
            }
            
        }
        catch(Exception e){
            if (!e.getMessage().contains("No remote services available")){
                throw new RuntimeException("Error saving Proposal. ",e);
            }
        }

        return cluProposalDTO;
    }
    


    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#deleteProposal(java.lang.String)
     */
    @Override
    public Boolean deleteProposal(String id) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return Boolean.FALSE;
    }
    
	@Override
	public Boolean submitProposal(CluProposalModelDTO cluProposalDTO) {
		try {
            if(simpleDocService==null){
            	throw new RuntimeException("Workflow Service is unavailable");
            }

            //get a user name
            String username = getCurrentUser();

            //Get the workflow ID
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(WF_TYPE_CLU_DOCUMENT, ((StringType)cluProposalDTO.get("id")).get());

            if(docDetail==null){
            	throw new RuntimeException("Error found gettting document. " );
            }

            String routeComment = "Routed By CluProposalService";

            StandardResponse stdResp = simpleDocService.route(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), getCluProposalDocContent(cluProposalDTO), routeComment);

            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new RuntimeException("Error found routing document: " + stdResp.getErrorMessage());
        	}


        } catch (Exception e) {
            e.printStackTrace();
        }

        return Boolean.TRUE;
	}

    @Override
    public CluProposalModelDTO getProposal(String id){
        MapContext ctx = new MapContext();
        CluProposalModelDTO result = null;
        try {
            //CluProposal cluProposal = new CluProposal();
            //cluProposal.setProposalInfo(proposalInfo);

            //String parentCluId = proposalInfo.getProposalReference().get(0);
            CluInfo cluInfo = service.getClu(id);
            //List<CluCluRelationInfo> cluRelations = service.getCluCluRelationsByClu(parentCluId);

            /*
            List<CluInfo> activities = new ArrayList<CluInfo>();
            for (CluCluRelationInfo relInfo:cluRelations){
                if (relInfo.getType().equals("proposal.activity")){
                    activities.add(service.getClu(relInfo.getRelatedCluId()));
                }
            }
            */

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
	
	private String getCluProposalDocContent(CluProposalModelDTO cluProposal){
    	try{

    		CluProposalDocInfo docContent = new CluProposalDocInfo();
    		
    		StringType idType= (StringType)cluProposal.get("id");
    		String cluId = idType==null?"":idType.get(); 

    		StringType adminOrgType= (StringType)cluProposal.get("adminOrg");
    		String adminOrg = adminOrgType==null?"":adminOrgType.get(); 

    		
    		docContent.setCluId(cluId);
            docContent.setOrgId(adminOrg);
            
    		JAXBContext context = JAXBContext.newInstance(docContent.getClass());
    		Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
    		marshaller.marshal(docContent, writer);
    		return writer.toString();

    	}catch(Exception e){
    		throw new RuntimeException("Error creating Document content for Clu. ", e);
    	}
	}
    
	@Override
    public Boolean adhocRequest(String docId, String recipientPrincipalId, String requestType, String annotation) {
        if (simpleDocService == null) {
            throw new RuntimeException("Workflow Service is unavailable");
        }

        try {
            // get a user name
            String username = getCurrentUser();

            String fyiAnnotation = "FYI by CluProposalService";
            String approveAnnotation = "Approve by CluProposalService";
            String ackAnnotation = "Ack by CluProposalService";
            // create and route a Collaborate workflow
            // Get the document app Id
            /*
             * CluProposal cluProposal = getCluProposalFromWorkflowId(docId); CluInfo cluInfo = cluProposal.getCluInfo();
             * String workflowDocTypeId = "CluCollaboratorDocument";// TODO make // sure this // name is // correct
             * DocumentResponse docResponse = simpleDocService.create(username, docId, workflowDocTypeId,
             * cluInfo.getOfficialIdentifier() .getLongName()); if (StringUtils.isNotBlank(docResponse.getErrorMessage())) {
             * throw new RuntimeException("Error found creating document: " + docResponse.getErrorMessage()); }
             */
            // Do the adHoc
            // Error with the simpleDocService.requestAdHocXXXToPrincipal method. the workflow document is getting routed to the wrong user.
            // Please change the order once this has been fixed in the rice api. 
            if (requestType.equals("FYI")) {
//                StandardResponse stdResp = simpleDocService.requestAdHocFyiToPrincipal(docId, username, recipientPrincipalId, fyiAnnotation);
                StandardResponse stdResp = simpleDocService.requestAdHocFyiToPrincipal(docId,recipientPrincipalId, username, fyiAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new RuntimeException("Error found in Adhoc FYI: " + stdResp.getErrorMessage());
                }
            }
            if (requestType.equals("Approve")) {
//                StandardResponse stdResp = simpleDocService.requestAdHocApproveToPrincipal(docId, username, recipientPrincipalId, approveAnnotation);
                StandardResponse stdResp = simpleDocService.requestAdHocApproveToPrincipal(docId, recipientPrincipalId,username, approveAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new RuntimeException("Error found in Adhoc Approve: " + stdResp.getErrorMessage());
                }
            }
            if (requestType.equals("Acknowledge")) {
//                StandardResponse stdResp = simpleDocService.requestAdHocAckToPrincipal(docId, username, recipientPrincipalId, ackAnnotation);
                StandardResponse stdResp = simpleDocService.requestAdHocAckToPrincipal(docId,recipientPrincipalId,username, ackAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new RuntimeException("Error found in Adhoc Ack: " + stdResp.getErrorMessage());
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
            return new Boolean(false);
        }
        return new Boolean(true);
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
}
