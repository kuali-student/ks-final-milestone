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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.torque.engine.database.model.Column;
import org.apache.torque.engine.database.model.Table;
import org.apache.torque.engine.platform.Platform;
import org.apache.torque.engine.platform.PlatformFactory;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.DocumentTypeImpl;
import org.apache.xerces.util.XMLChar;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import static org.kuali.db.JDBCUtils.*;
import org.w3c.dom.Element;

import static java.sql.Types.*;

/**
 * This task exports tables from a JDBC accessible database to XML
 */
public class KualiTorqueDataDumpTask extends DumpTask {
	Utils utils = new Utils();
	private static final String FS = System.getProperty("file.separator");

	/**
	 * The directory where XML files will be written
	 */
	private File dataXMLDir;

	/**
	 * The format to use for dates/timestamps
	 */
	private String dateFormat = "yyyyMMddHHmmss z";

	/**
	 * The formatter that will do the formatting of dates;
	 */
	private SimpleDateFormat dateFormatter;

	protected void showConfiguration() {
		super.showConfiguration();
		log("Exporting to: " + getDataXMLDir().getAbsolutePath());
		log("Date format: \"" + dateFormat + "\" - " + dateFormatter.format(new Date()));
	}

	protected void updateConfiguration(Platform platform) {
		super.updateConfiguration(platform);
		dateFormatter = new SimpleDateFormat(dateFormat);
	}

	/**
	 * Dump the data to XML files
	 */
	public void execute() throws BuildException {

		try {
			log("--------------------------------------");
			log("Impex - Data Export");
			log("--------------------------------------");
			Platform platform = PlatformFactory.getPlatformFor(targetDatabase);
			updateConfiguration(platform);
			showConfiguration();

			// Generate the XML
			generateXML(platform);
		} catch (Exception e) {
			throw new BuildException(e);
		}
	}

	/**
	 * Generate a SQL statement that selects all data from the table
	 */
	protected String getDataSelectStatement(TableHelper helper, String tableName) throws SQLException {
		StringBuffer sb = new StringBuffer("SELECT * FROM ");
		sb.append(tableName);
		sb.append(" ORDER BY 'x'");
		List<String> pkFields = helper.getPlatform().getPrimaryKeys(helper.getDbMetaData(), getSchema(), tableName);
		for (String field : pkFields) {
			sb.append(", ").append(field);
		}
		return sb.toString();
	}

	/**
	 * Generate an array of Column objects from the result set metadata
	 */
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

	/**
	 * Extract a column value from the result set, converting as needed
	 */
	protected Object getColumnValue(ResultSet rs, int index, Column column) throws SQLException {
		// Extract a raw object
		Object columnValue = rs.getObject(index);

		// If it is null we're done
		if (columnValue == null) {
			return null;
		}
		// Handle special types
		switch (column.getJdbcType()) {
		case (CLOB):
			// Extract a CLOB
			return getClob((Clob) columnValue);
		case (DATE):
		case (TIMESTAMP):
			// Extract dates and timestamps
			return getDate(rs, index);
		default:
			// Otherwise return the raw object
			return columnValue;
		}
	}

	/**
	 * Convert a JDBC Timestamp into a java.util.Date using the specified format
	 */
	protected String getDate(ResultSet rs, int index) throws SQLException {
		Timestamp date = rs.getTimestamp(index);
		return dateFormatter.format(date);
	}

	/**
	 * Convert a CLOB to a String
	 */
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

	/**
	 * Convert a row from the result set into an Element
	 */
	protected Element getRow(DocumentImpl doc, String tableName, ResultSetMetaData md, ResultSet rs, Column[] columns, int rowCount) throws SQLException {
		// Generate a row object
		Element row = doc.createElement(tableName);
		// Cycle through the result set columns
		int colCount = md.getColumnCount();
		for (int i = 1; i <= colCount; i++) {
			// Attempt to extract a column value
			Object columnValue = null;
			try {
				columnValue = getColumnValue(rs, i, columns[i]);
			} catch (Exception ex) {
				// Don't let an issue extracting a value stop the process
				// Log the row/column and continue
				log("Problem reading row " + rowCount + " column " + columns[i] + " from " + tableName, Project.MSG_ERR);
				log(ex.getClass().getName() + " : " + ex.getMessage(), Project.MSG_ERR);
			}

			// Null values can be omitted from the XML
			if (columnValue == null) {
				continue;
			}

			// Otherwise, escape the String and add it to the row Element
			row.setAttribute(columns[i].getName(), xmlEscape(columnValue.toString()));
		}
		// Return our Element that represents one row of JDBC data from the ResultSet
		return row;
	}

	/**
	 * Generate and return the dataset Element
	 */
	protected Element getDatasetNode(TableHelper helper, DocumentImpl document, String tableName) throws SQLException {
		Element datasetNode = document.createElement("dataset");
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// This query selects everything from the table
			String query = getDataSelectStatement(helper, tableName);
			stmt = helper.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(query);
			ResultSetMetaData md = rs.getMetaData();
			Column[] columns = getColumns(md);
			int count = 0;
			// Process the ResultSet
			while (rs.next()) {
				count++;
				log("Processing row " + count + " of " + tableName, Project.MSG_DEBUG);
				Element row = getRow(document, tableName, md, rs, columns, count);
				datasetNode.appendChild(row);
			}
			helper.setRowCount(count);
			// Keep track of how many rows we found
			if (count == 0) {
				log("No data found in table " + tableName, Project.MSG_DEBUG);
				return null;
			}
		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			// Clean up
			closeQuietly(rs);
			closeQuietly(stmt);
		}
		return datasetNode;
	}

	/**
	 * Return the systemId to use
	 */
	protected String getSystemId() {
		return getArtifactId() + ".dtd";
	}

	/**
	 * Return the XML Document object that we will serialize to disk
	 */
	protected DocumentImpl getDocument(TableHelper helper, String tableName) throws SQLException {
		// Generate the document type
		DocumentTypeImpl docType = new DocumentTypeImpl(null, "dataset", null, getSystemId());
		// Generate an empty document
		DocumentImpl doc = new DocumentImpl(docType);
		// Extract the data from the table
		Element datasetNode = getDatasetNode(helper, doc, tableName);
		if (datasetNode == null) {
			// There was no data (zero rows), we are done
			return null;
		}
		// Add the dataset to the document
		doc.appendChild(datasetNode);
		// Return what we found
		return doc;
	}

	/**
	 * <code>
	 * Convert a List<Table> into a List<String> of table names
	 * </code>
	 */
	protected List<String> getTableNamesFromTableObjects(List<?> list) {
		List<String> names = new ArrayList<String>();
		for (Object object : list) {
			Table table = (Table) object;
			names.add(table.getName());
		}
		return names;
	}

	/**
	 * Convert a List to a Set
	 * 
	 * @param list
	 * @return
	 */
	protected Set<String> getSet(List<String> list) {
		Set<String> set = new TreeSet<String>();
		set.addAll(list);
		return set;
	}

	/**
	 * Generate XML from the data in the tables in the database
	 */
	protected void generateXML(Platform platform) throws Exception {
		Connection connection = null;

		try {
			connection = getConnection();
			// Get metadata about the database
			DatabaseMetaData dbMetaData = connection.getMetaData();
			// Get the correct platform (oracle, mysql etc)
			// Get ALL the table names
			Set<String> tableNames = getSet(getJDBCTableNames(dbMetaData));
			log("Table Count: " + tableNames.size());
			int completeSize = tableNames.size();

			StringFilter filterer = new StringFilter(includePatterns, excludePatterns);
			filterer.filter(tableNames.iterator());

			int filteredSize = tableNames.size();

			if (filteredSize != completeSize) {
				log("Filtered table Count: " + tableNames.size());
			} else {
				log("No table filtering applied.  Exporting all tables.");
			}

			TableHelper helper = new TableHelper();
			helper.setConnection(connection);
			helper.setPlatform(platform);
			helper.setDbMetaData(dbMetaData);
			helper.setTableNames(tableNames);

			processTables(helper);
		} catch (Exception e) {
			closeQuietly(connection);
		}
	}

	/**
	 * Process the tables, keeping track of which tables had at least one row of data
	 */
	protected void processTables(TableHelper helper) throws IOException, SQLException {
		long start = System.currentTimeMillis();
		int exportCount = 0;
		int skipCount = 0;
		for (String tableName : helper.getTableNames()) {
			boolean exported = processTable(helper, tableName);
			if (exported) {
				exportCount++;
			} else {
				skipCount++;
			}
		}
		long elapsed = System.currentTimeMillis() - start;
		log(utils.pad("Processed " + helper.getTableNames().size() + " tables", elapsed));
		log("Exported data from " + exportCount + " tables to XML");
		log("Skipped " + skipCount + " tables that had zero rows");
	}

	/**
	 * Process one table. Only create an XML file if there is at least one row of data
	 */
	protected boolean processTable(TableHelper helper, String tableName) throws SQLException, IOException {
		log("Processing: " + tableName, Project.MSG_DEBUG);
		long ts1 = System.currentTimeMillis();
		DocumentImpl doc = getDocument(helper, tableName);
		long ts2 = System.currentTimeMillis();
		log(utils.pad("Extracting: " + tableName + " ", ts2 - ts1), Project.MSG_DEBUG);
		boolean exported = false;
		if (doc != null) {
			serialize(tableName, doc);
			exported = true;
		}
		long ts3 = System.currentTimeMillis();
		log(utils.pad("Serializing: " + tableName + " ", ts3 - ts2), Project.MSG_DEBUG);
		if (!exported) {
			log(utils.pad("Rows: " + StringUtils.leftPad(helper.getRowCount() + "", 5) + " " + tableName, (ts3 - ts1)), Project.MSG_DEBUG);
		} else {
			log(utils.pad("Rows: " + StringUtils.leftPad(helper.getRowCount() + "", 5) + " " + tableName, (ts3 - ts1)));
		}
		return exported;
	}

	/**
	 * This is where the XML will be written to
	 */
	protected Writer getWriter(String tableName) throws FileNotFoundException {
		String filename = getDataXMLDir() + FS + tableName + ".xml";
		log("filename:" + filename, Project.MSG_DEBUG);
		return new PrintWriter(new FileOutputStream(filename));
	}

	/**
	 * This is the XMLSerializer responsible for outputting the XML document
	 */
	protected XMLSerializer getSerializer(Writer out) {
		return new XMLSerializer(out, new OutputFormat(Method.XML, getEncoding(), true));
	}

	/**
	 * Serialize the document
	 */
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

	/**
	 * Escape characters that would cause issues for XML parsers
	 */
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
		return tables;
	}

	public File getDataXMLDir() {
		return dataXMLDir;
	}

	public void setDataXMLDir(File outputDirectory) {
		this.dataXMLDir = outputDirectory;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
}
