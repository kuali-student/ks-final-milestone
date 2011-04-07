package org.kuali.student.enrollment.lpr.service.aspect;

import java.lang.reflect.Method;
import java.util.List;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;

/**
 * 
 * @author sambit
 * 
 * @param <T> the service this aspect is operating on
 */
public class ServiceAspects<T>  {

	List <T>  serviceEntryDecorators; 

	List<Throwable> includeThrowableClassList ;

	public void setServiceEntryDecorators(List<T> serviceImplObjs) {
		this.serviceEntryDecorators = serviceImplObjs;
	}
	public void setIncludeThrowableClassList(List <Throwable> serviceExceptionTypes) {
		this.includeThrowableClassList = serviceExceptionTypes;
	}

	/**
	 * 
	 * @param join
	 */
	public void beforeInvokingService(JoinPoint join) throws Throwable{

		Object[] argValues =  join.getArgs();

		for(Object argValue:argValues){

			if(argValue==null){
			
				throw new MissingParameterException("Input parameters cannot be null, one of the parameters had null value:"+argValue);
				
			}
		}
	}
	/**
	 * Aspect method, this is the controller which routes the call to the top decorator 
	 *  
	 * @param call
	 * @throws Throwable
	 */
	public Object invokeFirstDecorator(ProceedingJoinPoint join) throws Throwable{
		Object retVal = null;

		for (Object decorator : serviceEntryDecorators){
			if	(join.getSignature().getDeclaringType().isAssignableFrom(decorator.getClass())) {
				for (Method m : decorator.getClass().getMethods()){
					if (m.getName().equals(join.getSignature().getName())) {
						retVal =	m.invoke(decorator, join.getArgs());
						break;
					}
				}
			}

		}
		return retVal;
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

