package org.kuali.student.rules.internal.common.runtime.ast;

import java.util.HashMap;
import java.util.Map;

public class MessageContainer {
	private Map<String, Message> messageMap = new HashMap<String, Message>();
	
	public void addMessage(Message message) {
		this.messageMap.put(message.getMessageId(), message);
	}
	
	public Message getMessage(String id) {
		return this.messageMap.get(id);
	}
	
	public Map<String, Message> getMessageMap() {
		return this.messageMap;
	}
}
