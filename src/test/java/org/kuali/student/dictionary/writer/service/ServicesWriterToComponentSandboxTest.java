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
package org.kuali.student.dictionary.writer.service;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.dictionary.model.impl.DictionaryModelCache;
import org.kuali.student.dictionary.model.impl.DictionaryModelLoader;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.spreadsheet.ExcelSpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.SpreadsheetReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.dictionary.TestConstants;
import org.kuali.student.dictionary.model.spreadsheet.CompositeSpreadsheetReader;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class ServicesWriterToComponentSandboxTest implements
 TestConstants
{

 public ServicesWriterToComponentSandboxTest ()
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
  * Test of write method, of class ServicesDataWriter.
  */
 @Test
 public void testWrite ()
 {
  System.out.println ("write");
  List<SpreadsheetReader> list = new ArrayList ();
  list.add (new ExcelSpreadsheetReader (TYPE_STATE_DICTIONARY_EXCEL_FILE));
  list.add (new ExcelSpreadsheetReader (ORCHESTRATION_DICTIONARY_EXCEL_FILE));
  list.add (new ExcelSpreadsheetReader (SERVICES_EXCEL_FILE));
  list.add (new ExcelSpreadsheetReader (SERVICE_METHODS_EXCEL_FILE));
  SpreadsheetReader reader = new CompositeSpreadsheetReader (list);
  try
  {
   DictionaryModelLoader loader =
    new DictionaryModelLoader (reader);
   DictionaryModel model = new DictionaryModelCache (loader);
   ServicesWriter instance =
    new ServicesWriter (model, COMPONENT_SANDBOX_DIRECTORY_TO_WRITE_JAVA);
   instance.write ();
  }
  finally
  {
   reader.close ();
  }
  assertEquals (true, true);
 }

}
