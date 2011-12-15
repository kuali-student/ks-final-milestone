/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.population.service;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;

/**
 *
 * @author nwright
 */
public class PopulationServiceMockImplTest {

    public PopulationServiceMockImplTest() {
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
    private static final String TEST_PRINCIPAL_ID1 = "testPrincipalId1";
    private static final String TEST_PRINCIPAL_ID2 = "testPrincipalId2";

    private ContextInfo getContext() {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipalId1");
        return context;
    }
    private PopulationService instance = new PopulationServiceMockImpl();

    /**
     * Test of createPopulation method, of class PopulationServiceMockImpl.
     */
    @Test
    public void testPopulationCrud() throws Exception {
        System.out.println("createPopulation");
        ContextInfo context = getContext();
        // create
        PopulationInfo info = new PopulationInfo();
        info.setTypeKey(PopulationServiceConstants.POPULATION_TYPE_KEY);
        info.setStateKey(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
        info.setKey(PopulationServiceConstants.SUMMER_ONLY_STUDENTS_POPULATION_KEY);
        info.setName("Summer Only Studetns");
        Date before = new Date();
        PopulationInfo result = instance.createPopulation(info, context);
        Date after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals(info.getKey(), result.getKey());
        assertEquals(info.getTypeKey(), result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().before(before)) {
            fail("create time should not be before the call");
        }
        if (result.getMeta().getCreateTime().after(after)) {
            fail("create time should not be after the call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getUpdateId());
        assertNotNull(result.getMeta().getVersionInd());

        // READ/get
        info = new PopulationInfo(result);

        result = instance.getPopulation(info.getKey(), context);
        assertEquals(result.getKey(), info.getKey());
        assertEquals(result.getTypeKey(), info.getTypeKey());
        assertEquals(result.getStateKey(), info.getStateKey());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(result.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(result.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(result.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());
        assertEquals(result.getMeta().getVersionInd(), info.getMeta().getVersionInd());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());

        // update
        info = new PopulationInfo(result);
        info.setName("new name");
        context.setPrincipalId(TEST_PRINCIPAL_ID2);
        before = new Date();
        result = instance.updatePopulation(info.getKey(), info, context);
        after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals(info.getKey(), result.getKey());
        assertEquals(info.getTypeKey(), result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(info.getName(), result.getName());
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().after(before)) {
            fail("create time should be before the update call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(TEST_PRINCIPAL_ID2, result.getMeta().getUpdateId());
        if (info.getMeta().getVersionInd().compareTo(result.getMeta().getVersionInd()) >= 0) {
            fail("version ind should be lexically greater than the old version id");
        }

        // delete
    }

    /**
     * Test of createInstruction method, of class PopulationServiceMockImpl.
     */
    @Test
    public void testPopulationRuleCrud() throws Exception {
        System.out.println("createPopulationRule");
        PopulationRuleInfo populationRuleInfo = new PopulationRuleInfo();
        populationRuleInfo.setTypeKey(PopulationServiceConstants.POPULATION_RULE_TYPE_KEY);
        populationRuleInfo.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        ContextInfo context = getContext();
        Date before = new Date();
        PopulationRuleInfo result = instance.createPopulationRule(populationRuleInfo, context);
        Date after = new Date();
        if (result == populationRuleInfo) {
            fail("returned object should not be the same as the one passed in");
        }
        assertNotNull(result.getId());
        assertEquals(populationRuleInfo.getTypeKey(), result.getTypeKey());
        assertEquals(populationRuleInfo.getStateKey(), result.getStateKey());
//        assertEquals(populationRuleInfo.getPersonId(), result.getPersonId());
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().before(before)) {
            fail("create time should not be before the call");
        }
        if (result.getMeta().getCreateTime().after(after)) {
            fail("create time should not be after the call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getUpdateId());
        assertNotNull(result.getMeta().getVersionInd());
        
//        TODO: test attaching rule to a population
        
    }
}
