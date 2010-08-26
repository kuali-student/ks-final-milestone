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
package org.kuali.student.dictionary.model.util;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kuali.student.dictionary.model.Service;

/**
 *
 * @author nwright
 */
public class ServicesFilterArrangeByKeysTest
{

 public ServicesFilterArrangeByKeysTest ()
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
  * Test of filter method, of class ServicesFilterChained.
  */
 @Test
 public void testFilter ()
 {
  System.out.println ("filter");
  Service service;
  List<Service> services = new ArrayList ();

  Service atprc1 = newService ("atp", "v1.0-rc1", "Academic Time Period");
  Service authv1 = newService ("authorization", "v1.0", "Authorization");
  Service brmsv1 = newService ("brms", "v1.0", "Business Rules Management");
  Service cmntr2 = newService ("comment", "v1.0-rc2", "Comment");

  services.add (atprc1);
  services.add (authv1);
  services.add (brmsv1);
  services.add (cmntr2);

  List<String> keys = new ArrayList ();
  keys.add ("comment");
  keys.add ("atp");
  keys.add ("authorization");
  keys.add ("brms");
  ServicesFilterArrangeByKeys instance = new ServicesFilterArrangeByKeys (keys);

 
  List<Service> expResult = new ArrayList ();
  expResult.add (cmntr2);
  expResult.add (atprc1);
  expResult.add (authv1);
  expResult.add (brmsv1);

  List<Service> result = instance.filter (services);
  for (Service serv : result)
  {
   System.out.println ("service=" + serv.getKey () + "(" + serv.getVersion ()
    + ")");
  }
  assertEquals (expResult.size (), result.size ());
  for (int i = 0; i < expResult.size (); i ++)
  {
   assertEquals (expResult.get (i).getKey () + "(" + expResult.get (i).
    getVersion () + ")",
                 result.get (i).getKey () + "(" + result.get (i).getVersion ()
    + ")");
  }
 }

 private Service newService (String key, String version, String name)
 {
  Service service = new Service ();
  service.setKey (key);
  service.setVersion (version);
  service.setName (name);
  return service;
 }

}
