package org.kuali.student.enrollment.class2.registration.dto;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Needs to clean up the core slice codes
@Deprecated
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

    //TODO this is a hack to parse for human readable - it should be going to some service to get a human readable name for this activity
    public String getTypeName() {
        String key = activityOffering.getTypeKey();
        String name = key.substring(key.lastIndexOf(".") + 1);
        return String.format( "%s%s", Character.toUpperCase(name.charAt(0)), name.substring(1));
    }
}
