package org.kuali.student.ui.personidentity.client.model;

import java.io.Serializable;

public class GwtCourseInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6458994207820252453L;
	private String courseTitle;
	private String courseNumber;
	private String courseId;
	private String courseDescription;
	private String courseInstructor;
	
	public String getCourseInstructor() {
		return courseInstructor;
	}
	public void setCourseInstructor(String courseInstructor) {
		this.courseInstructor = courseInstructor;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public String getCourseNumber() {
		return courseNumber;
	}
	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

}
