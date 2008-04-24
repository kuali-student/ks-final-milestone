/*******************************************************************************
 * Copyright (c) 2001, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
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
