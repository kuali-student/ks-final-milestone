package org.kuali.student.enrollment.class2.registration.dto;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseOfferingInfoWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    private CourseOfferingInfo courseOffering;

    private List<RegistrationGroupInfoWrapper> registrationGroupInfoWrappers;

    public CourseOfferingInfoWrapper() {
        registrationGroupInfoWrappers = new ArrayList<RegistrationGroupInfoWrapper>();
    }

    public List<RegistrationGroupInfoWrapper> getRegistrationGroupInfoWrappers() {
        return registrationGroupInfoWrappers;
    }

    public void setRegistrationGroupInfoWrappers(List<RegistrationGroupInfoWrapper> registrationGroupInfoWrappers) {
        this.registrationGroupInfoWrappers = registrationGroupInfoWrappers;
    }

    public CourseOfferingInfo getCourseOffering() {
        return courseOffering;
    }

    public void setCourseOffering(CourseOfferingInfo courseOffering) {
        this.courseOffering = courseOffering;
    }
}
