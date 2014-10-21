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

package org.kuali.student.r2.common.class1.search;

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
		
		this.registerBeanDefinitionParser("crossSearchType", parser);		
		this.registerBeanDefinitionParser("subSearch", parser);
		this.registerBeanDefinitionParser("subSearchParamMappings", parser);
		this.registerBeanDefinitionParser("joinCriteria", parser);
		this.registerBeanDefinitionParser("comparison", parser);
		this.registerBeanDefinitionParser("leftHandSide", parser);
		this.registerBeanDefinitionParser("rightHandSide", parser);
		this.registerBeanDefinitionParser("joinResultMapping", parser);

	}

}
