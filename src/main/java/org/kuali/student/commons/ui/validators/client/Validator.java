package org.kuali.student.commons.ui.validators.client;

import java.util.Map;

import org.kuali.student.commons.ui.messages.client.Messages;


public interface Validator {
	public void setMessages(Messages messages);
	public ValidationResult validate(Object value);
	public ValidationResult validate(Object value, String valueName);
	public ValidationResult validate(Object value, Map<String, String> attributes);
	public ValidationResult validate(Object value, String valueName, Map<String, String> attributes);
}
