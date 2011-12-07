package org.kuali.student.enrollment.class2.registration.dto;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseOfferingWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    private CourseOfferingInfo courseOffering;

    private List<RegistrationGroupWrapper> registrationGroupWrappers;

    public CourseOfferingWrapper() {
        registrationGroupWrappers = new ArrayList<RegistrationGroupWrapper>();
    }

    public List<RegistrationGroupWrapper> getRegistrationGroupWrappers() {
        return registrationGroupWrappers;
    }

    public void setRegistrationGroupWrappers(List<RegistrationGroupWrapper> registrationGroupWrappers) {
        this.registrationGroupWrappers = registrationGroupWrappers;
    }

    public CourseOfferingInfo getCourseOffering() {
        return courseOffering;
    }

    public void setCourseOffering(CourseOfferingInfo courseOffering) {
        this.courseOffering = courseOffering;
    }
}
