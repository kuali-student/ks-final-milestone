package org.kuali.student.brms.repository.drools;

import java.net.URL;

import javax.jcr.Repository;

import org.drools.repository.JCRRepositoryConfigurator;

public interface RuleEngineRepositoryConfigurator extends JCRRepositoryConfigurator {

    /**
     * Gets a JCR (Java Content Repository) repository.
     * 
     * @param repoRootDir Root location of the repository's directories and files
     * @param repoConfigLocation Location of the <code>repository.xml</code> file
     * @return
     */
    public Repository getJCRRepository( URL repoConfigLocation );

    public Repository getJCRRepository( URL repoConfigLocation, URL repoLocation );
}