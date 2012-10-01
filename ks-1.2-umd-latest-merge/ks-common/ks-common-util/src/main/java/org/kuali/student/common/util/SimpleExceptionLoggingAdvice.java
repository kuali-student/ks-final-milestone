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

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;

/**
 * Advice to log exceptions. 
 * Default <code>loggingLevel=STACKTRACE</code> and <code>exceptionLoggingType=THROWABLE</code>.
 * 
 * <p>Spring configuration example</p>
 * <pre>
 * &lt;aop:config&gt;
 *     &lt;aop:aspect id="exceptionLoggingAspect" ref="exceptionLoggingAdvice" order="2"&gt;
 *         &lt;aop:after-throwing
 *             pointcut="execution(* org.kuali.student.lum.lu.service.*.*(..))"
 *             method="afterThrowing" throwing="t" /&gt;
 *     &lt;/aop:aspect&gt;
 * &lt;/aop:config&gt;
 * &lt;bean id="exceptionLoggingAdvice" class="org.kuali.student.common.util.SimpleExceptionLoggingAdvice"&gt;
 *     &lt;property name="loggingLevel" value="INFO" /&gt;
 *     &lt;property name="exceptionLoggingType" value="RUNTIME" /&gt;
 * &lt;/bean&gt;
 * </pre>
 */
public class SimpleExceptionLoggingAdvice implements ThrowsAdvice {

	private enum ExceptionLevel{THROWABLE, EXCEPTION, RUNTIME};
	private ExceptionLevel exceptionType = ExceptionLevel.THROWABLE;

	private enum LoggingLevel{NONE, DEBUG, INFO, WARN, ERROR, STACKTRACE};
	private LoggingLevel loggingLevel = LoggingLevel.STACKTRACE;
	
	/**
	 * Constructor.
	 */
	public SimpleExceptionLoggingAdvice() {
	}
	
	/**
	 * Sets the logging level.
	 * <p>Logging levels:</p>
	 * <ul>
	 * <li>NONE</li>
	 * <li>DEBUG</li>
	 * <li>INFO</li>
	 * <li>WARN</li>
	 * <li>ERROR</li>
	 * <li>STACKTRACE</li>
	 * </ul>
	 * @param loggingLevel Logging level
	 */
	public void setLoggingLevel(String loggingLevel) {
		this.loggingLevel = LoggingLevel.valueOf(loggingLevel.toUpperCase());
	}

	/**
	 * Sets the type of exception to log. 
	 * E.g. Only log runtime exception (<code>RUNTIME</code>).
	 * 
	 * <p>Exception types:</p>
	 * <ul>
	 * <li>THROWABLE</li>
	 * <li>EXCEPTION</li>
	 * <li>RUNTIME</li>
	 * </ul>
	 *  
	 * @param exceptionType exception logging type
	 */
	public void setExceptionLoggingType(String exceptionType) {
		this.exceptionType = ExceptionLevel.valueOf(exceptionType.toUpperCase());
	}

	/**
	 * Catches the exception being thrown.
	 * 
	 * @param jp Aspect join point
	 * @param t Exception being thrown
	 * @throws Throwable
	 */
    public void afterThrowing(JoinPoint jp, Throwable t) throws Throwable {
    	switch(this.exceptionType) {
			case THROWABLE:
		    	if(t instanceof Throwable) {
		    		logException(jp.getTarget().getClass(), t);
		    	}		    		
			case EXCEPTION:
		    	if(t instanceof Exception) {
		    		logException(jp.getTarget().getClass(), t);
		    	}		    		
			case RUNTIME:
		    	if(t instanceof RuntimeException) {
		    		logException(jp.getTarget().getClass(), t);
		    	}		    		
		}
		throw t;
	}
    
    /**
     * Logs the exception.
     * 
     * @param targetClass The join point class the exception was caught.
     * @param t Exception being thrown.
     */
    private void logException(Class<?> targetClass, Throwable t) {
    	Logger logger = LoggerFactory.getLogger(targetClass);

    	switch(this.loggingLevel) {
			case NONE:
				break;
			case DEBUG:
		    	logger.debug(t.getMessage(), t);
				break;
			case INFO:
		    	logger.info(t.getMessage(), t);
				break;
			case WARN:
		    	logger.warn(t.getMessage(), t);
				break;
			case ERROR:
		    	logger.error(t.getMessage(), t);
				break;
			case STACKTRACE:
		    	logger.error(t.getMessage(), t);
				break;
		}
    }
}
