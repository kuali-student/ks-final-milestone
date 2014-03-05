package org.kuali.student.ap.framework.context;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.infc.Course;

import java.util.List;
import java.util.Map;

public interface CourseHelper {

	void frontLoad(List<String> courseIds, String... termId);

	CourseInfo getCourseInfo(String courseId);

	List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysByCourseAndTerm(String courseId, String term);

	Map<String, Map<String, Object>> getAllSectionStatus(
			Map<String, Map<String, Object>> mapmap, String termId,
			String courseId);

	String getLastOfferedTermId(Course course);
	
	List<String> getScheduledTermsForCourse(Course course);

	String getSLN(String year, String term, String subject, String number,
			String activityCd);

	String getCurrentVersionIdOfCourse(String courseId);

    Course getCurrentVersionOfCourse(String courseId);

	String getCourseCdFromActivityId(String activityId);

	List<Course> getCoursesByCode(String courseCd);

    /**
     * Determines whether a course is in a specific term.
     *
     * @param term
     * @param course
     * @return
     */
    boolean isCourseOffered(Term term, Course course);

}
