package org.kuali.student.lum.statement.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.statement.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:statement-test-context.xml"})
public class SampleStatementServiceTests {

	@Autowired
    public StatementService statementService;

	@Test
    public void testGetNaturalLanguageForReqComponent_TEST() throws Exception {
        //req. type: 'kuali.reqComponent.type.test'
        String nl = statementService.getNaturalLanguageForReqComponent("TEST-REQCOMP-1", "KUALI.RULE", null);
        Assert.assertEquals("Must completed all courses from English Dept", nl);
    }	
}
