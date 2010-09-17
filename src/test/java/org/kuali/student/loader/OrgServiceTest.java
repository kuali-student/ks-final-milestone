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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.wsdl.organization.AlreadyExistsException;
import org.kuali.student.wsdl.organization.DataValidationErrorException;
import org.kuali.student.wsdl.organization.DoesNotExistException;
import org.kuali.student.wsdl.organization.OrgInfo;
import org.kuali.student.wsdl.organization.OrgTypeInfo;
import org.kuali.student.wsdl.organization.VersionMismatchException;
import org.kuali.student.wsdl.search.SearchTypeInfo;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class OrgServiceTest
{

 public OrgServiceTest ()
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

 /**
  * Test of getTypes method in OrgFinder
  */
 @Test
 public void testGetOrgTypes ()
 {
  System.out.println ("getOrgTypes");
  OrgService instance = new OrgService ();
//  List<OrgTypeInfo> expResult = new ArrayList ();
  List<OrgTypeInfo> result = instance.getOrgTypes ();
  System.out.print (result.size () + " types returned");
  System.out.print ("Key");
  System.out.print ("|");
  System.out.print ("Name");
  System.out.print ("|");
  System.out.print ("getDesc");
  System.out.print ("|");
  System.out.print ("EffectiveDate");
  System.out.print ("|");
  System.out.print ("ExpirationDate");
  System.out.println ("|");
  for (OrgTypeInfo typeInfo : result)
  {
   System.out.print (typeInfo.getKey ());
   System.out.print ("|");
   System.out.print (typeInfo.getName ());
   System.out.print ("|");
   System.out.print (typeInfo.getDesc ());
   System.out.print ("|");
   System.out.print (typeInfo.getEffectiveDate ());
   System.out.print ("|");
   System.out.print (typeInfo.getExpirationDate ());
   System.out.println ("|");
  }
  assertEquals (18, result.size ());
 }

 /**
  * Test of GetOrganization method in OrgFinder
  */
 @Test
 public void testGetOrganization ()
 {
  System.out.println ("getOrganization");
  OrgService instance = new OrgService ();
//  List<OrgTypeInfo> expResult = new ArrayList ();
  String id = "1";
  OrgInfo result;
  try
  {
   result = instance.getOrganization (id);
  }
  catch (DoesNotExistException ex)
  {
   throw new RuntimeException (ex);
  }
  assertNotNull (result);
  System.out.print (result.getId ());
  System.out.print ("|");
  System.out.print (result.getShortName ());
  System.out.println ("|");

 }

 /**
  * Test of CreateOrganization method in OrgFinder
  */
 @Test
 public void testOrganizationLifeCycle ()
 {
  System.out.println ("createOrganization");
  OrgService instance = new OrgService ();
//  List<OrgTypeInfo> expResult = new ArrayList ();
  OrgInfo info = new OrgInfo ();
  info.setType ("kuali.org.Department");
  info.setState ("active");
  info.setShortName ("short name");
  info.setLongName ("long name that is longer than the short name");
  info.setSortName ("sort name");
  OrgInfo result = null;
  try
  {
   result = instance.createOrganization (info);
  }
  catch (AlreadyExistsException ex)
  {
   throw new RuntimeException (ex);
  }
  assertNotNull (result);
  assertNotNull (result.getId ());
  assertEquals (info.getType (), result.getType ());
  assertEquals (info.getState (), result.getState ());
  assertEquals (info.getShortName (), result.getShortName ());
  assertEquals (info.getLongName (), result.getLongName ());
//  assertEquals (info.getSortName (), result.getSortName ());
  System.out.println ("id=" + result.getId ());

  // get it now
  info = result;
  try
  {
   result = instance.getOrganization (info.getId ());
  }
  catch (DoesNotExistException ex)
  {
   fail ("org was just created by cannot get it");
  }
  assertNotNull (result.getId ());
  assertEquals (info.getType (), result.getType ());
  assertEquals (info.getState (), result.getState ());
  assertEquals (info.getShortName (), result.getShortName ());
  assertEquals (info.getLongName (), result.getLongName ());

  // now update it
  info = result;
  info.setLongName ("new long name");
  try
  {
   result = instance.updateOrganization (info);
  }
  catch (DoesNotExistException ex)
  {
   throw new RuntimeException (ex);
  }
  catch (VersionMismatchException ex)
  {
   throw new RuntimeException (ex);
  }
  catch (DataValidationErrorException ex)
  {
   throw new RuntimeException (ex);
  }
  assertNotNull (result.getId ());
  assertEquals (info.getType (), result.getType ());
  assertEquals (info.getState (), result.getState ());
  assertEquals (info.getShortName (), result.getShortName ());
  assertEquals (info.getLongName (), result.getLongName ());


  // now delete
  info = result;
  boolean success = false;
  try
  {
   success = instance.deleteOrganization (info.getId ());
  }
  catch (DoesNotExistException ex)
  {
   throw new RuntimeException (ex);
  }
  catch (DataValidationErrorException ex)
  {
   throw new RuntimeException (ex);
  }
  assertTrue (success);

  // get it now
  info = result;
  try
  {
   result = instance.getOrganization (info.getId ());
   fail ("should not be able to get the org");
  }
  catch (DoesNotExistException ex)
  {
   System.out.println ("as expected org does not exist");
  }
  catch (Exception ex)
  {
   System.out.println (
     "The server does not throw a DoesNotExistException but oh well");
  }


 }
}
