package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DictionaryTesterHelper
{

 public void doTest (String dictFileName, List<Class<?>> startingClasses)
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:" + dictFileName);
//  for (String beanName: ac.getBeanDefinitionNames ())
//  {
//   System.out.println ("beanName=" + beanName);
//  }
  Set<Class<?>> structures = new LinkedHashSet ();
  for (Class<?> clazz : startingClasses)
  {
   structures.addAll (getComplexSubStructures (clazz));
  }

  System.out.println ("{toc}");
  System.out.println ("----");
  for (Class<?> clazz : structures)
  {
   List<String> discrepancies = compare (clazz, ac);
   if (discrepancies.size () > 0)
   {
    System.out.println ("h3. " + discrepancies.size ()
                        + " discrepancie(s) found in " + clazz.getSimpleName ());
    System.out.println (formatAsString (discrepancies));
   }
  }
 }

 private Set<Class<?>> getComplexSubStructures (Class<?> clazz)
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
  List<String> errors = new DictionaryValidator (os).validate ();
  if (errors.size () > 0)
  {
   fail (clazz.getName () + " failed dictionary validation:\n"
         + this.formatAsString (errors));
  }

  System.out.println (new DictionaryFormatter (os, "|").formatForWiki ());
  return new Dictionary2BeanComparer (clazz, os).compare ();
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
}
