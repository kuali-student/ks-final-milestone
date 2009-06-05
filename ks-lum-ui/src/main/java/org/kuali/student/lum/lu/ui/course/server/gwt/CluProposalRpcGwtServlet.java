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
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.proposal.dto.ProposalInfo;
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
public class CluProposalRpcGwtServlet extends BaseRpcGwtServletAbstract<Object> implements CluProposalRpcService {

    private static final long serialVersionUID = 1L;
    private LuService service;
    private SimpleDocumentActionsWebService simpleDocService; 
    
    private Map<String, ProposalInfo> getProposalInfoMap() {
        Map<String, ProposalInfo> proposalInfoMap = (Map<String, ProposalInfo>) getThreadLocalRequest().getSession(true).getAttribute("proposal");

        return proposalInfoMap;
    }

    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#createProposal(org.kuali.student.lum.lu.ui.course.client.service.CluProposal)
     */
    @Override
    public CluProposal createProposal(CluProposal cluProposal) {
        try {
            ProposalInfo proposalInfo = cluProposal.getProposalInfo();
            proposalInfo.setId(UUIDHelper.genStringUUID());
            proposalInfo.setProposalReferenceType("clu");
            
            ArrayList<String> proposalRefIds = new ArrayList<String>();
            
            CluInfo parentCluInfo = cluProposal.getCluInfo();
            parentCluInfo = service.createClu(cluProposal.getCluInfo().getType(), parentCluInfo);
            proposalRefIds.add(parentCluInfo.getId());
            proposalInfo.setProposalReference(proposalRefIds);

            List<CluInfo> activities = cluProposal.getActivities();
            for (CluInfo cluInfo : activities) {

                cluInfo = service.createClu(cluInfo.getType(), cluInfo);
                CluCluRelationInfo relInfo = new CluCluRelationInfo();
                
                //TODO: Create a proper relation type for activities
                relInfo.setCluId(parentCluInfo.getId());
                relInfo.setRelatedCluId(cluInfo.getId());                    
                relInfo.setType("proposal.actvitiy"); 

                service.createCluCluRelation(parentCluInfo.getId(), cluInfo.getId(), "proposal.actvitiy", relInfo);
            }

            getProposalInfoMap().put(proposalInfo.getId(), proposalInfo);
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
            ProposalInfo proposalInfo = cluProposal.getProposalInfo();
                       
            List<String> proposalReferences = new ArrayList<String>();

            CluInfo parentCluInfo = cluProposal.getCluInfo();
            parentCluInfo = service.updateClu(cluProposal.getCluInfo().getType(), parentCluInfo);

            proposalReferences.add(parentCluInfo.getId());

            List<CluInfo> activities = cluProposal.getActivities();
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

            getProposalInfoMap().put(proposalInfo.getId(), proposalInfo);
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

    public LuService getService() {
        return service;
    }

    public void setService(LuService service) {
        this.service = service;
    }

	@Override
	public CluProposal createAndRouteProposal(CluProposal cluProposal) {
        try {
            ProposalInfo proposalInfo = cluProposal.getProposalInfo();
            proposalInfo.setId(UUIDHelper.genStringUUID());
            proposalInfo.setProposalReferenceType("clu");
            
            ArrayList<String> proposalRefIds = new ArrayList<String>();
            
            CluInfo parentCluInfo = cluProposal.getCluInfo();
            parentCluInfo = service.createClu(cluProposal.getCluInfo().getType(), parentCluInfo);
            proposalRefIds.add(parentCluInfo.getId());
            proposalInfo.setProposalReference(proposalRefIds);

            List<CluInfo> activities = cluProposal.getActivities();
            for (CluInfo cluInfo : activities) {

                cluInfo = service.createClu(cluInfo.getType(), cluInfo);
                CluCluRelationInfo relInfo = new CluCluRelationInfo();
                
                //TODO: Create a proper relation type for activities
                relInfo.setCluId(parentCluInfo.getId());
                relInfo.setRelatedCluId(cluInfo.getId());                    
                relInfo.setType("proposal.actvitiy"); 

                service.createCluCluRelation(parentCluInfo.getId(), cluInfo.getId(), "proposal.actvitiy", relInfo);
            }

            getProposalInfoMap().put(proposalInfo.getId(), proposalInfo);
            
            //get a user name
            Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;
            if (obj instanceof UserDetails) {
            	username = ((UserDetails)obj).getUsername();
            } else {
            	username = obj.toString();
            }
            //Create and then route the document 
            String workflowDocTypeId = "CluDocument";
            DocumentResponse docResponse = simpleDocService.create(username, parentCluInfo.getId(), workflowDocTypeId, parentCluInfo.getOfficialIdentifier().getShortName());
            
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
            
            simpleDocService.route(docResponse.getDocId(), username, parentCluInfo.getOfficialIdentifier().getShortName(), writer.toString(), createComment);
            
            cluProposal.setWorkflowId(docResponse.getDocId());
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cluProposal;
	}

	@Override
	public String getCluIdFromWorkflowId(String docId) {
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

}
