package org.kuali.student.enrollment.registration.client.service.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class stores simple registration counts.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WaitlistPositionResult", propOrder = {
        "luiId", "position", "countType"})
public class WaitlistPositionResult {

    private String luiId; // This is often needed to map resulst back to objects
    private int position;
    private String countType;

    public WaitlistPositionResult(){};

    public WaitlistPositionResult(String luiId, int position, String countType) {
        this.luiId = luiId;
        this.position = position;
        this.countType = countType;
    }

    public String getLuiId() {
        return luiId;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }
}
