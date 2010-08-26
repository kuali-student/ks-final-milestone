/*
 * Copyright 2010 The Kuali Foundation
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
package org.kuali.student.dictionary.model.util;

import java.util.ArrayList;
import org.kuali.student.dictionary.model.impl.DictionaryModelCache;
import org.kuali.student.dictionary.model.impl.DictionaryModelLoader;
import org.kuali.student.dictionary.model.spreadsheet.ExcelSpreadsheetReader;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.dictionary.command.run.RunConstants;
import org.kuali.student.dictionary.model.Dictionary;
import org.kuali.student.dictionary.model.DictionaryModel;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class ModelFinderTest implements RunConstants
{

 public ModelFinderTest ()
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
 private DictionaryModel model;

 @Before
 public void setUp ()
 {
  System.out.println ("reading " + TYPE_STATE_DICTIONARY_EXCEL_FILE);
  reader = new ExcelSpreadsheetReader (TYPE_STATE_DICTIONARY_EXCEL_FILE);
  model = new DictionaryModelLoader (reader);
  model = new DictionaryModelCache (model);
  DictionaryParentSetter parentSetter = new DictionaryParentSetter (model);
  parentSetter.set ();
 }

 @After
 public void tearDown ()
 {
  reader.close ();
 }

 /**
  * Test of loadDicts method, of class SpreadsheetGeter.
  */
 @Test
 public void testFindChildDictionaryEntries ()
 {
  System.out.println ("findChildDictionaryEntries");
  ModelFinder finder = new ModelFinder (model);
  Dictionary parent = finder.findDictionaryEntry ("coursE.oFFicial");
  assertNotNull (parent);
  //List<Dictionary> expResult = new ArrayList ();
  List<Dictionary> result = finder.findChildDictionaryEntries (parent);
  System.out.println ("found " + result.size () + " children of " + parent.getId ());
  for (Dictionary dict : result)
  {
   System.out.println (dict.getId ());
  }
  assertEquals (21, result.size ());
 }
}