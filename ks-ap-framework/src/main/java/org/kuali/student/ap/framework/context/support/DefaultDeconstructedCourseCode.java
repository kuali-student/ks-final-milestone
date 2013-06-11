package org.kuali.student.ap.framework.context.support;

import org.kuali.student.ap.framework.context.DeconstructedCourseCode;

public class DefaultDeconstructedCourseCode implements DeconstructedCourseCode {

	private String subject;
	private String number;
	private String section;

	public DefaultDeconstructedCourseCode() {
	}

	public DefaultDeconstructedCourseCode(String subject, String number,
			String section) {
		this.subject = subject;
		this.number = number;
		this.section = section;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String getSubject() {
		return subject;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String getNumber() {
		return number;
	}

	public void setSection(String section) {
		this.section = section;
	}

	@Override
	public String getSection() {
		return section;
	}

}
