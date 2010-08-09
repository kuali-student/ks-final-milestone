package org.kuali.db;

/**
 * 
 * 
 */
public class DatabasePojo {
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
