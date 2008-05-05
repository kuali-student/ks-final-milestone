package org.kuali.student.brms.repository.drools;

import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.junit.Test;

public class DefaultDroolsRepositoryTest {

    @Test
    public void testStartupShutdownRepository() throws Exception {
        DefaultDroolsRepository repo = new DefaultDroolsRepository();
        assertNotNull( repo.getRepository().listStates() );
        repo.shutdownRepository();
    }    

    //@Test
    //public void testStartupShutdownRepositoryWithUrlConfiguration() throws Exception {
        // Get repository.xml from /repository
    //    URL url = DefaultDroolsRepositoryTest.class.getResource("/repository");
    //    DefaultDroolsRepository repo = new DefaultDroolsRepository( url );
    //    assertNotNull( repo.getRepository().listStates() );
    //    repo.shutdownRepository();
    //}    

    @Test
    public void testStartupShutdownRepositoryWithPathConfiguration() throws Exception {
        // Get repository.xml from /repository
        URL url = DefaultDroolsRepositoryTest.class.getResource("/repository");
        // E.g. path=file:/C:/drools/repository
        String path = url.toExternalForm();
        DefaultDroolsRepository repo = new DefaultDroolsRepository( path );
        assertNotNull( repo.getRepository().listStates() );
        repo.shutdownRepository();
    }    
}
