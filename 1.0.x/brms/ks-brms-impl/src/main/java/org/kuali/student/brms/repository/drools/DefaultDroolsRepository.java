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

import javax.jcr.Credentials;
import javax.jcr.SimpleCredentials;

import org.kuali.student.brms.repository.exceptions.RepositoryLoginException;
import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;

public final class DefaultDroolsRepository extends DroolsJackrabbitRepository {

    /**
     * Constructs a default repository and initialize it:
     * <ol>
     * <li>Startup repository</li>
     * <li>Login as 'superuser' with password 'superuser'</li>
     * </ol>
     * A default <code>repository.xml</code> will be created in the current directory.
     * 
     * @param url Location of the Jackrabbit <code>repository.xml</code> configuration file
     */
    public DefaultDroolsRepository( String url ) {
        super( url );
        try {
            initialize();
        } catch( RepositoryLoginException e ) {
            throw new RuleEngineRepositoryException( e );
        }
    }

    /**
     * Starts up the repository and logins as a superuser. 
     * This is a convenience method.<br/><br/> 
     * Calls:</br>
     * <code>startupRepository()</code><br/> 
     * <code>login( "superuser" )</code><br/>
     * 
     * @throws Exception
     */
    private void initialize() throws RepositoryLoginException {
        startupRepository();
        String id = "superuser";
        char[] password = "superuser".toCharArray();
        Credentials credentials = new SimpleCredentials(id, password);
        login(credentials);
    }

    /**
     * 
     * This method is a convenience method to cleanly shutdown the repository
     *
     */
    /*public void shutdown() {
      clearData();
      shutdownRepository();
      clearAll();
    }*/
    
}
