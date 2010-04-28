/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model.wiki;

import java.io.File;
import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.dictionary.command.run.RunConstants;
import org.w3c.dom.Document;

/**
 *
 * @author nwright
 */
public class PageHelperTest implements RunConstants
{

 private static final String JSESSIONID =
  "213C25A1E57C50C27A5E1A7B32E4F782.Kuali3_1Engine";

 public PageHelperTest ()
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
  * Test of getDocument method, of class PageHelper.
  */
 @Test
 public void testGetDocument_URL_String ()
 {
  System.out.println ("getDocument");
//  URL url = new UrlHelper (ATP_CONTRACT_PATH_ON_WIKI).getUrl ();
//  String jSessionId = JSESSIONID;
//  PageHelper instance = new PageHelper ();
//  instance.setTrimmer (new BodyOnlyPageTrimmer ());
//  Document result = instance.getDocument (url, jSessionId);
 }

 /**
  * Test of getDocument method, of class PageHelper.
  */
 @Test
 public void testGetDocument_File ()
 {
  System.out.println ("getDocument");
  File file = new File (ATP_CONTRACT_PATH_ON_DISK);
  PageHelper instance = new PageHelper ();
  instance.setTrimmer (new BodyOnlyPageTrimmer ());
  Document result = instance.getDocument (file);
 }

}
