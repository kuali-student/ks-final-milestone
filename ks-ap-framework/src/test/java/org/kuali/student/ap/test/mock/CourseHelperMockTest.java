package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.framework.context.CourseHelper;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOfferingDisplay;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.infc.Course;

import java.util.List;

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
    public Course getCurrentVersionOfCourse(String courseId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingDisplay> getActivityOfferingDisplaysByCourseAndTerm(String courseId, String term) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Term getLastOfferedTermForCourse(Course course) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getScheduledTermsForCourse(Course course) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get a list of offerings for a list of courses in a given list of terms
     *
     * @param courses - List of courses
     * @return List of all offerings for each course id that occurs during one of the listed terms
     */
    @Override
    public List<CourseOffering> getCourseOfferingsForCourses(List<CourseSearchItem> courses) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<CourseOffering> getCourseOfferingsForCoursesAndTerms(List<String> courseIds, List<Term> terms) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public String getCurrentVersionIdOfCourse(String courseId) {
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

    @Override
    public boolean isCourseBookmarked(Course course, List<PlanItem> planItems){
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Course getCurrentVersionOfCourseByVersionIndependentId(String versionIndependentId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getAllCourseIdsByVersionIndependentId(String versionIndependentId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates that the course is able to be added to the learning plan
     *
     * @param course - Course to be added
     * @return null if valid, response message otherwise
     */
    @Override
    public String validateCourseForAdd(Course course) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve and format the list of projected terms to be displayed on the page
     *
     * @param course - Course that is being displayed
     * @return Formatted list of projected terms
     */
    @Override
    public List<String> getProjectedTermsForCourse(Course course) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
