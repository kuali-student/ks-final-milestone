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
public class ExpandedSpreadsheetTest
{

 public ExpandedSpreadsheetTest ()
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

 protected static Spreadsheet getInstance ()
 {
  Spreadsheet sheet = ExcelSpreadsheetLoaderTest.getInstance ();
  sheet = new SpreadsheetCache (sheet);
  return sheet;
 }

 /**
  * Test of loadDicts method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetDictsWithMainTypeExpanded ()
 {
  System.out.println ("getDictsWithMainTypeExpanded");

  Spreadsheet sheet = getInstance ();
  sheet =
   new ExpandedSpreadsheet (sheet, new DictionaryMainTypeExpander (sheet));
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
  assertEquals (630, result.size ());
 }

 /**
  * Test of loadDicts method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetDictsWithMainAndSubTypeExpanded ()
 {
  System.out.println ("getDictsWithMainAndSubTypeExpanded");

  Spreadsheet sheet = getInstance ();
  sheet =
   new ExpandedSpreadsheet (sheet, new DictionaryMainTypeExpander (sheet));
  int before = sheet.getDictionary ().size ();
  sheet =
   new ExpandedSpreadsheet (sheet, new DictionarySubTypeExpander (sheet));
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
  assertEquals (630, result.size ());
 }

 /**
  * Test of lu states method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetStates ()
 {
  System.out.println ("loadStates");
  Spreadsheet instance = getInstance ();
//  List<State> expResult = new ArrayList ();
  List<State> result = instance.getStates ();
  assertEquals (26, result.size ());
 }

 /**
  * Test of lu types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetTypes ()
 {
  System.out.println ("loadTypes");
  Spreadsheet instance = getInstance ();
//  List<Type> expResult = new ArrayList ();
  List<Type> result = instance.getTypes ();
  for (Type type : result)
  {
   System.out.println (type.getName () + " " + type.getXmlObject () +
    " include=" + type.getInclude ());
  }
  assertEquals (111, result.size ());
 }

 /**
  * Test of info types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetXmlTypes ()
 {
  System.out.println ("loadXmlTypes");
  Spreadsheet instance = getInstance ();
//  List<Type> expResult = new ArrayList ();
  List<XmlType> result = instance.getXmlTypes ();
//  for (XmlType info : result)
//  {
//   System.out.println (info.getObject ());
//  }
  assertEquals (57, result.size ());
 }

 /**
  * Test of info types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetFields ()
 {
  System.out.println ("loadFields");
  Spreadsheet instance = getInstance ();
//  List<Type> expResult = new ArrayList ();
  List<Field> result = instance.getFields ();
//  for (MessageStructureField field : result)
//  {
//   System.out.println (field.getObjectField ());
//  }
  assertEquals (178, result.size ());
 }

 /**
  * Test of info types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetConstraints ()
 {
  System.out.println ("loadConstraints");
  Spreadsheet instance = getInstance ();
//  List<Type> expResult = new ArrayList ();
  List<Constraint> result = instance.getConstraints ();
//  for (MessageStructureField field : result)
//  {
//   System.out.println (field.getObjectField ());
//  }
  assertEquals (94, result.size ());
 }

 /**
  * Test of info types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetCrossObjectConstraints ()
 {
  System.out.println ("loadCrossObjectConstraints");
  Spreadsheet instance = getInstance ();
//  List<Type> expResult = new ArrayList ();
  List<CrossObjectConstraint> result = instance.getCrossObjectConstraints ();
//  for (MessageStructureField field : result)
//  {
//   System.out.println (field.getObjectField ());
//  }
  assertEquals (11, result.size ());
 }

}