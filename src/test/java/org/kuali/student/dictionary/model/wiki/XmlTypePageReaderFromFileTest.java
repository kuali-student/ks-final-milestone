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
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.util.XmlTypeDumper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class XmlTypePageReaderFromFileTest implements RunConstants
{

 public XmlTypePageReaderFromFileTest ()
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
  * Test of getDocument xmlType, of class XmlTypeReader.
  */
// @Test
// public void testGetDocument ()
// {
//  System.out.println ("getDocument");
//  XmlTypePageReader instance =
//   new XmlTypePageReader (new File (ATP_DURATION_TYPE_CONTRACT_PATH_ON_DISK));
//  Document doc = instance.getDocument ();
//  new NodeHelper ().dump (doc, System.out);
// }

// /**
//  * Test of getDocument xmlType, of class XmlTypeReader.
//  */
// @Test
// public void testGetStructureTableNode ()
// {
//  System.out.println ("getStructureTableNode");
//  XmlTypePageReader instance =
//   new XmlTypePageReader (new File (ATP_DURATION_TYPE_CONTRACT_PATH_ON_DISK));
//  Node node = instance.getStructureMetaTableNode ();
//  System.out.println ("Structure table is " + node.getNodeName () + "=" + node.
//   getNodeValue ());
//  new NodeHelper ().dump (node, System.out);
//
// }
//
// /**
//  * Test of getDocument xmlType, of class XmlTypeReader.
//  */
// @Test
// public void testGetNameValuePairsFromStructureMetaTable ()
// {
//  System.out.println ("getNameValuePairsFromStructureMetaTable");
//  XmlTypePageReader instance =
//   new XmlTypePageReader (new File (ATP_DURATION_TYPE_CONTRACT_PATH_ON_DISK));
//  Node structureMetaTableNode = instance.getStructureMetaTableNode ();
//  List<XmlTypePageReader.NameValue> nvs =
//   instance.getNameValuePairsFromStructureMetaTable (structureMetaTableNode);
//  for (XmlTypePageReader.NameValue nv : nvs)
//  {
//   System.out.println (nv.name + "=" + nv.value + " - " + nv.url);
//  }
// }

 /**
  * Test of getDocument xmlType, of class XmlTypeReader.
  */
 @Test
 public void testGetXmlType ()
 {
  System.out.println ("getXmlType");
  XmlTypePageReader instance =
   new XmlTypePageReader (ATP_DURATION_TYPE_CONTRACT_PATH_ON_DISK,
   new File (ATP_DURATION_TYPE_CONTRACT_PATH_ON_DISK));
  XmlType xmlType = instance.getXmlType ();
  new XmlTypeDumper (xmlType, System.out).dump ();
  assertEquals ("atpDurationTypeInfo", xmlType.getName ());
  assertEquals ("Dev (1.0-rc2)", xmlType.getVersion ());
  assertEquals ("src/test/resources/atpDurationTypeInfo+Structure.html", xmlType.getUrl ());
  assertEquals ("Information about an academic time period duration type.", xmlType.getDesc ());
 }

}
