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

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.split;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.settings.Server;
import org.apache.maven.shared.filtering.MavenFileFilter;
import org.apache.torque.engine.platform.Platform;
import org.apache.torque.engine.platform.PlatformFactory;
import org.apache.torque.util.JdbcConfigurer;
import org.apache.torque.util.MojoDatabaseListener;
import org.kuali.core.db.torque.PropertyHandlingException;
import org.kuali.core.db.torque.Utils;
import org.kuali.db.ConnectionHandler;
import org.kuali.db.Credentials;
import org.kuali.db.JDBCUtils;
import org.kuali.db.SQLExecutor;
import org.kuali.db.Transaction;

/**
 * Abstract mojo for making use of SQLExecutor
 */
public abstract class AbstractSQLExecutorMojo extends BaseMojo {
    Utils utils = new Utils();
    JDBCUtils jdbcUtils;
    ConnectionHandler connectionHandler;
    Platform platform;

    public static final String DRIVER_INFO_PROPERTIES_USER = "user";
    public static final String DRIVER_INFO_PROPERTIES_PASSWORD = "password";

    /**
     * Call {@link #setOrder(String)} with this value to sort in ascendant order the sql files.
     */
    public static final String FILE_SORTING_ASC = "ascending";

    /**
     * Call {@link #setOrder(String)} with this value to sort in descendant order the sql files.
     */
    public static final String FILE_SORTING_DSC = "descending";

    // ////////////////////////// User Info ///////////////////////////////////

    /**
     * The type of database we are targeting (eg oracle, mysql). This is optional if <code>url</code> is supplied as the
     * database type will be automatically detected based on the <code>url</code>. If targetDatabase is explicitly
     * supplied it will override the type selected by the automatic detection logic.
     *
     * @parameter expression="${targetDatabase}"
     */
    String targetDatabase;

    /**
     * Database username. If not given, it will be looked up through <code>settings.xml</code>'s server with
     * <code>${settingsKey}</code> as key.
     *
     * @parameter expression="${username}"
     */
    String username;

    /**
     * Database password. If not given, it will be looked up through <code>settings.xml</code>'s server with
     * <code>${settingsKey}</code> as key.
     *
     * @parameter expression="${password}"
     */
    String password;

    /**
     * Ignore the password and use anonymous access.
     *
     * @parameter expression="${enableAnonymousPassword}" default-value="false"
     */
    boolean enableAnonymousPassword;

    /**
     * Ignore the username and use anonymous access.
     *
     * @parameter expression="${enableAnonymousUsername}" default-value="false"
     */
    boolean enableAnonymousUsername;

    /**
     * Additional key=value pairs separated by a comma to be passed to JDBC driver.
     *
     * @parameter expression="${driverProperties}" default-value=""
     */
    String driverProperties;

    /**
     * If set to true the password being used to connect to the database will be displayed in log messages.
     *
     * @parameter expression="${showPassword}" default-value="false"
     */
    boolean showPassword;

    /**
     * The id of the server in settings.xml containing the username/password to use.
     *
     * @parameter expression="${settingsKey}" default-value="impex.${project.artifactId}"
     */
    String settingsKey;

    /**
     * Skip execution if there is an error obtaining a connection. If this is set to true, the build will continue even
     * if there is an error obtaining a connection
     *
     * @parameter expression="${skipOnConnectionError}" default-value="false"
     */
    boolean skipOnConnectionError;

    /**
     * SQL input commands separated by <code>${delimiter}</code>.
     *
     * @parameter expression="${sqlCommand}" default-value=""
     */
    String sqlCommand = "";

    /**
     * List of files containing SQL statements to load.
     *
     * @parameter expression="${srcFiles}"
     */
    File[] srcFiles;

    // //////////////////////////////// Database info /////////////////////////
    /**
     * Database URL.
     *
     * @parameter expression="${url}"
     */
    String url;

    /**
     * Database driver classname. This parameter is optional, as the correct JDBC driver to use is detected from the
     * <code>url</code> in almost all cases (works for Oracle, MySQL, Derby, PostGresSQL, DB2, H2, HSQL, SQL Server). If
     * a driver is explicitly supplied, it will be used in place of the JDBC driver the automatic detection logic would
     * have chosen.
     *
     * @parameter expression="${driver}"
     */
    String driver;

    // //////////////////////////// Operation Configuration ////////////////////
    /**
     * Set to <code>true</code> to execute non-transactional SQL.
     *
     * @parameter expression="${autocommit}" default-value="false"
     */
    boolean autocommit;

    /**
     * Action to perform if an error is found. Possible values are <code>abort</code> and <code>continue</code>.
     *
     * @parameter expression="${onError}" default-value="abort"
     */
    String onError = SQLExecutor.ON_ERROR_ABORT;

    // //////////////////////////// Parser Configuration ////////////////////

    /**
     * Set the delimiter that separates SQL statements.
     *
     * @parameter expression="${delimiter}" default-value="/"
     */
    String delimiter = "/";

    /**
     * The delimiter type takes two values - "normal" and "row". Normal means that any occurrence of the delimiter
     * terminates the SQL command whereas with row, only a line containing just the delimiter is recognized as the end
     * of the command.<br>
     * <br>
     * For example, set delimiterType to "row" and delimiter to "/" for Oracle
     *
     * @parameter expression="${delimiterType}" default-value="row"
     */
    String delimiterType = DelimiterType.ROW;

    /**
     * Keep the format of an SQL block.
     *
     * @parameter expression="${keepFormat}" default-value="true"
     */
    boolean keepFormat = true;

    /**
     * Print header columns.
     *
     * @parameter expression="${showheaders}" default-value="true"
     */
    boolean showheaders = true;

    /**
     * If writing output to a file, append to an existing file or overwrite it?
     *
     * @parameter expression="${append}" default-value="false"
     */
    boolean append = false;

    /**
     * Argument to Statement.setEscapeProcessing If you want the driver to use regular SQL syntax then set this to
     * false.
     *
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
     */
    MavenFileFilter fileFilter;

    /**
     * The credentials to use for database access
     */
    Credentials credentials;

    protected void configureTransactions() throws MojoExecutionException {
        // default implementation does nothing
    }

    protected Properties getContextProperties() {
        Properties properties = new Properties();
        Map<String, String> environment = System.getenv();
        for (String key : environment.keySet()) {
            properties.put("env." + key, environment.get(key));
        }
        properties.putAll(getProject().getProperties());
        properties.putAll(System.getProperties());
        return properties;
    }

    protected Credentials getNewCredentials() {
        Credentials credentials = new Credentials();
        credentials.setUsername(getUsername());
        credentials.setPassword(getPassword());
        return credentials;
    }

    protected ConnectionHandler getNewConnectionHandler() throws MojoExecutionException {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        try {
            BeanUtils.copyProperties(connectionHandler, this);
            return connectionHandler;
        } catch (Exception e) {
            throw new MojoExecutionException("Error establishing connection", e);
        }
    }

    /**
     * Validate our configuration and execute SQL as appropriate
     *
     * @throws MojoExecutionException
     */
    @Override
    public void executeMojo() throws MojoExecutionException {
        jdbcUtils = new JDBCUtils();
        updateConfiguration();
        Credentials credentials = getNewCredentials();
        updateCredentials(credentials);
        validateCredentials(credentials);
        setCredentials(credentials);
        validateConfiguration();

        connectionHandler = getNewConnectionHandler();
        conn = getConnection();

        if (connectionHandler.isConnectionError() && skipOnConnectionError) {
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
            throw new MojoExecutionException("Error executing SQL", e);
        }
    }

    /**
     * Set an inline SQL command to execute.
     *
     * @param sql
     * the sql statement to add
     */
    public void addText(final String sql) {
        this.sqlCommand += sql;
    }

    /**
     * Set the delimiter that separates SQL statements. Defaults to &quot;;&quot;;
     *
     * @param delimiter
     * the new delimiter
     */
    public void setDelimiter(final String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Set the delimiter type: "normal" or "row" (default "normal").
     *
     * @param delimiterType
     * the new delimiterType
     */
    public void setDelimiterType(final String delimiterType) {
        this.delimiterType = delimiterType;
    }

    /**
     * Print headers for result sets from the statements; optional, default true.
     *
     * @param showheaders
     * <code>true</code> to show the headers, otherwise <code>false</code>
     */
    public void setShowheaders(final boolean showheaders) {
        this.showheaders = showheaders;
    }

    /**
     * whether output should be appended to or overwrite an existing file. Defaults to false.
     *
     * @param append
     * <code>true</code> to append, otherwise <code>false</code> to overwrite
     */
    public void setAppend(final boolean append) {
        this.append = append;
    }

    /**
     * whether or not format should be preserved. Defaults to false.
     *
     * @param keepformat
     * The keepformat to set
     */
    public void setKeepFormat(final boolean keepformat) {
        this.keepFormat = keepformat;
    }

    /**
     * Set escape processing for statements.
     *
     * @param enable
     * <code>true</code> to escape, otherwiser <code>false</code>
     */
    public void setEscapeProcessing(final boolean enable) {
        escapeProcessing = enable;
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

    /**
     * Attempt to automatically detect the correct JDBC driver and database type (oracle, mysql, h2, derby, etc) given a
     * JDBC url
     */
    protected void updateConfiguration() throws MojoExecutionException {
        try {
            new JdbcConfigurer().updateConfiguration(this);
        } catch (PropertyHandlingException e) {
            throw new MojoExecutionException("Error handling properties", e);
        }
        platform = PlatformFactory.getPlatformFor(targetDatabase);
    }

    /**
     * Validate that some essential configuration items are present
     */
    protected void validateConfiguration() throws MojoExecutionException {
        new JdbcConfigurer().validateConfiguration(this);
    }

    protected void validateCredentials(final Credentials credentials, final boolean anonymousAccessAllowed,
            final String validationFailureMessage) throws MojoExecutionException {
        if (anonymousAccessAllowed) {
            // If credentials aren't required, don't bother validating
            return;
        }
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        if (!isEmpty(username) && !isEmpty(password)) {
            // Both are required, and both have been supplied
            return;
        }
        throw new MojoExecutionException(validationFailureMessage);
    }

    protected void validateCredentials(final Credentials credentials) throws MojoExecutionException {
        // Both are required but one (or both) are missing
        StringBuffer sb = new StringBuffer();
        sb.append("\n\n");
        sb.append("Username and password must be specified.\n");
        sb.append("Specify them in the plugin configuration or as a system property.\n");
        sb.append("\n");
        sb.append("For example:\n");
        sb.append("-Dusername=myuser\n");
        sb.append("-Dpassword=mypassword\n");
        sb.append("\n.");
        validateCredentials(credentials, enableAnonymousUsername && enableAnonymousPassword, sb.toString());
    }

    protected boolean isNullOrEmpty(final Collection<?> c) {
        if (c == null) {
            return true;
        }
        if (c.size() == 0) {
            return true;
        }
        return false;
    }

    protected String convertNullToEmpty(final String s) {
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
    protected void updateCredentials(final Credentials credentials) {
        Server server = getServerFromSettingsKey();
        String username = getUpdatedUsername(server, credentials.getUsername());
        String password = getUpdatedPassword(server, credentials.getPassword());
        credentials.setUsername(convertNullToEmpty(username));
        credentials.setPassword(convertNullToEmpty(password));
    }

    protected Server getServerFromSettingsKey() {
        Server server = getSettings().getServer(getSettingsKey());
        if (server == null) {
            // Fall through to using the JDBC url as a key
            return getSettings().getServer("impex." + getUrl());
        } else {
            return null;
        }
    }

    protected String getUpdatedPassword(final Server server, final String password) {
        // They already gave us a password, don't mess with it
        if (!isEmpty(password)) {
            return password;
        }
        if (server != null) {
            // We've successfully located a server in settings.xml, use the password from that
            getLog().info(
                    "Located a password in settings.xml under the server id '" + server.getId() + "' Password: "
                            + getDisplayPassword(server.getPassword()));
            return server.getPassword();
        }
        getLog().info("Using default password generated from the artifact id");
        return platform.getSchemaName(getProject().getArtifactId());
    }

    protected String getDisplayPassword(final String password) {
        if (isShowPassword()) {
            return password;
        } else {
            return StringUtils.repeat("*", password.length());
        }
    }

    protected String getUpdatedUsername(final Server server, final String username) {
        // They already gave us a username, don't mess with it
        if (!isEmpty(username)) {
            return username;
        }
        if (server != null) {
            // We've successfully located a server in settings.xml, use the username from that
            getLog().info(
                    "Located a username in settings.xml under the server id '" + server.getId() + "' Username: "
                            + server.getUsername());
            return server.getUsername();
        }
        getLog().info("Using default username generated from the artifact id");
        return platform.getSchemaName(getProject().getArtifactId());
    }

    /**
     * Creates a new Connection as using the driver, url, userid and password specified. The calling method is
     * responsible for closing the connection.
     *
     * @return Connection the newly created connection.
     * @throws MojoExecutionException
     * if the UserId/Password/Url is not set or there is no suitable driver or the driver fails to load.
     * @throws SQLException
     * if there is problem getting connection with valid url
     */
    protected Connection getConnection() throws MojoExecutionException {
        try {
            return connectionHandler.getConnection();
        } catch (Exception e) {
            throw new MojoExecutionException("Error establishing connection", e);
        }
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

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getDriver() {
        return this.driver;
    }

    public void setDriver(final String driver) {
        this.driver = driver;
    }

    public void setAutocommit(final boolean autocommit) {
        this.autocommit = autocommit;
    }

    public File[] getSrcFiles() {
        return this.srcFiles;
    }

    public void setSrcFiles(final File[] files) {
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

    public void setOnError(final String action) {
        if (SQLExecutor.ON_ERROR_ABORT.equalsIgnoreCase(action)) {
            this.onError = SQLExecutor.ON_ERROR_ABORT;
        } else if (SQLExecutor.ON_ERROR_CONTINUE.equalsIgnoreCase(action)) {
            this.onError = SQLExecutor.ON_ERROR_CONTINUE;
        } else if (SQLExecutor.ON_ERROR_ABORT_AFTER.equalsIgnoreCase(action)) {
            this.onError = SQLExecutor.ON_ERROR_ABORT_AFTER;
        } else {
            throw new IllegalArgumentException(action + " is not a valid value for onError, only '"
                    + SQLExecutor.ON_ERROR_ABORT + "', '" + SQLExecutor.ON_ERROR_ABORT_AFTER + "', or '"
                    + SQLExecutor.ON_ERROR_CONTINUE + "'.");
        }
    }

    public void setSettingsKey(final String key) {
        this.settingsKey = key;
    }

    public void setDriverProperties(final String driverProperties) {
        this.driverProperties = driverProperties;
    }

    public String getSqlCommand() {
        return sqlCommand;
    }

    public void setSqlCommand(final String sqlCommand) {
        this.sqlCommand = sqlCommand;
    }

    public Vector<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(final Vector<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setFileFilter(final MavenFileFilter filter) {
        this.fileFilter = filter;
    }

    public String getTargetDatabase() {
        return targetDatabase;
    }

    public void setTargetDatabase(final String targetDatabase) {
        this.targetDatabase = targetDatabase;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(final Connection conn) {
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

    public boolean isSkipOnConnectionError() {
        return skipOnConnectionError;
    }

    public void setSkipOnConnectionError(final boolean skipOnConnectionError) {
        this.skipOnConnectionError = skipOnConnectionError;
    }

    public MavenFileFilter getFileFilter() {
        return fileFilter;
    }

    public boolean isShowPassword() {
        return showPassword;
    }

    public void setShowPassword(final boolean showPassword) {
        this.showPassword = showPassword;
    }

    public boolean isEnableAnonymousPassword() {
        return enableAnonymousPassword;
    }

    public void setEnableAnonymousPassword(final boolean enableAnonymousPassword) {
        this.enableAnonymousPassword = enableAnonymousPassword;
    }

    public String getSettingsKey() {
        return settingsKey;
    }

    public boolean isAutocommit() {
        return autocommit;
    }

    public void setSuccessfulStatements(final int successfulStatements) {
        this.successfulStatements = successfulStatements;
    }

    public void setTotalStatements(final int totalStatements) {
        this.totalStatements = totalStatements;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public Credentials getCredentials() {
        return credentials;
    }

}
