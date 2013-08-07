package org.kuali.student.enrollment.class2.registration.dto;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Needs to clean up the core slice codes
@Deprecated
public class RegistrationGroupWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    private CourseOfferingInfo courseOffering;
    private RegistrationGroupInfo registrationGroup;

    private List<ActivityOfferingWrapper> activityOfferingWrappers;

    public RegistrationGroupWrapper() {
        activityOfferingWrappers = new ArrayList<ActivityOfferingWrapper>();
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

    public List<ActivityOfferingWrapper> getActivityOfferingWrappers() {
        return activityOfferingWrappers;
    }

    public void setActivityOfferingWrappers(List<ActivityOfferingWrapper> activityOfferingWrappers) {
        this.activityOfferingWrappers = activityOfferingWrappers;
    }
}
