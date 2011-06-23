package org.kuali.student.enrollment.registration.course.dto;



import java.io.Serializable;

import org.kuali.student.enrollment.registration.course.infc.CourseRegistration;
import org.kuali.student.r2.common.dto.RelationshipInfo;


public class CourseRegistrationInfo extends  RelationshipInfo implements CourseRegistration, Serializable {

  
    private static final long serialVersionUID = 1L;
    
    private String courseOfferingId; 

    private String studentId;
    
    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

   
}
