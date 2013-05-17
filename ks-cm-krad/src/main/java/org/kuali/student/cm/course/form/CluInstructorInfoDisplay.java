package org.kuali.student.cm.course.form;

import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;

public class CluInstructorInfoDisplay {
	
	private CluInstructorInfo cluInstructorInfo;
	
	private String displayName;

	public CluInstructorInfo getCluInstructorInfo() {
		return cluInstructorInfo;
	}

	public void setCluInstructorInfo(CluInstructorInfo cluInstructorInfo) {
		this.cluInstructorInfo = cluInstructorInfo;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
