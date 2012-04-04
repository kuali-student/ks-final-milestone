package org.kuali.student.r2.common.criteria.transform;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PropertyPathPredicate;
import org.kuali.rice.core.framework.persistence.jpa.criteria.Criteria;

public class TypeTransform extends BaseTransform{
    
    private static final String TYPE_PROPERTY = "id";
    private static final String TYPE_DESC = "type";
    
    private String typeAttribute;

    private TypeTransform(String typeAttribute) {
        this.typeAttribute = typeAttribute;
    }

    @Override
    public Predicate apply(final Predicate input, Criteria criteria) {
        if (input instanceof PropertyPathPredicate) {
            String pp = ((PropertyPathPredicate) input).getPropertyPath();
            if (TYPE_DESC.equalsIgnoreCase(pp)) {
                return this.createPredicate(input, typeAttribute);
            }
        }

        return input;
    }
    
}
