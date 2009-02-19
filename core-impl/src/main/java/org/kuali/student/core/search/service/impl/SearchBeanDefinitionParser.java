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
		if (element.getLocalName().equals("searchCriteria")) {
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
		if (element.getLocalName().equals("searchResult")) {
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
		
		//Parse the children
		for(int i = 0;i<element.getChildNodes().getLength();i++){
			Node node = element.getChildNodes().item(i);
			if(Node.ELEMENT_NODE == node.getNodeType()){
				if(isList(node.getLocalName())){
					List<?> refList = pc.getDelegate().parseListElement((Element) node, pc.getContainingBeanDefinition());
					builder.addPropertyValue(node.getLocalName(),refList);
				}else if(((Element)node).getElementsByTagName("ref").getLength()>0){
					Object refBean = pc.getDelegate().parsePropertySubElement((Element) ((Element)node).getElementsByTagName("ref").item(0), pc.getContainingBeanDefinition());
					builder.addPropertyValue(node.getLocalName(), refBean);
				}else{
					builder.addPropertyValue(node.getLocalName(), node.getTextContent());
				}
			}
		}
	}


	private boolean isList(String localName) {
		return localName.equals("queryParams")||localName.equals("resultColumns");
	}

}
