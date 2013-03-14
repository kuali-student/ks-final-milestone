package org.kuali.student.myplan.plan.dataobject;

import org.kuali.student.myplan.course.dataobject.ActivityOfferingItem;
import org.kuali.student.myplan.course.dataobject.CourseDetails;
import org.kuali.student.myplan.course.dataobject.CourseOfferingInstitution;
import org.kuali.student.myplan.course.dataobject.CourseSummaryDetails;

import java.util.List;

/**
 * Captures a course detail object along with a single instance of its planned information.
 * <p/>
 * Date: 4/26/12
 * Time: 3:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlannedCourseDataObject implements Comparable {

    private transient PlanItemDataObject planItemDataObject;

    private List<ActivityOfferingItem> planActivities;

    private transient CourseSummaryDetails courseDetails;

    private transient boolean showAlert;

    // TODO: KULRICE-9003. This should be on plannedTerm once the jira is resolved
    private transient boolean timeScheduleOpen;

    public CourseSummaryDetails getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(CourseSummaryDetails courseDetails) {
        this.courseDetails = courseDetails;
    }

    public PlanItemDataObject getPlanItemDataObject() {
        return planItemDataObject;
    }

    public void setPlanItemDataObject(PlanItemDataObject planItemDataObject) {
        this.planItemDataObject = planItemDataObject;
    }


    public boolean isShowAlert() {
        return showAlert;
    }

    public void setShowAlert(boolean showAlert) {
        this.showAlert = showAlert;
    }

    @Override
    public int compareTo( Object object ) {
        PlannedCourseDataObject that = (PlannedCourseDataObject) object;
        return this.getPlanItemDataObject().getDateAdded().compareTo( that.getPlanItemDataObject().getDateAdded() ) * -1;
    }

    public boolean isTimeScheduleOpen() {
        return timeScheduleOpen;
    }

    public void setTimeScheduleOpen(boolean timeScheduleOpen) {
        this.timeScheduleOpen = timeScheduleOpen;
    }

    public List<ActivityOfferingItem> getPlanActivities() {
        return planActivities;
    }

    public void setPlanActivities(List<ActivityOfferingItem> planActivities) {
        this.planActivities = planActivities;
    }
}
