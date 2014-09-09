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
package org.kuali.student.common.test.spring;

import java.lang.reflect.Proxy;

import org.kuali.student.common.test.proxy.MethodCountingInvocationHandler;
import org.springframework.beans.factory.FactoryBean;

/**
 * A factory bean that will wrap a MethodCountingInvocationHandler jdk proxy around the target class.
 * 
 * This is intended to be used to test caching decorators.
 * 
 * @author Kuali Student Team (ks.collab@kuali.org)
 *
 */
public class MethodCountingInvocationHandlerFactoryBean<T> implements
		FactoryBean<T> {

	private T proxiedObject;
	
	/**
	 * 
	 */
	public MethodCountingInvocationHandlerFactoryBean() {

	}
	
	

	public void setProxiedObject(T proxiedObject) {
		this.proxiedObject = proxiedObject;
	}

	@Override
	public T getObject() throws Exception {
		
		MethodCountingInvocationHandler handler = new MethodCountingInvocationHandler(proxiedObject);
		
		T proxy = (T) Proxy.newProxyInstance(getClass().getClassLoader(), proxiedObject.getClass().getInterfaces(), handler);
		
		return proxy;
	}

	@Override
	public Class<?> getObjectType() {
		// object type comes from the type of the proxied object
		return getServiceInterface();
	}

	private Class<?> getServiceInterface() {
		
		if (proxiedObject == null)
			return null;
		
		for (Class<?>candidateInterface : proxiedObject.getClass().getInterfaces()) {
			
			if (candidateInterface.getName().contains("Service"))
				return candidateInterface;
		}
		
		throw new IllegalArgumentException("proxied object does not implement any interface containing 'Service'");
	}



	@Override
	public boolean isSingleton() {
		return true;
	}
	
	

}
