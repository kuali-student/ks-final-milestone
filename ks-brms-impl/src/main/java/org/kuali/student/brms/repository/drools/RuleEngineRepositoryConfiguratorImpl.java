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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleEngineRepositoryConfiguratorImpl 
    extends JackrabbitRepositoryConfigurator
    implements RuleEngineRepositoryConfigurator {

    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleEngineRepositoryConfiguratorImpl.class);
    
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
                String config = repoConfigLocation + DEFAULT_REPOSITORY_XML;
                if ( !repoConfigLocation.toString().endsWith( "/" ) ) {
                    config = repoConfigLocation + "/" + DEFAULT_REPOSITORY_XML;
                }
                URL configFile = new URL( config );
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
    public Repository getJCRRepository( final URL repoConfigFile, final URL repoLocation ) {
        try {
            if (repoConfigFile == null || repoConfigFile == null ) {
                return new TransientRepository();
            } else { 
                return new PrivateTransientRepository(repoConfigFile, repoLocation);
            }
        } catch ( IOException e ) {
            throw new RulesRepositoryException("Unable to create a Repository instance.", e);
        }
    }

    private static class PrivateTransientRepository extends TransientRepository {
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
        public PrivateTransientRepository( URL configFile, URL repositoryLocationDir ) throws IOException {
            super( new PrivateRepositoryFactory( configFile, repositoryLocationDir ) );
        }

    }
    
    private static class PrivateRepositoryFactory implements RepositoryFactory {

        /**
         * Configuration location for <code>repository.xml</code>.
         */
        private URL configFile;
        
        /**
         * Repository location
         */
        private URL repositoryLocation;

        /**
         * Constructor.
         * 
         * @param configFile Repository configuration file
         * @param repositoryLocationDir Location of the repository files
         */
        public PrivateRepositoryFactory( final URL configFile, final URL repositoryLocationDir ) {
            this.configFile = configFile;
            this.repositoryLocation = repositoryLocationDir;
        }

        /**
         * Get the repository path. 
         * If it is a jar file then return the <code>user.dir</code>
         * 
         * @param url Repository location URL
         * @return Repository path
         */
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
            final String repoConfigFile = this.configFile.toString();
            
            logger.info("**********************************************************************");
            logger.info("* configLocation     = "+this.configFile);
            logger.info("* repositoryLocation = "+this.repositoryLocation);
            logger.info("* repoHome           = "+repoHome);
            logger.info("* repoConfigFile     = "+repoConfigFile);
            logger.info("**********************************************************************");
            
            try {
                // Make sure that the repository configuration file exists
                if ( this.configFile.getProtocol().equalsIgnoreCase("file") ) {
                    String configHome = getLocation( this.configFile );
                    File configFile = new File( configHome );
                    if (!configFile.exists()) {
                        throw new RuleEngineRepositoryException( 
                                "Repository configuration directory does not exist: " + 
                                this.configFile );
                    }
                }

                // Make sure that the repository home directory exists
                File homeDir = new File( repoHome );
                if (!homeDir.exists()) {
                    logger.info("Creating repository home directory: " + this.repositoryLocation);
                    homeDir.mkdirs();
                }
                URL configFile = new URL( repoConfigFile ); 
                // Load the configuration and create the repository
                RepositoryConfig rc = RepositoryConfig.create(configFile.toURI(), repoHome);
                return RepositoryImpl.create(rc);
            } catch (MalformedURLException e) {
                throw new RuleEngineRepositoryException(
                        "Invalid repository configuration: " + this.configFile, e);
            } catch (URISyntaxException e) {
                throw new RuleEngineRepositoryException(
                        "Invalid repository configuration: " + this.configFile, e);
            } catch (ConfigurationException e) {
                throw new RuleEngineRepositoryException(
                        "Invalid repository configuration: " + this.configFile, e);
            } catch (RepositoryException e) {
                throw new RuleEngineRepositoryException( 
                        "Creating repository failed: " + this.configFile, e);
            }
        }
    }
    
}
