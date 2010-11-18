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

package org.kuali.student.common.test.mock;

import java.util.Map;

import org.springframework.beans.factory.FactoryBean;

public class MockProxyFactoryBean implements FactoryBean {
	private Class<?> interfaceClass;
	private Map<String,Object> methodReturnMap;

	@Override
	public Object getObject() throws Exception {
		return MockProxy.newInstance(methodReturnMap, interfaceClass);
	}

	@Override
	public Class<?> getObjectType() {
		return interfaceClass;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	/**
	 * @return the interfaceClass
	 */
	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}

	/**
	 * @param interfaceClass the interfaceClass to set
	 */
	public void setInterfaceClass(Class<?> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	/**
	 * @return the methodReturnMap
	 */
	public Map<String, Object> getMethodReturnMap() {
		return methodReturnMap;
	}

	/**
	 * @param methodReturnMap the methodReturnMap to set
	 */
	public void setMethodReturnMap(Map<String, Object> methodReturnMap) {
		this.methodReturnMap = methodReturnMap;
	}

}
