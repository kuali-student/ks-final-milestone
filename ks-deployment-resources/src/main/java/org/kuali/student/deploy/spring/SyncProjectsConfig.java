/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.util.Project;
import org.kuali.common.util.ProjectUtils;
import org.kuali.common.util.execute.SyncFilesExecutable;
import org.kuali.common.util.service.ScmService;
import org.kuali.common.util.spring.ScmServiceFactoryBean;
import org.kuali.common.util.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SyncProjectsConfig {

	private static final Logger logger = LoggerFactory.getLogger(SyncProjectsConfig.class);

	protected final static String SYNC_PROJECTS_KEY = "impex.sync.projects";
	protected final static String SYNC_SOURCE_DIR_KEY = "impex.sync.sourceDir";

	@Autowired
	Environment env;

	@Bean
	public SyncFilesExecutable syncFilesExecutable() {

		List<String> gavs = SpringUtils.getListFromCSV(env, SYNC_PROJECTS_KEY);
		List<Project> projects = new ArrayList<Project>();
		for (String gav : gavs) {
			Project project = ProjectUtils.loadProject(gav);
			projects.add(project);
			logger.debug(project.getOrgId());
		}

		// Configure the executable
		SyncFilesExecutable exec = new SyncFilesExecutable();
		exec.setService(scmService());
		exec.setSkip(SpringUtils.getBoolean(env, "impex.sync.skip", false));
		exec.setCommitChanges(SpringUtils.getBoolean(env, "impex.scm.commit", false));
		exec.setMessage(SpringUtils.getProperty(env, "impex.scm.message", "Automated Impex update"));
		// exec.setCommitPaths(commitPaths);
		// exec.setRequests(requests);
		return exec;
	}

	@Bean
	public ScmService scmService() {
		String url = SpringUtils.getProperty(env, "project.scm.developerConnection");
		ScmServiceFactoryBean factory = new ScmServiceFactoryBean();
		factory.setUrl(url);
		return factory.getObject();
	}

}
