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
public class DefaultSQLGenerator {
	PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");
	JDBCUtils jdbcUtils = new JDBCUtils();
	DefaultResourceLoader loader = new DefaultResourceLoader();
	String encoding;
	Properties properties;
	String url;
	DatabaseCommand command;

	public DefaultSQLGenerator() {
		this(null, null, null);
	}

	public DefaultSQLGenerator(Properties properties, String url, DatabaseCommand command) {
		super();
		this.properties = properties;
		this.url = url;
		this.command = command;
	}

	public String getSQL() throws IOException {
		JDBCConfiguration jdbcConfiguration = jdbcUtils.getDatabaseConfiguration(url);
		DatabaseType type = jdbcConfiguration.getType();
		String location = "classpath:org/kuali/db/" + getPackageFriendlyName(type.toString()) + "/" + getPackageFriendlyName(command.toString()) + ".sql";
		Resource resource = loader.getResource(location);
		String sql = IOUtils.toString(resource.getInputStream(), getEncoding());
		sql = helper.replacePlaceholders(sql, getCustomProperties(type));
		return sql;
	}

	protected String getPackageFriendlyName(String s) {
		return s.toLowerCase().replace("_", "");
	}

	protected Properties getCustomProperties(DatabaseType type) {
		String packageFriendlyName = getPackageFriendlyName(type.toString());
		String location = "classpath:org/kuali/db/" + packageFriendlyName + "/custom.properties";
		Reader input = null;
		try {
			Resource resource = loader.getResource(location);
			input = new InputStreamReader(resource.getInputStream(), getEncoding());
			Properties p = new Properties();
			p.load(input);
			if (properties != null) {
				p.putAll(properties);
			}
			return p;
		} catch (IOException e) {
			throw new RuntimeException(e);
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

}