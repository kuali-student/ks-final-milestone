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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.kuali.common.impex.spring.DataExportConfig;
import org.kuali.common.impex.spring.ModularSchemaExportConfig;
import org.kuali.common.impex.spring.SchemaExtractionConfig;
import org.kuali.common.jdbc.spring.JdbcMavenPropertySourceConfig;
import org.kuali.common.util.ProjectContext;
import org.kuali.common.util.property.ProjectProperties;
import org.kuali.common.util.property.PropertiesContext;
import org.kuali.common.util.spring.ConfigUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExportPropertyConfig extends JdbcMavenPropertySourceConfig {

	protected static final String APP_PREFIX = "student.export.app.";

	protected static final String APP_ARTIFACT_ID = "ks-impex-app-db";

	protected static final String TABLE_NAME_WILDCARD = ".*";

	protected static final String FILE_NAME_WILDCARD = "*";

	protected static final String APP_INCLUDE = "KS";

	protected static final String RICE_PREFIX = "student.export.rice.";

	protected static final String RICE_ARTIFACT_ID = "ks-impex-rice-db";

	protected static final String RICE_INCLUDE = "KR";

	protected static final String DATA_INCLUDE_CSV = APP_INCLUDE + TABLE_NAME_WILDCARD + "," + RICE_INCLUDE + TABLE_NAME_WILDCARD;

	protected static final String BUNDLED_PREFIX = "student.export.bundled.";

	protected static final String BUNDLED_ARTIFACT_ID = "ks-impex-bundled-db";

	protected static final String SCHEMA_PROPERTY_PREFIXES = APP_PREFIX + "," + RICE_PREFIX + "," + BUNDLED_PREFIX;

	protected static final String SOURCE_DB_TABLE_STATISTICS_LOCATION = "${project.basedir}/../../ks-deployment-resources/src/main/resources/org/kuali/student/ks-source-db/tablestats.properties";

	protected static final String SCHEMA_OUTPUT_LOCATION_SUFFIX = "-schema.xml";

	protected static final String CONSTRAINT_SCHEMA_OUTPUT_LOCATION_SUFFIX = "-constraints" + SCHEMA_OUTPUT_LOCATION_SUFFIX;

	protected static final String FS = File.separator;

	protected static final String RESOURCES_PATH = "src" + FS + "main" + FS + "resources" + FS + "${project.groupId.path}";

	protected static final String SYNC_GLOBAL_PREFIX = "student.export.sync.global.";

	protected static final String SYNC_PROPERTY_PREFIXES = SCHEMA_PROPERTY_PREFIXES + "," + SYNC_GLOBAL_PREFIX;

	protected static final String DATA_FILE_EXTENSION = ".mpx";

	protected static final String OUTPUT_LOCATION = "${project.build.directory}/impex/";

	@Override
	protected List<ProjectProperties> getOtherProjectProperties() {
		Properties props = new Properties();

		// add properties for the prefixes
		props.put(ModularSchemaExportConfig.PROPERTY_PREFIXES_KEY, SCHEMA_PROPERTY_PREFIXES);
		props.put(DbExportConfig.SYNC_DEFINITIONS_PREFIX_KEY, SYNC_PROPERTY_PREFIXES);

		// schema export properties
		populateSchemaProps(props, APP_PREFIX, Collections.singleton(APP_INCLUDE), APP_ARTIFACT_ID);
		populateSchemaProps(props, RICE_PREFIX, Collections.singleton(RICE_INCLUDE), RICE_ARTIFACT_ID);
		populateSchemaProps(props, BUNDLED_PREFIX, Arrays.asList(APP_INCLUDE, RICE_INCLUDE), BUNDLED_ARTIFACT_ID);

		// data export properties
		props.put(DataExportConfig.STATISTICS_LOCATION_KEY, SOURCE_DB_TABLE_STATISTICS_LOCATION);
		props.put(DataExportConfig.TABLE_NAME_INCLUDE_KEY, DATA_INCLUDE_CSV);
		props.put(DataExportConfig.WORKING_DIR_KEY, OUTPUT_LOCATION);

		// do not populate data properties for KS-BUNDLED, since it gets all data files from APP and RICE

		// add a sync commit path entry for the database statistics file
		props.put(SYNC_GLOBAL_PREFIX + DbExportConfig.SYNC_COMMIT_PATH_KEY, SOURCE_DB_TABLE_STATISTICS_LOCATION);

		// file sync properties for each artifact
		populateSyncProps(props, APP_PREFIX, APP_INCLUDE, APP_ARTIFACT_ID);
		populateSyncProps(props, RICE_PREFIX, RICE_INCLUDE, RICE_ARTIFACT_ID);
		populateSyncProps(props, BUNDLED_PREFIX, null, BUNDLED_ARTIFACT_ID);

		ProjectProperties exportProjectProperties = new ProjectProperties(getProjectProperties().getProject(), new PropertiesContext(props));

		ProjectContext dump = new DumpDbProjectContext();

		List<ProjectProperties> results = super.getOtherProjectProperties();
		results.add(ConfigUtils.getProjectProperties(dump));
		results.add(exportProjectProperties);
		return results;
	}

	protected void populateSchemaProps(Properties props, String prefix, Collection<String> includes, String artifactId) {
		// add properties for schema export
		StringBuilder includeBuilder = new StringBuilder();
		boolean first = true;
		for (String include : includes) {
			if (first) {
				first = false;
			} else {
				includeBuilder.append(",");
			}
			includeBuilder.append(include).append(TABLE_NAME_WILDCARD);
		}

		props.put(prefix + SchemaExtractionConfig.NAME_INCLUDES_KEY, includeBuilder.toString());

		// build the schema output location
		StringBuilder schemaSb = new StringBuilder(OUTPUT_LOCATION);
		schemaSb.append(artifactId).append(SCHEMA_OUTPUT_LOCATION_SUFFIX);
		props.put(prefix + ModularSchemaExportConfig.OUTPUT_LOCATION_KEY, schemaSb.toString());

		// build the constraint output location
		StringBuilder constraintSb = new StringBuilder(OUTPUT_LOCATION);
		constraintSb.append(artifactId).append(CONSTRAINT_SCHEMA_OUTPUT_LOCATION_SUFFIX);
		props.put(prefix + ModularSchemaExportConfig.FOREIGN_KEY_OUTPUT_LOCATION_KEY, constraintSb.toString());

	}

	protected void populateSyncProps(Properties props, String prefix, String dataFileInclude, String artifactId) {
		// Create the expression to match the file names for the schema matching the artifact id
		StringBuilder expressionsBuilder = new StringBuilder();
		expressionsBuilder.append(artifactId).append(FILE_NAME_WILDCARD).append(SCHEMA_OUTPUT_LOCATION_SUFFIX);

		// If an include to match data files is defined, append an expression to match data files
		if (dataFileInclude != null) {
			expressionsBuilder.append(",");
			expressionsBuilder.append(dataFileInclude).append(FILE_NAME_WILDCARD).append(DATA_FILE_EXTENSION);
		}

		String syncTargetDir = getTargetDir(artifactId);

		// add a commit path entry for the target dir
		props.put(prefix + DbExportConfig.SYNC_COMMIT_PATH_KEY, syncTargetDir);

		// create properties to define a sync request to copy files matching the expression created above from the source dir to the target dir
		// and create svn add/delete requests for files matching the pattern in the target dir
		props.put(prefix + DbExportConfig.SYNC_REQUEST_SOURCE_DIR_KEY, OUTPUT_LOCATION);
		props.put(prefix + DbExportConfig.SYNC_REQUEST_DESTINATION_DIR_KEY, syncTargetDir);
		props.put(prefix + DbExportConfig.SYNC_REQUEST_FILTER_EXPRESSIONS_KEY, expressionsBuilder.toString());
	}

	protected String getTargetDir(String artifactId) {
		StringBuilder sb = new StringBuilder();
		sb.append("${project.basedir}");
		sb.append(FS);
		sb.append(artifactId);
		sb.append(FS);
		sb.append(RESOURCES_PATH);
		sb.append(FS);
		sb.append(artifactId);
		sb.append(FS);

		return sb.toString();
	}
}
