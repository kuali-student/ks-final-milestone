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
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class ExpandedDictionarySpreadsheetTest implements TestConstants
{

 public ExpandedDictionarySpreadsheetTest ()
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
 private DictionarySpreadsheet instance;

 @Before
 public void setUp ()
 {
  System.out.println ("reading " + TYPE_STATE_EXCEL_FILE);
  reader = new ExcelSpreadsheetReader (TYPE_STATE_EXCEL_FILE);
  instance = new DictionarySpreadsheetLoader (reader);
  instance = new DictionarySpreadsheetCache (instance);
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
 public void testGetDictsWithMainTypeExpanded ()
 {
  System.out.println ("getDictsWithMainTypeExpanded");

  DictionarySpreadsheet sheet = instance;
  sheet =
   new DictionarySpreadsheetExpanded (sheet, new DictionaryMainTypeExpander (sheet));
//  List<Field> expResult = new ArrayList ();
  List<Dictionary> result = sheet.getDictionary ();
  boolean found = false;
  for (Dictionary dict : result)
  {
// System.out.println (dict.getId ());
   if (dict.getId ().equals ("course.activity.contact.hours.no"))
   {
    found = true;
    assertEquals ("0", dict.getInlineConstraint ().getMinValue ());
    assertEquals ("40", dict.getInlineConstraint ().getMaxValue ());
    if (dict.getMainType ().equals ("All Activity Types"))
    {
     fail ("All activity types was not fully expanded");
    }
   }
  }
  if ( ! found)
  {
   fail ("course.activity.contact.hours.no was not in default dictionary");
  }
  if (result.size () < 640)
  {
   fail ("Should have at least 630");
  }
//  assertEquals (630, result.size ());
  assertEquals (true, true);
 }

 /**
  * Test of loadDicts method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetDictsWithMainAndSubTypeExpanded ()
 {
  System.out.println ("getDictsWithMainAndSubTypeExpanded");

  DictionarySpreadsheet sheet = instance;
  sheet =
   new DictionarySpreadsheetExpanded (sheet, new DictionaryMainTypeExpander (sheet));
  int before = sheet.getDictionary ().size ();
  sheet =
   new DictionarySpreadsheetExpanded (sheet, new DictionarySubTypeExpander (sheet));
  int after = sheet.getDictionary ().size ();
  assertEquals (before, after);
//  List<Field> expResult = new ArrayList ();
  List<Dictionary> result = sheet.getDictionary ();
  boolean found = false;
  for (Dictionary dict : result)
  {
// System.out.println (dict.getId ());
   if (dict.getId ().equals ("course.activity.contact.hours.no"))
   {
    found = true;
    assertEquals ("0", dict.getInlineConstraint ().getMinValue ());
    assertEquals ("40", dict.getInlineConstraint ().getMaxValue ());
    if (dict.getMainType ().equals ("All Activity Types"))
    {
     fail ("All activity types was not fully expanded");
    }
   }
  }
  if ( ! found)
  {
   fail ("course.activity.contact.hours.no was not in default dictionary");
  }
  if (result.size () < 630)
  {
   fail ("Should have at least 630");
  }
//  assertEquals (630, result.size ());
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
  if (result.size () < 26)
  {
   fail ("Should have at least 630");
  }
//  assertEquals (26, result.size ());
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
  if (result.size () < 111)
  {
   fail ("Should have at least 111");
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
  if (result.size () < 57)
  {
   fail ("Should have at least 57");
  }
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
  if (result.size () < 178)
  {
   fail ("Should have at least 178");
  }
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
  if (result.size () < 94)
  {
   fail ("Should have at least 94");
  }
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
  if (result.size () < 11)
  {
   fail ("Should have at least 11");
  }
//  assertEquals (11, result.size ());
  assertEquals (true, true);
 }

}