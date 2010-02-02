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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.dictionary.TestConstants;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.util.XmlTypeDumper;
import org.w3c.dom.Document;

/**
 *
 * @author nwright
 */
public class XmlTypePageReaderTest implements TestConstants
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
  "D3E272B66BF8EE6630D5BF7018DCD575.Kuali3_1Engine";


 public XmlTypePageReaderTest ()
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
//   new XmlTypePageReader (ATP_DURATION_TYPE_CONTRACT_PATH_ON_WIKI, JSESSIONID);
//  Document doc = instance.getDocument ();
//  new NodeHelper ().dump (doc, System.out);
// }


// /**
//  * Test of getDocument xmlType, of class XmlTypeReader.
//  */
// @Test
// public void testGetNameValuePairsFromStructureMetaTable ()
// {
//  System.out.println ("getNameValuePairsFromStructureMetaTable");
//  XmlTypePageReader instance =
//   new XmlTypePageReader (ATP_DURATION_TYPE_CONTRACT_PATH_ON_WIKI, JSESSIONID);
//  Node structureMetaTableNode = instance.getStructureMetaTableNode ();
//  List<XmlTypePageReader.NameValue> nvs =
//   instance.getNameValuePairsFromStructureMetaTable (structureMetaTableNode);
//  for (XmlTypePageReader.NameValue nv : nvs)
//  {
//   System.out.println (nv.name + "=" + nv.value + " - " + nv.url);
//  }
// }

// /**
//  * Test of getDocument xmlType, of class XmlTypeReader.
//  */
// @Test
// public void testGetXmlTypeMapped ()
// {
//  System.out.println ("getXmlTypeMapped");
//  XmlTypePageReader instance =
//   new XmlTypePageReader (ATP_DURATION_TYPE_KEY_CONTRACT_PATH_ON_WIKI, JSESSIONID);
//  XmlType xmlType = instance.getXmlType ();
//  new XmlTypeDumper (xmlType, System.out).dump ();
//  assertEquals ("atpDurationTypeKey", xmlType.getName ());
//  assertEquals ("Dev (1.0-rc1)", xmlType.getVersion ());
//  assertEquals ("/confluence/display/KULSTU/atpDurationTypeKey+Structure+v1.0-rc1", xmlType.getUrl ());
//  assertEquals ("Unique identifier for an academic time period duration type.", xmlType.getDesc ());
//  assertEquals ("Mapped String", xmlType.getPrimitive ());
// }
//
//
// /**
//  * Test of getDocument xmlType, of class XmlTypeReader.
//  */
// @Test
// public void testGetXmlTypeComplex ()
// {
//  System.out.println ("getXmlTypeComplex");
//  XmlTypePageReader instance =
//   new XmlTypePageReader (ATP_DURATION_TYPE_CONTRACT_PATH_ON_WIKI, JSESSIONID);
//  XmlType xmlType = instance.getXmlType ();
//  new XmlTypeDumper (xmlType, System.out).dump ();
//  assertEquals ("atpDurationTypeInfo", xmlType.getName ());
//  assertEquals ("Dev (1.0-rc2)", xmlType.getVersion ());
//  assertEquals ("/confluence/display/KULSTU/atpDurationTypeInfo+Structure+v1.0-rc2", xmlType.getUrl ());
//  assertEquals ("Information about an academic time period duration type.", xmlType.getDesc ());
//  assertEquals ("Complex", xmlType.getPrimitive ());
// }

  /**
  * Test of getDocument xmlType, of class XmlTypeReader.
  */
 @Test
 public void testGetXmlTypeOrgPositionRestriction ()
 {
  System.out.println ("getXmlTypeOrgPositionRestriction");
  XmlTypePageReader instance =
   new XmlTypePageReader (ORG_POSITION_RESTRICTION_CONTRACT_PATH_ON_WIKI, JSESSIONID);
  Document doc = instance.getDocument ();
  new NodeHelper ().dump (doc, System.out);
  XmlType xmlType = instance.getXmlType ();
  new XmlTypeDumper (xmlType, System.out).dump ();
 }

}
