package org.kuali.student.enrollment.registration.engine.dto;

import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;

import java.io.Serializable;

public class RegistrationRequestItemEngineMessage implements Serializable {
    private RegistrationRequestItem requestItem;
    private RegistrationGroup registrationGroup;

    public RegistrationRequestItemEngineMessage(RegistrationRequestItem requestItem, RegistrationGroup registrationGroup) {
        this.requestItem = requestItem;
        this.registrationGroup = registrationGroup;
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
}
