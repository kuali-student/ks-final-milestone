package org.kuali.student.enrollment.lpr.service.aspect;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.kuali.student.common.exceptions.OperationFailedException;






/**
 * 
 * @author sambit
 * 
 * @param <T> the service this aspect is operating on
 */
public class ServiceAspectLayering<T,P>   {

	List <T>  serviceImplObjs; 

	List<Throwable> includeThrowableClassList ;

	List <P> servicePostProcessClasses;

	public void setServicePostProcessClasses(List<P> servicePostProcessClasses) {
		this.servicePostProcessClasses = servicePostProcessClasses;
	}
	public void setServiceImpls(List<T> serviceImplObjs) {
		this.serviceImplObjs = serviceImplObjs;
	}
	public void setIncludeThrowableClassList(List <Throwable> serviceExceptionTypes) {
		this.includeThrowableClassList = serviceExceptionTypes;
	}
	/**
	 * Aspect method, this is the controller which invokes other impl method using reflection. 
	 * One of this method could be defined per service or there could just be one generic method for the services in a package. 
	 * @param call
	 * @throws Throwable
	 */
	public Object performLayeringCalls(ProceedingJoinPoint call) throws Throwable{

		for (Object service : serviceImplObjs){

			for (Method m : service.getClass().getMethods()){
				if (m.getName().equals(call.getSignature().getName())) {
					m.invoke(service, call.getArgs());
					break;
				}
			}
		}
		return call.proceed();
	}

	/**
	 * 
	 * @param ex
	 * @return
	 * @throws Throwable
	 */
	public void handleExceptions(Throwable ex) throws OperationFailedException{

		if (!includeThrowableClassList.contains(ex)) {
			throw new OperationFailedException(ex.getMessage());
		}
	}



	/**
	 * The advice used after return 
	 */

	public void afterReturning( JoinPoint call, Object returnValue) throws Throwable {

		for (Object servicePostProcessClass : servicePostProcessClasses){

			if ( servicePostProcessClass.getClass().getName().contains(call.getTarget().getClass().getName())){	

				for (Method m : servicePostProcessClass.getClass().getMethods()){
					if (m.getName().equals(call.getSignature().getName())) {
						List  <Object> argsList = new ArrayList<Object>();
						argsList.add(0,returnValue);
						argsList.add (1,call.getArgs());	
						m.invoke( servicePostProcessClass, argsList.toArray());
						break;
					}
				}
			}
		}
	}

}

