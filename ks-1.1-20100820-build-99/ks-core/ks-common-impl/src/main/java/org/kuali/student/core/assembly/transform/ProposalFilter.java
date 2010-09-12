package org.kuali.student.core.assembly.transform;

import java.util.Map;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.Data.StringKey;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;

/**
 * This filter is used to add and process proposal info to data object.
 * 
 * @author Will
 *
 */
public class ProposalFilter extends AbstractDataFilter implements MetadataFilter{

	public static final String PROPOSAL_NAME				= "ProposalName";
	
	public static final String PROPOSAL_REFERENCE_TYPE		= "kuali.proposal.referenceType.clu";
    public static final String PROPOSAL_TYPE_CREATE_COURSE	= "kuali.proposal.type.course.create";

	private ProposalService proposalService;
	private MetadataServiceImpl metadataService;
	private DataBeanMapper mapper = new DefaultDataBeanMapper();
		
	private Metadata proposalMetadata = null;
	private String proposalReferenceType;
	private String proposalType;

	/**
	 *	This creates a proposal object if it does not exist and sets a dynamic attribute
	 *  for proposal id. If one does exist it updates the proposal object with new values.
	 */
	@Override
	public void applyInboundDataFilter(Data data, Metadata metadata,
			Map<String, String> properties) throws Exception {
		
		//Get the proposal data portion from the data
		Data proposalData = data.query("proposal");
		data.remove(new StringKey("proposal"));
		ProposalInfo proposalInfo = (ProposalInfo)mapper.convertFromData(proposalData, ProposalInfo.class, getProposalMetadata());
				
		//Create new proposal if no proposal data received
		if (proposalInfo == null) {
			proposalInfo = new ProposalInfo();
		}
		
		//Create/Update new proposal
		if (proposalInfo.getId() == null){
			proposalInfo.setType(getProposalType());
			proposalInfo = proposalService.createProposal(proposalInfo.getType(), proposalInfo);			
		} else {
			proposalInfo = proposalService.updateProposal(proposalInfo.getId(), proposalInfo);
		}
		
		//Set filter property so outbound filter can retreive proposal
		properties.put(METADATA_ID_VALUE, proposalInfo.getId());
		properties.put(PROPOSAL_NAME, proposalInfo.getName());
	}
	
	
	/**
	 *  This updates the newly created proposal object from the inbound filter with the id of the
	 *  object being saved (if not already set)
	 */
	@Override
	public void applyOutboundDataFilter(Data data, Metadata metadata,
			Map<String, String> properties) throws Exception {
		
		//Get proposal associated with this data
		String proposalId = properties.get(METADATA_ID_VALUE);
		ProposalInfo proposalInfo = proposalService.getProposal(proposalId);
		
		//If no proposal reference id set, set to id of reference object
		if (proposalInfo.getProposalReference().isEmpty()){
			String referenceId = data.query("id");
			proposalInfo.setProposalReferenceType(getProposalReferenceType());
			proposalInfo.getProposalReference().add(referenceId);
			proposalInfo = proposalService.updateProposal(proposalId, proposalInfo);
		}
		
		//Tack on proposal data to data returned to UI client
		Data proposalData = mapper.convertFromBean(proposalInfo);
		data.set("proposal", proposalData);
	}

	/**
	 * Adds proposalInfo metadata to metadata sent to client
	 */
	@Override
	public void applyMetadataFilter(String dtoName, Metadata metadata,
			Map<String, String> filterProperties) {	
		Metadata proposalMetadata = metadataService.getMetadata(ProposalInfo.class.getName());
				
		Map<String, Metadata> properties = metadata.getProperties();
		properties.put("proposal", proposalMetadata);		
	}
	

	/**
	 * This method returns the default metadata (w/o) regard to state. The intent is
	 * to cut down on repeated metadata service calls, by having a cached version for 
	 * passing into the DataBeanMapper which only uses to  map dynamic attributes and 
	 * state does not matter.
	 * 
	 * @return
	 */
	private Metadata getProposalMetadata(){
		if (proposalMetadata == null){
			proposalMetadata = metadataService.getMetadata(ProposalInfo.class.getName());
		}
		
		return proposalMetadata;
	}
	
	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

	public void setMetadataService(MetadataServiceImpl metadataService) {
		this.metadataService = metadataService;
	}


	public String getProposalReferenceType() {
		return proposalReferenceType;
	}


	public void setProposalReferenceType(String proposalReferenceType) {
		this.proposalReferenceType = proposalReferenceType;
	}


	public String getProposalType() {
		return proposalType;
	}


	public void setProposalType(String proposalType) {
		this.proposalType = proposalType;
	}
	
}
