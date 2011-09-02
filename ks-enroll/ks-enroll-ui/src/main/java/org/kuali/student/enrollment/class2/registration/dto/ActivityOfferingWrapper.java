package org.kuali.student.enrollment.class2.registration.dto;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityOfferingWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    private ActivityOfferingInfo activityOffering;

    private List<MeetingScheduleWrapper> meetingScheduleWrappers;

    public ActivityOfferingWrapper() {
        meetingScheduleWrappers = new ArrayList<MeetingScheduleWrapper>();
    }

    public ActivityOfferingInfo getActivityOffering() {
        return activityOffering;
    }

    public void setActivityOffering(ActivityOfferingInfo activityOffering) {
        this.activityOffering = activityOffering;
    }

    public List<MeetingScheduleWrapper> getMeetingScheduleWrappers() {
        return meetingScheduleWrappers;
    }

    public void setMeetingScheduleWrappers(List<MeetingScheduleWrapper> meetingScheduleWrappers) {
        this.meetingScheduleWrappers = meetingScheduleWrappers;
    }
}
