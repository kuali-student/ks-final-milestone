package org.kuali.student.enrollment.lpr.service.aspect;

import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;


public class ServiceAspectLayering<T> {

	List <T>  serviceImplObjs; 

	public void setServiceImpls(List<T> serviceImplObjs) {
		this.serviceImplObjs = serviceImplObjs;
	}
	
	/**
	 * Aspect method, this is the controller which invokes other impl method using reflection. 
	 * One of this method could be defined per web service operation or there could just be one generic method for the service if the 
	 * @param call
	 * @throws Throwable
	 */
	public Object performLayeringCalls(ProceedingJoinPoint call) throws Throwable{

		try{
			for (Object service : serviceImplObjs){
				for (Method s : service.getClass().getMethods()){
					if (s.getName().equals(call.getSignature().getName())) {
						s.invoke(service, call.getArgs());
						break;
					}
				}
			}
			return call.proceed();
		}

		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

}