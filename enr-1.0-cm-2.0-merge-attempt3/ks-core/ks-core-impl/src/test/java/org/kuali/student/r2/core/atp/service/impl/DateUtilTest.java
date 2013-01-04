/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.atp.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.r2.core.class1.atp.service.impl.DateUtil;

import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class DateUtilTest {

    public DateUtilTest() {
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

    private Date str2Date(String str) {
        if (str == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
        try {
            Date date = df.parse(str);
            return date;
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Bad date " + str, ex);
        }
    }

    /**
     * Test of startOfDay method, of class DateUtil.
     */
    @Test
    public void testStartOfDay() {
        System.out.println("startOfDay");
        Date date = str2Date("2012-01-01 12:12:12.9");
        Date expResult = str2Date("2012-01-01 00:00:00.0");
        Date result = DateUtil.startOfDay(date);
        System.out.println("Expected=" + expResult);
        System.out.println("Result=" + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of endOfDay method, of class DateUtil.
     */
    @Test
    public void testEndOfDay() {
        System.out.println("endOfDay");
        Date date = str2Date("2012-01-01 12:12:12.9");
        Date expResult = str2Date("2012-01-01 23:59:59.9");
        Date result = DateUtil.endOfDay(date);
        System.out.println("Expected=" + expResult);
        System.out.println("Result=" + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of nullIfNotDateRange method, of class DateUtil.
     */
    @Test
    public void testNullIfNotDateRange() {
        System.out.println("nullIfNotDateRange");
        boolean isDateRange = true;
        Date endDate = str2Date("2012-01-01 12:12:12.9");
        Date expResult = str2Date("2012-01-01 12:12:12.9");
        Date result = DateUtil.nullIfNotDateRange(isDateRange, endDate);
        assertEquals(expResult, result);

        isDateRange = false;
        endDate = str2Date("2012-01-01 12:12:12.9");
        expResult = null;
        result = DateUtil.nullIfNotDateRange(isDateRange, endDate);
        assertEquals(expResult, result);
    }

    /**
     * Test of startOfDayfIsAllDay method, of class DateUtil.
     */
    @Test
    public void testStartOfDayfIsAllDay() {
        System.out.println("startOfDayfIsAllDay");
        boolean isAllDay = false;
        Date date = str2Date("2012-01-01 12:12:12.9");
        Date expResult = str2Date("2012-01-01 12:12:12.9");
        Date result = DateUtil.startOfDayfIsAllDay(isAllDay, date);
        assertEquals(expResult, result);

        isAllDay = true;
        date = str2Date("2012-01-01 12:12:12.9");
        expResult = str2Date("2012-01-01 00:00:00.0");
        result = DateUtil.startOfDayfIsAllDay(isAllDay, date);
        assertEquals(expResult, result);
    }

    /**
     * Test of endOfDayIfIsAllDay method, of class DateUtil.
     */
    @Test
    public void testEndOfDayIfIsAllDay() {
        System.out.println("endOfDayIfIsAllDay");
        boolean isAllDay = false;
        Date date = str2Date("2012-01-01 12:12:12.9");
        Date expResult = str2Date("2012-01-01 12:12:12.9");
        Date result = DateUtil.endOfDayIfIsAllDay(isAllDay, date);
        assertEquals(expResult, result);
        
        isAllDay = true;
        date = str2Date("2012-01-01 12:12:12.9");
        expResult = str2Date("2012-01-01 23:59:59.9");
        result = DateUtil.endOfDayIfIsAllDay(isAllDay, date);
        assertEquals(expResult, result);
    }
}
