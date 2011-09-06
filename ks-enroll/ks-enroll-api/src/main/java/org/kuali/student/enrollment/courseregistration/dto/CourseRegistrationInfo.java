package org.kuali.student.enrollment.courseregistration.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseregistration.infc.CourseRegistration;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseRegistrationInfo", propOrder = {"id", "typeKey", "stateKey", "courseOffering", "studentId",
        "regGroupRegistration", "creditCount", "gradingOption", "effectiveDate", "expirationDate", "meta",
        "attributes", "_futureElements"})
public class CourseRegistrationInfo extends RelationshipInfo implements CourseRegistration, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private CourseOfferingInfo courseOffering;

    @XmlElement
    private RegGroupRegistrationInfo regGroupRegistration;

    @XmlElement
    private String studentId;

    @XmlElement
    private String creditCount;

    @XmlElement
    private String gradingOption;
 
    @XmlAnyElement
    private List<Element> _futureElements;

    public CourseRegistrationInfo() {
        super();
        this.courseOffering = null;
        this.studentId = null;
        this.creditCount = null;
        this._futureElements = null;
    }

    public CourseRegistrationInfo(CourseRegistration courseRegistration) {
        super(courseRegistration);
        if (null != courseRegistration) {
            this.courseOffering = new CourseOfferingInfo(courseRegistration.getCourseOffering());
            this.studentId = courseRegistration.getStudentId();
            this.creditCount = courseRegistration.getCreditCount();
            this._futureElements = null;
        }
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    @Override
    public String getCreditCount() {
        return creditCount;
    }

    @Override
    public String getGradingOption() {
        return gradingOption;
    }

    public void setGradingOption(String gradingOption) {
        this.gradingOption = gradingOption;
    }

    @Override
    public CourseOfferingInfo getCourseOffering() {
        return courseOffering;
    }

    @Override
    public RegGroupRegistrationInfo getRegGroupRegistration() {
        return regGroupRegistration;
    }

    public void setRegGroupRegistration(RegGroupRegistrationInfo regGroupRegistration) {
        this.regGroupRegistration = regGroupRegistration;
    }

    public void setCourseOffering(CourseOfferingInfo courseOffering) {
        this.courseOffering = courseOffering;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setCreditCount(String creditCount) {
        this.creditCount = creditCount;
    }

}
