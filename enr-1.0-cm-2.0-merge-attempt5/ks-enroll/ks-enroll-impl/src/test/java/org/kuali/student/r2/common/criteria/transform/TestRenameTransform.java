/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.common.criteria.transform;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.rice.core.api.criteria.AndPredicate;
import org.kuali.rice.core.api.criteria.EqualPredicate;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.framework.persistence.jpa.criteria.Criteria;
import org.kuali.student.r2.common.criteria.LookupCustomizer.PredicateTransform;

/**
 *
 * @author nwright
 */
public class TestRenameTransform {

    public TestRenameTransform() {
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
    public void testRenameTransform() {
        System.out.println("renameTransform");

        Map<String, String> renames = null;
        PredicateTransform transform = null;
        
        Criteria criteria = null;

        // simple case
        renames = new HashMap<String, String>();
        renames.put("oldName1", "newName1");  
        transform = new RenameTransform(renames);
        Predicate input = PredicateFactory.equal("oldName1", "value1");
        Predicate output = transform.apply(input, criteria);
        assertEquals(EqualPredicate.class, output.getClass());
        assertNotSame(input, output);
        EqualPredicate eqp = (EqualPredicate) output;
        assertEquals("newName1", eqp.getPropertyPath());
        assertEquals("value1", eqp.getValue().getValue());

        // composite case
        renames = new HashMap<String, String>();
        renames.put("oldName1", "newName1");
        transform = new RenameTransform(renames);
        Predicate input1 = PredicateFactory.equal("oldName1", "value1");
        Predicate input2 = PredicateFactory.equal("oldName1", "value2");
        Predicate input3 = PredicateFactory.equal("oldName2", "value3");

        input = PredicateFactory.and(input1, input2, input3);
        output = transform.apply(input, criteria);
        assertEquals(AndPredicate.class, output.getClass());
//        assertNotSame(input, output);
        AndPredicate andp = (AndPredicate) output;
        assertEquals(3, andp.getPredicates().size());
        boolean found1 = false;
        boolean found2 = false;
        boolean found3 = false;
        for (Predicate p : andp.getPredicates()) {
            eqp = (EqualPredicate) p;
            if (eqp.getValue().getValue().equals("value1")) {
                assertEquals("newName1", eqp.getPropertyPath());
                found1 = true;
            } else if (eqp.getValue().getValue().equals("value2")) {
                assertEquals("newName1", eqp.getPropertyPath());
                found2 = true;
            } else if (eqp.getValue().getValue().equals("value3")) {
                assertEquals("oldName2", eqp.getPropertyPath());
                found3 = true;
            } else {
                fail("unknown value " + eqp.getValue().getValue());
            }
        }
        assertTrue(found1);
        assertTrue(found2);
        assertTrue(found3);

        // test composite with multiple renames
        renames = new HashMap<String, String>();
        renames.put("oldName1", "newName1");
        renames.put("oldName2", "newName2");    
        transform = new RenameTransform(renames);
        input1 = PredicateFactory.equal("oldName1", "value1");
        input2 = PredicateFactory.equal("oldName1", "value2");
        input3 = PredicateFactory.equal("oldName2", "value3");

        input = PredicateFactory.and(input1, input2, input3);
        output = transform.apply(input, criteria);
        assertEquals(AndPredicate.class, output.getClass());
        assertNotSame(input, output);
        andp = (AndPredicate) output;
        assertEquals(3, andp.getPredicates().size());
        found1 = false;
        found2 = false;
        found3 = false;
        for (Predicate p : andp.getPredicates()) {
            eqp = (EqualPredicate) p;
            if (eqp.getValue().getValue().equals("value1")) {
                assertEquals("newName1", eqp.getPropertyPath());
                found1 = true;
            } else if (eqp.getValue().getValue().equals("value2")) {
                assertEquals("newName1", eqp.getPropertyPath());
                found2 = true;
            } else if (eqp.getValue().getValue().equals("value3")) {
                assertEquals("newName2", eqp.getPropertyPath());
                found3 = true;
            } else {
                fail("unknown value " + eqp.getValue().getValue());
            }
        }
        assertTrue(found1);
        assertTrue(found2);
        assertTrue(found3);
    }
}
