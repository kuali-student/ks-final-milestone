package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.r2.common.infc.OperationStatus;

public class OperationStatusInfo implements OperationStatus, Serializable {
    
    private static final long serialVersionUID = 1L;

	private String status;
	private List<String> messages;

    private List<String> warnings;

    private List<String> errors;
    

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

    
    
}
