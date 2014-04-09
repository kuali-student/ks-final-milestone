package org.kuali.student.enrollment.registration.engine.node.impl;

import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestItemEngineMessage;
import org.kuali.student.enrollment.registration.engine.node.AbstractCourseRegistrationSplitNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Splits the registration request into RegistrationRequestItems
 */
public class CourseRegistrationSplitRequestItemsNode extends AbstractCourseRegistrationSplitNode<RegistrationRequestEngineMessage, RegistrationRequestItemEngineMessage> {

    @Override
    public List<RegistrationRequestItemEngineMessage> split(RegistrationRequestEngineMessage message) {
        List<RegistrationRequestItemEngineMessage> outputMessageList = new ArrayList<RegistrationRequestItemEngineMessage>();
        for (RegistrationRequestItem registrationRequestItem : message.getRegistrationRequest().getRegistrationRequestItems()) {
            outputMessageList.add(new RegistrationRequestItemEngineMessage(registrationRequestItem, message.getRegistrationGroupMap().get(registrationRequestItem.getRegistrationGroupId())));
        }
        return outputMessageList;
    }

    /**
     * Resolves based on Format Offering Id of the output message
     */
    @Override
    protected String resolveGroupId(RegistrationRequestItemEngineMessage inputMessage, RegistrationRequestItemEngineMessage outputMessage) {
        return "FOID=" + outputMessage.getRegistrationGroup().getFormatOfferingId();
    }

}
