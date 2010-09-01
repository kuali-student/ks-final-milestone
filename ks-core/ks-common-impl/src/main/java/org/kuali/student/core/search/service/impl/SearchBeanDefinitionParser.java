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

package org.kuali.student.core.search.service.impl;

import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.kuali.student.core.dictionary.old.dto.FieldDescriptor;
import org.kuali.student.core.search.dto.ComparisonParamInfo;
import org.kuali.student.core.search.dto.CrossSearchTypeInfo;
import org.kuali.student.core.search.dto.JoinComparisonInfo;
import org.kuali.student.core.search.dto.JoinCriteriaInfo;
import org.kuali.student.core.search.dto.JoinResultMappingInfo;
import org.kuali.student.core.search.dto.QueryParamInfo;
import org.kuali.student.core.search.dto.ResultColumnInfo;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.dto.SubSearchInfo;
import org.kuali.student.core.search.dto.SubSearchParamMappingInfo;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SearchBeanDefinitionParser extends
		AbstractSingleBeanDefinitionParser {
    private static final Logger logger = Logger.getLogger(SearchBeanDefinitionParser.class);

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
		
		
		if (element.getLocalName().equals("crossSearchType")) {
			return CrossSearchTypeInfo.class;
		}
		if (element.getLocalName().equals("subSearch")) {
			return SubSearchInfo.class;
		}
		if (element.getLocalName().equals("subSearchParamMappings")) {
			return SubSearchParamMappingInfo.class;
		}
		if (element.getLocalName().equals("joinCriteria")) {
			return JoinCriteriaInfo.class;
		}
		if (element.getLocalName().equals("comparison")) {
			return JoinComparisonInfo.class;
		}
		if (element.getLocalName().equals("leftHandSide")) {
			return ComparisonParamInfo.class;
		}
		if (element.getLocalName().equals("rightHandSide")) {
			return ComparisonParamInfo.class;
		}
		if (element.getLocalName().equals("joinResultMapping")) {
			return JoinResultMappingInfo.class;
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
		
    	//Copy Attributes
    	if(element.hasAttributes()){
    		for(int i = 0;i<element.getAttributes().getLength();i++){
        		Attr attr = (Attr) element.getAttributes().item(i);
        		if("abstract".equals(attr.getName())){
        			builder.setAbstract(true);
        		}else if(!"id".equals(attr.getName())&&!"parent".equals(attr.getName())){
        			String fieldName = resolveFieldName(element.getLocalName(),attr.getName());
        			builder.addPropertyValue(fieldName, attr.getValue());
        		}
    		}
    	}
		
	
		HashSet<String> visitedNodes = new HashSet<String>();
		//Parse the children
		for(int i = 0;i<element.getChildNodes().getLength();i++){
			Node node = element.getChildNodes().item(i);

			if(Node.ELEMENT_NODE == node.getNodeType()){
            	String localName=node.getLocalName();
            	if(!visitedNodes.contains(localName)){
					if(isWrappedList(node.getLocalName())){
						List<?> refList = pc.getDelegate().parseListElement((Element) node, pc.getContainingBeanDefinition());
						builder.addPropertyValue(node.getLocalName(),refList);
					}else if(isUnwrappedList(node.getLocalName())){
	            		Element childList=getChildList(element,node.getLocalName());
	                	visitedNodes.add(node.getLocalName());
	                    List<?> refList = pc.getDelegate().parseListElement(childList, pc.getContainingBeanDefinition());
	                    if(refList!=null&&!refList.isEmpty()){
	                    	String fieldName=resolveFieldName(element.getLocalName(),node.getLocalName());
	                    	builder.addPropertyValue(fieldName,refList);
	                    }
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
							if(("ref".equals(node.getLocalName())&&"queryParam".equals(element.getLocalName()))){
								Object childBean = pc.getDelegate().parsePropertySubElement((Element)node, pc.getContainingBeanDefinition());
								builder.addPropertyValue("fieldDescriptor", childBean);
							}else if("leftHandSide".equals(node.getLocalName())
									||"rightHandSide".equals(node.getLocalName())){
								Object childBean = pc.getDelegate().parsePropertySubElement((Element)node, pc.getContainingBeanDefinition());
								builder.addPropertyValue(node.getLocalName(), childBean);
							}else{
								builder.addPropertyValue(node.getLocalName(), node.getTextContent());
							}
						}
					}
            	}
			}
		}
	}

	private boolean isUnwrappedList(String localName) {
		return localName.equals("subSearchParamMappings")||
               localName.equals("comparison")||
               localName.equals("subSearch")||
               localName.equals("joinResultMapping");
	}
	
    //This is called to resolve tag names to field names based on the element and parent element local names
	private String resolveFieldName(String parentName, String nodeName) {
		if("comparison".equals(nodeName)){
			return "comparisons";
		}
		if("subSearch".equals(nodeName)){
			return "subSearches";
		}
		if("joinResultMapping".equals(nodeName)){
			return "joinResultMappings";
		}
		if("joinCriteria".equals(parentName)&&"type".equals(nodeName)){
			return "joinType";
		}

		return nodeName;
	}
	
    //This builds up a list of the child nodes so that the spring parseListElement can be used
    //it also translates <fooRef> elements into straight spring <ref> elements
    private Element getChildList(Element element, String localName) {
    	try{
    		//Create a new document to contain our list of elements
	    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = dbf.newDocumentBuilder();
	    	Document doc = builder.newDocument();
	
	    	Element root = doc.createElement("listRoot");
	    	
	        for(int i = 0;i<element.getChildNodes().getLength();i++){
	            Node node = element.getChildNodes().item(i);
	            if(Node.ELEMENT_NODE == node.getNodeType() && localName.equals(node.getLocalName())){
	            	
	            	//Copy the node from the spring config to our list
	            	Node copied = doc.importNode(node, true);
	            	root.appendChild(copied);
	            }
	            if(Node.ELEMENT_NODE == node.getNodeType() && (localName+"Ref").equals(node.getLocalName())){
	            	
	            	//Create a new spring ref element and copy the bean attribute
	            	Element ref = doc.createElement("ref");
	            	ref.setAttribute("bean", ((Element)node).getAttribute("bean"));
	            	root.appendChild(ref);
	            }
	        }
	        
	    	return root;
    	}catch(Exception e){
    		logger.error("Exception occured: ", e);
    	}
    	return null;
	}
    
	private Element getFirstChildElement(Node node) {
		for(int i = 0;i<node.getChildNodes().getLength();i++){
			Node childNode = node.getChildNodes().item(i);
			if(Node.ELEMENT_NODE == childNode.getNodeType()){
				return (Element) childNode;
			}
		}
		
		return null;
	}

	private boolean isWrappedList(String localName) {
		return localName.equals("queryParams")||
			   localName.equals("resultColumns");
	}
	
	@Override
	protected String getParentName(Element element) {
		if(element.hasAttribute("parent")){
            return element.getAttribute("parent");
		}
		return super.getParentName(element);
	}
	
	@Override
	protected boolean shouldGenerateIdAsFallback() {
		return true;
	}
}
