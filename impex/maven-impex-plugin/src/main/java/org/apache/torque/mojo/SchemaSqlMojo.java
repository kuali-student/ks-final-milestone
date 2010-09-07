package org.apache.torque.mojo;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * Generates SQL from schema XML files
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
		generateContextProperties();
		configureTask();
		updateConfiguration();
		validateConfiguration();
		addTargetDatabaseToOutputDir();
		addTargetDatabaseToReportFile();
		ChangeDetector detector = new ChangeDetector(getOutputDir(), getReportFile(), getSchemaFiles(), null);
		if (!detector.isSchemaChanged() && isRunOnlyOnSchemaChange()) {
			getLog().info("Schema has not changed.  Skipping generation");
			return;
		}
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Generating SQL for " + getTargetDatabase() + " from schema XML files");
		getLog().info("------------------------------------------------------------------------");
		getAntTask().execute();
	}
}
