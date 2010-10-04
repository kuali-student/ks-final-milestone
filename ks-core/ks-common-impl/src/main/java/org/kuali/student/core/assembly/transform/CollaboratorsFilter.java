package org.kuali.student.core.assembly.transform;

import java.util.List;
import java.util.Map;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.Data.StringKey;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.core.rice.authorization.CollaboratorHelper;
import org.kuali.student.core.workflow.dto.CollaboratorInfo;
import org.kuali.student.core.workflow.dto.WorkflowPersonInfo;

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
	public void applyOutboundDataFilter(Data data, Metadata metadata,
			Map<String, Object> properties) throws Exception {
		
		CollaboratorInfo collabInfo = (CollaboratorInfo)properties.get(COLLABORATOR_INFO);
		ProposalInfo proposalInfo = (ProposalInfo)properties.get(ProposalWorkflowFilter.PROPOSAL_INFO);

		if (collabInfo == null){
			collabInfo = new CollaboratorInfo();
		}

		//Add new collaborators to workflow
		boolean updateProposal = false;
		if (collabInfo.getCollaborators() != null){		
			for (WorkflowPersonInfo wfPerson:collabInfo.getCollaborators()){
				if ("New".equals(wfPerson.getActionRequestStatus())){ 
					collaboratorHelper.addCollaborator(proposalInfo.getWorkflowId(), proposalInfo.getId(), "title here", 
							wfPerson.getPrincipalId(), wfPerson.getPermission(), wfPerson.getAction(), true, "");
					if (wfPerson.isAuthor()){
						proposalInfo.getProposerPerson().add(wfPerson.getPrincipalId());
						updateProposal = true;
					}
				}
			}
		}
		
		//Update proposal with new authors (if any)
		if (updateProposal){
			proposalInfo = proposalService.updateProposal(proposalInfo.getId(), proposalInfo);
			properties.put(ProposalWorkflowFilter.PROPOSAL_INFO, proposalInfo);
		}
		
		//Retrieve updated collaborator info for this workflow
		List<WorkflowPersonInfo> collaborators = collaboratorHelper.getCollaborators(proposalInfo.getWorkflowId());

		//Add the author notation to retrieved collaborators
		try{
		for (WorkflowPersonInfo wfPerson:collaborators){
			String principal = wfPerson.getPrincipalId();
			boolean isAuthor = (proposalInfo.getProposerPerson().contains(principal));
			wfPerson.setAuthor(isAuthor);
		}
		} catch (Exception e){
			throw new Exception("Failed setting author", e);
		}
		collabInfo.setCollaborators(collaborators);
		
		//Tack on updated collaborators data to data returned to UI client
		Data collabData = mapper.convertFromBean(collabInfo);
		data.set("collaboratorInfo", collabData);

	}

	/**
	 * Adds collaboratorInfo metadata to metadata returned to client
	 */
	@Override
	public void applyMetadataFilter(String dtoName, Metadata metadata,
			Map<String, Object> filterProperties) {
		Metadata proposalMetadata = metadataService.getMetadata(CollaboratorInfo.class.getName());
		
		Map<String, Metadata> properties = metadata.getProperties();
		properties.put("collaboratorInfo", proposalMetadata);				
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
	
	public void setMetadataService(MetadataServiceImpl metadataService) {
		this.metadataService = metadataService;
	}

	public void setCollaboratorHelper(CollaboratorHelper collaboratorHelper) {
		this.collaboratorHelper = collaboratorHelper;
	}

	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

}
