package org.kuali.student.enrollment.lpr.service.impl;
import java.util.ArrayList;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.springframework.stereotype.Component;


@Component
public class LuiPersonRelationServiceAuthorizationImpl {

	public void  createBulkRelationshipsForPersonAuthorization(ProceedingJoinPoint call) throws Throwable {
		if(true){
			System.out.println ("PersonId needed");
		}
		call.proceed();
	

	}

}
