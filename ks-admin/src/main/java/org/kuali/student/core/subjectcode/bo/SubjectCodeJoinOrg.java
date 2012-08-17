package org.kuali.student.core.subjectcode.bo;

import org.kuali.student.core.bo.KsInactivatableFromToBase;

public class SubjectCodeJoinOrg extends KsInactivatableFromToBase {

	private static final long serialVersionUID = 4071877051631187891L;

	private String orgId;
	private String subjectCodeId;
	private SubjectCode subjectCode;

	public void setSubjectCode(SubjectCode subjectCode) {
		this.subjectCode = subjectCode;
	}

	public SubjectCode getSubjectCode() {
		return subjectCode;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setSubjectCodeId(String subjectCodeId) {
		this.subjectCodeId = subjectCodeId;
	}

	public String getSubjectCodeId() {
		return subjectCodeId;
	}
}
