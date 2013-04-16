package org.kuali.student.enrollment.coursewaitlist.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.coursewaitlist.infc.CourseWaitlistEntry;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseWaitlistEntryInfo", propOrder = {"id", "typeKey", "stateKey", "effectiveDate", "expirationDate",
        "studentId", "registrationGroupId", "position", "lastCheckedIn", "courseOfferingId", "meta", "attributes",
        "_futureElements"})
public class CourseWaitlistEntryInfo extends RelationshipInfo implements CourseWaitlistEntry, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String studentId;

    @XmlElement
    private String registrationGroupId;

    @XmlElement
    private Integer position;

    @XmlElement
    private Date lastCheckedIn;

    @XmlElement
    private String courseOfferingId;

    @XmlAnyElement
    private List<Element> _futureElements;

    public CourseWaitlistEntryInfo() {
        super();
    }

    public CourseWaitlistEntryInfo(CourseWaitlistEntry courseWaitlistEntry) {
        super(courseWaitlistEntry);
        if (null != courseWaitlistEntry) {
            this.studentId = courseWaitlistEntry.getStudentId();
            this.registrationGroupId = courseWaitlistEntry.getRegistrationGroupId();
            this.position = courseWaitlistEntry.getPosition();
            this.lastCheckedIn = courseWaitlistEntry.getLastCheckedIn();
            this.courseOfferingId = courseWaitlistEntry.getCourseOfferingId();
        }
    }

    public void setLastCheckedIn(Date hasCheckedIn) {
        this.lastCheckedIn = hasCheckedIn;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getRegistrationGroupId() {
        return registrationGroupId;
    }

    public void setRegistrationGroupId(String registrationGroupId) {
        this.registrationGroupId = registrationGroupId;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public Date getLastCheckedIn() {
        return lastCheckedIn;
    }

    @Override
    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }
}
