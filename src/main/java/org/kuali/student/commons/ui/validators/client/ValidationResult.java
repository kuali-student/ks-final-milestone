package org.kuali.student.commons.ui.validators.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationResult implements Serializable {
	private static final long serialVersionUID = 1L;
	public enum ValidationState {OK, WARN, FAILED};
	public static final ValidationResult RESULT_OK = new ValidationResult();
	
	private ValidationState state = ValidationState.OK;
	private List<String> messages = new ArrayList<String>();
	
	public ValidationResult() {
		super();
	}

	
	public ValidationState getState() {
		return state;
	}


	
	public List<String> getMessages() {
		return messages;
	}
	
	public ValidationResult addMessage(ValidationState state, String message) {
		if (state.ordinal() > this.state.ordinal()) {
			this.state = state;
		}
		if (message != null) {
			messages.add(message);
		}
		return this;
	}
	
}
