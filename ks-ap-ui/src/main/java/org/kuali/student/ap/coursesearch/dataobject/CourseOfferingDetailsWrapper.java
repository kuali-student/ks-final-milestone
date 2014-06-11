package org.kuali.student.ap.coursesearch.dataobject;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 6/6/14
 * Time: 8:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingDetailsWrapper {

    private String courseOfferingId;
    private String courseOfferingCode;
    private String courseOfferingTitle;
    private String courseOfferingDescription;
    private boolean multipleFormatOfferings;

    private List<PlannedRegGroupDetailsWrapper> plannedRegGroupDetailsWrappers;
    private List<ActivityFormatDetailsWrapper> activityFormatDetailsWrappers;

    public CourseOfferingDetailsWrapper (CourseOfferingInfo courseOfferingInfo) {
        courseOfferingId = courseOfferingInfo.getId();
        courseOfferingCode = courseOfferingInfo.getCourseOfferingCode();
        courseOfferingTitle = courseOfferingInfo.getCourseOfferingTitle();
        courseOfferingDescription = courseOfferingInfo.getDescr().getPlain();
//        multipleFormatOfferings = courseOfferingInfo.
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
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

    public List<ActivityFormatDetailsWrapper> getActivityFormatDetailsWrappers() {
        return activityFormatDetailsWrappers;
    }

    public void setActivityFormatDetailsWrappers(List<ActivityFormatDetailsWrapper> activityFormatDetailsWrappers) {
        this.activityFormatDetailsWrappers = activityFormatDetailsWrappers;
    }
    public List<PlannedRegGroupDetailsWrapper> getPlannedRegGroupDetailsWrappers() {
        return plannedRegGroupDetailsWrappers;
    }

    public void setPlannedRegGroupDetailsWrappers(List<PlannedRegGroupDetailsWrapper> plannedRegGroupDetailsWrappers) {
        this.plannedRegGroupDetailsWrappers = plannedRegGroupDetailsWrappers;
    }

}
