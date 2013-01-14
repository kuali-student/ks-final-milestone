package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.krms.framework.engine.expression.ComparisonOperator;
import org.kuali.student.enrollment.class1.krms.dto.TemplateInfo;
import org.kuali.student.enrollment.class1.krms.service.TemplateResolverService;

import java.util.Map;

/**
 * @author Christoff
 * Date: 2013/01/10
 */
public class TemplateResolverServiceMockImpl implements TemplateResolverService {

    private Map<String, TemplateInfo> templatesMap;

    public void setTemplatesMap(Map<String, TemplateInfo> templatesMap) {
        this.templatesMap = templatesMap;
    }

    public String getTermSpecificationForType(String type) {
        TemplateInfo template = templatesMap.get(type);
        return (template != null ? template.getTermSpecification() : null);
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
        return (template != null ? template.getTermSpecification() : null);
    }


}
