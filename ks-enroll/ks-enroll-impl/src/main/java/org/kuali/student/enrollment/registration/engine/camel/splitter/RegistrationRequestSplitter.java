package org.kuali.student.enrollment.registration.engine.camel.splitter;

import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestItemEngineMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swedev on 3/20/14.
 */
public class RegistrationRequestSplitter {
    public List<RegistrationRequestItemEngineMessage> split(RegistrationRequestEngineMessage message) {
        List<RegistrationRequestItemEngineMessage> outputMessageList = new ArrayList<RegistrationRequestItemEngineMessage>();
        for (RegistrationRequestItem registrationRequestItem : message.getRegistrationRequest().getRegistrationRequestItems()) {
            outputMessageList.add(new RegistrationRequestItemEngineMessage(registrationRequestItem, 
                    message.getRegistrationGroupMap().get(registrationRequestItem.getRegistrationGroupId()),
                    message.getContextInfo().getPrincipalId(),
                    message.getContextInfo()));
        }
        return outputMessageList;
    }
}
