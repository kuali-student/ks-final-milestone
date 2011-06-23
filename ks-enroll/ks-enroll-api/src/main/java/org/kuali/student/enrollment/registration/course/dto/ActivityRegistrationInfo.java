package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.Date;

import org.kuali.student.enrollment.registration.course.infc.ActivityRegistration;
import org.kuali.student.r2.common.dto.RelationshipInfo;

public class ActivityRegistrationInfo extends RelationshipInfo implements ActivityRegistration, Serializable {

    private static final long serialVersionUID = 1L;

    private String activityOfferingId;

    private String studentId;

    private String courseRegistrationId;

    public void setActivityOfferingId(String activityOfferingId) {
        this.activityOfferingId = activityOfferingId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setCourseRegistrationId(String courseRegistrationId) {
        this.courseRegistrationId = courseRegistrationId;
    }

    @Override
    public String getActivityOfferingId() {
        return activityOfferingId;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    @Override
    public String getCourseRegistrationId() {
        return courseRegistrationId;
    }

}
