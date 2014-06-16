package org.kuali.student.ap.coursesearch.dataobject;

import org.kuali.student.ap.coursesearch.util.CourseDetailsUtil;
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
    private List<String> courseRequisites;
    private boolean honors;

    private List<ActivityFormatDetailsWrapper> activityFormatDetailsWrappers;

    private List<ActivityOfferingDetailsWrapper> plannedActivityDetailsWrappers;

    public CourseOfferingDetailsWrapper (CourseOfferingInfo courseOfferingInfo) {
        courseOfferingId = courseOfferingInfo.getId();
        courseOfferingCode = courseOfferingInfo.getCourseOfferingCode();
        courseOfferingTitle = courseOfferingInfo.getCourseOfferingTitle();
        courseOfferingDescription = courseOfferingInfo.getDescr().getPlain();
//        multipleFormatOfferings = courseOfferingInfo.
        honors = Boolean.TRUE.equals(courseOfferingInfo.getIsHonorsOffering());
        courseRequisites = CourseDetailsUtil.getCourseOfferingRequisites(courseOfferingInfo);
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

    public List<ActivityOfferingDetailsWrapper> getPlannedActivityDetailsWrappers() {
        return plannedActivityDetailsWrappers;
    }

    public void setPlannedActivityDetailsWrappers(List<ActivityOfferingDetailsWrapper>
            plannedActivityDetailsWrappers) {
        this.plannedActivityDetailsWrappers = plannedActivityDetailsWrappers;
    }

    public int getPlannedGroupsCount() {
        int count=0;
        String lastCountedRegGroupCode="";
        for (ActivityOfferingDetailsWrapper plannedActivities : plannedActivityDetailsWrappers ) {
            if (!lastCountedRegGroupCode.equals(plannedActivities.getRegGroupCode())) {
                count++;
                lastCountedRegGroupCode=plannedActivities.getRegGroupCode();
            }
        }
        return count;
    }

    public boolean isHonors() {
        return honors;
    }

    public void setHonors(boolean honors) {
        this.honors = honors;
    }

    public List<String> getCourseRequisites() {
        return courseRequisites;
    }

    public void setCourseRequisites(List<String> courseRequisites) {
        this.courseRequisites = courseRequisites;
    }
}
