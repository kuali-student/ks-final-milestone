/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.service.impl;

import org.kuali.rice.krms.framework.engine.expression.ComparisonOperator;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.dto.TemplateInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a hashmap implementation that contains rule statement (proposition) types to
 * template information relationships.
 *
 * @author Kuali Student Team
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
            } catch (Exception e) {
                throw new RuntimeException("Unable to locate component builder for class " + template.getComponentBuilderClass());
            }
        }

        return builder;
    }

}
