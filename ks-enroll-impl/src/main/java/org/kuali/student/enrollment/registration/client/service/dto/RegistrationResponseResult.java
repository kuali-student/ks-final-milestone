package org.kuali.student.enrollment.registration.client.service.dto;

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
@XmlType(name = "RegistrationResponseResult", propOrder = {
        "registrationRequestId", "state", "status", "state", "state"})
public class RegistrationResponseResult {
    String state;  // current state of the lprTransaction
    String status; // human readable message, typically based on the state
    String registrationRequestId;
    List<RegistrationResponseItemResult> responseItemResults;


    public RegistrationResponseResult() {}


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

    public List<RegistrationResponseItemResult> getResponseItemResults() {
        if(responseItemResults == null){
            responseItemResults = new ArrayList<RegistrationResponseItemResult>();
        }
        return responseItemResults;
    }

    public void setResponseItemResults(List<RegistrationResponseItemResult> responseItemResults) {
        this.responseItemResults = responseItemResults;
    }
}
