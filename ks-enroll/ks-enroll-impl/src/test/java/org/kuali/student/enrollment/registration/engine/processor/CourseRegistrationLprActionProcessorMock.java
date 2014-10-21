package org.kuali.student.enrollment.registration.engine.processor;

import org.kuali.student.enrollment.registration.engine.CourseRegistrationEngineIT;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestItemEngineMessage;
import org.kuali.student.r2.common.dto.AttributeInfo;

public class CourseRegistrationLprActionProcessorMock extends CourseRegistrationLprActionProcessor {

    @Override
    public RegistrationRequestItemEngineMessage process(RegistrationRequestItemEngineMessage message) {
        for (AttributeInfo attribute:message.getContextInfo().getAttributes()) {
            if (attribute.getKey().equals(CourseRegistrationEngineIT.RESULT_ITEM_EXCEPTION)
                    && attribute.getValue().equals(CourseRegistrationEngineIT.TRUE)) {
                throw new RuntimeException("Result Item Exception attribute found in context info");
            }
        }

        return super.process(message);
    }

}
