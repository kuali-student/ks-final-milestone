package org.kuali.student.enrollment.class2.registration.dto;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegistrationGroupWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    private CourseOfferingInfo courseOffering;
    private RegistrationGroupInfo registrationGroup;

    private List<ActivityOfferingInfo> activityOfferings;

    public RegistrationGroupWrapper() {
        activityOfferings = new ArrayList<ActivityOfferingInfo>();
    }

    public CourseOfferingInfo getCourseOffering() {
        return courseOffering;
    }

    public void setCourseOffering(CourseOfferingInfo courseOffering) {
        this.courseOffering = courseOffering;
    }

    public RegistrationGroupInfo getRegistrationGroup() {
        return registrationGroup;
    }

    public void setRegistrationGroup(RegistrationGroupInfo registrationGroup) {
        this.registrationGroup = registrationGroup;
    }

    public List<ActivityOfferingInfo> getActivityOfferings() {
        return activityOfferings;
    }

    public void setActivityOfferings(List<ActivityOfferingInfo> activityOfferings) {
        this.activityOfferings = activityOfferings;
    }
}
