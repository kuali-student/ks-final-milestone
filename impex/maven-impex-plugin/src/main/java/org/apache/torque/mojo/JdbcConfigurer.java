package org.apache.torque.mojo;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isEmpty;

import org.apache.commons.beanutils.BeanUtils;
import org.kuali.db.DatabaseType;
import org.kuali.db.JDBCConfiguration;
import org.kuali.db.JDBCUtils;

public class JdbcConfigurer {
	JDBCUtils jdbcUtils = new JDBCUtils();

	@SuppressWarnings("unchecked")
	protected <T> T getProperty(String name, Object bean) {
		try {
			return (T) BeanUtils.getProperty(bean, name);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void setProperty(Object bean, String name, Object value) {
		try {
			BeanUtils.copyProperty(bean, name, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void updateConfiguration(Object bean) {
		String url = getProperty("url", bean);
		if (isEmpty(url)) {
			throw new IllegalArgumentException(getEmptyURLErrorMessage());
		}

		JDBCConfiguration config = jdbcUtils.getDatabaseConfiguration(url);
		if (config.equals(JDBCConfiguration.UNKNOWN_CONFIG)) {
			return;
		}

		String driver = getProperty("driver", bean);
		if (isBlank(driver)) {
			setProperty(bean, "driver", config.getDriver());
		}

		String targetDatabase = getProperty("targetDatabase", bean);
		if (isBlank(targetDatabase)) {
			setProperty(bean, "targetDatabase", config.getType().toString().toLowerCase());
		}
	}

	public void validateConfiguration(Object bean) {
		String driver = getProperty("driver", bean);
		if (isBlank(driver)) {
			throw new IllegalArgumentException("No database driver. Specify one in the plugin configuration.");
		}

		String url = getProperty("url", bean);
		if (isBlank(url)) {
			throw new IllegalArgumentException(getEmptyURLErrorMessage());
		}

		String targetDatabase = getProperty("targetDatabase", bean);
		try {
			DatabaseType.valueOf(targetDatabase.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Database type of '" + targetDatabase + "' is invalid.  Valid values: " + org.springframework.util.StringUtils.arrayToCommaDelimitedString(DatabaseType.values()));
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Can't load driver class " + driver + ". Be sure to include it as a plugin dependency.");
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
}
