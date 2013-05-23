package org.kuali.student.cm.course.form;

public class SubjectCodeDisplay {
	
	private String id;
	
	private String code;
	
	public SubjectCodeDisplay() {
	}
	
	public SubjectCodeDisplay(String id, String code) {
		this.id = id;
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
