package org.kuali.student.enrollment.class1.lpr.service.aspect;


import java.util.List;


import org.aspectj.lang.JoinPoint;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * 
 * @author sambit
 * 
 * @param <T> the service this aspect is operating on
 */
public class ServiceAspects<T>  {

	List<Throwable> includeThrowableClassList ;


	public void setIncludeThrowableClassList(List <Throwable> serviceExceptionTypes) {
		this.includeThrowableClassList = serviceExceptionTypes;
	}

	/**
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
	 * 
	 * @param ex
	 * @return
	 * @throws Throwable
	 */
	public void handleExceptions(Throwable ex) throws OperationFailedException{

		if (!includeThrowableClassList.contains(ex)) {
			throw new OperationFailedException(ex.getMessage(), ex);
		}
	}

}

