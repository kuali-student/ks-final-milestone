package org.kuali.student.r2.common.criteria.transform;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PropertyPathPredicate;
import org.kuali.rice.core.framework.persistence.jpa.criteria.Criteria;

public class AttributeTransform extends BaseTransform {
    
    private static final String ATTRIBUTE_PROPERTY = "attributes";
    private static final String ATTRIBUTE_ALIAS = "attr";
    private static final String ATTRIBUTE_VALUE = "value";
    private static final String ATTRIBUTE_KEY = "key";
    private static final String ATTRIBUTES_REGEX = "^attributes\\[[0-9a-zA-Z.]+\\]$";
    private static final Pattern ATTRIBUTES_PATTERN = Pattern.compile(ATTRIBUTES_REGEX);

    private AttributeTransform() {

    }

    @Override
    public Predicate apply(final Predicate input, Criteria criteria) {
        if (input instanceof PropertyPathPredicate) {
            String pp = ((PropertyPathPredicate) input).getPropertyPath();
            if (isAttributesPredicate(pp)) {
                criteria.join(ATTRIBUTE_PROPERTY, ATTRIBUTE_ALIAS, false, true);
                
                final String attributeName = pp.substring(pp.indexOf('[') + 1, pp.indexOf(']'));
                final Predicate attrValue = this.createPredicate(input, getPropertyDesc() + ATTRIBUTE_VALUE);

                return and(equal(getPropertyDesc() + ATTRIBUTE_KEY, attributeName), attrValue);
            }
        }

        return input;
    }
    
    private String getPropertyDesc(){
        return Criteria.JPA_ALIAS_PREFIX + "'" + ATTRIBUTE_ALIAS + "'" + Criteria.JPA_ALIAS_SUFFIX + ".";
    }

    private boolean isAttributesPredicate(String pp) {
        Matcher matcher = ATTRIBUTES_PATTERN.matcher(pp);
        return matcher.matches();
    }
}
