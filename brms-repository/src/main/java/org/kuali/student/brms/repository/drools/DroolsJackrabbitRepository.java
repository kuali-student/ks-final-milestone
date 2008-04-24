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
import java.net.URISyntaxException;
import java.net.URL;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.api.JackrabbitRepository;
import org.drools.repository.JCRRepositoryConfigurator;
import org.drools.repository.JackrabbitRepositoryConfigurator;
import org.drools.repository.RulesRepository;
import org.drools.repository.RulesRepositoryAdministrator;

/**
 * This is the <a href="http://www.jboss.org/drools/">Drools</a> and 
 * <a href="http://jackrabbit.apache.org/">Jackrabbit</a> repository implementation.
 * <p>
 * <b>Example: 1 - Create, initialize and login to a new default repository</b>
 * </p>
 * <pre>
 * DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository();
 * // Initialize repository - delete any existing files 
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
    /** Jackrabbit repository configuration file */
    private final static String REPOSITORY_CONFIG_FILE = "repository.xml";
    /** Location of the Jackrabbit repository */
    private URL url;
    /** Location of the Jackrabbit repository */
    private String path;
    /** Current repository credentials */
    private Credentials credentials;
    /** Jackrabbit repository */
    private Repository repository; 
    /** Jackrabbit repository configuration */
    private JCRRepositoryConfigurator repoConfig;
    /** Current Jackrabbit repository session for a logged in user */
    private Session repositorySession;

    /**
     * Constructor
     */
    public DroolsJackrabbitRepository() {
        this(null);
    }

    /**
     * URL of the Jackrabbit repository <code>repository.xml</code> configuration file.
     * 
     * @param url
     *            Location of <code>repository.xml</code>
     */
    public DroolsJackrabbitRepository(URL url) {
        this.url = url;

        if ( url != null )
        {
            if (this.url.getProtocol().equalsIgnoreCase("file")) {
                try {
                    File file = new File(this.url.toURI());
                    this.path = file.getAbsolutePath();
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            } else {
                this.path = url.getPath();
            }
        }
    }

    /**
     * Warning: This will delete all repository system files.</br> Initialize a new repository.<br/><br/> Calls:</br>
     * <code>clearAll()</code></br> <code>startupRepository()</code></br> <code>login( ... )</code></br>
     * 
     * @throws Exception
     */
    public void initialize() throws Exception {
        clearAll();
        startupRepository();
        String id = "superuser";
        char[] password = "superuser".toCharArray();
        credentials = new SimpleCredentials(id, password);
        login(credentials);
    }

    /**
     * Starts up the repository.
     */
    public void startupRepository() {
        //System.out.println( "Repository: " + this.path );
        this.repoConfig = new JackrabbitRepositoryConfigurator();
        this.repository = repoConfig.getJCRRepository(this.path);
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
     * Warning: This will delete everything in the repository including all the repository system files. Path value must be
     * repository in repository.xml <FileSystem class="org.apache.jackrabbit.core.fs.local.LocalFileSystem"> <param
     * name="path" value="repository"/> </FileSystem>
     */
    public void clearAll() {
        try {
            File repoDir = null;
            File[] exclude = null;
            if ( url == null ) {
                repoDir = new File("repository");
            }
            else {
                repoDir = new File(this.url.toURI());
                File config = new File(this.path + "/" + REPOSITORY_CONFIG_FILE);
                exclude = new File[]{repoDir, config};
            }
            //System.out.println( "DELETE repository sub-directories: " + repoDir.getAbsolutePath());
            boolean b = deleteDir(exclude, repoDir);
            //System.out.println( "Repository sub-directories deleted: " + b );
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes all sub directories under <code>parent</code>. <code>parent</code> will not be deleted.
     * <code>parent</code> and <code>dir</code> should be the same.
     * 
     * @param parent
     *            Parent directory which not to delete
     * @param dir
     *            Sub-directories under which to delete
     * @return True if all sub-directories have been deleted, otherwise false
     */
    private boolean deleteDir(File[] exclude, File dir) {
        if (dir.isDirectory()) {
            String subdir[] = dir.list();
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
     * This will clear all the repository data. This will not delete any files on the file system.
     */
    public void clearData() {
        RulesRepositoryAdministrator repoAdmin = new RulesRepositoryAdministrator(this.repositorySession);
        if (repoAdmin.isRepositoryInitialized()) {
            repoAdmin.clearRulesRepository();
        }
        this.repoConfig.setupRulesRepository(this.repositorySession);
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
     * 
     * @throws Exception
     */
    public void shutdownRepository() {
        if (this.repositorySession != null) {
            this.repositorySession.logout();
        }
        JackrabbitRepository repo = (JackrabbitRepository) this.repository;
        repo.shutdown();
    }

    /**
     * Logout the current user.
     * 
     * @throws Exception
     */
    public void logout() {
        this.repositorySession.logout();
    }

    /**
     * Login a user.
     * 
     * @param credentials
     *            User credentials
     * @throws Exception
     */
    public void login(Credentials credentials) throws Exception {
        this.credentials = credentials;
        this.repositorySession = repository.login(credentials);
        this.repoConfig.setupRulesRepository(this.repositorySession);
    }
}
