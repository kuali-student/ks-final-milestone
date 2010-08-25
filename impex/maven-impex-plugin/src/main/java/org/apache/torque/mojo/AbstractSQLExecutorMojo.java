package org.apache.torque.mojo;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;
import org.apache.maven.shared.filtering.MavenFileFilter;
import org.kuali.core.db.torque.Utils;
import org.kuali.db.DatabaseType;
import org.kuali.db.JDBCConfiguration;
import org.kuali.db.JDBCUtils;
import org.kuali.db.SQLExecutor;
import org.kuali.db.Transaction;

import static org.apache.commons.lang.StringUtils.*;

/**
 * Abstract mojo for making use of SQLExecutor
 */
public abstract class AbstractSQLExecutorMojo extends AbstractMojo {
	Utils utils = new Utils();
	JDBCUtils jdbcUtils = new JDBCUtils();

	public static final String DRIVER_INFO_PROPERTIES_USER = "user";
	public static final String DRIVER_INFO_PROPERTIES_PASSWORD = "password";

	/**
	 * Call {@link #setOrderFile(String)} with this value to sort in ascendant order the sql files.
	 */
	public static final String FILE_SORTING_ASC = "ascending";

	/**
	 * Call {@link #setOrderFile(String)} with this value to sort in descendant order the sql files.
	 */
	public static final String FILE_SORTING_DSC = "descending";

	// ////////////////////////// User Info ///////////////////////////////////

	/**
	 * The type of database we are targeting eg oracle, mysql etc
	 * 
	 * @since 1.0
	 * @parameter expression="${targetDatabase}"
	 * @required
	 */
	String targetDatabase;

	/**
	 * If no username/password is supplied, use the artifact id of the project as the username/password
	 * 
	 * @since 1.0
	 * @parameter expression="${useArtifactIdForCredentials}" default-value="false"
	 */
	boolean useArtifactIdForCredentials;

	/**
	 * If trim artifactId is true, any hyphens in the artifact id are trimmed out and if the artifactId ends in "-db"
	 * the trailing "-db" is trimmed off.
	 * 
	 * @since 1.0
	 * @parameter expression="${trimArtifactId}" default-value="false"
	 */
	boolean trimArtifactId;

	/**
	 * Database username. If not given, it will be looked up through <code>settings.xml</code>'s server with
	 * <code>${settingsKey}</code> as key.
	 * 
	 * @since 1.0
	 * @parameter expression="${username}"
	 */
	String username;

	/**
	 * Database password. If not given, it will be looked up through <code>settings.xml</code>'s server with
	 * <code>${settingsKey}</code> as key.
	 * 
	 * @since 1.0
	 * @parameter expression="${password}"
	 */
	String password;

	/**
	 * Ignore the password and use anonymous access. This may be useful for databases like MySQL which do not allow
	 * empty password parameters in the connection initialization.
	 * 
	 * @since 1.4
	 * @parameter expression="${enableAnonymousPassword}" default-value="false"
	 */
	boolean enableAnonymousPassword;

	/**
	 * Additional key=value pairs separated by comma to be passed into JDBC driver.
	 * 
	 * @since 1.0
	 * @parameter expression="${driverProperties}" default-value = ""
	 */
	String driverProperties;

	/**
	 * If set to true the password being used to connect to the database will be displayed in log messages. Default is
	 * false
	 * 
	 * @since 1.0
	 * @parameter expression="${showPassword}" default-value = "false"
	 */
	boolean showPassword;

	/**
	 * @parameter expression="${settings}"
	 * @required
	 * @since 1.0
	 * @readonly
	 */
	Settings settings;

	/**
	 * Server's <code>id</code> in <code>settings.xml</code> to look up username and password. Defaults to
	 * <code>${url}</code> if not given.
	 * 
	 * @since 1.0
	 * @parameter expression="${settingsKey}"
	 */
	String settingsKey;

	/**
	 * Skip execution when there is an error obtaining a connection. This is a special case to support databases, such
	 * as embedded Derby, that can shutdown the database via the URL (i.e. <code>shutdown=true</code>).
	 * 
	 * @since 1.1
	 * @parameter expression="${skipOnConnectionError}" default-value="false"
	 */
	boolean skipOnConnectionError;

	/**
	 * Setting this parameter to <code>true</code> will force the execution of this mojo, even if it would get skipped
	 * usually.
	 * 
	 * @parameter expression="${forceMojoExecution}" default-value=false
	 * @required
	 */
	boolean forceMojoExecution;

	/**
	 * The Maven Project Object
	 * 
	 * @parameter default-value="${project}"
	 * @required
	 * @readonly
	 */
	MavenProject project;

	/**
	 * @parameter default-value="${session}"
	 * @required
	 * @readonly
	 */
	MavenSession mavenSession;

	// ////////////////////////////// Source info /////////////////////////////

	/**
	 * SQL input commands separated by <code>${delimiter}</code>.
	 * 
	 * @since 1.0
	 * @parameter expression="${sqlCommand}" default-value=""
	 */
	String sqlCommand = "";

	/**
	 * List of files containing SQL statements to load.
	 * 
	 * @since 1.0
	 * @parameter expression="${srcFiles}"
	 */
	File[] srcFiles;

	/**
	 * When <code>true</code>, skip the execution.
	 * 
	 * @since 1.0
	 * @parameter default-value="false"
	 */
	boolean skip;

	// //////////////////////////////// Database info /////////////////////////
	/**
	 * Database URL.
	 * 
	 * @parameter expression="${url}"
	 * @required
	 * @since 1.0-beta-1
	 */
	String url;

	/**
	 * Database driver classname.
	 * 
	 * @since 1.0
	 * @parameter expression="${driver}"
	 */
	String driver;

	// //////////////////////////// Operation Configuration ////////////////////
	/**
	 * Set to <code>true</code> to execute none-transactional SQL.
	 * 
	 * @since 1.0
	 * @parameter expression="${autocommit}" default-value="false"
	 */
	boolean autocommit;

	/**
	 * Action to perform if an error is found. Possible values are <code>abort</code> and <code>continue</code>.
	 * 
	 * @since 1.0
	 * @parameter expression="${onError}" default-value="abort"
	 */
	String onError = SQLExecutor.ON_ERROR_ABORT;

	// //////////////////////////// Parser Configuration ////////////////////

	/**
	 * Set the delimiter that separates SQL statements.
	 * 
	 * @since 1.0
	 * @parameter expression="${delimiter}" default-value="/"
	 */
	String delimiter = "/";

	/**
	 * <p>
	 * The delimiter type takes two values - "normal" and "row". Normal means that any occurrence of the delimiter
	 * terminate the SQL command whereas with row, only a line containing just the delimiter is recognized as the end of
	 * the command.
	 * </p>
	 * <p>
	 * For example, set this to "go" and delimiterType to "row" for Sybase ASE or MS SQL Server.
	 * </p>
	 * 
	 * @since 1.2
	 * @parameter expression="${delimiterType}" default-value="row"
	 */
	String delimiterType = DelimiterType.ROW;

	/**
	 * Keep the format of an SQL block.
	 * 
	 * @since 1.1
	 * @parameter expression="${keepFormat}" default-value="true"
	 */
	boolean keepFormat = true;

	/**
	 * Print header columns.
	 */
	boolean showheaders = true;

	/**
	 * Encoding to use when reading SQL statements from a file.
	 * 
	 * @parameter expression="${encoding}" default-value= "${project.build.sourceEncoding}"
	 * @since 1.1
	 */
	String encoding = "";

	/**
	 * Append to an existing file or overwrite it?
	 */
	boolean append = false;

	/**
	 * Argument to Statement.setEscapeProcessing If you want the driver to use regular SQL syntax then set this to
	 * false.
	 * 
	 * @since 1.4
	 * @parameter expression="${escapeProcessing}" default-value="true"
	 */
	boolean escapeProcessing = true;

	// //////////////////////////////// Internal properties//////////////////////

	/**
	 * number of successful executed statements
	 */
	int successfulStatements = 0;

	/**
	 * number of total executed statements
	 */
	int totalStatements = 0;

	/**
	 * Database connection
	 */
	Connection conn = null;

	/**
	 * SQL transactions to perform
	 */
	Vector<Transaction> transactions = new Vector<Transaction>();

	/**
	 * @component role="org.apache.maven.shared.filtering.MavenFileFilter"
	 * @since 1.4
	 */
	MavenFileFilter fileFilter;

	/**
	 * This gets set to true if there is an error connecting to the database
	 */
	boolean connectionError = false;

	protected String getTrimmedArtifactId() {
		if (trimArtifactId) {
			return trimArtifactId(project.getArtifactId());
		} else {
			return project.getArtifactId();
		}
	}

	protected void configureTransactions() throws MojoExecutionException {
		// default implementation does nothing
	}

	protected Properties getContextProperties() {
		Properties properties = new Properties();
		Map<String, String> environment = System.getenv();
		for (String key : environment.keySet()) {
			properties.put("env." + key, environment.get(key));
		}
		properties.putAll(project.getProperties());
		properties.putAll(System.getProperties());
		return properties;
	}

	/**
	 * Validate our configuration and execute SQL as appropriate
	 * 
	 * @throws MojoExecutionException
	 */
	public void execute() throws MojoExecutionException {

		if (skipMojo()) {
			return;
		}

		updateConfiguration();
		updateCredentials();
		validateConfiguration();

		conn = getConnection();

		if (connectionError && skipOnConnectionError) {
			// There was an error obtaining a connection
			// Do not fail the build but don't do anything more
			return;
		}

		// Configure the transactions we will be running
		configureTransactions();

		// Make sure our counters are zeroed out
		successfulStatements = 0;
		totalStatements = 0;

		// Get an SQLExecutor
		SQLExecutor executor = getSqlExecutor();

		try {
			executor.execute();
		} catch (SQLException e) {
			throw new MojoExecutionException(getSQLExceptionErrorMessage(e, getInfo(), "Error executing SQL"));
		}

	}

	/**
	 * Set an inline SQL command to execute.
	 * 
	 * @param sql
	 *            the sql statement to add
	 */
	public void addText(String sql) {
		this.sqlCommand += sql;
	}

	/**
	 * Set the file encoding to use on the SQL files read in
	 * 
	 * @param encoding
	 *            the encoding to use on the files
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Set the delimiter that separates SQL statements. Defaults to &quot;;&quot;;
	 * 
	 * @param delimiter
	 *            the new delimiter
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Set the delimiter type: "normal" or "row" (default "normal").
	 * 
	 * @param delimiterType
	 *            the new delimiterType
	 */
	public void setDelimiterType(String delimiterType) {
		this.delimiterType = delimiterType;
	}

	/**
	 * Print headers for result sets from the statements; optional, default true.
	 * 
	 * @param showheaders
	 *            <code>true</code> to show the headers, otherwise <code>false</code>
	 */
	public void setShowheaders(boolean showheaders) {
		this.showheaders = showheaders;
	}

	/**
	 * whether output should be appended to or overwrite an existing file. Defaults to false.
	 * 
	 * @param append
	 *            <code>true</code> to append, otherwise <code>false</code> to overwrite
	 */
	public void setAppend(boolean append) {
		this.append = append;
	}

	/**
	 * whether or not format should be preserved. Defaults to false.
	 * 
	 * @param keepformat
	 *            The keepformat to set
	 */
	public void setKeepFormat(boolean keepformat) {
		this.keepFormat = keepformat;
	}

	/**
	 * Set escape processing for statements.
	 * 
	 * @param enable
	 *            <code>true</code> to escape, otherwiser <code>false</code>
	 */
	public void setEscapeProcessing(boolean enable) {
		escapeProcessing = enable;
	}

	/**
	 * <p>
	 * Determine if the mojo execution should get skipped.
	 * </p>
	 * This is the case if:
	 * <ul>
	 * <li>{@link #skip} is <code>true</code></li>
	 * <li>if the mojo gets executed on a project with packaging type 'pom' and {@link #forceMojoExecution} is
	 * <code>false</code></li>
	 * </ul>
	 * 
	 * @return <code>true</code> if the mojo execution should be skipped.
	 */
	protected boolean skipMojo() {
		if (skip) {
			getLog().info("Skip sql execution");
			return true;
		}

		if (!forceMojoExecution && project != null && "pom".equals(project.getPackaging())) {
			getLog().info("Skipping sql execution for project with packaging type 'pom'");
			return true;
		}

		return false;
	}

	protected SQLExecutor getSqlExecutor() throws MojoExecutionException {
		try {
			SQLExecutor executor = new SQLExecutor();
			BeanUtils.copyProperties(executor, this);
			executor.addListener(new MojoDatabaseListener(getLog()));
			return executor;
		} catch (InvocationTargetException e) {
			throw new MojoExecutionException("Error copying properties from the mojo to the SQL executor", e);
		} catch (IllegalAccessException e) {
			throw new MojoExecutionException("Error copying properties from the mojo to the SQL executor", e);
		}
	}

	protected String getEmptyURLErrorMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n");
		sb.append("No url was supplied.\n");
		sb.append("You can specify a url in the plugin configuration or provide it as a system property.\n\n");
		sb.append("For example:\n\n");
		sb.append("-Durl=jdbc:oracle:thin:@localhost:1521:XE (oracle)\n");
		sb.append("-Durl=jdbc:mysql://localhost:3306/<database> (mysql)\n");
		sb.append("\n.");
		return sb.toString();
	}

	/**
	 * Attempt to automatically detect the correct JDBC driver and database type (oracle, mysql, h2, derby, etc) given a
	 * JDBC url
	 */
	protected void updateConfiguration() throws MojoExecutionException {
		if (isEmpty(url)) {
			throw new MojoExecutionException(getEmptyURLErrorMessage());
		}

		JDBCConfiguration config = jdbcUtils.getDatabaseConfiguration(url);
		if (config.equals(JDBCConfiguration.UNKNOWN_CONFIG)) {
			return;
		}

		if (isBlank(driver)) {
			driver = config.getDriver();
		}

		if (isBlank(targetDatabase)) {
			targetDatabase = config.getType().toString().toLowerCase();
		}
	}

	/**
	 * Validate that some essential configuration items are present
	 */
	protected void validateConfiguration() throws MojoExecutionException {
		if (isBlank(driver)) {
			throw new MojoExecutionException("No database driver. Specify one in the plugin configuration.");
		}

		if (isBlank(url)) {
			throw new MojoExecutionException(getEmptyURLErrorMessage());
		}

		try {
			DatabaseType.valueOf(targetDatabase.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new MojoExecutionException("Database type of '" + targetDatabase + "' is invalid.  Valid values: " + org.springframework.util.StringUtils.arrayToCommaDelimitedString(DatabaseType.values()));
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new MojoExecutionException("Can't load driver class " + driver + ". Be sure to include it as a plugin dependency.");
		}
	}

	protected boolean isNullOrEmpty(Collection<?> c) {
		if (c == null) {
			return true;
		}
		if (c.size() == 0) {
			return true;
		}
		return false;
	}

	protected String convertNullToEmpty(String s) {
		if (s == null) {
			return "";
		} else {
			return s;
		}
	}

	/**
	 * Load username/password from settings.xml if user has not set them in JVM properties
	 * 
	 * @throws MojoExecutionException
	 */
	protected void updateCredentials() {
		if (settingsKey == null) {
			// Use the JDBC url as a key into settings.xml by default
			settingsKey = getUrl();
		}
		Server server = settings.getServer(settingsKey);
		updateUsername(server);
		updatePassword(server);
	}

	protected void updatePassword(Server server) {
		// They already gave us a password, don't mess with it
		if (getPassword() != null) {
			return;
		}
		if (server != null) {
			// We've successfully located a server in settings.xml, use the password from that
			setPassword(server.getPassword());
		} else if (useArtifactIdForCredentials) {
			// No password was explicitly set, no server was located in settings.xml and they've asked for the password
			// to default to the artifact id
			if (isTrimArtifactId()) {
				setPassword(trimArtifactId(project.getArtifactId()));
			} else {
				setPassword(project.getArtifactId());
			}
		}
		// If it is null convert it to the empty string
		setPassword(convertNullToEmpty(getPassword()));
	}

	protected void updateUsername(Server server) {
		// Username is already set, don't mess with it
		if (getUsername() != null) {
			return;
		}
		if (server != null) {
			// We've successfully located a server in settings.xml, use the username from that
			setUsername(server.getUsername());
		} else if (useArtifactIdForCredentials) {
			// No username was explicitly set, no server was located in settings.xml and they've asked for the username
			// to default to the artifact id
			if (isTrimArtifactId()) {
				setUsername(trimArtifactId(project.getArtifactId()));
			} else {
				setUsername(project.getArtifactId());
			}
		}
		// If it is null convert it to the empty string
		setUsername(convertNullToEmpty(getUsername()));
	}

	protected String trimArtifactId(String artifactId) {
		String s = StringUtils.remove(artifactId, "-db");
		return StringUtils.remove(s, "-");
	}

	protected Properties getInfo() throws MojoExecutionException {
		Properties info = new Properties();
		info.put(DRIVER_INFO_PROPERTIES_USER, getUsername());

		if (!enableAnonymousPassword) {
			info.put(DRIVER_INFO_PROPERTIES_PASSWORD, getPassword());
		}

		info.putAll(getDriverProperties());
		return info;
	}

	protected Driver getDriverInstance() throws MojoExecutionException {
		try {
			Class<?> dc = Class.forName(getDriver());
			return (Driver) dc.newInstance();
		} catch (ClassNotFoundException e) {
			throw new MojoExecutionException("Driver class not found: " + getDriver(), e);
		} catch (Exception e) {
			throw new MojoExecutionException("Failure loading driver: " + getDriver(), e);
		}
	}

	protected void showConnectionInfo(Properties properties) {
		getLog().info("---------------------------");
		getLog().info("JDBC Connection Information");
		getLog().info("---------------------------");
		getLog().info("URL: " + getUrl());
		getLog().info("Username: " + properties.getProperty(DRIVER_INFO_PROPERTIES_USER));
		if (isShowPassword()) {
			getLog().info("Password: " + properties.getProperty(DRIVER_INFO_PROPERTIES_PASSWORD));
		} else {
			getLog().info("Password: *******");
		}
		getLog().info("Driver: " + getDriver());
		getLog().info("---------------------------");
	}

	/**
	 * Creates a new Connection as using the driver, url, userid and password specified.
	 * 
	 * The calling method is responsible for closing the connection.
	 * 
	 * @return Connection the newly created connection.
	 * @throws MojoExecutionException
	 *             if the UserId/Password/Url is not set or there is no suitable driver or the driver fails to load.
	 * @throws SQLException
	 *             if there is problem getting connection with valid url
	 * 
	 */
	protected Connection getConnection() throws MojoExecutionException {
		Properties info = getInfo();
		Connection conn = null;
		try {
			Driver driverInstance = getDriverInstance();
			showConnectionInfo(info);
			conn = driverInstance.connect(getUrl(), info);

			if (conn == null) {
				// Driver doesn't understand the URL
				throw new SQLException("No suitable Driver for " + getUrl());
			}

			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			if (skipOnConnectionError) {
				// Error getting the connection but they have asked us not to fail the build
				// This mojo should now skip doing anything else, but the build may continue
				this.connectionError = true;
				return null;
			} else {
				// Otherwise, fail the build
				throw new MojoExecutionException(getConnectionErrorMessage(e, info), e);
			}
		}
		return conn;
	}

	protected String getConnectionErrorMessage(SQLException e, Properties info) {
		return getSQLExceptionErrorMessage(e, info, "The following error occurred establishing a connection to the database:");
	}

	protected String getJDBCConfigurationErrorMessage(Properties info) {
		StringBuffer sb = new StringBuffer();
		sb.append("------------------------------------------------------\n\n");
		sb.append("The following information was provided to JDBC:\n");
		sb.append("------------------------------------------------------\n");
		sb.append("URL: " + getUrl() + "\n");
		sb.append("Driver: " + getDriver() + "\n");
		sb.append("Username: " + info.getProperty(DRIVER_INFO_PROPERTIES_USER) + "\n");
		String password = info.getProperty(DRIVER_INFO_PROPERTIES_PASSWORD);
		if (isShowPassword()) {
			sb.append("Password: " + password + "\n");
		} else {
			if (StringUtils.isEmpty(password)) {
				sb.append("Password: [No password was supplied]\n");
			} else {
				sb.append("Password: *******\n");
			}
		}
		sb.append("------------------------------------------------------\n");
		sb.append("\n");
		return sb.toString();
	}

	protected String getSQLExceptionErrorMessage(SQLException e, Properties info, String message) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n");
		sb.append(message + "\n\n------------------------------------------------------\n");
		String emsg = e.getMessage();
		sb.append(emsg);
		if (!emsg.endsWith("\n")) {
			sb.append("\n");
		}
		sb.append(getJDBCConfigurationErrorMessage(info));
		return sb.toString();
	}

	/**
	 * parse driverProperties into Properties set
	 * 
	 * @return the driver properties
	 * @throws MojoExecutionException
	 */
	protected Properties getDriverProperties() throws MojoExecutionException {
		Properties properties = new Properties();

		if (isEmpty(this.driverProperties)) {
			return properties;
		}

		String[] tokens = split(this.driverProperties, ",");
		for (int i = 0; i < tokens.length; ++i) {
			String[] keyValueTokens = split(tokens[i].trim(), "=");
			if (keyValueTokens.length != 2) {
				throw new MojoExecutionException("Invalid JDBC Driver properties: " + this.driverProperties);
			}
			properties.setProperty(keyValueTokens[0], keyValueTokens[1]);
		}
		return properties;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return this.driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	void setAutocommit(boolean autocommit) {
		this.autocommit = autocommit;
	}

	public File[] getSrcFiles() {
		return this.srcFiles;
	}

	public void setSrcFiles(File[] files) {
		this.srcFiles = files;
	}

	/**
	 * Number of SQL statements executed so far that caused errors.
	 * 
	 * @return the number
	 */
	public int getSuccessfulStatements() {
		return successfulStatements;
	}

	/**
	 * Number of SQL statements executed so far, including the ones that caused errors.
	 * 
	 * @return the number
	 */
	public int getTotalStatements() {
		return totalStatements;
	}

	public String getOnError() {
		return this.onError;
	}

	public void setOnError(String action) {
		if (SQLExecutor.ON_ERROR_ABORT.equalsIgnoreCase(action)) {
			this.onError = SQLExecutor.ON_ERROR_ABORT;
		} else if (SQLExecutor.ON_ERROR_CONTINUE.equalsIgnoreCase(action)) {
			this.onError = SQLExecutor.ON_ERROR_CONTINUE;
		} else if (SQLExecutor.ON_ERROR_ABORT_AFTER.equalsIgnoreCase(action)) {
			this.onError = SQLExecutor.ON_ERROR_ABORT_AFTER;
		} else {
			throw new IllegalArgumentException(action + " is not a valid value for onError, only '" + SQLExecutor.ON_ERROR_ABORT + "', '" + SQLExecutor.ON_ERROR_ABORT_AFTER + "', or '" + SQLExecutor.ON_ERROR_CONTINUE + "'.");
		}
	}

	void setSettings(Settings settings) {
		this.settings = settings;
	}

	void setSettingsKey(String key) {
		this.settingsKey = key;
	}

	void setSkip(boolean skip) {
		this.skip = skip;
	}

	public void setDriverProperties(String driverProperties) {
		this.driverProperties = driverProperties;
	}

	public String getSqlCommand() {
		return sqlCommand;
	}

	public void setSqlCommand(String sqlCommand) {
		this.sqlCommand = sqlCommand;
	}

	public Vector<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Vector<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void setFileFilter(MavenFileFilter filter) {
		this.fileFilter = filter;
	}

	public String getTargetDatabase() {
		return targetDatabase;
	}

	public void setTargetDatabase(String targetDatabase) {
		this.targetDatabase = targetDatabase;
	}

	public String getEncoding() {
		return encoding;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public String getDelimiterType() {
		return delimiterType;
	}

	public boolean isKeepFormat() {
		return keepFormat;
	}

	public boolean isShowheaders() {
		return showheaders;
	}

	public boolean isAppend() {
		return append;
	}

	public boolean isEscapeProcessing() {
		return escapeProcessing;
	}

	public boolean isUseArtifactIdForCredentials() {
		return useArtifactIdForCredentials;
	}

	public void setUseArtifactIdForCredentials(boolean useArtifactIdForCredentials) {
		this.useArtifactIdForCredentials = useArtifactIdForCredentials;
	}

	public boolean isTrimArtifactId() {
		return trimArtifactId;
	}

	public void setTrimArtifactId(boolean trimArtifactId) {
		this.trimArtifactId = trimArtifactId;
	}

	public boolean isSkipOnConnectionError() {
		return skipOnConnectionError;
	}

	public void setSkipOnConnectionError(boolean skipOnConnectionError) {
		this.skipOnConnectionError = skipOnConnectionError;
	}

	public MavenSession getMavenSession() {
		return mavenSession;
	}

	public void setMavenSession(MavenSession mavenSession) {
		this.mavenSession = mavenSession;
	}

	public MavenFileFilter getFileFilter() {
		return fileFilter;
	}

	public boolean isShowPassword() {
		return showPassword;
	}

	public void setShowPassword(boolean showPassword) {
		this.showPassword = showPassword;
	}
}
