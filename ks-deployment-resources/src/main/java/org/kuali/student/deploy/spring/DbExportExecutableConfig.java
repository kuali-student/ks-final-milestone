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

import org.kuali.common.impex.database.DumpDatabaseConfig;
import org.kuali.common.impex.spring.ProjectStagingConfig;
import org.kuali.common.util.execute.Executable;
import org.kuali.common.util.execute.ExecutablesExecutable;
import org.kuali.common.util.spring.BuildUpdateScmConfig;
import org.kuali.common.util.spring.ExecutableConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ DumpDatabaseConfig.class, ProjectStagingConfig.class, BuildUpdateScmConfig.class })
public class DbExportExecutableConfig extends ExecutableConfig {

	@Autowired
	DumpDatabaseConfig dumpDatabaseConfig;

	@Autowired
	ProjectStagingConfig projectStagingConfig;

	@Autowired
	BuildUpdateScmConfig buildUpdateScmConfig;

	@Override
	protected Executable getExecutable() {

		// Add the executables that will get run (in the correct order)
		List<Executable> executables = new ArrayList<Executable>();

		// First export the schema + MPX files all to one directory
		executables.add(dumpDatabaseConfig.dumpDatabaseExecutable());

		// Create a staging directory for each project
		// The staging directory must be contain the exact set of files that get checked into SCM
		// When the staging directories are synchronized with the directories under SCM, any files present in the SCM
		// directories that are not also present in the staging directories are deleted.
		executables.add(projectStagingConfig.projectStagingExecutable());

		// Connect to the SCM system and add/update/delete files as needed
		executables.add(buildUpdateScmConfig.buildScmExecutable());

		// Setup an executable that will execute things in the right order
		return new ExecutablesExecutable(executables);
	}

}
