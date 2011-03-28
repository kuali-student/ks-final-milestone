package org.kuali.student.enrollment.lpr.service.aspect;

import java.lang.reflect.Method;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.kuali.student.core.exceptions.OperationFailedException;




/**
 * 
 * @author sambit
 * 
 * @param <T> the service this aspect is operating on
 */
public class ServiceAspectLayering<T> {

	List <T>  serviceImplObjs; 

	List<Throwable> includeThrowableClassList ;


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

}