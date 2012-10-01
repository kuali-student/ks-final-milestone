package org.kuali.student.r2.common.criteria.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.kuali.rice.core.api.criteria.CriteriaValue;
import org.kuali.rice.core.api.criteria.MultiValuedPredicate;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.SingleValuedPredicate;
import org.kuali.student.r2.common.criteria.LookupCustomizer.PredicateTransform;

import static org.kuali.rice.core.api.criteria.PredicateFactory.*;

public abstract class BaseTransform implements PredicateTransform {

    public BaseTransform() {

    }

    public Predicate createPredicate(final Predicate input, String path) {
        final Predicate attrValue;
        if (input instanceof SingleValuedPredicate) {
            final CriteriaValue<?> value = ((SingleValuedPredicate) input).getValue();
            attrValue = dynConstruct(input.getClass().getSimpleName(), path, value.getValue());
        } else if (input instanceof MultiValuedPredicate) {
            final Set<? extends CriteriaValue<?>> values = ((MultiValuedPredicate) input).getValues();
            List<Object> l = new ArrayList<Object>();
            for (CriteriaValue<?> v : values) {
                l.add(v.getValue());
            }

            attrValue = dynConstruct(input.getClass().getSimpleName(), path, l.toArray());
        } else {
            attrValue = dynConstruct(input.getClass().getSimpleName(), path);
        }
        return attrValue;

    }
}