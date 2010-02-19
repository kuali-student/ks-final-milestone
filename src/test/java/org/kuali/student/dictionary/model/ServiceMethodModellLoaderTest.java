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
package org.kuali.student.dictionary.model;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.dictionary.command.run.RunConstants;
import org.kuali.student.dictionary.model.impl.ServiceContractModelCache;
import org.kuali.student.dictionary.model.impl.ServiceContractModelLoader;
import org.kuali.student.dictionary.model.spreadsheet.ExcelSpreadsheetReader;
import org.kuali.student.dictionary.model.util.ModelFinder;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class ServiceMethodModellLoaderTest implements RunConstants
{

 public ServiceMethodModellLoaderTest ()
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
 private ServiceContractModel instance;

 @Before
 public void setUp ()
 {
  System.out.println ("reading " + SERVICE_METHODS_EXCEL_FILE);
  reader = new ExcelSpreadsheetReader (SERVICE_METHODS_EXCEL_FILE);
  instance = new ServiceContractModelCache (new ServiceContractModelLoader (reader));
 }

 @After
 public void tearDown ()
 {
 }

 /**
  * Test of getSearchRows method, of class SpreadsheetLoader.
  */
 @Test
 public void testGetServiceMethods ()
 {
  System.out.println ("getServiceMethods");
  List<ServiceMethod> list = instance.getServiceMethods ();
  if (list.size () < 30)
  {
   fail ("not enough rows");
  }
  ServiceMethod serviceMethod =
   new ModelFinder (instance).findServiceMethod ("atp", "getAtpTypes");
  assertNotNull (serviceMethod);
  assertEquals ("Retrieves the list of academic time period types known by this service", serviceMethod.
   getDescription ());
  assertEquals (0, serviceMethod.getParameters ().size ());
  assertEquals ("atpTypeInfoList", serviceMethod.getReturnValue ().
   getType ());
  assertEquals ("list of academic time period types", serviceMethod.
   getReturnValue ().getDescription ());
  assertEquals (1, serviceMethod.getErrors ().size ());
  assertEquals ("OPERATION_FAILED", serviceMethod.getErrors ().get (0).getType ());
  assertEquals ("unable to complete request", serviceMethod.getErrors ().get (0).
   getDescription ());

 }

}
