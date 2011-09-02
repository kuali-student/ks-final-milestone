package org.kuali.student.enrollment.class2.registration.dto;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityOfferingWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    private ActivityOfferingInfo activityOffering;

    private List<ScheduleDataWrapper> scheduleDataWrappers;

    public ActivityOfferingWrapper() {
        scheduleDataWrappers = new ArrayList<ScheduleDataWrapper>();
    }

    public ActivityOfferingInfo getActivityOffering() {
        return activityOffering;
    }

    public void setActivityOffering(ActivityOfferingInfo activityOffering) {
        this.activityOffering = activityOffering;
    }

    public List<ScheduleDataWrapper> getScheduleDataWrappers() {
        return scheduleDataWrappers;
    }

    public void setScheduleDataWrappers(List<ScheduleDataWrapper> scheduleDataWrappers) {
        this.scheduleDataWrappers = scheduleDataWrappers;
    }
}
