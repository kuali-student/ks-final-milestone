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

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.kuali.common.util.CollectionUtils;
import org.kuali.common.util.MetaInfContext;
import org.kuali.common.util.MetaInfUtils;
import org.kuali.common.util.spring.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DataMetaInfConfig {

	// private static final Logger logger = LoggerFactory.getLogger(DataMetaInfConfig.class);

	@Autowired
	Environment env;

	@Bean
	public Object scanAndCreateFiles() {
		// Extract the CSV include patterns and convert to a list
		String csv = SpringUtils.getProperty(env, "impex.metainf.includes", "**/${project.groupId.path}/${project.artifactId}/*.mpx");
		List<String> includes = CollectionUtils.getTrimmedListFromCSV(csv);

		// This is the base directory to scan
		File baseDir = new File(SpringUtils.getProperty(env, "project.build.outputDirectory"));

		// Output file contains one line of text for each file that gets located
		// Each line is an entry similar to this "classpath:MYCONTENT.mpx"
		File outputFile = new File(SpringUtils.getProperty(env, "impex.metainf.outputFile"));

		// Setup the context
		MetaInfContext context = new MetaInfContext();
		context.setBaseDir(baseDir);
		context.setOutputFile(outputFile);
		context.setIncludes(includes);

		try {
			// Invoke MetaInfUtils to create the resource listings
			MetaInfUtils.scanAndCreateFiles(Arrays.asList(context));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return null;
	}

}
