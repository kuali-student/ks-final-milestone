package org.kuali.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	public static void closeQuietly(ResultSet rs, Statement stmt, Connection conn) {
		closeQuietly(rs);
		closeQuietly(stmt);
		closeQuietly(conn);
	}

	public static void closeQuietly(Statement stmt, Connection conn) {
		closeQuietly(null, stmt, conn);
	}

	public static void closeQuietly(ResultSet rs) {
		if (rs == null) {
			return;
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// ignore
		}
	}

	public static void closeQuietly(Statement stmt) {
		if (stmt == null) {
			return;
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// ignore
		}
	}

	public static void closeQuietly(Connection conn) {
		if (conn == null) {
			return;
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// ignore
		}
	}

	public static void rollbackQuietly(Connection conn) {
		if (conn == null) {
			return;
		}
		try {
			conn.rollback();
		} catch (SQLException e) {
			// ignore
		}
	}
}
