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

package org.kuali.student.common.dictionary.service.impl.old;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.kuali.student.common.dictionary.old.dto.CaseConstraint;
import org.kuali.student.common.dictionary.old.dto.ConstraintDescriptor;
import org.kuali.student.common.dictionary.old.dto.ConstraintSelector;
import org.kuali.student.common.dictionary.old.dto.Context;
import org.kuali.student.common.dictionary.old.dto.Field;
import org.kuali.student.common.dictionary.old.dto.FieldDescriptor;
import org.kuali.student.common.dictionary.old.dto.LookupConstraint;
import org.kuali.student.common.dictionary.old.dto.LookupKeyConstraint;
import org.kuali.student.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.common.dictionary.old.dto.OccursConstraint;
import org.kuali.student.common.dictionary.old.dto.RequireConstraint;
import org.kuali.student.common.dictionary.old.dto.SearchSelector;
import org.kuali.student.common.dictionary.old.dto.State;
import org.kuali.student.common.dictionary.old.dto.Type;
import org.kuali.student.common.dictionary.old.dto.TypeStateCaseConstraint;
import org.kuali.student.common.dictionary.old.dto.TypeStateWhenConstraint;
import org.kuali.student.common.dictionary.old.dto.ValidCharsConstraint;
import org.kuali.student.common.dictionary.old.dto.WhenConstraint;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Daniel Epstein
 *
 */
@Deprecated
public class DictionaryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser{
    
    final Logger logger = Logger.getLogger(DictionaryBeanDefinitionParser.class);

	//Resolves the tag name to an actual class
    @Override
    protected Class<?> getBeanClass(Element element) {

    	if (element.getLocalName().equals("case")){
    		return CaseConstraint.class;
		}
		if (element.getLocalName().equals("constraintDescriptor")) {
			return ConstraintDescriptor.class;
		}
		if (element.getLocalName().equals("constraint")) {
			return ConstraintSelector.class;
		}
		if (element.getLocalName().equals("context")) {
			return Context.class;
		}
		if (element.getLocalName().equals("field")) {
			return Field.class;
		}
		if (element.getLocalName().equals("fieldDescriptor")) {
			return FieldDescriptor.class;
		}
		if (element.getLocalName().equals("lookup")) {
			return LookupConstraint.class;
		}
		if (element.getLocalName().equals("lookupKey")) {
			return LookupKeyConstraint.class;
		}
		if (element.getLocalName().equals("objectStructure")) {
			return ObjectStructure.class;
		}
		if (element.getLocalName().equals("occurs")) {
			return OccursConstraint.class;
		}
		if (element.getLocalName().equals("require")) {
			return RequireConstraint.class;
		}
		if (element.getLocalName().equals("search")) {
			return SearchSelector.class;
		}
		if (element.getLocalName().equals("state")) {
			return State.class;
		}
		if (element.getLocalName().equals("type")) {
			return Type.class;
		}
		if (element.getLocalName().equals("typeStateCase")) {
			return TypeStateCaseConstraint.class;
		}
		if (element.getLocalName().equals("typeStateWhen")) {
			return TypeStateWhenConstraint.class;
		}
		if (element.getLocalName().equals("when")) {
			return WhenConstraint.class;
		}
		if (element.getLocalName().equals("validChars")) {
			return ValidCharsConstraint.class;
		}
    	

        return super.getBeanClass(element);
    }

    @Override
    protected void doParse(Element element, ParserContext pc, BeanDefinitionBuilder builder) {
  	
    	//Copy Attributes
    	if(element.hasAttributes()){
    		for(int i = 0;i<element.getAttributes().getLength();i++){
        		Attr attr = (Attr) element.getAttributes().item(i);
        		if("abstract".equals(attr.getName())){
        			builder.setAbstract(true);
        		}else if(!"id".equals(attr.getName())&&!"parent".equals(attr.getName())){
        			builder.addPropertyValue(attr.getName(), attr.getValue());
        		}
    		}
    	}
    	
	    //Parse the children
    	HashSet<String> visitedNodes = new HashSet<String>();
        for(int i = 0;i<element.getChildNodes().getLength();i++){
            Node node = element.getChildNodes().item(i);

            //We are only interested in child elements that have not been visited
            if(Node.ELEMENT_NODE == node.getNodeType()){	              
	            
            	//Get the local name minus the "Ref"
            	String localName=node.getLocalName();
            	if(localName.endsWith("Ref")){
            		localName=localName.substring(0, localName.length()-"Ref".length());
            	}
            	if(!visitedNodes.contains(localName)){
	            	//Check if the child element belongs in a list
	            	if(isList(localName)){
	            		Element childList=getChildList(element,localName);
	                	visitedNodes.add(localName);
	                    List<?> refList = pc.getDelegate().parseListElement(childList, pc.getContainingBeanDefinition());
	                    if(refList!=null&&!refList.isEmpty()){
	                    	String fieldName=resolveFieldName(element.getLocalName(),localName);
	                    	builder.addPropertyValue(fieldName,refList);
	                    }
	                //Check if this is an attribute map
	            	}else if("attributes".equals(node.getLocalName())){
	            		Map<String,String> attributes = getAttributeMap((Element)node);
	            		builder.addPropertyValue(node.getLocalName(), attributes);
	                //Check if the child element is a Ref
	                }else if(node.getLocalName().endsWith("Ref")){
	                	if("objectStructureRef".equals(node.getLocalName())){
	                		builder.addPropertyValue("objectStructureRef", ((Element)node).getAttribute("bean"));
	                		//Add in the nested object too
	                		builder.addPropertyReference("objectStructure", ((Element)node).getAttribute("bean"));
	                	}else{
	                		builder.addPropertyReference(localName, ((Element)node).getAttribute("bean"));
	                	}
	               }else{
	            	    //Get the child of the child to see if we need to parse the nested node, or just set the text value
	                    Element childElement = getFirstChildElement(node);
	                    if(childElement!=null ||"search".equals(node.getLocalName())){
	                    	//Parse the nested Node
	                        Object childBean = pc.getDelegate().parsePropertySubElement((Element)node, pc.getContainingBeanDefinition());
	                        String fieldName=resolveFieldName(element.getLocalName(),node.getLocalName());
	                        builder.addPropertyValue(fieldName, childBean);
	                    }else{
	                    	
	                    	
	                   		//Set the text value
	                		String fieldName=resolveFieldName(element.getLocalName(),node.getLocalName());

	                		if(Node.ELEMENT_NODE == node.getNodeType()&&"date".equals(((Element)node).getSchemaTypeInfo().getTypeName())){
	                			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	                			try {
									builder.addPropertyValue(fieldName, df.parse(node.getTextContent()));
								} catch (Exception e) {
									logger.error("Cannot convert date, must be in format 'YYYY-MM-DD' :"+node.getTextContent(),e);
								}
	                		}else{
	                			builder.addPropertyValue(fieldName, node.getTextContent());
	                		}
	                    }
	                }
	            }
	        }
        }
    }

    
    /**
     * Parses attribute map from 
     * &lt;attributes&gt;
     * 	&lt;attribute key="attr1" value="attr2"/&gt;
     * &lt;/attributes&gt;
     * @param element
     * @return map of attributes
     */
    private Map<String, String> getAttributeMap(Element element) {
    	Map<String, String> attributes = new HashMap<String, String>();
    	for(int i = 0;i<element.getChildNodes().getLength();i++){
    		Node node = element.getChildNodes().item(i);
    		if(Node.ELEMENT_NODE == node.getNodeType() && "attribute".equals(node.getLocalName())){
    			String key = ((Element)node).getAttribute("key");
    			String value = ((Element)node).getAttribute("value");
    			attributes.put(key, value);
    		}
    	}
		return attributes;
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

    //This is called to resolve tag names to field names based on the element and parent element local names
	private String resolveFieldName(String parentName, String nodeName) {
		if("constraint".equals(parentName)&&"case".equals(nodeName)){
			return "caseConstraint";
		}
		if("constraint".equals(parentName)&&"typeStateCase".equals(nodeName)){
			return "typeStateCaseConstraint";
		}
		if("constraint".equals(parentName)&&"lookup".equals(nodeName)){
			return "lookupConstraint";
		}
		if("constraint".equals(parentName)&&"occurs".equals(nodeName)){
			return "occursConstraint";
		}
		if("constraint".equals(parentName)&&"require".equals(nodeName)){
			return "requireConstraint";
		}
		if("case".equals(parentName)&&"when".equals(nodeName)){
			return "whenConstraint";
		}

		return nodeName;
	}

	//Gets the first child element
	private Element getFirstChildElement(Node node) {
        for(int i = 0;i<node.getChildNodes().getLength();i++){
            Node childNode = node.getChildNodes().item(i);
            if(Node.ELEMENT_NODE == childNode.getNodeType()){
                return (Element) childNode;
            }
        }
        return null;
    }

	//Returns true if the element should be part of a list
    private boolean isList(String localName) {

        return "field".equals(localName)||
        	   "case".equals(localName)||
        	   "when".equals(localName)||
        	   "lookup".equals(localName)||
        	   "lookupKey".equals(localName)||
        	   "occurs".equals(localName)||
        	   "constraint".equals(localName)||
        	   "type".equals(localName)||
        	   "state".equals(localName)||
        	   "require".equals(localName);
    }

    //This makes use of the spring parent="" functionality 
	@Override
	protected String getParentName(Element element) {
		if(element.hasAttribute("parent")){
            return element.getAttribute("parent");
		}
		return super.getParentName(element);
	}

	//This means any bean without an id attribute gets one auto generated for it
	@Override
	protected boolean shouldGenerateIdAsFallback() {
		return true;
	}
	
}
