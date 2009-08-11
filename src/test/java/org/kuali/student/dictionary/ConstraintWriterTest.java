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
package org.kuali.student.dictionary;

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
public class ConstraintWriterTest
{

 public ConstraintWriterTest ()
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

  private Constraint getTestConstraint ()
 {
  Constraint constraint = new Constraint ();
  constraint.setId ("testId");
  constraint.setDesc ("Basic constraints on code fields");
  constraint.setServerSide ("true");
  constraint.setMinLength ("0");
  constraint.setMaxLength ("20");
  constraint.setMinOccurs ("0");
  constraint.setMaxOccurs ("1");
  constraint.setValidChars ("[A-Za-z0-9 -.]*");
  constraint.setLookup ("test.lookup");
  constraint.setComments ("Note 1: There is a space character before the hyphen and it is a valid character" +
   "\n" +
   "Note 2: The *embedded* new lines that are between the various sets of charactsrs are for readability purposes only and are not allowed");
  return constraint;
 }


 /**
  * Test of write method, of class ConstraintWriter.
  */
 @Test
 public void testWrite ()
 {
  System.out.println ("write");
  ByteArrayOutputStream baos = new ByteArrayOutputStream ();
  PrintStream out = new PrintStream (baos);
  Constraint constraint = getTestConstraint ();
  ConstraintWriter instance = new ConstraintWriter (out, 0, constraint);
  instance.write ();
  System.out.println ("|||||||");
  System.out.println (baos.toString ());
  System.out.println ("|||||||");
  assertEquals (668, baos.toString ().length ());
 }

 /**
  * Test of write method, of class ConstraintWriter.
  */
 @Test
 public void testWriteEmptyValidChars ()
 {
   System.out.println ("writeEmptyValidChars");
  ByteArrayOutputStream baos = new ByteArrayOutputStream ();
  PrintStream out = new PrintStream (baos);
  Constraint constraint = getTestConstraint ();
  ConstraintWriter instance = new ConstraintWriter (out, 0, constraint);
  constraint.setValidChars ("");
  instance = new ConstraintWriter (out, 0, constraint);
  instance.write ();
  System.out.println ("|||||||");
  System.out.println (baos.toString ());
  System.out.println ("|||||||");
  assertEquals (581, baos.toString ().length ());
 }

}