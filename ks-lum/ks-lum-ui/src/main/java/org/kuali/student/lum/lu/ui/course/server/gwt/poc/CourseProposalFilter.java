package org.kuali.student.lum.lu.ui.course.server.gwt.poc;

import java.util.Map;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.transform.AbstractDataFilter;
import org.kuali.student.core.assembly.transform.MetadataFilter;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;

/**
 * This is only a POC filter which just creates a proposal object and reference ino the proposal service.
 * 
 * @author Will
 *
 */
public class CourseProposalFilter extends AbstractDataFilter implements MetadataFilter{

	public static final String PROPOSAL_REFERENCE_TYPE = "kuali.proposal.referenceType.clu";
    public static final String PROPOSAL_TYPE_CREATE_COURSE = "kuali.proposal.type.course.create";
		
	private ProposalService proposalService;
	
	@Override
	public void applyOutboundDataFilter(Data data, Metadata metadata,
			Map<String, String> properties) throws Exception {
		ProposalInfo proposalInfo;
		
		String proposalId = data.query("proposalId");
		String proposalTitle = data.query("courseTitle");
		String proposalRationale = data.query("proposalRationale");
		if (proposalId != null && !proposalId.isEmpty()){
			proposalInfo = proposalService.getProposal(proposalId);
			proposalInfo.setName(proposalTitle);
			proposalService.updateProposal(proposalId, proposalInfo);
		} else {
			proposalInfo = new ProposalInfo();
			proposalInfo.setType(PROPOSAL_TYPE_CREATE_COURSE);
			proposalInfo.setName(proposalTitle);
			proposalInfo.setProposalReferenceType(PROPOSAL_REFERENCE_TYPE);
			proposalInfo.setRationale(proposalRationale);
			proposalInfo.getProposalReference().add((String)data.query("id"));
			proposalInfo = proposalService.createProposal(proposalInfo.getType(), proposalInfo);
			data.set("proposalId", proposalInfo.getId());
		}			
	}
	
	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}
	
	@Override
	public void applyMetadataFilter(String dtoName, Metadata metadata,
			Map<String, String> filterProperties) {
		//This is adding proposal and proposal rationale fields to course object as dynamic attributes, will
		//need to add to dictionary when dynamic attributes are checked.
		Map<String, Metadata> properties = metadata.getProperties();
		
		Metadata propMetadata = new Metadata();
		propMetadata.setDataType(DataType.STRING);
		
		properties.put("proposalId", propMetadata);
		properties.put("proposalRationale", propMetadata);
	}

}
