package org.kuali.core.db.torque;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.torque.engine.platform.Platform;
import org.apache.torque.engine.platform.PlatformFactory;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.DocumentTypeImpl;
import org.apache.xerces.util.XMLChar;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Element;

public class KualiTorqueDataDumpTask extends Task {

	/** Database URL used for JDBC connection. */
	private String databaseUrl;

	/** Database driver used for JDBC connection. */
	private String databaseDriver;

	/** Database user used for JDBC connection. */
	private String databaseUser;

	/** Database user used for JDBC connection. */
	private String databaseSchema;
	
	/** Database password used for JDBC connection. */
	private String databasePassword;
	
	private String databaseType;
	private String tableName;
	
	/** The database connection used to retrieve the data to dump. */
	private Connection conn;
	
	private String outputDirectory;

	/**
	 * Get the database url
	 * 
	 * @return The DatabaseUrl value
	 */
	public String getDatabaseUrl() {
		return databaseUrl;
	}

	/**
	 * Set the database url
	 * 
	 * @param v
	 *            The new DatabaseUrl value
	 */
	public void setDatabaseUrl(String v) {
		databaseUrl = v;
	}

	/**
	 * Get the database driver name
	 * 
	 * @return String database driver name
	 */
	public String getDatabaseDriver() {
		return databaseDriver;
	}

	/**
	 * Set the database driver name
	 * 
	 * @param v
	 *            The new DatabaseDriver value
	 */
	public void setDatabaseDriver(String v) {
		databaseDriver = v;
	}

	/**
	 * Get the database user
	 * 
	 * @return String database user
	 */
	public String getDatabaseUser() {
		return databaseUser;
	}

	/**
	 * Set the database user
	 * 
	 * @param v
	 *            The new DatabaseUser value
	 */
	public void setDatabaseUser(String v) {
		databaseUser = v;
	}

	/**
	 * Get the database password
	 * 
	 * @return String database password
	 */
	public String getDatabasePassword() {
		return databasePassword;
	}

	/**
	 * Set the database password
	 * 
	 * @param v
	 *            The new DatabasePassword value
	 */
	public void setDatabasePassword(String v) {
		databasePassword = v;
	}
	
	/**
	 * Initializes initial context
	 * 
	 * @return the context
	 * @throws Exception
	 *             generic exception
	 */
	public void execute() throws BuildException {

        log("Torque - KualiTorqueDataDump starting");
        log("Your DB settings are:");
        log("driver: " + databaseDriver);
        log("URL: " + databaseUrl);
        log("user: " + databaseUser);
        // log("password: " + databasePassword);

		
		try {
            Class.forName(databaseDriver);

			conn = DriverManager.getConnection( getDatabaseUrl(), getDatabaseUser(), getDatabasePassword() );
			log( "DB connection established", Project.MSG_DEBUG );
			//if ( databaseDriver.contains( "Oracle" ) ) {
			//	Statement stmt = conn.createStatement();
			//	stmt.execute( "ALTER SESSION SET NLS_DATE_FORMAT='MM/DD/YYYY HH24:MI:SS'" );
			//	stmt.close();
			//}

			generateXML( conn );
			
		} catch ( SQLException se ) {
			System.err.println( "SQLException while connecting to DB:" );
			se.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
            System.err.println("cannot load driver:");
            cnfe.printStackTrace();
		} catch ( Exception ex ) {
			ex.printStackTrace();
        } finally {
        	if ( conn != null ) {
        		try {
        			conn.close();
        		} catch ( SQLException ex ) {
        			ex.printStackTrace();
        		}
        	}
        }
	}

	private void generateXML( Connection con ) throws Exception {
	
        DatabaseMetaData dbMetaData = con.getMetaData();
        Platform platform = PlatformFactory.getPlatformFor(databaseType);
        
        List<String> tableList = getTableNames( dbMetaData );
        for ( String tableName : tableList ) {
        	//if ( !tableName.startsWith( "EN_DOC" ) ) continue;
        	System.out.println( "Processing: " + tableName );
            DocumentTypeImpl docType = new DocumentTypeImpl(null, "dataset", null, "data.dtd" );
            DocumentImpl doc = new DocumentImpl(docType);
            
        	Element datasetNode = doc.createElement( "dataset" );
    		Statement stmt = conn.createStatement( ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
    		StringBuffer dataSelectStatement = new StringBuffer( "SELECT * FROM " );
    		dataSelectStatement.append( tableName );
    		dataSelectStatement.append( " ORDER BY 'x'" );
    		List<String> pkFields = platform.getPrimaryKeys(dbMetaData, databaseSchema, tableName);
    		for ( String field : pkFields ) {
    			dataSelectStatement.append( ", " ).append( field );
    		}
    		ResultSet rs = stmt.executeQuery( dataSelectStatement.toString() );
    		ResultSetMetaData md = rs.getMetaData();
    		int[] columnTypes = new int[md.getColumnCount() + 1];
    		String[] columnNames = new String[md.getColumnCount() + 1];
			for ( int i = 1; i <= md.getColumnCount(); i++ ) {
				columnNames[i] = md.getColumnName( i );
				columnTypes[i] = md.getColumnType( i );
			}
    		SimpleDateFormat df = new SimpleDateFormat( "yyyyMMddHHmmss" );
    		while ( rs.next() ) {
    			Element row = doc.createElement( tableName );
    			int colCount = md.getColumnCount();
    			for ( int i = 1; i <= colCount; i++ ) {

    				Object columnValue = rs.getObject( i );
    				if ( columnValue != null ) {
	    				if ( columnTypes[i] == java.sql.Types.CLOB ) {
	    					java.sql.Clob clob = (java.sql.Clob)columnValue;
	    					Reader r = clob.getCharacterStream();
	    					StringBuffer sb = new StringBuffer();
	    					char[] buffer = new char[2000];
	    					try {
	    						int len;
	    						while ( (len = r.read( buffer )) != -1 ) {
	    							sb.append( buffer, 0, len );
	    						}
	    					} catch ( IOException ex ) {
	    						log( "IO exception processing CLOB", Project.MSG_ERR );
	    					}
	    					columnValue = sb;
	    				} else if ( columnTypes[i] == java.sql.Types.DATE || columnTypes[i] == java.sql.Types.TIMESTAMP ) {
	    					columnValue = rs.getTimestamp( i );
	    					//System.out.println( columnValue );
	    					columnValue = df.format( (java.util.Date)columnValue );
	    				}
	    				
	   					row.setAttribute( columnNames[i], xmlEscape( columnValue.toString() ) );
    				}
    			}        	
    			datasetNode.appendChild( row );
    		}
    		rs.close();
    		stmt.close();
        	doc.appendChild( datasetNode );
			XMLSerializer xmlSerializer = new XMLSerializer(
	                new PrintWriter(
	                new FileOutputStream(outputDirectory + "/" + tableName + ".xml")),
	                new OutputFormat(Method.XML, null, true));
	        xmlSerializer.serialize(doc);
        }
	}
	
	public static final String LINE_SEPARATOR = System.getProperty( "line.separator" );
	
	private String xmlEscape(String st) {
		StringBuffer buff = new StringBuffer();
		char[] block = st.toCharArray();
		String stEntity = null;
		int i, last;

		for ( i = 0, last = 0; i < block.length; i++ ) {
			if ( XMLChar.isInvalid( block[i] ) ) {
				stEntity = " ";
			}
			if ( stEntity != null ) {
				buff.append( block, last, i - last );
				buff.append( stEntity );
				stEntity = null;
				last = i + 1;
			}
		}
		if ( last < block.length ) {
			buff.append( block, last, i - last );
		}
		return buff.toString();
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	/**
	 * Get all the table names in the current database that are not system
	 * tables.
	 * 
	 * @param dbMeta
	 *            JDBC database metadata.
	 * @return The list of all the tables in a database.
	 * @throws SQLException
	 */
	public List getTableNames(DatabaseMetaData dbMeta) throws SQLException {
		log( "Getting table list..." );
		List tables = new ArrayList();
		ResultSet tableNames = null;
		// these are the entity types we want from the database
		String[] types = { "TABLE" }; // JHK: removed views from list
		try {
			tableNames = dbMeta.getTables( null, databaseSchema.toUpperCase(), tableName,
					types ); // JHK: upper-cased schema name (required by Oracle)
			while ( tableNames.next() ) {
				String name = tableNames.getString( 3 );
				tables.add( name );
			}
		} finally {
			if ( tableNames != null ) {
				tableNames.close();
			}
		}
		log( "Found " + tables.size() + " tables." );
		return tables;
	}

	public String getDatabaseSchema() {
		return databaseSchema;
	}

	public void setDatabaseSchema(String databaseSchema) {
		this.databaseSchema = databaseSchema;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}	
}
