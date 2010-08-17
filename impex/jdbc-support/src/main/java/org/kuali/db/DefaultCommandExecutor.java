package org.kuali.db;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * 
 *
 */
public class DefaultCommandExecutor {
	JDBCUtils jdbcUtils = new JDBCUtils();
	DefaultResourceLoader loader = new DefaultResourceLoader();
	String encoding;

	public void execute(DatabaseCommand command) throws SQLException {
		try {
			String url = getUrl(System.getProperties());
			JDBCConfiguration jdbcConfiguration = jdbcUtils.getDatabaseConfiguration(url);
			DatabaseType type = jdbcConfiguration.getType();
			String location = "classpath:org/kuali/db/" + getPackageFriendlyName(type.toString()) + "/" + getPackageFriendlyName(command.toString()) + ".sql";
			Resource resource = loader.getResource(location);
			String sql = IOUtils.toString(resource.getInputStream(), getEncoding());
			Properties properties = getAdditionalProperties(type);
			properties.putAll(System.getProperties());
			PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");
			sql = helper.replacePlaceholders(sql, properties);
			SQLExecutor executor = new SQLExecutor();
			Connection connection = getConnection();
			executor.setConn(connection);
			executor.executeSql(sql);
		} catch (IOException e) {
			throw new SQLException(e);
		}
	}

	protected String getPackageFriendlyName(String s) {
		return s.toLowerCase().replace("_", "");
	}

	protected Properties getAdditionalProperties(DatabaseType type) {
		String packageFriendlyName = getPackageFriendlyName(type.toString());
		String location = "classpath:org/kuali/db/" + packageFriendlyName + "/custom.properties";
		Reader input = null;
		try {
			Resource resource = loader.getResource(location);
			input = new InputStreamReader(resource.getInputStream(), getEncoding());
			Properties p = new Properties();
			p.load(input);
			return p;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	protected String getPassword(Properties props) {
		return getProperty(props, "datasource", "password");
	}

	protected String getUsername(Properties props) {
		return getProperty(props, "datasource", "username");
	}

	protected String getUrl(Properties props) {
		return getProperty(props, "datasource", "url");
	}

	protected String getProperty(Properties props, String prefix, String property) {
		String s = props.getProperty(prefix + "." + property);
		if (s == null) {
			s = props.getProperty(property);
		}
		return s;
	}

	public Connection getConnection() throws SQLException {
		String url = getUrl(System.getProperties());

		if (url == null) {
			throw new SQLException("No url specified");
		}

		JDBCConfiguration jdbcConfiguration = jdbcUtils.getDatabaseConfiguration(url);

		Properties driverInfo = new Properties();
		driverInfo.put("user", getUsername(System.getProperties()));
		driverInfo.put("password", getPassword(System.getProperties()));

		Driver driverInstance = null;

		try {
			Class<?> dc = Class.forName(jdbcConfiguration.getDriver());
			driverInstance = (Driver) dc.newInstance();
		} catch (ClassNotFoundException e) {
			throw new SQLException("Driver class not found: " + jdbcConfiguration.getDriver(), e);
		} catch (Exception e) {
			throw new SQLException("Failure loading driver: " + jdbcConfiguration.getDriver(), e);
		}

		Connection conn = driverInstance.connect(url, driverInfo);

		if (conn == null) {
			// Driver doesn't understand the URL
			throw new SQLException("No suitable Driver for " + url);
		}

		return conn;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}