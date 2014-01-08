package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentScheduleActivityOfferingResult", propOrder = {
        "activiyOfferingTypeShortName", "scheduleComponents"})
public class StudentScheduleActivityOfferingResult {
    private String activiyOfferingTypeShortName;
    private List<ActivityOfferingScheduleComponentResult> scheduleComponents;

    public String getActiviyOfferingTypeShortName() {
        return activiyOfferingTypeShortName;
    }

    public void setActiviyOfferingTypeShortName(String activiyOfferingTypeShortName) {
        this.activiyOfferingTypeShortName = activiyOfferingTypeShortName;
    }

    public List<ActivityOfferingScheduleComponentResult> getScheduleComponents() {
        return scheduleComponents;
    }

    public void setScheduleComponents(List<ActivityOfferingScheduleComponentResult> scheduleComponents) {
        this.scheduleComponents = scheduleComponents;
    }
}
