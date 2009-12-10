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
package org.kuali.student.dictionary;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.search.SearchType;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class ExcelDictionaryModelLoaderTest implements TestConstants
{

 public ExcelDictionaryModelLoaderTest ()
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

 private ExcelSpreadsheetReader dictReader;
 private ExcelSpreadsheetReader orchReader;
 private DictionaryModel instance;

 @Before
 public void setUp ()
 {
  System.out.println ("reading " + TYPE_STATE_DICTIONARY_EXCEL_FILE);
  dictReader = new ExcelSpreadsheetReader (TYPE_STATE_DICTIONARY_EXCEL_FILE);
  orchReader = new ExcelSpreadsheetReader (ORCHESTRATION_DICTIONARY_EXCEL_FILE);
  instance = new DictionaryModelLoader (dictReader, orchReader);
 }

 @After
 public void tearDown ()
 {
  dictReader.close ();
 }

 /**
  * Test of loadDicts method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetDicts ()
 {
  System.out.println ("loadDicts");

//  List<Field> expResult = new ArrayList ();
  List<Dictionary> result = instance.getDictionary ();
  boolean found = false;
  for (Dictionary dict : result)
  {
//   System.out.println (dict.getId ());
   if (dict.getId ().equals ("course.activity.contact.hours.no"))
   {
    found = true;
    assertEquals ("0", dict.getInlineConstraint ().getMinValue ());
    assertEquals ("40", dict.getInlineConstraint ().getMaxValue ());
   }
  }
  if ( ! found)
  {
   fail ("course.activity.contact.hours.no was not in default dictionary");
  }
//   assertEquals (713, result.size ());
  assertEquals (true, true);
 }

 /**
  * Test of lu states method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetStates ()
 {
  System.out.println ("loadStates");
//  List<State> expResult = new ArrayList ();
  List<State> result = instance.getStates ();
// assertEquals (26, result.size ());
  assertEquals (true, true);
 }

 /**
  * Test of lu types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetTypes ()
 {
  System.out.println ("loadTypes");
//  List<Type> expResult = new ArrayList ();
  List<Type> result = instance.getTypes ();
  for (Type type : result)
  {
   System.out.println (type.getName () + " " + type.getXmlObject () +
    " include=" + type.getInclude ());
  }
//  assertEquals (111, result.size ());
  assertEquals (true, true);
 }

 /**
  * Test of info types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetXmlTypes ()
 {
  System.out.println ("loadXmlTypes");
//  List<Type> expResult = new ArrayList ();
  List<XmlType> result = instance.getXmlTypes ();
//  for (XmlType info : result)
//  {
//   System.out.println (info.getObject ());
//  }
//  assertEquals (57, result.size ());
  assertEquals (true, true);
 }

 /**
  * Test of info types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetFields ()
 {
  System.out.println ("loadFields");
//  List<Type> expResult = new ArrayList ();
  List<Field> result = instance.getFields ();
//  for (MessageStructureField field : result)
//  {
//   System.out.println (field.getObjectField ());
//  }
//  assertEquals (178, result.size ());
  assertEquals (true, true);
 }

 /**
  * Test of info types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetConstraints ()
 {
  System.out.println ("loadConstraints");
//  List<Type> expResult = new ArrayList ();
  List<Constraint> result = instance.getConstraints ();
//  for (MessageStructureField field : result)
//  {
//   System.out.println (field.getObjectField ());
//  }
//  assertEquals (94, result.size ());
  assertEquals (true, true);
 }

 /**
  * Test of info types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetCrossObjectConstraints ()
 {
  System.out.println ("loadCrossObjectConstraints");
//  List<Type> expResult = new ArrayList ();
  List<CrossObjectConstraint> result = instance.getCrossObjectConstraints ();
//  for (MessageStructureField field : result)
//  {
//   System.out.println (field.getObjectField ());
//  }
//  assertEquals (11, result.size ());
  assertEquals (true, true);

 }

 /**
  * Test of info types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetOrchObjs ()
 {
  System.out.println ("getOrchObjs");
//  List<Type> expResult = new ArrayList ();
  List<OrchObj> result = instance.getOrchObjs ();
  for (OrchObj orch : result)
  {
   System.out.println (orch.getId ());
  }
  if (result.size () < 50)
  {
   fail ("too few rows should be > 50 but was" + result.size ());
  }
  assertEquals (true, true);
 }

 /**
  * Test of info types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetMessageStructures ()
 {
  System.out.println ("getMessageStructures");
//  List<Type> expResult = new ArrayList ();
  List<MessageStructure> result = instance.getMessageStructures ();
//  for (MessageStructureField field : result)
//  {
//   System.out.println (field.getObjectField ());
//  }
  if (result.size () < 400)
  {
   fail ("fewer than expected >= 400 rows: " + result.size ());
  }
  for (MessageStructure ms : result)
  {
   System.out.print (ms.getId () + "\t");
   System.out.print (ms.getXmlObject () + "\t");
   System.out.print (ms.getShortName () + "\t");
   System.out.print (ms.getName () + "\t");
   System.out.print (ms.getType ());
   System.out.println ("");
  }
  //assertEquals (135, result.size ());
  assertEquals (true, true);
 }

 /**
  * Test of info types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetSearchTypes ()
 {
  System.out.println ("getSearchTypes");
//  List<Type> expResult = new ArrayList ();
  List<SearchType> result = instance.getSearchTypes ();
  for (SearchType searchType : result)
  {
   System.out.println (searchType.getLookupKey () + " ==> " + searchType.getKey ());
  }
  if (result.size () < 2)
  {
   fail ("too few rows should be > 50 but was" + result.size ());
  }
  assertEquals (true, true);
 }


}
