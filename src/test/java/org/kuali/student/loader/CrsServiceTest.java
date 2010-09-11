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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kuali.student.wsdl.course.CourseInfo;
import org.kuali.student.wsdl.course.DoesNotExistException;
import org.kuali.student.wsdl.course.ValidationResultInfo;

/**
 *
 * @author nwright
 */
public class CrsServiceTest
{

 public CrsServiceTest ()
 {
 }

 @BeforeClass
 public static void setUpClass () throws Exception
 {
 }

 @AfterClass
 public static void tearDownClass () throws Exception
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
  * Test of getCourse method, of class CrsService.
  */
 @Test
 public void testGetCourse ()
 {
  System.out.println ("getCourse");
  String id = "a079e6e4-9d4c-4296-8db3-5fa353fcfec9";
  CrsService instance = new CrsService ();
  CourseInfo result = null;
  try
  {
   result = instance.getCourse (id);
  }
  catch (DoesNotExistException ex)
  {
   fail ("should exist if impex was run properly");
  }
  assertNotNull (result);
 }

 /**
  * Test of createCourse method, of class CrsService.
  */
 @Test
 public void testCreateCourse ()
 {
  System.out.println ("createCourse");
  CourseInfo info = new CourseInfo ();
  info.setSubjectArea ("ENGL");
//  info.setCode ("ENGL111");
  info.setState ("draft");
  info.setType ("kuali.lu.type.CreditCourse");
  info.setCourseNumberSuffix ("111");
  info.setCourseTitle ("Intro to English");
  info.setEffectiveDate (new DateHelper ().asXmlDate ("2010-01-01"));
  CrsService instance = new CrsService ();
  CourseInfo result = null;
  try
  {
   result = instance.createCourse (info);
   assertNotNull (result);
   assertNotSame (info, result);
   assertNotNull (result.getId ());
   assertEquals ("ENGL111", result.getCode ());
   assertEquals (info.getSubjectArea (), result.getSubjectArea ());
   assertEquals (info.getCourseNumberSuffix (), result.getCourseNumberSuffix ());
   assertEquals (info.getCourseTitle (), result.getCourseTitle ());
  }
  catch (org.kuali.student.wsdl.course.DataValidationErrorException ex)
  {
   for (ValidationResultInfo vri : ex.getFaultInfo ().getValidationResults ())
   {
    System.out.println (vri.getElement () + " " + vri.getMessage ());
   }
   fail ("got validation exception");
  }

  // get
  info = result;
  try
  {
   result = instance.getCourse (info.getId ());
  }
  catch (DoesNotExistException ex)
  {
   fail ("should exist");
  }
  assertNotNull (result);
  assertNotSame (info, result);
  assertNotNull (result.getId ());
  assertEquals (info.getId (), result.getId ());
  assertEquals (info.getCode (), result.getCode ());
  assertEquals (info.getSubjectArea (), result.getSubjectArea ());
  assertEquals (info.getCourseNumberSuffix (), result.getCourseNumberSuffix ());
  assertEquals (info.getCourseTitle (), result.getCourseTitle ());

  // update
  info = result;
  try
  {
   info.setSubjectArea ("MATH");
   info.setCode (null);
   info.setState ("Active");
//  info.setType ("kuali.lu.type.CreditCourse");
   info.setCourseNumberSuffix ("222");
   info.setCourseTitle ("Intro to math");
   info.setEffectiveDate (new DateHelper ().asXmlDate ("2010-02-02"));
   info.setDescr (new RichTextInfoHelper ().getFromPlain ("this is the description of the course which is required when active"));
   result = instance.updateCourse (info);
   assertNotNull (result);
   assertNotSame (info, result);
   assertNotNull (result.getId ());
   assertEquals (info.getId (), result.getId ());
   assertNotNull (result.getCode ());
   assertEquals ("MATH222", result.getCode ());
   assertEquals (info.getSubjectArea (), result.getSubjectArea ());
   assertEquals (info.getCourseNumberSuffix (), result.getCourseNumberSuffix ());
   assertEquals (info.getCourseTitle (), result.getCourseTitle ());
  }
  catch (org.kuali.student.wsdl.course.DataValidationErrorException ex)
  {
   for (ValidationResultInfo vri : ex.getFaultInfo ().getValidationResults ())
   {
    System.out.println (vri.getElement () + " " + vri.getMessage ());
   }
   fail ("got validation exception");
  }

  // get
  info = result;
  try
  {
   result = instance.getCourse (info.getId ());
  }
  catch (DoesNotExistException ex)
  {
   fail ("should exist");
  }
  assertNotNull (result);
  assertNotSame (info, result);
  assertNotNull (result.getId ());
  assertEquals (info.getId (), result.getId ());
  assertEquals (info.getCode (), result.getCode ());
  assertEquals (info.getSubjectArea (), result.getSubjectArea ());
  assertEquals (info.getCourseNumberSuffix (), result.getCourseNumberSuffix ());
  assertEquals (info.getCourseTitle (), result.getCourseTitle ());

  // delete
  info = result;
  try
  {
   boolean success = instance.deleteCourse (info.getId ());
   assertTrue (success);
  }
  catch (org.kuali.student.wsdl.course.DataValidationErrorException ex)
  {
   for (ValidationResultInfo vri : ex.getFaultInfo ().getValidationResults ())
   {
    System.out.println (vri.getElement () + " " + vri.getMessage ());
   }
   fail ("got validation exception");
  }
  try
  {
   result = instance.getCourse (info.getId ());
   fail ("should have thrown does not exist exception");
  }
  catch (DoesNotExistException ex)
  {
   // as expected
  }

 }
}
