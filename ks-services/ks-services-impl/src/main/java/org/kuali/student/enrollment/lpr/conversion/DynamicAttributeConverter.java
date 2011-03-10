package org.kuali.student.enrollment.lpr.conversion;

import org.kuali.student.enrollment.lpr.model.DynamicAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor
 */
public class DynamicAttributeConverter implements SimpleConverter<List<DynamicAttribute>, Map<String, String>> {

    @Override
    public List<DynamicAttribute> fromDto(Map<String, String> attributes) {
        List<DynamicAttribute> dynamicAttributes = new ArrayList<DynamicAttribute>();
        dtoToEntity(attributes, dynamicAttributes);
        return dynamicAttributes;
    }

    @Override
    public Map<String, String> fromEntity(List<DynamicAttribute> dynamicAttributes) {
        Map<String, String> attributes = new HashMap<String, String>();
        entityToDto(dynamicAttributes, attributes);
        return attributes;
    }

    @Override
    public void entityToDto(List<DynamicAttribute> dynamicAttributes, Map<String, String> attributes) {
        for (DynamicAttribute dynamicAttribute : dynamicAttributes) {
            attributes.put(dynamicAttribute.getKey(), dynamicAttribute.getValue());
        }
    }

    @Override
    public void dtoToEntity(Map<String, String> attributes, List<DynamicAttribute> dynamicAttributes) {
        for (String key : attributes.keySet()) {
            dynamicAttributes.add(new DynamicAttribute(key, attributes.get(key)));
        }
    }
}
