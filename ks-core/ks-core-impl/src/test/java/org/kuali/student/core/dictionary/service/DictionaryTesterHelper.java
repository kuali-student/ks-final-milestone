package org.kuali.student.core.dictionary.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DictionaryTesterHelper
{

 private String outputFileName;
 private File file;
 private OutputStream os;
 private PrintStream out;
 private Set<Class<?>> startingClasses;
 private String dictFileName;
 private boolean processSubstructures = false;

 public DictionaryTesterHelper (String outputFileName,
                                Set<Class<?>> startingClasses,
                                String dictFileName,
                                boolean processSubstructures)
 {
  this.outputFileName = outputFileName;
  this.startingClasses = startingClasses;
  this.dictFileName = dictFileName;
  this.processSubstructures = processSubstructures;
  // get printstream from file
  this.file = new File (this.outputFileName);
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
  // First validate all the starting classes
  for (Class<?> clazz : startingClasses)
  {
   ObjectStructureDefinition os = null;
   try
   {
    os = (ObjectStructureDefinition) ac.getBean (clazz.getName ());
    DictionaryValidator validator = new DictionaryValidator (os,
                                                             new HashSet (),
                                                             false);
    List<String> errors = validator.validate ();
    if (errors.size () > 0)
    {
     throw new RuntimeException (clazz.getName () + " failed dictionary validation:\n"
           + this.formatAsString (errors));
    }
   }
   catch (NoSuchBeanDefinitionException ex)
   {
    // TODO: fail if the starting class isn't even defined yet.
   }
  }


  Set<Class<?>> allStructures = new LinkedHashSet ();
  for (Class<?> clazz : startingClasses)
  {
   allStructures.addAll (getComplexSubStructures (clazz));
  }
  Set<Class<?>> classesToProcess = null;
  if (this.processSubstructures)
  {
   classesToProcess = startingClasses;
//   System.out.println ("Processing just the starting classes but then processing their substructures in-line");
  }
  else
  {
   classesToProcess = allStructures;
//   System.out.println ("Processing all substructures as separate entitiies");
  }

  out.println ("(!) This page was automatically generated on " + new Date ());
  out.println ("DO NOT UPDATE MANUALLY!");
  out.println ("");
  out.print ("This page represents a formatted view of [" + dictFileName
             + "|https://test.kuali.org/svn/student/trunk/ks-core/ks-core-impl/src/main/resources/"
             + dictFileName + "]");
  out.println (
    " and is compared to following DTOs and thier sub-DTO's for discrepancies:");
  for (Class<?> clazz : startingClasses)
  {
   out.println ("# " + clazz.getName ());
  }
  out.println ("");
  out.println ("----");
  out.println ("{toc}");
  out.println ("----");
  for (Class<?> clazz : classesToProcess)
  {
//   System.out.println ("processing class " + clazz.getSimpleName ());
   doTestOnClass (clazz, ac);
  }
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

 private Set<Class<?>> getComplexSubStructures (Class<?> clazz)
 {
  return new ComplexSubstructuresHelper ().getComplexStructures (clazz);
 }

 private void doTestOnClass (Class<?> clazz, ApplicationContext ac)
 {
  ObjectStructureDefinition os = null;
  try
  {
   os = (ObjectStructureDefinition) ac.getBean (clazz.getName ());
  }
  catch (NoSuchBeanDefinitionException ex)
  {
   out.println ("h1. " + clazz.getSimpleName ());
   out.println ("{anchor:" + clazz.getSimpleName () + "}");
   out.println ("h2. Error Getting Bean from dictionary");
   out.println (ex.getMessage ());
   return;
  }
  String name = calcSimpleName (os.getName ());
  DictionaryFormatter formatter =
                      new DictionaryFormatter (name,
                                               clazz,
                                               os,
                                               new HashSet (),
                                               1, // header level to start at
                                               this.processSubstructures);
  out.println (formatter.formatForWiki ());
 }

 private String calcSimpleName (String name)
 {
  if (name.lastIndexOf (".") != -1)
  {
   name = name.substring (name.lastIndexOf (".") + 1);
  }
  return name;
 }
}
