package org.kuali.student.enrollment.registration.engine.node.impl;

import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.enrollment.registration.engine.node.AbstractCourseRegistrationNode;

public class CourseRegistrationVerifyRegRequestNode extends AbstractCourseRegistrationNode<RegistrationRequestEngineMessage, RegistrationRequestEngineMessage> {
    @Override
    public RegistrationRequestEngineMessage process(RegistrationRequestEngineMessage message) {
        return message;
    }
}
