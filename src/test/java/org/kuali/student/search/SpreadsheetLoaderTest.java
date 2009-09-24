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
package org.kuali.student.search;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.dictionary.ExcelSpreadsheetReader;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class SpreadsheetLoaderTest implements TestConstants
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

 private ExcelSpreadsheetReader reader;
 private Spreadsheet instance;

 @Before
 public void setUp ()
 {
  System.out.println ("reading " + ORG_SEARCH_EXCEL_FILE);
  reader = new ExcelSpreadsheetReader (ORG_SEARCH_EXCEL_FILE);
  instance = new SpreadsheetCache (new SpreadsheetLoader (reader));
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
  List<SearchRow> list = instance.getSearchRows ();
  assertEquals (88, list.size ());
  SearchRow row = list.get (0);
  assertEquals ("org.search.orgHierarchyIds", row.getKey ());
  assertEquals ("Search", row.getType ());
  assertEquals ("All org Hierarchies", row.getName ());
  assertEquals ("Returns all org hierarchy ids", row.getDescription ());
  assertEquals ("", row.getDataType ());
  assertEquals ("Implemented", row.getStatus ());
  assertEquals ("", row.getComments ());

  row = list.get (4);
  assertEquals ("org.resultColumn.orgHierarchyId", row.getKey ());
  assertEquals ("Column", row.getType ());
  assertEquals ("Organization Hierarchy Id", row.getName ());
  assertEquals ("Id for the Hierarchy an Organization is in", row.getDescription ());
  assertEquals ("string", row.getDataType ());
  assertEquals ("Implemented", row.getStatus ());
  assertEquals ("", row.getComments ());
 }

}
