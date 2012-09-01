package org.kuali.student.enrollment.class2.registration.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Needs to clean up the core slice codes
@Deprecated
public class CourseOfferingWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    private CourseOfferingInfo courseOffering;
    private String prereq;

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

    public String getInstructorsList(){
        String instructors = "";
        if(this.getCourseOffering() != null && this.getCourseOffering().getInstructors() != null &&
                !this.getCourseOffering().getInstructors().isEmpty()){
            for(OfferingInstructorInfo instructor: this.getCourseOffering().getInstructors()){
                if(StringUtils.isBlank(instructors)){
                    instructors = instructor.getPersonId();
                }
                else{
                    instructors = instructor + ", " + instructor.getPersonId();
                }
            }
        }
        return instructors;
    }

    public String getPrereq() {
        return prereq;
    }

    public void setPrereq(String prereq) {
        this.prereq = prereq;
    }
}
