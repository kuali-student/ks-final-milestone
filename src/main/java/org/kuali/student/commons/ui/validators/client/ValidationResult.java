package org.kuali.student.commons.ui.validators.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationResult implements Serializable {
	private static final long serialVersionUID = 1L;
	public enum ErrorLevel {
		OK(0),
		WARN(1),
		ERROR(2);
		
		int level;
		private ErrorLevel(int level) {
			this.level = level;
		}
		public int getLevel() {
			return level;
		}
	}
	
	ErrorLevel errorLevel = ErrorLevel.OK;
	List<String> messages = new ArrayList<String>();
	
	public void addMessage(ErrorLevel level, String message) {
		if (level.getLevel() > errorLevel.getLevel()) {
			errorLevel = level;
		}
		messages.add(message);
	}
	
	public List<String> getMessages() {
		return messages;
	}
	public ErrorLevel getErrorLevel() {
		return errorLevel;
	}
	

	public void addWarning(String message) {
		addMessage(ErrorLevel.WARN, message);
	}
	public void addError(String message) {
		addMessage(ErrorLevel.ERROR, message);
	}
	
	public boolean isOk() {
		return getErrorLevel() == ErrorLevel.OK;
	}
	public boolean isWarn() {
		return getErrorLevel() == ErrorLevel.WARN;
	}
	public boolean isError() {
		return getErrorLevel() == ErrorLevel.ERROR;
	}
	
	
}
