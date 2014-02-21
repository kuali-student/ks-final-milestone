package org.kuali.student.ap.plannerreview.dto;

/**
 * 
 * @author Chris Maurer <chmaurer@iupui.edu>
 *
 */
public class CourseInfo implements Comparable<CourseInfo> {

	private String courseId;
	private String courseCode;
	private String courseName;
	private String courseDescription;
	private boolean backup; 
	private boolean isChecked;
	
	public CourseInfo() {
		
	}
	
	public CourseInfo(String courseId, String courseCode, String courseName,
			String courseDescription, boolean backup) {
		this.courseId = courseId;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.backup = backup;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	public boolean isBackup() {
		return backup;
	}
	public void setBackup(boolean backup) {
		this.backup = backup;
	}

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean checked) {
        this.isChecked = checked;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((courseId == null) ? 0 : courseId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseInfo other = (CourseInfo) obj;
		if (courseId == null) {
			if (other.courseId != null)
				return false;
		} else if (!courseId.equals(other.courseId))
			return false;
		return true;
	}

	@Override
	public int compareTo(CourseInfo obj) {
		return this.courseCode.compareTo(obj.getCourseCode());
	}

	@Override
	public String toString() {
		return "CourseInfo [courseId=" + courseId + ", courseCode="
				+ courseCode + ", courseName=" + courseName
				+ ", courseDescription=" + courseDescription + ", backup="
				+ backup + "]";
	}
	
}
