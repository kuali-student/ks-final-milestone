package org.kuali.student.core.subjectcode.bo;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.bo.KsTypeStateBusinessObjectBase;

public class SubjectCode extends KsTypeStateBusinessObjectBase {

	private static final long serialVersionUID = 8238736199474085646L;

	private String code;
	private SubjectCodeType type;
	private List<SubjectCodeJoinOrg> orgs;

	public SubjectCode() {
		super();
		orgs = new ArrayList<SubjectCodeJoinOrg>();
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setType(SubjectCodeType type) {
		this.type = type;
	}

	public SubjectCodeType getType() {
		return type;
	}

	public void setOrgs(List<SubjectCodeJoinOrg> orgs) {
		this.orgs = orgs;
	}

	public List<SubjectCodeJoinOrg> getOrgs() {
		return orgs;
	}

}
