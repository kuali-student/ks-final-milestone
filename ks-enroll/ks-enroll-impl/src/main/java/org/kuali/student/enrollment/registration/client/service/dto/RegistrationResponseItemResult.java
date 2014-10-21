package org.kuali.student.enrollment.registration.client.service.dto;

import org.codehaus.jackson.annotate.JsonRawValue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swedev on 2/11/14.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationResponseItemResult", propOrder = {
        "registrationRequestId", "registrationRequestItemId","state", "status",
        "resultingLprId","messages", "newLuiId", "type"})
public class RegistrationResponseItemResult {
    String state;  // current state of the lprTransaction
    String status; // human readable message, typically based on the state
    String type; // we might need this information
    String registrationRequestId;
    String registrationRequestItemId;

    String resultingLprId;

    @JsonRawValue
    List<String> messages;

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

    public String getNewLuiId() {
        return newLuiId;
    }

    public void setNewLuiId(String newLuiId) {
        this.newLuiId = newLuiId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getMessages() {
        if(messages == null){
            messages = new ArrayList<String>();
        }
        return messages;
    }

    public void setMessages(List<String> messages) { this.messages = messages; }

    @Override
    public String toString() {
        return "RegistrationResponseItemResult{" +
                "state='" + state + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", registrationRequestId='" + registrationRequestId + '\'' +
                ", registrationRequestItemId='" + registrationRequestItemId + '\'' +
                ", resultingLprId='" + resultingLprId + '\'' +
                ", messages='" + messages + '\'' +
                ", newLuiId='" + newLuiId + '\'' +
                '}';
    }
}
