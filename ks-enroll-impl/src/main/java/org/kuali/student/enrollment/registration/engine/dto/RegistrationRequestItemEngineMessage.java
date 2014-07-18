package org.kuali.student.enrollment.registration.engine.dto;

import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.io.Serializable;

public class RegistrationRequestItemEngineMessage implements Serializable {
    private RegistrationRequestItem requestItem;
    private RegistrationGroup registrationGroup;
    private ContextInfo contextInfo;

    public RegistrationRequestItemEngineMessage(RegistrationRequestItem requestItem, RegistrationGroup registrationGroup, ContextInfo contextInfo) {
        this.requestItem = requestItem;
        this.registrationGroup = registrationGroup;
        this.contextInfo = contextInfo;
    }

    public RegistrationRequestItem getRequestItem() {
        return requestItem;
    }

    public void setRequestItem(RegistrationRequestItem requestItem) {
        this.requestItem = requestItem;
    }

    public RegistrationGroup getRegistrationGroup() {
        return registrationGroup;
    }

    public void setRegistrationGroup(RegistrationGroup registrationGroup) {
        this.registrationGroup = registrationGroup;
    }

    public ContextInfo getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(ContextInfo contextInfo) {
        this.contextInfo = contextInfo;
    }
}
