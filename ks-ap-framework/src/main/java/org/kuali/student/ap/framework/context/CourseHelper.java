package org.kuali.student.ap.framework.context;

import java.util.List;
import java.util.Map;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.infc.Course;

public interface CourseHelper {

	void frontLoad(List<String> courseIds, String... termId);

	CourseInfo getCourseInfo(String courseId);

	List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysByCourseAndTerm(String courseId, String term);

	Map<String, Map<String, Object>> getAllSectionStatus(
			Map<String, Map<String, Object>> mapmap, String termId,
			String courseId);

	DeconstructedCourseCode getCourseDivisionAndNumber(String courseCode);
	
	String getLastOfferedTermId(Course course);
	
	List<String> getScheduledTerms(Course course);
	
	String getCourseId(String subjectArea, String number);

	String getCourseIdForTerm(String subjectArea, String number, String termId);

	String buildActivityRefObjId(String atpId, String subject, String number,
			String activityCd);

	String getSLN(String year, String term, String subject, String number,
			String activityCd);

	String joinStringsByDelimiter(char delimiter, String... list);

	String getVerifiedCourseId(String courseId);

	String getCourseCdFromActivityId(String activityId);

	String getCodeFromActivityId(String activityId);

	List<Course> getCoursesByCode(String courseCd);

}
