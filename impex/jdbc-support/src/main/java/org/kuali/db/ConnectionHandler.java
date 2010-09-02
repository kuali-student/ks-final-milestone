package org.kuali.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConnectionHandler {
	private static final Log log = LogFactory.getLog(ConnectionHandler.class);

	public static final String DRIVER_INFO_PROPERTIES_USER = "user";
	public static final String DRIVER_INFO_PROPERTIES_PASSWORD = "password";

	String url;
	boolean autocommit = true;
	boolean skipOnConnectionError = false;
	boolean connectionError = false;
	boolean enableAnonymousPassword = false;;
	Credentials credentials;
	Properties driverProperties;
	String driver;
	boolean showPassword = false;

	protected void showConnectionInfo(Properties properties) {
		log.info("---------------------------");
		log.info("JDBC Connection Information");
		log.info("---------------------------");
		log.info("URL: " + getUrl());
		log.info("Username: " + properties.getProperty(DRIVER_INFO_PROPERTIES_USER));
		String password = properties.getProperty(DRIVER_INFO_PROPERTIES_PASSWORD);
		if (isShowPassword()) {
			log.info("Password: " + password);
		} else {
			if (StringUtils.isEmpty(password)) {
				log.info("Password: <no password was supplied>");
			} else {
				log.info("Password: " + StringUtils.repeat("*", password.length()));
			}
		}
		log.info("Driver: " + getDriver());
		log.info("---------------------------");
	}

	protected Driver getDriverInstance() throws SQLException {
		try {
			Class<?> dc = Class.forName(getDriver());
			return (Driver) dc.newInstance();
		} catch (ClassNotFoundException e) {
			throw new SQLException("Driver class not found: " + getDriver(), e);
		} catch (Exception e) {
			throw new SQLException("Failure loading driver: " + getDriver(), e);
		}
	}

	protected Properties getInfo() throws SQLException {
		Properties info = new Properties();
		info.put(DRIVER_INFO_PROPERTIES_USER, credentials.getUsername());

		if (!enableAnonymousPassword) {
			info.put(DRIVER_INFO_PROPERTIES_PASSWORD, credentials.getPassword());
		}
		if (driverProperties != null) {
			info.putAll(getDriverProperties());
		}
		return info;
	}

	public Connection getConnection() throws SQLException {
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
				// Error getting the connection but they have asked us not to throw an exception
				// Set our flag and return
				connectionError = true;
				return null;
			} else {
				// Otherwise, throw an exception
				throw new SQLException("Connection error: " + e.getMessage().toString(), e);
			}
		}
		return conn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isAutocommit() {
		return autocommit;
	}

	public void setAutocommit(boolean autocommit) {
		this.autocommit = autocommit;
	}

	public boolean isSkipOnConnectionError() {
		return skipOnConnectionError;
	}

	public void setSkipOnConnectionError(boolean skipOnConnectionError) {
		this.skipOnConnectionError = skipOnConnectionError;
	}

	public boolean isConnectionError() {
		return connectionError;
	}

	public void setConnectionError(boolean connectionError) {
		this.connectionError = connectionError;
	}

	public boolean isEnableAnonymousPassword() {
		return enableAnonymousPassword;
	}

	public void setEnableAnonymousPassword(boolean enableAnonymousPassword) {
		this.enableAnonymousPassword = enableAnonymousPassword;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public Properties getDriverProperties() {
		return driverProperties;
	}

	public void setDriverProperties(Properties driverProperties) {
		this.driverProperties = driverProperties;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public boolean isShowPassword() {
		return showPassword;
	}

	public void setShowPassword(boolean showPassword) {
		this.showPassword = showPassword;
	}

}
