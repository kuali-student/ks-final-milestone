package org.kuali.student.ap.plannerreview.dto;

import org.kuali.student.ap.plannerreview.infc.ConversationAdvisor;

public class ConversationAdvisorInfo extends ConversationCommenterInfo implements ConversationAdvisor {
	
	private static final long serialVersionUID = -8214427743645252861L;
	
	private String uniqueId;
	private String advisorType;
	private String advisorRoleId;
	private String advisorRoleName;
	
	
	public ConversationAdvisorInfo() {
		super();
	}
	
	public ConversationAdvisorInfo(String id, String advisorId, String advisorType,
                                   String advisorRoleId, String advisorRoleName,
                                   String advisorDescription, String advisorName) {
		super(advisorId, advisorName, advisorDescription);
		this.uniqueId = id;
		this.advisorType = advisorType;
		this.advisorRoleId = advisorRoleId;
		this.advisorRoleName = advisorRoleName;

	}


	@Override
	public String getAdvisorType() {
		return advisorType;
	}
	public void setAdvisorType(String advisorType) {
		this.advisorType = advisorType;
	}
	@Override
	public String getAdvisorRoleId() {
		return advisorRoleId;
	}
	public void setAdvisorRoleId(String advisorRoleId) {
		this.advisorRoleId = advisorRoleId;
	}
	@Override
	public String getAdvisorRoleName() {
		return advisorRoleName;
	}
	public void setAdvisorRoleName(String advisorRoleName) {
		this.advisorRoleName = advisorRoleName;
	}

	@Override
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
}
