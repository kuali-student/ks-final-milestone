/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.class1.state.decorators;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.class1.state.service.impl.StateServiceMockDataImpl;

/**
 *
 * @author nwright
 */
public class TestStateServiceCacheDecorator {
    
    public TestStateServiceCacheDecorator() {
    }

    @BeforeClass
    public static void setUpClass()
            throws Exception {
    }

    @AfterClass
    public static void tearDownClass()
            throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testLifecycleCache()
            throws Exception {
        System.out.println("testLifecycleCache");
        ContextInfo contextInfo = new ContextInfo ();
        contextInfo.setPrincipalId("TESTUSER");
        
        StateServiceMockDataImpl mock = new StateServiceMockDataImpl ();
        StateServiceCacheDecorator instance = new StateServiceCacheDecorator();
        instance.setNextDecorator(mock);
        
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create ();
        
        // duplicated to get it the 2nd time from the cache
        assertEquals (mock.searchForStateKeys(criteria.build(), contextInfo), instance.searchForStateKeys(criteria.build(), contextInfo));
        assertEquals (mock.searchForStateKeys(criteria.build(), contextInfo), instance.searchForStateKeys(criteria.build(), contextInfo));
        
        // duplicated to get it the 2nd time from the cache
        assertEquals (mock.searchForStates(criteria.build(), contextInfo), instance.searchForStates(criteria.build(), contextInfo));
        assertEquals (mock.searchForStates(criteria.build(), contextInfo), instance.searchForStates(criteria.build(), contextInfo));
        
        // duplicated to get it the 2nd time from the cache
        assertEquals (mock.searchForLifecycleKeys(criteria.build(), contextInfo), instance.searchForLifecycleKeys(criteria.build(), contextInfo));
        assertEquals (mock.searchForLifecycleKeys(criteria.build(), contextInfo), instance.searchForLifecycleKeys(criteria.build(), contextInfo));
        
        // duplicated to get it the 2nd time from the cache
        assertEquals (mock.searchForLifecycles(criteria.build(), contextInfo), instance.searchForLifecycles(criteria.build(), contextInfo));
        assertEquals (mock.searchForLifecycles(criteria.build(), contextInfo), instance.searchForLifecycles(criteria.build(), contextInfo));
        
    }

}
