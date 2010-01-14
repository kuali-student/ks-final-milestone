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

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author nwright
 */
public class ContractReaderTest
{

 public static final String ATP_CONTRACT_PATH =
  "https://test.kuali.org/confluence/display/KULSTU/Academic+Time+Period+Service";
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
  "892A1381C9F5A588C46ADEE1441E7A5D.Kuali3_1Engine";

 public ContractReaderTest ()
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
 public void testAppendMethodTableNodes ()
 {
  System.out.println ("appendMethodTableNodes");
  ContractReader instance = new ContractReader (ATP_CONTRACT_PATH, JSESSIONID);
  Document doc = instance.getDocument ();
  List<Node> list = instance.getMethodTableNodes (doc);
  for (Node node : list)
  {
   System.out.println ("method table is " + node.getNodeName () + "=" + node.
    getNodeValue ());
  }
  new NodeDumper (list.get (0), System.out).dump ();

 }

 /**
  * Test of getDocument method, of class ContractReader.
  */
 @Test
 public void testGetTdNodesWithClass ()
 {
  System.out.println ("GetTdNodesWithClass");
  ContractReader instance = new ContractReader (ATP_CONTRACT_PATH, JSESSIONID);
  Document doc = instance.getDocument ();
  List<Node> methodTables = instance.getMethodTableNodes (doc);
  List<ContractReader.NameValue> nvs =
   instance.getNameValuePairsFromMethodTable (methodTables.get (0));
  for (ContractReader.NameValue nv : nvs)
  {
   System.out.println (nv.name + "=" + nv.value + " - " + nv.url);
  }

 }

}
