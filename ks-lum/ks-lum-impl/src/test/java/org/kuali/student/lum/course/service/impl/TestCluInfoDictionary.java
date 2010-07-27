package org.kuali.student.lum.course.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
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
  List<String> discrepancies = new ArrayList ();
  for (Class<?> clazz : getComplexStructures (CluInfo.class))
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
  ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean (
    clazz.getName ());
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
  for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
  // TODO: change back to 4 when we undo Will Gomes making effective date not required
  assertEquals (3, validationResults.size ());

  //
  info.setOfficialIdentifier (new CluIdentifierInfo ());
  validationResults = val.validateObject (info, os);
  System.out.println ("validation results adding a blank CluIdentifierInfo");
  for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
  // TODO: change back to 5 when we undo Will Gomes making effective date not required
  assertEquals (4, validationResults.size ());
 }
}
