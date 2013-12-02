package org.kuali.student.ap.plannerreview.dto;

import java.util.List;

/**
 * 
 * @author Chris Maurer <chmaurer@iupui.edu>
 *
 */
public class ConversationYearInfo {

	private String headerOpenText;
	private String headerClosedText;
	private List<ConversationInfo> conversations;
	
	public String getHeaderOpenText() {
		return headerOpenText;
	}
	public void setHeaderOpenText(String headerOpenText) {
		this.headerOpenText = headerOpenText;
	}
	public String getHeaderClosedText() {
		return headerClosedText;
	}
	public void setHeaderClosedText(String headerClosedText) {
		this.headerClosedText = headerClosedText;
	}
	public List<ConversationInfo> getConversations() {
		return conversations;
	}
	public void setConversations(List<ConversationInfo> conversations) {
		this.conversations = conversations;
	}
}
