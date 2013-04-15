/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.common.criteria.transform;

import org.kuali.rice.core.api.criteria.AndPredicate;
import org.kuali.rice.core.api.criteria.EqualPredicate;
import org.junit.Ignore;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.kuali.rice.core.api.criteria.LookupCustomizer;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class TransformTest {

    public TransformTest() {
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

    /**
     * Test the Rename Transform
     */
    @Ignore
    public void testRenameTransform() {
        System.out.println("renameTransform");

        Map<String, String> renames = null;
        LookupCustomizer.Transform<Predicate, Predicate> transform = null;

        // simple case
        renames = new HashMap<String, String>();
        renames.put("oldName1", "newName1");
        // TODO: Rewrote the Rename Transform to honor the RiceContract then configure it here to test it        
//        transform = new RenameTransform(renames);
        Predicate input = PredicateFactory.equal("oldName1", "value1");
        Predicate output = transform.apply(input);
        assertEquals(EqualPredicate.class, output.getClass());
        assertNotSame(input, output);
        EqualPredicate eqp = (EqualPredicate) output;
        assertEquals("newName1", eqp.getPropertyPath());
        assertEquals("value1", eqp.getValue().getValue());

        // composite case
        renames = new HashMap<String, String>();
        renames.put("oldName1", "newName1");
        // TODO: Rewrote the Rename Transform to honor the RiceContract then configure it here to test it        
//        transform = new RenameTransform(renames);
        Predicate input1 = PredicateFactory.equal("oldName1", "value1");
        Predicate input2 = PredicateFactory.equal("oldName1", "value2");
        Predicate input3 = PredicateFactory.equal("oldName2", "value3");

        input = PredicateFactory.and(input1, input2, input3);
        output = transform.apply(input);
        assertEquals(AndPredicate.class, output.getClass());
        assertNotSame(input, output);
        AndPredicate andp = (AndPredicate) output;
        assertEquals(3, andp.getPredicates().size());
        boolean found1 = false;
        boolean found2 = false;
        boolean found3 = false;
        for (Predicate p : andp.getPredicates()) {
            eqp = (EqualPredicate) output;
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
        // TODO: Rewrote the Rename Transform to honor the RiceContract then configure it here to test it        
//        transform = new RenameTransform(renames);
        input1 = PredicateFactory.equal("oldName1", "value1");
        input2 = PredicateFactory.equal("oldName1", "value2");
        input3 = PredicateFactory.equal("oldName2", "value3");

        input = PredicateFactory.and(input1, input2, input3);
        output = transform.apply(input);
        assertEquals(AndPredicate.class, output.getClass());
        assertNotSame(input, output);
        andp = (AndPredicate) output;
        assertEquals(3, andp.getPredicates().size());
        found1 = false;
        found2 = false;
        found3 = false;
        for (Predicate p : andp.getPredicates()) {
            eqp = (EqualPredicate) output;
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
