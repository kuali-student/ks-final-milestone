package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.registration.course.infc.CourseRegistration;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseRegistrationInfo", propOrder = {"id", "typeKey", "stateKey", "courseOfferingId", "studentId",
        "creditCount", "meta", "attributes", "_futureElements"})
public class CourseRegistrationInfo extends RelationshipInfo implements CourseRegistration, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String courseOfferingId;

    @XmlElement
    private String studentId;
    
    @XmlElement
    private String creditCount;
    

    @XmlAnyElement
    private List<Element> _futureElements;
    
    public CourseRegistrationInfo() {
        super();
        this.courseOfferingId = null;
        this.studentId = null;
        this.creditCount = null;
        this._futureElements = null;
    }

    public CourseRegistrationInfo(CourseRegistration courseRegistration) {
        super(courseRegistration);
        if (null != courseRegistration) {
            this.courseOfferingId = courseRegistration.getCourseOfferingId();
            this.studentId = courseRegistration.getStudentId();
            this.creditCount = courseRegistration.getCreditCount();
            this._futureElements = null;
        }
    }

    public void setCreditCount(String creditCount) {
        this.creditCount = creditCount;
    }
    
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

}
