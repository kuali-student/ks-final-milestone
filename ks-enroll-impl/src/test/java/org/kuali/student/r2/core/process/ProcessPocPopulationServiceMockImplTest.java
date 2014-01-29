/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.r2.common.dto.ContextInfo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author nwright
 */
public class ProcessPocPopulationServiceMockImplTest {

    public ProcessPocPopulationServiceMockImplTest() {
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
     * Test of isMember method, of class ProcessPocPopulationServiceMockImpl.
     */
    @Test
    public void testPocMethods() throws Exception {
        System.out.println("test poc methods");

        ContextInfo context = new ContextInfo();
        context.setPrincipalId("POC-tester");

        ProcessPocPopulationServiceMockImpl instance = new ProcessPocPopulationServiceMockImpl();
        assertTrue(instance.isMemberAsOfDate("2155", ProcessPocPopulationServiceMockImpl.ALL_STUDENTS, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate("2016", ProcessPocPopulationServiceMockImpl.ALL_STUDENTS, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate("2005", ProcessPocPopulationServiceMockImpl.ALL_STUDENTS, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate("2397", ProcessPocPopulationServiceMockImpl.ALL_STUDENTS, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate("2406", ProcessPocPopulationServiceMockImpl.ALL_STUDENTS, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate("2374", ProcessPocPopulationServiceMockImpl.ALL_STUDENTS, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate("2272", ProcessPocPopulationServiceMockImpl.ALL_STUDENTS, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate("2166", ProcessPocPopulationServiceMockImpl.ALL_STUDENTS, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate("2132", ProcessPocPopulationServiceMockImpl.ALL_STUDENTS, context.getCurrentDate(), context));

        assertTrue(instance.isMemberAsOfDate("2155", ProcessPocPopulationServiceMockImpl.SUMMER_ONLY_STUDENTS, context.getCurrentDate(), context));
        assertFalse(instance.isMemberAsOfDate("2016", ProcessPocPopulationServiceMockImpl.SUMMER_ONLY_STUDENTS, context.getCurrentDate(), context));
        assertFalse(instance.isMemberAsOfDate("2005", ProcessPocPopulationServiceMockImpl.SUMMER_ONLY_STUDENTS, context.getCurrentDate(), context));

    }
}
