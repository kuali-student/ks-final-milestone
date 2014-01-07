package org.kuali.student.enrollment.registration.engine.service.impl;

import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.registration.engine.service.RegistrationProcessService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swedev on 12/20/13.
 */
public class CourseRegistrationInitilizationServiceImpl implements RegistrationProcessService {

    public RegistrationResponseInfo process(String courseRegistrationRequestId){
        RegistrationResponseInfo responseInfo = new RegistrationResponseInfo();

        responseInfo.setRegistrationRequestId(courseRegistrationRequestId);
        List<String> userMessages = new ArrayList<String>();

        responseInfo.getMessages().add("Request Processing");

        return responseInfo;
    }


}
