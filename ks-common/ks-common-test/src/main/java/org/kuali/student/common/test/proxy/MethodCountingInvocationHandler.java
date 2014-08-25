/**
 * 
 */
package org.kuali.student.common.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ocleirig
 *
 */
public class MethodCountingInvocationHandler implements InvocationHandler {

	private Map<String, AtomicInteger> methodCountMap = new HashMap<String, AtomicInteger>();
	private Object base;

	/**
	 * @param base 
	 * 
	 */
	public MethodCountingInvocationHandler(Object base) {
		this.base = base;
		// TODO Auto-generated constructor stub
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

		return method.invoke(base, args);

	}
	
	public int getMethodCount (String methodName) {
		
		AtomicInteger c = this.methodCountMap.get(methodName);
		
		if (c == null)
			return 0;
		else {
			return c.intValue();
		}
	}

}
