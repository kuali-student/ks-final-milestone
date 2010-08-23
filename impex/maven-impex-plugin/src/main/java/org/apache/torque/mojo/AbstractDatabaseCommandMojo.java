package org.apache.torque.mojo;

import org.apache.maven.plugin.MojoExecutionException;

import static org.apache.commons.lang.StringUtils.*;

/**
 * Common logic for running SQL commands on a database
 */
public abstract class AbstractDatabaseCommandMojo extends AbstractSQLExecutorMojo {
	public static final String DATABASE_PROPERTY = "database";
	public static final String DATABASE_PW_PROPERTY = "databasePassword";
	public static final String DATABASE_USERNAME_PROPERTY = "databaseUsername";

	/**
	 * The name of the database
	 * 
	 * @parameter expression="${database}" default-value="${project.artifactId}"
	 * @required
	 */
	String database;

	/**
	 * The username to use when accessing this database
	 * 
	 * @parameter expression="${databaseUsername}" default-value="${project.artifactId}"
	 */
	String databaseUsername;

	/**
	 * The password to use when accessing this database
	 * 
	 * @parameter expression="${databasePassword}" default-value="${project.artifactId}"
	 */
	String databasePassword;

	protected void updateConfiguration() throws MojoExecutionException {
		super.updateConfiguration();
		if (project.getArtifactId().equals(database)) {
			database = getTrimmedArtifactId();
		}
		if (project.getArtifactId().equals(databasePassword)) {
			databasePassword = getTrimmedArtifactId();
		}
	}

	protected void validateConfiguration() throws MojoExecutionException {
		super.validateConfiguration();
		if (isEmpty(database)) {
			throw new MojoExecutionException("Must specify a database.");
		}

		if (isEmpty(username) || isEmpty(password)) {
			throw new MojoExecutionException("username and password must be specified.");
		}
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String schema) {
		this.database = schema;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}
}
