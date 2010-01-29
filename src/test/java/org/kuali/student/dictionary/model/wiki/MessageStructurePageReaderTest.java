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
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.util.MessageStructureDumper;
import org.w3c.dom.Node;

/**
 *
 * @author nwright
 */
public class MessageStructurePageReaderTest implements TestConstants
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
  "69164E6A77E6246078B6C92DB3504E1A.Kuali3_1Engine";

 public MessageStructurePageReaderTest ()
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
//  * Test of getDocument messageStructure, of class MessageStructureReader.
//  */
// @Test
// public void testGetDocument ()
// {
//  System.out.println ("getDocument");
//  MessageStructureReader instance = new MessageStructureReader (ATP_CONTRACT_PATH, JSESSIONID);
//  Document doc = instance.getDocument ();
//  new DocumentDumper (doc, System.out).dump ();
// }
 /**
  * Test of getDocument messageStructure, of class MessageStructureReader.
  */
 @Test
 public void testGetStructureTableNodes ()
 {
  System.out.println ("getStructureTableNodes");
  MessageStructurePageReader2 instance =
   new MessageStructurePageReader2 (ATP_DURATION_TYPE_CONTRACT_PATH_ON_WIKI, JSESSIONID);
  List<Node> list = instance.getStructureTableNodes ();
  for (Node node : list)
  {
   System.out.println ("Structure table is " + node.getNodeName () + "=" + node.
    getNodeValue ());
  }
  new NodeHelper ().dump (list.get (0), System.out);

 }

 /**
  * Test of getDocument messageStructure, of class MessageStructureReader.
  */
 @Test
 public void testGetNameValuePairsFromMessageStructureTable ()
 {
  System.out.println ("getNameValuePairsFromMessageStructureTable");
  MessageStructurePageReader2 instance =
   new MessageStructurePageReader2 (ATP_DURATION_TYPE_CONTRACT_PATH_ON_WIKI, JSESSIONID);
  List<Node> messageStructureTables = instance.getStructureTableNodes ();
  List<MessageStructurePageReader2.NameValue> nvs =
   instance.getNameValuePairsFromStructureTable (messageStructureTables.get (0));
  for (MessageStructurePageReader2.NameValue nv : nvs)
  {
   System.out.println (nv.name + "=" + nv.value + " - " + nv.url);
  }

 }


 /**
  * Test of getDocument messageStructure, of class MessageStructureReader.
  */
 @Test
 public void testGetServiceMessageStructures ()
 {
  System.out.println ("getServiceMessageStructures");
  MessageStructurePageReader2 instance =
   new MessageStructurePageReader2 (ATP_DURATION_TYPE_CONTRACT_PATH_ON_WIKI, JSESSIONID);
  List<MessageStructure> messageStructures = instance.getMessageStructures ();
  for (MessageStructure messageStructure : messageStructures)
  {
   new MessageStructureDumper (messageStructure, System.out).dump ();
  }
 }

}
