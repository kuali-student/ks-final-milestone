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
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class OrganizationLoaderTest
{

 public OrganizationLoaderTest ()
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

 /**
  * Test of load method, of class OrgLoader.
  */
 @Test
 public void testLoadOrganizations ()
   throws Exception
 {
  System.out.println (new Date () + " load organizations");

  OrganizationLoader ccLoader = new OrganizationLoader ();
  OrgService orgService = new OrgService ();
  orgService.setHostUrl (OrganizationLoaderModelFactory.LOCAL_HOST_URL);
  ccLoader.setOrgService (orgService);
  OrganizationLoaderModel ccModel = OrgLoaderModelFactoryTest.getInstance ().
    getModel ();

  System.out.println (new Date () + " getting organizations...");
  List<Organization> orgs = ccModel.getOrganizations ();

  System.out.println (new Date () + " loading " + orgs.size ()
                      + " organizations");
//  ccLoader.setInputDataSource (orgs.subList (372, 373).iterator ());
  ccLoader.setInputDataSource (orgs.iterator ());
  List<OrganizationLoadResult> results = ccLoader.update ();
  int created = 0;
  int failures = 0;
  for (OrganizationLoadResult result : results)
  {
   if (result.isSuccess ())
   {
    created ++;
    System.out.println (result.getOrgInfo ().getShortName () + " id = " + result.getOrgInfo ().getId ());
   }
   else
   {
    failures ++;
   }
  }
  System.out.println (created + " recordes created out of " + orgs.size () + " organizations");
  System.out.println (failures + " records failed to load");
  for (OrganizationLoadResult result : results)
  {
   if ( ! result.isSuccess ())
   {
    System.out.println (result);
   }
  }
  if (failures > 0)
  {
   fail (failures + " records failed to load");
  }
 }
}
