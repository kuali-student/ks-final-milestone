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
package org.kuali.student.orchestration.orch;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.core.assembly.data.Metadata;
import static org.junit.Assert.*;
import org.kuali.student.orchestration.LookupMetadataDumper;

/**
 *
 * @author nwright
 */
public class CreditCourseMetadataTest
{

 public CreditCourseMetadataTest ()
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
  * Test of matches method, of class CreditCourseMetadata.
  */
 @Test
 public void testMatches ()
 {
  System.out.println ("matches");
  String inputType = "";
  String inputState = "";
  String dictType = "";
  String dictState = "";
  CreditCourseMetadata instance = new CreditCourseMetadata ();
  boolean expResult = true;
  boolean result = instance.matches (inputType, inputState, dictType, dictState);
 }

 /**
  * Test of getMetadata method, of class CreditCourseMetadata.
  */
 @Test
 public void testGetMetadataForCreditCourse ()
 {
  System.out.println ("getMetadataForCreditCourse");
  String type = "";
  String state = "";
  CreditCourseMetadata instance = new CreditCourseMetadata ();
  Metadata meta = instance.getMetadata (type, state);
  new LookupMetadataDumper (System.out, meta, "CreditCourse", 0).dump ();
  assertTrue (true);
 }

}
