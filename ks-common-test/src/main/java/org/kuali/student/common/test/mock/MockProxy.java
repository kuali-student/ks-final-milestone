/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.test.mock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Epstein
 *
 */
public class MockProxy implements InvocationHandler {
    private Map<String,Object> methodReturnMap;
    
    /**
     * Creates a new instance of an object that implements the interface you pass it.
     * The Map contains a map of method names to return values.
     * If a method is not in the map, then invoking that method will return null
     * Example:
     * 	 Map<String,Object> methodReturnMap = new HashMap<String,Object>();
     * 	 MyInterface myObject = MockProxy.newInstance(map, MyInterface.class);
     *   map.put("foo", "NEW RETRUN VALUE");
     *   System.out.println(myObject.foo()); // Outputs "NEW RETRUN VALUE"
     * 
     * @param <T>
     * @param methodReturnMap
     * @param interfaceClass
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
	public static <T> T newInstance(Map<String,Object> methodReturnMap, Class<T> interfaceClass) throws InstantiationException, IllegalAccessException {
    	return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] { interfaceClass }, new MockProxy(methodReturnMap));
    }

    private MockProxy(Map<String,Object> methodReturnMap) {
    	super();
    	this.methodReturnMap = methodReturnMap;
    	//Create the map if it does not exist
    	if(this.methodReturnMap==null){
    		this.methodReturnMap = new HashMap<String, Object>();
    	}
    }
	
	public MockProxy() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = methodReturnMap.get(method.getName());
		
		if (result instanceof MockArgumentMapper){
		    result = ((MockArgumentMapper)result).getReturnValue(args);
		}
		
		return result;
	}

}
