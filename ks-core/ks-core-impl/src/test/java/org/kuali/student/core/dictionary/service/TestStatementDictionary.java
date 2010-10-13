package org.kuali.student.core.dictionary.service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class TestStatementDictionary {

	@Test
	public void testLoadStatementInfoDictionary() {
  System.out.println ("testing statement dictionary");
		Set<Class<?>> startingClasses = new LinkedHashSet<Class<?>>();
		startingClasses.add(StatementInfo.class);
		startingClasses.add(ReqComponentInfo.class);
		startingClasses.add(StatementTreeViewInfo.class);
		String contextFile = "ks-statement-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryTesterHelper helper = new DictionaryTesterHelper(outFile,
				startingClasses, contextFile + ".xml", false);
		helper.doTest();
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
		List<ValidationResultInfo> validationResults = val.validateObject(info,	os);
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
		List<ValidationResultInfo> validationResults = val.validateObject(info,	os);
		System.out.println("h3. With just a blank ReqComponentInfo");
		for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
		assertEquals(0, validationResults.size());

  ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo ();
  fieldInfo.setType ("kuali.reqComponent.field.type.gradeType.id");
  fieldInfo.setValue ("kuali.resultComponent.grade.letter");
  info.setReqCompFields (Arrays.asList (fieldInfo));
  validationResults = val.validateObject(info,	os);
		System.out.println("h3. With just a blank ReqComponentInfo");
		for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
		assertEquals(0, validationResults.size());

  fieldInfo = new ReqCompFieldInfo ();
  fieldInfo.setType ("kuali.reqComponent.field.type.gradeType.id");
  fieldInfo.setValue ("bad with an embedded space in value");
  info.setReqCompFields (Arrays.asList (fieldInfo));
  validationResults = val.validateObject(info,	os);
		System.out.println("h3. With just a blank ReqComponentInfo");
		for (ValidationResultInfo vr : validationResults)
  {
   System.out.println (vr.getElement () + " " + vr.getMessage ());
  }
		assertEquals(1, validationResults.size());

	}
}
