package org.kuali.student.enrollment.lpr.service.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;


@Aspect
@Order(value=1) 
public class LuiPersonRelationServiceAuthorizationImpl {
	
    public void createBulkRelationshipsForPersonAuthorization(ProceedingJoinPoint call) throws Throwable {
        if (true) {
            System.out.println("PersonId needed");
        }
        call.proceed();


    }

}
