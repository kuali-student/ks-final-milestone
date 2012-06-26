package org.kuali.student.r2.common.criteria.transform;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.framework.persistence.jpa.criteria.Criteria;

public class AddEqualTransform extends BaseTransform {
    
    private String propertyPath;
    private String value;
    
    private AddEqualTransform(String propertyPath, String value) {
        this.propertyPath = propertyPath;
        this.value = value;
    }

    @Override
    public Predicate apply(final Predicate input, Criteria criteria) {
        return and(equal(propertyPath, value), input);    
    }

}
