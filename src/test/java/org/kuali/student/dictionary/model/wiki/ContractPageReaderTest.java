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

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.dictionary.TestConstants;
import org.kuali.student.dictionary.model.ServiceMethod;
import org.kuali.student.dictionary.model.util.ServiceMethodDumper;
import org.w3c.dom.Node;

/**
 *
 * @author nwright
 */
public class ContractPageReaderTest implements TestConstants
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
  "1BDFD210C59231F650A842CF3A2F6688.Kuali3_1Engine";

 public ContractPageReaderTest ()
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

// /**
//  * Test of getDocument method, of class ContractReader.
//  */
// @Test
// public void testGetDocument ()
// {
//  System.out.println ("getDocument");
//  ContractReader instance = new ContractReader (ATP_CONTRACT_PATH, JSESSIONID);
//  Document doc = instance.getDocument ();
//  new DocumentDumper (doc, System.out).dump ();
// }
 /**
  * Test of getDocument method, of class ContractReader.
  */
 @Test
 public void testGetMethodTableNodes ()
 {
  System.out.println ("getMethodTableNodes");
  ContractPageReader instance =
   new ContractPageReader ("atp", ATP_CONTRACT_PATH_ON_WIKI, JSESSIONID);
  List<Node> list = instance.getMethodTableNodes ();
  for (Node node : list)
  {
   System.out.println ("method table is " + node.getNodeName () + "=" + node.
    getNodeValue ());
  }
  new NodeHelper ().dump (list.get (1), System.out);

 }

 /**
  * Test of getDocument method, of class ContractReader.
  */
 @Test
 public void testGetNameValuePairsFromMethodTable ()
 {
  System.out.println ("getNameValuePairsFromMethodTable");
  ContractPageReader instance =
   new ContractPageReader ("atp", ATP_CONTRACT_PATH_ON_WIKI, JSESSIONID);
  List<Node> methodTables = instance.getMethodTableNodes ();
  List<ContractPageReader.NameValue> nvs =
   instance.getNameValuePairsFromMethodTable (methodTables.get (1));
  for (ContractPageReader.NameValue nv : nvs)
  {
   System.out.println (nv.name + "=" + nv.value + " - " + nv.url);
  }

 }

 /**
  * Test of getDocument method, of class ContractReader.
  */
 @Test
 public void testGetServiceMethods ()
 {
  System.out.println ("getServiceMethods");
  ContractPageReader instance =
   new ContractPageReader ("atp", ATP_CONTRACT_PATH_ON_WIKI, JSESSIONID);
  List<ServiceMethod> methods = instance.getServiceMethods ();
  for (ServiceMethod method : methods)
  {
   new ServiceMethodDumper (method, System.out).dump ();
  }

 }

}
