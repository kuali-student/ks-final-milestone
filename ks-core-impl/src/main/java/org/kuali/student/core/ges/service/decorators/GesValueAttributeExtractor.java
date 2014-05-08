package org.kuali.student.core.ges.service.decorators;

import net.sf.ehcache.Element;
import net.sf.ehcache.search.attribute.AttributeExtractor;
import net.sf.ehcache.search.attribute.AttributeExtractorException;
import org.kuali.student.core.ges.dto.ValueInfo;

import java.util.List;


public class GesValueAttributeExtractor implements AttributeExtractor {
    @Override
    public Object attributeFor(Element element, String s) throws AttributeExtractorException {
        StringBuilder ids = new StringBuilder();

        if(element.getValue() instanceof List) {
            List<ValueInfo> values = (List<ValueInfo>)element.getValue();
            for(ValueInfo value : values) {
                ids.append(" " + value.getId() + " ");
            }
        } else {
            ValueInfo value = (ValueInfo)element.getValue();
            ids.append(" " + value.getId() + " ");
        }

        return ids;
    }

}
