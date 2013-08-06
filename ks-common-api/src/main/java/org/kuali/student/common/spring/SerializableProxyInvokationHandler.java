/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.spring;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.springframework.util.ReflectionUtils;

/**
 * 
 * A Serializable Proxy Invokation Handler.  This handler is serializable and knows how to look up the service using the GlobalResourceLocator.
 * 
 * @author Kuali Student Team
 * 
 */
public class SerializableProxyInvokationHandler implements InvocationHandler, Externalizable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient Object serviceDelegate;

	private QName serviceName;

	/**
	 * 
	 */
	public SerializableProxyInvokationHandler() {
	}

	
	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(QName serviceName) {
		this.serviceName = serviceName;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		if (serviceDelegate == null) {
			// first time or just after reserialization.
			try {
	            serviceDelegate = GlobalResourceLoader.getService(serviceName);
            } catch (Exception e) {
            	
            	if (method.getName().equals("toString")) {
            		// fake toString
            		// spring has an assertion that expects this to work so we will just fake it
            		// once the delegate is resolved the normal toString method will be used.
            		return getClass().getName() + " (serviceName=" + serviceName + ")";
            	}
            	else
            		throw e;
            }
		}

		// delegate the call to the actual service to fulfill.
		return ReflectionUtils.invokeMethod(method, serviceDelegate, args);
	}

	/* (non-Javadoc)
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		
		QName name = new QName(serviceName.getNamespaceURI(), serviceName.getLocalPart());
		
		out.writeObject(name);
		
	}

	/* (non-Javadoc)
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		
		QName name = (QName) in.readObject();
	
		this.serviceName = new QName (name.getNamespaceURI(), name.getLocalPart());
		
	}
	
	

}
