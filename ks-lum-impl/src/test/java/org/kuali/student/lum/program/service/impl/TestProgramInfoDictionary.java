package org.kuali.student.lum.program.service.impl;

import org.junit.Test;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.core.messages.service.impl.MessageServiceMock;
import org.kuali.student.lum.course.service.impl.MockSearchDispatcher;
import org.kuali.student.r2.lum.program.service.assembler.MajorDisciplineDataGenerator;
import org.kuali.student.r2.lum.program.service.validation.ProgramManagingBodiesValidator;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.r1.common.validator.ServerDateParser;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r2.lum.program.dto.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;

public class TestProgramInfoDictionary {

	 ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
	
    @Test
    public void testLoadProgramInfoDictionary() {
        Set<String> startingClasses = new LinkedHashSet<String>();
        startingClasses.add(MajorDisciplineInfo.class.getName ());
        startingClasses.add(CoreProgramInfo.class.getName ());
        startingClasses.add(MinorDisciplineInfo.class.getName ());
        startingClasses.add(CredentialProgramInfo.class.getName ());
        startingClasses.add(ProgramRequirementInfo.class.getName ());
        String contextFile = "ks-programInfo-dictionary-context";
        String outFile = "target/" + contextFile + ".txt";
        DictionaryTesterHelper helper = new DictionaryTesterHelper(outFile,
                startingClasses,
                contextFile
                        + ".xml",
                true);
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
    public void testMajorDisciplineInfoValidation() throws Exception {
        System.out.println("h1. Validation results");
        DefaultValidatorImpl val = new DefaultValidatorImpl();
        val.setValidatorFactory(new ValidatorFactory());
        ProgramManagingBodiesValidator programManagingBodiesValidator = new ProgramManagingBodiesValidator();
        MessageServiceMock messageServiceMock = new MessageServiceMock();
        MessageInfo message = new MessageInfo();
        message.setGroupName("validation");
        LocaleInfo l = new LocaleInfo();
        l.setLocaleLanguage("en");
        l.setLocaleRegion("US");
        l.setLocaleScript("Latn");
        l.setLocaleVariant("POSIX");
        
        message.setLocale(l);
        message.setMessageKey("validation.programManagingBodiesMatch");
        //message.setId("validation.programManagingBodiesMatch");
        message.setValue("validation.programManagingBodiesMatch");
        //message.setValue("validation.programManagingBodiesMatch");
        
        messageServiceMock.createMessage(l,message.getGroupName(), message.getMessageKey(), message, new ContextInfo());
        programManagingBodiesValidator.setMessageService(messageServiceMock);
        programManagingBodiesValidator.setSearchDispatcher(new MockSearchDispatcher());
        List<Validator> validatorList = new ArrayList<Validator>();
        validatorList.add(programManagingBodiesValidator);
        val.getValidatorFactory().setValidatorList(validatorList);
        val.setDateParser(new ServerDateParser());
        val.setSearchDispatcher(new MockSearchDispatcher());
        MajorDisciplineInfo info = new MajorDisciplineInfo();

        ConfigurableApplicationContext ac = new ClassPathXmlApplicationContext(
                "classpath:ks-programInfo-dictionary-context.xml");
        ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean(
                info.getClass().getName());
        ac.close();

        List<ValidationResultInfo> validationResults = val.validateObject(info, os, contextInfo);
        System.out.println("h2. with just a blank record");
        for (ValidationResultInfo vr : validationResults) {
            System.out.println(vr.getElement() + " " + vr.getMessage());
        }
//      /credentialProgramId validation.required
//      /universityClassification validation.required
//      /longTitle validation.required
//      /type validation.required
//      /state validation.required
        assertEquals(5, validationResults.size());

        info = new MajorDisciplineDataGenerator().getMajorDisciplineInfoTestData();
        validationResults = val.validateObject(info, os, contextInfo);
        System.out.println("h2. with generated data");
        for (ValidationResultInfo vr : validationResults) {
            System.out.println(vr.getElement() + " " + vr.getMessage());
        }
        assertEquals(18, validationResults.size());
    }
}
