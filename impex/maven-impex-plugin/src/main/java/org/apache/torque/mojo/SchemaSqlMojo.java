package org.apache.torque.mojo;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.db.DatabaseType;

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
	 * @parameter property="outputDir" expression="${outputDir}"
	 *            default-value="${project.build.directory}/generated-sql/sql"
	 */
	@SuppressWarnings("unused")
	private String dummy1;

	/**
	 * The location where the report file will be generated.
	 * 
	 * @parameter property="reportFile" expression="${reportFile}" default-value=
	 *            "../../../impex/report.${project.artifact.artifactId}.sql.generation"
	 */
	@SuppressWarnings("unused")
	private String dummy2;

	/**
	 * The location where the context property file for velocity will be generated.
	 * 
	 * @parameter property="contextPropertiesPath" expression="${contextPropertiesPath}"
	 *            default-value="${project.build.directory}/impex/context.sql.properties"
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
	 * Validate that some essential configuration items are present
	 */
	protected void validateConfiguration() throws MojoExecutionException {
		if (StringUtils.isEmpty(getTargetDatabase())) {
			throw new MojoExecutionException("Database type of '" + getTargetDatabase() + "' is invalid.  Valid values: " + org.springframework.util.StringUtils.arrayToCommaDelimitedString(DatabaseType.values()));
		}

		try {
			DatabaseType.valueOf(getTargetDatabase().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new MojoExecutionException("Database type of '" + getTargetDatabase() + "' is invalid.  Valid values: " + org.springframework.util.StringUtils.arrayToCommaDelimitedString(DatabaseType.values()));
		}
	}

	/**
	 * Generate SQL from schema XML files
	 */
	public void execute() throws MojoExecutionException {
		if (skipMojo()) {
			return;
		}
		validateConfiguration();
		addTargetDatabaseToOutputDir();
		addTargetDatabaseToReportFile();
		if (!isSchemaChanged() && isRunOnlyOnSchemaChange()) {
			getLog().info("Schema has not changed.  Skipping generation");
			return;
		}
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Generating SQL for " + getTargetDatabase() + " from schema XML files");
		getLog().info("------------------------------------------------------------------------");
		super.execute();
	}
}
