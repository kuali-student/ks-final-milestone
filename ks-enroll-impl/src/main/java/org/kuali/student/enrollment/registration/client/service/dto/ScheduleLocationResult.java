package org.kuali.student.enrollment.registration.client.service.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingScheduleResult", propOrder = {
        "building", "room"})
public class ScheduleLocationResult {
    private String building;
    private String room;

    public String getBuilding() { return building; }

    public void setBuilding(String building) { this.building = building; }

    public String getRroom() { return room; }

    public void setRoom(String room) {
        this.room = room;
    }
}
