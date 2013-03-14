/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.rice.krms.impl.repository.mock;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;

/**
 *
 * @author nwright
 */
public class RuleManagementServiceMockImplTest {
    
    public RuleManagementServiceMockImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   
    @Test
    public void testCrud () {
        System.out.println("testCrud");
        ContextDefinition.Builder context = ContextDefinition.Builder.create("KS-SYS", "Course Rules");
        context.setTypeId("");
        
        PropositionDefinition.Builder prop = PropositionDefinition.Builder.create(null, PropositionType.SIMPLE.getCode(), null, null, null);
        prop.setId(null);
    }

}
