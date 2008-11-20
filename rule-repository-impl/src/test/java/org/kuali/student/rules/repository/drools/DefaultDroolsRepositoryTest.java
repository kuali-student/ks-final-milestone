package org.kuali.student.rules.repository.drools;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.rules.repository.drools.DefaultDroolsRepository;

public class DefaultDroolsRepositoryTest {

    //@Test
    //public void testStartupShutdownRepository() throws Exception {
    //    URL url = DefaultDroolsRepositoryTest.class.getResource("/drools-repository");
    //    DefaultDroolsRepository repo = new DefaultDroolsRepository( url.toString() );
    //    assertNotNull( repo.getRepository().listStates() );
    //    repo.shutdownRepository();
    //}    

    //@Test
    //public void testStartupShutdownRepositoryWithUrlConfiguration() throws Exception {
        // Get repository.xml from /repository
    //    URL url = DefaultDroolsRepositoryTest.class.getResource("/drools-repository");
    //    DefaultDroolsRepository repo = new DefaultDroolsRepository( url );
    //    assertNotNull( repo.getRepository().listStates() );
    //    repo.shutdownRepository();
    //}    

    @Test
    public void testStartupShutdownRepositoryWithPathConfiguration() throws Exception {
        // Get repository.xml from directory /drools-repository
        String path = "/drools-repository";
        DefaultDroolsRepository repo = new DefaultDroolsRepository( path );
        assertNotNull( repo.getRepository().listStates() );
        repo.shutdownRepository();
        //repo.clearAll();
    }    
}
