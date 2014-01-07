package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseSearchResult", propOrder = {
        "courseOfferingId", "activityOfferingIds", "regGroupId", "regGroupName",
        "regGroupState"})
public class RegGroupSearchResult {

    String courseOfferingId;
    List<String> activityOfferingIds;
    String regGroupId;
    String regGroupName;
    String regGroupState;

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public List<String> getActivityOfferingIds() {
        if(activityOfferingIds == null) activityOfferingIds = new ArrayList<String>();

        return activityOfferingIds;
    }

    public void setActivityOfferingIds(List<String> activityOfferingIds) {
        this.activityOfferingIds = activityOfferingIds;
    }

    public String getRegGroupId() {
        return regGroupId;
    }

    public void setRegGroupId(String regGroupId) {
        this.regGroupId = regGroupId;
    }

    public String getRegGroupName() {
        return regGroupName;
    }

    public void setRegGroupName(String regGroupName) {
        this.regGroupName = regGroupName;
    }

    public String getRegGroupState() {
        return regGroupState;
    }

    public void setRegGroupState(String regGroupState) {
        this.regGroupState = regGroupState;
    }
}
