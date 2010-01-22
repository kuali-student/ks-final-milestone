/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.lu.ui.course.server.gwt;


import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.dto.ActionRequestDTO;
import org.kuali.rice.kew.dto.DocumentContentDTO;
import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.dto.RouteNodeInstanceDTO;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.AssemblerFilterManager;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.ui.server.applicationstate.ApplicationStateManager;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.common.ui.server.mvc.dto.BeanMapper;
import org.kuali.student.common.ui.server.mvc.dto.BeanMappingException;
import org.kuali.student.common.ui.server.mvc.dto.DefaultBeanMapper;
import org.kuali.student.common.ui.server.mvc.dto.MapContext;
import org.kuali.student.common.ui.server.mvc.dto.PropertyMapping;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lu.assembly.CreditCourseProposalAssembler;
import org.kuali.student.lum.lu.assembly.CreditCourseProposalWorkflowAssemblerFilter;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.workflow.CluProposalCollabRequestDocInfo;
import org.kuali.student.lum.lu.dto.workflow.CluProposalDocInfo;
import org.kuali.student.lum.lu.dto.workflow.PrincipalIdRoleAttribute;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.DataSaveResult;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.User;

/**
 * GWT service orchestration code for creating and modifying clu proposals.
 *
 * @author Kuali Student Team
 */
public class CluProposalRpcGwtServlet extends BaseRpcGwtServletAbstract<LuService> implements CluProposalRpcService {

	final Logger logger = Logger.getLogger(CluProposalRpcGwtServlet.class);

    private static final long serialVersionUID = 1L;

    // ID of only user ('admin') who can initiate a CluDocument workflow
    private static final String DEFAULT_USER_ID = "3";

    private static final String WF_TYPE_CLU_DOCUMENT = "CluDocument";
    private static final String WF_TYPE_CLU_COLLABORATOR_DOCUMENT =  "CluCollaboratorDocument";
    private static final String PROPOSAL_REFERENCE_TYPE = "kuali.proposal.referenceType.clu";
    
    private static final String CLU_INFO_KEY = "cluInfo";
    private static final String PROPOSAL_INFO_KEY = "proposalInfo";
    
    //Rice Services
    private SimpleDocumentActionsWebService simpleDocService;
    private WorkflowUtility workflowUtilityService;
    private PermissionService permissionService;
    
    //Core Services
    private OrganizationService orgService;
    private ProposalService proposalService;
    private LearningObjectiveService learningObjectiveService;
    private ApplicationStateManager applicationStateManager;
    
    /* TODO - this should all go away when
     * a) we figure out how to do multiple LO's in the dictionary, so the configurable UI can handle
     *    this for us by using a key of "desc/plain", or
     * b) the new orchestration framework makes all this moot
     */
    private static MapContext loMapContext;
    static {
	    loMapContext = new MapContext();
	    BeanMapper loBeanMapper = loMapContext.getBeanMapper(LoInfo.class.getName(), new DefaultBeanMapper());
	    loBeanMapper.addPropertyMapping("desc", new LoDescMapper());
	    loMapContext.addBeanMapper(LoInfo.class.getName(), loBeanMapper);
    }

	// Handle ModelDTO<->LoInfo for LO's "desc" RichText
	public static class LoDescMapper implements PropertyMapping {

		/* (non-Javadoc)
		 * @see org.kuali.student.common.ui.server.mvc.dto.PropertyMapping#fromModelDTOValue(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue, org.kuali.student.common.ui.server.mvc.dto.MapContext)
		 */
		@Override
		public Object fromModelDTOValue(ModelDTOValue value, MapContext context)
				throws BeanMappingException {
			assert value instanceof ModelDTOValue.StringType;
			RichTextInfo desc = new RichTextInfo();
			String descStr = ((ModelDTOValue.StringType) value).get();
			desc.setPlain(descStr);
			desc.setFormatted(descStr);
			return desc;
		}

		/* (non-Javadoc)
		 * @see org.kuali.student.common.ui.server.mvc.dto.PropertyMapping#toModelDTOValue(java.lang.Object, org.kuali.student.common.ui.server.mvc.dto.MapContext)
		 */
		@Override
		public ModelDTOValue toModelDTOValue(Object source, MapContext context)
				throws BeanMappingException {
			assert source instanceof RichTextInfo;
			ModelDTOValue.StringType returnVal = new ModelDTOValue.StringType();
			returnVal.set(((RichTextInfo) source).getPlain());
			return returnVal;
		}
	}
	/* End of:
	 * TODO - this should all go away when ...
	 */

	@Override
	public Data getCluProposalFromWorkflowId(String docId) throws OperationFailedException{
        if(simpleDocService==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

        //get a user name
        String username = getCurrentUser();

        DocumentResponse docResponse = simpleDocService.getDocument(docId, username);
        if(docResponse==null||StringUtils.isNotBlank(docResponse.getErrorMessage())){
        	throw new OperationFailedException("Error found gettting document: " + docResponse.getErrorMessage());
        }

        Data proposal = getCreditCourseProposal(docResponse.getAppDocId());

		return proposal;
	}
	
	@Override
	public String getWorkflowIdFromProposalId(String proposalId) throws OperationFailedException {
    	logger.info("Looking up workflow for proposalId: "+proposalId);

		if(null==simpleDocService){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

        DocumentDetailDTO docDetail;
		try {
			docDetail = workflowUtilityService.getDocumentDetailFromAppId(WF_TYPE_CLU_DOCUMENT, proposalId);
	        if(null==docDetail){
	        	logger.error("Nothing found for proposalId: "+proposalId);
	        	return null;
	        }
	        Long workflowId=docDetail.getRouteHeaderId();
	        return null==workflowId?null:workflowId.toString();
		} catch (Exception e) {
			logger.error("Call Failed getting workflowId for proposalId: "+proposalId, e);
		}
		return null;
    }

	@Override
	public String getActionsRequested(String cluProposalId) throws OperationFailedException {
        try{
		if(workflowUtilityService==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		if(null==cluProposalId){
			logger.info("No Clu Id was set for the proposal");
			return "";
		}

        //get a user name
        String username = getCurrentUser();

        //Lookup the workflowId from the cluId
        String workflowDocTypeId = "CluDocument";
        DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(workflowDocTypeId, cluProposalId);
        if(docDetail==null){
        	throw new OperationFailedException("Error found gettting document. " );
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
            throw new OperationFailedException("Error getting actions Requested",e);
        }
	}

	@Override
	public Boolean approveDocument(String requestDocId){
        if(simpleDocService==null){
        	logger.error("Workflow Service is unavailable");
        	return Boolean.FALSE;
        }

		try{
			DocumentContentDTO docContent = workflowUtilityService.getDocumentContent(Long.parseLong(requestDocId));
			DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetail(Long.parseLong(requestDocId));
            //get a user name
            String username = getCurrentUser();
	        String approveComment = "Approved";

	        StandardResponse stdResp = simpleDocService.approve(requestDocId.toString(), username, docDetail.getDocTitle(), docContent.getApplicationContent(), approveComment);
            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
            	logger.error("Error found approving document: " + stdResp.getErrorMessage());
            	return Boolean.FALSE;
        	}
		}catch(Exception e){
            logger.error("Error approving document",e);
            return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean disapproveDocument(String requestDocId){
        if(simpleDocService==null){
        	logger.error("Workflow Service is unavailable");
        	return Boolean.FALSE;
        }

		try{
			DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetail(Long.parseLong(requestDocId));
            //get a user name
            String username = getCurrentUser();
	        String disapproveComment = "Disapproved";

	        //String docId, String principalId, String docTitle, String docContent, String annotation
	        StandardResponse stdResp = simpleDocService.disapprove(docDetail.getRouteHeaderId().toString(), username, disapproveComment);
	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		logger.error("Error found approving document: " + stdResp.getErrorMessage());
        		return Boolean.FALSE;
        	}
		}catch(Exception e){
            logger.error("Error approving document",e);
            return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	
	@Override
	public Boolean approveProposal(Data cluProposal) throws OperationFailedException {
        if(simpleDocService==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		try{
            //get a user name
            String username = getCurrentUser();
            
            CreditCourseProposalHelper root = CreditCourseProposalHelper.wrap(cluProposal);
            
            //Lookup the workflowId from the cluId
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(WF_TYPE_CLU_DOCUMENT, root.getProposal().getId());
            if(docDetail==null){
            	throw new OperationFailedException("Error found getting document. " );
            }
            
	        String approveComment = "Approved by CluProposalService";
	        
	        StandardResponse stdResp = simpleDocService.approve(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), getCluProposalDocContent(root), approveComment);
            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new OperationFailedException("Error found approving document: " + stdResp.getErrorMessage());
        	}

		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
	}

	@Override
	public Boolean disapproveProposal(Data cluProposal) throws OperationFailedException {
        if(simpleDocService==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		try{
            //get a user name
            String username = getCurrentUser();
            
            CreditCourseProposalHelper root = CreditCourseProposalHelper.wrap(cluProposal);
            
	        //Lookup the workflowId from the cluId
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(WF_TYPE_CLU_DOCUMENT, root.getProposal().getId());
            if(docDetail==null){
            	throw new OperationFailedException("Error found gettting document. " );
            }
            
	        String disapproveComment = "Disapproved by CluProposalService";

	        //String docId, String principalId, String docTitle, String docContent, String annotation
	        StandardResponse stdResp = simpleDocService.disapprove(docDetail.getRouteHeaderId().toString(), username, disapproveComment);

	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new OperationFailedException("Error found disapproving document: " + stdResp.getErrorMessage());
        	}

		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
	}


	@Override
	public Boolean acknowledgeProposal(Data cluProposal) throws OperationFailedException {
        if(simpleDocService==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		try{
			//get a user name
            String username=getCurrentUser();
	        
            CreditCourseProposalHelper root = CreditCourseProposalHelper.wrap(cluProposal);
            
            //Lookup the workflowId from the cluId
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(WF_TYPE_CLU_DOCUMENT, root.getProposal().getId());
            if(docDetail==null){
            	throw new OperationFailedException("Error found gettting document. " );
            }
            
	        String acknowledgeComment = "Acknowledged by CluProposalService";

	        //String docId, String principalId, String docTitle, String docContent, String annotation
	        StandardResponse stdResp = simpleDocService.acknowledge(docDetail.getRouteHeaderId().toString(), username, acknowledgeComment);

	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new OperationFailedException("Error found acknowledging document: " + stdResp.getErrorMessage());
        	}

		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
	}

	private CluInfo getCluInfo(CluProposalModelDTO cluProposal){
		try {
			MapContext ctx = new MapContext();//TODO should/can this be reused?
			ModelDTO cluInfoModelDTO = ((ModelDTOType)cluProposal.get("cluInfo")).get();
			CluInfo cluInfo;
			cluInfo = (CluInfo)ctx.fromModelDTO(cluInfoModelDTO);
			return cluInfo;
		} catch (Exception e) {
			logger.warn("Error converting CluModelDTO to cluInfo",e);
		}
		return null;
	}
	
	private ProposalInfo getProposalInfo(CluProposalModelDTO cluProposal){
		try {
			MapContext ctx = new MapContext();//TODO should/can this be reused?
			ModelDTO proposalInfoModelDTO = ((ModelDTOType)cluProposal.get("proposalInfo")).get();
			ProposalInfo proposalInfo;
			proposalInfo = (ProposalInfo)ctx.fromModelDTO(proposalInfoModelDTO);
			return proposalInfo;
		} catch (Exception e) {
			logger.warn("Error converting ProposalModelDTO to proposalInfo",e);
		}
		return null;
	}
	
    private List<LoInfo> getLoInfo(CluProposalModelDTO cluProposal){
        try {
            MapContext ctx = new MapContext();//TODO should/can this be reused?
            ModelDTO loInfoModelDTO = ((ModelDTOType)cluProposal.get("cluInfo/loInfos" /*LOInfoModelDTO.DTO_KEY*/)).get();
            LoInfo loInfo;
            loInfo = (LoInfo)ctx.fromModelDTO(loInfoModelDTO);
            return null; // loInfo;
        } catch (Exception e) {
            logger.warn("Error converting ProposalModelDTO to loInfo",e);
        }
        return null;
    }	
	@Override
    public Boolean addCollaborator(String docId, String recipientPrincipalId, String collabType, boolean participationRequired, String respondBy) throws OperationFailedException{
        if(simpleDocService==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		try{
			//get a user name
            String username=getCurrentUser();

	        String collaborateComment = "Collaborate by CluProposalService";

	        //create and route a Collaborate workflow
	        //Get the document app Id
	        Data cluProposal = getCluProposalFromWorkflowId(docId);
            CreditCourseProposalHelper root = CreditCourseProposalHelper.wrap(cluProposal);

	        
            String title = root.getProposal().getTitle()==null?"NoNameSet":root.getProposal().getTitle();
            
            DocumentResponse docResponse = simpleDocService.create(username, docId, WF_TYPE_CLU_COLLABORATOR_DOCUMENT, title);
            if (StringUtils.isNotBlank(docResponse.getErrorMessage())) {
            	throw new OperationFailedException("Error found creating document: " + docResponse.getErrorMessage());
            }

            //Get the current routeNodeName
            String routeNodeName="";
            RouteNodeInstanceDTO[] activeNodes = workflowUtilityService.getActiveNodeInstances(Long.decode(docId));
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
            StandardResponse stdResp = simpleDocService.route(docResponse.getDocId(), username, docResponse.getTitle(), docContentString, collaborateComment);

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
        	logger.info("Getting collaborators for docId: "+docId);

	        if(workflowUtilityService==null){
	        	logger.error("No workflow Utility Service is available.");
	        	throw new OperationFailedException("Workflow Service is unavailable");
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
	
	        results.put("Co-Author", coAuthors);
	        results.put("Commentor", commentors);
	        results.put("Viewer", viewers);
	        results.put("Delegate", delegates);
	        logger.info("Returning collaborators: "+results.toString());
	        return results;
		}catch(Exception e){
			logger.error("Error getting actions Requested.",e);
            throw new OperationFailedException("Error getting actions Requested",e);
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
			//Try to put the username in a threadloacal for testing
			this.getThreadLocalRequest().getSession().setAttribute("backdoorId", backdoorId);
			logger.error("Error with backdoor login, saving to a session attribute", e);
		}
		return Boolean.TRUE;
	}

    /**
     * @throws OperationFailedException 
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#createProposal(org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO)
     */
    @Override
    public CluProposalModelDTO createProposal(CluProposalModelDTO cluProposalDTO) throws OperationFailedException {        
        MapContext ctx = new MapContext();

        logger.info("Creating proposal");
        try{                    
            //add application state data
            //applicationStateManager.createOrUpdateApplicationState(cluProposalDTO);

            //Convert cluInfo model dto to cluInfo object
            ModelDTO cluInfoModelDTO = ((ModelDTOType)cluProposalDTO.get(CLU_INFO_KEY)).get();
            CluInfo cluInfo = (CluInfo)ctx.fromModelDTO(cluInfoModelDTO);
            

            //Create clu in LuService
            cluInfo = service.createClu(cluInfo.getType(), cluInfo);
            
            //applicationStateManager.getApplicationState(cluProposalDTO);

            saveCourseFormats(cluInfo, cluInfoModelDTO);
            saveCoursesOfferedJointly(cluInfo, cluProposalDTO);
            saveDynamicAttributes(cluInfo, cluInfoModelDTO);
            
            // save rules
            LuRuleInfoPersistanceBean ruleInfoBean = new LuRuleInfoPersistanceBean();
            ruleInfoBean.setLuService(service);
            ruleInfoBean.updateRules(cluInfo.getId(), cluProposalDTO.getRuleInfos());
            
            // now update the clu with whatever changes were made in save... methods
            cluInfo = service.updateClu(cluInfo.getId(), cluInfo);
            
            // saveLearningObjectives calls an LuService method that ends up
            // updating the Clu to a new version
            saveLearningObjectives(cluInfo, cluProposalDTO);
            
            // so now we need to re-load that new version
            // TODO - this is heinous; far too much LuService interaction,
            // but with separate services, what's to be done?
            cluInfo = service.getClu(cluInfo.getId());
            
            //Copy everything back from updated clu
            ModelDTO cluModelDTO = (ModelDTO)ctx.fromBean(cluInfo);
            cluInfoModelDTO.copyFrom(cluModelDTO);
            
            //Convert proposalInfo model dto to proposalInfo
            ModelDTO proposalInfoModelDTO = ((ModelDTOType)cluProposalDTO.get(PROPOSAL_INFO_KEY)).get();
            ProposalInfo proposalInfo = (ProposalInfo)ctx.fromModelDTO(proposalInfoModelDTO);

            //TODO: Should effective/expiration date be copied from cluInfo or new fields added to screen

            //Add reference to clu in the proposal
            List<String> proposalReferences = new ArrayList<String>();
            proposalReferences.add(cluInfo.getId());
            proposalInfo.setProposalReferenceType(PROPOSAL_REFERENCE_TYPE);
            proposalInfo.setProposalReference(proposalReferences);            
            
            //Create proposal in proposalService
            proposalInfo = proposalService.createProposal(proposalInfo.getType(), proposalInfo);
            ModelDTO proposalModelDTO = (ModelDTO)ctx.fromBean(proposalInfo);
            proposalInfoModelDTO.copyFrom(proposalModelDTO);            

            //Convert loInfo model dto to LoInfo
            /*
            ModelDTO loInfoModelDTO = ((ModelDTOType)cluProposalDTO.get(LOInfoModelDTO.DTO_KEY)).get();
            if(loInfoModelDTO != null && loInfoModelDTO.size() > 0) {
                LoInfo loInfo = (LoInfo)ctx.fromModelDTO(loInfoModelDTO);
            
	            //Create lo in lo service
	            loInfo = learningObjectiveService.createLo(null, loInfo.getType(), loInfo);
            }
            */
            //Do Workflow Create and save docContent
            //get a user name
            String username=getCurrentUser();

            String title = proposalInfo.getName()==null?"NoLongNameSet":proposalInfo.getName();
            title = title==null?"NoLongNameSet":title;
            
            logger.info("Creating proposal Workflow Document.");
            DocumentResponse docResponse = simpleDocService.create(username, proposalInfo.getId(), WF_TYPE_CLU_DOCUMENT, title);
            if (StringUtils.isNotBlank(docResponse.getErrorMessage())) {
            	throw new OperationFailedException("Error found creating document: " + docResponse.getErrorMessage());
            }
            
            if(null!=cluInfo.getAdminOrg()){
//	            String saveComment = "Created By CluProposalService";
//	            StandardResponse stdResp = simpleDocService.save(docResponse.getDocId(), username, title,getCluProposalDocContent(cluInfo,proposalInfo), saveComment);
//	            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
//	            	throw new OperationFailedException("Error found saving document: " + stdResp.getErrorMessage());
//	            }
            }

        }
        catch(Exception e){
            if ((e.getMessage()== null) || (!e.getMessage().contains("No remote services available"))){
                throw new OperationFailedException("Error saving Proposal. ",e);
            }
            logger.error("Error saving Proposal",e);
        }

        return cluProposalDTO;
    }

	/**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#saveProposal(org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO)
     */
    @Override
    public CluProposalModelDTO saveProposal(CluProposalModelDTO cluProposalDTO) throws OperationFailedException{
        MapContext ctx = new MapContext();
        try{
            logger.debug("Updating proposal");

            //Convert proposalInfo model dto to proposalInfo
            ModelDTO proposalInfoModelDTO = ((ModelDTOType)cluProposalDTO.get(PROPOSAL_INFO_KEY)).get();
            ProposalInfo proposalInfo = (ProposalInfo)ctx.fromModelDTO(proposalInfoModelDTO);
            
            // Update proposal
            proposalInfo = proposalService.updateProposal(proposalInfo.getId(), proposalInfo);
            
            // and copy updated proposal back into proposalInfo modeldto
            ModelDTO proposalModelDTO = (ModelDTO)ctx.fromBean(proposalInfo);
            proposalInfoModelDTO.copyFrom(proposalModelDTO);            
            
            /*
            //Convert loInfo model dto to LoInfo
            ModelDTO loInfoModelDTO = ((ModelDTOType)cluProposalDTO.get("cluInfo/loInfos" LOInfoModelDTO.DTO_KEY)).get();
            if(loInfoModelDTO != null) {
                LoInfo loInfo = (LoInfo)ctx.fromModelDTO(loInfoModelDTO);
            
            //Create lo in lo service
                loInfo = learningObjectiveService.updateLo(loInfo.getId(), loInfo);
                loInfoModelDTO.copyFrom(loInfoModelDTO);
            }            
            */
            //Convert cluInfo model dto to cluInfo object
            ModelDTO cluInfoModelDTO = ((ModelDTOType)cluProposalDTO.get(CLU_INFO_KEY)).get();
            CluInfo cluInfo = (CluInfo)ctx.fromModelDTO(cluInfoModelDTO);

            
            saveCourseFormats(cluInfo, cluInfoModelDTO);
            saveCoursesOfferedJointly(cluInfo, cluProposalDTO);
            saveDynamicAttributes(cluInfo, cluInfoModelDTO);
            
            // now update the clu with whatever changes were made in save... methods
            cluInfo = service.updateClu(cluInfo.getId(), cluInfo);
            
            // saveLearningObjectives calls an LuService method that ends up
            // updating the Clu to a new version
            saveLearningObjectives(cluInfo, cluProposalDTO);
            
            // save rules
            LuRuleInfoPersistanceBean ruleInfoBean = new LuRuleInfoPersistanceBean();
            ruleInfoBean.setLuService(service);
            ruleInfoBean.updateRules(cluInfo.getId(), cluProposalDTO.getRuleInfos());
            
            // so now we need to re-load that new version
            // TODO - this is heinous; far too much LuService interaction,
            // but with separate services, what's to be done?
            cluInfo = service.getClu(cluInfo.getId());
            
            //Copy everything back from updated clu
            ModelDTO cluModelDTO = (ModelDTO) ctx.fromBean(cluInfo);
            cluInfoModelDTO.copyFrom(cluModelDTO);
            
            //get a user name
            String username = getCurrentUser();
            
            //Lookup the workflowId from the cluId
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(WF_TYPE_CLU_DOCUMENT, proposalInfo.getId());
            
            //Check that the call was successful
            if(docDetail==null){
            	throw new OperationFailedException("Error found gettting document: ");
            }
            
            String title = proposalInfo.getName()==null?"NoNameSet":proposalInfo.getName();
            title = title==null?"NoLongNameSet":title;
            
            if ( (KEWConstants.ROUTE_HEADER_INITIATED_CD.equals(docDetail.getDocRouteStatus())) ||
            	 (KEWConstants.ROUTE_HEADER_SAVED_CD.equals(docDetail.getDocRouteStatus())) ) {
//                StandardResponse stdResp = simpleDocService.save(docDetail.getRouteHeaderId().toString(), username, title, getCluProposalDocContent(cluInfo,proposalInfo), "");
//                if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
//                	throw new OperationFailedException("Error found saving document: " + stdResp.getErrorMessage());
//                }
            }
            else {
//            	StandardResponse stdResp = simpleDocService.saveDocumentContent(docDetail.getRouteHeaderId().toString(), username, title, getCluProposalDocContent(cluInfo,proposalInfo));
//            	if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
//            		throw new OperationFailedException("Error found updating document: " + stdResp.getErrorMessage());
//            	}
            }
            
        }
        catch(Exception e){
            if (e.getMessage()!=null&&!e.getMessage().contains("No remote services available")){
                throw new OperationFailedException("Error saving Proposal. ",e);
            }
            logger.error("Error saving Proposal",e);
        }

        return cluProposalDTO;
    }
    
    private void saveCourseFormats(CluInfo parentCluInfo, ModelDTO cluInfoModelDTO) throws Exception{
        List<ModelDTOValue> courseFormatList =  cluInfoModelDTO.getList("courseFormats");
        
        if (courseFormatList != null) { 
            MapContext ctx = new MapContext();
	        
            List<String> courseFormatIds = new ArrayList<String>();
            
            // for every 'Course Format' in the proposalDTO
            for (ModelDTOValue courseFormatValue : courseFormatList) {
                //Convert course format shell model dto to clu info
                ModelDTO courseFormatModelDTO = ((ModelDTOType)courseFormatValue).get();                
                CluInfo courseFormatShell = (CluInfo)ctx.fromModelDTO(courseFormatModelDTO);                
                
                courseFormatShell.setType("kuali.lu.type.CreditCourseFormatShell");	// TODO - a constant somewhere?
                courseFormatShell.setState("draft"); 							// TODO - a constant somewhere?
                // TODO - what else should we set from the parentCluInfo?
                // courseFormatShell.setAcademicSubjectOrgs(new ArrayList(parentCluInfo.getAcademicSubjectOrgs()));
	        	
                //Get activities
		        List<ModelDTOValue> activityList = courseFormatModelDTO.getList("activities");
		        List<String> activityIds = new ArrayList<String>();
		        
		        if (activityList != null) {
		        	
		        	for (ModelDTOValue activityValue : activityList) {
		        		ModelDTO activityModelDTO = ((ModelDTOType) activityValue).get();                
		                CluInfo activityCluInfo = (CluInfo) ctx.fromModelDTO(activityModelDTO);
		                /* 
		                // populate activity's fields from overarching CluInfo
		                // could probably just copy some of these List's, rather than creating copies
		                activityCluInfo.setAcademicSubjectOrgs(new ArrayList(parentCluInfo.getAcademicSubjectOrgs()));
		                // activityCluInfo.setAccountingInfo(parentCluInfo.getAccountingInfo()); // ??
		                activityCluInfo.setAccreditationList(parentCluInfo.getAccreditationList()); // ??
		                activityCluInfo.setPrimaryAdminOrg(parentCluInfo.getPrimaryAdminOrg());
		                activityCluInfo.setAlternateAdminOrgs(new ArrayList<AdminOrgInfo>(parentCluInfo.getAlternateAdminOrgs()));
		                activityCluInfo.setAlternateIdentifiers(new ArrayList<CluIdentifierInfo>(parentCluInfo.getAlternateIdentifiers()));
		                // activityCluInfo.setAttributes(new HashMap<String, String>(parentCluInfo.getAttributes()); // ??
		                activityCluInfo.setCampusLocationList(new ArrayList<String>(parentCluInfo.getCampusLocationList()));
		                // don't do this; this is one of the values set by proposer when creating the activity
		                // activityCluInfo.setDefaultEnrollmentEstimate(...
		                activityCluInfo.setDefaultMaximumEnrollment(parentCluInfo.getDefaultMaximumEnrollment());
		                activityCluInfo.setDesc(parentCluInfo.getDesc()); // ??
		                activityCluInfo.setEffectiveDate((Date) parentCluInfo.getEffectiveDate().clone());
		                activityCluInfo.setExpirationDate((Date) parentCluInfo.getExpirationDate().clone());
		                activityCluInfo.setFeeInfo(parentCluInfo.getFeeInfo()); // ??
		                activityCluInfo.setHasEarlyDropDeadline(parentCluInfo.isHasEarlyDropDeadline());
		                activityCluInfo.setHazardousForDisabledStudents(parentCluInfo.isHasEarlyDropDeadline());
		                activityCluInfo.setInstructors(new ArrayList<CluInstructorInfo>(parentCluInfo.getInstructors());
		                activityCluInfo.set
		                */
		                
			            // Create or update clu via LuService
		                if (null == activityCluInfo.getId()) {
			                activityCluInfo = service.createClu(activityCluInfo.getType(), activityCluInfo);
		                } else {
			                activityCluInfo = service.updateClu(activityCluInfo.getId(), activityCluInfo);
		                }
		                
			            ModelDTO newModelDTO = (ModelDTO) ctx.fromBean(activityCluInfo);
		                activityModelDTO.copyFrom(newModelDTO);

		                activityIds.add(activityCluInfo.getId());
		        	}
		        }
		        
	            // Create or update clu via LuService
		        if (null == courseFormatShell.getId()) {
	                courseFormatShell = service.createClu(courseFormatShell.getType(), courseFormatShell);
		        }
		        else {
	                courseFormatShell = service.updateClu(courseFormatShell.getId(), courseFormatShell);
		        }
	                
	            ModelDTO newModelDTO = (ModelDTO) ctx.fromBean(courseFormatShell);
	            courseFormatModelDTO.copyFrom(newModelDTO);
		                
                // create the CourseFormat->Activity relationships
                for (String activityId : activityIds)  {
	                CluCluRelationInfo ccrInfo = new CluCluRelationInfo();
	                ccrInfo.setCluId(courseFormatShell.getId());
	                ccrInfo.setRelatedCluId(activityId);
	                ccrInfo.setType(LUConstants.LU_LU_RELATION_TYPE_CONTAINS);
	                // TODO - ccrInfo.setState("<some kind of 'draft'?");
	                try {
		                service.createCluCluRelation(ccrInfo.getCluId(), ccrInfo.getRelatedCluId(), ccrInfo.getType(), ccrInfo);
	                } catch (AlreadyExistsException aee) {} // should't be a problem
                }
                
                courseFormatIds.add(courseFormatShell.getId());
                
                logger.debug(courseFormatShell.getType());                                
            }
            
            // create the Clu->CourseFormat relationships
            for (String courseFormatId : courseFormatIds)  {
                CluCluRelationInfo ccrInfo = new CluCluRelationInfo();
                ccrInfo.setCluId(parentCluInfo.getId());
                ccrInfo.setRelatedCluId(courseFormatId);
                ccrInfo.setType(LUConstants.LU_LU_RELATION_TYPE_HAS_COURSE_FORMAT);
                // TODO - ccrInfo.setState("<some kind of 'draft'?");
                try {
	                service.createCluCluRelation(ccrInfo.getCluId(), ccrInfo.getRelatedCluId(), ccrInfo.getType(), ccrInfo);
                } catch (AlreadyExistsException aee) {} // should't be a problem
            }
            // TODO - delete activity or course-format and associated activities when delete button is pressed in multiplicities
        }
    }
    
    private void saveCoursesOfferedJointly(CluInfo parentCluInfo, CluProposalModelDTO cluProposalDTO) throws Exception{
        ModelDTOValue.ListType offeredJointlyListType = (ModelDTOValue.ListType) ((ModelDTOValue.ModelDTOType) cluProposalDTO.get("cluInfo")).get().get("offeredJointly");
        
        if (null != offeredJointlyListType) {
	        MapContext ctx = new MapContext();
        	List<ModelDTOValue> offeredJointlyList = offeredJointlyListType.get();
        	
        	for (ModelDTOValue offeredJointlyValue : offeredJointlyList) {
        		ModelDTO offeredJointlyModelDTO = ((ModelDTOValue.ModelDTOType) offeredJointlyValue).get();
                
                try {
	                String offeredJointlyCluId = offeredJointlyModelDTO.getString("id");
                
	                service.getClu(offeredJointlyCluId);
	                
	                // Found it; create a LU_LU_RELATION_TYPE_JOINTLY_OFFERED CluCluRelation
	                CluCluRelationInfo ccrInfo = new CluCluRelationInfo();
	                ccrInfo.setCluId(parentCluInfo.getId());
	                ccrInfo.setRelatedCluId(offeredJointlyCluId);
	                ccrInfo.setType(LUConstants.LU_LU_RELATION_TYPE_CONTAINS);
	                service.createCluCluRelation(parentCluInfo.getId(), offeredJointlyCluId, LUConstants.LU_LU_RELATION_TYPE_JOINTLY_OFFERED, ccrInfo);
                } catch (DoesNotExistException dnee) {
                	// TODO = let the user know there was no such Clu?
                }
        	}
        }
    }
    
    //TODO: Get rid of this, for now this is so we can save data somewhere
    private void saveDynamicAttributes(CluInfo cluInfo, ModelDTO cluInfoModelDTO){        
        Map<String,String> attributes = cluInfo.getAttributes();
        attributes.put("creditType", cluInfoModelDTO.getString("creditType"));
        attributes.put("creditValue", cluInfoModelDTO.getString("creditValue"));
        attributes.put("maxCredits", cluInfoModelDTO.getString("maxCredits"));
        attributes.put("evalType", cluInfoModelDTO.getString("evalType"));
        attributes.put("feeAmount", cluInfoModelDTO.getString("feeAmount"));
        attributes.put("taxable", cluInfoModelDTO.getString("taxable"));
        attributes.put("feeDesc", cluInfoModelDTO.getString("feeDesc"));
        attributes.put("internalNotation", cluInfoModelDTO.getString("internalNotation"));
    }
    
    private void saveLearningObjectives(CluInfo cluInfo, CluProposalModelDTO cluProposalDTO) throws Exception {
        ModelDTOValue.ListType learningObjectiveListType = (ModelDTOValue.ListType) ((ModelDTOValue.ModelDTOType) cluProposalDTO.get("cluInfo")).get().get("loInfos");
        
        if (null != learningObjectiveListType) {
        	
        	List<ModelDTOValue> learningObjectiveList = learningObjectiveListType.get();
        	
        	removeDeletedLOs(cluInfo.getId(), learningObjectiveList);
        	
        	// going to assume here that cluInfo is for a valid Clu that's already been persisted
        	// TODO - this needs to recursively navigate the LO tree(s) that the user has specified in the UI
        	for (ModelDTOValue learningObjectiveValue : learningObjectiveList) {
        		ModelDTO learningObjectiveModelDTO = ((ModelDTOValue.ModelDTOType) learningObjectiveValue).get();
        		// for now, all we have is 'desc' in the ModelDTO, and don't have an LoInfoModelDTO as such
        		// (dictionary only has one; need a list)
        		LoInfo info = (LoInfo) loMapContext.fromModelDTO(learningObjectiveModelDTO);
        		
        		// only create/update LO's with a description entered
        		RichTextInfo desc = info.getDesc();
				if (null != desc && null != desc.getPlain() && desc.getPlain().length() > 0) {
	                // does the LO already exist?
	                LoInfo existingLo =  (null != info.getId() ?
						                	learningObjectiveService.getLo(info.getId()) :
							            		null);
	            	if (null == existingLo) {
	            		// nope; create it
	            		// TODO - hardcoded Repo is just bad; somehow this needs to come from
	            		LoInfo createdLo = learningObjectiveService.createLo("kuali.loRepository.key.singleUse", info.getType(), info);
	            		// create Clu-Lo relation
	            		// TODO - this will be Clu-Lo or Lo-Lo, depending on where we are in the tree
	            		service.addOutcomeLoToClu(createdLo.getId(), cluInfo.getId());
	            	} else {
	            		// update it in case the client updated description, etc.
	            		learningObjectiveService.updateLo(info.getId(), info);
	            		// make sure the proper CluLoRelation exists
	        			if ( ! service.getLoIdsByClu(cluInfo.getId()).contains(existingLo.getId()) ) {
	            			// TODO - will need to be this call or
	            			// learningObjectiveService.createLoLoRelation() when doing multi-level LO's
	            			service.addOutcomeLoToClu(existingLo.getId(), cluInfo.getId());
	        			}
	            	}
				}
        	}
        }
	}

    
    private void removeDeletedLOs(String cluId, List<ModelDTOValue> learningObjectiveList)
										    			throws DoesNotExistException,
										    				   InvalidParameterException,
										    				   MissingParameterException,
										    				   OperationFailedException,
										    				   org.kuali.student.core.exceptions.OperationFailedException, BeanMappingException, DependentObjectsExistException, PermissionDeniedException {
		
		List<String> loIds = service.getLoIdsByClu(cluId);
		Set<String> loIdsFromUser = new TreeSet<String>();
		
		// get all the existing LO id's received from the client
    	for (ModelDTOValue learningObjectiveValue : learningObjectiveList) {
    		ModelDTO learningObjectiveModelDTO = ((ModelDTOValue.ModelDTOType) learningObjectiveValue).get();
    		LoInfo info = (LoInfo) loMapContext.fromModelDTO(learningObjectiveModelDTO);
    		String loId = info.getId();
    		if (null != loId && loId.length() > 0) {
    			loIdsFromUser.add(loId);
    		}
    	}
    	// now see if any of the existing related LO's weren't submitted by the client
    	for (String loId : loIds) {
    		if ( ! loIdsFromUser.contains(loId) ) {
    			// With LO reuse, we need to check that the LO is not related to any other LU's
    			List<String> relatedCluIds = service.getCluIdsByLoId(loId);
    			assert relatedCluIds.size() > 0;
    			if (relatedCluIds.size() == 1) {
    				assert loId.equals(relatedCluIds.get(0));
    				// only Clu this LO's related to is the one this proposal's for, so get rid of it
    				service.removeOutcomeLoFromClu(loId, cluId);
    				learningObjectiveService.deleteLo(loId);
    			}
    		}
    	}
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
	public Boolean submitProposal(Data cluProposal) {
		try {
            if(simpleDocService==null){
            	throw new OperationFailedException("Workflow Service is unavailable");
            }

            //get a user name
            String username = getCurrentUser();

	        //Get the cluInfo
            CreditCourseProposalHelper root = CreditCourseProposalHelper.wrap(cluProposal);
	        
            //Get the workflow ID
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(WF_TYPE_CLU_DOCUMENT, root.getProposal().getId());

            if(docDetail==null){
            	throw new OperationFailedException("Error found getting document. " );
            }

            String routeComment = "Routed By CluProposalService";

            StandardResponse stdResp = simpleDocService.route(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), getCluProposalDocContent(root), routeComment);

            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new OperationFailedException("Error found routing document: " + stdResp.getErrorMessage());
        	}


        } catch (Exception e) {
            e.printStackTrace();
        }

        return Boolean.TRUE;
	}

    /**
     * Retrieves a CluProposalModelDTO given a proposal id.
     * @throws OperationFailedException 
     */
    public CluProposalModelDTO getProposal(String proposalId) throws OperationFailedException{
        MapContext ctx = new MapContext();
        CluProposalModelDTO cluProposalDTO = new CluProposalModelDTO();

        	boolean authorized=true;
        	try{
            logger.debug("Retrieving clu proposal with proposal id " + proposalId);
            
            //Get proposal
            ProposalInfo proposalInfo = proposalService.getProposal(proposalId);
            ModelDTO proposalModelDTO = ((ModelDTOType)cluProposalDTO.get(PROPOSAL_INFO_KEY)).get(); 
            proposalModelDTO.copyFrom((ModelDTO)ctx.fromBean(proposalInfo));
            
            //Get proposal's clu reference id
            List<String> references = proposalInfo.getProposalReference();
            //FIXME: Better error handling if more than one reference or no reference
            assert(references.size() == 1);
            String cluId = references.get(0);
            
        	try{
	        	String QUALIFICATION_PROPOSAL_ID = "proposalId";
	        	String DOCUMENT_TYPE_NAME = "documentTypeName";
	        	AttributeSet qualification = new AttributeSet();
	        	qualification.put(QUALIFICATION_PROPOSAL_ID, proposalInfo.getId());
	  			qualification.put(DOCUMENT_TYPE_NAME, "CluDocument");
	  			authorized = permissionService.isAuthorized(getCurrentUser(), "KS-LUM", "Open Document", null, qualification);
        	}catch(Exception e){
        		logger.info("Error calling permission service. ", e);
        	}
            
            //Get clu
            CluInfo cluInfo = service.getClu(cluId);
            ModelDTO cluModelDTO = ((ModelDTOType)cluProposalDTO.get(CLU_INFO_KEY)).get(); 
            cluModelDTO.copyFrom((ModelDTO) ctx.fromBean(cluInfo));
                     
            getDynamicAttributes(cluInfo, cluModelDTO);
            hydrateCluModelDTO(cluInfo.getId(), cluProposalDTO);

        } catch (Exception e) {
            logger.error("Error getting Proposal. ",e);
        }

    	if(!authorized){
    		throw new OperationFailedException("User is not authorized to open this document");
        }

        return cluProposalDTO;
    }

	/**
     * Retrieves a CluProposalModelDTO given a clu id.
     * @throws OperationFailedException 
     */
    public CluProposalModelDTO getClu(String cluId) throws OperationFailedException{
        MapContext ctx = new MapContext();
        CluProposalModelDTO cluProposalDTO = new CluProposalModelDTO();

        try{
	        logger.debug("Retrieving clu with clu id " + cluId);
        
	        //Get clu
	        CluInfo cluInfo = service.getClu(cluId);
	//        CluInfo cluInfo = buildTestClu();
	        ModelDTO cluModelDTO = ((ModelDTOType)cluProposalDTO.get(CLU_INFO_KEY)).get(); 
	        cluModelDTO.copyFrom((ModelDTO)ctx.fromBean(cluInfo));
	        
	        getDynamicAttributes(cluInfo, cluModelDTO);
	        
	        hydrateCluModelDTO(cluInfo.getId(), cluProposalDTO);
	
	    } catch (Exception e) {
	        logger.error("Error getting Clu. ",e);
	    }
	

        
        return cluProposalDTO;
    }
    
    private void hydrateCluModelDTO(String parentCluId, ModelDTO cluModelDTO) throws OperationFailedException {
        getCourseFormats(parentCluId, cluModelDTO);
        getCoursesOfferedJointly(parentCluId, cluModelDTO);
        getLearningObjectives(parentCluId, cluModelDTO);
	}

	private void getDynamicAttributes(CluInfo cluInfo, ModelDTO cluModelDTO){
	    Map<String, String> attributes = cluInfo.getAttributes();
	    Set<String> keys = attributes.keySet();
	    
	    for (String key:keys){
	        String value = attributes.get(key);
	        cluModelDTO.put(key, value, true);
	    }
	}
	
    private void getCourseFormats(String parentCluId, ModelDTO cluProposalDTO) {
         
        MapContext ctx = new MapContext();
        ModelDTOValue.ListType courseFormatList = new ModelDTOValue.ListType();

        try {	
	        logger.debug("Retrieving course format clu(s) for clu with id: " + parentCluId);
	        
	        List<CluInfo> courseFormatCluInfos = service.getRelatedClusByCluId(parentCluId, LUConstants.LU_LU_RELATION_TYPE_HAS_COURSE_FORMAT);
	               
	        for (CluInfo cfCluInfo : courseFormatCluInfos) {
	        	ModelDTO cluInfoModelDTO = ctx.fromBean(cfCluInfo);
	        	
	        	ModelDTOValue.ModelDTOType cluInfoModelDTOValue = new ModelDTOValue.ModelDTOType();
	        	
	        	cluInfoModelDTOValue.set(cluInfoModelDTO);
	        	
	        	if (null == courseFormatList.get()) {
	        		courseFormatList.set(new ArrayList<ModelDTOValue>());
	        	}
	        	courseFormatList.get().add(cluInfoModelDTOValue);
	        	
		        logger.debug("Retrieving activity clu(s) for clu with id: " + cfCluInfo.getId());
		        List<CluInfo> activityCluInfos = service.getRelatedClusByCluId(cfCluInfo.getId(), LUConstants.LU_LU_RELATION_TYPE_CONTAINS);
		        
		        ModelDTOValue.ListType activityList = new ModelDTOValue.ListType();
		        activityList.set(new ArrayList<ModelDTOValue>());
		        for (CluInfo activityCluInfo : activityCluInfos) {
		        	ModelDTO activityCluDTO = ctx.fromBean(activityCluInfo);
		        	ModelDTOValue.ModelDTOType activityCluDTOValue = new ModelDTOValue.ModelDTOType();
		        	activityCluDTOValue.set(activityCluDTO);
		        	activityList.get().add(activityCluDTOValue);
		        }
		        cluInfoModelDTO.put("activities", activityList, true);
	        }
        	((ModelDTOValue.ModelDTOType) cluProposalDTO.get("cluInfo")).get().put("courseFormats", courseFormatList, true);
	
	    } catch (Exception e) {
	        logger.error("Error getting Clu. " ,e);
	    }
	}
    
	private void getCoursesOfferedJointly(String parentCluId, ModelDTO cluProposalDTO)  {
        MapContext ctx = new MapContext();
        ModelDTOValue.ListType courseList = new ModelDTOValue.ListType();
    	List<ModelDTOValue> courseModelDTOValueList = new ArrayList<ModelDTOValue>();;

		try {
	        logger.debug("Retrieving course format clu(s) for clu with id: " + parentCluId);
	        
			List<CluInfo> jointlyOfferedCluInfos = service.getRelatedClusByCluId(parentCluId, LUConstants.LU_LU_RELATION_TYPE_JOINTLY_OFFERED);
	        
	        for (CluInfo cfCluInfo : jointlyOfferedCluInfos) {
	        	ModelDTO cluInfoModelDTO = ctx.fromBean(cfCluInfo);
	        	
	        	ModelDTOValue.ModelDTOType cluInfoModelDTOValue = new ModelDTOValue.ModelDTOType();
	        	
	        	cluInfoModelDTOValue.set(cluInfoModelDTO);
	        	
				courseModelDTOValueList.add(cluInfoModelDTOValue);
	        }
        	courseList.set(courseModelDTOValueList);
        	
        	((ModelDTOValue.ModelDTOType) cluProposalDTO.get("cluInfo")).get().put("offeredJointly", courseList, true);
	    } catch (Exception e) {
	        logger.error("Error getting Clu. " ,e);
	    }
	}

	private void getLearningObjectives(String parentCluId, ModelDTO cluProposalDTO) {
        ModelDTOValue.ListType loList = new ModelDTOValue.ListType();
    	List<ModelDTOValue> loModelDTOValueList = new ArrayList<ModelDTOValue>();;

		try {
	        logger.debug("Retrieving learning objectives for clu with id: " + parentCluId);
	        
			List<String> loIds = service.getLoIdsByClu(parentCluId);
	        
	        for (String loId : loIds) {
	        	LoInfo loInfo = learningObjectiveService.getLo(loId);
	        	
	        	ModelDTO loInfoModelDTO = loMapContext.fromBean(loInfo);
	        	
	        	ModelDTOValue.ModelDTOType loInfoModelDTOValue = new ModelDTOValue.ModelDTOType();
	        	
	        	loInfoModelDTOValue.set(loInfoModelDTO);
	        	
				loModelDTOValueList.add(loInfoModelDTOValue);
				
	        	// M3 - recursively retrieve "include"ed LO's
	        }
        	loList.set(loModelDTOValueList);
        	
        	((ModelDTOValue.ModelDTOType) cluProposalDTO.get("cluInfo")).get().put("loInfos", loList, true);
	    } catch (Exception e) {
	        logger.error("Error getting learning objective. ", e);
	    }
	}

	private String getCurrentUser() {
		String username = SecurityUtils.getCurrentUserId();
		if(username==null&&this.getThreadLocalRequest().getSession().getAttribute("backdoorId")!=null){
			username=(String)this.getThreadLocalRequest().getSession().getAttribute("backdoorId");
        }
		return username;
	}
	
	private String getCluProposalDocContent(CreditCourseProposalHelper cluProposal) throws OperationFailedException{
    	try{

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
    		throw new OperationFailedException("Error creating Document content for Clu. ", e);
    	}
	}
    
	@Override
    public Boolean adhocRequest(String docId, String recipientPrincipalId, String requestType, String annotation) throws OperationFailedException {
        if (simpleDocService == null) {
            throw new OperationFailedException("Workflow Service is unavailable");
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
             * throw new OperationFailedException("Error found creating document: " + docResponse.getErrorMessage()); }
             */
            // Do the adHoc
            // Error with the simpleDocService.requestAdHocXXXToPrincipal method. the workflow document is getting routed to the wrong user.
            // Please change the order once this has been fixed in the rice api. 
            if (requestType.equals("FYI")) {
//                StandardResponse stdResp = simpleDocService.requestAdHocFyiToPrincipal(docId, username, recipientPrincipalId, fyiAnnotation);
                StandardResponse stdResp = simpleDocService.requestAdHocFyiToPrincipal(docId,recipientPrincipalId, username, fyiAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new OperationFailedException("Error found in Adhoc FYI: " + stdResp.getErrorMessage());
                }
            }
            if (requestType.equals("Approve")) {
//                StandardResponse stdResp = simpleDocService.requestAdHocApproveToPrincipal(docId, username, recipientPrincipalId, approveAnnotation);
                StandardResponse stdResp = simpleDocService.requestAdHocApproveToPrincipal(docId, recipientPrincipalId,username, approveAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new OperationFailedException("Error found in Adhoc Approve: " + stdResp.getErrorMessage());
                }
            }
            if (requestType.equals("Acknowledge")) {
//                StandardResponse stdResp = simpleDocService.requestAdHocAckToPrincipal(docId, username, recipientPrincipalId, ackAnnotation);
                StandardResponse stdResp = simpleDocService.requestAdHocAckToPrincipal(docId,recipientPrincipalId,username, ackAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new OperationFailedException("Error found in Adhoc Ack: " + stdResp.getErrorMessage());
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

    public ProposalService getProposalService() {
        return proposalService;
    }

    public void setProposalService(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

	/**
	 * @param permissionService the permissionService to set
	 */
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
    }
    
    public void setApplicationStateManager(ApplicationStateManager applicationStateManager) {
        this.applicationStateManager = applicationStateManager;
    } 
    
    /**
     * @return the learningObjectiveService
     */
    public LearningObjectiveService getLearningObjectiveService() {
        return learningObjectiveService;
    }

    /**
     * @param learningObjectiveService the learningObjectiveService to set
     */
    public void setLearningObjectiveService(LearningObjectiveService learningObjectiveService) {
        this.learningObjectiveService = learningObjectiveService;
    }
    
//    /**
//     * This method should return a an empty model with associated model definition for 
//     * requested model
//     */
//    public DataModel getCluProposalModelDefinition(String modelId){
//        DataModel model = null;
//        if (CourseConfigurer.CLU_PROPOSAL_MODEL.equals(modelId)){
//            final SimpleModelDefinition def = new SimpleModelDefinition();
//            def.define("proposal", "org.kuali.student.lum.lu.assembly.data.client.proposal.ProposalInfoData");
//            def.define("course", "org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourse");
//            def.define("course/formats/*", "org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourseFormat");
//            def.define("course/formats/*/activities/*", "org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourseActivity");
//            
//            def.define("course/description", "org.kuali.student.lum.lu.assembly.data.client.RichTextInfoData");
//            def.define("course/rationale", "org.kuali.student.lum.lu.assembly.data.client.RichTextInfoData");
//            def.define("course/duration", "org.kuali.student.lum.lu.assembly.data.client.atp.TimeAmountInfoData");
//            def.define("course/primaryInstructor", "org.kuali.student.lum.lu.assembly.data.client.CluInstructorInfoData");
//            def.define("course/alternateIdentifiers", "org.kuali.student.lum.lu.assembly.data.client.CluIdentifierInfoData");
//            
//            def.define("course/formats/*/activities/*/intensity", "org.kuali.student.lum.lu.assembly.data.client.atp.TimeAmountInfoData");
//            
////            
////            def.define("cluInfo/officialIdentifier", "org.kuali.student.lum.lu.dto.CluIdentifier");            
////            def.define("cluInfo/rationale", "org.kuali.student.RichText");
////            def.define("cluInfo/formats/*/cluInfo", "org.kuali.student.lum.lu.dto.CluInfo");
////            def.define("cluInfo/formats/*/activities/*/cluInfo", "org.kuali.student.lum.lu.dto.CluInfo");            
////            def.define("cluInfo/effectiveDate", "Date");
////            def.define("cluInfo/expirationDate", "Date");
////            
//            model = new DataModel(def, new Data());     
//            
//        }
//        return model;
//    }
    
    private Assembler<Data, Void> creditCourseProposalAssembler;
//    private CreditCourseProposalAssembler creditCourseProposalAssembler;
    
    private synchronized void initAssemblers() {
    	if (creditCourseProposalAssembler == null) {
    		CreditCourseProposalAssembler targetAssembler = new CreditCourseProposalAssembler("draft");
    		targetAssembler.setLuService(service);
    		targetAssembler.setProposalService(proposalService);   
    		targetAssembler.setPermissionService(permissionService);
    		targetAssembler.setLearningObjectiveService(learningObjectiveService);
    		
    		CreditCourseProposalWorkflowAssemblerFilter workflowAssemblerFilter = new CreditCourseProposalWorkflowAssemblerFilter();
    		workflowAssemblerFilter.setSimpleDocService(simpleDocService);
    		workflowAssemblerFilter.setWorkflowUtilityService(workflowUtilityService);
    		
    		AssemblerFilterManager<Data, Void> filterManager = new AssemblerFilterManager<Data, Void>(targetAssembler);
    		filterManager.addFilter(workflowAssemblerFilter);
    		
    		creditCourseProposalAssembler = filterManager;
    		
    		// TODO change how the state is set/passed in to the proposal assembler, if at all
//    		creditCourseProposalAssembler = new CreditCourseProposalAssembler("draft");
//    		creditCourseProposalAssembler.setLuService(service);
//    		creditCourseProposalAssembler.setProposalService(proposalService);
    		
    	}
    }
	@Override
	public Data getCreditCourseProposal(String id) throws OperationFailedException {
		try {
			initAssemblers();
			return creditCourseProposalAssembler.get(id);
		} catch (Exception e) {
			logger.error("Unable to retrieve credit course proposal", e);
			e.printStackTrace();
			throw new OperationFailedException("Unable to retrieve credit course proposal");
		}
	}

   @Override
   public Data saveData(Data data) {
       logger.info("Save Data: " + data.toString());
       return data;
   }
	
	@Override
	public DataSaveResult saveCreditCourseProposal(
			Data proposal) throws OperationFailedException {
		try {
			initAssemblers();
			SaveResult<Data> s = creditCourseProposalAssembler.save(proposal);
			
			if (s == null) {
				return null;
			} else {
				return new DataSaveResult(s.getValidationResults(), s.getValue());
			}
		} catch (Exception e) {
			logger.error("Unable to retrieve credit course proposal", e);
			e.printStackTrace();
			throw new OperationFailedException("Unable to save credit course proposal");
		}
	}

	@Override
	public Metadata getCreditCourseProposalMetadata()
			throws OperationFailedException {
		try {
			initAssemblers();
			return creditCourseProposalAssembler.getMetadata(null, "draft");
		} catch (Exception e) {
			logger.error("Unable to retrieve metadata for credit course proposal", e);
			throw new OperationFailedException("Unable to retrieve metadata for credit course proposal");
		}
	}

}
