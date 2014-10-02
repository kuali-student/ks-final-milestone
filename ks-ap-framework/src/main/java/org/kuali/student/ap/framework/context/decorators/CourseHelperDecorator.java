/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.ap.framework.context.decorators;

import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.framework.context.CourseHelper;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOfferingDisplay;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.infc.Course;

import java.util.List;


public class CourseHelperDecorator implements CourseHelper {

    private CourseHelper nextDecorator;

    public CourseHelper getNextDecorator(){
        if (null == nextDecorator) {
            throw new RuntimeException("Misconfigured application: nextDecorator is null");
        }

        return nextDecorator;
    }

    public void setNextDecorator(CourseHelper nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public void frontLoad(List<String> courseIds, String... termId) {
        getNextDecorator().frontLoad(courseIds,termId);
    }

    @Override
    public Course getCurrentVersionOfCourse(String courseId) {
        return getNextDecorator().getCurrentVersionOfCourse(courseId);
    }

    @Override
    public List<ActivityOfferingDisplay> getActivityOfferingDisplaysByCourseAndTerm(String courseId, String term) {
        return getNextDecorator().getActivityOfferingDisplaysByCourseAndTerm(courseId,term);
    }

    @Override
    public Term getLastOfferedTermForCourse(Course course) {
        return getNextDecorator().getLastOfferedTermForCourse(course);
    }

    @Override
    public List<String> getScheduledTermsForCourse(Course course) {
        return getNextDecorator().getScheduledTermsForCourse(course);
    }

    @Override
    public List<CourseOffering> getCourseOfferingsForCourses(List<CourseSearchItem> courses) {
        return getNextDecorator().getCourseOfferingsForCourses(courses);
    }

    @Override
    public List<CourseOffering> getCourseOfferingsForCoursesAndTerms(List<String> courseIds, List<Term> terms) {
        return getNextDecorator().getCourseOfferingsForCoursesAndTerms(courseIds,terms);
    }

    @Override
    public String getCurrentVersionIdOfCourse(String courseId) {
        return getNextDecorator().getCurrentVersionIdOfCourse(courseId);
    }

    @Override
    public String getCourseCdFromActivityId(String activityId) {
        return getNextDecorator().getCourseCdFromActivityId(activityId);
    }

    @Override
    public Course getCourseByCode(String courseCd) {
        return getNextDecorator().getCourseByCode(courseCd);
    }

    @Override
    public boolean isCourseOffered(Term term, Course course) {
        return getNextDecorator().isCourseOffered(term,course);
    }

    @Override
    public boolean isCourseBookmarked(Course course, List<PlanItem> planItems) {
        return getNextDecorator().isCourseBookmarked(course,planItems);
    }

    @Override
    public Course getCurrentVersionOfCourseByVersionIndependentId(String versionIndependentId) {
        return getNextDecorator().getCurrentVersionOfCourseByVersionIndependentId(versionIndependentId);
    }

    @Override
    public List<String> getAllCourseIdsByVersionIndependentId(String versionIndependentId) {
        return getNextDecorator().getAllCourseIdsByVersionIndependentId(versionIndependentId);
    }

    @Override
    public String validateCourseForAdd(Course course) {
        return getNextDecorator().validateCourseForAdd(course);
    }

    @Override
    public List<String> getProjectedTermsForCourse(Course course) {
        return getNextDecorator().getProjectedTermsForCourse(course);
    }

    /**
     * Retrieves formatter class for handling the retrieval and display of credit values
     *
     * @return Set formatter class
     */
    @Override
    public CreditsFormatter getCreditsFormatter() {
        return getNextDecorator().getCreditsFormatter();
    }
}
