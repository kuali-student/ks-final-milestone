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
package org.kuali.student.loader;


import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author nwright
 */
public class CreditCourseLoaderTest
{

 public CreditCourseLoaderTest ()
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
  * Test of load method, of class OrgLoader.
  */
 @Test
 public void testLoadCreditCourses ()
     throws Exception
 {
  System.out.println (new Date () + " load credit courses");

  CreditCourseLoader ccLoader = new CreditCourseLoader ();
  CreditCourseLoaderModel ccModel = CreditCourseLoaderModelFactoryTest.getInstance ().
      getModel ();

  System.out.println (new Date () + " getting credit courses...");
  List<CreditCourse> creditCourses = ccModel.getCreditCourses ();

  System.out.println (new Date () + " loading " + creditCourses.size ()
                      + " credit courses");
  ccLoader.setSource (creditCourses.iterator ());
  ccLoader.setOut (CreditCourseLoaderModelFactoryTest.getInstance ().getOut ());
  int written = ccLoader.write ();
  ccLoader.getOut ().close ();
  System.out.println (written + " recordes written out of " + creditCourses.size () + " credit courses");


 }

}
