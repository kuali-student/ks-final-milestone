package org.apache.torque.mojo;

import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.settings.Server;
import org.kuali.db.jdbc.DatabaseCommand;

import static org.apache.commons.lang.StringUtils.*;

/**
 * Common logic for running SQL commands on a database
 */
public abstract class AbstractDBACommandMojo extends AbstractSQLExecutorMojo {
	public static final String DATABASE_PROPERTY = "database";
	public static final String DATABASE_PW_PROPERTY = "databasePassword";
	public static final String DATABASE_USERNAME_PROPERTY = "databaseUsername";

	/**
	 * URL to connect directly to the database server itself (ie: no database specified). This is optional as it is
	 * automatically detected in almost all cases from the <code>url</code>. If <code>serverUrl</code> is explicitly
	 * supplied it overrides the <code>serverUrl</code> chosen by the automatic detection logic.
	 * 
	 * @parameter expression="${serverUrl}"
	 */
	String serverUrl;

	/**
	 * The name of the database to DROP/CREATE. If not specified, this defaults to a database name that is compatible
	 * with ${targetDatabase} based on platform specific logic that converts the artifact id.<br>
	 * <br>
	 * 
	 * For example:<br>
	 * ks-embedded-db is converted to KSEMBEDDED for Oracle, and ksembedded for MySQL)
	 * 
	 * @parameter expression="${database}"
	 */
	String database;

	/**
	 * The user to DROP/CREATE when issuing DBA commands for creating/dropping a user. If not specified, this defaults
	 * to a user that is compatible with ${targetDatabase} based on platform specific logic that converts the artifact
	 * id.<br>
	 * <br>
	 * 
	 * For example:<br>
	 * ks-embedded-db is converted to KSEMBEDDED for Oracle, and ksembedded for MySQL
	 * 
	 * @parameter expression="${databaseUser}"
	 */
	String databaseUser;

	/**
	 * The password for the user that is DROPPED/CREATED. If not specified, this defaults to a password that is
	 * compatible with ${targetDatabase} based on platform specific logic that converts the artifact id.<br>
	 * <br>
	 * 
	 * For example:<br>
	 * ks-embedded-db is converted to KSEMBEDDED for Oracle, and ksembedded for MySQL
	 * 
	 * @parameter expression="${databasePassword}"
	 */
	String databasePassword;

	/**
	 * Lookup DBA credentials in settings.xml using this key. If nothing is found under
	 * <code>impex.dba.${project.artifactId}</code> a second attempt will be made to locate a set of credentials under
	 * <code>impex.dba.${url}</code>
	 * 
	 * @parameter expression="${dbaSettingsKey}" default-value="impex.dba.${project.artifactId}"
	 */
	String dbaSettingsKey;

	protected String getTransactionDescription(DatabaseCommand command) {
		return command + " " + getDatabase();
	}

	protected void updateConfiguration() throws MojoExecutionException {
		super.updateConfiguration();
		if (isEmpty(database)) {
			database = platform.getSchemaName(getProject().getArtifactId());
		}
		if (isEmpty(databasePassword)) {
			databasePassword = platform.getSchemaName(getProject().getArtifactId());
		}
		if (isEmpty(databaseUser)) {
			databaseUser = platform.getSchemaName(getProject().getArtifactId());
		}
		if (isEmpty(serverUrl)) {
			serverUrl = platform.getServerUrl(url);
		}
	}

	@Override
	protected String getUpdatedPassword(Server server, String password) {
		// They already gave us a password, don't mess with it
		if (!isEmpty(password)) {
			return password;
		}
		if (server != null) {
			// We've successfully located a server in settings.xml, use the password from that
			getLog().info("Located a password in settings.xml under the server id '" + server.getId() + "' Password: " + getDisplayPassword(server.getPassword()));
			return server.getPassword();
		}
		// Do not return a default value
		return null;
	}

	@Override
	protected String getUpdatedUsername(Server server, String username) {
		// They already gave us a username, don't mess with it
		if (!isEmpty(username)) {
			return username;
		}
		if (server != null) {
			// We've successfully located a server in settings.xml, use the username from that
			getLog().info("Located a username in settings.xml under the server id '" + server.getId() + "' Username: " + server.getUsername());
			return server.getUsername();
		}
		// Do not return a default value
		return null;
	}

	@Override
	protected Properties getContextProperties() {
		Properties properties = super.getContextProperties();
		properties.setProperty(DATABASE_PROPERTY, getDatabase());
		properties.setProperty(DATABASE_PW_PROPERTY, getDatabasePassword());
		properties.setProperty(DATABASE_USERNAME_PROPERTY, getDatabaseUser());
		return properties;
	}

	@Override
	protected Server getServerFromSettingsKey() {
		Server server = getSettings().getServer(dbaSettingsKey);
		if (server != null) {
			return server;
		}

		String settingsKey = "impex.dba." + getUrl();
		return getSettings().getServer(settingsKey);
	}

	@Override
	protected void validateConfiguration() throws MojoExecutionException {
		super.validateConfiguration();
		if (isEmpty(database)) {
			throw new MojoExecutionException("\n\nNo database was specified.\nSpecify a database in the plugin configuration or as a system property.\n\nFor example:\n-Ddatabase=MYDB\n\n.");
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

	public String getDatabaseUser() {
		return databaseUser;
	}

	public void setDatabaseUser(String databaseUsername) {
		this.databaseUser = databaseUsername;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getDbaSettingsKey() {
		return dbaSettingsKey;
	}

	public void setDbaSettingsKey(String dbaSettingsKey) {
		this.dbaSettingsKey = dbaSettingsKey;
	}
}
