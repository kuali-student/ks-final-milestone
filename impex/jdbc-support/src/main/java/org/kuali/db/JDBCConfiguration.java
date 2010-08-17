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
	String customProperties;
	String dropSql;
	String createSql;

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

	public String getCustomProperties() {
		return customProperties;
	}

	public void setCustomProperties(String customProperties) {
		this.customProperties = customProperties;
	}

	public String getDropSql() {
		return dropSql;
	}

	public void setDropSql(String dropSql) {
		this.dropSql = dropSql;
	}

	public String getCreateSql() {
		return createSql;
	}

	public void setCreateSql(String createSql) {
		this.createSql = createSql;
	}
}
