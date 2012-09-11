package org.kuali.student.r2.core.dictionary.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.r1.common.validator.ServerDateParser;
import org.kuali.student.r2.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r2.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r2.core.statement.dto.StatementInfo;
import org.kuali.student.r2.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestStatementDictionary {

//	 org.kuali.student.r2.core.statement.dto.StatementInfo - Used Instead
//	 org.kuali.student.r2.core.statement.dto.ReqComponentInfo - Used Instead
//	 org.kuali.student.r2.core.statement.dto.StatementTreeViewInfo - Used Instead
	
	@Test
	public void testLoadStatementInfoDictionary() {
  System.out.println ("testing statement dictionary");
		Set<String> startingClasses = new LinkedHashSet();
		startingClasses.add(StatementInfo.class.getName ());
		startingClasses.add(ReqComponentInfo.class.getName ());
		startingClasses.add(StatementTreeViewInfo.class.getName ());
		String contextFile = "ks-statement-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryTesterHelper helper = new DictionaryTesterHelper(outFile,
				startingClasses, contextFile + ".xml", false);
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
	public void testStatementInfoValidation() throws OperationFailedException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:ks-statement-dictionary-context.xml");
		System.out.println("h2. Validation Test");
		DefaultValidatorImpl val = new DefaultValidatorImpl();
		val.setDateParser(new ServerDateParser());
		val.setSearchDispatcher(new MockSearchDispatcher());
		StatementInfo info = new StatementInfo();
		ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean(info.getClass().getName());
		List<ValidationResultInfo> validationResults = val.validateObject(info,	os, null);
		System.out.println("h3. With just a blank StatementInfo");
		for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
		assertEquals(1, validationResults.size());
	}

 @Test
	public void testRequirementComponentInfoValidation() throws OperationFailedException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:ks-statement-dictionary-context.xml");
		System.out.println("h2. Validation Test");
		DefaultValidatorImpl val = new DefaultValidatorImpl();
		val.setDateParser(new ServerDateParser());
		val.setSearchDispatcher(new MockSearchDispatcher());
		ReqComponentInfo info = new ReqComponentInfo();
		ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean(info.getClass().getName());
		List<ValidationResultInfo> validationResults = val.validateObject(info,	os, null);
		System.out.println("h3. With just a blank ReqComponentInfo");
		for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
		assertEquals(0, validationResults.size());

  ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo ();
  fieldInfo.setTypeKey ("kuali.reqComponent.field.type.gradeType.id");
  fieldInfo.setValue ("kuali.resultComponent.grade.letter");
  info.setReqCompFields (Arrays.asList (fieldInfo));
  validationResults = val.validateObject(info,	os, null);
		System.out.println("h3. With just a blank ReqComponentInfo");
		for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
		assertEquals(0, validationResults.size());

//  commented out this test because the gradeType.id was changed to be name that allows spaces
//             <!-- Note: the grade.id is really a key so it should coform to the baseKualiType 
//		BUT in R1.1 we are displaying the value and the atpService wants to see percent
//		sign in it so changed this to baseKualiName -->
//  fieldInfo = new ReqCompFieldInfo ();
//  fieldInfo.setTypeKey ("kuali.reqComponent.field.type.gradeType.id");
//  fieldInfo.setValue ("bad with an embedded space in value");
//  info.setReqCompFields (Arrays.asList (fieldInfo));
//  validationResults = val.validateObject(info, os, null);
//		System.out.println("h3. With just a blank ReqComponentInfo");
//		for (ValidationResultInfo vr : validationResults)
//  {
//   System.out.println (vr.getElement () + " " + vr.getMessage ());
//  }
//		assertEquals(1, validationResults.size());

	}
}
