package org.kuali.student.r2.common.criteria.transform;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.kuali.rice.core.api.criteria.CriteriaStringValue;
import org.kuali.rice.core.api.criteria.EqualPredicate;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.framework.persistence.jpa.criteria.Criteria;

import static org.kuali.rice.core.api.criteria.PredicateFactory.like;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;

public class Keyword2LikeTransform
        extends BaseTransform {

    public static final String KEYWORD_SEARCH_ATTRIBUTE = "keywordSearch";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String DESCR_PLAIN_ATTRIBUTE = "descrPlain";
    public static final Set<String> DEFAULT_LIKE_ATTRIBUTES;

    static {
        Set<String> set = new LinkedHashSet();
        set.add(NAME_ATTRIBUTE);
        set.add(DESCR_PLAIN_ATTRIBUTE);
        DEFAULT_LIKE_ATTRIBUTES = set;
    }
    private String keywordAttribute = KEYWORD_SEARCH_ATTRIBUTE;
    private Set<String> likeAttributes;


    public String getKeywordAttribute() {
        return keywordAttribute;
    }

    public void setKeywordAttribute(String keywordAttribute) {
        this.keywordAttribute = keywordAttribute;
    }

    public Set<String> getLikeAttributes() {
        return likeAttributes;
    }

    public void setLikeAttributes(Set<String> likeAttributes) {
        this.likeAttributes = likeAttributes;
    }

    /**
     * default constructor
     */
    public Keyword2LikeTransform() {
    }

    @Override
    public Predicate apply(final Predicate input, Criteria criteria) {
        if (input instanceof EqualPredicate) {
            EqualPredicate ppp = (EqualPredicate) input;
            String pp = ppp.getPropertyPath();
            CriteriaStringValue cv = (CriteriaStringValue) ppp.getValue();
            List<Predicate> predicates = new ArrayList<Predicate>();
            if (keywordAttribute.equalsIgnoreCase(pp)) {
                String value = cv.getValue();
                for (String likeAttribute : likeAttributes) {
                    predicates.add(like(likeAttribute, "%" + value + "%"));
                }
                Predicate predicate = or(predicates.toArray(new Predicate[predicates.size()]));
                return predicate;
            }
        }
        return input;
    }
}
