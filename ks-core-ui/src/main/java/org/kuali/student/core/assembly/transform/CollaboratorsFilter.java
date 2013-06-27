package org.kuali.student.core.assembly.transform;

import java.util.List;
import java.util.Map;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.Data.StringKey;
import org.kuali.student.r1.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.r1.common.assembly.transform.AbstractDataFilter;
import org.kuali.student.r1.common.assembly.transform.DataBeanMapper;
import org.kuali.student.r1.common.assembly.transform.DefaultDataBeanMapper;
import org.kuali.student.r1.common.assembly.transform.MetadataFilter;
import org.kuali.student.core.rice.authorization.CollaboratorHelper;
import org.kuali.student.r1.core.workflow.dto.CollaboratorInfo;
import org.kuali.student.r1.core.workflow.dto.WorkflowPersonInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.proposal.service.ProposalService;

/**
 * Filter can be used to add authors and collaborators to a workflow process. The filter must be
 * configured to run after the ProposalWorkflowFilter; proposal must have been created before
 * collaborators can be added.
 *  
 * @author Will
 * */
public class CollaboratorsFilter extends AbstractDataFilter implements MetadataFilter{
	public static final String COLLABORATOR_INFO 		= "CollaboratorFilter.CollaboratorInfo";
	private CollaboratorHelper collaboratorHelper;
	private ProposalService proposalService;
	
	private MetadataServiceImpl metadataService;
	private DataBeanMapper mapper = DefaultDataBeanMapper.INSTANCE;	
	
	private Metadata collaboratorMetadata = null;
	private Metadata proposalMetadata = null;
	
	private String proposalObjectType;
	
	/**
	 *  This removes the collaborator info from the data and saves it for use in outbound filter.	
	 */
	@Override
	public void applyInboundDataFilter(Data data, Metadata metadata,
			Map<String, Object> properties) throws Exception {
		
		Data collaboratorData = data.query("collaboratorInfo");
		data.remove(new StringKey("collaboratorInfo"));
				
		if (collaboratorData != null){
			CollaboratorInfo collabInfo = (CollaboratorInfo)mapper.convertFromData(collaboratorData, CollaboratorInfo.class, getCollaboratorMetadata());		
			properties.put(COLLABORATOR_INFO, collabInfo);
		}	
	}
	
	
	/**
	 *  This adds newly added collaborators for workflow and updates collab data sent to client
	 */
    @Override
    public void applyOutboundDataFilter(Data data, Metadata metadata, Map<String, Object> properties) throws Exception {

        CollaboratorInfo collabInfo = (CollaboratorInfo) properties.get(COLLABORATOR_INFO);
        ProposalInfo proposalInfo = (ProposalInfo) properties.get(ProposalWorkflowFilter.PROPOSAL_INFO);

        if (collabInfo == null) {
            collabInfo = new CollaboratorInfo();
        }

        // Add new collaborators to workflow
        boolean updateProposal = false;
        if (collabInfo.getCollaborators() != null) {
            for (WorkflowPersonInfo wfPerson : collabInfo.getCollaborators()) {
                if ("New".equals(wfPerson.getActionRequestStatus())) {
                    collaboratorHelper.addCollaborator(proposalInfo.getWorkflowId(), proposalInfo.getId(), "title here", wfPerson.getPrincipalId(), wfPerson.getPermission(), wfPerson.getAction(),
                            true, "");
                    if (wfPerson.isAuthor()) {
                        proposalInfo.getProposerPerson().add(wfPerson.getPrincipalId());
                        updateProposal = true;
                    }
                } else if ("Remove".equals(wfPerson.getActionRequestStatus())) {
                    collaboratorHelper.removeCollaborator(proposalInfo.getWorkflowId(), proposalInfo.getId(), wfPerson.getActionRequestId());
                    if (wfPerson.isAuthor()) {
                        proposalInfo.getProposerPerson().remove(wfPerson.getPrincipalId());
                        updateProposal = true;
                    }
                }
            }
        }

        // Update proposal with new authors (if any)
        if (updateProposal) {
            proposalInfo = proposalService.updateProposal(proposalInfo.getId(), proposalInfo, ContextUtils.getContextInfo());
            properties.put(ProposalWorkflowFilter.PROPOSAL_INFO, proposalInfo);
            
    		//Note: A proposalInfo conversion for data sent to UI happens in PropoposalWorkflowFilter as well. It
            //would be nice if there was a way to do this conversion once per filter chain in the ProposalWorkflowFilter. 
            //Unfortunately the ProposalWorkflowFilter gets processed before this filter and the updateProposal call
            //above doesn't get reflected in UI data. It is necessary to do another conversion here, otherwise we end
            //up with out of sync proposal data, ultimately resulting in version mismatch errors.
            Data proposalData = mapper.convertFromBean(proposalInfo, getProposalMetadata());
    		data.remove(new StringKey("proposal"));
            data.set("proposal", proposalData);		
        }

        // Retrieve updated collaborator info for this workflow
        List<WorkflowPersonInfo> collaborators = collaboratorHelper.getCollaborators(proposalInfo.getWorkflowId(), proposalInfo.getId(), proposalInfo.getType());

        // Add the author notation to retrieved collaborators
        for (WorkflowPersonInfo wfPerson : collaborators) {
            String principal = wfPerson.getPrincipalId();
            boolean isAuthor = (proposalInfo.getProposerPerson().contains(principal));
            wfPerson.setAuthor(isAuthor);
        }

        collabInfo.setCollaborators(collaborators);

        // Tack on updated collaborators data to data returned to UI client
        Data collabData = mapper.convertFromBean(collabInfo, getCollaboratorMetadata());
        data.set("collaboratorInfo", collabData);

    }

	/**
	 * Adds collaboratorInfo metadata to metadata returned to client
	 */
	@Override
	public void applyMetadataFilter(String dtoName, Metadata metadata,
			Map<String, Object> filterProperties) {
		Metadata collabMetadata = metadataService.getMetadata(CollaboratorInfo.class.getName());
		
		Map<String, Metadata> properties = metadata.getProperties();
		properties.put("collaboratorInfo", collabMetadata);				
	}

	/**
	 * This method returns the default metadata (w/o) regard to state. The intent is
	 * to cut down on repeated metadata service calls by having a cached version. This 
	 * metadata is passed into the DataBeanMapper where state does not matter. 
	 * 
	 * @return
	 */
	private Metadata getCollaboratorMetadata(){
		if (collaboratorMetadata == null){
			collaboratorMetadata = metadataService.getMetadata(CollaboratorInfo.class.getName());
		}
		
		return collaboratorMetadata;
	}
	
	/**
	 * This method returns the default metadata (w/o) regard to state. The intent is
	 * to cut down on repeated metadata service calls by having a cached version. This 
	 * metadata is passed into the DataBeanMapper where state does not matter. 
	 * 
	 * @return
	 */
	private Metadata getProposalMetadata(){
		if (proposalMetadata == null){
			proposalMetadata = metadataService.getMetadata(getProposalObjectType());
		}
		
		return proposalMetadata;
	}
		
	public void setMetadataService(MetadataServiceImpl metadataService) {
		this.metadataService = metadataService;
	}

	public void setCollaboratorHelper(CollaboratorHelper collaboratorHelper) {
		this.collaboratorHelper = collaboratorHelper;
	}

	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}


	public String getProposalObjectType() {
		return proposalObjectType;
	}


	public void setProposalObjectType(String proposalObjectType) {
		this.proposalObjectType = proposalObjectType;
	}

	
}
