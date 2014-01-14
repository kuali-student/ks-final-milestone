package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseAndPrimaryAOSearchResult", propOrder = {
        "courseOfferingInfo", "primaryActivityOfferings"})
public class CourseAndPrimaryAOSearchResult {

    private CourseSearchResult courseOfferingInfo;
    private List<ActivityOfferingSearchResult> primaryActivityOfferings;

    public CourseSearchResult getCourseOfferingInfo() {
        return courseOfferingInfo;
    }

    public void setCourseOfferingInfo(CourseSearchResult courseOfferingInfo) {
        this.courseOfferingInfo = courseOfferingInfo;
    }

    public List<ActivityOfferingSearchResult> getPrimaryActivityOfferingInfo() {
        return primaryActivityOfferings;
    }

    public void setPrimaryActivityOfferingInfo(List<ActivityOfferingSearchResult> primaryActivityOfferingInfo) {
        this.primaryActivityOfferings = primaryActivityOfferingInfo;
    }
}
