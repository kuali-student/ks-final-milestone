package org.kuali.student.cm.course.form;

import org.kuali.student.r2.lum.course.dto.CourseJointInfo;

public class CourseJointInfoDisplay extends CourseJointInfo {

	private static final long serialVersionUID = -3581960069878061510L;
	
	private String courseCode;
	
	private String level;

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
