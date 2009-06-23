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

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.kuali.rice.kew.dto.ActionItemDTO;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
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
    private SimpleWorkflowUtility workflowUtilityService;
    private OrganizationService orgService;
     
    private String simpleDocServiceAddress="http://localhost:8081/kr-dev/remoting/simpleDocumentActionsService";
    private String workflowUtilityServiceAddress="http://localhost:8081/kr-dev/remoting/WorkflowUtilityServiceSOAP";
    
    private Map<String, ProposalInfo> getProposalInfoMap() {
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

	@Override
	public CluProposal createAndRouteProposal(CluProposal cluProposal) {
		aquireSimpleDocService();
        try {
            
        	//Get org info stuff
        	String orgId = cluProposal.getProposalInfo().getProposerOrg().get(0);
        	
        	String departmentName = orgService.getOrganization(orgId).getShortName();
        	String collegeName = "";
        	
        	List<OrgOrgRelationInfo> relations = orgService.getOrgOrgRelationsByRelatedOrg(orgId);
        	if(relations!=null){
        		for(OrgOrgRelationInfo relation:relations){
        			if("kuali.org.Part".equals(relation.getType())){
        				OrgInfo part = orgService.getOrganization(relation.getId());
        				if("kuali.org.College".equals(part.getType())){
        					collegeName = part.getShortName();
        				}
        			}
        			
        		}
        	}
        	
        	//FIXME: Restore code to handle proposal service?
            CluInfo parentCluInfo = cluProposal.getCluInfo();
            parentCluInfo = service.createClu(cluProposal.getCluInfo().getType(), parentCluInfo);
            cluProposal.setCluInfo(parentCluInfo);
            
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
            
            //get a user name
            String username=getCurrentUser();
            
            //Create and then route the document 
            String workflowDocTypeId = "CluDocument";
            DocumentResponse docResponse = simpleDocService.create(username, parentCluInfo.getId(), workflowDocTypeId, parentCluInfo.getOfficialIdentifier().getLongName());
            
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            Element root = doc.createElement("cluProposal");
            doc.appendChild(root);
            
            Element cluId = doc.createElement("cluId");
            root.appendChild(cluId);
            
            Text cluIdText = doc.createTextNode(parentCluInfo.getId());
            cluId.appendChild(cluIdText);
            
            Element department = doc.createElement("department");
            root.appendChild(department);
            
            Text departmentText = doc.createTextNode(departmentName);
            department.appendChild(departmentText);
            
            Element college = doc.createElement("college");
            root.appendChild(college);
            
            Text collegeText = doc.createTextNode(collegeName);
            college.appendChild(collegeText);
           
            
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            String createComment = "Created By CluProposalService";
            
            simpleDocService.route(docResponse.getDocId(), username, parentCluInfo.getOfficialIdentifier().getLongName(), writer.toString(), createComment);
            
            cluProposal.setWorkflowId(docResponse.getDocId());
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cluProposal;
	}


	@Override
	public CluProposal startProposalWorkflow(CluProposal cluProposal) {
		aquireSimpleDocService();
        try {
        	
        	//Get org info stuff
        	String orgId = cluProposal.getProposalInfo().getProposerOrg().get(0);
        	
        	String departmentName = orgService.getOrganization(orgId).getShortName();
        	String collegeName = "";
        	
        	List<OrgOrgRelationInfo> relations = orgService.getOrgOrgRelationsByRelatedOrg(orgId);
        	if(relations!=null){
        		for(OrgOrgRelationInfo relation:relations){
        			if("kuali.org.Part".equals(relation.getType())){
        				OrgInfo part = orgService.getOrganization(relation.getOrgId());
        				if("kuali.org.College".equals(part.getType())){
        					collegeName = part.getShortName();
        				}
        			}
        			
        		}
        	}
        	
            CluInfo cluInfo = cluProposal.getCluInfo();
            
            //get a user name
            String username = getCurrentUser();

            //Create and then route the document 
            String workflowDocTypeId = "CluDocument";
            DocumentResponse docResponse = simpleDocService.create(username, cluInfo.getId(), workflowDocTypeId, cluInfo.getOfficialIdentifier().getLongName());
            
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            Element root = doc.createElement("cluProposal");
            doc.appendChild(root);
            
            Element cluId = doc.createElement("cluId");
            root.appendChild(cluId);
            
            Text cluIdText = doc.createTextNode(cluInfo.getId());
            cluId.appendChild(cluIdText);
            
            Element department = doc.createElement("department");
            root.appendChild(department);
            
            Text departmentText = doc.createTextNode(departmentName);
            department.appendChild(departmentText);
            
            Element college = doc.createElement("college");
            root.appendChild(college);
            
            Text collegeText = doc.createTextNode(collegeName);
            college.appendChild(collegeText);
            
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            String createComment = "Created By CluProposalService";
            
            simpleDocService.route(docResponse.getDocId(), username, cluInfo.getOfficialIdentifier().getLongName(), writer.toString(), createComment);
            
            cluProposal.setWorkflowId(docResponse.getDocId());
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cluProposal;
	}

	@Override
	public CluProposal getCluProposalFromWorkflowId(String docId) {
		aquireSimpleDocService();
		
        //get a user name
        String username = getCurrentUser();
        
        DocumentResponse docResponse = simpleDocService.getDocument(docId, username);
		CluProposal proposal = getProposal(docResponse.getAppDocId());
		proposal.setWorkflowId(docId);
		return proposal;
	}
	
	@Override
	public String getActionsRequested(CluProposal cluProposal) {
		aquireWorkflowUtilityService();
		
        //get a user name
        String username = getCurrentUser();
        
		//Build up a string of actions requested from the attribute set.  The actions can be F,A,C,K. examples are "A" "AF" "FCK"
        System.out.println("Calling action requested with user:"+username+" and docId:"+cluProposal.getWorkflowId());
        AttributeSet results = workflowUtilityService.getActionsRequested(username, Long.parseLong(cluProposal.getWorkflowId()));
        String actionsRequested = "";
        for(Map.Entry<String,String> entry:results.entrySet()){
        	if("true".equals(entry.getValue())){
        		actionsRequested+=entry.getKey();
        	}
        }
		return actionsRequested;
	}
	

	@Override
	public Boolean approveProposal(CluProposal cluProposal) {
		aquireSimpleDocService();
		
		try{
            //get a user name
            String username = getCurrentUser();
            
	        CluInfo cluInfo = cluProposal.getCluInfo();
	        
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	        Document doc = docBuilder.newDocument();
	        Element root = doc.createElement("cluId");
	        doc.appendChild(root);
	        Text text = doc.createTextNode(cluInfo.getId());
	        root.appendChild(text);
	        
	        DOMSource domSource = new DOMSource(doc);
	        StringWriter writer = new StringWriter();
	        StreamResult result = new StreamResult(writer);
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.transform(domSource, result);
	        
	        String approveComment = "Approved by CluProposalService";
	        
	        //String docId, String principalId, String docTitle, String docContent, String annotation
	        simpleDocService.approve(cluProposal.getWorkflowId(), username, cluInfo.getOfficialIdentifier().getLongName(), writer.toString(), approveComment);
	       
		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
	}
	
	@Override
	public Boolean disapproveProposal(CluProposal cluProposal) {
		aquireSimpleDocService();
		
		try{
            //get a user name
            String username = getCurrentUser();
            
	        String disapproveComment = "Disapproved by CluProposalService";
	        
	        //String docId, String principalId, String docTitle, String docContent, String annotation
	        simpleDocService.disapprove(cluProposal.getWorkflowId(), username, disapproveComment);
	       
		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
	}
	

	@Override
	public Boolean acknowledgeProposal(CluProposal cluProposal) {
		aquireSimpleDocService();
		
		try{
			//get a user name
            String username=getCurrentUser();
            
	        String acknowledgeComment = "Acknowledged by CluProposalService";
	        
	        //String docId, String principalId, String docTitle, String docContent, String annotation
	        simpleDocService.acknowledge(cluProposal.getWorkflowId(), username, acknowledgeComment);
	       
		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
	}
	
	@Override
    public Boolean addCollaborator(String docId, String recipientPrincipalId){
		aquireSimpleDocService();
		
		try{
			//get a user name
            String username=getCurrentUser();
            
	        String collaborateComment = "Collaborate by CluProposalService";
	        
	        //String docId, String principalId, String docTitle, String docContent, String annotation
	        simpleDocService.requestAdHocFyiToPrincipal(docId, recipientPrincipalId, username, collaborateComment);
	       
		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
    }

	@Override
    public ArrayList<String> getCollaborators(String docId){
		aquireWorkflowUtilityService();
		
		ArrayList<String> users = new ArrayList<String>();
		
        ActionItemDTO[] items= workflowUtilityService.getActionItems(Long.parseLong(docId));
        if(items!=null){
        	for(ActionItemDTO item:items){
        		if(KEWConstants.ACTION_REQUEST_FYI_REQ.equals(item.getActionRequestCd())){
	        		users.add(item.getPrincipalId());
        		}
        	}
        }
		return users;
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
	
	private void aquireSimpleDocService() {
		if(simpleDocService==null){
			try{
				ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
				factory.setServiceClass(SimpleDocumentActionsWebService.class);
				factory.setAddress(simpleDocServiceAddress);
				factory.setWsdlLocation(simpleDocServiceAddress+"?wsdl");
				factory.setServiceName(new QName("RICE", "simpleDocumentActionsService"));
				factory.getServiceFactory().setDataBinding(new AegisDatabinding());
				simpleDocService = (SimpleDocumentActionsWebService) factory.create();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void aquireWorkflowUtilityService() {
		if(workflowUtilityService==null){
			try{
				ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
				factory.setServiceClass(SimpleWorkflowUtility.class);
				factory.setAddress(workflowUtilityServiceAddress);
				//factory.setWsdlLocation(workflowUtilityServiceAddress+"?wsdl");
				factory.setServiceName(new QName("RICE", "WorkflowUtilityServiceSOAP"));
				AegisDatabinding binding = new AegisDatabinding();
				factory.getServiceFactory().setDataBinding(binding);
				factory.setEndpointName(new QName("RICE", "WorkflowUtilityPort"));
				
				workflowUtilityService = (SimpleWorkflowUtility) factory.create();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	@WebService(targetNamespace="RICE",name="WorkflowUtilityServiceSOAP",serviceName="WorkflowUtilityServiceSOAP",portName="WorkflowUtilityPort")
	private interface SimpleWorkflowUtility{
		public AttributeSet getActionsRequested(String username, long docId);
		public ActionItemDTO[] getActionItems(Long docId);
	}
	

	public void setSimpleDocService(SimpleDocumentActionsWebService simpleDocService) {
		this.simpleDocService = simpleDocService;
	}

	public void setWorkflowUtilityService(SimpleWorkflowUtility workflowUtilityService) {
		this.workflowUtilityService = workflowUtilityService;
	}

	public void setSimpleDocServiceAddress(String simpleDocServiceAddress) {
		this.simpleDocServiceAddress = simpleDocServiceAddress;
	}

	public void setWorkflowUtilityServiceAddress(
			String workflowUtilityServiceAddress) {
		this.workflowUtilityServiceAddress = workflowUtilityServiceAddress;
	}

	public OrganizationService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrganizationService orgService) {
		this.orgService = orgService;
	}

}
