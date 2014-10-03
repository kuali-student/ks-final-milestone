package org.kuali.student.enrollment.registration.engine.camel.splitter;

import org.kuali.student.enrollment.registration.engine.TestCourseRegistrationEngine;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestItemEngineMessage;
import org.kuali.student.r2.common.dto.AttributeInfo;

import java.util.List;

/**
 * Created by swedev on 3/20/14.
 */
public class RegistrationRequestSplitterMock extends RegistrationRequestSplitter {

    @Override
    public List<RegistrationRequestItemEngineMessage> split(RegistrationRequestEngineMessage message) {
        for (AttributeInfo attribute:message.getContextInfo().getAttributes()) {
            if (attribute.getKey().equals(TestCourseRegistrationEngine.RESULT_EXCEPTION)
                    && attribute.getValue().equals(TestCourseRegistrationEngine.TRUE)) {
                throw new RuntimeException("Result Exception attribute found in context info");
            }
        }

        return super.split(message);
    }
}
