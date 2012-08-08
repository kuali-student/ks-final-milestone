package org.kuali.student.r2.common.criteria.transform;

import java.util.Map;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PropertyPathPredicate;
import org.kuali.rice.core.framework.persistence.jpa.criteria.Criteria;

public class RenameTransform extends BaseTransform {
    
    private Map<String, String> renameAttributes;

    private RenameTransform(Map<String, String> renameAttributes) {
        this.renameAttributes = renameAttributes;
    }

    @Override
    public Predicate apply(final Predicate input, Criteria criteria) {
        if (input instanceof PropertyPathPredicate) {
            String pp = ((PropertyPathPredicate) input).getPropertyPath();
            String property = this.getRenameProperty(pp);
            if (property != null) {
                return this.createPredicate(input, property);
            }
        }

        return input;
    }

    private String getRenameProperty(String key) {
        return renameAttributes.get(key);
    }
}
