package org.kuali.student.lum.statement.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:statement-test-context.xml"})
public class SampleStatementServiceTests {

	@Autowired
    public StatementService statementService;
	
	@Test
    public void testGetNaturalLanguageForReqComponent1() throws Exception {
        //req. type: 'kuali.reqComponent.type.test'
        String nl = statementService.getNaturalLanguageForReqComponent("TEST-REQCOMP-1", "KUALI.RULE", null);
        Assert.assertEquals("Must completed all courses from English Dept", nl);
    }	

	@Test
    public void testGetNaturalLanguageForReqComponent2() throws Exception {
        //req. type: 'kuali.reqComponent.type.test'
        String nl = statementService.getNaturalLanguageForReqComponent("TEST-REQCOMP-2", "KUALI.RULE", null);
        Assert.assertEquals("Must completed all courses from French Dept", nl);
    }	

	@Test
    public void testGetNaturalLanguageForStatement1() throws Exception {
        //req. type: 'kuali.reqComponent.type.test'
        String nl = statementService.getNaturalLanguageForStatement("TEST-STMT-1", "KUALI.RULE", "en");
        Assert.assertEquals("Must completed all courses from English Dept", nl);
    }	

	@Test
    public void testGetNaturalLanguageForStatement2() throws Exception {
        //req. type: 'kuali.reqComponent.type.test'
        String nl = statementService.getNaturalLanguageForStatement("TEST-STMT-2", "KUALI.RULE", "en");
        Assert.assertEquals("Must completed all courses from English Dept and Must completed all courses from French Dept", nl);
    }	
}
