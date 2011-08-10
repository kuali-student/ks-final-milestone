package org.kuali.student.enrollment.courseregistration.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseScheduleEntryViewInfo", propOrder = {"registrationGroup", "courseOffering", "activityOfferings",
        "courseRegistrationId", "creditCount", "_futureElements"})
public class CourseScheduleEntryViewInfo {
    @XmlElement
    private RegistrationGroupInfo registrationGroup;
    @XmlElement
    private CourseOfferingInfo courseOffering;
    @XmlElement
    private List<ActivityOfferingInfo> activityOfferings;
    @XmlElement
    private String courseRegistrationId;
    @XmlElement
    private String creditCount;
    @XmlAnyElement
    private List<Element> _futureElements;

    public CourseScheduleEntryViewInfo() {}

    public CourseScheduleEntryViewInfo(RegistrationGroupInfo registrationGroup, CourseOfferingInfo courseOffering,
            List<ActivityOfferingInfo> activityOfferings, String courseRegistrationId, String creditCount) {
        this.activityOfferings = activityOfferings;
        this.courseOffering = courseOffering;
        this.courseRegistrationId = courseRegistrationId;
        this.creditCount = creditCount;
        this.registrationGroup = registrationGroup;

    }

    public String getCourseRegistrationId() {
        return courseRegistrationId;
    }

    public String getCreditCount() {
        return creditCount;
    }

    public RegistrationGroupInfo getRegistrationGroup() {
        return registrationGroup;
    }

    public CourseOfferingInfo getCourseOffering() {
        return courseOffering;
    }

    public List<ActivityOfferingInfo> getActivityOfferings() {
        return activityOfferings;
    }

}
