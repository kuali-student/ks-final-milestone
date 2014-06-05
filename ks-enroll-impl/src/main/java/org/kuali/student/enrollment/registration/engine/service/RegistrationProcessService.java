package org.kuali.student.enrollment.registration.engine.service;

import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;

/**
 * Created by swedev on 12/26/13.
 */
public interface RegistrationProcessService {

    /**
     * Every registration service impl will have a default process method that is called by the
     * Registration listeners.
     *
     * @param courseRegistrationRequestId
     * @return
     */
    public RegistrationRequestInfo process(String courseRegistrationRequestId);
}
