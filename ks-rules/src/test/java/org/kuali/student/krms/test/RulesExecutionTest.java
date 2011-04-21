package org.kuali.student.krms.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.krms.api.engine.TermResolver;

public class RulesExecutionTest {

    private List<TermResolver<?>> termResolvers;
    private StatementServiceTranslationTest statementTranslator;
    
    @Before
    public void setUp() {
        termResolvers = new ArrayList<TermResolver<?>>();
        
        termResolvers.add(new CourseSetResolver());
        termResolvers.add(new CompletedCoursesResolver());
        termResolvers.add(new GradeForCourseResolver(new DummyLrcService()));
        termResolvers.add(new GpaForCourseResolver());
        termResolvers.add(new TestSetScoreResolver());
        termResolvers.add(new CompletedCreditsForCourseSetResolver());
        
        statementTranslator = new StatementServiceTranslationTest();
        statementTranslator.setUp();
    }
    
    @After
    public void tearDown() {
        
    }
    
    @Test
    public void executeStatementsForStudent1() {
        
    }
    
    @Test
    public void executeStatementsForStudent2() {
        
    }
    
    @Test
    public void executeStatementsForStudent3() {
        
    }
    
}
