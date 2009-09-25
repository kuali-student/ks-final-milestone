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

import java.util.ArrayList;
import java.util.Collection;
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
public class SearchSpreadsheetValidatorTest implements SearchTestConstants
{

 public SearchSpreadsheetValidatorTest ()
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
 private SearchSpreadsheet spreadsheet;

 @Before
 public void setUp ()
 {
  System.out.println ("reading " + ORG_SEARCH_EXCEL_FILE);
  reader = new ExcelSpreadsheetReader (ORG_SEARCH_EXCEL_FILE);
  spreadsheet =
   new SearchSpreadsheetCache (new SearchSpreadsheetLoader (reader));
 }

 @After
 public void tearDown ()
 {
 }

 /**
  * Test of validate method, of class SearchTypeValidator.
  */
 @Test
 public void testValidate ()
 {
  System.out.println ("validate");
  SearchSpreadsheetValidator instance =
   new SearchSpreadsheetValidator (spreadsheet);
  Collection<String> expResult = new ArrayList ();
  Collection<String> result = instance.validate ();
  for (String error : result)
  {
   System.out.println (error);
  }
  assertEquals (expResult, result);
 }

}
