package org.kuali.student.enrollment.registration.engine.dto;

import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;

import java.io.Serializable;
import java.util.Map;
import org.kuali.student.r2.common.dto.ContextInfo;

public class RegistrationRequestEngineMessage implements Serializable {
    private RegistrationRequest registrationRequest;
    private ContextInfo contextInfo;
    private boolean stopProcessing = false;
    private Map<String, RegistrationGroup> registrationGroupMap;

    public RegistrationRequestEngineMessage(RegistrationRequest registrationRequest, Map<String, RegistrationGroup> registrationGroupMap, ContextInfo contextInfo) {
        this.registrationRequest = registrationRequest;
        this.registrationGroupMap = registrationGroupMap;
        this.contextInfo = contextInfo;
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

    public boolean isStopProcessing() {
        return stopProcessing;
    }

    public void setStopProcessing(boolean stopProcessing) {
        this.stopProcessing = stopProcessing;
    }

    public ContextInfo getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(ContextInfo contextInfo) {
        this.contextInfo = contextInfo;
    }
    
    
}
