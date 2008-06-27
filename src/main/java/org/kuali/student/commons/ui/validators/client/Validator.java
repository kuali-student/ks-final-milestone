package org.kuali.student.commons.ui.validators.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.commons.ui.messages.client.Messages;


public abstract class Validator implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> attributes = new HashMap<String, String>();
	private Messages messages = null;
	
	
	public Messages getMessages() {
		return messages;
	}
	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public void setAttribute(String key, String value) {
		attributes.put(key, value);
	}
	public String getAttribute(String key) {
		return attributes.get(key);
	}
	
	/**
	 * To be overridden by implementing classes
	 * @param data
	 * @return
	 */
	public abstract ValidationResult validate(String value, String label);
	
	
	// convenience methods for subclasses
	protected int getAttributeAsInt(String key, int defaultValue) {
		int result = defaultValue;
		try {
			result = Integer.parseInt(attributes.get(key));
		} catch (Exception e) {
			// eat it
		}
		return result;
	}
	protected boolean getAttributeAsBoolean(String key, boolean defaultValue) {
		boolean result = defaultValue;
		String s = attributes.get(key);
		if (s!=null && s.trim().length() > 0) {
			result = s.trim().equalsIgnoreCase("true");
		}
		return result;
	}
	protected double getAttributeAsDouble(String key, double defaultValue) {
		double result = defaultValue;
		try {
			result = Double.parseDouble(attributes.get(key));
		} catch (Exception e) {
			// eat it
		}
		return result;
	}
}
