package org.kuali.student.poc.client.admin;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;


public class MessageModelObject implements ModelObject {
	private static final long serialVersionUID = 1L;
	private String locale = null;
	private String groupName = null;
	private String messageId = null;
	private String message = null;
	
	public String getUniqueId() {
		return locale + ":" + groupName + ":" + messageId;
	}
	
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
