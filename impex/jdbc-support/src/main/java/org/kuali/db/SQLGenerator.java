package org.kuali.db;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * 
 *
 */
public class SQLGenerator {
	PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");
	JDBCUtils jdbcUtils = new JDBCUtils();
	DefaultResourceLoader loader = new DefaultResourceLoader();
	String encoding;
	Properties properties;
	String url;
	DatabaseCommand command;
	String prefix = "classpath:org/kuali/db/";

	public SQLGenerator() {
		this(null, null, null);
	}

	public SQLGenerator(Properties properties, String url, DatabaseCommand command) {
		super();
		this.properties = properties;
		this.url = url;
		this.command = command;
	}

	protected String getSQLLocation() {
		return getLocation("/" + getPackageFriendlyName(command.toString()) + ".sql");
	}

	public String getSQL() throws IOException {
		String location = getSQLLocation();
		Resource resource = loader.getResource(location);
		String sql = IOUtils.toString(resource.getInputStream(), getEncoding());
		Properties customProperties = getCustomProperties();
		if (properties != null) {
			customProperties.putAll(properties);
		}
		sql = helper.replacePlaceholders(sql, customProperties);
		return sql;
	}

	protected String getPackageFriendlyName(String s) {
		return s.toLowerCase().replace("_", "");
	}

	protected String getLocation(String suffix) {
		JDBCConfiguration jdbcConfiguration = jdbcUtils.getDatabaseConfiguration(url);
		DatabaseType type = jdbcConfiguration.getType();
		String packageFriendlyName = getPackageFriendlyName(type.toString());
		String location = prefix + packageFriendlyName + suffix;
		return location;
	}

	protected String getCustomPropertiesLocation() {
		return getLocation("/custom.properties");
	}

	protected Properties getCustomProperties() throws IOException {
		String location = getCustomPropertiesLocation();
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

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DatabaseCommand getCommand() {
		return command;
	}

	public void setCommand(DatabaseCommand command) {
		this.command = command;
	}

	public PropertyPlaceholderHelper getHelper() {
		return helper;
	}

	public void setHelper(PropertyPlaceholderHelper helper) {
		this.helper = helper;
	}

	public JDBCUtils getJdbcUtils() {
		return jdbcUtils;
	}

	public void setJdbcUtils(JDBCUtils jdbcUtils) {
		this.jdbcUtils = jdbcUtils;
	}

	public DefaultResourceLoader getLoader() {
		return loader;
	}

	public void setLoader(DefaultResourceLoader loader) {
		this.loader = loader;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}