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

import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.wsdl.enumerationmanagement.AlreadyExistsException;
import org.kuali.student.wsdl.enumerationmanagement.DoesNotExistException;
import org.kuali.student.wsdl.enumerationmanagement.EnumContextInfo;
import org.kuali.student.wsdl.enumerationmanagement.EnumContextValueInfo;
import org.kuali.student.wsdl.enumerationmanagement.EnumeratedValueFieldInfo;
import static org.junit.Assert.*;
import org.kuali.student.wsdl.enumerationmanagement.EnumeratedValueInfo;
import org.kuali.student.wsdl.enumerationmanagement.EnumerationMetaInfo;

/**
 *
 * @author nwright
 */
public class EnumManServiceTest
{

 public EnumManServiceTest ()
 {
 }

 @BeforeClass
 public static void setUpClass () throws Exception
 {
 }

 @AfterClass
 public static void tearDownClass () throws Exception
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

// /**
//  * Test of createCourse method, of class EnumManService.
//  */
// @Test
// public void testEnumerationLifeCycle ()
// {
//  System.out.println ("testEnumerationLifeCycle");
//  String enumerationKey = "enumerationKey";
//  EnumeratedValueInfo info = new EnumeratedValueInfo ();
//  info.setCode ("code");
//  info.setAbbrevValue ("abbrev value");
//  info.setValue ("value");
//  info.setEffectiveDate (new DateHelper ().asXmlDate ("2010-08-01"));
//  info.setExpirationDate (null);
//  info.setSortKey ("1");
//  EnumContextValueInfo context = new EnumContextValueInfo ();
//  context.setType ("context type");
//  context.setValue ("value");
//  info.getContexts ().add (context);
//
//  EnumManService instance = new EnumManService ();
//  instance.setHostUrl (EnumeratedValueLoaderModelFactory.LOCAL_HOST_URL);
//  EnumeratedValueInfo result = null;
//  // try to remove
//  try
//  {
//   boolean success = instance.removeEnumeratedValue (enumerationKey,
//                                                     info.getCode ());
//   assertEquals (true, success);
//  }
//  catch (DoesNotExistException ex)
//  {
//   // ok
//  }
//
//  try
//  {
//   result = instance.addEnumeratedValue (enumerationKey, info);
//  }
//  catch (AlreadyExistsException ex)
//  {
//   fail ("got already exists exception");
//  }
//  assertNotNull (result);
//  assertNotSame (info, result);
//  assertEquals (info.getCode (), result.getCode ());
//  assertEquals (info.getAbbrevValue (), result.getAbbrevValue ());
//  assertEquals (info.getValue (), result.getValue ());
//  assertEquals (info.getEffectiveDate (), result.getEffectiveDate ());
//  assertEquals (info.getExpirationDate (), result.getExpirationDate ());
//  assertEquals (info.getSortKey (), result.getSortKey ());
//  assertEquals (info.getContexts ().size (), result.getContexts ().size ());
//  for (int i = 0; i < info.getContexts ().size (); i ++)
//  {
//   EnumContextValueInfo contextInfo = info.getContexts ().get (i);
//   EnumContextValueInfo contextResult = result.getContexts ().get (i);
//   assertEquals (contextInfo.getType (), contextResult.getType ());
//   assertEquals (contextInfo.getValue (), contextResult.getValue ());
//  }
//
//  // get
//  info = result;
//  List<EnumeratedValueInfo> enumeration = null;
//
//  try
//  {
//   enumeration = instance.getEnumeration (enumerationKey, context.getType (),
//                                          context.getValue (), new Date ());
//   assertNotNull (enumeration);
//   assertEquals (1, enumeration.size ());
//   result = enumeration.get (0);
//  }
//  catch (DoesNotExistException ex)
//  {
//   fail ("should exist");
//  }
//  assertNotNull (result);
//  assertNotSame (info, result);
//  assertEquals (info.getCode (), result.getCode ());
//  assertEquals (info.getAbbrevValue (), result.getAbbrevValue ());
//  assertEquals (info.getValue (), result.getValue ());
//  assertEquals (info.getEffectiveDate (), result.getEffectiveDate ());
//  assertEquals (info.getExpirationDate (), result.getExpirationDate ());
//  assertEquals (info.getSortKey (), result.getSortKey ());
//  assertEquals (info.getContexts ().size (), result.getContexts ().size ());
//  for (int i = 0; i < info.getContexts ().size (); i ++)
//  {
//   EnumContextValueInfo contextInfo = info.getContexts ().get (i);
//   EnumContextValueInfo contextResult = result.getContexts ().get (i);
//   assertEquals (contextInfo.getType (), contextResult.getType ());
//   assertEquals (contextInfo.getValue (), contextResult.getValue ());
//  }
//
//  // update
//  info = result;
//  info.setCode ("new code");
//  info.setValue ("new value");
//  try
//  {
//   result = instance.updateEnumeratedValue (enumerationKey, "code", info);
//  }
//  catch (DoesNotExistException ex)
//  {
//   fail ("got already exists exception");
//  }
//  assertNotNull (result);
//  assertNotSame (info, result);
//  assertEquals (info.getCode (), result.getCode ());
//  assertEquals (info.getAbbrevValue (), result.getAbbrevValue ());
//  assertEquals (info.getValue (), result.getValue ());
//  assertEquals (info.getEffectiveDate (), result.getEffectiveDate ());
//  assertEquals (info.getExpirationDate (), result.getExpirationDate ());
//  assertEquals (info.getSortKey (), result.getSortKey ());
//  assertEquals (info.getContexts ().size (), result.getContexts ().size ());
//  for (int i = 0; i < info.getContexts ().size (); i ++)
//  {
//   EnumContextValueInfo contextInfo = info.getContexts ().get (i);
//   EnumContextValueInfo contextResult = result.getContexts ().get (i);
//   assertEquals (contextInfo.getType (), contextResult.getType ());
//   assertEquals (contextInfo.getValue (), contextResult.getValue ());
//  }
//
//
//  // remove
//  info = result;
//  try
//  {
//   boolean success = instance.removeEnumeratedValue (enumerationKey,
//                                                     info.getCode ());
//   assertEquals (true, success);
//  }
//  catch (DoesNotExistException ex)
//  {
//   fail ("got does not exist exception");
//  }
// }
//
// /**
//  * Test of createCourse method, of class EnumManService.
//  */
// @Test
// public void testEnumerationLifeCycleWithoutUpdate ()
// {
//  System.out.println ("testEnumerationLifeCycleWithoutUpdate");
//  String enumerationKey = "enumerationKey";
//  EnumeratedValueInfo info = new EnumeratedValueInfo ();
//  info.setCode ("code");
//  info.setAbbrevValue ("abbrev value");
//  info.setValue ("value");
//  info.setEffectiveDate (new DateHelper ().asXmlDate ("2010-08-01"));
//  info.setExpirationDate (null);
//  info.setSortKey ("1");
//  EnumContextValueInfo context = new EnumContextValueInfo ();
//  context.setType ("context type");
//  context.setValue ("value");
//  info.getContexts ().add (context);
//
//  EnumManService instance = new EnumManService ();
//  instance.setHostUrl (EnumeratedValueLoaderModelFactory.LOCAL_HOST_URL);
//  EnumeratedValueInfo result = null;
//  // try to remove
//  try
//  {
//   boolean success = instance.removeEnumeratedValue (enumerationKey,
//                                                     info.getCode ());
//   assertEquals (true, success);
//  }
//  catch (DoesNotExistException ex)
//  {
//   // ok
//  }
//
//  try
//  {
//   result = instance.addEnumeratedValue (enumerationKey, info);
//  }
//  catch (AlreadyExistsException ex)
//  {
//   fail ("got already exists exception");
//  }
//  assertNotNull (result);
//  assertNotSame (info, result);
//  assertEquals (info.getCode (), result.getCode ());
//  assertEquals (info.getAbbrevValue (), result.getAbbrevValue ());
//  assertEquals (info.getValue (), result.getValue ());
//  assertEquals (info.getEffectiveDate (), result.getEffectiveDate ());
//  assertEquals (info.getExpirationDate (), result.getExpirationDate ());
//  assertEquals (info.getSortKey (), result.getSortKey ());
//  assertEquals (info.getContexts ().size (), result.getContexts ().size ());
//  for (int i = 0; i < info.getContexts ().size (); i ++)
//  {
//   EnumContextValueInfo contextInfo = info.getContexts ().get (i);
//   EnumContextValueInfo contextResult = result.getContexts ().get (i);
//   assertEquals (contextInfo.getType (), contextResult.getType ());
//   assertEquals (contextInfo.getValue (), contextResult.getValue ());
//  }
//
//  // get
//  info = result;
//  List<EnumeratedValueInfo> enumeration = null;
//
//  try
//  {
//   enumeration = instance.getEnumeration (enumerationKey, context.getType (),
//                                          context.getValue (), new Date ());
//   assertNotNull (enumeration);
//   assertEquals (1, enumeration.size ());
//   result = enumeration.get (0);
//  }
//  catch (DoesNotExistException ex)
//  {
//   fail ("should exist");
//  }
//  assertNotNull (result);
//  assertNotSame (info, result);
//  assertEquals (info.getCode (), result.getCode ());
//  assertEquals (info.getAbbrevValue (), result.getAbbrevValue ());
//  assertEquals (info.getValue (), result.getValue ());
//  assertEquals (info.getEffectiveDate (), result.getEffectiveDate ());
//  assertEquals (info.getExpirationDate (), result.getExpirationDate ());
//  assertEquals (info.getSortKey (), result.getSortKey ());
//  assertEquals (info.getContexts ().size (), result.getContexts ().size ());
//  for (int i = 0; i < info.getContexts ().size (); i ++)
//  {
//   EnumContextValueInfo contextInfo = info.getContexts ().get (i);
//   EnumContextValueInfo contextResult = result.getContexts ().get (i);
//   assertEquals (contextInfo.getType (), contextResult.getType ());
//   assertEquals (contextInfo.getValue (), contextResult.getValue ());
//  }
//
////  // update
////  info = result;
////  info.setValue ("new value");
////  try
////  {
////   result = instance.updateEnumeratedValue (enumerationKey, info);
////  }
////  catch (DoesNotExistException ex)
////  {
////   fail ("got already exists exception");
////  }
////  assertNotNull (result);
////  assertNotSame (info, result);
////  assertEquals (info.getCode (), result.getCode ());
////  assertEquals (info.getAbbrevValue (), result.getAbbrevValue ());
////  assertEquals (info.getValue (), result.getValue ());
////  assertEquals (info.getEffectiveDate (), result.getEffectiveDate ());
////  assertEquals (info.getExpirationDate (), result.getExpirationDate ());
////  assertEquals (info.getSortKey (), result.getSortKey ());
////  assertEquals (info.getContexts ().size (), result.getContexts ().size ());
////  for (int i = 0; i < info.getContexts ().size (); i ++)
////  {
////   EnumContextValueInfo contextInfo = info.getContexts ().get (i);
////   EnumContextValueInfo contextResult = result.getContexts ().get (i);
////   assertEquals (contextInfo.getType (), contextResult.getType ());
////   assertEquals (contextInfo.getValue (), contextResult.getValue ());
////  }
//
//
//  // remove
//  info = result;
//  try
//  {
//   boolean success = instance.removeEnumeratedValue (enumerationKey,
//                                                     info.getCode ());
//   assertEquals (true, success);
//  }
//  catch (DoesNotExistException ex)
//  {
//   fail ("got does not exist exception");
//  }
// }

 /**
  * Test of createCourse method, of class EnumManService.
  */
 @Test
 public void testGetEnumerationMetas ()
 {
  System.out.println ("testGetEnumerationMetas");
  EnumManService instance = new EnumManService ();
  instance.setHostUrl (EnumeratedValueLoaderModelFactory.LOCAL_HOST_URL);
  List<EnumerationMetaInfo>  result = null;
  result = instance.getEnumerationMetas ();
  assertNotNull (result);
  if (result.size () == 0)
  {
   fail ("no metadata returned");
  }
  for (EnumerationMetaInfo meta : result)
  {
   System.out.println (meta.getKey () + " - " + meta.getName ());
  }
  for (EnumerationMetaInfo meta : result)
  {
   System.out.println (meta.getKey () + " - " + meta.getName ());
   System.out.println ("     Desc=" + meta.getDesc ());
   String contextsLabel = "     Contexts=";
   for (EnumContextInfo contextInfo: meta.getContextDescriptors ())
   {
    System.out.println ("     " + contextsLabel + contextInfo.getType ());
    contextsLabel = "              ";
   }
   for (EnumeratedValueFieldInfo fieldInfo : meta.getEnumeratedValueFields ())
   {
    System.out.println (fieldInfo.getKey () + " " + fieldInfo.getFieldDescriptor ().getDataType ());
   }

  }
 }

}
