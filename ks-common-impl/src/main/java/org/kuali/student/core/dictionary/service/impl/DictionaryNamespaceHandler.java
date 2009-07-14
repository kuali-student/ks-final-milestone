package org.kuali.student.core.dictionary.service.impl;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class DictionaryNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		BeanDefinitionParser parser = new DictionaryBeanDefinitionParser();

		this.registerBeanDefinitionParser("case", parser);
		this.registerBeanDefinitionParser("constraintDescriptor", parser);
		this.registerBeanDefinitionParser("constraint", parser);
		this.registerBeanDefinitionParser("context", parser);
		this.registerBeanDefinitionParser("field", parser);
		this.registerBeanDefinitionParser("fieldDescriptor", parser);
		this.registerBeanDefinitionParser("lookup", parser);
		this.registerBeanDefinitionParser("lookupKey", parser);
		this.registerBeanDefinitionParser("objectStructure", parser);
		this.registerBeanDefinitionParser("occurs", parser);
		this.registerBeanDefinitionParser("require", parser);
		this.registerBeanDefinitionParser("search", parser);
		this.registerBeanDefinitionParser("state", parser);
		this.registerBeanDefinitionParser("type", parser);
		this.registerBeanDefinitionParser("typeStateCase", parser);
		this.registerBeanDefinitionParser("typeStateWhen", parser);
		this.registerBeanDefinitionParser("when", parser);
		this.registerBeanDefinitionParser("validChars", parser);
	}

}
