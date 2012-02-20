package org.kuali.student.core.assembly.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockProposal {
	private String id;
	private List<String> orgId;
	private String proposalTitle;
	private String proposalRationale;
	private Map<String, String> attributes;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public List<String> getOrgId() {
		return orgId;
	}
	
	public void setOrgId(List<String> orgId) {
		this.orgId = orgId;
	}
	
	public String getProposalTitle() {
		return proposalTitle;
	}
	
	public void setProposalTitle(String proposalTitle) {
		this.proposalTitle = proposalTitle;
	}
	
	public String getProposalRationale() {
		return proposalRationale;
	}
	
	public void setProposalRationale(String proposalRationale) {
		this.proposalRationale = proposalRationale;
	}

	public Map<String, String> getAttributes() {
		if (attributes == null){
				attributes = new HashMap<String,String>();
		}
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}	
	
}
