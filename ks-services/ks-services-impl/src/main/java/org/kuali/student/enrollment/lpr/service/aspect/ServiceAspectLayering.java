package org.kuali.student.enrollment.lpr.service.aspect;

import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.kuali.student.enrollment.lpr.service.impl.LuiPersonRelationServiceAuthorizationImpl;

public class ServiceAspectLayering {

	List  serviceImpls ; 
	LuiPersonRelationServiceAuthorizationImpl authImpl;
	
	public LuiPersonRelationServiceAuthorizationImpl getAuthImpl() {
		return authImpl;
	}
	public void setAuthImpl(LuiPersonRelationServiceAuthorizationImpl authImpl) {
		this.authImpl = authImpl;
	}
	public void performLayeringCalls(ProceedingJoinPoint call) throws Throwable{

		try{
			call.proceed();
			//authImpl.createBulkRelationshipsForPerson("123", null, null, null, null, null);
			Class classC = Class.forName("org.kuali.student.enrollment.lpr.service.impl.LuiPersonRelationServiceAuthorizationImpl");
			Object obj = classC.newInstance();
			for (Method s :classC.getMethods()){
				System.out.println(s);
				if(s.getName().equals(call.getSignature().getName())){
					s.invoke(obj, call.getArgs());
				}
			}
			
//			Method m = classC.getMethod(call.getSignature().getName());
//			m.invoke(obj, call.getArgs());
//			
		}
		
		catch(Exception ex){
			ex.printStackTrace();
		}


	}


}
