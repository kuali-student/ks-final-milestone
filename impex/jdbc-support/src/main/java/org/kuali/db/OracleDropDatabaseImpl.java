package org.kuali.db;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.util.PropertyPlaceholderHelper;

public class OracleDropDatabaseImpl extends AbstractDropDatabaseImpl {

	@Override
	public void drop() throws SQLException {
		try {
			String location = "classpath:org/kuali/db/sql/oracle/drop.sql";
			Resource resource = loader.getResource(location);
			String sql = IOUtils.toString(resource.getInputStream(), getEncoding());
			PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");
			sql = helper.replacePlaceholders(sql, getDefaultOracleProperties());
			SQLExecutor executor = new SQLExecutor();
			Connection connection = getConnection();
			executor.setConn(connection);
			executor.runSql(sql);
		} catch (IOException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		Properties info = new Properties();
		info.put("user", "system");
		info.put("password", "gw570229");
		JDBCConfiguration jdbcConfiguration = jdbcUtils.getDatabaseConfiguration(DatabaseType.ORACLE);

		Driver driverInstance = null;

		try {
			Class<?> dc = Class.forName(jdbcConfiguration.getDriver());
			driverInstance = (Driver) dc.newInstance();
		} catch (ClassNotFoundException e) {
			throw new SQLException("Driver class not found: " + jdbcConfiguration.getDriver(), e);
		} catch (Exception e) {
			throw new SQLException("Failure loading driver: " + jdbcConfiguration.getDriver(), e);
		}

		String url = "";

		Connection conn = driverInstance.connect(url, info);

		if (conn == null) {
			// Driver doesn't understand the URL
			throw new SQLException("No suitable Driver for " + url);
		}
		return conn;
	}

	protected Properties getDefaultOracleProperties() throws IOException {
		String location = "classpath:org/kuali/db/sql/oracle/oracle.properties";
		Reader input = null;
		try {
			Resource resource = loader.getResource(location);
			input = new InputStreamReader(resource.getInputStream(), getEncoding());
			Properties p = new Properties();
			p.load(input);
			return p;
		} finally {
			IOUtils.closeQuietly(input);
		}
	}
}
