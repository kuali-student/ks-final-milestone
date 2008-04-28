package org.kuali.student.brms.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( 
    {
        org.kuali.student.brms.repository.RuleEngineRepositoryTest.class, 
        org.kuali.student.brms.repository.rule.AllTests.class,
        org.kuali.student.brms.repository.drools.AllTests.class
    } )
public class AllTests {
}
