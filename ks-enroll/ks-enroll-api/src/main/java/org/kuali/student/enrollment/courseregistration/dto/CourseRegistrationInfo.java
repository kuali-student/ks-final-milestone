package org.kuali.student.enrollment.courseregistration.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseregistration.infc.CourseRegistration;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseRegistrationInfo", propOrder = {"id", "typeKey", "stateKey", "courseOfferingId", "studentId",
        "regGroupRegistration", "credits", "gradingOptionKey", "effectiveDate", "expirationDate", "meta",
        "attributes", "_futureElements"})

public class CourseRegistrationInfo 
    extends RelationshipInfo 
    implements CourseRegistration, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String courseOfferingId;

    @XmlElement
    private RegGroupRegistrationInfo regGroupRegistration;

    @XmlElement
    private String studentId;

    @XmlElement
    private String credits;

    @XmlElement
    private String gradingOptionKey;

    @XmlAnyElement
    private List<Element> _futureElements;

    public CourseRegistrationInfo() {
    }

    public CourseRegistrationInfo(CourseRegistration courseRegistration) {
        super(courseRegistration);
        if (null != courseRegistration) {
            this.courseOfferingId = courseRegistration.getCourseOfferingId();
            this.studentId = courseRegistration.getStudentId();
            this.credits = courseRegistration.getCredits();
            this.gradingOptionKey = courseRegistration.getGradingOptionKey();
         }
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    @Override
    public String getCredits() {
        return credits;
    }

    public void setCredits(String creditCount) {
        this.credits = creditCount;
    }

    @Override
    public String getGradingOptionKey() {
        return gradingOptionKey;
    }

    public void setGradingOptionKey(String gradingOptionKey) {
        this.gradingOptionKey = gradingOptionKey;
    }
}
