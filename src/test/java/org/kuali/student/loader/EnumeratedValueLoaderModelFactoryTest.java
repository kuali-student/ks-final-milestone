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
package org.kuali.student.loader;

import java.util.Properties;
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
public class EnumeratedValueLoaderModelFactoryTest
{

 public EnumeratedValueLoaderModelFactoryTest ()
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

 public static EnumeratedValueLoaderModelFactory getInstance ()
 {
  Properties props = new Properties ();
  props.putAll (EnumeratedValueLoaderModelFactory.getDefaultConfig ());
  props.put (EnumeratedValueLoaderModelFactory.EXCEL_FILES_DEFAULT_DIRECTORY_KEY, "src/main/"
   + EnumeratedValueLoaderModelFactory.RESOURCES_DIRECTORY);
  props.put (EnumeratedValueLoaderModelFactory.SERVICE_HOST_URL, "src/main/"
   + EnumeratedValueLoaderModelFactory.RESOURCES_DIRECTORY);
  System.out.println ("Current Directory=" + System.getProperty ("user.dir"));
  EnumeratedValueLoaderModelFactory factory = new EnumeratedValueLoaderModelFactory ();
  factory.setConfig (props);
  return factory;
 }

 /**
  * Test of getModel method, of class DataModelFactory.
  */
 @Test
 public void testGetModel ()
 {
  System.out.println ("getModel");
  EnumeratedValueLoaderModelFactory instance = getInstance ();
  EnumeratedValueLoaderModel result = instance.getModel ();
  if (result.getEnumeratedValues ().size () < 100)
  {
   fail (" too few courses");
  }
  EnumeratedValue ev = result.getEnumeratedValues ().get (0);
  assertEquals ("kuali.enum.type.cip2010", ev.getEnumerationKey ());
  assertEquals ("01", ev.getCode ());
  assertEquals ("01", ev.getAbbrevValue ());
  assertEquals ("AGRICULTURE, AGRICULTURE OPERATIONS, AND RELATED SCIENCES.", ev.getValue ());
  assertEquals (new DateHelper ().asDate ("2010-07-01"), ev.getEffectiveDate ());
  assertEquals (null, ev.getExpirationDate ());
  assertEquals ((Integer) 2, ev.getSortKey ());
  assertEquals (null, ev.getContexts ());
 }

}
