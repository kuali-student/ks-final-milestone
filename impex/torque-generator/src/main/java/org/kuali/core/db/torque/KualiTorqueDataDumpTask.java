package org.kuali.core.db.torque;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.torque.engine.database.model.Column;
import org.apache.torque.engine.database.model.Database;
import org.apache.torque.engine.database.model.Table;
import org.apache.torque.engine.platform.Platform;
import org.apache.torque.engine.platform.PlatformFactory;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.DocumentTypeImpl;
import org.apache.xerces.util.XMLChar;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Element;

import static java.sql.Types.*;

/**
 * This task dumps the tables specified by schema.xml to the file system. One table per XML file.
 */
public class KualiTorqueDataDumpTask extends Task {
	NumberFormat nf = NumberFormat.getInstance();
	private static final String FS = System.getProperty("file.separator");

	/**
	 * Database we are dumping
	 */
	Database database;

	/**
	 * Encoding to use
	 */
	private String encoding;

	/**
	 * JDBC URL
	 */
	private String url;

	/**
	 * JDBC driver
	 */
	private String driver;

	/**
	 * Database username
	 */
	private String username;

	/**
	 * Database schema
	 */
	private String schema;

	/**
	 * Database password
	 */
	private String password;

	/**
	 * XML file describing the schema
	 */
	private String schemaXMLFile;

	/**
	 * Oracle, mysql, postgres, etc
	 */
	private String databaseType;

	/**
	 * The database connection used to retrieve the data to dump.
	 */
	private Connection connection;

	/**
	 * The directory where XML files will be written
	 */
	private File outputDirectory;

	/**
	 * The format to use for dates/timestamps
	 */
	private String dateFormat = "yyyyMMddHHmmss";

	/**
	 * Dump the data to XML files
	 */
	public void execute() throws BuildException {
		nf.setMaximumFractionDigits(0);
		nf.setMinimumFractionDigits(0);

		log("Impex - Starting Data Dump");
		log("Driver: " + getDriver());
		log("URL: " + getUrl());
		log("Username: " + getUsername());
		log("Schema: " + getSchema());

		try {

			File file = new File(getSchemaXMLFile());
			if (!file.exists()) {
				throw new BuildException("Unable to locate: " + getSchemaXMLFile());
			}
			// Get an xml parser for the schema XML
			KualiXmlToAppData xmlParser = new KualiXmlToAppData(getDatabaseType(), "");
			// Parse schema XML into a database object
			Database database = xmlParser.parseFile(getSchemaXMLFile());
			setDatabase(database);

			Class.forName(getDriver());
			connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
			log("DB connection established", Project.MSG_DEBUG);
			generateXML(connection);
		} catch (Exception e) {
			throw new BuildException(e);
		} finally {
			closeQuietly(connection);
		}
	}

	protected String getDataSelectStatement(Platform platform, DatabaseMetaData dbMetaData, String tableName) throws SQLException {
		StringBuffer sb = new StringBuffer("SELECT * FROM ");
		sb.append(tableName);
		sb.append(" ORDER BY 'x'");
		List<String> pkFields = platform.getPrimaryKeys(dbMetaData, getSchema(), tableName);
		for (String field : pkFields) {
			sb.append(", ").append(field);
		}
		return sb.toString();
	}

	protected Column[] getColumns(ResultSetMetaData md) throws SQLException {
		Column[] columns = new Column[md.getColumnCount() + 1];
		for (int i = 1; i <= md.getColumnCount(); i++) {
			Column column = new Column();
			column.setName(md.getColumnName(i));
			column.setJdbcType(md.getColumnType(i));
			columns[i] = column;
		}
		return columns;
	}

	protected Object getColumnValue(ResultSet rs, int index, Column column) throws SQLException {
		Object columnValue = rs.getObject(index);
		if (columnValue == null) {
			return null;
		}
		switch (column.getJdbcType()) {
		case (CLOB):
			return getClob((Clob) columnValue);
		case (DATE):
		case (TIMESTAMP):
			return getDate(rs, index);
		default:
			return columnValue;
		}
	}

	protected String getDate(ResultSet rs, int index) throws SQLException {
		SimpleDateFormat df = new SimpleDateFormat(getDateFormat());
		Timestamp date = rs.getTimestamp(index);
		return df.format(date);
	}

	protected String getClob(Clob clob) throws SQLException {
		Reader r = null;
		StringBuffer sb = new StringBuffer();
		try {
			r = clob.getCharacterStream();
			char[] buffer = new char[4096];
			int len;
			while ((len = r.read(buffer)) != -1) {
				sb.append(buffer, 0, len);
			}
		} catch (IOException e) {
			throw new SQLException(e);
		} finally {
			IOUtils.closeQuietly(r);
		}
		return sb.toString();
	}

	protected Element getRow(DocumentImpl doc, String tableName, ResultSetMetaData md, ResultSet rs, Column[] columns) throws SQLException {
		Element row = doc.createElement(tableName);
		int colCount = md.getColumnCount();
		for (int i = 1; i <= colCount; i++) {
			Object columnValue = getColumnValue(rs, i, columns[i]);
			if (columnValue == null) {
				continue;
			}
			row.setAttribute(columns[i].getName(), xmlEscape(columnValue.toString()));
		}
		return row;
	}

	protected Element getDatasetNode(DocumentImpl document, Platform platform, DatabaseMetaData dbMetaData, String tableName) throws SQLException {
		Element datasetNode = document.createElement("dataset");
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String query = getDataSelectStatement(platform, dbMetaData, tableName);
			stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(query);
			ResultSetMetaData md = rs.getMetaData();
			Column[] columns = getColumns(md);
			while (rs.next()) {
				log("processing row of " + tableName, Project.MSG_DEBUG);
				Element row = getRow(document, tableName, md, rs, columns);
				datasetNode.appendChild(row);
			}
		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			closeQuietly(rs);
			closeQuietly(stmt);
		}
		return datasetNode;
	}

	protected String getSystemId() {
		if (getDatabase().getName() != null) {
			return getDatabase().getName() + "-data.dtd";
		} else if (getSchema() != null) {
			return getSchema() + "-data.dtd";
		} else {
			return "data.dtd";
		}

	}

	protected DocumentImpl getDocument(String tableName, Platform platform, DatabaseMetaData dbMetaData) throws SQLException {
		DocumentTypeImpl docType = new DocumentTypeImpl(null, "dataset", null, getSystemId());
		DocumentImpl doc = new DocumentImpl(docType);
		Element datasetNode = getDatasetNode(doc, platform, dbMetaData, tableName);
		doc.appendChild(datasetNode);
		return doc;
	}

	protected List<String> getTableNamesFromTableObjects(List<?> list) {
		List<String> names = new ArrayList<String>();
		for (Object object : list) {
			Table table = (Table) object;
			names.add(table.getName());
		}
		return names;
	}

	protected Set<String> getSet(List<String> list) {
		Set<String> set = new TreeSet<String>();
		set.addAll(list);
		return set;
	}

	protected void generateXML(Connection con) throws Exception {
		DatabaseMetaData dbMetaData = con.getMetaData();
		Platform platform = PlatformFactory.getPlatformFor(getDatabaseType());
		Set<String> jdbcTableNames = getSet(getJDBCTableNames(dbMetaData));
		log("JDBC Table Count: " + jdbcTableNames.size());
		Set<String> schemaXMLNames = getSet(getTableNamesFromTableObjects(getDatabase().getTables()));
		Set<String> extraTables = SetUtils.difference(jdbcTableNames, schemaXMLNames);
		Set<String> missingTables = SetUtils.difference(schemaXMLNames, jdbcTableNames);
		Set<String> intersection = SetUtils.intersection(jdbcTableNames, schemaXMLNames);
		log("Schema XML Table Count: " + schemaXMLNames.size());
		log("Tables present in both: " + intersection.size());
		log("Tables in JDBC that will not be dumped: " + extraTables.size());
		if (missingTables.size() > 0) {
			throw new BuildException("There are " + missingTables.size() + " tables defined in " + getSchemaXMLFile() + " that are not being returned by JDBC [" + missingTables + "]");
		}
		processTables(intersection, platform, dbMetaData);

	}

	protected void processTables(Set<String> tableNames, Platform platform, DatabaseMetaData dbMetaData) throws IOException, SQLException {
		long start = System.currentTimeMillis();
		for (String tableName : tableNames) {
			processTable(tableName, platform, dbMetaData);
		}
		long elapsed = System.currentTimeMillis() - start;
		log("Processed " + tableNames.size() + " tables [" + getElapsed(elapsed) + " ms]");
	}

	protected String getElapsed(long elapsed) {
		return nf.format(elapsed);
	}

	protected void processTable(String tableName, Platform platform, DatabaseMetaData dbMetaData) throws SQLException, IOException {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(1);
		nf.setMinimumFractionDigits(1);
		log("Processing: " + tableName, Project.MSG_DEBUG);
		long ts1 = System.currentTimeMillis();
		DocumentImpl doc = getDocument(tableName, platform, dbMetaData);
		long ts2 = System.currentTimeMillis();
		log("Extracting: " + tableName + " [" + getElapsed(ts2 - ts1) + " ms]", Project.MSG_DEBUG);
		serialize(tableName, doc);
		long ts3 = System.currentTimeMillis();
		log("Serializing: " + tableName + " [" + getElapsed(ts3 - ts2) + " ms]", Project.MSG_DEBUG);
		log("Processed: " + tableName + " [" + getElapsed(ts3 - ts1) + " ms]");
	}

	protected Writer getWriter(String tableName) throws FileNotFoundException {
		String filename = getOutputDirectory() + FS + tableName + ".xml";
		log("filename:" + filename, Project.MSG_DEBUG);
		return new PrintWriter(new FileOutputStream(filename));
	}

	protected XMLSerializer getSerializer(Writer out) {
		return new XMLSerializer(out, new OutputFormat(Method.XML, getEncoding(), true));
	}

	protected void serialize(String tableName, DocumentImpl doc) throws IOException {
		Writer out = null;
		try {
			out = getWriter(tableName);
			XMLSerializer serializer = getSerializer(out);
			serializer.serialize(doc);
			out.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	protected String xmlEscape(String st) {
		StringBuffer buff = new StringBuffer();
		char[] block = st.toCharArray();
		String stEntity = null;
		int i, last;

		for (i = 0, last = 0; i < block.length; i++) {
			if (XMLChar.isInvalid(block[i])) {
				stEntity = " ";
			}
			if (stEntity != null) {
				buff.append(block, last, i - last);
				buff.append(stEntity);
				stEntity = null;
				last = i + 1;
			}
		}
		if (last < block.length) {
			buff.append(block, last, i - last);
		}
		return buff.toString();
	}

	/**
	 * Get the names of all the tables in our schema
	 */
	public List<String> getJDBCTableNames(DatabaseMetaData dbMeta) throws SQLException {
		log("Getting table list...");
		// these are the entity types we want from the database
		String[] types = { "TABLE" }; // JHK: removed views from list
		List<String> tables = new ArrayList<String>();
		ResultSet tableNames = null;
		try {
			// JHK: upper-cased schema name (required by Oracle)
			tableNames = dbMeta.getTables(null, getSchema().toUpperCase(), null, types);
			while (tableNames.next()) {
				String name = tableNames.getString(3);
				tables.add(name);
			}
		} finally {
			closeQuietly(tableNames);
		}
		log("Found " + tables.size() + " tables.");
		return tables;
	}

	protected void closeQuietly(ResultSet rs, Connection c) {
		closeQuietly(rs);
		closeQuietly(c);
	}

	protected void closeQuietly(Statement stmt) {
		if (stmt == null) {
			return;
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void closeQuietly(Connection c) {
		if (c == null) {
			return;
		}
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void closeQuietly(ResultSet rs) {
		if (rs == null) {
			return;
		}
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public String getSchemaXMLFile() {
		return schemaXMLFile;
	}

	public void setSchemaXMLFile(String schemaXMLFile) {
		this.schemaXMLFile = schemaXMLFile;
	}

	public File getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}
}
