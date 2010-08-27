package org.apache.torque.mojo;

import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;

import static org.apache.commons.lang.StringUtils.*;

/**
 * Common logic for running SQL commands on a database
 */
public abstract class AbstractDBACommandMojo extends AbstractSQLExecutorMojo {
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

	/**
	 * A user with DBA privileges on the database
	 * 
	 * @parameter expression="${dbaUsername}"
	 */
	String dbaUsername;

	/**
	 * The password for the DBA user;
	 * 
	 * @parameter expression="${dbaPassword}"
	 */
	String dbaPassword;

	/**
	 * Set this to false if you are allowing DBA commands (eg CREATE database, DROP database) to be issued against your
	 * database without required authentication
	 * 
	 * @parameter expression="${requireDbaCredentials}" default-value="true"
	 */
	boolean requireDbaCredentials;

	protected void updateConfiguration() throws MojoExecutionException {
		super.updateConfiguration();
		if (getProject().getArtifactId().equals(database)) {
			database = getTrimmedArtifactId();
		}
		if (getProject().getArtifactId().equals(databasePassword)) {
			databasePassword = getTrimmedArtifactId();
		}
		if (getProject().getArtifactId().equals(databaseUsername)) {
			databaseUsername = getTrimmedArtifactId();
		}
	}

	@Override
	protected Properties getContextProperties() {
		Properties properties = super.getContextProperties();
		properties.setProperty(DATABASE_PROPERTY, getDatabase());
		properties.setProperty(DATABASE_PW_PROPERTY, getDatabasePassword());
		properties.setProperty(DATABASE_USERNAME_PROPERTY, getDatabaseUsername());
		return properties;
	}

	protected void validateConfiguration() throws MojoExecutionException {
		super.validateConfiguration();
		if (isEmpty(database)) {
			throw new MojoExecutionException("\n\nNo database was specified.\nSpecify a database in the plugin configuration or as a system property.\n\nFor example:\n-Ddatabase=MYDB\n\n.");
		}
	}

	protected void validateCredentials() throws MojoExecutionException {
		if (!requireDbaCredentials) {
			return;
		}
		if (isEmpty(dbaUsername) || isEmpty(dbaPassword)) {
			throw new MojoExecutionException("\n\nUsername and password for a DBA user must be specified.\nSpecify them in the plugin configuration or as a system property.\n\nFor example:\n-DdbaUsername=dbauser\n-DdbaPassword=dbapassword\n\n.");
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

	public String getDatabaseUsername() {
		return databaseUsername;
	}

	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}

	public String getDbaUsername() {
		return dbaUsername;
	}

	public void setDbaUsername(String dbaUsername) {
		this.dbaUsername = dbaUsername;
	}

	public String getDbaPassword() {
		return dbaPassword;
	}

	public void setDbaPassword(String dbaPassword) {
		this.dbaPassword = dbaPassword;
	}

	public boolean isRequireDbaCredentials() {
		return requireDbaCredentials;
	}

	public void setRequireDbaCredentials(boolean requireDbaCredentials) {
		this.requireDbaCredentials = requireDbaCredentials;
	}
}
