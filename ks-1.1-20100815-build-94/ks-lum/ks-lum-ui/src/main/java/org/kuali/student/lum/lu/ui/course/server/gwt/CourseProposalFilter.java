package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.util.Map;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.transform.AbstractDataFilter;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;

/**
 * This is only a POC filter which just creates a proposal object and reference into the proposal service.
 * 
 * @author Will
 *
 */
public class CourseProposalFilter extends AbstractDataFilter{

	public static final String PROPOSAL_REFERENCE_TYPE		= "kuali.proposal.referenceType.clu";
    public static final String PROPOSAL_TYPE_CREATE_COURSE	= "kuali.proposal.type.course.create";
		
	private ProposalService proposalService;

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
	
	
	/**
	 *  This updates the newly created proposal object from the inbound filter with the id of the
	 *  object being saved
	 */
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
}
