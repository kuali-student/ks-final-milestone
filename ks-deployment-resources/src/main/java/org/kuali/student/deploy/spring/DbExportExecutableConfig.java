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

import org.kuali.common.impex.spring.DumpDatabaseConfig;
import org.kuali.common.util.execute.Executable;
import org.kuali.common.util.execute.ExecutablesExecutable;
import org.kuali.common.util.spring.ExecutableConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ DumpDatabaseConfig.class, SyncFilesConfig.class })
public class DbExportExecutableConfig extends ExecutableConfig {

	@Autowired
	SyncFilesConfig syncFilesConfig;

	@Autowired
	DumpDatabaseConfig databaseExportConfig;

	@Override
	protected Executable getExecutable() {

		// Add the executables that will get run (in the correct order)
		List<Executable> executables = new ArrayList<Executable>();

		// First export the schema + MPX files all to one directory
		executables.add(databaseExportConfig.dumpDatabaseExecutable());

		// Then split them up as needed into the various sub directories
		executables.add(syncFilesConfig.syncFilesExecutable());

		// Setup an executable that will execute things in the right order
		return new ExecutablesExecutable(executables);
	}

}
