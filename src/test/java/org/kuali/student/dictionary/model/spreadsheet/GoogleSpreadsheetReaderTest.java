/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.dictionary.model.spreadsheet;

import org.kuali.student.dictionary.model.spreadsheet.GoogleSpreadsheetReader;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
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
public class GoogleSpreadsheetReaderTest
{

 public GoogleSpreadsheetReaderTest ()
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

 private static String USER_ID = "nwright@mit.edu";
 private static String PASSWORD = "xxxxx";
 private static String PUBLISHED_SPREADSHEET =
  "Unit Test Spreadsheet 1 - Published";
 private static String UNPUBLISHED_SPREADSHEET =
  "Unit Test Spreadsheet 2 - Unpublished";
 private static String WORKSHEET = "TestWorksheet2";
 private static String PUBLISHED_KEY = "tZ5p8stPQLHYbfUU7AxWAjg";
 private static String UNPUBLISHED_KEY = "tk4cpzowoRNN3UO2QXIyHJQ";

 /**
  * Test of write method, of class DictionaryWriter.
  */
 @Test
 public void testTrue ()
 {
  assertEquals (true, true);
 }

 /**
  * Test of find method, of class GoogleSpreadsheetReader.
  */
// @Test
 public void testFindAuthenticatedAccessToUnpublishedWorksheet ()
 {
  System.out.println ("testFindAuthenticatedAccessToUnpublishedWorksheet");
  GoogleSpreadsheetReader instance = new GoogleSpreadsheetReader ();
  instance.setUserCredentials (USER_ID, PASSWORD);
  instance.setSpreadsheetTitle (UNPUBLISHED_SPREADSHEET);
  instance.setVisibility ("private");
  instance.setProjection ("full");
  WorksheetEntry entry = instance.getWorksheetEntry (WORKSHEET);
  assertNotNull (entry);
 }

 /**
  * Test of find method, of class GoogleSpreadsheetReader.
  */
// @Test
 public void testFindAuthenticatedAccessToPublishedWorksheet ()
 {
  System.out.println ("findWorksheetByUserIdTitlePublicBasic");
  GoogleSpreadsheetReader instance = new GoogleSpreadsheetReader ();
  instance.setUserCredentials (USER_ID, PASSWORD);
  instance.setSpreadsheetTitle (PUBLISHED_SPREADSHEET);
  instance.setVisibility ("private");
  instance.setProjection ("full");
  WorksheetEntry entry = instance.getWorksheetEntry (WORKSHEET);
  assertNotNull (entry);
 }

 /**
  * Test of find method, of class GoogleSpreadsheetReader.
  */
// @Test
 public void testFindAuthenticatedKeyedAccessToUnpublishedWorksheet ()
 {
  System.out.println ("testFindAuthenticatedKeyedAccessToUnpublishedWorksheet");
  GoogleSpreadsheetReader instance = new GoogleSpreadsheetReader ();
  instance.setKey (UNPUBLISHED_KEY);
  instance.setUserCredentials (USER_ID, PASSWORD);
  instance.setVisibility ("private");
  instance.setProjection ("full");
  WorksheetEntry entry = instance.getWorksheetEntry (WORKSHEET);
  assertNotNull (entry);
 }

 /**
  * Test of find method, of class GoogleSpreadsheetReader.
  */
// @Test
 public void testFindUnauthenticatedKeyedAccessToPublishedWorksheet ()
 {
  System.out.println ("testFindUnauthenticatedKeyedAccessToPublishedWorksheet");
  GoogleSpreadsheetReader instance = new GoogleSpreadsheetReader ();
  instance.setKey (PUBLISHED_KEY);
  instance.setVisibility ("public");
  instance.setProjection ("values");
  WorksheetEntry entry = instance.getWorksheetEntry (WORKSHEET);
  assertNotNull (entry);
 }

}