package org.kuali.student.lum.course.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
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

 private String fileName;
 private File file;
 private OutputStream os;
 private PrintStream out;
 private List<Class<?>> startingClasses;
 private String dictFileName;

 public DictionaryTesterHelper (String fileName, List<Class<?>> startingClasses,
                                String dictFileName)
 {
  this.fileName = fileName;
  this.startingClasses = startingClasses;
  this.dictFileName = dictFileName;
  // get printstream
  this.file = new File (this.fileName);
  try
  {
   os = new FileOutputStream (file, false);
  }
  catch (FileNotFoundException ex)
  {
   throw new IllegalArgumentException (ex);
  }
  this.out = new PrintStream (os);
 }

 public void doTest ()
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:" + dictFileName);
//  for (String beanName: ac.getBeanDefinitionNames ())
//  {
//   out.println ("beanName=" + beanName);
//  }
  Set<Class<?>> structures = new LinkedHashSet ();
  for (Class<?> clazz : startingClasses)
  {
   structures.addAll (getComplexSubStructures (clazz));
  }

  out.println ("This page represents a formatted view of this file:");
  out.println ("[" + dictFileName
               + "|https://test.kuali.org/svn/student/trunk/ks-lum/ks-lum-impl/src/main/resources/"
               + dictFileName + "]");
  out.println (
    "as compared to the following DTOs and thier sub-DTO's for discrepancies:");
  for (Class<?> clazz : startingClasses)
  {
   out.println ("# " + clazz.getName ());
  }
  out.println ("");
  out.println ("----");
  out.println ("{toc}");
  out.println ("----");
  for (Class<?> clazz : structures)
  {
   List<String> discrepancies = compare (clazz, ac);
   if (discrepancies.size () > 0)
   {
    out.println ("h3. " + discrepancies.size ()
                 + " discrepancie(s) found in " + clazz.getSimpleName ());
    out.println (formatAsString (discrepancies));
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

  out.println (new DictionaryFormatter (os, "|").formatForWiki ());
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
