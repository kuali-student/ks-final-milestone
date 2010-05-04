/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.repository.drools;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.brms.repository.drools.DroolsJackrabbitRepository;

public class DroolsJackrabbitRepositoryStartupShutdownTest {
    /** Drools test utility class */
    private DroolsTestUtil droolsTestUtil = DroolsTestUtil.getInstance();
    
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

    private void assertLogin( DroolsJackrabbitRepository repo ) {
        try {
            repo.login( droolsTestUtil.getSuperUserCredentials() );
            assertTrue( true );
        } catch( Exception e ) {
            fail( "Unable to login to repository: " + e.getMessage() );
        }
    }
    
    @Test
    public void testStartupShutdownRepositoryWithUrlConfiguration() throws Exception {
        // Get repository.xml from /repository
        URL url = DroolsJackrabbitRepositoryStartupShutdownTest.class.getResource("/drools-repository");
        DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository( url );
        repo.clearAll();
        repo.startupRepository();
        assertLogin( repo );
        assertNotNull( repo.getRepository().listStates() );
        repo.shutdownRepository();
        //repo.clearAll();
    }    

}
