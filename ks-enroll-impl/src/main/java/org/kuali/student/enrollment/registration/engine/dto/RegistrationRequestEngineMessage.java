package org.kuali.student.enrollment.registration.engine.dto;

import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;

import java.io.Serializable;
import java.util.Map;

public class RegistrationRequestEngineMessage implements Serializable {
    private RegistrationRequest registrationRequest;
    private Map<String, RegistrationGroup> registrationGroupMap;

    public RegistrationRequestEngineMessage(RegistrationRequest registrationRequest, Map<String, RegistrationGroup> registrationGroupMap) {
        this.registrationRequest = registrationRequest;
        this.registrationGroupMap = registrationGroupMap;
    }

    public RegistrationRequest getRegistrationRequest() {
        return registrationRequest;
    }

    public void setRegistrationRequest(RegistrationRequest registrationRequest) {
        this.registrationRequest = registrationRequest;
    }

    public Map<String, RegistrationGroup> getRegistrationGroupMap() {
        return registrationGroupMap;
    }

    public void setRegistrationGroupMap(Map<String, RegistrationGroup> registrationGroupMap) {
        this.registrationGroupMap = registrationGroupMap;
    }
}
