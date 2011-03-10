package org.kuali.student.enrollment.lpr.service.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;


@Component
public class LuiPersonRelationServiceAuthorizationImpl {

    public void createBulkRelationshipsForPersonAuthorization(ProceedingJoinPoint call) throws Throwable {
        if (true) {
            System.out.println("PersonId needed");
        }
        call.proceed();


    }

}
