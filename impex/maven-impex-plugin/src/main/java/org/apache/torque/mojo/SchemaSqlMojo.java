package org.apache.torque.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.util.ChangeDetector;

/**
 * Generates platform specific SQL from database agnostic XML files.<br>
 * <br>
 * There are two types of SQL files created by this mojo:<br>
 * <br>
 * 1) DDL statements for creating tables, primary keys, indexes, and unique constraints. Does not contain DDL for
 * enforcing relationships between tables.<br>
 * 2) DDL statements for creating and enforcing relationships between tables<br>
 * <br>
 * This allows data to be imported into multiple tables concurrently. Run the first SQL file to generate the empty
 * tables, then run data imports as needed, and then layer on the relationship constraints. The database platform to
 * generate SQL for is determined by ${targetDatabase}. See also <code>impex:datasql</code>
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
