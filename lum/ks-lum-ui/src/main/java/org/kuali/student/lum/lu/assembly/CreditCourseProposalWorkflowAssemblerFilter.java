package org.kuali.student.lum.lu.assembly;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.assembly.PassThroughAssemblerFilter;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;
import org.kuali.student.lum.lu.dto.workflow.CluProposalDocInfo;

public class CreditCourseProposalWorkflowAssemblerFilter extends PassThroughAssemblerFilter<Data, Void> {
	
    private final Logger LOG = Logger.getLogger(CreditCourseProposalWorkflowAssemblerFilter.class);
	
    //Rice KEW services
    private SimpleDocumentActionsWebService simpleDocService;
	private WorkflowUtility workflowUtilityService;
	
	private static final String WF_TYPE_CLU_DOCUMENT = "CluCreditCourseProposal";
	
	@Override
	public void doSaveFilter(
			FilterParamWrapper<Data> request,
			FilterParamWrapper<SaveResult<Data>> response,
			SaveFilterChain<Data, Void> chain) throws AssemblyException {
		//Process down the chain first, then do the workflow document create/save 
		chain.doSaveFilter(request, response);
		
		SaveResult<Data> saveResult = response.getValue();
		if(saveResult!=null&&(saveResult.getValidationResults()==null||saveResult.getValidationResults().size()==0)){
			
            String username=SecurityUtils.getCurrentUserId();
            
            CreditCourseProposalHelper requestProposal = CreditCourseProposalHelper.wrap(request.getValue());
            CreditCourseProposalHelper responseProposal = CreditCourseProposalHelper.wrap(saveResult.getValue());
            
            String title = responseProposal.getProposal().getTitle()==null?"Unnamed":responseProposal.getProposal().getTitle();
            title = title==null?"Unnamed":title;
            
            //Create a new workflow document if this is a new proposal 
            if(requestProposal.getProposal().getId()==null){
                LOG.info("Creating proposal Workflow Document.");
                DocumentResponse docResponse = simpleDocService.create(username, responseProposal.getProposal().getId(), WF_TYPE_CLU_DOCUMENT, title);
                if (StringUtils.isNotBlank(docResponse.getErrorMessage())) {
                	throw new RuntimeException("Error found creating document: " + docResponse.getErrorMessage());
                }
            }

            //Lookup the workflow document detail from the proposalId
            DocumentDetailDTO docDetail;
			try {
				docDetail = workflowUtilityService.getDocumentDetailFromAppId(WF_TYPE_CLU_DOCUMENT, responseProposal.getProposal().getId());
			} catch (Exception e) {
				docDetail = null;
			}
            
            //Check that the call was successful
            if(docDetail==null){
            	throw new RuntimeException("Error found gettting documen for proposal id: "+responseProposal.getProposal().getId());
            }            
            
            //Generate the document content xml
            String docContent = getDocContent(responseProposal);
            
            //Save
            StandardResponse stdResp;
            if ( (KEWConstants.ROUTE_HEADER_INITIATED_CD.equals(docDetail.getDocRouteStatus())) ||
            	 (KEWConstants.ROUTE_HEADER_SAVED_CD.equals(docDetail.getDocRouteStatus())) ) {
            	//if the route status is initial, then save initial
            	stdResp = simpleDocService.save(docDetail.getRouteHeaderId().toString(), username, title, docContent, "");
            } else {
            	//Otherwise just update the doc content
            	stdResp = simpleDocService.saveDocumentContent(docDetail.getRouteHeaderId().toString(), username, title, docContent);
            }

            //Check if there were errors saving
            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new RuntimeException("Error found updating document: " + stdResp.getErrorMessage());
        	}            
		}
	}
	
	private String getDocContent(CreditCourseProposalHelper creditCourseProposal) {
    	try{
    		
    		CluProposalDocInfo docContent = new CluProposalDocInfo();
    		
    		if(null == creditCourseProposal.getCourse()){
    			throw new RuntimeException("CluInfo must be set.");
    		}
    		
    		String cluId = creditCourseProposal.getCourse().getId()==null?"":creditCourseProposal.getCourse().getId(); 
    		String adminOrg = creditCourseProposal.getCourse().getDepartment()==null?"":creditCourseProposal.getCourse().getDepartment(); 
    		String proposalId = creditCourseProposal.getProposal().getId()==null?"":creditCourseProposal.getProposal().getId();
    		
    		docContent.setCluId(cluId);
            docContent.setOrgId(adminOrg);
            docContent.setProposalId(proposalId);
            
    		JAXBContext context = JAXBContext.newInstance(docContent.getClass());
    		Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
    		marshaller.marshal(docContent, writer);
    		return writer.toString();

    	} catch(Exception e) {
    		throw new RuntimeException("Error creating Document content for Clu Proposal. ", e);
    	}
	}

	public void setSimpleDocService(SimpleDocumentActionsWebService simpleDocService) {
		this.simpleDocService = simpleDocService;
	}

	public void setWorkflowUtilityService(WorkflowUtility workflowUtilityService) {
		this.workflowUtilityService = workflowUtilityService;
	}
}
