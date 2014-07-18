package org.kuali.student.enrollment.class2.registration.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.registration.client.service.dto.CourseSearchResult;

import java.util.List;

/**
 * Created by swedev on 1/12/14.
 */
public class CourseRegistrationKradForm extends UifFormBase {

    String termCode;
    String courseCode;

    List<CourseSearchResult> courseOfferingSearchResults;

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public List<CourseSearchResult> getCourseOfferingSearchResults() {
        return courseOfferingSearchResults;
    }

    public void setCourseOfferingSearchResults(List<CourseSearchResult> courseOfferingSearchResults) {
        this.courseOfferingSearchResults = courseOfferingSearchResults;
    }
}
