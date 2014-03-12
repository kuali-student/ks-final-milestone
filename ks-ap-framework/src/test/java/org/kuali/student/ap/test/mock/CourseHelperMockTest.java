package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.framework.context.CourseHelper;
import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.infc.Course;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 9:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseHelperMockTest implements CourseHelper {
    @Override
    public void frontLoad(List<String> courseIds, String... termId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CourseInfo getCourseInfo(String courseId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysByCourseAndTerm(String courseId, String term) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, Map<String, Object>> getAllSectionStatus(Map<String, Map<String, Object>> mapmap, String termId, String courseId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getLastOfferedTermIdForCourse(Course course) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getScheduledTermsForCourse(Course course) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get a list of offerings for a list of courses in a given list of terms
     *
     * @param courseIds - List of courses
     * @return List of all offerings for each course id that occurs during one of the listed terms
     */
    @Override
    public List<CourseOfferingInfo> getCourseOfferingsForCourses(List<CourseSearchItem> courses) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getSLN(String year, String term, String subject, String number, String activityCd) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCurrentVersionIdOfCourse(String courseId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Course getCurrentVersionOfCourse(String courseId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCourseCdFromActivityId(String activityId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Course> getCoursesByCode(String courseCd) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isCourseOffered(Term term, Course course) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
