package org.kuali.student.enrollment.registration.engine.node.impl;

import org.kuali.student.enrollment.registration.engine.TestCourseRegistrationEngine;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;

import javax.jms.MapMessage;

/**
 * Initializes the registration request for further processing
 */
public class CourseRegistrationInitializationNodeMock extends CourseRegistrationInitializationNode {

    @Override
    public RegistrationRequestEngineMessage process(MapMessage message) {

        RegistrationRequestEngineMessage requestEngineMessage=super.process(message);
        ContextInfo contextInfo = requestEngineMessage.getContextInfo();

        try {
            for (String exception : TestCourseRegistrationEngine.EXCEPTIONS) {
                if (message.getBoolean(exception)) {
                    contextInfo.getAttributes().add(new AttributeInfo(exception, TestCourseRegistrationEngine.TRUE));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing request", e);
        }

        requestEngineMessage.setContextInfo(contextInfo);

        return requestEngineMessage;
    }

}
