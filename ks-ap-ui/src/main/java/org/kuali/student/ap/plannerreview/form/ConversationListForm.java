package org.kuali.student.ap.plannerreview.form;

import org.kuali.student.ap.plannerreview.dto.ConversationYearInfo;

import java.util.List;


public class ConversationListForm extends ConversationFormBase {

	private static final long serialVersionUID = 7449349322991178582L;
	
	private List<ConversationYearInfo> conversations;
	private String messageSummaryText;
	private boolean hasNoAdvisors = true;

	public List<ConversationYearInfo> getConversations() {
		return conversations;
	}

	public void setConversations(List<ConversationYearInfo> conversations) {
		this.conversations = conversations;
	}

	public String getMessageSummaryText() {
		return messageSummaryText;
	}

	public void setMessageSummaryText(String messageSummaryText) {
		this.messageSummaryText = messageSummaryText;
	}

	public boolean getHasNoAdvisors() {
		return hasNoAdvisors;
	}

	public void setHasNoAdvisors(boolean hasNoAdvisors) {
		this.hasNoAdvisors = hasNoAdvisors;
	}
	
}
