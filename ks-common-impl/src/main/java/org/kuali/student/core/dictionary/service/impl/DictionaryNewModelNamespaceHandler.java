package org.kuali.student.core.dictionary.service.impl;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class DictionaryNewModelNamespaceHandler extends NamespaceHandlerSupport{

	@Override
	public void init() {
        BeanDefinitionParser parser = new DictionaryNewModelBeanDefinitionParser();
        this.registerBeanDefinitionParser("fieldDescriptor", parser);
        this.registerBeanDefinitionParser("objectStructure", parser);
        this.registerBeanDefinitionParser("type", parser);
        this.registerBeanDefinitionParser("state", parser);
        this.registerBeanDefinitionParser("field", parser);
        this.registerBeanDefinitionParser("enum", parser);
        this.registerBeanDefinitionParser("contextDescriptor", parser);
        this.registerBeanDefinitionParser("contextValueDescriptor", parser);  
	}

}
