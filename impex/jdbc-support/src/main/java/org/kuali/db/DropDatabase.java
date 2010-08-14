package org.kuali.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DropDatabase {

	public void drop() throws SQLException;

	public Connection getConnection() throws SQLException;

}
