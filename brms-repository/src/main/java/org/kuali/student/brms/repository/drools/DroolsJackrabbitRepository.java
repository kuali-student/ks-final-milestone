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
import org.drools.repository.RepositorySessionUtil;
import org.drools.repository.RulesRepository;
import org.drools.repository.RulesRepositoryAdministrator;

/**
 * Drools repository.
 * 
 * Example: 1 - Create, initialize and login to a new default repository
 * 
 * <pre>
 * DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository();
 * // Initialize repository - delete any existing files 
 * repo.initialize();
 * //Shutdown (and logout from) the repository:
 * repo.shutdownRepository()
 * </pre>
 * 
 * Example: 2 - Create and login to an existing repository
 * <pre>
 * DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository(); 
 * //Login into the repository:
 * String id = "kauli-repo-admin";
 * char[] password = "kuali-repo-nimda".toCharArray();
 * Credentials credentials = new SimpleCredentials( id, password );
 * repo.login( credentials );
 * 
 * BRMSRepository brmsRepository = new BRMSRepositoryDroolsImpl( repo.getRepository() );
 * 
 * String rulesetUuid = brmsRepository.createRuleSet( 
 *     "MyPackage", "My package description" );
 * String ruleSourceCode = “rule \“rule_1\” when then end”;
 * String ruleUuid1 = brmsRepository.createRule( 
 *     rulesetUuid, "rule_1", "", ruleSourceCode, "testJSR94" );
 * ...
 * 
 * //Shutdown (and logout from) the repository:
 * repo.shutdownRepository();
 * </pre>
 * 
 * Example: 3 - Create, delete a repository's data and file system files and login
 * <pre>
 * DroolsJackrabbitRepository repo = new DroolsJackrabbitRepository(); 
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
 * BRMSRepository brmsRepository = new BRMSRepositoryDroolsImpl( repo.getRepository() );
 * String rulesetUuid = brmsRepository.createRuleSet( 
 *     "MyPackage", "My package description" );
 * String ruleSourceCode = “rule \“rule_1\” when then end”;
 * String ruleUuid1 = brmsRepository.createRule( 
 *     rulesetUuid, "rule_1", "", ruleSourceCode, "testJSR94" );
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
	// Jackrabbit repository configuration file
	private final static String REPOSITORY_CONFIG_FILE = "repository.xml";
	// Location of the Jackrabbit repository
	private URL url;
	// Location of the Jackrabbit repository
	private String path;
	// Current repository credentials
	private Credentials credentials;
	// Jackrabbit repository
	private Repository repository;
	// Jackrabbit repository configuration
	private JCRRepositoryConfigurator repoConfig;
	// Current Jackrabbit repository session for a logged in user
	private Session repositorySession;

	/**
	 * Constructor
	 */
	public DroolsJackrabbitRepository()
	{
		this( null );
	}

	/**
	 * URL of the Jackrabbit repository <code>repository.xml</code>
	 * configuration file.
	 * 
	 * @param url Location of <code>repository.xml</code> 
	 */
	public DroolsJackrabbitRepository( URL url )
	{
		this.url = url;
		
		if ( url == null )
		{
			throw new RuntimeException( "JackRabbit repository configuration not found" );
		}
		
		if ( url.getProtocol().equalsIgnoreCase( "file" ) )
		{
			try 
			{
				File file = new File( url.toURI() );
				this.path = file.getAbsolutePath();
			} 
			catch ( URISyntaxException e )
			{
				throw new RuntimeException( e );
			}
		}
		else
		{
			this.path = url.getPath();
		}
	}

	/**
	 * Warning: This will delete all repository system files.</br>
	 * Initialize a new repository.<br/><br/>
	 * Calls:</br>
	 * <code>clearAll()</code></br>
	 * <code>startupRepository()</code></br>
	 * <code>login( ... )</code></br>
	 * @throws Exception
	 */
	public void initialize() throws Exception
	{
		clearAll();
		startupRepository();
		String id = "superuser";
		char[] password = "superuser".toCharArray();
		credentials = new SimpleCredentials( id, password );
		login( credentials );
	}

	/**
	 * Starts up the repository.
	 */
	public void startupRepository()
	{
        //System.out.println( "Repository: " + this.path );
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
	 * Warning: This will delete everything in the repository including all the
	 * repository system files.
	 * 
	 * Path value must be repository in repository.xml
	 * <FileSystem class="org.apache.jackrabbit.core.fs.local.LocalFileSystem">
	 *     <param name="path" value="repository"/>
	 * </FileSystem>
	 */
	public void clearAll()
	{
		try
		{
			File repoDir = new File( this.url.toURI() );
	        //System.out.println( "DELETE repository sub-directories: " + repoDir.getAbsolutePath());
			File config = new File( this.path + "/" + REPOSITORY_CONFIG_FILE );
	        File[] exclude = new File[] { repoDir, config };
			boolean b = deleteDir( exclude , repoDir );
	        //System.out.println( "Repository sub-directories deleted: " + b );
		} 
		catch ( URISyntaxException e )
		{
			throw new RuntimeException( e );
		}
	}

	/**
	 * Deletes all sub directories under <code>parent</code>.
	 * <code>parent</code> will not be deleted.
	 * <code>parent</code> and <code>dir</code> should be the same.
	 * 
	 * @param parent Parent directory which not to delete 
	 * @param dir Sub-directories under which to delete
	 * @return True if all sub-directories have been deleted, otherwise false 
	 */
	public boolean deleteDir( File[] exclude, File dir )
    {
		if ( dir.isDirectory() ) 
		{
			String subdir[] = dir.list();
			for (int i = 0; i < subdir.length; i++) 
			{
				boolean success = deleteDir( exclude, new File( dir, subdir[i] ) );
				if ( !success )
				{
					return false;
				}
			}
		}
		boolean delete = true;
		for( int i=0; i<exclude.length; i++ )
		{
			if ( dir == exclude[i] || dir.getAbsolutePath().equals( exclude[i].getAbsolutePath() ) )
			{
				delete = false;
				break;
			}
		}
		if ( delete )
		{
			return dir.delete();
		}
		return true;
    }

	/**
	 * This will clear all the repository data. This will not delete any files
	 * on the file system.
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
