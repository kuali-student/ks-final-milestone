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
package org.kuali.student.common.util;

import org.kuali.student.common.util.ModPropertyPlaceholderConfigurer.PlaceholderResolvingStringValueResolver;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.util.StringValueResolver;

public class ModBeanDefinitionVisitor extends BeanDefinitionVisitor {

	PlaceholderResolvingStringValueResolver valueResolver;
	
	public ModBeanDefinitionVisitor(StringValueResolver valueResolver) {
		super(valueResolver);
		this.valueResolver=(PlaceholderResolvingStringValueResolver) valueResolver;
	}
	

	@Override
	protected Object resolveValue(Object value) {
		value = super.resolveValue(value);
		String strValue = null;
		
		if(value instanceof String){
			strValue=(String)value;
		}else if(value instanceof TypedStringValue){
			strValue=((TypedStringValue)value).getValue();
		}
		
		if(strValue!=null&&strValue.startsWith("$[") && strValue.endsWith("]")){
			value = valueResolver.resolvePropertyValue(strValue.substring(2, strValue.length()-1));
		}
		
		return value;
	}

}
