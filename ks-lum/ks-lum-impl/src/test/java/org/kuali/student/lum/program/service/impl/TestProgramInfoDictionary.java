package org.kuali.student.lum.program.service.impl;

import org.junit.Test;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.common.validator.SampCustomValidator;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.course.service.impl.DictionaryTesterHelper;
import org.kuali.student.lum.course.service.impl.MockSearchDispatcher;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.MinorDisciplineInfo;
import org.kuali.student.lum.program.service.assembler.MajorDisciplineDataGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestProgramInfoDictionary {

    @Test
    public void testLoadProgramInfoDictionary() {
        Set<Class<?>> startingClasses = new LinkedHashSet();
        startingClasses.add(MajorDisciplineInfo.class);
        startingClasses.add(CoreProgramInfo.class);
        startingClasses.add(MinorDisciplineInfo.class);
        startingClasses.add(CredentialProgramInfo.class);
        String contextFile = "ks-programInfo-dictionary-context";
        String outFile = "target/" + contextFile + ".txt";
        DictionaryTesterHelper helper = new DictionaryTesterHelper(outFile,
                startingClasses,
                contextFile
                        + ".xml",
                true);
        helper.doTest();
    }

    @Test
    public void testMajorDisciplineInfoValidation() throws
            OperationFailedException {
        System.out.println("h1. Validation results");
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                "classpath:ks-programInfo-dictionary-context.xml");
        DefaultValidatorImpl val = new DefaultValidatorImpl();
        val.setValidatorFactory(new ValidatorFactory(new SampCustomValidator()));
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
        assertEquals(10, validationResults.size());

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
