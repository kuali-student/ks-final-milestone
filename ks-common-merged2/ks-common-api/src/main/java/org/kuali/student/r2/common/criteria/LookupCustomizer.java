package org.kuali.student.r2.common.criteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.framework.persistence.jpa.criteria.Criteria;

public class LookupCustomizer<T> {
    
    private List<PredicateTransform> additionalTransforms;
    private List<PredicateTransform> predicateTransforms;
    private final Transform<T, T> resultTransform;

    private LookupCustomizer(Builder<T> builder) {
        this.additionalTransforms = builder.getAdditionalTransforms();
        this.predicateTransforms = builder.getPredicateTransforms();
        this.resultTransform = builder.getResultTransform() != null ? builder.getResultTransform() : IndentityTransform.<T, T>getInstance();
    }
    
    public Predicate applyAdditionalTransforms(Predicate predicate, Criteria criteria) {
        for (PredicateTransform transform : additionalTransforms){
            predicate = transform.apply(predicate, criteria);
        }
        return predicate;
    }

    public Predicate applyPredicateTransforms(Predicate predicate, Criteria criteria) {
        for (PredicateTransform transform : predicateTransforms){
            predicate = transform.apply(predicate, criteria);
        }
        return predicate;
    }

    public T applyResultTransforms(Object o) {
        return null;
    }
    
    public List<PredicateTransform> getAdditionalTransforms() {
        return additionalTransforms;
    }

    public void setAdditionalTransforms(List<PredicateTransform> additionalTransforms) {
        this.additionalTransforms = additionalTransforms;
    }

    public List<PredicateTransform> getPredicateTransforms() {
        return predicateTransforms;
    }

    public void setPredicateTransforms(List<PredicateTransform> predicateTransforms) {
        this.predicateTransforms = predicateTransforms;
    }

    public Transform<T, T> getResultTransform() {
        return resultTransform;
    }
    
    public static final class Builder<T> implements ModelBuilder, Serializable {

        private List<PredicateTransform> additionalTransforms;
        private List<PredicateTransform> predicateTransforms;
        private Transform<T, T> resultTransform;

        private Builder() {

        }

        public static <T> Builder<T> create() {
            return new Builder<T>();
        }

        public List<PredicateTransform> getAdditionalTransforms() {
            return additionalTransforms;
        }

        public void setAdditionalTransforms(List<PredicateTransform> additionalTransforms) {
            this.additionalTransforms = additionalTransforms;
        }

        public List<PredicateTransform> getPredicateTransforms() {
            return predicateTransforms;
        }

        public void setPredicateTransforms(List<PredicateTransform> predicateTransforms) {
            this.predicateTransforms = predicateTransforms;
        }

        public Transform<T, T> getResultTransform() {
            return resultTransform;
        }

        public void setResultTransform(final Transform<T, T> resultTransform) {
            this.resultTransform = resultTransform;
        }

        @Override
        public LookupCustomizer<T> build() {
            if (additionalTransforms == null){
                additionalTransforms = new ArrayList<LookupCustomizer.PredicateTransform>();
            }
            if (predicateTransforms == null){
                predicateTransforms = new ArrayList<LookupCustomizer.PredicateTransform>();
            }
            return new LookupCustomizer<T>(this);
        }
    }

    public interface PredicateTransform {
        Predicate apply(Predicate input, Criteria criteria);
    }
    
    public interface Transform<P, R> {
        R apply(P input);
    }

    /**
     * f: x -> x.  This function just returns the passed in parameter.
     *
     * @param <I> the type the function acts on.
     */
    private static final class IndentityTransform<I> implements Transform<I, I> {

        @SuppressWarnings("unchecked")
        private static final Transform INSTANCE = new IndentityTransform();

        @SuppressWarnings("unchecked")
        public static <P, R> Transform<P, R> getInstance() {
            return INSTANCE;
        }

        @Override
        public I apply(final I input) {
            return input;
        }
    }

}
