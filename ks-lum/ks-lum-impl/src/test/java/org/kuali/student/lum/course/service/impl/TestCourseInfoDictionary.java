package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.common.validator.SampCustomValidator;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCourseInfoDictionary
{

 @Test
 public void testLoadCourseInfoDictionary ()
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:ks-courseInfo-dictionary-context.xml");
//  for (String beanName: ac.getBeanDefinitionNames ())
//  {
//   System.out.println ("beanName=" + beanName);
//  }
  Set<Class<?>> structures = new LinkedHashSet<Class<?>> ();
  List<Class<?>> startingClasses = new ArrayList<Class<?>> ();
  startingClasses.add (CourseInfo.class);
  startingClasses.add (StatementTreeViewInfo.class);
  for (Class<?> clazz : startingClasses)
  {
   structures.addAll (getComplexStructures (clazz));
  }

  List<String> discrepancies = new ArrayList<String> ();
  for (Class<?> clazz : structures)
  {
   discrepancies.addAll (compare (clazz, ac));
  }
  if (discrepancies.size () > 0)
  {
   System.out.println (formatAsString (discrepancies));
//   fail (formatAsString (discrepancies));
   return;
  }

 }

 private Set<Class<?>> getComplexStructures (Class<?> clazz)
 {
  return new ComplexSubstructuresHelper ().getComplexStructures (clazz);
 }

 private List<String> compare (Class<?> clazz, ApplicationContext ac)
 {
  ObjectStructureDefinition os = null;
  try
  {
   os = (ObjectStructureDefinition) ac.getBean (clazz.getName ());
  }
  catch (NoSuchBeanDefinitionException ex)
  {
   return Arrays.asList (ex.getMessage ());
  }
  os.getAttributes ();
  System.out.println (new DictionaryFormatter (os, "|").format ());
  return compare (clazz, os);
 }

 private List<String> compare (Class<?> clazz, ObjectStructureDefinition os)
 {
  Dictionary2BeanComparer comparer = new Dictionary2BeanComparer (clazz, os);
  List<String> discrepancies = comparer.compare ();
  if (discrepancies.size () > 0)
  {
   discrepancies.add (0, discrepancies.size () + " discrepancies in "
                         + clazz.getSimpleName ());
  }
  return discrepancies;
 }

 private String formatAsString (List<String> discrepancies)
 {
  int i = 0;
  StringBuilder builder = new StringBuilder ();
  for (String discrep : discrepancies)
  {
   i ++;
   builder.append (i + ". " + discrep + "\n");
  }
  return builder.toString ();
 }

 @Test
 public void testCourseInfoValidation ()
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:ks-courseInfo-dictionary-context.xml");
  DefaultValidatorImpl val = new DefaultValidatorImpl ();
  val.setValidatorFactory (new ValidatorFactory (new SampCustomValidator ()));
  val.setDateParser (new ServerDateParser ());
  CourseInfo info = new CourseInfo ();
  ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean (
    info.getClass ().getName ());
  List<ValidationResultInfo> validationResults = val.validateObject (info, os);
  System.out.println ("validation results with just a blank Course");
  for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
  assertEquals (3, validationResults.size ());


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
  System.out.println ("validation results with generated data");
  for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
  assertEquals (0, validationResults.size ());
 }
}
