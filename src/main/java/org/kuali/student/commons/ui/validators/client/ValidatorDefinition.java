package org.kuali.student.commons.ui.validators.client;

import java.io.Serializable;

public class ValidatorDefinition implements Serializable {
	private static final long serialVersionUID = 1L;
	String id;
	String type;
	String script;
	
	public ValidatorDefinition() {
		
	}
	
	public ValidatorDefinition(String id, String type, String script) {
		super();
		this.id = id;
		this.type = type;
		this.script = script;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	
}
