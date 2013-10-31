package org.kuali.student.ap.plannerreview.dto;

import org.kuali.student.ap.plannerreview.util.ConversationConstants;

import java.util.ArrayList;
import java.util.List;

public class PlanTermInfo implements Comparable<PlanTermInfo> {
	
	private String termId;
	private String termName;
	private int year;
	private boolean selected = false;
	private List<CourseTypeInfo> courseTypes = new ArrayList<CourseTypeInfo>();
	
	public PlanTermInfo(String termId, String termName, int year) {
		this.termId = termId;
		this.termName = termName;
		this.year = year;
	}

	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getTermName() {
		return termName;
	}
	public void setTermName(String termName) {
		this.termName = termName;
	}
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public List<CourseTypeInfo> getCourseTypes() {
		return courseTypes;
	}

	public void setCourseTypes(List<CourseTypeInfo> courseTypes) {
		this.courseTypes = courseTypes;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((courseTypes == null) ? 0 : courseTypes.hashCode());
		result = prime * result + (selected ? 1231 : 1237);
		result = prime * result + ((termId == null) ? 0 : termId.hashCode());
		result = prime * result
				+ ((termName == null) ? 0 : termName.hashCode());
		result = prime * result + year;
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
		PlanTermInfo other = (PlanTermInfo) obj;
		if (courseTypes == null) {
			if (other.courseTypes != null)
				return false;
		} else if (!courseTypes.equals(other.courseTypes))
			return false;
		if (termId == null) {
			if (other.termId != null)
				return false;
		} else if (!termId.equals(other.termId))
			return false;
		return true;
	}

	
	public void addCourseTypeInfo(CourseTypeInfo courseType) {
		if (courseTypes == null) {
			courseTypes = new ArrayList<CourseTypeInfo>();
		}
		courseTypes.add(courseType);
	}

	
	public void addPlannedCourse(CourseInfo course) {
		addCourseByType(ConversationConstants.CONV_COURSE_TYPE_PLANNED, course);
	}
	
	public void addBackupCourse(CourseInfo course) {
		addCourseByType(ConversationConstants.CONV_COURSE_TYPE_BACKUP, course);
	}
	
	private void addCourseByType(String type, CourseInfo course) {
		CourseTypeInfo cti = getCourseTypeInfosByType(type);
		if (cti == null) {
			cti = new CourseTypeInfo(type);
			courseTypes.add(cti);
		}
		cti.addCourse(course);
	}
	
	public List<CourseInfo> getBackupCourses() {
		return getCoursesByType(ConversationConstants.CONV_COURSE_TYPE_BACKUP);
	}
	
	public List<CourseInfo> getPlannedCourses() {
		return getCoursesByType(ConversationConstants.CONV_COURSE_TYPE_PLANNED);
	}
	
	public List<CourseInfo> getAllCourses() {
		List<CourseInfo> courses = new ArrayList<CourseInfo>();
		courses.addAll(getCoursesByType(ConversationConstants.CONV_COURSE_TYPE_PLANNED));
		courses.addAll(getCoursesByType(ConversationConstants.CONV_COURSE_TYPE_BACKUP));
		return courses;
	}
	
	/**
	 * Helper method to look through the CourseTypeInfos and return the list of CourseInfos for the desired type
	 * @param type
	 * @return
	 */
	private List<CourseInfo> getCoursesByType(String type) {
		if (courseTypes != null) {
			for (CourseTypeInfo cti : courseTypes) {
				if (cti.getType().equals(type))
					return cti.getCourses();
			}
		}
		return new ArrayList<CourseInfo>();
	}
	
	/**
	 * Helper method to look through the list of CourseTypeInfos and return the one of the desired type
	 * @param type Type to return
	 * @return
	 */
	private CourseTypeInfo getCourseTypeInfosByType(String type) {
		if (courseTypes != null) {
			for (CourseTypeInfo cti : courseTypes) {
				if (cti.getType().equals(type))
					return cti;
			}
		}
		return null;
	}

	@Override
	public int compareTo(PlanTermInfo obj) {
		//Reverse sort
		return -1 * this.termId.compareTo(obj.getTermId());
	}

	@Override
	public String toString() {
		return "PlanTermInfo [termId=" + termId + ", termName=" + termName
				+ ", year=" + year + ", selected=" + selected
				+ ", courseTypes=" + courseTypes + "]";
	}
	
	/**
	 * Combine the term name ("Spring") and year (2013) to build the full term name ("Spring 2012")
	 * @return
	 */
	public String getFullName() {
		return termName + " " + String.valueOf(year);
	}
	
}
