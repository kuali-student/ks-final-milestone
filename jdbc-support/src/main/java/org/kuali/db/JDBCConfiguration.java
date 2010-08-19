package org.kuali.db;

/**
 * A pojo containing JDBC related configuration information. eg JDBC drivers, url fragments and type
 */
public class JDBCConfiguration {
	public static final JDBCConfiguration UNKNOWN_CONFIG = new JDBCConfiguration(DatabaseType.UNKNOWN);

	public JDBCConfiguration() {
		this(null);
	}

	public JDBCConfiguration(DatabaseType type) {
		super();
		this.type = type;
	}

	DatabaseType type;
	String urlFragment;
	String driver;
	public DatabaseType getType() {
		return type;
	}

	public void setType(DatabaseType type) {
		this.type = type;
	}

	public String getUrlFragment() {
		return urlFragment;
	}

	public void setUrlFragment(String urlFragment) {
		this.urlFragment = urlFragment;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
}
