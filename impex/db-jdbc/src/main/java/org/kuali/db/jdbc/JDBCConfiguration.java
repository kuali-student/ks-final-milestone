package org.kuali.db.jdbc;

/**
 * A pojo containing JDBC related configuration information. eg JDBC drivers, url fragments and type
 */
public class JDBCConfiguration {
    public static final JDBCConfiguration UNKNOWN_CONFIG = new JDBCConfiguration(DatabaseType.UNKNOWN);

    DatabaseType type;
    String urlFragment;
    String driver;
    DbaSql resetSql;

    public JDBCConfiguration() {
        this(null);
    }

    public JDBCConfiguration(final DatabaseType type) {
        super();
        this.type = type;
    }

    public DatabaseType getType() {
        return type;
    }

    public void setType(final DatabaseType type) {
        this.type = type;
    }

    public String getUrlFragment() {
        return urlFragment;
    }

    public void setUrlFragment(final String urlFragment) {
        this.urlFragment = urlFragment;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(final String driver) {
        this.driver = driver;
    }

    /**
     * @return the resetSql
     */
    public DbaSql getResetSql() {
        return resetSql;
    }

    /**
     * @param resetSql
     * the resetSql to set
     */
    public void setResetSql(final DbaSql resetSql) {
        this.resetSql = resetSql;
    }
}
