package org.kuali.student.krms.naturallanguage.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.impl.repository.PropositionBo;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;
import org.kuali.student.krms.naturallanguage.translators.PropositionTranslator;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/13
 * Time: 12:54 PM
 * To change this template use File | Settings | File Templates.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:nl-test-context.xml"})
public class PropositionTranslatorTest {

    //@Autowired
    //public PropositionTranslator translator;

    //private KRMSDataGenerator dataGenerator = new KRMSDataGenerator();

    //@Test
    public void testGetNaturalLanguageForReqComponent1() throws Exception {
        //req. type: 'kuali.reqComponent.type.test'
        //PropositionBo proposition = dataGenerator.getPropositionForId("TEST-REQCOMP-1");
        //String nl = translator.translate(proposition, "KUALI.RULE", null);
        //Assert.assertEquals("Must completed all courses from English Dept", nl);
    }

    //@Test
    public void testGetNaturalLanguageForReqComponent2() throws Exception {
        //req. type: 'kuali.reqComponent.type.test'
        //PropositionBo proposition = dataGenerator.getPropositionForId("TEST-REQCOMP-2");
        //String nl = translator.translate(proposition, "KUALI.RULE", null);
        //Assert.assertEquals("Must completed all courses from French Dept", nl);
    }
}
