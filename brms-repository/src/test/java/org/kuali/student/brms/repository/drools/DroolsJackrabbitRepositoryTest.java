package org.kuali.student.brms.repository.drools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URL;

import javax.jcr.Credentials;
import javax.jcr.SimpleCredentials;

import org.drools.repository.StateItem;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DroolsJackrabbitRepositoryTest {
    private static DroolsJackrabbitRepository repo;

    /*@BeforeClass
    public static void setUpOnce() throws Exception {
        URL url = BRMSRepositoryTest.class.getResource("/repository");
        repo = new DroolsJackrabbitRepository(url);
        //repo.initialize();
        repo.clearAll();
        repo.startupRepository();
        repo.login( getCredentials() );
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
        repo.shutdownRepository();
    }

    @Before
    public void setUp() throws Exception {
        repo.login(repo.getCredentials());
        repo.clearData();
    }

    @After
    public void tearDown() throws Exception {
        repo.logout();
    }*/

    private static Credentials getCredentials() {
        String id = "superuser";
        char[] password = "superuser".toCharArray();
        return new SimpleCredentials(id, password);
    }
    
    private void assertLogin( DroolsJackrabbitRepository repo ) {
        try {
            repo.login( getCredentials() );
            assertTrue( true );
        } catch( Exception e ) {
            fail( "Unable to login to repository: " + e.getMessage() );
        }
    }
    
    @Test
    public void testStartupRepositoryWithUrlConfiguration() throws Exception {
        // Get repository.xml from /repository
        URL url = DroolsJackrabbitRepositoryTest.class.getResource("/repository");
        DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository( url );
        repo.clearAll();
        repo.startupRepository();
        assertLogin( repo );
        assertNotNull( repo.getRepository().listStates() );
        repo.shutdownRepository();
    }    

    @Test
    public void testStartupDefaultRepository() throws Exception {
        DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository();
        repo.clearAll();
        repo.startupRepository();
        assertLogin( repo );
        assertNotNull( repo.getRepository().listStates() );
        repo.shutdownRepository();
    }    
    
    @Test
    public void testShutdownRepository() throws Exception {
        DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository();
        repo.clearAll();
        repo.startupRepository();
        repo.login( getCredentials() );
        assertLogin( repo );
        repo.shutdownRepository();
        assertFalse( repo.getRepository().getSession().isLive() );
    }

    @Test
    public void testGetRepository() throws Exception {
        DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository();
        repo.clearAll();
        repo.startupRepository();
        repo.login( getCredentials() );
        assertFalse( repo.getRepository() == null );
        assertTrue( repo.getRepository().getSession().isLive() );
        repo.shutdownRepository();
    }
    
    @Test
    public void testLogin() throws Exception
    {
        DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository();
        repo.clearAll();
        repo.startupRepository();
        repo.login( getCredentials() );
        assertTrue( repo.getRepository().getSession().isLive() );
        repo.shutdownRepository();
    }

    @Test
    public void testLogout() throws Exception {
        DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository();
        repo.clearAll();
        repo.startupRepository();
        repo.login( getCredentials() );
        repo.logout();
        assertFalse( repo.getRepository().getSession().isLive() );
        repo.shutdownRepository();
    }
    
    @Test
    public void testClearData() throws Exception {
        DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository();
        repo.clearAll();
        repo.startupRepository();
        repo.login( getCredentials() );
        StateItem item1 = repo.getRepository().createState( "NewState" );
        StateItem item2 = repo.getRepository().getState( "NewState" );
        assertEquals( item1.getName(), item2.getName() );
        repo.shutdownRepository();
    }

}
