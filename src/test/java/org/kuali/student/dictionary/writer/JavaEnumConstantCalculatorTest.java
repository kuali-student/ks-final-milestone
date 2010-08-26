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
package org.kuali.student.dictionary.writer;

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
public class JavaEnumConstantCalculatorTest
{

 public JavaEnumConstantCalculatorTest ()
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
  * Test of calc method, of class JavaEnumConstantCalculator.
  */
 @Test
 public void testCalc ()
 {
  System.out.println ("calc");
  assertEquals ("DELETED", new JavaEnumConstantCalculator ("deleted").calc ());
  assertEquals ("TRANSCRIPT_TITLE", new JavaEnumConstantCalculator ("transcriptTitle").calc ());
  assertEquals ("DYNAMIC_CLUSET", new JavaEnumConstantCalculator ("dynamicCLUSet").calc ());
 }

 /**
  * Test of reverse method, of class JavaEnumConstantCalculator.
  */
 @Test
 public void testReverse ()
 {
  System.out.println ("reverse");
  assertEquals ("Deleted", new JavaEnumConstantCalculator ("DELETED").reverse ());
  assertEquals ("TranscriptTitle", new JavaEnumConstantCalculator ("TRANSCRIPT_TITLE").reverse ());
  assertEquals ("DynamicCluSet", new JavaEnumConstantCalculator ("DYNAMIC_CLU_SET").reverse ());
 }

}
