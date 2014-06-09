package org.kuali.student.ap.coursesearch.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.coursesearch.dataobject.CourseTermDetailsWrapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 6/6/14
 * Time: 8:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseSectionDetailsForm extends UifFormBase {

    private String courseCode;
    private String courseTitle;
    private List<CourseTermDetailsWrapper> courseTermDetailsWrappers;

    public CourseSectionDetailsForm() {
        super();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public List<CourseTermDetailsWrapper> getCourseTermDetailsWrappers() {
        return courseTermDetailsWrappers;
    }

    public void setCourseTermDetailsWrappers(List<CourseTermDetailsWrapper> courseTermDetailsWrappers) {
        this.courseTermDetailsWrappers = courseTermDetailsWrappers;
    }


}