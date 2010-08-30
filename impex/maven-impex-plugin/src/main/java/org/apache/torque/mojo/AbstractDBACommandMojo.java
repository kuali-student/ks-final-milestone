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
	 * The password for the DBA user
	 * 
	 * @parameter expression="${dbaPassword}"
	 */
	String dbaPassword;

	/**
	 * Lookup DBA credentials in settings.xml using this key
	 * 
	 * @parameter expression="${dbaSettingsKey}" default-value="impex.${project.artifactId}.dba"
	 */
	String dbaSettingsKey;

	/**
	 * Set this to false if you are allowing DBA commands (eg CREATE database, DROP database) to be issued against your
	 * database anonymously
	 * 
	 * @parameter expression="${requireDbaCredentials}" default-value="true"
	 */
	boolean requireDbaCredentials;

	protected void updateConfiguration() throws MojoExecutionException {
		super.updateConfiguration();
		if (getProject().getArtifactId().equals(database)) {
			database = getConvertedArtifactId();
		}
		if (getProject().getArtifactId().equals(databasePassword)) {
			databasePassword = getConvertedArtifactId();
		}
		if (getProject().getArtifactId().equals(databaseUsername)) {
			databaseUsername = getConvertedArtifactId();
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

	@Override
	protected void validateConfiguration() throws MojoExecutionException {
		super.validateConfiguration();
		if (isEmpty(database)) {
			throw new MojoExecutionException("\n\nNo database was specified.\nSpecify a database in the plugin configuration or as a system property.\n\nFor example:\n-Ddatabase=MYDB\n\n.");
		}
	}

	@Override
	protected Credentials getCredentials() {
		Credentials credentials = new Credentials();
		credentials.setUsername(getDbaUsername());
		credentials.setPassword(getDbaPassword());
		return credentials;
	}

	@Override
	protected void validateCredentials(Credentials credentials) throws MojoExecutionException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n");
		sb.append("Username and password for a DBA user must be specified.\n");
		sb.append("Specify them in the plugin configuration or as a system property.\n");
		sb.append("\n");
		sb.append("For example:\n");
		sb.append("-DdbaUsername=system\n");
		sb.append("-DdbaPassword=systempassword\n");
		sb.append("\n.");
		validateCredentials(credentials, requireDbaCredentials, sb.toString());
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
