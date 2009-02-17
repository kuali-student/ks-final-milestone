package org.kuali.student.common.test.mock;

import java.util.Map;

import org.springframework.beans.factory.FactoryBean;

public class MockProxyFactoryBean implements FactoryBean {
	private Class<Object> interfaceClass;
	private Map<String,Object> methodReturnMap;

	@Override
	public Object getObject() throws Exception {
		return MockProxy.newInstance(methodReturnMap, interfaceClass);
	}

	@Override
	public Class<Object> getObjectType() {
		return interfaceClass;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	/**
	 * @return the interfaceClass
	 */
	public Class<Object> getInterfaceClass() {
		return interfaceClass;
	}

	/**
	 * @param interfaceClass the interfaceClass to set
	 */
	public void setInterfaceClass(Class<Object> interfaceClass) {
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
