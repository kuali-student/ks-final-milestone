package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by swedev on 2/11/14.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationResponseItemResult", propOrder = {
        "registrationRequestId", "registrationRequestItemId","state", "status",
        "resultingLprId","message", "newLuiId"})
public class RegistrationResponseItemResult {
    String state;  // current state of the lprTransaction
    String status; // human readable message, typically based on the state
    String registrationRequestId;
    String registrationRequestItemId;

    String resultingLprId;
    String message;
    String newLuiId;


    public RegistrationResponseItemResult() {}


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegistrationRequestId() {
        return registrationRequestId;
    }

    public void setRegistrationRequestId(String registrationRequestId) {
        this.registrationRequestId = registrationRequestId;
    }

    public String getRegistrationRequestItemId() {
        return registrationRequestItemId;
    }

    public void setRegistrationRequestItemId(String registrationRequestItemId) {
        this.registrationRequestItemId = registrationRequestItemId;
    }

    public String getResultingLprId() {
        return resultingLprId;
    }

    public void setResultingLprId(String resultingLprId) {
        this.resultingLprId = resultingLprId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNewLuiId() {
        return newLuiId;
    }

    public void setNewLuiId(String newLuiId) {
        this.newLuiId = newLuiId;
    }
}
