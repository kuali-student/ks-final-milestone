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
package org.kuali.student.dictionary.model.validation;

import org.kuali.student.dictionary.model.SearchModel;
import org.kuali.student.dictionary.model.impl.SearchModelCache;
import org.kuali.student.dictionary.model.impl.SearchModelLoader;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.dictionary.command.run.RunConstants;
import org.kuali.student.dictionary.model.spreadsheet.ExcelSpreadsheetReader;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class SearchModelValidatorTest implements RunConstants
{

 public SearchModelValidatorTest ()
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
  * Test of validate method, of class SearchTypeValidator.
  */
 @Test
 public void testValidateGood ()
 {
  System.out.println ("validateGood");
  System.out.println ("reading " + ORG_SEARCH_EXCEL_FILE);
  ExcelSpreadsheetReader reader =
   new ExcelSpreadsheetReader (ORG_SEARCH_EXCEL_FILE);
  SearchModel spreadsheet =
   new SearchModelCache (new SearchModelLoader (reader));
  SearchModelValidator instance =
   new SearchModelValidator (spreadsheet);
  Collection<String> expResult = new ArrayList ();
  Collection<String> result = instance.validate ();
  for (String error : result)
  {
   System.out.println (error);
  }
  assertEquals (expResult, result);
 }

 /**
  * Test of validate method, of class SearchTypeValidator.
  */
 @Test
 public void testValidateBad ()
 {
  System.out.println ("validateBad");
  System.out.println ("reading " + ORG_SEARCH_EXCEL_FILE_BAD);
  ExcelSpreadsheetReader reader =
   new ExcelSpreadsheetReader (ORG_SEARCH_EXCEL_FILE_BAD);
  SearchModel spreadsheet =
   new SearchModelCache (new SearchModelLoader (reader));
  SearchModelValidator instance =
   new SearchModelValidator (spreadsheet);
  Collection<String> expResult = new ArrayList ();
  expResult.add ("Error in overall spreadsheet: two criteria with the same key have different names, one is on row 16 the other is on row 75");
  expResult.add ("Error in overall spreadsheet: Two criteria with the same key have different descriptions, one is on row 16 the other is on row 75");
  expResult.add ("Error in overall spreadsheet: two criteria with the same key have different parameters, one is on row 16 the other is on row 75 the parameters that are different are on row 17");
  expResult.add ("Error in overall spreadsheet: two results with the same key have different result columns, one is on row 18 the other is on row 26 the result columns that are different are on row 19");
  expResult.add ("Error in overall spreadsheet: two result columns with the same key have different names, one is on row 6 the other is on row 13");
  Collection<String> result = instance.validate ();
  for (String error : result)
  {
   System.out.println (error);
  }
  assertEquals (expResult, result);
 }

}
