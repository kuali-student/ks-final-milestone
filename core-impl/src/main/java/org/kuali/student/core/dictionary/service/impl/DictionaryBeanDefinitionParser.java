package org.kuali.student.core.dictionary.service.impl;

import java.util.List;

import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DictionaryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {

        if (element.getLocalName().equals("fieldDescriptor")) {
            return FieldDescriptor.class;
        }
        if (element.getLocalName().equals("objectStructure")) {
            return ObjectStructure.class;
        }
        if (element.getLocalName().equals("typeSelector")) {
            return Type.class;
        }
        if (element.getLocalName().equals("stateSelector")) {
            return State.class;
        }
        if (element.getLocalName().equals("fieldSelector")) {
            return Field.class;
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
                        builder.addPropertyValue(node.getLocalName(), node.getTextContent());
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

}
