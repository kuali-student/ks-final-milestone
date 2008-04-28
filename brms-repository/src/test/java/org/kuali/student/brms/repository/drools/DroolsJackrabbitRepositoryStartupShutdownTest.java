/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.brms.repository.drools;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URL;

import javax.jcr.Credentials;
import javax.jcr.SimpleCredentials;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DroolsJackrabbitRepositoryStartupShutdownTest {
    @BeforeClass
    public static void setUpOnce() throws Exception {
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

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
        URL url = DroolsJackrabbitRepositoryStartupShutdownTest.class.getResource("/repository");
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

}
