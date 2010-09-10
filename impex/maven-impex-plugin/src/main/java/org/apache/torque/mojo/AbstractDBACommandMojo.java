package org.apache.torque.mojo;

import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.settings.Server;
import org.kuali.db.ConnectionHandler;
import org.kuali.db.Credentials;
import org.kuali.db.DatabaseCommand;

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
	 * The name of the database to DROP/CREATE. If not specified this defaults to a database name that is compatible
	 * with ${targetDatabase}. There is platform specific logic that will automatically convert the artifact id into a
	 * compatible name.
	 * 
	 * For example:<br>
	 * ks-embedded-db is converted to KSEMBEDDED for Oracle, and ksembedded for MySQL)
	 * 
	 * @parameter expression="${database}"
	 */
	String database;

	/**
	 * The user to DROP/CREATE when issuing DBA commands for creating/dropping a user. If not specified this defaults to
	 * a user based on a conversion of the artifact id.<br>
	 * 
	 * For example:<br>
	 * ks-embedded-db is converted to KSEMBEDDED for Oracle, and ksembedded for MySQL)
	 * 
	 * 
	 * @parameter expression="${databaseUser}"
	 */
	String databaseUser;

	/**
	 * The password for the user to DROP/CREATE when issuing DBA commands for creating/dropping a user. If not specified
	 * this defaults to a password based on a conversion of the artifact id.<br>
	 * 
	 * For example:<br>
	 * ks-embedded-db is converted to KSEMBEDDED for Oracle, and ksembedded for MySQL)
	 * 
	 * @parameter expression="${databasePassword}"
	 */
	String databasePassword;

	/**
	 * A user with DBA privileges on the database. This is the user that Impex will connect to the database as to issue
	 * DBA commands for dropping/creating databases and users. This overrides <code>username</code>
	 * 
	 * @parameter expression="${dbaUser}"
	 */
	String dbaUser;

	/**
	 * The password for the DBA user. This overrides <code>password</code>
	 * 
	 * @parameter expression="${dbaPassword}"
	 */
	String dbaPassword;

	/**
	 * Lookup DBA credentials in settings.xml using this key
	 * 
	 * @parameter expression="${dbaSettingsKey}" default-value="impex.dba.${project.artifactId}"
	 */
	String dbaSettingsKey;

	/**
	 * Set this to true if you are you are including the username/password as part of the JDBC url
	 * 
	 * @parameter expression="${enableAnonymousDbaAccess}" default-value="false"
	 */
	boolean enableAnonymousDbaAccess;

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
	protected ConnectionHandler getNewConnectionHandler() throws MojoExecutionException {
		ConnectionHandler connectionHandler = super.getNewConnectionHandler();
		connectionHandler.setEnableAnonymousPassword(enableAnonymousDbaAccess);
		connectionHandler.setEnableAnonymousUsername(enableAnonymousDbaAccess);
		connectionHandler.setUrl(serverUrl);
		return connectionHandler;
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

	@Override
	protected Credentials getNewCredentials() {
		Credentials credentials = new Credentials();
		credentials.setUsername(getDbaUser());
		credentials.setPassword(getDbaPassword());
		return credentials;
	}

	@Override
	protected void validateCredentials(Credentials credentials) throws MojoExecutionException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n\n");
		sb.append("Username and password for a DBA user must be specified.\n");
		sb.append("Specify them in the plugin configuration or as a system property.\n");
		sb.append("\n");
		sb.append("For example:\n");
		sb.append("-DdbaUsername=system\n");
		sb.append("-DdbaPassword=password\n");
		sb.append("\n\n.");
		validateCredentials(credentials, enableAnonymousDbaAccess, sb.toString());
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

	public String getDbaUser() {
		return dbaUser;
	}

	public void setDbaUser(String dbaUsername) {
		this.dbaUser = dbaUsername;
	}

	public String getDbaPassword() {
		return dbaPassword;
	}

	public void setDbaPassword(String dbaPassword) {
		this.dbaPassword = dbaPassword;
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

	public boolean isEnableAnonymousDbaAccess() {
		return enableAnonymousDbaAccess;
	}

	public void setEnableAnonymousDbaAccess(boolean enableAnonymousDbaAccess) {
		this.enableAnonymousDbaAccess = enableAnonymousDbaAccess;
	}
}
