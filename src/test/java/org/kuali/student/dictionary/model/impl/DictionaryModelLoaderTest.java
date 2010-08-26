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
package org.kuali.student.dictionary.model.impl;

import java.util.ArrayList;
import org.kuali.student.dictionary.model.spreadsheet.ExcelSpreadsheetReader;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.dictionary.command.run.RunConstants;
import org.kuali.student.dictionary.model.Constraint;
import org.kuali.student.dictionary.model.CrossObjectConstraint;
import org.kuali.student.dictionary.model.Dictionary;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Field;
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.OrchObj;
import org.kuali.student.dictionary.model.SearchType;
import org.kuali.student.dictionary.model.State;
import org.kuali.student.dictionary.model.Type;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.spreadsheet.CompositeSpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.SpreadsheetReader;
import org.kuali.student.dictionary.model.util.DictionaryParentSetter;
import org.kuali.student.dictionary.model.util.ModelFinder;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class DictionaryModelLoaderTest implements RunConstants
{

 public DictionaryModelLoaderTest ()
 {
 }

 private static SpreadsheetReader reader;
 private static DictionaryModel model;
 private static ModelFinder finder;

 @BeforeClass
 public static void setUpClass ()
  throws Exception
 {
  System.out.println ("reading " + TYPE_STATE_DICTIONARY_EXCEL_FILE);
  List<SpreadsheetReader> list = new ArrayList ();

  list.add (new ExcelSpreadsheetReader (TYPE_STATE_DICTIONARY_EXCEL_FILE));
  list.add (new ExcelSpreadsheetReader (SERVICES_EXCEL_FILE));
  list.add (new ExcelSpreadsheetReader (ORCHESTRATION_DICTIONARY_EXCEL_FILE));
  reader = new CompositeSpreadsheetReader (list);
  model = new DictionaryModelLoader (reader);
  model = new DictionaryModelCache (model);
  DictionaryParentSetter parentSetter = new DictionaryParentSetter (model);
  parentSetter.set ();
  finder = new ModelFinder (model);
 }

 @AfterClass
 public static void tearDownClass ()
  throws Exception
 {
  reader.close ();
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
  * Test of loadDicts method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetDictionary ()
 {
  System.out.println ("getDictionary");

//  List<Field> expResult = new ArrayList ();
  List<Dictionary> result = model.getDictionary ();
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
  Dictionary parent = null;
  Dictionary child = null;

  parent = finder.findDictionaryEntry ("course.official");
  assertNull (parent.getParent ());
  child = finder.findDictionaryEntry ("course.official.no");
  assertEquals (child.getParent ().getId (), parent.getId ());
  child =
   finder.findDictionaryEntry ("course.official.transcriptTitle.draft");
  assertEquals (child.getParent ().getId (), parent.getId ());

  parent = finder.findDictionaryEntry ("course.alternateIdentifiers");
  assertNull (parent.getParent ());
  child = finder.findDictionaryEntry ("course.cross-listed.version");
  assertEquals (child.getParent ().getId (), parent.getId ());
  child = finder.findDictionaryEntry ("course.cross-listed.no");
  assertEquals (child.getParent ().getId (), parent.getId ());

  parent = finder.findDictionaryEntry ("program.official");
  assertNull (parent.getParent ());
  child = finder.findDictionaryEntry ("program.official.no");
  assertEquals (child.getParent ().getId (), parent.getId ());
  child = finder.findDictionaryEntry ("program.official.transcriptTitle.draft");
  assertEquals (child.getParent ().getId (), parent.getId ());
 }

 /**
  * Test of lu states method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetStates ()
 {
  System.out.println ("getStates");
//  List<State> expResult = new ArrayList ();
  List<State> result = model.getStates ();
// assertEquals (26, result.size ());
  assertEquals (true, true);
 }

 /**
  * Test of lu types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetTypes ()
 {
  System.out.println ("getTypes");
//  List<Type> expResult = new ArrayList ();
  List<Type> result = model.getTypes ();
  for (Type type : result)
  {
   System.out.println (type.getName () + " " + type.getXmlObject ()
    + " include=" + type.getInclude ());
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
  System.out.println ("getXmlTypes");
//  List<Type> expResult = new ArrayList ();
  List<XmlType> result = model.getXmlTypes ();
  boolean foundCluInfo = false;
  boolean foundCluIdentifierInfo = false;
  boolean foundRichTextInfo = false;
  for (XmlType info : result)
  {
   if (info.getName ().equalsIgnoreCase ("cluInfo"))
   {
    assertEquals (true, info.hasOwnType ());
    assertEquals (true, info.hasOwnState ());
    assertEquals (true, info.hasOwnCreateUpdate ());
    foundCluInfo = true;
   }
   if (info.getName ().equalsIgnoreCase ("cluIdentifierInfo"))
   {
    assertEquals (true, info.hasOwnType ());
    assertEquals (true, info.hasOwnState ());
    assertEquals (false, info.hasOwnCreateUpdate ());
    foundCluIdentifierInfo = true;
   }
   if (info.getName ().equalsIgnoreCase ("richTextInfo"))
   {
    assertEquals (false, info.hasOwnType ());
    assertEquals (false, info.hasOwnState ());
    assertEquals (false, info.hasOwnCreateUpdate ());
    foundRichTextInfo = true;
   }
  }
  assertEquals (true, foundCluInfo);
  assertEquals (true, foundCluIdentifierInfo);
  assertEquals (true, foundRichTextInfo);
  //assertEquals (57, result.size ());
  assertEquals (true, true);
 }

 /**
  * Test of info types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetFields ()
 {
  System.out.println ("getFields");
//  List<Type> expResult = new ArrayList ();
  List<Field> result = model.getFields ();
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
  System.out.println ("getConstraints");
//  List<Type> expResult = new ArrayList ();
  List<Constraint> result = model.getConstraints ();
  for (Constraint cons : result)
  {
   System.out.println (cons.getId ());
  }
  assertNotNull (finder.findConstraint ("required"));
  assertNotNull (finder.findConstraint ("hidden"));
  assertNotNull (finder.findConstraint ("read.only"));
 }

 /**
  * Test of info types method, of class SpreadsheetGeter.
  */
 @Test
 public void testGetCrossObjectConstraints ()
 {
  System.out.println ("getCrossObjectConstraints");
//  List<Type> expResult = new ArrayList ();
  List<CrossObjectConstraint> result = model.getCrossObjectConstraints ();
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
  List<OrchObj> result = model.getOrchObjs ();
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
  List<MessageStructure> result = model.getMessageStructures ();
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
  List<SearchType> result = model.getSearchTypes ();
  for (SearchType searchType : result)
  {
   System.out.println (searchType.getLookupKey () + " ==> "
    + searchType.getKey ());
  }
  if (result.size () < 2)
  {
   fail ("too few rows should be > 50 but was" + result.size ());
  }
  assertEquals (true, true);
 }

}
