package org.kuali.student.brms.repository.drools;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.TransientRepository;
import org.apache.jackrabbit.core.TransientRepository.RepositoryFactory;
import org.apache.jackrabbit.core.config.ConfigurationException;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.drools.repository.JackrabbitRepositoryConfigurator;
import org.drools.repository.RulesRepositoryException;
import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;

public class RuleEngineRepositoryConfiguratorImpl 
    extends JackrabbitRepositoryConfigurator
    implements RuleEngineRepositoryConfigurator {

    /** Logging framework */
    private static org.apache.log4j.Logger logger =
        org.apache.log4j.Logger.getLogger( RuleEngineRepositoryConfiguratorImpl.class );

    /**
     * Resource path of the default repository configuration file.
     */
    public static final String DEFAULT_REPOSITORY_XML = "repository.xml";

    /**
     * Creates and gets a JCR repository.
     * 
     * @see org.kuali.student.brms.repository.drools.RuleEngineRepositoryConfigurator#getJCRRepository(java.net.URL)
     */
    public Repository getJCRRepository( final URL repoConfigLocation ) {
        try {
            if (repoConfigLocation == null ) {
                throw new RulesRepositoryException( 
                        "Unable to create a Repository instance. " +
                		"Repository configuration location is null." );
            } else { 
                URL configFile = new URL( repoConfigLocation + "/" + DEFAULT_REPOSITORY_XML );
                return new PrivateTransientRepository(configFile, repoConfigLocation);
            }
        } catch ( IOException e ) {
            throw new RulesRepositoryException("Unable to create a Repository instance.", e);
        }
    }
    
    /**
     * Creates and gets a JCR repository.
     * 
     * @see org.kuali.student.brms.repository.drools.RuleEngineRepositoryConfigurator#getJCRRepository(java.net.URL, java.net.URL)
     */
    public Repository getJCRRepository( final URL repoConfigLocation, final URL repoLocation ) {
        try {
            if (repoConfigLocation == null || repoConfigLocation == null ) {
                return new TransientRepository();
            } else { 
                return new PrivateTransientRepository(repoConfigLocation, repoLocation);
            }
        } catch ( IOException e ) {
            throw new RulesRepositoryException("Unable to create a Repository instance.", e);
        }
    }

    private class PrivateTransientRepository extends TransientRepository {
        /**
         * Constructor.
         * 
         * @throws IOException
         */
        public PrivateTransientRepository() throws IOException {
            super();
        }

        /**
         * Constructor.
         * 
         * @throws IOException
         */
        public PrivateTransientRepository( URL configLocationDir, URL repositoryLocationDir ) throws IOException {
            super( new PrivateRepositoryFactory( configLocationDir, repositoryLocationDir ) );
        }

    }
    
    private class PrivateRepositoryFactory implements RepositoryFactory {

        /**
         * Configuration location for <code>repository.xml</code>.
         */
        private URL configLocation;
        
        /**
         * Repository location
         */
        private URL repositoryLocation;

        /**
         * Constructor.
         * 
         * @param configLocationDir
         */
        public PrivateRepositoryFactory( final URL configLocationDir, final URL repositoryLocationDir ) {
            this.configLocation = configLocationDir;
            this.repositoryLocation = repositoryLocationDir;
        }

        private String getLocation( URL url ) {
            if (url.getProtocol().equalsIgnoreCase("file")) {
                try {
                    File file = new File(url.toURI());
                    return file.getAbsolutePath();
                } catch (URISyntaxException e) {
                    throw new RuleEngineRepositoryException(e);
                }
            } else if (url.getProtocol().equalsIgnoreCase("jar")) {
                return System.getProperty("user.dir");
            } else {
                return url.getPath();
            }
        }
        
        /**
         * Gets a JCR repository.
         * 
         * @see org.apache.jackrabbit.core.TransientRepository$RepositoryFactory#getRepository()
         */
        public RepositoryImpl getRepository() {
            final String repoHome = getLocation( this.repositoryLocation );
            //final String repoConfigFile = this.configLocation + "/" + DEFAULT_REPOSITORY_XML;
            final String repoConfigFile = this.configLocation.toString();
            
            System.out.println("\n**************************************************");
            System.out.println("* configLocation     = "+this.configLocation);
            System.out.println("* repositoryLocation = "+this.repositoryLocation);
            System.out.println("* repoHome           = "+repoHome);
            System.out.println("* repoConfigFile     = "+repoConfigFile);
            System.out.println("**************************************************\n");
            
            try {
                // Make sure that the repository configuration file exists
                if ( this.configLocation.getProtocol().equalsIgnoreCase("file") ) {
                    String configHome = getLocation( this.configLocation );
                    File configFile = new File( configHome );
                    if (!configFile.exists()) {
                        throw new RuleEngineRepositoryException( 
                                "Repository configuration directory does not exist: " + 
                                this.configLocation );
                    }
                }

                // Make sure that the repository home directory exists
                File homeDir = new File( repoHome );
                if (!homeDir.exists()) {
System.out.println("Creating repository home directory " + this.repositoryLocation);
                    homeDir.mkdirs();
                }
                URL configFile = new URL( repoConfigFile ); 
                // Load the configuration and create the repository
                RepositoryConfig rc = RepositoryConfig.create(configFile.toURI(), repoHome);
                return RepositoryImpl.create(rc);
            } catch (MalformedURLException e) {
                throw new RuleEngineRepositoryException(
                        "Invalid repository configuration: " + this.configLocation, e);
            } catch (URISyntaxException e) {
                throw new RuleEngineRepositoryException(
                        "Invalid repository configuration: " + this.configLocation, e);
            } catch (ConfigurationException e) {
                throw new RuleEngineRepositoryException(
                        "Invalid repository configuration: " + this.configLocation, e);
            } catch (RepositoryException e) {
                throw new RuleEngineRepositoryException( 
                        "Creating repository failed: " + this.configLocation, e);
            }
        }
    }
    
}
