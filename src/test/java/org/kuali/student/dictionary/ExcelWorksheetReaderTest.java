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
public class ExcelWorksheetReaderTest
{

 public ExcelWorksheetReaderTest ()
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

 private ExcelWorksheetReader getInstance (String name)
 {
  return (ExcelWorksheetReader) ExcelSpreadsheetReaderTest.getInstance ().
   getWorksheetReader (name);
 }

 /**
  * Test of getEstimatedRows method, of class ExcelWorksheetReader.
  */
 @Test
 public void testGetEstimatedRows ()
 {
  System.out.println ("getEstimatedRows");
  ExcelWorksheetReader instance = getInstance ("Constraints");
  int expResult = 100;
  int result = instance.getEstimatedRows ();
  assertEquals (expResult, result);
 }

 /**
  * Test of getValue method, of class ExcelWorksheetReader.
  */
 @Test
 public void testGetValue ()
 {
  System.out.println ("getValue");
  String name = "id";
  ExcelWorksheetReader instance = getInstance ("Constraints");
  if ( ! instance.next ())
  {
   fail ("no rows found expected lots");
  }
  String expResult = "General constraints";
  String result = instance.getValue (name);
  assertEquals (expResult, result);
 }

 /**
  * Test of getValue method, of class ExcelWorksheetReader.
  */
 @Test
 public void testGetValueBoolean ()
 {
  System.out.println ("getValueBoolean");
  String name = "serverSide";
  ExcelWorksheetReader instance = getInstance ("Constraints");
  for (int i = 1; i <= 28; i ++)
  {
   if ( ! instance.next ())
   {
    fail ("no rows found expected lots");
   }
   String expResult = null;
   String id = instance.getValue ("id");
   if (id.equals ("General constraints"))
   {
    expResult = "true";
   }
   else if (id.equals ("kuali.id"))
   {
    expResult = "true";
   }
    else if (id.equals ("kuali.type"))
   {
    expResult = "true";
   }
   else
   {
    expResult = "";
   }
   String result = instance.getValue (name);
   System.out.println (i + ") " + id + "=" + result);
   assertEquals (id + "=" + expResult, id + "=" + result);
  }
  assertEquals (true, true);
 }

 /**
  * Test of getValue method, of class ExcelWorksheetReader.
  */
 @Test
 public void testGetValueInteger ()
 {
  System.out.println ("getValueInteger");
  String name = "minLength";
  ExcelWorksheetReader instance = getInstance ("Constraints");
  for (int i = 1; i <= 14; i ++)
  {
   if ( ! instance.next ())
   {
    fail ("no rows found expected lots");
   }
   String expResult = null;
   switch (i)
   {
    case 1:
     expResult = "0";
     break;
    case 2:
     expResult = "";
     break;
    case 3:
     expResult = "";
     break;
    case 4:
     expResult = "";
     break;
    case 5:
     expResult = "";
     break;
    case 6:
     expResult = "";
     break;
    case 7:
     expResult = "";
     break;
    case 8:
     expResult = "";
     break;
    case 9:
     expResult = "";
     break;
    case 10:
     expResult = "";
     break;
    case 11:
     expResult = "1";
     break;
    case 12:
     expResult = "1";
     break;
    case 13:
     expResult = "1";
     break;
    case 14:
     expResult = "1";
     break;
    default:
     expResult = "";
     break;
   }
   String id = instance.getValue ("id");
   String result = instance.getValue (name);
   System.out.println (i + ") " + id + "=" + result);
   assertEquals (id + "=" + expResult, id + "=" + result);
  }
  assertEquals (true, true);
 }

 /**
  * Test of next method, of class ExcelWorksheetReader.
  */
 @Test
 public void testNext ()
 {
  System.out.println ("next");
  ExcelWorksheetReader instance = getInstance ("Constraints");
  boolean expResult = true;
  boolean result = instance.next ();
  assertEquals (expResult, result);
 }

 /**
  * Test of getValue method, of class ExcelWorksheetReader.
  */
 @Test
 public void testGetDictionaryComments ()
 {
  System.out.println ("getDictionaryComments");
  String name = "comments";
  ExcelWorksheetReader instance = getInstance ("Dictionary");
  for (int i = 1; i <= 6; i ++)
  {
   if ( ! instance.next ())
   {
    fail ("no rows found expected lots");
   }
   String expResult = null;
   switch (i)
   {
    case 1:
     expResult = "xxxx";
     break;
    case 6:
     expResult = "Rationale is stored in the Marketing Description for now";
     break;
    default:
     expResult = "";
     break;
   }
   String id = instance.getValue ("id");

   instance.getValue ("name");
   instance.getValue ("desc");
   instance.getValue ("mainType");
   instance.getValue ("ok1");
   instance.getValue ("parentObject");
   instance.getValue ("parentShortName");
   instance.getValue ("ok2");
   instance.getValue ("subType");
   instance.getValue ("subState");
   instance.getValue ("ok3");
   instance.getValue ("xmlObject");
   instance.getValue ("shortName");
   instance.getValue ("primitive");
   instance.getValue ("baseConstraintDescription");
   instance.getValue ("selector");
   instance.getValue ("dynamic");
   instance.getValue ("additionalConstraintId1");
   instance.getValue ("additionalConstraintId2");
   instance.getValue ("additionalConstraintId3");
   instance.getValue ("additionalConstraintId4");
   instance.getValue ("additionalConstraintDescription");
   instance.getValue ("combinedConstraintDescription");
   instance.getValue ("minLength");
   instance.getValue ("maxLength");
   instance.getValue ("minOccurs");
   instance.getValue ("maxOccurs");
   instance.getValue ("validChars");
   instance.getValue ("lookup");
   String result = instance.getValue ("comments");

   System.out.println (i + ") " + id + "=" + result);
   assertEquals (id + "=" + expResult, id + "=" + result);
  }
  assertEquals (true, true);
 }

}