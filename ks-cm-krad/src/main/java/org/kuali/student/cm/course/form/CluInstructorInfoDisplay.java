package org.kuali.student.cm.course.form;

import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;

public class CluInstructorInfoDisplay extends CluInstructorInfo {
	
	private static final long serialVersionUID = 7495209564517379554L;

	private String displayName;
	
	private String givenName;
	
	private String principalName;
	
	public CluInstructorInfoDisplay() {
		
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	
	public String getPrincipalName() {
		return principalName;
	}
	
}
