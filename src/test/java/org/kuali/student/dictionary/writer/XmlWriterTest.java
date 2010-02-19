/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.dictionary.writer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
public class XmlWriterTest
{

 public XmlWriterTest ()
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
  * Test of writeAttribute method, of class FieldWriter.
  */
 @Test
 public void testWriteAttribute ()
 {
  System.out.println ("writeAttribute");
  ByteArrayOutputStream baos = new ByteArrayOutputStream ();
  PrintStream out = new PrintStream (baos);
  XmlWriter instance = new SimpleXmlWriter (out, 0);
  String attribute = "id";
  String value = "myId";
  instance.writeAttribute (attribute, value);
  assertEquals (" id=\"myId\"", baos.toString ());
 }

 /**
  * Test of writeAttribute method, of class FieldWriter.
  */
 @Test
 public void testWriteBlankAttribute ()
 {
  System.out.println ("writeBlankAttribute");
  ByteArrayOutputStream baos = new ByteArrayOutputStream ();
  PrintStream out = new PrintStream (baos);
  XmlWriter instance = new SimpleXmlWriter (out, 0);
  String attribute = "id";
  String value = "";
  instance.writeAttribute (attribute, value);
  assertEquals ("", baos.toString ());
 }


 /**
  * Test of writeTag method, of class FieldWriter.
  */
 @Test
 public void testWriteTag ()
 {
  System.out.println ("writeTag");
  ByteArrayOutputStream baos = new ByteArrayOutputStream ();
  PrintStream out = new PrintStream (baos);
  XmlWriter instance = new SimpleXmlWriter (out, 0);
  String tag = "testTag";
  String value = "testTagValue";
  instance.writeTag (tag, value);
  //System.out.println ("|" + baos.toString () + "|");
  assertEquals ("<testTag>testTagValue</testTag>\r\n", baos.toString ());
 }

 /**
  * Test of writeTag method, of class FieldWriter.
  */
 @Test
 public void testWriteBlankTag ()
 {
  System.out.println ("writeBkankTag");
  ByteArrayOutputStream baos = new ByteArrayOutputStream ();
  PrintStream out = new PrintStream (baos);
  XmlWriter instance = new SimpleXmlWriter (out, 0);
  String tag = "testTag";
  String value = "";
  instance.writeTag (tag, value);
  //System.out.println ("|" + baos.toString () + "|");
  assertEquals ("", baos.toString ());
 }


 /**
  * Test of writeComment method, of class XmlWriter.
  */
 @Test
 public void testWriteComment ()
 {
  System.out.println ("writeComment");
  ByteArrayOutputStream baos = new ByteArrayOutputStream ();
  PrintStream out = new PrintStream (baos);
  XmlWriter instance = new SimpleXmlWriter (out, 0);
  String comment = "test Comment with new \n line in the middle";
  instance.writeComment (comment);
  System.out.println ("|" + baos.toString () + "|");
  assertEquals ("<!-- test Comment with new \n line in the middle -->\r\n", baos.
   toString ());
 }

 /**
  * Test of writeComment method, of class XmlWriter.
  */
 @Test
 public void testWriteBlankComment ()
 {
  System.out.println ("writeBlankComment");
  ByteArrayOutputStream baos = new ByteArrayOutputStream ();
  PrintStream out = new PrintStream (baos);
  XmlWriter instance = new SimpleXmlWriter (out, 0);
  String comment = "";
  instance.writeComment (comment);
  System.out.println ("|" + baos.toString () + "|");
  assertEquals ("", baos.toString ());
 }

 /**
  * Test of escapeXML method, of class XmlWriter.
  */
 @Test
 public void testEscapeXML ()
 {
  System.out.println ("escapeXML");
  XmlWriter instance = new SimpleXmlWriter (System.out, 0);
  assertEquals ("&lt;", instance.escapeXML ("<"));
  assertEquals ("&gt;", instance.escapeXML (">"));
  assertEquals ("xyz", instance.escapeXML ("xyz"));
  assertEquals ("x&lt;y&gt;z", instance.escapeXML ("x<y>z"));
 }

}