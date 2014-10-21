package org.kuali.student.r1.common.dictionary.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DictionaryDiscrepencyTesterHelper
{

 private String outputFileName;
 private File file;
 private OutputStream outputStream;
 private PrintStream out;
 private Set<String> startingClasses;
 private String dictFileName;
 private boolean processSubstructures = false;
 private boolean printDescrepenciesOnly = false;

 public DictionaryDiscrepencyTesterHelper (String outputFileName,
                                Set<String> startingClasses,
                                String dictFileName,
                                boolean processSubstructures)
 {
  this.outputFileName = outputFileName;
  this.startingClasses = startingClasses;
  this.dictFileName = dictFileName;
  this.processSubstructures = processSubstructures;
  this.printDescrepenciesOnly = printDescrepenciesOnly;
  // get printstream from file
  this.file = new File (this.outputFileName);
  try
  {
   outputStream = new FileOutputStream (file, false);
  }
  catch (FileNotFoundException ex)
  {
   throw new IllegalArgumentException (ex);
  }
  this.out = new PrintStream (outputStream);
 }

 private transient Map<String, ObjectStructureDefinition> objectStructures;

 public List<String> doTest ()
 {
  ConfigurableApplicationContext ac = new ClassPathXmlApplicationContext (
      "classpath:" + dictFileName);
  Map<String, ObjectStructureDefinition> beansOfType =
                                         (Map<String, ObjectStructureDefinition>) ac.
      getBeansOfType (ObjectStructureDefinition.class);
  ac.close();

  objectStructures = new HashMap ();
  for (ObjectStructureDefinition objStr: beansOfType.values ())
  {
   objectStructures.put (objStr.getName (), objStr);
   System.out.println ("Loading object structure: " + objStr.getName ());
  }
  // First validate all the starting classes
//  for (String className: startingClasses)
//  {
//   ObjectStructureDefinition os = null;
//   os = objectStructures.get (className);
//   if (os == null)
//   {
//    throw new RuntimeException ("className is not defined in dictionary: " + className);
//   }
//   DictionaryValidator validator = new DictionaryValidator (os,
//                                                            new HashSet (),
//                                                            false);
//   List<String> errors = validator.validate ();
//   if (errors.size () > 0)
//   {
//    return errors;
//   }
//  }


  Set<String> allStructures = new LinkedHashSet ();
  for (String className: startingClasses)
  {
   allStructures.addAll (getComplexSubStructures (className));
  }
  Set<String> classesToProcess = null;
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
             + "|https://test.kuali.org/svn/student/trunk/ks-lum/ks-lum-impl/src/main/resources/"
             + dictFileName + "]");
  out.println (
      " and is compared to the following java classes (and their sub-classes) for discrepancies:");
  for (String className: startingClasses)
  {
   out.println ("# " + className);
  }
  out.println ("");
  out.println ("----");
  out.println ("{toc}");
  out.println ("----");
  List<String> discrepancies = new ArrayList (1);
  List<String> alldiscrepancies = new ArrayList (1);
  for (String className: classesToProcess)
  {
	// Debuggin System.out.println ("processing class " + className);
   discrepancies = doTestOnClass (className, ac);
   if (discrepancies.size() > 0) {
	   alldiscrepancies.addAll(discrepancies);
	
}
  }
  out.close ();
  return alldiscrepancies;
 }

 private Set<String> getComplexSubStructures (String className)
 {
  return new ComplexSubstructuresHelper ().getComplexStructures (className);
 }

 private List<String> doTestOnClass (String className, ApplicationContext ac)
 {
  ObjectStructureDefinition os = objectStructures.get (className);
  String simpleName = calcSimpleName (className);
//Debuggin System.out.println("processing " + simpleName);
  if (os == null)
  {

   out.println ("h1. " + simpleName);
   out.println ("{anchor:" + simpleName + "}");
   out.println ("h2. Error could not find a corresponding dictionary definition");
//   return;
  }
  DictionaryDiscrepencyTester formatter =
                      new DictionaryDiscrepencyTester (className,
                                               className,
                                               os,
                                               new HashSet (),
                                               1, // header level to start at
                                               this.processSubstructures, this.printDescrepenciesOnly);
//  out.println (formatter.formatForWiki ());
  return formatter.formatForWiki();
 }

 private String calcSimpleName (String name)
 {
  if (name.lastIndexOf (".") != -1)
  {
   name = name.substring (name.lastIndexOf (".") + 1);
  }
  return name;
 }

public boolean isPrintDescrepenciesOnly() {
	return printDescrepenciesOnly;
}

public void setPrintDescrepenciesOnly(boolean printDescrepenciesOnly) {
	this.printDescrepenciesOnly = printDescrepenciesOnly;
}

}
