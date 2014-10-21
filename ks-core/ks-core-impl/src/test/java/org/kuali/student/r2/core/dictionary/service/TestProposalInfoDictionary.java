package org.kuali.student.r2.core.dictionary.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;
import org.kuali.student.r1.common.validator.ServerDateParser;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;

public class TestProposalInfoDictionary {

	@Test
	public void testLoadProposalInfoDictionary() {
		Set<String> startingClasses = new LinkedHashSet<String>();
		startingClasses.add(ProposalInfo.class.getName ());
		String contextFile = "ks-proposalInfo-dictionary-context";
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
	public void testProposalInfoValidation() throws OperationFailedException {
	    ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
		System.out.println("h2. Validation Test");
		DefaultValidatorImpl val = new DefaultValidatorImpl();
		val.setDateParser(new ServerDateParser());
		val.setSearchDispatcher(new MockSearchDispatcher());
		ProposalInfo info = new ProposalInfo();

        ConfigurableApplicationContext ac = new ClassPathXmlApplicationContext("classpath:ks-proposalInfo-dictionary-context.xml");
		ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean(info.getClass().getName());
        ac.close();

		List<ValidationResultInfo> validationResults = val.validateObject(info,	os, contextInfo);
		System.out.println("h3. With just a blank ProposalInfo");
		// for (ValidationResultInfo vr : validationResults)
		// {
		// System.out.println (vr.getElement () + " " + vr.getMessage ());
		// }
		assertEquals(2, validationResults.size());
	}
}
