package org.kuali.rice.krms.service.impl;

import org.kuali.rice.krms.framework.engine.expression.ComparisonOperator;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.dto.TemplateInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christoff
 * Date: 2013/01/10
 */
public class TemplateRegistryImpl implements TemplateRegistry {

    private Map<String, TemplateInfo> templatesMap;
    private Map<String, ComponentBuilder> componentBuilderMap = new HashMap<String, ComponentBuilder>();

    public void setTemplatesMap(Map<String, TemplateInfo> templatesMap) {
        this.templatesMap = templatesMap;
    }

    public TemplateInfo getTemplateForType(String type) {
        TemplateInfo template = templatesMap.get(type);
        return (template != null ? template : null);
    }

    public String getTermSpecNameForType(String type) {
        TemplateInfo template = templatesMap.get(type);
        return (template != null ? template.getTermSpecName() : null);
    }

    public String getOperationForType(String type) {
        TemplateInfo template = templatesMap.get(type);
        if (template == null) {
            return null;
        }
        ComparisonOperator operator = ComparisonOperator.fromCode(template.getOperator());
        return (operator != null ? operator.getCode() : null);
    }

    public String getValueForType(String type) {
        TemplateInfo template = templatesMap.get(type);
        return (template != null ? template.getValue() : null);
    }

    public ComponentBuilder getComponentBuilderForType(String type){
        TemplateInfo template = templatesMap.get(type);
        if ((template == null) || (template.getComponentBuilderClass() == null)){
            return null;
        }

        ComponentBuilder builder = componentBuilderMap.get(template.getComponentBuilderClass());
        if (builder == null){
            try {
                builder = (ComponentBuilder) Class.forName(template.getComponentBuilderClass()).newInstance();
                componentBuilderMap.put(template.getComponentBuilderClass(), builder);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return builder;
    }

}
