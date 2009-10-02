package org.kuali.student.common.validator;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

public class BeanConstraintDataProvider implements ConstraintDataProvider {

	Map<String, Object> dataMap = null;

	/*
	 * The only place where the dataprovider should get initialized is
	 * BeanConstraintSetupFactory
	 */
	protected BeanConstraintDataProvider() {

	}
	//TODO fix it later. 
    public String getPath(){
        return "";
    }
	@Override
	public void initialize(Object o) {

		dataMap = new HashMap<String, Object>();
		
		Map<String, PropertyDescriptor> beanInfo = getBeanInfo(o.getClass());

		for (String propName : beanInfo.keySet()) {
			PropertyDescriptor pd = beanInfo.get(propName);
			Object value = null;
			try {
				value = pd.getReadMethod().invoke(o);
			} catch (Exception e) {
				// TODO: Should not be ignoring exception
			}

			dataMap.put(propName, value);
		}
	}

	@Override
	public String getObjectId() {
		return (dataMap.containsKey("id")) ? dataMap.get("id").toString() : null;
	}

	@Override
	public Object getValue(String fieldKey) {
		return dataMap.get(fieldKey);
	}

	@Override
	public Boolean hasField(String fieldKey) {
		return dataMap.containsKey(fieldKey);
	}

	private Map<String, PropertyDescriptor> getBeanInfo(Class<?> clazz) {
		Map<String, PropertyDescriptor> properties = new HashMap<String, PropertyDescriptor>();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			properties.put(propertyDescriptor.getName(), propertyDescriptor);
		}
		return properties;
	}
}
