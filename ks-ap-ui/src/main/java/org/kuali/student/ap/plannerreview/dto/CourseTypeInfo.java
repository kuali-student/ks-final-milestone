package org.kuali.student.ap.plannerreview.dto;

import java.util.ArrayList;
import java.util.List;

public class CourseTypeInfo {
	private String type;
	private List<CourseInfo> courses;
	
	public CourseTypeInfo(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<CourseInfo> getCourses() {
		return courses;
	}
	public void setCourses(List<CourseInfo> courses) {
		this.courses = courses;
	}
	
	public void addCourse(CourseInfo course) {
		if (courses == null) {
			courses = new ArrayList<CourseInfo>();
		}
		courses.add(course);
	}

	@Override
	public String toString() {
		return "CourseTypeInfo [type=" + type + ", courses=" + courses + "]";
	}
	
}
