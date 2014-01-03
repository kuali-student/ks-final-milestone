package org.kuali.student.enrollment.registration.engine.service.impl;

import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.registration.engine.service.RegistrationProcessService;

/**
 * Created by swedev on 12/20/13.
 */
public class CourseRegistrationVerificationServiceImpl implements RegistrationProcessService {

    public RegistrationResponseInfo process(String courseRegistrationRequestId){
        RegistrationResponseInfo responseInfo = new RegistrationResponseInfo();

        responseInfo.setRegistrationRequestId(courseRegistrationRequestId);


        responseInfo.getMessages().add("Validating Request");

        return responseInfo;
    }

}
