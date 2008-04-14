package org.kuali.student.brms.repository.drools;

import java.io.File;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.api.JackrabbitRepository;
import org.drools.repository.JCRRepositoryConfigurator;
import org.drools.repository.JackrabbitRepositoryConfigurator;
import org.drools.repository.RepositorySessionUtil;
import org.drools.repository.RulesRepository;
import org.drools.repository.RulesRepositoryAdministrator;

/**
 * Drools repository.
 * 
 * Example: 1 - Create, initialize and login to a new default repository
 * 
 * <pre>
 * DroolsJackrabbitRepositoryImpl repo = new DroolsJackrabbitRepositoryImpl();
 * // Initialize repository - delete any existing files 
 * repo.init();
 * //Shutdown (and logout from) the repository:
 * repo.shutdownRepository()
 * </pre>
 * 
 * Example: 2 - Create and login to an existing repository
 * <pre>
 * DroolsJackrabbitRepositoryImpl repo = new DroolsJackrabbitRepositoryImpl(); 
 * //Login into the repository:
 * String id = "kauli-repo-admin";
 * char[] password = "kuali-repo-nimda".toCharArray();
 * Credentials credentials = new SimpleCredentials( id, password );
 * repo.login( credentials );
 * 
 * brmsRepository = new BRMSRepositoryDroolsImpl( repo.getRepository() );
 * 
 * String rulesetUuid = brmsRepository.createRuleSet( 
 *     "MyPackage", "My package description" );
 * String ruleUuid1 = brmsRepository.createRule( 
 *     rulesetUuid, "rule_1", "", DroolsTestUtil.getSimpleRule1(), "testJSR94" );
 * ...
 * 
 * //Shutdown (and logout from) the repository:
 * repo.shutdownRepository();
 * </pre>
 * 
 * Example: 3 - Create, delete a repository's data and file system files and login
 * <pre>
 * DroolsJackrabbitRepositoryImpl repo = new DroolsJackrabbitRepositoryImpl(); 
 *
 * //Delete repository files:
 * repo.clearAll();
 * repo.startupRepository();
 * //Login into the repository:
 * String id = "kauli-repo-admin";
 * char[] password = "kuali-repo-nimda".toCharArray();
 * Credentials credentials = new SimpleCredentials( id, password );
 * repo.login( credentials );
 * 
 * brmsRepository = new BRMSRepositoryDroolsImpl( repo.getRepository() );
 * String rulesetUuid = brmsRepository.createRuleSet( 
 *     "MyPackage", "My package description" );
 * String ruleUuid1 = brmsRepository.createRule( 
 *     rulesetUuid, "rule_1", "", DroolsTestUtil.getSimpleRule1(), "testPackage" );
 * ...
 * 
 * //Logout current user from the repository:
 * repo.logout()
 * //Shutdown the repository:
 * repo.shutdownRepository();
 * </pre>
 * 
 */
public class DroolsJackrabbitRepository
{
	private String path;
	private Credentials credentials;
	private Repository repository;
	
	private JCRRepositoryConfigurator repoConfig;

	private Session repositorySession;

	/**
	 * Constructor
	 */
	public DroolsJackrabbitRepository()
	{
		this( null );
	}

	/**
	 * Constructor
	 * 
	 * @param path File system path to store the repository
	 */
	public DroolsJackrabbitRepository( String path )
	{
		this.path = path;
	}

	/**
	 * Initialize a new repository.
	 * Calls:
	 *   <code>deleteRepository()</code>
	 *   <code>startupRepository()</code>
	 *   <code>login( ... )</code>
	 * @throws Exception
	 */
	public void init() throws Exception
	{
		clearAll();
		startupRepository();
		String id = "kauli-repo-admin";
		char[] password = "kuali-repo-nimda".toCharArray();
		credentials = new SimpleCredentials( id, password );
		login( credentials );
	}

	/**
	 * Starts up the repository.
	 */
	public void startupRepository()
	{
		this.repoConfig = new JackrabbitRepositoryConfigurator();
		this.repository = repoConfig.getJCRRepository( this.path );
	}

	/**
	 * Gets the current logged in credentials.
	 * 
	 * @return Current logged in credentials
	 */
	public Credentials getCredentials()
	{
		return this.credentials;
	}
	
	/**
	 * This will delete everything in the repository including all the
	 * repository files.
	 */
	public void clearAll()
	{
        File repoDir = new File( "repository" );
        //System.out.println("DELETE test repository directory: " + repoDir.getAbsolutePath());
        RepositorySessionUtil.deleteDir( repoDir );
        //System.out.println("Repository directory deleted.");
	}

	/**
	 * This will clear all the repository data. 
	 * This will not delete any files on the file system.
	 */
	public void clearData()
	{
        RulesRepositoryAdministrator repoAdmin = new RulesRepositoryAdministrator( this.repositorySession );

        if ( repoAdmin.isRepositoryInitialized() ) 
        {
        	repoAdmin.clearRulesRepository();
        }
        this.repoConfig.setupRulesRepository( this.repositorySession );
	}

	/**
	 * Gets the Drools rules repository.
	 * 
	 * @return Rules repository
	 */
	public RulesRepository getRepository()
	{
		RulesRepository ruleRepository = new RulesRepository( this.repositorySession );   
        return ruleRepository;
	}
	
	/**
	 * Shuts down the repository.
	 * 
	 * @throws Exception
	 */
	public void shutdownRepository()
	{
		if ( this.repositorySession != null )
		{
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
	public void logout()
	{
		this.repositorySession.logout();
	}

	/**
	 * Login a user.
	 * 
	 * @param credentials User credentials
	 * @throws Exception
	 */
	public void login( Credentials credentials ) throws Exception
	{
		this.credentials = credentials;
		this.repositorySession = repository.login( credentials );
	}
}
