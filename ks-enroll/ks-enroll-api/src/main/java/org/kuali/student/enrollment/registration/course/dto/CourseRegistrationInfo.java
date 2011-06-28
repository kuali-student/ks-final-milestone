package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.registration.course.infc.CourseRegistration;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExemptionInfo", propOrder = {"id", "typeKey", "stateKey","courseOfferingId",
        "studentId", "creditCount", "meta", "attributes", "_futureElements"})
public class CourseRegistrationInfo extends RelationshipInfo implements CourseRegistration, Serializable {

    private static final long serialVersionUID = 1L;

    private String courseOfferingId;
    
    private List<String> activityOfferingIds;
    
    private List<String> regGroupOfferingIds;
    
    private String studentId;
    

    private String creditCount;

    public void setCreditCount(String creditCount) {
        this.creditCount = creditCount;
    }

    private List<Element> _futureElements;

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

    @Override
    public String getCreditCount() {
        return creditCount;
    }

    public List<String> getActivityOfferingIds() {
        return activityOfferingIds;
    }

    public void setActivityOfferingIds(List<String> activityOfferingIds) {
        this.activityOfferingIds = activityOfferingIds;
    }

    public List<String> getRegGroupOfferingIds() {
        return regGroupOfferingIds;
    }

    public void setRegGroupOfferingIds(List<String> regGroupOfferingIds) {
        this.regGroupOfferingIds = regGroupOfferingIds;
    }

    
}
