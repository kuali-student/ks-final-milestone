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
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.util.MessageStructureDumper;
import org.w3c.dom.Node;

/**
 *
 * @author nwright
 */
public class MessageStructurePageReaderFromFileTest implements RunConstants
{
 public MessageStructurePageReaderFromFileTest ()
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
  MessageStructurePageReader instance =
   new MessageStructurePageReader (new File (ATP_DURATION_TYPE_CONTRACT_PATH_ON_DISK));
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
  MessageStructurePageReader instance =
   new MessageStructurePageReader (new File (ATP_DURATION_TYPE_CONTRACT_PATH_ON_DISK));
  List<Node> messageStructureTables = instance.getStructureTableNodes ();
  List<MessageStructurePageReader.NameValue> nvs =
   instance.getNameValuePairsFromStructureTable (messageStructureTables.get (0));
  for (MessageStructurePageReader.NameValue nv : nvs)
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
  MessageStructurePageReader instance =
   new MessageStructurePageReader (new File (ATP_DURATION_TYPE_CONTRACT_PATH_ON_DISK));
  List<MessageStructure> messageStructures = instance.getMessageStructures ();
  for (MessageStructure messageStructure : messageStructures)
  {
   new MessageStructureDumper (messageStructure, System.out).dump ();
  }

 }

}
