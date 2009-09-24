/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.search;

import java.util.List;
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
public class SpreadsheetLoaderTest
{

 public SpreadsheetLoaderTest ()
 {
 }

 @BeforeClass
 public static void setUpClass ()
  throws Exception
 {
 }

 @AfterClass
 public static void tearDownClass ()
  throws Exception
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
  * Test of getSearchRows method, of class SpreadsheetLoader.
  */
 @Test
 public void testGetSearchRows ()
 {
  System.out.println ("getSearchRows");

  SpreadsheetLoader instance = new SpreadsheetLoader ();
  List expResult = null;
  List result = instance.getSearchRows ();
  assertEquals (expResult, result);
  // TODO review the generated test code and remove the default call to fail.
  fail ("The test case is a prototype.");
 }

}
