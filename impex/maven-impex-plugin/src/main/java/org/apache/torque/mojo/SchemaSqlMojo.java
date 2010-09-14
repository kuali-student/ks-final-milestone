package org.apache.torque.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.util.ChangeDetector;

/**
 * Generates platform specific SQL from database agnostic XML files.<br>
 * <br>
 * There are two types of SQL files created by this goal:<br>
 * <br>
 * Type 1: DDL statements for creating tables, primary keys, indexes, and unique constraints. Does not contain DDL for
 * enforcing relationships between tables.<br>
 * Type 2: DDL statements for creating and enforcing relationships between tables<br>
 * <br>
 * This allows data to be imported into multiple tables concurrently. Running the first type of SQL file will create the
 * empty tables without any foreign key constraints. Data can then be loaded concurrently into the tables (using
 * optimized high speed tools if desired) without needing to worry about the order in which the tables are loaded. After
 * data has been loaded, the second type of SQL file can be run to add the relationships between the tables. As long as
 * the data set is consistent and correct, all the relationships will get created correctly.<br>
 * <br>
 * The database platform to generate SQL for is determined by ${targetDatabase}. See also <code>impex:datasql</code>
 * 
 * @goal schemasql
 * @phase generate-sources
 */
public class SchemaSqlMojo extends SqlMojoBase {

	/**
	 * The directory in which the SQL will be generated.
	 * 
	 * @parameter property="outputDir" expression="${outputDir}" default-value="${project.build.directory}/classes/sql"
	 */
	@SuppressWarnings("unused")
	private String dummy1;

	/**
	 * The location where the report file will be generated.
	 * 
	 * @parameter property="reportFile" expression="${reportFile}" default-value=
	 *            "../../../reports/report.${project.artifactId}.sql.generation"
	 */
	@SuppressWarnings("unused")
	private String dummy2;

	/**
	 * The location where the context property file for velocity will be generated.
	 * 
	 * @parameter property="contextPropertiesPath" expression="${contextPropertiesPath}"
	 *            default-value="${project.build.directory}/reports/context.sql.properties"
	 */
	@SuppressWarnings("unused")
	private String dummy3;

	/**
	 * The suffix of the generated sql files.
	 * 
	 * @parameter property="suffix" expression="${suffix}"
	 */
	@SuppressWarnings("unused")
	private String dummy4;

	/**
	 * Generate SQL from schema XML files
	 */
	public void executeMojo() throws MojoExecutionException {
		updateConfiguration();
		validateConfiguration();
		generateContextProperties();
		configureTask();
		addTargetDatabaseToOutputDir();
		addTargetDatabaseToReportFile();
		ChangeDetector detector = new ChangeDetector(getCanonicalReportFile(), getSchemaFiles());
		if (!detector.isChanged() && isRunOnlyOnSchemaChange()) {
			getLog().info("Schema has not changed.  Skipping generation");
			return;
		}
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Generating SQL for " + getTargetDatabase() + " from schema XML files");
		getLog().info("------------------------------------------------------------------------");
		getAntTask().execute();
	}
}
