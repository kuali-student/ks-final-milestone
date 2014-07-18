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
@XmlType(name = "RegistrationCountResult", propOrder = {
        "luiId", "count", "countType"})
public class RegistrationCountResult {

    private String luiId; // This is often needed to map resulst back to objects
    private int count;
    private String countType;

    public RegistrationCountResult(){};

    public RegistrationCountResult(String luiId, int count, String countType) {
        this.luiId = luiId;
        this.count = count;
        this.countType = countType;
    }

    public String getLuiId() {
        return luiId;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }
}
