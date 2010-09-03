package org.kuali.core.db.torque;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Set;

import org.apache.torque.engine.platform.Platform;
import org.apache.xerces.dom.DocumentImpl;

public class TableHelper {
	Set<String> tableNames;
	Connection connection;
	Platform platform;
	DatabaseMetaData dbMetaData;
	int rowCount;
	DocumentImpl document;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public DatabaseMetaData getDbMetaData() {
		return dbMetaData;
	}

	public void setDbMetaData(DatabaseMetaData dbMetaData) {
		this.dbMetaData = dbMetaData;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public DocumentImpl getDocument() {
		return document;
	}

	public void setDocument(DocumentImpl document) {
		this.document = document;
	}

	public Set<String> getTableNames() {
		return tableNames;
	}

	public void setTableNames(Set<String> tableNames) {
		this.tableNames = tableNames;
	}
}
