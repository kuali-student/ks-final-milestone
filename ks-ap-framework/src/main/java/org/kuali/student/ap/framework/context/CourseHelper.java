package org.kuali.student.ap.framework.context;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.infc.Course;

import java.util.List;
import java.util.Map;

/**
 * Helper class that provides some convenience methods around Course interactions.
 */
public interface CourseHelper {

    /**
     * Pre-load the listed courses and terms (and related objects) and add them to a cache so that they are available for future calls
     * @param courseIds
     * @param termId
     */
	void frontLoad(List<String> courseIds, String... termId);

    /**
     * returns the courseInfo for the given courseId by verifying the courseId to
     * be a verifiedCourseId
     *
     * @param courseId
     * @return
     */
	CourseInfo getCourseInfo(String courseId);

    /**
     * Get the list of ActivityOfferingDisplayInfo objects for the given courseId and term
     * @param courseId
     * @param term
     * @return
     */
	List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysByCourseAndTerm(String courseId, String term);

    /**
     * Get enrollment related information for the given courseId in the given termId
     * @param mapmap
     * @param termId
     * @param courseId
     * @return
     */
	Map<String, Map<String, Object>> getAllSectionStatus(
			Map<String, Map<String, Object>> mapmap, String termId,
			String courseId);

    /**
     *
     * @param course
     * @return
     */
	String getLastOfferedTermId(Course course);

    /**
     * Get a list of term ids where the given course is scheduled
     * @param course
     * @return
     */
	List<String> getScheduledTermsForCourse(Course course);

    /**
     * returns a SLN for given params
     *
     * @param year
     * @param term
     * @param subject
     * @param number
     * @param activityCd
     * @return
     */
	String getSLN(String year, String term, String subject, String number,
			String activityCd);

    /**
     * Takes a courseId that can be either a version independent Id or a version
     * dependent Id and returns a version dependent Id. In case of being passed
     * in a version depend
     *
     * @param courseId
     * @return
     */
	String getCurrentVersionIdOfCourse(String courseId);

    /**
     * Takes a courseId that can be either a version independent Id or a version
     * dependent Id and returns a version dependent course. In case of being passed
     * in a version depend
     *
     * @param courseId
     * @return
     */
    Course getCurrentVersionOfCourse(String courseId);

    /**
     * returns the course code from given activityId
     * <p/>
     * eg: for activityId '2013:2:CHEM:152:A' course code CHEM 152 is returned
     *
     * @param activityId
     * @return
     */
	String getCourseCdFromActivityId(String activityId);

    /**
     * Get a list of Course objects that have a matching course code
     * @param courseCd Course code used to find all courses
     * @return List of Course objects
     */
	List<Course> getCoursesByCode(String courseCd);

    /**
     * Determines whether a course is in a specific term.
     *
     * @param term Term to search for the given course
     * @param course Course to find within the given term
     * @return True if found, false if not
     */
    boolean isCourseOffered(Term term, Course course);

}
