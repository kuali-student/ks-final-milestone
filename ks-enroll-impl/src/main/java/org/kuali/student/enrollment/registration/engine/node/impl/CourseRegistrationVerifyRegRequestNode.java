package org.kuali.student.enrollment.registration.engine.node.impl;

import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.enrollment.registration.engine.node.AbstractCourseRegistrationNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CourseRegistrationVerifyRegRequestNode extends AbstractCourseRegistrationNode<RegistrationRequestEngineMessage, RegistrationRequestEngineMessage> {
    private static final Logger LOG = LoggerFactory.getLogger(CourseRegistrationVerifyRegRequestNode.class);

    @Override
    public RegistrationRequestEngineMessage process(RegistrationRequestEngineMessage message) {
        try {
            //long delay =  (System.currentTimeMillis() % 3);
            long delay =  20;
            LOG.info(delay + " second simulated delay in verify");
            Thread.sleep(delay * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        return message;
    }
}
