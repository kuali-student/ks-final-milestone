package org.kuali.student.lum.course.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
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
  List<Class<?>> startingClasses = new ArrayList ();
  startingClasses.add (CluInfo.class);
  startingClasses.add (CluCluRelationInfo.class);
  String contextFile = "ks-cluInfo-dictionary-context";
  String outFile = "target/" + contextFile + ".txt";
  new DictionaryTesterHelper (outFile, startingClasses, contextFile + ".xml").doTest ();
 }

 @Test
 public void testCluInfoValidation () throws OperationFailedException
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:ks-cluInfo-dictionary-context.xml");
  System.out.println ("h2. Validation Test");
  DefaultValidatorImpl val = new DefaultValidatorImpl ();
  val.setDateParser (new ServerDateParser ());
  val.setSearchDispatcher (new MockSearchDispatcher ());
  CluInfo info = new CluInfo ();
  ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean (
    info.getClass ().getName ());
  List<ValidationResultInfo> validationResults = val.validateObject (info, os);
  System.out.println ("h3. With just a blank CluInfo");
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
