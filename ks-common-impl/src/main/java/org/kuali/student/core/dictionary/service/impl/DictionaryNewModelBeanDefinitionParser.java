package org.kuali.student.core.dictionary.service.impl;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class DictionaryNewModelBeanDefinitionParser extends AbstractSingleBeanDefinitionParser{

    @Override
    protected Class<?> getBeanClass(Element element) {
    	return null;
    }
    
    @Override
    protected void doParse(Element element, ParserContext pc, BeanDefinitionBuilder builder) {
    	
    }
	
}
