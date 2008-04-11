package org.kuali.student.brms.repository;

import org.kuali.student.brms.repository.DroolsJackRabbitRepository;

import java.io.File;

import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.api.JackrabbitRepository;
import org.drools.repository.JCRRepositoryConfigurator;
import org.drools.repository.JackrabbitRepositoryConfigurator;
import org.drools.repository.RepositorySessionUtil;
import org.drools.repository.RulesRepository;
import org.drools.repository.RulesRepositoryAdministrator;

public class DroolsJackRabbitRepository {

	private static Repository repository = null;
	
	private static Session repositorySession = null;

	private static Session getSession() throws Exception
	{
		SimpleCredentials credentials = new SimpleCredentials( "lcarlsen", "password".toCharArray() );
        if ( repository == null )
        {
            File repoDir = new File( "repository" );
            System.out.println("DELETE test repository directory: " + repoDir.getAbsolutePath());
            RepositorySessionUtil.deleteDir( repoDir );
            System.out.println("TEST repository directory deleted.");

            JCRRepositoryConfigurator repoConfig = new JackrabbitRepositoryConfigurator();
	        repository = repoConfig.getJCRRepository( null );
	        repositorySession = repository.login( credentials );

	        clearRepository( repoConfig );
        }
        return repositorySession;
	}

	private static void clearRepository( JCRRepositoryConfigurator repoConfig )
	{
        RulesRepositoryAdministrator repoAdmin = new RulesRepositoryAdministrator( repositorySession );

        if ( repoAdmin.isRepositoryInitialized() ) 
        {
        	repoAdmin.clearRulesRepository();
        }
        repoConfig.setupRulesRepository( repositorySession );
	}

	public static RulesRepository createRepository() throws Exception
	{
        RulesRepository ruleRepository = new RulesRepository( getSession() );   
        return ruleRepository;
	}
	
	public static void shutdown() throws Exception
	{
		getSession().logout();
		JackrabbitRepository repo = (JackrabbitRepository) repository;
		repo.shutdown();
	}
	
}
