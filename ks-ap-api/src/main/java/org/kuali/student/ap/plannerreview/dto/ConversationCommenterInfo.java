package org.kuali.student.ap.plannerreview.dto;

import org.kuali.student.ap.plannerreview.infc.ConversationCommenter;

import java.io.Serializable;

public class ConversationCommenterInfo implements ConversationCommenter, Serializable {

	private static final long serialVersionUID = -123367047578945806L;
	
	private String userId;
	private String name;
	private String description;

	public ConversationCommenterInfo() {
		
	}
	
	public ConversationCommenterInfo(String userId, String name,
			String description) {
		super();
		this.userId = userId;
		this.name = name;
		this.description = description;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
