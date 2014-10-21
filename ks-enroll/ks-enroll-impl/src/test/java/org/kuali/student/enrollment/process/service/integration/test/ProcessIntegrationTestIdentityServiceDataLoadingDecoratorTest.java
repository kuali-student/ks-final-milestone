/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.process.service.integration.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.student.kim.permission.map.IdentityServiceMapImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 *
 * @author nwright
 */
public class ProcessIntegrationTestIdentityServiceDataLoadingDecoratorTest {

    public ProcessIntegrationTestIdentityServiceDataLoadingDecoratorTest() {
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
    public void testTestData() {
        IdentityService service = new IdentityServiceMapImpl();
        service = new ProcessIntegrationTestIdentityServiceDataLoadingDecorator(service);
        Entity entity = null;
        
        entity = service.getEntity(ProcessIntegrationTestConstants.PERSON_ID_AMBER_HOPKINS_2155);
        assertEquals(ProcessIntegrationTestConstants.PERSON_ID_AMBER_HOPKINS_2155, entity.getId());
        assertNull (entity.getBioDemographics().getDeceasedDate());
        
        entity = service.getEntity(ProcessIntegrationTestConstants.PERSON_ID_BARBARA_HARRIS_2016);
        assertEquals(ProcessIntegrationTestConstants.PERSON_ID_BARBARA_HARRIS_2016, entity.getId());
        assertNull (entity.getBioDemographics().getDeceasedDate());
        
        entity = service.getEntity(ProcessIntegrationTestConstants.PERSON_ID_BETTY_MARTIN_2005);
        assertEquals(ProcessIntegrationTestConstants.PERSON_ID_BETTY_MARTIN_2005, entity.getId());
        assertNull (entity.getBioDemographics().getDeceasedDate());
        
        entity = service.getEntity(ProcessIntegrationTestConstants.PERSON_ID_CLIFFORD_RIDDLE_2397);
        assertEquals(ProcessIntegrationTestConstants.PERSON_ID_CLIFFORD_RIDDLE_2397, entity.getId());
        assertNull (entity.getBioDemographics().getDeceasedDate());
        
        entity = service.getEntity(ProcessIntegrationTestConstants.PERSON_ID_EDDIE_PITTMAN_2406);
        assertEquals(ProcessIntegrationTestConstants.PERSON_ID_EDDIE_PITTMAN_2406, entity.getId());
        assertNull (entity.getBioDemographics().getDeceasedDate());
        
        entity = service.getEntity(ProcessIntegrationTestConstants.PERSON_ID_JOHNNY_MANNING_2374);
        assertEquals(ProcessIntegrationTestConstants.PERSON_ID_JOHNNY_MANNING_2374, entity.getId());
        assertNull (entity.getBioDemographics().getDeceasedDate());
        
        entity = service.getEntity(ProcessIntegrationTestConstants.PERSON_ID_KARA_STONE_2272);
        assertEquals(ProcessIntegrationTestConstants.PERSON_ID_KARA_STONE_2272, entity.getId());
        assertNotNull (entity.getBioDemographics().getDeceasedDate());
        
        entity = service.getEntity(ProcessIntegrationTestConstants.PERSON_ID_NINA_WELCH_2166);
        assertEquals(ProcessIntegrationTestConstants.PERSON_ID_NINA_WELCH_2166, entity.getId());
        assertNull (entity.getBioDemographics().getDeceasedDate());
        
        entity = service.getEntity(ProcessIntegrationTestConstants.PERSON_ID_TRACY_BURTON_2132);
        assertEquals(ProcessIntegrationTestConstants.PERSON_ID_TRACY_BURTON_2132, entity.getId());
        assertNull (entity.getBioDemographics().getDeceasedDate());

    }
}
