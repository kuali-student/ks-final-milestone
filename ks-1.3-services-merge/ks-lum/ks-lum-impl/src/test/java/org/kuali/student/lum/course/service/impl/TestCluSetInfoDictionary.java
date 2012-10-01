package org.kuali.student.lum.course.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class TestCluSetInfoDictionary
{

 @Test
 public void testLoadCluSetInfoDictionary ()
 {
  Set<String> startingClasses = new LinkedHashSet ();
  startingClasses.add (CluSetInfo.class.getName ());
  String contextFile = "ks-cluSetInfo-dictionary-context";
  String outFile = "target/" + contextFile + ".txt";
  DictionaryTesterHelper helper = new DictionaryTesterHelper (outFile,
                                                              startingClasses,
                                                              contextFile
                                                              + ".xml",
                                                              false);
  List<String> errors = helper.doTest ();
  if (errors.size () > 0)
  {
   fail ("failed dictionary validation:\n" + formatAsString (errors));
  }
 }

 private String formatAsString (List<String> errors)
 {
  int i = 0;
  StringBuilder builder = new StringBuilder ();
  for (String error : errors)
  {
   i ++;
   builder.append (i + ". " + error + "\n");
  }
  return builder.toString ();
 }

 @Test
 public void testCluSetInfoValidation () throws OperationFailedException
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:ks-cluSetInfo-dictionary-context.xml");
  System.out.println ("h2. Validation Test");
  DefaultValidatorImpl val = new DefaultValidatorImpl ();
  val.setDateParser (new ServerDateParser ());
  val.setSearchDispatcher (new MockSearchDispatcher ());
  CluSetInfo info = new CluSetInfo ();
  ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean (
    info.getClass ().getName ());
  List<ValidationResultInfo> validationResults = val.validateObject (info, os);
  System.out.println ("h3. With just a blank CluInfo");
  for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
  assertEquals (2, validationResults.size ());

 }
}
