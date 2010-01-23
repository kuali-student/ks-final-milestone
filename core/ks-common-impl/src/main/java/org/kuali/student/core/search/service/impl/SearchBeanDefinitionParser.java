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
package org.kuali.student.core.search.service.impl;

import java.util.List;

import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.search.dto.QueryParamInfo;
import org.kuali.student.core.search.dto.ResultColumnInfo;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SearchBeanDefinitionParser extends
		AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {

		if (element.getLocalName().equals("fieldDescriptor")) {
			return FieldDescriptor.class;
		}
		if (element.getLocalName().equals("searchCriteriaTypeInfo")) {
			return SearchCriteriaTypeInfo.class;
		}
		if (element.getLocalName().equals("queryParam")) {
			return QueryParamInfo.class;
		}
		if (element.getLocalName().equals("searchType")) {
			return SearchTypeInfo.class;
		}
		if (element.getLocalName().equals("resultColumn")) {
			return ResultColumnInfo.class;
		}
		if (element.getLocalName().equals("searchResultTypeInfo")) {
			return SearchResultTypeInfo.class;
		}
		return super.getBeanClass(element);
	}

	@Override
	protected void doParse(Element element, ParserContext pc, BeanDefinitionBuilder builder) {
		//Set the key
		if(!"fieldDescriptor".equals(element.getLocalName())&&element.hasAttribute("id")){
			builder.addPropertyValue("key", element.getAttributes().getNamedItem("id").getTextContent());
		}
		
		if("fieldDescriptor".equals(element.getLocalName())&&element.hasAttribute("parent")){
			builder.setParentName(element.getAttributes().getNamedItem("parent").getTextContent());
		}
		
		//Set optional if its a queryParam
		if("queryParam".equals(element.getLocalName())&&element.hasAttribute("optional")){
			builder.addPropertyValue("optional", "true".equals(element.getAttribute("optional"))?true:false);
		}
		
	
		//Parse the children
		for(int i = 0;i<element.getChildNodes().getLength();i++){
			Node node = element.getChildNodes().item(i);
			if(Node.ELEMENT_NODE == node.getNodeType()){
				if(isList(node.getLocalName())){
					List<?> refList = pc.getDelegate().parseListElement((Element) node, pc.getContainingBeanDefinition());
					builder.addPropertyValue(node.getLocalName(),refList);
				}else{
					Element childElement = getFirstChildElement(node); 
					if(childElement!=null){
						if("ref".equals(childElement.getLocalName())){
							Object childBean = pc.getDelegate().parsePropertySubElement(childElement, pc.getContainingBeanDefinition());
							builder.addPropertyValue(node.getLocalName(), childBean);
						}else{
							Object childBean = pc.getDelegate().parsePropertySubElement((Element)node, pc.getContainingBeanDefinition());
							builder.addPropertyValue(node.getLocalName(), childBean);
						}
					}else{
						if("ref".equals(node.getLocalName())&&"queryParam".equals(element.getLocalName())){
							Object childBean = pc.getDelegate().parsePropertySubElement((Element)node, pc.getContainingBeanDefinition());
							builder.addPropertyValue("fieldDescriptor", childBean);
						}else{
							builder.addPropertyValue(node.getLocalName(), node.getTextContent());
						}
					}
				}
			}
		}
	}


	private Element getFirstChildElement(Node node) {
		// TODO Auto-generated method stub
		for(int i = 0;i<node.getChildNodes().getLength();i++){
			Node childNode = node.getChildNodes().item(i);
			if(Node.ELEMENT_NODE == childNode.getNodeType()){
				return (Element) childNode;
			}
		}
		
		return null;
	}

	private boolean isList(String localName) {
		return localName.equals("queryParams")||localName.equals("resultColumns");
	}
	
	@Override
	protected boolean shouldGenerateIdAsFallback() {
		return true;
	}
}
