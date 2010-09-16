/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.loader;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.XMLGregorianCalendar;
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
public class DateHelperTest
{

 public DateHelperTest ()
 {
 }

 @BeforeClass
 public static void setUpClass () throws Exception
 {
 }

 @AfterClass
 public static void tearDownClass () throws Exception
 {
 }

 @Before
 public void setUp ()
 {
 }

 @After
 public void tearDown ()
 {
 }

 /**
  * Test of asDate method, of class DateHelper.
  */
 @Test
 public void testAsDate ()
 {
  System.out.println ("asDate");
  String str = "2010-01-01";
  DateHelper instance = new DateHelper ();
  Calendar cal = new GregorianCalendar ();
  cal.set (2010, 0, 1);
  cal.set (2010, 0, 1, 0, 0, 0);
  cal.set (Calendar.MILLISECOND, 0);
  Date expResult = cal.getTime ();
  Date result = instance.asDate (str);
  assertEquals (expResult, result);
 }

 /**
  * Test of asXmlDate method, of class DateHelper.
  */
 @Test
 public void testAsXmlDate_String ()
 {
  System.out.println ("asXmlDate");
  String strDate = "2010-01-01";
  DateHelper instance = new DateHelper ();
  XMLGregorianCalendar result = instance.asXmlDate (strDate);
  assertEquals (2010, result.getYear ());
  assertEquals (01, result.getMonth ());
  assertEquals (01, result.getDay ());
 }

 /**
  * Test of asXmlDate method, of class DateHelper.
  */
 @Test
 public void testAsXmlDate_Date ()
 {
  System.out.println ("asXmlDate");
  Calendar cal = new GregorianCalendar ();
  cal.set (2010, 0, 1);
  Date date = cal.getTime ();
  DateHelper instance = new DateHelper ();
  XMLGregorianCalendar result = instance.asXmlDate (date);
  assertEquals (2010, result.getYear ());
  assertEquals (01, result.getMonth ());
  assertEquals (01, result.getDay ());
 }

 /**
  * Test of asYYYYMMDD method, of class DateHelper.
  */
 @Test
 public void testAsYYYYMMDD ()
 {
  System.out.println ("asYYYYMMDD");
  Calendar cal = new GregorianCalendar ();
  cal.set (2010, 0, 1);
  Date date = cal.getTime ();
  DateHelper instance = new DateHelper ();
  String expResult = "2010-01-01";
  String result = instance.asYYYY_MM_DD (date);
  assertEquals (expResult, result);
 }
}
