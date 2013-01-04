package org.kuali.student.r2.common.criteria.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.criteria.AndPredicate;
import org.kuali.rice.core.api.criteria.OrPredicate;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.PropertyPathPredicate;
import org.kuali.rice.core.framework.persistence.jpa.criteria.Criteria;

public class RenameTransform extends BaseTransform {
    
    private Map<String, String> renameAttributes;

    public RenameTransform ()  {
    }

    public RenameTransform(Map<String, String> renameAttributes) {
        this ();
        this.renameAttributes = renameAttributes;
    }


    @Override
    public Predicate apply(final Predicate input, Criteria criteria) {
        if (input instanceof AndPredicate) {
            AndPredicate and = (AndPredicate) input;
            List<Predicate> andClauses = new ArrayList<Predicate> ();
            for (Predicate p: and.getPredicates()) {
                Predicate renamedPredicate = this.apply(p, criteria);
                andClauses.add(renamedPredicate);
            }
            return PredicateFactory.and(andClauses.toArray(new Predicate[andClauses.size()]));
        }
        if (input instanceof OrPredicate) {
            OrPredicate and = (OrPredicate) input;
            List<Predicate> orClauses = new ArrayList<Predicate> ();
            for (Predicate p: and.getPredicates()) {
                Predicate renamedPredicate = this.apply(p, criteria);
                orClauses.add(renamedPredicate);
            }
            return PredicateFactory.or(orClauses.toArray(new Predicate[orClauses.size()]));
        }
        if (input instanceof PropertyPathPredicate) {
            String pp = ((PropertyPathPredicate) input).getPropertyPath();
            String newName = this.getRenameProperty(pp);
            if (newName != null) {
                return this.createPredicate(input, newName);
            }
        }

        return input;
    }

    public Map<String, String> getRenameAttributes() {
        return renameAttributes;
    }

    public void setRenameAttributes(Map<String, String> renameAttributes) {
        this.renameAttributes = renameAttributes;
    }

    private String getRenameProperty(String key) {
        return renameAttributes.get(key);
    }
}
