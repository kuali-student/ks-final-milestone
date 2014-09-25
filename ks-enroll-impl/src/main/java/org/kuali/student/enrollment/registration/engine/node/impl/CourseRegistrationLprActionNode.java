package org.kuali.student.enrollment.registration.engine.node.impl;

import org.joda.time.DateTime;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.TermResolverPerformanceUtil;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestItemEngineMessage;
import org.kuali.student.enrollment.registration.engine.node.AbstractCourseRegistrationNode;
import org.kuali.student.enrollment.registration.engine.processor.CourseRegistrationLprActionProcessor;

/**
 * This class handles processing of all Lpr actions (drop/swap/update/add)
 */
public class CourseRegistrationLprActionNode extends AbstractCourseRegistrationNode<RegistrationRequestItemEngineMessage, RegistrationRequestItemEngineMessage> {

    private CourseRegistrationLprActionProcessor courseRegistrationLprActionProcessor;

    @Override
    public RegistrationRequestItemEngineMessage process(RegistrationRequestItemEngineMessage message) {
        DateTime startTime = new DateTime();

        try {
            RegistrationRequestItemEngineMessage registrationRequestItemEngineMessage =
                    courseRegistrationLprActionProcessor.process(message);

            DateTime endTime = new DateTime();
            TermResolverPerformanceUtil.putStatistics("CourseRegistrationLprActionNode", startTime, endTime);

            return registrationRequestItemEngineMessage;
        } catch (Exception e) {
            throw new RuntimeException("Error processing", e);
        }
    }

    public void setCourseRegistrationLprActionProcessor(CourseRegistrationLprActionProcessor courseRegistrationLprActionProcessor) {
        this.courseRegistrationLprActionProcessor = courseRegistrationLprActionProcessor;
    }
}
