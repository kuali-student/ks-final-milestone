/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.student.core.person.service.impl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class NameParserTest {
    
    public NameParserTest() {
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

    /**
     * Test of parseName method, of class NameParser.
     */
    @Test
    public void testParseName() {
        System.out.println("parseName");
        NameParser instance = new NameParser();
        String[] expResult = {null, "Norman", "J", "Wright", null};
        String[] result = instance.parseName("Wright, Norman J");
        assertArrayEquals(expResult, result);
    }
    
}
