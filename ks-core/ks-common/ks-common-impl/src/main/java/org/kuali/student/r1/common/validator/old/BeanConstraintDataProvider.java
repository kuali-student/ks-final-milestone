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

package org.kuali.student.r1.common.validator.old;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

@Deprecated
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

        //Code Changed for JIRA-8997 - SONAR Critical issues - Performance - Inefficient use of keySet iterator instead of entrySet iterator
		for(Map.Entry<String, PropertyDescriptor> entry: beanInfo.entrySet()) {
            String propName = entry.getKey();
            PropertyDescriptor pd = entry.getValue();
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
		return (dataMap.containsKey("id") && null != dataMap.get("id")) ? dataMap.get("id").toString() : null;
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
