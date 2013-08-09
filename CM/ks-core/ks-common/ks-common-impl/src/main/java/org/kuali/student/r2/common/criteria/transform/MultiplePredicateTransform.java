package org.kuali.student.r2.common.criteria.transform;

import java.util.ArrayList;
import java.util.List;
import org.kuali.rice.core.api.criteria.LookupCustomizer;
import org.kuali.rice.core.api.criteria.LookupCustomizer.Transform;
import org.kuali.rice.core.api.criteria.Predicate;

/**
 * A transform that simply applies a set of other transforms sequentially to a predicate
 * @author nwright
 */
public class MultiplePredicateTransform implements LookupCustomizer.Transform<Predicate,Predicate>{
    

    private List<LookupCustomizer.Transform<Predicate,Predicate>> transforms = 
            new ArrayList<LookupCustomizer.Transform<Predicate,Predicate>> ();

    public MultiplePredicateTransform() {
    }

    /** 
     * Convenience constructor
     * @param transforms 
     */
    public MultiplePredicateTransform(List<LookupCustomizer.Transform<Predicate,Predicate>> transforms) {
        this.transforms = transforms;
    }

    public List<Transform<Predicate, Predicate>> getTransforms() {
        return transforms;
    }

    public void setTransforms(List<Transform<Predicate, Predicate>> transforms) {
        this.transforms = transforms;
    }

    @Override
    public Predicate apply(Predicate p) {
        for (Transform<Predicate,Predicate> transform : transforms) {
           p = transform.apply(p); 
        }
        return p;
    }
    




    
}
