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
public class SearchSpreadsheetLoaderTest implements SearchTestConstants
{

 public SearchSpreadsheetLoaderTest ()
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
 private SearchSpreadsheet instance;

 @Before
 public void setUp ()
 {
  System.out.println ("reading " + ORG_SEARCH_EXCEL_FILE);
  reader = new ExcelSpreadsheetReader (ORG_SEARCH_EXCEL_FILE);
  instance = new SearchSpreadsheetCache (new SearchSpreadsheetLoader (reader));
 }

 @After
 public void tearDown ()
 {
 }

 /**
  * Test of getSearchRows method, of class SpreadsheetLoader.
  */
 @Test
 public void testGetSearchTypes ()
 {
  System.out.println ("getSearchTypes");
  List<SearchType> list = instance.getSearchTypes ();
  assertEquals (11, list.size ());
  SearchType searchType = list.get (0);
  assertEquals ("org.search.orgHierarchyIds", searchType.getKey ());
  assertEquals ("Search", searchType.getType ());
  assertEquals ("All org Hierarchies", searchType.getName ());
  assertEquals ("Returns all org hierarchy ids", searchType.getDescription ());
  assertEquals ("", searchType.getDataType ());
  assertEquals ("Implemented", searchType.getStatus ());
  assertEquals ("", searchType.getComments ());

  assertEquals ("JPQL", searchType.getImplementation ().getType ());
  assertEquals ("org.search.orgHierarchyIds", searchType.getImplementation ().getKey ());
  
  assertEquals (0, searchType.getCriteria ().getParameters ().size ());
  assertEquals (2, searchType.getResults ().getResultColumns ().size ());
  SearchResultColumn col = searchType.getResults ().getResultColumns ().get (0);
  assertEquals ("org.resultColumn.orgHierarchyId", col.getKey ());
  assertEquals ("Column", col.getType ());
  assertEquals ("Organization Hierarchy Id", col.getName ());
  assertEquals ("Id for the Hierarchy an Organization is in", col.getDescription ());
  assertEquals ("string", col.getDataType ());
  assertEquals ("Implemented", col.getStatus ());
  assertEquals ("", col.getComments ());
 }

}
