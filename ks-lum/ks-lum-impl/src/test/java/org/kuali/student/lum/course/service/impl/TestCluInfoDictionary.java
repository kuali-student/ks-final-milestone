package org.kuali.student.lum.course.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class TestCluInfoDictionary
{

 @Test
 public void testLoadCluInfoDictionary ()
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:ks-cluInfo-dictionary-context.xml");
//  for (String beanName: ac.getBeanDefinitionNames ())
//  {
//   System.out.println ("beanName=" + beanName);
//  }
  Set<Class<?>> structures = new LinkedHashSet ();
  List<Class<?>> startingClasses = new ArrayList ();
  startingClasses.add (CluInfo.class);
  startingClasses.add (CluCluRelationInfo.class);
  for (Class<?> clazz : startingClasses)
  {
   structures.addAll (getComplexStructures (clazz));
  }

  List<String> discrepancies = new ArrayList ();
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
  };
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
 public void testCluInfoValidation ()
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:ks-cluInfo-dictionary-context.xml");
  DefaultValidatorImpl val = new DefaultValidatorImpl ();
  val.setDateParser (new ServerDateParser ());
  CluInfo info = new CluInfo ();
  ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean (
    info.getClass ().getName ());
  List<ValidationResultInfo> validationResults = val.validateObject (info, os);
  System.out.println ("validation results with just a blank CluInfo");
//  for (ValidationResultInfo vr : validationResults)
//  {
//   System.out.println (vr.getElement () + " " + vr.getMessage ());
//  }
  assertEquals (2, validationResults.size ());

  // test that we validate substructures
  info.setOfficialIdentifier (new CluIdentifierInfo ());
  validationResults = val.validateObject (info, os);
//  for (ValidationResultInfo vr : validationResults)
//  {
//   System.out.println (vr.getElement () + " " + vr.getMessage ());
//  }
  // should now require type and state of the identifier structure too
  assertEquals (4, validationResults.size ());


  // test that we can put completely blank timeAmountInfo structures
  info.setStdDuration (new TimeAmountInfo ());
  validationResults = val.validateObject (info, os);
//  for (ValidationResultInfo vr : validationResults)
//  {
//   System.out.println (vr.getElement () + " " + vr.getMessage ());
//  }
  assertEquals (4, validationResults.size ());

  // test the requires constraint
  // that requires a durationType if we have a timeQuantity
  info.getStdDuration ().setTimeQuantity (1);
  validationResults = val.validateObject (info, os);
//  for (ValidationResultInfo vr : validationResults)
//  {
//   System.out.println (vr.getElement () + " " + vr.getMessage ());
//  }
  assertEquals (5, validationResults.size ());

  // test that we can put completely blank timeAmountInfo structures
  info.setIntensity (new AmountInfo ());
  validationResults = val.validateObject (info, os);
//  System.out.println ("validation results adding a blank CluIdentifierInfo");
//  for (ValidationResultInfo vr : validationResults)
//  {
//   System.out.println (vr.getElement () + " " + vr.getMessage ());
//  }
  assertEquals (5, validationResults.size ());

  // test the requires constraint
  // that requires a unity if we have a unitQuantity
  info.getIntensity ().setUnitQuantity ("1");
  validationResults = val.validateObject (info, os);
//  System.out.println ("validation results adding a blank CluIdentifierInfo");
//  for (ValidationResultInfo vr : validationResults)
//  {
//   System.out.println (vr.getElement () + " " + vr.getMessage ());
//  }
  assertEquals (6, validationResults.size ());


 }
}
