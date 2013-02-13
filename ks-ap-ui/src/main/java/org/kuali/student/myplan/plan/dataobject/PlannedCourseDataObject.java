package org.kuali.student.myplan.plan.dataobject;

import org.kuali.student.myplan.course.dataobject.CourseDetails;
import org.kuali.student.myplan.course.dataobject.CourseSummaryDetails;

import java.security.PrivateKey;

/**
 * Created by IntelliJ IDEA.
 * User: hemanthg
 * Date: 4/26/12
 * Time: 3:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlannedCourseDataObject implements Comparable {

    private transient PlanItemDataObject planItemDataObject;
    private transient CourseDetails courseDetails;
    private transient boolean showAlert;
    private transient boolean timeScheduleOpen;

    public CourseDetails getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(CourseDetails courseDetails) {
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
}
