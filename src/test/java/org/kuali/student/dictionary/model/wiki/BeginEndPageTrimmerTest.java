/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model.wiki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.dictionary.command.run.RunConstants;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class BeginEndPageTrimmerTest implements RunConstants
{

 public BeginEndPageTrimmerTest ()
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
  * Test of trim method, of class BeginEndPageTrimmer.
  */
 @Test
 public void testTrim ()
  throws IOException
 {
  InputStream in = null;
  System.out.println ("trim");
  in =
   new FileInputStream (new File (ATP_DURATION_TYPE_SAVED_CONTRACT_PATH_ON_DISK));
  BeginEndPageTrimmer instance =
   new BeginEndPageTrimmer ("<body ", "</body>");
  InputStream expResult =
   new FileInputStream (new File (ATP_DURATION_TYPE_BODY_CONTRACT_PATH_ON_DISK));
  InputStream actResult = instance.trim (in);
  BufferedReader expResultRdr =
   new BufferedReader (new InputStreamReader (expResult));
  BufferedReader actResultRdr =
   new BufferedReader (new InputStreamReader (actResult));
  for (int i = 1; true; i ++)
  {
   String expLine = expResultRdr.readLine ();
   String actLine = actResultRdr.readLine ();
   if (expLine == null && actLine == null)
   {
    break;
   }
   if (expLine == null)
   {
    System.out.println ("expLine: " + expLine);
    System.out.println ("actLine: " + actLine);
    fail ("expected stream ended too soon, after " + i +
     " lines");
   }
   if (actLine == null)
   {
    System.out.println ("expLine: " + expLine);
    System.out.println ("actLine: " + actLine);
    fail ("actual stream ended too soon, after " + i +
     " lines");
   }
   if ( ! actLine.equals (expLine))
   {
    System.out.println ("expLine: " + expLine);
    System.out.println ("actLine: " + actLine);
    fail ("line #" + i + " is different");
   }
  }
 }

}
