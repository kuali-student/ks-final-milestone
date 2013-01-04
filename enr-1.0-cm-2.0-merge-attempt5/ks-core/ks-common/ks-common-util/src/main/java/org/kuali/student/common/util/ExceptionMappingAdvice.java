/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.core.Ordered;

/**
 * @author Daniel Epstein
 *         <p>
 *         Use this Advice to map one exception to another for use when other
 *         advice eats your runtime exceptions outside of your code. This happens in
 *         Transactions when commit is not called until outside of your DAO
 *         layer.
 *         </p>
 * 
 * <p>
 * Set the property "exceptionMapping" as a map that maps an exception class to
 * your own exception class
 * </p>
 * 
 * <p>
 * Remember that aspect order is important and that this bean will always be
 * order "500"
 * </p>
 * 
 * Example:
 * 
 * <pre>
 * &lt;tx:annotation-driven transaction-manager=&quot;JtaTxManager&quot; order=&quot;1000&quot;/&gt;
 * lt;bean id=&quot;mapExceptionAdvisor&quot;
 * class=&quot;org.myfoo.ExceptionMappingAdvice&quot;&gt;
 * &lt;property name=&quot;exceptionMapping&quot;&gt;
 * 	&lt;map&gt;
 * 		&lt;entry key=&quot;javax.persistence.EntityExistsException&quot;
 * 			value=&quot;org.myfoo.exceptions.AlreadyExistsException&quot; /&gt;
 * 	&lt;/map&gt;
 * &lt;/property&gt;
 * lt;/bean&gt;
 * lt;aop:config&gt;
 * &lt;aop:aspect id=&quot;dataAccessToBusinessException&quot;
 * 	ref=&quot;mapExceptionAdvisor&quot;&gt;
 * 	&lt;aop:after-throwing
 * 		pointcut=&quot;execution(* org.myfoo.service.*.*(..))&quot;
 * 		method=&quot;afterThrowing&quot; throwing=&quot;ex&quot; /&gt;
 * &lt;/aop:aspect&gt;
 * lt;/aop:config&gt;
 * </pre>
 */
public class ExceptionMappingAdvice implements ThrowsAdvice, Ordered {
	private int order = 500;
	private static final long serialVersionUID = 1L;
	private Map<Class<? extends Exception>, Class<? extends Exception>> exceptionMapping;
	private Class<? extends Exception> defaultException;
	final Logger logger = Logger.getLogger(ExceptionMappingAdvice.class);

	/**
	 * This method will use the real exception thrown and look up the exception
	 * that should be thrown
	 * 
	 * @param ex
	 * @throws Exception
	 */
	public void afterThrowing(Exception ex) throws Exception {
		Class<? extends Exception> mappedExceptionClass = exceptionMapping
				.get(ex.getClass());

		if (mappedExceptionClass != null) {
	        logger.debug("Mapping exception "+ex.getClass()+" to "+mappedExceptionClass);
	        Constructor<? extends Exception> c = mappedExceptionClass
					.getConstructor(String.class);
			Exception mappedException = c.newInstance(ex.getMessage());
			throw mappedException;
		}
		
		//Throw a default exception if this is a runtime exception
		if(ex instanceof RuntimeException){
			logger.trace("No mapping available, throwing default exception "+defaultException);
			if (defaultException != null) {
				//Log the error
				StringWriter traceWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(traceWriter, false);
				logger.error(printWriter, ex);
				printWriter.close();
				String faultMessage = traceWriter.getBuffer().toString();
				logger.error(faultMessage);
				//Throw the default exception
				try{
					Constructor<? extends Exception> c = defaultException
							.getConstructor(String.class, Throwable.class);
					throw c.newInstance(ex.getMessage(), ex);
				}catch(NoSuchMethodException e){
					Constructor<? extends Exception> c = defaultException
							.getConstructor(String.class);
					throw c.newInstance(ex.getMessage());
				}
			}
			//Check if no default was defined
			logger.debug("No mapping or default exception available. Exception "+ex.getClass());
			throw new RuntimeException("Could Not Map Exception: " + ex.toString());
		}
	}

	@Override
	public int getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return the exceptionMapping
	 */
	public Map<Class<? extends Exception>, Class<? extends Exception>> getExceptionMapping() {
		return exceptionMapping;
	}

	/**
	 * @param exceptionMapping
	 *            the exceptionMapping to set
	 */
	public void setExceptionMapping(
			Map<Class<? extends Exception>, Class<? extends Exception>> exceptionMapping) {
		this.exceptionMapping = exceptionMapping;
	}

	/**
	 * @return the defaultException
	 */
	public Class<? extends Exception> getDefaultException() {
		return defaultException;
	}

	/**
	 * @param defaultException
	 *            the defaultException to set
	 */
	public void setDefaultException(Class<? extends Exception> defaultException) {
		this.defaultException = defaultException;
	}

}
