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

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.proposal.dto.ProposalInfo;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
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
    private SimpleDocumentActionsWebService simpleDocService; 
    
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
            } catch (Exception e) {
                // TODO: handle exception
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
            //FIXME: Restore code to handle proposal service?
            /*
            ProposalInfo proposalInfo = cluProposal.getProposalInfo();
            proposalInfo.setId(UUIDHelper.genStringUUID());
            proposalInfo.setProposalReferenceType("clu");
            */
            
            
            CluInfo parentCluInfo = cluProposal.getCluInfo();
            parentCluInfo = service.createClu(cluProposal.getCluInfo().getType(), parentCluInfo);

            /*
            ArrayList<String> proposalRefIds = new ArrayList<String>();
            proposalRefIds.add(parentCluInfo.getId());
            proposalInfo.setProposalReference(proposalRefIds);
            getProposalInfoMap().put(proposalInfo.getId(), proposalInfo);
            */
            
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
            String username="admin";//FIXME this is bad, need to find some kind of mock security context
            Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
            if(auth!=null){
            	Object obj = auth.getPrincipal();
            	if (obj instanceof UserDetails) {
	            	username = ((UserDetails)obj).getUsername();
	            } else {
	            	username = obj.toString();
	            }
            }
            //Create and then route the document 
            String workflowDocTypeId = "CluDocument";
            DocumentResponse docResponse = simpleDocService.create(username, parentCluInfo.getId(), workflowDocTypeId, parentCluInfo.getOfficialIdentifier().getLongName());
            
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("cluId");
            doc.appendChild(root);
            Text text = doc.createTextNode(parentCluInfo.getId());
            root.appendChild(text);
            
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
	public String getCluIdFromWorkflowId(String docId) {
		aquireSimpleDocService();
		
        //get a user name
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (obj instanceof UserDetails) {
        	username = ((UserDetails)obj).getUsername();
        } else {
        	username = obj.toString();
        }
        
        DocumentResponse docResponse = simpleDocService.getDocument(docId, username);
		return docResponse.getAppDocId();
	}

	private void aquireSimpleDocService() {
		// TODO Auto-generated method stub
		if(simpleDocService==null){
			try{
				ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
				factory.setServiceClass(SimpleDocumentActionsWebService.class);
				factory.setAddress("http://localhost:8081/kr-dev/remoting/simpleDocumentActionsService");
				factory.setWsdlLocation("http://localhost:8081/kr-dev/remoting/simpleDocumentActionsService?wsdl");
				factory.setServiceName(new QName("RICE", "simpleDocumentActionsService"));
				factory.getServiceFactory().setDataBinding(new AegisDatabinding());
				simpleDocService = (SimpleDocumentActionsWebService) factory.create();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void setSimpleDocService(SimpleDocumentActionsWebService simpleDocService) {
		this.simpleDocService = simpleDocService;
	}

}
