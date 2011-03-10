package org.kuali.student.enrollment.lpr.service.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
public class LuiPersonRelationServiceValidationImpl {

    public void createBulkRelationshipsForPersonValidation(ProceedingJoinPoint call) throws Throwable {
        if (true) {
            System.out.println("Valid inputs needed");
        }
        call.proceed();


    }
}
