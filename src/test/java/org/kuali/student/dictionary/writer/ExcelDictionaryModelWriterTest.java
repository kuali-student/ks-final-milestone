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
package org.kuali.student.dictionary.writer;


import org.kuali.student.dictionary.model.impl.DictionaryModelCache;
import org.kuali.student.dictionary.model.impl.DictionaryModelLoader;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.spreadsheet.ExcelSpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.SpreadsheetReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.dictionary.TestConstants;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class ExcelDictionaryModelWriterTest implements TestConstants
{

 public ExcelDictionaryModelWriterTest ()
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
  * Test of write method, of class DictionaryWriter.
  */
 @Test
 public void testWriteExcelDictionary ()
 {
  System.out.println ("writeExcelDictionary");
  File file =
   new File (RESOURCES_DIRECTORY + "lu-dictionary-config-generated-excel.xml");
  PrintStream out;
  try
  {
   out = new PrintStream (file);
  }
  catch (FileNotFoundException ex)
  {
   throw new RuntimeException (ex);
  }
  SpreadsheetReader reader = new ExcelSpreadsheetReader (TYPE_STATE_DICTIONARY_EXCEL_FILE);
  try
  {
  DictionaryModelLoader loader = new DictionaryModelLoader (reader, null);
  DictionaryModel cache = new DictionaryModelCache (loader);
  DictionaryModelWriter instance = new DictionaryModelWriter (out, cache);
  instance.write ();
  }
  finally
  {
   out.close ();
   reader.close ();
  }
  assertEquals (true, true);
 }

}