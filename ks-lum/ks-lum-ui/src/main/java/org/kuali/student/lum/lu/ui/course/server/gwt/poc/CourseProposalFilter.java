package org.kuali.student.lum.lu.ui.course.server.gwt.poc;

import java.util.Map;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.transform.AbstractDataFilter;
import org.kuali.student.core.assembly.transform.MetadataFilter;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.lum.course.service.CourseService;

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
	private CourseService courseService;

	/**
	 *	This creates a proposal object if it does not exist and sets a dynamic attribute
	 *  for proposal id. If one does it exist it updates the proposal object with new values.
	 */
	@Override
	public void applyInboundDataFilter(Data data, Metadata metadata,
			Map<String, String> properties) throws Exception {
		ProposalInfo proposalInfo;
		
		String proposalId = data.query("proposalId");
		
		if (proposalId == null) {
			proposalInfo = new ProposalInfo();
			proposalInfo.setType(PROPOSAL_TYPE_CREATE_COURSE);
			proposalInfo = proposalService.createProposal(proposalInfo.getType(), proposalInfo);
			data.set("proposalId", proposalInfo.getId());
		}			
	}
	
	
	@Override
	public void applyOutboundDataFilter(Data data, Metadata metadata,
			Map<String, String> properties) throws Exception {
		String courseId = data.query("id");
		String proposalId = data.query("proposalId");
		String proposalTitle = data.query("courseTitle");		
		String proposalRationale = data.query("proposalRationale");
		
		if (proposalId != null && !proposalId.isEmpty()){
			ProposalInfo proposalInfo = proposalService.getProposal(proposalId);
			proposalInfo.setName(proposalTitle);
			proposalInfo.setRationale(proposalRationale);

			if (proposalInfo.getProposalReference().isEmpty()){
				proposalInfo.setProposalReferenceType(PROPOSAL_REFERENCE_TYPE);
				proposalInfo.getProposalReference().add(courseId);
			}
			
			proposalService.updateProposal(proposalId, proposalInfo);
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
