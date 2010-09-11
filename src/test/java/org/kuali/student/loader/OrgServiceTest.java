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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.wsdl.organization.OrgInfo;
import org.kuali.student.wsdl.organization.OrgTypeInfo;
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
  * Test of find method, of class OrgFinder.
  */
 @Test
 public void testFindMatch ()
 {
  System.out.println ("findMatch");
  String orgName = "";
  List<String> types = null;
  OrgService instance = new OrgService ();
  String expResult = "";
  String result = instance.findMatch (orgName, types);
  assertEquals (expResult, result);
 }

  /**
  * Test of getAll
  */
 @Test
 public void testGetAll ()
 {
  System.out.println ("getAll");
  OrgService instance = new OrgService ();
  List<OrgResultGeneric> expResult = new ArrayList ();
  List<OrgResultGeneric> result = instance.getAll ();
  assertEquals (expResult, result);
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
  assertEquals (17, result.size ());
 }

 /**
  * Test of getTypes method in OrgFinder
  */
 @Test
 public void testGetSearchTypes ()
 {
  System.out.println ("getSearchTypes");
  OrgService instance = new OrgService ();
//  List<OrgTypeInfo> expResult = new ArrayList ();
  List<SearchTypeInfo> result = instance.getSearchTypes ();
  System.out.println (result.size () + " rows returned");
  for (SearchTypeInfo type : result)
  {
   System.out.print (type.getKey ());
   System.out.print ("|");
   System.out.print (type.getName ());
   System.out.print ("|");
   System.out.print (type.getDesc ());
   System.out.println ("|");
  }
  assertEquals (16, result.size ());
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
  OrgInfo result = instance.getOrganization (id);
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
 public void testCreateOrganization ()
 {
  System.out.println ("createOrganization");
  OrgService instance = new OrgService ();
//  List<OrgTypeInfo> expResult = new ArrayList ();
  OrgInfo info = new OrgInfo ();
  info.setType ("kuali.org.Department");
  info.setState ("active");
  info.setShortName ("short name");
  info.setLongName ("long name that is longer than the short name");
  OrgInfo result = instance.createOrganization (info);
  assertNotNull (result);
  assertNotNull (result.getId ());
  assertEquals (info.getType (), result.getType ());
  assertEquals (info.getState (), result.getState ());
  assertEquals (info.getShortName (), result.getShortName ());
  assertEquals (info.getLongName (), result.getLongName ());
  System.out.println ("id=" + result.getId ());

 }
}
