package org.kuali.student.lum.lrc.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.course.service.impl.MockSearchDispatcher;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class TestLrcDictionary
{

 @Test
 public void testLoadCluInfoDictionary ()
 {
  Set<String> startingClasses = new LinkedHashSet ();
  startingClasses.add (ResultComponentInfo.class.getName ());
  String contextFile = "ks-lrc-dictionary-context";
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
 public void testCluInfoValidation () throws OperationFailedException
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:ks-lrc-dictionary-context.xml");
  System.out.println ("h2. Validation Test");
  DefaultValidatorImpl val = new DefaultValidatorImpl ();
  val.setDateParser (new ServerDateParser ());
  val.setSearchDispatcher (new MockSearchDispatcher ());
  ResultComponentInfo info = new ResultComponentInfo ();
  ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean (
    info.getClass ().getName ());
  List<ValidationResultInfo> validationResults = val.validateObject (info, os);
  System.out.println ("h3. With just a blank CluInfo");
//  for (ValidationResultInfo vr : validationResults)
//  {
//   System.out.println (vr.getElement () + " " + vr.getMessage ());
//  }
  assertEquals (0, validationResults.size ());

 }
}
