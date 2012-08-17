package org.kuali.student.lum.course.service.impl;

import org.junit.Test;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.r1.common.validator.ServerDateParser;
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestCluInfoDictionary {

    ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

    @Test
    public void testLoadCluInfoDictionary() {
        Set<String> startingClasses = new LinkedHashSet();
        startingClasses.add(CluInfo.class.getName());
        // startingClasses.add (CluCluRelationInfo.class.getName ());
        String contextFile = "ks-cluInfo-dictionary-context";
        String outFile = "target/" + contextFile + ".txt";
        DictionaryTesterHelper helper = new DictionaryTesterHelper(outFile, startingClasses, contextFile + ".xml", false);
        List<String> errors = helper.doTest();
        if (errors.size() > 0) {
            fail("failed dictionary validation:\n" + formatAsString(errors));
        }
    }

    private String formatAsString(List<String> errors) {
        int i = 0;
        StringBuilder builder = new StringBuilder();
        for (String error : errors) {
            i++;
            builder.append(i + ". " + error + "\n");
        }
        return builder.toString();
    }

    @Test
    public void testCluInfoValidation() throws OperationFailedException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:ks-cluInfo-dictionary-context.xml");
        System.out.println("h2. Validation Test");
        DefaultValidatorImpl val = new DefaultValidatorImpl();
        val.setDateParser(new ServerDateParser());
        val.setSearchDispatcher(new MockSearchDispatcher());
        CluInfo info = new CluInfo();
        ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean(info.getClass().getName());

        List<ValidationResultInfo> validationResults = val.validateObject(info, os, contextInfo);
        System.out.println("h3. With just a blank CluInfo");
         for (ValidationResultInfo vr : validationResults)
         {
         System.out.println (vr.getElement () + " " + vr.getMessage ());
         }
        assertEquals(3, validationResults.size());

        // test that we validate substructures
        info.setOfficialIdentifier(new CluIdentifierInfo());
        validationResults = val.validateObject(info, os, contextInfo);
        for (ValidationResultInfo vr : validationResults)
        {
        System.out.println (vr.getElement () + " " + vr.getMessage ());
        }
        // should now require type and state of the identifier structure too
        assertEquals(5, validationResults.size());

        // test that we can put completely blank timeAmountInfo structures
        info.setStdDuration(new TimeAmountInfo());
        validationResults = val.validateObject(info, os, contextInfo);
        for (ValidationResultInfo vr : validationResults)
        {
        System.out.println (vr.getElement () + " " + vr.getMessage ());
        }
        assertEquals(5, validationResults.size());

        // test the requires constraint
        // that requires a durationType if we have a timeQuantity
        info.getStdDuration().setTimeQuantity(1);
        validationResults = val.validateObject(info, os, contextInfo);
        for (ValidationResultInfo vr : validationResults)
        {
        System.out.println (vr.getElement () + " " + vr.getMessage ());
        }
        assertEquals(7, validationResults.size());

        // test that we can put completely blank timeAmountInfo structures
        info.setIntensity(new AmountInfo());
        validationResults = val.validateObject(info, os, contextInfo);
        System.out.println ("validation results adding a blank CluIdentifierInfo");
        for (ValidationResultInfo vr : validationResults)
        {
        System.out.println (vr.getElement () + " " + vr.getMessage ());
        }
        assertEquals(7, validationResults.size());

        // test the requires constraint
        // that requires a unity if we have a unitQuantity
        info.getIntensity().setUnitQuantity("1");
        validationResults = val.validateObject(info, os, contextInfo);
        System.out.println ("validation results adding a blank CluIdentifierInfo");
        for (ValidationResultInfo vr : validationResults)
        {
        System.out.println (vr.getElement () + " " + vr.getMessage ());
        }
        assertEquals(9, validationResults.size());

    }
}
