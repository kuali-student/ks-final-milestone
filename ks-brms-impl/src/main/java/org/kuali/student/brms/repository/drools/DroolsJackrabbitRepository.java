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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.api.JackrabbitRepository;
import org.drools.repository.RulesRepository;
import org.drools.repository.RulesRepositoryAdministrator;
import org.kuali.student.brms.repository.exceptions.RepositoryLoginException;
import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the <a href="http://www.jboss.org/drools/">Drools</a> and 
 * <a href="http://jackrabbit.apache.org/">Jackrabbit</a> repository implementation.
 * <p>
 * <b>Example: 1 - Create, initialize and login to a new default repository</b>
 * </p>
 * <pre>
 * DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository();
 * // Initialize repository - startup and login to repository 
 * repo.initialize();
 * //Shutdown (and logout from) the repository:
 * repo.shutdownRepository()
 * </pre>
 * 
 * <b>Example: 2 - Create and login to an existing repository</b>
 * 
 * <pre>
 * DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository(); 
 * //Login into the repository:
 * String id = &quot;kauli-repo-admin&quot;;
 * char[] password = &quot;kuali-repo-nimda&quot;.toCharArray();
 * Credentials credentials = new SimpleCredentials( id, password );
 * repo.login( credentials );
 * 
 * RuleEngineRepository rulesRepository = new RuleEngineRepositoryDroolsImpl( repo.getRepository() );
 * 
 * String rulesetUuid = rulesRepository.createRuleSet( 
 *     &quot;MyPackage&quot;, &quot;My package description&quot; );
 * String ruleSourceCode = &quot;rule \&quot;rule_1\&quot; when then end&quot;;
 * String ruleUuid1 = rulesRepository.createRule( 
 *     rulesetUuid, &quot;rule_1&quot;, &quot;&quot;, ruleSourceCode, &quot;testJSR94&quot; );
 * ...
 * 
 * //Shutdown (and logout from) the repository:
 * repo.shutdownRepository();
 * </pre>
 * 
 * <b>Example: 3 - Create, delete a repository's data and file system files and login</b>
 * 
 * <pre>
 * DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository(); 
 * 
 * //Delete repository files:
 * repo.clearAll();
 * repo.startupRepository();
 * //Login into the repository:
 * String id = &quot;kauli-repo-admin&quot;;
 * char[] password = &quot;kuali-repo-nimda&quot;.toCharArray();
 * Credentials credentials = new SimpleCredentials( id, password );
 * repo.login( credentials );
 * 
 * RuleEngineRepository rulesRepository = new RuleEngineRepositoryDroolsImpl( repo.getRepository() );
 * 
 * String rulesetUuid = rulesRepository.createRuleSet( 
 *     &quot;MyPackage&quot;, &quot;My package description&quot; );
 * String ruleSourceCode = &quot;rule \&quot;rule_1\&quot; when then end&quot;;
 * String ruleUuid1 = rulesRepository.createRule( 
 *     rulesetUuid, &quot;rule_1&quot;, &quot;&quot;, ruleSourceCode, &quot;testJSR94&quot; );
 * ...
 * 
 * //Logout current user from the repository:
 * repo.logout()
 * //Shutdown the repository:
 * repo.shutdownRepository();
 * </pre>
 */
public class DroolsJackrabbitRepository {
    
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(DroolsJackrabbitRepository.class);
    
    /** Jackrabbit repository configuration file */
    private final static String REPOSITORY_CONFIG_FILE = "repository.xml";
    /** Jackrabbit repository configuration file */
    private final static String REPOSITORY_CONFIG_DIR = "repository";
    /** Location of the Jackrabbit repository */
    private URL url;
    /** Location of Jackrabbit repository configuration */
    private String repoConfigLocation;
    /** Current repository credentials */
    private Credentials credentials;
    /** Jackrabbit repository */
    private Repository repository; 
    /** Jackrabbit repository configuration */
    private RuleEngineRepositoryConfigurator repoConfig;
    /** Current Jackrabbit repository session for a logged in user */
    private Session repositorySession;

    /**
     * Constructs a new Drools Jackrabbit repository.
     * 
     * @param url Location of <code>repository.xml</code> configuration file
     */
    public DroolsJackrabbitRepository(final URL url) {
        if ( url == null ) {
            throw new IllegalArgumentException( "Repository configuration URL cannot be null" );
        }
        this.url = url;
    }

    /**
     * Constructs a new Drools Jackrabbit repository.
     * 
     * @param url Location of <code>repository.xml</code> configuration file
     */
    public DroolsJackrabbitRepository(final String repoConfigLocation) {
        if ( repoConfigLocation == null ) {
            throw new IllegalArgumentException( "Repository configuration path cannot be null" );
        }
        this.repoConfigLocation = repoConfigLocation;
        this.url = DroolsJackrabbitRepository.class.getResource(repoConfigLocation) ;
        logger.info("**********************************************************************");
        logger.info("* repoConfigLocation = " + this.repoConfigLocation);
        logger.info("* String url         = " + url);
        logger.info("* URL url            = " + this.url);
        logger.info("**********************************************************************");
    }
    
    /**
     * Get the repository path.
     * If it is a jar file then return the <code>user.dir</code>
     * 
     * @param url Repository location URL
     * @return Repository path
     */
    private String getPath( URL url ) {
        if (url == null) {
            return null;
        }
        // Workaround for Jackrabbit
        if ( isFile( url ) ) {
            try {
                File file = new File(url.toURI());
                return file.getAbsolutePath();
            } catch (URISyntaxException e) {
                throw new RuleEngineRepositoryException(e);
            }
        } else if ( isJar( url ) ) {
            return System.getProperty("user.dir") + this.repoConfigLocation;
        } else {
            return url.getPath();
        }
    }
    
    /**
     * Determines whether a URL is a file.
     * 
     * @param url A URL
     * @return Returns true if <code>url</code> is a file
     */
    private boolean isFile( URL url ) {
        return url.getProtocol().equalsIgnoreCase("file");
    }

    /**
     * Determines whether a URL is a jar file.
     * 
     * @param url A URL
     * @return Returns true if <code>url</code> is a jar file
     */
    private boolean isJar( URL url ) {
        return url.getProtocol().equalsIgnoreCase("jar");
    }

    /**
     * Starts up the repository.
     */
    public void startupRepository() {
        logger.info( "Starting repository..." );
        this.repoConfig = new RuleEngineRepositoryConfiguratorImpl();
        try {
            URL configFile = new URL( 
                    this.url + ( !this.url.toString().endsWith( "/" ) ? "/" : "" ) + 
                    RuleEngineRepositoryConfiguratorImpl.DEFAULT_REPOSITORY_XML );
            this.repository = repoConfig.getJCRRepository( configFile, this.url );
        } catch( MalformedURLException e ) {
            throw new RuleEngineRepositoryException("Invalid repository configuration." + e);
        }
        logger.info( "Repository started" );
    }

    /**
     * Gets the current logged in credentials.
     * 
     * @return Current logged in credentials
     */
    public Credentials getCredentials() {
        return this.credentials;
    }

    /**
     * <p>Warning: This will delete everything in the repository including all 
     * the repository system files.</p> 
     * <p>Path value must be 'repository' in repository.xml</p>
     * Example:
     * <pre>
     * &lt;FileSystem class="org.apache.jackrabbit.core.fs.local.LocalFileSystem"&gt;
     * &lt;param name="path" value="<b>repository</b>"/&gt;&lt;/FileSystem&gt;
     * </pre>
     */
    public void clearAll() {
        logger.info( "Deleting repository..." );
        if ( this.repositorySession != null && this.repositorySession.isLive() ) {
            throw new RuleEngineRepositoryException("Repository must be shut down before clearing all repository database files.");
        }
        
        File repoDir = null;
        File[] exclude = null;
        if ( this.url == null ) {
            repoDir = new File(REPOSITORY_CONFIG_DIR);
        } else {
            repoDir = new File( getPath( this.url ) );
            File config = new File( getPath( this.url ) + "/" + 
                    RuleEngineRepositoryConfiguratorImpl.DEFAULT_REPOSITORY_XML );
            if ( !isJar( this.url ) ) {
                exclude = new File[]{repoDir, config};
            }
        }
        logger.info( "DELETE repository sub-directories: " + repoDir.getAbsolutePath());
        boolean b = deleteDir(exclude, repoDir);
        logger.info( "Repository DELETED: " + b );
    }

    /**
     * Deletes all sub directories under <code>parent</code>. <code>parent</code> will not be deleted.
     * <code>parent</code> and <code>dir</code> should be the same.
     * 
     * @param exclude File or directories to exclude
     * @param dir Sub-directories under which to delete
     * @return True if all sub-directories have been deleted, otherwise false
     */
    private boolean deleteDir(File[] exclude, File dir) {
        if (dir.isDirectory()) {
            String[] subdir = dir.list();
            for (int i = 0; i < subdir.length; i++) {
                boolean success = deleteDir(exclude, new File(dir, subdir[i]));
                if (!success) {
                    return false;
                }
            }
        }
        boolean delete = true;
        for (int i = 0; exclude != null && i < exclude.length; i++) {
            if (dir == exclude[i] || dir.getAbsolutePath().equals(exclude[i].getAbsolutePath())) {
                delete = false;
                break;
            }
        }
        if (delete) {
            return dir.delete();
        }
        return true;
    }

    /**
     * This will clear all the repository data. 
     * This will not delete any files on the file system.
     */
    public void clearData() {
        if ( this.repositorySession == null ) {
            throw new RuleEngineRepositoryException( "Login to repository is required" );
        }
        RulesRepositoryAdministrator repoAdmin = new RulesRepositoryAdministrator(this.repositorySession);
        
        if ( repoAdmin == null ) {
            throw new RuleEngineRepositoryException( "Unable to create a repository administrator to clear data" );
        }
        else if ( !repoAdmin.isRepositoryInitialized() ) {
            throw new RuleEngineRepositoryException( "Repository has not been initialized. Please login again." );
        }
        
        repoAdmin.clearRulesRepository();
        this.repoConfig.setupRulesRepository(this.repositorySession);
        logger.info( "Repository has cleared all data" );
    }

    /**
     * Gets the Drools rules repository.
     * 
     * @return Rules repository
     */
    public RulesRepository getRepository() {
        RulesRepository ruleRepository = new RulesRepository(this.repositorySession);
        return ruleRepository;
    }

    /**
     * Shuts down the repository.
     */
    public void shutdownRepository() {
        logger.info( "Shutting down repository..." );
        if (this.repositorySession != null) {
            this.repositorySession.logout();
        }
        JackrabbitRepository repo = (JackrabbitRepository) this.repository;
        repo.shutdown();
        logger.info( "Repository has shut down" );
    }

    /**
     * Logout the current user.
     */
    public void logout() {
        this.repositorySession.logout();
    }

    /**
     * Login a user.
     * 
     * @param credentials User credentials
     * @throws RepositoryLoginException
     */
    public void login(Credentials credentials) throws RepositoryLoginException{
        this.credentials = credentials;
        try {
            this.repositorySession = repository.login(credentials);
        } catch (LoginException e) {
            throw new RepositoryLoginException( e );
        } catch (RepositoryException e) {
            throw new RepositoryLoginException( e );
        }
        this.repoConfig.setupRulesRepository(this.repositorySession);
    }
}
