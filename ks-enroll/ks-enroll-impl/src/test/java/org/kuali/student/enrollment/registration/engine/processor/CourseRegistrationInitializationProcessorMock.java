package org.kuali.student.enrollment.registration.engine.processor;

import org.kuali.student.enrollment.registration.engine.CourseRegistrationEngineIT;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.Map;

/**
 * Created by Daniel on 9/25/14.
 */
public class CourseRegistrationInitializationProcessorMock extends CourseRegistrationInitializationProcessor {
    @Override
    public RegistrationRequestEngineMessage process(Map message) {

        RegistrationRequestEngineMessage requestEngineMessage = super.process(message);
        ContextInfo contextInfo = requestEngineMessage.getContextInfo();

        try {
            for (String exception : CourseRegistrationEngineIT.EXCEPTIONS) {
                if (message.get(exception)!=null && (Boolean)message.get(exception)) {
                    contextInfo.getAttributes().add(new AttributeInfo(exception, CourseRegistrationEngineIT.TRUE));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing request", e);
        }

        requestEngineMessage.setContextInfo(contextInfo);

        return requestEngineMessage;

    }
}
