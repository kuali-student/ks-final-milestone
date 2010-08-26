/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model.util;

import org.kuali.student.dictionary.model.util.ServicesFilterExcludeDev;
import org.kuali.student.dictionary.model.util.ServicesFilterChained;
import org.kuali.student.dictionary.model.util.ServicesFilterLatestVersionOnly;
import org.kuali.student.dictionary.model.util.ServicesFilter;
import org.kuali.student.dictionary.model.util.ServicesFilterByKeys;
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
public class ServicesFilterChainedTest
{

 public ServicesFilterChainedTest ()
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
  Service atpdev = newService ("atp", "dev", "Academic Time Period");
  Service atprc2 = newService ("atp", "v1.0-rc2", "Academic Time Period");
  Service authv1 = newService ("authorization", "v1.0", "Authorization");
  Service brmsv1 = newService ("brms", "v1.0", "Business Rules Management");
  Service cmntr2 = newService ("comment", "v1.0-rc2", "Comment");

  services.add (atprc1);
  services.add (atpdev);
  services.add (atprc2);
  services.add (authv1);
  services.add (brmsv1);
  services.add (cmntr2);

  List<ServicesFilter> filters = new ArrayList ();
  filters.add (new ServicesFilterExcludeDev ());
  filters.add (new ServicesFilterLatestVersionOnly ());
  List<String> keys = new ArrayList ();
  keys.add ("comment");
  keys.add ("atp");
  filters.add (new ServicesFilterByKeys (keys));

  ServicesFilterLatestVersionOnly latestVersionFilter =
   new ServicesFilterLatestVersionOnly ();
  assertEquals (0, latestVersionFilter.compare ("v1-rc1", "v1-rc1"));
  assertEquals (-1, latestVersionFilter.compare ("v1-rc1", "v1-rc2"));
  assertEquals (1, latestVersionFilter.compare ("v1-rc2", "v1-rc1"));
  assertEquals (1, latestVersionFilter.compare ("v1-rc2", "dev"));

  ServicesFilterChained instance = new ServicesFilterChained (filters);
  List<Service> expResult = new ArrayList ();
  expResult.add (atprc2);
  expResult.add (cmntr2);
  List<Service> result = instance.filter (services);
  for (Service serv : result)
  {
   System.out.println ("service=" + serv.getKey () + "(" + serv.getVersion () +
    ")");
  }
  assertEquals (expResult.size (), result.size ());
  for (int i = 0; i < expResult.size (); i ++)
  {
   assertEquals (expResult.get (i).getKey () + "(" + expResult.get (i).getVersion () +")",
    result.get (i).getKey () + "(" +  result.get (i).getVersion () + ")");
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
