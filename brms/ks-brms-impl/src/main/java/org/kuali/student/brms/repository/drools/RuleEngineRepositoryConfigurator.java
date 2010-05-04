/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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