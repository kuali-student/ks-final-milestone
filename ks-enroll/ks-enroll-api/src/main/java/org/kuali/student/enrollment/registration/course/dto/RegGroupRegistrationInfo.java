package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.enrollment.registration.course.infc.RegGroupRegistration;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;

public class RegGroupRegistrationInfo extends RelationshipInfo implements RegGroupRegistration, Serializable {

    private static final long serialVersionUID = 1L;

    private String regGroupId;

    private String studentId;
    
    private String courseRegistrationId;

    public void setRegGroupId(String regGroupId) {
        this.regGroupId = regGroupId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getRegGroupId() {
        return regGroupId;
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
