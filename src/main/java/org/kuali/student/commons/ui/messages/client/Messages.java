package org.kuali.student.commons.ui.messages.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Messages implements Serializable {
	private static final long serialVersionUID = 1L;
	private String groupName = null;
	private Map<String, String> messages = new HashMap<String, String>();
	
	public Messages() {
		super();
	}
	public Messages(String groupName, Map<String, String> messages) {
		super();
		this.groupName = groupName;
		this.messages = messages;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public Set<String> keySet() {
		return messages.keySet();
	}
	
	public String get(String key) {
		return messages.get(key);
	}
	
	public String get(String key, String... data) {
		String result = messages.get(key);
		return interpolate(result, data);
	}
	
	public String get(String key, Map<String, String> data) {
		System.out.println("calling messages.get(String, Map) for key: " + key);
		System.out.println("messages content: " + messages.toString());
		System.out.println("data content: " + data.toString());
		String result = messages.get(key);
		result = interpolate(result, data);
		System.out.println("returning: " + result);
		return result;
	}
	
	public String get(String key, Map<String, String> data1, String... data2) {
		String result = messages.get(key);
		return interpolate(interpolate(result, data2), data1);
	}
	
	public Map<String, String> getMessageMap() {
		return messages;
	}
	
	public String interpolate(String message, String... data) {
		if (message != null) {
			for (int i=0; i<data.length; i++) {
				message = message.replaceAll("\\$\\{" + i + "\\}", "" + data[i]);
			}
		}
		return message;
	}
	public String interpolate(String message, Map<String, String> data) {
		if (message != null) {
			Set<String> fields = findFields(message);
			for (String s : fields) {
				message = message.replaceAll("\\$\\{" + s + "\\}", "" + data.get(s));
			}
		}
		return message;
	}
	
	// hack method due to limited regex matching support
	protected Set<String> findFields(String input) {
		Set<String> result = new HashSet<String>();
		int begin = input.indexOf("${");
		while (begin != -1) {
			int end = input.indexOf("}", begin);
			result.add(input.substring(begin+2, end));
			begin = input.indexOf("${", end);
		}
		return result;
	}
	
	
	
}
