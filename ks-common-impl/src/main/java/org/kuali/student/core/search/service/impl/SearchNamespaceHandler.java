package org.kuali.student.core.search.service.impl;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class SearchNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		BeanDefinitionParser parser = new SearchBeanDefinitionParser();
		this.registerBeanDefinitionParser("fieldDescriptor", parser);
		this.registerBeanDefinitionParser("searchCriteriaTypeInfo", parser);
		this.registerBeanDefinitionParser("queryParam", parser);
		this.registerBeanDefinitionParser("resultColumn", parser);
		this.registerBeanDefinitionParser("searchType", parser);
		this.registerBeanDefinitionParser("searchResultTypeInfo", parser);
	}

}
