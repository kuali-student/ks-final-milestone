package org.kuali.student.brms.repository;

import org.kuali.student.brms.repository.DroolsJackrabbitRepository;

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

public class DroolsJackrabbitRepository {

	private static Repository repository = null;
	
	private static JCRRepositoryConfigurator repoConfig;

	private static Session repositorySession = null;

	public static void setupRepository() throws Exception
	{
        //if ( repository == null )
        {
            File repoDir = new File( "repository" );
            System.out.println("DELETE test repository directory: " + repoDir.getAbsolutePath());
            RepositorySessionUtil.deleteDir( repoDir );
            System.out.println("TEST repository directory deleted.");

    		//SimpleCredentials credentials = new SimpleCredentials( "lcarlsen", "password".toCharArray() );
            repoConfig = new JackrabbitRepositoryConfigurator();
	        repository = repoConfig.getJCRRepository( null );
	        repositorySession = repository.login( getCredentials() );

	        clearRepository();
        }
	}

	private static SimpleCredentials getCredentials()
	{
		return new SimpleCredentials( "lcarlsen", "password".toCharArray() );
	}
	
	public static void clearRepository()
	{
        RulesRepositoryAdministrator repoAdmin = new RulesRepositoryAdministrator( repositorySession );

        if ( repoAdmin.isRepositoryInitialized() ) 
        {
        	repoAdmin.clearRulesRepository();
        }
        repoConfig.setupRulesRepository( repositorySession );
	}

	public static RulesRepository getRepository() throws Exception
	{
		RulesRepository ruleRepository = new RulesRepository( repositorySession );   
        return ruleRepository;
	}
	
	public static void shutdownRepository() throws Exception
	{
		JackrabbitRepository repo = (JackrabbitRepository) repository;
		repo.shutdown();
	}
	
	public static void logout() throws Exception
	{
		repositorySession.logout();
	}

	public static void login() throws Exception
	{
    	repositorySession = repository.login( getCredentials() );
	}
}
