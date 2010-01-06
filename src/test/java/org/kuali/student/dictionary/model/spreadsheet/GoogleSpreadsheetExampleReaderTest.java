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

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
public class GoogleSpreadsheetExampleReaderTest
{

 public GoogleSpreadsheetExampleReaderTest ()
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
 private static String APPPLICATION = "kuali-mit-1";
 private static String PUBLISHED_SPREADSHEET =
  "Unit Test Spreadsheet 1 - Published";
 private static String UNPUBLISHED_SPREADSHEET =
  "Unit Test Spreadsheet 2 - Unpublished";
 private static String WORKSHEET = "TestWorksheet2";
 private static String PUBLISHED_KEY = "tZ5p8stPQLHYbfUU7AxWAjg";
 private static String UNPUBLISHED_KEY = "tk4cpzowoRNN3UO2QXIyHJQ";
 public static String FEEDS_URL =
  "http://spreadsheets.google.com/feeds";

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
 public void testFindAuthenticatedPrivateAccessToPublishedWorksheet ()
  throws AuthenticationException,
         MalformedURLException,
         IOException,
         ServiceException
 {
  System.out.println ("testFindAuthenticatedPrivateAccessToPublishedWorksheet");
  SpreadsheetService service = new SpreadsheetService (APPPLICATION);
  service.setUserCredentials (USER_ID, PASSWORD);
  String visibility = "private";
  String projection = "values";
  URL url = new URL (FEEDS_URL + "/worksheets/" + PUBLISHED_KEY + "/" +
   visibility + "/" + projection);
  WorksheetFeed feed = service.getFeed (url, WorksheetFeed.class);
  // search by name
  List<WorksheetEntry> worksheets = feed.getEntries ();
  boolean found = false;
  for (WorksheetEntry sheet : worksheets)
  {
   System.out.println ("\t" + sheet.getTitle ().getPlainText ());
   if (sheet.getTitle ().getPlainText ().equals (WORKSHEET))
   {
    found = true;
   }
  }
  assertTrue (found);
 }


 /**
  * Test of find method, of class GoogleSpreadsheetReader.
  */
// @Test
 public void testFindAuthenticatedPublicAccessToPublishedWorksheet ()
  throws AuthenticationException,
         MalformedURLException,
         IOException,
         ServiceException
 {
  System.out.println ("testFindAuthenticatedPublicAccessToPublishedWorksheet");
  SpreadsheetService service = new SpreadsheetService (APPPLICATION);
  service.setUserCredentials (USER_ID, PASSWORD);
  String visibility = "public";
  String projection = "values";
  URL url = new URL (FEEDS_URL + "/worksheets/" + PUBLISHED_KEY + "/" +
   visibility + "/" + projection);
  WorksheetFeed feed = service.getFeed (url, WorksheetFeed.class);
  // search by name
  List<WorksheetEntry> worksheets = feed.getEntries ();
  boolean found = false;
  for (WorksheetEntry sheet : worksheets)
  {
   System.out.println ("\t" + sheet.getTitle ().getPlainText ());
   if (sheet.getTitle ().getPlainText ().equals (WORKSHEET))
   {
    found = true;
   }
  }
  assertTrue (found);
 }


 /**
  * Test of find method, of class GoogleSpreadsheetReader.
  */
// @Test
 public void testFindUnauthenticatedPublicAccessToPublishedWorksheet ()
  throws AuthenticationException,
         MalformedURLException,
         IOException,
         ServiceException
 {
  System.out.println ("testFindUnauthenticatedPublicAccessToPublishedWorksheet");
  SpreadsheetService service = new SpreadsheetService (APPPLICATION);
  //service.setUserCredentials (USER_ID, PASSWORD);
  String visibility = "public";
  String projection = "values";
  URL url = new URL (FEEDS_URL + "/worksheets/" + PUBLISHED_KEY + "/" +
   visibility + "/" + projection);
  WorksheetFeed feed = service.getFeed (url, WorksheetFeed.class);
  // search by name
  List<WorksheetEntry> worksheets = feed.getEntries ();
  boolean found = false;
  for (WorksheetEntry sheet : worksheets)
  {
   System.out.println ("\t" + sheet.getTitle ().getPlainText ());
   if (sheet.getTitle ().getPlainText ().equals (WORKSHEET))
   {
    found = true;
   }
  }
  assertTrue (found);
 }

}