package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentScheduleActivityOfferingResult", propOrder = {
        "activiyOfferingId", "activiyOfferingTypeShortName", "activiyOfferingType", "scheduleComponents"})
public class StudentScheduleActivityOfferingResult {
    private String activiyOfferingId;
    private String activiyOfferingTypeShortName;
    private String activiyOfferingType;
    private List<ActivityOfferingScheduleComponentResult> scheduleComponents;

    public String getActiviyOfferingId() {
        return activiyOfferingId;
    }

    public void setActiviyOfferingId(String activiyOfferingId) {
        this.activiyOfferingId = activiyOfferingId;
    }

    public String getActiviyOfferingTypeShortName() {
        return activiyOfferingTypeShortName;
    }

    public void setActiviyOfferingTypeShortName(String activiyOfferingTypeShortName) {
        this.activiyOfferingTypeShortName = activiyOfferingTypeShortName;
    }

    public String getActiviyOfferingType() {
        return activiyOfferingType;
    }

    public void setActiviyOfferingType(String activiyOfferingType) {
        this.activiyOfferingType = activiyOfferingType;
    }

    public List<ActivityOfferingScheduleComponentResult> getScheduleComponents() {
        return scheduleComponents;
    }

    public void setScheduleComponents(List<ActivityOfferingScheduleComponentResult> scheduleComponents) {
        this.scheduleComponents = scheduleComponents;
    }
}
