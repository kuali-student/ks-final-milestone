package org.kuali.student.lum.program.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.cxf.service.model.MessageInfo;
import org.junit.Test;
import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.messages.dto.LocaleKeyList;
import org.kuali.student.common.messages.dto.Message;
import org.kuali.student.common.messages.dto.MessageGroupKeyList;
import org.kuali.student.common.messages.dto.MessageList;
import org.kuali.student.common.messages.service.MessageService;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.core.messages.service.impl.MessageServiceMock;
import org.kuali.student.lum.course.service.impl.MockSearchDispatcher;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.MinorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.lum.program.service.assembler.MajorDisciplineDataGenerator;
import org.kuali.student.lum.program.service.validation.ProgramManagingBodiesValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestProgramInfoDictionary {

    @Test
    public void testLoadProgramInfoDictionary() {
        Set<String> startingClasses = new LinkedHashSet();
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
    public void testMajorDisciplineInfoValidation() throws
            OperationFailedException {
        System.out.println("h1. Validation results");
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                "classpath:ks-programInfo-dictionary-context.xml");
        DefaultValidatorImpl val = new DefaultValidatorImpl();
        val.setValidatorFactory(new ValidatorFactory());
        ProgramManagingBodiesValidator programManagingBodiesValidator = new ProgramManagingBodiesValidator();
        MessageServiceMock messageServiceMock = new MessageServiceMock();
        Message message = new Message();
        message.setGroupName("validation");
        message.setLocale("en");
        message.setId("validation.programManagingBodiesMatch");
        message.setValue("validation.programManagingBodiesMatch");
        messageServiceMock.addMessage(message);
        programManagingBodiesValidator.setMessageService(messageServiceMock);
        programManagingBodiesValidator.setSearchDispatcher(new MockSearchDispatcher());
        List<Validator> validatorList = new ArrayList<Validator>();
        validatorList.add(programManagingBodiesValidator);
        val.getValidatorFactory().setValidatorList(validatorList);
        val.setDateParser(new ServerDateParser());
        val.setSearchDispatcher(new MockSearchDispatcher());
        MajorDisciplineInfo info = new MajorDisciplineInfo();
        ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean(
                info.getClass().getName());
        List<ValidationResultInfo> validationResults = val.validateObject(info, os);
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

        try {
            info =
                    new MajorDisciplineDataGenerator().getMajorDisciplineInfoTestData();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        validationResults = val.validateObject(info, os);
        System.out.println("h2. with generated data");
        for (ValidationResultInfo vr : validationResults) {
            System.out.println(vr.getElement() + " " + vr.getMessage());
        }
        //assertEquals(4, validationResults.size());
    }
}
