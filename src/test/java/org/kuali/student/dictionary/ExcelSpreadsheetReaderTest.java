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

import java.sql.Connection;
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
public class ExcelSpreadsheetReaderTest
{

 public ExcelSpreadsheetReaderTest ()
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

 public static String DIRECTORY = "src/test/resources/dictionary/";

 public static ExcelSpreadsheetReader getInstance ()
 {
  System.out.println ("reading " + DIRECTORY + "type-state configuration.xls");
  return new ExcelSpreadsheetReader (DIRECTORY + "type-state configuration.xls");
 }

 /**
  * Test of getConnection method, of class ExcelSpreadsheetReader.
  */
 @Test
 public void testGetConnection ()
 {
  System.out.println ("getConnection");
  ExcelSpreadsheetReader instance = getInstance ();
  Connection con = instance.getConnection ();
  assertNotNull (con);
 }

 /**
  * Test of getWorksheetReader method, of class ExcelSpreadsheetReader.
  */
 @Test
 public void testGetWorksheetReader ()
 {
  System.out.println ("getWorksheetReader");
  String name = "Constraints";
  ExcelSpreadsheetReader instance = this.getInstance ();
  WorksheetReader reader = instance.getWorksheetReader (name);
  assertNotNull (reader);
 }

}