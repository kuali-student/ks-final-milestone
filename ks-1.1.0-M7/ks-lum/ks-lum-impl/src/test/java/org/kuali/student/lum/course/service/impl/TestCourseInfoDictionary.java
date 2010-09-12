package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.common.validator.SampCustomValidator;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCourseInfoDictionary
{

 @Test
 public void testLoadCourseInfoDictionary ()
 {
  List<Class<?>> startingClasses = new ArrayList ();
  startingClasses.add (CourseInfo.class);
  startingClasses.add (StatementTreeViewInfo.class);
  String contextFile = "ks-courseInfo-dictionary-context";
  String outFile = "target/" + contextFile + ".txt";
  new DictionaryTesterHelper (outFile, startingClasses, contextFile + ".xml").doTest ();
 }

 @Test
 public void testCourseInfoValidation () throws OperationFailedException
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:ks-courseInfo-dictionary-context.xml");
  System.out.println ("h1. Test Validation");
  DefaultValidatorImpl val = new DefaultValidatorImpl ();
  val.setValidatorFactory (new ValidatorFactory (new SampCustomValidator ()));
  val.setDateParser (new ServerDateParser ());
  val.setSearchDispatcher (new MockSearchDispatcher ());
  CourseInfo info = new CourseInfo ();
  ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean (
    info.getClass ().getName ());
  List<ValidationResultInfo> validationResults = val.validateObject (info, os);
  System.out.println ("h3. With just a blank Course");
  for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
  assertEquals (4, validationResults.size ());

  try
  {
   info =
   new CourseDataGenerator ().getCourseTestData ();
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  validationResults = val.validateObject (info, os);
  System.out.println ("h3. With generated data");
  for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
  assertEquals (0, validationResults.size ());

  System.out.println ("testCourseDescrRequiredBasedOnState");
  info.setState ("DRAFT");
  info.setDescr (null);
  validationResults = val.validateObject (info, os);
  assertEquals (0, validationResults.size ());

  info.setState ("ACTIVE");
  info.setDescr (null);
  validationResults = val.validateObject (info, os);
  for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
  assertEquals (1, validationResults.size ());

  System.out.println ("test validation on dynamic attributes");
  info.getAttributes().put("finalExamStatus", "123");
 validationResults = val.validateObject (info, os);
  for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
  assertEquals (2, validationResults.size ());

 }
}
