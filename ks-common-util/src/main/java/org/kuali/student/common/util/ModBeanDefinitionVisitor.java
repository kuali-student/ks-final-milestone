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
