package org.kuali.student.ap.coursesearch.dataobject;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 6/6/14
 * Time: 8:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingDetailsWrapper {

    private String courseOfferingCode;
    private String courseOfferingTitle;
    private String courseOfferingDescription;
    private boolean multipleFormatOfferings;
//    private List<PlannedRegGroupDetails> plannedRegGroupDetails;
//    private List<FormatOfferingDetailsWrapper> formatOfferingDetailsWrappers;

    public CourseOfferingDetailsWrapper (CourseOfferingInfo courseOfferingInfo) {
        courseOfferingCode = courseOfferingInfo.getCourseOfferingCode();
        courseOfferingTitle = courseOfferingInfo.getCourseOfferingTitle();
        courseOfferingDescription = courseOfferingInfo.getDescr().getPlain();
//        multipleFormatOfferings = courseOfferingInfo.
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getCourseOfferingTitle() {
        return courseOfferingTitle;
    }

    public void setCourseOfferingTitle(String courseOfferingTitle) {
        this.courseOfferingTitle = courseOfferingTitle;
    }

    public String getCourseOfferingDescription() {
        return courseOfferingDescription;
    }

    public void setCourseOfferingDescription(String courseOfferingDescription) {
        this.courseOfferingDescription = courseOfferingDescription;
    }

    public boolean isMultipleFormatOfferings() {
        return multipleFormatOfferings;
    }

    public void setMultipleFormatOfferings(boolean multipleFormatOfferings) {
        this.multipleFormatOfferings = multipleFormatOfferings;
    }
}
