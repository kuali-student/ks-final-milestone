package org.kuali.student.brms.repository.drools;

import java.net.URL;

import javax.jcr.Repository;

import org.drools.repository.JCRRepositoryConfigurator;

public interface RuleEngineRepositoryConfigurator extends JCRRepositoryConfigurator {

    /**
     * Gets a JCR (Java Content Repository) repository.
     * 
     * @param repoConfigLocation Location of the default <code>repository.xml</code> repository configuration file
     * @return A repository
     */
    public Repository getJCRRepository( URL repoConfigLocation );

    /**
     * Gets a JCR (Java Content Repository) repository.
     * 
     * @param repoConfigFile Repository configuration file
     * @param repoLocation Root location of the repository's directories and files
     * @return A repository
     */
    public Repository getJCRRepository( URL repoConfigFile, URL repoLocation );
}