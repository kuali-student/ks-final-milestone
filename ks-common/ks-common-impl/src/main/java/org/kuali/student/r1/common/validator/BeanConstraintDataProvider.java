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

package org.kuali.student.r1.common.validator;

import org.apache.log4j.Logger;
import org.kuali.student.r2.common.dto.AttributeInfo;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanConstraintDataProvider implements ConstraintDataProvider {
    final static Logger LOG = Logger.getLogger(BeanConstraintDataProvider.class);
    
    private static final String DYNAMIC_ATTRIBUTE = "attributes";
    private static final String PARENT = "parent";
    
	Map<String, Object> dataMap = null;

	/*
	 * The only place where the dataprovider should get initialized is
	 * BeanConstraintSetupFactory
	 */
	public BeanConstraintDataProvider() {

	}
	
	//TODO fix it later. 
    public String getPath(){
        return "";
    }

    @SuppressWarnings("unchecked")
    @Override
	public void initialize(Object o) {

		dataMap = new HashMap<String, Object>();
		
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(o.getClass());
		
			for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
				Object value = null;
				try {
					value = pd.getReadMethod().invoke(o);
				} catch (Exception e) {
					LOG.warn("Method invokation failed",e);
				}
	
	            // Extract dynamic attributes
	            if(DYNAMIC_ATTRIBUTE.equals(pd.getName())) {
                    if (o.getClass().getPackage().getName().contains(".r1.")){
	                dataMap.putAll((Map<String, String>)value);
                    }

                    if (o.getClass().getPackage().getName().contains(".r2."))
                    {

                        List<AttributeInfo> attToMap = (List<AttributeInfo>)value;
                        HashMap<String,String > getAllKEysOnR2 = new HashMap<String, String>();
                        if (attToMap != null){
                        for ( AttributeInfo atin : attToMap ){

                            getAllKEysOnR2.put(atin.getKey(),atin.getValue());
                        }
                        dataMap.putAll(getAllKEysOnR2);
                        }
                    }

	            } else {
					dataMap.put(pd.getName(), value);
	            }
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
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

    @Override
    public void setParent(ConstraintDataProvider parentDataProvider) {
        if (dataMap == null){
            return;
        }

        dataMap.put(PARENT, parentDataProvider);
    }

    @Override
    public ConstraintDataProvider getParent() {
        if (dataMap == null){
            return null;
        }
        return (ConstraintDataProvider) dataMap.get(PARENT);
    }

}
