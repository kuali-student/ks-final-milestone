package org.kuali.db;

import java.util.Properties;

import org.springframework.core.io.DefaultResourceLoader;

public abstract class AbstractDropDatabaseImpl implements DropDatabase {
	JDBCUtils jdbcUtils = new JDBCUtils();
	DefaultResourceLoader loader = new DefaultResourceLoader();
	Properties properties;
	String encoding;

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

}