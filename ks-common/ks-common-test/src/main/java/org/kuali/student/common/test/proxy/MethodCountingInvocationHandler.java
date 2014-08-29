/*
 * Copyright 2014 Kuali Foundation Licensed under the
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
package org.kuali.student.common.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.kuali.student.common.object.KSClassUtils;
import org.kuali.student.common.spring.AnnotationUtils;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;

/**
 * An invocation handler for creating JDK proxies that count the number of times a method is called.
 * 
 * @author ocleirig
 *
 */
public class MethodCountingInvocationHandler implements InvocationHandler {

	private Map<String, AtomicInteger> methodCountMap = new HashMap<String, AtomicInteger>();
	private Object base;

	/**
	 * @param base The object being proxied.  Calls to us are applied to the base object instead.
	 * 
	 */
	public MethodCountingInvocationHandler(Object base) {
		this.base = base;
	}
	
	

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		AtomicInteger counter = methodCountMap.get(method.getName());

		if (counter == null) {
			counter = new AtomicInteger(0);
			this.methodCountMap.put(method.getName(), counter);
		}

		counter.addAndGet(1);

		/*
		 * With out this section we get UndeclaredThrowableException's when using KSCachingUtils for singular get's that have a cache-miss.
		 * 
		 * We just detect that scenario here and reach into the exception being thrown to pull out and rethrow the DoesNotExistException cause.
		 * 
		 */
		Class returnClass = method.getReturnType();
		
		if (!KSClassUtils.targetIsInstanceOf(returnClass, Collection.class)) {
			// not returning a collection
			if (!KSClassUtils.targetIsInstanceOf(returnClass, Void.class)) {
				// not void means a singular get
				
				try {
					return method.invoke(base, args);
				}
				catch (InvocationTargetException e) {
					// find if it is a does not exist exception
					Throwable dne = KSClassUtils.extractInstanceOf(e, DoesNotExistException.class);
					
					if (dne != null)
						throw (DoesNotExistException)dne;
					else
						throw e;
				}
				
			}
			
		}
		
		return method.invoke(base, args);
		
		

	}
	
	/**
	 * Clear the method invocation counts.
	 * 
	 * Typically this would be done after a test case runs so that the counts are accurate within each unit test scope.
	 * 
	 */
	public void reset() {
		this.methodCountMap.clear();
	}
	
	/**
	 * Get the number of times the method was called.  This aggregates methods with the same name but different arguments into the same count.
	 * 
	 * This counts the number of times the method on the proxy was called.
	 * 
	 * For KS Service layering there may be lateral method calls at a lower point in the service stack and those are not represented in these counts.
	 * 
	 * @param methodName
	 * @return
	 */
	public int getMethodCount (String methodName) {
		
		AtomicInteger c = this.methodCountMap.get(methodName);
		
		if (c == null)
			return 0;
		else {
			return c.intValue();
		}
	}

}
