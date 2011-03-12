package org.kuali.student.enrollment.lpr.service.aspect;

import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;


public class ServiceAspectLayering {

	List <String>  serviceImpls ; 

	public void setServiceImpls(List<String> serviceImpls) {
		this.serviceImpls = serviceImpls;
	}
	
	/**
	 * Aspect method, this is the controller which invokes other impl method using reflection. 
	 * One of this method could be defined per web service operation or there could just be one generic method for the service if the 
	 * @param call
	 * @throws Throwable
	 */
	public void performLayeringCalls(ProceedingJoinPoint call) throws Throwable{

		try{
			call.proceed();
			for ( String serviceName:serviceImpls){
				Class<?> classC = Class.forName(serviceName);
				Object obj = classC.newInstance();
				for (Method s :classC.getMethods()){
					if(s.getName().equals(call.getSignature().getName())){
						s.invoke(obj, call.getArgs());
					}
				}
			}
		}

		catch(Exception ex){
			ex.printStackTrace();
		}

	}

}