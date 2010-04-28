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
package org.kuali.student.dictionary.model.wiki;

import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.dictionary.command.run.RunConstants;
import org.kuali.student.dictionary.model.Service;
import org.w3c.dom.Node;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class ServiceRepositoryPageReaderTest implements RunConstants
{

 //           ***** NOTE *******
 // In firefox to get this you have to do the following:
 // Menu Toools/Options
 // Privacy Tab
 // Change "History/FireFox will" from "remmber history to "use custom settings for history"
 // Show Cookies button
 // Scoll down or search for to "test.kuali.org"
 // Select the JSESSIONID cookie
 // cut and paste the content here.
 // ==> the JSessionID changes everytime you drop out of the browser.
 public static final String JSESSIONID =
  "EFD3DCBDE8A0820236C270EE6BA1CB5F.Kuali3_1Engine";

 public ServiceRepositoryPageReaderTest ()
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
  * Test of getDocument method, of class ContractReader.
  */
 @Test
 public void testGetServices ()
 {
  System.out.println ("getServices");
//  ServiceRepositoryPageReader instance =
//   new ServiceRepositoryPageReader (SERVICE_REPOSITORY_PATH_ON_WIKI, JSESSIONID);
//  List<Service> list = instance.getServices ();
//  for (Service service : list)
//  {
//   System.out.println (service.getKey () + "=" + service.getName () + "(" + service.
//    getVersion () + ") " + service.getUrl ());
//  }
 }

 
 @Test
 public void testExtractLoad ()
 {
  System.out.println ("extractLoad");
  ServiceRepositoryPageReader instance =
   new ServiceRepositoryPageReader (new File (SERVICE_REPOSITORY_PATH_ON_DISK));
  Service service = new Service ();
  instance.extractLoad (service, "Academic Time Period Service v1.0-rc1");
  assertEquals ("Academic Time Period", service.getName ());
  assertEquals ("v1.0-rc1", service.getVersion ());
  assertEquals ("atp", service.getKey ());

  service = new Service ();
  instance.extractLoad (service, "Comment Service v1.0-rc1");
  assertEquals ("Comment", service.getName ());
  assertEquals ("v1.0-rc1", service.getVersion ());
  assertEquals ("comment", service.getKey ());
 }

 /**
  * Test of getDocument method, of class ContractReader.
  */
 @Test
 public void testGetHtmlLinkNodes ()
 {
  System.out.println ("getHtmlLinkNodes");
  ServiceRepositoryPageReader instance =
   new ServiceRepositoryPageReader (new File (SERVICE_REPOSITORY_PATH_ON_DISK));
  List<Node> list = instance.getHtmlLinkNodes ();
  for (Node node : list)
  {
   new NodeHelper ().dump (node, System.out);
  }
 }

}
