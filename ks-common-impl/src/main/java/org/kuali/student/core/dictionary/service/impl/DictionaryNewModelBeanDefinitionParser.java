package org.kuali.student.core.dictionary.service.impl;

import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.kuali.student.core.dictionary.newmodel.dto.CaseConstraint;
import org.kuali.student.core.dictionary.newmodel.dto.ConstraintDescriptor;
import org.kuali.student.core.dictionary.newmodel.dto.ConstraintSelector;
import org.kuali.student.core.dictionary.newmodel.dto.Context;
import org.kuali.student.core.dictionary.newmodel.dto.Field;
import org.kuali.student.core.dictionary.newmodel.dto.FieldDescriptor;
import org.kuali.student.core.dictionary.newmodel.dto.LookupConstraint;
import org.kuali.student.core.dictionary.newmodel.dto.LookupKeyConstraint;
import org.kuali.student.core.dictionary.newmodel.dto.ObjectStructure;
import org.kuali.student.core.dictionary.newmodel.dto.OccursConstraint;
import org.kuali.student.core.dictionary.newmodel.dto.RequireConstraint;
import org.kuali.student.core.dictionary.newmodel.dto.SearchSelector;
import org.kuali.student.core.dictionary.newmodel.dto.State;
import org.kuali.student.core.dictionary.newmodel.dto.Type;
import org.kuali.student.core.dictionary.newmodel.dto.TypeStateCaseConstraint;
import org.kuali.student.core.dictionary.newmodel.dto.TypeStateWhenConstraint;
import org.kuali.student.core.dictionary.newmodel.dto.ValidCharsConstraint;
import org.kuali.student.core.dictionary.newmodel.dto.WhenConstraint;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DictionaryNewModelBeanDefinitionParser extends AbstractSingleBeanDefinitionParser{

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
  	

    	
    	if(element.hasAttribute("key")&&!"objectStructure".equals(element.getLocalName())){
            builder.addPropertyValue("key", element.getAttribute("key"));
		}
		
		if("contextDescriptor".equals(element.getLocalName())){
			 builder.addPropertyValue("type", element.getAttribute("type"));
		}

		if("state".equals(element.getLocalName())){
            List<?> refList = pc.getDelegate().parseListElement(element, pc.getContainingBeanDefinition());
            if(refList!=null&&!refList.isEmpty()){
            	builder.addPropertyValue("field",refList);
            }
        }else if("type".equals(element.getLocalName())){
            List<?> refList = pc.getDelegate().parseListElement(element, pc.getContainingBeanDefinition());
            if(refList!=null&&!refList.isEmpty()){
            	builder.addPropertyValue("state",refList);
            }
        }else if("objectStructure".equals(element.getLocalName())){
    		if(element.hasAttribute("key")){
                builder.addPropertyValue("key", element.getAttribute("key"));
    		}
	        for(int i = 0;i<element.getChildNodes().getLength();i++){
	            Node node = element.getChildNodes().item(i);
	            if(Node.ELEMENT_NODE == node.getNodeType()&&"desc".equals(node.getLocalName())){
	            	builder.addPropertyValue(node.getLocalName(), node.getTextContent());
	            	element.removeChild(node);
	            }
	        }
        	List<?> refList = pc.getDelegate().parseListElement(element, pc.getContainingBeanDefinition());
        	if(refList!=null&&!refList.isEmpty()){
        		builder.addPropertyValue("type",refList);
        	}
        	
        }else{
	        //Parse the children
        	HashSet<String> visitedNodes = new HashSet<String>();
	        for(int i = 0;i<element.getChildNodes().getLength();i++){
	            Node node = element.getChildNodes().item(i);
	            if(Node.ELEMENT_NODE == node.getNodeType() && !visitedNodes.contains(node.getLocalName())){
	                if(isList(node.getLocalName())){
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
//	                            Object childBean = pc.getDelegate().parsePropertySubElement(childElement, pc.getContainingBeanDefinition());
	                            builder.addPropertyReference(node.getLocalName(), childElement.getAttribute("bean"));
	                        }else{
	                            Object childBean = pc.getDelegate().parsePropertySubElement((Element)node, pc.getContainingBeanDefinition());
	                            String fieldName=resolveFieldName(element.getLocalName(),node.getLocalName());
	                            builder.addPropertyValue(fieldName, childBean);
	                        }
	                    }else{
	                    	if("field".equals(element.getLocalName())&&"ref".equals(node.getLocalName())){
	                    		//Object refBean = pc.getDelegate().parsePropertySubElement((Element)node, pc.getContainingBeanDefinition());
	                    		builder.addPropertyReference("fieldDescriptor", ((Element)node).getAttribute("bean"));
	                    	}else{
	                    		String fieldName=resolveFieldName(element.getLocalName(),node.getLocalName());
	                    		builder.addPropertyValue(fieldName, node.getTextContent());
	                    	}
	                    }
	                }
	            }
	        }
        }
    }


    private Element getChildList(Element element, String localName) {
    	try{
	    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = dbf.newDocumentBuilder();
	    	Document doc = builder.newDocument();
	
	    	Element root = doc.createElement("listRoot");
	    	
	        for(int i = 0;i<element.getChildNodes().getLength();i++){
	            Node node = element.getChildNodes().item(i);
	            if(Node.ELEMENT_NODE == node.getNodeType() && localName.equals(node.getLocalName())){
	            	Node copied = doc.importNode(node, true);
	            	root.appendChild(copied);
	            }
	        }
	        
	    	return root;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}

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

        return "case".equals(localName)||
        	   "when".equals(localName)||
        	   "typeStateCase".equals(localName)||
        	   "typeStateWhen".equals(localName)||
        	   "lookup".equals(localName)||
        	   "lookupKey".equals(localName)||
        	   "occurs".equals(localName)||
        	   "require".equals(localName);
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
