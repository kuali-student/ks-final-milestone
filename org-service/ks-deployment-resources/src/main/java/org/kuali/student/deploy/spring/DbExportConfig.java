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
import java.util.ArrayList;
import java.util.List;

import org.kuali.common.impex.spring.DatabaseExportConfig;
import org.kuali.common.util.CollectionUtils;
import org.kuali.common.util.LocationUtils;
import org.kuali.common.util.SimpleScanner;
import org.kuali.common.util.SyncRequest;
import org.kuali.common.util.execute.SyncFilesExecutable;
import org.kuali.common.util.service.ScmService;
import org.kuali.common.util.spring.ScmServiceFactoryBean;
import org.kuali.common.util.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Configuration
@Import(DatabaseExportConfig.class)
public class DbExportConfig {

	private static final Logger logger = LoggerFactory.getLogger(DbExportConfig.class);

	protected final static String SYNC_SKIP_KEY = "impex.sync.skip";

	protected final static String SCM_COMMIT_KEY = "impex.scm.commit";

	protected final static String SYNC_COMMIT_MESSAGE_KEY = "impex.scm.message";

	protected final static String SYNC_COMMIT_MESSAGE_DEFAULT = "Automated Impex update";

	protected final static String SYNC_DEFINITIONS_PREFIX_KEY = "impex.sync.prefixes";

	protected final static String SYNC_COMMIT_PATH_KEY = "sync.commitPath";

	protected final static String SYNC_REQUEST_SOURCE_DIR_KEY = "sync.sourceDir";

	protected final static String SYNC_REQUEST_DESTINATION_DIR_KEY = "sync.destDir";

	protected final static String SYNC_REQUEST_FILTER_EXPRESSIONS_KEY = "sync.filter";

	/**
	 * Default behavior is to not skip execution,
	 */
	protected final static Boolean SYNC_SKIP_DEFAULT = Boolean.FALSE;

	/**
	 * default behavior is to not commit to scm
	 */
	protected final static Boolean SCM_COMMIT_DEFAULT = Boolean.FALSE;

	/**
	 * default behavior is to sync all files in a sync request
	 */
	protected final static String SYNC_REQUEST_FILTER_EXPRESSIONS_DEFAULT = ".*";

	@Autowired
	Environment env;

	@Autowired
	DatabaseExportConfig dbExportConfig;

	public void doDatabaseExport() {
		dbExportConfig.exportDatabaseExecutable().execute();
	}

	@Bean(initMethod = "execute")
	public SyncFilesExecutable syncFilesExecutable() {

		// run the database export executable first
		doDatabaseExport();

		SyncFilesExecutable exec = new SyncFilesExecutable();
		exec.setService(scmService());
		exec.setSkip(SpringUtils.getBoolean(env, SYNC_SKIP_KEY, SYNC_SKIP_DEFAULT));
		exec.setCommitChanges(SpringUtils.getBoolean(env, SCM_COMMIT_KEY, SCM_COMMIT_DEFAULT));
		exec.setMessage(SpringUtils.getProperty(env, SYNC_COMMIT_MESSAGE_KEY, SYNC_COMMIT_MESSAGE_DEFAULT));

		List<String> prefixes = CollectionUtils.getTrimmedListFromCSV(SpringUtils.getProperty(env, SYNC_DEFINITIONS_PREFIX_KEY));

		List<File> commitPaths = new ArrayList<File>();

		List<SyncRequest> requests = new ArrayList<SyncRequest>();

		// for each prefix we find, find properties with that prefix in the key
		// to build a SyncRequest, and/or a File from a location
		for (String prefix : prefixes) {
			String commitLocation = SpringUtils.getProperty(env, prefix + SYNC_COMMIT_PATH_KEY, "");

			String sourceLocation = SpringUtils.getProperty(env, prefix + SYNC_REQUEST_SOURCE_DIR_KEY, "");
			String destinationLocation = SpringUtils.getProperty(env, prefix + SYNC_REQUEST_DESTINATION_DIR_KEY, "");

			if (StringUtils.hasText(commitLocation)) {
				// if a commit path is defined for this prefix, add the file to the list of commit paths
				commitPaths.add(LocationUtils.getFileQuietly(commitLocation));
			}

			// sourceLocation and destinationLocation must either both have a value, or both be null
			if (StringUtils.hasText(sourceLocation)) {
				Assert.hasText(destinationLocation);

				// build a file object for the source directory
				// this directory will be scanned with the filename filter
				File sourceDir = LocationUtils.getFileQuietly(sourceLocation);

				// get the filename filter regex for this sync request
				String filterExpressions = SpringUtils.getProperty(env, prefix + SYNC_REQUEST_FILTER_EXPRESSIONS_KEY, SYNC_REQUEST_FILTER_EXPRESSIONS_DEFAULT);

				logger.info("Building scanner for dir " + sourceDir + " and expressions: " + filterExpressions);

				List<String> expressionList = CollectionUtils.getTrimmedListFromCSV(filterExpressions);

				// build a scanner with no excludes (3rd argument)
				SimpleScanner scanner = new SimpleScanner(sourceDir, expressionList, null);

				SyncRequest s = new SyncRequest();
				s.setSrcDir(sourceDir);
				s.setDstDir(LocationUtils.getFileQuietly(destinationLocation));
				s.setSrcFiles(scanner.getFiles());

				logger.info("Source dir scanner found " + s.getSrcFiles().size() + " files.");

				requests.add(s);
			}
		}

		exec.setCommitPaths(commitPaths);
		exec.setRequests(requests);

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
