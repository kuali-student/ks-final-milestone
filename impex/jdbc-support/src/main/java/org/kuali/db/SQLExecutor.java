package org.kuali.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;
import static org.apache.commons.io.IOUtils.*;
import static org.kuali.db.JDBCUtils.*;

/**
 * Executes SQL statements
 */
public class SQLExecutor {
	/**
	 * Call {@link #setOnError(String)} with this value to abort SQL command execution if an error is found.
	 */
	public static final String ON_ERROR_ABORT = "abort";

	/**
	 * Call {@link #setOnError(String)} with this value to continue SQL command execution until all commands have been
	 * attempted, then abort the build if an SQL error occurred in any of the commands.
	 */
	public static final String ON_ERROR_ABORT_AFTER = "abortAfter";

	/**
	 * Call {@link #setOnError(String)} with this value to continue SQL command execution if an error is found.
	 */
	public static final String ON_ERROR_CONTINUE = "continue";

	boolean keepFormat;
	String delimiterType;
	String delimiter;
	boolean printResultSet;
	String onError;
	boolean showheaders;
	String outputDelimiter;
	int totalStatements;
	int successfulStatements;
	Connection conn;
	Statement statement;
	boolean autocommit;
	boolean escapeProcessing;
	boolean append;
	List<DatabaseListener> listeners = new ArrayList<DatabaseListener>();

	public void addListener(DatabaseListener listener) {
		listeners.add(listener);
	}

	protected void fireMessageLogged(String message) {
		fireMessageLogged(message, MessagePriority.INFO);
	}

	protected void fireMessageLogged(String message, MessagePriority priority) {
		DatabaseEvent event = new DatabaseEvent(message, priority);
		for (DatabaseListener listener : listeners) {
			listener.messageLogged(event);
		}
	}

	protected void fireBeginTransaction(Transaction transaction) {
		DatabaseEvent event = new DatabaseEvent();
		event.setTransaction(transaction);
		for (DatabaseListener listener : listeners) {
			listener.beginTransaction(event);
		}
	}

	protected void fireFinishTransaction(Transaction transaction) {
		DatabaseEvent event = new DatabaseEvent();
		event.setTransaction(transaction);
		for (DatabaseListener listener : listeners) {
			listener.finishTransaction(event);
		}
	}

	public void info(String message) {
		fireMessageLogged(message);
	}

	public void debug(String message) {
		fireMessageLogged(message, MessagePriority.DEBUG);
	}

	public void error(String message) {
		fireMessageLogged(message, MessagePriority.ERROR);
	}

	public void runTransactions(Vector<Transaction> transactions) throws SQLException {
		try {
			statement = conn.createStatement();
			statement.setEscapeProcessing(escapeProcessing);

			// Process all transactions
			for (Enumeration<Transaction> e = transactions.elements(); e.hasMoreElements();) {
				Transaction t = (Transaction) e.nextElement();

				fireBeginTransaction(t);
				runTransaction(t, System.out);
				fireFinishTransaction(t);

				if (!autocommit) {
					debug("Committing transaction");
					conn.commit();
				}
			}
		} catch (IOException e) {
			throw new SQLException(e);
		} catch (SQLException e) {
			if (!autocommit && ON_ERROR_ABORT.equalsIgnoreCase(getOnError())) {
				rollbackQuietly(conn);
			}
			throw e;
		} finally {
			closeQuietly(statement, conn);
		}

		info(getSuccessfulStatements() + " of " + getTotalStatements() + " SQL statements executed successfully");

		if (ON_ERROR_ABORT_AFTER.equalsIgnoreCase(getOnError()) && totalStatements != successfulStatements) {
			throw new SQLException("Some SQL statements failed to execute");
		}
	}

	protected void runTransaction(Transaction t, PrintStream out) throws IOException, SQLException {
		Reader in = null;
		try {
			in = t.getReader();
			runStatements(in, out);
		} finally {
			closeQuietly(in);
		}
	}

	/**
	 * Exec the sql statement.
	 * 
	 * @param sql
	 *            query to execute
	 * @param out
	 *            the outputstream
	 */
	protected void execSQL(String sql, PrintStream out) throws SQLException {
		// Check and ignore empty statements
		if ("".equals(sql.trim())) {
			return;
		}

		ResultSet resultSet = null;
		try {
			totalStatements++;
			debug("SQL: " + sql);

			boolean ret;
			int updateCountTotal = 0;

			ret = statement.execute(sql);
			do {
				if (!ret) {
					int updateCount = statement.getUpdateCount();
					if (updateCount != -1) {
						updateCountTotal += updateCount;
					}
				} else {
					resultSet = statement.getResultSet();
					if (printResultSet) {
						printResultSet(resultSet, out);
					}
				}
				ret = statement.getMoreResults();
			} while (ret);

			debug(updateCountTotal + " rows affected");

			if (printResultSet) {
				StringBuffer line = new StringBuffer();
				line.append(updateCountTotal).append(" rows affected");
				out.println(line);
			}

			SQLWarning warning = conn.getWarnings();
			while (warning != null) {
				debug(warning + " sql warning");
				warning = warning.getNextWarning();
			}
			conn.clearWarnings();
			successfulStatements++;
		} catch (SQLException e) {
			error("Failed to execute: " + sql);
			if (ON_ERROR_ABORT.equalsIgnoreCase(getOnError())) {
				throw e;
			}
			error(e.toString());
		} finally {
			closeQuietly(resultSet);
		}
	}

	/**
	 * read in lines and execute them
	 * 
	 * @param reader
	 *            the reader
	 * @param out
	 *            the outputstream
	 * @throws SQLException
	 * @throws IOException
	 */
	public void runStatements(Reader reader, PrintStream out) throws SQLException, IOException {
		String line;

		StringBuffer sql = new StringBuffer();

		BufferedReader in = new BufferedReader(reader);

		while ((line = in.readLine()) != null) {
			if (!keepFormat) {
				line = line.trim();
			}

			if (!keepFormat) {
				if (line.startsWith("//")) {
					continue;
				}
				if (line.startsWith("--")) {
					continue;
				}
				StringTokenizer st = new StringTokenizer(line);
				if (st.hasMoreTokens()) {
					String token = st.nextToken();
					if ("REM".equalsIgnoreCase(token)) {
						continue;
					}
				}
			}

			if (!keepFormat) {
				sql.append(" ").append(line);
			} else {
				sql.append("\n").append(line);
			}

			// SQL defines "--" as a comment to EOL
			// and in Oracle it may contain a hint
			// so we cannot just remove it, instead we must end it
			if (!keepFormat) {
				if (SqlSplitter.containsSqlEnd(line, delimiter) == SqlSplitter.NO_END) {
					sql.append("\n");
				}
			}

			if ((delimiterType.equals(DelimiterType.NORMAL) && SqlSplitter.containsSqlEnd(line, delimiter) > 0) || (delimiterType.equals(DelimiterType.ROW) && line.trim().equals(delimiter))) {
				execSQL(sql.substring(0, sql.length() - delimiter.length()), out);
				sql.setLength(0); // clean buffer
			}
		}

		// Catch any statements not followed by ;
		if (!sql.toString().equals("")) {
			execSQL(sql.toString(), out);
		}
	}

	/**
	 * print any results in the result set.
	 * 
	 * @param rs
	 *            the resultset to print information about
	 * @param out
	 *            the place to print results
	 * @throws SQLException
	 *             on SQL problems.
	 */
	protected void printResultSet(ResultSet rs, PrintStream out) throws SQLException {
		if (rs == null) {
			return;
		}
		debug("Processing new result set.");
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		StringBuffer line = new StringBuffer();
		if (showheaders) {
			boolean first = true;
			for (int col = 1; col <= columnCount; col++) {
				String columnValue = md.getColumnName(col);

				if (columnValue != null) {
					columnValue = columnValue.trim();

					if (",".equals(outputDelimiter)) {
						columnValue = StringEscapeUtils.escapeCsv(columnValue);
					}
				}

				if (first) {
					first = false;
				} else {
					line.append(outputDelimiter);
				}
				line.append(columnValue);
			}
			out.println(line);
			line = new StringBuffer();
		}
		while (rs.next()) {
			boolean first = true;
			for (int col = 1; col <= columnCount; col++) {
				String columnValue = rs.getString(col);
				if (columnValue != null) {
					columnValue = columnValue.trim();

					if (",".equals(outputDelimiter)) {
						columnValue = StringEscapeUtils.escapeCsv(columnValue);
					}
				}

				if (first) {
					first = false;
				} else {
					line.append(outputDelimiter);
				}
				line.append(columnValue);
			}
			out.println(line);
			line = new StringBuffer();
		}
		out.println();
	}

	public boolean isKeepFormat() {
		return keepFormat;
	}

	public void setKeepFormat(boolean keepFormat) {
		this.keepFormat = keepFormat;
	}

	public String getDelimiterType() {
		return delimiterType;
	}

	public void setDelimiterType(String delimiterType) {
		this.delimiterType = delimiterType;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public boolean isPrintResultSet() {
		return printResultSet;
	}

	public void setPrintResultSet(boolean printResultSet) {
		this.printResultSet = printResultSet;
	}

	public String getOnError() {
		return onError;
	}

	public void setOnError(String onError) {
		this.onError = onError;
	}

	public boolean isShowheaders() {
		return showheaders;
	}

	public void setShowheaders(boolean showheaders) {
		this.showheaders = showheaders;
	}

	public String getOutputDelimiter() {
		return outputDelimiter;
	}

	public void setOutputDelimiter(String outputDelimiter) {
		this.outputDelimiter = outputDelimiter;
	}

	public int getTotalStatements() {
		return totalStatements;
	}

	public void setTotalStatements(int totalStatements) {
		this.totalStatements = totalStatements;
	}

	public int getSuccessfulStatements() {
		return successfulStatements;
	}

	public void setSuccessfulStatements(int successfulStatements) {
		this.successfulStatements = successfulStatements;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public boolean isAutocommit() {
		return autocommit;
	}

	public void setAutocommit(boolean autocommit) {
		this.autocommit = autocommit;
	}

	public boolean isEscapeProcessing() {
		return escapeProcessing;
	}

	public void setEscapeProcessing(boolean escapeProcessing) {
		this.escapeProcessing = escapeProcessing;
	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}
}
