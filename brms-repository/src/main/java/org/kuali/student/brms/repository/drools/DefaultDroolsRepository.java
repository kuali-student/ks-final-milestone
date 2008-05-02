package org.kuali.student.brms.repository.drools;

import java.net.URL;

import javax.jcr.Credentials;
import javax.jcr.SimpleCredentials;

public final class DefaultDroolsRepository extends DroolsJackrabbitRepository {

    /**
     * Constructs a default repository and initialize it:
     * <ol>
     * <li>Startup repository</li>
     * <li>Login as 'superuser' with password 'superuser'</li>
     * </ol>
     * A default <code>repository.xml</code> will be created in the current directory.
     */
    public DefaultDroolsRepository() {
        super();
        initialize();
    }

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
    public DefaultDroolsRepository( URL url ) {
        super( url );
        initialize();
    }

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
        initialize();
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
    private void initialize() {
        startupRepository();
        String id = "superuser";
        char[] password = "superuser".toCharArray();
        Credentials credentials = new SimpleCredentials(id, password);
        login(credentials);
    }

}
