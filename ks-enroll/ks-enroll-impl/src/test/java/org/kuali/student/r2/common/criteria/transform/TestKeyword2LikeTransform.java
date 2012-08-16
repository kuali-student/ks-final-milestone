/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.common.criteria.transform;

import org.kuali.rice.core.api.criteria.EqualPredicate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import static org.junit.Assert.*;
import org.junit.Test;
import org.kuali.rice.core.api.criteria.OrPredicate;
import org.kuali.rice.core.framework.persistence.jpa.criteria.Criteria;

/**
 *
 * @author nwright
 */
public class TestKeyword2LikeTransform {

    public TestKeyword2LikeTransform() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testKeyword2LikeTransform() {
        System.out.println("testKeyword2LikeTransform");

        Keyword2LikeTransform transform = new Keyword2LikeTransform ();
        transform.setKeywordAttribute(Keyword2LikeTransform.KEYWORD_SEARCH_ATTRIBUTE);
        transform.setLikeAttributes(Keyword2LikeTransform.DEFAULT_LIKE_ATTRIBUTES);

        Predicate input = PredicateFactory.equal(Keyword2LikeTransform.KEYWORD_SEARCH_ATTRIBUTE, "value1");
        System.out.println("input=" + input);
        Criteria criteria = null;
        Predicate output = transform.apply(input, criteria);
        assertEquals(OrPredicate.class, output.getClass());
        System.out.println("output=" + output);
        
        input = PredicateFactory.equal("name", "value1");
        System.out.println("input=" + input);
        output = transform.apply(input, criteria);
        assertEquals(EqualPredicate.class, output.getClass());
        System.out.println("output=" + output);
        
    }
    
}
