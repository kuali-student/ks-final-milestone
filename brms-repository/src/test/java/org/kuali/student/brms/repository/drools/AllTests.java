package org.kuali.student.brms.repository.drools;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses( {
    DroolsJackrabbitRepositoryStartupShutdownTest.class, 
    DroolsJackrabbitRepositoryTest.class } )
public class AllTests {
}
